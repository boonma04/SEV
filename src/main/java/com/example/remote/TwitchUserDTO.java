package com.example.remote;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TwitchUserDTO {
    private String id;
    private String login;
}
