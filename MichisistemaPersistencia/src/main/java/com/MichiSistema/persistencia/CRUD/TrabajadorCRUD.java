
package com.MichiSistema.persistencia.CRUD;
import com.MichiSistema.Enum.TipoTrabajador;
import com.MichiSistema.conexion.DBManager;
import com.MichiSistema.dominio.Trabajador;
import com.MichiSistema.persistencia.dao.TrabajadorDAO;
import java.sql.*;
import java.util.ArrayList;

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
        PreparedStatement ps = conn.prepareStatement("SELECT p.persona_id, p.nombres, p.apellidos, p.celular, p.email, "
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
    

}
