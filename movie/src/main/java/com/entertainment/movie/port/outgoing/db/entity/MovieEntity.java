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

@Entity(name = "movie")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String title;
    private String description;
    private String producer;
    private String movieLanguage;
    private int durationInMinutes;
    private Date createdAt;
    private Date updatedAt;

}
