package com.MichiSistema.dominio;
/**
 *
 * @author "MICHIPROGRAMADORES"
 */
import com.MichiSistema.Enum.TipoRecepcion;
import java.time.LocalDateTime; // Para manejar fechas y horas
import java.time.LocalDate; // Para manejar la fecha (dia/mes/anio)
import java.util.ArrayList;
import com.MichiSistema.Enum.TipoEstadoDevolucion;
import com.MichiSistema.Enum.TipoFechaDevolucion;

public class Orden {
    private int idOrden;
    private TipoRecepcion tipoRecepcion;
    private LocalDateTime fecha_registro;
    private String setUpPersonalizado;
    private double totalPagar;
    private double saldo;
    private int cantDias;
    private LocalDate fecha_devolucion;
    private LocalDate fecha_entrega;
    private LocalDate fecha_emisión;
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
                 double saldo, int cantDias, LocalDate fecha_devolucion, LocalDate fecha_emisión,
                 int clienteID, int trabajadorID) {
        this.tipoRecepcion = tipoRecepcion;
        this.fecha_registro = LocalDateTime.now();
        this.setUpPersonalizado = setUpPersonalizado;
        this.totalPagar = totalPagar; 
        this.saldo = saldo;
        this.cantDias = cantDias;
        this.fecha_devolucion = fecha_devolucion;
        this.fecha_entrega = this.fecha_registro.plusDays(cantDias).toLocalDate();
        this.fecha_emisión = fecha_emisión;
        this.listaOrdenes = new ArrayList<>();
        this.clienteID = clienteID;
        this.trabajadorID = trabajadorID;
    }

    public void aplicarDescuentoPorEstado() {
        if (estado != null) {
            double descuento = estado.getPorcentajeDesc();
            this.totalPagar = this.totalPagar * ((100 - descuento) / 100);
        }
    }


    // Getters y Setters
    public TipoRecepcion getTipoRecepcion() { return tipoRecepcion; }
    public void setTipoRecepcion(TipoRecepcion tipoRecepcion) { this.tipoRecepcion = tipoRecepcion; }
    public String getSetUpPersonalizado() { return setUpPersonalizado; }
    public void setSetUpPersonalizado(String setUpPersonalizado) { this.setUpPersonalizado = setUpPersonalizado; }
    public double getTotalPagar() { return totalPagar; }
    public void setTotalPagar(double totalPagar) { this.totalPagar = totalPagar; }
    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
    public LocalDate getFecha_entrega() { return fecha_entrega; }
    public void setFecha_entrega(LocalDate fecha_entrega) { this.fecha_entrega = fecha_entrega; }
    public LocalDate getFecha_emisión() { return fecha_emisión; }
    public void setFecha_emisión(LocalDate fecha_emisión) { this.fecha_emisión = fecha_emisión; }
    public LocalDateTime getFecha_registro() { return fecha_registro; }
    public void setFecha_registro(LocalDateTime fecha_registro) { this.fecha_registro = fecha_registro; }
    public int getCantDias() { return cantDias; }
    public void setCantDias(int cantDias) { this.cantDias = cantDias; }
    public int getIdOrden() { return idOrden; }
    public void setIdOrden(int idOrden) { this.idOrden = idOrden; }
    public LocalDate getFecha_devolucion() { return fecha_devolucion; }
    public void setFecha_devolucion(LocalDate fecha_devolucion) { this.fecha_devolucion = fecha_devolucion; }
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


