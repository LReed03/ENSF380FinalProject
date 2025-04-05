package edu.ucalgary.oop;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 @author Landon Reed
 @version 1.0
 Created on: April 5, 2025
 */
public class ReliefService {
    private Person inquirer;
    private Person missingPerson;
    private String dateOfInquiry;
    private String infoProvided;
    private Location lastKnownLocation;
    private int inquiryID;
    private static int highestId = 0;

    /**
    Constructor to initialize a relief service inquiry.
    @param inquirer The person making the inquiry.
    @param missingPerson The person being inquired about.
    @param dateOfInquiry The date of the inquiry in the format "YYYY-MM-DD".
    @param infoProvided Additional information provided during the inquiry.
    @param lastKnownLocation The last known location of the missing person.
     */
    public ReliefService(Person inquirer, Person missingPerson, String dateOfInquiry, String infoProvided, Location lastKnownLocation) throws IllegalArgumentException {
        this.inquirer = inquirer;
        this.missingPerson = missingPerson;
        this.dateOfInquiry = dateOfInquiry;
        this.infoProvided = infoProvided;
        this.lastKnownLocation = lastKnownLocation;
    }

    /**
    Retrieves the person making the inquiry.
    @return The inquirer.
     */
    public Person getInquirer() {
        return this.inquirer;
    }

    /**
    Sets the person making the inquiry.
    @param inquirer The inquirer to set.
     */
    public void setInquirer(Person inquirer) {
        this.inquirer = inquirer;
    }

    /**
    Retrieves the person being inquired about.
    @return The missing person.
     */
    public Person getMissingPerson() {
        return this.missingPerson;
    }

    /**
    Sets the person being inquired about.
    @param person The missing person to set.
     */
    public void setMissingPerson(Person person) {
        this.missingPerson = person;
    }

    /**
    Retrieves the date of the inquiry.
    @return The date of the inquiry in the format "YYYY-MM-DD".
     */
    public String getDateOfInquiry() throws IllegalArgumentException {
        return this.dateOfInquiry;
    }

    /**
    Sets the date of the inquiry.
    @param dateOfInquiry The date of the inquiry in the format "YYYY-MM-DD".
     */
    public void setDateOfInquiry(String dateOfInquiry) throws IllegalArgumentException {
        if(!isValidDateFormat(dateOfInquiry)){
            throw new IllegalArgumentException("Date must be YYYY-MM-DD Format");
        }
        this.dateOfInquiry = dateOfInquiry;
    }

    /**
    Retrieves the additional information provided during the inquiry.
    @return The information provided.
     */
    public String getInfoProvided() {
        return this.infoProvided;
    }

    /**
    Sets the additional information provided during the inquiry.
    @param info The information to set.
     */
    public void setInfoProvided(String info) {
        this.infoProvided = info;
    }

    /**
    Retrieves the last known location of the missing person.
    @return The last known location.
     */
    public Location getLastKnownLocation() {
        return this.lastKnownLocation;
    }

    /**
    Sets the last known location of the missing person.
    @param lastKnownLocation The location to set.
     */
    public void setLastKnownLocation(Location lastKnownLocation) {
        this.lastKnownLocation = lastKnownLocation;
    }

    /**
    Validates the format of a date string.
    @param date The date string to validate.
    return True if the date format is valid, false otherwise.
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
    Sets the unique ID of the inquiry.
    @param inquiryID The ID to set.
     */
    public void setId(int inquiryID) {
        this.inquiryID = inquiryID;
    }

    /**
     Retrieves the unique ID of the inquiry.
    @return The ID of the inquiry.
     */
    public int getId() {
        return this.inquiryID;
    }

    /**
    automatically assigns a new unique ID to the inquiry.
    Increments the highest ID and assigns it to the inquiry.
     */
    protected void setId() {
        highestId = highestId + 1;
        this.inquiryID = highestId;
    }
}