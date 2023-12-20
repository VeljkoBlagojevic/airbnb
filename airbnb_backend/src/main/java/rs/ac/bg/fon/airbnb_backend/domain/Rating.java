package rs.ac.bg.fon.airbnb_backend.domain;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Rating {
    private RatingId ratingId;
    private Short value;
    private ReviewCategory category;


}
