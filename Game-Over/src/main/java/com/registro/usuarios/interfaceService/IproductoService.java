package com.registro.usuarios.interfaceService;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.registro.usuarios.modelo.Producto;

public interface IproductoService extends JpaRepository<Producto, Integer> {
	
	@Query(value="SELECT * FROM Producto p WHERE p.nombre LIKE %:busqueda%", nativeQuery=true)
	List<Producto> buscador(@Param("busqueda") String busqueda);
	
	@Query(value="SELECT * FROM Producto p WHERE p.categoria_id_categoria = 1", nativeQuery=true)
	List<Producto> mostrarVideojuegos(@Param("videojuegos") String videojuegos);
	
	@Query(value="SELECT * FROM Producto p WHERE p.categoria_id_categoria = 2", nativeQuery=true)
	List<Producto> mostrarAccesorios(@Param("accesorios") String accesorios);
	
	@Query(value="SELECT * FROM Producto p WHERE p.categoria_id_categoria = 3", nativeQuery=true)
	List<Producto> mostrarElectronica(@Param("electronica") String electronica);
	
    @Modifying
    @Transactional
    @Query(value="UPDATE `producto` SET `nombre` = :nombre, `descripcion` = :descripcion, `precio` = :precio, `stock` = :stock "
    		+ ", `categoria_id_categoria` = :id_categoria, `plataforma_id_plataforma` = :id_plataforma "
    		+ "WHERE `producto`.`id_producto` = :id", nativeQuery=true)
    void guardarProducto(@Param("id") int id_producto, @Param("nombre") String nombre, @Param("descripcion") String descripcion, 
    		@Param("precio") double precio, @Param("stock") int stock
    		, @Param("id_categoria") int id_categoria, @Param("id_plataforma") int id_plataforma
    		);

}
