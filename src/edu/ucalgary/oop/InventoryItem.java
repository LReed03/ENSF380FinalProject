package edu.ucalgary.oop;
import java.util.ArrayList;

/**
 @author Landon Reed
 @version 1.0
 Created on: April 5, 2025
 */
public abstract class InventoryItem {
    protected DisasterVictim allocatedToPerson = null;
    protected Location allocatedToLocation = null;
    protected ItemType ITEMTYPE;
    protected int itemId;
    protected static int highestId = 0;

    /**
    Retrieves the type of the inventory item
    @return The item type as an ItemType enum
     */
    public ItemType getItemType(){
        return this.ITEMTYPE;
    }

    /**
    Checks if the item is allocated to a person
    @return True if allocated to a person, false otherwise
     */
    protected boolean isAllocatedToPerson(){
        if(allocatedToPerson == null){
            return false;
        }
        else{
            return true;
        }
    }

    /**
    Checks if the item is in the same location as the specified disaster victim
    @param person The disaster victim to check against
    @return True if in the same location, false otherwise
     */
    public boolean sameLocation(DisasterVictim person) {
        if (this.allocatedToLocation == null || person == null) {
            return false;
        }
    
        ArrayList<DisasterVictim> occupants = this.allocatedToLocation.getOccupants();
        return occupants != null && occupants.contains(person);
    }
    

    /**
    Sets the ID of the inventory item
    Updates the highest ID if the new ID is greater
    @param id The new ID to set
     */
    public void setId(int id){
        if(id > highestId){
            highestId = id;
        }
        this.itemId = id;
    }

    /**
    Automatically assigns a new unique ID to the inventory item
    Increments the highest ID and assigns it to the item
     */
    public void setId(){
        highestId = highestId + 1;
        this.itemId = highestId;
    }
    
    /**
    Gets the ID of the inventory item
    @return The ID of the item
     */
    public int getId(){
        return this.itemId;
    }

    /**
    Gets the disaster victim to whom the item is allocated
    @return The allocated disaster victim, or null if not allocated
     */
    public DisasterVictim getAllocatedToPerson(){
        return this.allocatedToPerson;
    }

    /**
    Retrieves the location to which the item is allocated
    @return The allocated location, or null if not allocated
     */
    public Location getAllocatedToLocation(){
        return this.allocatedToLocation;
    }

    /**
    Allocates the item to a location
    @param location The location to allocate the item to
     */
    public void setAllocatedToLocation(Location location) throws IllegalArgumentException{
        if(this.isAllocatedToPerson()){
            throw new IllegalArgumentException("Cannot be allocated to a location if it already is allocated to a person");
        }
        this.allocatedToLocation = location;
    }

    /**
    Allocates the item to a disaster victim
    @param person The disaster victim to allocate the item to
     */
    public void setAllocatedToPerson(DisasterVictim person) throws IllegalArgumentException{
        if(!this.sameLocation(person)){
            throw new IllegalArgumentException("Item must be in the same location as the victim");
        }
        else{
            this.allocatedToPerson = person;
            this.allocatedToLocation = null;
        }
       
    }

}
