/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.MichiSistema.persistencia.CRUD;

import com.MichiSistema.Enum.TipoCliente;
import com.MichiSistema.Enum.TipoProducto;
import com.MichiSistema.Enum.TipoRecepcion;
import com.MichiSistema.Enum.TipoTrabajador;
import com.MichiSistema.Enum.UnidadMedida;
import com.MichiSistema.dominio.Cliente;
import com.MichiSistema.dominio.Comprobante;
import com.MichiSistema.dominio.DetalleComprobante;
import com.MichiSistema.dominio.EstadoFechaDevolucion;
import com.MichiSistema.dominio.Orden;
import com.MichiSistema.dominio.Producto;
import com.MichiSistema.dominio.Trabajador;
import com.MichiSistema.dominio.Usuario;
import com.MichiSistema.negocio.ClienteService;
import com.MichiSistema.negocio.ComprobanteService;
import com.MichiSistema.negocio.OrdenService;
import com.MichiSistema.negocio.ProductoService;
import com.MichiSistema.negocio.UsuarioService;
import com.MichiSistema.negocio.impl.ClienteServiceImpl;
import com.MichiSistema.negocio.impl.ComprobanteServiceImpl;
import com.MichiSistema.negocio.impl.OrdenServiceImpl;
import com.MichiSistema.negocio.impl.ProductoServiceImpl;
import com.MichiSistema.negocio.impl.UsuarioServiceImpl;
import com.MichiSistema.persistencia.dao.OrdenDAO;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Usuario
 */
public class ComprobanteServiceTest {
    private ComprobanteService comprobanteService;
    private UsuarioService usuarioService;
    private ProductoService productoService;
    private ClienteService clienteService;
    private Producto producto;
    private Trabajador trabajador;
    private OrdenService ordenService;
    private Cliente cliente;
    private Orden orden;
    private static int clienteId;
    @BeforeEach
       
    public void setUp() {
        comprobanteService = new ComprobanteServiceImpl();
        clienteService = new ClienteServiceImpl();
       // usuarioService = new UsuarioServiceImpl();
        productoService = new ProductoServiceImpl();
        ordenService =  new OrdenServiceImpl();   
    }
    private int generarCelular() {
        // Generar un número aleatorio entre 600,000,000 y 699,999,999
        return 600000000 + new Random().nextInt(100000000); // 600000000 + (0 to 99999999)
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

    private Orden crearOrdenPrueba(int id) throws Exception {   
    Orden orden = new Orden();
    orden.setTipoRecepcion(TipoRecepcion.RECOJO_EN_TIENDA); // Usa uno de tus enums
    orden.setFecha_registro(LocalDateTime.now());
    orden.setSetUpPersonalizado("Sin configuración especial");
    orden.setTotalPagar(100.0);  
    orden.setSaldo(0.0);
    orden.setCantDias(1);
    orden.setFecha_devolucion(Date.valueOf(LocalDate.now().plusDays(1)));
    orden.setFecha_entrega(LocalDate.now());
    orden.setFecha_emisión(LocalDate.now());
    orden.setClienteID(id);  // Cliente válido
    // Si no se tiene un trabajador creado, debes poner uno válido o crearlo antes
    orden.setTrabajadorID(13);  
    ordenService.registrarOrden(orden);
    return orden;
    }
    
    private Comprobante crearComprobantePrueba() throws Exception {
        Cliente cliente = crearClientePrueba();
        Producto producto=crearProductoPrueba();
        Orden orden=crearOrdenPrueba(cliente.getPersona_id());
        Comprobante comprobante = new Comprobante();
        comprobante.setCliente_id(cliente.getPersona_id());
       // comprobante.setUsuario(usuario);
        comprobante.setOrden_id(orden.getIdOrden()); 
        DetalleComprobante detalle = new DetalleComprobante();
        detalle.setProducto_id(producto.getProducto_id());
        detalle.setCantidad(2);
        detalle.setSubtotal(producto.getPrecio()*detalle.getCantidad());
        
        comprobante.getDetalles().add(detalle);
        
        comprobante.setMonto_total(detalle.getSubtotal());//¿con Sales Tax o no?
        //comprobante.setTax(0.08* comprobante.getMonto_total());
        return comprobante;
    }

    @Test
    public void testRegistrarComprobante() throws Exception {
        Comprobante comprobante = crearComprobantePrueba();      
        comprobanteService.registrarComprobante(comprobante);     
        assertTrue(comprobante.getId_comprobante()> 0);   
        Comprobante comprobanteObtenido = comprobanteService.obtenerComprobante(comprobante.getId_comprobante());
        assertNotNull(comprobanteObtenido);
        assertEquals(comprobante.getCliente_id(), comprobanteObtenido.getCliente_id());
        assertEquals(comprobante.getMonto_total(), comprobanteObtenido.getMonto_total());
   }

    @Test
    public void testActualizarEstado() throws Exception {
        Comprobante comprobante = crearComprobantePrueba();
        comprobanteService.registrarComprobante(comprobante);  
        comprobante.setEstado("FACTURADO");
        comprobanteService.actualizarComprobante(comprobante);
        
        //comprobanteService.actualizarEstadoComprobante(comprobante.getId_comprobante(), "FACTURADO");      
        Comprobante comprobanteActualizado = comprobanteService.obtenerComprobante(comprobante.getId_comprobante());
        assertEquals("FACTURADO", comprobanteActualizado.getEstado().toUpperCase());
    }

    @Test
    public void testCancelarVenta() throws Exception {
       // TODO: Implementar testCancelarVenta      
    }

    @Test
    public void testListarVentasPorCliente() throws Exception {
      // TODO: Implementar testListarVentasPorCliente
    }

    @Test
    public void testListarVentasPorEmpleado() throws Exception {
       // TODO: Implementar testListarVentasPorEmpleado
    }

    @Test
    public void testListarVentasPorFecha() throws Exception {
       // TODO: Implementar testListarVentasPorFecha
    }
            
}
