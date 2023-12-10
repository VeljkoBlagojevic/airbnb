package rs.ac.bg.fon.airbnb_backend.repository;

import java.util.List;

public interface MyRepository<T, ID> {
    List<T> findAll();
    T findById(ID id);
}
