package rs.ac.bg.fon.airbnb_backend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.airbnb_backend.domain.Currency;
import rs.ac.bg.fon.airbnb_backend.domain.Location;
import rs.ac.bg.fon.airbnb_backend.domain.Property;
import rs.ac.bg.fon.airbnb_backend.domain.PropertyCategory;
import rs.ac.bg.fon.airbnb_backend.exception.JdbcException;

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
                .name(rs.getString("currencyName"))
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
                JOIN Currency currency
                    ON p.currencyId = currency.currencyId
                JOIN Location l
                    ON p.locationId = l.locationId
                JOIN PropertyCategory category
                    ON p.propertyCategoryId = category.propertyCategoryId""";
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
                JOIN Currency currency
                    ON p.currencyId = currency.currencyId
                JOIN Location l
                    ON p.locationId = l.locationId
                JOIN PropertyCategory category
                    ON p.propertyCategoryId = category.propertyCategoryId
                WHERE p.propertyId = %d""".formatted(id);
        return jdbcTemplate.queryForObject(sqlQuery, this);
    }

    public List<Property> findAllStartingWithName(String propertyName) {
        String sqlQuery = """
                SELECT p.propertyId, p.name AS propertyName, p.price, p.categoryName AS propertyCategoryName,
                    currency.currencyId, currency.name AS currencyName,
                    l.locationId, l.country, l.city, l.zipcode, l.address, l.floor,
                    category.propertyCategoryId, category.categoryName AS categoryName
                FROM Property p
                    WITH(INDEX(Property_Index))
                JOIN Currency currency
                    ON p.currencyId = currency.currencyId
                JOIN Location l
                    ON p.locationId = l.locationId
                JOIN PropertyCategory category
                    ON p.propertyCategoryId = category.propertyCategoryId
                WHERE p.name LIKE '""" + propertyName + "%'";
        return jdbcTemplate.query(sqlQuery, this);
    }

    public List<Property> findAllInCity(String city) {
        String sqlQuery = """
                SELECT p.propertyId, p.name AS propertyName, p.price, p.categoryName AS propertyCategoryName,
                    currency.currencyId, currency.name AS currencyName,
                    l.locationId, l.country, l.city, l.zipcode, l.address, l.floor,
                    category.propertyCategoryId, category.categoryName AS categoryName
                FROM Location l
                    WITH(INDEX(Location_Index))
                JOIN Property p
                    ON p.locationId = l.locationId
                JOIN Currency currency
                    ON p.currencyId = currency.currencyId
                JOIN PropertyCategory category
                    ON p.propertyCategoryId = category.propertyCategoryId
                WHERE l.city LIKE '%""" + city + "%'";
        return jdbcTemplate.query(sqlQuery, this);
    }

    public void save(Property property) {
        String sqlQuery = """
                INSERT INTO Property
                    (name, price, propertyCategoryId, currencyId, locationId, areaId, hostId)
                    VALUES ('%s',%f,%d,%d,%d,%d,%d)"""
                .formatted(
                        property.getName(),
                        property.getPrice(),
                        property.getCategory().getPropertyCategoryId(),
                        property.getCurrency().getCurrencyId(),
                        property.getLocation().getLocationId(),
                        1, 1);
        try {
            jdbcTemplate.update(sqlQuery);
        } catch (Exception e) {
            throw new JdbcException(e.getMessage(), e);
        }
    }

    public void delete(Long propertyId) {
        String sqlQuery = """
                DELETE FROM Property
                WHERE propertyId = %d
                """.formatted(propertyId);
        try {
            jdbcTemplate.update(sqlQuery);
        } catch (Exception e) {
            throw new JdbcException(e.getMessage(), e);
        }
    }

    public void update(Long oldPropertyId, Property newProperty) {
        String sqlQuery = """
                UPDATE Property
                SET name = '%s', price = %f, propertyCategoryId = %d, currencyId = %d, locationId = %d
                WHERE propertyId = %d"""
                .formatted(
                        newProperty.getName(),
                        newProperty.getPrice(),
                        newProperty.getCategory().getPropertyCategoryId(),
                        newProperty.getCurrency().getCurrencyId(),
                        newProperty.getLocation().getLocationId(),
                        oldPropertyId);
        try {
            jdbcTemplate.update(sqlQuery);
        } catch (Exception e) {
            throw new JdbcException(e.getMessage(), e);
        }
    }
}
