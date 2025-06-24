/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.MichiSistema.negocio.impl;
import com.MichiSistema.Enum.TipoTrabajador;
import com.MichiSistema.dominio.Trabajador;
import com.MichiSistema.negocio.TrabajadorService;
import com.MichiSistema.persistencia.CRUD.TrabajadorCRUD;
import com.MichiSistema.persistencia.dao.TrabajadorDAO;

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
        ArrayList<Trabajador> trabajadores= new ArrayList<>();
    
        try {
         
            trabajadores = (ArrayList<Trabajador>) trabajadorDAO.obtenerTodos(); 
        }catch (Exception e) {
            
            throw new Exception("Error inesperado al obtener trabajadores", e);
        }
        return trabajadores;
    }

    @Override
    public ArrayList<Trabajador> listarTrabajadoresActivos() throws Exception {
        ArrayList<Trabajador> trabajadoresActivos = new ArrayList<>();
    
        try {
         
            trabajadoresActivos = (ArrayList<Trabajador>) trabajadorDAO.obtenerActivos(); 
        }catch (Exception e) {
            
            throw new Exception("Error inesperado al obtener trabajadores activos", e);
        }
        return trabajadoresActivos;
    }

    @Override
    public ArrayList<Trabajador> listarPorNombreTrabajadores(String nombre) throws Exception {
        ArrayList<Trabajador> trabajadoresEncontrados = new ArrayList<>();
    
        try {
         
            trabajadoresEncontrados = (ArrayList<Trabajador>) trabajadorDAO.buscarPorNombre(nombre); 
        }catch (Exception e) {
            
            throw new Exception("Error inesperado al obtener trabajadores por nombre", e);
        }
        return trabajadoresEncontrados;
    }

    @Override
    public ArrayList<Trabajador> listarPorTipoTrabajadores(TipoTrabajador tipoTrabajador) throws Exception {
        ArrayList<Trabajador> trabajadores = new ArrayList<>();

    try {
        // Lógica para obtener los trabajadores por tipoTrabajador
        // Aquí deberías implementar la llamada a trabajadorDAO o el método correspondiente para obtener los trabajadores
        trabajadores = (ArrayList<Trabajador>) trabajadorDAO.obtenerPorTipoTrabajador(tipoTrabajador);
    } catch (Exception e) {
        // Manejo de la excepción si ocurre algún error
        System.err.println("Error inesperado al obtener trabajadores por tipo: " + tipoTrabajador);
        // Imprime la traza de la excepción
        e.printStackTrace();
    }

    return trabajadores;
    }
}

