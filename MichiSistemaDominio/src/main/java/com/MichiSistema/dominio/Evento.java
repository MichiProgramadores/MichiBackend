package com.MichiSistema.dominio;

/**
 *
 * @author Usuario
 */
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import com.MichiSistema.Enum.TipoEvento;
import java.time.Instant;
import java.util.Date;

public class Evento {
    private int evento_id;
    private Date fechaInicio;
    private Date fechaFin;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String direccion;
    private String codigoPostal;
    private String descripcion;
    private TipoEvento tipoEvento;
    
    public Evento(TipoEvento tipoEvento,Date fechaInicio, Date fechaFin,
                  LocalTime horaInicio, LocalTime horaFin,
                  String direccion, String codigoPostal) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
        this.tipoEvento=tipoEvento;
    }
    // Constructor sin parámetros (con valores predeterminados)
    public Evento() {
        this.fechaInicio = new Date(); // Fecha de inicio predeterminada es la fecha actual
        this.fechaFin = new Date(); // Fecha de fin predeterminada es la fecha actual
        this.horaInicio = LocalTime.MIDNIGHT; // Hora de inicio predeterminada es la medianoche
        this.horaFin = LocalTime.MIDNIGHT; // Hora de fin predeterminada es la medianoche
        this.direccion = ""; // Dirección predeterminada vacía
        this.codigoPostal = ""; // Código postal predeterminado vacío
        this.descripcion = ""; // Descripción predeterminada vacía
        this.tipoEvento=tipoEvento.BODA;
    }

    // Constructor copia
    public Evento(Evento otroEvento) {
        this.evento_id = otroEvento.evento_id; // Copia el ID del evento
        this.fechaInicio = otroEvento.fechaInicio; // Copia la fecha de inicio
        this.fechaFin = otroEvento.fechaFin; // Copia la fecha de fin
        this.horaInicio = otroEvento.horaInicio; // Copia la hora de inicio
        this.horaFin = otroEvento.horaFin; // Copia la hora de fin
        this.direccion = otroEvento.direccion; // Copia la dirección
        this.codigoPostal = otroEvento.codigoPostal; // Copia el código postal
        this.descripcion = otroEvento.descripcion; // Copia la descripción del evento
    }

    /**
     * @return the evento_id
     */
    public int getEvento_id() {
        return evento_id;
    }

    /**
     * @param evento_id the evento_id to set
     */
    public void setEvento_id(int evento_id) {
        this.evento_id = evento_id;
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the horaInicio
     */
    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    /**
     * @param horaInicio the horaInicio to set
     */
    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    /**
     * @return the horaFin
     */
    public LocalTime getHoraFin() {
        return horaFin;
    }

    /**
     * @param horaFin the horaFin to set
     */
    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the codigoPostal
     */
    public String getCodigoPostal() {
        return codigoPostal;
    }

    /**
     * @param codigoPostal the codigoPostal to set
     */
    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    @Override
    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");  // Define el formato deseado
        String fechaInicioFormateada = fechaInicio != null ? fechaInicio.toString(): "N/A";
        String fechaFinFormateada = fechaFin != null ? fechaFin.toString() : "N/A";
        
        return getEvento_id()+ "  "+fechaInicioFormateada + "  " +fechaFinFormateada + "  "+descripcion;
    }

    /**
     * @return the tipoEvento
     */
    public TipoEvento getTipoEvento() {
        return tipoEvento;
    }

    /**
     * @param tipoEvento the tipoEvento to set
     */
    public void setTipoEvento(TipoEvento tipoEvento) {
        this.tipoEvento = tipoEvento;
    }
}
