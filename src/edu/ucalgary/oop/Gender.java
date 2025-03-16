package edu.ucalgary.oop;

public enum Gender {
    MALE,
    FEMALE,
    NONBINARY;

    public String toString(Gender gender){
        if(gender == MALE){
            return "male";
        }
        if(gender == FEMALE){
            return "female";
        }
        if(gender == NONBINARY){
            return "non-binary";
        }
        return "";
    }
}
