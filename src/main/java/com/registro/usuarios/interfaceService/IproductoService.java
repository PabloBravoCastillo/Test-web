package com.registro.usuarios.interfaceService;

import org.springframework.data.jpa.repository.JpaRepository;

import com.registro.usuarios.modelo.Producto;

public interface IproductoService extends JpaRepository<Producto, Integer> {

}
