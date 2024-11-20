package database;


import serverSide.Question;

import javax.sql.rowset.Predicate;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class QuestionDAO implements DAO<Question>{
    private final SerializationManager<Question> sm = new SerializationManager<>();
    private final String filepath = "src/database/questions.ser";

    @Override
    public Optional<Question> findOne(Predicate p) {
        //Deserialize entire questions.txt
        //Search through list
        //Return optional question
        return null;
    }

    @Override
    public List<Question> findAll(Predicate p) {
        return List.of();
    }

    @Override
    public void delete(Predicate p) {

    }

    @Override
    public void save(Question question) {
        sm.append(question, filepath);
    }

    public List<Question> retrieveAll() {
        try {
            return sm.deserialize(filepath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
