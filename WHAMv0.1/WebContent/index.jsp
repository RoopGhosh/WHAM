<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="js/GeoLocation.js"></script>
<title>WHAM - Home</title>
</head>
<body>
<%
try{
double latitude = Double.parseDouble(request.getParameter("latitude"));
double longitude = Double.parseDouble(request.getParameter("longitude"));
}
catch(Exception e)
{
	response.sendRedirect("geolocator.html");
}
%>
</body>
</html>