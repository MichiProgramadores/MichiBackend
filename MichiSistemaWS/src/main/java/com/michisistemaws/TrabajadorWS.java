package com.michisistemaws;

import com.MichiSistema.dominio.Trabajador;
import com.MichiSistema.negocio.TrabajadorService;
import com.MichiSistema.negocio.impl.TrabajadorServiceImpl;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.WebServiceException;
import java.util.List;


@WebService(serviceName = "TrabajadorWS", targetNamespace = "com.MichiSistema")
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
        @WebMethod(operationName = "registrarTrabajador")
    public void registrarTrabajador(@WebParam(name = "trabajador") Trabajador trabajador) {
        try {
            trabajadorService.registrarTrabajador(trabajador);
           // return "Trabajador registrado exitosamente";
        } catch (Exception ex) {
            throw new WebServiceException("Error al registrar trabajador: " + ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "actualizarTrabajador")
    public void actualizarTrabajador(@WebParam(name = "trabajador") Trabajador trabajador) {
        try {
            trabajadorService.actualizarTrabajador(trabajador);
            //return "Trabajador actualizado exitosamente";
        } catch (Exception ex) {
            throw new WebServiceException("Error al actualizar trabajador: " + ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "eliminarTrabajador")
    public void eliminarTrabajador(@WebParam(name = "idTrabajador") int idTrabajador) {
        try {
            trabajadorService.eliminarTrabajador(idTrabajador);
        } catch (Exception ex) {
            throw new WebServiceException("Error al eliminar trabajador: " + ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "obtenerTrabajador")
    public Trabajador obtenerTrabajador(@WebParam(name = "idTrabajador") int idTrabajador) {
        try {
            return trabajadorService.obtenerTrabajador(idTrabajador);
        } catch (Exception ex) {
            throw new WebServiceException("Error al obtener trabajador: " + ex.getMessage());
        }
    }
}