package rs.ac.bg.fon.airbnb_backend.controller.amenity;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.airbnb_backend.domain.AmenityCategory;
import rs.ac.bg.fon.airbnb_backend.repository.AmenityCategoryRepository;

import java.util.List;

@RestController
@RequestMapping("api/v1/amenityCategories")
@RequiredArgsConstructor
@CrossOrigin
public class AmenityCategoryController {

    private final AmenityCategoryRepository amenityCategoryRepository;

    @GetMapping
    public List<AmenityCategory> findAll() {
        return amenityCategoryRepository.findAll();
    }

    @GetMapping("/{amenityCategoryId}")
    public AmenityCategory findById(@PathVariable Long amenityCategoryId) {
        return amenityCategoryRepository.findById(amenityCategoryId);
    }
}
