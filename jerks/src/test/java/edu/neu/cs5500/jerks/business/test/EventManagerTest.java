package edu.neu.cs5500.jerks.business.test;

import java.text.SimpleDateFormat;
import java.util.*;

import org.junit.Test;

import edu.neu.cs5500.Jerks.business.*;
import junit.framework.Assert;

public class EventManagerTest {

	///Sample Test case
	/*@Test
	public void testFetchEvents() {
		double latitude = 42.3132882;
		double longitude = -71.1972408;
		Calendar c = Calendar.getInstance(); // starts with today's date and time
		c.add(Calendar.DAY_OF_YEAR, 2);  // advances day by 2
		Date date = c.getTime();
		EventManager em = new EventManager();
		List<Event> result = em.fetchEvents(latitude, longitude, "", "", "", date, new String[0], new String[0]);
		Assert.assertTrue("returned event list size does not meet requirements", result.size()  > 0 && result.size()<200);
	}*/
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
}
