/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package com.Michisistema.ws;

import com.MichiSistema.negocio.ProductoService;
import com.MichiSistema.negocio.impl.ProductoServiceImpl;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;

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
}
