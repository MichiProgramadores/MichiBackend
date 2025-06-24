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
    public List<Comprobante> listarComprobante() {
        try{
            return comprobanteService.listarComprobante();
        }catch(Exception ex){
            throw new WebServiceException("Error al listar comprobantes "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "registrarComprobante")
    public void registrarComprobante(@WebParam(name = "comprobante") Comprobante comprobante) {
        try{
            comprobanteService.registrarComprobante(comprobante);
        }catch(Exception ex){
            throw new WebServiceException("Error al registrar comprobante "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "actualizarComprobante")
    public void actualizarComprobante(@WebParam(name = "comprobante") Comprobante comprobante) {
        try{
            comprobanteService.actualizarComprobante(comprobante);
        }catch(Exception ex){
            throw new WebServiceException("Error al actualizar comprobante "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "eliminarComprobante")
    public void eliminarComprobante(@WebParam(name = "idComprobante") int idComprobante) {
        try{
            comprobanteService.eliminarComprobante(idComprobante);
        }catch(Exception ex){
            throw new WebServiceException("Error al eliminar comprobante "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "obtenerComprobante")
    public Comprobante obtenerComprobante(@WebParam(name = "idComprobante") int idComprobante) {
        try{
            return comprobanteService.obtenerComprobante(idComprobante);
        }catch(Exception ex){
            throw new WebServiceException("Error al obtener comprobante "+ex.getMessage());
        }
    }
    
    /*
    @WebMethod(operationName = "actualizarEstadoComprobante")
    public void actualizarEstadoComprobante(@WebParam(name = "idComprobante") int idComprobante,
            @WebParam(name = "toString") String toString) {
        try{
            comprobanteService.actualizarEstadoComprobante(idComprobante, toString);
        }catch(Exception ex){
            throw new WebServiceException("Error al actualizar el estado del comprobante "+ex.getMessage());
        }
    }
    */
    
}
