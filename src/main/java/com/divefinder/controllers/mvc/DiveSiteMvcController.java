package com.divefinder.controllers.mvc;

import com.divefinder.helpers.AuthenticationHelper;
import com.divefinder.helpers.DiveSiteDtoMapper;
import com.divefinder.models.DiveSite;
import com.divefinder.services.DiveSiteService;
import com.divefinder.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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


}
