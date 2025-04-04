package edu.ucalgary.oop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CLI {
    private ReliefController controller;
    private Scanner scanner;
    private LanguageManager languageManager;
    private ArrayList<String> languageCodes = new ArrayList<>(Arrays.asList("en-CA", "fr-CA"));

    public CLI() {
        this.scanner = new Scanner(System.in);
    
        // Language selection stuff...
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
    
        this.controller = new ReliefController(scanner, dbManager, languageManager);
    }
    
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
                case "1": insertMenu(); break;
                case "2": viewMenu(); break;
                case "3": updateMenu(); break;
                case "0": running = false; break;
                default: System.out.println(languageManager.getTranslation("InvalidInputNumber"));
            }
        }
    }

    private void insertMenu() {
        System.out.println("\n=== " + languageManager.getTranslation("InsertData") + " ===");
        System.out.println("1. " + languageManager.getTranslation("AddDisasterVictim"));
        System.out.println("2. " + languageManager.getTranslation("AddInquirer"));
        System.out.println("3. " + languageManager.getTranslation("AddMedicalRecord"));
        System.out.println("4. " + languageManager.getTranslation("AddSupply"));
        System.out.println("5. " + languageManager.getTranslation("LogInquiry"));
        System.out.print(languageManager.getTranslation("EnterChoice") + ": ");

        String choice = scanner.nextLine().trim();
        switch (choice) {
            case "1": controller.addDisasterVictim(); break;
            case "2": controller.addNewInquirer(); break;
            case "3": controller.addNewMedicalRecord(); break;
            case "4": controller.addNewSupply(); break;
            case "5": controller.logInquiry(); break;
            default: System.out.println(languageManager.getTranslation("InvalidInputNumber"));
        }
    }

    private void viewMenu() {
        System.out.println("\n=== " + languageManager.getTranslation("ViewData") + " ===");
        System.out.println("1. " + languageManager.getTranslation("ViewVictims"));
        System.out.println("2. " + languageManager.getTranslation("ViewInquirers"));
        System.out.println("3. " + languageManager.getTranslation("ViewLocations"));
        System.out.println("4. " + languageManager.getTranslation("ViewMedicalRecords"));
        System.out.println("5. " + languageManager.getTranslation("ViewInquiries"));
        System.out.println("6. " + languageManager.getTranslation("ViewInventory"));
        System.out.print(languageManager.getTranslation("EnterChoice") + ": ");

        String choice = scanner.nextLine().trim();
        switch (choice) {
            case "1": controller.viewDisasterVictims(); break;
            case "2": controller.viewInquirers(); break;
            case "3": controller.viewLocations(); break;
            case "4": controller.viewMedicalRecords(); break;
            case "5": controller.viewInquiries(); break;
            case "6": controller.viewInventory(); break;
            default: System.out.println(languageManager.getTranslation("InvalidInputNumber"));
        }
    }

    private void updateMenu() {
        System.out.println("\n=== " + languageManager.getTranslation("UpdateData") + " ===");
        System.out.println("1. " + languageManager.getTranslation("UpdateVictim"));
        System.out.println("2. " + languageManager.getTranslation("UpdateInquirer"));
        System.out.println("3. " + languageManager.getTranslation("UpdateMedicalRecord"));
        System.out.println("4. " + languageManager.getTranslation("UpdateInquiry"));
        System.out.println("5. " + languageManager.getTranslation("AllocateInventoryToPerson"));
        System.out.println("6. " + languageManager.getTranslation("AllocateVictimToLocation"));
        System.out.print(languageManager.getTranslation("EnterChoice") + ": ");
    
        String choice = scanner.nextLine().trim();
        switch (choice) {
            case "1": controller.updateDisasterVictim(); break;
            case "2": controller.updateInquirer(); break;
            case "3": controller.updateMedicalRecord(); break;
            case "4": controller.updateInquiry(); break;
            case "5": controller.allocateInventoryToPerson(); break;
            case "6": controller.allocateVictimToLocation(); break;
            default: System.out.println(languageManager.getTranslation("InvalidInputNumber"));
        }
    }
    
}
