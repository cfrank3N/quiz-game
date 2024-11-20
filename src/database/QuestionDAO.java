package database;


import serverSide.Question;

import javax.sql.rowset.Predicate;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class QuestionDAO implements DAO<Question>{
    //Called "eager initialization", supposedly loads this at class loading therefore won't result in multiple instances?
    private final SerializationManager sm;//Can't handle exceptions properly maybe?
    private static QuestionDAO instance;

    public QuestionDAO() {
        sm = SerializationManager.getInstance();
    }

//    public static QuestionDAO getInstance() {
//        if (instance == null) {
//            instance = new QuestionDAO();
//        }
//
//        return instance;
//    }

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
        sm.write(question);
    }

    public List<Question> retrieveAll() {
        return sm.read();
    }
}
