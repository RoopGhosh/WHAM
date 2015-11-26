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
 * Creation Date: 11/01/2015 6:49 PM EST
 * Description: Event info object definition
 * */

@Entity
@Table
public class Event {

	@Id
	private String eventId;
	private String name;
	@Temporal(TemporalType.DATE)
	private Date date;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ADDRESSID")
	private Address address;
	private String description;
	private double ticketPrice;
	private int minAgeLimit;
	private double rating;
	private int remainingTickets;
	private EventSource source;

	public Event() {

	}

	public Event(String name, Date date, Address address, String description, double ticketPrice, int minAgeLimit,
			double rating, int remainingTickets, EventSource source) {

		this.eventId = (new Date()).toString();
		this.name = name;
		this.date = date;
		this.address = address;
		this.description = description;
		this.ticketPrice = ticketPrice;
		this.minAgeLimit = minAgeLimit;
		this.rating = rating;
		this.remainingTickets = remainingTickets;
		this.source = source;
	}

	// Getters and Setters for Id
	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	// Getters and Setters for Name
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// Getters and Setters for Date
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	// Getters and Setters for Address
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	// Getters and Setters for Description
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	// Getters and Setters for minimum age limit
	public int getMinAgeLimit() {
		return minAgeLimit;
	}

	public void setMinAgeLimit(int minAgeLimit) {
		this.minAgeLimit = minAgeLimit;
	}

	// Getters and Setters for ticket price in US dollars
	public double getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	// Getters and Setters for event rating
	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	// Getters and Setters for remaining tickets
	public int getRemainingTickets() {
		return remainingTickets;
	}

	public void setRemainingTickets(int remainingTickets) {
		this.remainingTickets = remainingTickets;
	}

	// Getters and Setters for event source
	public EventSource getSource() {
		return source;
	}

	public void setSource(EventSource source) {
		this.source = source;
	}

	@Override
	public String toString() {
		return "Event [eventId=" + eventId + ", name=" + name + ", date=" + date + ", address=" + address
				+ ", description=" + description + ", ticketPrice=" + ticketPrice + ", minAgeLimit=" + minAgeLimit
				+ ", rating=" + rating + ", remainingTickets=" + remainingTickets + ", source=" + source + "]";
	}

}