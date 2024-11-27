package database;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class SerializationManager<T> {
    private final String tempFilePath;
    private final String permanentFilePath;
    private final String name;
    private State state;


    public SerializationManager(String tempFilePath, String permanentFilePath, String name) {
        this.tempFilePath = tempFilePath;
        this.permanentFilePath = permanentFilePath;
        this.name = name;
        state = State.IDLE;
    }

    public synchronized void write(T object) {
        while (state.equals(State.READING)) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        state = State.WRITING;
        append(object);
        state = State.IDLE;
        notify();
    }

    public synchronized List<T> read() {
        while (state.equals(State.WRITING)) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        state = State.READING;
        List<T> objects = null;
        try {
            objects = deserialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
        state = State.IDLE;
        notify();
        return objects;
    }

    public void append(T object) {
        Path path = Paths.get(permanentFilePath);

        try {
            if (!Files.exists(path) || (Files.size(path) == 0)) {
                List<T> list = new ArrayList<>(List.of(object));
                serialize(list);
            } else {
                List<T> previous = deserialize(); //Load previous content
//                       writing = true;
                List<T> newObjects = new ArrayList<>();
                newObjects.addAll(previous);
                newObjects.add(object);
                serialize(newObjects);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serialize(List<T> objects) {
        try (FileOutputStream fileout = new FileOutputStream(tempFilePath);
             ObjectOutputStream objectout = new ObjectOutputStream(fileout)) {

            objectout.writeObject(objects);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Path source = Paths.get(tempFilePath);
            Files.move(source, source.resolveSibling(name), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<T> deserialize() throws IOException {
        if (!Paths.get(permanentFilePath).toFile().exists()) {
            throw new IOException("File does not exist.");
        } else if (Paths.get(permanentFilePath).toFile().length() == 0) {
            return new ArrayList<>();
        }

        List<T> objects = new ArrayList<>();
        try (FileInputStream fileIn = new FileInputStream(permanentFilePath);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
                objects = (List<T>) in.readObject();
        }
        catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return objects;
    }
}