package com.entertainment.movie.port.outgoing.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;
import java.util.UUID;

@Entity(name = "movie")
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String title;
    private String description;
    private String producer;
    private String coverImage;
    private String movieLanguage;
    private int durationInMinutes;
    private Date createdAt;
    private Date updatedAt;

    public UUID getId() {
        return id;
    }

    public MovieEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public MovieEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public MovieEntity setProducer(String producer) {
        this.producer = producer;
        return this;
    }

    public MovieEntity setCoverImage(String coverImage) {
        this.coverImage = coverImage;
        return this;
    }

    public MovieEntity setMovieLanguage(String movieLanguage) {
        this.movieLanguage = movieLanguage;
        return this;
    }

    public MovieEntity setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
        return this;
    }

}
