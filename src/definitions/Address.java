package definitions;

/* Author: Sandeep Ramamoorthy
 * Creation Date: 11/01/2015 7:20 PM EST
 * Description: Address info object definition
 * */
public class Address {
	
	private String _addressLine1;
	private String _addressLine2;
	private String _city;
	private String _state;
	private String _country;
	private String _zipCode;
	private float _latitude;
	private float _longitude;
	
	// Getters and Setters for Address Line 1
	public String getAddressLine1() {
		return _addressLine1;
	}
	public void setAddressLine1(String _addressLine1) {
		this._addressLine1 = _addressLine1;
	}
	
	// Getters and Setters for Address Line 2
		public String getAddressLine2() {
			return _addressLine2;
		}
		public void setAddressLine2(String _addressLine2) {
			this._addressLine2 = _addressLine2;
		}
		
		//Getters and Setters for city
		public String getCity() {
			return _city;
		}
		public void setCity(String _city) {
			this._city = _city;
		}
		
		//Getters and Setters for state
		public String getState() {
			return _state;
		}
		public void setState(String _state) {
			this._state = _state;
		}
		
		//Getters and Setters for country
				public String getCountry() {
					return _country;
				}
				public void setCountry(String _country) {
					this._country = _country;
				}	
		
		//Getters and Setters for Zip Code
		public String getZipCode() {
			return _zipCode;
		}
		public void setZipCode(String _zipCode) {
			this._zipCode = _zipCode;
		}
		
		//Getters and Setters for Latitude
		public float getLatitude() {
			return _latitude;
		}
		public void setLatitude(float _latitude) {
			this._latitude = _latitude;
		}
		
		//Getters and Setters for Longitude
		public float getLongitude() {
			return _longitude;
		}
		public void setLongitude(float _longitude) {
			this._longitude = _longitude;
		}
		
		
}
