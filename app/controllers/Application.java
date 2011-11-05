package controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import Util.Utils;
import Util.maxmind.geoip.Location;
import Util.maxmind.geoip.LookupService;
import Util.maxmind.geoip.regionName;
import Util.maxmind.geoip.timeZone;



import models.Farmacia;
import play.Logger;
import play.mvc.Controller;
import scrapers.Aragon;

public class Application extends Controller {

    
    /* 
       Aqui se hace la llamada a /
       esta no debería devolver nada por el momento 
     */
    
	public static void index() throws IOException {
		
		String ipRemote=Utils.getIpRemote(request);
		Logger.info("ip--"+ipRemote);
		String rutaFichero = "lib/GeoLiteCity.dat";
		rutaFichero.replaceAll("/", File.separator);
		 
		 LookupService cl = new LookupService(rutaFichero,
					LookupService.GEOIP_MEMORY_CACHE );
         Location l1 = cl.getLocation("213.52.50.8");
         Location l2 = cl.getLocation(ipRemote);
         Logger.info("countryCode: " + l2.countryCode +
                            "\n countryName: " + l2.countryName +
                            "\n region: " + l2.region +
                            "\n regionName: " + regionName.regionNameByCode(l2.countryCode, l2.region) +
                            "\n city: " + l2.city +
                            "\n postalCode: " + l2.postalCode +
                            "\n latitude: " + l2.latitude +
                            "\n longitude: " + l2.longitude +
                            "\n distance: " + l2.distance(l1) +
                            "\n distance: " + l1.distance(l2) + 
			       "\n metro code: " + l2.metro_code +
			       "\n area code: " + l2.area_code +
                            "\n timezone: " + timeZone.timeZoneByCountryAndRegion(l2.countryCode, l2.region));

	    cl.close();

		
        render();
    }

    /*
        Esta llamada se hace despues de haber cargado la posición del usuario.
     */
	public static void farmacias(String latitude, String longitude) {

        /*

        Aqui debería haber el código que obtiene las N farmacias más proximas ordenadas por distancia.

         */



        List<Farmacia> farmacias = Farmacia.find("order by titular").fetch();
        if (farmacias.isEmpty())
            try {
                Aragon.scrape();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        System.out.print(farmacias);
        renderJSON(farmacias);
	}

    public static void test() {
        render();
    }
	
}