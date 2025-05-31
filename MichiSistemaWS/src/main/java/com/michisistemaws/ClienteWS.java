/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package com.michisistemaws;

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
    @WebMethod(operationName = "listaClientes")
    public List<Cliente> listaClientes() {
        try{
            return clienteService.listarClientes();
        }catch(Exception ex){
            throw new WebServiceException("Error al listar clientes "+ex.getMessage());
        }
    }
}
