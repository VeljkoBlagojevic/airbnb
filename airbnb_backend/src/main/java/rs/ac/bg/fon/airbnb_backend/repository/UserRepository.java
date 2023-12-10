package rs.ac.bg.fon.airbnb_backend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.airbnb_backend.domain.User;

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
}
