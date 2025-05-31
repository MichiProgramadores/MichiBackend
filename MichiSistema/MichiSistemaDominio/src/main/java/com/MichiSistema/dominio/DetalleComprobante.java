package com.MichiSistema.dominio;
import com.MichiSistema.Enum.UnidadMedida;
//import com.MichiSistema.persistencia.CRUD.ProductoCRUD;

/**
 *
 * @author Mulatillo Gomez, Ariana Camila
 */
import java.sql.*;

public class DetalleComprobante {
    private int producto_id;
    private double subtotal;
    private int cantidad;
    
    private UnidadMedida unidad_medida;
    private int comprobante_id;
    // Constructor con parámetros
    public DetalleComprobante(int comprobante_id, Producto producto, 
            int cantidad) throws SQLException {
//        ProductoCRUD productoCRUD = new ProductoCRUD();
//        Producto producto=productoCRUD.obtenerPorId(producto_id);
        if (producto != null){
             this.producto_id = producto.getProducto_id(); // Inicializa el id del producto
             this.subtotal = cantidad*producto.getPrecio(); // Inicializa el subtotal del producto
             this.unidad_medida = producto.getUnidadMedida(); // Inicializa la unidad de medida  
        }      
        this.cantidad = cantidad; // Inicializa la cantidad del producto 
        this.comprobante_id= comprobante_id;
    }
    public DetalleComprobante(){
    }

    // Constructor copia
    public DetalleComprobante(DetalleComprobante otroDetalle) {
        this.producto_id = otroDetalle.producto_id; // Copia el id del producto
        this.subtotal = otroDetalle.subtotal; // Copia el subtotal
        this.cantidad = otroDetalle.cantidad; // Copia la cantidad
        this.unidad_medida = otroDetalle.unidad_medida; // Copia la unidad de medida
        this.comprobante_id=otroDetalle.comprobante_id;
    }
    /**
     * @return the producto_id
     */
    public int getProducto_id() {
        return producto_id;
    }

    /**
     * @param producto_id the producto_id to set
     */
    public void setProducto_id(int producto_id) {
        this.producto_id = producto_id;
    }

    /**
     * @return the subtotal
     */
    public double getSubtotal() {
        return subtotal;
    }

    /**
     * @param subtotal the subtotal to set
     */
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * @return the cantidad
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**

    /**
     * @return the unidad
     */
    public UnidadMedida getUnidad_medida() {
        return unidad_medida;
    }

    /**
     * @param unidad the unidad to set
     */
    public void setUnidad_medida(UnidadMedida unidad) {
        this.unidad_medida = unidad;
    }

    /**
     * @return the comprobante_id
     */
    public int getComprobante_id() {
        return comprobante_id;
    }

    /**
     * @param comprobante_id the comprobante_id to set
     */
    public void setComprobante_id(int comprobante_id) {
        this.comprobante_id = comprobante_id;
    }
    
    
    
}
