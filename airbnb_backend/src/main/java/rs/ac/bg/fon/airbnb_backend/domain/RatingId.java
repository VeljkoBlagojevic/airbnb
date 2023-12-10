package rs.ac.bg.fon.airbnb_backend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RatingId {
    private Long reviewId;
    private Long reviewCategoryId;
}
