package database;


import serverSide.ESubject;
import serverSide.Question;

import javax.sql.rowset.Predicate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class QuestionDAO {
    private final SerializationManager sm;//Can't handle exceptions properly maybe?

    public QuestionDAO() {
        sm = SerializationManager.getInstance();
    }

    public Question findOne(ESubject subject) throws IOException {
        List<Question> all = findAll(subject);
        Collections.shuffle(all);
        return all.getFirst();
    }

    public List<Question> findAll(ESubject subject) throws IOException {
        return new ArrayList<>(sm.deserialize().stream().filter(q -> q.getSubject().equals(subject)).toList());
    }

    public List<Question> retrieveAll() throws IOException {
        return sm.deserialize();
    }

    public void overwrite(List<Question> questions) {
        sm.serialize(questions);
    }
}
