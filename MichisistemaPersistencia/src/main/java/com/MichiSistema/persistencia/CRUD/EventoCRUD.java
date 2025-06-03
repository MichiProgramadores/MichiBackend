
package com.MichiSistema.persistencia.CRUD;

import java.sql.*;
import com.MichiSistema.dominio.Evento;
import com.MichiSistema.persistencia.dao.EventoDAO;

public class EventoCRUD extends BaseCRUD<Evento> implements EventoDAO{

    @Override
    protected PreparedStatement getInsertPS(Connection conn, Evento evento) throws SQLException {
        String query = "INSERT INTO Evento(fechaInicio, fechaFin, horaInicio, horaFin, direccion, codigoPostal, descripcion, tipoEvento) "
               + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setDate(1, java.sql.Date.valueOf(evento.getFechaInicio())); // Convertir LocalDate a Date
        ps.setDate(2, java.sql.Date.valueOf(evento.getFechaFin())); // Convertir LocalDate a Date
        ps.setTime(3, java.sql.Time.valueOf(evento.getHoraInicio())); // Convertir LocalTime a Time
        ps.setTime(4, java.sql.Time.valueOf(evento.getHoraFin())); // Convertir LocalTime a Time
        ps.setString(5, evento.getDireccion());
        ps.setString(6, evento.getCodigoPostal());
        ps.setString(7, evento.getDescripcion());
        ps.setString(8, evento.getTipoEvento().toString());
        return ps;
    }

    @Override
    protected PreparedStatement getUpdatePS(Connection conn, Evento evento) throws SQLException {
        String query = "UPDATE Evento SET fechaInicio=?, fechaFin=?, horaInicio=?, horaFin=?, direccion=?, codigoPostal=?, descripcion=? WHERE evento_id=?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setDate(1, java.sql.Date.valueOf(evento.getFechaInicio()));
        ps.setDate(2, java.sql.Date.valueOf(evento.getFechaFin()));
        ps.setTime(3, java.sql.Time.valueOf(evento.getHoraInicio()));
        ps.setTime(4, java.sql.Time.valueOf(evento.getHoraFin()));
        ps.setString(5, evento.getDireccion());
        ps.setString(6, evento.getCodigoPostal());
        ps.setString(7, evento.getDescripcion());
        ps.setInt(8, evento.getEvento_id());
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
       String query = "SELECT evento_id, fechaInicio, fechaFin, horaInicio, horaFin, direccion, codigoPostal, descripcion FROM Evento WHERE evento_id=?";
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
        evento.setFechaInicio(rs.getDate("fechaInicio").toLocalDate());  // Convertir Date a LocalDate
        evento.setFechaFin(rs.getDate("fechaFin").toLocalDate());  // Convertir Date a LocalDate
        evento.setHoraInicio(rs.getTime("horaInicio").toLocalTime());  // Convertir Time a LocalTime
        evento.setHoraFin(rs.getTime("horaFin").toLocalTime());  // Convertir Time a LocalTime
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
