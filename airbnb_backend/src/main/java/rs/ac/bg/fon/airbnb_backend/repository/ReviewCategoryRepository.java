package rs.ac.bg.fon.airbnb_backend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.airbnb_backend.domain.ReviewCategory;
import rs.ac.bg.fon.airbnb_backend.exception.JdbcException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewCategoryRepository implements MyRepository<ReviewCategory, Long>, RowMapper<ReviewCategory> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public ReviewCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ReviewCategory.builder()
                .reviewCategoryId(rs.getLong("reviewCategoryId"))
                .name(rs.getString("name"))
                .build();
    }

    @Override
    public List<ReviewCategory> findAll() {
        String sqlQuery = "SELECT * FROM ReviewCategory";
        return jdbcTemplate.query(sqlQuery, this);
    }

    @Override
    public ReviewCategory findById(Long reviewCategoryId) {
        String sqlQuery = """
                SELECT *
                FROM ReviewCategory
                WHERE reviewCategoryId = %d"""
                .formatted(reviewCategoryId);
        return jdbcTemplate.queryForObject(sqlQuery, this);
    }

    @Override
    public void save(ReviewCategory reviewCategory) {
        String sqlQuery = """
                INSERT INTO ReviewCategory(name)
                VALUES ('%s')""".formatted(reviewCategory.getName());
        try {
            jdbcTemplate.update(sqlQuery);
        } catch (Exception e) {
            throw new JdbcException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(Long reviewCategoryId) {
        String sqlQuery = """
                DELETE FROM ReviewCategory
                WHERE propertyCategoryId = %d)"""
                .formatted(reviewCategoryId);
        try {
            jdbcTemplate.update(sqlQuery);
        } catch (Exception e) {
            throw new JdbcException(e.getMessage(), e);
        }
    }

    @Override
    public void update(Long reviewCategoryId, ReviewCategory reviewCategory) {
        String sqlQuery = """
                UPDATE ReviewCategory
                SET name = '%s'
                WHERE propertyCategoryId = %d)"""
                .formatted(
                        reviewCategory.getName(),
                        reviewCategoryId);
        try {
            jdbcTemplate.update(sqlQuery);
        } catch (Exception e) {
            throw new JdbcException(e.getMessage(), e);
        }
    }
}
