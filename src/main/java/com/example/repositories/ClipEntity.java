package com.example.repositories;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="clip")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClipEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String createdBy;
    private Date created;
    private String clipId;
    private String editUrl;
    private String broadcasterId;
    private String broadcasterName;
    private int viewCount;
}
