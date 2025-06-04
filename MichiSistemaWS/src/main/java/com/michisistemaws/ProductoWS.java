package com.michisistemaws;

import com.MichiSistema.Enum.TipoProducto;
import com.MichiSistema.Enum.UnidadMedida;
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
    public void registrarProducto(@WebParam(name = "producto") Producto producto,
            @WebParam(name = "str_tipoProducto") String str_tipoProducto,
            @WebParam(name = "str_unidadMedida") String str_unidadMedida) {
        try{
            producto.setCategoriaProducto(TipoProducto.valueOf(str_tipoProducto));
            producto.setUnidadMedida(UnidadMedida.valueOf(str_unidadMedida));
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
            productoService.eliminarProducto(idProducto);
        }catch(Exception ex){
            throw new WebServiceException("Error al eliminar producto "+ex.getMessage());
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
    
    @WebMethod(operationName = "listarTipoProducto")
    public List<String> listarTipoProducto() {
        try{
            return productoService.listarTipoProductos();
        }catch(Exception ex){
            throw new WebServiceException("Error al listar productos "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "listarProductosPorTipo")
    public List<Producto> listarPorTipoProducto(@WebParam(name = "tipoProducto") String tipo) {
        try{
            return productoService.listarPorTipoProductos(TipoProducto.valueOf(tipo));
        }catch(Exception ex){
            throw new WebServiceException("Error al listar productos por tipo "+ex.getMessage());
        }
    }
    
    
}