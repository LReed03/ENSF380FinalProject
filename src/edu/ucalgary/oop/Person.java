package edu.ucalgary.oop;

/**
 @author Landon Reed
 @version 1.0
 Created on: April 5, 2025
 */
public abstract class Person {
    protected String firstName;
    protected String lastName;
    protected Gender gender;
    protected FamilyGroup family;
    protected int personId;
    protected static int highestId = 0;

    /**
    Sets the family group of the person.
    @param family The family group to associate with the person.
     */
    public void setFamily(FamilyGroup family){
        this.family = family;
    }

    /**
    Retrieves the family group of the person.
    @return The family group associated with the person.
     */
    public FamilyGroup getFamily(){
        return this.family;
    }

    /**
    Sets the first name of the person.
    @param firstName The first name to set.
     */
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    /**
    Retrieves the first name of the person.
    @return The first name of the person.
     */
    public String getFirstName(){
        return this.firstName;
    }

    /**
    Sets the last name of the person.
    @param lastName The last name to set.
     */
    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    /**
    Retrieves the last name of the person.
    @return The last name of the person.
     */
    public String getLastName(){
        return this.lastName;
    }

    /**
    Sets the gender of the person.
    @param gender The gender to set.
     */
    public void setGender(Gender gender){
        this.gender = gender;
    }

    /**
    Retrieves the gender of the person.
    @return The gender of the person.
     */
    public Gender getGender(){
        return this.gender;
    }

    /**
    Sets the ID of the person.
    Updates the highest ID if the new ID is greater.
    @param id The new ID to set.
     */
    public void setId(int id){
        if(id > highestId){
            highestId = id;
        }
        this.personId = id;

    }

    /**
    Automatically assigns a new unique ID to the person.
    Increments the highest ID and assigns it to the person.
     */
    protected void setId(){
        highestId = highestId + 1;
        this.personId = highestId;
    }

    /**
    Retrieves the unique ID of the person.
    @return The ID of the person.
     */
    protected int getId(){
        return this.personId;
    }
}
