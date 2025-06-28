
package com.MichiSistema.persistencia.dao;
import com.MichiSistema.dominio.Comprobante;
import com.MichiSistema.persistencia.dao.BaseDAO;
import java.util.List;


/**
 *
 * @author rober
 */
public interface ComprobanteDAO extends BaseDAO<Comprobante>{
    public void actualizar(Comprobante comprobante);
   public void actualizarEstado(Comprobante comprobante, String estado);
   public Comprobante obtenerPorId(int idComprobante);
   public List<Comprobante> obtenerPorIdOrden(int idOrden);
}
