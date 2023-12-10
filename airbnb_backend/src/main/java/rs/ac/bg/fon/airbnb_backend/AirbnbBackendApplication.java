package rs.ac.bg.fon.airbnb_backend;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class AirbnbBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AirbnbBackendApplication.class, args);
    }

}
