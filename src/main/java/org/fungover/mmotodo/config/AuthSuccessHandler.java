package org.fungover.mmotodo.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.fungover.mmotodo.dto.GithubUser;
import org.fungover.mmotodo.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(AuthSuccessHandler.class);

    private final AuthService authService;

    @Autowired
    public AuthSuccessHandler(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res,
                                        Authentication authentication) throws IOException {
        logger.info("Authentication successful for user: " + authentication.getName());

        if (authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User auth = (OAuth2User) authentication.getPrincipal();
            GithubUser githubUser = authService.getUserData(auth);
            authService.updateUser(githubUser);
        }

        res.sendRedirect("/");
    }
}
