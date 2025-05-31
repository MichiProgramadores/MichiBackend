/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package pe.edu.pucp.MichiSistema.NEGOCIOImpl;
import pe.edu.pucp.MichiSistema.dominio.Trabajador;
import pe.edu.pucp.MichiSistema.NEGOCIO.TrabajadorService;
import pe.edu.pucp.MichiSistema.persistencia.DAOimpl.TrabajadorCRUD;
import pe.edu.pucp.MichiSistema.persistencia.DAO.TrabajadorDAO;

/**
 *
 * @author Ariana Mulatillo Gomez
 */
import java.util.ArrayList;

public class TrabajadorServiceImpl implements TrabajadorService {
    
    private final TrabajadorDAO trabajadorDAO;
    
    public TrabajadorServiceImpl() {
        this.trabajadorDAO = new TrabajadorCRUD(); // Asumiendo que el CRUD es el que interactúa con la base de datos
    }
    
    @Override
    public void registrarTrabajador(Trabajador trabajador) throws Exception {
        // Validaciones de negocio
        if (trabajador.getNombres() == null || trabajador.getNombres().trim().isEmpty()) {
            throw new Exception("El nombre del trabajador no puede estar vacío");
        }
        if (trabajador.getTipoTrabajador() == null) {
            throw new Exception("El tipo de trabajador debe ser especificado");
        }

        // Insertar en la base de datos
        trabajadorDAO.insertar(trabajador);
    }
    
    @Override
    public void actualizarTrabajador(Trabajador trabajador) throws Exception {
        
        // Validar que el trabajador exista
        if (trabajadorDAO.obtenerPorId(trabajador.getPersona_id()) == null) {
            throw new Exception("El trabajador no existe");
        }
        
        // Validaciones de negocio
        if (trabajador.getNombres() == null || trabajador.getNombres().trim().isEmpty()) {
            throw new Exception("El nombre del trabajador no puede estar vacío");
        }
        if (trabajador.getTipoTrabajador() == null) {
            throw new Exception("El tipo de trabajador debe ser especificado");
        }

        // Actualizar en la base de datos
        trabajadorDAO.actualizar(trabajador);
    }
    
    @Override
    public void eliminarTrabajador(int idTrabajador) throws Exception {
        Trabajador trabajador = trabajadorDAO.obtenerPorId(idTrabajador);
        
        if (trabajador == null) {
            throw new Exception("El trabajador no existe");
        }
        
        // En lugar de eliminar físicamente, cambiamos el estado a inactivo
        trabajador.setEstado(false); 
        trabajadorDAO.eliminar(idTrabajador);
    }
    
    @Override
    public Trabajador obtenerTrabajador(int idTrabajador) throws Exception {
        Trabajador trabajador = trabajadorDAO.obtenerPorId(idTrabajador);
        
        if (trabajador == null) {
            throw new Exception("Trabajador no encontrado");
        }
        
        return trabajador;
    }
    
    @Override
    public ArrayList<Trabajador> listarTrabajadores() throws Exception {
        return (ArrayList<Trabajador>) trabajadorDAO.obtenerTodos();
    }
}

