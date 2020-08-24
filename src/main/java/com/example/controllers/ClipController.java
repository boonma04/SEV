package com.example.controllers;

import com.example.remote.*;
import com.example.repositories.ClipEntity;
import com.example.repositories.ClipRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@Log
public class ClipController {
    @Value("${cron.partitionSize:10}")
    private int partitionSize;

    private final TwitchApi api;
    private final ClipRepository clipRepo;

    public ClipController(TwitchApi api, ClipRepository clipRepo) {
        this.api =api;
        this.clipRepo = clipRepo;
    }

    @RequestMapping("/")
    String index() {
        return "index";
    }

    @GetMapping("/clip")
    public String showClips(OAuth2AuthenticationToken authToken, Map<String, Object> model) {
        model.put("clips", clipRepo.findByCreatedBy(authToken.getName()));
        model.put("myClipViewCount", clipRepo.getNumberOfViewsForMyClips(authToken.getName()));
        model.put("viewCountByChannels", clipRepo.getNumberOfViewsGroupByBroadcaster(authToken.getName()));
        return "clip";
    }

    @PostMapping("/submitClip")
    public String createClip(@RequestParam("userName")String userName,
                                   OAuth2AuthenticationToken authToken) {
        log.info("Creating clip for broadcaster " + userName);

        TwitchUserDTO userDTO = Optional.of(api.getUsers(userName))
                .map(list -> list.getData())
                .filter(list -> list.size() == 1)
                .map(list -> list.get(0))
                .orElse(null);
        if (userDTO == null) {
            return "error";
        }

        CreateClipResponseList responseList = api.createClip(userDTO.getId());
        Optional.ofNullable(responseList)
                .map(list -> list.getData())
                .filter(list -> list.size() == 1)
                .map(list -> list.get(0))
                .map(dto -> ClipEntity.builder()
                            .created(new Date())
                            .createdBy(authToken.getName())
                            .broadcasterName(userDTO.getLogin())
                            .broadcasterId(userDTO.getId())
                            .clipId(dto.getId())
                            .editUrl(dto.getEditUrl())
                            .build())
                .ifPresent(entity -> {
                    log.info("Clip created with id " + entity.getClipId());
                    clipRepo.save(entity);
                });
        return "redirect:/clip";
    }

    @GetMapping("/updateViewCount")
    public String updateViewCounts() {
        log.info("Start updating view counts with partition=" + partitionSize);

        clipRepo.findAll().stream()
                .collect(Collectors.groupingBy(c -> c.getId()/partitionSize))
                .values()
                .stream()
                .forEach(this::updateViewCounts);

        return "redirect:/clip";
    }

    private void updateViewCounts(List<ClipEntity> clips) {
        List<String> ids = clips.stream()
                .map(ClipEntity::getClipId)
                .collect(Collectors.toList());

        log.info("Updating clips: " + ids);
        Map<String, ClipDTO> dtos = api.getClips(ids).getData().stream()
                .collect(Collectors.toMap(c -> c.getId(), c-> c));

        List<ClipEntity> updatedClips = clipRepo.findByClipIdIn(ids).stream()
                .filter(entity -> entity.getViewCount() != dtos.get(entity.getClipId()).getViewCount())
                .peek(entity -> entity.setViewCount(dtos.get(entity.getClipId()).getViewCount()))
                .collect(Collectors.toList());

        log.info("Updated clips: " + updatedClips.size());
        if (updatedClips.size() > 0) {
            clipRepo.saveAll(updatedClips);
        }
    }
}
