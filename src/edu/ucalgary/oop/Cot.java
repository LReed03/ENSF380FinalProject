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
