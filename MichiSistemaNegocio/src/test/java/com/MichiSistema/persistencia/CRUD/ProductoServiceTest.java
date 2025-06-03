
package com.MichiSistema.persistencia.CRUD;

import com.MichiSistema.Enum.TipoProducto;
import com.MichiSistema.Enum.UnidadMedida;
import com.MichiSistema.dominio.Producto;
import com.MichiSistema.negocio.ProductoService;
import com.MichiSistema.negocio.impl.ProductoServiceImpl;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Ariana Mulatillo Gomez
 */
public class ProductoServiceTest {
    private static ProductoService productoService;
    private static int productoId;

    @BeforeAll
    public static void setUp() {
        productoService = new ProductoServiceImpl();
    }

    private static Producto crearProductoPrueba() {
        return new Producto("Laptop Dell XPS 13", 1299.99, 10, 100, 10, TipoProducto.DECORACION, 300, "producto de decoracion", UnidadMedida.PULGADA);
    }

    @Test
    @Order(1)
    void registrarProducto() throws Exception {
        Producto producto = crearProductoPrueba();
        productoService.registrarProducto(producto);
        System.out.println("Producto insertado:" + producto);

        List<Producto> productos = productoService.listarProductos();

        assertNotNull(productos);
        assertFalse(productos.isEmpty());

        Producto productoRegistrado = null;
        for(Producto p : productos){
            if(p.getNombre().equals(producto.getNombre())){
                productoRegistrado = p;
            }
        }
        assertNotNull(productoRegistrado);
        //productoId = productoRegistrado.getProducto_id();
        productoId =producto.getProducto_id();
        assertEquals(producto.getNombre(), productoRegistrado.getNombre());
        assertEquals(producto.getPrecio(), productoRegistrado.getPrecio());
    }

    @Test
    @Order(2)
    void obtenerProducto() throws Exception {
        Producto producto = crearProductoPrueba();
        productoService.registrarProducto(producto);
        productoId =producto.getProducto_id(); 
        Producto productoObt = productoService.obtenerProducto(productoId);
        assertNotNull(productoObt);
        assertEquals("Laptop Dell XPS 13", productoObt.getNombre());
        assertEquals(1299.99, productoObt.getPrecio());
    }

    @Test
    @Order(3)
    void actualizarProducto() throws Exception {
        Producto producto = crearProductoPrueba();
        productoService.registrarProducto(producto);
        productoId =producto.getProducto_id();
        producto.setNombre("Laptop Dell XPS 15");
        producto.setPrecio(1599.99);
        productoService.actualizarProducto(producto);

        Producto productoActualizado = productoService.obtenerProducto(productoId);
        assertNotNull(productoActualizado);
        assertEquals("Laptop Dell XPS 15", productoActualizado.getNombre());
        assertEquals(1599.99, productoActualizado.getPrecio());
    }

    @Test
    @Order(4)
    void eliminarProducto() throws Exception {
        Producto producto = crearProductoPrueba();
        productoService.registrarProducto(producto);
        productoId =producto.getProducto_id();
        
        productoService.eliminarProducto(productoId);        
        Producto productoEliminado = productoService.obtenerProducto(productoId);
        assertNotNull(productoEliminado);
                   
    }
    
    
}
