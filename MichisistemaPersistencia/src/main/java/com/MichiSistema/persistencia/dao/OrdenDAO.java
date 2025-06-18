
package com.MichiSistema.persistencia.dao;

import com.MichiSistema.dominio.Orden;



/**
 *
 * @author rober
 */
public interface OrdenDAO extends BaseDAO<Orden> {
    public void eliminar(int idOrden);
    public Orden obtenerPorId(int idOrden);
}
