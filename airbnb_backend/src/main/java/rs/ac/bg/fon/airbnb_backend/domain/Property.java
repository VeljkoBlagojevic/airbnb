package rs.ac.bg.fon.airbnb_backend.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Property {
    private Long propertyId;
    private String name;
    private Double price;
    private Currency currency;
    private Location location;
    private PropertyCategory category;
    private String categoryName;
}
