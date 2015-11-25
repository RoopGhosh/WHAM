<%@page import="org.springframework.ui.ModelMap"%>
<%@page import="org.springframework.ui.Model"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="edu.neu.cs5500.Jerks.apiCall.*, edu.neu.cs5500.Jerks.definitions.*, edu.neu.cs5500.Jerks.dbProviders.*, edu.neu.cs5500.Jerks.business.*, com.google.gson.Gson ,java.util.*"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
	<spring:url value="/resources/js/GeoLocation.js" var="GeoLocator" />
	<spring:url value="/resources/js/GoogleMaps.js" var="GoogleMaps" />
	<spring:url value="/resources/css/main.css" var="MainCSS" />
	<spring:url value="/resources/js/bootstrap.min.js" var="BootStrap" />
	<spring:url value="/resources/img/favicon.GIF" var="favIcon" />
	<spring:url value="/resources/js/Login.js" var="Login" />
<%	
	String jsonEvents = "";
	double latitude = 0.0f;
	double longitude = 0.0f;
	String searchAddress = "";
	String searchEvent = ""; 
	String price = "";
	Calendar c = Calendar.getInstance(); // starts with today's date and time
	c.add(Calendar.DAY_OF_YEAR, 2);  // advances day by 2
	Date date = c.getTime(); // gets modified time
	String loginMessage = "HowdyUser!";
	// Remove hardcoded categories & dislikes
	String[] categories = {"food", "science"};
	String[] dislikes = {"music", "boston", "cheese"};
	try
	{	
		System.out.println("From index try block");
		String s = String.valueOf(request.getAttribute("longitude"));
		String s1 = String.valueOf(request.getAttribute("latitude"));
		latitude = Double.parseDouble(s1);
		longitude = Double.parseDouble(s);
		try
		{
			
			String username = (String)request.getAttribute("username");
			String password = (String)request.getAttribute("password");
			System.out.println("Password from url: "+password);
			UserProvider userDao = new UserProvider();
			User user = userDao.findByEmail(username);
			if(user != null)
			{
				String passCheck = user.getPassword();
				System.out.println("Passcheck: "+passCheck);
				System.out.println("Password: "+password);
				if (passCheck.equals(password))
				{
					session.setAttribute("username", user.getEmail());
					session.setAttribute("password", user.getPassword());
					System.out.println("Valid Password");
					loginMessage = user.getFirstName();
				}
				else
				{
					System.out.println("Invalid password: "+user.getPassword());
					loginMessage = "Invalid Password";
				}
			}
			else
			{
				System.out.println("Invalid email id, no record found");
				loginMessage = "Please Regsiter";
			}
		}
		catch(Exception e)
		{
			System.out.println("Could not find username or password");
		}
			
		EventManager em = new EventManager();
		List<Event> events = em.fetchEvents(latitude, longitude, searchAddress, searchEvent, price, date, categories, dislikes);
		jsonEvents = new Gson().toJson(events);
		//System.out.println("Json events"+jsonEvents);
	}
	catch(Exception e) {
		System.out.println("From index catch block");
		response.sendRedirect("geolocator");
	}
%>
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/font-awesome/4.0.0/css/font-awesome.css" />
<link rel="stylesheet" type="text/css" href="${MainCSS}">
<link rel="icon" type="image/gif" href="${favIcon}" />
<script src="${GeoLocator}"></script>
<script src="http://maps.googleapis.com/maps/api/js?key="></script>
<script src="${GoogleMaps}"></script>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
<script src="${BootStrap}"></script>
<script src="${Login}"></script>
<title>WHAM - Home</title>
<script>
	google.maps.event.addDomListener(window, 'load', function() {
		initialize(<%=jsonEvents%>, <%=latitude%>, <%=longitude%>);
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
						data-toggle="dropdown"><label><%=loginMessage %>
							</label><span
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
				<div class="form-group">
					<form action="/login">
						<input type="text" class="col-xs-1" name="username" placeholder="Username" >
						<input type="text" class="col-xs-1" name="password" placeholder="Password" >
						<input type ="hidden"  name="latitude" value = "${latitude}"> 
						<input type ="hidden"  name="longitude" value ="${longitude}"> 
						<button type="submit" class="btn btn-success navbar-btn">Sign in</button>
					</form>
					<button type="button" class="btn btn-success navbar-btn" onclick="location.href='/register'" value="">Register</button>
				</div>
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