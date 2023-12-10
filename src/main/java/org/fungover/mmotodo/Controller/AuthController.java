package org.fungover.mmotodo.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.fungover.mmotodo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class AuthController {

    @Autowired
    private AuthService authService;
    @GetMapping()
    public String signIn(@AuthenticationPrincipal OAuth2User principal, Model model) {
        return authService.handleAuthenticatedUserData(principal, model);
    }


    @GetMapping("/auth/logout")
    public String logOut(HttpServletRequest request, HttpServletResponse response) {
        // Manually invalidate the session and log the user out
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/"; // Redirect to the home page or login page after logout
    }
}
