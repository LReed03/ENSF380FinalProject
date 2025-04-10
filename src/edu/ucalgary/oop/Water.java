package edu.ucalgary.oop;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 @author Landon Reed
 @version 1.0
 Created on: April 5, 2025
 */
public class Water extends InventoryItem {
    private boolean avalible;
    private String allocationDate;

    /**
     Constructor to initialize water allocated to a location
    @param location The location to which the water is allocated
     */
    public Water(Location location) {
        this.allocatedToLocation = location;
        this.ITEMTYPE = ItemType.WATER;
        this.avalible = true;
    }

    /**
    Constructor to initialize water allocated to a disaster victim
    @param person The disaster victim to whom the water is allocated
     */
    public Water(DisasterVictim person) {
        this.allocatedToPerson = person;
        this.ITEMTYPE = ItemType.WATER;
        this.avalible = true;
    }

    /**
    Retrieves the availability status of the water
    @return True if the water is available, false otherwise
     */
    public boolean getAvalible() {
        return this.avalible;
    }

    /**
    Sets the availability status of the water
    @param avalible The availability status to set
     */
    public void setAvalible(boolean avalible) {
        this.avalible = avalible;
    }

    /**
    Sets the allocation date of the water
    @param allocationDate The allocation date in the format "YYYY-MM-DD"
     */
    public void setAllocationDate(String allocationDate) {
        boolean validEntryDate = isValidDateFormat(allocationDate);
        if (validEntryDate == false){
			throw new IllegalArgumentException("Invalid date");
		}
        this.allocationDate = allocationDate;
    }

    /**
    Retrieves the allocation date of the water
    @return The allocation date in the format "YYYY-MM-DD"
     */
    public String getAllocationDate() {
        return this.allocationDate;
    }

    /**
    Checks if the water is expired based on the current date
    @param currentDate The current date in the format "YYYY-MM-DD"
    @return True if the water is expired, false otherwise
     */
    public boolean isExpired(String currentDate) throws IllegalArgumentException {
        boolean validCurrentDate = isValidDateFormat(currentDate);
        if (validCurrentDate == false){
			throw new IllegalArgumentException("Invalid date");
		}
        int currentDateInt = convertDateStringToInt(currentDate);
		int allocationDateInt = convertDateStringToInt(this.allocationDate);
        if(currentDateInt - allocationDateInt > 1){
            return true;
        }
        return false;
    }

    /**
    Marks the water as unavailable if it is expired based on the current date
    @param currentDate The current date in the format "YYYY-MM-DD"
     */
    public void useWater(String currentDate) {
        if(isExpired(currentDate)){
            this.setAvalible(false);
        }
    }

    /**
    Validates the format of a date string
    @param date The date string to validate
    @return True if the date format is valid, false if not
     */
    private static boolean isValidDateFormat(String date){
        String dateRegex = "^\\d{4}[-]{1}\\d{2}[-]\\d{2}$";
        Pattern myPattern = Pattern.compile(dateRegex);
        Matcher mymatcher = myPattern.matcher(date);
        if(mymatcher.find()){
            return true;
        }
        else{
            return false;
        }
    
	}

    /**
    Converts a date string in the format "YYYY-MM-DD" to an integer for comparison
    @param dateStr The date string to convert
    @return The integer representation of the date
     */
    private static int convertDateStringToInt(String dateStr){
		dateStr = dateStr.replace("-", "");
		int dateInt = Integer.parseInt(dateStr);
		return dateInt;
	}


}
