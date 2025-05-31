
package com.MichiSistema.persistencia.dao;
import com.MichiSistema.dominio.Comprobante;
import com.MichiSistema.persistencia.dao.BaseDAO;


/**
 *
 * @author rober
 */
public interface ComprobanteDAO extends BaseDAO<Comprobante>{
   public void actualizarEstado(int id_comprobante, String estado);
   
}
