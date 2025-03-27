package edu.ucalgary.oop;

public interface DBAccess {

    public DBAccess getInstance();

    public ArrayList<DisasterVictim> getAllDisasterVictims();

    public void insertDisasterVictim(DisasterVictim victim);

    public void updateDisasterVictim(DisasterVictim victim);

    public void allocateInventoryToPerson(InventoryItem item, DisasterVictim victim);

    public void allocateInventoryToLocation(InventoryItem item, Location location);

    public void logInquiry(Person person, String message);

    public void removeExpiredWater();

    public ArrayList<InventoryItem> getAllAvalibleInventory();

    public ArrayList<String> getallInquiries();

    public DisasterVictim getVictimById(int id);


}
