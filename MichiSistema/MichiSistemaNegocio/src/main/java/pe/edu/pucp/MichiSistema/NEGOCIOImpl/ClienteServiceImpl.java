

package pe.edu.pucp.MichiSistema.NEGOCIOImpl;

import pe.edu.pucp.MichiSistema.dominio.Cliente;
import pe.edu.pucp.MichiSistema.NEGOCIO.ClienteService;
import pe.edu.pucp.MichiSistema.persistencia.DAOimpl.ClienteCRUD;
import pe.edu.pucp.MichiSistema.persistencia.DAO.ClienteDAO;
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
        return (ArrayList<Cliente>) clienteDAO.obtenerTodos();
    }
}
