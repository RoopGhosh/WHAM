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
	<spring:url value="/resources/img/fb_share.jpg" var="facebookIcon" />
<%	
	String latitude = String.valueOf(request.getAttribute("latitude"));
	String longitude = String.valueOf(request.getAttribute("longitude"));
	String date = String.valueOf(request.getAttribute("date"));
	System.out.println("Event Date"+date);
	String description = String.valueOf(request.getAttribute("description"));
	String eventId = String.valueOf(request.getAttribute("eventId"));
	String minAgeLimit = String.valueOf(request.getAttribute("minAgeLimit"));
	String eventName = String.valueOf(request.getAttribute("name"));
	String remainingTickets = String.valueOf(request.getAttribute("remainingTickets"));
	String addressLine1 = String.valueOf(request.getAttribute("addressLine1"));
	String addressLine2 = String.valueOf(request.getAttribute("addressLine2"));
	String city = String.valueOf(request.getAttribute("city"));
	String state = String.valueOf(request.getAttribute("state"));
	String zipCode = String.valueOf(request.getAttribute("zipCode"));
	String eventLatitude = String.valueOf(request.getAttribute("eventLatitude"));
	String eventLongitude = String.valueOf(request.getAttribute("eventLongitude"));
	String username = String.valueOf(session.getAttribute("username"));
	String source = String.valueOf(request.getAttribute("source"));
	System.out.println("Event Description source"+source);
	String password="";
	String ticketPrice = String.valueOf(request.getAttribute("ticketPrice"));
	String completeAddress = addressLine1 + " " + addressLine2 + " " + city + " " + state + " " + zipCode;
	UserProvider userDao = new UserProvider();
	User user = null;
	String userName = "";
	try
	{
		user = userDao.findByEmail(username);
		if(user != null)
		{
			password  = user.getPassword();
			userName = user.getFirstName();
		}
		else
		{
			System.out.println("User is nulllll");
		}
	}
	catch(Exception e)
	{
		System.out.println("Could not find the user in event details page");
	}
		
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

<title>WHAM - Event Details</title>
</head>
<body>

<div id="fb-root"></div>
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
				<% if(user!=null)
				{
					%>
					<form action="/jerks/login"  method="post">
						<input type = "hidden" name="username" value=<%=username %>>
						<input type = "hidden" name="password" value=<%=password %>>
						<input type ="hidden"  name="latitude" value = "${latitude}"> 
						<input type ="hidden"  name="longitude" value ="${longitude}"> 
						<button type="submit" class="btn btn-success navbar-btn">New Search</button>
					</form>
					<%
				}
				else
				{
					%>
					<form action="/jerks/index">
						<input type ="hidden"  name="latitude" value = "${latitude}"> 
						<input type ="hidden"  name="longitude" value ="${longitude}"> 
						<button type="submit" class="btn btn-success navbar-btn">New Search</button>
					</form>
					<%
				}
					%>	
				</div>
			</div>
		</div>
	<!-- Header End -->

	<div class="main">
		<div class="sidebar">
			<div id="eventDetails" class="eventDetails"></div>

			<div id="buy"></div>
		</div>
		<div id="googleMap" class="googleMap">
		</div>
	</div>

	<div class="footer">		
		<p style="float:center">&copy; JeRKS (CS5500)</p>
		<p style="float:right">CCIS - Northeastern University</p>
	</div>
	
	<script>
	google.maps.event.addDomListener(window, 'load', function() {
		var distance = 0;
		var time = 0;
		initialize();
	});
	
	
	function initialize()
	{
		var directionsService = new google.maps.DirectionsService();
	     var directionsDisplay = new google.maps.DirectionsRenderer();
	     var map = new google.maps.Map(document.getElementById('googleMap'), {
	       zoom:7,
	       mapTypeId: google.maps.MapTypeId.ROADMAP,
	     });

	     directionsDisplay.setMap(map);
	     directionsDisplay.setPanel(document.getElementById('panel'));
	     
	     
	     var request = {
	       origin: new google.maps.LatLng(<%=latitude%>, <%=longitude%>), 
	       destination: new google.maps.LatLng(<%=eventLatitude%>, <%=eventLongitude%>), 
	       travelMode: google.maps.DirectionsTravelMode.DRIVING,
	       unitSystem: google.maps.UnitSystem.IMPERIAL
	     };

	     directionsService.route(request, function(response, status) {
	       if (status == google.maps.DirectionsStatus.OK) {
	    	   distance = response.routes[0].legs[0].distance.text;
		       time = response.routes[0].legs[0].duration.text;
		       loadEventDetails(distance, time);
	           directionsDisplay.setDirections(response); 
	       }
	     });
	     
	}
	
	function loadEventDetails(distance, time)
	{
		
		
		var display = "";
		display += "<h2>Event Details</h2>";
		display += "<p>Event Name: "+unescape("<%=eventName%>")+"</p>";
		display += "<table><tr><td>Event Address:<td>"+"<%=addressLine1%>"+"</td></td></tr>";
		display += "<tr><td><td>"+"<%=addressLine2%>"+"</td></td></tr>";
		display += "<tr><td>"+"<td>"+"<%=city%>"+"</td></td></tr>";
		display += "<tr><td>"+"<td>"+"<%=state%>"+"</td></td></tr>";
		display += "<tr><td>"+"<td>"+"<%=zipCode%>"+"</td></td></tr>";
		display += "<tr><td>"+"<td>US</td></td></tr>";
		display +="</table>";
		display += "<p>Date:"+"<%=date%>"+"</p>";
		display += "<p>Description: "+unescape("<%=description%>")+"</p>";
		display += "<p>Minumum Age Limit:"+"<%=minAgeLimit%>"+"</p>";
		display += "<p>Remaining Tickets:"+"<%=remainingTickets%>"+"</p>";
		display += "<p>Price:"+"<%=ticketPrice%>"+"</p>";
		display += "<p>Distance:"+distance+"les</p>";
		display += "<p>Estimated time of Arrival:"+time+"</p>";
		
		display += '<form action="/jerks/buy" method="post">';
		display += '<input type="hidden" name="latitude" value="<%=latitude%>">';
		display += '<input type="hidden" name="longitude" value="<%=longitude%>">';
		display += '<input type="hidden" name="date" value="<%=date%>">';
		display += '<input type="hidden" name="username" value="<%=username%>">';
		display += '<input type="hidden" name="password" value="<%=password%>">';
		display += '<input type="hidden" name="address" value="<%=completeAddress%>">';
		display += '<input type="hidden" name="eventId" value="<%=eventId%>">';
		display += '<input type="hidden" name="eventName" value="<%=eventName%>">';
		display += '<input type="hidden" name="source" value="<%=source%>">';
		display +='<div class="row"><input type="submit" class="btn btn-primary" value="Buy">';
		display += '</form>'
		display += '<a href="https://www.facebook.com/sharer/sharer.php?s=100&p[url]=http://jerks-cs5500.rhcloud.com/index/<%=latitude%>/<%=longitude%>&p[title]=<%=eventName%>"><img alt="Share on Facebook!" style="width: 40%; float: right;" src="${facebookIcon}"></a></div>';
		$('#eventDetails').html(display);
			
	}
	
	//Dynamicaly detect the window's size and resize the map
	$(window).resize(function() {
		var currentWidth = $('.main').width();
		$("#googleMap").width(currentWidth - 300); // 500px is the sidebar width
	});
	
	</script>
</body>
</html>