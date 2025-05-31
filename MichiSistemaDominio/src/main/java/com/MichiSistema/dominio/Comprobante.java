package com.MichiSistema.dominio;

import com.MichiSistema.Enum.TipoComprobante;
import java.util.Date;
import java.util.ArrayList;
/**
 *
 * @author Mulatillo Gomez, Ariana Camila
 */
public class Comprobante {
    private int id_comprobante;
    private double monto_total;
    private String estado;
    private Date fecha_emision;
    private ArrayList<DetalleComprobante> detalles;
    private TipoComprobante tipoComprobante;
    private double tax;
    private int cliente_id;
    private int orden_id;
    //private Usuario usuario;
    // Constructor con parámetros

    public Comprobante(int  orden_id, double monto_total,
            Date fecha_emision, TipoComprobante tipoComprobante, double tax,int cliente_id) {
        this.monto_total = monto_total;
        this.estado = "EMITIDO";
        this.fecha_emision = fecha_emision;
        this.detalles =  new ArrayList<>();
        this.tipoComprobante = tipoComprobante;
        this.tax = tax;
        this.cliente_id = cliente_id;
        this.orden_id = orden_id;
        //this.usuario= usuario;
    }


    // Constructor sin parámetros (con valores predeterminados)
    public Comprobante() {
        this.id_comprobante = 0; // Valor predeterminado para id_comprobante
        this.monto_total = 0.0; // Valor predeterminado para monto_total
        this.estado = "PENDIENTE"; // Estado predeterminado
        this.fecha_emision = new Date(); // Fecha de emisión predeterminada (fecha actual)
        this.detalles = new ArrayList<>(); // Lista vacía de detalles
        this.tipoComprobante = TipoComprobante.INVOICE; // Valor predeterminado para tipoComprobante (suponiendo que 'FACTURA' es uno de los valores de TipoComprobante)
    
    }
    //setters y getters
    /**
     * @return the id_comprobante
     */
    public int getId_comprobante() {
        return id_comprobante;
    }

    /**
     * @param id_comprobante the id_comprobante to set
     */
    public void setId_comprobante(int id_comprobante) {
        this.id_comprobante = id_comprobante;
    }

    /**
     * @return the monto_total
     */
    public double getMonto_total() {
        return monto_total;
    }

    /**
     * @param monto_total the monto_total to set
     */
    public void setMonto_total(double monto_total) {
        this.monto_total = monto_total;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the fecha_emision
     */
    public Date getFecha_emision() {
        return fecha_emision;
    }

    /**
     * @param fecha_emision the fecha_emision to set
     */
    public void setFecha_emision(Date fecha_emision) {
        this.fecha_emision = fecha_emision;
    }

    /**
     * @return the detalles
     */
    public ArrayList<DetalleComprobante> getDetalles() {
        return new ArrayList(this.detalles);
    }

    /**
     * @param detalles the detalles to set
     */
    public void setDetalles(ArrayList<DetalleComprobante> detalles) {
        this.detalles = detalles;
    }

    /**
     * @return the tipoComprobante
     */
    public TipoComprobante getTipoComprobante() {
        return tipoComprobante;
    }

    /**
     * @param tipoComprobante the tipoComprobante to set
     */
    public void setTipoComprobante(TipoComprobante tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    /**
     * @return the tax
     */
    public double getTax() {
        return tax;
    }

    /**
     * @param tax the tax to set
     */
    public void setTax(double tax) {
        this.tax = tax;
    }

    /**
     * @return the cliente_id
     */
    public int getCliente_id() {
        return cliente_id;
    }

    /**
     * @param cliente_id the cliente_id to set
     */
    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

//    /**
//     * @return the usuario
//     */
//    public Usuario getUsuario() {
//        return usuario;
//    }
//
//    /**
//     * @param usuario the usuario to set
//     */
//    public void setUsuario(Usuario usuario) {
//        this.usuario = usuario;
//    }

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
