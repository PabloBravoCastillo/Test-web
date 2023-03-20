package com.registro.usuarios.controlador;

import com.registro.usuarios.interfaceService.IusuarioService;
import com.registro.usuarios.interfaceService.IvaloracionService;
import com.registro.usuarios.modelo.Usuario;
//import com.registro.usuarios.repositorio.UsuarioRepositorio;
import com.registro.usuarios.servicio.ProductoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.registro.usuarios.dto.UsuarioRegistroDTO;
import com.registro.usuarios.interfaceService.IcategoriaService;
import com.registro.usuarios.interfaceService.IproductoService;
import com.registro.usuarios.modelo.Categoria;
import com.registro.usuarios.modelo.DetalleOrden;
import com.registro.usuarios.modelo.Orden;
import com.registro.usuarios.modelo.Plataforma;
import com.registro.usuarios.modelo.Producto;

@Controller
@RequestMapping("/")
public class RegistroControlador {

	//private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
    private IproductoService service;
	
	@Autowired
	private IcategoriaService categoriaServicio;
	
	@Autowired
	private IusuarioService servicio;

	@Autowired
	private IvaloracionService comentarioServicio;
	
	@Autowired
	private ProductoService productoService;
	
	
	//Para Almacenar detalles de la orden 
		List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();
		
		//Alamcena datos de la orden6
		Orden orden = new Orden();
		
		
		
	
	@GetMapping("/")
	public String listar(Model model, String busqueda) {
		if (busqueda != null) {
			model.addAttribute("productos", service.buscador(busqueda));
			return "busqueda";
		} else {
			model.addAttribute("productos", service.findAll());
			return "index";
		}
	}

	@GetMapping("/perfil")
	public String entrarPerfil(Model model, String perfiles) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		UserDetails userDetail = (UserDetails) auth.getPrincipal();

		String emailLogueado = userDetail.getUsername();

