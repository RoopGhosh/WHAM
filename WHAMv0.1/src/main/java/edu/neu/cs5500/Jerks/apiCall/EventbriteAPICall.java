package edu.neu.cs5500.Jerks.apiCall;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.jar.JarException;

import org.json.JSONException;
import org.json.JSONObject;

import edu.neu.cs5500.Jerks.definitions.*;

/*
@Auth: Roop
	this class should be used to obtain event lists from the the given set of criteria.
	the input would be in form of a formed URL which will return JSON from EventBrite REST apis
	which is then parsed to obtain the Event object.
	CURRENTLY, event object is not described hence just parsing for the title text of each event */

public class EventbriteAPICall {
	
	
	private String getJsontext(String url) throws IOException
	{
		URL neturl = new URL(url);
		BufferedReader br = new BufferedReader(new InputStreamReader(neturl.openStream()));
		String jsontext = "";
		String temp;
		while((temp=br.readLine())!=null)
			jsontext = jsontext.concat(temp);
		br.close();
		return jsontext;
	}
	
	public ArrayList<Event> getListofEventsFromJSON(String url) throws IOException, JarException, ParseException, NumberFormatException, JSONException
	{
		ArrayList<Event> events = new ArrayList<>();
		url = updateURL(url);		
		int currentPage = 1;
		int totalPage = 1;
		while(currentPage <= totalPage)
		{
			url = url.concat("&page="+currentPage);			
			url.replace("&page="+(currentPage-1), "");
			String jsontext = getJsontext(url);					
			JSONObject json = new JSONObject(jsontext);
			totalPage =  hasPagination(json);	
			org.json.JSONArray listings = json.getJSONArray("events"); // getting the actual Events array.
			for(int i=0; i< listings.length(); i++) // for every event in the array, retrieving the required materials.
			{
				JSONObject iterateObj = listings.getJSONObject(i);
				String name = iterateObj.getJSONObject("name").getString("text");
				String startDate = iterateObj.getJSONObject("start").getString("local");
				String description = iterateObj.getJSONObject("description").getString("html");
				if(iterateObj.has("vanity_url"))
						description = description.concat(iterateObj.getString("vanity_url"));
				String id  = iterateObj.get("id").toString();
				int capacity = (int) iterateObj.get("capacity");
				JSONObject addressObj = iterateObj.getJSONObject("venue").getJSONObject("address");
				Address address = getAddressFromVenue(addressObj);
				org.json.JSONArray arr = iterateObj.getJSONArray("ticket_classes");
				double ticket_price =0;	
				if( arr.length()>0 && !arr.getJSONObject(0).getBoolean("free") && arr.getJSONObject(0).has("cost"))
					ticket_price = (double) arr.getJSONObject(0).getJSONObject("cost").getDouble("value")/100;
				events.add(makeEventObj(name,startDate,description,id,capacity,address,ticket_price,EventSource.EventBrite));
			}			
			if(events.size() > 50)
				break;
			currentPage++;
		}

		return events;
	}
	
	private Event makeEventObj(String name,String startDate,String description,String id,int capacity,Address address,double ticket_price,EventSource eventbrite) throws ParseException
	{
		Event event = new Event();
		event.setName(name);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(startDate.substring(0,10));
		event.setDate(date);
		event.setDescription(description);
		event.setAddress(address);
		event.setId(id);
		event.setRemainingTickets(capacity);
		event.setTicketPrice(ticket_price);
		return event;
	}
	
	private Address getAddressFromVenue(JSONObject temp) throws JSONException
	{
		Address address = new Address();
		address.setAddressLine1(temp.getString("address_1"));
		address.setAddressLine2(temp.getString("address_2"));
		address.setCity(temp.getString("city"));
		address.setState(temp.getString("region"));
		address.setCountry(temp.getString("country"));
		address.setZipCode(temp.getString("postal_code"));
		address.setLatitude(Float.parseFloat(temp.getString("latitude")));
		address.setLongitude(Float.parseFloat(temp.getString("longitude")));
		return address;
	}
	
	private int hasPagination(JSONObject json) throws NumberFormatException, JSONException {
		int page_count= 1;
		if(json.has("pagination")) // checking if the page has pagination tag to summarize the returns
			page_count =Integer.parseInt(json.getJSONObject("pagination").get("page_count").toString());
		return page_count;
	}
	
	private String updateURL(String url)
	{
		final String KEY = "NI22KJTJHZUPJKYDVEUZ";
		if(!url.contains("&token"))
			url = url.concat("&token="+KEY);
		if(!url.contains("&format=json"))
			url = url.concat("&format=json");
		if(!url.contains("&expand"))
			url = url.concat("&expand=venue,ticket_classes");
		return url;
	}
}