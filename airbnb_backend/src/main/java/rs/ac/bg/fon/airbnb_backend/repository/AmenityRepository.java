package rs.ac.bg.fon.airbnb_backend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.airbnb_backend.domain.Amenity;
import rs.ac.bg.fon.airbnb_backend.domain.AmenityCategory;
import rs.ac.bg.fon.airbnb_backend.exception.JdbcException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AmenityRepository implements MyRepository<Amenity, Long>, RowMapper<Amenity> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Amenity mapRow(ResultSet rs, int rowNum) throws SQLException {
        String categoryName = rs.getString("categoryName");
        AmenityCategory amenityCategory = AmenityCategory.builder()
                .amenityCategoryId(rs.getLong("amenityCategoryId"))
                .name(categoryName)
                .build();

        return Amenity.builder()
                .amenityId(rs.getLong("amenityId"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .amenityCategory(amenityCategory)
                .categoryName(categoryName)
                .build();
    }

    @Override
    public List<Amenity> findAll() {
        String sqlQuery = """
                SELECT amenity.amenityId, amenity.name, amenity.description, category.amenityCategoryId, category.name as categoryName
                FROM Amenity_View amenity
                JOIN AmenityCategory category
                    ON amenity.amenityCategoryId = category.amenityCategoryId""";
        return jdbcTemplate.query(sqlQuery, this);
    }

    @Override
    public Amenity findById(Long id) {
        String sqlQuery = """
                SELECT amenity.amenityId, amenity.name, amenity.description, category.amenityCategoryId, category.name as categoryName
                FROM Amenity_View amenity
                JOIN AmenityCategory category
                    ON amenity.amenityCategoryId = category.amenityCategoryId
                WHERE amenityId = %d""".formatted(id);
        return jdbcTemplate.queryForObject(sqlQuery, this);
    }

    @Override
    public void save(Amenity amenity) {
        String sqlQuery = """
                INSERT INTO Amenity_View(amenityId, name, description, amenityCategoryId)
                VALUES (%d, '%s', '%s', %d)""".formatted(
                amenity.getAmenityId(),
                amenity.getName(),
                amenity.getDescription(),
                amenity.getAmenityCategory().getAmenityCategoryId());
        jdbcTemplate.update(sqlQuery);
    }

    @Override
    public void delete(Long basicId) {
        String sqlQuery = """
                DELETE FROM Amenity_View
                WHERE amenityId = %d""".formatted(basicId);
        try {
            jdbcTemplate.update(sqlQuery);
        } catch (Exception e) {
            throw new JdbcException(e.getMessage(), e);
        }
    }

    @Override
    public void update(Long oldAmenityId, Amenity newAmenity) {
        String sqlQuery = """
                UPDATE Amenity_View
                SET name = '%s', description = '%s', amenityCategoryId = %d
                WHERE amenityId = %d"""
                .formatted(
                        newAmenity.getName(),
                        newAmenity.getDescription(),
                        newAmenity.getAmenityCategory().getAmenityCategoryId(),
                        oldAmenityId);
        try {
            jdbcTemplate.update(sqlQuery);
        } catch (Exception e) {
            throw new JdbcException(e.getMessage(), e);
        }
    }
}
