package edu.ucalgary.oop;

import java.sql.Timestamp;
import java.util.ArrayList;
/**
 @author Landon Reed
 @version 1.0
 Created on: April 5, 2025
 */
public class MockDatabase implements DBAccess {
    private ArrayList<FamilyGroup> families = new ArrayList<>();
    private ArrayList<DisasterVictim> victims = new ArrayList<>();
    private ArrayList<Inquirer> inquirers = new ArrayList<>();
    private ArrayList<Location> locations = new ArrayList<>();
    private ArrayList<InventoryItem> inventory = new ArrayList<>();
    private ArrayList<MedicalRecord> medicalRecords = new ArrayList<>();
    private ArrayList<ReliefService> inquiries = new ArrayList<>();
    /**
    constructor to create mock database with values
     */
    public MockDatabase() {
        FamilyGroup family1 = new FamilyGroup(1);
        families.add(family1);

        DisasterVictim victim1 = new DisasterVictim("Jane", "2025-01-01");
        victim1.setLastName("Doe");
        victim1.setId(1);
        victim1.setFamily(family1);
        victims.add(victim1);

        Inquirer inquirer1 = new Inquirer("Alice", "Smith", "555-1234");
        inquirer1.setId(2);
        inquirers.add(inquirer1);

        Location location1 = new Location("Shelter A", "100 Main St");
        location1.setId(10);
        locations.add(location1);

        Blanket blanket = new Blanket(location1);
        blanket.setId(100);
        inventory.add(blanket);

        ReliefService inquiry = new ReliefService(inquirer1, victim1, "2025-04-01", "Last seen near Shelter A", location1);
        inquiry.setId(500);
        inquiries.add(inquiry);

        MedicalRecord record = new MedicalRecord(location1, "Routine Check", "2025-03-30");
        record.setId(300);
        medicalRecords.add(record);
        victim1.addMedicalRecord(record);
    }
    /**
    Retrieves all family groups from the database
    @return A list of FamilyGroup objects
     */
    @Override
    public ArrayList<FamilyGroup> getFamilyGroups() {
        return families;
    }

    /**
    Retrieves all disaster victims from the database
    @param families A list of family groups to associate with victims
    @return A list of DisasterVictim objects
     */
    @Override
    public ArrayList<DisasterVictim> getAllDisasterVictims(ArrayList<FamilyGroup> inputFamilies) {
        inputFamilies.addAll(families);
        return victims;
    }

        /**
    Retrieves all inquirers from the database
    @param families A list of family groups to associate with inquirers
    @return A list of Inquirer objects
     */
    @Override
    public ArrayList<Inquirer> getAllInquirers(ArrayList<FamilyGroup> inputFamilies) {
        inputFamilies.addAll(families);
        return inquirers;
    }


    /**
    Retrieves all locations from the database
    @return A list of Location objects
     */

    @Override
    public ArrayList<Location> getAllLocations() {
        return locations;
    }
    /**
    Retrieves all inventory items from the database
    @param victims   A list of DisasterVictim objects
    @param locations A list of Location objects
    @return A list of InventoryItem objects
     */
    @Override
    public ArrayList<InventoryItem> getAllInventory(ArrayList<DisasterVictim> v, ArrayList<Location> l) {
        return inventory;
    }

    /**
    Retrieves all inquiries from the database
    @param inquirers A list of Inquirer objects
    @param victims   A list of DisasterVictim objects
    @param locations A list of Location objects
    @return A list of ReliefService objects
     */

    @Override
    public ArrayList<ReliefService> getAllInquiries(ArrayList<Inquirer> i, ArrayList<DisasterVictim> v, ArrayList<Location> l) {
        return inquiries;
    }

    /**
    Retrieves all medical records from the database and associates them with victims
    @param allLocations A list of Location objects
    @param victims      A list of DisasterVictim objects
     */

    @Override
    public void getAllMedicalRecords(ArrayList<Location> l, ArrayList<DisasterVictim> v) {
        for (DisasterVictim victim : v) {
            for (MedicalRecord record : medicalRecords) {
                if (!victim.getMedicalRecords().contains(record)) {
                    victim.addMedicalRecord(record);
                }
            }
        }
    }

