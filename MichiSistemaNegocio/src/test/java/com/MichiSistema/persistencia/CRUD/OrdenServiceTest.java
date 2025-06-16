/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.MichiSistema.persistencia.CRUD;


import com.MichiSistema.Enum.TipoCliente;
import com.MichiSistema.Enum.TipoFechaDevolucion;
import com.MichiSistema.Enum.TipoProducto;
import com.MichiSistema.Enum.TipoRecepcion;
import com.MichiSistema.Enum.TipoTrabajador;
import com.MichiSistema.Enum.UnidadMedida;
import com.MichiSistema.dominio.Cliente;
import com.MichiSistema.dominio.DetalleOrden;
import com.MichiSistema.dominio.EstadoFechaDevolucion;
import com.MichiSistema.dominio.Orden;
import com.MichiSistema.dominio.Producto;
import com.MichiSistema.dominio.Trabajador;
import com.MichiSistema.dominio.Usuario;
import com.MichiSistema.negocio.ClienteService;
import com.MichiSistema.negocio.impl.OrdenServiceImpl;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import com.MichiSistema.negocio.OrdenService;
import com.MichiSistema.negocio.ProductoService;
import com.MichiSistema.negocio.TrabajadorService;
import com.MichiSistema.negocio.impl.ClienteServiceImpl;
import com.MichiSistema.negocio.impl.ProductoServiceImpl;
import com.MichiSistema.negocio.impl.TrabajadorServiceImpl;
import java.sql.Date;
import java.time.Instant;
import java.util.HashSet;
import org.junit.jupiter.api.Disabled;
//import pe.edu.pucp.MichiSistema.persistencia.DAOimpl.EstadoFechaDevolucionCRUD;


/**
 *
 * @author Usuario
 */
public class OrdenServiceTest {
    private static OrdenService ordenService;
    private static ClienteService clienteService;
    private static TrabajadorService trabajadorService;
    private static ProductoService productoService;
    private static int ordenId;
    //private Producto producto;
    
    @BeforeAll
    public static void setUp() throws Exception {
        ordenService = new OrdenServiceImpl();
        clienteService = new ClienteServiceImpl();
        trabajadorService = new TrabajadorServiceImpl();
        productoService= new ProductoServiceImpl();
    }
    private Producto crearProductoPrueba()throws Exception {
        Producto producto= new Producto("Inflables", 1299.99, 10, 100, 10, TipoProducto.DECORACION, 300, 
                "producto de decoracion", UnidadMedida.PULGADA);
        productoService.registrarProducto(producto);
        return producto;
    }
    private Cliente crearClientePrueba() throws Exception {
        Cliente cliente = new Cliente("Juan", "Perez", 959777957,
                "123@hotmail.com", TipoCliente.SSN, 25, "409-52-2002");
        clienteService.registrarCliente(cliente);
        return cliente;
    }

    private Trabajador crearTrabajadorPrueba() throws Exception {
        Trabajador trabajador = new Trabajador("Carlos", "Sanchez", 987654321,
                "trabajador@example.com", TipoTrabajador.DESPACHADOR);
        trabajadorService.registrarTrabajador(trabajador);
        return trabajador;
    }
    
    private EstadoFechaDevolucion crearEstadoFechaDevolucionPrueba() throws Exception {
    EstadoFechaDevolucionCRUD crud = new EstadoFechaDevolucionCRUD();
    EstadoFechaDevolucion estado = new EstadoFechaDevolucion();
    estado.setTipoFechaDevolucion(TipoFechaDevolucion.DEVUELTO_FECHA_LIMITE);
    estado.setPenalidad(24.0);//penalidad asignada
//    estado.setPorcentajeDesc(15);
        
    crud.insertar(estado); // 👈 esto guardará el estado en BD
    return estado;

    
    }   
     
