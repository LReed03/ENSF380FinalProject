package edu.ucalgary.oop;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class LocationTest {
    private Location location;
    private DisasterVictim victim;
    private Blanket blanket;
    private Cot cot;
    private Water water;
    private PersonalBelongings belonging;

    @Before
    public void setUp() {
        location = new Location("Shelter A", "1234 Shelter Ave");
        victim = new DisasterVictim("John Doe", "2025-01-01");
        blanket = new Blanket(location);
        cot = new Cot(101, "A1", location);
        water = new Water(location);
        belonging = new PersonalBelongings("Backpack", victim);
    }

    @Test
    public void testObjectCreation() {
        assertNotNull("Location object should be successfully created", location);
    }

    @Test
    public void testConstructorValues() {
        assertEquals("Constructor should correctly set name", "Shelter A", location.getName());
        assertEquals("Constructor should correctly set address", "1234 Shelter Ave", location.getAddress());
    }

    @Test
    public void testSetName() {
        String newName = "Shelter B";
        location.setName(newName);
        assertEquals("setName should update location name", newName, location.getName());
    }

    @Test
    public void testSetAddress() {
        String newAddress = "4321 Shelter Blvd";
        location.setAddress(newAddress);
        assertEquals("setAddress should update location address", newAddress, location.getAddress());
    }

    @Test
    public void testAddOccupant() {
        location.addOccupant(victim);
        boolean found = false;
        for (DisasterVictim dv : location.getOccupants()) {
            if (dv.equals(victim)) {
                found = true;
                break;
            }
        }
        assertTrue("addOccupant should add a disaster victim", found);
    }

    @Test
    public void testRemoveOccupant() {
        location.addOccupant(victim);
        location.removeOccupant(victim);
        boolean found = false;
        for (DisasterVictim dv : location.getOccupants()) {
            if (dv.equals(victim)) {
                found = true;
                break;
            }
        }
        assertFalse("removeOccupant should remove the disaster victim", found);
    }

    @Test
    public void testRemoveNonExistentOccupant() {
        boolean found = false;
        for (DisasterVictim dv : location.getOccupants()) {
            if (dv.equals(victim)) {
                found = true;
                break;
            }
        }
        assertFalse("Removing a non-existent occupant should not change the list", found);
    }

    @Test
    public void testSetAndGetOccupants() {
        ArrayList<DisasterVictim> newOccupants = new ArrayList<>();
        newOccupants.add(victim);
        location.setOccupants(newOccupants);
        assertEquals("setOccupants should correctly replace the occupant list", newOccupants, location.getOccupants());
    }

    @Test
    public void testAddBlanketSupply() {
        location.addSupply(blanket);
        boolean found = false;
        for (InventoryItem item : location.getSupplies()) {
            if (item.equals(blanket)) {
                found = true;
                break;
            }
        }
        assertTrue("addSupply should add a blanket to the list", found);
    }

    @Test
    public void testAddCotSupply() {
        location.addSupply(cot);
        boolean found = false;
        for (InventoryItem item : location.getSupplies()) {
            if (item.equals(cot)) {
                found = true;
                break;
            }
        }
        assertTrue("addSupply should add a cot to the list", found);
    }

    @Test
    public void testAddWaterSupply() {
        location.addSupply(water);
        boolean found = false;
        for (InventoryItem item : location.getSupplies()) {
            if (item.equals(water)) {
                found = true;
                break;
            }
        }
        assertTrue("addSupply should add a water supply to the list", found);
    }

    @Test
    public void testRemoveBlanketSupply() {
        location.addSupply(blanket);
        location.removeSupply(blanket);
        boolean found = false;
        for (InventoryItem item : location.getSupplies()) {
            if (item.equals(blanket)) {
                found = true;
                break;
            }
        }
        assertFalse("removeSupply should remove a blanket from the list", found);
    }

    @Test
    public void testRemoveCotSupply() {
        location.addSupply(cot);
        location.removeSupply(cot);
        boolean found = false;
        for (InventoryItem item : location.getSupplies()) {
            if (item.equals(cot)) {
                found = true;
                break;
            }
        }
        assertFalse("removeSupply should remove a cot from the list", found);
    }

    @Test
    public void testRemoveWaterSupply() {
        location.addSupply(water);
        location.removeSupply(water);
        boolean found = false;
        for (InventoryItem item : location.getSupplies()) {
            if (item.equals(water)) {
                found = true;
                break;
            }
        }
        assertFalse("removeSupply should remove a water supply from the list", found);
    }

    @Test
    public void testRemoveNonExistentSupply() {
        boolean found = false;
        for (InventoryItem item : location.getSupplies()) {
            if (item.equals(blanket)) {
                found = true;
                break;
            }
        }
        assertFalse("Removing a non-existent supply should not change the list", found);
    }

    @Test
    public void testSetAndGetSupplies() {
        ArrayList<InventoryItem> newSupplies = new ArrayList<>();
        newSupplies.add(blanket);
        newSupplies.add(cot);
        newSupplies.add(water);
        location.setSupplies(newSupplies);
        assertEquals("setSupplies should correctly update the supply list", newSupplies, location.getSupplies());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testAddPersonalBelongingToLocation() {
        location.addSupply(belonging);
    }

    @Test
    public void testAutoIncrementedLocationIdsAreSequential() {
        Location loc1 = new Location("Shelter 1", "123 15 St");
        loc1.setId();

        Location loc2 = new Location("Shelter 2", "32 16 Ave");
        loc2.setId();

        int expectedId = loc1.getId() + 1;
        assertEquals("Second Location ID should be exactly one greater than the first", expectedId, loc2.getId());
    }

    @Test
    public void testSetLocationIdManualGreaterThanHighest() {
        Location loc = new Location("Shelter 1", "789 Reunion Blvd");
        loc.setId(9000);
        assertEquals("Location ID should be manually set to 9000", 9000, loc.getId());

        Location newLoc = new Location("Shelter 2", "101 Calgary Road");
        newLoc.setId();
        assertEquals("Next auto-incremented Location ID should be 9001", 9001, newLoc.getId());
    }
}