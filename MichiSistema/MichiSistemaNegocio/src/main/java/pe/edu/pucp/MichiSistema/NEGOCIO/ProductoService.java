
package pe.edu.pucp.MichiSistema.NEGOCIO;

import pe.edu.pucp.MichiSistema.dominio.Producto;
import java.util.ArrayList;

/**
 *
 * @author rober
 */

public interface ProductoService {
    void registrarProducto(Producto producto) throws Exception;
    void actualizarProducto(Producto producto) throws Exception;
    void eliminarProducto(int idProducto) throws Exception;
    Producto obtenerProducto(int idProducto) throws Exception;
    ArrayList<Producto> listarProductos() throws Exception;
}
