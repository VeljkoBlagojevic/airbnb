package rs.ac.bg.fon.airbnb_backend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.airbnb_backend.domain.Currency;
import rs.ac.bg.fon.airbnb_backend.domain.Location;
import rs.ac.bg.fon.airbnb_backend.domain.Property;
import rs.ac.bg.fon.airbnb_backend.domain.PropertyCategory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PropertyRepository implements MyRepository<Property, Long>, RowMapper<Property> {

    private final JdbcTemplate jdbcTemplate;


    @Override
    public Property mapRow(ResultSet rs, int rowNum) throws SQLException {
        Currency currency = Currency.builder()
                .currencyId(rs.getLong("currencyId"))
                .currencyName(rs.getString("currencyName"))
                .build();
        Location location = Location.builder()
                .locationId(rs.getLong("locationId"))
                .country(rs.getString("country"))
                .city(rs.getString("city"))
                .zipcode(rs.getLong("zipcode"))
                .address(rs.getString("address"))
                .floor(rs.getString("floor"))
                .build();
        PropertyCategory category = PropertyCategory.builder()
                .propertyCategoryId(rs.getLong("propertyCategoryId"))
                .categoryName(rs.getString("propertyCategoryName"))
                .build();
        return Property.builder()
                .propertyId(rs.getLong("propertyId"))
                .name(rs.getString("propertyName"))
                .price(rs.getDouble("price"))
                .currency(currency)
                .location(location)
                .category(category)
                .categoryName(rs.getString("categoryName"))
                .build();
    }

    @Override
    public List<Property> findAll() {
        String sqlQuery = """
                SELECT p.propertyId, p.name AS propertyName, p.price, p.categoryName AS propertyCategoryName,
                    currency.currencyId, currency.name AS currencyName,
                    l.locationId, l.country, l.city, l.zipcode, l.address, l.floor,
                    category.propertyCategoryId, category.categoryName AS categoryName
                FROM Property p
                JOIN Currency currency ON p.currencyId = currency.currencyId
                JOIN Location l ON p.locationId = l.locationId
                JOIN PropertyCategory category ON p.propertyCategoryId = category.propertyCategoryId""";
        return jdbcTemplate.query(sqlQuery, this);
    }

    @Override
    public Property findById(Long id) {
        String sqlQuery = """
                SELECT p.propertyId, p.name AS propertyName, p.price, p.categoryName AS propertyCategoryName,
                    currency.currencyId, currency.name AS currencyName,
                    l.locationId, l.country, l.city, l.zipcode, l.address, l.floor,
                    category.propertyCategoryId, category.categoryName AS categoryName
                FROM Property p
                JOIN Currency currency ON p.currencyId = currency.currencyId
                JOIN Location l ON p.locationId = l.locationId
                JOIN PropertyCategory category ON p.propertyCategoryId = category.propertyCategoryId
                WHERE p.propertyId = %d""".formatted(id);
        return jdbcTemplate.queryForObject(sqlQuery, this);
    }
}
