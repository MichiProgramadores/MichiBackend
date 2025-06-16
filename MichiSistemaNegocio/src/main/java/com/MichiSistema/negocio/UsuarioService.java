
package com.MichiSistema.negocio;
import com.MichiSistema.dominio.Usuario;
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
    public Usuario autenticar(String user, String contraseña) throws SQLException ;
}

