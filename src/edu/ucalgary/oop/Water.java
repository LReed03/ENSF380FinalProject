package edu.ucalgary.oop;

public class Water extends InventoryItem{
    private boolean avalible;
    private String allocationDate;

    public Water(Location location){
        this.allocatedToLocation = location;
    }

    public Water(DisasterVictim person){
        this.allocatedToPerson = person;
    }

    public boolean getAvalible(){
        return this.avalible;
    }

    public void setAvalible(boolean avalible){
        this.avalible = avalible;
    }

        public void setAllocatedToPerson(DisasterVictim person) throws IllegalArgumentException{
        if(!this.sameLocation(person)){
            throw new IllegalArgumentException("Item must be in the same location as the victim");
        }
        else{
            this.allocatedToPerson = person;
        }
       
    }

    public void setAllocatedToLocation(Location location) throws IllegalArgumentException{
        if(this.isAllocatedToPerson()){
            throw new IllegalArgumentException("Cannot be allocated to a location if it already is allocated to a person");
        }
        this.allocatedToLocation = location;
    }

    public DisasterVictim getAllocatedToPerson(){
        return this.allocatedToPerson;
    }

    public Location getAllocatedToLocation(){
        return this.allocatedToLocation;
    }

    public String setAllocationDate(String allocationDate){
        boolean validEntryDate = isValidDateFormat(dateOfTreatment);
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
