/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.soltelec.servidor.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author Usuario
 */
public class UtilPropiedades {

    /**
     * Metodo para cargar una propiedad de un archivo properties
     * @param nombrePropiedad
     * @param nombreArchivo
     * @return el valor de la propiedad
     * @throws java.io.FileNotFoundException
     */
    public static String cargarPropiedad(String nombrePropiedad, String nombreArchivo) throws FileNotFoundException, IOException{
            Properties props = new Properties();
            props.load(new FileInputStream("./" + nombreArchivo));
            return props.getProperty(nombrePropiedad);
    }//end of method cargarPropiedad
    
    /**
     * Metodo que carga de un archivo de propiedades la dirreccion ip del servidor y
     * forma la cadena apropiada para configurar la propiedad javax.persistence.jdbc.url
     * @param nombreArchivo, nombre del archivo en donde esta configurada la direccion ip del servidor
     * @return 
     */
    public static String cargarURLPersistence(String nombreArchivo) throws IOException{
        
        String ip = cargarPropiedad("urljdbc","propiedades.properties");
        StringBuilder sb = new StringBuilder("jdbc:mysql://");
        sb.append(ip).append(":3306/db_cda?zeroDateTimeBehavior=convertToNull");
        return sb.toString();
    }
    
    public static Map<String,String> crearMapaParametrosEMF() throws IOException{
        
        Map<String,String> map = new HashMap<String,String>();
        String url = UtilPropiedades.cargarURLPersistence("propiedades.properties");
        map.put("javax.persistence.jdbc.url", url);
        return map;
    }
}
