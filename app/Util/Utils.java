package Util;

import javax.servlet.http.HttpServletRequest;

import play.Logger;
import play.mvc.Http.Request;

// Class that containt useful methods
public class Utils {

	
	public static String getIpRemote(Request request)
	{
		String ipReturn = "";
		
		try 
		{
			if (request != null) 
			{
				ipReturn = request.remoteAddress;
			}
			else {
				throw new Exception("El request es null");
			}
			
		} 
		catch (Exception e) {
			// TODO: handle exception
			Logger.error("No se ha podido recuperar la ip", e);
		}
		
		
		return ipReturn;
	}
	

}
