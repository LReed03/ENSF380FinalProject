package edu.ucalgary.oop;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class BlanketTest {
    private Blanket blanket;
    private Blanket locationBlanket;
    private DisasterVictim victim1;
    private DisasterVictim victim2;
    private DisasterVictim victimWithoutLocation;
    private Location location1;
    private Location location2;
    private Blanket victimBlanket;

    @Before
    public void setUp() {
        location1 = new Location("Shelter A", "1234 Shelter Ave");
        location2 = new Location("Shelter B", "5678 Safe Haven");
        victim1 = new DisasterVictim("Jane", "2025-01-20");
        victim2 = new DisasterVictim("John", "2025-02-15");
        victimWithoutLocation = new DisasterVictim("NoLocation", "2025-03-10");
        location1.addOccupant(victim1);
        location2.addOccupant(victim2);
        blanket = new Blanket(location1);
        locationBlanket = new Blanket(location1); 
        victimBlanket = new Blanket(victim1);
    }

    @Test
    public void testConstructorWithVictim() {
        assertNotNull("Blanket object should be created", victimBlanket);
        assertEquals("Blanket should be assigned to victim1", victim1, victimBlanket.getAllocatedToPerson());
    }

    @Test
    public void testConstructorWithLocation() {
        assertNotNull("Blanket should be created with location", locationBlanket);
        assertEquals("Blanket should be allocated to location1", location1, locationBlanket.getAllocatedToLocation());
        assertNull("Blanket should not be allocated to a person", locationBlanket.getAllocatedToPerson());
    }

    @Test
    public void testSetAllocatedToLocation() {
        Blanket b = new Blanket(location1);
        b.setAllocatedToLocation(location2);
        assertEquals("Location should be updated to location2", location2, b.getAllocatedToLocation());
    }

    @Test
    public void testSetAllocatedToPersonAtSameLocation() {
        locationBlanket.setAllocatedToPerson(victim1);
        assertEquals("Should be allocated to victim1", victim1, locationBlanket.getAllocatedToPerson());
        assertNull("Should no longer be allocated to location", locationBlanket.getAllocatedToLocation());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetAllocatedToLocationWhenAlreadyAllocatedToPersonThrowsException() {
        locationBlanket.setAllocatedToPerson(victim1);
        locationBlanket.setAllocatedToLocation(location1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetAllocatedToPersonWithNoLocationThrowsException() {
        Blanket b = new Blanket(location1);
        b.setAllocatedToPerson(victimWithoutLocation);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetAllocatedToPersonWithDifferentLocationThrowsException() {
        Blanket b = new Blanket(location1);
        b.setAllocatedToPerson(victim2);
    }

    @Test
    public void testGetItemType() {
        assertEquals("Item type should be BLANKET", ItemType.BLANKET, blanket.getItemType());
    }

    @Test
    public void testSameLocationTrue() {
        assertTrue("Victim1 and blanket should have the same location", blanket.sameLocation(victim1));
    }

    @Test
    public void testSameLocationFalse() {
        assertFalse("Victim2 and blanket should not have the same location", blanket.sameLocation(victim2));
    }

    @Test
    public void testIsAllocatedToPersonTrue() {
        Blanket b = new Blanket(location1);
        b.setAllocatedToPerson(victim1);
        assertTrue("Blanket should be allocated to victim1", b.getAllocatedToPerson() != null);
    }

    @Test
    public void testIsAllocatedToPersonFalse() {
        Blanket b = new Blanket(location1);
        assertFalse("Blanket should not be allocated to any person", b.getAllocatedToPerson() != null);
    }

    @Test
    public void testSetAndGetId() {
        Blanket b = new Blanket(location1);
        int id = 999;
        b.setId(id);
        assertEquals("Blanket ID should be set and retrieved correctly", id, b.getId());
    }

    @Test
    public void testAutoIncrementedBlanketIdsAreSequential() {
        Blanket b1 = new Blanket(location1);
        b1.setId();

        Blanket b2 = new Blanket(location1);
        b2.setId();

        int expectedId = b1.getId() + 1;
        assertEquals("Blanket IDs should increment by 1", expectedId, b2.getId());
    }

    @Test
    public void testSetBlanketIdManualGreaterThanHighest() {
        Blanket b1 = new Blanket(location1);
        b1.setId(4000);
        assertEquals("Blanket ID should be manually set to 4000", 4000, b1.getId());

        Blanket b2 = new Blanket(location1);
        b2.setId();
        assertEquals("Next auto-incremented ID should be 4001", 4001, b2.getId());
    }
}
