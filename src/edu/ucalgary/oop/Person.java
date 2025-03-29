package edu.ucalgary.oop;

public abstract class Person {
    protected String firstName;
    protected String lastName;
    protected Gender gender;
    protected FamilyGroup family;

    public void setFamily(FamilyGroup family){
        this.family = family;
    }

    public FamilyGroup getFamily(){
        return this.family;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public void setGender(Gender gender){
        this.gender = gender;
    }

    public Gender getGender(){
        return this.gender;
    }


}
