package edu.ucalgary.oop;
import java.util.ArrayList;
/**
 @author Landon Reed
 @version 1.0
 Created on: April 5, 2025
 */
public class Location {
    private String name;
    private String address;
    private ArrayList<DisasterVictim> occupants;
    private ArrayList<InventoryItem> supplies;
    private int locationId;
    private static int highestId = 0;

    /**
    Constructor to initialize a location with a name and address.
    @param name The name of the location.
    @param address The address of the location.
     */
    public Location(String name, String address) {
        if (name == null || address == null) {
            throw new IllegalArgumentException("Name and address cannot be null.");
        }
        this.name = name;
        this.address = address;
        this.occupants = new ArrayList<>();
        this.supplies = new ArrayList<>();
    }

    /**
    Retrieves the name of the location.
    @return The name of the location.
     */
    public String getName() {
        return this.name;
    }

    /**
    Sets the name of the location.
    @param name The new name of the location.
     */
    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null.");
        }
        this.name = name;
    }

    /**
    Retrieves the address of the location.
    @return The address of the location.
     */
    public String getAddress() {
        return this.address;
    }

    /**
    Sets the address of the location.
    @param address The new address of the location.
     */
    public void setAddress(String address) {
        if (address == null) {
            throw new IllegalArgumentException("Address cannot be null.");
        }
        this.address = address;
    }

    /**
    Retrieves the list of occupants at the location.
    @return An ArrayList of disaster victims at the location.
     */
    public ArrayList<DisasterVictim> getOccupants() {
        return this.occupants;
    }

    /**
    Sets the list of occupants at the location.
    @param occupants An ArrayList of disaster victims to set.
     */
    public void setOccupants(ArrayList<DisasterVictim> occupants) {
        this.occupants = occupants;
    }

    /**
    Retrieves the list of supplies at the location.
    @return An ArrayList of inventory items at the location.
     */
    public ArrayList<InventoryItem> getSupplies() {
        return this.supplies;
    }

    /**
    Sets the list of supplies at the location.
    @param supplies An ArrayList of inventory items to set.
     */
    public void setSupplies(ArrayList<InventoryItem> supplies) throws IllegalArgumentException {
        for(int i = 0; i < supplies.size(); i++){
            if(supplies.get(i).getItemType() == ItemType.PERSONALBELONGINGS){
                throw new IllegalArgumentException("Locations cannot store personal belongings");
            }
        }
        this.supplies = supplies;
    }

    /**
    Adds a disaster victim to the location's occupants.
    @param occupant The disaster victim to add.
     */
    public void addOccupant(DisasterVictim occupant) {
        this.occupants.add(occupant);
    }

    /**
    Removes a disaster victim from the location's occupants.
    @param occupant The disaster victim to remove.
     */
    public void removeOccupant(DisasterVictim occupant) {
        this.occupants.remove(occupant);
    }

    /**
    Adds an inventory item to the location's supplies.
    param supply The inventory item to add.
     */
    public void addSupply(InventoryItem supply) throws IllegalArgumentException {
        if(supply.getItemType() == ItemType.PERSONALBELONGINGS){
            throw new IllegalArgumentException("Locations cannot store personal belongings");
        }
        this.supplies.add(supply);
    }

    /**
    Removes an inventory item from the location's supplies.
    @param supply The inventory item to remove.
     */
    public void removeSupply(InventoryItem supply) {
        this.supplies.remove(supply);
    }

    /**
    Retrieves the unique ID of the location.
    @return The ID of the location.
     */
    public int getId() {
        return locationId; 
    }

    /**
    Sets the ID of the location.
    Updates the highest ID if the new ID is greater.
    @param id The new ID to set.
     */
    public void setId(int id) {
        highestId = id;
        this.locationId = id;
    }

    /**
    Automatically assigns a new unique ID to the location.
    Increments the highest ID and assigns it to the location.
     */
    public void setId(){
        highestId = highestId + 1;
        this.locationId = highestId;
    }
}