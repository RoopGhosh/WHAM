<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="edu.neu.cs5500.Jerks.apiCall.*, edu.neu.cs5500.Jerks.definitions.*, edu.neu.cs5500.Jerks.business.*, com.google.gson.Gson ,java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<%
	String jsonEvents = null;
	double latitude = 0.0f;
	double longitude = 0.0f;
	String searchAddress = "";
	String searchEvent = ""; 
	String price = "";
	Date date = new Date();
	String[] categories = new String[1];
	try
	{		
		latitude = Double.parseDouble(request.getParameter("latitude"));
		longitude = Double.parseDouble(request.getParameter("longitude"));
		EventManager em = new EventManager();
		List<Event> events = em.fetchEvents(latitude, longitude, searchAddress, searchEvent, price, date, categories); 
		jsonEvents = new Gson().toJson(events);
		//System.out.println(jsonEvents);
	}
	catch(Exception e) {
		response.sendRedirect("geolocator.html");
	}
	
	/* Subbed events code. uncomment if you want to test the maps. */
	
// 	String url = "http://api.eventful.com/json/events/search?app_key=4fgZC93XQz2fgKpV&where=42.3601,-71.0589&within=10&date=Future&page_size=50&sort_order=distance";
// 	EventfulAPICall eventCall = new EventfulAPICall();
// 	ArrayList<Event> listOfEvent = eventCall.getListofEventsFromJSON(url);
// 	String jsonEvents = new Gson().toJson(listOfEvent);
// 	double latitude = Double.parseDouble(request.getParameter("latitude"));
// 	double longitude = Double.parseDouble(request.getParameter("longitude"));
%>
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/font-awesome/4.0.0/css/font-awesome.css" />
<link rel="stylesheet" type="text/css" href="css/main.css">
<link rel="icon" type="image/gif" href="img/favicon.GIF" />
<script src="js/GeoLocation.js"></script>
<script src="http://maps.googleapis.com/maps/api/js?key="></script>
<script src="js/GoogleMaps.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<title>WHAM - Home</title>
<script>
	google.maps.event.addDomListener(window, 'load', function() {
		initialize(<%=jsonEvents%>);
	});

	//Dynamicaly detect the window's size and resize the map
	$(window).resize(function() {
		var currentWidth = $('.main').width();
		$("#googleMap").width(currentWidth - 300); // 300px is the sidebar width
	});
</script>
</head>
<body>
	<div class="container-fluid">
		<!-- Header Start -->
		<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
			<div class="navbar-header">
				<a class="navbar-brand" rel="home" href="#">WHAM</a>
				<ul class="nav navbar-nav">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">Howdy User!<span
							class="glyphicon glyphicon-user icon-large brown pull-left">&nbsp;</span></a>
						<ul class="dropdown-menu">
							<li><a href="#">Create Event<span
									class="glyphicon glyphicon-bullhorn icon-large brown pull-right"></span></a></li>
							<li class="divider"></li>
							<li><a href="#">Profile<span
									class="glyphicon glyphicon-cog icon-large brown pull-right"></span></a></li>
							<li class="divider"></li>
							<li><a href="#">History<span
									class="glyphicon glyphicon-time icon-large brown pull-right"></span></a></li>
							<li class="divider"></li>
							<li><a href="#">Reports<span
									class="glyphicon glyphicon-stats icon-large brown pull-right"></span></a></li>
							<li class="divider"></li>
							<li><a href="#">Logout<span
									class="glyphicon glyphicon-off icon-large brown pull-right"></span></a></li>
						</ul></li>
				</ul>
			</div>

			<div class="collapse navbar-collapse">

				<div class="col-sm-6 col-md-6">
					<form class="navbar-form" role="search">
						<div class="input-group">
							<input type="text" class="form-control" placeholder="Search"
								name="srch-term" id="srch-term">
							<div class="input-group-btn">
								<button class="btn btn-default" type="submit">
									<i class="glyphicon glyphicon-search"></i>
								</button>
							</div>
						</div>
					</form>
				</div>
		
					<button type="button" class="btn btn-success navbar-btn">Sign in</button>
					<button type="button" class="btn btn-success navbar-btn" onclick="location.href='register.jsp'">Register</button>

			</div>
		</div>
	</div>
	<!-- Header End -->

	<div class="main">
		<div class="sidebar">
			<div id="eventDetails" class="eventDetails">
				<H3 id="name">Select an event to see its details...</H3>
				<p id="description"></p>
				<p id="more"></p>
			</div>
		</div>
		<div id="googleMap" class="googleMap"></div>
	</div>

	<div class="footer">		
		<p style="float:center">&copy; JeRKS (CS5500)</p>
		<p style="float:right">CCIS - Northeastern University</p>
	</div>

</body>
</html>