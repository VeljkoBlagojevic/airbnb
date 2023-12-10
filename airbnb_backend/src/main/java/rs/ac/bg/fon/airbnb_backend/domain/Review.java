package rs.ac.bg.fon.airbnb_backend.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Review {
    private Long reviewId;
    private String description;
    private LocalDate startDate;
//    private User guest;
//    private Property property;
    private Double overallRating;
}
