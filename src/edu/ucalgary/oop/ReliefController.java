package edu.ucalgary.oop;

public class ReliefController {
    private ArrayList<DisasterVictim> disastervictims;
    private ArrayList<Inquirer> inquirers;
    private ArrayList<Location> locations;
    private ArrayList<ReliefService> inquiries;
    private ArrayList<InventoryItem> supply;
    private ArrayList<MedicalRecord> medicalRecords;
    private static DBAccess model;
    private static Scanner scanner;

    public ReliefController(Scanner scanner, DBAccess model){
        this.scanner = scanner;
        this.model = model;
        this.StartUp();
    }

    public void StartUp(){
        model.removeExpiredWater();
        this.disastervictims = model.getAllDisasterVictims();
        this.inquirers = model.getAllInquirers();
        this.locations = model.getAllLocations();
        this.inquiries = model.getAllInquiries();
        this.suppy = model.getAllInventory();
        this.medicalRecords = model.getAllMedicalRecords();
    }

}
