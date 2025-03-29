package edu.ucalgary.oop;
import java.util.ArrayList;

public abstract class InventoryItem {
    protected DisasterVictim allocatedToPerson = null;
    protected Location allocatedToLocation = null;
    protected ItemType ITEMTYPE;


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

}
