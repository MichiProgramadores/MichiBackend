
package com.MichiSistema.persistencia.dao;

import com.MichiSistema.Enum.TipoEstadoDevolucion;
import com.MichiSistema.Enum.TipoFechaDevolucion;
import com.MichiSistema.dominio.Orden;
import java.sql.Date;


/**
 *
 * @author rober
 */
public interface OrdenDAO extends BaseDAO<Orden> {
   void actualizarTipoEstadoDevolucion(Integer orden_id,TipoEstadoDevolucion estado);
    void actualizarSaldoOrden(Integer orden_id,Double  monto);
    void actualizarFechaEntrega(Integer orden_id,Date  fecha);
    void actualizarFechaEmision(Integer orden_id,Date  fecha);
    void actualizarFechaDevolucion(Integer orden_id,Date  fecha, TipoFechaDevolucion tipo);
    void actualizarDetalleOrden(Integer orden_id,Integer cantidad);
}
