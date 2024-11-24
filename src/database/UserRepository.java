package database;

import serverSide.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class UserRepository implements Repository<User> {
    SerializationManager<User> sm = new SerializationManager<>("src/database/users_temp.ser", "src/database/users.ser", "users.ser");

    public User findOne(Predicate<User> p) {
        List<User> all = findAll(p);
        Collections.shuffle(all);
        return all.getFirst();
    }

    public List<User> findAll(Predicate<User> p) {
        return new ArrayList<>(sm.read().stream().filter(p).toList());
    }

    public List<User> retrieveAll() {
        return sm.read();
    }

    public void save(User user) {
        sm.write(user);
    }
}
