package database;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

//There should only be one serialization manager per DAO, might have to refactor things so this always holds true and no one can instantiate a serialization manager that modifies the same file as another serialization manager
public class SerializationManager<T extends Serializable> {
    private boolean serializing;

    public SerializationManager() {
        serializing = false;
    }

    //Lol so much code duplication.
    //TODO: Should not be needed, must be a method with an append flag probably?
    public void append(T object, String filepath) {
        if (serializing) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Path path = Paths.get(filepath);

        try {
            if (!Files.exists(path) || (Files.size(path) == 0)) {
                List<T> list = new ArrayList<>(List.of(object));
                serialize(list, filepath);
            } else {
                List<T> previous = deserialize(filepath); //Load previous content
                List<T> newObjects = new ArrayList<>();
                newObjects.addAll(previous);
                newObjects.add(object);
                serialize(newObjects, filepath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO: Should not be needed, must be a method with an append flag probably?
    public void appendList(List<T> objects, String filepath) {
        if (serializing) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

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

    //This is to write over the whole file
    //Since several threads need to be able to reach this but only one is allowed to use it at a time it needs to be synchronized
    public synchronized void serialize(List<T> objects, String filepath) {
        serializing = true;

        try (FileOutputStream fileout = new FileOutputStream(filepath);
             ObjectOutputStream objectout = new ObjectOutputStream(fileout)) {

            objectout.writeObject(objects);

        } catch (IOException e) {
            e.printStackTrace();
        }

        serializing = false;
        notify(); //To synchronize
    }

    //This is for reading, does not need to be synchronized but does need to wait if serialize is in progress
    public List<T> deserialize(String filepath) throws IOException {
        if (serializing) {
            try {
                wait(); //If serializing is in progress
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

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
