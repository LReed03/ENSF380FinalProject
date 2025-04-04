package edu.ucalgary.oop;

public class Inquirer extends Person{
    private String info;
    private String phoneNumber;

    public Inquirer(String firstName, String lastName, String phone){
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phone;
    }

    public String getInfo(){
        return this.info;
    }

    public void setInfo(String info){
        this.info = info;
    }

    public String getPhone(){
        return this.phoneNumber;
    }

    public void setPhone(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }



}