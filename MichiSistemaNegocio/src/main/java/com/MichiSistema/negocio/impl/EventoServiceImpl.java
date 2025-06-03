/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.MichiSistema.negocio.impl;

import com.MichiSistema.dominio.Evento;
import com.MichiSistema.negocio.EventoService;
import com.MichiSistema.persistencia.CRUD.EventoCRUD;
import com.MichiSistema.persistencia.dao.EventoDAO;
import java.util.ArrayList;

/**
 *
 * @author Ariana Mulatillo Gomez
 */
public class EventoServiceImpl implements EventoService {

    private final EventoDAO eventoDAO;

    public EventoServiceImpl() {
        this.eventoDAO = new EventoCRUD();  
    }

    @Override
    public void registrarEvento(Evento evento) throws Exception {
        // Validaciones de negocio
        if (evento.getFechaInicio() == null || evento.getFechaFin() == null) {
            throw new Exception("Las fechas del evento no pueden estar vacías");
        }
        if (evento.getHoraInicio() == null || evento.getHoraFin() == null) {
            throw new Exception("Las horas del evento no pueden estar vacías");
        }
        if (evento.getFechaInicio().after(evento.getFechaFin())) {
            throw new Exception("La fecha de inicio no puede ser posterior a la fecha de fin");
        }

        // Insertar evento
        eventoDAO.insertar(evento);
    }

    @Override
    public void actualizarEvento(Evento evento) throws Exception {
        // Validar que el evento exista
        if (eventoDAO.obtenerPorId(evento.getEvento_id()) == null) {
            throw new Exception("El evento no existe");
        }

        // Validaciones de negocio
        if (evento.getFechaInicio() == null || evento.getFechaFin() == null) {
            throw new Exception("Las fechas del evento no pueden estar vacías");
        }
        if (evento.getHoraInicio() == null || evento.getHoraFin() == null) {
            throw new Exception("Las horas del evento no pueden estar vacías");
        }
        if (evento.getFechaInicio().after(evento.getFechaFin())) {
            throw new Exception("La fecha de inicio no puede ser posterior a la fecha de fin");
        }

        // Actualizar evento
        eventoDAO.actualizar(evento);
    }

    @Override
    public void eliminarEvento(int idEvento) throws Exception {
        // Validar que el evento exista
        Evento evento = eventoDAO.obtenerPorId(idEvento);
        if (evento == null) {
            throw new Exception("El evento no existe");
        }
        eventoDAO.eliminar(idEvento);
    }

    @Override
    public Evento obtenerEvento(int idEvento) throws Exception {
        // Obtener evento por ID
        Evento evento = eventoDAO.obtenerPorId(idEvento);
        if (evento == null) {
            throw new Exception("Evento no encontrado");
        }
        return evento;
    }

    @Override
    public ArrayList<Evento> listarEventos() throws Exception {
        // Obtener todos los eventos
        return (ArrayList<Evento>) eventoDAO.obtenerTodos();
    }
}
