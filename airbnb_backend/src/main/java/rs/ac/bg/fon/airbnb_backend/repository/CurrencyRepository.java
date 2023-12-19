package rs.ac.bg.fon.airbnb_backend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.airbnb_backend.domain.Currency;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CurrencyRepository implements MyRepository<Currency, Long>, RowMapper<Currency> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Currency mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Currency.builder()
                .currencyId(rs.getLong("currencyId"))
                .name(rs.getString("name"))
                .build();
    }

    @Override
    public List<Currency> findAll() {
        return jdbcTemplate.query("SELECT * FROM Currency", this);
    }

    @Override
    public Currency findById(Long id) {
        String sqlQuery = String.format("SELECT * FROM Currency WHERE currencyId = %d", id);
        return jdbcTemplate.queryForObject(sqlQuery, this);
    }

    @Override
    public void save(Currency value) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void delete(Long aLong) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
