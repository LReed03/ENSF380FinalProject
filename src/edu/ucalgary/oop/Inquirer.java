package edu.ucalgary.oop;

public class Inquirer extends Person{
    private final String INFO;

    public Inquirer(String firstName, String lastName, String phone, String INFO){
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phone;
        this.INFO = INFO;
    }

    public String getInfo(){
        return this.INFO;
    }


}