package org.fungover.mmotodo.controller;


import org.fungover.mmotodo.dto.GithubUser;
import org.fungover.mmotodo.service.AuthService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import org.slf4j.Logger;

@RestController
public class AuthRestController {

    private AuthService authService;
    private static final Logger logger = LoggerFactory.getLogger(AuthRestController.class);

    @Autowired
    public AuthRestController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/api/user")
    public ResponseEntity<Object> getUserData(@AuthenticationPrincipal OAuth2User principal) {
        try {
            Optional<GithubUser> githubUser = Optional.ofNullable(authService.getUserData(principal));

            if (githubUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Couldn't find user data");
            }
            return ResponseEntity.ok(githubUser.get());
        } catch (Exception e) {
            logger.error("Error retrieving data", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving data");
        }
    }
    @GetMapping("/csrf")
    public CsrfToken csrf(CsrfToken csrfToken) {
        return csrfToken;
    }
}

