package com.sherpa.ejercicio.domain;

public class Usuario {
	
	private String nombre;

	public Usuario () {}
	
	public Usuario (String nombre) {
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + "]";
	}

}
