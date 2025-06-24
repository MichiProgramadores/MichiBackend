package com.michisistemaws;

import com.MichiSistema.Enum.TipoTrabajador;
import com.MichiSistema.conexion.DBManager;
import com.MichiSistema.dominio.Trabajador;
import com.MichiSistema.negocio.TrabajadorService;
import com.MichiSistema.negocio.impl.TrabajadorServiceImpl;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.WebServiceException;
import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;


@WebService(serviceName = "TrabajadorWS", targetNamespace = "com.MichiSistema")
public class TrabajadorWS {

    private TrabajadorService trabajadorService;
    
    public TrabajadorWS(){
        trabajadorService=new TrabajadorServiceImpl();
    }
    
    @WebMethod(operationName = "listarTrabajadores")
    public List<Trabajador> listaTrabajadores() {
        try{
            return trabajadorService.listarTrabajadores();
        }catch(Exception ex){
            throw new WebServiceException("Error al listar trabajadores "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "listaTrabajadoresActivos")
    public List<Trabajador> listaTrabajadoresActivos() {
        try{
            return trabajadorService.listarTrabajadoresActivos();
        }catch(Exception ex){
            throw new WebServiceException("Error al listar trabajadores activos "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "listaTrabajadoresPorNombre")
    public List<Trabajador> listaTrabajadoresPorNombre(@WebParam(name = "nombre")String cadena) {
        try{
            return trabajadorService.listarPorNombreTrabajadores(cadena);
        }catch(Exception ex){
            throw new WebServiceException("Error al listar trabajadores por nombre "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "registrarTrabajador")
    public void registrarTrabajador(@WebParam(name = "trabajador") Trabajador trabajador,
            @WebParam(name = "str_tipoTrabajador") String str_tipoTrabajador) {
        try {
            trabajador.setTipoTrabajador(TipoTrabajador.valueOf(str_tipoTrabajador));
            trabajadorService.registrarTrabajador(trabajador);
           // return "Trabajador registrado exitosamente";
        } catch (Exception ex) {
            throw new WebServiceException("Error al registrar trabajador: " + ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "actualizarTrabajador")
    public void actualizarTrabajador(@WebParam(name = "trabajador") Trabajador trabajador) {
        try {
            trabajadorService.actualizarTrabajador(trabajador);
            //return "Trabajador actualizado exitosamente";
        } catch (Exception ex) {
            throw new WebServiceException("Error al actualizar trabajador: " + ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "eliminarTrabajador")
    public void eliminarTrabajador(@WebParam(name = "idTrabajador") int idTrabajador) {
        try {
            trabajadorService.eliminarTrabajador(idTrabajador);
        } catch (Exception ex) {
            throw new WebServiceException("Error al eliminar trabajador: " + ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "obtenerTrabajador")
    public Trabajador obtenerTrabajador(@WebParam(name = "idTrabajador") int idTrabajador) {
        try {
            return trabajadorService.obtenerTrabajador(idTrabajador);
        } catch (Exception ex) {
            throw new WebServiceException("Error al obtener trabajador: " + ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "reporteTrabajadores")
    public byte[] reporteTrabajadores(@WebParam(name = "TIPO_TRABAJADOR") String tipoTrabajador,
            @WebParam(name = "ID_BUSCADO") Integer id_buscado){
        try{
            Map<String, Object> params = new HashMap<>();
            params.put("TIPO_TRABAJADOR", tipoTrabajador);  
            params.put("id_buscado", id_buscado);
            if(id_buscado==0){
                params.put("header","REPORTE HISTÓRICO DE TRABAJADORES" );
            }else{
                params.put("header","REPORTE HISTÓRICO DEL TRABAJADOR "+id_buscado );
            }
            params.put("logo",ImageIO.read(new File(getFileResource("logoMichiSistema.png"))));
            String fileXML = getFileResource("TRABAJADORES.jrxml");            
            return generarBufferFromJP(fileXML, params);
        }catch(Exception ex){
            throw new WebServiceException("Error al generar report: " + ex.getMessage());
        }
    }
    
    private String getFileResource(String fileName){ 
        String filePath = getClass().getClassLoader().getResource(fileName).getPath();
        filePath = filePath.replace("%20", " ");
        return filePath;
    }

    private byte[] generarBufferFromJP(String inFileXML, Map<String, Object> params) throws JRException {
        
        String fileJasper = inFileXML +".jasper";
        if(!new File(fileJasper).exists()){
            
            JasperCompileManager.compileReportToFile(inFileXML, fileJasper);         
        }
        
        JasperReport jr = (JasperReport) JRLoader.loadObjectFromFile(fileJasper);
        
        Connection conn = DBManager.getInstance().obtenerConexion();
        JasperPrint jp = JasperFillManager.fillReport(jr,params, conn);          
        return JasperExportManager.exportReportToPdf(jp);
    }
    
    @WebMethod(operationName = "listarTrabajadoresPorTipo")
    public List<Trabajador> listarPorTipoTrabajador(@WebParam(name = "tipoTrabajador") String tipo) {
        try {
            // Llamada al servicio que obtiene los trabajadores por tipo
            return trabajadorService.listarPorTipoTrabajadores(TipoTrabajador.valueOf(tipo));
        } catch (Exception ex) {
            // Captura cualquier error y lanza una WebServiceException con el mensaje de error
            throw new WebServiceException("Error al listar trabajadores por tipo: " + ex.getMessage());
        }
    }

    
}