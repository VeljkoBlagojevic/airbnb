package rs.ac.bg.fon.airbnb_backend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.airbnb_backend.domain.PropertyCategory;
import rs.ac.bg.fon.airbnb_backend.exception.JdbcException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PropertyCategoryRepository implements MyRepository<PropertyCategory, Long>, RowMapper<PropertyCategory> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public PropertyCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        return PropertyCategory.builder()
                .propertyCategoryId(rs.getLong("propertyCategoryId"))
                .categoryName(rs.getString("categoryName"))
                .build();
    }

    @Override
    public List<PropertyCategory> findAll() {
        return jdbcTemplate.query("SELECT * FROM PropertyCategory", this);
    }

    @Override
    public PropertyCategory findById(Long id) {
        String sqlQuery = String.format("SELECT * FROM PropertyCategory WHERE PropertyCategoryId = %d", id);
        return jdbcTemplate.queryForObject(sqlQuery, this);
    }

    @Override
    public void save(PropertyCategory category) {
        String sqlQuery = """
                INSERT INTO PropertyCategory (categoryName)
                VALUES ('%s')""".formatted(category.getCategoryName());
        try {
            jdbcTemplate.update(sqlQuery);
        } catch (Exception e) {
            throw new JdbcException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(Long categoryId) {
        String sqlQuery = """
                DELETE FROM PropertyCategory
                WHERE propertyCategoryId = '%s'"""
                .formatted(categoryId);
        try {
            jdbcTemplate.update(sqlQuery);
        } catch (Exception e) {
            throw new JdbcException(e.getMessage(), e);
        }
    }

    @Override
    public void update(Long propertyCategoryId, PropertyCategory category) {
        String sqlQuery = """
                UPDATE PropertyCategory
                SET categoryName = '%s'
                WHERE propertyCategoryId = %d"""
                .formatted(category.getCategoryName(), propertyCategoryId);
        try {
            jdbcTemplate.update(sqlQuery);
        } catch (Exception e) {
            throw new JdbcException(e.getMessage(), e);
        }
    }
}
