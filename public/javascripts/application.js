var tweet_coordinates = [];
var map;
var infoWindow = new google.maps.InfoWindow({
    maxWidth: 350
});

function initialize() {

    //opciones para configurar la visualizacion del mapa
    var myOptions = {
        zoom: 15,
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        panControl: false,
        mapTypeControl: false,
        zoomControl: false,
        streetViewControl: false
    };

    //creamos el mapa indicando el div donde se va a insertar y las opciones
    map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);

    var initialLocation;

    if(navigator.geolocation) { //si el navegador soporta la localizacion
        navigator.geolocation.getCurrentPosition(porGeo, porIp); //porGeo si acepta, porIp si rechaza
    } else { //en caso de no soportarla, por ip
        porIp;
    }


    function porGeo(position) {
        //se obtienen las coordenadas por navegador
        initialLocation = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
        setHome();
    }

    function porIp(position) {
        //se obtienen las coordenadas con el servicio de maxmind
        var lat = geoip_latitude();
        var lon = geoip_longitude();
        initialLocation = new google.maps.LatLng(lat, lon);
        setHome();
    }


    function setHome() {
        $('#indicador').hide();
        map.setCenter(initialLocation);
        var marker = new google.maps.Marker({
            position: initialLocation,
            map: map,
            title:"Su posicion",
            icon:"/public/images/home.png"
        });
        setFarmacias();
    }

    //prueba de como quedaría el mapa con las farmacias mas cercanas
    function setFarmacias() {
        var request = {
            location: map.getCenter(),
            radius: '1000',
            types: ['pharmacy']
        };

        var service = new google.maps.places.PlacesService(map);
        service.search(request, callback);

        function callback(results, status) {
            if (status == google.maps.places.PlacesServiceStatus.OK) {
                for (var i = 0; i < results.length; i++) {
                    createMarker(results[i]);
                }
            }
        }

        function createMarker(place) {
            var marker = new google.maps.Marker({
                map: map,
                position: place.geometry.location,
                icon:"/public/images/medicine.png"
            });

            google.maps.event.addListener(marker, 'click', function() {
                infoWindow.setContent(place.name);
                infoWindow.open(map, this);
            });
        }
    }
}

$("body").ready(function() {
    initialize();
});
