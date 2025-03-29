package edu.ucalgary.oop;
import java.sql.*;

public class DBManager implements DBAccess{
    private final String URL = "jdbc:postgresssql://localhost:5432/oop";
    private final String USER = "oop";
    private final String PASSWORD = "ucalgary";
    private DBManager instance;
    private Connection connection;
    private ResultSet results;

    private DBManager(){
        try{
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
        }
        catch(SQLExcepion e){
            e.printStackTrace();
        }
    }

    public DBManager getInstance(){

    }

    public ArrayList<DisasterVictim> getAllDisasterVictims(){
        try{

        }
        catch(SQLExcepion e){
            e.printStackTrace();
        }

    }

    public void insertDisasterVictim(DisasterVictim victim){
        try{
            String query = "INSERT INTO Person (first_name, last_name, date_of_birth, gender, comments, phone_number, family_group) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement myStmt = connection.prepareStatement(query);

            myStmt.setString(1, victim.getFirstName());
            myStmt.setString(2, victim.getFirstName());
            myStmt.setDate(3, victim.getDateOfBirth());
            myStmt.setString(4, victim.getGender().toString());
            myStmt.setString(5, victim.getComments());
            myStmt.setString(6, victim.getPhone());
            myStmt.setInt(7, victim.getFamily().getFamilyID());

            int rowCount = myStmt.executeUpdate();
            System.out.println("Rows affected: " + rowCount);
            myStmt.close();
        }
        catch(SQLExcepion ex){
            ex.printStackTrace();
        }


    }

    public void updateDisasterVictim(DisasterVictim victim){

    }

    public void allocateInventoryToPerson(InventoryItem item, DisasterVictim victim){
        try{
            String query = "INSERT INTO SupplyAllocation (supply_id, person_id, location_id, allocation_date) VALUES (?,?,?,?)"
            PreparedStatement myStmt = connection.prepareStatement(query);

            myStmt.setInt(1, );
            myStmt.setInt(2, null);
            myStmt.setInt(3, );
            myStmt.setInt(4, item.getAllocationDate());
     
            int rowCount = myStmt.executeUpdate();
            System.out.println("Rows affected: " + rowCount);
            myStmt.close();
        }
        catch(SQLExcepion ex){
            ex.printStackTrace();
        }

    }

    public void allocateInventoryToLocation(InventoryItem item, Location location){

    }

    public void logInquiry(Person person, String message){

    }

    public void removeExpiredWater(){

    }

    public ArrayList<InventoryItem> getAllAvalibleInventory(){

    }

    public ArrayList<String> getallInquiries(){

    }

    public DisasterVictim getVictimById(int id){

    }

    public void close(){
        try{
            results.close();
            connection.close();
        }
        catch(SQLExcepion e){
            e.printStackTrace();
        }
    }



}
