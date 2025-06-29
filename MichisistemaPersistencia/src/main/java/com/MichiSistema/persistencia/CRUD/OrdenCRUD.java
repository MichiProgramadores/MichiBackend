package com.MichiSistema.persistencia.CRUD;
import com.MichiSistema.Enum.TipoCliente;
import com.MichiSistema.Enum.TipoEstadoDevolucion;
import com.MichiSistema.Enum.TipoRecepcion;
import com.MichiSistema.Enum.UnidadMedida;
import com.MichiSistema.conexion.DBManager;
import com.MichiSistema.dominio.DetalleOrden;
import com.MichiSistema.dominio.Orden;
import com.MichiSistema.persistencia.dao.DetalleOrdenDAO;
import com.MichiSistema.persistencia.dao.OrdenDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
//import java.util.Date;

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
        if(rs.getString("tipo_estado_devolucion")!=null){
            orden.setTipoEstadoDevolucion(TipoEstadoDevolucion.valueOf(rs.getString("tipo_estado_devolucion")));
        }
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
                actualizarMontoTotal(conn, orden);
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
    
    private ArrayList<DetalleOrden> obtenerDetallesPorOrdenId(Connection conn, int idOrden) throws SQLException {
    String sql = "SELECT * FROM DetalleOrden WHERE orden_id = ?";
    ArrayList<DetalleOrden> detalles = new ArrayList<>();
    //System.out.println("🧪 Buscando detalles para orden_id = " + idOrden);

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, idOrden);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                
                DetalleOrden detalle = new DetalleOrden();
                detalle.setProducto(rs.getInt("producto_id"));
                detalle.setOrden_id(rs.getInt("orden_id"));
                detalle.setCantidadSolicitada(rs.getInt("cantidad_solicitada"));
                detalle.setCantidadEntregada(rs.getInt("cantidad_entregada"));
                detalle.setSubtotal(rs.getDouble("subtotal"));
                detalle.setPrecioAsignado(rs.getDouble("precio_asignado")); 
                detalle.setUnidadMedida(UnidadMedida.valueOf(rs.getString("unidad_medida")));// Puedes mapear a Enum si tienes uno
                detalles.add(detalle);

            }
        }
    }

    return detalles;
    }

    
    private void actualizarMontoTotal(Connection conn, Orden orden) throws SQLException {
        double montoTotal = 0;
        ArrayList<DetalleOrden> detalles = obtenerDetallesPorOrdenId(conn, orden.getIdOrden());

        // Calcula el monto total sumando los subtotales de los detalles
        for (DetalleOrden detalle : detalles) {
            montoTotal += detalle.getSubtotal(); // Sumar el subtotal de cada detalle
        }

        // Actualizar la orden con el monto total calculado
        String updateQuery = "UPDATE Orden SET total_pagar = ? WHERE orden_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(updateQuery)) {
            ps.setDouble(1, montoTotal);
            ps.setInt(2, orden.getIdOrden());
            ps.executeUpdate();
        }
    }
    
    
    @Override
    public Orden obtenerPorId(int idOrden){
        
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement ps = getSelectByIdPS(conn, idOrden);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                
                Orden orden = createFromResultSet(rs);
                ArrayList<DetalleOrden> detalles= obtenerDetallesPorOrdenId(conn, idOrden);
                orden.setListaOrdenes(detalles);
                return orden;
                } else {
                System.out.println(" No se encontró ninguna orden con ese ID");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener entidad", e);
        }
        return null;
    }

    
