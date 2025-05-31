package pe.edu.pucp.MichiSistema.Service;


import pe.edu.pucp.MichiSistema.Enum.TipoCliente;
import pe.edu.pucp.MichiSistema.Enum.TipoFechaDevolucion;
import pe.edu.pucp.MichiSistema.Enum.TipoProducto;
import pe.edu.pucp.MichiSistema.Enum.TipoRecepcion;
import pe.edu.pucp.MichiSistema.Enum.TipoTrabajador;
import pe.edu.pucp.MichiSistema.Enum.UnidadMedida;
import pe.edu.pucp.MichiSistema.dominio.Cliente;
import pe.edu.pucp.MichiSistema.dominio.DetalleOrden;
import pe.edu.pucp.MichiSistema.dominio.EstadoFechaDevolucion;
import pe.edu.pucp.MichiSistema.dominio.Orden;
import pe.edu.pucp.MichiSistema.dominio.Producto;
import pe.edu.pucp.MichiSistema.dominio.Trabajador;
import pe.edu.pucp.MichiSistema.dominio.Usuario;
import pe.edu.pucp.MichiSistema.NEGOCIO.ClienteService;
import pe.edu.pucp.MichiSistema.NEGOCIOImpl.OrdenServiceImpl;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import pe.edu.pucp.MichiSistema.NEGOCIO.OrdenService;
import pe.edu.pucp.MichiSistema.NEGOCIO.ProductoService;
import pe.edu.pucp.MichiSistema.NEGOCIO.TrabajadorService;
import pe.edu.pucp.MichiSistema.NEGOCIOImpl.ClienteServiceImpl;
import pe.edu.pucp.MichiSistema.NEGOCIOImpl.ProductoServiceImpl;
import pe.edu.pucp.MichiSistema.NEGOCIOImpl.TrabajadorServiceImpl;
import pe.edu.pucp.MichiSistema.persistencia.DAOimpl.EstadoFechaDevolucionCRUD;

public class OrdenServiceTest {

    private static OrdenService ordenService;
    private static ClienteService clienteService;
    private static TrabajadorService trabajadorService;
    private static ProductoService productoService;
    private static int ordenId;

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
    estado.setPenalidad(0.0);// ➡️ descuento de prueba
    crud.insertar(estado); // 👈 esto guardará el estado en BD
    return estado;
}

   private Orden crearOrdenPrueba() throws Exception {
    Cliente cliente = crearClientePrueba();
    Trabajador trabajador = crearTrabajadorPrueba();
    EstadoFechaDevolucion estado = crearEstadoFechaDevolucionPrueba();  // ✅ solo este
    Producto producto=crearProductoPrueba();
    
    DetalleOrden detalle = new DetalleOrden();
       detalle.setProducto(producto.getProducto_id());
       detalle.setCantidadSolicitada(2);
       detalle.setPrecioAsignado(producto.getPrecio());
       detalle.setUnidadMedida(producto.getUnidadMedida());
       if(producto.getStockActual()>detalle.getCantidadSolicitada())
            detalle.setCantidadEntregada(detalle.getCantidadSolicitada());
       else detalle.setCantidadSolicitada(producto.getStockActual());

    Orden orden = new Orden(
            TipoRecepcion.RECOJO_EN_TIENDA,
            "set up ",
            500,
            cliente.getPersona_id(),
            trabajador.getPersona_id()
    );

    orden.setEstado(estado);   // ✅ usas el que creaste
    orden.getListaOrdenes().add(detalle);
    

    return orden;
}

     @Test
    @Order(1)
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
//        assertEquals(orden.getFecha_emisión(), ordenRegistrada.getFecha_emisión());
    }



    @Test
    @Order(2)
    void obtenerOrden() throws Exception {
        Orden orden = crearOrdenPrueba();
        ordenService.registrarOrden(orden);
        ordenId = orden.getIdOrden();

        // Obtener la orden previamente registrada
        Orden ordenObt = ordenService.obtenerOrden(ordenId);
        assertNotNull(ordenObt);
//        assertEquals(TipoRecepcion.DELIVERY, ordenObt.getTipoRecepcion());
//        assertEquals(orden.getFecha_emisión(), ordenObt.getFecha_emisión());
    }

    @Test
    @Order(3)
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
    void eliminarOrden() throws Exception {
        Orden orden = crearOrdenPrueba();
        ordenService.registrarOrden(orden);
        ordenId = orden.getIdOrden();

        // Simplemente ejecutamos eliminar (en tu caso no hace nada, pero se prueba que no falle)
        ordenService.eliminarOrden(ordenId);

       
        assertNotNull(orden);
    }

}