/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package com.michisistemaws;

import com.MichiSistema.dominio.Orden;
import com.MichiSistema.negocio.OrdenService;
import com.MichiSistema.negocio.impl.OrdenServiceImpl;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.WebServiceException;
import java.util.List;

/**
 *
 * @author OsquiCnapi
 */
@WebService(serviceName = "OrdenWS")
public class OrdenWS {

    private OrdenService ordenService;
    
    public OrdenWS(){
        ordenService=new OrdenServiceImpl();
    }
    
    @WebMethod(operationName = "listaOrdenes")
    public List<Orden> listaOrdenes() {
        try{
            return ordenService.listarOrdenes();
        }catch(Exception ex){
            throw new WebServiceException("Error al listar orden "+ex.getMessage());
        }
    }
}
