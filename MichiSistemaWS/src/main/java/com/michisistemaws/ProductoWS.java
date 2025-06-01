package com.michisistemaws;

import com.MichiSistema.dominio.Producto;
import com.MichiSistema.negocio.ProductoService;
import com.MichiSistema.negocio.impl.ProductoServiceImpl;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.WebServiceException;
import java.util.List;

/**
 *
 * @author OsquiCnapi
 */
@WebService(serviceName = "ProductoWS", targetNamespace = "com.MichiSistema")
public class ProductoWS {

    private ProductoService productoService;
    
    public ProductoWS(){
        productoService=new ProductoServiceImpl();
    }
        
    @WebMethod(operationName = "listarProductos")
    public List<Producto> listaProductos() {
        try{
            return productoService.listarProductos();
        }catch(Exception ex){
            throw new WebServiceException("Error al listar productos "+ex.getMessage());
        }
    }
    @WebMethod(operationName = "registrarProducto")
    public void registrarProducto(@WebParam(name = "producto") Producto producto) {
        try{
            productoService.registrarProducto(producto);
        }catch(Exception ex){
            throw new WebServiceException("Error al registrar productos "+ex.getMessage());
        }
    }
    @WebMethod(operationName = "obtenerProducto")
    public Producto obtenerProducto(@WebParam(name = "idProducto") int idProducto) {
        try{
            return productoService.obtenerProducto(idProducto);
        }catch(Exception ex){
            throw new WebServiceException("Error al obtener producto "+ex.getMessage());
        }
    }
    @WebMethod(operationName = "eliminarProducto")
    public void eliminarProducto(@WebParam(name = "idProducto") int idProducto) {
        try{
            productoService.obtenerProducto(idProducto);
        }catch(Exception ex){
            throw new WebServiceException("Error al obtener producto "+ex.getMessage());
        }
    }
    @WebMethod(operationName = "actualizarProducto")
    public void actualizarProducto(@WebParam(name = "producto") Producto producto) {
        try{
            productoService.actualizarProducto(producto);
        }catch(Exception ex){
            throw new WebServiceException("Error al actualizar producto "+ex.getMessage());
        }
    }
    
    
}