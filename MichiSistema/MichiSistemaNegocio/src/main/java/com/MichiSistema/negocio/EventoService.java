/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.MichiSistema.negocio;

import com.MichiSistema.dominio.Evento;
import java.util.ArrayList;

/**
 *
 * @author Ariana Mulatillo Gomez
 */
public interface EventoService {
    void registrarEvento(Evento evento) throws Exception;
    void actualizarEvento(Evento evento) throws Exception;
    void eliminarEvento(int idEvento) throws Exception;
    Evento obtenerEvento(int idEvento) throws Exception;
    ArrayList<Evento> listarEventos() throws Exception;

}
