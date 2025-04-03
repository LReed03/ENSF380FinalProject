package edu.ucalgary.oop;

public class Inquirer extends Person{
    private String info;
    private String phoneNumber;
    private int inquirerId;

    public Inquirer(String firstName, String lastName, String phone){
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phone;
    }

    public String getInfo(){
        return this.INFO;
    }

    public void setInfo(String info){
        this.info = info;
    }

    public String getPhone(){
        return this.phone_number;
    }

    public void setPhone(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public int getInquirerId(){
        return this.inquirerId;
    }

    public void setInquirerId(int inquirerId){
        this.inquirerId = inquirerId;
    }


}