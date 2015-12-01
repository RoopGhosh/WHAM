<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="edu.neu.cs5500.Jerks.apiCall.*, edu.neu.cs5500.Jerks.definitions.*, edu.neu.cs5500.Jerks.dbProviders.*, edu.neu.cs5500.Jerks.business.*, com.google.gson.Gson ,java.util.*"
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" />
    <style>
        body     { padding-top:30px; }
    </style>
    
    <!-- JS -->
    <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.26/angular.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.2/jquery-ui.min.js"></script>
	<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
    <script src="register.js"></script>
    <% 
    String email="";
	String firstName;
	String lastName;	
	String password="";
 	String addressLine1;
	String addressLine2;
 	String city;
	String state;
	String country;
	String zipCode;
	String latitude=String.valueOf(request.getParameter("latitude"));
 	String longitude=String.valueOf(request.getParameter("longitude"));
    Address address;
	String phoneNumber;
	Date dob;
	String gender;	
	String areaOfInterest;
	String disLikes;
	
	try
	{
		firstName = String.valueOf(request.getAttribute("firstName"));
	    System.out.println("Register page:"+firstName);
	    lastName = request.getParameter("lastName");
	    System.out.println(lastName);
	    System.out.println("Register page:"+lastName);
		email = request.getParameter("email");
		System.out.println("Register page:"+email);
		password = request.getParameter("password");
		addressLine1 = request.getParameter("addrLine1");
		addressLine2 = request.getParameter("addrLine2");
		city = request.getParameter("city");
		country = "US";
		state = request.getParameter("state");
		zipCode = request.getParameter("zipCode");
		//latitude = String.valueOf(session.getAttribute("latitude"));
		System.out.println();
		//longitude = String.valueOf(session.getAttribute("longitude"));
	}
	catch(Exception e)
	{
		System.out.println("Could not create a user");
	}
	
    %>
    
	<script>
		$(function() {
			$( "#datepicker" ).datepicker({ dateFormat: 'yy-mm-dd' }).val();
		});
	</script>
</head>
</head>
<body ng-app="validationApp" ng-controller="mainController" >
	<div class="container">

<div class="row">
	<div class="col-md-4">    
    <!-- PAGE HEADER -->
    <div class="page-header"><h1>User Registration</h1></div>
   
    <form name="userForm"  ng-submit="submitForm(userForm.$valid)" action="/jerks/register"  method="post">

        
        <div class="form-group" ng-class="{ 'has-error' : userForm.firstName.$invalid && !userForm.firstName.$pristine }">
            <label>First Name*</label>
            <input type="text" name="firstName" class="form-control col-s-1" ng-model="user.firstName" required>
            <p ng-show="userForm.firstName.$invalid && !userForm.firstName.$pristine" class="help-block">You name is required.</p>
        </div>
        
        <div class="form-group" ng-class="{ 'has-error' : userForm.lastName.$invalid && !userForm.lastName.$pristine }">
            <label>Last Name*</label>
            <input type="text" name="lastName" class="form-control" ng-model="user.lastName" required>
            <p ng-show="userForm.lastName.$invalid && !userForm.lastName.$pristine" class="help-block">You name is required.</p>
        </div>           
        
        <div class="form-group" ng-class="{ 'has-error' : userForm.email.$invalid && !userForm.email.$pristine }">
            <label>Email*</label>
            <input type="text" name="email" class="form-control" ng-model="user.email" required>
            <p ng-show="userForm.email.$invalid && !userForm.email.$pristine" class="help-block">Enter a valid email.</p>
        </div>
        
		<div class="form-group" ng-class="{ 'has-error' : userForm.password.$invalid && !userForm.password.$pristine }">
            <label>Password*</label>
            <input type="password" name="password" class="form-control" ng-model="user.password" ng-minlength="8" ng-maxlength="20" ng-pattern="/(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z])/" required >
            <p ng-show="userForm.password.$invalid && !userForm.password.$pristine" class="help-block">Enter a valid Password.</p>
        </div>
		
		<div class="form-group" >
            <label>Address Line 1</label>
            <input type="text" name="addrLine1" class="form-control" ng-model="user.addrLine1" >
        </div>  
		
		<div class="form-group" >
            <label>Address Line 2</label>
            <input type="text" name="addrLine2" class="form-control" ng-model="user.addrLine2">
        </div>
		
		<div class="form-group" >
            <label>City</label>
            <input type="text" name="city" class="form-control" ng-model="user.city">
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

		
		<div class="form-group" ng-class="{ 'has-error' : userForm.zipCode.$invalid && !userForm.zipCode.$pristine }">
            <label>Zip Code</label>
            <input type="text" name="zipCode" class="form-control" ng-model="user.zipCode" ng-pattern="/^\d{5}$/" required >
            <p ng-show="userForm.zipCode.$invalid && !userForm.zipCode.$pristine" class="help-block">Enter valid Zip Code</p>
        </div> 
		
		<div class="form-group">
			<select name="category" multiple="multiple">
				<option value="103">music</option>
					<option value="110">food</option>
					<option value="107">support</option>
					<option value="104">movies_film</option>
					<option value="105">performing_arts</option>
					<option value="115">family_fun_kids</option>
					<option value="115">learning_education</option>
					<option value="114">religion_spirituality</option>
					<option value="108">sports</option>
					<option value="116">holiday</option>
					<option value="101">business</option>
					<option value="102">science</option>
					<option value="102">technology</option>
					<option value="111">fundraisers</option>
					<option value="112">politics_activism</option>
					<option value="109">outdoors_recreation</option>
					<option value="113">community</option>
					<option value="119">books</option>
					<option value="199">other</option>
			</select>
		</div>
		 
		<div class="form-group" >
            <label>Date</label>
            <input type="text" name="datepicker" id="datepicker" class="form-control">
        </div> 
		
		<div class="form-group" >
			<label>Gender:&nbsp;</label>
            <label class="radio-inline"><input type="radio" name="optradio" value="M">Male</label>
			<label class="radio-inline"><input type="radio" name="optradio" value="F">Female</label>
			<label class="radio-inline"><input type="radio" name="optradio" value="X">Prefer not to say</label>
        </div> 
		
		<div class="form-group" ng-class="{ 'has-error' : userForm.phoneNumber.$invalid && !userForm.phoneNumber.$pristine }">
            <label>Phone Number</label>
            <input type="phoneNumber" name="phoneNumber" class="form-control" ng-model="user.phoneNumber" ng-pattern="/^\d{0,10}(\.\d{1,10})?$/" required >
            <p ng-show="userForm.phoneNumber.$invalid && !userForm.phoneNumber.$pristine" class="help-block">Enter a valid Phone number.</p>
        </div>
    		 
        <input type="submit" ng-disabled="userForm.$invalid" class="btn btn-success">Register</button>
        
        <input type ="hidden"  name="latitude" value = "<%=latitude%>"> 
		<input type ="hidden"  name="longitude" value ="<%=longitude %>">  
    </form>
 </div>
</div> 
</div>
</body>
</html>