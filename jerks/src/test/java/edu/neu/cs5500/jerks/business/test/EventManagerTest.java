package edu.neu.cs5500.jerks.business.test;

import java.util.*;

import org.junit.Test;

import edu.neu.cs5500.Jerks.business.*;
import edu.neu.cs5500.Jerks.definitions.*;
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
		Assert.assertTrue(result.size()  > 0 );
	}

}
