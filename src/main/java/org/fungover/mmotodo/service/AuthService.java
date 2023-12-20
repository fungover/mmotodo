package org.fungover.mmotodo.service;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.fungover.mmotodo.dto.GithubUser;

import org.fungover.mmotodo.task.Task;
import org.fungover.mmotodo.user.User;
import org.fungover.mmotodo.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Map;

@Service
public class AuthService {

    @Value("${spring.security.oauth2.client.registration.github.clientId}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.github.clientSecret}")
    private String clientSecret;

    @Value("${github.api.logout.url}")
    private String githubSignOutUrl;

    private final OAuth2AuthorizedClientService authorizedClientService;

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private UserRepository userInfoRepository;

    public AuthService(OAuth2AuthorizedClientService authorizedClientService, UserRepository userInfoRepository) {
        this.authorizedClientService = authorizedClientService;
        this.userInfoRepository = userInfoRepository;
    }


    public GithubUser getUserData(@AuthenticationPrincipal OAuth2User principal) {
        Map<String, Object> attributes = principal.getAttributes();

        System.out.println(attributes);

        String userName = getAttribute(attributes, "login", String.class);
        String name = getAttribute(attributes, "name", String.class);
        Integer id = getAttribute(attributes, "id", Integer.class);
        String role = getAttribute(attributes, "type", String.class);
        String avatarUrl = getAttribute(attributes, "avatar_url", String.class);
        String githubProfileUrl = getAttribute(attributes, "html_url", String.class);
        String email = getAttribute(attributes, "email", String.class);
        String createdAt = getAttribute(attributes, "created_at", String.class);
        String updatedAt = getAttribute(attributes, "updated_at", String.class);

        return new GithubUser(userName, name, id, role, avatarUrl, githubProfileUrl, email, createdAt, updatedAt);
    }

    // Method used to get the attributes from the OAuth object and checks correct type
    private <T> T getAttribute(Map<String, Object> attributeObj, String name, Class<T> type) {
        Object attribute = attributeObj.get(name);

        if (attribute != null && type.isInstance(attribute)) {
            return type.cast(attribute);
        } else {
            logger.warn("Invalid or missing attribute '{}': {}", name, attribute);
            return null;
        }
    }


    public boolean performLogout() {
        String accessTokenValue = retrieveAccessToken();
        if (accessTokenValue != null) {
            HttpHeaders headers = createAuthHeader();
            String jsonBody = "{\"access_token\":\"" + accessTokenValue + "\"}";
            HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

            // Send request to sign out from OAuth application
            try {
                new RestTemplate().exchange(
                        githubSignOutUrl,
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

    private HttpHeaders createAuthHeader() {
        String basicAuth = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + basicAuth);
        return headers;
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

        if (attributes == null) {
            return;
        }

        HttpServletRequest req = attributes.getRequest();
        HttpServletResponse res = attributes.getResponse();

        if (req != null && res != null) {
            Cookie[] cookies = req.getCookies();

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("JSESSIONID")) {
                        cookie.setMaxAge(0);
                        cookie.setPath("/");
                        res.addCookie(cookie);
                    }
                }
            }
        }
    }

    public void updateUser(GithubUser githubUser) {
        var user = userInfoRepository.findByGithubId(githubUser.id());
        if (user == null) {
            logger.info("New user detected, {}, {}", githubUser.id(), githubUser.userName());
            user = new User();
        }

        String[] nameIndex = githubUser.name().trim().split("\\s+", 2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

        user.setFirstName(nameIndex[0]);
        user.setLastName(nameIndex[1]);
        user.setCreated(LocalDateTime.parse(githubUser.createdAt(), formatter));
        user.setUpdated(LocalDateTime.parse(githubUser.updatedAt(), formatter));
        user.setRole(githubUser.role());
        user.setGithubId(githubUser.id());
        user.setGithubUsername(githubUser.userName());
        user.setGithubProfileUrl(githubUser.githubProfile());
        user.setAvatarUrl(githubUser.avatarUrl());
        user.setGithubEmail(githubUser.email());

        userInfoRepository.save(user);
    }
}
