package com.MichiSistema.dominio;
import com.MichiSistema.Enum.TipoTrabajador;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author OsquiCnapi
 */
public class Trabajador extends Persona{
    private TipoTrabajador tipoTrabajador;
    // Constructor con parámetros
    public Trabajador(String nombres, String apellidos, int celular, String email, TipoTrabajador tipotrabajador) {
        super(nombres, apellidos, celular, email); // Llama al constructor de la clase Persona
        this.tipoTrabajador = tipotrabajador; // Inicializa el tipo de trabajador
    }

    // Constructor sin parámetros (con valores predeterminados)
    public Trabajador() {
        super(); // Llama al constructor sin parámetros de la clase Persona
        this.tipoTrabajador = null; // Valor predeterminado para tipo de trabajador
    }

    /**
     * @return the tipotrabajador
     */
    public TipoTrabajador getTipoTrabajador() {
        return tipoTrabajador;
    }

    /**
     * @param tipotrabajador the tipotrabajador to set
     */
    public void setTipoTrabajador(TipoTrabajador tipoTrabajador) {
        this.tipoTrabajador = tipoTrabajador;
    }
  
}
