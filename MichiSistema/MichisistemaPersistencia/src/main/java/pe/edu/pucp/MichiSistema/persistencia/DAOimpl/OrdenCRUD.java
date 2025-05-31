package pe.edu.pucp.MichiSistema.persistencia.DAOimpl;
import pe.edu.pucp.MichiSistema.Enum.TipoEstadoDevolucion;
import pe.edu.pucp.MichiSistema.Enum.TipoFechaDevolucion;
import pe.edu.pucp.MichiSistema.Enum.TipoRecepcion;
import pe.edu.pucp.MichiSistema.conexion.DBManager;
import pe.edu.pucp.MichiSistema.dominio.DetalleOrden;
import pe.edu.pucp.MichiSistema.dominio.Orden;
import pe.edu.pucp.MichiSistema.persistencia.DAO.OrdenDAO;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class OrdenCRUD extends BaseCRUD<Orden> implements OrdenDAO{

    @Override
    protected CallableStatement getInsertPS(Connection conn, Orden orden) throws SQLException {

        String query = "{CALL sp_registrar_orden(?, ?, ?, ?,?,?,?)}";
        
        CallableStatement ps =conn.prepareCall(query);
        ps.setString(1, orden.getTipoRecepcion().name());  // Establecer el tipo de recepción
         ps.setString(2, orden.getSetUpPersonalizado());  // Descripción del set-up
 
        ps.setInt(3, orden.getCantDias());  // Cantidad de días

        ps.setInt(4, orden.getClienteID());  // ID del cliente relacionado
        ps.setInt(5, orden.getTrabajadorID());
        ps.setInt(6, orden.getTipoEstadoFechaDevol_id());
        ps.registerOutParameter(7, Types.INTEGER);
        return ps;
    }

    @Override
    protected PreparedStatement getUpdatePS(Connection conn, Orden orden) throws SQLException {
        String query = "UPDATE Orden SET tipo_recepcion=?, set_up_personalizado=?, total_pagar=?, saldo=?, cantidad_dias=? WHERE orden_id=?";
        
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, orden.getTipoRecepcion().name());
        ps.setString(2, orden.getSetUpPersonalizado());
        ps.setDouble(3, orden.getTotalPagar());
        ps.setDouble(4, orden.getSaldo());
        ps.setInt(5, orden.getCantDias());
        ps.setInt(6, orden.getIdOrden());
        
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
    
    // Mapear los campos
    orden.setIdOrden(rs.getInt("orden_id"));
    orden.setTipoRecepcion(TipoRecepcion.valueOf(rs.getString("tipo_recepcion")));
    
    // Manejo de fecha_registro (Timestamp)
    if (rs.getTimestamp("fecha_registro") != null) {
        orden.setFecha_registro(rs.getTimestamp("fecha_registro").toLocalDateTime());
    } else {
        orden.setFecha_registro(LocalDateTime.now());  // Valor por defecto
    }

    orden.setSetUpPersonalizado(rs.getString("set_up_personalizado"));
    orden.setTotalPagar(rs.getDouble("total_pagar"));
    orden.setSaldo(rs.getDouble("saldo"));
    orden.setCantDias(rs.getInt("cantidad_dias"));
    
    // Manejo de fechas de tipo Date (fecha_devolucion, fecha_entrega, fecha_emision)
    if (rs.getDate("fecha_devolucion") != null) {
        orden.setFecha_devolucion(rs.getDate("fecha_devolucion").toLocalDate());
    } else {
        orden.setFecha_devolucion(LocalDate.now());  // Valor por defecto
    }

    if (rs.getDate("fecha_entrega") != null) {
        orden.setFecha_entrega(rs.getDate("fecha_entrega").toLocalDate());
    } else {
        orden.setFecha_entrega(LocalDate.now());  // Valor por defecto
    }

    if (rs.getDate("fecha_emision") != null) {
        orden.setFecha_emisión(rs.getDate("fecha_emision").toLocalDate());
    } else {
        orden.setFecha_emisión(LocalDate.now());  // Valor por defecto
    }

    orden.setClienteID(rs.getInt("cliente_persona_id"));
    orden.setTrabajadorID(rs.getInt("trabajador_persona_id"));

    return orden;
    }


    @Override
    protected void setId(Orden orden, Integer id) {
        orden.setIdOrden(id);
    }
    