//    private void actualizarDetalles(Connection conn, Orden orden) throws SQLException {
//        String sp = "{CALL sp_actualizar_detalle_orden(?, ?,?,?,?,?)}";
//        try (CallableStatement cs = conn.prepareCall(sp)) {
//            System.out.println("## lista detalles size : "+ orden.getListaOrdenes().size());
//            for(DetalleOrden detalle : orden.getListaOrdenes()){
//                cs.setInt(1, detalle.getOrden_id());
//                cs.setInt(2, detalle.getProducto());//get producto trae el id del producto
//                cs.setInt(3, detalle.getCantidadSolicitada());
//                cs.setInt(4, detalle.getCantidadEntregada());
//                cs.setDouble(5, detalle.getPrecioAsignado());
//                cs.setString(6, detalle.getUnidadMedida().name());
//                //cs.setDouble(7, detalle.getSubtotal());
//                cs.execute();
//            }
//        }
//    }
    private void actualizarDetalles(Connection conn, Orden orden) throws SQLException {
    String sp = "{CALL sp_actualizar_detalle_orden(?, ?, ?, ?, ?, ?)}";
    try (CallableStatement cs = conn.prepareCall(sp)) {
        System.out.println("## lista detalles size : " + orden.getListaOrdenes().size());
        
        for (DetalleOrden detalle : orden.getListaOrdenes()) {
            try {
                cs.setInt(1, detalle.getOrden_id());
                cs.setInt(2, detalle.getProducto()); //get producto trae el id del producto
                cs.setInt(3, detalle.getCantidadSolicitada());
                cs.setInt(4, detalle.getCantidadEntregada());
                cs.setDouble(5, detalle.getPrecioAsignado());
                cs.setString(6, detalle.getUnidadMedida().name());
                //cs.setDouble(7, detalle.getSubtotal());
                cs.execute();
            } catch (SQLException e) {
                // Manejar el error específico para cada detalle
                System.err.println("Error al actualizar el detalle de la orden con producto ID: " + detalle.getProducto());
                throw new SQLException("Error al actualizar el detalle de la orden: " + e.getMessage(), e);
            }
        }
    } catch (SQLException e) {
        // Manejar los errores de la base de datos relacionados con el CallableStatement
        System.err.println("Error al preparar o ejecutar el stored procedure para actualizar detalles: " + e.getMessage());
        throw e; // Re-lanzar la excepción para que sea manejada por la capa superior
    }
}

    
    @Override
    public void actualizar(Orden orden) {
        try (Connection conn = DBManager.getInstance().obtenerConexion()) {
            conn.setAutoCommit(false);
            
            try {
                try(PreparedStatement ps= getUpdatePS(conn, orden)){
                    ps.executeUpdate();
                    
                }
                
                // Llamar a la función que actualiza el detalle de la orden
                actualizarDetalles(conn, orden); // Actualizar detalles de la orden
                actualizarMontoTotal(conn, orden); // Actualizar monto total de la orden (si es necesario)
                System.out.println(" Entrando a ingresar nuevos detalles a orden" + orden.getIdOrden());   
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar Orden", e);
        }
    }
     @Override
    public List<Orden> obtenerPorTipoOrden(TipoRecepcion tipo){
        List<Orden> ordenes = new ArrayList<>();
    
    // Consulta SQL para obtener productos por tipo
        String sql = "SELECT * FROM Orden WHERE tipo_recepcion = ?";
    
        try (Connection conn = DBManager.getInstance().obtenerConexion()) {
        // Usamos PreparedStatement para evitar inyecciones SQL
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            // Establecemos el valor del tipoProducto (convertimos el enum a String)
                ps.setString(1, tipo.toString());

                // Ejecutamos la consulta y obtenemos el resultado
                try (ResultSet rs = ps.executeQuery()) {
                    // Iteramos sobre el resultado de la consulta y utilizamos la función createFromResultSet
                    while (rs.next()) {
                        Orden orden = createFromResultSet(rs);  // Usamos la función createFromResultSet para mapear los datos
                        ordenes.add(orden);
                    }
                }
            }
        } catch (SQLException e) {
            // En caso de error, se lanza una RuntimeException
            throw new RuntimeException("Error al obtener ordenes por tipo de recepcion: " + tipo, e);
        }

        // Retornamos la lista de productos encontrados
        return ordenes;
    }

    
    
    @Override
    public void actualizarCantidadEntregada(Orden orden){
        
        try (Connection conn = DBManager.getInstance().obtenerConexion();
            PreparedStatement ps = getUpdatePS(conn, orden)) {
            
            ps.executeUpdate();
            ArrayList<DetalleOrden> detalles= obtenerDetallesPorOrdenId(conn, orden.getIdOrden());
            for (DetalleOrden detalle : detalles) {
                if(detalle.getCantidadEntregada()>0)
                   // actualizarCantProductoDetalle(conn, detalle);
                System.out.println(" Entrando a actualizar cantidad entregada orden" + orden.getIdOrden());    
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar entidad", e);
        }
    }

    @Override
    public void actualizarEstadoDevolucion(int idOrden, TipoEstadoDevolucion estado) {
        String sp = "{CALL sp_actualizar_puntuacion(?, ?)}";
        
        try (Connection conn = DBManager.getInstance().obtenerConexion()) {
             System.out.println("Actualizando estado de devolucion: " + estado.name());
            try (CallableStatement cs = conn.prepareCall(sp)) { 
                 
                cs.setInt(1, idOrden);
                cs.setString(2, estado.name());
                
                cs.executeUpdate();
            }catch(SQLException e){
                e.printStackTrace();
                throw new RuntimeException("Error al ejecutar procedimiento de actu del estado", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al contectar con la bd para actualizar el estado", e);
        }
            
    }

    @Override
    public void actualizarSaldoCero(Orden orden) {
        //Orden orden = ordenDAO.obtenerPorId(idOrden);

        String updateQuery= "UPDATE Orden SET saldo = ? WHERE orden_id = ? ";
        try (Connection conn = DBManager.getInstance().obtenerConexion()) {
            try (PreparedStatement ps = conn.prepareStatement(updateQuery)) {
                if(orden.getSaldo()==0)
                    ps.setDouble(1,orden.getTotalPagar());
                else 
                    ps.setDouble(1,0.0);
                ps.setInt(2, orden.getIdOrden());
                ps.executeUpdate();
            } catch (SQLException ex) {
                 throw new RuntimeException("Error al contectar con la bd para actualizar el saldo", ex);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Error al contectar con la bd para actualizar el saldo", ex);
        }  
    }
}
    
   