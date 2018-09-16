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
      origin.addListener('position_changed',
                          function(destination) { bindInfo(this, destination) })
      destination.addListener('position_changed',
                               function(origin) { bindInfo(this, origin) })
      self.map.setCenter(pos)
      });
    }
  }

  function bindInfo(p1, p2){
    geocoder.geocode({'location': p1.getPosition()}, function(results, status, p2) {
       if (status === 'OK') {
         console.log(status)
         console.log(results)
       }
     });
  }
