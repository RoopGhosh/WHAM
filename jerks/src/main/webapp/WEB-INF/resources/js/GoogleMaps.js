function initialize(jsonEvents, latitude, longitude) {
	
	var myCenter = new google.maps.LatLng(latitude, longitude);
	var mapProp = {
		center : myCenter,
		zoom : 12,
		mapTypeId : google.maps.MapTypeId.ROADMAP,
		
	};
	
	var map = new google.maps.Map(document.getElementById("googleMap"), mapProp);

	google.maps.event.addDomListener(window, "resize", function() {
		var center = map.getCenter();
		google.maps.event.trigger(map, "resize");
		map.setCenter(center);
	});
	
	var infowindow = new google.maps.InfoWindow();
	//Marker for current location
	console.log('My lat: '+latitude);
	console.log('My long: '+longitude);
	var pinColor = "00FF00";
    var pinImage = new google.maps.MarkerImage("http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=%E2%80%A2|" + pinColor,
        new google.maps.Size(21, 34),
        new google.maps.Point(0,0),
        new google.maps.Point(10, 34));	
	var marker = new google.maps.Marker({
		animation: google.maps.Animation.BOUNCE,
	    position: new google.maps.LatLng(latitude, longitude),
	    icon: pinImage,
	    map: map
	  });
	
	for (var i = 0; i < jsonEvents.length; i++) {
		var marker = new google.maps.Marker({
			position : new google.maps.LatLng(jsonEvents[i].address.latitude,
					jsonEvents[i].address.longitude),
			animation: google.maps.Animation.DROP
		});
		marker.setMap(map);

		google.maps.event.addListener(marker, 'click', (function(marker, i) {
			return function() {
				var contentString = '<div id="content">'
						+ '<div id="bodyContent">' + '<p>'
						+ jsonEvents[i].name + '</div>' + '</div>';
				infowindow.setContent(contentString);
				infowindow.open(map, marker);
				showEventDetails(jsonEvents[i], latitude, longitude);
			}
		})(marker, i));
	}
}