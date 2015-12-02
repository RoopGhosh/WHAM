package edu.neu.cs5500.Jerks.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.neu.cs5500.Jerks.apiCall.GoogleAddressToLatLong;
import edu.neu.cs5500.Jerks.dbProviders.EventProvider;
import edu.neu.cs5500.Jerks.definitions.Address;
import edu.neu.cs5500.Jerks.definitions.Event;
import edu.neu.cs5500.Jerks.definitions.EventSource;
import edu.neu.cs5500.Jerks.definitions.User;

@Controller
public class HelloController {

	@RequestMapping(value = "/index/{latitude}/{longitude}", method = RequestMethod.GET)
	public String index (@PathVariable("latitude") String a,@PathVariable("longitude") String b, ModelMap model)
	{
		try{
			Double latitude = (double) Double.parseDouble(a);
			Double longitude = (double) Double.parseDouble(b);
			model.put("latitude", latitude);
			model.put("longitude", longitude);
			return "index";
		}
		catch(Exception e) {
			System.out.println("From controller catch block");
			return "geolocator";
		}
	}
	
	@RequestMapping(value = {"/geolocator","/index", "/index/"}, method = RequestMethod.GET)
	public String geolocator (ModelMap model)
	{
		System.out.println("Hello from geolocator");
		return "geolocator";
	}
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String b (@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("latitude") String latitude,
			@RequestParam("longitude") String longitude,   ModelMap model)
	{
		model.put("username", username);
		model.put("password", password);
		model.put("latitude", latitude);
		model.put("longitude", longitude);
		return "index";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String defaultMethod ()
	{
		System.out.println("Hello from default");
		return "geolocator";
	}
	
	@RequestMapping(value = "/createEvents", method = RequestMethod.GET)
	public String createUserEvents(ModelMap model)
	{
		return "createEvents";
	}
	
	
	@RequestMapping(value = "/createEvents", method = RequestMethod.POST)
	public String createUserEventsPOST(
			@RequestParam("eventName") String eventName,
			@RequestParam("datepicker") String datepicker,
			@RequestParam("addressLine1") String addressLine1,
			@RequestParam("addressLine2") String addressLine2,
			@RequestParam("city") String city,
			@RequestParam("state") String state,
			@RequestParam("zipCode") String zipCode,
			@RequestParam("description") String description,
			@RequestParam("ticketPrice") String ticketPrice,
			@RequestParam("minAgeLimit") String minAgeLimit,
			@RequestParam("remainingTickets") String remainingTickets,
			@RequestParam("latitude") String latitude,
			@RequestParam("longitude") String longitude,
			@RequestParam("username") String username,
			@RequestParam("password") String password,			
			ModelMap model) throws IOException, JSONException
	{
		GoogleAddressToLatLong addr2Latlong = new GoogleAddressToLatLong();
		float[] latlong = addr2Latlong.getLatLong(addressLine1, addressLine2, city, state, zipCode);
		System.out.println("Latitude from create event controller: "+latitude);
		System.out.println("Longitude from create event controller: "+longitude);
		System.out.println("username from create event controller: "+username);
		System.out.println("paswword from create event controller: "+password);
		model.put("username", username);
		model.put("password", password);
		model.put("latitude", latitude);
		model.put("longitude", longitude);
		
		try
		{
			String dob = datepicker;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date parsed = sdf.parse(dob);
			java.sql.Date sql = new java.sql.Date(parsed.getTime());
		
			double ticketPrice1 = Double.parseDouble(ticketPrice);
			int minAgeLimit1 = Integer.parseInt(minAgeLimit);
			int remainingTickets1 = Integer.parseInt(remainingTickets);
        
        
			Address addr = new Address(addressLine1, addressLine2,city,state,"US", zipCode,latlong[0],latlong[1]);
			EventProvider eventDao = new EventProvider();
			EventSource source = EventSource.WHAM;
			Event event = new Event(eventName, sql, addr, description, ticketPrice1, minAgeLimit1,
				0.0, remainingTickets1, source);
			if(event != null)
			eventDao.createEvent(event);
			else
				System.out.println("Null event");
			
			System.out.println("Event Created");
		}
		catch(Exception e)
		{
			System.out.println("Could not create event");
		}
		
		return "index";
	}
		

	@RequestMapping(value = "/dislike/{email}/{eventName}/{latitude}/{longitude}", method = RequestMethod.GET)
	public String dislike (@PathVariable("email") String email, @PathVariable("eventName") String eventName, 
			@PathVariable("latitude") String lat,@PathVariable("longitude") String longi, ModelMap model)
	{
		try{
			System.out.println("in dislike try block Start");
			model.put("email", email);
			model.put("eventName", eventName);
			model.put("latitude", lat);
			model.put("longitude", longi);
			System.out.println("in dislike try block end");
			return "dislike";
		}
		catch(Exception e) {
			System.out.println("From dislike controller catch block");
			return "geolocator";
		}
	}
	

	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(
			@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName,
			@RequestParam("email") String email,
			@RequestParam("latitude") String latitude,
			@RequestParam("longitude") String longitude,
		    @RequestParam("password") String password, 
		    @RequestParam("addrLine1") String addrLine1, 
		    @RequestParam("addrLine2") String addrLine2,
		    @RequestParam("city") String city,
		    @RequestParam("state") String state,
		    @RequestParam("zipCode") String zipCode,
		    @RequestParam("category") List<String> category,
		    @RequestParam("datepicker") String date,
		    @RequestParam("optradio") String gender,
		    @RequestParam("phoneNumber") String phoneNumber,
		    ModelMap model)
	{
		model.put("firstName", firstName);
		model.put("lastName", lastName);
		model.put("username", email);
		model.put("password", password);
		model.put("addrLine1", addrLine1);
		model.put("addrLine2", addrLine2);
		model.put("city", city);
		model.put("state", state);
		model.put("zipCode", zipCode);
		model.put("category", category);
		model.put("date", date);
		model.put("gender", gender);
		model.put("gender", gender);
		model.put("latitude", latitude);
		model.put("longitude", longitude);
		model.put("phoneNumber", phoneNumber);
		System.out.println("Hello from controller firstName: "+firstName);
		System.out.println("Hello from controller for category: "+category);
		System.out.println("Hello from post");
	//	System.out.println("Hello from post date"+date);
		return "index";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerFromIndex(@RequestParam("latitude") String a, @RequestParam("longitude") String b, ModelMap model)
	{
		model.put("latitude",a);
		model.put("longitude",b);
		return "register";
	}
	
	@RequestMapping(value = "/eventDetails", method = RequestMethod.POST)
	public String eventDetailsFromIndex(@RequestParam("latitude") String latitude, 
			@RequestParam("longitude") String longitude,
			@RequestParam("date") String date,
			@RequestParam("description") String description,
			@RequestParam("eventId") String eventId,
			@RequestParam("minAgeLimit") String minAgeLimit,
			@RequestParam("name") String name,
			@RequestParam("rating") String rating,
			@RequestParam("remainingTickets") String remainingTickets,
			@RequestParam("addressLine1") String addressLine1,
			@RequestParam("addressLine2") String addressLine2,
			@RequestParam("city") String city,
			@RequestParam("eventLatitude") String eventLatitude,
			@RequestParam("eventLongitude") String eventLongitude,
			@RequestParam("state") String state,
			@RequestParam("zipCode") String zipCode,
			@RequestParam("username") String user,
			@RequestParam("ticketPrice") String ticketPrice,
			ModelMap model)
	{
		System.out.println("Hello from eventsDetails Controller");
		model.put("latitude",latitude);
		model.put("longitude",longitude);
		model.put("date",date);
		model.put("description",description);
		model.put("eventId",eventId);
		model.put("minAgeLimit",minAgeLimit);
		model.put("name",name);
		model.put("remainingTickets",remainingTickets);
		model.put("addressLine1",addressLine1);
		model.put("addressLine2",addressLine2);
		model.put("city",city);
		model.put("eventLatitude",eventLatitude);
		model.put("eventLongitude",eventLongitude);
		model.put("state",state);
		model.put("zipCode",zipCode);
		model.put("username", user);
		model.put("ticketPrice", ticketPrice);
		System.out.println("From eventsdetailsController lat "+latitude);
		System.out.println("From eventsdetailsController long "+longitude);
		System.out.println();
		return "eventDetails";
	}
	
	/*@RequestMapping(value = "/resources/**")
	   public String redirect() {
	     System.out.println();
	      return "redirect:/resources/**";
	   }*/

}
