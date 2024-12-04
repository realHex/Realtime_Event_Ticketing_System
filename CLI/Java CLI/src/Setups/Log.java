package Setups;

import java.io.*;
import java.util.logging.Logger;

//Modules to get the time for the text file
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
    private static final Logger log = Logger.getLogger("Logger");
    private static File logFile;
    private static FileWriter logFileWriter;


    //Method to create the file
    public static void createLogFile() {
        //Getting current date and time and formatting it
        String fileName;
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter dateFormatted = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        fileName = "Configuration.Log - " + date.format(dateFormatted);
        try {
            logFile = new File(fileName);
            logFileWriter = new FileWriter(logFile);

        }
        catch (IOException e) {
            System.out.println("Error while creating the file" + fileName);
        }
    }

    public static void log (String level, String message) {
        try {
            System.out.println(message);
            logFileWriter.write("[" + level + "] " + message + "\n");
        }
        catch (IOException e) {
            System.out.println("Error while writing to file");
        }
    }
}
