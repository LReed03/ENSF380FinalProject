package edu.ucalgary.oop;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class WaterTest {
    private DisasterVictim victim;
    private Location location;
    private Water waterForPerson;
    private Water waterForLocation;

    @Before
    public void setUp() {
        victim = new DisasterVictim("John Doe", "2025-01-10");
        location = new Location("Shelter A", "1234 Aid Street");
        waterForPerson = new Water(victim);
        waterForLocation = new Water(location);
    }

    @Test
    public void testWaterAllocatedToPerson() {
        assertEquals("Water should be allocated to the correct person", victim, waterForPerson.getAllocatedToPerson());
        assertNull("Water allocated to a person should not be allocated to a location", waterForPerson.getAllocatedToLocation());
    }

    @Test
    public void testWaterAllocatedToLocation() {
        assertEquals("Water should be allocated to the correct location", location, waterForLocation.getAllocatedToLocation());
        assertNull("Water allocated to a location should not be allocated to a person", waterForLocation.getAllocatedToPerson());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidAllocationToBothPersonAndLocation() {
        Water invalidWater = new Water(victim);
        invalidWater.setAllocatedToLocation(location);
    }

    @Test
    public void testWaterAvailabilityByDefault() {
        assertTrue("Water should be available by default", waterForPerson.getAvalible());
    }

    @Test
    public void testSetWaterAvailability() {
        waterForPerson.setAvalible(false);
        assertFalse("Water availability should be updated to false", waterForPerson.getAvalible());
    }


    @Test
    public void testWaterIsNotExpiredOnSameDay() {
        waterForPerson.setAllocationDate("2025-01-01");
        assertFalse("Water should not be expired on the same day", waterForPerson.isExpired("2025-01-01"));
    }

    @Test
    public void testWaterIsNotExpiredOneDayLater() {
        waterForPerson.setAllocationDate("2025-01-01");
        assertFalse("Water should not be expired one day later", waterForPerson.isExpired("2025-01-02"));
    }

    @Test
    public void testWaterIsExpiredAfterTwoDays() {
        waterForPerson.setAllocationDate("2025-01-01");
        assertTrue("Water should be expired two days later", waterForPerson.isExpired("2025-01-03"));
    }

    @Test
    public void testUseWaterMarksAsUnavailableIfExpired() {
        waterForPerson.setAllocationDate("2025-01-01");
        waterForPerson.useWater("2025-01-03");
        assertFalse("Water should be marked unavailable after expiration", waterForPerson.getAvalible());
    }

    @Test
    public void testUseWaterKeepsAvailableIfNotExpired() {
        waterForPerson.setAllocationDate("2025-01-01");
        waterForPerson.useWater("2025-01-02");
        assertTrue("Water should remain available if not expired", waterForPerson.getAvalible());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testInvalidDateFormatForAllocation() {
        waterForPerson.setAllocationDate("01-01-2025");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidDateFormatForExpirationCheck() {
        waterForPerson.setAllocationDate("2025-01-01");
        waterForPerson.isExpired("01-03-2025");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidStringForAllocation() {
        waterForPerson.setAllocationDate("not-a-date");
    }

    @Test
    public void testValidDateForAllocation() {
        waterForPerson.setAllocationDate("2025-01-10");
        assertEquals("Allocation date should be correctly set", "2025-01-10", waterForPerson.getAllocationDate());
    }


    @Test
    public void testSetAndGetId() {
        Water w = new Water(victim);
        w.setId(1234);
        assertEquals("Water ID should be set and retrieved correctly", 1234, w.getId());
    }

    @Test
    public void testAutoIncrementedWaterIdsAreSequential() {
        Water w1 = new Water(location);
        w1.setId();

        Water w2 = new Water(location);
        w2.setId();

        int expectedId = w1.getId() + 1;
        assertEquals("Second water ID should be one greater than the first", expectedId, w2.getId());
    }

    @Test
    public void testSetWaterIdManualGreaterThanHighest() {
        Water w1 = new Water(location);
        w1.setId(5000);
        assertEquals("Water ID should be set to 5000", 5000, w1.getId());

        Water w2 = new Water(location);
        w2.setId();
        assertEquals("Next auto-incremented ID should be 5001", 5001, w2.getId());
    }
}
