package edu.ucalgary.oop;

import org.junit.Test;
import static org.junit.Assert.*;

public class MedicalRecordTest {

    private Location expectedLocation = new Location("ShelterA", "140 8 Ave NW ");
    private String expectedTreatmentDetails = "Broken arm treated";
    private String expectedDateOfTreatment = "2025-01-19";
    private String validDateOfTreatment = "2025-02-04";
    private String inValidDateOfTreatment = "2025/02/04";

    private MedicalRecord medicalRecord = new MedicalRecord(expectedLocation, expectedTreatmentDetails,
            expectedDateOfTreatment);

    @Test
    public void testObjectCreation() {
        assertNotNull("MedicalRecord object should be created", medicalRecord);
    }

    @Test
    public void testGetLocation() {
        assertEquals("getLocation should return the correct Location", expectedLocation, medicalRecord.getLocation());
    }

    @Test
    public void testSetLocation() {
        Location newExpectedLocation = new Location("Shelter B", "150 8 Ave NW ");
        medicalRecord.setLocation(newExpectedLocation);
        assertEquals("setLocation should update the Location name", newExpectedLocation.getName(),
                medicalRecord.getLocation().getName());
    }

    @Test
    public void testGetTreatmentDetails() {
        assertEquals("getTreatmentDetails should return the correct treatment details", expectedTreatmentDetails,
                medicalRecord.getTreatmentDetails());
    }

    @Test
    public void testSetTreatmentDetails() {
        String newExpectedTreatment = "No surgery required";
        medicalRecord.setTreatmentDetails(newExpectedTreatment);
        assertEquals("setTreatmentDetails should update the treatment details", newExpectedTreatment,
                medicalRecord.getTreatmentDetails());
    }

    @Test
    public void testGetDateOfTreatment() {
        assertEquals("getDateOfTreatment should return the correct date of treatment", expectedDateOfTreatment,
                medicalRecord.getDateOfTreatment());
    }

    @Test
    public void testSetDateOfTreatment() {
        String newExpectedDateOfTreatment = "2025-02-05";
        medicalRecord.setDateOfTreatment(newExpectedDateOfTreatment);
        assertEquals("setDateOfTreatment should update date of treatment", newExpectedDateOfTreatment,
                medicalRecord.getDateOfTreatment());
    }

    @Test
    public void testSetDateOfTreatmentWithValidFormat() {
        medicalRecord.setDateOfTreatment(validDateOfTreatment);
        assertEquals("setDateOfTreatment should accept a valid date format", validDateOfTreatment,
                medicalRecord.getDateOfTreatment());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetDateOfTreatmentWithInvalidFormat() {
        medicalRecord.setDateOfTreatment(inValidDateOfTreatment);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetDateOfTreatmentWithNonDateInput() {
        medicalRecord.setDateOfTreatment(expectedTreatmentDetails);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetDateOfTreatmentWithEmptyString() {
        medicalRecord.setDateOfTreatment("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetDateOfTreatmentWithNullInput() {
        medicalRecord.setDateOfTreatment(null);
    }

    @Test
    public void testAutoIncrementedMedicalRecordIdsAreUnique() {
        Location testLocation = new Location("Clinic A", "123 Health Ave");

        MedicalRecord record1 = new MedicalRecord(testLocation, "First aid treatment", "2025-04-01");
        record1.setId();

        MedicalRecord record2 = new MedicalRecord(testLocation, "Follow-up treatment", "2025-04-02");
        record2.setId();

        int expectedId = record1.getId() + 1;
        assertEquals("MedicalRecord IDs should increment by 1", expectedId, record2.getId());
    }

    @Test
    public void testSetMedicalRecordIdManualGreaterThanHighest() {
        Location testLocation = new Location("Clinic B", "456 Recovery Rd");

        MedicalRecord record = new MedicalRecord(testLocation, "Surgery", "2025-04-03");
        record.setId(8000);
        assertEquals(8000, record.getId());

        MedicalRecord newRecord = new MedicalRecord(testLocation, "Checkup", "2025-04-04");
        newRecord.setId();
        assertEquals(8001, newRecord.getId());
    }
}