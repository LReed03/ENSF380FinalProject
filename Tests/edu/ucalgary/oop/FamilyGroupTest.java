package edu.ucalgary.oop;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class FamilyGroupTest {
    private FamilyGroup familyGroup1, familyGroup2;
    private Person person1, person2, person3;
    private ArrayList<Person> expectedFamilyMembers;

    @Before
    public void setUp() {
        familyGroup1 = new FamilyGroup();
        familyGroup2 = new FamilyGroup();
        person1 = new Inquirer("John", "Doe", "123-456-7890", "Relative");
        person2 = new Inquirer("Jane", "Doe", "987-654-3210", "Friend");
        person3 = new Inquirer("Alex", "Smith", "555-123-4567", "Sibling");
        expectedFamilyMembers = new ArrayList<>();
        expectedFamilyMembers.add(person1);
        expectedFamilyMembers.add(person2);
    }


    @Test
    public void testObjectCreation() {
        assertNotNull(familyGroup1);
    }


    @Test
    public void testFamilyIDIsAssigned() {
        assertTrue("Family ID should be a non-negative value", familyGroup1.getId() >= 0);
    }



    @Test
    public void testAddFamilyMember() {
        familyGroup1.addFamilyMember(person1);
        assertEquals("addFamilyMember should add a person to the family", 1, familyGroup1.getFamilyMembers().size());
    }

    @Test
    public void testAddDuplicateFamilyMember() {
        familyGroup1.addFamilyMember(person1);
        familyGroup1.addFamilyMember(person1); 
        assertEquals("Adding duplicate family member should not increase count", 1, familyGroup1.getFamilyMembers().size());
    }

    @Test
    public void testRemoveFamilyMember() {
        familyGroup1.addFamilyMember(person1);
        familyGroup1.removeFamilyMember(person1);
        assertEquals("removeFamilyMember should remove a person from the family", 0, familyGroup1.getFamilyMembers().size());
    }


    @Test
    public void testRemoveNonExistentFamilyMember() {
        familyGroup1.addFamilyMember(person1);
        familyGroup1.removeFamilyMember(person2); 
        assertEquals("Removing non-existent member should not change size", 1, familyGroup1.getFamilyMembers().size());
    }


    @Test
    public void testSetAndGetFamilyMembers() {
        familyGroup1.setFamilyMembers(expectedFamilyMembers);
        assertEquals("setFamilyMembers should correctly update the list", expectedFamilyMembers, familyGroup1.getFamilyMembers());
    }

    @Test
    public void testSetEmptyFamilyList() {
        ArrayList<Person> emptyList = new ArrayList<>();
        familyGroup1.setFamilyMembers(emptyList);
        assertEquals("setFamilyMembers should allow an empty list", 0, familyGroup1.getFamilyMembers().size());
    }

    @Test
    public void testAutoIncrementedFamilyIdsAreSequential() {
        FamilyGroup group1 = new FamilyGroup();
        group1.setId();

        FamilyGroup group2 = new FamilyGroup();
        group2.setId();

        int expectedId = group1.getId() + 1;
        assertEquals("Second FamilyGroup ID should be exactly one greater than the first", expectedId, group2.getId());
    }

    @Test
    public void testSetFamilyIdManualGreaterThanHighest() {
        FamilyGroup group = new FamilyGroup();
        group.setId(3000);
        assertEquals("FamilyGroup ID should be manually set to 3000", 3000, group.getId());

        FamilyGroup newGroup = new FamilyGroup();
        newGroup.setId();
        assertEquals("Next auto-incremented FamilyGroup ID should be 3001", 3001, newGroup.getId());
    }
}