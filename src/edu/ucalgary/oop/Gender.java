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

    public static Gender valueOf(String value){
        if (value == null){
            return null;
        }

        value = value.toLowerCase();
        if (value.equals("male") || value.equals("man")) {
            return MALE;
        } 
        else if (value.equals("female") || value.equals("woman")) {
            return FEMALE;
        } 
        else if (value.equals("nonbinary") || value.equals("non-binary") || value.equals("non-binary person") || value.equals("nonbinary person")) {
            return NONBINARY;
        }
    }
}
