package com.sherpa.ejercicio.service;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.SQLException;

import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

import com.sherpa.ejercicio.dao.GeonamesDao;
import com.sherpa.ejercicio.domain.Ciudad;
import com.sherpa.ejercicio.domain.Usuario;

@RunWith(MockitoJUnitRunner.class)
public class GeonamesServiceTest {

	Usuario usuario = new Usuario("ggartzia");
	Ciudad ciudad = new Ciudad("48940", "La Chopera");
	
	@Mock
	private GeonamesDao mockGeonamesDao = new GeonamesDao();
	
	private GeonamesService geonamesService = new GeonamesService();
	
	@Test
	public void handleUsuarioTest () throws SQLException {
		
		doNothing().when(mockGeonamesDao).guardarValoresGeonames(usuario, ciudad);

		String response = geonamesService.handleUsuario(usuario, "48940");
		
		assertEquals("error en handleUsuario", "", response);
	}
	
	@Test
	public void handleUsuarioThrowsSQLExceptionTest () throws Exception {
		
		doThrow(new SQLException()).when(mockGeonamesDao).guardarValoresGeonames(Matchers.anyObject(), Matchers.anyObject());

		String response = geonamesService.handleUsuario(usuario, "48940");
		
		assertEquals("no se ha lanzado error en la BBDD", "No se han podido guardar los datos en la BBDD", response);
		
	}
	
	@Test
	public void handleUsuarioThrowsParseExceptionTest () throws ParseException {

		when(geonamesService.extraerCiudad(Matchers.any(String.class))).thenThrow(new ParseException(0));
		
		String response = geonamesService.handleUsuario(usuario, "48940");
		
		assertEquals("no se ha lanzado error al parsear", "Ha ocurrido un error al examinar los datos devueltos por geoname.org", response);
		
	}
	
	@Test
	public void handleUsuarioThrowsIOExceptionTest () throws IOException {

		when(geonamesService.accederUrl(Matchers.any(String.class))).thenThrow(new IOException());
        
		String response = geonamesService.handleUsuario(usuario, "48940");
		
		assertEquals("no se ha lanzado error al parsear", "No se ha podido obtener la informacion de geonames.ong", response);	
	}

	@Test
	public void extraerCiudadTest () throws Exception {
		String ciudad = "La Chopera";
		String response = "{\"postalCodes\":[{\"postalCode\":\"48940\",\"adminName1\":\"Pais Vasco\",\"placeName\":\"La Chopera\"}]}";
		
		String respuestaCiudad = geonamesService.extraerCiudad(response);
		assertEquals("ciudad no encontrada", ciudad, respuestaCiudad);
	}
}
