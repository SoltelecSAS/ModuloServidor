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

            File[] listOfFiles = new File(p.getProperty("directoriobackup")).listFiles();
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    archivos.add(new Archivo(file.getName(), file.getPath()));
                }
            }
            return (archivos);
        } catch (IOException ex) {
            Logger.getLogger(ReadFiles.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
