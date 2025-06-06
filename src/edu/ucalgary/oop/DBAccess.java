package edu.ucalgary.oop;

import java.sql.Timestamp;
import java.util.ArrayList;
/**
 @author Landon Reed
 @version 1.0
 Created on: April 5, 2025
 */
public interface DBAccess {

    ArrayList<DisasterVictim> getAllDisasterVictims(ArrayList<FamilyGroup> families);

    void assignVictimsToLocations(ArrayList<DisasterVictim> victims, ArrayList<Location> locations);

    ArrayList<FamilyGroup> getFamilyGroups();

    ArrayList<Inquirer> getAllInquirers(ArrayList<FamilyGroup> families);

    ArrayList<Location> getAllLocations();
    
    void removeVictimFromLocation(int personId, int locationId);

    void updateSupplyToLocation(int supplyId, int locationId);
    
    void removeSupplyAllocation(int supplyId);

    ArrayList<InventoryItem> getAllInventory(ArrayList<DisasterVictim> victims, ArrayList<Location> locations);

    ArrayList<ReliefService> getAllInquiries(ArrayList<Inquirer> inquirers, ArrayList<DisasterVictim> victims, ArrayList<Location> locations);

    void insertDisasterVictim(DisasterVictim victim);

    void insertInquirer(Inquirer inquirer);

    void updateDisasterVictim(DisasterVictim victim);

    void updateInquirer(Inquirer inquirer);

    void updateMedicalRecord(MedicalRecord record, int recordId);

    void updateInquiry(ReliefService inquiry, int inquiryId);

    void addDisasterVictimToLocation(int personId, int locationId);

    void addMedicalRecord(MedicalRecord record, int locationId, int personId);

    void allocateInventoryToPerson(int itemId, int victimId);

    void allocateInventoryToLocation(int itemId, int locationId);

    void addNewSupply(String type, String comments, InventoryItem item);

    void logInquiry(int inquirerId, int seekingId, int locationId, Timestamp date, String comments);

    void getAllMedicalRecords(ArrayList<Location> locations, ArrayList<DisasterVictim> victims);

    void removeExpiredWater();

    void close();
}