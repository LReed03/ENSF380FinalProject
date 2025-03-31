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
    
                victim.setVictimID(result.getInt("person_id"));
                victim.setLastName(result.getString("last_name"));
                java.sql.Date dob = result.getDate("date_of_birth");
                victim.setDateOfBirth(dob.toString()); 
                victim.setGender(Gender.valueOf(result.getString("gender")));
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
            e.printStackTrace();
        }
    
        return victims;
    }
    
    

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return groups;
    }
    

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
    
                inquirer.setInquirerId(result.getInt("person_id"));
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

    public void updatePerson(Person person) {
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

    public void logInquiry(int inquirerId, int seekingId, int locationId, String date, String comments) {
        try {
            String query = """
                INSERT INTO Inquiry (inquirer_id, seeking_id, location_id, date_of_inquiry, comments)
                VALUES (?, ?, ?, ?, ?)
            """;
    
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, inquirerId);
            stmt.setInt(2, seekingId);
            stmt.setInt(3, locationId);
            stmt.setTimestamp(4, Timestamp.valueOf(date)); 
            stmt.setString(5, comments);
    
            stmt.executeUpdate();
            stmt.close();
    
            System.out.println("Inquiry successfully logged.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        } 
        catch (IllegalArgumentException e) {
            System.err.println("Invalid date format. Expected format: yyyy-MM-dd HH:mm:ss");
        }
    }
    

    public void removeExpiredWater() {
        try {
            String query = """
                DELETE FROM SupplyAllocation
                USING Supply
                WHERE SupplyAllocation.supply_id = Supply.supply_id
                  AND Supply.type = 'water'
                  AND SupplyAllocation.allocation_date < CURRENT_DATE - INTERVAL '1 day'
                """;
            PreparedStatement stmt = connection.prepareStatement(query);
            int rowsDeleted = stmt.executeUpdate();
            System.out.println("Expired water supply allocations removed: " + rowsDeleted);
            stmt.close();
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
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
                if (!rs.wasNull() && personId != 0) {
                    for (DisasterVictim v : victims) {
                        if (v.getVictimID() == personId) {
                            person = v;
                            break;
                        }
                    }
                }
    
                Location location = null;
                if (!rs.wasNull() && locationId != 0) {
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
                        item = (person != null) ? new Blanket(person) : new Blanket(location);
                    }
                    case "water" -> {
                        item = (person != null) ? new Water(person) : new Water(location);
                        ((Water) item).allocationDate = allocationDate;
                    }
                    case "cot" -> {
                        String grid = (comments != null) ? comments : "unknown";
                        item = (person != null) ? new Cot(0, grid, person) : new Cot(0, grid, location);
                    }
                    case "personal item" -> {
                        if (person != null) {
                            String desc = (comments != null) ? comments : "unspecified";
                            item = new PersonalBelongings(desc, person);
                        }
                    }
                }
    
                if (item != null) {
                    item.setItemId(supplyId);
                    items.add(item);
                }
            }
    
            rs.close();
            stmt.close();
    
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
    
        return items;
    }
    

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

                Inquirer inquirer = null;
                for (Inquirer i : inquirers) {
                    if (i.getInquirerId() == inquirerId) {
                        inquirer = i;
                        break;
                    }
                }
                DisasterVictim victim = null;
                for (DisasterVictim d : victims) {
                    if (d.getVictimID() == seekingId) {
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

                ReliefService service = new ReliefService(inquirer, victim, date, comments, location);
                inquiries.add(service);
            }
    
            rs.close();
            stmt.close();
    
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
    
        return inquiries;
    }
    

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
            e.printStackTrace();
        }
    
        return locations;
    }
    

    public ArrayList<MedicalRecord> getAllMedicalRecords(ArrayList<Location> allLocations, ArrayList<DisasterVictim> victims) {
        ArrayList<MedicalRecord> records = new ArrayList<>();
    
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
                    if (v.getVictimID() == personId) {
                        victim = v;
                        break;
                    }
                }
 
                MedicalRecord record = new MedicalRecord(location, treatment, date);
                records.add(record);
    
                if (victim != null) {
                    victim.addMedicalRecord(record); 
                }
            }
    
            rs.close();
            stmt.close();
    
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
    
        return records;
    }
    
     
    

    public void addDisasterVictimToLocation(int personId, int locationId) {
        try {
            String query = "INSERT INTO PersonLocation (person_id, location_id) VALUES (?, ?)";
    
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, personId);
            stmt.setInt(2, locationId);
    
            stmt.executeUpdate();
            stmt.close();
    
            System.out.println("Victim successfully added to location.");
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    

    public void addMedicalRecord(MedicalRecord record, int locationId) {
        try {
            String query = """
                INSERT INTO MedicalRecord (location_id, treatment_details, date_of_treatment)
                VALUES (?, ?, ?)
            """;
    
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, locationId); 
            stmt.setString(2, record.getTreatmentDetails());
            stmt.setTimestamp(3, Timestamp.valueOf(record.getDateOfTreatment())); 
    
            stmt.executeUpdate();
            stmt.close();
    
            System.out.println("Medical record added.");
        } 
        catch (SQLException e) {
            e.printStackTrace();
        } 
        catch (IllegalArgumentException e) {
            System.err.println("Date format must be yyyy-MM-dd HH:mm:ss");
        }
    }

    private FamilyGroup findFamilyGroupById(ArrayList<FamilyGroup> list, int id) {
        for (FamilyGroup fg : list) {
            if (fg.getFamilyID() == id) {
                return fg;
            }
        }
        return null;
    }
    

    private static boolean checkPersonExists(Person person){
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
