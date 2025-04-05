package edu.ucalgary.oop;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 @author Landon Reed
 @version 1.0
 Created on: April 5, 2025
 */
public class LanguageManager {
    private String languageCode;
    private ArrayList<String> languageCodes = new ArrayList<>(Arrays.asList("en-CA", "fr-CA"));
    private ArrayList<String> keys = new ArrayList<>();
    private ArrayList<String> values = new ArrayList<>();
    private static LanguageManager instance;

    /**
    Private constructor to initialize the LanguageManager with a specific language code.
    @param languageCode The language code to initialize with.
     */
    private LanguageManager(String languageCode){
        setLanguageCode(languageCode);
        loadLanguageFile(this.languageCode);
    }

    /**
    Retrieves the singleton instance of LanguageManager, initializing it if necessary.
    @param languageCode The language code to initialize with if the instance is null.
    @return The singleton instance of LanguageManager.
     */
    public static LanguageManager getInstance(String languageCode){
        if(instance == null){
            instance = new LanguageManager(languageCode);
        }
        return instance;
    }

    /**
    Retrieves the singleton instance of LanguageManager without reinitializing.
    @return The singleton instance of LanguageManager.
     */
    public static LanguageManager getInstance(){
        return instance;
    }
    

    /**
    Loads the language file corresponding to the specified language code.
    Clears any previously loaded keys and values.
    @param languageCode The language code of the file to load.
     */
    public void loadLanguageFile(String languageCode) {
        keys.clear();
        values.clear();
        File dataFolder = new File("data");
        File languagesFolder = new File(dataFolder, "languages");
        File languageFile = new File(languagesFolder, languageCode.concat(".xml"));

        Scanner scanner = null;

        try {
            scanner = new Scanner(languageFile);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                if (line.contains("<key>")) {
                    String key = line.replace("<key>", "").replace("</key>", "").trim();
                    String valueLine = scanner.nextLine().trim();
                    String value = valueLine.replace("<value>", "").replace("</value>", "").trim();
                    keys.add(key);
                    values.add(value);
                }
            }
        } 
        catch (FileNotFoundException e) {
            System.out.println("Could not find file.");
        } 
        finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    /**
    Sets the current language code and reloads the corresponding language file.
    @param code The new language code to set.
     */
    public void setLanguageCode(String code) {
        if (checkLanguageCodeFormat(code) && languageCodes.contains(code)) {
            this.languageCode = code;
            loadLanguageFile(code);
        }
    }

    /**
    Lists all supported language codes.
    @return An ArrayList of supported language codes.
     */
    public ArrayList<String> listLanguages(){
        return languageCodes;
    }

    /**
    Retrieves the translation for a given key.
    @param key The key to look up in the translations.
    @return The corresponding translation, or a placeholder if the key is missing.
     */
    public String getTranslation(String key) {
        for (int i = 0; i < keys.size(); i++) {
            if (keys.get(i).equals(key)) {
                return values.get(i);
            }
        }
        return "[Missing translation: " + key + "]";
    }

    /**
    Checks if a language code is in the correct format (e.g., "en-CA").
    @param code The language code to validate.
    @return True if the format is valid, false otherwise.
     */
    private static boolean checkLanguageCodeFormat(String code) {
        return code.matches("^[a-z]{2}-[A-Z]{2}$");
    }
}
