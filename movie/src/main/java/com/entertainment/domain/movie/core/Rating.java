package com.entertainment.domain.movie.core;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Builder
@Getter
@Setter
public class Rating {
    private UUID id;
    private UUID movieId;
    private UUID userId;
    private int ratingValue;
    private String comment;
    private Date createdAt;
    private Date updatedAt;
}
