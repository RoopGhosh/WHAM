package edu.neu.cs5500.Jerks.apiCall;
import java.io.IOException;
import org.json.*;


import edu.neu.cs5500.Jerks.apiCall.EventbriteAPICall;
public class GoogleAddressToLatLong {
	public final String googleGeocoder = "http://maps.googleapis.com/maps/api/geocode/json?address=";
	public double[] getLatLong(String addressLine1, String addressLine2, String city, String state, String zipCode) throws IOException, JSONException
	{
		double[] latlong = new double[2];
		EventbriteAPICall jsongetter = new EventbriteAPICall();
		String url = addressLine1+addressLine2+city+state+zipCode;
		url = url.replaceAll("\\s", "+");
		url = googleGeocoder+url;
		String jsonText = jsongetter.getJsontext(url);
		JSONObject json = new JSONObject(jsonText);
		JSONArray jsonarr = json.getJSONArray("results");
		JSONObject obj = (JSONObject) jsonarr.get(0);
		JSONObject geoLocation = obj.getJSONObject("geometry").getJSONObject("location");
		latlong[0] = Double.parseDouble(String.valueOf(geoLocation.get("lat")));
		latlong[1] = Double.parseDouble(String.valueOf(geoLocation.get("lng")));
		return latlong;
	}
}
