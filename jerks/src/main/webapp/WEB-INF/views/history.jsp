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
	<spring:url value="/resources/img/brandImage.JPG" var="brandIcon" />
<%	
	String username = String.valueOf(session.getAttribute("username"));
	String password = String.valueOf(session.getAttribute("password"));
	String latitude = String.valueOf(session.getAttribute("latitude"));
	String longitude = String.valueOf(session.getAttribute("longitude"));
	EventVisitedProvider dao = new EventVisitedProvider();
	List<EventVisited> listOfEvents = dao.findAllEventsVisitedBy(username);
	String eventsVisited = new Gson().toJson(listOfEvents);
	UserProvider userDao = new UserProvider();
	User user = userDao.findByEmail(username);
	String userName = user.getFirstName();
%>
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/font-awesome/4.0.0/css/font-awesome.css" />
<link rel="stylesheet" type="text/css" href="${MainCSS}">
<link rel="icon" type="image/gif" href="${favIcon}" />
<script src="${GeoLocator}"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
<script src="${BootStrap}"></script>
<script src="${Login}"></script>
<script type="text/javascript" 
           src="http://maps.google.com/maps/api/js?sensor=false"></script>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>

<title>WHAM - Home</title>
</head>
<body>
	<div class="container-fluid">
		<!-- Header Start -->
		<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
			<div class="navbar-header">
				<a class="navbar-brand" rel="home" href="/jerks"><img
					src="${brandIcon}" alt="WHAM"
					style="width: 42px; height: 42px; border: 0;"></a>
				<ul class="nav navbar-nav">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"><label><%=userName %>
							</label><span
							class="glyphicon glyphicon-user icon-large brown pull-left">&nbsp;</span></a>
						<ul class="dropdown-menu">
							<li><a href="/jerks/createEvents">Create Event<span
									class="glyphicon glyphicon-bullhorn icon-large brown pull-right"></span></a></li>
							<li class="divider"></li>
							<li><a href="/jerks/profile">Profile<span
									class="glyphicon glyphicon-cog icon-large brown pull-right"></span></a></li>
							<li class="divider"></li>
							<li><a href="/jerks/history">My Events<span
									class="glyphicon glyphicon-time icon-large brown pull-right"></span></a></li>
							<li class="divider"></li>
							<li><a href="/jerks/logout">Logout<span
									class="glyphicon glyphicon-off icon-large brown pull-right"></span></a></li>
						</ul></li>
				</ul>
			</div>
				<div class="form-group">
				
					<form action="/jerks/login"  method="post">
						<input type = "hidden" name="username" value=<%=username %>>
						<input type = "hidden" name="password" value=<%=password %>>
						<input type ="hidden"  name="latitude" value = "${latitude}"> 
						<input type ="hidden"  name="longitude" value ="${longitude}"> 
						<button type="submit" class="btn btn-success navbar-btn">New Search</button>
					</form>
				</div>
			</div>
		</div>

	<!-- Header End -->

		<div class="main">
			<div id="eventDetails" class="eventDetails">
				<h3>My Events</h3>
				<table class="table">
					<tr>
						<th>Event Name</th>
						<th>Event Date</th>
						<th>Event Address</th>
					</tr>
					<% for(EventVisited event : listOfEvents)
						{
							%>
								<tr>
									<td><%=event.getEventName() %></td>
									<td><%=event.getEventDate() %></td>
									<td><%=event.getEventAddress() %></td>
								</tr>
							<%
						}%>
						
				</table>
			</div>
	</div>
	<div class="footer">		
		<p style="float:center">&copy; JeRKS (CS5500)</p>
		<p style="float:right">CCIS - Northeastern University</p>
	</div>
</body>
</html>