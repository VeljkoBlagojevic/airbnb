package rs.ac.bg.fon.airbnb_backend.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Currency {
    private Long currencyId;
    private String currencyName;
}
