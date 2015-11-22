package edu.neu.cs5500.Jerks.definitions;
import java.lang.*;

/* Author: Sandeep Ramamoorthy
 * Creation Date: 11/19/2015 4:30 AM EST
 * Description: Enumeration containing the list of Eventful category Ids as names 
 * 				and Eventbrite category Ids as values
 * */
public enum EventCategory {
	music (103),
	food (110),
	support (107),
	movies_film (104),
	performing_arts (105),
	other (199),
	family_fun_kids (115),
	learning_education (115),
	religion_spirituality (114),
	sports (108),
	holiday (116),
	business (101),
	science	(102),
	technology (102),
	fundraisers	 (111),
	politics_activism (112),
	outdoors_recreation (109),
	community (113),
	books (119),
	sales (106);
	
	private int EventbriteId;

	EventCategory(int EventbriteId) {
        this.EventbriteId = EventbriteId;
    }
    public int getEventbriteId() {
        return EventbriteId;
    }
}
