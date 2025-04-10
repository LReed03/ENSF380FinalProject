package edu.ucalgary.oop;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 @author Landon Reed
 @version 1.0
 Created on: April 5, 2025
 */
public class DisasterVictim extends Person {
    private String dateOfBirth;
    private ArrayList<MedicalRecord> medicalRecords = new ArrayList<>();
    private ArrayList<InventoryItem> belongings = new ArrayList<>();
    private final String ENTRY_DATE;
    private String comments;

    /**
    Constructor for DisasterVictim with first name and entry date
	@param firstName  The first name of the disaster victim
	@param ENTRY_DATE The entry date in the format "YYYY-MM-DD"
     */
    public DisasterVictim(String firstName, String ENTRY_DATE) {
        boolean validEntryDate = isValidDateFormat(ENTRY_DATE);
        if (validEntryDate == false) {
            throw new IllegalArgumentException("Invalid date");
        }
        this.ENTRY_DATE = ENTRY_DATE;
        this.firstName = firstName;
    }

    /**
    Constructor for DisasterVictim with first name, entry date, and date of birth
	@param firstName   The first name of the disaster victim
	@param ENTRY_DATE  The entry date in the format "YYYY-MM-DD"
	@param dateOfBirth The date of birth in the format "YYYY-MM-DD"
     */
    public DisasterVictim(String firstName, String ENTRY_DATE, String dateOfBirth) throws IllegalArgumentException {
        int entryDateInt = convertDateStringToInt(ENTRY_DATE);
        int dateOfBirthInt = convertDateStringToInt(dateOfBirth);
        if (dateOfBirthInt > entryDateInt) {
            throw new IllegalArgumentException("Cant have birthday after the entry date");
        }
        boolean validEntryDate = isValidDateFormat(ENTRY_DATE);
        if (validEntryDate == false) {
            throw new IllegalArgumentException("Invalid date");
        }
        this.ENTRY_DATE = ENTRY_DATE;
        this.firstName = firstName;
        boolean validDOB = isValidDateFormat(dateOfBirth);
        if (validDOB == false) {
            throw new IllegalArgumentException("Invalid date");
        }
        this.dateOfBirth = dateOfBirth;
    }

    /**
    Gets the date of birth of the disaster victim
    @return The date of birth in the format "YYYY-MM-DD"
     */
    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    /**
    Sets the date of birth of the disaster victim.
    @param dateOfBirth The date of birth in the format "YYYY-MM-DD"
     */
    public void setDateOfBirth(String dateOfBirth) throws IllegalArgumentException {
        boolean valid = isValidDateFormat(dateOfBirth);
        if (valid == false) {
            throw new IllegalArgumentException("Not a valid DOB");
        }
        if (valid == true) {
            this.dateOfBirth = dateOfBirth;
        }

    }

    /**
    Gets the list of medical records associated with the disaster victim
    @return An ArrayList of MedicalRecord objects
     */
    public ArrayList<MedicalRecord> getMedicalRecords() {
        return this.medicalRecords;
    }

    /**
     Gets the list of belongings associated with the disaster victim
    @return An ArrayList of InventoryItem objects
     */
    public ArrayList<InventoryItem> getBelongings() {
        return this.belongings;
    }

    /**
	Sets the medical records for the disaster victim
    @param records An ArrayList of MedicalRecord objects
     */
    public void setMedicalRecords(ArrayList<MedicalRecord> records) {
        this.medicalRecords = records;
    }

    /**
    Sets the belongings for the disaster victim
    @param belongings An ArrayList of InventoryItem objects
     */
    public void setBelongings(ArrayList<InventoryItem> belongings) {
        this.belongings = belongings;
    }

    /**
    Adds an item to the belongings of the disaster victim
    @param supply The InventoryItem to add
     */
    public void addBelongings(InventoryItem supply) {
        this.belongings.add(supply);
    }

    /**
    Removes an item from the belongings of the disaster victim
    @param unwantedSupply The InventoryItem to remove
     */
    public void removeBelongings(InventoryItem unwantedSupply) {
        this.belongings.remove(unwantedSupply);

    }

    /**
    Adds a medical record to the disaster victim's records
    @param record The MedicalRecord to add
     */
    public void addMedicalRecord(MedicalRecord record) {
        this.medicalRecords.add(record);
    }

    /**
    Gets the entry date of the disaster victim
    @return The entry date in the format "YYYY-MM-DD"
     */
    public String getEntryDate() {
        return this.ENTRY_DATE;
    }

    /**
    Gets the comments associated with the disaster victim
    @return The comments as a String
     */
    public String getComments() {
        return this.comments;
    }

    /**
    Sets the comments for the disaster victim
    @param comments The comments as a String
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
    Validates if a date string is in the format "YYYY-MM-DD"
    @param date The date string to validate
    @return True if the date format is valid, false otherwise
     */
    private static boolean isValidDateFormat(String date) {
        String dateRegex = "^\\d{4}[-]{1}\\d{2}[-]\\d{2}$";
        Pattern myPattern = Pattern.compile(dateRegex);
        Matcher mymatcher = myPattern.matcher(date);
        if (mymatcher.find()) {
            return true;
        } else {
            return false;
        }

    }

    /**
    Converts a date string in the format "YYYY-MM-DD" to an integer
    @param dateStr The date string to convert
    @return The integer representation of the date
     */
    private static int convertDateStringToInt(String dateStr) {
        dateStr = dateStr.replace("-", "");
        int dateInt = Integer.parseInt(dateStr);
        return dateInt;
    }

}



