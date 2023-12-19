package rs.ac.bg.fon.airbnb_backend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.airbnb_backend.domain.DocumentType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DocumentTypeRepository implements MyRepository<DocumentType, Long>, RowMapper<DocumentType> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public DocumentType mapRow(ResultSet rs, int rowNum) throws SQLException {
        return DocumentType.builder()
                .documentTypeId(rs.getLong("documentTypeId"))
                .name(rs.getString("name"))
                .build();
    }


    @Override
    public List<DocumentType> findAll() {
        return jdbcTemplate.query("SELECT * FROM DocumentType", this);
    }

    @Override
    public DocumentType findById(Long id) {
        String sqlQuery = String.format("SELECT * FROM DocumentType WHERE documentTypeId = %d", id);
        return jdbcTemplate.queryForObject(sqlQuery, this);
    }

    @Override
    public void save(DocumentType value) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void delete(Long aLong) {
        throw new UnsupportedOperationException("Not implemented");
    }

}
