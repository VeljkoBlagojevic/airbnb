package rs.ac.bg.fon.airbnb_backend.controller.review;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.airbnb_backend.domain.Rating;
import rs.ac.bg.fon.airbnb_backend.domain.RatingId;
import rs.ac.bg.fon.airbnb_backend.repository.RatingRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ratings")
@RequiredArgsConstructor
@CrossOrigin
public class RatingController {

    private final RatingRepository ratingRepository;

    @GetMapping
    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    @GetMapping("/review/{reviewId}")
    public List<Rating> findAll(@PathVariable Long reviewId) {
        return ratingRepository.findAllByReviewId(reviewId);
    }

    @PostMapping
    public void save(@RequestBody Rating rating) {
        ratingRepository.save(rating);
    }

    @DeleteMapping("/review/{reviewId}/category/{categoryId}")
    public void delete(@PathVariable Long reviewId, @PathVariable Long categoryId) {
        RatingId ratingId = new RatingId(reviewId, categoryId);
        ratingRepository.delete(ratingId);
    }

    @PatchMapping
    public void update(@RequestBody Rating rating) {
        ratingRepository.update(rating.getRatingId(), rating);
    }
}
