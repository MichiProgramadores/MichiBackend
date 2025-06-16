package com.MichiSistema.persistencia.CRUD;


import com.MichiSistema.conexion.DBManager;
import com.MichiSistema.dominio.Trabajador;
import com.MichiSistema.dominio.Usuario;
import com.MichiSistema.persistencia.dao.UsuarioDAO;
import java.security.MessageDigest;
import java.sql.*;
import java.util.List;
import java.sql.*;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class UsuarioCRUD extends BaseCRUD<Usuario> implements UsuarioDAO {
    private final TrabajadorCRUD trabajador;
    private Trabajador trabaj;
    private String llave = "123456789012345678901234";
    public UsuarioCRUD() {
        this.trabajador = new TrabajadorCRUD();
        this.trabaj= new Trabajador();
    }
    @Override
    protected PreparedStatement getInsertPS(Connection conn, Usuario usuario) throws SQLException {
        trabaj=trabajador.obtenerPorId(usuario.getId());
        String user=trabaj.getNombres()+trabaj.getApellidos();
        usuario.setNombreUsuario(user);
        String contra=cifrar(usuario.getContrasena(), llave);
        usuario.setContrasena(contra);
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
    public Usuario autenticar(String user, String contraseña) throws SQLException {
        // Realizamos la consulta para verificar si el usuario existe y la contraseña es correcta
        String query = "SELECT persona_id, contrasenha, nombreUsuario FROM Usuario WHERE persona_id=?";
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(query)) {

            //ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Comparamos la contraseña almacenada con la que ingresó el usuario
                String contrasenaAlmacenada = rs.getString("contrasenha");
                String desencriptada= descifrar(contrasenaAlmacenada, llave);
                if (contrasenaAlmacenada.equals(desencriptada)) {
                    // Si la contraseña es correcta, retornamos el usuario
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("persona_id"));
                    usuario.setNombreUsuario(rs.getString("nombreUsuario"));
                   // usuario.setContrasena(contrasenaAlmacenada);  // Retornamos el usuario con la contraseña (aunque no es recomendable mostrarla)
                    return usuario;
                } else {
                    throw new SQLException("Contraseña incorrecta");
                }
            } else {
                throw new SQLException("Usuario no encontrado");
            }
        }
    }

    @Override
    public String cifrar(String texto, String llave) {
         try {
            // Aseguramos que la clave tenga 24 bytes
            SecretKey key = new SecretKeySpec(llave.getBytes("UTF-8"), "DESede");
            Cipher cipher = Cipher.getInstance("DESede");

            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] plainTextBytes = texto.getBytes("UTF-8");
            byte[] encryptedBytes = cipher.doFinal(plainTextBytes);

            // Retorna la cadena encriptada en formato Base64
            return Base64.getEncoder().encodeToString(encryptedBytes);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public String descifrar(String texto, String llave) {
        try {
            // Aseguramos que la clave tenga 24 bytes
            SecretKey key = new SecretKeySpec(llave.getBytes("UTF-8"), "DESede");
            Cipher cipher = Cipher.getInstance("DESede");

            cipher.init(Cipher.DECRYPT_MODE, key);

            // Decodificar el texto Base64 para obtener los bytes encriptados
            byte[] encryptedBytes = Base64.getDecoder().decode(texto);

            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            // Retorna el texto desencriptado
            return new String(decryptedBytes, "UTF-8");

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
