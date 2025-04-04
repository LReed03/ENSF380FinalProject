package edu.ucalgary.oop;
import java.util.ArrayList;

public abstract class InventoryItem {
    protected DisasterVictim allocatedToPerson = null;
    protected Location allocatedToLocation = null;
    protected ItemType ITEMTYPE;
    protected int itemId;
    protected static int highestId;


    public ItemType getItemType(){
        return this.ITEMTYPE;
    }

    protected boolean isAllocatedToPerson(){
        if(allocatedToPerson == null){
            return false;
        }
        else{
            return true;
        }
    }

    protected boolean sameLocation(DisasterVictim person){
        ArrayList<DisasterVictim> occupants = this.allocatedToLocation.getOccupants();
        for(DisasterVictim occupant: occupants){
            if(occupant == person){
                return true;
            }
        }
        return false;
    }

    protected void setId(int id){
        highestId = id;
        this.itemId = id;
    }

    protected void setId(){
        highestId = highestId + 1;
        this.itemId = highestId;
    }
    
    public int getId(){
        return this.itemId;
    }

    public DisasterVictim getAllocatedToPerson(){
        return this.allocatedToPerson;
    }

    public Location getAllocatedToLocation(){
        return this.allocatedToLocation;
    }

    public void setAllocatedToLocation(Location location) throws IllegalArgumentException{
        if(this.isAllocatedToPerson()){
            throw new IllegalArgumentException("Cannot be allocated to a location if it already is allocated to a person");
        }
        this.allocatedToLocation = location;
    }

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