    /**
    Inserts a new disaster victim into the database
    @param victim The DisasterVictim object to insert
     */
    @Override
    public void insertDisasterVictim(DisasterVictim victim) {
        victims.add(victim);
    }
    /**
    Inserts a new inquirer into the database
    @param inquirer The Inquirer object to insert
     */
    @Override
    public void insertInquirer(Inquirer inquirer) {
        inquirers.add(inquirer);
    }
    /**
    Updates an existing disaster victim in the database
    @param victim The DisasterVictim object with updated information
     */
    @Override
    public void updateDisasterVictim(DisasterVictim updated) {
        for (int i = 0; i < victims.size(); i++) {
            if (victims.get(i).getId() == updated.getId()) {
                victims.set(i, updated);
                return;
            }
        }
    }
    /**
    Updates an existing inquirer in the database
    @param inquirer The Inquirer object with updated information
     */
    @Override
    public void updateInquirer(Inquirer updated) {
        for (int i = 0; i < inquirers.size(); i++) {
            if (inquirers.get(i).getId() == updated.getId()) {
                inquirers.set(i, updated);
                return;
            }
        }
    }

    /**
    Updates an existing medical record in the database
    @param record   The MedicalRecord object with updated information
    @param recordId The ID of the medical record
     */

    @Override
    public void updateMedicalRecord(MedicalRecord updated, int recordId) {
        for (int i = 0; i < medicalRecords.size(); i++) {
            if (medicalRecords.get(i).getId() == recordId) {
                medicalRecords.set(i, updated);
                return;
            }
        }
    }

    /**
    Updates an existing inquiry in the database
    @param inquiry   The ReliefService object with updated information
    @param inquiryId The ID of the inquiry
     */

    @Override
    public void updateInquiry(ReliefService updated, int inquiryId) {
        for (int i = 0; i < inquiries.size(); i++) {
            if (inquiries.get(i).getId() == inquiryId) {
                inquiries.set(i, updated);
                return;
            }
        }
    }

    /**
    Adds a disaster victim to a location in the database
    @param personId   The ID of the disaster victim
    @param locationId The ID of the location
     */

    @Override
    public void addDisasterVictimToLocation(int personId, int locationId) {
        DisasterVictim v = findVictim(personId);
        Location l = findLocation(locationId);
        if (v != null && l != null) {
            l.addOccupant(v);
            
        }
    }

    /**
    Adds a medical record to the database
    @param record     The MedicalRecord object to add
    @param locationId The ID of the location
    @param personId   The ID of the disaster victim
     */

    @Override
    public void addMedicalRecord(MedicalRecord record, int locationId, int personId) {
        Location loc = findLocation(locationId);
        DisasterVictim victim = findVictim(personId);
        if (loc != null && victim != null) {
            record.setId(record.getId() == 0 ? medicalRecords.size() + 1 : record.getId());
            medicalRecords.add(record);
            victim.addMedicalRecord(record);
        }
    }
    /**
    Allocates an inventory item to a disaster victim
    @param itemId   The ID of the inventory item
    @param victimId The ID of the disaster victim
     */
    @Override
    public void allocateInventoryToPerson(int itemId, int victimId) {
        InventoryItem item = findItem(itemId);
        DisasterVictim victim = findVictim(victimId);
        if (item != null && victim != null) {
            item.setAllocatedToPerson(victim);
            victim.addBelongings(item);
        }
    }
    /**
     Allocates an inventory item to a location
    @param itemId     The ID of the inventory item
    @param locationId The ID of the location
     */
    @Override
    public void allocateInventoryToLocation(int itemId, int locationId) {
        InventoryItem item = findItem(itemId);
        Location location = findLocation(locationId);
        if (item != null && location != null) {
            item.setAllocatedToLocation(location);
            location.addSupply(item);
        }
    }

