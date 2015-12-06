/**
 * Author: Sandeep Ramamoorthy
 * Date: 11/6/2015 12:57 AM
 * Description: Contains the code snippet to detect the geographical
 * location of the user. This code snippet is taken from Mozilla Developers Network
 * (https://developer.mozilla.org/en-US/docs/Web/API/Geolocation/getCurrentPosition)
 */
var options = {
  enableHighAccuracy: true,
  timeout: 5000,
  maximumAge: 0
};

function success(pos) {
  var crd = pos.coords;
  location.href = "index" +"/"+ crd.latitude  +"/"+ crd.longitude;
};

/* Author: Sandeep
 * Date: 11/19/2015
 * The error will be because of two reasons.
 * 	1) user blocks location detection
 * 	2) Browser doesn't support location detection
 * In such times,return Boston's latitude & longitude*/
function error(err) {
 var bostonLat = 42.3132882;
 var bostonLong = -71.1972408;
 location.href = "index" +"/"+ bostonLat  +"/"+ bostonLong;
};

function getLocation() {
    navigator.geolocation.getCurrentPosition(success, error, options);
}