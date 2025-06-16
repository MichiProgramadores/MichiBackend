    
package com.MichiSistema.persistencia.CRUD;

import com.MichiSistema.Enum.TipoProducto;
import com.MichiSistema.Enum.UnidadMedida;
import com.MichiSistema.conexion.DBManager;
import com.MichiSistema.dominio.Producto;
import com.MichiSistema.persistencia.dao.ProductoDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoCRUD extends BaseCRUD<Producto> implements ProductoDAO{

    @Override
    protected PreparedStatement getInsertPS(Connection conn, Producto producto) throws SQLException {
        String query ="INSERT INTO Producto(producto_id, nombre, precio, edad_minima, stock_actual, stock_minimo, estado, tipo_producto, volumen, descripcion, unidad_medida) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
        PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, producto.getProducto_id());
        ps.setString(2, producto.getNombre());
        ps.setDouble(3, producto.getPrecio());
        ps.setInt(4, producto.getEdad_minima());
        ps.setInt(5, producto.getStockActual());
        ps.setInt(6, producto.getStockMinimo());
        ps.setString(7, "1");  
        ps.setString(8, producto.getCategoriaProducto().name());  
        ps.setDouble(9, producto.getVolumen());
        ps.setString(10, producto.getDescripcion());
        ps.setString(11, producto.getUnidadMedida().name());  
        return ps;
    }

    @Override
    protected PreparedStatement getUpdatePS(Connection conn, Producto producto) throws SQLException {
        String query = "UPDATE Producto SET nombre=?, precio=?, edad_minima=?, stock_actual=?,stock_minimo=?, tipo_producto=?, volumen=?, descripcion=?, unidad_medida=? WHERE producto_id=?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, producto.getNombre());
        ps.setDouble(2, producto.getPrecio());
        ps.setInt(3, producto.getEdad_minima());
        ps.setInt(4, producto.getStockActual());
        ps.setInt(5, producto.getStockMinimo());
       // ps.setString(6, String.valueOf(producto.getEstado()));  
        ps.setString(6, producto.getCategoriaProducto().name());  
        ps.setDouble(7, producto.getVolumen());
        ps.setString(8, producto.getDescripcion());
        ps.setString(9, producto.getUnidadMedida().name()); 
        ps.setInt(10, producto.getProducto_id());
        return ps;
    }
    
    @Override
    protected PreparedStatement getDeletePS(Connection conn, Integer id) throws SQLException {
        String query = "UPDATE Producto SET estado=0 WHERE producto_id=?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement getSelectByIdPS(Connection conn, Integer id) throws SQLException {
        String query = "SELECT * FROM Producto WHERE producto_id=?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement getSelectAllPS(Connection conn) throws SQLException {
        String query = "SELECT * FROM Producto";
        return conn.prepareStatement(query);
    }
    
    @Override
    protected Producto createFromResultSet(ResultSet rs) throws SQLException {
        Producto producto = new Producto();
        producto.setProducto_id(rs.getInt("producto_id"));
        producto.setNombre(rs.getString("nombre"));
        producto.setPrecio(rs.getDouble("precio"));
        producto.setEdad_minima(rs.getInt("edad_minima"));
        producto.setStockActual(rs.getInt("stock_actual"));
        producto.setStockMinimo(rs.getInt("stock_minimo"));
        producto.setEstado(rs.getBoolean("estado"));

        producto.setCategoriaProducto(TipoProducto.valueOf(rs.getString("tipo_producto")));  // Convertir String a Enum
        producto.setVolumen(rs.getDouble("volumen"));
        producto.setDescripcion(rs.getString("descripcion"));
        producto.setUnidadMedida(UnidadMedida.valueOf(rs.getString("unidad_medida")));  // Convertir String a Enum
        return producto;
    }

    @Override
    protected void setId(Producto producto, Integer id) {
        producto.setProducto_id(id);
    }

    @Override
    public List<Producto> obtenerPorTipoProducto(TipoProducto tipo){
        List<Producto> productos = new ArrayList<>();
    
    // Consulta SQL para obtener productos por tipo
    String sql = "SELECT * FROM Producto WHERE tipo_producto = ?";
    
    try (Connection conn = DBManager.getInstance().obtenerConexion()) {
        // Usamos PreparedStatement para evitar inyecciones SQL
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            // Establecemos el valor del tipoProducto (convertimos el enum a String)
                ps.setString(1, tipo.toString());

                // Ejecutamos la consulta y obtenemos el resultado
                try (ResultSet rs = ps.executeQuery()) {
                    // Iteramos sobre el resultado de la consulta y utilizamos la función createFromResultSet
                    while (rs.next()) {
                        Producto producto = createFromResultSet(rs);  // Usamos la función createFromResultSet para mapear los datos
                        productos.add(producto);
                    }
                }
            }
        } catch (SQLException e) {
            // En caso de error, se lanza una RuntimeException
            throw new RuntimeException("Error al obtener productos por tipo: " + tipo, e);
        }

        // Retornamos la lista de productos encontrados
        return productos;
    }

    @Override
    public List<Producto> obtenerActivos() {
        List<Producto> entities = new ArrayList<>();

        try (Connection conn = DBManager.getInstance().obtenerConexion();
            
             CallableStatement cs = conn.prepareCall("{CALL sp_obtener_productos_activos()}");
             ResultSet rs = cs.executeQuery()) { 
            while (rs.next()) {
                entities.add(createFromResultSet(rs)); 
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar productos activos", e);
        }
        return entities;
    }

    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> productos = new ArrayList<>();

        // El procedimiento almacenado que utilizas
        String sql = "{CALL sp_buscar_producto_por_nombre(?)}";

        try (Connection conn = DBManager.getInstance().obtenerConexion();
             CallableStatement cs = conn.prepareCall(sql)) {

            // Establecemos el parámetro de entrada para el procedimiento almacenado
            cs.setString(1, nombre);

            // Ejecutamos el procedimiento y obtenemos el resultado
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    // Mapear el resultado del conjunto de resultados a objetos Trabajador
                    productos.add(createFromResultSet(rs)); // Asumiendo que tienes un método para crear el objeto
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar productos por nombre", e);
        }

        return productos;
    }
            
}

