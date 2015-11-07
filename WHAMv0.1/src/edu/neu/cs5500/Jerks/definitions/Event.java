package edu.neu.cs5500.Jerks.definitions;

import java.util.Date;

/* Author: Sandeep Ramamoorthy
 * Creation Date: 11/01/2015 6:49 PM EST
 * Description: Event info object definition
 * */
public class Event {
	
	private String _id;
	private String _name;
	private Date _date;
	private Address _address; 
	private String _description;
	private double _ticketPrice;
	private short _minAgeLimit;
	private double _rating;
	private int _remainingTickets;
	private EventSource _source;
	
	//Getters and Setters for Id
	public String getId() {
		return _id;
	}
	public void setId(String _id) {
		this._id = _id;
	}
	
	//Getters and Setters for Name
	public String getName() {
		return _name;
	}
	public void setName(String _name) {
		this._name = _name;
	}
	
	//Getters and Setters for Date
	public Date getDate() {
		return _date;
	}
	public void setDate(Date _date) {
		this._date = _date;
	}
	
	//Getters and Setters for Address
	public Address getAddress() {
		return _address;
	}
	public void setAddress(Address _address) {
		this._address = _address;
	}
	
	//Getters and Setters for Description
	public String getDescription() {
		return _description;
	}
	public void setDescription(String _description) {
		this._description = _description;
	}
	
	//Getters and Setters for minimum age limit
	public short getMinAgeLimit() {
		return _minAgeLimit;
	}
	public void setMinAgeLimit(short _minAgeLimit) {
		this._minAgeLimit = _minAgeLimit;
	}
	
	//Getters and Setters for ticket price in US dollars
	public double getTicketPrice() {
		return _ticketPrice;
	}
	public void setTicketPrice(double _ticketPrice) {
		this._ticketPrice = _ticketPrice;
	}
	
	//Getters and Setters for event rating
	public double getRating() {
		return _rating;
	}
	public void setRating(double _rating) {
		this._rating = _rating;
	}
	
	//Getters and Setters for remaining tickets
	public int getRemainingTickets() {
		return _remainingTickets;	
	}
	public void setRemainingTickets(int _remainingTickets) {
		this._remainingTickets = _remainingTickets;
	}
	
	//Getters and Setters for event source
	public EventSource getSource() {
		return _source;
	}
	public void setSource(EventSource _source) {
		this._source = _source;
	}	
}