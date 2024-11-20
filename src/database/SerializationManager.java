import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SerializationManager<T extends Serializable> {
    public SerializationManager() {
    }

    public void append(List<T> objects, String filepath) {
        Path path = Paths.get(filepath);

        try {
            if (!Files.exists(path) || (Files.size(path) == 0)) {
                serialize(objects, filepath);
            } else {
                List<T> previous = deserialize(filepath); //Load previous content
                List<T> newObjects = new ArrayList<>();
                newObjects.addAll(previous);
                newObjects.addAll(objects);
                serialize(newObjects, filepath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serialize(List<T> objects, String filepath) {
        try (FileOutputStream fileout = new FileOutputStream(filepath);
             ObjectOutputStream objectout = new ObjectOutputStream(fileout)) {

            objectout.writeObject(objects);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<T> deserialize(String filepath) throws IOException {
        if (!Paths.get(filepath).toFile().exists()) {
            throw new IOException("File does not exist.");
        }

        List<T> objects = new ArrayList<>();
        try (FileInputStream fileIn = new FileInputStream(filepath);
             ObjectInputStream in = new ObjectInputStream(fileIn);) {

            objects = (List<T>) in.readObject(); //TODO Hm...
            return objects;
        }
        catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return objects;
    }
}
