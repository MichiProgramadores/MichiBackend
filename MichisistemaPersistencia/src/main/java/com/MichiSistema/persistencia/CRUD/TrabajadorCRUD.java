
package com.MichiSistema.persistencia.CRUD;
import com.MichiSistema.Enum.TipoTrabajador;
import com.MichiSistema.conexion.DBManager;
import com.MichiSistema.dominio.Trabajador;
import com.MichiSistema.persistencia.dao.TrabajadorDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mulatillo Gomez, Ariana Camila
 */


public class TrabajadorCRUD  extends BaseCRUD<Trabajador> implements TrabajadorDAO{;
    private final PersonaCRUD personaCRUD;

    public TrabajadorCRUD() {
        this.personaCRUD = new PersonaCRUD();
    }

    @Override
    protected PreparedStatement getInsertPS(Connection conn, Trabajador trabajador) throws SQLException {
        PreparedStatement ps =conn.prepareStatement("INSERT INTO Trabajador(persona_id, tipoTrabajador) "
                            + "VALUES(?, ?)");
        ps.setInt(1, trabajador.getPersona_id());
        ps.setString(2, trabajador.getTipoTrabajador().name()); // Convertir el enum a String
        return ps;
    }

    @Override
    protected PreparedStatement getUpdatePS(Connection conn, Trabajador trabajador) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("UPDATE Trabajador SET tipoTrabajador=? WHERE persona_id=?");
        ps.setString(1, trabajador.getTipoTrabajador().name()); // Convertir enum a String
        ps.setInt(2, trabajador.getPersona_id());
        return ps;
    }

    @Override
    protected PreparedStatement getDeletePS(Connection conn, Integer id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
            "UPDATE Persona SET estado=0 WHERE persona_id=?"
        );
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement getSelectByIdPS(Connection conn, Integer id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT p.persona_id, p.nombres, p.apellidos, p.celular, p.email, p.estado,  "
                + "t.tipoTrabajador "
                     + "FROM Persona p "
                     + "JOIN Trabajador t ON p.persona_id = t.persona_id "
                     + "WHERE p.persona_id=?");
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement getSelectAllPS(Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT p.persona_id, p.nombres, p.apellidos, p.celular, p.email, t.tipoTrabajador, p.estado "
                 + "FROM Persona p "
                 + "JOIN Trabajador t ON p.persona_id = t.persona_id");
        return ps;
    }

    @Override
    protected Trabajador createFromResultSet(ResultSet rs) throws SQLException {
        Trabajador trabajador = new Trabajador();
        trabajador.setPersona_id(rs.getInt("persona_id"));
        trabajador.setNombres(rs.getString("nombres"));
        trabajador.setApellidos(rs.getString("apellidos"));
        trabajador.setCelular(rs.getInt("celular"));
        trabajador.setEmail(rs.getString("email"));
        trabajador.setTipoTrabajador(TipoTrabajador.valueOf(rs.getString("tipoTrabajador")));  // Convertir String a Enum
        trabajador.setEstado(rs.getBoolean("estado"));
        return trabajador;
    }

    @Override
    protected void setId(Trabajador entity, Integer id) {
        entity.setPersona_id(id);
    }
    
    @Override
    public void insertar(Trabajador trabajador) {
    try (Connection conn = DBManager.getInstance().obtenerConexion()) {
        conn.setAutoCommit(false);
        try {
            personaCRUD.insertar(trabajador);
            try (PreparedStatement ps = getInsertPS(conn, trabajador)) {
                ps.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw new RuntimeException("Error al agregar trabajador", e);
        } finally {
            conn.setAutoCommit(true);
        }
    } catch (SQLException e) {
        throw new RuntimeException("Error de conexión al agregar trabajador", e);
    }
}

    @Override
    public void actualizar(Trabajador trabajador) {
        try (Connection conn = DBManager.getInstance().obtenerConexion()) {
            conn.setAutoCommit(false);
            try {
                // Actualizar en la tabla Persona
                personaCRUD.actualizar(trabajador);

                // Actualizar en la tabla Trabajador
                try (PreparedStatement ps = getUpdatePS(conn, trabajador)) {
                    ps.executeUpdate();
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException("Error al actualizar trabajador", e);
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error de conexión al actualizar trabajador", e);
        }
    }

    @Override
    public List<Trabajador> obtenerActivos() {
        List<Trabajador> entities = new ArrayList<>();

        try (Connection conn = DBManager.getInstance().obtenerConexion();
            
             CallableStatement cs = conn.prepareCall("{CALL sp_obtener_trabajadores_activos()}");
             ResultSet rs = cs.executeQuery()) { 
            while (rs.next()) {
                entities.add(createFromResultSet(rs)); 
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar trabajadores activos", e);
        }
        return entities;
    }

    @Override
    public List<Trabajador> buscarPorNombre(String nombre) {
        List<Trabajador> trabajadores = new ArrayList<>();

        // El procedimiento almacenado que utilizas
        String sql = "{CALL sp_buscar_trabajador_por_nombre(?)}";

        try (Connection conn = DBManager.getInstance().obtenerConexion();
             CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, nombre);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    // Mapear el resultado del conjunto de resultados a objetos Trabajador
                    trabajadores.add(createFromResultSet(rs)); // Asumiendo que tienes un método para crear el objeto
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar trabajadores por nombre", e);
        }

        return trabajadores;
    }

    @Override
    public List<Trabajador> obtenerPorTipoTrabajador(TipoTrabajador tipo) {
        List<Trabajador> trabajadores = new ArrayList<>();

    // Consulta SQL para obtener trabajadores por tipoTrabajador
    String sql = "SELECT p.persona_id, p.nombres, p.apellidos, p.celular, p.email, p.estado, "
               + "t.tipoTrabajador "
               + "FROM Persona p "
               + "JOIN Trabajador t ON p.persona_id = t.persona_id "
               + "WHERE t.tipoTrabajador = ?";

    try (Connection conn = DBManager.getInstance().obtenerConexion()) {
        // Usamos PreparedStatement para evitar inyecciones SQL
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            // Establecemos el valor del tipoTrabajador (convertimos el enum a String)
            ps.setString(1, tipo.toString());

            // Ejecutamos la consulta y obtenemos el resultado
            try (ResultSet rs = ps.executeQuery()) {
                // Iteramos sobre el resultado de la consulta y utilizamos la función createFromResultSet
                while (rs.next()) {
                    Trabajador trabajador = createFromResultSet(rs);  // Usamos la función createFromResultSet para mapear los datos
                    trabajadores.add(trabajador);
                }
            }
        }
    } catch (SQLException e) {
        // En caso de error, se lanza una RuntimeException
        throw new RuntimeException("Error al obtener trabajadores por tipo: " + tipo, e);
    }

    // Retornamos la lista de trabajadores encontrados
    return trabajadores;
    }
    
    @Override
    public List<Trabajador> obtenerPorTipoYEstado(TipoTrabajador tipo, String estado) {
        List<Trabajador> trabajadores = new ArrayList<>();

        // Consulta base
        String sql = "SELECT p.persona_id, p.nombres, p.apellidos, p.celular, p.email, p.estado, "
                   + "t.tipoTrabajador "
                   + "FROM Persona p "
                   + "JOIN Trabajador t ON p.persona_id = t.persona_id "
                   + "WHERE 1=1"; // Esto nos permite agregar condiciones dinámicamente con AND

        // Si tipoTrabajador no es nulo, añadir filtro
        if (tipo != null) {
            sql += " AND t.tipoTrabajador = ?";
        }

        // Convertir estado a un valor adecuado y agregar el filtro correspondiente
        if (estado != null) {
            if ("CUALQUIERA".equals(estado)) {
                // Si es "CUALQUIERA", no aplicar filtro de estado
            } else if ("INACTIVO".equals(estado)) {
                sql += " AND p.estado = 0";
            } else if ("ACTIVO".equals(estado)) {
                sql += " AND p.estado = 1";
            }
        }

        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int paramIndex = 1;

            // Si tipoTrabajador no es nulo, establecer el valor del parámetro
            if (tipo != null) {
                ps.setString(paramIndex++, tipo.toString());
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Trabajador trabajador = createFromResultSet(rs);
                    trabajadores.add(trabajador);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener trabajadores con filtros tipo/estado: " + e.getMessage(), e);
        }

        return trabajadores;
    }
    
    @Override
    public List<Trabajador> obtenerPorEstado(String estado) {
        List<Trabajador> trabajadores = new ArrayList<>();

        // Consulta base
        String sql = "SELECT p.persona_id, p.nombres, p.apellidos, p.celular, p.email, p.estado, "
                   + "t.tipoTrabajador "
                   + "FROM Persona p "
                   + "JOIN Trabajador t ON p.persona_id = t.persona_id "
                   + "WHERE 1=1";  // Esto nos permite agregar condiciones dinámicamente con AND

        // Convertir el estado a un valor adecuado y agregar el filtro correspondiente
        if (estado != null) {
            if ("CUALQUIERA".equals(estado)) {
                // Si es "CUALQUIERA", no aplicar filtro de estado
            } else if ("INACTIVO".equals(estado)) {
                sql += " AND p.estado = 0";  // Filtro para estado inactivo (0)
            } else if ("ACTIVO".equals(estado)) {
                sql += " AND p.estado = 1";  // Filtro para estado activo (1)
            }
        }

        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Ejecutar la consulta y obtener el resultado
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Trabajador trabajador = createFromResultSet(rs);
                    trabajadores.add(trabajador);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener trabajadores por estado: " + e.getMessage(), e);
        }

        return trabajadores;
    }
    

}
