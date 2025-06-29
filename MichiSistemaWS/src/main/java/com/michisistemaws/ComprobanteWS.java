/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package com.michisistemaws;

import com.MichiSistema.conexion.DBManager;
import com.MichiSistema.dominio.Comprobante;
import com.MichiSistema.negocio.ComprobanteService;
import com.MichiSistema.negocio.impl.ComprobanteServiceImpl;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.WebServiceException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.Date;
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

@WebService(serviceName = "ComprobanteWS")
public class ComprobanteWS {

    private ComprobanteService comprobanteService;
    
    public ComprobanteWS(){
        comprobanteService=new ComprobanteServiceImpl();
    }
    
    @WebMethod(operationName = "listarComprobante")
    public List<Comprobante> listarComprobante() {
        try{
            return comprobanteService.listarComprobante();
        }catch(Exception ex){
            throw new WebServiceException("Error al listar comprobantes "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "registrarComprobante")
    public void registrarComprobante(@WebParam(name = "comprobante") Comprobante comprobante) {
        try{
            comprobanteService.registrarComprobante(comprobante);
        }catch(Exception ex){
            throw new WebServiceException("Error al registrar comprobante "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "actualizarComprobante")
    public void actualizarComprobante(@WebParam(name = "comprobante") Comprobante comprobante) {
        try{
            comprobanteService.actualizarComprobante(comprobante);
        }catch(Exception ex){
            throw new WebServiceException("Error al actualizar comprobante "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "eliminarComprobante")
    public void eliminarComprobante(@WebParam(name = "idComprobante") int idComprobante) {
        try{
            comprobanteService.eliminarComprobante(idComprobante);
        }catch(Exception ex){
            throw new WebServiceException("Error al eliminar comprobante "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "obtenerComprobante")
    public Comprobante obtenerComprobante(@WebParam(name = "idComprobante") int idComprobante) {
        try{
            return comprobanteService.obtenerComprobante(idComprobante);
        }catch(Exception ex){
            throw new WebServiceException("Error al obtener comprobante "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "obtenerComprobantesPorOrden")
    public List<Comprobante> obtenerComprobantesPorOrden(@WebParam(name = "idOrden") int idOrden) {
        try{
            return comprobanteService.obtenerComprobantesPorOrden(idOrden);
        }catch(Exception ex){
            throw new WebServiceException("Error al obtener comprobante "+ex.getMessage());
        }
    }    
    /*
    @WebMethod(operationName = "actualizarEstadoComprobante")
    public void actualizarEstadoComprobante(@WebParam(name = "idComprobante") int idComprobante,
            @WebParam(name = "toString") String toString) {
        try{
            comprobanteService.actualizarEstadoComprobante(idComprobante, toString);
        }catch(Exception ex){
            throw new WebServiceException("Error al actualizar el estado del comprobante "+ex.getMessage());
        }
    }
    */
    
    @WebMethod(operationName = "reporteFacturacion")
    public byte[] reporteFacturacion(@WebParam(name = "fecha_inicio") Date fechaInicio,
            @WebParam(name = "fecha_fin") Date fechafin){
        try{
            // Convertir java.util.Date a java.sql.Date si es necesario
            java.sql.Date sqlFechaInicio = new java.sql.Date(fechaInicio.getTime());
            java.sql.Date sqlFechaFin = new java.sql.Date(fechafin.getTime());

            Map<String, Object> params = new HashMap<>();
            params.put("fecha_inicio", sqlFechaInicio);  
            params.put("fecha_fin", sqlFechaFin);
            params.put("logo", ImageIO.read(new File(getFileResource("logoMichiSistema.png"))));

            String fileXML = getFileResource("ComprobantesFactura.jrxml");            
            byte[] fileBuffer = generarBufferFromJP(fileXML, params);
            
            if (fileBuffer == null || fileBuffer.length == 0 ||  !isPdfNotEmpty(fileBuffer)) {
            // Si el archivo está vacío, devolver null
                return null;
            }

            // Si el archivo tiene contenido, devolver el buffer
            return fileBuffer;
        } catch(Exception ex) {
            throw new WebServiceException("Error al generar reporte de facturacion: " + ex.getMessage());
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
    
}