    @Override
    public void removeSupplyAllocation(int supplyId) {
        InventoryItem item = findItem(supplyId);
        if (item != null) {
            item.setAllocatedToLocation(null);
            item.setAllocatedToPerson(null);
        }
    }

    @Override
    public void updateSupplyToLocation(int supplyId, int locationId) {
        InventoryItem item = findItem(supplyId);
        Location location = findLocation(locationId);
        if (item != null && location != null) {
            item.setAllocatedToLocation(location);
        }
    }
    /**
    adds a new supply to the database
    @param type     The type of supply
    @param comments Additional comments about the supply
     */
    @Override
    public void addNewSupply(String type, String comments, InventoryItem item) {
        // You can simulate logging if needed
    }


    /**
    Logs an inquiry in the database.
    @param inquirerId The ID of the inquirer
    @param seekingId  The ID of the person being sought
    @param locationId The ID of the location
    @param date       The date of the inquiry
    @param comments   Additional comments about the inquiry
     */
    @Override
    public void logInquiry(int inquirerId, int seekingId, int locationId, Timestamp date, String comments) {
        Inquirer inquirer = (Inquirer) findPerson(inquirerId);
        DisasterVictim victim = findVictim(seekingId);
        Location location = findLocation(locationId);
        ReliefService inquiry = new ReliefService(inquirer, victim, date.toLocalDateTime().toLocalDate().toString(), comments, location);
        inquiry.setId(inquiries.size() + 1);
        inquiries.add(inquiry);
    }

    /**
    Removes a disaster victim from a location in the database
    @param personId   The ID of the disaster victim
    @param locationId The ID of the location
     */

    @Override
    public void removeVictimFromLocation(int personId, int locationId) {
        DisasterVictim v = findVictim(personId);
        Location l = findLocation(locationId);
        if (v != null && l != null) {
            l.removeOccupant(v);
        }
    }
    /**
    Assigns disaster victims to locations based on database records
    @param victims   A list of DisasterVictim objects
    @param locations A list of Location objects
     */
    @Override
    public void assignVictimsToLocations(ArrayList<DisasterVictim> vList, ArrayList<Location> lList) {
        if (lList.isEmpty()){
            return;
        } 
        Location firstLocation = lList.get(0); 
        for (DisasterVictim v : vList) {
            if (!firstLocation.getOccupants().contains(v)) {
                firstLocation.addOccupant(v);
            }
        }
}
    /**
    Removes expired water supplies from the database
     */
    @Override
    public void removeExpiredWater() {
        ArrayList<InventoryItem> toRemove = new ArrayList<>();
        String currentDate = java.time.LocalDate.now().toString();

        for (InventoryItem item : inventory) {
            if (item.getItemType() == ItemType.WATER) {
                Water water = (Water) item; 
                if (water.isExpired(currentDate)) {
                    if (water.getAllocatedToPerson() != null) {
                        water.getAllocatedToPerson().getBelongings().remove(water);
                        water.setAllocatedToPerson(null);
                    }

                    if (water.getAllocatedToLocation() != null) {
                        water.getAllocatedToLocation().getSupplies().remove(water);
                        water.setAllocatedToLocation(null);
                    }

                    toRemove.add(water);
                }
            }
            }

        inventory.removeAll(toRemove);
    }

        /**
    Does nothing in this
     */

    @Override
    public void close() {
        // Nothing to close in mock
    }

    private DisasterVictim findVictim(int id) {
        for (DisasterVictim v : victims) {
            if (v.getId() == id) {
                return v;
            }
        }
        return null;
    }
    

    private Location findLocation(int id) {
        for (Location l : locations) {
            if (l.getId() == id) {
                return l;
            }
        }
        return null;
    }
    
    private InventoryItem findItem(int id) {
        for (InventoryItem i : inventory) {
            if (i.getId() == id) {
                return i;
            }
        }
        return null;
    }
    

    private Person findPerson(int id) {
        for (DisasterVictim v : victims) {
            if (v.getId() == id) {
                return v;
            }
        }
    
        for (Inquirer i : inquirers) {
            if (i.getId() == id) {
                return i;
            }
        }
    
        return null;
    }
    
}
