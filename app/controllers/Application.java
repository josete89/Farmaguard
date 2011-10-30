package controllers;

import java.io.IOException;
import java.util.List;

import models.Farmacia;
import play.mvc.Controller;
import scrapers.Aragon;

public class Application extends Controller {

	public static void index() {
	   List<Farmacia> farmacias = Farmacia.find("order by titular").fetch();
	   if (farmacias.isEmpty())
		try {
			Aragon.scrape();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			   
       System.out.print(farmacias);
       render(farmacias);
    }
	
	public static void map() {
	   render();
	}
	
}