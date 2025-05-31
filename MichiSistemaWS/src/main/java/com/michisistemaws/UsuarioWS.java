/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package com.michisistemaws;

import com.MichiSistema.dominio.Usuario;
import com.MichiSistema.negocio.UsuarioService;
import com.MichiSistema.negocio.impl.UsuarioServiceImpl;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.WebServiceException;
import java.util.List;


@WebService(serviceName = "UsuarioWS")
public class UsuarioWS {

    private UsuarioService usuarioService;
    
    public UsuarioWS(){
        usuarioService=new UsuarioServiceImpl();
    }
    
    @WebMethod(operationName = "listarUsuarios")
    public List<Usuario> listaUsuarios() {
        try{
            return usuarioService.listarUsuarios();
        }catch(Exception ex){
            throw new WebServiceException("Error al listar usuarios "+ex.getMessage());
        }
    }
}
