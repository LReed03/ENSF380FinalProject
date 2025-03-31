package edu.ucalgary.oop;

public class ReliefService{
    private Person inquirer;
    private Person missingPerson;
    private String dateOfInquiry;
    private String infoProvided;
    private Location lastKnownLocation;
    private int inquiryID;

    public ReliefService(Person inquirer, Person missingPerson, String dateOfInquiry, Sting infoProvided, Location lastKnownLocation) throws IllegalArgumentException{
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

    private static boolean isValidDateFormat(){
        
    }

    public void setInquiryId(int inquiryID){
        this.inquiryID = inquiryID;
    }

    public int getInquiryId(){
        return this.inquiryID;
    }
}