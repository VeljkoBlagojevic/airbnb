package rs.ac.bg.fon.airbnb_backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private Long userId;
    private String name;
    private String email;
    private String gender;
    @JsonIgnore
    private String password;
}
