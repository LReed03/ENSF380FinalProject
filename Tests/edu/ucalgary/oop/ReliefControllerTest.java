package edu.ucalgary.oop;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ReliefControllerTest {
    private ReliefController controller;
    private MockDatabase mockDb;
    private LanguageManager languageManager;

    @Before
    public void setUp() {
        mockDb = new MockDatabase();
        languageManager = LanguageManager.getInstance("en-CA");
        controller = new ReliefController(mockDb, languageManager);
    }

    @Test
    public void testAddDisasterVictimValid() {
        FamilyGroup family = controller.getFamilyGroups().get(0);
        boolean result = controller.addDisasterVictim("John", "Smith", "1990-01-01", Gender.MALE, "Healthy", family);
        assertTrue("Valid disaster victim should be added successfully", result);
    }

    @Test
    public void testAddDisasterVictimMissingFields() {
        boolean result = controller.addDisasterVictim(null, "Smith", "1990-01-01", Gender.MALE, "Note", null);
        assertFalse("Missing first name should result in failure", result);
    }

    @Test
    public void testAddInquirerValid() {
        FamilyGroup family = controller.getFamilyGroups().get(0);
        boolean result = controller.addInquirer("Anna", "White", "123-456-7890", family);
        assertTrue("Valid inquirer should be added", result);
    }

    @Test
    public void testAddInquirerInvalidPhone() {
        boolean result = controller.addInquirer("Anna", "White", "invalid", null);
        assertFalse("Invalid phone number should fail", result);
    }

    @Test
    public void testAllocateVictimToLocationValid() {
        boolean result = controller.allocateVictimToLocation(0, 0);
        assertTrue("Valid victim should be allocated to valid location", result);
    }

    @Test
    public void testAllocateVictimToLocationInvalidIndex() {
        boolean result = controller.allocateVictimToLocation(-1, 999);
        assertFalse("Invalid indexes should return false", result);
    }

    @Test
    public void testAddBlanketToLocation() {
        boolean result = controller.addBlanket(true, 0);
        assertTrue("Blanket should be added to location", result);
    }

    @Test
    public void testAddBlanketToPerson() {
        boolean result = controller.addBlanket(false, 0);
        assertTrue("Blanket should be added to person", result);
    }

    @Test
    public void testAddCotValid() {
        boolean result = controller.addCot(true, 0, 101, "A1");
        assertTrue("Cot should be added successfully", result);
    }


    @Test
    public void testAddWaterToLocation() {
        boolean result = controller.addWater(true, 0);
        assertTrue("Water should be added to location", result);
    }

    @Test
    public void testAddWaterToVictim() {
        boolean result = controller.addWater(false, 0);
        assertTrue("Water should be added to victim", result);
    }

    @Test
    public void testAddMedicalRecordValid() {
        boolean result = controller.addMedicalRecordToVictim(0, "Checkup", "2025-04-09");
        assertTrue("Medical record should be added successfully", result);
    }

    @Test
    public void testAddMedicalRecordToVictimWithoutLocation() {
        Location loc = controller.getLocations().get(0);
        DisasterVictim victim = controller.getDisasterVictims().get(0);
        loc.removeOccupant(victim);
        boolean result = controller.addMedicalRecordToVictim(0, "Checkup", "2025-04-09");
        assertFalse("Victim without location should fail to receive record", result);
    }

    @Test
    public void testLogInquiryValid() {
        Person inquirer = controller.getInquirers().get(0);
        DisasterVictim victim = controller.getDisasterVictims().get(0);
        Location location = controller.getLocations().get(0);
        int initialSize = controller.getInquiries().size();
        controller.logInquiry(inquirer, victim, location, "Seen last at main tent");
        int finalSize = controller.getInquiries().size();
        assertEquals("Inquiry list should contain one more item", finalSize, controller.getInquiries().size());
    }

    @Test
    public void testUpdateDisasterVictimInfo() {
        DisasterVictim victim = controller.getDisasterVictims().get(0);
        controller.updateDisasterVictim(victim, Gender.NONBINARY, "Updated info", null);
        assertEquals("Victim gender should be updated", Gender.NONBINARY, victim.getGender());
        assertEquals("Victim comments should be updated", "Updated info", victim.getComments());
    }

    @Test
    public void testUpdateInquirerPhoneAndFamily() {
        Inquirer inquirer = controller.getInquirers().get(0);
        FamilyGroup newFamily = new FamilyGroup();
        newFamily.setId(99);
        controller.updateInquirer(inquirer, "000-111-2222", newFamily);
        assertEquals("Inquirer phone should update", "000-111-2222", inquirer.getPhone());
        assertEquals("Inquirer family should update", newFamily, inquirer.getFamily());
    }

    @Test
    public void testUpdateMedicalRecordValid() {
        DisasterVictim victim = controller.getDisasterVictims().get(0);
        MedicalRecord record = victim.getMedicalRecords().get(0);
        boolean updated = controller.updateMedicalRecord(record, "Updated Treatment", "2025-04-09");
        assertTrue("Medical record should be updated successfully", updated);
        assertEquals("New treatment should be set", "Updated Treatment", record.getTreatmentDetails());
    }

    @Test
    public void testUpdateMedicalRecordInvalidDate() {
        DisasterVictim victim = controller.getDisasterVictims().get(0);
        MedicalRecord record = victim.getMedicalRecords().get(0);
        boolean updated = controller.updateMedicalRecord(record, "Treatment", "BadDate");
        assertFalse("Invalid date format should fail", updated);
    }

    @Test
    public void testAllocateInventoryToLocationValid() {
        InventoryItem item = controller.getSupply().get(0);
        Location loc = controller.getLocations().get(0);
        boolean result = controller.allocateInventoryToLocation(item, loc);
        assertTrue("Valid inventory should be assigned to location", result);
    }

    @Test
    public void testAllocateInventoryToLocationInvalidType() {
        PersonalBelongings belongings = new PersonalBelongings("Glasses", controller.getDisasterVictims().get(0));
        belongings.setId(1000);
        boolean result = controller.allocateInventoryToLocation(belongings, controller.getLocations().get(0));
        assertFalse("Personal belongings should not be assigned to location", result);
    }



    @Test
    public void testAllocateInventoryToPersonNull() {
        InventoryItem item = controller.getSupply().get(0);
        boolean result = controller.allocateInventoryToPerson(null, item);
        assertFalse("Null victim should fail allocation", result);
    }

    @Test
    public void testRemoveFamilyAutomaticallyWhenVictimFamilyIsSetToNull() {
        FamilyGroup oldFamily = controller.getFamilyGroups().get(0);
        DisasterVictim victim = controller.getDisasterVictims().get(0);

        controller.updateDisasterVictim(victim, victim.getGender(), victim.getComments(), null);
        
        assertNull("Victim's family should be set to null", victim.getFamily());

        assertFalse("Old family should be automatically removed from the familyGroups list", controller.getFamilyGroups().contains(oldFamily));
    }


}
