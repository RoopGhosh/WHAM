function initialize(jsonEvents, latitude, longitude) {
	console.log('Hello from intialize'+longitude);
	var myCenter = new google.maps.LatLng(latitude, longitude);
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
			position : new google.maps.LatLng(jsonEvents[i].address.latitude,
					jsonEvents[i].address.longitude),
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

