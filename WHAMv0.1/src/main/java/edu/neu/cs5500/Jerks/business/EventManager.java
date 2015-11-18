package edu.neu.cs5500.Jerks.business;


import java.text.*;
import java.util.*;
import edu.neu.cs5500.Jerks.definitions.*;
import edu.neu.cs5500.Jerks.apiCall.EventbriteAPICall;
import edu.neu.cs5500.Jerks.apiCall.EventfulAPICall;

/* Author: Sandeep Ramamoorthy
 * Creation Date: 11/02/2015 6:00 AM EST
 * Description: All business logic related to events goes into this class 
 * */
public class EventManager {
	
	int searchRadius = 5;
	
	private String urlBuilder_EventBrite(double latitude, double longitude, String searchAddress, String searchEvent, String price,
			Date date, String[] categories)
	{
		String baseURLEventBrite= "https://www.eventbriteapi.com/v3/events/search/?";
		baseURLEventBrite = baseURLEventBrite.concat("q="+searchEvent);
		baseURLEventBrite = baseURLEventBrite.concat("&popular=on");
		baseURLEventBrite = baseURLEventBrite.concat("&sort_by=distance");
		String radiusInMiles = "&location.within=" + Integer.toString(searchRadius) + "mi";
		baseURLEventBrite = baseURLEventBrite.concat(radiusInMiles);
		baseURLEventBrite = baseURLEventBrite.concat("&price="+price);
		if(searchAddress!="")
			baseURLEventBrite = baseURLEventBrite.concat("&location.address="+searchAddress);
		else
		{
			baseURLEventBrite = baseURLEventBrite.concat("&location.latitude="+String.valueOf(latitude));
			baseURLEventBrite = baseURLEventBrite.concat("&location.longitude="+String.valueOf(longitude));
		}
		Date currdate = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = dateFormat.format(currdate);
		String givenDate = dateFormat.format(date);
		baseURLEventBrite = baseURLEventBrite.concat("&start_date.range_start="+strDate+"T00:00:00Z");
		baseURLEventBrite = baseURLEventBrite.concat("&start_date.range_end="+givenDate+"T23:00:00Z");
		return baseURLEventBrite;
	}
	
	private String urlBuilder_EventFul(double latitude, double longitude, String searchAddress, String searchEvent, String price,
			Date date, String[] categories)
	{
		String baseURLEventFul= "http://api.eventful.com/json/events/search?app_key=4fgZC93XQz2fgKpV";
		baseURLEventFul = baseURLEventFul.concat("&q="+searchEvent);
		if(searchAddress!="")
			baseURLEventFul = baseURLEventFul.concat("&l="+searchAddress);
		else
			baseURLEventFul = baseURLEventFul.concat("&l="+String.valueOf(latitude)+","+String.valueOf(longitude));
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date currdate = new Date();
		String currStrDate= dateFormat.format(currdate);
		String givenStrDate = dateFormat.format(date);
		baseURLEventFul = baseURLEventFul.concat("&date="+currStrDate+"-"+givenStrDate);
		String arrStr = "";
		for(String str : categories)
			arrStr = arrStr.concat(str+"||");
		if(arrStr.length()>2)
			arrStr = arrStr.substring(0, arrStr.length()-2);
		baseURLEventFul = baseURLEventFul.concat("&c="+arrStr);
		baseURLEventFul = baseURLEventFul.concat("&sort_order=distance&within="+Integer.toString(searchRadius)+"&units=miles&include=price&page_size=50");
		return baseURLEventFul;
	}
	
	public List<Event> fetchEvents(double latitude, double longitude, String searchAddress, String searchEvent, String price,
			Date date, String[] categories)
	{
		List<Event> events = new ArrayList<Event>();
		EventbriteAPICall eventBrite = new EventbriteAPICall();
		EventfulAPICall eventful = new EventfulAPICall();
		String baseURLEventBrite = urlBuilder_EventBrite(latitude, longitude, searchAddress, searchEvent, price, date, categories);
		String baseURLEventful = urlBuilder_EventFul(latitude, longitude, searchAddress, searchEvent, price, date, categories);
		try {
			System.out.println("String 1 "+ baseURLEventBrite);			
			events = eventBrite.getListofEventsFromJSON(baseURLEventBrite);
			System.out.println("String 2 " + baseURLEventful);
			events.addAll(eventful.getListofEventsFromJSON(baseURLEventful));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return events;
	}
	
}
