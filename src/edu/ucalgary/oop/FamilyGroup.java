package edu.ucalgary.oop;
import java.util.ArrayList;

public class FamilyGroup {
    private int FAMILYID;
    private ArrayList<Person> familyMembers;
    private static int counter = 0;

    public FamilyGroup(){
        this.FAMILYID = generateFamilyId();
    }

    public void addFamilyMember(Person person){
        this.familyMembers.add(person);
    }

    public void removeFamilyMember(Person person){
        this.familyMembers.remove(person);
    }

    public ArrayList<Person> getFamilyMembers(){
        return this.familyMembers;
    }

    public void setFamilyMembers(ArrayList<Person> family){
        this.familyMembers = family;
    }

    public int getFamilyID(){
        return this.FAMILYID;
    }

    private static int generateFamilyId(){
        int id = counter;
        counter++;
        return id;
    }
}
