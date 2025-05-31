

package pe.edu.pucp.MichiSistema.dominio;

import pe.edu.pucp.MichiSistema.Enum.TipoFechaDevolucion;

/**
 *
 * @author Ariana Mulatillo Gomez
 */
public class EstadoFechaDevolucion {
     private int tipoEstadoFechaDevol_id;
     private double penalidad;
     private TipoFechaDevolucion tipoFechaDevolucion;
     private int porcentajeDesc;

    public EstadoFechaDevolucion(int tipoEstadoFechaDevol_id, double penalidad, TipoFechaDevolucion tipoFechaDevolucion, int porcentajeDesc) {
        this.tipoEstadoFechaDevol_id = tipoEstadoFechaDevol_id;
        this.penalidad = penalidad;
        this.tipoFechaDevolucion = tipoFechaDevolucion;
        this.porcentajeDesc = porcentajeDesc;
    }

    public EstadoFechaDevolucion() {
        this.penalidad = 0;
        this.tipoFechaDevolucion = TipoFechaDevolucion.DEVUELTO_FECHA_LIMITE; // o cualquier valor default
        this.porcentajeDesc = 0; // Valor inicial sin descuento
    }

     
//    public EstadoFechaDevolucion(double penalidad, TipoFechaDevolucion tipoFechaDevolucion) {
//        this.penalidad = penalidad;
//        this.tipoFechaDevolucion = tipoFechaDevolucion;
//    }

    public int getTipoEstadoFechaDevol_id() {
        return tipoEstadoFechaDevol_id;
    }

    public void setTipoEstadoFechaDevol_id(int tipoEstadoFechaDevol_id) {
        this.tipoEstadoFechaDevol_id = tipoEstadoFechaDevol_id;
    }

    public double getPenalidad() {
        return penalidad;
    }

    public void setPenalidad(double penalidad) {
        this.penalidad = penalidad;
    }

    public TipoFechaDevolucion getTipoFechaDevolucion() {
        return tipoFechaDevolucion;
    }

    public void setTipoFechaDevolucion(TipoFechaDevolucion tipoFechaDevolucion) {
        this.tipoFechaDevolucion = tipoFechaDevolucion;
    }

    /**
     * @return the porcentajeDesc
     */
    public int getPorcentajeDesc() {
        return porcentajeDesc;
    }

    /**
     * @param porcentajeDesc the porcentajeDesc to set
     */
    public void setPorcentajeDesc(int porcentajeDesc) {
        this.porcentajeDesc = porcentajeDesc;
    }
     
     
}
