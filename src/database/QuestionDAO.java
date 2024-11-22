package database;


import serverSide.ESubject;
import serverSide.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class QuestionDAO {
    private final SerializationManager sm;//Can't handle exceptions properly maybe?

    public QuestionDAO() {
        sm = SerializationManager.getInstance();
    }

    public Question oneBySubject(ESubject subject) throws IOException {
        Predicate<Question> p = q -> q.getSubject().equals(subject);

        return findOne(p);
    }

    public Question findOne(Predicate<Question> p) throws IOException {
        List<Question> all = findAll(p);
        Collections.shuffle(all);
        return all.getFirst();
    }

    public List<Question> findAll(Predicate<Question> p) throws IOException {
        return new ArrayList<>(sm.deserialize().stream().filter(p).toList());
    }

    public List<Question> retrieveAll() throws IOException {
        return sm.deserialize();
    }

    public void overwrite(List<Question> questions) {
        sm.serialize(questions);
    }

    public void update(Question question) {
        sm.append(question);
    }
}
