package pe.edu.pucp.MichiSistema.persistencia.DAOimpl;


import pe.edu.pucp.MichiSistema.conexion.DBManager;
import pe.edu.pucp.MichiSistema.dominio.Usuario;
import pe.edu.pucp.MichiSistema.persistencia.DAO.UsuarioDAO;
import java.sql.*;
import java.util.List;
import java.sql.*;

public class UsuarioCRUD extends BaseCRUD<Usuario> implements UsuarioDAO {
    
    @Override
    protected PreparedStatement getInsertPS(Connection conn, Usuario usuario) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
            "INSERT INTO Usuario(persona_id, contrasenha, nombreUsuario) VALUES(?, ?, ?)",
            Statement.RETURN_GENERATED_KEYS
        );
        ps.setInt(1, usuario.getId());  // persona_id es el ID del trabajador, que es el mismo que el ID de Usuario
        ps.setString(2, usuario.getContrasena());
        ps.setString(3, usuario.getNombreUsuario());
        return ps;
    }

    @Override
    protected PreparedStatement getUpdatePS(Connection conn, Usuario usuario) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
            "UPDATE Usuario SET contrasenha=?, nombreUsuario=? WHERE persona_id=?"
        );
        ps.setString(1, usuario.getContrasena());
        ps.setString(2, usuario.getNombreUsuario());
        ps.setInt(3, usuario.getId());  // persona_id como referencia
        return ps;
    }
    @Override
    protected PreparedStatement getDeletePS(Connection conn, Integer id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
            "DELETE from Usuario WHERE persona_id=?"
        );
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement getSelectByIdPS(Connection conn, Integer id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
            "SELECT p.persona_id, p.nombres, p.apellidos, p.celular, p.email, "
            + "u.contrasenha, u.nombreUsuario "
            + "FROM Persona p "
            + "JOIN Usuario u ON p.persona_id = u.persona_id "
            + "WHERE p.persona_id=?"
        );
        ps.setInt(1, id);  // Usamos persona_id como referencia
        return ps;
    }

    @Override
    protected PreparedStatement getSelectAllPS(Connection conn) throws SQLException {
        return conn.prepareStatement(
            "SELECT persona_id, contrasenha, nombreUsuario FROM Usuario"
        );
    }

    @Override
    protected void setId(Usuario entity, Integer id) {
        entity.setId(id);
    }

    @Override
    protected Usuario createFromResultSet(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("persona_id"));
        usuario.setContrasena(rs.getString("contrasenha"));
        usuario.setNombreUsuario(rs.getString("nombreUsuario"));
        return usuario;
    }


    @Override
    public Usuario autenticar(int id, String contraseña) throws SQLException {
        // Realizamos la consulta para verificar si el usuario existe y la contraseña es correcta
        String query = "SELECT persona_id, contrasenha, nombreUsuario FROM Usuario WHERE persona_id=?";
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Comparamos la contraseña almacenada con la que ingresó el usuario
                String contrasenaAlmacenada = rs.getString("contrasenha");
                if (contrasenaAlmacenada.equals(contraseña)) {
                    // Si la contraseña es correcta, retornamos el usuario
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("persona_id"));
                    usuario.setNombreUsuario(rs.getString("nombreUsuario"));
                    usuario.setContrasena(contrasenaAlmacenada);  // Retornamos el usuario con la contraseña (aunque no es recomendable mostrarla)
                    return usuario;
                } else {
                    throw new SQLException("Contraseña incorrecta");
                }
            } else {
                throw new SQLException("Usuario no encontrado");
            }
        }
    }
}
