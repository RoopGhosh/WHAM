package edu.neu.cs5500.Jerks.definitions;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/* Author: Karthik Chandranna
 * Creation Date: 11/02/2015 6:04 AM EST
 * Description: All user related database calls and methods goes into this class 
 * */
@Entity
@Table
public class User {

	@Id
	private String email;
	private String firstName;
	private String lastName;	
	private String password;
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="ADDRESSID")
	private Address address;
	private String phoneNumber;
	@Temporal(TemporalType.DATE)
	private Date dob;
	private String gender;	
	private String areaOfInterest;
	private String disLikes;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}	

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}	

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}	

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}	

	public Date getDOB() {
		return dob;
	}

	public void setDOB(Date dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public List<String> getAreaOfInterest() {
		return Arrays.asList(areaOfInterest.split("|"));
	}
	
	public void setAreaOfInterest(List<String> areaOfInterest) {
		Iterator<String> it = areaOfInterest.iterator();
		String str = "";
		while(it.hasNext())
		{
			str = str.concat(it.next() + "|");
		}
		this.areaOfInterest = str.substring(0, str.length()-1);
	}

	public List<String> getDislikes() {
		return Arrays.asList(disLikes.split("|"));
	}
	public void setDislikes(List<String> dislikes) {
		Iterator<String> it = dislikes.iterator();
		String str = "";
		while(it.hasNext())
		{
			str = str.concat(it.next() + "|");
		}
		this.disLikes = str.substring(0, str.length()-1);
	}

	public User() {

	}

	public User(String email, String firstName, String lastName, String password, Address address, String phoneNumber,
			Date dob, String gender, List<String> areaOfInterest, List<String> dislikes) {

		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.dob = dob;
		this.gender = gender;
		Iterator<String> it = areaOfInterest.iterator();
		String str = "";
		while(it.hasNext())
		{
			str = str.concat(it.next() + "|");
		}
		this.areaOfInterest = str.substring(0, str.length()-1);
		
		Iterator<String> it1 = areaOfInterest.iterator();
		String str1 = "";
		while(it1.hasNext())
		{
			str1 = str1.concat(it1.next() + "|");
		}
		this.disLikes = str1.substring(0, str1.length()-1);
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", password=" + password + ", address=" + address + ", phoneNumber=" + phoneNumber + ", dob=" + dob
				+ ", gender=" + gender + ", areaOfInterest=" + areaOfInterest + ", dislikes=" + disLikes + "]";
	}	
}
