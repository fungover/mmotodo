package org.fungover.mmotodo.controller;

import org.fungover.mmotodo.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class AuthController {

    private AuthService authService;
    private static final Logger logger = LoggerFactory.getLogger(AuthRestController.class);


    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "auth/index";
    }

    /*Accessing this route will sign out and revoke the user from the application*/
    @GetMapping("/auth/logout")
    public String signOut(Model model) {
        boolean isSignedOut = authService.performLogout();

        if (isSignedOut) {
            model.addAttribute("logoutSuccess", true);
            logger.info("User signed out successfully");
        } else {
            model.addAttribute("logoutError", true);
            logger.error("Couldn't logout user");
        }

        return "auth/logout_status";
    }
}
