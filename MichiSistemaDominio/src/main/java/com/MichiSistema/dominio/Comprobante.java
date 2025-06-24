package com.MichiSistema.dominio;

import com.MichiSistema.Enum.TipoComprobante;
import java.util.Date;
import java.util.ArrayList;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement

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
    private double tax; //10%
    private int cliente_id;
    private int orden_id;
    //private Usuario usuario;
    
    /*
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
    */

    public Comprobante() {
        this.detalles = new ArrayList<>();
        this.fecha_emision = new Date();
        this.estado = "PENDIENTE";
    }

    public Comprobante(int id_comprobante, double monto_total, String estado,
            ArrayList<DetalleComprobante> detalles,
            TipoComprobante tipoComprobante, double tax, int cliente_id,
            int orden_id) {
        this.id_comprobante = id_comprobante;
        this.monto_total = monto_total;
        this.estado = estado;
        this.fecha_emision = new Date();
        this.detalles = detalles;
        this.tipoComprobante = tipoComprobante;
        this.tax = tax;
        this.cliente_id = cliente_id;
        this.orden_id = orden_id;
    }
    
    //setters y getters

    public int getId_comprobante() {
        return id_comprobante;
    }

    public void setId_comprobante(int id_comprobante) {
        this.id_comprobante = id_comprobante;
    }

    public double getMonto_total() {
        return monto_total;
    }

    public void setMonto_total(double monto_total) {
        this.monto_total = monto_total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFecha_emision() {
        return fecha_emision;
    }

    public void setFecha_emision(Date fecha_emision) {
        this.fecha_emision = fecha_emision;
    }
    
    @XmlElementWrapper(name = "detalles")
    @XmlElement(name = "detalle")
    public ArrayList<DetalleComprobante> getDetalles() {
        return detalles;
    }

    public void setDetalles(ArrayList<DetalleComprobante> detalles) {
        this.detalles = detalles;
    }

    public TipoComprobante getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(TipoComprobante tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public int getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

    public int getOrden_id() {
        return orden_id;
    }

    public void setOrden_id(int orden_id) {
        this.orden_id = orden_id;
    }
    
}
