package com.divefinder.helpers;


import com.divefinder.models.User;
import com.divefinder.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationHelper {
    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String INVALID_AUTHENTICATION_ERROR = "Invalid authentication.";
    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();
    private final UserService userService;

    @Autowired
    public AuthenticationHelper(UserService userService) {
        this.userService = userService;
    }

    public User tryGetUser(HttpHeaders headers) {
        if (!headers.containsKey(AUTHORIZATION_HEADER_NAME)) {
            throw new com.exceptions.AuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }

        try {
            String userInfo = headers.getFirst(AUTHORIZATION_HEADER_NAME);
            String email = getEmail(userInfo);
            String password = getPassword(userInfo);
            User user = userService.findUserByEmail(email);

            if (!encoder.matches(password, user.getPassword())) {
                throw new com.exceptions.AuthorizationException(INVALID_AUTHENTICATION_ERROR);
            }

            return user;
        } catch (com.exceptions.EntityNotFoundException e) {
            throw new com.exceptions.AuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }
    }

    private String getEmail(String userInfo) {
        int firstSpace = userInfo.indexOf(" ");
        if (firstSpace == -1) {
            throw new com.exceptions.AuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }

        return userInfo.substring(0, firstSpace);
    }

    private String getPassword(String userInfo) {
        int firstSpace = userInfo.indexOf(" ");
        if (firstSpace == -1) {
            throw new com.exceptions.AuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }

        return userInfo.substring(firstSpace + 1);
    }

    public User tryGetUser(HttpSession session){
        String currentUser = (String) session.getAttribute("currentUser");
        if (currentUser == null){
            throw new com.exceptions.AuthorizationException("No user logged in");
        }
        return userService.findUserByEmail(currentUser);
    }
    public String encodePassword(String password){
        return encoder.encode(password);
    }
    public boolean matchesPassword(String rawPassword, String encodedPassword){
        return encoder.matches(rawPassword, encodedPassword);
    }


}
