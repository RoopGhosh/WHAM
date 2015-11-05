package definitions;

import java.util.*;

/* Author: Sandeep Ramamoorthy
 * Creation Date: 11/02/2015 5:45 AM EST
 * Description: User info object
 * */
public class User {

	private String _firstName;
	private String _lastName;
	private String _email;
	private String _password;
	private Address _address;
	private String _phoneNumber;
	private Date _dob;
	private String _gender;
	private List<String> _areaOfInterest = new ArrayList<String>();
	private List<String> _dislikes = new ArrayList<String>();
	
	public String getFirstName() {
		return _firstName;
	}
	public void setFirstName(String _firstName) {
		this._firstName = _firstName;
	}
	
	
	public String getLastName() {
		return _lastName;
	}
	public void setLastName(String _lastName) {
		this._lastName = _lastName;
	}
	
	
	public String getEmail() {
		return _email;
	}
	public void setEmail(String _email) {
		this._email = _email;
	}
	
	
	public String getPassword() {
		return _password;
	}
	public void setPassword(String _password) {
		this._password = _password;
	}
	
	
	public Address getAddress() {
		return _address;
	}
	public void setAddress(Address _address) {
		this._address = _address;
	}
	

	public String getPhoneNumber() {
		return _phoneNumber;
	}
	public void setPhoneNumber(String _phoneNumber) {
		this._phoneNumber = _phoneNumber;
	}
	
	
	public Date getDOB() {
		return _dob;
	}
	public void setDOB(Date _dob) {
		this._dob = _dob;
	}
	
	
	public String getGender() {
		return _gender;
	}
	public void setGender(String _gender) {
		this._gender = _gender;
	}
	
	
	public List<String> getAreaOfInterest() {
		return _areaOfInterest;
	}
	public void setAreaOfInterest(List<String> _areaOfInterest) {
		this._areaOfInterest = _areaOfInterest;
	}
	
	public List<String> getDislikes() {
		return _dislikes;
	}
	public void setDislikes(List<String> _dislikes) {
		this._dislikes = _dislikes;
	}	
}
