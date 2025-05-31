/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package com.michisistemaws;

import com.MichiSistema.dominio.Comprobante;
import com.MichiSistema.negocio.ComprobanteService;
import com.MichiSistema.negocio.impl.ComprobanteServiceImpl;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.WebServiceException;
import java.util.List;



@WebService(serviceName = "ComprobanteWS")
public class ComprobanteWS {

    private ComprobanteService comprobanteService;
    
    public ComprobanteWS(){
        comprobanteService=new ComprobanteServiceImpl();
    }
    
    @WebMethod(operationName = "listarComprobante")
    public List<Comprobante> listaComprobantes() {
        try{
            return comprobanteService.listarComprobante();
        }catch(Exception ex){
            throw new WebServiceException("Error al listar comprobantes "+ex.getMessage());
        }
    }
}
