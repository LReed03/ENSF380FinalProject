package edu.ucalgary.oop;

public class Inquirer extends Person{
    private final String INFO;
    private String phoneNumber;

    public Inquirer(String firstName, String lastName, String phone, String INFO){
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phone;
        this.INFO = INFO;
    }

    public String getInfo(){
        return this.INFO;
    }

    public String getPhone(){
        return this.phone_number;
    }

    public void setPhone(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }


}