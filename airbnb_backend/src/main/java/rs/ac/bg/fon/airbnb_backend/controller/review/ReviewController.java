package rs.ac.bg.fon.airbnb_backend.controller.review;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.airbnb_backend.domain.Review;
import rs.ac.bg.fon.airbnb_backend.repository.ReviewRepository;

import java.util.List;

@RestController
@RequestMapping("api/v1/reviews")
@RequiredArgsConstructor
@CrossOrigin
public class ReviewController {

    private final ReviewRepository reviewRepository;

    @GetMapping
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    @GetMapping("{reviewId}")
    public Review findById(@PathVariable Long reviewId) {
        return reviewRepository.findById(reviewId);
    }

    @PostMapping
    public void save(@RequestBody Review review) {
        reviewRepository.save(review);
    }

    @DeleteMapping("{reviewId}")
    public void delete(@PathVariable Long reviewId) {
        reviewRepository.delete(reviewId);
    }

    @PatchMapping("{reviewId}")
    public void update(@PathVariable Long reviewId, @RequestBody Review review) {
        reviewRepository.update(reviewId, review);
    }

    @PostMapping("/withOverallRating")
    public void saveWithOverallRating(@RequestBody Review review) {
        reviewRepository.saveWithOverallRating(review);
    }

    @PatchMapping("/{reviewId}/withOverallRating")
    public void updateWithOverallRating(@PathVariable Long reviewId, @RequestBody Review review) {
        reviewRepository.updateWithOverallRating(reviewId, review);
    }

}
