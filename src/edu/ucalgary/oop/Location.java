package edu.ucalgary.oop;
import java.util.ArrayList;

public class Location {
    private String name;
    private String address;
    private ArrayList<DisasterVictim> occupants;
    private ArrayList<InventoryItem> supplies;
    private int locationId;
    private static int highestId = 0;

    public Location(String name, String address) {
        if (name == null || address == null) {
            throw new IllegalArgumentException("Name and address cannot be null.");
        }
        this.name = name;
        this.address = address;
        this.occupants = new ArrayList<>();
        this.supplies = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null.");
        }
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        if (address == null) {
            throw new IllegalArgumentException("Address cannot be null.");
        }
        this.address = address;
    }

    public ArrayList<DisasterVictim> getOccupants() {
        return this.occupants;
    }

    public void setOccupants(ArrayList<DisasterVictim> occupants) {
        this.occupants = occupants;
    }

    public ArrayList<InventoryItem> getSupplies() {
        return this.supplies;
    }

    public void setSupplies(ArrayList<InventoryItem> supplies) throws IllegalArgumentException{
        for(int i = 0; i < supplies.size(); i++){
            if(supplies.get(i).getItemType() == ItemType.PERSONALBELONGINGS){
                throw new IllegalArgumentException("Locations cannot store personal belongings");
            }
        }
        this.supplies = supplies;
    }

    public void addOccupant(DisasterVictim occupant) {
        this.occupants.add(occupant);
    }

    public void removeOccupant(DisasterVictim occupant) {
        this.occupants.remove(occupant);
    }

    public void addSupply(InventoryItem supply) throws IllegalArgumentException{
        if(supply.getItemType() == ItemType.PERSONALBELONGINGS){
            throw new IllegalArgumentException("Locations cannot store personal belongings");
        }
        this.supplies.add(supply);
    }

    public void removeSupply(InventoryItem supply) {
        this.supplies.remove(supply);
    }

    public int getId() {
        return locationId; 
    }

    public void setId(int id) {
        highestId = id;
        this.locationId = id;
    }

    public void setId(){
        highestId = highestId + 1;
        this.locationId = highestId;
    }
}