package edu.ucalgary.oop;
import java.util.ArrayList;

public class FamilyGroup {
    private int familyId;
    private ArrayList<Person> familyMembers;
    private static int highestId = 0;

    public FamilyGroup(){
        this.familyMembers = new ArrayList<>();
    }
    
    public FamilyGroup(int familyId){
        highestId = familyId;
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

    public int getId(){
        return this.familyId;
    }

    public void setId(int id){
        highestId = id;
        this.familyId = id;
    }

    public void setId(){
        highestId = highestId + 1;
        this.familyId = highestId;
    }
}
