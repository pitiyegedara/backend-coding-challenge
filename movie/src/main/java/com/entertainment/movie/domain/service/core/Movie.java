package com.entertainment.movie.domain.service.core;

import java.util.Date;
import java.util.UUID;

public class Movie {

    private UUID id;
    private String title;
    private String description;
    private String producer;
    private String coverImage;
    private String movieLanguage;
    private int durationInMinutes;

    public UUID getId() {
        return id;
    }

    public Movie setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Movie setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Movie setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getProducer() {
        return producer;
    }

    public Movie setProducer(String producer) {
        this.producer = producer;
        return this;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public Movie setCoverImage(String coverImage) {
        this.coverImage = coverImage;
        return this;
    }

    public String getMovieLanguage() {
        return movieLanguage;
    }

    public Movie setMovieLanguage(String movieLanguage) {
        this.movieLanguage = movieLanguage;
        return this;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public Movie setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
        return this;
    }
}
