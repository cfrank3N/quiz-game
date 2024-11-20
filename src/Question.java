import serverSide.Answer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Question {
    private String questionText;
    private List<Answer> answers = new ArrayList<>();

    public Question(String questionText, Answer... answers) {
        this.questionText = questionText;
        Collections.addAll(this.answers, answers);
        Collections.shuffle(this.answers);
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public boolean isCorrectAnswer(int index) {
        if (index >= 0 && index < answers.size()) {
            return answers.get(index).isCorrect();
        }
        return false;
    }

    public String displayQuestion() {
        StringBuilder sb = new StringBuilder();
        sb.append(questionText).append("\n");
        for (int i = 0; < answers.size(); i++) {
            sb.append((i+1)).append(". ").append(answers.get(i).getText()).append("\n");
        }
        return sb.toString();
    }
}

