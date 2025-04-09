package edu.ucalgary.oop;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class ReliefServiceTest {
    private ReliefService reliefService;
    private Person inquirer;
    private Person missingPerson;
    private String dateOfInquiry;
    private String infoProvided;
    private Location lastKnownLocation;
    private DisasterVictim disasterVictim;
    private ArrayList<String> inquiries;

    @Before
    public void setUp() {
        this.inquirer = new Inquirer("Alice", "Smith", "1234567890");
        this.missingPerson = new DisasterVictim("John Doe", "2025-01-01");
        this.dateOfInquiry = "2025-03-11";
        this.infoProvided = "Seen at Shelter A";
        this.lastKnownLocation = new Location("Shelter A", "140 8 Ave NW");
        this.disasterVictim = new DisasterVictim("Jane Doe", "2025-02-02");
        this.inquiries = new ArrayList<>();

        this.reliefService = new ReliefService(inquirer, missingPerson, dateOfInquiry, infoProvided, lastKnownLocation);
    }

    @Test
    public void testGetInquirer() {
        assertEquals("getInquirer should return the correct person", inquirer, reliefService.getInquirer());
    }

    @Test
    public void testSetInquirer() {
        Inquirer newInquirer = new Inquirer("Bob", "Johnson", "0987654321");
        reliefService.setInquirer(newInquirer);
        assertEquals("setInquirer should update the inquirer", newInquirer, reliefService.getInquirer());
    }

    @Test
    public void testGetMissingPerson() {
        assertEquals("getMissingPerson should return the correct person", missingPerson, reliefService.getMissingPerson());
    }

    @Test
    public void testSetMissingPerson() {
        Person newMissingPerson = new DisasterVictim("Jane Doe", "2025-02-02");
        reliefService.setMissingPerson(newMissingPerson);
        assertEquals("setMissingPerson should update the missing person", newMissingPerson, reliefService.getMissingPerson());
    }

    @Test
    public void testGetDateOfInquiry() {
        assertEquals("getDateOfInquiry should return the correct date", dateOfInquiry, reliefService.getDateOfInquiry());
    }

    @Test
    public void testSetDateOfInquiry() {
        String newDate = "2025-03-12";
        reliefService.setDateOfInquiry(newDate);
        assertEquals("setDateOfInquiry should update the date", newDate, reliefService.getDateOfInquiry());
    }

    @Test
    public void testGetInfoProvided() {
        assertEquals("getInfoProvided should return the correct info", infoProvided, reliefService.getInfoProvided());
    }

    @Test
    public void testSetInfoProvided() {
        String newInfo = "Seen at Shelter B";
        reliefService.setInfoProvided(newInfo);
        assertEquals("setInfoProvided should update the info", newInfo, reliefService.getInfoProvided());
    }

    @Test
    public void testGetLastKnownLocation() {
        assertEquals("getLastKnownLocation should return the correct location", lastKnownLocation, reliefService.getLastKnownLocation());
    }

    @Test
    public void testSetLastKnownLocation() {
        Location newLocation = new Location("Shelter B", "150 8 Ave NW");
        reliefService.setLastKnownLocation(newLocation);
        assertEquals("setLastKnownLocation should update the location", newLocation, reliefService.getLastKnownLocation());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetDateOfInquiryWithInvalidFormat() {
        reliefService.setDateOfInquiry("03-11-2025");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetDateOfInquiryWithNonDateString() {
        reliefService.setDateOfInquiry("Not a date");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetDateOfInquiryWithEmptyString() {
        reliefService.setDateOfInquiry("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetDateOfInquiryWithNullInput() {
        reliefService.setDateOfInquiry(null);
    }

    @Test
    public void testReliefServiceWithDisasterVictimInquirer() {
        DisasterVictim inquirerVictim = new DisasterVictim("Alice", "1990-01-01");
        DisasterVictim missingPerson = new DisasterVictim("Bob", "2000-02-02");
        Location location = new Location("Main Shelter", "101 Street Ave");
        String date = "2025-04-09";
        String comments = "Looking for Bob after the flood";

        ReliefService inquiry = new ReliefService(inquirerVictim, missingPerson, date, comments, location);

        assertEquals("Inquirer should be the disaster victim", inquirerVictim, inquiry.getInquirer());
    }

    @Test
    public void testSetAndGetId() {
        ReliefService service = new ReliefService(inquirer, missingPerson, "2025-04-01", "Info here", lastKnownLocation);
        int id = 999;
        service.setId(id);
        assertEquals("ReliefService ID should be set and retrieved correctly", id, service.getId());
    }
    
    @Test
    public void testAutoIncrementedReliefServiceIdsAreSequential() {
        ReliefService service1 = new ReliefService(inquirer, missingPerson, "2025-04-01", "Info 1", lastKnownLocation);
        service1.setId();
    
        ReliefService service2 = new ReliefService(inquirer, missingPerson, "2025-04-02", "Info 2", lastKnownLocation);
        service2.setId();
    
        int expectedId = service1.getId() + 1;
        assertEquals("Second ReliefService ID should be exactly one greater than the first", expectedId, service2.getId());
    }
    
    @Test
    public void testSetReliefServiceIdManualGreaterThanHighest() {
        ReliefService service = new ReliefService(inquirer, missingPerson, "2025-04-01", "Manual ID test", lastKnownLocation);
        service.setId(7000);
        assertEquals("Manually set ID should be 7000", 7000, service.getId());
    
        ReliefService newService = new ReliefService(inquirer, missingPerson, "2025-04-02", "Auto ID test", lastKnownLocation);
        newService.setId();
        assertEquals("Next auto-incremented ID should be 7001", 7001, newService.getId());
    }

}
