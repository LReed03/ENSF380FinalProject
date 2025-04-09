package edu.ucalgary.oop;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DisasterVictimTest {
    private DisasterVictim victim;
    private ArrayList<InventoryItem> items; 
    private FamilyGroup family;
    private String expectedFirstName = "Freda";
    private String EXPECTED_ENTRY_DATE = "2025-01-18";
    private String validDate = "2025-01-15";
    private String invalidDate = "15/13/2025";
    private String expectedComments = "Needs medical attention and speaks 2 languages";

    @Before
    public void setUp() {
        victim = new DisasterVictim(expectedFirstName, EXPECTED_ENTRY_DATE);
 
        
        DisasterVictim victim1 = new DisasterVictim("Jane", "2025-01-20");
        DisasterVictim victim2 = new DisasterVictim("John", "2025-01-22");
        
    }

  		  
    @Test
    public void testBirthdateConstructorWithValidEntryDate() {
        String validEntryDate = "2025-02-18";
        String validBirthdate = "2017-03-20";
        DisasterVictim victim = new DisasterVictim("Freda", validEntryDate, validBirthdate);
        assertNotNull("Constructor should successfully create an instance with a valid entry date", victim);
        assertEquals("Constructor should set the entry date correctly", validEntryDate, victim.getEntryDate());
        assertEquals("Constructor should set the birth date correctly", validBirthdate, victim.getDateOfBirth());
  }

    @Test(expected = IllegalArgumentException.class)
    public void testBirthdateConstructorWithInvalidEntryDateFormat() {
        String invalidEntryDate = "20250112"; 
        String validBirthdate = "2017-03-20";
        new DisasterVictim("Fang", invalidEntryDate, validBirthdate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBirthdateConstructorWithInvalidBirthdate() {
        String validEntryDate = "2025-02-18";
        String invalidBirthDate = "20250112"; 
        DisasterVictim victim = new DisasterVictim("Yaw", validEntryDate, invalidBirthDate);
  }

    @Test(expected = IllegalArgumentException.class)
    public void testBirthdateConstructorWithBirthdateAfterEntryDate() {
        String validEntryDate = "2025-02-17";
        String validBirthDate = "2025-02-18";
        DisasterVictim victim = new DisasterVictim("Jessica", validEntryDate, validBirthDate);
  }


    @Test
    public void testConstructorWithValidEntryDate() {
        String validEntryDate = "2025-01-18";
        DisasterVictim victim = new DisasterVictim("Freda", validEntryDate);
        assertNotNull("Constructor should successfully create an instance with a valid entry date", victim);
        assertEquals("Constructor should set the entry date correctly", validEntryDate, victim.getEntryDate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithInvalidEntryDateFormat() {
        String invalidEntryDate = "18/01/2025"; 
        new DisasterVictim("Freda", invalidEntryDate);
        
    }


    @Test
    public void testSetDateOfBirth() {
        String newDateOfBirth = "1987-05-21";
        victim.setDateOfBirth(newDateOfBirth);
        assertEquals("setDateOfBirth should correctly update the date of birth", newDateOfBirth, victim.getDateOfBirth());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetDateOfBirthWithInvalidFormat() {
        victim.setDateOfBirth(invalidDate); 
    }
	
	@Test
    public void testSetAndGetFirstName() {
        String newFirstName = "Alice";
        victim.setFirstName(newFirstName);
        assertEquals("setFirstName should update and getFirstName should return the new first name", newFirstName, victim.getFirstName());
    }

    @Test
    public void testSetAndGetLastName() {
        String newLastName = "Smith";
        victim.setLastName(newLastName);
        assertEquals("setLastName should update and getLastName should return the new last name", newLastName, victim.getLastName());
    }

    @Test
    public void testSetAndGetComments() {
        victim.setComments(expectedComments);
        String newComments = "Has a minor injury on the left arm";
        victim.setComments(newComments);
        assertEquals("setComments should update the comments correctly", newComments, victim.getComments());
    }


    @Test
    public void testGetEntryDate() {
        assertEquals("getEntryDate should return the expected entry date", EXPECTED_ENTRY_DATE, victim.getEntryDate());
    }
   
    @Test
    public void testSetAndGetGender() {
        Gender newGender = Gender.MALE;
        victim.setGender(newGender);
        assertEquals("setGender should update and getGender should return the new gender", newGender, victim.getGender());
    }

    @Test
    public void testInventoryItemAllocation() {
        Water water = new Water(victim);
        assertEquals("Water should be allocated to the correct victim", victim, water.getAllocatedToPerson());
    }
	
    @Test
    public void testAddFamily(){
        DisasterVictim victim1 = new DisasterVictim("Jane", "2025-01-20");
        DisasterVictim victim2 = new DisasterVictim("John", "2025-01-22");

        FamilyGroup family = new FamilyGroup();
        victim1.setFamily(family);
        victim2.setFamily(family);
        assertEquals("Victims should be in the same family",victim1.getFamily(), victim2.getFamily());

    }

    @Test
    public void testSetMedicalRecords() {
        Location testLocation = new Location("Shelter Z", "1234 Shelter Ave");
        MedicalRecord testRecord = new MedicalRecord(testLocation, "test for strep", "2025-02-09");
        boolean correct = true;
    
        ArrayList<MedicalRecord> newRecords = new ArrayList<>(Arrays.asList(testRecord));
        victim.setMedicalRecords(newRecords);
        ArrayList<MedicalRecord> actualRecords = victim.getMedicalRecords();
    
        // We have not studied overriding equals in arrays of custom objects so we will manually evaluate equality
        if (newRecords.size() != actualRecords.size()) {
            correct = false;
        } else {
            int i;
            for (i=0;i<newRecords.size();i++) {
                if (actualRecords.get(i) != newRecords.get(i)) {
                    correct = false;
                }
            }
        }
        assertTrue("setMedicalRecords should correctly update medical records", correct);
    }

    @Test
    public void testAddMedicalRecord() {
        DisasterVictim victim = new DisasterVictim("Jane", "2025-01-20");
        Location testLocation = new Location("Shelter Z", "1234 Shelter Ave");
        MedicalRecord testRecord = new MedicalRecord(testLocation, "test for strep", "2025-02-09");
        victim.addMedicalRecord(testRecord);


        ArrayList<MedicalRecord> testMedical = victim.getMedicalRecords();
        boolean correct = false;

        int i;
        for (i = 0; i < testMedical.size(); i++) {
            if (testMedical.get(i) == testRecord) {
                correct = true;
            }
        }
        assertTrue("addMedicalRecord should add medical record to victim", correct);
}


    @Test
    public void testRemoveInventoryItem() {
        Water water = new Water(victim);
        victim.addBelongings(water);
        victim.removeBelongings(water);

        ArrayList<InventoryItem> actualInventory = victim.getBelongings();
        boolean correct = true;

        for (InventoryItem item : actualInventory) {
            if (item.equals(water)) {
                correct = false;
                break;
            }
        }
    }
    public void testSetInventoryContents() {
        ArrayList<InventoryItem> newInventory = new ArrayList<>();
        newInventory.add(new Blanket(victim));
        newInventory.add(new Cot(2, "B2", victim));
        newInventory.add(new Water(victim));
        newInventory.add(new PersonalBelongings("Laptop", victim));
    
        victim.setBelongings(newInventory);
        ArrayList<InventoryItem> actualInventory = victim.getBelongings();
    
        boolean correct = true;
        for (int i = 0; i < newInventory.size(); i++) {
            if (!actualInventory.get(i).equals(newInventory.get(i))) {
                correct = false;
                break;
            }
        }
        assertTrue("Inventory contents should match the set inventory", correct);
    }

    @Test
    public void testSetPersonalBelongings() {
        PersonalBelongings item1 = new PersonalBelongings("Backpack", victim);
        PersonalBelongings item2 = new PersonalBelongings("Notebook", victim);
        ArrayList<InventoryItem> newItems = new ArrayList<>();
        newItems.add(item1);
        newItems.add(item2);
        
        victim.setBelongings(newItems);
        ArrayList<InventoryItem> actualItems = victim.getBelongings();
        
        boolean correct = true;
        
        if (newItems.size() != actualItems.size()) {
            correct = false;
        } else {
            for (int i = 0; i < newItems.size(); i++) {
                if (actualItems.get(i) != newItems.get(i)) {
                    correct = false;
                    break;
                }
            }
        }
        
        assertTrue("setPersonalBelongings should correctly update personal belongings", correct);
    }

    @Test
    public void testAutoIncrementedIdsAreSequential() {
        DisasterVictim victim1 = new DisasterVictim("Alice", "2025-04-01");
        victim1.setId();

        DisasterVictim victim2 = new DisasterVictim("Bob", "2025-04-01");
        victim2.setId();

        int expectedId = victim1.getId() + 1;
        assertEquals("Second DisasterVictim ID should be exactly one greater than the first", expectedId, victim2.getId());
    }

    @Test
    public void testSetIdManualGreaterThanHighest() {
        DisasterVictim victim = new DisasterVictim("Charlie", "2025-04-01");
        victim.setId(5000);
        assertEquals("Victim ID should be manually set to 5000", 5000, victim.getId());

        DisasterVictim newVictim = new DisasterVictim("Dana", "2025-04-01");
        newVictim.setId();
        assertEquals("Next auto-incremented ID should be 5001", 5001, newVictim.getId());
    }

}