		model.addAttribute("clientes", servicio.mostrarPerfiles(emailLogueado));
		return "perfil";
	}

	@GetMapping("/detalles/{id_producto}")
	public String buscarProductosPorId(@PathVariable("id_producto") int id_producto, Model model, Model model2, String comentarios) {
		Producto p = service.getById(id_producto);
		model.addAttribute("producto", p);


		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		UserDetails userDetail = (UserDetails) auth.getPrincipal();

		String emailLogueado = userDetail.getUsername();

		model2.addAttribute("comentarios", comentarioServicio.findAll());
		return "detalles";

	}

	@GetMapping("/login")
	public String iniciarSesion() {

		return "login";
	}

	@GetMapping("/modificarPerfil/{id}")
	public String modificarPerfil(@PathVariable Long id, Model model) {
		Optional<Usuario>usuario = servicio.findById(id);
		model.addAttribute("usuario", usuario);
		return "modificarPerfil";
	}

	/*------para guardar el comentario cuando esté modificada la base de datos

	@PostMapping("/comentario")
	public String enviarComment(int estrellas, String texto, Long id, Model model) {
		comentarioServicio.enviarComment(id, estrellas, texto);
		return "detalles";
	}

	*/
	
	@PostMapping("/saves")
	public String save(String nombre, String apellido, String direccion, String dni, Long id, Model model) {
		servicio.actualizarPerfil(id, nombre, apellido, direccion, dni);
		return "redirect:/perfil";
	}
	
	@GetMapping("/videojuegos")
  	public String listarVideojuegos(Model model, String videojuegos) {
		model.addAttribute("productos", service.mostrarVideojuegos(videojuegos));
		return "categoria";
	}
	
	@GetMapping("/accesorios")
  	public String listarAccesorios(Model model, String accesorios) {
		model.addAttribute("productos", service.mostrarAccesorios(accesorios));
		return "categoria";
	}
	
	@GetMapping("/electronica")
  	public String listarElectronica(Model model, String electronica) {
		model.addAttribute("productos", service.mostrarElectronica(electronica));
		return "categoria";
	}
	
	
	
	@GetMapping("/adminProductos")
	public String adminProducto(Model model) {
		model.addAttribute("productos", service.findAll());
		return "adminProductos";
	}
	
	@GetMapping("/modificarProducto/{id_producto}")
	public String modificarProducto(@PathVariable("id_producto") int id_producto, Model model) {
		Producto p = service.getById(id_producto);
		model.addAttribute("producto", p);
		return "modificarProducto";
	}
	

	@PostMapping("/actualizar")
	public String actualizarProducto(Producto producto) {
		Categoria categoria = producto.getCategoria();
		Plataforma plataforma = producto.getPlataforma();
		service.actualizarProducto(producto.getId_producto(), producto.getNombre(), producto.getDescripcion(), producto.getPrecio(), producto.getStock(),
				categoria.getId_categoria(), plataforma.getId_plataforma());
		return "redirect:/adminProductos";
	}
	
	@GetMapping("/adminProductos/{id_producto}")
	public String eliminarProducto(@PathVariable int id_producto) {
		service.deleteById(id_producto);
		return "redirect:/adminProductos";
	}
	
	@GetMapping("/agregarProducto")
	public String agregarProducto(Model modelo){
		Producto producto = new Producto();
		modelo.addAttribute("producto", producto);
		return "agregarProducto";
	}
	
	@PostMapping("/guardar")
	public String guardarProducto(Producto producto) {
		service.save(producto);
		return "redirect:/adminProductos";
	}
	
	
	//añadir al carrito con un solo articulo
	@PostMapping("/añadirCarrito")
	public String añadirCarro(@RequestParam Integer id,  Model model) {
		DetalleOrden detalleOrden = new DetalleOrden();
		Producto producto = new Producto();
		double cantidad=1;
		double sumaTotal = 0;
		double sumaTotalProductos=0;
		
		Optional<Producto> optionalProducto= productoService.get(id);
		producto=optionalProducto.get();
		detalleOrden.setNombre(producto.getNombre());
		detalleOrden.setCantidad(cantidad);
		detalleOrden.setTotal((producto.getPrecio())*cantidad);
		detalleOrden.setPrecio(producto.getPrecio());
		
		
		detalleOrden.setProducto(producto);
		detalleOrden.setFoto(producto.getFoto());
		
		
		detalles.add(detalleOrden);
		
		sumaTotalProductos = detalles.stream().mapToDouble(dt -> dt.getCantidad()).sum();
		sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();
		orden.setCantidad(sumaTotalProductos);
		orden.setTotal(sumaTotal);
		model.addAttribute("carrito", detalles);
		model.addAttribute("orden", orden);
		System.out.println(detalles);
		System.out.println(orden);
		return "carrito";
	}

	// quitar un producto del carrito
	@GetMapping("/delete/cart/{id}")
	public String deleteProductoCart(@PathVariable Integer id, Model model) {

		// lista nueva de prodcutos
		List<DetalleOrden> ordenesNueva = new ArrayList<DetalleOrden>();
		
		for (DetalleOrden detalleOrden : detalles) {
			if (detalleOrden.getProducto().getId_producto() != id) {
				System.out.println(detalleOrden.getProducto().getId_producto());
				ordenesNueva.add(detalleOrden);
			}
		}

		// poner la nueva lista con los productos restantes
		detalles = ordenesNueva;
		
		double sumaTotal = 0;
		double sumaTotalProductos=0;
		sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();
		sumaTotalProductos = detalles.stream().mapToDouble(dt -> dt.getCantidad()).sum();
		orden.setCantidad(sumaTotalProductos);
		orden.setTotal(sumaTotal);
		System.out.println(detalles);
		model.addAttribute("carrito", detalles);
		model.addAttribute("orden", orden);

		return "redirect:/carrito";
	}
	
	@GetMapping("/carrito")
	public String getCart(Model model) {
		
		model.addAttribute("carrito", detalles);
		model.addAttribute("orden", orden);
		
		return "carrito";
	}
	
}
