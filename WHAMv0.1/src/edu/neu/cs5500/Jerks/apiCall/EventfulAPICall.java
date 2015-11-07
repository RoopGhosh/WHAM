package edu.neu.cs5500.Jerks.apiCall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
//import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import edu.neu.cs5500.Jerks.definitions.*;

/*
@Auth: Karthik
	this class should be used to obtain event lists from the the given set of criteria.
	the input would be in form of a formed URL which will return JSON from EventBrite REST apis
	which is then parsed to obtain the Event object.
	CURRENTLY, event object is not described hence just parsing for the title text of each event */

public class EventfulAPICall {
	
	public static String getJsontext(String url) throws IOException
	{
		URL neturl = new URL(url);
		BufferedReader br = new BufferedReader(new InputStreamReader(neturl.openStream()));
		String jsontext = "";
		String temp;
		while((temp=br.readLine())!=null)
			jsontext = jsontext.concat(temp);		
		return jsontext;
	}
	
	public static void main(String[] args) throws IOException, JSONException, ParseException
	{
		EventfulAPICall obj = new EventfulAPICall();
		String url = "http://api.eventful.com/json/events/search?app_key=4fgZC93XQz2fgKpV&where=42.3601,-71.0589&within=10&date=Future&page_size=100&sort_order=popularity";
		
		obj.getListofEventsFromJSON(url);
	}
	
	public ArrayList<Event> getListofEventsFromJSON(String url) throws IOException, JSONException, ParseException
	{
		ArrayList<Event> LoEvents = new ArrayList<>();
		
		String jsontext = getJsontext(url);
		//System.out.println(jsontext);
		//creating jsonobject from text
		JSONObject json = new JSONObject(jsontext);
		int page_count = Integer.parseInt(json.get("page_count").toString());
		if (page_count > 0) {
			for (int j = 1; j <= page_count; j++) // now for no of page of results, we loop...
			{
				url = url.concat("&page_number=" + j);
				jsontext = getJsontext(url);
				json = new JSONObject(jsontext);
				org.json.JSONArray listings = json.getJSONObject("events").getJSONArray("event"); // getting the actual Events array.
				System.out.println(j);
				for (int i = 0; i < listings.length(); i++) // for every event in the array, retrieving the required materials.
				{
					JSONObject iterateObj = listings.getJSONObject(i);
					Event event = new Event();
					event.setId(iterateObj.get("id").toString());
					event.setName(iterateObj.get("title").toString());
					if(iterateObj.get("description").toString() == "null")
						event.setDescription(event.getName());
					else
						event.setDescription(iterateObj.get("description").toString());					
					Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(iterateObj.get("start_time").toString());
					event.setDate(date);
					event.setSource(EventSource.EventFul);
					
					Address address = new Address();
					address.setAddressLine1(iterateObj.get("venue_name").toString());
					address.setAddressLine2(iterateObj.get("venue_address").toString());
					address.setCity(iterateObj.get("city_name").toString());
					address.setState(iterateObj.get("region_name").toString());
					address.setCountry(iterateObj.get("country_name").toString());
					address.setZipCode(iterateObj.get("postal_code").toString());
					address.setLatitude(Float.parseFloat(iterateObj.get("latitude").toString())); 
					address.setLongitude(Float.parseFloat(iterateObj.get("longitude").toString()));					
					event.setAddress(address);
					
					LoEvents.add(event);				
				}
				System.out.println(LoEvents.size());
			}
		}
		System.out.println(LoEvents.size());
		if(page_count == LoEvents.size())
			System.out.println("Write on !!");
		System.out.println("DONE!!");
		return LoEvents;
	}
	
	public static int hasPagination(JSONObject json) throws NumberFormatException, JSONException {
		int page_count= 1;
		if(json.has("page_count")) // checking if the page has pagination tag to summarize the returns
			page_count =Integer.parseInt(json.get("page_count").toString());
		return page_count;
	}
}