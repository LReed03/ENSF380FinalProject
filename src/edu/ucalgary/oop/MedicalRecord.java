package edu.ucalgary.oop;

import java.util.regex.*;

public class MedicalRecord {
    private Location location;
    private String treatmentDetails;
    private String dateOfTreatment;
    private int medicalRecordId;
    private static int highestId = 0;

    public MedicalRecord(Location location, String treatmentDetails, String dateOfTreatment) throws IllegalArgumentException{
        boolean validEntryDate = isValidDateFormat(dateOfTreatment);
        if (validEntryDate == false){
			throw new IllegalArgumentException("Invalid date");
		}
        this.dateOfTreatment = dateOfTreatment;
        this.treatmentDetails = treatmentDetails;
        this.location = location;
    }

    public Location getLocation(){
        return this.location;
    }

    public void setLocation(Location location){
        this.location = location;
    }

    public String getTreatmentDetails(){
        return this.treatmentDetails;
    }

    public void setTreatmentDetails(String treatmentDetails){
        this.treatmentDetails = treatmentDetails;
    }

    public String getDateOfTreatment(){
        return this.dateOfTreatment;
    }

    public void setDateOfTreatment(String date) throws IllegalArgumentException{
        boolean validEntryDate = isValidDateFormat(dateOfTreatment);
        if (validEntryDate == false){
			throw new IllegalArgumentException("Invalid date");
		}
        this.dateOfTreatment = date;
    }


    private static boolean isValidDateFormat(String date){
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

    public int getId() {
        return this.medicalRecordId;
    }

    public void setId(int id) {
        highestId = id;
        this.medicalRecordId = id;
    }

    public void setId(){
        highestId = highestId + 1;
        this.medicalRecordId = highestId;
    }


}
