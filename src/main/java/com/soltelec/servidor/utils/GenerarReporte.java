/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.utils;

import com.soltelec.servidor.dao.Conexion;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.security.AuthProvider;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * Encargado de generar los diferentes tipos de reporte por JasperReport
 *
 * @author Luis Berna
 */
public class GenerarReporte {

    private static final Logger LOG = Logger.getLogger(GenerarReporte.class.getName());

    public static void generarReportePdf(String reporte, Map parametros) {
        
        for(int a = 0; a < parametros.size();a++){
              
        }
        
        try {
            Connection conn = getConexion();
            String archivoPdf = System.getProperty("user.dir").concat("\\sensorial.pdf");
            JasperPrint fillReport = JasperFillManager.fillReport(CargarArchivos.cargarArchivo(reporte), parametros, conn);
            JasperExportManager.exportReportToPdfFile(fillReport, archivoPdf);
            try {
                File path = new File(archivoPdf);
                Desktop.getDesktop().open(path);
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, ex.getMessage());
            }
            LOG.log(Level.INFO, "Se a logrado generar el reporde de inspeccion sensorial");
        } catch (IOException | SQLException | JRException e) {
            LOG.log(Level.SEVERE, e.getMessage());
        }
    }

    private static Connection getConexion() throws IOException, SQLException {
        Properties properties = new Properties();
        properties.load(CargarArchivos.cargarArchivo("conexion.properties"));
        System.out.println(properties.getProperty("urljdbc"));
        System.out.println(properties.getProperty("port"));
        System.out.println(properties.getProperty("namebd"));
        System.out.println(properties.getProperty("password"));
        
        return DriverManager.getConnection(String.format("jdbc:mysql://%s:%s/%s?zeroDateTimeBehavior=convertToNull",
             
                properties.getProperty("urljdbc"),
                properties.getProperty("port"),
                properties.getProperty("namebd")),
                properties.getProperty("usuario"),
                properties.getProperty("password"));
          
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir").concat("\\sensorial.pdf"));
    }

}
