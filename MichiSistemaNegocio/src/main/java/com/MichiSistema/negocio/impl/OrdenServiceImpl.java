/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.MichiSistema.negocio.impl;

import com.MichiSistema.Enum.TipoEstadoDevolucion;
import com.MichiSistema.Enum.TipoFechaDevolucion;
import com.MichiSistema.dominio.DetalleOrden;
import com.MichiSistema.dominio.EstadoFechaDevolucion;
import com.MichiSistema.dominio.Orden;
import com.MichiSistema.dominio.Producto;
import com.MichiSistema.negocio.OrdenService;
import com.MichiSistema.persistencia.CRUD.ClienteCRUD;
import com.MichiSistema.persistencia.CRUD.OrdenCRUD;
import com.MichiSistema.persistencia.CRUD.ProductoCRUD;
import com.MichiSistema.persistencia.CRUD.TrabajadorCRUD;
import com.MichiSistema.persistencia.CRUD.UsuarioCRUD;
import com.MichiSistema.persistencia.dao.ClienteDAO;
import com.MichiSistema.persistencia.dao.OrdenDAO;
import com.MichiSistema.persistencia.dao.ProductoDAO;
import com.MichiSistema.persistencia.dao.TrabajadorDAO;
import com.MichiSistema.persistencia.dao.UsuarioDAO;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class OrdenServiceImpl  implements OrdenService{
    private final OrdenDAO ordenDAO;
    private final ProductoDAO productoDAO;
    private final ClienteDAO clienteDAO;
    private final TrabajadorDAO trabajadorDAO;
    public OrdenServiceImpl() {
        this.ordenDAO = new OrdenCRUD();
        this.productoDAO = new ProductoCRUD();
        this.clienteDAO = new ClienteCRUD();
        this.trabajadorDAO = new TrabajadorCRUD();
    }
    
    @Override
    public void registrarOrden(Orden orden) throws Exception {
        // Validar que exista el cliente
        if (clienteDAO.obtenerPorId(orden.getClienteID()) == null) {
            throw new Exception("El cliente no existe");
        }
        
        // Validar que exista el empleado
        if (trabajadorDAO.obtenerPorId(orden.getTrabajadorID()) == null) {
            throw new Exception("El empleado no existe");
        }
        
        // Validar stock de productos
        for (DetalleOrden detalle : orden.getListaOrdenes()) {
            Producto producto = productoDAO.obtenerPorId(detalle.getProducto());
            if (producto == null) {
                throw new Exception("Producto no encontrado: " + detalle.getProducto());
            }
            if (producto.getStockActual()< detalle.getCantidadSolicitada()) {
                throw new Exception("Stock insuficiente para el producto: " + producto.getNombre());
            }
        }
        
        // Registrar la venta
        ordenDAO.insertar(orden);
        
        // Actualizar stock de productos lo realiza el procedimiento almacenado en la base de datos
        // por lo que no es necesario hacerlo aquí.
        /*for (DetalleVenta detalle : venta.getDetalles()) {
            Producto producto = productoDAO.obtener(detalle.getProducto().getId());
            producto.setStock(producto.getStock() - detalle.getCantidad());
            productoDAO.actualizar(producto);
        }*/
    }
    
    @Override
    public void actualizarOrden(Orden orden) throws Exception {
        if (ordenDAO.obtenerPorId(orden.getIdOrden()) == null) {
            throw new Exception("La Orden no existe");
        }
        
        // Validaciones de negocio
        if (orden.getTotalPagar()<= 0) {
            throw new Exception("El total de la venta debe ser mayor a 0");
        }
        
        ordenDAO.actualizar(orden);
    }
    
    @Override
    public void eliminarOrden(int idOrden) throws Exception {
        Orden orden = ordenDAO.obtenerPorId(idOrden);
        if (orden == null) {
            throw new Exception("La Orden no existe");
        }
//        if (orden.getEstado() == TipoFechaDevolucion.) {
//            throw new Exception("No se puede eliminar una venta completada");
//        }
         //Cambiar el estado a Cancelada en lugar de eliminarla físicamente
//        ordenDAO.actualizarEstado(idOrden, Orden.); 
        
        ordenDAO.eliminar(idOrden);
    }
    
    @Override
    public Orden obtenerOrden(int idOrden) throws Exception {
        Orden orden = ordenDAO.obtenerPorId(idOrden);
        if (orden == null) {
            throw new Exception("Orden no encontrada");
        }
        return orden;
    }
    
    @Override
    public List<Orden> listarOrdenes() throws Exception {
        return ordenDAO.obtenerTodos();
    }

    @Override
    public void actualizarEstadoFechaDevolucion(Integer ventaId, TipoFechaDevolucion estadoFecha) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void actualizarEstadoDevolucion(int ventaId, TipoEstadoDevolucion estado) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
