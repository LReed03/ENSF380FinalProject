package edu.ucalgary.oop;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

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
    
    

    public void addDisasterVictim() {
        try {
            System.out.println(languageManager.getTranslation("EnterFirstName"));
            String firstName = scanner.nextLine().trim();
            if (firstName.isEmpty() || !isValidName(firstName)) {
                System.out.println(languageManager.getTranslation("InvalidInput"));
                return;
            }
    
            System.out.println(languageManager.getTranslation("EnterLastName"));
            String lastName = scanner.nextLine().trim();
            if (lastName.isEmpty() || !isValidName(lastName)) {
                System.out.println(languageManager.getTranslation("InvalidInput"));
                return;
            }
    
            System.out.println(languageManager.getTranslation("EnterDOB")); 
            String dob = scanner.nextLine().trim();
            if (!isValidDateFormat(dob)) {
                System.out.println(languageManager.getTranslation("InvalidDateFormat"));
                return;
            }
    
            System.out.println(languageManager.getTranslation("EnterGender")); /
            String genderInput = scanner.nextLine().trim().toLowerCase();
            Gender gender = Gender.valueOf(genderInput);
            
            System.out.println(languageManager.getTranslation("EnterComments"));
            String comments = scanner.nextLine().trim();
    
            String entryDate = java.time.LocalDate.now().toString();
            DisasterVictim newVictim = new DisasterVictim(firstName, entryDate);
            newVictim.setLastName(lastName);
            newVictim.setDateOfBirth(dob);
            newVictim.setGender(gender);
            newVictim.setComments(comments);
    
            viewFamilies();
            System.out.println(languageManager.getTranslation("EnterFamilyGroupOrSkip"));
            Integer famIndex = getValidatedIndex(families.size());
            if (famIndex != null) {
                FamilyGroup family = families.get(famIndex);
                family.addFamilyMember(newVictim);
                newVictim.setFamily(family);
            }
    
            if (checkPersonExists(newVictim)) {
                System.out.println(languageManager.getTranslation("VictimAlreadyExists"));
                return;
            }
    
            disastervictims.add(newVictim);
            model.insertDisasterVictim(newVictim);
            System.out.println(languageManager.getTranslation("VictimSuccessfullyAdded"));
    
        } 
        catch (IllegalArgumentException | NullPointerException e) {
            logErrorToFile(e); 
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }
    }
    
    public void addNewInquirer() {
        try {
            System.out.print(languageManager.getTranslation("EnterFirstName") + ": ");
            String firstName = scanner.nextLine().trim();
            if (firstName.isEmpty() || !firstName.matches("[a-zA-Z]+")) {
                System.out.println(languageManager.getTranslation("InvalidName"));
                return;
            }
    
            System.out.print(languageManager.getTranslation("EnterLastName") + ": ");
            String lastName = scanner.nextLine().trim();
            if (lastName.isEmpty() || !lastName.matches("[a-zA-Z]+")) {
                System.out.println(languageManager.getTranslation("InvalidName"));
                return;
            }
    
            System.out.print(languageManager.getTranslation("EnterPhoneNumber") + ": ");
            String phoneNumber = scanner.nextLine().trim();
            if (phoneNumber.isEmpty() || !phoneNumber.matches("[0-9\\-]+")) {
                System.out.println(languageManager.getTranslation("InvalidPhoneNumber"));
                return;
            }
    
            viewFamilies();
            System.out.println(languageManager.getTranslation("OptionalFamily"));
            Integer familyIndex = getValidatedIndex(families.size());
            FamilyGroup family = (familyIndex != null) ? families.get(familyIndex) : null;
    
            Inquirer newInquirer = new Inquirer(firstName, lastName, phoneNumber);
            if (family != null) {
                newInquirer.setFamily(family);
                family.addFamilyMember(newInquirer);
            }
    
            inquirers.add(newInquirer);
            model.insertInquirer(newInquirer); 
    
            System.out.println(languageManager.getTranslation("InquirerAddedSuccess"));
        } catch (IllegalArgumentException | NullPointerException e) {
            logErrorToFile(e); 
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1); 
        }
    }
    
    

    public void addNewMedicalRecord(){

    }

    public void addNewSupply(){

    }

    public void logInquiry(){

    }

    public void updateDisasterVictim() {
        viewDisasterVictims(); 
    
        int index = getValidatedIndex(disastervictims.size());
        if (index == -1) return;
    
        DisasterVictim victim = disastervictims.get(index);
    
        System.out.print(languageManager.getTranslation("EnterNewGender") + " (" + victim.getGender() + "): ");
        String genderInput = scanner.nextLine().trim();
        if (!genderInput.isEmpty()) {
            Gender newGender = Gender.valueOf(genderInput.toLowerCase()); 
            victim.setGender(newGender);
        }
    
        System.out.print(languageManager.getTranslation("EnterNewComments") + " (" + victim.getComments() + "): ");
        String commentInput = scanner.nextLine().trim();
        if (!commentInput.isEmpty()) {
            victim.setComments(commentInput);
        }
    
        System.out.println(languageManager.getTranslation("AssignToFamily"));
        viewFamilies();
        Integer famIndex = getValidatedIndex(familyGroups.size());
        if (famIndex != null) {
            FamilyGroup selectedFam = familyGroups.get(famIndex);
            victim.setFamily(selectedFam);
        }
    
        model.updateDisasterVictim(victim);
        System.out.println(languageManager.getTranslation("VictimSuccessfullyUpdated"));
    }
    

    public void updateInquirer(){

    }

    public void updateMedicalRecord(){

    }

    public void updateInquiry(){

    }

    public void allocateInventoryToLocation() {
        viewLocations();
        int locationIndex = getValidatedIndex(locations.size());
        if (locationIndex == -1) return;
    
        Location location = locations.get(locationIndex);
    
        ArrayList<InventoryItem> unallocatedInventory = new ArrayList<>();
        for (InventoryItem item : inventory) {
            if (item.getAllocatedToPerson() == null && item.getAllocatedToLocation() == null) {
                if (item.getItemType() != ItemType.PERSONALBELONGINGS) {
                    unallocatedInventory.add(item);
                }
            }
        }
    
        if (unallocatedInventory.isEmpty()) {
            System.out.println(languageManager.getTranslation("NoUnallocatedInventory"));
            return;
        }
    
        for (int i = 0; i < unallocatedInventory.size(); i++) {
            System.out.println(i + ": " + unallocatedInventory.get(i).getItemType());
        }
    
        int invIndex = getValidatedIndex(unallocatedInventory.size());
        if (invIndex == -1) return;
    
        InventoryItem selectedItem = unallocatedInventory.get(invIndex);
        location.addSupply(selectedItem); 
        model.allocateInventoryToLocation(selectedItem.getItemId(), location.getId()); 
    
        System.out.println(languageManager.getTranslation("InventoryAllocatedSuccessfully"));
    }
    

    public void allocateInventoryToPerson() {
        viewDisasterVictims();
        int victimIndex = getValidatedIndex(disastervictims.size());
        if (victimIndex == -1) return;
    
        DisasterVictim victim = disastervictims.get(victimIndex);
        Location victimLocation = null;
    
        for (Location loc : locations) {
            if (loc.getOccupants().contains(victim)) {
                victimLocation = loc;
                break;
            }
        }
    
        if (victimLocation == null) {
            System.out.println(languageManager.getTranslation("VictimNotAllocatedToLocation"));
            return;
        }
    
        ArrayList<InventoryItem> matchingInventory = new ArrayList<>();
        for (InventoryItem item : inventory) {
            if (item.getAllocatedToLocation() == victimLocation) {
                matchingInventory.add(item);
            }
        }
    
        if (matchingInventory.isEmpty()) {
            System.out.println(languageManager.getTranslation("NoInventoryAtVictimLocation"));
            return;
        }
    
        for (int i = 0; i < matchingInventory.size(); i++) {
            System.out.println(i + ": " + matchingInventory.get(i).getItemType());
        }
    
        int invIndex = getValidatedIndex(matchingInventory.size());
        if (invIndex == -1) return;
    
        InventoryItem selectedItem = matchingInventory.get(invIndex);
        victim.addBelonging(selectedItem);
        model.allocateInventoryToPerson(selectedItem.getItemId(), victim.getVictimID());
    
        System.out.println(languageManager.getTranslation("InventoryAllocatedSuccessfully"));
    }
    

    public void viewDisasterVictims(){
        for (int i = 0; i < disastervictims.size(); i++) {
            DisasterVictim v = disastervictims.get(i);
            System.out.println(i + ": " + languageManager.getTranslation("FirstName") + " " + v.getFirstName() + " " + languageManager.getTranslation("LastName") + " " + v.getLastName() + " " + languageManager.getTranslation("DateOfBirth")  + " " + v.getDateOfBirth());
        }
    }

    public void viewInquirers(){
        for(int i = 0; i < inquirers.size(); i++){
            Inquirer i = inquirers.get(i);
            System.out.print(i + ": " + languageManager.getTranslation("FirstName") + " " + v.getFirstName() + " " + languageManager.getTranslation("LastName") + " " + v.getLastName() + " " + languageManager.getTranslation("PhoneNumber") + " "  + i.getPhone())
        }

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
        for(int i = 0; i < inquiries.size(); i++){
            ReliefService inquiry = inquiries.get(i);
            System.out.println(i + ": " + languageManager.getTranslation("Inquirer") + " " + inquiry.getInquirer() + " " + languageManager.getTranslation("MissingPerson") + " " + inquiry.getMissingPerson() + " " + languageManager.getTranslation("Infoed") + " " + inquiry.getInfoProvided());
    }
}

    public void viewInventory(){
        for(int i = 0; i < supply.size(); i++){
            InventoryItem item = supply.get(i);
            System.out.println(i + ": " + languageManager.getTranslation("ItemType") + " " + item.getItemType())
        }
    }

    public void viewFamilies() {
        for (FamilyGroup group : families) {
            System.out.println(languageManager.getTranslation("FamilyID") + ": " + group.getFamilyID());
    
            for (Person member : group.getMembers()) {
                System.out.println("  - " + languageManager.getTranslation("FirstName") + ": " + member.getFirstName()+ ", " + languageManager.getTranslation("LastName") + ": " + member.getLastName());
            }
    
            System.out.println(); 
        }
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
        int index = null;
    
        while (true) {
            System.out.print(languageManager.getTranslation("EnterValidIndex") + ": ");
            String input = scanner.nextLine().trim();
    
            if (input.isEmpty()) {
                return null;
            }
    
            boolean isNumeric = true;
            for (char c : input.toCharArray()) {
                if (!Character.isDigit(c)) {
                    isNumeric = false;
                    break;
                }
            }
    
            if (!isNumeric) {
                System.out.println(languageManager.getTranslation("InvalidInputNumber")); 
                continue;
            }
    
            int value = Integer.parseInt(input);
            if (value >= 0 && value < upperBound) {
                index = value;
                break;
            } else {
                System.out.println(languageManager.getTranslation("InvalidIndex")); 
            }
        }
    
        return index;
    }

    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z\\-\\s]+"); 
    }

    private static boolean isValidDateFormat(String date){
		String dateRegex = "^\\d{4}[-]{1}\\d{2}[-]\\d{2}$";
		Pattern myPattern = Pattern.compile(dateRegex);
		Matcher mymatcher = myPattern.matcher(date);
		if(mymatcher.find()){
			return true;
		}
		else{
			return false;
		}
		
	}

    public void logErrorToFile(String message) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter("errorlog.txt", true)); 
            out.write("[" + LocalDateTime.now() + "] " + message);
            out.newLine();
            out.newLine(); 
        } 
        catch (IOException e) {
            System.out.println("Could not write to errorlog.txt.");
        } 
        finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    System.out.println("Failed to close log file.");
                }
            }
        }
    }
    
    
}
