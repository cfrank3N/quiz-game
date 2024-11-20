import serverSide.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        List<Question> questions = new ArrayList<>();

        Answer q1a1 = new Answer("3", false);
        Answer q1a2 = new Answer("4", true);
        Answer q1a3 = new Answer("5", false);
        questions.add(new Question("What is 2 + 2?", q1a1, q1a2, q1a3));

        Answer q2a1 = new Answer("Earth", true);
        Answer q2a2 = new Answer("Mars", false);
        Answer q2a3 = new Answer("Venus", false);
        questions.add(new Question("Which planet do we live on?", q2a1, q2a2, q2a3));

        Answer q3a1 = new Answer("Blue", true);
        Answer q3a2 = new Answer("Red", false);
        Answer q3a3 = new Answer("Green", false);
        questions.add(new Question("What color is the sky?", q3a1, q3a2, q3a3));

        int score = 0;
        Scanner scanner = new Scanner(System.in);

        for (Question question : questions) {
            System.out.println(question.displayQuestion());
            System.out.print("Choose your answer (1, 2, or 3): ");
            int playerChoice = scanner.nextInt();


            if (question.isCorrectAnswer(playerChoice - 1)) {
                System.out.println("Correct!");
                score++;

            } else {
                System.out.println("incorrect.");
            }
            System.out.println();
        }


        System.out.println("Quiz finished! Your score: " + score + "/" + questions.size());
        scanner.close();
    }


}
