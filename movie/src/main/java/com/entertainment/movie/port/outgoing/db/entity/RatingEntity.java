package com.entertainment.movie.port.outgoing.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity(name = "rating")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class RatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID movieId;
    private UUID userId;
    private int ratingValue;
    private String comment;
    private Date createdAt;
    private Date updatedAt;
}
