package rs.ac.bg.fon.airbnb_backend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.airbnb_backend.domain.Amenity;
import rs.ac.bg.fon.airbnb_backend.domain.AmenityCategory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AmenityRepository implements MyRepository<Amenity, Long>, RowMapper<Amenity> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Amenity mapRow(ResultSet rs, int rowNum) throws SQLException {
        AmenityCategory amenityCategory = AmenityCategory.builder()
                .amenityCategoryId(rs.getLong("amenityCategoryId"))
                .name(rs.getString("categoryName"))
                .build();

        return Amenity.builder()
                .amenityId(rs.getLong("amenityId"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .amenityCategory(amenityCategory)
                .build();
    }

    @Override
    public List<Amenity> findAll() {
        String sqlQuery = """
                SELECT amenity.amenityId, amenity.name, amenity.description, category.amenityCategoryId, category.name as categoryName
                FROM Amenity_View amenity
                JOIN AmenityCategory category ON amenity.amenityCategoryId = category.amenityCategoryId""";

        return jdbcTemplate.query(sqlQuery, this);
    }

    @Override
    public Amenity findById(Long id) {
        String sqlQuery = """
                SELECT amenity.amenityId, amenity.name, amenity.description, category.amenityCategoryId, category.name as categoryName
                FROM Amenity_View amenity
                JOIN AmenityCategory category ON amenity.amenityCategoryId = category.amenityCategoryId
                WHERE amenityId = %d"""
                .formatted(id);
        return jdbcTemplate.queryForObject(sqlQuery, this);
    }
}
