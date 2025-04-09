package edu.ucalgary.oop;
/**
 @author Landon Reed
 @version 1.0
 Created on: April 5, 2025
 */
public class Cot extends InventoryItem {
    private int roomNumber;
    private String gridLocation;

    /**
    Constructs a Cot allocated to a disaster victim.
    @param rmNum        The room number where the cot is located.
    @param gridLocation The grid location of the cot.
    @param person       The disaster victim to whom the cot is allocated.
     */
    public Cot(int rmNum, String gridLocation, DisasterVictim person) {
        setRoomNumber(rmNum);              
        setGridLocation(gridLocation);   
        this.allocatedToPerson = person;
        this.ITEMTYPE = ItemType.COT;
    }

    /**
    Constructs a Cot allocated to a specific location.
    @param rmNum        The room number where the cot is located.
    @param gridLocation The grid location of the cot.
    @param location     The location to which the cot is allocated.
     */
    public Cot(int rmNum, String gridLocation, Location location) {
        setRoomNumber(rmNum);             
        setGridLocation(gridLocation);     
        this.allocatedToLocation = location;
        this.ITEMTYPE = ItemType.COT;
    }

    /**
    Sets the room number for the cot.
    @param rmNum The room number to set.
     */
    public void setRoomNumber(int rmNum) {
        if (rmNum < 100 || rmNum > 999) {
            throw new IllegalArgumentException("Room number must be a 3-digit number");
        }
        this.roomNumber = rmNum;
    }

    /**
    Gets the room number of the cot.
    @return The room number of the cot.
     */
    public int getRoomNumber() {
        return this.roomNumber;
    }

    /**
    Sets the grid location for the cot.
    @param gridLocation The grid location to set.
     */
    public void setGridLocation(String gridLocation) {
        if (gridLocation == null || gridLocation.trim().isEmpty() || !gridLocation.matches("^[A-Za-z]{1}\\d+$")) {
            throw new IllegalArgumentException("Invalid grid location format");
        }
        this.gridLocation = gridLocation;
    }

    /**
    Gets the grid location of the cot.
    @return The grid location of the cot.
     */
    public String getGridLocation() {
        return this.gridLocation;
    }
}
