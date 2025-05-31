/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
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
@WebService(serviceName = "ProductoWS")
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
}
