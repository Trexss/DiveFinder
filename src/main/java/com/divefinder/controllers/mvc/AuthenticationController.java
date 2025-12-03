package com.divefinder.controllers.mvc;

import com.divefinder.exceptions.EntityDuplicateException;
import com.divefinder.helpers.AuthenticationHelper;
import com.divefinder.helpers.UserDtoMapper;
import com.divefinder.models.User;
import com.divefinder.models.UserDto;
import com.divefinder.models.UserLoginDto;
import com.divefinder.models.UserRegisterDto;
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
            String rawPassword = loginDto.getPassword();
            if (!authenticationHelper.matchesPassword(rawPassword, user.getPassword())){
                bindingResult.rejectValue("email", "error.auth", null, "Username or password incorrect");
                return "LoginView";
            }
        }catch (Exception e){
            bindingResult.rejectValue("email", "error.auth", null, "Username or password incorrect");
            return "LoginView";
        }


        session.setAttribute("currentUser", user.getEmail());
        session.setAttribute("isAdmin", user.isAdmin());
        return "redirect:/";
    }
    @GetMapping("/register")
    public String showRegister(Model model){
        model.addAttribute("register", new UserRegisterDto());
        return "RegisterView";
    }
    @PostMapping("/register")
    public String handleRegister(@Valid @ModelAttribute("register") UserRegisterDto registerDto, BindingResult bindingResult, HttpSession session, Model model ){

        if (bindingResult.hasErrors()){
            return "RegisterView";
        }
        if (!registerDto.getPassword().equals(registerDto.getPasswordConfirm())){
            bindingResult.rejectValue("passwordConfirm", "error.mismatch", null, "Passwords do not match");
            return "RegisterView";
        }
        User user = userMapper.registerDtoToUser(registerDto);
        try{
            String rawPassword = user.getPassword();
            String encodedPassword = authenticationHelper.encodePassword(rawPassword);
            user.setPassword(encodedPassword);
            userService.createUser(user);
        }catch (EntityDuplicateException e){
            bindingResult.rejectValue("email", "error.duplicate", null, e.getMessage());
            return "RegisterView";
        }


        session.setAttribute("currentUser", user.getEmail());
        session.setAttribute("isAdmin", user.isAdmin());
        return "redirect:/";
    }
    @GetMapping("/logout")
    public String handleLogout(HttpSession session){
        session.removeAttribute("currentUser");
        return "redirect:/";
    }
}
