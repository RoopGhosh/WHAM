<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*, edu.neu.cs5500.Jerks.business.EventManager,
     edu.neu.cs5500.Jerks.definitions.Event, com.google.gson.Gson;"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="js/GeoLocation.js"></script>
<title>WHAM - Home</title>
</head>
<body>
<%
try {
	//TODO: Design Jsp form to get search parameters from user.
double latitude = Double.parseDouble(request.getParameter("latitude"));
double longitude = Double.parseDouble(request.getParameter("longitude"));
String searchAddress = null;
String searchEvent = null; 
String price = null;
Date date = null;
String[] categories = null;
EventManager em = new EventManager();
List<Event> events = em.fetchEvents(latitude, longitude, searchAddress, searchEvent, price, date, categories);
String jsonEvents = new Gson().toJson(events);
} catch(Exception e) {
	response.sendRedirect("geolocator.html");
}
%>
</body>
</html>