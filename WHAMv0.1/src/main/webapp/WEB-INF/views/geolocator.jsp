<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
	<spring:url value="/resources/js/GeoLocation.js" var="GeoLocator" />
	<spring:url value="/resources/js/GoogleMaps.js" var="GoogleMaps" />
	<spring:url value="/resources/css/main.css" var="MainCSS" />
	<spring:url value="/resources/img/ripple.gif" var="Ripple" />
<meta charset="ISO-8859-1">
<title>Detecting Location - Wait..</title>
<script src="${GeoLocator}"></script>
</head>
<body>
<div style = "position: fixed; top: 20%; left: 42%;">
<img src ="${Ripple}" >
<br>
<p style="font-size:20px">........&nbsp;Locating User&nbsp;........</p>
<p align="center" style="font-size:16px">Please allow us to detect your<br>location to proceed</p>
<script>getLocation()</script>
</div>
</body>
</html>