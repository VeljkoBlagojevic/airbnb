package rs.ac.bg.fon.airbnb_backend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.airbnb_backend.domain.CreditCard;
import rs.ac.bg.fon.airbnb_backend.domain.User;
import rs.ac.bg.fon.airbnb_backend.exception.JdbcException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                SELECT
                    card.creditCardNumber.ToString() AS cardNumber, card.expiryDate, card.name,
                    owner.userId AS userId, owner.name AS userName, owner.email, owner.gender
                FROM CreditCard card
                JOIN UserInfo owner
                    ON card.userId = owner.userId""";
        return jdbcTemplate.query(sqlQuery, this);
    }

    @Override
    public CreditCard findById(String creditCardNumber) {
        String sqlQuery = """
                SELECT
                    card.creditCardNumber.ToString() AS cardNumber, card.expiryDate, card.name,
                    owner.userId AS userId, owner.name AS userName, owner.email, owner.gender
                FROM CreditCard card
                JOIN UserInfo owner
                    ON card.userId = owner.userId
                WHERE creditCardNumber = %s""".formatted(creditCardNumber);
        return jdbcTemplate.queryForObject(sqlQuery, this);
    }

    public List<CreditCard> findAllExpiringInYear(Integer year) {
        String sqlQuery = """
                SELECT $PARTITION.DATE_PARTITION_FUNCTION(expiryDate) AS partition, *
                FROM CreditCard
                WHERE partition = 2022 + %d""".formatted(year);
        return jdbcTemplate.query(sqlQuery, this);
    }

    @Override
    public void save(CreditCard creditCard) {
        String sqlQuery = """
                INSERT INTO CreditCard
                    (creditCardNumber, expiryDate, CVC, userId)
                VALUES ('%s', '%s', %d, %d)"""
                .formatted(
                        creditCard.getCreditCardNumber(),
                        creditCard.getExpiryDate(),
                        123,
                        creditCard.getUser().getUserId());
        try {
            jdbcTemplate.update(sqlQuery);
        } catch (Exception e) {
            throw new JdbcException(e.getMessage(), e);
        }
    }

    public void saveWithUserName(CreditCard creditCard) {
        String sqlQuery = """
                INSERT INTO CreditCard
                    (creditCardNumber, expiryDate, CVC, userId, name)
                VALUES ('%s', '%s', %d, %d, '%s')"""
                .formatted(
                        creditCard.getCreditCardNumber(),
                        creditCard.getExpiryDate(),
                        123,
                        creditCard.getUser().getUserId(),
                        creditCard.getName());
        try {
            jdbcTemplate.update(sqlQuery);
        } catch (Exception e) {
            throw new JdbcException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(String creditCardNumber) {
        String sqlQuery = """
                DELETE FROM CreditCard
                WHERE creditCardNumber = '%s'"""
                .formatted(creditCardNumber);
        try {
            jdbcTemplate.update(sqlQuery);
        } catch (Exception e) {
            throw new JdbcException(e.getMessage(), e);
        }
    }

    public void update(String creditCardNumber, CreditCard creditCard) {
        String sqlQuery = """
                UPDATE CreditCard
                SET expiryDate = '%s'
                WHERE creditCardNumber = '%s'"""
                .formatted(
                        creditCard.getExpiryDate(),
                        creditCardNumber);
        try {
            jdbcTemplate.update(sqlQuery);
        } catch (Exception e) {
            throw new JdbcException(e.getMessage(), e);
        }
    }

    public void updateWithUserName(String creditCardNumber, CreditCard creditCard) {
        String sqlQuery = """
                UPDATE CreditCard
                SET
                    expiryDate = '%s',
                    name = '%s'
                WHERE creditCardNumber = '%s'"""
                .formatted(
                        creditCard.getExpiryDate(),
                        creditCard.getName(),
                        creditCardNumber);
        try {
            jdbcTemplate.update(sqlQuery);
        } catch (Exception e) {
            throw new JdbcException(e.getMessage(), e);
        }
    }

    public List<Integer> getExpiryYears() {
        String sqlQuery = """
                SELECT DISTINCT $PARTITION.DATE_PARTITION_FUNCTION(expiryDate) + 2022 AS partition
                FROM CreditCard""";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> rs.getInt("partition"));
    }

    public Map<Integer, List<CreditCard>> getPartitions() {
        String sql = """
                SELECT $PARTITION.DATE_PARTITION_FUNCTION(expiryDate) AS partition,
                    card.creditCardNumber.ToString() AS cardNumber, card.expiryDate, card.name,
                    owner.userId AS userId, owner.name AS userName, owner.email, owner.gender
                FROM CreditCard card
                JOIN UserInfo owner
                    ON owner.userId = card.userId""";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
                    User owner = User.builder()
                            .userId(rs.getLong("userId"))
                            .name(rs.getString("userName"))
                            .email(rs.getString("email"))
                            .gender(rs.getString("gender"))
                            .build();
                    return CreditCard.builder()
                            .partition(rs.getInt("partition"))
                            .creditCardNumber(rs.getString("cardNumber"))
                            .user(owner)
                            .expiryDate(rs.getDate("expiryDate").toLocalDate())
                            .name(rs.getString("name"))
                            .build();
                })
                .stream()
                .collect(Collectors.groupingBy(CreditCard::getPartition));
    }
}
