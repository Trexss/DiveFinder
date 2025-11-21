package com.divefinder.controllers.mvc;

import com.divefinder.helpers.AuthenticationHelper;
import com.divefinder.helpers.UserDtoMapper;
import com.divefinder.models.User;
import com.divefinder.models.UserLoginDto;
import com.divefinder.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationHelper authenticationHelper;
    private final UserDtoMapper userMapper;

    public AuthenticationController(UserService userService, AuthenticationHelper authenticationHelper, UserDtoMapper userMapper) {
        this.userService = userService;
        this.authenticationHelper = authenticationHelper;
        this.userMapper = userMapper;
    }
    @GetMapping("/login")
    public String showLogin(Model model){
        model.addAttribute("login", new UserLoginDto());
        return "LoginView";
    }
    @PostMapping("/login")
    public String handleLogin(@Valid @ModelAttribute("login") UserLoginDto loginDto, BindingResult bindingResult, HttpSession session ){
        User user;
        if (bindingResult.hasErrors()){
            return "LoginView";
        }
        try{
            user = userService.findUserByEmail(loginDto.getEmail());
            if (!user.getPassword().equals(loginDto.getPassword())){
                throw new com.exceptions.AuthorizationException("Username or password incorrect");

            }
        }catch (com.exceptions.EntityNotFoundException | com.exceptions.AuthorizationException e){
            bindingResult.rejectValue("username", "auth_error", e.getMessage());
            return "LoginView";
        }


        session.setAttribute("currentUser", user.getEmail());
        return "redirect:/";
    }
    @GetMapping("/logout")
    public String handleLogout(HttpSession session){
        session.removeAttribute("currentUser");
        return "redirect:/";
    }
}
