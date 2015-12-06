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
	
<%	
	String username = String.valueOf(session.getAttribute("username"));
	System.out.println("Username from create events: "+username);
	String password = String.valueOf(session.getAttribute("password"));
	System.out.println("password from create events: "+password);
	String latitude = String.valueOf(session.getAttribute("latitude"));
	String longitude = String.valueOf(session.getAttribute("longitude"));
%>
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/font-awesome/4.0.0/css/font-awesome.css" />
<link rel="stylesheet" type="text/css" href="${MainCSS}">
<link rel="icon" type="image/gif" href="${favIcon}" />
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
<script type="text/javascript"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<title>WHAM - Home</title>

</head>
<body>
	<div class="container-fluid">
		<!-- Header Start -->
		<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
			<div class="navbar-header">
				<a class="navbar-brand" rel="home" href="#">WHAM</a>
				<ul class="nav navbar-nav">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"><label><%=username %>
							</label><span
							class="glyphicon glyphicon-user icon-large brown pull-left">&nbsp;</span></a>
						<ul class="dropdown-menu">
							<li><a href="/jerks/createEvents/">Create Event<span
									class="glyphicon glyphicon-bullhorn icon-large brown pull-right"></span></a></li>
							<li class="divider"></li>
							<li><a href="/jerks/profile/<%=username %>">Profile<span
									class="glyphicon glyphicon-cog icon-large brown pull-right"></span></a></li>
							<li class="divider"></li>
							<li><a href="#">History<span
									class="glyphicon glyphicon-time icon-large brown pull-right"></span></a></li>
							<li class="divider"></li>
							<li><a href="#">Reports<span
									class="glyphicon glyphicon-stats icon-large brown pull-right"></span></a></li>
							<li class="divider"></li>
							<li><a href="/jerks/">Logout<span
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
	<div>
		<div id="eventForm" class="eventDetails">	
			<form action="/jerks/createEvents" METHOD="post">
				<div class="form-group">
					<label>Event Name*</label>
						<input type="text" name="eventName" class="form-control" required>
				</div>
				<div class="form-group">
					<label>Event Date*</label>
						<input type="text" name="datepicker" id="datepicker" class="form-control">
				</div>
				<div class="form-group">
					<label>Event Address Line 1*</label>
					<input type="text" name="addressLine1" id="datepicker" class="form-control">
				</div>
			
				<div class="form-group" >
           		 <label>Address Line 2</label>
            	 <input type="text" name="addressLine2" class="form-control" >
       		   </div>
		
			<div class="form-group" >
            <label>City</label>
            <input type="text" name="city" class="form-control">
        	</div>
        	
        		<div class="form-group" ">
            <label>State</label>
   <select class="form-control" name="state">
	<option value="AL">Alabama</option>
	<option value="AK">Alaska</option>
	<option value="AZ">Arizona</option>
	<option value="AR">Arkansas</option>
	<option value="CA">California</option>
	<option value="CO">Colorado</option>
	<option value="CT">Connecticut</option>
	<option value="DE">Delaware</option>
	<option value="DC">District Of Columbia</option>
	<option value="FL">Florida</option>
	<option value="GA">Georgia</option>
	<option value="HI">Hawaii</option>
	<option value="ID">Idaho</option>
	<option value="IL">Illinois</option>
	<option value="IN">Indiana</option>
	<option value="IA">Iowa</option>
	<option value="KS">Kansas</option>
	<option value="KY">Kentucky</option>
	<option value="LA">Louisiana</option>
	<option value="ME">Maine</option>
	<option value="MD">Maryland</option>
	<option value="MA">Massachusetts</option>
	<option value="MI">Michigan</option>
	<option value="MN">Minnesota</option>
	<option value="MS">Mississippi</option>
	<option value="MO">Missouri</option>
	<option value="MT">Montana</option>
	<option value="NE">Nebraska</option>
	<option value="NV">Nevada</option>
	<option value="NH">New Hampshire</option>
	<option value="NJ">New Jersey</option>
	<option value="NM">New Mexico</option>
	<option value="NY">New York</option>
	<option value="NC">North Carolina</option>
	<option value="ND">North Dakota</option>
	<option value="OH">Ohio</option>
	<option value="OK">Oklahoma</option>
	<option value="OR">Oregon</option>
	<option value="PA">Pennsylvania</option>
	<option value="RI">Rhode Island</option>
	<option value="SC">South Carolina</option>
	<option value="SD">South Dakota</option>
	<option value="TN">Tennessee</option>
	<option value="TX">Texas</option>
	<option value="UT">Utah</option>
	<option value="VT">Vermont</option>
	<option value="VA">Virginia</option>
	<option value="WA">Washington</option>
	<option value="WV">West Virginia</option>
	<option value="WI">Wisconsin</option>
	<option value="WY">Wyoming</option>
</select>			
        </div>
        
        <div class="form-group" >
            <label>Zip Code</label>
            <input type="text" name="zipCode" class="form-control"  required >
        </div> 
		
		<div class="form-group" >
            <label>Description</label>
            <input type="text" name="description" class="form-control"  required >
        </div> 
        
        <div class="form-group" >
            <label>Ticket Price</label>
            <input type="text" name="ticketPrice" class="form-control"  required >
        </div> 
		
		
		<div class="form-group" >
            <label>Minimum Age Limit</label>
            <input type="text" name="minAgeLimit" class="form-control"  required >
        </div> 
        
        <div class="form-group" >
            <label>Event Capacity</label>
            <input type="text" name="remainingTickets" class="form-control"  required >
        </div> 
        
			<div class="form-group">
				<button type="submit" class="btn btn-success navbar-btn" >Create Event & New Search</button>
			</div>
			
			<input type ="hidden"  name="latitude" value = "<%=latitude%>"> 
			<input type ="hidden"  name="longitude" value ="<%=longitude %>">  
			<input type ="hidden"  name="username" value = "<%=username%>"> 
			<input type ="hidden"  name="password" value ="<%=password %>">  
			
			</form>
		</div>
	</div>
	<div class="footer">		
		<p style="float:center">&copy; JeRKS (CS5500)</p>
		<p style="float:right">CCIS - Northeastern University</p>
	</div>

</body>
</html>