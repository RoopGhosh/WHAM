package edu.neu.cs5500.Jerks.apiCall;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
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
	private final int RESULTSIZE = 49;
	public String getJsontext(String url) throws IOException
	{
		URL neturl = new URL(url);
		BufferedReader br = new BufferedReader(new InputStreamReader(neturl.openStream()));
		String jsontext = "";
		String temp;
		while((temp=br.readLine())!=null)
			jsontext = jsontext.concat(temp);
		return jsontext;
	}
	
	public ArrayList<Event> getListofEventsFromJSON(String url) throws IOException, JarException, ParseException, NumberFormatException, JSONException
	{
		ArrayList<Event> events = new ArrayList<>();
		url = updateURL(url);
		System.out.println(url);
		String jsontext = getJsontext(url);
		//System.out.println(jsontext);
		//creating jsonobject from text
		JSONObject json = new JSONObject(jsontext);
		int page_size = hasPagination(json);
		for(int j=0;j<page_size;j++) // now for no of page of results, we loop...
		{
			url = url.concat("&page="+j);
			jsontext = getJsontext(url);
			if(j>0)
				json = new JSONObject(jsontext);
			org.json.JSONArray listings = json.getJSONArray("events"); // getting the actual Events array.
			//System.out.println(j);
			for(int i=0;i<listings.length() || events.size()<50 ;i++) // for every event in the array, retrving the required materials.
			{
				JSONObject iterateObj = listings.getJSONObject(i);
				JSONObject temp = iterateObj.getJSONObject("name"); // just getting the "name" for the time being.
				String name = temp.toString().split("text")[1].split(",")[0].replaceAll("\"", "");
				temp = iterateObj.getJSONObject("start");
				String startDate = temp.toString().split("local")[1].split(",")[0].replaceAll("\"", "").replaceAll(":", "");
				temp = iterateObj.getJSONObject("description");
				String description = temp.toString().split("text")[1].split(",")[0].replaceAll("\"", "");
				if(iterateObj.has("vanity_url"))
						description = description.concat(iterateObj.getString("vanity_url"));
				String id  = iterateObj.get("id").toString();
				int capacity = (int) iterateObj.get("capacity");
				temp = iterateObj.getJSONObject("venue").getJSONObject("address");
				Address address = getAddressFromVenue(temp);
				org.json.JSONArray arr = iterateObj.getJSONArray("ticket_classes");
				//System.out.println(id);
				double ticket_price =0;	
				/*temp = arr.getJSONObject(0);
				temp = temp.getJSONObject("cost");*/
				if( arr.length()>0 && !arr.getJSONObject(0).getBoolean("free") && arr.getJSONObject(0).has("cost"))
					ticket_price = (double) arr.getJSONObject(0).getJSONObject("cost").getDouble("value")/100;
				events.add(makeEventObj(name,startDate,description,id,capacity,address,ticket_price,EventSource.EventBrite));
			}
			if(events.size()>RESULTSIZE)
				break;
		}
		if(Integer.parseInt(json.getJSONObject("pagination").get("page_count").toString()) == events.size())
			System.out.println("Write on !!");
		System.out.println("DONE!!" + events.size());
		return events;
	}
	
	public Event makeEventObj(String name,String startDate,String description,String id,int capacity,Address address,double ticket_price,EventSource eventbrite) throws ParseException
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
	
	public Address getAddressFromVenue(JSONObject temp) throws JSONException
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
	
	public static void main(String args[]) throws IOException, JSONException, ParseException
	{
		EventbriteAPICall obj = new EventbriteAPICall();
		System.out.println("Enter the url you want to retrive with your key");
		Scanner s = new Scanner(System.in);
		String url = s.nextLine();
		s.close();
		obj.getListofEventsFromJSON(url);
	}
	public int hasPagination(JSONObject json) throws NumberFormatException, JSONException {
		int page_size= 1;
		if(json.has("pagination")) // checking if the page has pagination tag to summarize the returns
			page_size =Integer.parseInt(json.getJSONObject("pagination").get("page_count").toString());
		return page_size;
	}
	
	public String updateURL(String url)
	{
		final String KEY = "NI22KJTJHZUPJKYDVEUZ";
		if(!url.contains("&token"))
			url = url.concat("&token="+KEY);
		if(!url.contains("&format=json"))
			url = url.concat("&format=json");
		if(!url.contains("&expand"))
			url = url.concat("&expand=venue,ticket_classes");
		if(!url.contains("&page"))
			url = url.concat("&page=1");
		return url;
	}
}