package edu.neu.cs5500.jerks.business.test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.json.JSONException;
import org.junit.Test;

import edu.neu.cs5500.Jerks.apiCall.GoogleAddressToLatLong;
import edu.neu.cs5500.Jerks.business.*;
import edu.neu.cs5500.Jerks.definitions.Event;
import junit.framework.Assert;

public class GeoCoderTest {

	@Test
	public void validcity() throws IOException, JSONException
	{
		String address = "Los Angeles";
		float latitude = 34.0500f;
		float longitude = 118.2500f;
		GoogleAddressToLatLong google = new GoogleAddressToLatLong();
		float[] latlong= google.getLatLong("", "", address, "", "");
		boolean match = checkLatlong(latitude,longitude,latlong);
		Assert.assertTrue("latlongs are not good for los Angeles", match);
	}

	private boolean checkLatlong(float latitude, float longitude, float[] latlong) {
		if((Math.abs(Math.abs(latlong[0])-latitude) < 2)
			&&
			(Math.abs(Math.abs(latlong[1])-longitude) < 2))
			return true;
		else
			return false;
	}
}
