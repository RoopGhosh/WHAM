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
	@Test
	public void validZip() throws IOException, JSONException
	{
		String zip = "02150";
		float latitude = 42.40f;
		float longitude = -71.03f;
		GoogleAddressToLatLong google = new GoogleAddressToLatLong();
		float[] latlong= google.getLatLong("", "", "", "", zip);
		boolean match = checkLatlong(latitude,longitude,latlong);
		Assert.assertTrue("latlongs are not good for boston zipCode", match);
	}
	@Test
	public void validpinPointAddress() throws IOException, JSONException
	{
		String addr1 = "75 st alphonsus st";
		String addr2 = "#203";
		String city = "Boston";
		String state = "MA";
		float latitude = 42.334305f;
		float longitude = -71.10083f;
		GoogleAddressToLatLong google = new GoogleAddressToLatLong();
		float[] latlong= google.getLatLong(addr1, addr2, city, state,"");
		boolean match = checkLatlong(latitude,longitude,latlong);
		Assert.assertTrue("latlongs are not good for St alphonsus st", match);
	}
	
	@Test
	public void validState() throws IOException, JSONException
	{
		
		String state = "TX";
		float latitude = 31.9686f;
		float longitude = -99.90181f;
		GoogleAddressToLatLong google = new GoogleAddressToLatLong();
		float[] latlong= google.getLatLong("", "", "", state,"");
		boolean match = checkLatlong(latitude,longitude,latlong);
		Assert.assertTrue("latlongs are not good for Texas State", match);
	}
	@SuppressWarnings("unused")
	@Test
	public void noAddress() throws IOException, JSONException
	{
		
		String state = "";
		float[] latlong = new float[2];
		GoogleAddressToLatLong google = new GoogleAddressToLatLong();
		String errorExpected = "JSONArray[0] not found.";
		String catchError = "";
		try
		{
			latlong= google.getLatLong("", "", "", state,"");
		}
		catch(Exception e){
			catchError= e.getMessage();
		}
		/*System.out.println(latlong[0]);
		System.out.println(latlong[1]);*/
		Assert.assertTrue("null adddress doesnt return null JSON",errorExpected.contains(catchError));
	}
	

	private boolean checkLatlong(float latitude, float longitude, float[] latlong) {
		if((Math.abs(Math.abs(latlong[0])-Math.abs(latitude)) < 2)
			&&
			(Math.abs(Math.abs(latlong[1])-Math.abs(longitude)) < 2))
			return true;
		else
			return false;
	}
}
