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

var intialLocation = {
		latitude: 42.3398198,
		longitude: -71.0875516
};

function success(pos) {
  var crd = pos.coords;
  console.log('Latitude : ' + crd.latitude);
  console.log('Longitude: ' + crd.longitude);
  intialLocation.latitude = crd.latitude;
  intialLocation.longitude = crd.longitude;
  location.href = "index.jsp?latitude=" + crd.latitude + "&longitude=" + crd.longitude;
};

function error(err) {
  console.log('ERROR(' + err.code + '): ' + err.message);
};

function getLocation() {
    navigator.geolocation.getCurrentPosition(success, error, options);
    return intialLocation;
}