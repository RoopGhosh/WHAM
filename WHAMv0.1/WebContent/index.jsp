<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"
import="edu.neu.cs5500.Jerks.apiCall.*, edu.neu.cs5500.Jerks.definitions.*, edu.neu.cs5500.Jerks.business.*, com.google.gson.Gson ,java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" />
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.0.0/css/font-awesome.css" />
	<script src="js/GeoLocation.js"></script>
	<title>WHAM - Home</title>
	<style>
	</style>
	<script>
	function initialize()
	{	
	  var myCenter = new google.maps.LatLng(intialLocation.latitude, intialLocation.longitude);
	  var mapProp = {
	    center: myCenter,
	    zoom:12,
	    mapTypeId: google.maps.MapTypeId.ROADMAP
	  };
	  var map = new google.maps.Map(document.getElementById("googleMap"),mapProp);
	  
	  <%String url = "http://api.eventful.com/json/events/search?app_key=4fgZC93XQz2fgKpV&where=42.3601,-71.0589&within=10&date=Future&page_size=50&sort_order=popularity";
	 		EventfulAPICall eventCall = new EventfulAPICall();
	  		ArrayList<Event> listOfEvent = eventCall.getListofEventsFromJSON(url);
	  		String jsonEvents = new Gson().toJson(listOfEvent);
	  		System.out.println(jsonEvents); 
	  		double latitude = Double.parseDouble(request.getParameter("latitude"));
	  		double longitude = Double.parseDouble(request.getParameter("longitude"));
	  		System.out.println(latitude);
	  		System.out.println(longitude);
	  		%>
	 
	  		
	  		/* double latitude = 0.0f;
	  		double longitude = 0.0f;
			String searchAddress = null;
			String searchEvent = null; 
			String price = null;
			Date date = null;
			String[] categories = null;
			EventManager em = new EventManager();
			List<Event> events = em.fetchEvents(latitude, longitude, searchAddress, searchEvent, price, date, categories); */
			 
		google.maps.event.addDomListener(window, "resize", function() {
				   var center = map.getCenter();
				   google.maps.event.trigger(map, "resize");
				   map.setCenter(center); 
				});
	  
	  var jsonEvents = <%=jsonEvents%>;
	  console.log(jsonEvents);
	  var infowindow = new google.maps.InfoWindow();
	  for(var i=0; i<jsonEvents.length;i++)
		  {
				var marker=new google.maps.Marker({
					  position: new google.maps.LatLng(jsonEvents[i]._address._latitude, jsonEvents[i]._address._longitude),
					  });

				marker.setMap(map);
				
				google.maps.event.addListener(marker, 'click', (function(marker, i) {
			        return function() {
					  var contentString  = '<div id="content">'+
			      '<div id="bodyContent">'+
			      '<p>'+ jsonEvents[i]._name+
			      '</div>'+
			      '</div>';
			          infowindow.setContent(contentString);
			          infowindow.open(map, marker);
			          showEventDetails(jsonEvents[i]);
			        }
			      })(marker, i));
		  }
	  
	  function showEventDetails(jsonEvents)
	  {
		  var print = '<p>'+jsonEvents._name+'</p>'
		  eventDetails.innerHTML = print;
	  }
	  
	 }
	
	function loadScript()
	{
	  var script = document.createElement("script");
	  script.type = "text/javascript";
	  script.src = "http://maps.googleapis.com/maps/api/js?key=&sensor=false&callback=initialize";
	  document.body.appendChild(script);
	}
	window.onload = loadScript;
	</script>

</head>
<body>
	<div class="container">
		<div class="rows">
			<div class="col-md-12">
				<div class="navbar-header">
                	<a class="navbar-brand" href="#">Project name</a>
                </div>
                <div id="navbar" class="navbar-collapse collapse">
                    <ul class="nav navbar-nav navbar-right">
                        <li ><a href="#/home">Logout</a></li>
                    </ul>
                    <form class="navbar-form navbar-right">
                    	<div class="form-group">
                    		 <input type="text" class="form-control" placeholder="Search">
                    		 <button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search">
                    	</div>
                        <div class="form-group">
                            <input type="text" placeholder="Username" class="form-control">
                        </div>
                        <div class="form-group">
                            <input type="password" placeholder="Password" class="form-control">
                        </div>
                        <button type="button" class="btn btn-success">Sign in</button>
                         <button type="button" class="btn btn-success">Register</button>
                    </form>
                </div>
			</div>
		</div>
		<div class="rows">
			<div class="col-md-2">
				<div id="eventDetails">
				</div>
			</div>
			<div class="col-md-10">
				<div id="googleMap" style="width:100vw;height:100vh;">
				</div>   
			</div>
		</div>
	</div>
</body>
</html>