/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package com.michisistemaws;

import com.MichiSistema.Enum.TipoEstadoDevolucion;
import com.MichiSistema.Enum.TipoFechaDevolucion;
import com.MichiSistema.Enum.TipoRecepcion;
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
    
    @WebMethod(operationName = "listarOrdenes")
    public List<Orden> listarOrdenes() {
        try{
            return ordenService.listarOrdenes();
        }catch(Exception ex){
            throw new WebServiceException("Error al listar orden "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "registrarOrden")
    public void registrarOrden(@WebParam(name = "orden") Orden orden,@WebParam(name = "str_tipoRecepcion") String str_tipoRecepcion) {
        try{
            orden.setTipoRecepcion(TipoRecepcion.valueOf(str_tipoRecepcion));
            ordenService.registrarOrden(orden);
        }catch(Exception ex){
            throw new WebServiceException("Error al registrar orden "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "actualizarOrden")
    public void actualizarOrden(@WebParam(name = "orden") Orden orden) {
        try{
            ordenService.actualizarOrden(orden);
        }catch(Exception ex){
            throw new WebServiceException("Error al actualizar orden "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "eliminarOrden")
    public void eliminarOrden(@WebParam(name = "idOrden") int idOrden) {
        try{
            ordenService.eliminarOrden(idOrden);
        }catch(Exception ex){
            throw new WebServiceException("Error al eliminar orden "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "obtenerOrden")
    public Orden obtenerOrden(@WebParam(name = "idOrden") int idOrden) {
        try{
            
           // return ordenService.obtenerOrden(idOrden);
           Orden orden = ordenService.obtenerOrden(idOrden);

        // Verifica en consola
            if (orden.getListaOrdenes() == null) {
                System.out.println("⚠ listaOrdenes ES NULL en backend");
            } else {
                System.out.println("✅ listaOrdenes size: " + orden.getListaOrdenes().size());
            }

                return orden;
        }catch(Exception ex){
            throw new WebServiceException("Error al obtener orden "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "actualizarEstadoDevolucion")
    public void actualizarEstadoOrden(@WebParam(name = "ventaId") int ventaId,
            @WebParam(name = "estado") TipoEstadoDevolucion estado) {
        try{
            ordenService.actualizarEstadoDevolucion(ventaId, estado);
        }catch(Exception ex){
            throw new WebServiceException("Error al actualizar el estado de devolucion de la orden "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "actualizarEstadoFechaDevolucion")
    public void actualizarEstadoFechaDevolucion(@WebParam(name = "ventaId") Integer ventaId,
            @WebParam(name = "estado") TipoFechaDevolucion estadoFecha) {
        try{
            ordenService.actualizarEstadoFechaDevolucion(ventaId, estadoFecha);
        }catch(Exception ex){
            throw new WebServiceException("Error al actualizar el estado de la fecha de devolucion de la orden "+ex.getMessage());
        }
    }
    
    
}
