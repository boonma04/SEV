package com.example.remote;

import com.example.config.TwitchFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "twitch", configuration = TwitchFeignConfig.class, url="${twitch.api.url}")
public interface TwitchApi {
    @RequestMapping(method = RequestMethod.GET, value="/users")
    TwitchUserDTOList getUsers(@RequestParam("login") String login);

    @RequestMapping(method = RequestMethod.POST, value="/clips?broadcaster_id={broadcasterId}")
    CreateClipResponseList createClip(@PathVariable String broadcasterId);

    @RequestMapping(method = RequestMethod.GET, value="/clips")
    ClipDTOList getClips(@RequestParam("id")List<String> clipIds);
}
