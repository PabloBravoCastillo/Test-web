package com.registro.usuarios.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.registro.usuarios.interfaceService.IproductoService;
import com.registro.usuarios.modelo.Producto;

@Controller
@RequestMapping("/")
public class RegistroControlador {

	@Autowired
    private IproductoService service;

	@GetMapping("/")
	public String listar(Model model) {
		model.addAttribute("productos", service.findAll());
		return "index";
	}
	
	@GetMapping("/detalles/{id_producto}")
	public String buscarProductosPorId(@PathVariable("id_producto") int id_producto, Model model) {
		Producto p = service.getOne(id_producto);
		model.addAttribute("producto", p);
		return "detalles";
	}
	
	@GetMapping("/login")
	public String iniciarSesion() {
		return "login";
	}
	
}
