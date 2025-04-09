package edu.ucalgary.oop;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CotTest {

    private Location testLocation;
    private DisasterVictim testVictim;

    @Before
    public void setUp() {
        testLocation = new Location("Test Location", "A101");
        testVictim = new DisasterVictim("John", "2023-01-01");
        testVictim.setLastName("Doe");
        testLocation.addOccupant(testVictim);
    }

    @Test
    public void testValidRoomNumberLowerBound() {
        Cot cot = new Cot(100, "A1", testLocation);
        assertEquals("Room number should be 100", 100, cot.getRoomNumber());
    }

    @Test
    public void testValidRoomNumberUpperBound() {
        Cot cot = new Cot(999, "B2", testLocation);
        assertEquals("Room number should be 999", 999, cot.getRoomNumber());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRoomNumberBelowLowerBound() {
        new Cot(99, "C3", testLocation);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRoomNumberAboveUpperBound() {
        new Cot(1000, "D4", testLocation);
    }

    @Test
    public void testValidGridLocationSingleLetter() {
        Cot cot = new Cot(101, "E5", testLocation);
        assertEquals("Grid location should be 'E5'", "E5", cot.getGridLocation());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyGridLocation() {
        new Cot(101, "", testLocation);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullGridLocation() {
        new Cot(101, null, testLocation);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidFormatGridLocation() {
        new Cot(101, "55", testLocation);  
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidFormatGridLocationLettersOnly() {
        new Cot(101, "AA", testLocation);  
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidFormatGridLocationDigitsOnly() {
        new Cot(101, "12", testLocation);  
    }

    @Test
    public void testCotAssignedToVictim() {
        Cot cot = new Cot(102, "F6", testVictim);
        assertEquals("Cot should be allocated to the correct victim", testVictim, cot.getAllocatedToPerson());
        assertNull("Cot should not be allocated to a location", cot.getAllocatedToLocation());
    }

    @Test
    public void testCotAssignedToLocation() {
        Cot cot = new Cot(103, "G7", testLocation);
        assertEquals("Cot should be allocated to the correct location", testLocation, cot.getAllocatedToLocation());
        assertNull("Cot should not be allocated to a person", cot.getAllocatedToPerson());
    }

    @Test
    public void testAutoIncrementedIdsAreUnique() {
        Cot cot1 = new Cot(104, "H1", testLocation);
        cot1.setId();  

        Cot cot2 = new Cot(105, "H2", testLocation);
        cot2.setId();

        assertNotEquals("Each cot should have a unique ID", cot1.getId(), cot2.getId());
        assertTrue("Second cot ID should be greater than first", cot2.getId() > cot1.getId());
    }

    @Test
    public void testSetIdManualGreaterThanHighest() {
        Cot cot = new Cot(106, "J1", testLocation);
        cot.setId(5000);
        assertEquals("Cot ID should be set to 5000", 5000, cot.getId());

        Cot newCot = new Cot(107, "J2", testLocation);
        newCot.setId();  
        assertEquals("Next auto-incremented ID should be 5001", 5001, newCot.getId());
    }
}

