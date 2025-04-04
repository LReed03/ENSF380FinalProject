package edu.ucalgary.oop;

public class Blanket extends InventoryItem{
    
    public Blanket(DisasterVictim person){
        this.allocatedToPerson = person;
        this.ITEMTYPE = ItemType.BLANKET;
    }
    public Blanket(Location location){
        this.allocatedToLocation = location;
        this.ITEMTYPE = ItemType.BLANKET;
    }

    public void setAllocatedToPerson(DisasterVictim person) throws IllegalArgumentException{
        if(!this.sameLocation(person)){
            throw new IllegalArgumentException("Item must be in the same location as the victim");
        }
        else{
            this.allocatedToPerson = person;
        }
       
    }

    public void setAllocatedToLocation(Location location) throws IllegalArgumentException{
        if(this.isAllocatedToPerson()){
            throw new IllegalArgumentException("Cannot be allocated to a location if it already is allocated to a person");
        }
        this.allocatedToLocation = location;
    }


}
