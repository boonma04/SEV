package com.example.config;

import com.example.remote.TwitchAuthRequestInterceptor;
import com.example.repositories.TwitchOAuthRepository;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@Configuration
public class TwitchFeignConfig {
    @Value("${twitch.api.clientId}")
    private String twitchClientId;

    @Bean
    public RequestInterceptor twitchAuthRequestInterceptor(ClientRegistrationRepository clientRepo, TwitchOAuthRepository userRepo) {
        return new TwitchAuthRequestInterceptor(twitchClientId, clientRepo, userRepo);
    }
}
