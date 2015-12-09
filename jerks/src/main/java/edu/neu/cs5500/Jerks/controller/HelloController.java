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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.neu.cs5500.Jerks.apiCall.GoogleAddressToLatLong;
import edu.neu.cs5500.Jerks.dbProviders.EventProvider;
import edu.neu.cs5500.Jerks.dbProviders.EventVisitedProvider;
import edu.neu.cs5500.Jerks.dbProviders.UserProvider;
import edu.neu.cs5500.Jerks.definitions.Address;
import edu.neu.cs5500.Jerks.definitions.Event;
import edu.neu.cs5500.Jerks.definitions.EventSource;
import edu.neu.cs5500.Jerks.definitions.EventVisited;
import edu.neu.cs5500.Jerks.definitions.User;

@Controller
public class HelloController {

	
	
	@RequestMapping(value = "/index/{latitude:.+}/{longitude:.+}", method = RequestMethod.GET)
	public String index (@PathVariable("latitude") String a,@PathVariable("longitude") double b, ModelMap model)
	{
		try{
			Double latitude = (double) Double.parseDouble(a);
			Double longitude = b;
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
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam("latitude") String latitude,
			@RequestParam("longitude") String longitude,
			@RequestParam("username") String username,
			@RequestParam("password") String password,			
			ModelMap model) throws IOException
	{
		model.put("latitude", latitude);
		model.put("longitude", longitude);
		model.put("username", username);
		model.put("password", password);
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
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String profile (ModelMap model)
	{
		System.out.println("Hello from profile controller");
		
		return "profile";
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search (
			@RequestParam("type") String type,
			@RequestParam("search") String search,
			@RequestParam("daysWithin") String daysWithin,
			@RequestParam("catagories") String catagories,
			@RequestParam("price") String price,
			ModelMap model)
	{
		System.out.println("Hello from SEARCH controller:"+ catagories);
		System.out.println("Hello from SEARCH controller:"+ price);
		System.out.println("Hello from SEARCH controller:"+ daysWithin);
		model.put("search", search);
		model.put("daysWithin", daysWithin);
		model.put("catagories", catagories);
		model.put("price", price);
		return "index";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName,
			@RequestParam("username") String email,
			@RequestParam("latitude") String latitude,
			@RequestParam("longitude") String longitude,
		    @RequestParam("password") String password, 
		    @RequestParam("addrLine1") String addrLine1, 
		    @RequestParam("addrLine2") String addrLine2,
		    @RequestParam("city") String city,
		    @RequestParam("state") String state,
		    @RequestParam("zipCode") String zipCode,
		    @RequestParam("datepicker") String date,
		    @RequestParam("optradio") String gender,
		    @RequestParam("phoneNumber") String phoneNumber,
		    ModelMap model) throws ParseException
	{
		
		String dob = date;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date parsed = sdf.parse(dob);
		java.sql.Date sql = new java.sql.Date(parsed.getTime());
		UserProvider userDao = new UserProvider();
		User user = userDao.findByEmail(email);
		if(user != null)
		{
			Address addr = user.getAddress();
			addr.setAddressLine1(addrLine1.trim());
			addr.setAddressLine2(addrLine2.trim());
			addr.setCity(city.trim());
			addr.setState(state.trim());
			addr.setZipCode(zipCode.trim());
			user.setFirstName(firstName.trim());
			user.setLastName(lastName.trim());
			user.setPassword(password.trim());
			user.setPhoneNumber(phoneNumber.trim());
			user.setGender(gender.trim());
			user.setDOB(sql);
			User updatedUser = userDao.updateUser(email, user);
		}
		
		model.put("username", email);
		model.put("password", password);
		model.put("latitude", latitude);
		model.put("longitude", longitude);
		System.out.println("Hello from update controller firstName: "+firstName);
		System.out.println("Hello from post");
		
	//	System.out.println("Hello from post date"+date);
		return "index";
	}
	
	@RequestMapping(value = "/history", method = RequestMethod.GET)
	public String historyPage(ModelMap model)
	{
		return "history";
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
	
	@RequestMapping(value = "/buy", method = RequestMethod.POST)
	public String buyEvent(@RequestParam("latitude") String latitude, 
			@RequestParam("longitude") String longitude,
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("eventName") String eventName,
			@RequestParam("date") String date,
			@RequestParam("address") String address,
			@RequestParam("source") String source,
			@RequestParam("eventId") String eventId,
			ModelMap model)
	{	
		System.out.println("Date: "+date);
		model.put("username", username);
		model.put("password", password);
		model.put("latitude", latitude);
		model.put("longitude", longitude);
		
		System.out.println("Event Name: "+eventName);
		eventName = eventName.replace("%20", " ");
		eventName = eventName.replace("%3A", ";");
		address = address.replace("%20", " ");
		address = address.replace("%3A", ";");
		EventVisited visited = new EventVisited(username,eventId, eventName, address, date, EventSource.WHAM) ;
		EventVisitedProvider dao = new EventVisitedProvider();
		dao.createEventsVisted(visited);
		return "index";
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
			@RequestParam("source") String source,
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
		model.put("source", source);
		System.out.println("From eventsdetailsController lat "+latitude);
		System.out.println("From eventsdetailsController long "+longitude);
		System.out.println();
		return "eventDetails";
	}
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(ModelMap model)
	{
		return "logout";
	}
	
	/*@RequestMapping(value = "/resources/**")
	   public String redirect() {
	     System.out.println();
	      return "redirect:/resources/**";
	   }*/

}
