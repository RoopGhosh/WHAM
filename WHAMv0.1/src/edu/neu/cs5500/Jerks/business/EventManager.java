package edu.neu.cs5500.Jerks.business;

import java.text.*;
import java.util.*;
import edu.neu.cs5500.Jerks.definitions.*;

/* Author: Sandeep Ramamoorthy
 * Creation Date: 11/02/2015 6:00 AM EST
 * Description: All business logic related to events goes into this class 
 * */
public class EventManager {

	private String urlBuilder_EventBrite(double latitude, double longitude, String searchAddress, String searchEvent, String price,
			Date date, String[] categories)
	{
		String baseURLEventBrite= "https://www.eventbriteapi.com/v3/events/search/?";
		baseURLEventBrite = baseURLEventBrite.concat("q="+searchEvent);
		baseURLEventBrite = baseURLEventBrite.concat("&popular=on");
		baseURLEventBrite = baseURLEventBrite.concat("&sort_by=distance");
		if(searchAddress!="")
			baseURLEventBrite = baseURLEventBrite.concat("&location.address="+searchAddress);
		baseURLEventBrite = baseURLEventBrite.concat("&location.within=5mi");
		baseURLEventBrite = baseURLEventBrite.concat("&price="+price);
		if(searchAddress=="")
		{
			baseURLEventBrite = baseURLEventBrite.concat("&location.latitude="+String.valueOf(latitude));
			baseURLEventBrite = baseURLEventBrite.concat("&location.longitude="+String.valueOf(longitude));
		}
		Date currdate = new Date();
		System.out.println(date);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = dateFormat.format(currdate);
		String givenDate = dateFormat.format(date);
		baseURLEventBrite = baseURLEventBrite.concat("&start_date.range_start="+strDate+"T00%3A00%3A00");
		baseURLEventBrite = baseURLEventBrite.concat("&start_date.range_start="+givenDate+"T00%3A00%3A00");
		System.out.println(baseURLEventBrite);
		return baseURLEventBrite;
	}
	
	private String urlBuilder_EventFul(double latitude, double longitude, String searchAddress, String searchEvent, String price,
			Date date, String[] categories)
	{
		String baseURLEventFul= "http://api.eventful.com/json/events/search?app_key=4fgZC93XQz2fgKpV";
		baseURLEventFul = baseURLEventFul.concat("&q="+searchEvent);
		if(searchAddress!="")
			baseURLEventFul = baseURLEventFul.concat("&l="+searchAddress);
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
		baseURLEventFul = baseURLEventFul.concat("&sort_order=distance&json_request_id=50&within=5&units=miles&include=price");
		System.out.println(baseURLEventFul);
		return baseURLEventFul;
	}
	
	//TODO: Karthik and Roop to modify the below method
	public List<Event> fetchEvents(double latitude, double longitude, String searchAddress, String searchEvent, String price,
			Date date, String[] categories)
	{
		List<Event> events = new ArrayList<Event>();
		return events;
	}
	
	public static void main (String args[])
	{
		EventManager em = new EventManager();
		Date date = new Date();
		String[] arr = {};
		em.urlBuilder_EventFul(23.0f, 23.0f, "boston", "", "free", date, arr);
	}
}
