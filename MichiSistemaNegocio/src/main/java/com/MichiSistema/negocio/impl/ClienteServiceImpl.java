

package com.MichiSistema.negocio.impl;

import com.MichiSistema.Enum.TipoCliente;
import com.MichiSistema.dominio.Cliente;
import com.MichiSistema.negocio.ClienteService;
import com.MichiSistema.persistencia.CRUD.ClienteCRUD;
import com.MichiSistema.persistencia.dao.ClienteDAO;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Ariana Mulatillo Gomez
 */
public class ClienteServiceImpl implements ClienteService {

    private final ClienteDAO clienteDAO;
    public ClienteServiceImpl() {
        this.clienteDAO = new ClienteCRUD();  
    }

    @Override
    public void registrarCliente(Cliente cliente) throws Exception {
        // Validaciones de negocio
        if (cliente.getNombres() == null || cliente.getNombres().trim().isEmpty()) {
            throw new Exception("El nombre del cliente no puede estar vacío");
        }
        if (cliente.getApellidos() == null || cliente.getApellidos().trim().isEmpty()) {
            throw new Exception("El apellido del cliente no puede estar vacío");
        }
        if (cliente.getCelular() <= 0) {
            throw new Exception("El celular debe ser un valor válido");
        }
        if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
            throw new Exception("El email del cliente no puede estar vacío");
        }
        if (!cliente.getEmail().contains("@")) {
            throw new Exception("El email del cliente debe contener el símbolo '@'");
        }
        if (cliente.getTipoCliente() == null) {
            throw new Exception("El tipo de cliente no puede ser nulo");
        }

        // Insertar cliente
        clienteDAO.insertar(cliente);
    }

    @Override
    public void actualizarCliente(Cliente cliente) throws Exception {
        // Validar que el cliente exista
        if (clienteDAO.obtenerPorId(cliente.getPersona_id()) == null) {
            throw new Exception("El cliente no existe");
        }

        // Validaciones de negocio
        if (cliente.getNombres() == null || cliente.getNombres().trim().isEmpty()) {
            throw new Exception("El nombre del cliente no puede estar vacío");
        }
        if (cliente.getApellidos() == null || cliente.getApellidos().trim().isEmpty()) {
            throw new Exception("El apellido del cliente no puede estar vacío");
        }
        if (cliente.getCelular() <= 0) {
            throw new Exception("El celular debe ser un valor válido");
        }
        if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
            throw new Exception("El email del cliente no puede estar vacío");
        }
        if (cliente.getTipoCliente() == null) {
            throw new Exception("El tipo de cliente no puede ser nulo");
        }

        // Actualizar cliente
        clienteDAO.actualizar(cliente);
    }

    @Override
    public void eliminarCliente(int idCliente) throws Exception {
        // Validar que el cliente exista
        Cliente cliente = clienteDAO.obtenerPorId(idCliente);
        if (cliente == null) {
            throw new Exception("El cliente no existe");
        }
        cliente.setEstado(false); 
        clienteDAO.eliminar(idCliente);
    }

    @Override
    public Cliente obtenerCliente(int idCliente) throws Exception {
        // Obtener cliente por ID
        Cliente cliente = clienteDAO.obtenerPorId(idCliente);
        if (cliente == null) {
            throw new Exception("Cliente no encontrado");
        }
        return cliente;
    }

    @Override
    public ArrayList<Cliente> listarClientes() throws Exception {
        // Obtener todos los clientes
        ArrayList<Cliente> clientes= new ArrayList<>();
    
        try {
         
            clientes = (ArrayList<Cliente>) clienteDAO.obtenerTodos(); 
        }catch (Exception e) {
            
            throw new Exception("Error inesperado al obtener clientes", e);
        }
        return clientes;
    }

    @Override
    public ArrayList<Cliente> listarClientesActivos() throws Exception {
        ArrayList<Cliente> clientesActivos = new ArrayList<>();
    
        try {
         
            clientesActivos = (ArrayList<Cliente>) clienteDAO.obtenerActivos(); 
        }catch (Exception e) {
            
            throw new Exception("Error inesperado al obtener clientes activos", e);
        }
        return clientesActivos;
    }   

    @Override
    public ArrayList<Cliente> listarPorNombreClientes(String nombre) throws Exception {
        ArrayList<Cliente> clientesEncontrados = new ArrayList<>();
    
        try {
         
            clientesEncontrados = (ArrayList<Cliente>) clienteDAO.buscarPorNombre(nombre); 
        }catch (Exception e) {
            
            throw new Exception("Error inesperado al obtener clientes por nombre", e);
        }
        return clientesEncontrados;
    }
    
    @Override
    public ArrayList<Cliente> listarPorTipoClientes(TipoCliente tipoCliente) throws Exception {
        ArrayList<Cliente> clientes = new ArrayList<>();

        try {
            // Lógica para obtener los clientes por tipoCliente (similar a lo que hace obtenerPorTipoProducto)
            // Aquí deberías implementar la llamada a productoDAO o el método correspondiente para obtener los clientes
            clientes = (ArrayList<Cliente>) clienteDAO.obtenerPorTipoIDCliente(tipoCliente);
        } catch (Exception e) {
            // Manejo de la excepción si ocurre algún error
            System.err.println("Error inesperado al obtener clientes por tipo: " + tipoCliente);
            // Imprime la traza de la excepción
        }

        return clientes;
    }

}
