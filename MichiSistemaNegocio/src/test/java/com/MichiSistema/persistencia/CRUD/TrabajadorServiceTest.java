package com.MichiSistema.persistencia.CRUD;
import com.MichiSistema.Enum.TipoTrabajador;
import com.MichiSistema.dominio.Trabajador;
import com.MichiSistema.negocio.TrabajadorService;
import com.MichiSistema.negocio.impl.TrabajadorServiceImpl;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.List;

/**
 *
 * @author Ariana Mulatillo Gomez
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TrabajadorServiceTest {

    private static TrabajadorService trabajadorService;
    private static int trabajadorId ; // Se asumirá que el trabajador se inserta correctamente con este ID

    @BeforeAll
    public static void setUp() {
        trabajadorService = new TrabajadorServiceImpl();
    }

    private static Trabajador crearTrabajadorPrueba() {
        // Crear un trabajador de prueba
        return new Trabajador("Carlos", "Sanchez", 987654321, "carlos.sanchez@example.com", TipoTrabajador.DESPACHADOR);
    }

    @Test
    @Order(1)
            @Disabled("Deshabilitado temporalmente para pruebas")
    void registrarTrabajador() throws Exception {
        Trabajador trabajador = crearTrabajadorPrueba();
        trabajadorService.registrarTrabajador(trabajador);
        System.out.println("Trabajador insertado:" + trabajador);

        List<Trabajador> trabajadores = trabajadorService.listarTrabajadores();

        assertNotNull(trabajadores);
        assertFalse(trabajadores.isEmpty());

        Trabajador trabajadorRegistrado = null;
        for (Trabajador t : trabajadores) {
            if (t.getNombres().equals(trabajador.getNombres())) {
                trabajadorRegistrado = t;
            }
        }
        assertNotNull(trabajadorRegistrado);
        trabajadorId = trabajador.getPersona_id(); 
        assertEquals(trabajador.getNombres(), trabajadorRegistrado.getNombres());
        assertEquals(trabajador.getCelular(), trabajadorRegistrado.getCelular());
    }

    @Test
    @Order(2)
            @Disabled("Deshabilitado temporalmente para pruebas")
    void obtenerTrabajador() throws Exception {
        Trabajador trabajador = crearTrabajadorPrueba();
        trabajadorService.registrarTrabajador(trabajador);
        trabajadorId = trabajador.getPersona_id();
        
        Trabajador trabajadorObt = trabajadorService.obtenerTrabajador(trabajadorId);
        assertNotNull(trabajadorObt);
        assertEquals("Carlos", trabajadorObt.getNombres());
        assertEquals(987654321, trabajadorObt.getCelular());
    }

    @Test
    @Order(3)
            @Disabled("Deshabilitado temporalmente para pruebas")
    void actualizarTrabajador() throws Exception {
        Trabajador trabajador = crearTrabajadorPrueba();
        trabajadorService.registrarTrabajador(trabajador);
        trabajadorId = trabajador.getPersona_id();
        
        Trabajador trabajadorObt = trabajadorService.obtenerTrabajador(trabajadorId);
        trabajadorObt.setNombres("Juan");
        trabajadorObt.setCelular(123456789);
        trabajadorService.actualizarTrabajador(trabajadorObt);

        Trabajador trabajadorActualizado = trabajadorService.obtenerTrabajador(trabajadorId);
        assertNotNull(trabajadorActualizado);
        assertEquals("Juan", trabajadorActualizado.getNombres());
        assertEquals(123456789, trabajadorActualizado.getCelular());
    }

    @Test
    @Order(4)
            @Disabled("Deshabilitado temporalmente para pruebas")
    void eliminarTrabajador() throws Exception {
        Trabajador trabajador = crearTrabajadorPrueba();
        trabajadorService.registrarTrabajador(trabajador);
        trabajadorId = trabajador.getPersona_id();
        
        trabajadorService.eliminarTrabajador(trabajadorId);        
    }
}

