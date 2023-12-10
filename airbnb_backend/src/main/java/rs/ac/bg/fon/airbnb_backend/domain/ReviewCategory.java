package rs.ac.bg.fon.airbnb_backend.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewCategory {
    private Long reviewCategoryId;
    private String name;
}
