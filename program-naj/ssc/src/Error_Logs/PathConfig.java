
package Error_Logs;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;


public class PathConfig { // configures the file path of the image 

    public static Properties ImPaths = new Properties();

    public String GetPaths(String PImage) { // sets the path to a string value
        String value = "";
        try {
            ImPaths.load(new FileInputStream("Paths.p")); // loads the path file
            value = ImPaths.getProperty(PImage); // sets the path file to the string value inintiated
        } catch (IOException e) {

        }
        return value;
    }
}
