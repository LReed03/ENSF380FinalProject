package edu.ucalgary.oop;

public abstract class InventoryItem {
    protected DisasterVictim allocatedToPerson = null;
    protected Location allocatedToLocation = null;
    protected ItemType ITEMTYPE;


    public ItemType getItemType(){
        return this.ITEMTYPE;
    }


}
