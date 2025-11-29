package com.divefinder.controllers.mvc;

import com.divefinder.helpers.AuthenticationHelper;
import com.divefinder.models.DiveSiteDto;
import com.divefinder.models.User;
import com.divefinder.services.DiveSiteService;
import com.divefinder.services.UserService;
import com.exceptions.AuthorizationException;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminMvcController {
    private final UserService userService;
    private final AuthenticationHelper authenticationHelper;
    private final DiveSiteService diveSiteService;


    public AdminMvcController(UserService userService, AuthenticationHelper authenticationHelper, DiveSiteService diveSiteService) {
        this.userService = userService;
        this.authenticationHelper = authenticationHelper;
        this.diveSiteService = diveSiteService;
    }

    @GetMapping()
    public String showAdminView(Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetUser(session);
            if (!user.isAdmin()){
                throw new AuthorizationException("Not authorized");
            }
        }catch (com.exceptions.AuthorizationException e){
            return "redirect:/auth/login";
        }
        model.addAttribute("diveSites", diveSiteService.getAllUnapprovedSites());
        return "AdminView";
    }
    @PostMapping("{id}/approve")
    public String approveDiveSite(@PathVariable int id, HttpSession session, Model model){
        User user;
        try {
            user = authenticationHelper.tryGetUser(session);
            if (!user.isAdmin()){
                return "redirect:/auth/login";
            }
        }catch (com.exceptions.AuthorizationException e){
            return "redirect:/auth/login";
        }
        try {
            diveSiteService.approveDiveSite(id, user);
            return "redirect:/sites/" + id;
        } catch (com.exceptions.EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
    }
}
