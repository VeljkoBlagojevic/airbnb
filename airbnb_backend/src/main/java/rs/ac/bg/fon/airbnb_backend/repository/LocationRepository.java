package rs.ac.bg.fon.airbnb_backend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.airbnb_backend.domain.Location;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LocationRepository implements MyRepository<Location, Long>, RowMapper<Location> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Location.builder()
                .locationId(rs.getLong("locationId"))
                .country(rs.getString("country"))
                .city(rs.getString("city"))
                .zipcode(rs.getLong("zipcode"))
                .address(rs.getString("address"))
                .floor(rs.getString("floor"))
                .build();
    }

    @Override
    public List<Location> findAll() {
        return jdbcTemplate.query("SELECT * FROM Location", this);
    }

    @Override
    public Location findById(Long id) {
        String sqlQuery = String.format("SELECT * FROM Location WHERE locationId = %d", id);
        return jdbcTemplate.queryForObject(sqlQuery, this);
    }
}
