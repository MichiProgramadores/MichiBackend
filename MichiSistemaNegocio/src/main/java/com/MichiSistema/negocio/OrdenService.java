/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.MichiSistema.negocio;

import com.MichiSistema.Enum.TipoEstadoDevolucion;
import com.MichiSistema.Enum.TipoFechaDevolucion;
import com.MichiSistema.dominio.Orden;
import java.util.Date;
import java.util.List;

public interface OrdenService{
    void registrarOrden(Orden orden) throws Exception;
    void actualizarOrden(Orden orden) throws Exception;
    void eliminarOrden(int idOrden) throws Exception;
    Orden obtenerOrden(int idOrden) throws Exception;
    List<Orden> listarOrdenes() throws Exception;
//    List<Orden> buscarOrdenesPorCliente(Integer clienteId) throws Exception;
//    List<Orden> buscarOrdenesPorEmpleado(Integer empleadoId) throws Exception;
//    List<Orden> buscarOrdenesPorFecha(Date inicio, Date fin) throws Exception;
    void actualizarEstadoDevolucion(int ventaId, TipoEstadoDevolucion estado) throws Exception;
    void actualizarEstadoFechaDevolucion(Integer ventaId, TipoFechaDevolucion estadoFecha) throws Exception;
}
