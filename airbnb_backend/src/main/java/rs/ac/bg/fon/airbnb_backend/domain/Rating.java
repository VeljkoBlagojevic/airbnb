package rs.ac.bg.fon.airbnb_backend.domain;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Rating {
    private RatingId ratingId;
//    private Long reviewId;
    private Short value;
//    private Review review;
    private ReviewCategory category;


}
