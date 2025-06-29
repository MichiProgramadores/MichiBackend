/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package com.michisistemaws;

import com.MichiSistema.Enum.TipoEstadoDevolucion;
import com.MichiSistema.Enum.TipoFechaDevolucion;
import com.MichiSistema.Enum.TipoRecepcion;
import com.MichiSistema.conexion.DBManager;
import com.MichiSistema.dominio.Orden;
import com.MichiSistema.negocio.OrdenService;
import com.MichiSistema.negocio.impl.OrdenServiceImpl;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.WebServiceException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
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

/**
 *
 * @author OsquiCnapi
 */
@WebService(serviceName = "OrdenWS")
public class OrdenWS {

    private OrdenService ordenService;
    
    public OrdenWS(){
        ordenService=new OrdenServiceImpl();
    }
    
    @WebMethod(operationName = "listarOrdenes")
    public List<Orden> listarOrdenes() {
        try{
            return ordenService.listarOrdenes();
        }catch(Exception ex){
            throw new WebServiceException("Error al listar orden "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "registrarOrden")
    public void registrarOrden(@WebParam(name = "orden") Orden orden,@WebParam(name = "str_tipoRecepcion") String str_tipoRecepcion) {
        try{
            orden.setTipoRecepcion(TipoRecepcion.valueOf(str_tipoRecepcion));
            ordenService.registrarOrden(orden);
        }catch(Exception ex){
            throw new WebServiceException("Error al registrar orden "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "actualizarOrden")
    public void actualizarOrden(@WebParam(name = "orden") Orden orden, @WebParam(name = "str_tipoRecepcion") String str_tipoRecepcion) {
        try{
            orden.setTipoRecepcion(TipoRecepcion.valueOf(str_tipoRecepcion));
            ordenService.actualizarOrden(orden);
        }catch(Exception ex){
            throw new WebServiceException("Error al actualizar orden "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "eliminarOrden")
    public void eliminarOrden(@WebParam(name = "idOrden") int idOrden) {
        try{
            ordenService.eliminarOrden(idOrden);
        }catch(Exception ex){
            throw new WebServiceException("Error al eliminar orden "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "obtenerOrden")
    public Orden obtenerOrden(@WebParam(name = "idOrden") int idOrden) {
        try{
            
           // return ordenService.obtenerOrden(idOrden);
           Orden orden = ordenService.obtenerOrden(idOrden);

        // Verifica en consola
            if (orden.getListaOrdenes() == null) {
                System.out.println(" listaOrdenes ES NULL en backend");
            } else {
                System.out.println(" listaOrdenes size: " + orden.getListaOrdenes().size());
            }

                return orden;
        }catch(Exception ex){
            throw new WebServiceException("Error al obtener orden "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "actualizarEstadoDevolucion")
    public void actualizarEstadoOrden(@WebParam(name = "idOrden") int idOrden,
           @WebParam(name = "str_tipoEstadoDevol") String str_tipoEstadoDevol) {
        try{
            TipoEstadoDevolucion estado = TipoEstadoDevolucion.valueOf(str_tipoEstadoDevol);
            ordenService.actualizarEstadoDevolucion(idOrden, estado);
        }catch(Exception ex){
            throw new WebServiceException("Error al actualizar el estado de devolucion de la orden "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "actualizarEstadoFechaDevolucion")
    public void actualizarEstadoFechaDevolucion(@WebParam(name = "idOrden") Integer idOrden,
            @WebParam(name = "estado") TipoFechaDevolucion estadoFecha) {
        try{
            ordenService.actualizarEstadoFechaDevolucion(idOrden, estadoFecha);
        }catch(Exception ex){
            throw new WebServiceException("Error al actualizar el estado de la fecha de devolucion de la orden "+ex.getMessage());
        }
    }
    @WebMethod(operationName = "listarTipoRecepcion")
    public List<String> listarTipoRecepcion() {
        try{
            return ordenService.listarTipoRecepcion();
        }catch(Exception ex){
            throw new WebServiceException("Error al listar tipo recepcion "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "listarOrdenesPorTipo")
    public List<Orden> listarPorTipo(@WebParam(name = "tipoRecepcion") String tipo) {
        try{
            return ordenService.listarPorTipoRecepcion(TipoRecepcion.valueOf(tipo));
        }catch(Exception ex){
            throw new WebServiceException("Error al listar ordenes por tipo "+ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "actualizarSaldoCero")
    public void actualizarSaldoCero(@WebParam(name = "idOrden") int idOrden) {
        try{
            ordenService.actualizarSaldoCero(idOrden);
        }catch(Exception ex){
            throw new WebServiceException("Error al listar ordenes por tipo "+ex.getMessage());
        }
    }
    @WebMethod(operationName = "reporteRenta")
    public byte[] reporteRenta(@WebParam(name = "fecha_inicio") Date fechaInicio,
            @WebParam(name = "fecha_fin") Date fechafin){
        try{
            // Convertir java.util.Date a java.sql.Date si es necesario
            java.sql.Date sqlFechaInicio = new java.sql.Date(fechaInicio.getTime());
            java.sql.Date sqlFechaFin = new java.sql.Date(fechafin.getTime());

            Map<String, Object> params = new HashMap<>();
            params.put("fecha_inicio", sqlFechaInicio);  
            params.put("fecha_fin", sqlFechaFin);
            params.put("logo", ImageIO.read(new File(getFileResource("logoMichiSistema.png"))));

            String fileXML = getFileResource("REPORTERENTA.jrxml");            
            byte[] fileBuffer = generarBufferFromJP(fileXML, params);
            
            if (fileBuffer == null || fileBuffer.length == 0 ||  !isPdfNotEmpty(fileBuffer)) {
            // Si el archivo está vacío, devolver null
                return null;
            }

            // Si el archivo tiene contenido, devolver el buffer
            return fileBuffer;
        } catch(Exception ex) {
            throw new WebServiceException("Error al generar reporte de renta: " + ex.getMessage());
        }
    }
    
    private String getFileResource(String fileName){ 
        String filePath = getClass().getClassLoader().getResource(fileName).getPath();
        filePath = filePath.replace("%20", " ");
        return filePath;
    }

    private byte[] generarBufferFromJP(String inFileXML, Map<String, Object> params) throws JRException, SQLException {
        
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
