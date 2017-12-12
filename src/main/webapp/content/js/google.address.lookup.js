function initAutocomplete() {
// Create the search box and link it to the UI element.
var input = document.getElementById('formSearchUpLocation');
var city = document.getElementById('city');
city.addEventListener("change", function() {
    console.log("change");
  });
var autocomplete = new google.maps.places.Autocomplete(input);
autocomplete.setComponentRestrictions(
		{'country': ['ae']});
google.maps.event.addListener(autocomplete, 'place_changed',callback);
//auto-complete restricted to UAE locality
// Listen for the event fired when the user selects a prediction and retrieve
// more details for that place.
    function callback() {
        var place = autocomplete.getPlace();
        AddressLookup.location  = place.geometry.location;
        console.log(place.geometry.location.lat(),place.geometry.location.lng());
      }
}
AddressLookup = {
	location : ""
}
