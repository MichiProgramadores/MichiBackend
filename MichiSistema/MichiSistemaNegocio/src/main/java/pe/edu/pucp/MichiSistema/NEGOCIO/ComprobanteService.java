/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.pucp.MichiSistema.NEGOCIO;


import java.util.List;
import pe.edu.pucp.MichiSistema.dominio.Comprobante;
/**
 *
 * @author Gimena
 */
public interface ComprobanteService {
    void registrarComprobante(Comprobante comprobante) throws Exception;
    void actualizarComprobante(Comprobante comprobante) throws Exception;
    void eliminarComprobante(int idComprobante) throws Exception;
    Comprobante obtenerComprobante(int idComprobante) throws Exception;
    List<Comprobante> listarComprobante() throws Exception;

    public void actualizarEstadoComprobante(int id_comprobante, String toString);
}
