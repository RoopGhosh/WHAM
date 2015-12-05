package edu.neu.cs5500.Jerks.definitions;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/* Author: Karthik Chandranna
 * Creation Date: 12/02/2015 6:04 AM EST
 * Description: All user related database calls and methods goes into this class 
 * */

@Entity
@Table
public class EventVisited {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	private String email;
	private String eventId;
	private EventSource source;
	private String eventName;
	private String eventAddress;
	private String eventDate;
	
	public EventVisited(){
		
	}
	
	public EventVisited(String email, String eventId, String eventName, String eventAddress, String eventDate, EventSource source) {
		this.email = email;
		this.eventId = eventId;
		this.source = source;
		this.eventName = eventName;
		this.eventAddress = eventAddress;
		this.eventDate = eventDate;		
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public EventSource getSource() {
		return source;
	}

	public void setSource(EventSource source) {
		this.source = source;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventAddress() {
		return eventAddress;
	}

	public void setEventAddress(String eventAddress) {
		this.eventAddress = eventAddress;
	}

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}	
	
	@Override
	public String toString() {
		return "EventVisited [id=" + id + ", email=" + email + ", eventId=" + eventId + ", source=" + source
				+ ", eventName=" + eventName + ", eventAddress=" + eventAddress + ", eventDate=" + eventDate + "]";
	}

	public EventVisited clone(){
		EventVisited eventVisited = new EventVisited();
		eventVisited.id = this.id;
		eventVisited.email = this.email;
		eventVisited.eventId = this.eventId;
		eventVisited.eventAddress = this.eventAddress;
		eventVisited.eventName = this.eventName;
		eventVisited.eventDate = this.eventDate;
		return eventVisited;
	}
	

}
