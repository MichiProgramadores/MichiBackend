
package com.MichiSistema.persistencia.dao;
import com.MichiSistema.dominio.Trabajador;
import java.util.List;


/**
 *
 * @author rober
 */
public interface TrabajadorDAO extends BaseDAO<Trabajador>{
    List<Trabajador> obtenerActivos();
    List<Trabajador> buscarPorNombre(String nombre);
}
