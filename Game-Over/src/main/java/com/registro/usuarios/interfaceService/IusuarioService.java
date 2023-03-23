package com.registro.usuarios.interfaceService;

import com.registro.usuarios.modelo.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

import javax.transaction.Transactional;

public interface IusuarioService extends JpaRepository<Usuario, Long> {

    @Query(value="SELECT * FROM Cliente WHERE email = :emailLogueado", nativeQuery=true)
    List<Usuario> mostrarPerfiles(@Param("emailLogueado") String emailLogueado);
    
    @Modifying
    @Transactional
    @Query(value="UPDATE `cliente` SET `nombre` = :nombre, `apellido` = :apellido, `direccion` = :direccion, `dni` = :dni WHERE `cliente`.`id_cliente` = :id", nativeQuery=true)
    int actualizarPerfil(@Param("id") long id, @Param("nombre") String nombre, @Param("apellido") String apellido, @Param("direccion") String direccion, @Param("dni") String dni);
    
}
