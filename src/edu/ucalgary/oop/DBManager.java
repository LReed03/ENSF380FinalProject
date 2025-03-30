package edu.ucalgary.oop;
import java.sql.*;

public class DBManager implements DBAccess{
    private final String URL = "jdbc:postgresssql://localhost:5432/oop";
    private final String USER = "oop";
    private final String PASSWORD = "ucalgary";
    private static DBManager instance;
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

    public static DBManager getInstance(){
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public ArrayList<DisasterVictim> getAllDisasterVictims(){
        ArrayList<DisasterVictim> victims = new ArrayList<>();
        try{
            String query = "SELECT * FROM Person WHERE date_of_birth IS NOT NULL AND gender IS NOT NULL";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                DisasterVictim victim = new DisasterVictim();

                victim.setFirstName(result.getString("first_name"));
                victim.setLastName(result.getString("last_name"));
                victim.setDateOfBirth(result.getDate("date_of_birth"));
                victim.setGender(Gender.valueOf(result.getString("gender"))); 
                victim.setComments(result.getString("comments"));
                victim.setPhone(result.getString("phone_number"));

                int familyId = result.getInt("family_group");
                if (!result.wasNull()) {
                    Family fam = new Family();
                    fam.setFamilyID(familyId);
                    victim.setFamily(fam);
                }
                victims.add(victim);
            }
            result.close();
            stmt.close();
            }   
        catch (SQLException e) {
        e.printStackTrace();
        }

        return victims;
    }

public ArrayList<FamilyGroup> getAllFamilyGroups() {
    ArrayList<FamilyGroup> familyGroups = new ArrayList<>();

    try {
        String query = "SELECT * FROM Person WHERE family_group IS NOT NULL";
        PreparedStatement stmt = connection.prepareStatement(query);
        ResultSet result = stmt.executeQuery();

        while (result.next()) {
            int familyID = result.getInt("family_group");

            Person person = new Person();
            person.setFirstName(result.getString("first_name"));
            person.setLastName(result.getString("last_name"));
            person.setDateOfBirth(result.getDate("date_of_birth"));
            person.setGender(Gender.valueOf(result.getString("gender")));
            person.setComments(result.getString("comments"));
            person.setPhone(result.getString("phone_number"));

            FamilyGroup existingGroup = null;
            for (FamilyGroup group : familyGroups) {
                if (group.getFamilyID() == familyID) {
                    existingGroup = group;
                    break;
                }
            }
            if (existingGroup == null) {
                existingGroup = new FamilyGroup(familyID);
                familyGroups.add(existingGroup);
            }
            existingGroup.addFamilyMember(person);
        }
        result.close();
        stmt.close();
    } 
    catch (SQLException e) {
        e.printStackTrace();
    }

    return familyGroups;
}

public ArrayList<Inquirer> getAllInquirers() {
    ArrayList<Inquirer> inquirers = new ArrayList<>();

    try {
        String query = "SELECT * FROM Person WHERE phone_number IS NOT NULL";
        PreparedStatement stmt = connection.prepareStatement(query);
        ResultSet result = stmt.executeQuery();

        while (result.next()) {
            Inquirer inquirer = new Inquirer();

            inquirer.setFirstName(result.getString("first_name"));
            inquirer.setLastName(result.getString("last_name"));
            inquirer.setDateOfBirth(result.getDate("date_of_birth"));
            inquirer.setGender(Gender.valueOf(result.getString("gender")));
            inquirer.setComments(result.getString("comments"));
            inquirer.setPhone(result.getString("phone_number"));

            int familyId = result.getInt("family_group");
            if (!result.wasNull()) {
                FamilyGroup fam = new FamilyGroup(familyId);
                inquirer.setFamily(fam);
            }

            inquirers.add(inquirer);
        }

        result.close();
        stmt.close();

    } 
    catch (SQLException e) {
        e.printStackTrace();
    }

    return inquirers;
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

    public void logInquiry(int inquirerId, int seekingId, int locationId, String date, String comments){

    }

    public void removeExpiredWater() {
        try {
            String query = "DELETE FROM Inventory WHERE item_type = 'water' AND expiry_date < CURRENT_DATE";
            PreparedStatement stmt = connection.prepareStatement(query);

            int rowsDeleted = stmt.executeUpdate();
            System.out.println("Expired water items removed: " + rowsDeleted);

            stmt.close();
        } 
        catch (SQLException e) {
            e.printStackTrace();
            }
        }


    public ArrayList<InventoryItem> getAllAvalibleInventory(){

    }

    public ArrayList<ReliefService> getallInquiries(){

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
