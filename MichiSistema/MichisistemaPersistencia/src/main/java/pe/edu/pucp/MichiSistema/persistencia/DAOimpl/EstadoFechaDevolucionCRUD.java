/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package pe.edu.pucp.MichiSistema.persistencia.DAOimpl;

import pe.edu.pucp.MichiSistema.Enum.TipoFechaDevolucion;
import pe.edu.pucp.MichiSistema.dominio.EstadoFechaDevolucion;
import pe.edu.pucp.MichiSistema.persistencia.DAO.EstadoFechaDevolucionDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EstadoFechaDevolucionCRUD extends BaseCRUD<EstadoFechaDevolucion> implements EstadoFechaDevolucionDAO {

    @Override
    protected PreparedStatement getInsertPS(Connection conn, EstadoFechaDevolucion entity) throws SQLException {
        String query = "INSERT INTO EstadoFechaDevolucion(tipo_fecha_devolucion, penalidad) "
                     + "VALUES(?, ?)";
        PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, entity.getTipoFechaDevolucion().toString());
        ps.setDouble(2, entity.getPenalidad());
        return ps;
    }

    @Override
    protected PreparedStatement getUpdatePS(Connection conn, EstadoFechaDevolucion entity) throws SQLException {
        String query = "UPDATE EstadoFechaDevolucion SET tipo_fecha_devolucion=?, penalidad=? "
                     + "WHERE tipoEstadoFechaDevol_id=?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, entity.getTipoFechaDevolucion().toString());
        ps.setDouble(2, entity.getPenalidad());
        ps.setInt(3, entity.getTipoEstadoFechaDevol_id());
        return ps;
    }

    @Override
    protected PreparedStatement getDeletePS(Connection conn, Integer id) throws SQLException {
        String query = "DELETE FROM EstadoFechaDevolucion WHERE tipoEstadoFechaDevol_id=?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement getSelectByIdPS(Connection conn, Integer id) throws SQLException {
        String query = "SELECT tipoEstadoFechaDevol_id, tipo_fecha_devolucion, penalidad "
                     + "FROM EstadoFechaDevolucion WHERE tipoEstadoFechaDevol_id=?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement getSelectAllPS(Connection conn) throws SQLException {
        String query = "SELECT * FROM EstadoFechaDevolucion";
        return conn.prepareStatement(query);
    }

    @Override
    protected EstadoFechaDevolucion createFromResultSet(ResultSet rs) throws SQLException {
        EstadoFechaDevolucion estado = new EstadoFechaDevolucion();
        estado.setTipoEstadoFechaDevol_id(rs.getInt("tipoEstadoFechaDevol_id"));
        estado.setTipoFechaDevolucion(TipoFechaDevolucion.valueOf(rs.getString("tipo_fecha_devolucion")));
        estado.setPenalidad(rs.getDouble("penalidad"));
        return estado;
    }

    @Override
    protected void setId(EstadoFechaDevolucion entity, Integer id) {
        entity.setTipoEstadoFechaDevol_id(id);
    }
}
