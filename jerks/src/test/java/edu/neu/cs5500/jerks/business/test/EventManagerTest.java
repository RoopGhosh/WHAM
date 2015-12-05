package edu.neu.cs5500.jerks.business.test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.junit.Test;

import edu.neu.cs5500.Jerks.business.*;
import edu.neu.cs5500.Jerks.definitions.Event;
import junit.framework.Assert;

public class EventManagerTest {

	///Sample Test case
	@Test
	public void testFetchEvents() {
		double latitude = 42.3132882;
		double longitude = -71.1972408;
		Calendar c = Calendar.getInstance(); // starts with today's date and time
		c.add(Calendar.DAY_OF_YEAR, 2);  // advances day by 2
		Date date = c.getTime();
		EventManager em = new EventManager();
		List<Event> result = em.fetchEvents(latitude, longitude, "", "", "", date, new String[0], new String[0]);
		Assert.assertTrue("returned event list size does not meet requirements", result.size()  > 0 && result.size()<200);
	}
	
	@Test
	public void buildEventbriteURL_W_O_Address() {
		String dateStart = "https://www.eventbriteapi.com/v3/events/search/?popular=true&sort_by=distance&q=&location.within=25mi&price=&location.latitude=0.0&location.longitude=0.0&start_date.range_start=";
		String dateMid = "T00:00:00Z&start_date.range_end=";
		String dateEnd = "T23:59:59Z&categories=110,102";
		double latitude = 0.0f;
		double longitude = 0.0f;
		String searchAddress = "";
		String searchEvent = ""; 
		String price = "";
		Date date = new Date();// gets modified time
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String currDate = sdf.format(date);
		Calendar c = Calendar.getInstance();
		c.add(c.DAY_OF_MONTH, 5);
		String future = sdf.format(c.getTime());
		final String expectedRESULT = dateStart+currDate+dateMid+future+dateEnd;
		String loginMessage = "HowdyUser!";
		// Remove hardcoded categories & dislikes
		String[] categories = {"food", "science"};
		String[] dislikes = {"music", "boston", "cheese"};
		EventManager em = new EventManager();
		String prepURL = em.buildEventbriteURL(latitude, longitude, searchAddress, searchEvent, price, date, categories);
		Assert.assertTrue("eventBriteURL builder failed", prepURL.equals(expectedRESULT));
	}
	@Test
	public void buildEventbriteURL_W_Address() {
		String dateStart = "https://www.eventbriteapi.com/v3/events/search/?popular=true&sort_by=distance&q=&location.within=25mi&price=&location.address=BOSTON&start_date.range_start=";
		String dateMid = "T00:00:00Z&start_date.range_end=";
		String dateEnd = "T23:59:59Z&categories=110,102";
		double latitude = 0.0f;
		double longitude = 0.0f;
		String searchAddress = "BOSTON";
		String searchEvent = ""; 
		String price = "";
		Date date = new Date();// gets modified time
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String currDate = sdf.format(date);
		Calendar c = Calendar.getInstance();
		c.add(c.DAY_OF_MONTH, 5);
		String future = sdf.format(c.getTime());
		final String expectedRESULT = dateStart+currDate+dateMid+future+dateEnd;
		String loginMessage = "HowdyUser!";
		// Remove hardcoded categories & dislikes
		String[] categories = {"food", "science"};
		String[] dislikes = {"music", "boston", "cheese"};
		EventManager em = new EventManager();
		String prepURL = em.buildEventbriteURL(latitude, longitude, searchAddress, searchEvent, price, date, categories);
		
		Assert.assertTrue("eventBriteURL builder failed", prepURL.equals(expectedRESULT));
	}

	@Test
	public void buildEventfulURL_W_O_Address() {
		String dateStart = "http://api.eventful.com/json/events/search?app_key=4fgZC93XQz2fgKpV&sort_order=distance&units=miles&include=price&page_size=50&q=&l=0.0,0.0&price=&date=";
		String dateMid = "-";
		String dateEnd = "&category=food,science&within=25";
		double latitude = 0.0f;
		double longitude = 0.0f;
		String searchAddress = "";
		String searchEvent = ""; 
		String price = "";
		Date date = new Date();// gets modified time
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String currDate = sdf.format(date);
		Calendar c = Calendar.getInstance();
		c.add(c.DAY_OF_MONTH, 5);
		String future = sdf.format(c.getTime());
		final String expectedRESULT = dateStart+currDate+dateMid+currDate+dateEnd;
		String loginMessage = "HowdyUser!";
		// Remove hardcoded categories & dislikes
		String[] categories = {"food", "science"};
		String[] dislikes = {"music", "boston", "cheese"};
		EventManager em = new EventManager();
		String prepURL = em.buildEventfulURL(latitude, longitude, searchAddress, searchEvent, price, date, categories);
		Assert.assertTrue("eventBriteURL builder failed", prepURL.equals(expectedRESULT));
	}
	@Test
	public void buildEventfulURL_W_Address() {
		String dateStart = "http://api.eventful.com/json/events/search?app_key=4fgZC93XQz2fgKpV&sort_order=distance&units=miles&include=price&page_size=50&q=&l=Boston&price=&date=";
		String dateMid = "-";
		String dateEnd = "&category=food,science&within=25";
		double latitude = 0.0f;
		double longitude = 0.0f;
		String searchAddress = "Boston";
		String searchEvent = ""; 
		String price = "";
		Date date = new Date();// gets modified time
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String currDate = sdf.format(date);
		Calendar c = Calendar.getInstance();
		c.add(c.DAY_OF_MONTH, 5);
		String future = sdf.format(c.getTime());
		final String expectedRESULT = dateStart+currDate+dateMid+currDate+dateEnd;
		String loginMessage = "HowdyUser!";
		// Remove hardcoded categories & dislikes
		String[] categories = {"food", "science"};
		String[] dislikes = {"music", "boston", "cheese"};
		EventManager em = new EventManager();
		String prepURL = em.buildEventfulURL(latitude, longitude, searchAddress, searchEvent, price, date, categories);
		Assert.assertTrue("eventBriteURL builder failed", prepURL.equals(expectedRESULT));
	}
	
