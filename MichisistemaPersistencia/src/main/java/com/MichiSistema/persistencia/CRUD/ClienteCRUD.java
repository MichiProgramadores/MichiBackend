
package com.MichiSistema.persistencia.CRUD;

import com.MichiSistema.Enum.TipoCliente;
import com.MichiSistema.conexion.DBManager;
import com.MichiSistema.dominio.Cliente;
import com.MichiSistema.dominio.Persona;
import com.MichiSistema.persistencia.dao.ClienteDAO;
import com.MichiSistema.persistencia.dao.PersonaDAO;
import java.sql.*;
import java.util.ArrayList;


public class ClienteCRUD extends BaseCRUD<Cliente> implements ClienteDAO {
    private final PersonaCRUD personaCRUD;

    public ClienteCRUD() {
        this.personaCRUD = new PersonaCRUD();
    }
    @Override
    protected PreparedStatement getInsertPS(Connection conn, Cliente cliente) throws SQLException {
        
        PreparedStatement ps = conn.prepareStatement(
            "INSERT INTO Cliente(persona_id, tipoCliente, puntuacion, numeroTipoCliente) VALUES(?, ?, ?, ?)" 
        );
        ps.setInt(1, cliente.getPersona_id());
        ps.setString(2, cliente.getTipoCliente().name());
        ps.setInt(3, cliente.getPuntuacion());
        ps.setString(4, cliente.getNumeroTipoCliente());  
        return ps;
    }

    @Override
    protected PreparedStatement getUpdatePS(Connection conn, Cliente cliente) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
            "UPDATE Cliente SET tipoCliente=?, puntuacion=?, numeroTipoCliente=? WHERE persona_id=?"
        );
        ps.setString(1, cliente.getTipoCliente().name());
        ps.setInt(2, cliente.getPuntuacion());
        ps.setString(3, cliente.getNumeroTipoCliente());
        ps.setInt(4, cliente.getPersona_id());  // Usamos persona_id como referencia para actualizar
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
        PreparedStatement ps = conn.prepareStatement(
            "SELECT p.persona_id, p.nombres, p.apellidos, p.celular, p.email, "
            + "c.tipoCliente, c.puntuacion, c.numeroTipoCliente "
            + "FROM Persona p "
            + "JOIN Cliente c ON p.persona_id = c.persona_id "
            + "WHERE p.persona_id=?"
        );
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement getSelectAllPS(Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
            "SELECT p.persona_id, p.nombres, p.apellidos, p.celular, p.email, "
            + "c.tipoCliente, c.puntuacion, c.numeroTipoCliente "
            + "FROM Persona p "
            + "JOIN Cliente c ON p.persona_id = c.persona_id"
        );
        return ps;
    }

    @Override
    protected void setId(Cliente entity, Integer id) {
        entity.setPersona_id(id);
    }

    @Override
    protected Cliente createFromResultSet(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setPersona_id(rs.getInt("persona_id"));
        cliente.setNombres(rs.getString("nombres"));
        cliente.setApellidos(rs.getString("apellidos"));
        cliente.setCelular(rs.getInt("celular"));
        cliente.setEmail(rs.getString("email"));
        cliente.setTipoCliente(TipoCliente.valueOf(rs.getString("tipoCliente")));  // Convertir String a Enum
        cliente.setPuntuacion(rs.getInt("puntuacion"));
        cliente.setNumeroTipoCliente(rs.getString("numeroTipoCliente"));
        return cliente;
    }

    @Override
    public void insertar(Cliente cliente) {
    try (Connection conn = DBManager.getInstance().obtenerConexion()) {
        conn.setAutoCommit(false);
        try {
            personaCRUD.insertar(cliente);
            try (PreparedStatement ps = getInsertPS(conn, cliente)) {
                ps.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw new RuntimeException("Error al agregar cliente", e);
        } finally {
            conn.setAutoCommit(true);
        }
    } catch (SQLException e) {
        throw new RuntimeException("Error de conexión al agregar cliente", e);
    }
}

    @Override
    public void actualizar(Cliente cliente) {
        try (Connection conn = DBManager.getInstance().obtenerConexion()) {
            conn.setAutoCommit(false);
            try {
                // Actualizar en la tabla Persona
                personaCRUD.actualizar(cliente);

                // Actualizar en la tabla Cliente
                try (PreparedStatement ps = getUpdatePS(conn, cliente)) {
                    ps.executeUpdate();
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException("Error al actualizar cliente", e);
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error de conexión al actualizar cliente", e);
        }
    }
}
