package controllers;

import java.io.IOException;
import java.util.List;

import models.Farmacia;
import play.mvc.Controller;
import scrapers.Aragon;

public class Application extends Controller {

    
    /* 
       Aqui se hace la llamada a /
       esta no debería devolver nada por el momento 
     */
    
	public static void index() {
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