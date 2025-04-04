package edu.ucalgary.oop;

public abstract class Person {
    protected String firstName;
    protected String lastName;
    protected Gender gender;
    protected FamilyGroup family;
    protected int personId;
    protected static int highestId = 0;

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

    public void setId(int id){
        highestId = id;
        this.personId = id;

    }

    protected void setId(){
        highestId = highestId + 1;
        this.personId = highestId;
    }

    protected int getId(){
        return this.personId;
    }


}
