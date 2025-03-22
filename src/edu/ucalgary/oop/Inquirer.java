package edu.ucalgary.oop;

public class Inquirer extends Person{
    private final String INFO;
    private final String SERVICES_PHONE;

    public Inquirer(String firstName, String lastName, String PHONE, String INFO){
        this.firstName = firstName;
        this.lastName = lastName;
        this.SERVICES_PHONE = PHONE;
        this.INFO = INFO;
    }

    public String getInfo(){
        return this.INFO;
    }

    public String getPhone(){
        return this.INFO;
    }

}