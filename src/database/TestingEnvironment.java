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

//// Mathematics
//        questions.add(new Question("5", "15", "35", "25", ESubject.MATHEMATICS, "What is 5 times 5?"));
//        questions.add(new Question("8", "78", "12", "16", ESubject.MATHEMATICS, "What is 4 times 4?"));
//        questions.add(new Question("29", "18", "27", "36", ESubject.MATHEMATICS, "What is 6 times 6?"));
//
//// Science
//        questions.add(new Question("Bohr", "Einstein", "Tesla", "Newton", ESubject.SCIENCE, "Who formulated the laws of motion?"));
//        questions.add(new Question("Moon", "Mars", "Earth", "Sun", ESubject.SCIENCE, "Which celestial body is at the center of our solar system?"));
//        questions.add(new Question("Oxygen", "Carbon", "Water", "Hydrogen", ESubject.SCIENCE, "What is the main component of H2O?"));
//
//// History
//        questions.add(new Question("1720", "1492", "1066", "1776", ESubject.HISTORY, "In what year was the Declaration of Independence signed?"));
//        questions.add(new Question("Bill Clinton", "Abraham Lincoln", "Thomas Jefferson", "George Washington", ESubject.HISTORY, "Who was the first President of the United States?"));
//        questions.add(new Question("Vietnam War", "World War I", "The Renaissance", "The Great Depression", ESubject.HISTORY, "What major event occurred in 1929?"));
//
//// Literature
//        questions.add(new Question("Dick", "Dickens", "Hemingway", "Shakespeare", ESubject.LITERATURE, "Who wrote 'Romeo and Juliet'?"));
//        questions.add(new Question("Bible", "Brave New World", "The Great Gatsby", "1984", ESubject.LITERATURE, "Which novel was written by George Orwell?"));
//        questions.add(new Question("Dostoevsky", "Virgil", "Sophocles", "Homer", ESubject.LITERATURE, "Who authored 'The Iliad'?"));
//
//// Geography
//        questions.add(new Question("America", "Africa", "Europe", "Asia", ESubject.GEOGRAPHY, "Which is the largest continent by land area?"));
//        questions.add(new Question("Amazon", "Thames", "Yangtze", "Nile", ESubject.GEOGRAPHY, "What is the longest river in the world?"));
//        questions.add(new Question("Australia", "Africa", "Iceland", "Greenland", ESubject.GEOGRAPHY, "Which is the largest island in the world?"));
//
//// Sports
//        questions.add(new Question("Football", "Tennis", "Baseball", "Cricket", ESubject.SPORTS, "Which sport uses a bat, ball, and wickets?"));
//        questions.add(new Question("Michael Phelps", "Cristiano Ronaldo", "Roger Federer", "Usain Bolt", ESubject.SPORTS, "Who is the fastest sprinter in the world?"));
//        questions.add(new Question("Tennis", "Baseball", "Hockey", "Football", ESubject.SPORTS, "Which sport is known as 'the beautiful game'?"));
////
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
