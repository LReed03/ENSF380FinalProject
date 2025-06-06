package edu.ucalgary.oop;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
/**
 @author Landon Reed
 @version 1.0
 Created on: April 5, 2025
 */
public class DBManager implements DBAccess{
    private final String URL = "jdbc:postgresql://localhost:5432/ensf380project";
    private final String USER = "oop";
    private final String PASSWORD = "ucalgary";
    private static DBManager instance;
    private static LanguageManager languageManager;
    private Connection connection;
    private ResultSet results;

    /**
    Private constructor to establish a database connection
    Initializes the LanguageManager instance
     */
    private DBManager(){
        try{
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
            this.languageManager = LanguageManager.getInstance();
        }
        catch(SQLException e){
            ErrorLog error = new ErrorLog(e);
            System.exit(1); 
        }
    }

    /**
    Returns the singleton instance of DBManager
    @return The singleton instance of DBManager
     */
    public static DBManager getInstance(){
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    /**
    Retrieves all disaster victims from the database
    @param families A list of family groups to associate with victims
    @return A list of DisasterVictim objects
     */
    @Override
    public ArrayList<DisasterVictim> getAllDisasterVictims(ArrayList<FamilyGroup> families) {
        ArrayList<DisasterVictim> victims = new ArrayList<>();
    
        try {
            String query = "SELECT * FROM Person WHERE date_of_birth IS NOT NULL AND gender IS NOT NULL";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet result = stmt.executeQuery();
    
            while (result.next()) {
                String firstName = result.getString("first_name");
                String entryDate = java.time.LocalDate.now().toString(); 
                DisasterVictim victim = new DisasterVictim(firstName, entryDate);
    
                victim.setId(result.getInt("person_id"));
                victim.setLastName(result.getString("last_name"));
                java.sql.Date dob = result.getDate("date_of_birth");
                victim.setDateOfBirth(dob.toString()); 
                victim.setGender(Gender.fromString(result.getString("gender")));
                victim.setComments(result.getString("comments"));
                int familyId = result.getInt("family_group");
                if (!result.wasNull()) {
                    FamilyGroup fam = findFamilyGroupById(families, familyId);
                    if (fam != null) {
                        fam.addFamilyMember(victim);
                        victim.setFamily(fam);
                    }
                }
                victims.add(victim);
            }
    
            result.close();
            stmt.close();
    
        } 
        catch (SQLException e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }
    
        return victims;
    }
    
    /**
    Retrieves all family groups from the database
    @return A list of FamilyGroup objects
     */
    @Override
    public ArrayList<FamilyGroup> getFamilyGroups() {
        ArrayList<FamilyGroup> groups = new ArrayList<>();
    
        try {
            String query = "SELECT DISTINCT family_group FROM Person WHERE family_group IS NOT NULL";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
    
            while (rs.next()) {
                int familyId = rs.getInt("family_group");
                groups.add(new FamilyGroup(familyId));
            }
    
            rs.close();
            stmt.close();
        } 
        catch (SQLException e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }
    
        return groups;
    }

    /**
    Assigns disaster victims to locations based on database records
    @param victims   A list of DisasterVictim objects
    @param locations A list of Location objects
     */
    @Override
    public void assignVictimsToLocations(ArrayList<DisasterVictim> victims, ArrayList<Location> locations) {
    try {
        String query = "SELECT person_id, location_id FROM PersonLocation";
        PreparedStatement stmt = connection.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int personId = rs.getInt("person_id");
            int locationId = rs.getInt("location_id");

            DisasterVictim matchedVictim = null;
            Location matchedLocation = null;

            for (DisasterVictim v : victims) {
                if (v.getId() == personId) {
                    matchedVictim = v;
                    break;
                }
            }

            for (Location loc : locations) {
                if (loc.getId() == locationId) {
                    matchedLocation = loc;
                    break;
                }
            }

            if (matchedVictim != null && matchedLocation != null) {
                matchedLocation.addOccupant(matchedVictim);
            }
        }

        rs.close();
        stmt.close();
    } 
    catch (SQLException e) {
        ErrorLog error = new ErrorLog(e);
        System.out.println(languageManager.getTranslation("UnexpectedError"));
        System.exit(1);
    }
}

