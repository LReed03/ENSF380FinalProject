package edu.ucalgary.oop;

public class ReliefController {
    private static ArrayList<DisasterVictim> disastervictims;
    private static ArrayList<Inquirer> inquirers;
    private static ArrayList<Location> locations;
    private static ArrayList<ReliefService> inquiries;
    private static ArrayList<InventoryItem> supply;
    private static ArrayList<FamilyGroup> familyGroups;
    private static DBAccess model;
    private static Scanner scanner;
    private static LanguageManager languageManager;

    public ReliefController(Scanner scanner, DBAccess model, LanguageManager languageManager){
        this.scanner = scanner;
        this.model = model;
        this.languageManager = languageManager;
        this.StartUp();
    }

    public void StartUp(){
        model.removeExpiredWater();
        this.familyGroups = model.getAllFamilyGroups();
        this.disastervictims = model.getAllDisasterVictims(this.familyGroups);
        this.inquirers = model.getAllInquirers(this.familyGroups);
        this.locations = model.getAllLocations();
        this.inquiries = model.getAllInquiries(this.inquirers, this.disastervictims, this.locations);
        this.supply = model.getAllInventory(this.disastervictims, this.locations);
        model.getAllMedicalRecords(this.locations, this.disastervictims);
    }

    public void allocateVictimToLocation() {
        viewDisasterVictims();
        int victimIndex = getValidatedIndex(disastervictims.size());
        DisasterVictim victim = disastervictims.get(victimIndex);
    
        viewLocations();
        int locationIndex = getValidatedIndex(locations.size());
        Location location = locations.get(locationIndex);
    
        if (location.getOccupants().contains(victim)) {
            System.out.println(languageManager.getTranslation("VictimAlreadyAllocated"));
            return;
        }
    
        location.addOccupant(victim);
        model.addDisasterVictimToLocation(victim.getVictimID(), location.getId());
    
        System.out.println(languageManager.getTranslation("VictimSuccessfullyAllocated"));
    }
    
    

    public void addNewDisasterVictim(){

    }

    public void addNewInquirer(){

    }

    public void addNewMedicalRecord(){

    }

    public void addNewSupply(){

    }

    public void logInquiry(){

    }

    public void updateDisasterVictim(){

    }

    public void updateInquirer(){

    }

    public void updateMedicalRecord(){

    }

    public void updateInquiry(){

    }

    public void allocateInventoryToLocation(){

    }

    public void allocateInventoryToPerson(){

    }

    public void viewDisasterVictims(){
        for (int i = 0; i < disastervictims.size(); i++) {
            DisasterVictim v = disastervictims.get(i);
            System.out.println(i + ": " + languageManager.getTranslation("FirstName") + " " + v.getFirstName() + " " + languageManager.getTranslation("LastName") + " " + v.getLastName() + " " + v.getDateOfBirth());
        }
    }

    public void viewInquirers(){

    }

    public void viewLocations(){
        for (int i = 0; i < locations.size(); i++) {
            Location loc = locations.get(i);
            System.out.println(i + ": " + languageManager.getTranslation("LocationName") + " " + loc.getName());
        }
    }

    public DisasterVictim viewMedicalRecords(){
        System.out.println(languageManager.getTranslation("MedicalRecordView"));
        viewDisasterVictims();
        int victimIndex = getValidatedIndex(disastervictims.size());
        DisasterVictim victim = disastervictims.get(victimIndex);
        ArrayList<MedicalRecord> medicalRecords = victim.getMedicalRecords();
        for(int i = 0; i < medicalRecords.size(); i++){
            MedicalRecord med = medicalRecords.get(i);
            System.out.println(i + ": " + languageManager.getTranslation("TreatmentDetails") + " " + med.getTreatmentDetails() + " " + languageManager.getTranslation("dateOfTreatment") + " " + med.getDateOfTreatment());
        }
    }

    public void viewInquiries(){

    }

    public void viewInventory(){

    }

    private static boolean checkPersonExists(DisasterVictim victim){
        for(DisasterVictim v: disastervictims){
            if(v.getFirstName().equalsIgnoreCase(victim.getFirstName()) && v.getLastName().equalsIgnoreCase(victim.getLastName()) && v.getDateOfBirth().equals(victim.getDateOfBirth())){
                return true;
            }
        }
        return false;

    }

    private int getValidatedIndex(int upperBound) {
        int index = -1;
    
        while (index < 0 || index >= upperBound) {
            System.out.print(languageManager.getTranslation("EnterValidIndex") + ": ");
            if (scanner.hasNextInt()) {
                index = scanner.nextInt();
                scanner.nextLine(); 
            } 
            else {
                scanner.nextLine(); 
                System.out.println(languageManager.getTranslation("InvalidInputNumber"));
            }
        }
    
        return index;
    }
    

}
