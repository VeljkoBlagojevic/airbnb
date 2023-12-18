package rs.ac.bg.fon.airbnb_backend.controller.amenity;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.airbnb_backend.domain.AmenityAdditional;
import rs.ac.bg.fon.airbnb_backend.repository.AmenityAdditionalRepository;

import java.util.List;

@RestController
@RequestMapping("api/v1/amenities/additional")
@RequiredArgsConstructor
@CrossOrigin
public class AmenityAdditionalController {

    private final AmenityAdditionalRepository amenityAdditionalRepository;

    @GetMapping
    public List<AmenityAdditional> findAll() {
        return amenityAdditionalRepository.findAll();
    }


}
