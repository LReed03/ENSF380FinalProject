package edu.ucalgary.oop;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DisasterVictim extends Person{
    private String dateOfBirth;
	private int VictimID;
    private ArrayList<MedicalRecord> medicalRecords = new ArrayList<>();
    private ArrayList<InventoryItem> belongings = new ArrayList<>();
	private final String ENTRY_DATE;
	private String comments;
	

    public DisasterVictim(String firstName, String ENTRY_DATE) {
		boolean validEntryDate = isValidDateFormat(ENTRY_DATE);
		if (validEntryDate == false){
			throw new IllegalArgumentException("Invalid date");
		}
		this.ENTRY_DATE = ENTRY_DATE;
		this.firstName = firstName;
	}
	
	public DisasterVictim(String firstName, String ENTRY_DATE, String dateOfBirth) throws IllegalArgumentException{
		int entryDateInt = convertDateStringToInt(ENTRY_DATE);
		int dateOfBirthInt = convertDateStringToInt(dateOfBirth);
		if(dateOfBirthInt > entryDateInt){
			throw new IllegalArgumentException("Cant have birthday after the entry date");
		}
		boolean validEntryDate = isValidDateFormat(ENTRY_DATE);
		if (validEntryDate == false){
			throw new IllegalArgumentException("Invalid date");
		}
		this.ENTRY_DATE = ENTRY_DATE;
		this.firstName = firstName;
		boolean validDOB = isValidDateFormat(dateOfBirth);
		if (validDOB == false){
			throw new IllegalArgumentException("Invalid date");
		}
		this.dateOfBirth = dateOfBirth;
	}

    public String getDateOfBirth(){
		return this.dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) throws IllegalArgumentException{
		boolean valid = isValidDateFormat(dateOfBirth);
		if(valid == false){
			throw new IllegalArgumentException("Not a valid DOB");
		}
		if(valid == true){
			this.dateOfBirth = dateOfBirth;
		}
		
	}

    public int getVictimID(){
		return this.VictimID;
	}

	public void setVictimID(int id){
		this.VictimID = id;
	}


	public ArrayList<MedicalRecord> getMedicalRecords(){
		return this.medicalRecords;
	}
	public ArrayList<InventoryItem> getBelongings(){
		return this.belongings;
	}


	public void setMedicalRecords(ArrayList<MedicalRecord> records){
		this.medicalRecords = records;
	}

	public void setBelongings(ArrayList<InventoryItem> belongings){
		this.belongings = belongings;
	}

	public void addBelongings(InventoryItem supply){
		this.belongings.add(supply);
	}

	public void removeBelongings(InventoryItem unwantedSupply){
		this.belongings.remove(unwantedSupply);
		
	}


	public void addMedicalRecord(MedicalRecord record){
		this.medicalRecords.add(record);
	}

	public String getEntryDate(){
		return this.ENTRY_DATE;
	}

	public String getComments(){
		return this.comments;
	}

	public void setComments(String comments){
		this.comments = comments;
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



