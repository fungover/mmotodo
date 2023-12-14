package org.fungover.mmotodo.controller;


import org.fungover.mmotodo.dto.GithubUser;
import org.fungover.mmotodo.service.AuthService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import org.slf4j.Logger;

@RestController
@RequestMapping()
public class AuthController {

    private AuthService authService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);


    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Instead of using @Controller to serve HTML pages
    @GetMapping("/login")
    public ResponseEntity<String> showLoginPage() throws IOException {
        ClassPathResource resource = new ClassPathResource("static/auth/index.html");
        Path path = resource.getFile().toPath();
        String content = Files.readString(path, StandardCharsets.UTF_8);

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(content);
    }

    /*Data that can access in our frontend*/
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

    /*Accessing this route will sign out the user*/
    @GetMapping("/auth/logout")
    public RedirectView signOut() {
        boolean isSignedOut = authService.performLogout();

        if (isSignedOut) {
            // Redirect user to home page where we have our home page
            return new RedirectView("/");
        } else {
            logger.error("Couldn't logout user");
            return null;
        }
    }


}
