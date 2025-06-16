
package com.MichiSistema.persistencia.CRUD;
import com.MichiSistema.Enum.TipoTrabajador;
import com.MichiSistema.dominio.Trabajador;
import com.MichiSistema.dominio.Usuario;
import com.MichiSistema.negocio.TrabajadorService;
import com.MichiSistema.negocio.UsuarioService;
import com.MichiSistema.negocio.impl.TrabajadorServiceImpl;
import com.MichiSistema.negocio.impl.UsuarioServiceImpl;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.AfterEach;

import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.Disabled;
/**
 *
 * @author Ariana Mulatillo Gomez
 */
public class UsuarioServiceTest {
    
    private static UsuarioService usuarioService;
    private static TrabajadorService trabajadorService;

    @BeforeAll
    public static void setUp() {
        usuarioService = new UsuarioServiceImpl(); 
        trabajadorService= new TrabajadorServiceImpl();
    }
    private Trabajador crearTrabajadorPrueba() throws Exception {
        Trabajador trabajador = new Trabajador("Chizuru", "Wang", 987654321,
                "user@example.com", TipoTrabajador.DESPACHADOR);
        trabajadorService.registrarTrabajador(trabajador);
        return trabajador;
    }

    // Método auxiliar para crear un usuario de prueba
    private static Usuario crearUsuarioPrueba(int id) {
        // En este caso, se crea el usuario pasando el ID y la contraseña
        return new Usuario(id, "contrasena123");  
    }

    @Test
    @Order(1)
            @Disabled("Deshabilitado temporalmente para pruebas")
    void testRegistrarUsuario() throws Exception {
        Trabajador trabajador= crearTrabajadorPrueba();
        Usuario usuario = crearUsuarioPrueba(trabajador.getPersona_id());
        
        // Registro del usuario
        usuarioService.registrarUsuario(usuario);

    }

    @Test
    @Order(2)
            @Disabled("Deshabilitado temporalmente para pruebas")
    void testAutenticarUsuario() throws Exception {     
        Trabajador trabajador= crearTrabajadorPrueba();
        Usuario usuario = crearUsuarioPrueba(trabajador.getPersona_id());
        usuarioService.registrarUsuario(usuario);
        //nota: id harcoded
        Usuario usuarioAutenticado = usuarioService.autenticar(usuario.getNombreUsuario(), "contraseña123");

        // Verificación
        assertNotNull(usuarioAutenticado);
      
    }

    @Test
    @Order(3)
            @Disabled("Deshabilitado temporalmente para pruebas")
    void testActualizarUsuario() throws Exception {
        Trabajador trabajador= crearTrabajadorPrueba();
        Usuario usuario1 = crearUsuarioPrueba(trabajador.getPersona_id());
        usuarioService.registrarUsuario(usuario1);
       
        // Obtener el usuario previamente registrado
        Usuario usuario = usuarioService.obtenerUsuario(usuario1.getId());
        
         //Modificar el nombre de usuario
        usuario.setNombreUsuario("CarlosGomez");
        usuarioService.actualizarUsuario(usuario);

         //Verificar que los cambios se hayan guardado correctamente
        Usuario usuarioActualizado = usuarioService.obtenerUsuario(usuario.getId());
        assertNotNull(usuarioActualizado);
        assertEquals("CarlosGomez", usuarioActualizado.getNombreUsuario());
    }

    @Test
    @Order(4)
            @Disabled("Deshabilitado temporalmente para pruebas")
    void testEliminarUsuario() throws Exception {
        Trabajador trabajador= crearTrabajadorPrueba();
        Usuario usuario = crearUsuarioPrueba(trabajador.getPersona_id());
        usuarioService.registrarUsuario(usuario);
        // Eliminar el usuario
        usuarioService.eliminarUsuario(usuario.getId());
    }
}

