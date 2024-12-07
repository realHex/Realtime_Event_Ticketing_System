package org.w2053115.Setups.Configuration;


import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigurationUtility {
    public static void saveConfiguration(Configuration configuration){
        Gson gson = new Gson();

        String jsonText = gson.toJson(configuration); //Converting the object to a String

        try (FileWriter fileWriter = new FileWriter("configuration.json");){
            fileWriter.write(jsonText); //Writing the object to a json file
            //log
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Configuration loadConfiguration(){
        Gson gson = new Gson();
        try (FileReader fileReader = new FileReader("configuration.json")) {
            return gson.fromJson(fileReader, Configuration.class); //De-serializing the json file to an object
        } catch (IOException e) {
            return null;
        }
    }
}
