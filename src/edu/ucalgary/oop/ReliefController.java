package edu.ucalgary.oop;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
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
    private static Scanner scanner;
    private static LanguageManager languageManager;

    /**
    @param scanner Scanner object for user input.
    @param model DBAccess object for database operations.
    @param languageManager LanguageManager object for translations.
     */
    public ReliefController(Scanner scanner, DBAccess model, LanguageManager languageManager){
        this.scanner = scanner;
        this.model = model;
        this.languageManager = languageManager;
        this.StartUp();
    }

    /**
    Initializes the application by loading data from the database.
     */
    public void StartUp(){
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

    /**
    Allocates a disaster victim to a location.
     */
    public void allocateVictimToLocation() {
        viewDisasterVictims();
        int victimIndex = getValidatedIndex(disastervictims.size());
        if (victimIndex == -1) return;
    
        DisasterVictim victim = disastervictims.get(victimIndex);
    
        viewLocations();
        int locationIndex = getValidatedIndex(locations.size());
        if (locationIndex == -1) return;
    
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
    
        System.out.println(languageManager.getTranslation("VictimSuccessfullyAllocated"));
    }
    
    /**
    Adds a new disaster victim to the system.
     */
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
    
            System.out.println(languageManager.getTranslation("EnterGender"));
            System.out.println("0. " + languageManager.getTranslation("GenderMale"));
            System.out.println("1. " + languageManager.getTranslation("GenderFemale"));
            System.out.println("2. " + languageManager.getTranslation("GenderNonBinary"));
            int genderChoice = -1;
            while (genderChoice < 0 || genderChoice > 2) {
                try {
                    System.out.print(languageManager.getTranslation("EnterChoice") + " ");
                    genderChoice = Integer.parseInt(scanner.nextLine().trim());
                    if (genderChoice < 0 || genderChoice > 2) {
                        System.out.println(languageManager.getTranslation("InvalidInputNumber"));
                    }
                } 
                catch (Exception e) {
                    ErrorLog error = new ErrorLog(e);
                    System.out.println(languageManager.getTranslation("UnexpectedError"));
                    System.exit(1);
                }
            }

            Gender[] genders = Gender.values();
            Gender gender = genders[genderChoice];
            
            System.out.println(languageManager.getTranslation("EnterComments"));
            String comments = scanner.nextLine().trim();
    
            String entryDate = java.time.LocalDate.now().toString();
            DisasterVictim newVictim = new DisasterVictim(firstName, entryDate);
            newVictim.setLastName(lastName);
            newVictim.setDateOfBirth(dob);
            newVictim.setGender(gender);
            newVictim.setComments(comments);
            newVictim.setId();
    
            viewFamilies();
            System.out.println(String.format(languageManager.getTranslation("EnterFamilyGroupOrSkip"), familyGroups.size()));
            Integer familyIndex = getValidatedIndex(familyGroups.size() + 1);
            FamilyGroup family = null;
            if (familyIndex == familyGroups.size()) {
                family = new FamilyGroup();
                family.setId();
                family.addFamilyMember(newVictim);
                newVictim.setFamily(family);
                familyGroups.add(family);
            } 
            else if (familyIndex != -1) {
                family = familyGroups.get(familyIndex);
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
        catch (Exception e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }
    }
    
    /**
    Adds a new inquirer to the system.
     */
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
            FamilyGroup family = null;
            System.out.println(String.format(languageManager.getTranslation("EnterFamilyGroupOrSkip"), familyGroups.size()));
            Integer familyIndex = getValidatedIndex(familyGroups.size() + 1);

            if (familyIndex == familyGroups.size()) {
                family = new FamilyGroup();
                family.setId();
                familyGroups.add(family);
            } 
            else if (familyIndex != -1) {
                family = familyGroups.get(familyIndex);
            }

            
    
            Inquirer newInquirer = new Inquirer(firstName, lastName, phoneNumber);
            if (family != null) {
                newInquirer.setFamily(family);
                family.addFamilyMember(newInquirer);
            }
            newInquirer.setId();
    
            inquirers.add(newInquirer);
            model.insertInquirer(newInquirer);
    
            System.out.println(languageManager.getTranslation("InquirerAddedSuccess"));
        } 
        catch (Exception e) {
            ErrorLog error = new ErrorLog(e); 
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1); 
        }
    }
    
    /**
    Adds a new medical record for a disaster victim.
     */
    public void addNewMedicalRecord() {
        try {
            System.out.println(languageManager.getTranslation("MedicalRecordSelectVictim"));
            viewDisasterVictims();
            int victimIndex = getValidatedIndex(disastervictims.size());
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
                System.out.println(languageManager.getTranslation("VictimNotAllocatedToLocation"));
                return;
            }
    
            int locationId = location.getId();
    
            System.out.println(languageManager.getTranslation("EnterTreatmentDetails"));
            String treatmentDetails = scanner.nextLine().trim();
            if (treatmentDetails.isEmpty()) {
                System.out.println(languageManager.getTranslation("InvalidTreatmentDetails"));
                return;
            }
    
            System.out.println(languageManager.getTranslation("EnterDateOfTreatment"));
            String dateOfTreatment = scanner.nextLine().trim();
            if (!isValidDateFormat(dateOfTreatment)) {
                System.out.println(languageManager.getTranslation("InvalidDateFormat"));
                return;
            }
    
            MedicalRecord record = new MedicalRecord(location, treatmentDetails, dateOfTreatment);
            record.setId();
            victim.addMedicalRecord(record);
            model.addMedicalRecord(record, locationId, victimId);
            System.out.println(languageManager.getTranslation("MedicalRecordAddedSuccess"));
    
        } 
        catch (Exception e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }
    }
    
    /**
    Adds a new supply item to the system and allocates it to a location or person.
     */
    public void addNewSupply() {
        try {
            System.out.println(languageManager.getTranslation("SelectItem"));
            System.out.println("0. " + languageManager.getTranslation("SupplyBlanket"));
            System.out.println("1. " + languageManager.getTranslation("SupplyCot"));
            System.out.println("2. " + languageManager.getTranslation("SupplyBelongings"));
            System.out.println("3. " + languageManager.getTranslation("SupplyWater"));
            int itemTypeIndex = getValidatedIndex(4);
            if (itemTypeIndex == -1) return;
    
            String comments = null;

                
            switch (itemTypeIndex) {
                case 0: // Blanket
                System.out.println(languageManager.getTranslation("LocationOrPerson"));
                    int locOrPerson0 = getValidatedIndex(2);
                    if (locOrPerson0 == -1) return;
    
                    if (locOrPerson0 == 0) {
                        viewLocations();
                        int locationIndex = getValidatedIndex(locations.size());
                        if (locationIndex == -1) return;
                        Location location = locations.get(locationIndex);
    
                        Blanket blanket = new Blanket(location);
                        blanket.setId();
                        location.addSupply(blanket);
                        supply.add(blanket);
                        model.addNewSupply("blanket", comments);
                        model.allocateInventoryToLocation(blanket.getId(), location.getId());
                        System.out.println(languageManager.getTranslation("SupplyAddedSuccess"));
                    } 
                    else {
                        viewDisasterVictims();
                        int victimIndex = getValidatedIndex(disastervictims.size());
                        if (victimIndex == -1) return;
                        DisasterVictim victim = disastervictims.get(victimIndex);
    
                        Blanket blanket = new Blanket(victim);
                        blanket.setId();
                        victim.addBelongings(blanket);
                        supply.add(blanket);
                        model.addNewSupply("blanket", comments);
                        model.allocateInventoryToPerson(blanket.getId(), victim.getId());
                        System.out.println(languageManager.getTranslation("SupplyAddedSuccess"));
                    }
                    break;
    
                case 1: // Cot
                    System.out.println(languageManager.getTranslation("EnterGridLocation"));
                    String gridLocation = scanner.nextLine().trim();
                    if (!gridLocation.matches("^[A-Za-z]{1}\\d+$")) {
                        System.out.println(languageManager.getTranslation("InvalidGrid"));
                        return;
                    }
    
                    System.out.println(languageManager.getTranslation("EnterRoomNumber"));
                    String roomNumStr = scanner.nextLine().trim();
                    if (!roomNumStr.matches("^\\d{3}$")) {
                        System.out.println(languageManager.getTranslation("InvalidRoomNumber"));
                        return;
                    }
    
                    int roomNum = Integer.parseInt(roomNumStr);
    
                    System.out.println(languageManager.getTranslation("LocationOrPerson"));
                    int locOrPerson1 = getValidatedIndex(2);
                    if (locOrPerson1 == -1) return;
    
                    if (locOrPerson1 == 0) {
                        viewLocations();
                        int locationIndex = getValidatedIndex(locations.size());
                        if (locationIndex == -1) return;
                        Location location = locations.get(locationIndex);
    
                        Cot cot = new Cot(roomNum, gridLocation, location);
                        cot.setId();
                        comments = roomNum + " " + gridLocation;
                        location.addSupply(cot);
                        supply.add(cot);
                        model.addNewSupply("cot", comments);
                        model.allocateInventoryToLocation(cot.getId(), location.getId());
                        System.out.println(languageManager.getTranslation("SupplyAddedSuccess"));
                    } 
                    else {
                        viewDisasterVictims();
                        int victimIndex = getValidatedIndex(disastervictims.size());
                        if (victimIndex == -1) return;
                        DisasterVictim victim = disastervictims.get(victimIndex);
    
                        Cot cot = new Cot(roomNum, gridLocation, victim);
                        comments = roomNum + " " + gridLocation;
                        cot.setId();
                        victim.addBelongings(cot);
                        supply.add(cot);
                        model.addNewSupply("cot", comments);
                        model.allocateInventoryToPerson(cot.getId(), victim.getId());
                        System.out.println(languageManager.getTranslation("SupplyAddedSuccess"));
                    }
                    break;
    
                case 2: // Personal Belongings
                    System.out.println(languageManager.getTranslation("EnterPersonalDescription"));
                    comments = scanner.nextLine().trim();
                    if (comments.isEmpty()) {
                        System.out.println(languageManager.getTranslation("InvalidTreatmentDetails"));
                        return;
                    }
    
                    System.out.println(languageManager.getTranslation("PersonalVictim"));
                    viewDisasterVictims();
                    int victimIndex2 = getValidatedIndex(disastervictims.size());
                    if (victimIndex2 == -1) return;
                    DisasterVictim victim2 = disastervictims.get(victimIndex2);
    
                    PersonalBelongings belongings = new PersonalBelongings(comments, victim2);
                    belongings.setId();
                    victim2.addBelongings(belongings);
                    supply.add(belongings);
                    model.addNewSupply("personal item", comments);
                    model.allocateInventoryToPerson(belongings.getId(), victim2.getId());
                    System.out.println(languageManager.getTranslation("SupplyAddedSuccess"));
                    break;
    
                case 3: // Water
                System.out.println(languageManager.getTranslation("LocationOrPerson"));
                    int locOrPerson3 = getValidatedIndex(2);
                    if (locOrPerson3 == -1) return;
    
                    if (locOrPerson3 == 0) {
                        viewLocations();
                        int locationIndex = getValidatedIndex(locations.size());
                        if (locationIndex == -1) return;
                        Location location = locations.get(locationIndex);
    
                        Water water = new Water(location);
                        water.setId();
                        location.addSupply(water);
                        supply.add(water);
                        model.addNewSupply("water", comments);
                        model.allocateInventoryToLocation(water.getId(), location.getId());
                        System.out.println(languageManager.getTranslation("SupplyAddedSuccess"));
                    } 
                    else {
                        viewDisasterVictims();
                        int victimIndex = getValidatedIndex(disastervictims.size());
                        if (victimIndex == -1) return;
                        DisasterVictim victim = disastervictims.get(victimIndex);
    
                        Water water = new Water(victim);
                        water.setId();
                        victim.addBelongings(water);
                        supply.add(water);
                        model.addNewSupply("water", comments);
                        model.allocateInventoryToPerson(water.getId(), victim.getId());
                        System.out.println(languageManager.getTranslation("SupplyAddedSuccess"));
                    }
                    break;
    
                default:
                System.out.println(languageManager.getTranslation("InvalidInputNumber"));
            }
    
        } 
        catch (Exception e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }
    }
    
    /**
    Logs a new inquiry in the system.
     */
    public void logInquiry() {
        System.out.println(languageManager.getTranslation("WhoIsLoggingInquiry"));
        System.out.println("1. " + languageManager.getTranslation("Inquirer"));
        System.out.println("2. " + languageManager.getTranslation("DisasterVictim"));
        System.out.print(languageManager.getTranslation("EnterChoice") + ": ");
    
        String typeChoice = scanner.nextLine().trim();
    
        int personId = 0;
        boolean isInquirer = true;
        Person loggedBy = null;
    
        if (typeChoice.equals("1")) {
            System.out.println(languageManager.getTranslation("SelectInquirer"));
            viewInquirers();
            int inquirerIndex = getValidatedIndex(inquirers.size());
            if (inquirerIndex == -1) return;
            Inquirer inquirer = inquirers.get(inquirerIndex);
            personId = inquirer.getId();
            isInquirer = true;
            loggedBy = inquirer;
    
        } 
        else if (typeChoice.equals("2")) {
            System.out.println(languageManager.getTranslation("SelectDisasterVictim"));
            viewDisasterVictims();
            int victimIndex = getValidatedIndex(disastervictims.size());
            if (victimIndex == -1) return;
            DisasterVictim victim = disastervictims.get(victimIndex);
            personId = victim.getId();
            isInquirer = false;
            loggedBy = victim;
        } 
        else {
            System.out.println(languageManager.getTranslation("InvalidInputNumber"));
            return;
        }
    
        System.out.println(languageManager.getTranslation("SelectMissingPerson"));
        viewDisasterVictims();
        int missingIndex = getValidatedIndex(disastervictims.size());
        if (missingIndex == -1) return;
        DisasterVictim missingPerson = disastervictims.get(missingIndex);
    
        System.out.println(languageManager.getTranslation("SelectLocation"));
        viewLocations();
        int locationIndex = getValidatedIndex(locations.size());
        if (locationIndex == -1) return;
        Location location = locations.get(locationIndex);
    
        System.out.println(languageManager.getTranslation("EnterComments"));
        String comments = scanner.nextLine().trim();
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());    
        String fullString = timestamp.toString();  

        String[] parts = fullString.split(" ");  
        String date = parts[0];              

        model.logInquiry(personId, missingPerson.getId(), location.getId(), timestamp, comments);
        ReliefService inquiry = new ReliefService(loggedBy, missingPerson, date, comments, location);
        inquiries.add(inquiry);
        
    
        System.out.println(languageManager.getTranslation("InquiryLoggedSuccessfully"));
    }
    
    /**
    Updates the details of an existing disaster victim.
     */
    public void updateDisasterVictim() {
        viewDisasterVictims(); 
        int index = getValidatedIndex(disastervictims.size());
        if (index == -1) return;
    
        DisasterVictim victim = disastervictims.get(index);
    
        System.out.println(languageManager.getTranslation("EnterNewGender") + " (" + victim.getGender() + "):");
        System.out.println("0. " + languageManager.getTranslation("GenderMale"));
        System.out.println("1. " + languageManager.getTranslation("GenderFemale"));
        System.out.println("2. " + languageManager.getTranslation("GenderNonBinary"));
        int genderChoice = getValidatedIndex(3);
        if (genderChoice != -1) {
            Gender[] genders = Gender.values();
            victim.setGender(genders[genderChoice]);
        }
    
        System.out.println(languageManager.getTranslation("EnterNewComments") + " (" + victim.getComments() + "):");
        String commentInput = scanner.nextLine().trim();
        if (!commentInput.isEmpty()) {
            victim.setComments(commentInput);
        }

        viewFamilies();
        System.out.println(String.format(languageManager.getTranslation("EnterFamilyGroupOrSkip"), familyGroups.size()));
        int famIndex = getValidatedIndex(familyGroups.size() + 1);
    
        if (famIndex == familyGroups.size()) {
            FamilyGroup newFamily = new FamilyGroup();
            newFamily.setId(); 
            familyGroups.add(newFamily);
    
            if (victim.getFamily() != null) {
                victim.getFamily().getFamilyMembers().remove(victim);
                isFamilyEmpty();
            }
    
            newFamily.addFamilyMember(victim);
            victim.setFamily(newFamily);
        } 
        else if (famIndex != -1) {
            FamilyGroup newFam = familyGroups.get(famIndex);
    
            if (victim.getFamily() != null && victim.getFamily() != newFam) {
                victim.getFamily().getFamilyMembers().remove(victim);
                isFamilyEmpty();
            }
    
            newFam.addFamilyMember(victim);
            victim.setFamily(newFam);
        } 
        else {
            if (victim.getFamily() != null) {
                victim.getFamily().getFamilyMembers().remove(victim);
                victim.setFamily(null);
                isFamilyEmpty();
            }
        }
    
        model.updateDisasterVictim(victim);
        System.out.println(languageManager.getTranslation("VictimSuccessfullyUpdated"));
    }
    
    /**
    Updates the details of an existing inquirer.
     */
    public void updateInquirer() {
        viewInquirers();
        int index = getValidatedIndex(inquirers.size());
        if (index == -1) return;
    
        Inquirer inquirer = inquirers.get(index);
    
        System.out.println(languageManager.getTranslation("EnterNewPhone"));
        String phone = scanner.nextLine().trim();
        if (phone.isEmpty() || !phone.matches("[0-9\\-]+")) {
            System.out.println(languageManager.getTranslation("InvalidPhoneNumber"));
            return;
        }
        inquirer.setPhone(phone);
    
        viewFamilies();
        System.out.println(String.format(languageManager.getTranslation("EnterFamilyGroupOrSkip"), familyGroups.size()));
    
        int famIndex = getValidatedIndex(familyGroups.size() + 1);
    
        if (famIndex == familyGroups.size()) {
            FamilyGroup newFamily = new FamilyGroup();
            newFamily.setId(); 
            familyGroups.add(newFamily);
    
            if (inquirer.getFamily() != null) {
                inquirer.getFamily().getFamilyMembers().remove(inquirer);
                isFamilyEmpty();
            }
    
            newFamily.addFamilyMember(inquirer);
            inquirer.setFamily(newFamily);
        } 
        else if (famIndex != -1) {
            FamilyGroup newFam = familyGroups.get(famIndex);
    
            if (inquirer.getFamily() != null && inquirer.getFamily() != newFam) {
                inquirer.getFamily().getFamilyMembers().remove(inquirer);
                isFamilyEmpty();
            }
    
            newFam.addFamilyMember(inquirer);
            inquirer.setFamily(newFam);
        } 
        else {
            if (inquirer.getFamily() != null) {
                inquirer.getFamily().getFamilyMembers().remove(inquirer);
                inquirer.setFamily(null);
                isFamilyEmpty();
            }
        }
    
        model.updateInquirer(inquirer);
        System.out.println(languageManager.getTranslation("InquirerUpdated"));
    }
    
    /**
    Updates an existing medical record for a disaster victim.
     */
    public void updateMedicalRecord() {
        ArrayList<DisasterVictim> victimsWithRecords = new ArrayList<>();
        for (DisasterVictim victim : disastervictims) {
            if (victim.getMedicalRecords() != null && !victim.getMedicalRecords().isEmpty()) {
                victimsWithRecords.add(victim);
            }
        }
    
        for (int i = 0; i < victimsWithRecords.size(); i++) {
            DisasterVictim v = victimsWithRecords.get(i);
            System.out.println(i + ": " + languageManager.getTranslation("Name") + " " + v.getFirstName() + " " + v.getLastName() + " | " + languageManager.getTranslation("DateOfBirth") + ": " + v.getDateOfBirth());
        }
    
        int victimIndex = getValidatedIndex(victimsWithRecords.size());
        if (victimIndex == -1) return;
    
        DisasterVictim selectedVictim = victimsWithRecords.get(victimIndex);
        ArrayList<MedicalRecord> records = selectedVictim.getMedicalRecords();
    
        for (int i = 0; i < records.size(); i++) {
            System.out.println(i + ": " + records.get(i).getTreatmentDetails());
        }
    
        int recordIndex = getValidatedIndex(records.size());
        if (recordIndex == -1) return;
    
        MedicalRecord record = records.get(recordIndex);
    
        System.out.println(languageManager.getTranslation("EnterNewTreatment"));
        String newDetails = scanner.nextLine().trim();
        if (newDetails.isEmpty()) return;
    
        System.out.println(languageManager.getTranslation("EnterNewDate"));
        String newDate = scanner.nextLine().trim();
        if (!isValidDateFormat(newDate)) return;
    
        record.setTreatmentDetails(newDetails);
        record.setDateOfTreatment(newDate);
    
        model.updateMedicalRecord(record, record.getId());
        System.out.println(languageManager.getTranslation("MedicalRecordUpdated"));
    }
    
    /**
    Updates an existing inquiry in the system.
     */
    public void updateInquiry() {
        viewInquiries();
        int index = getValidatedIndex(inquiries.size());
        if (index == -1) return;

        ReliefService inquiry = inquiries.get(index);

        System.out.println(languageManager.getTranslation("EnterNewInquiryComments"));
        String comments = scanner.nextLine().trim();
        if (comments.isEmpty()) return;
        inquiry.setInfoProvided(comments);

        model.updateInquiry(inquiry, inquiry.getId());
        System.out.println(languageManager.getTranslation("InquiryUpdated"));
    }

    /**
    Allocates inventory to a location.
     */
    public void allocateInventoryToLocation() {
        viewLocations();
        int locationIndex = getValidatedIndex(locations.size());
        if (locationIndex == -1) return;
    
        Location location = locations.get(locationIndex);
    
        ArrayList<InventoryItem> allocatableInventory = new ArrayList<>();
        for (InventoryItem item : supply) {
            if (item.getItemType() != ItemType.PERSONALBELONGINGS) {
                allocatableInventory.add(item);
            }
        }
    
        if (allocatableInventory.isEmpty()) {
            System.out.println(languageManager.getTranslation("NoUnallocatedInventory"));
            return;
        }
    
        for (int i = 0; i < allocatableInventory.size(); i++) {
            System.out.println(i + ": " + allocatableInventory.get(i).getItemType());
        }
    
        int invIndex = getValidatedIndex(allocatableInventory.size());
        if (invIndex == -1) return;
    
        InventoryItem selectedItem = allocatableInventory.get(invIndex);
    
        if (selectedItem.getAllocatedToPerson() != null) {
            selectedItem.getAllocatedToPerson().removeBelongings(selectedItem);
            selectedItem.setAllocatedToPerson(null);
        }
        if (selectedItem.getAllocatedToLocation() != null) {
            selectedItem.getAllocatedToLocation().removeSupply(selectedItem);
            selectedItem.setAllocatedToLocation(null);
        }
    
        location.addSupply(selectedItem);
        selectedItem.setAllocatedToLocation(location);
    
        model.updateSupplyToLocation(selectedItem.getId(), location.getId());
    
        System.out.println(languageManager.getTranslation("InventoryAllocatedSuccessfully"));
    }
    
    /**
    Allocates inventory to a disaster victim.
     */
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
        for (InventoryItem item : supply) {
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
        selectedItem.setAllocatedToPerson(victim);
        victim.addBelongings(selectedItem);
        model.removeSupplyAllocation(selectedItem.getId());
        model.allocateInventoryToPerson(selectedItem.getId(), victim.getId());
    
        System.out.println(languageManager.getTranslation("InventoryAllocatedSuccessfully"));
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
    public void viewMedicalRecords(){
        System.out.println(languageManager.getTranslation("MedicalRecordView"));
        viewDisasterVictims();
        int victimIndex = getValidatedIndex(disastervictims.size());
        DisasterVictim victim = disastervictims.get(victimIndex);
        ArrayList<MedicalRecord> medicalRecords = victim.getMedicalRecords();
        for(int i = 0; i < medicalRecords.size(); i++){
            MedicalRecord med = medicalRecords.get(i);
            System.out.println(i + ": " + languageManager.getTranslation("TreatmentDetails") + ": " + med.getTreatmentDetails() + " | " + languageManager.getTranslation("dateOfTreatment") + ": " + med.getDateOfTreatment());
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
    alidates and retrieves a user input index within a specified range.
    @param upperBound The upper bound for the index.
    @return The validated index, or -1 if invalid.
     */
    private int getValidatedIndex(int upperBound) {
        int index = -1;
    
        while (true) {
            System.out.print(languageManager.getTranslation("EnterValidIndex") + ": ");
            String input = scanner.nextLine().trim();
    
            if (input.isEmpty()) {
                return -1;
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

    /**
    Validates a name string.
    @param name The name string to validate.
    @return true if the name is valid, false otherwise.
     */
    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z\\-\\s]+"); 
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
    
    
    
}
