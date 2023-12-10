package rs.ac.bg.fon.airbnb_backend.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AmenityCategory {
    private Long amenityCategoryId;
    private String name;
}
