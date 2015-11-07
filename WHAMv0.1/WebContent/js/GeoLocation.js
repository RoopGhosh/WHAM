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
  var outString = "index.jsp?latitude=" + crd.latitude
  outString += "&longitude=" + crd.longitude
  location.href = outString
  
  console.log('Your current position is:');
  console.log('Latitude : ' + crd.latitude);
  console.log('Longitude: ' + crd.longitude);
};

function error(err) {
  console.warn('ERROR(' + err.code + '): ' + err.message);
};

function getLocation() {
    navigator.geolocation.getCurrentPosition(success, error, options);
}