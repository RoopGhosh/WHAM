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
<spring:url value="/resources/img/brandImage.JPG" var="brandIcon" />
<spring:url value="/resources/img/favicon.GIF" var="favIcon" />
<spring:url value="/resources/js/Login.js" var="Login" />
<%	
	String jsonEvents = "";
	double latitude = 0.0f;
	double longitude = 0.0f;
	String searchAddress = "";
	String searchEvent = ""; 
	String price = "";
	String username = "";
	String firstName = "";
	String lastName = "";
	String email = "";
	String password ="";
	String addrLine1 ="";
	String addrLine2 = "";
	String city = "";
	String state ="";
	String zipCode="";
	String[] category ;
	String dob ="";
	Date parsed = new Date();
	String gender = "";
	float lat = 0.0f;
	float longi = 0.0f;
	Address addr;
	User registeredUser;
	List<String> dislikes = new ArrayList<String>();
	List<String> categories1 = new ArrayList<String>();
	Date date = new Date();// gets modified time
	String loginMessage = "HowdyUser!";

	UserProvider userDao = new UserProvider();
	User user = null;
	String emailFordetails = "Howdy User";
	//this try is run only when registering a new user.....
	try
	{
		firstName = request.getParameter("firstName");
		System.out.println("Index firstName: "+firstName);
		lastName = request.getParameter("lastName");
		System.out.println("Index lastName: "+lastName);
		email = request.getParameter("email");
		System.out.println("Index email: "+email);
		password = request.getParameter("password");
		System.out.println("Index password:  "+password);
		addrLine1 = request.getParameter("addrLine1");
		System.out.println("Index addrLine1: "+addrLine1);
		addrLine2 = request.getParameter("addrLine2");
		System.out.println("Index addrLine2: "+addrLine2);
		city = request.getParameter("city");
		System.out.println("Index city: "+city);
		state = request.getParameter("state");
		System.out.println("Index state: "+state);
		zipCode = request.getParameter("zipCode");
		System.out.println("Index zipCode: "+zipCode);
		category = request.getParameterValues("category");

		categories1 = Arrays.asList(category); 
		System.out.println("Index categories1: "+categories1.toArray().toString());
		dob = request.getParameter("datepicker");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		parsed = sdf.parse(dob);
	       java.sql.Date sql = new java.sql.Date(parsed.getTime());
	       
		System.out.println("Index date: "+sql.toString());
		gender = request.getParameter("optradio"); // where OPTRADIO is GENDER
		System.out.println("Index regsiter: "+request.getParameter("optradio"));
		lat = Float.parseFloat(String.valueOf(session.getAttribute("latitude")));
		longi = Float.parseFloat(String.valueOf(session.getAttribute("longitude")));
		System.out.println("neyo: " + lat + " " + longi);
		addr = new Address(addrLine1, addrLine2,city,state,"US", zipCode,lat,longi);
		String phoneNumber = request.getParameter("phoneNumber");
		System.out.println("index phoneNumber : " + phoneNumber);
		user = new User(email,firstName,lastName,password,addr,phoneNumber,
				sql,gender,categories1,dislikes);
		registeredUser = userDao.createUser(user);
		if(registeredUser != null)
		{
			System.out.println("User created successfully");
			emailFordetails = user.getEmail();
			username = user.getEmail();
		}
		else
		{
			System.out.println("User created unsuccessfully");
		}
	} // try for registering an user
	catch(Exception e)
	{
		System.out.println("Could not register user");
	}
	
	// the real index try block....
	try
	{	
		System.out.println("From index try block");
		String strLati= "";
		String strLongi ="";
			//getting latLong for the first time from geolocator
			if(session.getAttribute("latitude")==null)
			{
				strLati = String.valueOf(request.getAttribute("latitude"));
				session.setAttribute("latitude",strLati);
			}
			else
				strLati = (String) session.getAttribute("latitude");
	System.out.print("1"+strLati);

		if(session.getAttribute("longitude")==null)
		{
			strLongi = String.valueOf(request.getAttribute("longitude"));
			session.setAttribute("longitude",strLongi);
		}
		else
			strLongi = (String) session.getAttribute("longitude");
		
		System.out.print("2"+strLongi);
		latitude = Double.parseDouble(strLati);
		System.out.print("5");
		longitude = Double.parseDouble(strLongi);
		System.out.print("6");
		try
		{
			if(session.getAttribute("username")!=null)
				{
					username  = String.valueOf(session.getAttribute("username"));
					password =  String.valueOf(session.getAttribute("password"));
					user = userDao.findByEmail(username);
					loginMessage = user.getFirstName();
					dislikes = user.getDislikes();
					categories1.addAll(user.getAreaOfInterest());
				}
			else
			{
			username = (String)request.getAttribute("username");
			password = (String)request.getAttribute("password");
			System.out.println("username from url: "+username);
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
					
					//Add the userdislikes for events
					List<String> dislikesList = user.getDislikes();
					dislikes = dislikesList;
							
					//Add the user area of interest for events
					categories1 =  user.getAreaOfInterest();
				}
				else
				{
					System.out.println("Invalid password: "+user.getPassword());
					loginMessage = "Wrong Password";
				} // inner if
			} // outer if
			else
			{
				System.out.println("Invalid email id, no record found");
				loginMessage = "Please Regsiter";
			}
		}
		}// login try
		catch(Exception e)
		{
			System.out.println("Could not find username or password");
		}
	
		if (request.getParameter("search") != null)
		{
			String searchtype = (String) request.getParameter("type");
			if(searchtype == null)
				searchtype = "event";
			if (searchtype.equalsIgnoreCase("event"))
				{
					searchEvent = (String) request.getParameter("search");
					searchEvent = searchEvent.replaceAll(" ", "%20");
				}
			else
			{
				searchAddress = (String) request.getParameter("search");
				searchAddress = searchAddress.replaceAll(" ", "%20");
				GoogleAddressToLatLong google = new GoogleAddressToLatLong();
				float[] latlong = google.getLatLong("", "", searchAddress, "", "");
				latitude = latlong[0];
				longitude = latlong[1];
			}
		}

		// Capture search price range
		if (request.getParameter("price") != null) 
		{
			int priceRange = Integer.parseInt(request.getParameter("price"));
			System.out.println(priceRange);
			if (priceRange > 0)
			{
				price = String.valueOf(priceRange);
			}
		}
		// Capture the search dates
		if (request.getParameter("daysWithin") != null)
		{
			int daysWithin = Integer.parseInt(request.getParameter("daysWithin"));
			Calendar c = Calendar.getInstance(); // starts with today's date and time
			c.add(Calendar.DAY_OF_YEAR, daysWithin);
			date = c.getTime();
		}
		else{
			Calendar c = Calendar.getInstance(); // starts with today's date and time
			c.add(Calendar.DAY_OF_YEAR, 5);
			date = c.getTime();
		}
		// Capture the search catagories
		if (request.getParameter("catagories") != null)
		{
			String searchCategory = (String) request.getParameter("catagories");
			if (!searchCategory.equals("all"))
			{
				/* categories = new String[1];
				categories[0] = searchCategory; */
				categories1.add(searchCategory);
			}
		}
		EventManager em = new EventManager();
		System.out.println("From event manager"+categories1.toString());
		List<Event> events =   /* new  ArrayList<Event>();  */     em.fetchEvents(latitude, longitude, searchAddress, searchEvent, price, date,
				categories1.toArray(new String[categories1.size()]), dislikes.toArray(new String[dislikes.size()])); 
		jsonEvents = new Gson().toJson(events);
	}
	catch (Exception e)
	{
		System.out.println(e);
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
<script
	src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>
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

function showEventDetails(jsonEvent, latitude, longitude) {
	console.log(jsonEvent);
	var nameTag = document.getElementById("name");
	var descriptionTag = document.getElementById("description");
	var moreTag = document.getElementById("more");	
	nameTag.innerHTML = jsonEvent.name;
	console.log("desclength:" +jsonEvent.description.length);
	if(jsonEvent.description.length > 400)
		{
		console.log("Im here");
		var shortDesc = jsonEvent.description.substring(0,400);
		console.log(shortDesc);
		descriptionTag.innerHTML = shortDesc+'....';
		}
	else{descriptionTag.innerHTML = jsonEvent.description;}
	console.log(jsonEvent.source);
	var parentUrl = encodeURIComponent(window.location.href),
    eventDetailUrl = window.location.origin+ '/eventDetails.jsp?id='+jsonEvent.id;	
	
	var date = new Date(jsonEvent.date);
	var day = date.getDate();
	var monthIndex = date.getMonth();
	var year = date.getFullYear();
	var newDate = year+'-'+monthIndex+'-'+day;
	
	var form = '<form action="/jerks/eventDetails" method="POST">';
	form += '<input type="hidden" name="latitude" value='+latitude +'>';
	form +='<input type="hidden" name="longitude" value='+longitude +'>';
	form +='<input type="hidden" name="addressLine1" value='+jsonEvent.address.addressLine1 +'>';
	form +='<input type="hidden" name="addressLine2" value='+jsonEvent.address.addressLine2 +'>';
	form +='<input type="hidden" name="city" value='+jsonEvent.address.city +'>';
	form +='<input type="hidden" name="eventLatitude" value='+jsonEvent.address.latitude +'>';
	form +='<input type="hidden" name="eventLongitude" value='+jsonEvent.address.longitude +'>';
	form +='<input type="hidden" name="state" value='+jsonEvent.address.state +'>';
	form +='<input type="hidden" name="zipCode" value='+jsonEvent.address.zipCode +'>';
	form +='<input type="hidden" name="date" value='+newDate+'>';
	form +='<input type="hidden" name="description" value='+escape(jsonEvent.description)+'>';
	form +='<input type="hidden" name="eventId" value='+jsonEvent.eventId+'>';
	form +='<input type="hidden" name="minAgeLimit" value='+jsonEvent.minAgeLimit+'>';
	form +='<input type="hidden" name="name" value='+escape(jsonEvent.name)+'>';
	form +='<input type="hidden" name="rating" value='+jsonEvent.rating+'>';
	form +='<input type="hidden" name="remainingTickets" value='+jsonEvent.remainingTickets+'>';
	form +='<input type="hidden" name="ticketPrice" value='+jsonEvent.ticketPrice+'>';
	form +='<input type="hidden" name="source" value='+escape(jsonEvent.source)+'>';
	form +='<input type="hidden" name="username" value="<%=emailFordetails%>" >';
	form +='<input type="submit" class="btn btn-primary" value="More Details" style="float:left;">';
	form += '</form>';
	form += '<a href="/jerks/dislike/<%=username%>/'+ jsonEvent.name + '/' + latitude + '/' + longitude + '" class="btn btn-primary" style="float:right;"><span class="glyphicon glyphicon-thumbs-down icon-large white pull-left"></span>&nbsp;&nbsp;Dislike</a>';
	moreTag.innerHTML = form;
}
</script>
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
						data-toggle="dropdown"><label><%=loginMessage%> </label><span
							class="glyphicon glyphicon-user icon-large brown pull-left"
							style="color: #428bca">&nbsp;</span></a> <%
 	if (session.getAttribute("username") != null) {
 %>
						<ul class="dropdown-menu">
							<li><a href="/jerks/createEvents">Create Event<span
									class="glyphicon glyphicon-bullhorn icon-large brown pull-right"></span></a></li>
							<li class="divider"></li>
							<li><a href="/jerks/profile">Profile<span
									class="glyphicon glyphicon-cog icon-large brown pull-right"></span></a></li>
							<li class="divider"></li>
							<li><a href="/jerks/history/">My Events<span
									class="glyphicon glyphicon-time icon-large brown pull-right"></span></a></li>
							<li class="divider"></li>
							<li><a href="/jerks/logout">Logout<span
									class="glyphicon glyphicon-off icon-large brown pull-right"></span></a></li>
						</ul> <%
 	}
 %></li>
				</ul>


			</div>

			<div class="collapse navbar-collapse">
				<div
					class="<%if (session.getAttribute("username") != null){%>col-sm-8 col-md-8<%} else {%>col-sm-5 col-md-5 
					<%}%>">
					<form class="navbar-form" action="/jerks/search" role="search" id="searchForm">
						<div class="row">
							<div class="input-group my-group">
								<%
									if (session.getAttribute("username") != null) {
								%>
								<select form="searchForm" id="searchTypeSelector" name="type"
									class="selectpicker form-control"
									style="width: 15%; background: #428bca; border-color: #357ebd; color: white; font-weight: bold">
									<option value="event">Events</option>
									<option value="address">Address</option>
								</select>
								
								<%
									}
									else{
								%>
								<input type="hidden" name="type" value="address">
									<input type="hidden" name="daysWithin" value="5">
									<input type="hidden" name="catagories" value="all">
									<input type="hidden" name="price" value="-1">
									<%
									}%>
								<input type="text" class="form-control" placeholder="Search..."
									name="search" id="srch-term"
									style="width: <%if (session.getAttribute("username") != null){%>30%<%}else{%>100%<%}%>">
									


								<%
									if (session.getAttribute("username") != null) {
								%>
								<select form="searchForm" id="daysSelector" name="daysWithin"
									class="form-control" style="width: 20%">
									<option value="5">Next 5 days</option>
									<option value="1">Next 1 day</option>
									<option value="2">Next 2 days</option>
									<option value="3">Next 3 days</option>
									<option value="4">Next 4 days</option>
								</select> <select form="searchForm" id="catagorySelector"
									name="catagories" class="form-control" style="width: 20%">
									<option value="all">All Categories</option>
									<option value="music">Music</option>
									<option value="food">Food</option>
									<option value="support">Support</option>
									<option value="movies_film">Movies</option>
									<option value="performing_arts">Performing Arts</option>
									<option value="family_fun_kids">Family,Fun,Kids</option>
									<option value="learning_education">Learning and
										Education</option>
									<option value="religion_spirituality">Religion and
										Spiritual</option>
									<option value="sports">Sports</option>
									<option value="holiday">Holiday</option>
									<option value="business">Business</option>
									<option value="science">Science</option>
									<option value="technology">Technology</option>
									<option value="fundraisers">Fund Raisers</option>
									<option value="politics_activism">Politics and
										Activism</option>
									<option value="outdoors_recreation">Outdoors and
										Recreation</option>
									<option value="community">Community</option>
									<option value="books">Books</option>
									<option value="other">Other</option>
								</select> <select form="searchForm" id="priceSelector" name="price"
									class="form-control" style="width: 15%">
									<option value="-1">All Prices</option>
									<option value="0">Free</option>
									<option value="100">&lt; $100</option>
									<option value="200">&lt; $200</option>
									<option value="300">&lt; $300</option>
								</select>
								<%
									}
								%>
								<span class="input-group-btn">
									<button class="btn btn-primary">
										<i class="glyphicon glyphicon-search"></i>
									</button>
								</span>
							</div>
						</div>
					</form>
				</div>
				<%
					if (session.getAttribute("username") == null) {
				%>
				<div class="col-sm-4 col-md-4">
					<form class="navbar-form" action="/jerks/login" method="POST">
						<div class="has-feedback input-group">
							<input type="text" class="form-control" name="username"
								placeholder="username..." style="width: 50%"> <input
								type="password" class="form-control" name="password"
								placeholder="password..." style="width: 50%"> <input
								type="hidden" name="latitude" value="${latitude}"> <input
								type="hidden" name="longitude" value="${longitude}"> <span
								class="input-group-btn">
								<button type="submit" class="btn btn-primary">Sign&nbsp;in</button>
							</span>
						</div>
					</form>
				</div>
				<div class="col-sm-1 col-md-1">
					<form class="navbar-form" action="/jerks/register">
						<button type="submit" class="btn btn-primary" value="">Register</button>
						<input type="hidden" name="latitude" value="${latitude}">
						<input type="hidden" name="longitude" value="${longitude}">
					</form>
				</div>
				<%
					}
				%>
			</div>
		</div>
	</div>

	<!-- Header End -->

	<div class="main">
		<div class="sidebar">
			<div id="eventDetails" class="eventDetails">
				<%
					if(session.getAttribute("username") == null) {
				%>
				<script type="text/javascript">
				//Ad connect widget
				cpmgo_adhere_opt = 'left:50%';
				cpmgo_layer_banner = false;
				cpmgo_mobile_redirect = true;
				cpmgo_mobile_durl = '';
				cpmgo_max_dim = "0x0";
				//default banner house ad url 
				cpmgo_default_url = '';
				cpmgo_banner_border = '#ffffff';
				cpmgo_banner_ad_bg = '#ffffff';
				cpmgo_banner_link_color = '#0000FF';
				cpmgo_banner_text_color = '#008000';
				cpmgo_banner_text_banner = true;
				cpmgo_banner_image_banner = true;
				cpmgo_post_click_url = '';
			</script>
				<script type="text/javascript"
					src="http://adserving.cpmgo.com/show.php?nid=1068&amp;pid=11618&amp;adtype=17&amp;sid=14727"></script>
				<%
					} else {
				%>
				<H3 id="name">Select an event to see its details...</H3>
				<p id="description"></p>
				<p id="more"></p>
				<%
					}
				%>

			</div>
		</div>
		<div id="googleMap" class="googleMap"></div>
	</div>

	<div class="footer">
		<p style="float: center">&copy; JeRKS (CS5500)</p>
		<p style="float: right">CCIS - Northeastern University</p>
	</div>
</body>
</html>