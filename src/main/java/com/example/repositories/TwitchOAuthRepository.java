package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA-based repository interface to load user's oauth data from DB
 */
public interface TwitchOAuthRepository extends JpaRepository<TwitchOAuth, String> {

}
