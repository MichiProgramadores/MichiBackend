
package com.MichiSistema.persistencia.dao;
import com.MichiSistema.dominio.Usuario;
import java.sql.SQLException;


/**
 *
 * @author Usuario
 */
public interface UsuarioDAO extends BaseDAO<Usuario>{
    public Usuario autenticar(String user, String contraseña) throws SQLException;
    public String cifrar(String texto, String llave);
    public String descifrar(String texto, String llave);
}