    /**
    Retrieves all inquirers from the database
    @param families A list of family groups to associate with inquirers
    @return A list of Inquirer objects
     */
    @Override
    public ArrayList<Inquirer> getAllInquirers(ArrayList<FamilyGroup> families) {
        ArrayList<Inquirer> inquirers = new ArrayList<>();
    
        try {
            String query = "SELECT * FROM Person WHERE phone_number IS NOT NULL";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet result = stmt.executeQuery();
    
            while (result.next()) {
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String phone = result.getString("phone_number");
                Inquirer inquirer = new Inquirer(firstName, lastName, phone);
    
                inquirer.setId(result.getInt("person_id"));
                int familyId = result.getInt("family_group");
                if (!result.wasNull()) {
                    FamilyGroup fam = findFamilyGroupById(families, familyId);
                    if (fam != null) {
                        fam.addFamilyMember(inquirer);
                        inquirer.setFamily(fam);
                    }
                }
    
                inquirers.add(inquirer);
            }
    
            result.close();
            stmt.close();
    
        } 
        catch (SQLException e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }
    
        return inquirers;
    }
    

    /**
    Inserts a new disaster victim into the database
    @param victim The DisasterVictim object to insert
     */
    @Override
    public void insertDisasterVictim(DisasterVictim victim) {
        try {
            String query = "INSERT INTO Person (first_name, last_name, date_of_birth, gender, comments, family_group) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement myStmt = connection.prepareStatement(query);
    
            myStmt.setString(1, victim.getFirstName());
            myStmt.setString(2, victim.getLastName());
            myStmt.setDate(3, java.sql.Date.valueOf(victim.getDateOfBirth())); 
            myStmt.setString(4, Gender.toSQL(victim.getGender()));
            myStmt.setString(5, victim.getComments());
            if(victim.getFamily() != null){
                myStmt.setInt(6, victim.getFamily().getId());
            }
            else{
                myStmt.setNull(6, java.sql.Types.INTEGER);
            }
            myStmt.executeUpdate();
            myStmt.close();
        } 
        catch (SQLException e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }
    }

    /**
    Inserts a new inquirer into the database
    @param inquirer The Inquirer object to insert
     */
    @Override
    public void insertInquirer(Inquirer inquirer) {
        try {
            String query = "INSERT INTO Person (first_name, last_name, phone_number, family_group) VALUES (?, ?, ?, ?)";
            PreparedStatement myStmt = connection.prepareStatement(query);
    
            myStmt.setString(1, inquirer.getFirstName());
            myStmt.setString(2, inquirer.getLastName());
            myStmt.setString(3, inquirer.getPhone());
            if(inquirer.getFamily() != null){
                myStmt.setInt(4, inquirer.getFamily().getId());
            }
            else{
                myStmt.setNull(4, java.sql.Types.INTEGER);
            }
            myStmt.executeUpdate();
            myStmt.close();
        } 
        catch (SQLException e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }
    }
    
    /**
    adds a new supply to the database
    @param type     The type of supply
    @param comments Additional comments about the supply
     */
    @Override
    public void addNewSupply(String type, String comments, InventoryItem item){
        try{
            String query = "INSERT INTO Supply (supply_id, type, comments) VALUES (?,?,?)";
            PreparedStatement myStmt = connection.prepareStatement(query);
            myStmt.setInt(1, item.getId());
            myStmt.setString(2, type);
            myStmt.setString(3, comments);
            myStmt.executeUpdate();

            myStmt.close();
        }
        catch (SQLException e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }
    }
    
