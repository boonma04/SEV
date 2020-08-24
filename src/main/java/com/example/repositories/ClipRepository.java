package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClipRepository extends JpaRepository<ClipEntity, Long> {
    List<ClipEntity> findByCreatedBy(String userId);

    List<ClipEntity> findByClipIdIn(List<String> clipIds);

    @Query(nativeQuery = true,
        value="select sum(view_count) from clip where created_by = ?1")
    int getNumberOfViewsForMyClips(String userId);

    @Query(nativeQuery = true,
            value="select sum(view_count) as count,broadcaster_name as broadcasterName from clip where created_by = ?1 group by broadcaster_name")
    List<ViewCount> getNumberOfViewsGroupByBroadcaster(String userId);
}
