package rs.ac.bg.fon.airbnb_backend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.airbnb_backend.domain.User;
import rs.ac.bg.fon.airbnb_backend.exception.JdbcException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository implements MyRepository<User, Long>, RowMapper<User> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .userId(rs.getLong("userId"))
                .name(rs.getString("name"))
                .email(rs.getString("email"))
                .gender(rs.getString("gender"))
                .build();
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM UserInfo", this);
    }

    @Override
    public User findById(Long id) {
        String sqlQuery = String.format("SELECT * FROM UserInfo WHERE userId = %d", id);
        return jdbcTemplate.queryForObject(sqlQuery, this);
    }

    public void addNewGender(String newGender) {
        try {
            jdbcTemplate.update("EXEC AddNewGender ?", newGender);
        } catch (Exception e) {
            throw new JdbcException(e.getMessage(), e);
        }
    }

    @Override
    public void save(User user) {
        String sqlQuery = """
                INSERT INTO UserInfo(name, residenceId, email, gender)
                VALUES ('%s', %d, '%s', '%s')"""
                .formatted(user.getName(), 1, user.getEmail(), user.getGender());
        try {
            jdbcTemplate.update(sqlQuery);
        } catch (Exception e) {
            throw new JdbcException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(Long userId) {
        String sqlQuery = """
                DELETE FROM UserInfo
                WHERE userId = %d""".formatted(userId);
        try {
            jdbcTemplate.update(sqlQuery);
        } catch (Exception e) {
            throw new JdbcException(e.getMessage(), e);
        }
    }

    public void update(Long userId, User user) {
        String sqlQuery = """
                UPDATE UserInfo
                SET name = '%s', email = '%s', gender = '%s'
                WHERE userId = %d"""
                .formatted(
                        user.getName(),
                        user.getEmail(),
                        user.getGender(),
                        userId);
        try {
            jdbcTemplate.update(sqlQuery);
        } catch (Exception e) {
            throw new JdbcException(e.getMessage(), e);
        }
    }
}
