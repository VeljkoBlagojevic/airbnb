package rs.ac.bg.fon.airbnb_backend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.airbnb_backend.domain.AmenityAdditional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AmenityAdditionalRepository implements MyRepository<AmenityAdditional, Long>, RowMapper<AmenityAdditional> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public AmenityAdditional mapRow(ResultSet rs, int rowNum) throws SQLException {
        return AmenityAdditional.builder()
                .amenityId(rs.getLong("amenityId"))
                .description(rs.getString("description"))
                .categoryId(rs.getLong("amenityCategoryId"))
                .build();
    }

    @Override
    public List<AmenityAdditional> findAll() {
        String sqlQuery = """
                SELECT amenity.amenityId, amenity.description, amenity.amenityCategoryId
                FROM Amenity_Additional amenity""";
        return jdbcTemplate.query(sqlQuery, this);
    }

    @Override
    public AmenityAdditional findById(Long amenityId) {
        String sqlQuery = """
                SELECT amenity.amenityId, amenity.description, amenity.amenityCategoryId
                FROM Amenity_Additional amenity
                WHERE amenityId = %d""".formatted(amenityId);
        return jdbcTemplate.queryForObject(sqlQuery, this);
    }
}
