package serverSide;

import java.util.List;

public class Question {
    private String text;
    private List<Answer> answers;
    private int id;

    public Question(String text, int id) {
        this.id = id;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public int getId() {
        return id;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}



