package serverSide;

public class Answer {
    private int id;
    private int questionid;
    private String text;
    private boolean isCorrect;

    public Answer(String text, boolean isCorrect, int id, int questionid) {
        this.id = id;
        this.questionid = questionid;
        this.text = text;
        this.isCorrect = isCorrect;

    }

    public int getId() {
        return id;
    }

    public int getQuestionid() {
        return questionid;
    }

    public String getText() {
        return text;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}
