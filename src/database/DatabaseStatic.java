package database;

import enums.ESubject;
import shared.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class DatabaseStatic {
    static List<Question> questions = new ArrayList<>();

    static {
        questions.add(new Question("Choice A", "Choice B", "Choice C", "Choice D", ESubject.SUBJECT1, "What is 2 + 2?"));
        questions.add(new Question("Mercury", "Venus", "Mars", "Earth", ESubject.SUBJECT2, "Which planet is third from the sun?"));
        questions.add(new Question("Oxygen", "Carbon", "Nitrogen", "Hydrogen", ESubject.SUBJECT3, "What is the most abundant gas in Earth's atmosphere?"));
        questions.add(new Question("5", "6", "7", "8", ESubject.SUBJECT1, "What is 4 + 4?"));
        questions.add(new Question("France", "Germany", "Italy", "Spain", ESubject.SUBJECT4, "Which country is famous for the Eiffel Tower?"));
        questions.add(new Question("Dog", "Cat", "Bird", "Fish", ESubject.SUBJECT5, "Which animal is known as man's best friend?"));
        questions.add(new Question("Asia", "Europe", "Africa", "Australia", ESubject.SUBJECT2, "Which continent is the largest by area?"));
        questions.add(new Question("1990", "1995", "2000", "2005", ESubject.SUBJECT1, "In which year did the 21st century begin?"));
        questions.add(new Question("Triangle", "Square", "Circle", "Rectangle", ESubject.SUBJECT3, "Which shape has three sides?"));
        questions.add(new Question("Apple", "Banana", "Orange", "Strawberry", ESubject.SUBJECT2, "Which fruit is known for its red color and seeds on the outside?"));
        questions.add(new Question("Red", "Blue", "Yellow", "Green", ESubject.SUBJECT4, "What are the primary colors?"));
        questions.add(new Question("Copper", "Gold", "Iron", "Silver", ESubject.SUBJECT1, "Which metal is the most conductive?"));
        questions.add(new Question("Jupiter", "Saturn", "Uranus", "Neptune", ESubject.SUBJECT2, "Which planet has the most moons?"));
        questions.add(new Question("5", "15", "25", "50", ESubject.SUBJECT1, "What is 5 times 5?"));
        questions.add(new Question("H2O", "CO2", "O2", "N2", ESubject.SUBJECT3, "What is the chemical formula for water?"));
    }

    public Question oneBySubject(ESubject subject) {
        Predicate<Question> p = q -> q.getSubject().equals(subject);

        return findOne(p);
    }

    public Question findOne(Predicate<Question> p) {
        List<Question> all = findAll(p);
        Collections.shuffle(all);
        return all.getFirst();
    }

    public List<Question> findAll(Predicate<Question> p) {
        return new ArrayList<>(questions.stream().filter(p).toList());
    }

    public List<Question> retrieveAll() {
        return questions;
    }
}
