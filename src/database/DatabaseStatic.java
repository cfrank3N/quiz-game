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
// Mathematics
        questions.add(new Question("5", "15", "25", "25", ESubject.MATHEMATICS, "What is 5 times 5?"));
        questions.add(new Question("8", "16", "12", "16", ESubject.MATHEMATICS, "What is 4 times 4?"));
        questions.add(new Question("36", "18", "27", "36", ESubject.MATHEMATICS, "What is 6 times 6?"));

// Science
        questions.add(new Question("Newton", "Einstein", "Tesla", "Newton", ESubject.SCIENCE, "Who formulated the laws of motion?"));
        questions.add(new Question("Sun", "Mars", "Earth", "Sun", ESubject.SCIENCE, "Which celestial body is at the center of our solar system?"));
        questions.add(new Question("Oxygen", "Carbon", "Water", "Water", ESubject.SCIENCE, "What is the main component of H2O?"));

// History
        questions.add(new Question("1776", "1492", "1066", "1776", ESubject.HISTORY, "In what year was the Declaration of Independence signed?"));
        questions.add(new Question("George Washington", "Abraham Lincoln", "Thomas Jefferson", "George Washington", ESubject.HISTORY, "Who was the first President of the United States?"));
        questions.add(new Question("The Great Depression", "World War I", "The Renaissance", "The Great Depression", ESubject.HISTORY, "What major event occurred in 1929?"));

// Literature
        questions.add(new Question("Shakespeare", "Dickens", "Hemingway", "Shakespeare", ESubject.LITERATURE, "Who wrote 'Romeo and Juliet'?"));
        questions.add(new Question("1984", "Brave New World", "The Great Gatsby", "1984", ESubject.LITERATURE, "Which novel was written by George Orwell?"));
        questions.add(new Question("Homer", "Virgil", "Sophocles", "Homer", ESubject.LITERATURE, "Who authored 'The Iliad'?"));

// Geography
        questions.add(new Question("Asia", "Africa", "Europe", "Asia", ESubject.GEOGRAPHY, "Which is the largest continent by land area?"));
        questions.add(new Question("Amazon", "Nile", "Yangtze", "Nile", ESubject.GEOGRAPHY, "What is the longest river in the world?"));
        questions.add(new Question("Australia", "Greenland", "Iceland", "Greenland", ESubject.GEOGRAPHY, "Which is the largest island in the world?"));

// Sports
        questions.add(new Question("Football", "Tennis", "Cricket", "Cricket", ESubject.SPORTS, "Which sport uses a bat, ball, and wickets?"));
        questions.add(new Question("Michael Phelps", "Usain Bolt", "Roger Federer", "Usain Bolt", ESubject.SPORTS, "Who is the fastest sprinter in the world?"));
        questions.add(new Question("Soccer", "Baseball", "Hockey", "Soccer", ESubject.SPORTS, "Which sport is known as 'the beautiful game'?"));
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
