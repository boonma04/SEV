package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TwitchUserRepository extends JpaRepository<TwitchUser, Long> {
    TwitchUser findByTwitchUserId(String id);
}
