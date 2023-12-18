package rs.ac.bg.fon.airbnb_backend.controller.property;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.airbnb_backend.domain.Property;
import rs.ac.bg.fon.airbnb_backend.repository.PropertyRepository;

import java.util.List;

@RestController
@RequestMapping("api/v1/properties")
@RequiredArgsConstructor
@CrossOrigin
public class PropertyController {

    private final PropertyRepository propertyRepository;

    @GetMapping("/viewAll")
    public List<Property> findAll() {
        return propertyRepository.findAll();
    }

    @GetMapping("/{propertyId}")
    public Property findById(@PathVariable Long propertyId) {
        return propertyRepository.findById(propertyId);
    }

    @GetMapping("/name")
    public List<Property> findAllStartingWithName(@RequestParam String name) {
        return propertyRepository.findAllStartingWithName(name);
    }

    @GetMapping("/city")
    public List<Property> findAllInCity(@RequestParam String city) {
        return propertyRepository.findAllInCity(city);
    }

    @PostMapping
    public void save(@RequestBody Property property) {
        propertyRepository.save(property);
    }

    @DeleteMapping("{propertyId}")
    public void delete(@PathVariable Long propertyId) {
        propertyRepository.delete(propertyId);
    }

    @PatchMapping("{propertyId}")
    public void update(@PathVariable Long propertyId, @RequestBody Property property) {
        propertyRepository.update(propertyId, property);
    }

}
