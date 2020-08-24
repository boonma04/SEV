package com.example.remote;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CreateClipResponse {
    private String id;
    @JsonProperty("edit_url")
    private String editUrl;
}
