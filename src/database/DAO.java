package database;

import javax.sql.rowset.Predicate;
import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    Optional<T> findOne(Predicate p);
    List<T> findAll(Predicate p);
    void delete(Predicate p);
    void save(T t);
}
