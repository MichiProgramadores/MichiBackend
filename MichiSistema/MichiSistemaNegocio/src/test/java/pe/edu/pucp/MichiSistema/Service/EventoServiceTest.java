/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package pe.edu.pucp.MichiSistema.Service;

/**
 *
 * @author Ariana Mulatillo Gomez
 */

import pe.edu.pucp.MichiSistema.Enum.TipoEvento;
import pe.edu.pucp.MichiSistema.dominio.Evento;
import pe.edu.pucp.MichiSistema.NEGOCIO.EventoService;
import pe.edu.pucp.MichiSistema.NEGOCIOImpl.EventoServiceImpl;
import java.time.LocalDate;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import java.util.List;

public class EventoServiceTest {
    
    private static EventoService eventoService;
    private static int eventoId;

    @BeforeAll
    public static void setUp() {
        eventoService = new EventoServiceImpl();
    }

    private static Evento crearEventoPrueba() {
        // Creación de un evento de prueba
        return new Evento(TipoEvento.BODA, LocalDate.now(), LocalDate.now().plusDays(1),
                          LocalTime.of(10, 0), LocalTime.of(18, 0), "Dirección ejemplo", "12345");
    }

    @Test
    @Order(1)
    void registrarEvento() throws Exception {
        // Crear un evento de prueba y registrarlo
        Evento evento = crearEventoPrueba();
        eventoService.registrarEvento(evento);
        System.out.println("Evento insertado:" + evento);

        List<Evento> eventos = eventoService.listarEventos();

        assertNotNull(eventos);
        assertFalse(eventos.isEmpty());

        Evento eventoRegistrado = null;
        for (Evento e : eventos) {
            if (e.getTipoEvento().equals(evento.getTipoEvento())) {
                eventoRegistrado = e;
            }
        }
        assertNotNull(eventoRegistrado);
        eventoId =evento.getEvento_id(); // Asignar el ID al evento insertado (ajustar según la lógica de generación de ID)
        assertEquals(evento.getTipoEvento(), eventoRegistrado.getTipoEvento());
        assertEquals(evento.getFechaInicio(), eventoRegistrado.getFechaInicio());
    }

    @Test
    @Order(2)
    void obtenerEvento() throws Exception {
        Evento evento = crearEventoPrueba();
        eventoService.registrarEvento(evento);
        eventoId =evento.getEvento_id(); 
        // Obtener el evento previamente registrado
        Evento eventoObt = eventoService.obtenerEvento(eventoId);
        assertNotNull(eventoObt);
        assertEquals(TipoEvento.BODA, eventoObt.getTipoEvento());
        assertEquals(LocalDate.now(), eventoObt.getFechaInicio());
    }

    @Test
    @Order(3)
    void actualizarEvento() throws Exception {
        Evento evento = crearEventoPrueba();
        eventoService.registrarEvento(evento);
        eventoId =evento.getEvento_id();         
        evento.setTipoEvento(TipoEvento.BODA); // Cambiar el tipo de evento
        evento.setDireccion("Nueva dirección");
        eventoService.actualizarEvento(evento);
        
        Evento eventoActualizado = eventoService.obtenerEvento(eventoId);
        assertNotNull(eventoActualizado);
        assertEquals(TipoEvento.BODA, eventoActualizado.getTipoEvento());
        assertEquals("Nueva dirección", eventoActualizado.getDireccion());
    }

    @Test
    @Order(4)
    void eliminarEvento() throws Exception {
        Evento evento = crearEventoPrueba();
        eventoService.registrarEvento(evento);
        eventoId =evento.getEvento_id();
        // Eliminar el evento
        eventoService.eliminarEvento(eventoId);              
    }
}
