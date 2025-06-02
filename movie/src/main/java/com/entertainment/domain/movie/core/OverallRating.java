package com.entertainment.domain.movie.core;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public record OverallRating(UUID movieId, Long rateCount, Long rateSum) {

    public BigDecimal calculateOverallRating() {
        if (rateCount == null || rateSum == null) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(rateSum).divide(BigDecimal.valueOf(rateCount), 1, RoundingMode.HALF_EVEN);
    }
}
