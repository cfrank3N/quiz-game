package serverSide;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import serverSide.Question;
import serverSide.Answer;


public class DatabaseHandler {
   private static final String url = "jdbc:mysql://localhost:3306/quizdb";
   private static final String username = "root";
   private static final String password = "Hejsan123";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();

        String query = "SELECT id, question_text FROM questions";

        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String questionText = resultSet.getString("question_text");
                questions.add(new Question(questionText, id));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return questions;
    }

    public List<Answer> getAnswersForQuestion(int questionId) {
        List<Answer> answers = new ArrayList<>();

        String query = "SELECT id, answer_text, is_correct FROM answers WHERE question_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, questionId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String answerText = resultSet.getString("answer_text");
                boolean isCorrect = resultSet.getBoolean("is_correct");
                answers.add(new Answer(answerText, isCorrect, id, questionId));

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return answers;
    }
}
