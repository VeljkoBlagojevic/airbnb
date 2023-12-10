package rs.ac.bg.fon.airbnb_backend.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Amenity {
    private Long amenityId;
    private String name;
    private String description;
    private AmenityCategory amenityCategory;
}
