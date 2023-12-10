package rs.ac.bg.fon.airbnb_backend.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CreditCard {
    private String creditCardNumber;
    private User user;
    private LocalDate expiryDate;
    private String name;
}
