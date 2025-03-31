package edu.ucalgary.oop;
import java.util.ArrayList;

public class FamilyGroup {
    private int familyId;
    private ArrayList<Person> familyMembers;

    public FamilyGroup(){
        this.familyMembers = new ArrayList<>();
    }
    
    public FamilyGroup(int familyId){
        this.familyId = familyId;
        this.familyMembers = new ArrayList<>();
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
        return this.familyId;
    }

    public void setFamilyId(int id){
        this.familyId = id;
    }
}
