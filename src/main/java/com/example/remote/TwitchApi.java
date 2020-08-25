package com.example.remote;

import com.example.config.TwitchFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Interface for Twitch APIs used by this application.
 */
@FeignClient(value = "twitch", configuration = TwitchFeignConfig.class, url="${twitch.api.url}")
public interface TwitchApi {
    /**
     * Get user information from Twitch for the input login.
     * @param login
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value="/users")
    TwitchUserDTOList getUsers(@RequestParam("login") String login);
    /**
     * Create clip for the broadcaster id.
     * @param broadcasterId
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value="/clips?broadcaster_id={broadcasterId}")
    CreateClipResponseList createClip(@PathVariable String broadcasterId);
    /**
     * Get clip's meta data for input ids.
     * @param clipIds The number of ids can't exceed 100.
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value="/clips")
    ClipDTOList getClips(@RequestParam("id")List<String> clipIds);
}
