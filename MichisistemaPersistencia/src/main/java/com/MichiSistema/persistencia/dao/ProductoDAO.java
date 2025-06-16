
package com.MichiSistema.persistencia.dao;

import com.MichiSistema.Enum.TipoProducto;
import com.MichiSistema.dominio.Producto;
import java.util.List;

/**
 *
 * @author rober
 */
public interface ProductoDAO extends BaseDAO<Producto>{
    List<Producto> obtenerPorTipoProducto(TipoProducto tipo);
    List<Producto> obtenerActivos();
    List<Producto> buscarPorNombre(String nombre);
}
