/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package com.michisistemaws;

import com.MichiSistema.Enum.TipoCliente;
import com.MichiSistema.dominio.Cliente;
import com.MichiSistema.negocio.ClienteService;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.WebServiceException;
import java.util.List;
import com.MichiSistema.negocio.impl.ClienteServiceImpl;
import com.MichiSistema.negocio.ClienteService;

/**
 *
 * @author Usuario
 */
@WebService(serviceName = "ClienteWS")
public class ClienteWS {

    /**
     * This is a sample web service operation
     */
    private ClienteService clienteService;
    
    public ClienteWS(){
        clienteService=new ClienteServiceImpl();
    }
    
    @WebMethod(operationName = "listarClientes")
    public List<Cliente> listarClientes() {
        try{
            return clienteService.listarClientes();
        }catch(Exception ex){
            throw new WebServiceException("Error al listar clientes "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "registrarCliente")
    public void registrarCliente(@WebParam(name = "cliente") Cliente cliente,
            @WebParam(name = "str_tipoCliente") String str_tipoCliente) {
        try{
            cliente.setTipoCliente(TipoCliente.valueOf(str_tipoCliente));
            clienteService.registrarCliente(cliente);
        }catch(Exception ex){
            throw new WebServiceException("Error al registrar cliente "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "actualizarCliente")
    public void actualizarCliente(@WebParam(name = "cliente") Cliente cliente) {
        try{
            clienteService.actualizarCliente(cliente);
        }catch(Exception ex){
            throw new WebServiceException("Error al actualizar cliente "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "eliminarCliente")
    public void eliminarCliente(@WebParam(name = "idCliente") int idCliente) {
        try{
            clienteService.eliminarCliente(idCliente);
        }catch(Exception ex){
            throw new WebServiceException("Error al eliminar cliente "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "obtenerCliente")
    public Cliente obtenerCliente(@WebParam(name = "idCliente") int idCliente) {
        try{
            return clienteService.obtenerCliente(idCliente);
        }catch(Exception ex){
            throw new WebServiceException("Error al obtener cliente "+ex.getMessage());
        }
    }
    
}
