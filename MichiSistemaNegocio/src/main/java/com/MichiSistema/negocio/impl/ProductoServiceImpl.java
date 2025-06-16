
package com.MichiSistema.negocio.impl;

import com.MichiSistema.Enum.TipoProducto;
import com.MichiSistema.dominio.Producto;
import com.MichiSistema.negocio.ProductoService;
import com.MichiSistema.persistencia.CRUD.ProductoCRUD;
import com.MichiSistema.persistencia.dao.ProductoDAO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 *
 * @author rober
 */

public class ProductoServiceImpl implements ProductoService {
    
    private final ProductoDAO productoDAO;
    
    public ProductoServiceImpl() {
        this.productoDAO = new ProductoCRUD();
    }
    
    @Override
    public void registrarProducto(Producto producto) throws Exception {
        // Validaciones de negocio
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre del producto no puede estar vacío");
        }
        if (producto.getPrecio() <= 0) {
            throw new Exception("El precio debe ser mayor a 0");
        }
        
       
        if (producto.getStockActual()< 0) {
            throw new Exception("El stock no puede ser negativo");
        }
        productoDAO.insertar(producto);
        
    }
    
    @Override
    public void actualizarProducto(Producto producto) throws Exception {
        
        // Validar que el producto exista
        if (productoDAO.obtenerPorId(producto.getProducto_id()) == null) {
            
            throw new Exception("El producto no existe");
        }
        
        // Validaciones de negocio
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre del producto no puede estar vacío");
        }
        if (producto.getPrecio() <= 0) {
            throw new Exception("El precio debe ser mayor a 0");
        }

        if (producto.getStockActual()< 0) {
            throw new Exception("El stock no puede ser negativo");
        }
        
        productoDAO.actualizar(producto);
        
    }
    
    @Override
    public void eliminarProducto(int idProducto) throws Exception {
        
        Producto producto = productoDAO.obtenerPorId(idProducto);
        if (producto == null) {
            throw new Exception("El producto no existe");
        }     
         //En lugar de eliminar físicamente, cambiamos el estado a Inactivo
//        producto.setEstado(false);
//        productoDAO.actualizar(producto);
        productoDAO.eliminar(idProducto);
    }
    
    @Override
    public Producto obtenerProducto(int idProducto) throws Exception {
        Producto producto = productoDAO.obtenerPorId(idProducto);
        if (producto == null) {
            throw new Exception("Producto no encontrado");
        }
        return producto;
    }
    
    @Override
    public ArrayList<Producto> listarProductos() throws Exception {
        ArrayList<Producto> productos = new ArrayList<>();
    
        try {
         
            productos = (ArrayList<Producto>) productoDAO.obtenerTodos(); 
        }catch (Exception e) {
            
            throw new Exception("Error inesperado al obtener productos", e);
        }
        return productos;
    }
    
    @Override
    public ArrayList<String> listarTipoProductos() throws Exception {
        //arrayList o list?
        return (ArrayList<String>) Arrays.stream(TipoProducto.values())
                 .map(Enum::name)
                 .collect(Collectors.toList());
    }
    @Override
    public ArrayList<Producto> listarPorTipoProductos(TipoProducto tipoProducto) throws Exception {
        
        return (ArrayList<Producto>) productoDAO.obtenerPorTipoProducto(tipoProducto);
    }

    @Override
    public ArrayList<Producto> listarProductosActivos() throws Exception {
        ArrayList<Producto> productosActivos = new ArrayList<>();
    
        try {
         
            productosActivos = (ArrayList<Producto>) productoDAO.obtenerActivos(); 
        }catch (Exception e) {
            
            throw new Exception("Error inesperado al obtener productos activos", e);
        }
        return productosActivos;
    }

    @Override
    public ArrayList<Producto> listarPorNombreProductos(String nombre) throws Exception {
        ArrayList<Producto> productosEncontrados = new ArrayList<>();
    
        try {
            productosEncontrados = (ArrayList<Producto>) productoDAO.buscarPorNombre(nombre); 
        }catch (Exception e) {
            
            throw new Exception("Error inesperado al obtener productos por nombrei", e);
        }
        return productosEncontrados;
    }
    
    
    
}
