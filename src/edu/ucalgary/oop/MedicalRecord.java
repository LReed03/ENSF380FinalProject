package edu.ucalgary.oop;

import java.util.regex.*;
/**
 @author Landon Reed
 @version 1.0
 Created on: April 5, 2025
 */
public class MedicalRecord {
    private Location location;
    private String treatmentDetails;
    private String dateOfTreatment;
    private int medicalRecordId;
    private static int highestId = 0;

    /**
    Constructor to initialize a medical record with location, treatment details, and date of treatment
    @param location The location where the treatment occurred
    @param treatmentDetails The details of the treatment
    @param dateOfTreatment The date of the treatment in the format "YYYY-MM-DD"
     */
    public MedicalRecord(Location location, String treatmentDetails, String dateOfTreatment) throws IllegalArgumentException{
        boolean validEntryDate = isValidDateFormat(dateOfTreatment);
        if (validEntryDate == false){
			throw new IllegalArgumentException("Invalid date");
		}
        this.dateOfTreatment = dateOfTreatment;
        this.treatmentDetails = treatmentDetails;
        this.location = location;
    }

    /**
    Retrieves the location associated with the medical record
    @return The location of the treatment
     */
    public Location getLocation(){
        return this.location;
    }

    /**
    Sets the location associated with the medical record
    @param location The new location of the treatment
     */
    public void setLocation(Location location){
        this.location = location;
    }

    /**
    Retrieves the treatment details of the medical record
    @return The treatment details as a string
     */
    public String getTreatmentDetails(){
        return this.treatmentDetails;
    }

    /**
    Sets the treatment details of the medical record
    @param treatmentDetails The new treatment details
     */
    public void setTreatmentDetails(String treatmentDetails){
        this.treatmentDetails = treatmentDetails;
    }

    /**
    Retrieves the date of treatment
    @return The date of treatment in the format "YYYY-MM-DD"
     */
    public String getDateOfTreatment(){
        return this.dateOfTreatment;
    }

    /**
    Sets the date of treatment
    @param date The new date of treatment in the format "YYYY-MM-DD"
     */
    public void setDateOfTreatment(String date) throws IllegalArgumentException {
        if (!isValidDateFormat(date)) {
            throw new IllegalArgumentException("Invalid date");
        }
        this.dateOfTreatment = date;
    }

    /**
    Validates the format of a date string
    @param date The date string to validate
    @return True if the date format is valid, false otherwise
     */
    private static boolean isValidDateFormat(String date){
        if(date == null){
            return false;
        }
        String dateRegex = "^\\d{4}[-]{1}\\d{2}[-]\\d{2}$";
        Pattern myPattern = Pattern.compile(dateRegex);
        Matcher mymatcher = myPattern.matcher(date);
        if(mymatcher.find()){
            return true;
        }
        else{
            return false;
        }
    
	}

    /**
    Retrieves the unique ID of the medical record
    @return The ID of the medical record
     */
    public int getId() {
        return this.medicalRecordId;
    }

    /**
    Sets the ID of the medical record
    Updates the highest ID if the new ID is greater
    @param id The new ID to set
     */
    public void setId(int id) {
        if(id > highestId){
            highestId = id;
        }
        this.medicalRecordId = id;
    }

    /**
    Automatically assigns a new unique ID to the medical record
    Increments the highest ID and assigns it to the record
     */
    public void setId(){
        highestId = highestId + 1;
        this.medicalRecordId = highestId;
    }
}
