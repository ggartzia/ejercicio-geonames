package com.sherpa.ejercicio.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.sherpa.ejercicio.domain.Usuario;
import com.sherpa.ejercicio.service.GeonamesService;

@WebServlet(name="usuario",urlPatterns={"/guardarCiudad"})
public class GeonamesServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		
		String nombreUsuario = request.getParameter("usuario");
		String codigoPostal = request.getParameter("cp");
		
		System.out.println(nombreUsuario +  " " + codigoPostal);
		
		if (!nombreUsuario.isEmpty() && !codigoPostal.isEmpty()) {
			
			Usuario usuario = new Usuario();
			usuario.setNombre(nombreUsuario);
			
			GeonamesService service = new GeonamesService();
			String respuesta = service.handleUsuario(usuario, codigoPostal);
			
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			
			if (respuesta.isEmpty()) {
				json.put("status", HttpServletResponse.SC_OK);
				json.put("response", "El usuario y la ciudad se han guardado corretamente");
			} else {
				json.put("status", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				json.put("response", respuesta);
			}
			
		} else {
			json.put("status", HttpServletResponse.SC_BAD_REQUEST);
			json.put("response", "Los argumentos son incorrectos. Pruebe otra vez.");
		}
		
		out.print(json);
		out.flush();
	}

}
