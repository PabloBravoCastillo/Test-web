package com.registro.usuarios.interfaceService;

import com.registro.usuarios.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import com.registro.usuarios.modelo.Valoracion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IvaloracionService extends JpaRepository<Valoracion, Integer> {

    /* hay que hacer:
        -que sea igual al id_producto para que aparezcan las valoraciones del producto correspondiente (por ahora no necesario, así se ven valoraciones en todos los productos)
        -que sea igual al id_cliente para que se vea el nombre de quien lo ha puesto (solo puedo acceder al mail) */
    @Query(value="SELECT * FROM Valoracion v", nativeQuery=true)
    List<Valoracion> listarComentarios(@Param("comentarios") String comentarios);

    /*
    * SELECT valoracion.comentario, valoracion.puntuacion, cliente.email
    * FROM valoracion JOIN cliente ON valoracion.cliente_id_cliente=cliente.id_cliente
    * WHERE cliente.email = :emailLogueado;
    */


}
