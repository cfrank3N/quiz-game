package database;

import shared.Question;
import shared.User;

public class Connections {
    public static SerializationManager<Question> questionSerializationManager = new SerializationManager<>("src/database/questions_temp.ser", "src/database/questions.ser", "questions.ser");

    public static SerializationManager<User> userSerializationManager = new SerializationManager<>("src/database/users_temp.ser", "src/database/users.ser", "users.ser");
}
