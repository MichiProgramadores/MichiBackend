
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
import org.junit.jupiter.api.Disabled;
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
        return new Producto("Parlante", 1299.99, 10, 100, 10, TipoProducto.TECNOLOGIA, 300, "JBL 500", UnidadMedida.PULGADA);
    }

    @Test
    @Order(1)
            @Disabled("Deshabilitado temporalmente para pruebas")
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
            @Disabled("Deshabilitado temporalmente para pruebas")
    void obtenerProducto() throws Exception {
        Producto producto = crearProductoPrueba();
        productoService.registrarProducto(producto);
        productoId =producto.getProducto_id(); 
        Producto productoObt = productoService.obtenerProducto(productoId);
        assertNotNull(productoObt);
        assertEquals("PapaHuayro", productoObt.getNombre());
        assertEquals(1299.99, productoObt.getPrecio());
    }

    @Test
    @Order(3)
            @Disabled("Deshabilitado temporalmente para pruebas")
    void actualizarProducto() throws Exception {
        Producto producto = crearProductoPrueba();
        productoService.registrarProducto(producto);
        productoId =producto.getProducto_id();
        producto.setNombre("PapaHuayro");
        producto.setPrecio(1599.99);
        productoService.actualizarProducto(producto);

        Producto productoActualizado = productoService.obtenerProducto(productoId);
        assertNotNull(productoActualizado);
        assertEquals("PapaHuayro", productoActualizado.getNombre());
        assertEquals(1599.99, productoActualizado.getPrecio());
    }

    @Test
    @Order(4)
            @Disabled("Deshabilitado temporalmente para pruebas")
    void eliminarProducto() throws Exception {
        Producto producto = crearProductoPrueba();
        productoService.registrarProducto(producto);
        productoId =producto.getProducto_id();
        
        productoService.eliminarProducto(productoId);        
        Producto productoEliminado = productoService.obtenerProducto(productoId);
        assertNotNull(productoEliminado);
                   
    }
    
    @Test
    @Order(5)
    @Disabled("Deshabilitado temporalmente para pruebas")
    void listarProductosActivos() throws Exception {
        // Crear un producto de prueba
        Producto producto = crearProductoPrueba();
        productoService.registrarProducto(producto);  // Registrar el producto

        // Llamar al método listarProductosActivos() que debe devolver productos activos
        List<Producto> productosActivos = productoService.listarProductosActivos();

        // Verificar que la lista de productos no sea nula y tenga al menos 1 producto
        assertNotNull(productosActivos, "La lista de productos activos no debe ser nula");
        assertFalse(productosActivos.isEmpty(), "La lista de productos activos no debe estar vacía");

        // Verificar que el producto registrado esté presente en la lista de productos activos
        Producto productoRegistrado = null;
        for (Producto p : productosActivos) {
            if (p.getProducto_id() == producto.getProducto_id()) {
                productoRegistrado = p;
                break;
            }
        }

        // Comprobar que el producto registrado está presente en la lista de activos
        assertNotNull(productoRegistrado, "El producto registrado debería estar en la lista de productos activos");
        assertEquals(producto.getNombre(), productoRegistrado.getNombre(), "El nombre del producto debería coincidir");
        assertEquals(producto.getPrecio(), productoRegistrado.getPrecio(), "El precio del producto debería coincidir");
    }
    
    
}
