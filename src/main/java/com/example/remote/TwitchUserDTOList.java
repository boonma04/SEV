package com.example.remote;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class TwitchUserDTOList {
    private List<TwitchUserDTO> data;
}
