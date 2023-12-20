package rs.ac.bg.fon.airbnb_backend.controller.review;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.airbnb_backend.domain.ReviewCategory;
import rs.ac.bg.fon.airbnb_backend.repository.ReviewCategoryRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviewCategories")
@RequiredArgsConstructor
@CrossOrigin
public class ReviewCategoryController {

    private final ReviewCategoryRepository reviewCategoryRepository;

    @GetMapping
    public List<ReviewCategory> findAll() {
        return reviewCategoryRepository.findAll();
    }

    @PostMapping
    public void save(@RequestBody ReviewCategory reviewCategory) {
        reviewCategoryRepository.save(reviewCategory);
    }

    @DeleteMapping("{reviewCategoryId}")
    public void delete(@PathVariable Long reviewCategoryId) {
        reviewCategoryRepository.delete(reviewCategoryId);
    }

    @PatchMapping("{reviewCategoryId}")
    public void update(@PathVariable Long reviewCategoryId, @RequestBody ReviewCategory reviewCategory) {
        reviewCategoryRepository.update(reviewCategoryId, reviewCategory);
    }
}
