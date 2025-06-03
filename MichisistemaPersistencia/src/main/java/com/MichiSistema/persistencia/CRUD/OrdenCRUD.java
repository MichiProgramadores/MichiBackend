package com.MichiSistema.persistencia.CRUD;
import com.MichiSistema.Enum.TipoRecepcion;
import com.MichiSistema.conexion.DBManager;
import com.MichiSistema.dominio.DetalleOrden;
import com.MichiSistema.dominio.Orden;
import com.MichiSistema.persistencia.dao.OrdenDAO;
import java.sql.*;
import java.util.ArrayList;

public class OrdenCRUD extends BaseCRUD<Orden> implements OrdenDAO{

    @Override
    protected PreparedStatement getInsertPS(Connection conn, Orden orden) throws SQLException {
        String query = "INSERT INTO Orden(tipo_recepcion, fecha_registro, set_up_personalizado, total_pagar, saldo, cantidad_dias, fecha_devolucion, fecha_entrega, fecha_emision, cliente_persona_id,"
                + "trabajador_persona_id, tipoEstadoFechaDevol_id) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, orden.getTipoRecepcion().name());  // Establecer el tipo de recepción
        
        ps.setDate(2, new Date(orden.getFecha_registro().getTime()));  // Convertir LocalDateTime a Timestamp
        ps.setString(3, orden.getSetUpPersonalizado());  // Descripción del set-up
        ps.setDouble(4, orden.getTotalPagar());  // Monto total a pagar
        ps.setDouble(5, orden.getSaldo());  // Saldo pendiente
        ps.setInt(6, orden.getCantDias());   
        ps.setDate(7, new Date(orden.getFecha_devolucion().getTime()));
        ps.setDate(8, new Date(orden.getFecha_entrega().getTime()));
        ps.setDate(9, new Date(orden.getFecha_emisión().getTime()));
        ps.setInt(10, orden.getClienteID());  
        ps.setInt(11, orden.getTrabajadorID());
        ps.setInt(12, orden.getTipoEstadoFechaDevol_id());//ESTO SE VA A BORRAR SEGUN LA LOGICA
        return ps;
    }

    @Override
    protected PreparedStatement getUpdatePS(Connection conn, Orden orden) throws SQLException {
        String query = "UPDATE Orden SET tipo_recepcion=?, fecha_registro=?, set_up_personalizado=?, total_pagar=?, saldo=?, cantidad_dias=?, fecha_devolucion=?, fecha_entrega=?, fecha_emision=? WHERE orden_id=?";
        
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, orden.getTipoRecepcion().name());
        ps.setDate(2, new Date(orden.getFecha_registro().getTime())); // Convertir LocalDateTime a Timestamp
        ps.setString(3, orden.getSetUpPersonalizado());
        ps.setDouble(4, orden.getTotalPagar());
        ps.setDouble(5, orden.getSaldo());
        ps.setInt(6, orden.getCantDias());
        ps.setDate(7, new Date(orden.getFecha_devolucion().getTime())); // Convertir LocalDate a Date
        ps.setDate(8, new Date(orden.getFecha_entrega().getTime()));
        ps.setDate(9, new Date(orden.getFecha_emisión().getTime()));   // Convertir LocalDate a Date
        ps.setInt(10, orden.getIdOrden());
          
        return ps;
    }
    


    @Override
    protected PreparedStatement getDeletePS(Connection conn, Integer id) throws SQLException {
        String query = "DELETE FROM Orden WHERE orden_id=?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement getSelectByIdPS(Connection conn, Integer id) throws SQLException {
        String query = "SELECT * FROM Orden WHERE orden_id  = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        return ps;
    }
    

    @Override
    protected PreparedStatement getSelectAllPS(Connection conn) throws SQLException {
        String query = "SELECT * FROM Orden";
        return conn.prepareStatement(query);
    }

    @Override
    protected Orden createFromResultSet(ResultSet rs) throws SQLException {
        Orden orden = new Orden();
        orden.setIdOrden(rs.getInt("orden_id"));
        orden.setTipoRecepcion(TipoRecepcion.valueOf(rs.getString("tipo_recepcion")));
        orden.setFecha_registro(rs.getDate("fecha_registro"));
        orden.setSetUpPersonalizado(rs.getString("set_up_personalizado"));
        orden.setTotalPagar(rs.getDouble("total_pagar"));
        orden.setSaldo(rs.getDouble("saldo"));
        orden.setCantDias(rs.getInt("cantidad_dias"));

        // Validación para fecha_devolucion
        java.sql.Date fechaDevolucion = rs.getDate("fecha_devolucion");
        if (fechaDevolucion != null) {
            orden.setFecha_devolucion(fechaDevolucion);
        } else {
            orden.setFecha_devolucion(null);  // O asignar otra cosa según tu lógica
        }

        // Validación para fecha_entrega
        java.sql.Date fechaEntrega = rs.getDate("fecha_entrega");
        if (fechaEntrega != null) {
            orden.setFecha_entrega(fechaEntrega);
        } else {
            orden.setFecha_entrega(null);
        }

        // Validación para fecha_emision
        java.sql.Date fechaEmision = rs.getDate("fecha_emision");
        if (fechaEmision != null) {
            orden.setFecha_emisión(fechaEmision);
        } else {
            orden.setFecha_emisión(null);
        }

        orden.setClienteID(rs.getInt("cliente_persona_id"));  
        orden.setTrabajadorID(rs.getInt("trabajador_persona_id"));  
        return orden;
    }

    @Override
    protected void setId(Orden orden, Integer id) {
        orden.setIdOrden(id);
    }
    
    @Override
    public void eliminar(int idOrden) {
        try (Connection conn = DBManager.getInstance().obtenerConexion()) {
            conn.setAutoCommit(false);
            try {
                

                try(PreparedStatement ps2 = getDeletePS(conn, idOrden)){
                    ps2.executeUpdate();
                }              
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar Orden", e);
        }
    }
    
    private void registrarDetalles(Connection conn, Orden orden) throws SQLException {
        String sp = "{CALL sp_registrar_detalle_orden(?, ?, ?, ?,?,?)}";
        try (CallableStatement cs = conn.prepareCall(sp)) {
            for (DetalleOrden detalle : orden.getListaOrdenes()) {
                cs.setInt(1, orden.getIdOrden());
                cs.setInt(2, detalle.getProducto());//get producto trae el id del producto
                cs.setInt(3, detalle.getCantidadSolicitada());
                cs.setInt(4, detalle.getCantidadEntregada());
                cs.setDouble(5, detalle.getPrecioAsignado());
                cs.setString(6, detalle.getUnidadMedida().name());
                //cs.setDouble(7, detalle.getSubtotal());
                cs.execute();
            }
        }
    }

    @Override
    public void insertar(Orden orden) {
        try (Connection conn = DBManager.getInstance().obtenerConexion()) {
            conn.setAutoCommit(false);
            try {
                try (PreparedStatement ps = getInsertPS(conn, orden)) {
                    ps.executeUpdate();
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            setId(orden, rs.getInt(1));
                        }
                    }
                }
                // Registrar detalles después de insertar la orden
                registrarDetalles(conn, orden);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al registrar Orden", e);
        }
    }
    
    
    
    
}
    
   