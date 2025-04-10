package edu.ucalgary.oop;

/**
 @author Landon Reed
 @version 1.0
 Created on: April 5, 2025
 */
public enum Gender {
    MALE,
    FEMALE,
    NONBINARY;

    /**
    Converts a Gender enum value to its string representation
    @param gender The Gender enum value to convert
    @return A string representation of the gender ("male", "female", "non-binary")
     */
    public static String toSQL(Gender gender){
        if(gender == MALE){
            return "Man";
        }
        if(gender == FEMALE){
            return "Woman";
        }
        if(gender == NONBINARY){
            return "Non-binary person";
        }
        return "";
    }

    /**
    Converts a string representation of a gender to its corresponding Gender enum value
    @param value The string representation of the gender
    @return The corresponding Gender value, or null if the input is not in the Gender
     */
    public static Gender fromString(String value) {
        if (value == null) return null;
    
        value = value.toLowerCase();
        if (value.equals("male") || value.equals("man")) {
            return MALE;
        } 
        else if (value.equals("female") || value.equals("woman")) {
            return FEMALE;
        } 
        else if (value.equals("nonbinary") || value.equals("non-binary") ||
                   value.equals("non binary person") || value.equals("non-binary person")) {
            return NONBINARY;
        }
    
        return null; 
    }
    
}
