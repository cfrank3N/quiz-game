package database;

import enums.ESubject;
import shared.Question;
import shared.User;

import java.util.ArrayList;
import java.util.List;

public class TestingEnvironment {
    public static void main(String[] args) {
        QuestionRepository qr = new QuestionRepository();
        UserRepository ur = new UserRepository();
        List<Question> questions = new ArrayList<>();

//        questions.add(new Question("5", "15", "50", "25", ESubject.SUBJECT1, "What is 5 times 5?"));
//        questions.add(new Question("13", "2", "0", "20", ESubject.SUBJECT1, "What is 10 times 2?"));
//        questions.add(new Question("13", "2", "0", "20", ESubject.SUBJECT1, "What is 10 times 2?"));
//        questions.add(new Question("14", "2", "0", "20", ESubject.SUBJECT2, "What is the capital of France?"));
//        questions.add(new Question("15", "2", "0", "20", ESubject.SUBJECT3, "What is the square root of 16?"));
//        questions.add(new Question("16", "2", "0", "20", ESubject.SUBJECT4, "Who wrote 'Romeo and Juliet'?"));
//        questions.add(new Question("17", "2", "0", "20", ESubject.SUBJECT5, "How many continents are there on Earth?"));
//
//        for (Question q : questions) {
//            qr.save(q);
//        }

        List<Question> retrieved = qr.retrieveAll();
        for (Question q : retrieved) {
            System.out.println(q.getSubjectQuestions());
        }

//        List<User> users = new ArrayList<>();
//
//        // Add 5 users to the list
//        users.add(new User("Fiona Harper", "fiona.harper@example.com", 27));
//        users.add(new User("George Miller", "george.miller@example.com", 33));
//        users.add(new User("Hannah Davis", "hannah.davis@example.com", 24));
//        users.add(new User("Ian Wright", "ian.wright@example.com", 29));
//        users.add(new User("Jessica Taylor", "jessica.taylor@example.com", 31));
//
//        for (User u : users) {
//            ur.save(u);
//        }

        List<User> retrievedUsers = ur.retrieveAll();
        for (User u : retrievedUsers) {
            System.out.println(u);
        }
    }
}
