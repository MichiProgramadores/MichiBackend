
package pe.edu.pucp.MichiSistema.persistencia.DAO;
import pe.edu.pucp.MichiSistema.dominio.Comprobante;
import pe.edu.pucp.MichiSistema.persistencia.DAO.BaseDAO;


/**
 *
 * @author rober
 */
public interface ComprobanteDAO extends BaseDAO<Comprobante>{
   public void actualizarEstado(int id_comprobante, String estado);
   
}
