/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.soltelec.servidor.utils;

import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author SOLTELEC
 */
public final class Validaciones {



        // Validación de si una cadena es un número
        public static boolean isNumeric(String cadena){
                try {
                        Integer.parseInt(cadena);
                        return true;
                } catch (NumberFormatException nfe){
                        return false;
                }
        }

        // Validacion para que los campos solo aceptes numeros.
        public static void soloNumeros(java.awt.event.KeyEvent e) {
            char caracter = e.getKeyChar();
            if(((caracter < '0') || (caracter > '9')) && (caracter != java.awt.event.KeyEvent.VK_BACK_SPACE)) {
            e.consume();
            } 
        }
        
        /*
         * Metodo para comprobar que ningun campo este vacio y sin seleccionar
         * 
         * @param contenedor Es el componente contendor del cual se tomaran los campos         * 
         */
        public static boolean validarCampos(JComponent contenedor) {
            Component[] campos = contenedor.getComponents();
            boolean estado = true;            
            //bucle para iterar la variable campos y revisar cada componente
            for (Component campo: campos) {                
                //validar si es un JTextField
                if (campo instanceof JTextField) {
                    //validar si los campos estan vacios
                    if (((JTextField)campo).getText().isEmpty()) {
                        estado = false;
                        break;
                    }
                //validar si es un ComboBox    
                } else if (campo instanceof JComboBox) {
                    //validar si el elemento seleccionado es el indice 0
                    if (((JComboBox)campo).getSelectedIndex() == 0) {
                        estado = false;
                        break;
                    }
                }
            }// fin del bucle for
            
            if (!estado) {
                JOptionPane.showMessageDialog(contenedor, "Hay Campos Sin Llenar o Seleccionar", "Campos Vacios", JOptionPane.WARNING_MESSAGE);
            }
            
            return estado;
        }
        
        /*
         * Metodo para limitar la cantidad de caracteres ingresados en algun determinado campo
         * 
         * @param e Es el que contiene el evento ejercido sobre el componente
         * @param campo Es el campo al cual vamos a limitar
         * @param cantidadPermitida Es un entero con la cantidad de caracteres permitidos
         */
        public static void limitarCaracteres(java.awt.event.KeyEvent e, JTextField campo, int cantidadPermitida) {
            if (campo.getText().length() == cantidadPermitida) {
                e.consume();
            }
        }
        
        /*
         * 
         */
        public static void ordenarListas(List lista, final String propiedad) {
            Collections.sort(lista, new Comparator() {

                @Override
                public int compare(Object obj1, Object obj2) {
                    Class clase = obj1.getClass();
                    String getter = "get" + Character.toUpperCase(propiedad.charAt(0))+propiedad.substring(1);
                    try {
                        Method getPropiedad = clase.getMethod(getter);

                        Object propiedad1 = getPropiedad.invoke(obj1);
                        Object propiedad2 = getPropiedad.invoke(obj2);

                        if (propiedad1 instanceof Comparable && propiedad2 instanceof Comparable) {
                            Comparable prop1 = (Comparable) propiedad1;
                            Comparable prop2 = (Comparable) propiedad2;

                            return prop1.compareTo(prop2);
                        } else {
                            if (propiedad1.equals(propiedad2)) {
                                return 0;
                            } else {
                                return 1;
                            }
                        }

                    } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    }
                    return 0;
                }
            });
        }

}
