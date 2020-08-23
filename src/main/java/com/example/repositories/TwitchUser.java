package com.example.repositories;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TwitchUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;
    @NonNull
    private String twitchUserId;
    private String email;
    private String picture;
}
