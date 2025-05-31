
package com.MichiSistema.persistencia.CRUD;
import com.MichiSistema.Enum.TipoComprobante;
import com.MichiSistema.conexion.DBManager;
import com.MichiSistema.dominio.Comprobante;
import com.MichiSistema.dominio.DetalleComprobante;
import com.MichiSistema.persistencia.dao.ComprobanteDAO;
import java.sql.*;

public class ComprobanteCRUD extends BaseCRUD<Comprobante> implements ComprobanteDAO {
    
    @Override
    protected PreparedStatement getInsertPS(Connection conn, Comprobante comprobante) throws SQLException {
        
        // Cálculo del tax (suponiendo que el tax es un 18% del monto_total)
        double tax = comprobante.getMonto_total() * 0.18;
        // Obtener la fecha de emisión actual (el día de hoy)
        Date fechaEmision = new Date(System.currentTimeMillis());
        
        // Query de inserción
        String query = "INSERT INTO Comprobante(orden_id, monto_total, estado, tipo_comprobante, taxes) "
                + "VALUES(?,?, ?, ?, ?)";
        
        PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        // Establecer parámetros en el PreparedStatement
        ps.setInt(1, comprobante.getOrden_id());
        ps.setDouble(2, comprobante.getMonto_total());
        ps.setString(3, comprobante.getEstado());
        ps.setString(4, comprobante.getTipoComprobante().name());  // Enum a String
        ps.setDouble(5, comprobante.getTax());  // Establecer el tax calculado
        
        return ps;
        
    }

    @Override
    protected PreparedStatement getUpdatePS(Connection conn, Comprobante comprobante) throws SQLException {
        String query = "UPDATE Comprobante SET monto_total=?, estado=?, fecha_emision=?, tipo_comprobante=?, taxes=? WHERE comprobante_id=?";
        
        PreparedStatement ps = conn.prepareStatement(query);
   
        ps.setDouble(1, comprobante.getMonto_total());
        ps.setString(2, comprobante.getEstado());
   
        ps.setDate(3, new java.sql.Date(comprobante.getFecha_emision().getTime()));
        ps.setString(4, comprobante.getTipoComprobante().name());  // Usar el nombre del enum
        ps.setDouble(5, comprobante.getTax()); 
        ps.setInt(6, comprobante.getId_comprobante());

        return ps;
    }

    @Override
    protected PreparedStatement getDeletePS(Connection conn, Integer id) throws SQLException {
        String query = "UPDATE Comprobante SET estado='Eliminado' WHERE comprobante_id=?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement getSelectByIdPS(Connection conn, Integer id) throws SQLException {
        String query = "SELECT * FROM Comprobante WHERE comprobante_id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement getSelectAllPS(Connection conn) throws SQLException {
        String query = "SELECT * FROM Comprobante";
        return conn.prepareStatement(query);
    }

    @Override
    protected Comprobante createFromResultSet(ResultSet rs) throws SQLException {
        Comprobante c = new Comprobante();
        
        c.setId_comprobante(rs.getInt("comprobante_id"));
        c.setOrden_id(rs.getInt("orden_id"));
        c.setMonto_total(rs.getDouble("monto_total"));
        c.setEstado(rs.getString("estado"));
        c.setFecha_emision(rs.getDate("fecha_emision"));
        c.setTipoComprobante(TipoComprobante.valueOf(rs.getString("tipo_comprobante")));
        c.setTax(rs.getDouble("taxes"));
        c.setCliente_id(rs.getInt("cliente_persona_id"));
        return c;
    }

    @Override
    protected void setId(Comprobante comprobante, Integer id) {
        comprobante.setId_comprobante(id);
    }
    
    // Método auxiliar para establecer los parámetros en PreparedStatement
    private void setComprobanteParameters(PreparedStatement ps, Comprobante c) throws SQLException {
        ps.setInt(1, c.getOrden_id());
        ps.setDouble(2, c.getMonto_total());
        ps.setString(3, c.getEstado());
        //ps.setString(3, c.getTipoComprobante());
        //ps.setDate(3, new java.sql.Date(c.getFecha_emision().getTime()));
        ps.setString(4, c.getTipoComprobante().name());  // Usar el nombre del enum
        ps.setDouble(5, c.getTax());  // Establecer el tax calculado
    }

    @Override
    public void actualizarEstado(int id_comprobante, String estado) {
        Comprobante comprobante = new Comprobante();
        comprobante.setId_comprobante(id_comprobante);
        comprobante.setEstado(estado);
        actualizar(comprobante);
    }
    
    @Override
                       
    public void insertar(Comprobante comprobante) {
    try (Connection conn = DBManager.getInstance().obtenerConexion()) {
        conn.setAutoCommit(false);
        try {
            String sql = "INSERT INTO Comprobante(orden_id, monto_total, estado,fecha_emision, tipo_comprobante, taxes, cliente_persona_id) VALUES (?, ?, ?, ?, ?, ?,?)";
            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, comprobante.getOrden_id());
                ps.setDouble(2, comprobante.getMonto_total());
                ps.setString(3, comprobante.getEstado());
                ps.setDate(4, new java.sql.Date(comprobante.getFecha_emision().getTime())); // este es el cambio real
                ps.setString(5, comprobante.getTipoComprobante().name());
                ps.setDouble(6, comprobante.getTax());
                ps.setInt(7, comprobante.getCliente_id());
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        setId(comprobante, rs.getInt(1));
                    }
                }
            }

            registrarDetalles(conn, comprobante);
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    } catch (SQLException e) {
        throw new RuntimeException("Error al registrar Comprobante", e);
    }
}
    private void registrarDetalles(Connection conn, Comprobante comprobante) throws SQLException {
        String sp = "{CALL sp_registrar_detalle_comprobante(?, ?, ?, ?)}";
        try (CallableStatement cs = conn.prepareCall(sp)) {
            for (DetalleComprobante detalle : comprobante.getDetalles()) {
                cs.setInt(1, comprobante.getId_comprobante());
                cs.setInt(2, detalle.getProducto_id());
                cs.setInt(4, detalle.getCantidad());
                cs.setDouble(3, detalle.getSubtotal());
                cs.execute();
            }
        }
    }
   

}
