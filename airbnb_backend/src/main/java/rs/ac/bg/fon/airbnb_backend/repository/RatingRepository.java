package rs.ac.bg.fon.airbnb_backend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.airbnb_backend.domain.Rating;
import rs.ac.bg.fon.airbnb_backend.domain.RatingId;
import rs.ac.bg.fon.airbnb_backend.domain.ReviewCategory;
import rs.ac.bg.fon.airbnb_backend.exception.JdbcException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RatingRepository implements MyRepository<Rating, RatingId>, RowMapper<Rating> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Rating mapRow(ResultSet rs, int rowNum) throws SQLException {
        ReviewCategory reviewCategory = ReviewCategory.builder()
                .reviewCategoryId(rs.getLong("reviewCategoryId"))
                .name(rs.getString("categoryName"))
                .build();
        RatingId ratingId = RatingId.builder()
                .reviewId(rs.getLong("reviewId"))
                .reviewCategoryId(rs.getLong("reviewCategoryId"))
                .build();
        return Rating.builder()
                .ratingId(ratingId)
                .value(rs.getShort("value"))
                .category(reviewCategory)
                .build();
    }

    @Override
    public List<Rating> findAll() {
        String sqlQuery = """
                SELECT rating.reviewId, rating.reviewCategoryId, rating.value, category.name as categoryName
                FROM Rating rating
                JOIN ReviewCategory category ON rating.reviewCategoryId = category.reviewCategoryId""";
        return jdbcTemplate.query(sqlQuery, this);
    }

    @Override
    public Rating findById(RatingId id) {
        String sqlQuery = """
                SELECT rating.reviewId, rating.reviewCategoryId, rating.value, category.name as categoryName
                FROM Rating rating
                JOIN ReviewCategory category ON rating.reviewCategoryId = category.reviewCategoryId
                WHERE rating.reviewId = %d AND rating.reviewCategoryId = %d"""
                .formatted(id.getReviewId(), id.getReviewCategoryId());
        return jdbcTemplate.queryForObject(sqlQuery, this);
    }

    public List<Rating> findAllByReviewId(Long reviewId) {
        String sqlQuery = """
                SELECT rating.reviewId, rating.reviewCategoryId, rating.value, category.name as categoryName
                FROM Rating rating
                JOIN ReviewCategory category ON rating.reviewCategoryId = category.reviewCategoryId
                WHERE rating.reviewId = %d"""
                .formatted(reviewId);
        return jdbcTemplate.query(sqlQuery, this);
    }

    @Override
    public void save(Rating rating) {
        String sqlQuery = """
                INSERT INTO Rating (reviewId, reviewCategoryId, value)
                VALUES (%d, %d, %d)"""
                .formatted(
                        rating.getRatingId().getReviewId(),
                        rating.getRatingId().getReviewCategoryId(),
                        rating.getValue());
        try {
            jdbcTemplate.update(sqlQuery);
        } catch (Exception e) {
            throw new JdbcException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(RatingId ratingId) {
        String sqlQuery = """
                DELETE FROM Rating
                WHERE reviewId = %d AND reviewCategoryId = %d
                """.formatted(ratingId.getReviewId(), ratingId.getReviewCategoryId());
        try {
            jdbcTemplate.update(sqlQuery);
        } catch (Exception e) {
            throw new JdbcException(e.getMessage(), e);
        }
    }

    @Override
    public void update(RatingId ratingId, Rating rating) {
        String sqlQuery = """
                UPDATE Rating
                SET value = %d
                WHERE reviewId = %d AND reviewCategoryId = %d"""
                .formatted(
                        rating.getValue(),
                        ratingId.getReviewId(),
                        ratingId.getReviewCategoryId());
        try {
            jdbcTemplate.update(sqlQuery);
        } catch (Exception e) {
            throw new JdbcException(e.getMessage(), e);
        }
    }
}
