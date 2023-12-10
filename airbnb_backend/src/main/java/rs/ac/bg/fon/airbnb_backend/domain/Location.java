package rs.ac.bg.fon.airbnb_backend.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Location {
    private Long locationId;
    private String country;
    private String city;
    private Long zipcode;
    private String address;
    private String floor;
}
