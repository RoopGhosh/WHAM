package edu.neu.cs5500.Jerks.business;


import java.text.*;
import java.util.*;

import edu.neu.cs5500.Jerks.dbProviders.EventProvider;
import edu.neu.cs5500.Jerks.definitions.*;
import edu.neu.cs5500.Jerks.apiCall.EventbriteAPICall;
import edu.neu.cs5500.Jerks.apiCall.EventfulAPICall;

import org.springframework.util.StringUtils;
/* Author: Sandeep Ramamoorthy
 * Creation Date: 11/02/2015 6:00 AM EST
 * Description: All business logic related to events goes into this class 
 * */
public class EventManager {
	int searchRadius = 25;
	
	/* Author: Roop, Sandeep
	 * Description: Returns the URL string for Eventbrite API formed 
	 * using the supplied parameters.
	 */
	public String buildEventbriteURL(double latitude, double longitude, String searchAddress,
			String searchEvent, String price, Date givenDate, String[] categories)
	{
		String eventbriteURL= "https://www.eventbriteapi.com/v3/events/search/?popular=true&sort_by=distance";
		eventbriteURL = eventbriteURL.concat("&q="+searchEvent);
		String radiusInMiles = "&location.within=" + Integer.toString(searchRadius) + "mi";
		eventbriteURL = eventbriteURL.concat(radiusInMiles);
		eventbriteURL = eventbriteURL.concat("&price="+price);
		if(searchAddress!="")
			eventbriteURL = eventbriteURL.concat("&location.address="+searchAddress);
		else
		{
			eventbriteURL = eventbriteURL.concat("&location.latitude="+String.valueOf(latitude));
			eventbriteURL = eventbriteURL.concat("&location.longitude="+String.valueOf(longitude));
		}
		// sample eventbrite 2010-01-31
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
		Calendar cal = Calendar.getInstance();
		Calendar calDate = Calendar.getInstance();
		calDate.setTime(givenDate);
		cal.setTime(new Date()); // Now use today date.
		/*String futureDate;
		if(cal.DAY_OF_MONTH-calDate.DAY_OF_MONTH <2)
		{
			 // Adding 2 days
			cal.add(Calendar.DATE,5);
			futureDate = sdf.format(calDate.getTime());
		}
		else
			futureDate = sdf.format(calDate.getTime());*/
		String strCal = sdf.format(cal.getTime());
		String strCalDate = sdf.format(calDate.getTime());
		eventbriteURL = eventbriteURL.concat("&start_date.range_start="+strCal+"T00:00:00Z");
		eventbriteURL = eventbriteURL.concat("&start_date.range_end="+strCalDate+"T23:59:59Z");
		String categoryParam ="&categories=";
		for (String c : categories)
		{
			EventCategory category = EventCategory.valueOf(c);
			categoryParam =categoryParam.concat(category.getEventbriteId()+",");
		}
		// remove the last comma before appending to categories to url
		if(categoryParam.length()>0)
			eventbriteURL = eventbriteURL.concat(categoryParam.substring(0,categoryParam.length()-1));
		return eventbriteURL;
	}
	
	/* Author: Karthik, Sandeep
	 * Description: Returns the URL string for Eventful API formed 
	 * using the supplied parameters.
	 */
	public String buildEventfulURL(double latitude, double longitude, String searchAddress, String searchEvent, String price,
			Date date, String[] categories)
	{
		String eventfulURL= "http://api.eventful.com/json/events/search?app_key=4fgZC93XQz2fgKpV&sort_order=distance&units=miles&include=price&page_size=50";
		eventfulURL = eventfulURL.concat("&q="+searchEvent);
		if(searchAddress != "")
			eventfulURL = eventfulURL.concat("&l="+searchAddress);
		else
			eventfulURL = eventfulURL.concat("&l="+String.valueOf(latitude)+","+String.valueOf(longitude));
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date currdate = new Date();
		String currStrDate= dateFormat.format(currdate);
		String givenStrDate = dateFormat.format(date);
		eventfulURL = eventfulURL.concat("&price="+price);
		eventfulURL = eventfulURL.concat("&date="+currStrDate+"-"+givenStrDate);
		eventfulURL = eventfulURL.concat("&category="+StringUtils.arrayToCommaDelimitedString(categories));
		eventfulURL = eventfulURL.concat("&within="+Integer.toString(searchRadius));
		return eventfulURL;
	}
	
	/* Author: Roop, Karthick, Sandeep
	 * Description: Returns a combined list of events from Eventful
	 * and Eventbrite which matches the given parameters. The method
	 * also filters out the events which were disliked by the user
	 */
	public List<Event> fetchEvents(double latitude, double longitude, String searchAddress, String searchEvent, String price,
			Date date, String[] categories, String[] dislikes)
	{
		List<Event> events = new ArrayList<Event>();
		String eventbriteURL = buildEventbriteURL(latitude, longitude, searchAddress, searchEvent, price, date, categories);
		String eventfulURL = buildEventfulURL(latitude, longitude, searchAddress, searchEvent, price, date, categories);
		EventbriteAPICall eventBrite = new EventbriteAPICall();
		EventfulAPICall eventful = new EventfulAPICall();
		try {			
			events = eventBrite.getListofEventsFromJSON(eventbriteURL);
			System.out.println("EventBrite count:"+ events.size());
			events.addAll(eventful.getListofEventsFromJSON(eventfulURL));
			System.out.println("Total Count:"+ events.size());
			
			EventProvider eventDao = new EventProvider();
			List<Event> whamEvents = eventDao.findAllEvents();
			events.addAll(whamEvents);

			// Remove events if the user has disliked it
			List<Event> toRemove = new ArrayList<Event>();
			System.out.println("dislikes : "+dislikes.length);
			if (dislikes.length>0)
				System.out.println("dislikes list: "+dislikes[0]);
			for(Event event : events)
			{
				String eventName = event.getName().toLowerCase();
				for(String dislike :dislikes)
				{
				 if(eventName.replaceAll("\u0020", "").toLowerCase().contains(dislike.replaceAll("\u0020", "").toLowerCase().trim()) && !dislike.isEmpty())
					 toRemove.add(event);			 
				}
			}
			events.removeAll(toRemove);
			System.out.println("Events After Filter:"+ events.size());	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return events;
	}
	
	
	public static void main(String args[])
	{
		double latitude = 0.0f;
		double longitude = 0.0f;
		String searchAddress = "";
		String searchEvent = ""; 
		String price = "";
		//taken care in event manager
		//Calendar c = Calendar.getInstance(); // starts with today's date and time
		//c.add(Calendar.DAY_OF_YEAR, 2);  // advances day by 2
		// return the date pushed by user or return the curr date.
		Date date = new Date();// gets modified time
		String loginMessage = "HowdyUser!";
		// Remove hardcoded categories & dislikes
		String[] categories = {"food", "science"};
		String[] dislikes = {"music", "boston", "cheese"};
		String emailFordetails = "Howdy User!";
		EventManager em = new EventManager();
		em.fetchEvents(latitude, longitude, searchAddress, searchEvent , price, date, categories, dislikes);
	}
}
