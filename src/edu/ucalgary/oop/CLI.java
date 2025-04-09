package edu.ucalgary.oop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 @author Landon Reed
 @version 1.0
 Created on: April 5, 2025
 */
public class CLI {
    private ReliefController controller;
    private Scanner scanner;
    private LanguageManager languageManager;
    private ArrayList<String> languageCodes = new ArrayList<>(Arrays.asList("en-CA", "fr-CA"));

    /**
    Constructs a CLI object, initializes the language manager, database manager, 
    and the relief controller. Prompts the user to select a language.
     */
    public CLI() {
        this.scanner = new Scanner(System.in);
        System.out.println("Available Languages: ");
        ArrayList<String> langs = languageCodes;
        for (String code : langs) {
            System.out.println("- " + code);
        }

        System.out.print("Enter language code (default is en-CA): ");
        String userLangCode = scanner.nextLine().trim();

        if (userLangCode.isEmpty() || !langs.contains(userLangCode)) {
            System.out.println("Invalid or empty input. Defaulting to en-CA.");
            userLangCode = "en-CA";
        }

        this.languageManager = LanguageManager.getInstance(userLangCode);

        DBManager dbManager = DBManager.getInstance();

        this.controller = new ReliefController(dbManager, languageManager);
    }

    /**
    Starts the main loop of the command-line interface, allowing user to navigate
     */
    public void run() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== " + languageManager.getTranslation("MainMenu") + " ===");
            System.out.println("1. " + languageManager.getTranslation("InsertData"));
            System.out.println("2. " + languageManager.getTranslation("ViewData"));
            System.out.println("3. " + languageManager.getTranslation("UpdateData"));
            System.out.println("0. " + languageManager.getTranslation("Exit"));
            System.out.print(languageManager.getTranslation("EnterChoice") + ": ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    insertMenu();
                    break;
                case "2":
                    viewMenu();
                    break;
                case "3":
                    updateMenu();
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println(languageManager.getTranslation("InvalidInputNumber"));
            }
        }
    }

    /**
    displays the insert menu and handles user input for adding new data 
     */
    private void insertMenu() {    
        while(true){
            System.out.println("\n=== " + languageManager.getTranslation("InsertData") + " ===");
            System.out.println("1. " + languageManager.getTranslation("AddDisasterVictim"));
            System.out.println("2. " + languageManager.getTranslation("AddInquirer"));
            System.out.println("3. " + languageManager.getTranslation("AddMedicalRecord"));
            System.out.println("4. " + languageManager.getTranslation("AddSupply"));
            System.out.println("5. " + languageManager.getTranslation("LogInquiry"));
            System.err.println("0. " + languageManager.getTranslation("MainMenu"));
            System.out.print(languageManager.getTranslation("EnterChoice") + ": ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    addDisasterVictimCLI();
                    break;
                case "2":
                    addNewInquirerCLI();
                    break;
                case "3":
                    addNewMedicalRecord();
                    break;
                case "4":
                    addNewSupplyCLI();
                    break;
                case "5":
                    logInquiryCLI();
                    break;
                case "0":
                    break;
                default:
                    System.out.println(languageManager.getTranslation("InvalidInputNumber"));
                    continue;
            }
            break;
        }
    }

    /**
    Displays the view menu and handles user input for viewing different types of data  
     */
    private void viewMenu() {
        while(true){
            System.out.println("\n=== " + languageManager.getTranslation("ViewData") + " ===");
            System.out.println("1. " + languageManager.getTranslation("ViewVictims"));
            System.out.println("2. " + languageManager.getTranslation("ViewInquirers"));
            System.out.println("3. " + languageManager.getTranslation("ViewLocations"));
            System.out.println("4. " + languageManager.getTranslation("ViewMedicalRecords"));
            System.out.println("5. " + languageManager.getTranslation("ViewInquiries"));
            System.out.println("6. " + languageManager.getTranslation("ViewInventory"));
            System.out.println("7. " + languageManager.getTranslation("ViewFamilies"));
            System.err.println("0. " + languageManager.getTranslation("MainMenu"));
            System.out.print(languageManager.getTranslation("EnterChoice") + ": ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    controller.viewDisasterVictims();
                    break;
                case "2":
                    controller.viewInquirers();
                    break;
                case "3":
                    controller.viewLocations();
                    break;
                case "4":
                    controller.viewMedicalRecords();
                    break;
                case "5":
                    controller.viewInquiries();
                    break;
                case "6":
                    controller.viewInventory();
                    break;
                case "7":
                    controller.viewFamilies();
                    break;
                case "0":
                    break;
                default:
                    System.out.println(languageManager.getTranslation("InvalidInputNumber"));
            }
        break;
        }
    }

    /**
    Displays the update menu and handles user input for updating data 
     */
    private void updateMenu() {
        while(true){
        System.out.println("\n=== " + languageManager.getTranslation("UpdateData") + " ===");
        System.out.println("1. " + languageManager.getTranslation("UpdateVictim"));
        System.out.println("2. " + languageManager.getTranslation("UpdateInquirer"));
        System.out.println("3. " + languageManager.getTranslation("UpdateMedicalRecord"));
        System.out.println("4. " + languageManager.getTranslation("UpdateInquiry"));
        System.out.println("5. " + languageManager.getTranslation("AllocateInventoryToPerson"));
        System.out.println("6. " + languageManager.getTranslation("AllocateVictimToLocation"));
        System.err.println("0. " + languageManager.getTranslation("MainMenu"));
        System.out.print(languageManager.getTranslation("EnterChoice") + ": ");

        String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    updateDisasterVictimCLI();
                    break;
                case "2":
                    updateInquirerCLI();
                    break;
                case "3":
                    updateMedicalRecordCLI();
                    break;
                case "4":
                    updateInquiryCLI();
                    break;
                case "5":
                    allocateInventoryToPersonCLI();
                    break;
                case "6":
                    allocateVictimToLocationCLI();
                    break;
                case "0":
                    break;
                default:
                    System.out.println(languageManager.getTranslation("InvalidInputNumber"));
            }
        break;
    }
    }

    public void addDisasterVictimCLI() {
        try{
            System.out.println(languageManager.getTranslation("EnterFirstName"));
            String firstName = scanner.nextLine().trim();

            System.out.println(languageManager.getTranslation("EnterLastName"));
            String lastName = scanner.nextLine().trim();

            System.out.println(languageManager.getTranslation("EnterDOB"));
            String dob = scanner.nextLine().trim();

            System.out.println(languageManager.getTranslation("EnterGender"));
            System.out.println("0. " + languageManager.getTranslation("GenderMale"));
            System.out.println("1. " + languageManager.getTranslation("GenderFemale"));
            System.out.println("2. " + languageManager.getTranslation("GenderNonBinary"));
            int genderIndex = getValidatedIndex(3);
            Gender[] genderList = {Gender.MALE, Gender.FEMALE, Gender.NONBINARY};
            if(genderIndex == -1){
                System.out.println(languageManager.getTranslation("InvalidInputNumber"));
                return;
            }
            Gender gender = genderList[genderIndex];

            System.out.println(languageManager.getTranslation("EnterComments"));
            String comments = scanner.nextLine().trim();

            System.out.println(String.format(languageManager.getTranslation("EnterFamilyGroupOrSkip"), controller.getFamilyGroups().size()));
            int familyIndex = getValidatedIndex(controller.getFamilyGroups().size() + 1);

            FamilyGroup family = null;
            if (familyIndex == controller.getFamilyGroups().size()) {
                family = new FamilyGroup();
                family.setId();
                controller.getFamilyGroups().add(family);
            } 
            else if (familyIndex != -1) {
                family = controller.getFamilyGroups().get(familyIndex);
            }
            boolean added = controller.addDisasterVictim(firstName, lastName, dob, gender, comments, family);

            if (added) {
                System.out.println(languageManager.getTranslation("VictimSuccessfullyAdded"));
            } 
            else {
                System.out.println(languageManager.getTranslation("VictimAlreadyExists"));
            }
        }
        catch (Exception e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }
    }

    public void addNewInquirerCLI() {
        try {
            System.out.print(languageManager.getTranslation("EnterFirstName") + ": ");
            String firstName = scanner.nextLine().trim();
    
            System.out.print(languageManager.getTranslation("EnterLastName") + ": ");
            String lastName = scanner.nextLine().trim();
    
            System.out.print(languageManager.getTranslation("EnterPhoneNumber") + ": ");
            String phoneNumber = scanner.nextLine().trim();
    
            controller.viewFamilies();
            System.out.println(String.format(languageManager.getTranslation("EnterFamilyGroupOrSkip"), controller.getFamilyGroups().size()));
            int familyIndex = getValidatedIndex(controller.getFamilyGroups().size() + 1);
    
            FamilyGroup family = null;
            if (familyIndex == controller.getFamilyGroups().size()) {
                family = new FamilyGroup();
                family.setId();
                controller.getFamilyGroups().add(family);
            } 
            else if (familyIndex != -1) {
                family = controller.getFamilyGroups().get(familyIndex);
            }
    
            boolean added = controller.addInquirer(firstName, lastName, phoneNumber, family);
    
            if (added) {
                System.out.println(languageManager.getTranslation("InquirerAddedSuccess"));
            } 
            else {
                System.out.println(languageManager.getTranslation("InvalidInput"));
            }
    
        } 
        catch (Exception e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }
    }

    public void addNewSupplyCLI() {
        try {
            System.out.println(languageManager.getTranslation("SelectItem"));
            System.out.println("0. " + languageManager.getTranslation("SupplyBlanket"));
            System.out.println("1. " + languageManager.getTranslation("SupplyCot"));
            System.out.println("2. " + languageManager.getTranslation("SupplyBelongings"));
            System.out.println("3. " + languageManager.getTranslation("SupplyWater"));
            int type = getValidatedIndex(4);
            if (type == -1){
                System.out.println(languageManager.getTranslation("InvalidInputNumber"));
                return;
            } 
    
            System.out.println(languageManager.getTranslation("LocationOrPerson"));
            int locOrPerson = getValidatedIndex(2);
            if (locOrPerson == -1){
                System.out.println(languageManager.getTranslation("InvalidInputNumber"));
                return;
            } 
    
            int index = -1;
            if (locOrPerson == 0) {
                controller.viewLocations();
                index = getValidatedIndex(controller.getLocations().size());
            } else {
                controller.viewDisasterVictims();
                index = getValidatedIndex(controller.getDisasterVictims().size());
            }
    
            if (index == -1) {
                System.out.println(languageManager.getTranslation("InvalidInputNumber"));
                return;
            }
            boolean success = false;
            switch (type) {
                case 0:
                    success = controller.addBlanket(locOrPerson == 0, index);
                    break;
                case 1:
                    System.out.println(languageManager.getTranslation("EnterRoomNumber"));
                    int roomNum = Integer.parseInt(scanner.nextLine().trim());
                    System.out.println(languageManager.getTranslation("EnterGridLocation"));
                    String gridLoc = scanner.nextLine().trim();
                    success = controller.addCot(locOrPerson == 0, index, roomNum, gridLoc);
                    break;
                case 2:
                    System.out.println(languageManager.getTranslation("EnterPersonalDescription"));
                    String desc = scanner.nextLine().trim();
                    success = controller.addPersonalBelongings(desc, index);
                    break;
                case 3:
                    success = controller.addWater(locOrPerson == 0, index);
                    break;
            }
    
            if (success){
                System.out.println(languageManager.getTranslation("SupplyAddedSuccess"));
            }
            else{
                System.out.println(languageManager.getTranslation("UnexpectedError"));
            }
    
        } 
        catch (Exception e) {
            new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }
    }

    public void addNewMedicalRecord() {
        try {
            System.out.println(languageManager.getTranslation("MedicalRecordSelectVictim"));
            controller.viewDisasterVictims();
            int victimIndex = getValidatedIndex(controller.getDisasterVictims().size());
            if (victimIndex == -1){
                System.out.println(languageManager.getTranslation("InvalidInputNumber"));
                return;
            }
    
            DisasterVictim victim = controller.getDisasterVictims().get(victimIndex);
    
            boolean isAllocated = false;
            for (Location loc : controller.getLocations()) {
                if (loc.getOccupants().contains(victim)) {
                    isAllocated = true;
                    break;
                }
            }
    
            if (!isAllocated) {
                System.out.println(languageManager.getTranslation("VictimNotAllocatedToLocation"));
                return;
            }
    
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
    
            boolean success = controller.addMedicalRecordToVictim(victimIndex, treatmentDetails, dateOfTreatment);
            if (success) {
                System.out.println(languageManager.getTranslation("MedicalRecordAddedSuccess"));
            } 
            else {
                System.out.println(languageManager.getTranslation("UnexpectedError"));
            }
    
        } 
        catch (Exception e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }
    }

    public void logInquiryCLI() {
        try {
            System.out.println(languageManager.getTranslation("WhoIsLoggingInquiry"));
            System.out.println("1. " + languageManager.getTranslation("Inquirer"));
            System.out.println("2. " + languageManager.getTranslation("DisasterVictim"));
            System.out.print(languageManager.getTranslation("EnterChoice") + ": ");
    
            String typeChoice = scanner.nextLine().trim();
            Person loggedBy = null;
    
            if (typeChoice.equals("1")) {
                System.out.println(languageManager.getTranslation("SelectInquirer"));
                controller.viewInquirers();
                System.out.println(controller.getInquirers().size() + ": " + languageManager.getTranslation("CreateNewInquirer"));
                int inquirerIndex = getValidatedIndex(controller.getInquirers().size() + 1);
                if (inquirerIndex == -1) {
                    System.out.println(languageManager.getTranslation("InvalidInputNumber"));
                    return;
                }
                if (inquirerIndex == controller.getInquirers().size()) {
                    addNewInquirerCLI();
                    loggedBy = controller.getInquirers().get(controller.getInquirers().size() - 1);
                } 
                else {
                    loggedBy = controller.getInquirers().get(inquirerIndex);
                }
    
            } else if (typeChoice.equals("2")) {
                System.out.println(languageManager.getTranslation("SelectDisasterVictim"));
                controller.viewDisasterVictims();
                System.out.println(controller.getDisasterVictims().size() + ": " + languageManager.getTranslation("CreateNewVictim"));
                int victimIndex = getValidatedIndex(controller.getDisasterVictims().size() + 1);
                if (victimIndex == -1){
                    System.out.println(languageManager.getTranslation("InvalidInputNumber"));
                    return;                
                } 
    
                if (victimIndex == controller.getDisasterVictims().size()) {
                    addDisasterVictimCLI();
                    loggedBy = controller.getDisasterVictims().get(controller.getDisasterVictims().size() - 1);
                } 
                else {
                    loggedBy = controller.getDisasterVictims().get(victimIndex);
                }
            } 
            else {
                System.out.println(languageManager.getTranslation("InvalidInputNumber"));
                return;
            }
    
            System.out.println(languageManager.getTranslation("SelectMissingPerson"));
            controller.viewDisasterVictims();
            System.out.println(controller.getDisasterVictims().size() + ": " + languageManager.getTranslation("CreateNewVictim"));
            int missingIndex = getValidatedIndex(controller.getDisasterVictims().size() + 1);
            if (missingIndex == -1){
                System.out.println(languageManager.getTranslation("InvalidInputNumber"));
                return;
            } 
    
            DisasterVictim missingPerson;
            if (missingIndex == controller.getDisasterVictims().size()) {
                addDisasterVictimCLI();
                missingPerson = controller.getDisasterVictims().get(controller.getDisasterVictims().size() - 1);
            } 
            else {
                missingPerson = controller.getDisasterVictims().get(missingIndex);
            }
    
            System.out.println(languageManager.getTranslation("SelectLocation"));
            controller.viewLocations();
            int locationIndex = getValidatedIndex(controller.getLocations().size());
            if (locationIndex == -1){
                System.out.println(languageManager.getTranslation("InvalidInputNumber"));
                return;
            } 
            Location location = controller.getLocations().get(locationIndex);
    
            System.out.println(languageManager.getTranslation("EnterComments"));
            String comments = scanner.nextLine().trim();
    
            controller.logInquiry(loggedBy, missingPerson, location, comments);
            System.out.println(languageManager.getTranslation("InquiryLoggedSuccessfully"));
    
        } 
        catch (Exception e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }
    }

    public void updateDisasterVictimCLI() {
        try {
            controller.viewDisasterVictims();
            int index = getValidatedIndex(controller.getDisasterVictims().size());
            if (index == -1) {
                System.out.println(languageManager.getTranslation("InvalidInputNumber"));
                return;
            }
            DisasterVictim victim = controller.getDisasterVictims().get(index);
    
            System.out.println(languageManager.getTranslation("EnterNewGender") + " (" + victim.getGender() + "):");
            System.out.println("0. " + languageManager.getTranslation("GenderMale"));
            System.out.println("1. " + languageManager.getTranslation("GenderFemale"));
            System.out.println("2. " + languageManager.getTranslation("GenderNonBinary"));
            int genderChoice = getValidatedIndex(3);
            Gender[] genderList = {Gender.MALE, Gender.FEMALE, Gender.NONBINARY};
            Gender gender = null;
            if(genderChoice != -1){
                gender = genderList[genderChoice];
            }
            else{
                gender = victim.getGender();
            }
            System.out.println(languageManager.getTranslation("EnterNewComments") + " (" + victim.getComments() + "):");
            String newComments = scanner.nextLine().trim();
    
            controller.viewFamilies();
            System.out.println(String.format(languageManager.getTranslation("EnterFamilyGroupOrSkip"), controller.getFamilyGroups().size()));
            int famIndex = getValidatedIndex(controller.getFamilyGroups().size() + 1);
    
            FamilyGroup newFamily = null;
            if (famIndex == controller.getFamilyGroups().size()) {
                newFamily = new FamilyGroup();
                newFamily.setId();
                controller.getFamilyGroups().add(newFamily);
                newFamily.addFamilyMember(victim);
            } 
            else if (famIndex != -1) {
                newFamily = controller.getFamilyGroups().get(famIndex);
                newFamily.addFamilyMember(victim);
            }
    
            controller.updateDisasterVictim(victim, gender, newComments, newFamily);
            System.out.println(languageManager.getTranslation("VictimSuccessfullyUpdated"));
        } 
        catch (Exception e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }
    }

    public void updateInquirerCLI() {
        try {
            controller.viewInquirers();
            int index = getValidatedIndex(controller.getInquirers().size());
            if (index == -1){
                System.out.println(languageManager.getTranslation("InvalidInputNumber"));
                return;
            }
            Inquirer inquirer = controller.getInquirers().get(index);
    
            System.out.println(languageManager.getTranslation("EnterNewPhone"));
            String phone = scanner.nextLine().trim();
            if (phone.isEmpty() || !phone.matches("[0-9\\-]+")) {
                System.out.println(languageManager.getTranslation("InvalidPhoneNumber"));
                return;
            }
    
            controller.viewFamilies();
            System.out.println(String.format(languageManager.getTranslation("EnterFamilyGroupOrSkip"), controller.getFamilyGroups().size()));
            int famIndex = getValidatedIndex(controller.getFamilyGroups().size() + 1);
    
            FamilyGroup newFamily = null;
            if (famIndex == controller.getFamilyGroups().size()) {
                newFamily = new FamilyGroup();
                newFamily.setId();
                controller.getFamilyGroups().add(newFamily);
                newFamily.addFamilyMember(inquirer);

            } 
            else if (famIndex != -1) {
                newFamily = controller.getFamilyGroups().get(famIndex);
                newFamily.addFamilyMember(inquirer);
            }
    
            controller.updateInquirer(inquirer, phone, newFamily);
            System.out.println(languageManager.getTranslation("InquirerUpdated"));
        } 
        catch (Exception e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }
    }

    public void updateInquiryCLI() {
        try {
            controller.viewInquiries();
            int index = getValidatedIndex(controller.getInquiries().size());
            if (index == -1){
                System.out.println(languageManager.getTranslation("InvalidInputNumber"));
                return;
            }
    
            ReliefService inquiry = controller.getInquiries().get(index);
    
            System.out.println(languageManager.getTranslation("EnterNewInquiryComments"));
            String comments = scanner.nextLine().trim();
            if (comments.isEmpty()) return;
            controller.updateInquiry(inquiry, comments);
            System.out.println(languageManager.getTranslation("InquiryUpdated"));
        } 
        catch (Exception e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }
    }

    public void updateMedicalRecordCLI() {
        try {
            ArrayList<DisasterVictim> victimsWithRecords = new ArrayList<>();
            for (DisasterVictim victim : controller.getDisasterVictims()) {
                if (victim.getMedicalRecords() != null && !victim.getMedicalRecords().isEmpty()) {
                    victimsWithRecords.add(victim);
                }
            }
    
            if (victimsWithRecords.isEmpty()) {
                System.out.println(languageManager.getTranslation("NoMedicalRecordsFound"));
                return;
            }
    
            for (int i = 0; i < victimsWithRecords.size(); i++) {
                DisasterVictim v = victimsWithRecords.get(i);
                System.out.println(i + ": " + languageManager.getTranslation("Name") + " " + v.getFirstName() + " " + v.getLastName() + " | " + languageManager.getTranslation("DateOfBirth") + ": " + v.getDateOfBirth());
            }
    
            int victimIndex = getValidatedIndex(victimsWithRecords.size());
            if (victimIndex == -1){
                System.out.println(languageManager.getTranslation("InvalidInputNumber"));
                return;
            } 
    
            DisasterVictim selectedVictim = victimsWithRecords.get(victimIndex);
            ArrayList<MedicalRecord> records = selectedVictim.getMedicalRecords();
    
            for (int i = 0; i < records.size(); i++) {
                System.out.println(i + ": " + records.get(i).getTreatmentDetails());
            }
    
            int recordIndex = getValidatedIndex(records.size());
            if (recordIndex == -1){
                System.out.println(languageManager.getTranslation("InvalidInputNumber"));
                return;
            }
            MedicalRecord record = records.get(recordIndex);
    
            System.out.println(languageManager.getTranslation("EnterNewTreatment") + "(" + record.getTreatmentDetails() + ")");
            String newDetails = scanner.nextLine().trim();
            if (newDetails.isEmpty()) return;
    
            System.out.println(languageManager.getTranslation("EnterNewDate") +  "(" + record.getDateOfTreatment() + ")");
            String newDate = scanner.nextLine().trim();
            if (!isValidDateFormat(newDate)) return;
    
            boolean success = controller.updateMedicalRecord(record, newDetails, newDate);
    
            if (success) {
                System.out.println(languageManager.getTranslation("MedicalRecordUpdated"));
            } else {
                System.out.println(languageManager.getTranslation("InvalidInput"));
            }
    
        } 
        catch (Exception e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }
    }
    

    public void allocateInventoryToLocationCLI() {
        try {
            controller.viewLocations();
            int locationIndex = getValidatedIndex(controller.getLocations().size());
            if (locationIndex == -1){
                System.out.println(languageManager.getTranslation("InvalidInputNumber"));
                return;
            } 
            Location location = controller.getLocations().get(locationIndex);
    
            ArrayList<InventoryItem> allocatable = new ArrayList<>();
            for (InventoryItem item : controller.getSupply()) {
                if (item.getItemType() != ItemType.PERSONALBELONGINGS) {
                    allocatable.add(item);
                }
            }
    
            if (allocatable.isEmpty()) {
                System.out.println(languageManager.getTranslation("NoUnallocatedInventory"));
                return;
            }
    
            for (int i = 0; i < allocatable.size(); i++) {
                System.out.println(i + ": " + allocatable.get(i).getItemType());
            }
    
            int itemIndex = getValidatedIndex(allocatable.size());
            if (itemIndex == -1){
                System.out.println(languageManager.getTranslation("InvalidInputNumber"));
                return; 
            } 
    
            InventoryItem selectedItem = allocatable.get(itemIndex);
    
            boolean success = controller.allocateInventoryToLocation(selectedItem, location);
            if (success) {
                System.out.println(languageManager.getTranslation("InventoryAllocatedSuccessfully"));
            } 
            else {
                System.out.println(languageManager.getTranslation("InvalidInventorySelection"));
            }
    
        } 
        catch (Exception e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }
    }
    
    
    public void allocateInventoryToPersonCLI() {
        try {
            controller.viewDisasterVictims();
            int victimIndex = getValidatedIndex(controller.getDisasterVictims().size());
            if (victimIndex == -1){
                System.out.println(languageManager.getTranslation("InvalidInputNumber"));
                return;
            } 
            DisasterVictim victim = controller.getDisasterVictims().get(victimIndex);
    
            Location victimLocation = null;
            for (Location loc : controller.getLocations()) {
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
            for (InventoryItem item : controller.getSupply()) {
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
            if (invIndex == -1){
                System.out.println(languageManager.getTranslation("InvalidInputNumber"));
                return;
            }
            InventoryItem selectedItem = matchingInventory.get(invIndex);
            boolean success = controller.allocateInventoryToPerson(victim, selectedItem);
    
            if (success) {
                System.out.println(languageManager.getTranslation("InventoryAllocatedSuccessfully"));
            } 
            else {
                System.out.println(languageManager.getTranslation("InvalidInput"));
            }
    
        } 
        catch (Exception e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }
    }
    
    
    public FamilyGroup createNewFamilyGroup() {
        FamilyGroup newFamily = new FamilyGroup();
        newFamily.setId(); 
        controller.getFamilyGroups().add(newFamily);
        return newFamily;
    }
    

    public void allocateVictimToLocationCLI() {
        try {
            controller.viewDisasterVictims();
            int victimIndex = getValidatedIndex(controller.getDisasterVictims().size());
            if (victimIndex == -1){
                System.out.println(languageManager.getTranslation("InvalidInputNumber"));
                return;
            }    
            controller.viewLocations();
            int locationIndex = getValidatedIndex(controller.getLocations().size());
            if (locationIndex == -1){
                System.out.println(languageManager.getTranslation("InvalidInputNumber"));
                return;
            }    
            boolean success = controller.allocateVictimToLocation(victimIndex, locationIndex);
            if (success) {
                System.out.println(languageManager.getTranslation("VictimSuccessfullyAllocated"));
            } else {
                System.out.println(languageManager.getTranslation("UnexpectedError"));
            }
        } 
        catch (Exception e) {
            new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }
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


    private int getValidatedIndex(int size) {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()){
                    return -1;
                }
                int index = Integer.parseInt(input);
                if (index >= 0 && index < size) {
                    return index;
                } 
                else {
                    System.out.println(languageManager.getTranslation("InvalidInputNumber"));
                }
            } 
            catch (NumberFormatException e) {
                System.out.println(languageManager.getTranslation("InvalidInputNumber"));
            }
        }
    }
    
}