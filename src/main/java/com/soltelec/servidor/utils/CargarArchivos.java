/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.utils;

import java.io.InputStream;

/**
 *
 * @author Luis Berna
 */
public class CargarArchivos {

    /**
     * Metos encargada de cargar archivos del classLoader
     *
     * @param resourceName Nombre del archivo a buscar
     * @return
     */
    public static InputStream cargarArchivo(String resourceName) {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            return loader.getResourceAsStream(resourceName);
        } catch (Exception e) {
            System.out.println("-----------------------------error");
           throw new RuntimeException(e);
            
        }
    }

}
