<%@page import="org.springframework.ui.ModelMap"%>
<%@page import="org.springframework.ui.Model"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="edu.neu.cs5500.Jerks.apiCall.*, edu.neu.cs5500.Jerks.definitions.*,java.io.*,java.util.*,java.text.*, edu.neu.cs5500.Jerks.dbProviders.*, edu.neu.cs5500.Jerks.business.*, com.google.gson.Gson ,java.util.*"%>
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
	UserProvider userDao = new UserProvider();
	User user = null;
	String emailFordetails = "Howdy User!";
	try
	{
		String firstName = request.getParameter("firstName");
		System.out.println("Index regsiter: "+request.getParameter("firstName"));
		String lastName = request.getParameter("lastName");
		System.out.println("Index regsiter: "+request.getParameter("lastName"));
		String email = request.getParameter("email");
		System.out.println("Index regsiter: "+request.getParameter("email"));
		String password = request.getParameter("password");
		System.out.println("Index regsiter: "+request.getParameter("password"));
		String addrLine1 = request.getParameter("addrLine1");
		System.out.println("Index regsiter: "+request.getParameter("addrLine1"));
		String addrLine2 = request.getParameter("addrLine2");
		System.out.println("Index regsiter: "+request.getParameter("addrLine2"));
		String city = request.getParameter("city");
		System.out.println("Index regsiter: "+request.getParameter("city"));
		String state = request.getParameter("state");
		System.out.println("Index regsiter: "+request.getParameter("state"));
		String zipCode = request.getParameter("zipCode");
		System.out.println("Index regsiter: "+request.getParameter("zipCode"));
		String[] category = request.getParameterValues("category");
		List<String> categories1 = Arrays.asList(category); 
		System.out.println("Index regsiter: "+categories1);
		System.out.println("Index regsiter: "+category);
		//System.out.println("Index regsiter: "+request.getParameter("date"));
		System.out.println("Index regsiter: "+request.getParameter("gender"));
		String gender = request.getParameter("gender");
		System.out.println("Index regsiter: "+request.getParameter("phoneNumber"));
		String phoneNumber = request.getParameter("phoneNumber");
		latitude = Float.parseFloat(request.getParameter("latitude"));
		longitude = Float.parseFloat(request.getParameter("longitude"));
		System.out.println("Index regsiter: "+request.getParameter("latitude"));
		System.out.println("Index regsiter: "+request.getParameter("longitude"));
		float lat = Float.parseFloat(request.getParameter("latitude"));
		float longi = Float.parseFloat(request.getParameter("longitude"));
		Address addr = new Address(addrLine1, addrLine2,city,state,"US", zipCode,lat,longi);
		Date date1 = new Date();
		List<String> dislikes1 = Arrays.asList(dislikes);
		
		user = new User(email,firstName,lastName,password,addr,phoneNumber,
				date1,gender,categories1,dislikes1);
		
		User registeredUser = userDao.createUser(user);
		
		if(registeredUser != null)
		{
			System.out.println("User created successfully");
			emailFordetails = user.getEmail();
		}
		else
		{
			System.out.println("User created unsuccessfully");
		}
		
	}
	catch(Exception e)
	{
		System.out.println("Could not register user");
	}
	
	try
	{	
		System.out.println("From index try block");
		String strLati = String.valueOf(request.getAttribute("latitude"));
		String strLongi = String.valueOf(request.getAttribute("longitude"));
		session.setAttribute("latitude",strLati);
		session.setAttribute("longitude",strLongi);
		latitude = Double.parseDouble(strLati);
		longitude = Double.parseDouble(strLongi);
		try
		{
			
			String username = (String)request.getAttribute("username");
			String password = (String)request.getAttribute("password");
			
			
			System.out.println("Password from url: "+password);
			user = userDao.findByEmail(username);
			if(user != null)
			{
				String passCheck = user.getPassword();
				System.out.println("Passcheck: "+passCheck);
				System.out.println("Password: "+password);
				if (passCheck.equals(password))
				{
					session.setAttribute("username", user.getEmail());
					session.setAttribute("password", user.getPassword());
					emailFordetails = user.getEmail();
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
	
	<%
	%>
	
	function showEventDetails(jsonEvent, latitude, longitude) {
		console.log(jsonEvent);
		
		
		var nameTag = document.getElementById("name");
		var descriptionTag = document.getElementById("description");
		var moreTag = document.getElementById("more");	
		nameTag.innerHTML = jsonEvent.name;
		descriptionTag.innerHTML = jsonEvent.description;
		localStorage.setItem(jsonEvent.id, jsonEvent);
		var parentUrl = encodeURIComponent(window.location.href),
	    eventDetailUrl = window.location.origin+ '/eventDetails.jsp?id='+jsonEvent.id;	
		var form = '<form action="/eventDetails" method="POST">';
		form += '<input type="hidden" name="latitude" value='+latitude +'>';
		form +='<input type="hidden" name="longitude" value='+longitude +'>';
		form +='<input type="hidden" name="addressLine1" value='+jsonEvent.address.addressLine1 +'>';
		form +='<input type="hidden" name="addressLine2" value='+jsonEvent.address.addressLine2 +'>';
		form +='<input type="hidden" name="city" value='+jsonEvent.address.city +'>';
		form +='<input type="hidden" name="eventLatitude" value='+jsonEvent.address.latitude +'>';
		form +='<input type="hidden" name="eventLongitude" value='+jsonEvent.address.longitude +'>';
		form +='<input type="hidden" name="state" value='+jsonEvent.address.state +'>';
		form +='<input type="hidden" name="zipCode" value='+jsonEvent.address.zipCode +'>';
		form +='<input type="hidden" name="date" value='+jsonEvent.date+'>';
		form +='<input type="hidden" name="description" value='+jsonEvent.description+'>';
		form +='<input type="hidden" name="eventId" value='+jsonEvent.eventId+'>';
		form +='<input type="hidden" name="minAgeLimit" value='+jsonEvent.minAgeLimit+'>';
		form +='<input type="hidden" name="name" value='+jsonEvent.name+'>';
		form +='<input type="hidden" name="rating" value='+jsonEvent.rating+'>';
		form +='<input type="hidden" name="remainingTickets" value='+jsonEvent.remainingTickets+'>';
		form +='<input type="hidden" name="ticketPrice" value='+jsonEvent.ticketPrice+'>';
		form +='<input type="hidden" name="username" value="<%=emailFordetails%>">';
		form+='<input type="submit" value="More Details">';
		form += '</form>';
		moreTag.innerHTML = form; 
	}
	
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
					<form action="/register">
					<button type="submit" class="btn btn-success navbar-btn"  value="">Register</button>
					<input type ="hidden"  name="latitude" value = "${latitude}"> 
					<input type ="hidden"  name="longitude" value ="${longitude}"> 
					</form>
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