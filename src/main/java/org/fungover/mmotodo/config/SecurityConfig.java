package org.fungover.mmotodo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;


import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.CompositeLogoutHandler;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SecurityConfig {

  @Bean
    public LogoutHandler logoutHandler() {
        List<LogoutHandler> handlers = new ArrayList<>();
        handlers.add(new SecurityContextLogoutHandler());
        handlers.add(new CookieClearingLogoutHandler("JSESSIONID"));
        return new CompositeLogoutHandler(handlers);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/auth/logout","/api/user").authenticated()
                        .requestMatchers("signIn").permitAll()
                        .anyRequest().denyAll()
                )
                .oauth2Login(login -> login
                        .defaultSuccessUrl("/")
                ).logout(logout ->
                        logout
                                .logoutUrl("/auth/logout")
                                .logoutSuccessUrl("/signIn")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                );
        return http.build();
    }

}

