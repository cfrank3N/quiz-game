package serverSide;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utility {
    static Properties properties = new Properties();

    static {
        try (InputStream in = new FileInputStream("src/serverSide/application.properties")){
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
