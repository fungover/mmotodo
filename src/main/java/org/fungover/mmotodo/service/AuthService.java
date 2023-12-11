package org.fungover.mmotodo.service;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.fungover.mmotodo.GithubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.view.RedirectView;


import java.util.Map;

@Service
public class AuthService {


    public GithubUser getUserData(@AuthenticationPrincipal OAuth2User principal) {
        Map<String, Object> attributes = principal.getAttributes();
        String userName = attributes.get("login").toString();
        String name = attributes.get("name").toString();
        int id = (int) attributes.get("id");
        String avatarUrl = attributes.get("avatar_url").toString();
        String githubProfileUrl = attributes.get("html_url").toString();


        return new GithubUser(userName, name, id, avatarUrl, githubProfileUrl);
    }

    public RedirectView handleLogout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof OAuth2AuthenticationToken) {
            clearAuthenticationCookies();
        }

        String signOutUrl = "https://github.com/logout";
        return new RedirectView(signOutUrl);
    }


    private void clearAuthenticationCookies() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            HttpServletResponse response = attributes.getResponse();

            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("JSESSIONID")) {
                        cookie.setMaxAge(0);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                    }
                }
            }
        }
    }
}
