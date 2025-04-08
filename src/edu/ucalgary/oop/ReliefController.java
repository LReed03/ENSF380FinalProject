package edu.ucalgary.oop;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.*;
/**
 @author Landon Reed
 @version 1.0
 Created on: April 5, 2025
 */
public class ReliefController {
    private static ArrayList<DisasterVictim> disastervictims;
    private static ArrayList<Inquirer> inquirers;
    private static ArrayList<Location> locations;
    private static ArrayList<ReliefService> inquiries;
    private static ArrayList<InventoryItem> supply;
    private static ArrayList<FamilyGroup> familyGroups;
    private static DBAccess model;
    private static LanguageManager languageManager;

    /**
    @param scanner Scanner object for user input.
    @param model DBAccess object for database operations.
    @param languageManager LanguageManager object for translations.
     */
    public ReliefController(DBAccess model, LanguageManager languageManager){
        this.model = model;
        this.languageManager = languageManager;
        this.StartUp();
    }

    /**
    Initializes the application by loading data from the database.
     */
    public void StartUp(){
        try{
            model.removeExpiredWater();
            this.familyGroups = model.getFamilyGroups();
            this.disastervictims = model.getAllDisasterVictims(this.familyGroups);
            this.inquirers = model.getAllInquirers(this.familyGroups);
            this.locations = model.getAllLocations();
            this.inquiries = model.getAllInquiries(this.inquirers, this.disastervictims, this.locations);
            this.supply = model.getAllInventory(this.disastervictims, this.locations);
            model.getAllMedicalRecords(this.locations, this.disastervictims);
            model.assignVictimsToLocations(disastervictims, locations);
        }
        catch (Exception e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }
    }

    /**
    Allocates a disaster victim to a location.
     */
    public boolean allocateVictimToLocation(int victimIndex, int locationIndex) {
        if (victimIndex < 0 || victimIndex >= disastervictims.size() ||
            locationIndex < 0 || locationIndex >= locations.size()) {
            return false;
        }
    
        DisasterVictim victim = disastervictims.get(victimIndex);
        Location newLocation = locations.get(locationIndex);
    
        for (Location loc : locations) {
            if (loc.getOccupants().contains(victim)) {
                loc.removeOccupant(victim);
                model.removeVictimFromLocation(victim.getId(), loc.getId());
                break;
            }
        }
    
        newLocation.addOccupant(victim);
        model.addDisasterVictimToLocation(victim.getId(), newLocation.getId());
        return true;
    }
    
    
    
    /**
    Adds a new disaster victim to the system.
     */
    public boolean addDisasterVictim(String firstName, String lastName, String dob, Gender gender, String comments, FamilyGroup family) {
        if (firstName == null || lastName == null || dob == null || gender == null) {
            return false;
        }
    
        DisasterVictim victim = new DisasterVictim(firstName, LocalDate.now().toString());
        victim.setLastName(lastName);
        victim.setDateOfBirth(dob);
        victim.setGender(gender);
        victim.setComments(comments);
        if (family != null) {
            family.addFamilyMember(victim);
            victim.setFamily(family);
        }
    
        if (checkPersonExists(victim)) return false;
    
        disastervictims.add(victim);
        model.insertDisasterVictim(victim);
        return true;
    }
    
    

    
    /**
    Adds a new inquirer to the system.
     */
    public boolean addInquirer(String firstName, String lastName, String phoneNumber, FamilyGroup family) {
        if (firstName == null || !firstName.matches("[a-zA-Z]+")) return false;
        if (lastName == null || !lastName.matches("[a-zA-Z]+")) return false;
        if (phoneNumber == null || !phoneNumber.matches("[0-9\\-]+")) return false;

        Inquirer newInquirer = new Inquirer(firstName, lastName, phoneNumber);
        newInquirer.setId();

        if (family != null) {
            newInquirer.setFamily(family);
            family.addFamilyMember(newInquirer);
        }


        inquirers.add(newInquirer);
        model.insertInquirer(newInquirer);
        return true;
    }

    
    /**
    Adds a new medical record for a disaster victim.
     */
    public boolean addMedicalRecordToVictim(int victimIndex, String treatmentDetails, String dateOfTreatment) {
        try {
            DisasterVictim victim = disastervictims.get(victimIndex);
            int victimId = victim.getId();
    
            Location location = null;
            for (Location loc : locations) {
                if (loc.getOccupants().contains(victim)) {
                    location = loc;
                    break;
                }
            }
    
            if (location == null) {
                return false; // Not allocated
            }
    
            int locationId = location.getId();
    
            MedicalRecord record = new MedicalRecord(location, treatmentDetails, dateOfTreatment);
            record.setId();
            victim.addMedicalRecord(record);
            model.addMedicalRecord(record, locationId, victimId);
    
            return true;
        } 
        catch (Exception e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
            return false;
        }
    }
    
    
    /**
    Adds a new Blanket item to the system and allocates it to a location or person.
     */
    public boolean addBlanket(boolean toLocation, int index) {
        try {
            Blanket blanket;
            if (toLocation) {
                Location location = locations.get(index);
                blanket = new Blanket(location);
                blanket.setId();
                location.addSupply(blanket);
                model.allocateInventoryToLocation(blanket.getId(), location.getId());
            } else {
                DisasterVictim victim = disastervictims.get(index);
                blanket = new Blanket(victim);
                blanket.setId();
                victim.addBelongings(blanket);
                model.allocateInventoryToPerson(blanket.getId(), victim.getId());
            }
            supply.add(blanket);
            model.addNewSupply("blanket", null);
            return true;
        } 
        catch (Exception e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
            return false;
        }
    }

