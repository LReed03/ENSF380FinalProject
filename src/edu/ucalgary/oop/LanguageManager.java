package edu.ucalgary.oop;

public class LanguageManager {
    private String languageCode = 'en-CA';
    private ArrayList<String> languageCodes = new ArrayList<>(Arrays.asList("en-Ca", "fr-CA"));
    private Map<String, String> translations = new HashMap<>();
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
        try {
            translations.clear();

            File dataFolder = new File("data");
            File languageFolder = new File(dataFolder, "languages");
            File languageFile = new File(languageFolder, languageCode + ".xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(languageFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("translation");

            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String key = element.getElementsByTagName("key").item(0).getTextContent();
                    String value = element.getElementsByTagName("value").item(0).getTextContent();
                    translations.put(key, value);
                }
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
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

    private static boolean checkLanguageCodeFormat(String code) {
        return code.matches("^[a-z]{2}-[A-Z]{2}$");
    }


}
