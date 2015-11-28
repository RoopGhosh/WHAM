package edu.neu.cs5500.Jerks.controller;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@RequestParam("firstName") String firstName,
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
		   // @RequestParam("datepicker") Date date,
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
		//model.put("date", date);
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
