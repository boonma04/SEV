package com.example.remote;

import com.example.repositories.TwitchOAuth;
import com.example.repositories.TwitchOAuthRepository;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.var;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

public class TwitchAuthRequestInterceptor implements RequestInterceptor {
    private final TwitchOAuthRepository oauthRepo;
    private final ClientRegistrationRepository clientRepo;
    private final String clientId;

    public TwitchAuthRequestInterceptor(String clientId,
                                        ClientRegistrationRepository clientRepo,
                                        TwitchOAuthRepository oauthRepo) {
        this.clientRepo = clientRepo;
        this.oauthRepo = oauthRepo;
        this.clientId = clientId;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Client-ID", clientId);

        if (SecurityContextHolder.getContext().getAuthentication() instanceof OAuth2AuthenticationToken) {
            var authToken = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

            String currentUserId = ((DefaultOidcUser) authToken.getPrincipal()).getSubject();
            TwitchOAuth oAuth = oauthRepo.findById(currentUserId).get();
            String accessToken = oAuth.getAccessToken();
            requestTemplate.header("Authorization", "Bearer " + accessToken);
        }
    }
}
