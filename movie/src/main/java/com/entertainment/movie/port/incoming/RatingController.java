package com.entertainment.movie.port.incoming;

import com.entertainment.movie.api.RateApi;
import com.entertainment.movie.domain.service.RatingService;
import com.entertainment.movie.dto.CommonResponse;
import com.entertainment.movie.dto.RateDto;
import com.entertainment.movie.port.incoming.mapper.RequestResponseMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class RatingController implements RateApi {

    private final RatingService ratingService;
    private final RequestResponseMapper requestResponseMapper;

    public RatingController(RatingService ratingService, RequestResponseMapper requestResponseMapper) {
        this.ratingService = ratingService;
        this.requestResponseMapper = requestResponseMapper;
    }

    @Override
    public ResponseEntity<CommonResponse> createRating(UUID movieId, UUID userId, RateDto rateDto) {
        var domainRating = requestResponseMapper.mapToDomainRating(rateDto, userId.toString(), movieId.toString());
        ratingService.createRating(domainRating);
        return new ResponseEntity<>(
                requestResponseMapper.mapToCommonResponse("successfully created", domainRating.getId()),
                HttpStatus.CREATED);
    }
}
