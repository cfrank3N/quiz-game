package serverSide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Question implements Serializable {
    ArrayList<String> subjectQuestions = new ArrayList<>();
    private String correctAnswer;

    public Question(String q1, String q2, String q3, String correctAnswer) {
/*
        Man kan också kolla med vilken ämne som är valt och ta frågorna därifrån om dem ligger i egna metoder.
        Man kanske också vill ha denna någon annan stans i koden.
        Exempelvis:
        if (choice = subject){//subject är förutbestämt beroende på vad vi har för olika ämnen.
        Call method;

        subjectQuestions.add(q1);
        subjectQuestions.add(q2);
        subjectQuestions.add(q3);
        subjectQuestions.add(correctAnswer);
        this.correctAnswer = correctAnswer;
        Collections.shuffle(subjectQuestions);

        //Den metod som stämmer överens med ämne.
        }else if (subject = ex: kroppen){ //kroppen är då ett ämne
        Call method;
        }

         */
        subjectQuestions.add(q1);
        subjectQuestions.add(q2);
        subjectQuestions.add(q3);
        subjectQuestions.add(correctAnswer);
        this.correctAnswer = correctAnswer;
        Collections.shuffle(subjectQuestions);

//man kan antingen returnera listan i slutet eller skriva ut listan efter man är klar.
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


}
