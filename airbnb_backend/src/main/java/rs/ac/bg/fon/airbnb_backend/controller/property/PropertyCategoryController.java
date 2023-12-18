package rs.ac.bg.fon.airbnb_backend.controller.property;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.airbnb_backend.domain.PropertyCategory;
import rs.ac.bg.fon.airbnb_backend.repository.PropertyCategoryRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/propertyCategories")
@RequiredArgsConstructor
@CrossOrigin
public class PropertyCategoryController {

    private final PropertyCategoryRepository propertyCategoryRepository;

    @GetMapping
    public List<PropertyCategory> findAll() {
        return propertyCategoryRepository.findAll();
    }


}
