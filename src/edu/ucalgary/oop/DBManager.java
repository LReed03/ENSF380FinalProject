package edu.ucalgary.oop;

public class DBManager implements DBAccess{
    private final String URL = "jdbc:postgresssql://localhost:5432/oop";
    private final String USER = "oop";
    private final String PASSWORD = "ucalgary";
    private DBManager instance;
    private Connection connection;

}
