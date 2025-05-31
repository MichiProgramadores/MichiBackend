package com.MichiSistema.dominio;

import com.MichiSistema.Enum.TipoCliente;


/**
 *
 * @author Usuario
 */
public class Cliente extends Persona {
    private TipoCliente tipoCliente;
    private int puntuacion;
    private String numeroTipoCliente;//puede aceptar null

    public Cliente() {
         super();
    }

    // Constructor con parámetros
    public Cliente(String nombres, String apellidos, int celular, 
            String email, TipoCliente tipocliente, int puntuacion, String numeroTipoCliente) {
        super(nombres,apellidos,celular,email);
        this.tipoCliente = tipocliente;
        this.puntuacion = puntuacion;
        this.numeroTipoCliente = numeroTipoCliente;
    }
    //Getters y Setters COMPROBANTE EVENTO ORDEN
    /**
     * @return the tipocliente
     */
    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }
    /**
     * @param tipocliente the tipocliente to set
     */
    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    /**
     * @return the puntuacion
     */
    public int getPuntuacion() {
        return puntuacion;
    }

    /**
     * @param puntuacion the puntuacion to set
     */
    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    /**
     * @return the ruc
     */

    @Override
    public String toString() {
        if (getNumeroTipoCliente() != null && !numeroTipoCliente.isEmpty()) {
            return "RUC: " + getNumeroTipoCliente() + " " + apellidos + ", " + nombres;  // Persona Jurídica
        } else {
            //para versiones de java menores a 11
            String spaces = "            ";  // 12 espacios
            return "S/N" + spaces + apellidos + ", " + nombres; 
            //return "S/N"+" ".repeat(12) + apellidos + ", " + nombres;  // Persona Natural
        }
    }

    /**
     * @return the numeroTipoCliente
     */
    public String getNumeroTipoCliente() {
        return numeroTipoCliente;
    }

    /**
     * @param numeroTipoCliente the numeroTipoCliente to set
     */
    public void setNumeroTipoCliente(String numeroTipoCliente) {
        this.numeroTipoCliente = numeroTipoCliente;
    }


    
}
