
package com.MichiSistema.persistencia.CRUD;
import com.MichiSistema.Enum.TipoComprobante;
import com.MichiSistema.Enum.UnidadMedida;
import com.MichiSistema.conexion.DBManager;
import com.MichiSistema.dominio.Comprobante;
import com.MichiSistema.dominio.DetalleComprobante;
import com.MichiSistema.persistencia.dao.ComprobanteDAO;
import java.sql.*;
import java.util.ArrayList;

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
        String query = "UPDATE Comprobante SET orden_id=?, monto_total=?, estado=?, fecha_emision=?, tipo_comprobante=?, taxes=?, cliente_persona_id=? WHERE comprobante_id=?";
        
        PreparedStatement ps = conn.prepareStatement(query);
        
        ps.setInt(1, comprobante.getOrden_id());
        ps.setDouble(2, comprobante.getMonto_total());
        ps.setString(3, comprobante.getEstado());
   
        ps.setDate(4, new java.sql.Date(comprobante.getFecha_emision().getTime()));
        ps.setString(5, comprobante.getTipoComprobante().name());  // Usar el nombre del enum
        ps.setDouble(6, comprobante.getTax()); 
        ps.setInt(7, comprobante.getCliente_id());
        
        ps.setInt(8, comprobante.getId_comprobante());
        
        return ps;
    }

    @Override
    protected PreparedStatement getDeletePS(Connection conn, Integer id) throws SQLException {
        String query = "UPDATE Comprobante SET estado='ELIMINADO' WHERE comprobante_id=?";
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
    public void actualizarEstado(Comprobante comprobante, String estado) {
        comprobante.setEstado(estado);
        actualizar(comprobante);
    }
    
    @Override             
    public void insertar(Comprobante comprobante) {
    try (Connection conn = DBManager.getInstance().obtenerConexion()) {
        conn.setAutoCommit(false);
        try {
            String sql = "UPDATE Comprobante SET orden_id=?, monto_total=?, estado=?, fecha_emision=?, tipo_comprobante=?, taxes=?, cliente_persona_id=? WHERE comprobante_id=?";
            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, comprobante.getOrden_id());
                ps.setDouble(2, comprobante.getMonto_total());
                ps.setString(3, comprobante.getEstado());

                ps.setDate(4, new java.sql.Date(comprobante.getFecha_emision().getTime()));
                ps.setString(5, comprobante.getTipoComprobante().name());  // Usar el nombre del enum
                ps.setDouble(6, comprobante.getTax()); 
                ps.setInt(7, comprobante.getCliente_id());

                ps.setInt(8, comprobante.getId_comprobante());
                
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
    
    @Override             
    public void actualizar(Comprobante comprobante) {
    try (Connection conn = DBManager.getInstance().obtenerConexion()) {
        conn.setAutoCommit(false);
        try {
            try (PreparedStatement ps = getUpdatePS(conn, comprobante)) {
                ps.executeUpdate();
            }
            
            String query = "DELETE FROM DetalleComprobante WHERE comprobante_id=?";
            try (PreparedStatement ps2 = conn.prepareStatement(query)) {
                ps2.setInt(1, comprobante.getId_comprobante());
                ps2.executeUpdate();
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
            throw new RuntimeException("Error al actualizar Comprobante", e);
        }
    }
    
    private void registrarDetalles(Connection conn, Comprobante comprobante) throws SQLException {
        String sp = "{CALL sp_registrar_detalle_Comprobante(?, ?, ?, ?)}";
        try (CallableStatement cs = conn.prepareCall(sp)) {
            for (DetalleComprobante detalle : comprobante.getDetalles()) {
                cs.setInt(1, comprobante.getId_comprobante());
                cs.setInt(2, detalle.getProducto_id());
                cs.setDouble(3, detalle.getSubtotal());
                cs.setInt(4, detalle.getCantidad());
                cs.execute();
            }
        }
    }
    
    private ArrayList<DetalleComprobante> obtenerDetallesPorComprobanteId(Connection conn, int idComprobante) throws SQLException {
        String sql = "SELECT * FROM DetalleComprobante WHERE comprobante_id = ?";
        ArrayList<DetalleComprobante> detalles = new ArrayList<>();
        System.out.println("Buscando detalles para comprobante_id = " + idComprobante);

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idComprobante);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DetalleComprobante detalle = new DetalleComprobante();
                    detalle.setComprobante_id(rs.getInt("comprobante_id"));
                    detalle.setProducto_id(rs.getInt("producto_id"));
                    detalle.setSubtotal(rs.getDouble("subtotal"));
                    detalle.setUnidad_medida(UnidadMedida.valueOf(rs.getString("unidad_medida")));
                    detalle.setCantidad(rs.getInt("cantidad"));
                    detalles.add(detalle);
                }
            }
        }

        return detalles;
    }
    
    @Override
    public Comprobante obtenerPorId(int idComprobante){
        
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement ps = getSelectByIdPS(conn, idComprobante);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                System.out.println("Existe el comprobante, llamando a obtenerDetallesPorComprobanteId");
                Comprobante comprobante = createFromResultSet(rs);
                ArrayList<DetalleComprobante> detalles= obtenerDetallesPorComprobanteId(conn, idComprobante);
                comprobante.setDetalles(detalles);
                return comprobante;
                } else {
                System.out.println(" No se encontró ningun comprobante con ese ID");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener entidad", e);
        }
        return null;
    }

}
