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
}
