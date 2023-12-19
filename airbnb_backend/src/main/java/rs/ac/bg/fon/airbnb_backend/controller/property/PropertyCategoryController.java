package rs.ac.bg.fon.airbnb_backend.controller.property;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping
    public void save(@RequestBody PropertyCategory category) {
        propertyCategoryRepository.save(category);
    }

    @DeleteMapping("{propertyCategoryId}")
    public void delete(@PathVariable Long propertyCategoryId) {
        propertyCategoryRepository.delete(propertyCategoryId);
    }

    @PatchMapping("{propertyCategoryId}")
    public void update(@PathVariable Long propertyCategoryId, @RequestBody PropertyCategory category) {
        propertyCategoryRepository.update(propertyCategoryId, category);
    }


}
