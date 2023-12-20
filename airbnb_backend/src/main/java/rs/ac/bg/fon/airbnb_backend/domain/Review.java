package rs.ac.bg.fon.airbnb_backend.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Review {
    private Long reviewId;
    private String description;
    private Double overallRating;
}
