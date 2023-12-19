package rs.ac.bg.fon.airbnb_backend.repository;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface MyRepository<T, ID> {
    List<T> findAll();
    T findById(ID id);
    void save(T value);
    void delete(ID id);
}
