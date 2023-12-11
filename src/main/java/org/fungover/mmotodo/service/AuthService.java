package org.fungover.mmotodo.service;


import org.fungover.mmotodo.GithubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.core.annotation.AuthenticationPrincipal;


import org.springframework.security.oauth2.core.user.OAuth2User;

import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;



import java.util.Map;

@Service
public class AuthService {

    private AuthenticationManager authenticationManager;


    private LogoutHandler logoutHandler;

    @Autowired
    public AuthService(LogoutHandler logoutHandler) {
        this.logoutHandler = logoutHandler;
    }

    public GithubUser getUserData(@AuthenticationPrincipal OAuth2User principal){
        Map<String, Object> attributes = principal.getAttributes();
        String userName = attributes.get("login").toString();
        String name =  attributes.get("name").toString();
        int id = (int) attributes.get("id");
        String avatarUrl =  attributes.get("avatar_url").toString();
        String githubProfileUrl =  attributes.get("html_url").toString();

      return new GithubUser(userName, name, id, avatarUrl, githubProfileUrl);


    }


    /*public void handleLogOut() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof OAuth2AuthenticationToken) {
            // OAuth2 authentication token is present
            clearAuthenticationCookies();
        }
    }

    public boolean isUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }

    public void clearAuthenticationCookiesIfNotAuthenticated() {
        if (!isUserAuthenticated()) {
            clearAuthenticationCookies();
        }
    }

    private void clearAuthenticationCookies() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            HttpServletResponse response = attributes.getResponse();

            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    // Assuming your authentication-related cookies have specific names
                    if (cookie.getName().equals("JSESSIONID")) {
                        cookie.setMaxAge(0);  // Set the cookie's max age to 0 to delete it
                        cookie.setPath("/");   // Set the cookie's path to match the original path
                        response.addCookie(cookie);
                    }
                }
            }
        }
    }*/
}
