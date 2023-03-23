package com.registro.usuarios.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "producto", uniqueConstraints = @UniqueConstraint(columnNames = "id_Producto"))
public class Producto implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_Producto;
	
	private String Nombre;
	private String Foto;
	private String Descripcion;
	private double Precio;
	private int Stock;
	
	
	public Producto() {
		super();
	}


	public Producto(int id_Producto, String nombre, String foto, String descripcion, double precio, int stock) {
		super();
		this.id_Producto = id_Producto;
		this.Nombre = nombre;
		this.Foto = foto;
		this.Descripcion = descripcion;
		this.Precio = precio;
		this.Stock = stock;
	}


	public int getIdProducto() {
		return id_Producto;
	}


	public void setIdProducto(int id_Producto) {
		this.id_Producto = id_Producto;
	}


	public String getNombre() {
		return Nombre;
	}


	public void setNombre(String nombre) {
		Nombre = nombre;
	}


	public String getFoto() {
		return Foto;
	}


	public void setFoto(String foto) {
		Foto = foto;
	}


	public String getDescripcion() {
		return Descripcion;
	}


	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}


	public double getPrecio() {
		return Precio;
	}


	public void setPrecio(double precio) {
		Precio = precio;
	}


	public int getStock() {
		return Stock;
	}


	public void setStock(int stock) {
		Stock = stock;
	}
	
	private static final long serialVersionUID = 1L;
	
}
