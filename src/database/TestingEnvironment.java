package database;

import serverSide.ESubject;
import serverSide.Question;
import serverSide.User;

import java.util.ArrayList;
import java.util.List;

public class TestingEnvironment {
    public static void main(String[] args) {
        QuestionRepository qr = new QuestionRepository();
        UserRepository ur = new UserRepository();
//        List<Question> questions = new ArrayList<>();
//
//        questions.add(new Question("Choice A", "Choice B", "Choice C", "Choice D", ESubject.SUBJECT1, "What is 2 + 2?"));
//        questions.add(new Question("Mercury", "Venus", "Mars", "Earth", ESubject.SUBJECT2, "Which planet is third from the sun?"));
//        questions.add(new Question("Oxygen", "Carbon", "Nitrogen", "Hydrogen", ESubject.SUBJECT3, "What is the most abundant gas in Earth's atmosphere?"));
//        questions.add(new Question("5", "6", "7", "8", ESubject.SUBJECT1, "What is 4 + 4?"));
//        questions.add(new Question("France", "Germany", "Italy", "Spain", ESubject.SUBJECT4, "Which country is famous for the Eiffel Tower?"));
//        questions.add(new Question("Dog", "Cat", "Bird", "Fish", ESubject.SUBJECT5, "Which animal is known as man's best friend?"));
//        questions.add(new Question("Asia", "Europe", "Africa", "Australia", ESubject.SUBJECT2, "Which continent is the largest by area?"));
//        questions.add(new Question("1990", "1995", "2000", "2005", ESubject.SUBJECT1, "In which year did the 21st century begin?"));
//        questions.add(new Question("Triangle", "Square", "Circle", "Rectangle", ESubject.SUBJECT3, "Which shape has three sides?"));
//        questions.add(new Question("Apple", "Banana", "Orange", "Strawberry", ESubject.SUBJECT2, "Which fruit is known for its red color and seeds on the outside?"));
//        questions.add(new Question("Red", "Blue", "Yellow", "Green", ESubject.SUBJECT4, "What are the primary colors?"));
//        questions.add(new Question("Copper", "Gold", "Iron", "Silver", ESubject.SUBJECT1, "Which metal is the most conductive?"));
//        questions.add(new Question("Jupiter", "Saturn", "Uranus", "Neptune", ESubject.SUBJECT2, "Which planet has the most moons?"));
//        questions.add(new Question("5", "15", "25", "50", ESubject.SUBJECT1, "What is 5 times 5?"));
//        questions.add(new Question("H2O", "CO2", "O2", "N2", ESubject.SUBJECT3, "What is the chemical formula for water?"));
//
//        for (Question q : questions) {
//            qr.save(q);
//        }

        List<Question> retrieved = qr.retrieveAll();
        for (Question q : retrieved) {
            System.out.println(q.getSubjectQuestions());
        }
//
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
