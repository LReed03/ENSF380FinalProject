package edu.ucalgary.oop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DisasterVictim extends Person{
    private String dateOfBirth;
	private int ASSIGNED_SOCIAL_ID;
    private ArrayList<MedicalRecord> medicalRecords;
    private ArrayList<InventoryItem> belongings;
	private final String ENTRY_DATE;
	private String gender;
	private String comments;
    private static int counter = 0;

    public DisasterVictim(String firstName, String ENTRY_DATE) {
		boolean validEntryDate = isValidDateFormat(ENTRY_DATE);
		if (validEntryDate == false){
			throw new IllegalArgumentException("Invalid date");
		}
		this.ENTRY_DATE = ENTRY_DATE;
		this.firstName = firstName;
		this.ASSIGNED_SOCIAL_ID = generateSocialID();
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
		this.ASSIGNED_SOCIAL_ID = generateSocialID();
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

    public int getAssignedSocialID(){
		return this.ASSIGNED_SOCIAL_ID;
	}


	public ArrayList<MedicalRecord> getMedicalRecords(){
		return this.medicalRecords;
	}
	public ArrayList<InventoryItem> getBelongings(){
		return this.personalBelongings;
	}


	public void setMedicalRecords(ArrayList<MedicalRecord> records){
		this.medicalRecords = records;
	}

	public void setPersonalBelongings(ArrayList<InventoryItem> belongings){
		this.personalBelongings = belongings;
	}

	public void addPersonalBelongings(InventoryItem supply){
		personalBelongings = Arrays.copyOf(personalBelongings, personalBelongings.length + 1);
		personalBelongings[personalBelongings.length - 1] = supply;
	}

	public void removePersonalBelongings(InventoryItem unwantedSupply){
		List<Supply> tempList = new ArrayList<>(Arrays.asList(personalBelongings));
		tempList.remove(unwantedSupply);
		personalBelongings = tempList.toArray(new Supply[0]);
		
	}


	public void addMedicalRecord(MedicalRecord record){
		medicalRecords = Arrays.copyOf(medicalRecords, medicalRecords.length + 1);
		medicalRecords[medicalRecords.length - 1] = record;
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

	public void setGender(String gender){
		this.gender = gender;
	}

	private static int generateSocialID(){
		int socialId = 2891 + counter;
		counter++;
		return socialId;
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



