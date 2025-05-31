/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.MichiSistema.negocio;

import com.MichiSistema.dominio.Trabajador;
import java.util.ArrayList;

/**
 *
 * @author Ariana Mulatillo Gomez
 */
public interface TrabajadorService {
    void registrarTrabajador(Trabajador trabajador) throws Exception;
    void actualizarTrabajador(Trabajador trabajador) throws Exception;
    void eliminarTrabajador(int idTrabajador) throws Exception;
    Trabajador obtenerTrabajador(int idTrabajador) throws Exception;
    ArrayList<Trabajador> listarTrabajadores() throws Exception;
}
