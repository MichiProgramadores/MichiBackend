/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.MichiSistema.negocio.impl;

import com.MichiSistema.dominio.Comprobante;
import com.MichiSistema.dominio.DetalleComprobante;
import com.MichiSistema.dominio.Evento;
import com.MichiSistema.dominio.Producto;
import com.MichiSistema.negocio.ComprobanteService;
import com.MichiSistema.persistencia.CRUD.ClienteCRUD;
import com.MichiSistema.persistencia.CRUD.ComprobanteCRUD;
import com.MichiSistema.persistencia.CRUD.EventoCRUD;
import com.MichiSistema.persistencia.CRUD.ProductoCRUD;
import com.MichiSistema.persistencia.CRUD.TrabajadorCRUD;
import com.MichiSistema.persistencia.dao.ClienteDAO;
import com.MichiSistema.persistencia.dao.ComprobanteDAO;
import com.MichiSistema.persistencia.dao.EventoDAO;
import com.MichiSistema.persistencia.dao.ProductoDAO;
import com.MichiSistema.persistencia.dao.TrabajadorDAO;
import com.MichiSistema.dominio.DetalleComprobante;

import java.util.ArrayList;
import java.util.List;

public class ComprobanteServiceImpl implements ComprobanteService {
    private final ComprobanteDAO comprobanteDAO;
    private final ProductoDAO productoDAO;
    private final ClienteDAO clienteDAO;
    private final TrabajadorDAO trabajadorDAO;
    
    public ComprobanteServiceImpl() {
        this.comprobanteDAO = new ComprobanteCRUD();  
        this.productoDAO = new ProductoCRUD();
        this.clienteDAO = new ClienteCRUD();
        this.trabajadorDAO = new TrabajadorCRUD();
        
    }

    @Override
    public void registrarComprobante(Comprobante comprobante) throws Exception {
        if(comprobante.getFecha_emision() == null){
            throw new Exception("La fecha del comprobante no puede estar vacía");
        }
        if(comprobante.getMonto_total() <=0 ){
            throw new Exception("El monto total debe ser un número positivo");
        }
        if(comprobante.getTax()<0 ){
            throw new Exception("Taxes debe ser un número positivo");
        }
        
// Validar que exista el cliente
        if (clienteDAO.obtenerPorId(comprobante.getCliente_id())==null){
            throw new Exception("El cliente no existe");
        }
//        // Validar que exista el empleado
//        if (empleadoDAO.obtenerPorId(venta.getEmpleado().getId()) == null) {
//            throw new Exception("El empleado no existe");
//        }
//        
        for(DetalleComprobante detalle : comprobante.getDetalles() ){
            Producto producto = productoDAO.obtenerPorId(detalle.getProducto_id());
            if(producto == null ){
                throw new Exception("Producto no encontrado: " + detalle.getProducto_id());
            }
            if (producto.getStockActual()< detalle.getCantidad()) {
                throw new Exception("Stock insuficiente para el producto: " + producto.getNombre());
            }
        }
        
        
         comprobanteDAO.insertar(comprobante);
    }

    @Override
    public void actualizarComprobante(Comprobante comprobante) throws Exception {
        if(comprobanteDAO.obtenerPorId(comprobante.getId_comprobante())==null){
            throw new Exception("El comprobante no existe");
        }
        // Validaciones de negocio
        if (comprobante.getMonto_total()<= 0) {
            throw new Exception("El total de la venta debe ser mayor a 0");
        }
        
        //comprobanteDAO.actualizarEstado(comprobante, "FACTURADO");
        comprobanteDAO.actualizar(comprobante);
        
    }

    @Override
    public void eliminarComprobante(int idComprobante) throws Exception {
        Comprobante comprobante = comprobanteDAO.obtenerPorId(idComprobante);
        if(comprobante == null){
            throw new Exception("La venta no existe");
        }
        if (comprobante.getEstado().equals("PAGADO")) {
            throw new Exception("No se puede eliminar un comprobante pagado");
        }
        
        comprobanteDAO.actualizarEstado(comprobante, "ELIMINADO");

    }

    @Override
    public Comprobante obtenerComprobante(int idComprobante) throws Exception {
        Comprobante comprobante = comprobanteDAO.obtenerPorId(idComprobante);
        if(comprobante ==null){
             throw new Exception("Comprobante no encontrado");
        }
        return comprobante;
    }

    @Override
    public List<Comprobante> listarComprobante() throws Exception {
        return comprobanteDAO.obtenerTodos();
    }

    @Override
    public void actualizarEstadoComprobante(Comprobante comprobante, String estado) {

        comprobanteDAO.actualizarEstado(comprobante, estado);
    }
}
