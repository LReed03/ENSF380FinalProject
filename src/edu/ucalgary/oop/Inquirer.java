package edu.ucalgary.oop;
/**
 @author Landon Reed
 @version 1.0
 Created on: April 5, 2025
 */
public class Inquirer extends Person {
    private String info;
    private String phoneNumber;

    /**
    Constructor to initialize an Inquirer with a first name, last name, and phone number.
    @param firstName The first name of the inquirer.
    @param lastName The last name of the inquirer.
    @param phone The phone number of the inquirer.
     */
    public Inquirer(String firstName, String lastName, String phone){
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phone;
    }

    /**
    Retrieves the information associated with the inquirer.
    @return The information as a string.
     */
    public String getInfo(){
        return this.info;
    }

    /**
    Sets the information associated with the inquirer.
    @param info The information to set.
     */
    public void setInfo(String info){
        this.info = info;
    }

    /**
    Retrieves the phone number of the inquirer.
    @return The phone number as a string.
     */
    public String getPhone(){
        return this.phoneNumber;
    }

    /**
    Sets the phone number of the inquirer.
    @param phoneNumber The phone number to set.
     */
    public void setPhone(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
}