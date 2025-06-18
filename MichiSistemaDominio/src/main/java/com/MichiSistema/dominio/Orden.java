package com.MichiSistema.dominio;
/**
 *
 * @author "MICHIPROGRAMADORES"
 */
import com.MichiSistema.Enum.TipoRecepcion;
import java.util.ArrayList;
import com.MichiSistema.Enum.TipoEstadoDevolucion;
import java.util.Date;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement

public class Orden {
    private int idOrden;
    private TipoRecepcion tipoRecepcion;
    private Date fecha_registro;
    private String setUpPersonalizado;
    private double totalPagar;
    private double saldo;
    private int cantDias;
    private Date fecha_devolucion;
    private Date fecha_entrega;
    private Date fecha_emisión;
    private ArrayList<DetalleOrden> listaOrdenes;
    private TipoEstadoDevolucion tipoEstadoDevolucion;
    private int clienteID;
    private int trabajadorID;
    private EstadoFechaDevolucion estado;

    public Orden() {
        this.listaOrdenes = new ArrayList<>();
    }
    public int getTipoEstadoFechaDevol_id() {
        return (estado != null) ? estado.getTipoEstadoFechaDevol_id() : 1; // o cualquier valor por defecto que exista
    }

    public Orden(TipoRecepcion tipoRecepcion, String setUpPersonalizado, double totalPagar,
                 double saldo, int cantDias, Date fecha_devolucion, Date fecha_emisión,
                 int clienteID, int trabajadorID) {
        this.tipoRecepcion = tipoRecepcion;
        this.fecha_registro = new Date();
        this.setUpPersonalizado = setUpPersonalizado;
        this.totalPagar = totalPagar; 
        this.saldo = saldo;
        this.cantDias = cantDias;
        this.fecha_devolucion = fecha_devolucion;
        this.fecha_entrega = new Date();
        this.fecha_emisión = fecha_emisión;
        this.listaOrdenes = new ArrayList<>();
        this.clienteID = clienteID;
        this.trabajadorID = trabajadorID;
    }

//    public void aplicarDescuentoPorEstado() {
//        if (estado != null) {
//            double descuento = estado.getPorcentajeDesc();
//            this.totalPagar = this.totalPagar * ((100 - descuento) / 100);
//        }
//    }


    // Getters y Setters
    public TipoRecepcion getTipoRecepcion() { return tipoRecepcion; }
    public void setTipoRecepcion(TipoRecepcion tipoRecepcion) { this.tipoRecepcion = tipoRecepcion; }
    public String getSetUpPersonalizado() { return setUpPersonalizado; }
    public void setSetUpPersonalizado(String setUpPersonalizado) { this.setUpPersonalizado = setUpPersonalizado; }
    public double getTotalPagar() { return totalPagar; }
    public void setTotalPagar(double totalPagar) { this.totalPagar = totalPagar; }
    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
    public Date getFecha_entrega() { return fecha_entrega; }
    public void setFecha_entrega(Date fecha_entrega) { this.fecha_entrega = fecha_entrega; }
    public Date getFecha_emisión() { return fecha_emisión; }
    public void setFecha_emisión(Date fecha_emisión) { this.fecha_emisión = fecha_emisión; }
    public Date getFecha_registro() { return fecha_registro; }
    public void setFecha_registro(Date fecha_registro) { this.fecha_registro = fecha_registro; }
    public int getCantDias() { return cantDias; }
    public void setCantDias(int cantDias) { this.cantDias = cantDias; }
    public int getIdOrden() { return idOrden; }
    public void setIdOrden(int idOrden) { this.idOrden = idOrden; }
    public Date getFecha_devolucion() { return fecha_devolucion; }
    public void setFecha_devolucion(Date fecha_devolucion) { this.fecha_devolucion = fecha_devolucion; }
        @XmlElementWrapper(name = "listaOrdenes")
    @XmlElement(name = "detalleOrden")
    public ArrayList<DetalleOrden> getListaOrdenes() { return listaOrdenes; }
    public void setListaOrdenes(ArrayList<DetalleOrden> listaOrdenes) { this.listaOrdenes = listaOrdenes; }
    public TipoEstadoDevolucion getTipoEstadoDevolucion() { return tipoEstadoDevolucion; }
    public void setTipoEstadoDevolucion(TipoEstadoDevolucion tipoEstadoDevolucion) { this.tipoEstadoDevolucion = tipoEstadoDevolucion; }
    public int getClienteID() { return clienteID; }
    public void setClienteID(int clienteID) { this.clienteID = clienteID; }
    public int getTrabajadorID() { return trabajadorID; }
    public void setTrabajadorID(int trabajadorID) { this.trabajadorID = trabajadorID; }
    public EstadoFechaDevolucion getEstado() { return estado; }
    public void setEstado(EstadoFechaDevolucion estado) { this.estado = estado; }
}


