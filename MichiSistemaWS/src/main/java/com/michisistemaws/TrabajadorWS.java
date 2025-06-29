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
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
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
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;


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
    
    @WebMethod(operationName = "listaTrabajadoresPorTipoEstado")
    public List<Trabajador> listaTrabajadoresPorTipoEstado(@WebParam(name = "tipo")String tipo,
            @WebParam(name = "estado")String estado) {
        try{
            return trabajadorService.ObtenerPorTipoEstadoTrabajadores(TipoTrabajador.valueOf(tipo), estado);
        }catch(Exception ex){
            throw new WebServiceException("Error al listar trabajadores por tipo y estado "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "listaTrabajadoresPorEstado")
    public List<Trabajador> listaTrabajadoresPorEstado(@WebParam(name = "estado")String estado) {
        try{
            return trabajadorService.ObtenerPorEstadoTrabajadores(estado);
        }catch(Exception ex){
            throw new WebServiceException("Error al listar trabajadores por estado "+ex.getMessage());
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
            @WebParam(name = "ID_BUSCADO") Integer id_buscado,@WebParam(name = "ESTADO")String estado){
        try{
            Map<String, Object> params = new HashMap<>();
            if(tipoTrabajador.equals("GENERAL")){
                params.put("TIPO_TRABAJADOR", null); 
            }else{
                params.put("TIPO_TRABAJADOR", tipoTrabajador); 
            }
             
            params.put("id_buscado", id_buscado);
            if(estado.equals("INACTIVO")){
                params.put("ESTADO", false);
            }else if(estado.equals("ACTIVO")){
                params.put("ESTADO", true);
            }else{
                params.put("ESTADO", null);
            }
            
            if(id_buscado==0){
                params.put("header","REPORTE HISTÓRICO DE TRABAJADORES" );
            }else{
                params.put("header","REPORTE HISTÓRICO DEL TRABAJADOR "+id_buscado );
            }
            params.put("logo",ImageIO.read(new File(getFileResource("logoMichiSistema.png"))));
            String fileXML = getFileResource("TRABAJADORES.jrxml");  
            
            byte[] fileBuffer = generarBufferFromJP(fileXML, params);
            
            if (fileBuffer == null || fileBuffer.length == 0 ||  !isPdfNotEmpty(fileBuffer)) {
            // Si el archivo está vacío, devolver null
                return null;
            }

            // Si el archivo tiene contenido, devolver el buffer
            return fileBuffer;
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
    
    private boolean isPdfNotEmpty(byte[] fileBuffer) {
        try {
            // Crear un InputStream a partir del byte array del PDF
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileBuffer);

            // Cargar el documento PDF desde el InputStream
            PDDocument document = PDDocument.load(byteArrayInputStream);

            // Usar PDFTextStripper para extraer el texto del PDF
            PDFTextStripper stripper = new PDFTextStripper();
            String pdfText = stripper.getText(document);

            // Verificar si el contenido del PDF tiene texto (no está vacío)
            document.close();

            // Si el PDF tiene texto, no está vacío
            return !pdfText.trim().isEmpty();  // Retorna true si tiene texto
        } catch (IOException e) {
            e.printStackTrace();
            return false;  // Si hay un error, asumimos que está vacío
        }
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