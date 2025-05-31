
package pe.edu.pucp.MichiSistema.persistencia.DAO;
import pe.edu.pucp.MichiSistema.dominio.Usuario;
import java.sql.SQLException;


/**
 *
 * @author Usuario
 */
public interface UsuarioDAO extends BaseDAO<Usuario>{
    public Usuario autenticar(int id, String contraseña) throws SQLException;
}
