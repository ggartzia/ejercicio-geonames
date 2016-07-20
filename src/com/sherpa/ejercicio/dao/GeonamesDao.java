package com.sherpa.ejercicio.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.sherpa.ejercicio.domain.Ciudad;
import com.sherpa.ejercicio.domain.Usuario;

public class GeonamesDao {
	
	String db = "";
	String user = "";
	String password = "";
	
	public GeonamesDao() {}
	
	public void guardarValoresGeonames(Usuario usuario, Ciudad ciudad) throws SQLException {
		String queryUsuario = "insert into master values( master_seq.nextval, '" + usuario.getNombre() + "')";
		String queryCiudad = "insert into detalle values( detalle_seq.nextval, '" + ciudad.getNombre() + ", '" + ciudad.getCodigoPostal() + "')";
		
		Connection conn = this.createConnection();
		conn.setAutoCommit(false);
		try (Statement stmt = conn.createStatement(); ) {
			stmt.executeUpdate(queryUsuario);
			stmt.executeUpdate(queryCiudad);
			conn.commit();
		} finally {
			this.closeConnection(conn);
		}
	}
	
	private Connection createConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(db, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	private void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
