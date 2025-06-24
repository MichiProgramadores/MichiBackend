
package com.MichiSistema.persistencia.dao;
import com.MichiSistema.dominio.Comprobante;
import com.MichiSistema.persistencia.dao.BaseDAO;


/**
 *
 * @author rober
 */
public interface ComprobanteDAO extends BaseDAO<Comprobante>{
    public void actualizar(Comprobante comprobante);
   public void actualizarEstado(Comprobante comprobante, String estado);
   public Comprobante obtenerPorId(int idComprobante);
}
