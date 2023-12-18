package rs.ac.bg.fon.airbnb_backend.controller.amenity;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.airbnb_backend.domain.AmenityBasic;
import rs.ac.bg.fon.airbnb_backend.repository.AmenityBasicRepository;

import java.util.List;

@RestController
@RequestMapping("api/v1/amenities/basic")
@RequiredArgsConstructor
@CrossOrigin
public class AmenityBasicController {

    private final AmenityBasicRepository amenityBasicRepository;

    @GetMapping
    public List<AmenityBasic> findAll() {
        return amenityBasicRepository.findAll();
    }

    @GetMapping("{basicId}")
    public AmenityBasic findById(@PathVariable Long basicId) {
        return amenityBasicRepository.findById(basicId);
    }


}
