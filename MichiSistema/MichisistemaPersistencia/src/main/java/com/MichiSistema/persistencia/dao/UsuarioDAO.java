
package com.MichiSistema.persistencia.dao;
import com.MichiSistema.dominio.Usuario;
import java.sql.SQLException;


/**
 *
 * @author Usuario
 */
public interface UsuarioDAO extends BaseDAO<Usuario>{
    public Usuario autenticar(int id, String contraseña) throws SQLException;
}
