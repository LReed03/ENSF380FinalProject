package edu.ucalgary.oop;

import org.junit.*;
import static org.junit.Assert.*;

public class InquirerTest {
    private String expectedFirstName = "Joseph";
    private String expectedLastName = "Bouillon";
    private String expectedPhoneNumber = "456-7890";
    private String expectedMessage = "Looking for my family members";
    private Inquirer inquirer;

    @Before
    public void setUp() {
        inquirer = new Inquirer(expectedFirstName, expectedLastName, expectedPhoneNumber, expectedMessage);
    }

    @Test
    public void testObjectCreation() {
        assertNotNull("Inquirer object should be created", inquirer);
    }

    @Test
    public void testGetFirstName() {
        assertEquals("getFirstName() should return inquirer's first name", expectedFirstName, inquirer.getFirstName());
    }

    @Test
    public void testSetFirstName() {
        String newFirstName = "Michael";
        inquirer.setFirstName(newFirstName);
        assertEquals("setFirstName() should update the inquirer's first name", newFirstName, inquirer.getFirstName());
    }

    @Test
    public void testGetLastName() {
        assertEquals("getLastName() should return inquirer's last name", expectedLastName, inquirer.getLastName());
    }

    @Test
    public void testSetLastName() {
        String newLastName = "Smith";
        inquirer.setLastName(newLastName);
        assertEquals("setLastName() should update the inquirer's last name", newLastName, inquirer.getLastName());
    }

    @Test
    public void testGetServicesPhoneNum() {
        assertEquals("getServicesPhoneNum() should return the correct Services Number", expectedPhoneNumber, inquirer.getPhone());
    }

    @Test
    public void testGetInfo() {
        assertEquals("getInfo() should return the inquirer message", expectedMessage, inquirer.getInfo());
    }

    @Test
    public void testAutoIncrementedInquirerIdsAreSequential() {
        Inquirer inquirer1 = new Inquirer("Alice", "Smith", "123-456-7890");
        inquirer1.setId();

        Inquirer inquirer2 = new Inquirer("Bob", "Johnson", "987-654-3210");
        inquirer2.setId();

        int expectedId = inquirer1.getId() + 1;
        assertEquals("Second Inquirer ID should be exactly one greater than the first", expectedId, inquirer2.getId());
    }

    @Test
    public void testSetInquirerIdManualGreaterThanHighest() {
        Inquirer inquirer = new Inquirer("Charlie", "Brown", "555-000-1234");
        inquirer.setId(7000);
        assertEquals("Inquirer ID should be manually set to 7000", 7000, inquirer.getId());

        Inquirer newInquirer = new Inquirer("Dana", "White", "555-111-5678");
        newInquirer.setId();
        assertEquals("Next auto-incremented ID should be 7001", 7001, newInquirer.getId());
    }

}