package com.example.repositories;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name="oauth")
public class TwitchOAuth {
    @NonNull
    @Id
    private Long userId;
    private String scopes;
    private String accessToken;
    private String refreshToken;
}
