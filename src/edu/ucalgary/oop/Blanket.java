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


}
