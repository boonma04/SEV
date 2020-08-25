package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA-based repository interface to load TwitchUser. Implementation of this interface (and other repostiory interfaces
 * in this package will be created at runtime by the JPA framework.
 */
public interface TwitchUserRepository extends JpaRepository<TwitchUser, Long> {
    TwitchUser findByTwitchUserId(String id);
}
