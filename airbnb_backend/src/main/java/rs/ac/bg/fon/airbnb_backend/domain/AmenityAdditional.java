package rs.ac.bg.fon.airbnb_backend.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AmenityAdditional {
    private Long amenityId;
    private String description;
    private Long categoryId;
}
