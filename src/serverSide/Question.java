package serverSide;

import enums.ESubject;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Question implements Serializable {
    ArrayList<String> subjectQuestions = new ArrayList<>();
    private String correctAnswer;
    private ESubject subject;
    private String question;
    @Serial
    private static final long serialVersionUID = 1L;

    public Question(String q1, String q2, String q3, String correctAnswer, ESubject subject, String question) {

        subjectQuestions.add(q1);
        subjectQuestions.add(q2);
        subjectQuestions.add(q3);
        subjectQuestions.add(correctAnswer);
        this.correctAnswer = correctAnswer;
        this.subject = subject;
        this.question = question;
        Collections.shuffle(subjectQuestions);

//man kan antingen returnera listan i slutet eller skriva ut listan efter man är klar.
    }

    public ArrayList<String> getSubjectQuestions() {
        return subjectQuestions;
    }

    //Beror lite på hur vi ska ha spelet. Antagligen knappar då får man bara skriva om lite. Men antagligen får man skriva om lite
    public boolean ifAnswerIsCorrect(String playerAnswer) {

        return playerAnswer.equalsIgnoreCase(getCorrectAnswer());
    }
    /*

public serverSide.ESubject getSubject(){
        return  serverSide.ESubject.SUBJECT1;
}

     */

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public ESubject getSubject() {
        return subject;
    }

    public String getQuestion() {
        return question;
    }
}