    public boolean addCot(boolean toLocation, int index, int roomNum, String gridLoc) {
        try {
            Cot cot;
            String comments = roomNum + " " + gridLoc;
    
            if (toLocation) {
                Location location = locations.get(index);
                cot = new Cot(roomNum, gridLoc, location);
                cot.setId();
                location.addSupply(cot);
                model.allocateInventoryToLocation(cot.getId(), location.getId());
            } else {
                DisasterVictim victim = disastervictims.get(index);
                cot = new Cot(roomNum, gridLoc, victim);
                cot.setId();
                victim.addBelongings(cot);
                model.allocateInventoryToPerson(cot.getId(), victim.getId());
            }
    
            supply.add(cot);
            model.addNewSupply("cot", comments);
            return true;
        } 
        catch (Exception e) {
            ErrorLog error = new ErrorLog(e);
            return false;
        }
    }

    public boolean addPersonalBelongings(String description, int victimIndex) {
        try {
            DisasterVictim victim = disastervictims.get(victimIndex);
            PersonalBelongings belongings = new PersonalBelongings(description, victim);
            belongings.setId();
            victim.addBelongings(belongings);
            supply.add(belongings);
            model.addNewSupply("personal item", description);
            model.allocateInventoryToPerson(belongings.getId(), victim.getId());
            return true;
        } 
        catch (Exception e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
            return false;
        }
    }
    
    
    public boolean addWater(boolean toLocation, int index) {
        try {
            Water water;
    
            if (toLocation) {
                Location location = locations.get(index);
                water = new Water(location);
                water.setId();
                location.addSupply(water);
                model.allocateInventoryToLocation(water.getId(), location.getId());
            } else {
                DisasterVictim victim = disastervictims.get(index);
                water = new Water(victim);
                water.setId();
                victim.addBelongings(water);
                model.allocateInventoryToPerson(water.getId(), victim.getId());
            }
    
            supply.add(water);
            model.addNewSupply("water", null);
            return true;
        } 
        catch (Exception e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
            return false;
        }
    }
    
    
    /**
    Logs a new inquiry in the system.
     */
    public void logInquiry(Person loggedBy, DisasterVictim missingPerson, Location location, String comments) {
        try {
            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
            String date = timestamp.toLocalDateTime().toLocalDate().toString();
    
            model.logInquiry(loggedBy.getId(), missingPerson.getId(), location.getId(), timestamp, comments);
            ReliefService inquiry = new ReliefService(loggedBy, missingPerson, date, comments, location);
            inquiries.add(inquiry);
        } 
        catch (Exception e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);        }
    }
    
        
    
    /**
    Updates the details of an existing disaster victim.
     */
    public void updateDisasterVictim(DisasterVictim victim, Gender newGender, String newComments, FamilyGroup newFamily) {
        if (newGender != null) {
            victim.setGender(newGender);
        }
    
        if (newComments != null && !newComments.isEmpty()) {
            victim.setComments(newComments);
        }
    
        // Family reassignment
        FamilyGroup oldFamily = victim.getFamily();
        if (oldFamily != null && oldFamily != newFamily) {
            oldFamily.getFamilyMembers().remove(victim);
            isFamilyEmpty();
        }
    
        if (newFamily != null) {
            newFamily.addFamilyMember(victim);
            victim.setFamily(newFamily);
        } else if (oldFamily != null) {
            victim.setFamily(null);
            isFamilyEmpty();
        }
    
        model.updateDisasterVictim(victim);
    }
    
    
    /**
    Updates the details of an existing inquirer.
     */
    public void updateInquirer(Inquirer inquirer, String newPhone, FamilyGroup newFamily) {
        if (newPhone != null && !newPhone.isEmpty()) {
            inquirer.setPhone(newPhone);
        }
    
        FamilyGroup oldFamily = inquirer.getFamily();
        if (oldFamily != null && oldFamily != newFamily) {
            oldFamily.getFamilyMembers().remove(inquirer);
            isFamilyEmpty();
        }
    
        if (newFamily != null) {
            newFamily.addFamilyMember(inquirer);
            inquirer.setFamily(newFamily);
        } else if (oldFamily != null) {
            inquirer.setFamily(null);
            isFamilyEmpty();
        }
    
        model.updateInquirer(inquirer);
    }
    
    
    /**
    Updates an existing medical record for a disaster victim.
     */
    public boolean updateMedicalRecord(MedicalRecord record, String newDetails, String newDate) {
        if (record == null || newDetails == null || newDetails.isEmpty() || !isValidDateFormat(newDate)) {
            return false;
        }
    
        record.setTreatmentDetails(newDetails);
        record.setDateOfTreatment(newDate);
        model.updateMedicalRecord(record, record.getId());
        return true;
    }
    
    
    /**
    Updates an existing inquiry in the system.
     */
    public void updateInquiry(ReliefService inquiry, String newComments) {
        if (newComments != null && !newComments.isEmpty()) {
            inquiry.setInfoProvided(newComments);
            model.updateInquiry(inquiry, inquiry.getId());
        }
    }
    
    /**
    Allocates inventory to a location.
     */
    public boolean allocateInventoryToLocation(InventoryItem item, Location location) {
        if (item == null || location == null || item.getItemType() == ItemType.PERSONALBELONGINGS) {
            return false;
        }
    
        if (item.getAllocatedToPerson() != null) {
            item.getAllocatedToPerson().removeBelongings(item);
            item.setAllocatedToPerson(null);
        }
    
        if (item.getAllocatedToLocation() != null) {
            item.getAllocatedToLocation().removeSupply(item);
            item.setAllocatedToLocation(null);
        }
    
        location.addSupply(item);
        item.setAllocatedToLocation(location);
    
        model.updateSupplyToLocation(item.getId(), location.getId());
        return true;
    }
    
    
    /**
    Allocates inventory to a disaster victim.
     */
    public boolean allocateInventoryToPerson(DisasterVictim victim, InventoryItem item) {
        if (victim == null || item == null) return false;
    
        if (item.getAllocatedToLocation() != null) {
            item.getAllocatedToLocation().removeSupply(item);
            item.setAllocatedToLocation(null);
        }
    
        item.setAllocatedToPerson(victim);
        victim.addBelongings(item);
    
        model.removeSupplyAllocation(item.getId());
        model.allocateInventoryToPerson(item.getId(), victim.getId());
    
        return true;
    }
    
    
    /**
    Displays a list of all disaster victims.
     */
    public void viewDisasterVictims(){
        for (int i = 0; i < disastervictims.size(); i++) {
            DisasterVictim v = disastervictims.get(i);
            System.out.println(i + ": " + languageManager.getTranslation("Name") + " " + v.getFirstName() + " "  + v.getLastName() + " | " + languageManager.getTranslation("DateOfBirth")  + ": " + v.getDateOfBirth());
        }
    }

    /**
    Displays a list of all inquirers.
     */
    public void viewInquirers(){
        for(int i = 0; i < inquirers.size(); i++){
            Inquirer in = inquirers.get(i);
            System.out.println(i + ": " + languageManager.getTranslation("Name") + ": " + in.getFirstName() + " " + in.getLastName() + " | " + languageManager.getTranslation("PhoneNumber") + ": "  + in.getPhone());
        }

    }

    /**
    Displays a list of all locations.
     */
    public void viewLocations(){
        for (int i = 0; i < locations.size(); i++) {
            Location loc = locations.get(i);
            System.out.println(i + ": " + languageManager.getTranslation("LocationName") + ": " + loc.getName());
        }
    }

    /**
    Displays medical records for a selected disaster victim.
     */
    public void viewMedicalRecords() {
        System.out.println(languageManager.getTranslation("MedicalRecordView"));
    
        boolean foundAny = false;
        for (DisasterVictim victim : disastervictims) {
            ArrayList<MedicalRecord> medicalRecords = victim.getMedicalRecords();
            if (medicalRecords != null && !medicalRecords.isEmpty()) {
                for (int i = 0; i < medicalRecords.size(); i++) {
                    MedicalRecord med = medicalRecords.get(i);
                    System.out.println(languageManager.getTranslation("Name") + ": " + victim.getFirstName() + " " + victim.getLastName()+ " | " + languageManager.getTranslation("TreatmentDetails") + ": " + med.getTreatmentDetails()+ " | " + languageManager.getTranslation("dateOfTreatment") + ": " + med.getDateOfTreatment());
                    foundAny = true;
                }
            }
        }
    
        if (!foundAny) {
            System.out.println(languageManager.getTranslation("NoMedicalRecordsFound"));
        }
    }
    
    /**
    Displays a list of all inquiries.
     */
    public void viewInquiries(){
        for(int i = 0; i < inquiries.size(); i++){
            ReliefService inquiry = inquiries.get(i);
            System.out.println(i + ": " + languageManager.getTranslation("Inquirer") + ": " + inquiry.getInquirer().getFirstName() + " " + inquiry.getInquirer().getLastName() + " | " + languageManager.getTranslation("MissingPerson") + ": " + inquiry.getMissingPerson().getFirstName() + " " + inquiry.getMissingPerson().getLastName() + " | " + languageManager.getTranslation("Infoed") + ": " + inquiry.getInfoProvided());
    }
}

    /**
    Displays a list of all inventory items.
     */
    public void viewInventory(){
        for(int i = 0; i < supply.size(); i++){
            InventoryItem item = supply.get(i);
            System.out.println(i + ": " + languageManager.getTranslation("ItemType") + ": " + item.getItemType());
        }
    }

    /**
    Displays a list of all family groups and their members.
     */
    public void viewFamilies() {
        for (int i =0; i < familyGroups.size(); i++) {
            System.out.println(languageManager.getTranslation("FamilyID") + ": " + i);
    
            for (Person member : familyGroups.get(i).getFamilyMembers()){
                System.out.println("  - " + languageManager.getTranslation("Name") + ": " + member.getFirstName() + " " + member.getLastName());
            }
    
            System.out.println(); 
        }
    }
    
    /**
    Checks if a disaster victim already exists in the system.
    @param victim DisasterVictim object to check.
    @return true if the victim exists, false otherwise.
     */
    private static boolean checkPersonExists(DisasterVictim victim){
        for(DisasterVictim v: disastervictims){
            if(v.getFirstName().equalsIgnoreCase(victim.getFirstName()) && v.getLastName().equalsIgnoreCase(victim.getLastName()) && v.getDateOfBirth().equals(victim.getDateOfBirth())){
                return true;
            }
        }
        return false;

    }

   
    /**
    Validates a date string format (YYYY-MM-DD).
    @param date The date string to validate.
    @return true if the date format is valid, false otherwise.
     */
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

    /**
    Removes empty family groups from the system.
     */
    private static void isFamilyEmpty() {
        Iterator<FamilyGroup> iterator = familyGroups.iterator();
        while (iterator.hasNext()) {
            FamilyGroup f = iterator.next();
            if (f.getFamilyMembers().isEmpty()) {
                iterator.remove(); 
            }
        }
    }

    public ArrayList<DisasterVictim> getDisasterVictims() {
        return disastervictims;
    }
    
    public ArrayList<Inquirer> getInquirers() {
        return inquirers;
    }
    
    public ArrayList<Location> getLocations() {
        return locations;
    }
    
    public ArrayList<ReliefService> getInquiries() {
        return inquiries;
    }
    
    public ArrayList<InventoryItem> getSupply() {
        return supply;
    }
    
    public ArrayList<FamilyGroup> getFamilyGroups() {
        return familyGroups;
    }
    
}
