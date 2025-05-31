/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.MichiSistema.persistencia.CRUD;

/**
 *
 * @author Ariana Mulatillo Gomez
 */

import com.MichiSistema.Enum.TipoCliente;
import com.MichiSistema.dominio.Cliente;
import com.MichiSistema.negocio.ClienteService;
import com.MichiSistema.negocio.impl.ClienteServiceImpl;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClienteServiceTest {

    private static ClienteService clienteService;
    private static int clienteId; 

    @BeforeAll
    public static void setUp() {
        clienteService = new ClienteServiceImpl();
    }

    private static Cliente crearClientePrueba() {
        // Crear un cliente de prueba
        return new Cliente("Juan", "Perez", 987654321, "juan.perez@example.com", TipoCliente.EIN, 100, "1324");
    }

    @Test
    @Order(1)
   void registrarCliente() throws Exception {
        Cliente cliente = crearClientePrueba();
        clienteService.registrarCliente(cliente);
        System.out.println("Cliente insertado:" + cliente);

        List<Cliente> clientes = clienteService.listarClientes();

        assertNotNull(clientes);
        assertFalse(clientes.isEmpty());

        Cliente clienteRegistrado = null;
        for (Cliente c : clientes) {
            if (c.getNombres().equals(cliente.getNombres())) {
                clienteRegistrado = c;
            }
        }
        assertNotNull(clienteRegistrado);
        clienteId = cliente.getPersona_id();
        assertEquals(cliente.getNombres(), clienteRegistrado.getNombres());
        assertEquals(cliente.getCelular(), clienteRegistrado.getCelular());
    }

    @Test
    @Order(2)
    void obtenerCliente() throws Exception {
        Cliente cliente = crearClientePrueba();
        clienteService.registrarCliente(cliente);
        clienteId = cliente.getPersona_id();
        
        Cliente clienteObt = clienteService.obtenerCliente(clienteId);
        assertNotNull(clienteObt);
        assertEquals("Juan", clienteObt.getNombres());
        assertEquals(987654321, clienteObt.getCelular());
    }

    @Test
    @Order(3)
    void actualizarCliente() throws Exception {
        Cliente cliente = crearClientePrueba();
        clienteService.registrarCliente(cliente);
        clienteId = cliente.getPersona_id();
        
        Cliente clienteObt= clienteService.obtenerCliente(clienteId);
        clienteObt.setNombres("Pedro");
        clienteObt.setCelular(123456789);
        clienteService.actualizarCliente(clienteObt);
        clienteId = cliente.getPersona_id();
        
        Cliente clienteActualizado = clienteService.obtenerCliente(clienteId);
        assertNotNull(clienteActualizado);
        assertEquals("Pedro", clienteActualizado.getNombres());
        assertEquals(123456789, clienteActualizado.getCelular());
    }

    @Test
    @Order(4)
    void eliminarCliente() throws Exception {
        Cliente cliente = crearClientePrueba();
        clienteService.registrarCliente(cliente);
        clienteId = cliente.getPersona_id();
      
        clienteService.eliminarCliente(clienteId);                
    }
}
