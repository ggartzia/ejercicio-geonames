package com.sherpa.ejercicio.domain;

public class Ciudad {

	private String codigoPostal;
	private String nombre;
	
	public Ciudad () {}
	
	public Ciudad (String codigoPostal, String nombre){
		this.codigoPostal = codigoPostal;	
		this.nombre = nombre;
	}
	
	public String getCodigoPostal() {
		return codigoPostal;
	}
	
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Ciudad [codigoPostal=" + this.codigoPostal + ", ciudad=" + this.nombre + "]";
	}	
}