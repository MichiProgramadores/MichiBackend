
package com.MichiSistema.persistencia.CRUD;

import java.sql.*;
import com.MichiSistema.dominio.Evento;
import com.MichiSistema.persistencia.dao.EventoDAO;

public class EventoCRUD extends BaseCRUD<Evento> implements EventoDAO{

    @Override
    protected PreparedStatement getInsertPS(Connection conn, Evento evento) throws SQLException {
        String query = "INSERT INTO Evento(fechaInicio, fechaFin, direccion, codigoPostal, descripcion, tipoEvento) "
               + "VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setDate(1, new Date(evento.getFechaInicio().getTime()));
        ps.setDate(2, new Date(evento.getFechaFin().getTime())); // Convertir LocalDate a Date
        ps.setString(3, evento.getDireccion());
        ps.setString(4, evento.getCodigoPostal());
        ps.setString(5, evento.getDescripcion());
        ps.setString(6, evento.getTipoEvento().toString());
        return ps;
    }

    @Override
    protected PreparedStatement getUpdatePS(Connection conn, Evento evento) throws SQLException {
        String query = "UPDATE Evento SET fechaInicio=?, fechaFin=?, direccion=?, codigoPostal=?, descripcion=? WHERE evento_id=?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setDate(1, new Date(evento.getFechaInicio().getTime()));
        ps.setDate(2, new Date(evento.getFechaFin().getTime()));
        ps.setString(3, evento.getDireccion());
        ps.setString(4, evento.getCodigoPostal());
        ps.setString(5, evento.getDescripcion());
        ps.setInt(6, evento.getEvento_id());
        return ps;
    }

    @Override
    protected PreparedStatement getDeletePS(Connection conn, Integer id) throws SQLException {
        String query = "DELETE FROM Evento WHERE evento_id=?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement getSelectByIdPS(Connection conn, Integer id) throws SQLException {
       String query = "SELECT evento_id, fechaInicio, fechaFin, direccion, codigoPostal, descripcion FROM Evento WHERE evento_id=?";
       PreparedStatement ps = conn.prepareStatement(query);
       ps.setInt(1, id);
       return ps;
    }

    @Override
    protected PreparedStatement getSelectAllPS(Connection conn) throws SQLException {
        String query = "SELECT * FROM Evento";
        return conn.prepareStatement(query);
    }

    @Override
    protected Evento createFromResultSet(ResultSet rs) throws SQLException {
        Evento evento = new Evento();
        evento.setEvento_id(rs.getInt("evento_id"));
        evento.setFechaInicio(rs.getDate("fechaInicio"));  // Convertir Date a LocalDate
        evento.setFechaFin(rs.getDate("fechaFin"));  // Convertir Date a LocalDate
        evento.setDireccion(rs.getString("direccion"));
        evento.setCodigoPostal(rs.getString("codigoPostal"));
        evento.setDescripcion(rs.getString("descripcion"));
        return evento;
    }

    @Override
    protected void setId(Evento evento, Integer id) {
        evento.setEvento_id(id);
    }
    
}
