/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package com.michisistemaws;

import com.MichiSistema.dominio.Evento;
import com.MichiSistema.negocio.EventoService;
import com.MichiSistema.negocio.impl.EventoServiceImpl;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.WebServiceException;
import java.util.List;

@WebService(serviceName = "EventoWS")
public class EventoWS {

    private EventoService eventoService;
    
    public EventoWS(){
        eventoService=new EventoServiceImpl();
    }
    
    @WebMethod(operationName = "listarEventos")
    public List<Evento> listarEventos() {
        try{
            return eventoService.listarEventos();
        }catch(Exception ex){
            throw new WebServiceException("Error al listar eventos "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "registrarEvento")
    public void registrarEvento(@WebParam(name = "evento") Evento evento) {
        try{
            eventoService.registrarEvento(evento);
        }catch(Exception ex){
            throw new WebServiceException("Error al registrar evento "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "actualizarEvento")
    public void actualizarEvento(@WebParam(name = "evento") Evento evento) {
        try{
            eventoService.actualizarEvento(evento);
        }catch(Exception ex){
            throw new WebServiceException("Error al actualizar evento "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "eliminarEvento")
    public void eliminarEvento(@WebParam(name = "idEvento") int idEvento) {
        try{
            eventoService.eliminarEvento(idEvento);
        }catch(Exception ex){
            throw new WebServiceException("Error al eliminar evento "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "obtenerEvento")
    public Evento obtenerEvento(@WebParam(name = "idEvento") int idEvento) {
        try{
            return eventoService.obtenerEvento(idEvento);
        }catch(Exception ex){
            throw new WebServiceException("Error al obtener evento "+ex.getMessage());
        }
    }
    
}
