/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.MichiSistema.dominio;
import com.MichiSistema.Enum.UnidadMedida;
//import com.MichiSistema.persistencia.CRUD.ProductoCRUD;
import java.sql.*;

/**
 *
 * @author Usuario
 */
public class DetalleOrden {
    private int cantidadSolicitada;
    private int cantidadEntregada;
    private double precioAsignado;
    private int producto;
    private UnidadMedida unidadMedida;
    private double subtotal;
    private int orden_id;
    public DetalleOrden(){}
    
    public DetalleOrden(int cantidadSolicitada,int cantidadEntregada, 
            Producto prod, int orden_id) throws SQLException{
//        ProductoCRUD productoCRUD = new ProductoCRUD();
//        Producto prod=productoCRUD.obtenerPorId(producto_id);
        this.cantidadSolicitada = cantidadSolicitada;
        this.precioAsignado = prod.getPrecio();
        this.producto = prod.getProducto_id();
        this.unidadMedida = prod.getUnidadMedida();
        this.subtotal = cantidadEntregada*prod.getPrecio();
        this.cantidadEntregada=cantidadEntregada;    
        this.orden_id = orden_id;
    }

    /**
     * @return the cantidadSolicitada
     */
    public int getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    /**
     * @param cantidadSolicitada the cantidadSolicitada to set
     */
    public void setCantidadSolicitada(int cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }

    /**
     * @return the cantidadEntregada
     */
    public int getCantidadEntregada() {
        return cantidadEntregada;
    }

    /**
     * @param cantidadEntregada the cantidadEntregada to set
     */
    public void setCantidadEntregada(int cantidadEntregada) {
        this.cantidadEntregada = cantidadEntregada;
    }

    /**
     * @return the precioAsignado
     */
    public double getPrecioAsignado() {
        return precioAsignado;
    }

    /**
     * @param precioAsignado the precioAsignado to set
     */
    public void setPrecioAsignado(double precioAsignado) {
        this.precioAsignado = precioAsignado;
    }

    /**
     * @return the producto
     */
    public int getProducto() {
        return producto;
    }

    /**
     * @param producto the producto to set
     */
    public void setProducto(int producto) {
        this.producto = producto;
    }

    /**
     * @return the unidadMedida
     */
    public UnidadMedida getUnidadMedida() {
        return unidadMedida;
    }

    /**
     * @param unidadMedida the unidadMedida to set
     */
    public void setUnidadMedida(UnidadMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
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
     * @return the orden_id
     */
    public int getOrden_id() {
        return orden_id;
    }

    /**
     * @param orden_id the orden_id to set
     */
    public void setOrden_id(int orden_id) {
        this.orden_id = orden_id;
    }
    
}
