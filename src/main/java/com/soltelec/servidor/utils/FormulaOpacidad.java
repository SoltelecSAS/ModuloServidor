/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.utils;


/**
 *
 * @author user
 */
public class FormulaOpacidad {
    
    private static final double I = 0.430d;
    
    public static Double opacidad(Double opacidad) throws ArithmeticException{
        double a = Math.log(1 - (opacidad / 100.0d));
        double K = a / I; 
        return(-1 * K); 
    }
    
    public static String formatearOpacidad(String decimalsPlaces, double valor){
        String dp = "%." + decimalsPlaces + "f";
        return String.format(dp, valor);
    }
    
    public static void main(String[] args){
        try{
        double K = FormulaOpacidad.opacidad(new Double(50.0));
        System.out.println("resultado: " + K);
            System.out.println("formateado: " + FormulaOpacidad.formatearOpacidad("8", K));
        }catch(ArithmeticException ar){
            System.out.println("" + ar.getMessage());
        }
    }
    
}
