

package pe.edu.pucp.MichiSistema.NEGOCIOImpl;
import pe.edu.pucp.MichiSistema.dominio.Usuario;
import java.util.ArrayList;
import pe.edu.pucp.MichiSistema.NEGOCIO.UsuarioService;
import pe.edu.pucp.MichiSistema.persistencia.DAOimpl.UsuarioCRUD;
import pe.edu.pucp.MichiSistema.persistencia.DAO.UsuarioDAO;


/**
 *
 * @author Ariana Mulatillo Gomez
 */

import java.sql.SQLException;

public class UsuarioServiceImpl implements UsuarioService {
    
    private final UsuarioDAO usuarioDAO;
    
    public UsuarioServiceImpl() {
        this.usuarioDAO = new UsuarioCRUD(); // Asumiendo que el CRUD es el que interactúa con la base de datos
    }

    @Override
    public void registrarUsuario(Usuario usuario) throws Exception {
        // Validaciones de negocio
        if (usuario.getNombreUsuario() == null || usuario.getNombreUsuario().trim().isEmpty()) {
            throw new Exception("El nombre de usuario no puede estar vacío");
        }
        if (usuario.getContrasena() == null || usuario.getContrasena().trim().isEmpty()) {
            throw new Exception("La contraseña no puede estar vacía");
        }

        // Insertar en la base de datos
        usuarioDAO.insertar(usuario);
    }
    
    @Override
    public void actualizarUsuario(Usuario usuario) throws Exception {
        
        // Validar que el usuario exista
        if (usuarioDAO.obtenerPorId(usuario.getId()) == null) {
            throw new Exception("El usuario no existe");
        }
        
        // Validaciones de negocio
        if (usuario.getNombreUsuario() == null || usuario.getNombreUsuario().trim().isEmpty()) {
            throw new Exception("El nombre de usuario no puede estar vacío");
        }
        if (usuario.getContrasena() == null || usuario.getContrasena().trim().isEmpty()) {
            throw new Exception("La contraseña no puede estar vacía");
        }

        // Actualizar en la base de datos
        usuarioDAO.actualizar(usuario);
    }
    
    @Override
    public void eliminarUsuario(int idUsuario) throws Exception {
        Usuario usuario = usuarioDAO.obtenerPorId(idUsuario);
        
        if (usuario == null) {
            throw new Exception("El usuario no existe");
        }

        usuarioDAO.eliminar(idUsuario);
    }
    
    @Override
    public Usuario obtenerUsuario(int idUsuario) throws Exception {
        Usuario usuario = usuarioDAO.obtenerPorId(idUsuario);
        
        if (usuario == null) {
            throw new Exception("Usuario no encontrado");
        }     
        return usuario;
    }
    
    @Override
    public Usuario autenticar(int id, String contraseña) throws SQLException {
        return usuarioDAO.autenticar(id, contraseña); // Llamamos al método del CRUD para autenticar
    }
    
    @Override
    public ArrayList<Usuario> listarUsuarios() throws Exception {
        return (ArrayList<Usuario>) usuarioDAO.obtenerTodos();
    }
}
