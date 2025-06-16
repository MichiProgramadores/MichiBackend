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
    public int autenticar(String user, String contrase) throws SQLException {
    String sqlVerificarUsuario = "{CALL sp_verificar_existencia_usuario_por_nombre(?, ?)}";  // Usamos el parámetro OUT
    
    try (Connection conn = DBManager.getInstance().obtenerConexion();
         CallableStatement cs = conn.prepareCall(sqlVerificarUsuario)) {
        // Establecemos el parámetro de entrada (nombre de usuario)
        cs.setString(1, user);
        // Registramos el parámetro de salida (persona_id)
        cs.registerOutParameter(2, java.sql.Types.INTEGER);  // Es un OUT parameter de tipo INTEGER
        // Ejecutamos el procedimiento
        cs.execute();
        // Obtenemos el valor del parámetro de salida
        int personaId = cs.getInt(2);  // Usamos el índice 2 para obtener el parámetro de salida
        //System.out.println(personaId);
        // Verificamos si el ID del usuario es válido
        if (personaId > 0) {
            // Ahora que tenemos el persona_id, consultamos la contraseña
            String sqlObtenerContrasena = "SELECT contrasenha FROM Usuario WHERE persona_id=?";
            try (PreparedStatement ps = conn.prepareStatement(sqlObtenerContrasena)) {
                ps.setInt(1, personaId);
                
                // Ejecutamos la consulta para obtener la contraseña almacenada
                try (ResultSet rsContrasena = ps.executeQuery()) {
                    if (rsContrasena.next()) {
                        String contrasenaAlmacenada = rsContrasena.getString("contrasenha");
                        String desencriptada = descifrar(contrasenaAlmacenada, llave);
                       //System.out.println(desencriptada);
                       //System.out.println(contrase);
                        // Comparar la contraseña almacenada con la proporcionada
                        if (desencriptada.equals(contrase)) {
                            // Si la contraseña es correcta, devolvemos el usuario
                            return personaId;
                        } else {
                            return 0;
                        }
                    } else {
                        return 0;
                    }
                }
            }
        } else {
            return 0;
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