   private Orden crearOrdenPrueba() throws Exception {
    Cliente cliente = crearClientePrueba();
    Trabajador trabajador = crearTrabajadorPrueba();
    EstadoFechaDevolucion estado = crearEstadoFechaDevolucionPrueba(); 
    Producto producto=crearProductoPrueba();
    
    DetalleOrden detalle = new DetalleOrden();
       detalle.setProducto(producto.getProducto_id());
       detalle.setCantidadSolicitada(2);
       detalle.setPrecioAsignado(producto.getPrecio());
       detalle.setUnidadMedida(producto.getUnidadMedida());
       if(producto.getStockActual()>detalle.getCantidadSolicitada())
            detalle.setCantidadEntregada(detalle.getCantidadSolicitada());
       else detalle.setCantidadSolicitada(producto.getStockActual());
     //HOLA
    
    Orden orden = new Orden(
            TipoRecepcion.DELIVERY,
            "set up personal",
            350,
            200.5,
            3,
            new java.util.Date(),
            new java.util.Date(),
            cliente.getPersona_id(),
            trabajador.getPersona_id()
    );
    orden.setEstado(estado);   // 
//    orden.aplicarDescuentoPorEstado();
    orden.getListaOrdenes().add(detalle);
    
    return orden;
}

    @Test
    @Order(1)
            @Disabled("Deshabilitado temporalmente para pruebas")
    void registrarOrden() throws Exception {
        // Crear un evento de prueba y registrarlo
        
        Orden orden = crearOrdenPrueba();
        ordenService.registrarOrden(orden);
        System.out.println("Orden insertada:" + orden);
        
        List<Orden> ordenes = ordenService.listarOrdenes();

        assertNotNull(ordenes);
        assertFalse(ordenes.isEmpty());
        Orden ordenRegistrada = null;
        for (Orden e : ordenes) {
            if (e.getTipoRecepcion().equals(orden.getTipoRecepcion())) {
                ordenRegistrada = e;
            }
        }
        assertNotNull(ordenRegistrada);
        ordenId =orden.getIdOrden(); // Asignar el ID al evento insertado (ajustar según la lógica de generación de ID)
        assertEquals(orden.getTipoRecepcion(), ordenRegistrada.getTipoRecepcion());
        //assertEquals(orden.getFecha_emisión(), ordenRegistrada.getFecha_emisión());
    }

    @Test
    @Order(2)
            @Disabled("Deshabilitado temporalmente para pruebas")
    void obtenerOrden() throws Exception {
        Orden orden = crearOrdenPrueba();
        ordenService.registrarOrden(orden);
        ordenId = orden.getIdOrden();

        // Obtener la orden previamente registrada
        Orden ordenObt = ordenService.obtenerOrden(ordenId);
        assertNotNull(ordenObt);
        assertEquals(TipoRecepcion.DELIVERY, ordenObt.getTipoRecepcion());
    }

    @Test
    @Order(3)
            @Disabled("Deshabilitado temporalmente para pruebas")
    void actualizarOrden() throws Exception {
        Orden orden = crearOrdenPrueba();
        ordenService.registrarOrden(orden);
        ordenId = orden.getIdOrden();

        // Cambiar valores para actualizar
        orden.setSetUpPersonalizado("Nueva descripción personalizada");
        orden.setSaldo(150.0);
        ordenService.actualizarOrden(orden);

        Orden ordenActualizada = ordenService.obtenerOrden(ordenId);
        assertNotNull(ordenActualizada);
        assertEquals("Nueva descripción personalizada", ordenActualizada.getSetUpPersonalizado());
        assertEquals(150.0, ordenActualizada.getSaldo());
    }

    @Test
    @Order(4)
            @Disabled("Deshabilitado temporalmente para pruebas")
    void eliminarOrden() throws Exception {
        Orden orden = crearOrdenPrueba();
        ordenService.registrarOrden(orden);
        ordenId = orden.getIdOrden();

        ordenService.eliminarOrden(ordenId);     
        assertNotNull(orden);
    }

}
