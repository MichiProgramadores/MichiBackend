package com.michisistemaws;

import com.MichiSistema.dominio.Usuario;
import com.MichiSistema.negocio.UsuarioService;
import com.MichiSistema.negocio.impl.UsuarioServiceImpl;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.WebServiceException;
import java.sql.SQLException;
import java.util.List;


@WebService(serviceName = "UsuarioWS", targetNamespace = "com.MichiSistema")
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
     @WebMethod(operationName = "registrarUsuario")
    public void registrarUsuario(@WebParam(name = "usuario") Usuario usuario) {
        try {
            usuarioService.registrarUsuario(usuario);
            //return "Usuario registrado exitosamente";
        } catch (Exception ex) {
            throw new WebServiceException("Error al registrar usuario: " + ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "actualizarUsuario")
    public void actualizarUsuario(@WebParam(name = "usuario") Usuario usuario) {
        try {
            usuarioService.actualizarUsuario(usuario);
            //return "Usuario actualizado exitosamente";
        } catch (Exception ex) {
            throw new WebServiceException("Error al actualizar usuario: " + ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "eliminarUsuario")
    public void eliminarUsuario(@WebParam(name = "idUsuario") int idUsuario) {
        try {
            usuarioService.eliminarUsuario(idUsuario);
        } catch (Exception ex) {
            throw new WebServiceException("Error al eliminar usuario: " + ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "obtenerUsuario")
    public Usuario obtenerUsuario(@WebParam(name = "idUsuario") int idUsuario) {
        try {
            Usuario usuario = usuarioService.obtenerUsuario(idUsuario);
            if (usuario == null) {
                throw new WebServiceException("Usuario no encontrado con ID: " + idUsuario);
            }
            return usuario;
        } catch (Exception ex) {
            throw new WebServiceException("Error al obtener usuario: " + ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "autenticarUsuario")
    public int autenticarUsuario(
            @WebParam(name = "nombreUsuario") String nombreUsuario,
            @WebParam(name = "contraseña") String contraseña) {
        try {
            int id = usuarioService.autenticar(nombreUsuario, contraseña);
            if (id==0) {
               // throw new WebServiceException("Autenticación fallida: Credenciales inválidas");
                return 0;
            }
            return id;
        } catch (Exception ex) {
            return 0;
        }
    }
}
