package scrapers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import models.Farmacia;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class Aragon {

	public static void main(String[] args) {
		
		try {
			scrape();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void scrape () throws IOException {
		
		/* I - primero recopilamos todas las zonas de salud de aragon: */
		
		String id = "50"; // id de aragon
		
			// 1- conectamos y obtenemos el arbol entero
			String url = "http://pfarmals.portalfarma.com:8080/guard/web_guardias/publico/provincia_p.asp?id=" + id ;
			System.out.println(url);			
			Document doc = Jsoup.connect(url).timeout(10*1000).get();
			
			//2- filtramos para solo quedarnos con los options
			Elements datos = doc.select("select[name=vzona] option");
	
			//3-rellenamos un hashmap con <idZona, literal>
			HashMap<String, String> zonas = new HashMap<String,String>();
			for (int c = 1; c < datos.size(); c++) {
				zonas.put(datos.get(c).attr("value"), datos.get(c).text());
				System.out.println(datos.get(c).attr("value") + " -> " +  datos.get(c).text());
			}
		
		/* II - vamos obteniendo de cada zona sus farmacias de guardia de hoy */

			String fecha = new SimpleDateFormat("d/M/yyyy").format(new Date()); //fecha formateada para url
			
			Iterator<String> it = zonas.keySet().iterator();
			String idZona;
			String literal;
			
			while(it.hasNext()) {	
				idZona = it.next();
				literal = zonas.get(idZona);
				
				// 1- conectamos y obtenemos el arbol entero
				doc = Jsoup.connect("http://pfarmals.portalfarma.com:8080/guard/web_guardias/Guardias.asp?date="+ fecha +"&vzona=" + idZona +"&vmenu=1&provincia=" + id).timeout(10*1000).get();
				
				//2- filtramos para solo quedarnos con las farmacias
				datos = doc.select("small small");
				
				//3- sacamos por pantalla
				System.out.println("******************************");
				System.out.println(literal + " -- " + idZona);
				for (int c = 0; c < datos.size(); c++) {
					//System.out.print(" ----------- " + datos.get(c).text().substring(2));
					
					/* III - sacamos los datos de cada famacia listada */
					String idFarmacia = datos.get(c).parent().parent().siblingElements().select("a").first().attr("href");
					idFarmacia = idFarmacia.substring(idFarmacia.indexOf(",") +1, idFarmacia.lastIndexOf(","));
					//este id farmacia servira para parsear las urls: http://pfarmals.portalfarma.com:8080/guard/web_guardias/datos.asp?id=50000138
					System.out.println(" -- ID = "  + idFarmacia);
					
					//se conecta a la url y se obtiene el contenido
					doc = Jsoup.connect("http://pfarmals.portalfarma.com:8080/guard/web_guardias/datos.asp?id=" + idFarmacia).timeout(10*1000).get();
					
					//extraccion la lista de datos que nos interesa
					Elements datosFarmacia = doc.select("font[color=#000000]");
					if (!datosFarmacia.isEmpty()) {
						Farmacia farmacia = new Farmacia.Builder(datosFarmacia.get(1).text())
																.direccion(datosFarmacia.get(3).text())
																.localidad(datosFarmacia.get(5).text())
																.telefono(datosFarmacia.get(7).text())
																.fax(datosFarmacia.get(8).text())
																.observaciones(datosFarmacia.get(10).text())
																.scrapeId(idFarmacia)
																.build();
						System.out.println(farmacia.toString());
						farmacia.save();
					}
				}
			}
	}
	

}
