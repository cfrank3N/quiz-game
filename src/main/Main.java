package main;
import serverSide.DatabaseHandler;
import serverSide.Question;
import serverSide.Answer;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DatabaseHandler dbHandler = new DatabaseHandler();

        // Hämta alla frågor
        List<Question> questions = dbHandler.getAllQuestions();
        for (Question question : questions) {
            System.out.println("Question: " + question.getText());

            // Hämta svar för varje fråga
            List<Answer> answers = dbHandler.getAnswersForQuestion(question.getId());
            for (Answer answer : answers) {
                System.out.println(" - Answer: " + answer.getText() + " (Correct: " + answer.isCorrect() + ")");
            }
        }
    }



}
