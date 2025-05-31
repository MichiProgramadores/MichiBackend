
package pe.edu.pucp.MichiSistema.NEGOCIO;
import pe.edu.pucp.MichiSistema.dominio.Usuario;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Ariana Mulatillo Gomez
 */

public interface UsuarioService {
    void registrarUsuario(Usuario usuario) throws Exception;
    void actualizarUsuario(Usuario usuario) throws Exception;
    void eliminarUsuario(int idUsuario) throws Exception;
    Usuario obtenerUsuario(int idUsuario) throws Exception;
    ArrayList<Usuario> listarUsuarios() throws Exception;
    public Usuario autenticar(int id, String contraseña) throws SQLException ;
}

