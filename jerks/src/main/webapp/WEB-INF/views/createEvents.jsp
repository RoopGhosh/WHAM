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
	<spring:url value="/resources/img/brandImage.JPG" var="brandIcon" />
	
<%	
// 	String username = String.valueOf(session.getAttribute("username"));
// 	System.out.println("Username from create events: "+username);
// 	String password = String.valueOf(session.getAttribute("password"));
// 	System.out.println("password from create events: "+password);
	//String latitude = String.valueOf(session.getAttribute("latitude"));
	//String longitude = String.valueOf(session.getAttribute("longitude"));
	//UserProvider dao = new UserProvider();
	//User user = dao.findByEmail(username);
	//String userName = user.getFirstName();
	String username = "dd";
	String userName = "dd";
	String password = "xxx";
	String longitude = "34.32";
	String latitude = "33.2";
	// Do not allow guest users to create events
	//if(session.getAttribute("username") == null)
		//response.sendRedirect("geolocator");
%>
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/font-awesome/4.0.0/css/font-awesome.css" />
<link rel="stylesheet" type="text/css" href="${MainCSS}">
<link rel="icon" type="image/gif" href="${favIcon}" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.2/jquery-ui.min.js"></script>
	<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
	<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.26/angular.min.js"></script>
   
<title>WHAM - Create New Event</title>

<script>
	// create angular app
	var validationApp = angular.module('validationApp', []);

	// create angular controller
	validationApp.controller('mainController', function($scope) {

		// function to submit the form after all validation has occurred			
		$scope.submitForm = function(isValid) {

			// check to make sure the form is completely valid
			if (isValid) { 
				
			}

		};

	});
	</script>
<script>
		$(function() {
			$( "#datepicker" ).datepicker({ dateFormat: 'yy-mm-dd' }).val();
		});
	</script>
	
	
	
</head>
<body  ng-app="validationApp" ng-controller="mainController">
	<div class="container-fluid">
		<!-- Header Start -->
		<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
			<div class="navbar-header">
				<a class="navbar-brand" rel="home" href="/jerks"><img
					src="${brandIcon}" alt="WHAM"
					style="width: 42px; height: 42px; border: 0;"></a>
				<ul class="nav navbar-nav">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"><label><%=username%></label><span
							class="glyphicon glyphicon-user icon-large brown pull-left"
							style="color: #428bca">&nbsp;</span></a>
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
		<div class = "wrapper" style="width:30%; margin:50px auto;">	
			<form name = "eventForm" ng-submit="submitForm(eventForm.$valid)" action="/jerks/createEvents" METHOD="post" novalidate>
				<div class="form-group" ng-class="{ 'has-error' : eventForm.eventName.$invalid && !eventForm.eventName.$pristine }">
					<label>Event Name*</label>
						<input type="text" name="eventName" class="form-control" ng-model="event.eventName" required>
				<p ng-show="eventForm.eventName.$invalid && !eventForm.eventName.$pristine" class="help-block">Event name is required.</p>
				</div>
				<div class="form-group" ng-class="{ 'has-error' : eventForm.datepicker.$invalid && !eventForm.datepicker.$pristine }">
					<label>Event Date*</label>
						<input type="text" name="datepicker" id="datepicker" class="form-control" ng-model="event.datepicker" required>
				<p ng-show="eventForm.datepicker.$invalid && !eventForm.datepicker.$pristine" class="help-block">Event Date is required.</p>
				</div>
				<div class="form-group" ng-class="{ 'has-error' : eventForm.addressLine1.$invalid && !eventForm.addressLine1.$pristine }">
					<label>Event Address Line 1*</label>
					<input type="text" name="addressLine1" id="addressLine1" class="form-control" ng-model="event.addressLine1" required>
				<p ng-show="eventForm.addressLine1.$invalid && !eventForm.addressLine1.$pristine" class="help-block">Address Line 1 is required.</p>
				</div>
			
				<div class="form-group" ng-class="{ 'has-error' : eventForm.addressLine2.$invalid && !eventForm.addressLine2.$pristine }">
           		 <label>Address Line 2</label>
            	 <input type="text" name="addressLine2" class="form-control" ng-model="event.addressLine2" required>
       		   <p ng-show="eventForm.addressLine2.$invalid && !eventForm.addressLine2.$pristine" class="help-block">Address Line 2 is required.</p>
       		   </div>
		
			<div class="form-group" ng-class="{ 'has-error' : eventForm.city.$invalid && !eventForm.city.$pristine }">
            <label>City</label>
            <input type="text" name="city" class="form-control" ng-model="event.city" required>
        	<p ng-show="eventForm.city.$invalid && !eventForm.city.$pristine" class="help-block">city is required.</p>
        	</div>
        	
        		<div class="form-group">
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
        
        <div class="form-group"  ng-class="{ 'has-error' : eventForm.zipCode.$invalid && !eventForm.zipCode.$pristine }">
            <label>Zip Code</label>
            <input type="text" name="zipCode" class="form-control" ng-model="event.zipCode" required>
            <p ng-show="eventForm.zipCode.$invalid && !eventForm.zipCode.$pristine" class="help-block">Zip Code is required.</p>
        </div> 
		
		<div class="form-group" ng-class="{ 'has-error' : eventForm.description.$invalid && !eventForm.description.$pristine }">
            <label>Description</label>
            <input type="text" name="description" class="form-control" ng-model="event.description" required >
        <p ng-show="eventForm.description.$invalid && !eventForm.description.$pristine" class="help-block">Description is required.</p>
        </div> 
        
        <div class="form-group" ng-class="{ 'has-error' : eventForm.ticketPrice.$invalid && !eventForm.ticketPrice.$pristine }">
            <label>Ticket Price</label>
            <input type="text" name="ticketPrice" class="form-control"  ng-model="event.ticketPrice" required >
        <p ng-show="eventForm.ticketPrice.$invalid && !eventForm.ticketPrice.$pristine" class="help-block">Ticket Price is required.</p>
        </div> 
		
		
		<div class="form-group" ng-class="{ 'has-error' : eventForm.minAgeLimit.$invalid && !eventForm.minAgeLimit.$pristine }">
            <label>Minimum Age Limit</label>
            <input type="text" name="minAgeLimit" class="form-control" ng-model="event.minAgeLimit"  required >
        </div> 
        
        <div class="form-group" ng-class="{ 'has-error' : eventForm.remainingTickets.$invalid && !eventForm.remainingTickets.$pristine }">
            <label>Event Capacity</label>
            <input type="text" name="remainingTickets" class="form-control"  ng-model="event.remainingTickets" required >
       <p ng-show="eventForm.remainingTickets.$invalid && !eventForm.remainingTickets.$pristine" class="help-block">Event Capacity is required.</p>
        </div> 
        
			<div class="form-group">
				<button type="submit" ng-disabled="eventForm.$invalid" class="btn btn-success navbar-btn" >Create Event & New Search</button>
			</div>
			
			<input type ="hidden"  name="latitude" value = "<%=latitude%>"> 
			<input type ="hidden"  name="longitude" value ="<%=longitude %>">  
			<input type ="hidden"  name="username" value = "<%=username%>"> 
			<input type ="hidden"  name="password" value ="<%=password %>">  
			
			</form>
		</div>
		</div>
	</div>
	<div class="footer">		
		<p style="float:center">&copy; JeRKS (CS5500)</p>
		<p style="float:right">CCIS - Northeastern University</p>
	</div>

</body>
</html>