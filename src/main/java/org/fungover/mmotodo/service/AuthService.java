package org.fungover.mmotodo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class AuthService {

    private AuthenticationManager authenticationManager;


    private LogoutHandler logoutHandler;

    @Autowired
    public AuthService(LogoutHandler logoutHandler) {
        this.logoutHandler = logoutHandler;
    }

    public String handleAuthenticatedUserData(@AuthenticationPrincipal OAuth2User principal, Model model) {
        model.addAttribute("user", principal.getAttribute("login"));
        return "github";
    }


    public void logout(HttpServletRequest request, HttpServletResponse response) {
        // Perform logout logic
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            logoutHandler.logout(request, response, authentication);
        }
    }
}
