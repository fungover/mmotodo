package org.fungover.mmotodo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Value("${github.api.logout.url}")
    private String githubSignOutUrl;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/auth/logout", "/api/user",githubSignOutUrl).authenticated()
                        .requestMatchers(HttpMethod.GET, "/").permitAll()
                        .anyRequest().denyAll()
                )
                .oauth2Login(login -> login
                        .defaultSuccessUrl("/")
                );
        return http.build();
    }

}

