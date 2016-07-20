package com.sherpa.ejercicio.service;

import java.io.IOException;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sherpa.ejercicio.domain.Usuario;
import com.sherpa.ejercicio.dao.GeonamesDao;
import com.sherpa.ejercicio.domain.Ciudad;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GeonamesService {
	
	String geonamesUrl = "http://api.geonames.org/postalCodeSearchJSON?postalcode=%s&maxRows=1&username=%s";
	
	public String handleUsuario(Usuario usuario, String codigoPostal) {
		String url = String.format(geonamesUrl, codigoPostal, usuario.getNombre());

		try {
			String nombreCiudad = extraerCiudad(accederUrl(url));
			if (nombreCiudad.isEmpty()) return "No se ha podido obtener la ciudad";
			
			Ciudad ciudad = new Ciudad(codigoPostal, nombreCiudad);
			GeonamesDao dao = new GeonamesDao();
			dao.guardarValoresGeonames(usuario, ciudad);
		} catch (IOException e) {
			return "No se ha podido obtener la informacion de geonames.ong";
		} catch (ParseException e1) {
			return "Ha ocurrido un error al examinar los datos devueltos por geoname.org";
		} catch (SQLException e) {
			return "No se han podido guardar los datos en la BBDD";
		}
		return "";
	}
	
	public String accederUrl(String url) throws IOException {
		Request request = new Request.Builder().url(url).build();
		OkHttpClient client = new OkHttpClient();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}
	
	public String extraerCiudad(String response) throws ParseException {
		String ciudad = "";
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(response);
		JSONArray postalCodes = (JSONArray)json.get("postalCodes");
		json = (JSONObject)postalCodes.get(0);
		ciudad = (String)json.get("placeName");
		return ciudad;
	}
}
