package edu.ucalgary.oop;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
/**
 @author Landon Reed
 @version 1.0
 Created on: April 5, 2025
 */
public class ErrorLog {

    /**
    Constructor for ErrorLog.
    Logs the provided exception's message along with the current timestamp to "errorlog.txt". 
    @param e The exception to log.
     */
    public ErrorLog(Exception e) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter("errorlog.txt", true)); 
            out.write("[" + LocalDateTime.now() + "] " + e.getMessage());
            out.newLine();
            out.newLine(); 
        } 
        catch (IOException ex) {
            System.out.println("Could not write to errorlog.txt.");
        } 
        finally {
            if (out != null) {
                try {
                    out.close();
                } 
                catch (IOException ex) {
                    System.out.println("Failed to close log file.");
                }
            }
        }
    }

}