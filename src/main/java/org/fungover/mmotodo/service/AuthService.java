package org.fungover.mmotodo.service;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.fungover.mmotodo.GithubUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

import org.springframework.stereotype.Service;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import java.util.Base64;
import java.util.Map;

@Service
public class AuthService {

    @Value("${spring.security.oauth2.client.registration.github.clientId}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.github.clientSecret}")
    private String clientSecret;

    @Value("${github.api.logout.url}")
    private String githubDeleteUrl;

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);



    public GithubUser getUserData(@AuthenticationPrincipal OAuth2User principal) {
        Map<String, Object> attributes = principal.getAttributes();
        String userName = attributes.get("login").toString();
        String name = attributes.get("name").toString();
        int id = (int) attributes.get("id");
        String avatarUrl = attributes.get("avatar_url").toString();
        String githubProfileUrl = attributes.get("html_url").toString();


        return new GithubUser(userName, name, id, avatarUrl, githubProfileUrl);
    }


    public boolean performLogout() {
        String accessTokenValue = retrieveAccessToken();

        if (accessTokenValue != null) {
            String basicAuth = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Basic " + basicAuth);
            headers.setContentType(MediaType.APPLICATION_JSON);

            String jsonBody = "{\"access_token\":\"" + accessTokenValue + "\"}";
            HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

            // Send request to sign out from OAuth application
            try {
                new RestTemplate().exchange(
                        githubDeleteUrl,
                        HttpMethod.DELETE,
                        request,
                        String.class
                );

                clearAuthenticationCookies();
                return true;
            } catch (HttpClientErrorException.UnprocessableEntity e) {
                logger.error("Couldn't sign out from GitHub: ", e.getMessage());
            } catch (Exception e) {
                logger.error("Exception during logout: ", e.getMessage());
            }
        } else {
            // Handle the case where access token retrieval fails
            logger.warn("Couldn't find access token");
        }

        return false;
    }


    public String retrieveAccessToken() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth instanceof OAuth2AuthenticationToken) {

            // Getting the access token to make a request to sign out from account
            OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) auth;
            String clientRegistrationId = token.getAuthorizedClientRegistrationId();
            OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
                    clientRegistrationId, token.getName());

            OAuth2AccessToken accessToken = authorizedClient.getAccessToken();


            return accessToken.getTokenValue();
        }
        return null;
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
