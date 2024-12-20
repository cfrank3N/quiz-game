package database;

import enums.ESubject;
import shared.Question;


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

    public List<Question> threebySubject(ESubject subject) {
        Predicate<Question> p = q -> q.getSubject().equals(subject);
        List<Question> questions = findAll(p);
        Collections.shuffle(questions); // Added shuffle in the questions.
        return new ArrayList<>(questions.subList(0, 3));

    }

    public List<Question> nrOfQuestionsBySubject(ESubject subject, int nrOfQuestions) {
        Predicate<Question> p = q -> q.getSubject().equals(subject);
        List<Question> allOfSubject = findAll(p);
        Collections.shuffle(allOfSubject);

        return new ArrayList<>(allOfSubject.subList(0,nrOfQuestions));
    }
}