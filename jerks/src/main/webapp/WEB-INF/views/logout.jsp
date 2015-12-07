<%@page import="org.springframework.ui.ModelMap"%>
<%@page import="org.springframework.ui.Model"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="edu.neu.cs5500.Jerks.apiCall.*, edu.neu.cs5500.Jerks.definitions.*,java.io.*,java.util.*,java.text.*, edu.neu.cs5500.Jerks.dbProviders.*, edu.neu.cs5500.Jerks.business.*, com.google.gson.Gson ,java.util.*"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
	<spring:url value="/resources/css/main.css" var="MainCSS" />
	<spring:url value="/resources/js/bootstrap.min.js" var="BootStrap" />
	<spring:url value="/resources/img/favicon.GIF" var="favIcon" />
	<spring:url value="/resources/img/ripple.gif" var="Ripple" />
	<title>Logout</title>
<%	
	String latitude = String.valueOf(session.getAttribute("latitude"));
	String longitude = String.valueOf(session.getAttribute("longitude"));
	session.invalidate(); 
%>
<script>
	function click()
	{	
		location.href = "index" +"/"+ <%=latitude%>  +"/"+ <%=longitude%>;
	}
</script>
</head>
<body onload="click()">
	<div style = "position: fixed; top: 20%; left: 42%;">
	<img src ="${Ripple}" >
	<br>
	<p style="font-size:20px">&nbsp;Please wait....Logging Out&nbsp;........</p>
</body>
</html>