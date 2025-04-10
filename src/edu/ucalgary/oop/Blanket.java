package edu.ucalgary.oop;

/**
 @author Landon Reed
 @version 1.0
 Created on: April 5, 2025
 */
public class Blanket extends InventoryItem {

    /**
    Constructs a Blanket object and allocates it to a disaster victim
    @param person The disaster victim to whom the blanket is allocated
     */
    public Blanket(DisasterVictim person) {
        this.allocatedToPerson = person;
        this.ITEMTYPE = ItemType.BLANKET;
    }

    /**
    Constructs a Blanket object and allocates it to a specific location
    @param location The location to which the blanket is allocated
     */
    public Blanket(Location location) {
        this.allocatedToLocation = location;
        this.ITEMTYPE = ItemType.BLANKET;
    }
}
