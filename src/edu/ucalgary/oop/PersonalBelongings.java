package edu.ucalgary.oop;
/**
 @author Landon Reed
 @version 1.0
 Created on: April 5, 2025
 */
public class PersonalBelongings extends InventoryItem {
    private String description;

    /**
    Constructor to initialize personal belongings with a description and associated disaster victim.
    @param description A description of the personal belongings.
    @param person The disaster victim to whom the belongings are allocated.
     */
    public PersonalBelongings(String description, DisasterVictim person) throws IllegalArgumentException {
        if(description == null || description.length() == 0){
            throw new IllegalArgumentException("Description can not be empty");
        }
        this.description = description;
        this.allocatedToPerson = person;
        this.ITEMTYPE = ItemType.PERSONALBELONGINGS;
    }

    /**
    Overrides the method to prevent allocating personal belongings to a location.
    @param location The location to allocate to (ignored).
     */
    @Override
    public void setAllocatedToLocation(Location location){
        return;
    }

    /**
    Sets the description of the personal belongings.
    @param description The new description to set.
     */
    public void setDescription(String description){
        if(description == null || description.length() == 0){
            throw new IllegalArgumentException("Description can not be empty");
        }
        this.description = description;
    }

    /**
    Retrieves the description of the personal belongings.
    @return The description of the belongings.
     */
    public String getDescription(){
        return this.description;
    }
}
