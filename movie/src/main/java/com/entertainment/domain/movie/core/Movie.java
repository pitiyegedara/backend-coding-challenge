package com.entertainment.domain.movie.core;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Builder
@Getter
@Setter
public class Movie {
    private UUID id;
    private String title;
    private String description;
    private String producer;
    private String movieLanguage;
    private int durationInMinutes;
    private Date createdAt;
    private Date updatedAt;

    private BigDecimal overallRating;
    private Long totalRatings;
}
