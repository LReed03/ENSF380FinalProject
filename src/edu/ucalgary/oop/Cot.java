package edu.ucalgary.oop;

public class Cot extends InventoryItem{
    private int roomNumber;
    private String gridLocation;

    public Cot(int rmNum, String gridLocation, DisasterVictim person){
        this.roomNumber = rmNum;
        this.gridLocation = gridLocation;
        this.allocatedToPerson = person;
        this.ITEMTYPE = ItemType.COT;
    }

    public Cot(int rmNum, String gridLocation, Location location){
        this.roomNumber = rmNum;
        this.gridLocation = gridLocation;
        this.allocatedToLocation = location;
        this.ITEMTYPE = ItemType.COT;
    }

    public void setRoomNumber(int rmNum){

    }

    public int getRoomNumber(){
        return this.roomNumber;
    }

    public void setGridLocation(String gridLocation){

    }

    public String getGridLocaiton(){
        return this.gridLocation;
    }



}
