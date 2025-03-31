package edu.ucalgary.oop;

public class ReliefController {
    private ArrayList<DisasterVictim> disastervictims;
    private ArrayList<Inquirer> inquirers;
    private ArrayList<Location> locations;
    private ArrayList<ReliefService> inquiries;
    private ArrayList<InventoryItem> supply;
    private ArrayList<MedicalRecord> medicalRecords;
    private ArrayList<FamilyGroup> familyGroups;
    private static DBAccess model;
    private static Scanner scanner;

    public ReliefController(Scanner scanner, DBAccess model){
        this.scanner = scanner;
        this.model = model;
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
        this.medicalRecords = model.getAllMedicalRecords(this.locations, this.disastervictims);
    }

}
