package edu.ucalgary.oop;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReliefService{
    private Person inquirer;
    private Person missingPerson;
    private String dateOfInquiry;
    private String infoProvided;
    private Location lastKnownLocation;
    private int inquiryID;
    private static int highestId = 0;
    

    public ReliefService(Person inquirer, Person missingPerson, String dateOfInquiry, String infoProvided, Location lastKnownLocation) throws IllegalArgumentException{
        this.inquirer = inquirer;
        this.missingPerson = missingPerson;
        this.dateOfInquiry = dateOfInquiry;
        this.infoProvided = infoProvided;
        this.lastKnownLocation = lastKnownLocation;
    }

    public Person getInquirer(){
        return this.inquirer;
    }

    public void setInquirer(Person inquirer){
        this.inquirer = inquirer;
    }

    public void setMissingPerson(Person person){
        this.missingPerson = person;
    }

    public Person getMissingPerson(){
        return this.missingPerson;
    }

    public void setDateOfInquiry(){
        this.dateOfInquiry = dateOfInquiry;
    }

    public String getDateOfInquiry() throws IllegalArgumentException{
        return this.dateOfInquiry;
    }

    public void setInfoProvided(String info){
        this.infoProvided = info;
    }

    public String getInfoProvided(){
        return this.infoProvided;
    }

    public Location getLastKnownLocation(){
        return this.lastKnownLocation;
    }

    public void setLastKnownLocation(){
        this.lastKnownLocation = lastKnownLocation;
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

    public void setId(int inquiryID){
        this.inquiryID = inquiryID;
    }

    public int getId(){
        return this.inquiryID;
    }

    protected void setId(){
        highestId = highestId + 1;
        this.inquiryID = highestId;
    }
}