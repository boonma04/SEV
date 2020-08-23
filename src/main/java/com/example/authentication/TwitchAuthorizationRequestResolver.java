package com.example.authentication;

import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TwitchAuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {
    private static final String AUTH_REQUEST_CLAIMS = "{ \"id_token\": { \"email\": null, \"preferred_username\": null, \"picture\": null } }";

    private final DefaultOAuth2AuthorizationRequestResolver delegate;

    public TwitchAuthorizationRequestResolver(ClientRegistrationRepository repo, String authUri) {
        delegate = new DefaultOAuth2AuthorizationRequestResolver(repo, authUri);
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
        return Optional.ofNullable(delegate.resolve(request))
                .map(this::createTwitchAuthRequest)
                .orElse(null);
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientId) {
        return Optional.ofNullable(delegate.resolve(request, clientId))
                .map(this::createTwitchAuthRequest)
                .orElse(null);
    }

    private OAuth2AuthorizationRequest createTwitchAuthRequest(OAuth2AuthorizationRequest request) {
        Map<String,Object> extraParams = new HashMap<>();
        extraParams.putAll(request.getAdditionalParameters());
        extraParams.put("claims", AUTH_REQUEST_CLAIMS);

        return OAuth2AuthorizationRequest.from(request)
                .additionalParameters(extraParams)
                .build();
    }
}
