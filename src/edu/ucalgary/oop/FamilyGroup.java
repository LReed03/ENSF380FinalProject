package edu.ucalgary.oop;
import java.util.ArrayList;

/**
 @author Landon Reed
 @version 1.0
 Created on: April 5, 2025
 */
public class FamilyGroup {
    private int familyId;
    private ArrayList<Person> familyMembers;
    private static int highestId = 0;

    /**
    Default constructor that initializes an empty family group.
     */
    public FamilyGroup(){
        this.familyMembers = new ArrayList<>();
    }
    
    /**
    Constructor that initializes a family group with a specific ID.
    @param familyId The ID of the family group.
     */
    public FamilyGroup(int familyId){
        highestId = familyId;
        this.familyId = familyId;
        this.familyMembers = new ArrayList<>();
    }

    /**
    Adds a person to the family group.
    @param person The person to be added to the family group.
     */
    public void addFamilyMember(Person person){
        this.familyMembers.add(person);
    }

    /**
    Removes a person from the family group.
    @param person The person to be removed from the family group.
     */
    public void removeFamilyMember(Person person){
        this.familyMembers.remove(person);
    }

    /**
    Retrieves the list of family members in the group.
    @return An ArrayList of family members.
     */
    public ArrayList<Person> getFamilyMembers(){
        return this.familyMembers;
    }

    /**
    Sets the list of family members in the group.
    @param family An ArrayList of family members to set.
     */
    public void setFamilyMembers(ArrayList<Person> family){
        this.familyMembers = family;
    }

    /**
    Retrieves the ID of the family group.
    @return The ID of the family group.
     */
    public int getId(){
        return this.familyId;
    }

    /**
    Sets the ID of the family group.
    Updates the highest ID if the new ID is greater.
    @param id The new ID to set for the family group.
     */
    public void setId(int id){
        highestId = id;
        this.familyId = id;
    }

    /**
    Automatically assigns a new unique ID to the family group.
    Increments the highest ID and assigns it to the family group.
     */
    public void setId(){
        highestId = highestId + 1;
        this.familyId = highestId;
    }
}