	@Test
	public void antarcticaEvents() {
		double latitude = 90.0000;
		double longitude = 0.000000;
		Calendar c = Calendar.getInstance(); // starts with today's date and time
		c.add(Calendar.DAY_OF_YEAR, 2);  // advances day by 2
		Date date = c.getTime();
		EventManager em = new EventManager();
		List<Event> result = em.fetchEvents(latitude, longitude, "", "", "", date, new String[0], new String[0]);
		Assert.assertTrue("Tests Failed: Apparently there are events in antartica?", result.size()  ==0);
	}
	@Test
	public void randomPlaceEvents() {
		double latitude = 0.0000;
		double longitude = 0.000000;
		String address = "blahLong";
		Calendar c = Calendar.getInstance(); // starts with today's date and time
		c.add(Calendar.DAY_OF_YEAR, 2);  // advances day by 2
		Date date = c.getTime();
		EventManager em = new EventManager();
		List<Event> result = new ArrayList<>();
		String errormsg = "";
		try{
			result = em.fetchEvents(latitude, longitude, address, "", "", date, new String[0], new String[0]);
		}
		catch (Exception IOException){
			 errormsg = IOException.getMessage();
		}
		Assert.assertTrue("Tests Failed: Apparently there are events in blahblahLand?", result.size()  ==0);
	}
	@Test
	public void TestComnination_ValidAddress_InvalidLatLong() {
		double latitude = -82.862752;
		double longitude = 135.000000;
		String address = "boston";
		Calendar c = Calendar.getInstance(); // starts with today's date and time
		c.add(Calendar.DAY_OF_YEAR, 2);  // advances day by 2
		Date date = c.getTime();
		EventManager em = new EventManager();
		List<Event> result = new ArrayList<>();
		String errormsg = "";
		try{
			result = em.fetchEvents(latitude, longitude, address, "", "", date, new String[0], new String[0]);
		}
		catch (Exception IOException){
			 errormsg = IOException.getMessage();
		}
		Assert.assertTrue("Tests Failed: Address and Latlong both?", result.size()  >0);
	}
	@Test
	public void TestComnination_InValidAddress_InvalidLatLong() {
		double latitude = 2999.99;
		double longitude = 135.000000;
		String address = "wertyuiopsdfghjkl";
		Calendar c = Calendar.getInstance(); // starts with today's date and time
		c.add(Calendar.DAY_OF_YEAR, 2);  // advances day by 2
		Date date = c.getTime();
		EventManager em = new EventManager();
		List<Event> result = new ArrayList<>();
		String errormsg = "";
		try{
			result = em.fetchEvents(latitude, longitude, address, "", "", date, new String[0], new String[0]);
		}
		catch (Exception IOException){
			 errormsg = IOException.getMessage();
		}
		Assert.assertTrue("Tests Failed: Address and Latlong both?", result.size()  ==0);
	}
	@Test
	public void TestCombination_paidonly() {
		double latitude = 0.0f;
		double longitude = 0.0f;
		String searchAddress = "boston";
		String price = "free";
		Date date = new Date();// gets modified time
		// Remove hardcoded categories & dislikes
		
		String errormsg = "";
		EventManager em = new EventManager();
		List<Event> result= new ArrayList<>();
		try{
			result = em.fetchEvents(latitude, longitude, searchAddress, "", price, date, new String[0], new String[0]);
		}
		catch (Exception IOException){
			 errormsg = IOException.getMessage();
		}
		Assert.assertTrue("Tests Failed: No free events in boston", result.size() >0);
	}
	
	// to be corrected!
	//@Test
	public void TestCombination_sameLike_dislike() {
		double latitude = 0.0f;
		double longitude = 0.0f;
		String searchAddress = "boston";
		String searchEvent = ""; 
		String price = "";
		Date date = new Date();// gets modified time
		// Remove hardcoded categories & dislikes
		String[] categories = {"food"};
		String[] dislikes = {"food"};
		String errormsg = "";
		EventManager em = new EventManager();
		List<Event> result= new ArrayList<>();
		try{
			result = em.fetchEvents(latitude, longitude, searchAddress, "", price, date, categories,dislikes);
		}
		catch (Exception IOException){
			 errormsg = IOException.getMessage();
		}
		System.out.println(result.size());
		Assert.assertTrue("Tests Failed: same like and dislikes should return 0 results.", result.size() ==0);
	}
	
	//-------------------------------------------------------------------------------//
	//                      		PERFORMANCE TESTS
	//-------------------------------------------------------------------------------//
	
	
	@Test
	public void performanceAPICalls()
	{
		long start = System.currentTimeMillis();
		double latitude = 42.3132882;
		double longitude = -71.1972408;
		Calendar c = Calendar.getInstance(); // starts with today's date and time
		c.add(Calendar.DAY_OF_YEAR, 2);  // advances day by 2
		Date date = c.getTime();
		EventManager em = new EventManager();
		em.fetchEvents(latitude, longitude, "", "", "", date, new String[0], new String[0]);
		long stop = System.currentTimeMillis();
		boolean threshold= false;
		if(stop-start<20001)
			threshold = true;
		Assert.assertTrue("fetchEvents Runs longer than 20 secs",threshold);
	}
}

