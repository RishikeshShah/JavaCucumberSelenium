package utilities;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ReadPropertiesValue {
    public static String getBrowser() throws IOException, FileNotFoundException {
        // We need to FileReader class from java.io and provide the location of properties file
        //System.getProperty("user.dir") return the project location
        FileReader fr = new FileReader(System.getProperty("user.dir")+"\\src\\test\\resources\\configFiles\\config.properties");
        //We create an object of Properties class to load the content of the properties file
        Properties pr = new Properties();
        pr.load(fr);
        return pr.getProperty("browser");
    }
}
