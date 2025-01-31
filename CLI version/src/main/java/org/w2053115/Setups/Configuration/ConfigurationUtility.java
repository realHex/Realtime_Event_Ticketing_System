package org.w2053115.Setups.Configuration;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This utility class provides methods for saving and loading the {@link Configuration} object to and from a JSON file.
 * It uses the Gson library for serialization and deserialization.
 */
public class ConfigurationUtility {

    /**
     * Saves the provided configuration object to a JSON file named "configuration.json".
     *
     * @param configuration The Configuration object to be saved
     * @throws RuntimeException If an IOException occurs during file writing
     */
    public static void saveConfiguration(Configuration configuration){
        Gson gson = new Gson();

        String jsonText = gson.toJson(configuration); //Converting the object to a String

        try (FileWriter fileWriter = new FileWriter("configuration.json");){
            fileWriter.write(jsonText); //Writing the object to a json file
            //log (Consider adding logging for successful save operation)
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Attempts to load a Configuration object from a JSON file named "configuration.json".
     *
     * @return The loaded Configuration object or null if the file is not found or an error occurs.
     */
    public static Configuration loadConfiguration(){
        Gson gson = new Gson();
        try (FileReader fileReader = new FileReader("configuration.json")) {
            return gson.fromJson(fileReader, Configuration.class); //De-serializing the json file to an object
        } catch (IOException e) {
            return null;
        }
    }
}