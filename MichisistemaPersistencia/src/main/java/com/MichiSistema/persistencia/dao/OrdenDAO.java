
package com.MichiSistema.persistencia.dao;

import com.MichiSistema.Enum.TipoEstadoDevolucion;
import com.MichiSistema.dominio.Orden;



/**
 *
 * @author rober
 */
public interface OrdenDAO extends BaseDAO<Orden> {
    public void eliminar(int idOrden);
    public Orden obtenerPorId(int idOrden);   
   // public void actualizar(Orden orden);
    public void actualizarCantidadEntregada(Orden orden);
    public void actualizarEstadoDevolucion(int idOrden, TipoEstadoDevolucion estado);
}
