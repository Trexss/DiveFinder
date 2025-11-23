package com.divefinder.controllers.mvc;

import com.divefinder.exceptions.EntityDuplicateException;
import com.divefinder.helpers.AuthenticationHelper;
import com.divefinder.helpers.DiveSiteDtoMapper;
import com.divefinder.models.DiveSite;
import com.divefinder.models.DiveSiteDto;
import com.divefinder.models.User;
import com.divefinder.services.DiveSiteService;
import com.divefinder.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sites")
public class DiveSiteMvcController {
    private final DiveSiteService diveSiteService;
    private final UserService userService;
    private final DiveSiteDtoMapper diveSiteDtoMapper;
    private final AuthenticationHelper authenticationHelper;


    public DiveSiteMvcController(DiveSiteService diveSiteService, UserService userService, DiveSiteDtoMapper diveSiteDtoMapper, AuthenticationHelper authenticationHelper) {
        this.userService = userService;
        this.diveSiteDtoMapper = diveSiteDtoMapper;
        this.authenticationHelper = authenticationHelper;
        this.diveSiteService = diveSiteService;
    }
    @GetMapping("/{id}")
    public String showSingleDiveSite(@PathVariable int id, Model model) {
        try {
            DiveSite diveSite = diveSiteService.getSiteById(id);
            model.addAttribute("diveSite", diveSite);
            return "diveSiteView";
        } catch (com.exceptions.EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
    }
    @GetMapping("/new")
    public String showCreateDiveSiteForm(Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetUser(session);
        }catch (com.exceptions.AuthorizationException e){
            return "redirect:/auth/login";
        }
        model.addAttribute("diveSite", new DiveSiteDto());
        return "DiveSiteCreateView";
    }
    @PostMapping("/new")
    public String createBeer(@Valid @ModelAttribute("diveSite") DiveSiteDto diveSiteDto,
                             BindingResult bindingResult,
                             Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetUser(session);
        }catch (com.exceptions.AuthorizationException e){
            return "redirect:/auth/login";
        }
        if (bindingResult.hasErrors()) {
            return "DiveSiteCreateView";
        }

        try {


            DiveSite diveSite = diveSiteDtoMapper.diveSiteDtoToDiveSite(diveSiteDto);
            diveSiteService.createDiveSite(diveSite);
            return "redirect:/";
        } catch (com.exceptions.EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        } catch (EntityDuplicateException e) {
            bindingResult.rejectValue("name", "duplicate_beer", e.getMessage());
            return "DiveSiteCreateView";
        }
    }


}
