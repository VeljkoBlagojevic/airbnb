package rs.ac.bg.fon.airbnb_backend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.airbnb_backend.domain.AmenityBasic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AmenityBasicRepository implements MyRepository<AmenityBasic, Long>, RowMapper<AmenityBasic> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public AmenityBasic mapRow(ResultSet rs, int rowNum) throws SQLException {
        return AmenityBasic.builder()
                .amenityId(rs.getLong("amenityId"))
                .name(rs.getString("name"))
                .build();
    }

    @Override
    public List<AmenityBasic> findAll() {
        String sqlQuery = """
                SELECT amenity.amenityId, amenity.name
                FROM Amenity_Basic amenity""";
        return jdbcTemplate.query(sqlQuery, this);
    }

    @Override
    public AmenityBasic findById(Long amenityId) {
        String sqlQuery = """
                SELECT amenity.amenityId, amenity.name
                FROM Amenity_Basic amenity
                WHERE amenityId = %d""".formatted(amenityId);
        return jdbcTemplate.queryForObject(sqlQuery, this);
    }

    @Override
    public void save(AmenityBasic value) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void delete(Long aLong) {
        throw new UnsupportedOperationException("Not implemented");
    }

}
