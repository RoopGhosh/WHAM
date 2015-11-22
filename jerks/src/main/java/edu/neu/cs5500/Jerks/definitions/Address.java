package edu.neu.cs5500.Jerks.definitions;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 		
	private int addressId;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String country;
	private String zipCode;
	private float latitude;
	private float longitude;
	
	public int getAddressId() {
		return addressId;
	}
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	// Getters and Setters for Address Line 1
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	// Getters and Setters for Address Line 2
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	//Getters and Setters for city
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	//Getters and Setters for state
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	//Getters and Setters for country
	public String getCountry() {
		return country;
	}

	public Address(){

	}

	public Address( String addressLine1, String addressLine2, String city, String state, String country,
			String zipCode, float latitude, float longitude) {

		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.state = state;
		this.country = country;
		this.zipCode = zipCode;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public void setCountry(String country) {
		this.country = country;
	}	

	//Getters and Setters for Zip Code
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	//Getters and Setters for Latitude
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	//Getters and Setters for Longitude
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	@Override
	public String toString() {
		return "Address [addressId=" + addressId + ", addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2
				+ ", city=" + city + ", state=" + state + ", country=" + country + ", zipCode=" + zipCode
				+ ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}	

}
