/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.dao;

import com.soltelec.servidor.utils.CargarArchivos;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lberna
 */
public class ReadFiles {

    public static List<Archivo> cargarRegistros() {

        try {
            List<Archivo> archivos = new ArrayList<>();
    
            Properties p = new Properties();
            p.load(CargarArchivos.cargarArchivo("conexion.properties"));
    
            // Obtener el directorio de backup desde las propiedades
            File backupDirectory = new File(p.getProperty("directoriobackup"));
    
            // Verificar si el directorio no existe
            if (!backupDirectory.exists()) {
                // Intentar crear el directorio si no existe
                if (backupDirectory.mkdirs()) {
                    System.out.println("El directorio de backup no existía, pero se creó correctamente.");
                } else {
                    throw new IOException("No se pudo crear el directorio de backup: " + p.getProperty("directoriobackup"));
                }
            }
    
            // Verificar que sea un directorio válido
            if (!backupDirectory.isDirectory()) {
                throw new IOException("La ruta especificada no es un directorio: " + p.getProperty("directoriobackup"));
            }
    
            // Listar los archivos del directorio
            File[] listOfFiles = backupDirectory.listFiles();
    
            // Verificar si listOfFiles es nulo (directorio vacío o error al acceder)
            if (listOfFiles == null) {
                System.out.println("No se encontraron archivos en el directorio de backup.");
                return archivos; // Retornar una lista vacía si no hay archivos
            }
    
            // Agregar archivos a la lista
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    archivos.add(new Archivo(file.getName(), file.getPath()));
                }
            }
            
            return archivos;
        } catch (IOException ex) {
            Logger.getLogger(ReadFiles.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
