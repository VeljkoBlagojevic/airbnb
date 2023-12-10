package rs.ac.bg.fon.airbnb_backend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.airbnb_backend.domain.Review;

import java.sql.ResultSet;
import java.sql.SQLException;
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
                .startDate(rs.getDate("startDate").toLocalDate())
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
}
