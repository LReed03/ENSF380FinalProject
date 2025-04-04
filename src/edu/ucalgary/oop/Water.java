package edu.ucalgary.oop;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Water extends InventoryItem{
    private boolean avalible;
    private String allocationDate;

    public Water(Location location){
        this.allocatedToLocation = location;
        this.ITEMTYPE = ItemType.WATER;
    }

    public Water(DisasterVictim person){
        this.allocatedToPerson = person;
        this.ITEMTYPE = ItemType.WATER;
    }

    public boolean getAvalible(){
        return this.avalible;
    }

    public void setAvalible(boolean avalible){
        this.avalible = avalible;
    }





    public void setAllocationDate(String allocationDate){
        boolean validEntryDate = isValidDateFormat(allocationDate);
        if (validEntryDate == false){
			throw new IllegalArgumentException("Invalid date");
		}
        this.allocationDate = allocationDate;
    }

    public boolean isExpired(String currentDate) throws IllegalArgumentException{
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

    public void useWater(String currentDate){
        if(isExpired(currentDate)){
            this.setAvalible(false);
        }
    }

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

    private static int convertDateStringToInt(String dateStr){
		dateStr = dateStr.replace("-", "");
		int dateInt = Integer.parseInt(dateStr);
		return dateInt;
	}

}
