package database;

import enums.ESubject;
import serverSide.Question;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class QuestionRepository implements Repository<Question> {
    private final SerializationManager<Question> sm = Connections.questionSerializationManager;

    public Question oneBySubject(ESubject subject) throws IOException {
        Predicate<Question> p = q -> q.getSubject().equals(subject);

        return findOne(p);
    }

    public Question findOne(Predicate<Question> p) {
        List<Question> all = findAll(p);
        Collections.shuffle(all);
        return all.getFirst();
    }

    public List<Question> findAll(Predicate<Question> p) {
        return new ArrayList<>(sm.read().stream().filter(p).toList());
    }

    public List<Question> retrieveAll() {
        return sm.read();
    }

    public void save(Question question) {
        sm.write(question);
    }
}