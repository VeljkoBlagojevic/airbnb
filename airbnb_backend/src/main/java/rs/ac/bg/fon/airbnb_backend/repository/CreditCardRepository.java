package rs.ac.bg.fon.airbnb_backend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.airbnb_backend.domain.CreditCard;
import rs.ac.bg.fon.airbnb_backend.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CreditCardRepository implements MyRepository<CreditCard, String>, RowMapper<CreditCard> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public CreditCard mapRow(ResultSet rs, int rowNum) throws SQLException {
        User owner = User.builder()
                .userId(rs.getLong("userId"))
                .name(rs.getString("userName"))
                .email(rs.getString("email"))
                .gender(rs.getString("gender"))
                .build();
        return CreditCard.builder()
                .creditCardNumber(rs.getString("cardNumber"))
                .user(owner)
                .expiryDate(rs.getDate("expiryDate").toLocalDate())
                .name(rs.getString("name"))
                .build();
    }

    @Override
    public List<CreditCard> findAll() {
        String sqlQuery = """
                SELECT card.creditCardNumber.ToString() AS cardNumber, card.expiryDate, card.name, owner.userId AS userId, owner.name AS userName, owner.email, owner.gender
                FROM CreditCard card
                JOIN UserInfo owner ON card.userId = owner.userId""";
        return jdbcTemplate.query(sqlQuery, this);
    }

    @Override
    public CreditCard findById(String creditCardNumber) {
        String sqlQuery = """
                SELECT card.creditCardNumber.ToString() AS cardNumber, card.expiryDate, card.name, owner.userId AS userId, owner.name AS userName, owner.email, owner.gender
                FROM CreditCard card
                JOIN UserInfo owner ON card.userId = owner.userId
                WHERE creditCardNumber = %s""".formatted(creditCardNumber);
        return jdbcTemplate.queryForObject(sqlQuery, this);
    }
}
