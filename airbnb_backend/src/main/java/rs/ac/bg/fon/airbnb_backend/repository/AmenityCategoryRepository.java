package rs.ac.bg.fon.airbnb_backend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.airbnb_backend.domain.AmenityCategory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AmenityCategoryRepository implements MyRepository<AmenityCategory, Long>, RowMapper<AmenityCategory> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public AmenityCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        return AmenityCategory.builder()
                .amenityCategoryId(rs.getLong("amenityCategoryId"))
                .name(rs.getString("name"))
                .build();
    }

    @Override
    public List<AmenityCategory> findAll() {
        return jdbcTemplate.query("SELECT * FROM AmenityCategory", this);
    }

    @Override
    public AmenityCategory findById(Long categoryId) {
        String sqlQuery = String.format("SELECT * FROM AmenityCategory WHERE amenityCategoryId = %d", categoryId);
        return jdbcTemplate.queryForObject(sqlQuery, this);
    }

    @Override
    public void save(AmenityCategory value) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void delete(Long aLong) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
