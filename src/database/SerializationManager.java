package database;

import serverSide.Question;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

//There should only be one serialization manager per DAO, might have to refactor things so this always holds true and no one can instantiate a serialization manager that modifies the same file as another serialization manager.
public class SerializationManager {
    private final String filepath = "src/database/questions.ser";
    private static SerializationManager instance = new SerializationManager();


    private SerializationManager () {
    }

    public static SerializationManager getInstance() {
        return instance;
    }

    //TODO: Should not be needed, must be a method with an append flag probably?
    public void append(Question object) {
        Path path = Paths.get(filepath);

        try {
            if (!Files.exists(path) || (Files.size(path) == 0)) {
                List<Question> list = new ArrayList<>(List.of(object));
                serialize(list);
            } else {
                List<Question> previous = deserialize(); //Load previous content
//                       writing = true;
                List<Question> newObjects = new ArrayList<>();
                newObjects.addAll(previous);
                newObjects.add(object);
                serialize(newObjects);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serialize(List<Question> objects) {
        try (FileOutputStream fileout = new FileOutputStream(filepath);
             ObjectOutputStream objectout = new ObjectOutputStream(fileout)) {

            objectout.writeObject(objects);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Question> deserialize() throws IOException {
        if (!Paths.get(filepath).toFile().exists()) {
            throw new IOException("File does not exist.");
        } else if (Paths.get(filepath).toFile().length() == 0) {
            return new ArrayList<>();
        }

        List<Question> objects = new ArrayList<>();
        try (FileInputStream fileIn = new FileInputStream(filepath);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
                objects = (List<Question>) in.readObject(); //TODO Hm...
        }
        catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return objects;
    }
}
