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

    public void insertDisasterVictim(Person person){
        try{
            String query = "INSERT INTO Person (first_name, last_name, date_of_birth, gender, comments, phone_number, family_group) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement myStmt = connection.prepareStatement(query);

            myStmt.setString(1, person.getFirstName());
            myStmt.setString(2, person.getFirstName());
            myStmt.setDate(3, person.getDateOfBirth());
            myStmt.setString(4, person.getGender().toString());
            myStmt.setString(5, person.getComments());
            myStmt.setString(6, person.getPhone());
            myStmt.setInt(7, person.getFamily().getFamilyID());

            int rowCount = myStmt.executeUpdate();
            System.out.println("Rows affected: " + rowCount);
            myStmt.close();
        }
        catch(SQLExcepion ex){
            ex.printStackTrace();
        }


    }

    public void updateDisasterVictim(Person person) {
        try {
            String query = "UPDATE Person SET gender = ?, comments = ?, phone_number = ?, family_group = ? WHERE first_name = ? AND last_name = ? AND date_of_birth = ?";
            PreparedStatement myStmt = connection.prepareStatement(query);

            myStmt.setString(1, person.getGender().toString());
            myStmt.setString(2, person.getComments());
            myStmt.setString(3, person.getPhone());
            myStmt.setInt(4, person.getFamily().getFamilyID());
            myStmt.setString(5, person.getFirstName());
            myStmt.setString(6, person.getLastName());
            myStmt.setDate(7, person.getDateOfBirth());

            int rowCount = myStmt.executeUpdate();
            System.out.println("Rows affected: " + rowCount);
            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
}


    public void allocateInventoryToPerson(int itemId, int victimId){
        try{
            String query = "INSERT INTO SupplyAllocation (supply_id, person_id, location_id, allocation_date) VALUES (?,?,?,?)"
            PreparedStatement myStmt = connection.prepareStatement(query);

            myStmt.setInt(1, itemId);
            myStmt.setInt(2, victimId);
            myStmt.setInt(3, null);
            myStmt.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
     
            int rowCount = myStmt.executeUpdate();
            System.out.println("Rows affected: " + rowCount);
            myStmt.close();
        }
        catch(SQLExcepion ex){
            ex.printStackTrace();
        }

    }

    public void allocateInventoryToLocation(int itemId, int locationId){
        try{
            String query = "INSERT INTO SupplyAllocation (supply_id, person_id, location_id, allocation_date) VALUES (?,?,?,?)"
            PreparedStatement myStmt = connection.prepareStatement(query);

            myStmt.setInt(1, null);
            myStmt.setInt(2, victimId);
            myStmt.setInt(3, locationId);
            myStmt.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
     
            int rowCount = myStmt.executeUpdate();
            System.out.println("Rows affected: " + rowCount);
            myStmt.close();
        }
        catch(SQLExcepion ex){
            ex.printStackTrace();
        }

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

    public boolean checkPersonExists(Person person){
        boolean exists = false;
        try {
            String sql = "SELECT 1 FROM Person WHERE first_name = ? AND last_name = ? AND date_of_birth = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, person.getFirstName());
            stmt.setString(2, person.getLastName());
            stmt.setDate(3, person.getDateOfBirth());

            ResultSet rs = stmt.executeQuery();
            exists = rs.next(); 

            rs.close();
            stmt.close();
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return exists;
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
