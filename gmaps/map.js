var self = this;

function initMap() {
  this.unq = { lat: -34.707629, lng: -58.278222 };
  this.map = new google.maps.Map(document.getElementById('map'),
                                 { zoom: 16, center: unq });

  this.geocoder = new google.maps.Geocoder;
  var locations = userGeolocation()
}

function userGeolocation() {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(function(position) {
      var self = this;
      this.pos = {
        lat: position.coords.latitude,
        lng: position.coords.longitude
      };
      this.posDest = {
        lat: position.coords.latitude + 0.001,
        lng: position.coords.longitude + 0.001
      };
      var origin = new google.maps.Marker({ position: pos, map:  self.map,
                                            draggable:true, title:"Origen" });
      var destination = new google.maps.Marker({ position: posDest,
                                                 map: self.map, draggable:true,
                                                 title:"Destino" });
      getInfo(origin, destination)
      origin.addListener('position_changed',
                          function() { getInfo(this, destination) })

      destination.addListener('position_changed',
                               function() { getInfo(origin, this) })
      self.map.setCenter(pos)
      });
    }
  }

  function getInfo(origin, destination){
    geocoder.geocode({'location': origin.getPosition()}, function(results, status) {
       if (status === 'OK') {
         console.log("sdsadfs")
         console.log(destination.getPosition())
         var service = new google.maps.DistanceMatrixService;
         service.getDistanceMatrix({
           origins: [origin.getPosition()],
           destinations: [destination.getPosition()],
           travelMode: 'DRIVING',
           unitSystem: google.maps.UnitSystem.METRIC,
           avoidHighways: false,
           avoidTolls: false
           }, function(response, status){
           bindInfo('origin-info', response.originAddresses[0])
           bindInfo('destination-info', response.destinationAddresses[0])
           var distanceInfo = response.rows[0].elements[0]
           bindInfo('distance-info', distanceInfo.distance.text)
           bindInfo('duration-info', distanceInfo.duration.text)
         })
        }
      });
  }

function bindInfo(elemId, info) {
  document.getElementById(elemId).innerHTML = info
}
