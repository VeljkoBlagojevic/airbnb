package rs.ac.bg.fon.airbnb_backend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.airbnb_backend.domain.Review;
import rs.ac.bg.fon.airbnb_backend.exception.JdbcException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepository implements MyRepository<Review, Long>, RowMapper<Review> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Review.builder()
                .reviewId(rs.getLong("reviewId"))
                .description(rs.getString("description"))
                .overallRating(rs.getDouble("overallRating"))
                .build();
    }

    @Override
    public List<Review> findAll() {
        return jdbcTemplate.query("SELECT * FROM Review", this);
    }

    @Override
    public Review findById(Long id) {
        String sqlQuery = String.format("SELECT * FROM Review WHERE reviewId = %d", id);
        return jdbcTemplate.queryForObject(sqlQuery, this);
    }

    @Override
    public void save(Review review) {
        String sqlQuery = """
                INSERT INTO Review (description, startDate, guestId, propertyId)
                VALUES ('%s', '%s', %d, %d)"""
                .formatted(
                        review.getDescription(),
                        LocalDate.of(2024, 3, 11),
                        2,
                        6);
        try {
            jdbcTemplate.update(sqlQuery);
        } catch (Exception e) {
            throw new JdbcException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(Long reviewId) {
        String sqlQuery = """
                DELETE FROM Review
                WHERE reviewId = %d"""
                .formatted(reviewId);
        try {
            jdbcTemplate.update(sqlQuery);
        } catch (Exception e) {
            throw new JdbcException(e.getMessage(), e);
        }
    }

    @Override
    public void update(Long oldId, Review review) {
        String sqlQuery = """
                UPDATE Review
                SET description = '%s'
                WHERE reviewId = %d"""
                .formatted(review.getDescription(), oldId);
        try {
            jdbcTemplate.update(sqlQuery);
        } catch (Exception e) {
            throw new JdbcException(e.getMessage(), e);
        }
    }

    public void saveWithOverallRating(Review review) {
        String sqlQuery = """
                INSERT INTO Review (description, startDate, guestId, propertyId, overallRating)
                VALUES ('%s', '%s', %d, %d, %f)"""
                .formatted(
                        review.getDescription(),
                        LocalDate.of(2024, 3, 11),
                        2,
                        6,
                        review.getOverallRating());
        try {
            jdbcTemplate.update(sqlQuery);
        } catch (Exception e) {
            throw new JdbcException(e.getMessage(), e);
        }
    }

    public void updateWithOverallRating(Long oldId, Review review) {
        String sqlQuery = """
                UPDATE Review
                SET description = '%s', overallRating = %f
                WHERE reviewId = %d"""
                .formatted(
                        review.getDescription(),
                        review.getOverallRating(),
                        oldId);
        try {
            jdbcTemplate.update(sqlQuery);
        } catch (Exception e) {
            throw new JdbcException(e.getMessage(), e);
        }
    }

}
