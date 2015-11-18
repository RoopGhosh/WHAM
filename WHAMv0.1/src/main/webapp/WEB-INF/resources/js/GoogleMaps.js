function initialize(jsonEvents) {
	var myCenter = new google.maps.LatLng(intialLocation.latitude,
			intialLocation.longitude);
	var mapProp = {
		center : myCenter,
		zoom : 12,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	};
	var map = new google.maps.Map(document.getElementById("googleMap"), mapProp);

	google.maps.event.addDomListener(window, "resize", function() {
		var center = map.getCenter();
		google.maps.event.trigger(map, "resize");
		map.setCenter(center);
	});
	var infowindow = new google.maps.InfoWindow();
	for (var i = 0; i < jsonEvents.length; i++) {
		var marker = new google.maps.Marker({
			position : new google.maps.LatLng(jsonEvents[i]._address._latitude,
					jsonEvents[i]._address._longitude),
		});
		marker.setMap(map);

		google.maps.event.addListener(marker, 'click', (function(marker, i) {
			return function() {
				var contentString = '<div id="content">'
						+ '<div id="bodyContent">' + '<p>'
						+ jsonEvents[i]._name + '</div>' + '</div>';
				infowindow.setContent(contentString);
				infowindow.open(map, marker);
				showEventDetails(jsonEvents[i]);
			}
		})(marker, i));
	}

	function showEventDetails(jsonEvent) {
		var nameTag = document.getElementById("name");
		var descriptionTag = document.getElementById("description");
		var moreTag = document.getElementById("more");	
		nameTag.innerHTML = jsonEvent._name;
		descriptionTag.innerHTML = jsonEvent._description;
		// Store the event details in the browser's local storage for retrieving the event in eventDetails page.  
		localStorage.setItem(jsonEvent._id, jsonEvent);
		moreTag.innerHTML = '<b><a href="eventDetails.jsp?id='+jsonEvent._id+'"> More Details >> </a></b>'; 
	}
}