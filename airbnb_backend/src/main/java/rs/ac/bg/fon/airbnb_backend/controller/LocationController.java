package rs.ac.bg.fon.airbnb_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.airbnb_backend.domain.Location;
import rs.ac.bg.fon.airbnb_backend.repository.LocationRepository;

import java.util.List;

@RestController
@RequestMapping("api/v1/locations")
@RequiredArgsConstructor
@CrossOrigin
public class LocationController {

    private final LocationRepository locationRepository;

    @GetMapping
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

}
