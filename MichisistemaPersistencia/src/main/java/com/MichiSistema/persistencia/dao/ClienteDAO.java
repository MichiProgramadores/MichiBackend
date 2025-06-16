
package com.MichiSistema.persistencia.dao;
import com.MichiSistema.dominio.Cliente;
import java.util.List;

/**
 *
 * @author rober
 */
public interface ClienteDAO extends BaseDAO<Cliente>{
    List<Cliente> obtenerActivos();
    List<Cliente> buscarPorNombre(String Nombre);
}
