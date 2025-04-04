package edu.ucalgary.oop;

public class PersonalBelongings extends InventoryItem{
    private String description;

    public PersonalBelongings(String description, DisasterVictim person) throws IllegalArgumentException{
        if(description.length() == 0){
            throw new IllegalArgumentException("Description can not be empty");
        }
        this.description = description;
        this.allocatedToPerson = person;
        this.ITEMTYPE = ItemType.PERSONALBELONGINGS;
    }

    public void setAllocatedToPerson(DisasterVictim person){
        this.allocatedToPerson = person;
    }

    public void setDescription(String description){
        if(description.length() == 0){
            throw new IllegalArgumentException("Description can not be empty");
        }
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }
}