    /**
    Updates an existing disaster victim in the database
    @param victim The DisasterVictim object with updated information
     */
    @Override
    public void updateDisasterVictim(DisasterVictim victim) {
        try {
            String query = "UPDATE Person SET gender = ?, comments = ?, family_group = ? WHERE first_name = ? AND last_name = ? AND date_of_birth = ?";
            PreparedStatement myStmt = connection.prepareStatement(query);
    
            myStmt.setString(1, Gender.toSQL(victim.getGender()));
            myStmt.setString(2, victim.getComments());
            if(victim.getFamily() != null){
                myStmt.setInt(3, victim.getFamily().getId());
            }
            else{
                myStmt.setNull(3, java.sql.Types.INTEGER);
            }
            myStmt.setString(4, victim.getFirstName());
            myStmt.setString(5, victim.getLastName());
            myStmt.setDate(6, java.sql.Date.valueOf(victim.getDateOfBirth()));
    
            myStmt.executeUpdate();
            myStmt.close();
        } 
        catch (SQLException e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }
    }
    
    /**
    Updates an existing inquirer in the database
    @param inquirer The Inquirer object with updated information
     */
    @Override
    public void updateInquirer(Inquirer inquirer) {
        try {
            String query = "UPDATE Person SET phone_number = ?, family_group = ? WHERE first_name = ? AND last_name = ?";
            PreparedStatement myStmt = connection.prepareStatement(query);
    
            myStmt.setString(1, inquirer.getPhone());
            if(inquirer.getFamily() != null){
                myStmt.setInt(2, inquirer.getFamily().getId());
            }
            else{
                myStmt.setNull(2, java.sql.Types.INTEGER);
            }
            myStmt.setString(3, inquirer.getFirstName());
            myStmt.setString(4, inquirer.getLastName());
    
            myStmt.executeUpdate();

            myStmt.close();
        } 
        catch (SQLException e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }
    }
    
    /**
    Allocates an inventory item to a disaster victim
    @param itemId   The ID of the inventory item
    @param victimId The ID of the disaster victim
     */
    @Override
    public void allocateInventoryToPerson(int itemId, int victimId){
        try{
            String query = "INSERT INTO SupplyAllocation (supply_id, person_id, location_id, allocation_date) VALUES (?,?,?,?)";
            PreparedStatement myStmt = connection.prepareStatement(query);

            myStmt.setInt(1, itemId);
            myStmt.setInt(2, victimId);
            myStmt.setNull(3, java.sql.Types.INTEGER);
            myStmt.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
     
            myStmt.executeUpdate();
            myStmt.close();
        }
        catch(SQLException e){
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }

    }

    /**
     Allocates an inventory item to a location
    @param itemId     The ID of the inventory item
    @param locationId The ID of the location
     */
    @Override
    public void allocateInventoryToLocation(int itemId, int locationId){
        try{
            String query = "INSERT INTO SupplyAllocation (supply_id, person_id, location_id, allocation_date) VALUES (?,?,?,?)";
            PreparedStatement myStmt = connection.prepareStatement(query);

            myStmt.setInt(1, itemId);
            myStmt.setNull(2 , java.sql.Types.INTEGER);
            myStmt.setInt(3, locationId);
            myStmt.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
            myStmt.executeUpdate();
            myStmt.close();
        }
        catch(SQLException e){
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }

    }

