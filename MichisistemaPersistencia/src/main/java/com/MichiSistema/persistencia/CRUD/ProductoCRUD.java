    
package com.MichiSistema.persistencia.CRUD;

import com.MichiSistema.Enum.TipoProducto;
import com.MichiSistema.Enum.UnidadMedida;
import com.MichiSistema.dominio.Producto;
import com.MichiSistema.persistencia.dao.ProductoDAO;
import java.sql.*;

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
        String query = "DELETE FROM Producto WHERE producto_id=?";
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
        if(rs.getString("estado").compareTo("A")==0){
            producto.setEstado(true); 
        }
        else{
            producto.setEstado(false);
        }
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

}

