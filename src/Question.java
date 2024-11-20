import java.util.ArrayList;
import java.util.Collections;

public class Question {
    ArrayList<String> subjectQuestions = new ArrayList<>();
    private String correctAnswer;

    public Question(String q1, String q2, String q3, String correctAnswer) {

        subjectQuestions.add(q1);
        subjectQuestions.add(q2);
        subjectQuestions.add(q3);
        subjectQuestions.add(correctAnswer);
        this.correctAnswer = correctAnswer;
        Collections.shuffle(subjectQuestions);


    }

    public String displayQuestion() {
        StringBuilder sb = new StringBuilder()
    }


    public boolean ifAnswerIsCorrect(String playerAnswer) {

        return playerAnswer.equalsIgnoreCase(getCorrectAnswer());
    }


    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }


}
