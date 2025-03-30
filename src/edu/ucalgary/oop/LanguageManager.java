package edu.ucalgary.oop;

public class LanguageManager {
    private String languageCode = 'en-CA';
    private ArrayList<String> languageCodes = new ArrayList<>(Arrays.asList("en-CA", "fr-CA"));
    private ArrayList<String> keys = new ArrayList<>();
    private ArrayList<String> values = new ArrayList<>();
    private static LanguageManager instance;

    private LanguageManager(){
        loadLanguageFile(languageCode);
    }

    public static LanguageManager getInstance(){
        if(instance == null){
            instance = new LanguageManager();
        }
        return instance;
    }

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

    public void setLanguageCode(String code) {
        if (checkLanguageCodeFormat(code) && languageCodes.contains(code)) {
            languageCode = code;
            loadLanguageFile(code);
        }
    }


    public ArrayList<String> listLanguages(){
        reutrn languageCodes;
    }

    public String getTranslation(String key) {
        for (int i = 0; i < keys.size(); i++) {
            if (keys.get(i).equals(key)) {
                return values.get(i);
            }
        }
    }

    private static boolean checkLanguageCodeFormat(String code) {
        return code.matches("^[a-z]{2}-[A-Z]{2}$");
    }


}
