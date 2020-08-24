package com.example.authentication;

import com.example.repositories.TwitchOAuth;
import com.example.repositories.TwitchOAuthRepository;
import com.example.repositories.TwitchUser;
import com.example.repositories.TwitchUserRepository;
import lombok.extern.java.Log;
import lombok.var;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Log
public class UserPersister implements ApplicationListener<AuthenticationSuccessEvent> {
    private final TwitchUserRepository userRepository;
    private final TwitchOAuthRepository oauthRepository;

    public UserPersister(TwitchUserRepository userRepository, TwitchOAuthRepository oAuthRepository) {
        this.userRepository = userRepository;
        this.oauthRepository = oAuthRepository;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        var token = (OAuth2LoginAuthenticationToken)event.getSource();
        var user = (DefaultOidcUser)token.getPrincipal();

        log.info("User " + user.getEmail() + " is logging in via OAuth2");
        TwitchUser userEntity = Optional.ofNullable(userRepository.findByTwitchUserId(user.getSubject()))
                .map(u -> u.toBuilder())
                .orElseGet(() -> TwitchUser.builder())
                    .email(user.getEmail())
                    .name(user.getClaimAsString("preferred_username"))
                    .twitchUserId(user.getSubject())
                    .picture(user.getPicture())
                .build();

        if (userEntity.getId() == null) {
            log.info("New user created: " + userEntity.getName());
        } else {
            log.info("Updating existing user: " + userEntity.getId());
        }
        userEntity = userRepository.save(userEntity);

        TwitchOAuth oauth = TwitchOAuth.builder()
                .userId(userEntity.getTwitchUserId())
                .accessToken(token.getAccessToken().getTokenValue())
                .refreshToken(token.getRefreshToken().getTokenValue())
                .scopes(String.join(",", token.getAccessToken().getScopes()))
                .build();

        oauthRepository.save(oauth);
    }
}
