package com.registro.usuarios.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.registro.usuarios.interfaceService.IproductoService;
import com.registro.usuarios.modelo.Producto;

public class ProductoServicio {
	
	@Autowired
	private IproductoService service;
	
	public List<Producto> buscador(String busqueda){
		return service.buscador(busqueda);	
	}
	
	public List<Producto> mostrarVideojuegos(String videojuegos){
		return service.mostrarVideojuegos(videojuegos);	
	}
	
	public List<Producto> mostrarAccesorios(String accesorios){
		return service.mostrarAccesorios(accesorios);	
	}
	
	public List<Producto> mostrarElectronica(String electronica){
		return service.mostrarElectronica(electronica);	
	}
	
}
