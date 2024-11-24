package database;

import java.util.List;
import java.util.function.Predicate;

public interface Repository<T> {
    T findOne(Predicate<T> p);
    List<T> findAll(Predicate<T> p);
    List<T> retrieveAll();
    void save(T question);
}
