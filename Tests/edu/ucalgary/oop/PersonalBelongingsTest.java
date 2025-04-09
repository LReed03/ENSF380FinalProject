package edu.ucalgary.oop;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PersonalBelongingsTest {
    private DisasterVictim victim;
    private PersonalBelongings belongings;

    @Before
    public void setUp() {
        victim = new DisasterVictim("Alice", "2025-02-10");
        belongings = new PersonalBelongings("Blue Backpack", victim);
    }

    @Test
    public void testObjectCreation() {
        assertNotNull("PersonalBelongings object should be created", belongings);
    }

    @Test
    public void testSetAndGetDescription() {
        String newDescription = "Red Suitcase";
        belongings.setDescription(newDescription);
        assertEquals("setDescription should update the description", newDescription, belongings.getDescription());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetDescriptionWithNull() {
        belongings.setDescription(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetDescriptionWithEmptyString() {
        belongings.setDescription("");
    }

    @Test
    public void testGetAllocatedToPerson() {
        assertEquals("PersonalBelongings should be allocated to the correct victim", victim, belongings.getAllocatedToPerson());
    }

    @Test
    public void testGetItemType() {
        assertEquals("Item type should be PERSONALBELONGINGS", ItemType.PERSONALBELONGINGS, belongings.getItemType());
    }

    @Test
    public void testSetAllocatedToLocationDoesNothing() {
        Location dummyLocation = new Location("Dummy", "123 Street");
        belongings.setAllocatedToLocation(dummyLocation);
        assertNull("AllocatedToLocation should remain null for PersonalBelongings", belongings.getAllocatedToLocation());
    }

    @Test
    public void testSetAndGetId() {
        PersonalBelongings item = new PersonalBelongings("Wallet", victim);
        item.setId(999);
        assertEquals("ID should be set and retrieved correctly", 999, item.getId());
    }

    @Test
    public void testAutoIncrementedPersonalBelongingsIdsAreSequential() {
        PersonalBelongings item1 = new PersonalBelongings("Bag", victim);
        item1.setId();

        PersonalBelongings item2 = new PersonalBelongings("Phone", victim);
        item2.setId();

        int expectedId = item1.getId() + 1;
        assertEquals("Second ID should be exactly one greater than the first", expectedId, item2.getId());
    }

    @Test
    public void testSetIdManualGreaterThanHighest() {
        PersonalBelongings item = new PersonalBelongings("Laptop", victim);
        item.setId(8000);
        assertEquals("ID should be manually set to 8000", 8000, item.getId());

        PersonalBelongings newItem = new PersonalBelongings("Notebook", victim);
        newItem.setId();
        assertEquals("Next auto-incremented ID should be 8001", 8001, newItem.getId());
    }
}
