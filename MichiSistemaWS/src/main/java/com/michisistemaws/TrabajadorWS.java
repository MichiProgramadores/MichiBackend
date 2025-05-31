/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package com.michisistemaws;

import com.MichiSistema.dominio.Trabajador;
import com.MichiSistema.negocio.TrabajadorService;
import com.MichiSistema.negocio.impl.TrabajadorServiceImpl;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.WebServiceException;
import java.util.List;


@WebService(serviceName = "TrabajadorWS")
public class TrabajadorWS {

    private TrabajadorService trabajadorService;
    
    public TrabajadorWS(){
        trabajadorService=new TrabajadorServiceImpl();
    }
    
    @WebMethod(operationName = "listarTrabajadores")
    public List<Trabajador> listaTrabajadores() {
        try{
            return trabajadorService.listarTrabajadores();
        }catch(Exception ex){
            throw new WebServiceException("Error al listar trabajadores "+ex.getMessage());
        }
    }
}
