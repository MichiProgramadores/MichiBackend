
package pe.edu.pucp.MichiSistema.persistencia.DAO;

/**
 *
 * @author Ariana Mulatillo Gomez
 */
import java.util.List;

public interface BaseDAO <T> {
    void insertar(T modelo);
    void actualizar(T modelo);
    T obtenerPorId(Integer id);
    List<T> obtenerTodos();
    void eliminar(Integer id);
    
}
