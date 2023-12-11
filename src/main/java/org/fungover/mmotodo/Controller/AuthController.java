package org.fungover.mmotodo.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.fungover.mmotodo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController

@RequestMapping(value = "/")
public class AuthController {



    @Autowired
    private AuthService authService;

    /*Data that can be access to be used in out frontend*/
    @GetMapping("/api/user")
    public ResponseEntity<Object> signIn(@AuthenticationPrincipal OAuth2User principal) {
        Optional<Object> githubUser = Optional.ofNullable(authService.getUserData(principal));
        if (githubUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Couldn't find user data");
        }
        return ResponseEntity.ok(githubUser);
    }


 /*   @GetMapping("auth/logout")
    public String logOut(HttpServletRequest request, HttpServletResponse response) {
       authService.clearAuthenticationCookiesIfNotAuthenticated();
        return "redirect:/";
    }*/
}