//    @Override
//    public void insertar(Orden orden) {
//        try (Connection conn = DBManager.getInstance().obtenerConexion()) {
//            conn.setAutoCommit(false);
//            try {
//                try (PreparedStatement ps = getInsertPS(conn, orden)) {
//                    ps.executeUpdate();
//                    try (ResultSet rs = ps.getGeneratedKeys()) {
//                        if (rs.next()) {
//                            setId(orden, rs.getInt(1));
//                        }
//                    }
//                }
//
//                // Registrar detalles después de insertar la orden
//                registrarDetalles(conn, orden);
//
//                conn.commit();
//            } catch (SQLException e) {
//                conn.rollback();
//                throw e;
//            } finally {
//                conn.setAutoCommit(true);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException("Error al registrar Orden", e);
//        }
//    }
    
    @Override
    public void insertar(Orden orden) {
        try (Connection conn = DBManager.getInstance().obtenerConexion()) {
            conn.setAutoCommit(false);
            try {
                try (CallableStatement ps = getInsertPS(conn, orden)) {
                    ps.executeUpdate();

                    // Recuperar el valor OUT (p_orden_id)
                    int pOrdenId = ps.getInt(7);  // El parámetro OUT está en el índice 7
                    setId(orden, pOrdenId);  // Asignar el ID generado a la entidad

                    // Registrar detalles después de insertar la orden
                    registrarDetalles(conn, orden);

                    conn.commit();
                }

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
                cs.execute();
            }
        }
    }

    @Override
    public void actualizarTipoEstadoDevolucion(Integer orden_id, TipoEstadoDevolucion estado) {
        String sp = "{CALL sp_actualizar_tipo_estado_devoluciona(?, ?)}";
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             CallableStatement ps =conn.prepareCall(sp);) {
            ps.setInt(1, orden_id);
            ps.setString(2, estado.name());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar tipo estado devolucion", e);
        }
    }

    @Override
    public void actualizarSaldoOrden(Integer orden_id, Double monto) {
        String sp = "{CALL sp_actualizar_saldo_orden(?, ?)}";
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             CallableStatement ps =conn.prepareCall(sp);) {
            ps.setInt(1, orden_id);
            ps.setDouble(2, monto);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar tipo estado devolucion", e);
        }
    }

    @Override
    public void actualizarFechaEntrega(Integer orden_id, Date fecha) {
        String sp = "{CALL sp_actualizar_fecha_entrega(?, ?)}";
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             CallableStatement ps =conn.prepareCall(sp);) {
            ps.setInt(1, orden_id);
            ps.setDate(2, fecha);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar tipo estado devolucion", e);
        }
    }

    @Override
    public void actualizarFechaEmision(Integer orden_id, Date fecha) {
        String sp = "{CALL sp_actualizar_fecha_emision(?, ?)}";
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             CallableStatement ps =conn.prepareCall(sp);) {
            ps.setInt(1, orden_id);
            ps.setDate(2, fecha);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar tipo estado devolucion", e);
        }
    }

    @Override
    public void actualizarFechaDevolucion(Integer orden_id, Date fecha, TipoFechaDevolucion tipo) {
        String sp = "{CALL sp_actualizar_fecha_devolucion(?, ?, ?)}";
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             CallableStatement cs =conn.prepareCall(sp);) {
            cs.setInt(1, orden_id);
            cs.setDate(2, fecha);
            cs.setString(3, tipo.name());
            cs.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar tipo fecha devolucion", e);
        }
    }

    @Override
    public void actualizarDetalleOrden(Integer detalle_orden_id, Integer cantidad) {
        String sp = "{CALL sp_actualizar_detalle_orden(?, ?)}";
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             CallableStatement cs =conn.prepareCall(sp);) {
            cs.setInt(1, detalle_orden_id);
            cs.setInt(2, cantidad);
            cs.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar tipo fecha devolucion", e);
        }
    }
}

    
   