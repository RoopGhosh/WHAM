<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="edu.neu.cs5500.Jerks.apiCall.*,edu.neu.cs5500.Jerks.business.*, edu.neu.cs5500.Jerks.definitions.*,java.io.*,java.util.*,java.text.*, edu.neu.cs5500.Jerks.dbProviders.*, edu.neu.cs5500.Jerks.business.*, com.google.gson.Gson ,java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>WHAM</title>
<%
	try {
		String eventName = String.valueOf(request.getAttribute("eventName"));
		String username = String.valueOf(request.getAttribute("email"));
		String latitude = String.valueOf(request.getAttribute("latitude"));
		String longitude = String.valueOf(request.getAttribute("longitude"));
		
// 		UserManager um = new UserManager();
// 		um.dislikeEvent(username, eventName);		
		response.sendRedirect("/jerks/index/"+latitude+"/"+longitude);
		
	} catch (Exception e) {
		response.sendRedirect("geolocator");
	}
%>
</head>
<body>
<p>Thank you. Your response have been recored. You will not see this event anymore in the future.</p>
<p>Redirecting you to homepage... </p>
</body>
</html>