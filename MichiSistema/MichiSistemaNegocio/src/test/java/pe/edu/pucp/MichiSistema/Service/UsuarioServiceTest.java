
package pe.edu.pucp.MichiSistema.Service;
import pe.edu.pucp.MichiSistema.Enum.TipoTrabajador;
import pe.edu.pucp.MichiSistema.dominio.Trabajador;
import pe.edu.pucp.MichiSistema.dominio.Usuario;
import pe.edu.pucp.MichiSistema.NEGOCIO.TrabajadorService;
import pe.edu.pucp.MichiSistema.NEGOCIO.UsuarioService;
import pe.edu.pucp.MichiSistema.NEGOCIOImpl.TrabajadorServiceImpl;
import pe.edu.pucp.MichiSistema.NEGOCIOImpl.UsuarioServiceImpl;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.AfterEach;

import java.sql.SQLException;
import java.util.List;
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
        Trabajador trabajador = new Trabajador("Carlos", "Sanchez", 987654321,
                "trabajador@example.com", TipoTrabajador.DESPACHADOR);
        trabajadorService.registrarTrabajador(trabajador);
        return trabajador;
    }

    // Método auxiliar para crear un usuario de prueba
    private static Usuario crearUsuarioPrueba(int id) {
        // En este caso, se crea el usuario pasando el ID y la contraseña
        return new Usuario(id, "contraseña123");  
    }

    @Test
    @Order(1)
    void testRegistrarUsuario() throws Exception {
        Trabajador trabajador= crearTrabajadorPrueba();
        Usuario usuario = crearUsuarioPrueba(trabajador.getPersona_id());
        
        // Registro del usuario
        usuarioService.registrarUsuario(usuario);

        // Verificación: Obtener todos los usuarios
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        assertNotNull(usuarios);
        assertFalse(usuarios.isEmpty());

        // Confirmamos que el usuario se ha guardado correctamente en la base de datos
        Usuario usuarioRegistrado = usuarioService.obtenerUsuario(usuario.getId());
        assertNotNull(usuarioRegistrado);
        assertEquals(usuario.getNombreUsuario(), usuarioRegistrado.getNombreUsuario());
    }

    @Test
    @Order(2)
    void testAutenticarUsuario() throws Exception {     
        Trabajador trabajador= crearTrabajadorPrueba();
        Usuario usuario = crearUsuarioPrueba(trabajador.getPersona_id());
        usuarioService.registrarUsuario(usuario);
        //nota: id harcoded
        Usuario usuarioAutenticado = usuarioService.autenticar(usuario.getId(), "contraseña123");

        // Verificación
        assertNotNull(usuarioAutenticado);
        assertEquals("user", usuarioAutenticado.getNombreUsuario());
    }

    @Test
    @Order(3)
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
    void testEliminarUsuario() throws Exception {
        Trabajador trabajador= crearTrabajadorPrueba();
        Usuario usuario = crearUsuarioPrueba(trabajador.getPersona_id());
        usuarioService.registrarUsuario(usuario);
        // Eliminar el usuario
        usuarioService.eliminarUsuario(usuario.getId());
    }
}

