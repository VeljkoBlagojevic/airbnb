package rs.ac.bg.fon.airbnb_backend.controller.amenity;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.airbnb_backend.domain.Amenity;
import rs.ac.bg.fon.airbnb_backend.repository.AmenityRepository;

import java.util.List;

@RestController
@RequestMapping("api/v1/amenities/view")
@RequiredArgsConstructor
@CrossOrigin
public class AmenityViewController {

    private final AmenityRepository amenityRepository;

    @GetMapping
    public List<Amenity> findAll() {
        return amenityRepository.findAll();
    }

    @PostMapping
    public void save(@RequestBody Amenity amenity) {
        amenityRepository.save(amenity);
    }

    @PatchMapping("{amenityId}")
    public void update(@PathVariable Long amenityId, @RequestBody Amenity amenity) {
        amenityRepository.update(amenityId, amenity);
    }

    @DeleteMapping("{amenityId}")
    public void delete(@PathVariable Long amenityId) {
        amenityRepository.delete(amenityId);
    }
}