    /**
    Logs an inquiry in the database.
    @param inquirerId The ID of the inquirer
    @param seekingId  The ID of the person being sought
    @param locationId The ID of the location
    @param date       The date of the inquiry
    @param comments   Additional comments about the inquiry
     */
    @Override
    public void logInquiry(int inquirerId, int seekingId, int locationId, Timestamp date, String comments) {
        try {
            String query = """
                INSERT INTO Inquiry (inquirer_id, seeking_id, location_id, date_of_inquiry, comments)
                VALUES (?, ?, ?, ?, ?)
            """;
    
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, inquirerId);
            stmt.setInt(2, seekingId);
            stmt.setInt(3, locationId);
            stmt.setTimestamp(4, date); 
            stmt.setString(5, comments);
    
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        } 
    }

    /**
    Removes expired water supplies from the database
     */
    @Override
    public void removeExpiredWater() {
        try {
            String deleteAllocationsQuery = """
                DELETE FROM SupplyAllocation
                USING Supply
                WHERE SupplyAllocation.supply_id = Supply.supply_id
                AND Supply.type = 'water'
                AND SupplyAllocation.allocation_date < CURRENT_DATE - INTERVAL '1 day'
            """;

            PreparedStatement stmt1 = connection.prepareStatement(deleteAllocationsQuery);
            stmt1.executeUpdate();
            stmt1.close();

            String deleteSupplyQuery = """
                DELETE FROM Supply
                WHERE type = 'water'
                AND supply_id NOT IN (SELECT supply_id FROM SupplyAllocation)
            """;

            PreparedStatement stmt2 = connection.prepareStatement(deleteSupplyQuery);
            stmt2.executeUpdate();
            stmt2.close();

        } 
        catch (SQLException e) {

            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }
    }

    
    /**
    Retrieves all inventory items from the database
    @param victims   A list of DisasterVictim objects
    @param locations A list of Location objects
    @return A list of InventoryItem objects
     */
    @Override
    public ArrayList<InventoryItem> getAllInventory(ArrayList<DisasterVictim> victims, ArrayList<Location> locations) {
        ArrayList<InventoryItem> items = new ArrayList<>();
    
        try {
            String query = """
                SELECT sa.supply_id, s.type, s.comments, sa.person_id, sa.location_id, sa.allocation_date
                FROM SupplyAllocation sa
                JOIN Supply s ON sa.supply_id = s.supply_id
            """;
    
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
    
            while (rs.next()) {
                int supplyId = rs.getInt("supply_id");
                String type = rs.getString("type").toLowerCase();
                String comments = rs.getString("comments");
                int personId = rs.getInt("person_id");
                int locationId = rs.getInt("location_id");
                String allocationDate = rs.getTimestamp("allocation_date").toLocalDateTime().toLocalDate().toString();
    
                DisasterVictim person = null;
                if (personId > 0) {
                    for (DisasterVictim v : victims) {
                        if (v.getId() == personId) {
                            person = v;
                            break;
                        }
                    }
                }
    
                Location location = null;
                if (locationId > 0) {
                    for (Location loc : locations) {
                        if (loc.getId() == locationId) {
                            location = loc;
                            break;
                        }
                    }
                }
    
                InventoryItem item = null;
    
                switch (type) {
                    case "blanket" -> {
                        item = (person != null) ? new Blanket(person) : (location != null ? new Blanket(location) : null);
                    }
                    case "water" -> {
                        item = (person != null) ? new Water(person) : (location != null ? new Water(location) : null);
                        if (item instanceof Water water) {
                            water.setAllocationDate(allocationDate); 
                        }
                    }
                    case "cot" -> {
                        if (comments != null && comments.matches("^\\d{3} [A-Za-z]\\d+$")) {
                            String[] parts = comments.split(" ");
                            int roomNum = Integer.parseInt(parts[0]);
                            String grid = parts[1];
                            item = (person != null) ? new Cot(roomNum, grid, person) : (location != null ? new Cot(roomNum, grid, location) : null);
                        }
                    }
                    case "personal item" -> {
                        if (person != null) {
                            String desc = (comments != null) ? comments : "unspecified";
                            item = new PersonalBelongings(desc, person);
                        }
                    }
                }
    
                if (item != null) {
                    item.setId(supplyId);
                    items.add(item);
                }
            }
    
            rs.close();
            stmt.close();
        } 
        catch (SQLException e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }
    
        return items;
    }
    
    /**
    Retrieves all inquiries from the database
    @param inquirers A list of Inquirer objects
    @param victims   A list of DisasterVictim objects
    @param locations A list of Location objects
    @return A list of ReliefService objects
     */
    @Override
    public ArrayList<ReliefService> getAllInquiries(ArrayList<Inquirer> inquirers, ArrayList<DisasterVictim> victims, ArrayList<Location> locations) {
        ArrayList<ReliefService> inquiries = new ArrayList<>();
    
        try {
            String query = "SELECT * FROM Inquiry";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
    
            while (rs.next()) {
                int inquirerId = rs.getInt("inquirer_id");
                int seekingId = rs.getInt("seeking_id");
                int locationId = rs.getInt("location_id");
                String date = rs.getTimestamp("date_of_inquiry").toLocalDateTime().toLocalDate().toString(); 
                String comments = rs.getString("comments");
    
                Person person = null;
                for (Inquirer i : inquirers) {
                    if (i.getId() == inquirerId) {
                        person = i;
                        break;
                    }
                }


                if (person == null) {
                    for (DisasterVictim v : victims) {
                        if (v.getId() == inquirerId) {
                            person = v;
                            break;
                        }
                    }
                }

                DisasterVictim victim = null;
                for (DisasterVictim d : victims) {
                    if (d.getId() == seekingId) {
                        victim = d;
                        break;
                    }
                }
    
                Location location = null;
                for (Location loc : locations) {
                    if (loc.getId() == locationId) {
                        location = loc;
                        break;
                    }
                }
    
                ReliefService service = new ReliefService(person, victim, date, comments, location);
                inquiries.add(service);
            }
    
            rs.close();
            stmt.close();
    
        } catch (SQLException e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }
    
        return inquiries;
    }
    
    /**
    Retrieves all locations from the database
    @return A list of Location objects
     */
    @Override
    public ArrayList<Location> getAllLocations() {
        ArrayList<Location> locations = new ArrayList<>();
    
        try {
            String query = "SELECT * FROM Location";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
    
            while (rs.next()) {
                String name = rs.getString("name");
                String address = rs.getString("address");
    
                Location location = new Location(name, address);
                location.setId(rs.getInt("location_id")); 
    
                locations.add(location);
            }
    
            rs.close();
            stmt.close();
    
        } 
        catch (SQLException e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }
    
        return locations;
    }
    
    /**
    Retrieves all medical records from the database and associates them with victims
    @param allLocations A list of Location objects
    @param victims      A list of DisasterVictim objects
     */
    @Override
    public void getAllMedicalRecords(ArrayList<Location> allLocations, ArrayList<DisasterVictim> victims) {
    
        try {
            String query = "SELECT * FROM MedicalRecord";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
    
            while (rs.next()) {
                int locationId = rs.getInt("location_id");
                int personId = rs.getInt("person_id");
                String treatment = rs.getString("treatment_details");
                String date = rs.getTimestamp("date_of_treatment").toLocalDateTime().toLocalDate().toString();

                Location location = null;
                for (Location loc : allLocations) {
                    if (loc.getId() == locationId) {
                        location = loc;
                        break;
                    }
                }
                DisasterVictim victim = null;
                for (DisasterVictim v : victims) {
                    if (v.getId() == personId) {
                        victim = v;
                        break;
                    }
                }
 
                MedicalRecord record = new MedicalRecord(location, treatment, date);
    
                if (victim != null) {
                    victim.addMedicalRecord(record); 
                }
            }
    
            rs.close();
            stmt.close();
    
        } 
        catch (SQLException e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }
    
    }


    /**
    Updates supply allocation to person
    @param supplyId SupplyId
    @param personId  PersonId
     */
    public void updateSupplyToPerson(int supplyId, int personId) {
        try {
            String query = "UPDATE SupplyAllocation SET person_id = ?, location_id = NULL, allocation_date = ? WHERE supply_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, personId);
            stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(3, supplyId);
            stmt.executeUpdate();
            stmt.close();
        } 
        catch (SQLException e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }
    }

    
    /**
    Removes supply allocation
    @param supplyId SupplyId
    @param personId  PersonId
     */

    public void removeSupplyAllocation(int supplyId) {
        try {
            String query = "DELETE FROM SupplyAllocation WHERE supply_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, supplyId);
            stmt.executeUpdate();
            stmt.close();
        } 
        catch (SQLException e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }
    }
    
    
    /**
    Updates supply allocation to location
    @param supplyId SupplyId
    @param locationId  LocationId
     */

    public void updateSupplyToLocation(int supplyId, int locationId) {
        try {
            String query = "UPDATE SupplyAllocation SET location_id = ?, person_id = NULL, allocation_date = ? WHERE supply_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, locationId);
            stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(3, supplyId);
            stmt.executeUpdate();
            stmt.close();
        } 
        catch (SQLException e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }
    }
    

    
    /**
    Adds a disaster victim to a location in the database
    @param personId   The ID of the disaster victim
    @param locationId The ID of the location
     */
    @Override
    public void addDisasterVictimToLocation(int personId, int locationId) {
        try {
            String query = "INSERT INTO PersonLocation (person_id, location_id) VALUES (?, ?)";
    
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, personId);
            stmt.setInt(2, locationId);
    
            stmt.executeUpdate();
            stmt.close();
        } 
        catch (SQLException e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }
    }

    /**
    Removes a disaster victim from a location in the database
    @param personId   The ID of the disaster victim
    @param locationId The ID of the location
     */
    @Override
    public void removeVictimFromLocation(int personId, int locationId) {
        try {
            String query = "DELETE FROM PersonLocation WHERE person_id = ? AND location_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, personId);
            stmt.setInt(2, locationId);
            stmt.executeUpdate();
            stmt.close();
        } 
        catch (SQLException e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }
    }

    /**
    Adds a medical record to the database
    @param record     The MedicalRecord object to add
    @param locationId The ID of the location
    @param personId   The ID of the disaster victim
     */
    @Override
    public void addMedicalRecord(MedicalRecord record, int locationId, int personId) {
        try {
            String query = """
                INSERT INTO MedicalRecord (location_id, person_id, date_of_treatment, treatment_details)
                VALUES (?, ?, ?, ?)
            """;
    
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, locationId); 
            stmt.setInt(2, personId);
            stmt.setTimestamp(3, Timestamp.valueOf(record.getDateOfTreatment() + " 00:00:00"));            stmt.setString(4, record.getTreatmentDetails());
            stmt.executeUpdate();
            stmt.close();
        } 
        catch (SQLException e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        } 
    }

    /**
    Updates an existing medical record in the database
    @param record   The MedicalRecord object with updated information
    @param recordId The ID of the medical record
     */
    @Override
    public void updateMedicalRecord(MedicalRecord record, int recordId) {
        try {
            String query = "UPDATE MedicalRecord SET treatment_details = ?, date_of_treatment = ? WHERE medical_record_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, record.getTreatmentDetails());
            stmt.setTimestamp(2, Timestamp.valueOf(record.getDateOfTreatment() + " 00:00:00"));            
            stmt.setInt(3, recordId);
            stmt.executeUpdate();
            stmt.close();
        } 
        catch (SQLException e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }
    }

    /**
    Updates an existing inquiry in the database
    @param inquiry   The ReliefService object with updated information
    @param inquiryId The ID of the inquiry
     */
    @Override
    public void updateInquiry(ReliefService inquiry, int inquiryId) {
        try {
            String query = "UPDATE Inquiry SET comments = ?, date_of_inquiry = ?, location_id = ? WHERE inquiry_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
            stmt.setString(1, inquiry.getInfoProvided());
            stmt.setTimestamp(2, timestamp);
            stmt.setInt(3, inquiry.getLastKnownLocation().getId());
            stmt.setInt(4, inquiryId);
            stmt.executeUpdate();
            stmt.close();
        } 
        catch (SQLException e) {
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }
    }
    
    /**
    Finds a family group by its ID
    @param list A list of FamilyGroup objects
    @param id   The ID of the family group
    @return The FamilyGroup object with the specified ID, or null if not found
     */
    private FamilyGroup findFamilyGroupById(ArrayList<FamilyGroup> list, int id) {
        for (FamilyGroup fg : list) {
            if (fg.getId() == id) {
                return fg;
            }
        }
        return null;
    }
    
    /**
    Closes the database connection and result set
     */
    @Override
    public void close(){
        try{
            results.close();
            connection.close();
        }
        catch(SQLException e){
            ErrorLog error = new ErrorLog(e);
            System.out.println(languageManager.getTranslation("UnexpectedError"));
            System.exit(1);
        }
    }



}