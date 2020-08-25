package com.example.config;

import com.example.authentication.TwitchAuthorizationRequestResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

/**
 * Configuration for Spring Security
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private ClientRegistrationRepository clientRegistrationRepo;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
            .and().csrf().disable()
            .oauth2Login()
                .authorizationEndpoint()
                .authorizationRequestResolver(
                        new TwitchAuthorizationRequestResolver(clientRegistrationRepo, "/oauth2/authorization"));
    }
}
