/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.dao;

import com.soltelec.servidor.dao.exceptions.IllegalOrphanException;
import com.soltelec.servidor.dao.exceptions.NonexistentEntityException;
import com.soltelec.servidor.model.Llantas;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.omg.CORBA.PRIVATE_MEMBER;

/**
 *
 * @author SOLTELEC
 */
public class LlantasDAO {
    private  LlantasJpaController ljc = new LlantasJpaController();
    private Llantas llantas ;
    private String mensaje ="";
    
    public String insertarLlantas(String nombre, Integer Radio, Integer Id){
        try{
            llantas = new Llantas();
        llantas.setNombrellanta(nombre);
        llantas.setRadiollanta(Radio);
        llantas.setWheel(Id);
            
        
        ljc.create(llantas);
        mensaje= "tipo de llanta guardada correctamente";
        }catch(Exception e){
            System.out.println("llantas que intenta agregar no se pudo agregar " + e.getMessage());
            mensaje= "los datos ingresados no se pudieron guardar";
            
        }
        
        return mensaje;
    }
    
    public String actualizarLlantas(Integer Id,String nombre, Integer Radio){
          try{
       /* llantas.setWheel(Integer.BYTES);*/
       llantas = new Llantas();
        llantas.setNombrellanta(nombre);
        llantas.setRadiollanta(Radio);
        llantas.setWheel(Id);
        ljc.edit(llantas);
        mensaje= "tipo de llanta actualizada correctamente";
        }catch(Exception e){
            System.out.println("llantas que intenta actualizar no se pudo agregar " + e.getMessage());
            mensaje= "No se pudo actualizar la llanta";
            
        }
        
        return mensaje;
    }
    
    public String eliminarLlantas(int id) throws IllegalOrphanException{
        try {
            llantas = new Llantas();
            llantas.setWheel(id);
            ljc.destroy(id);
           mensaje = "eliminada correctamente";
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(LlantasDAO.class.getName()).log(Level.SEVERE, null, ex);
            mensaje = "no se pudo eliminar correctamente";
        }
        return mensaje;
    }
          
    public void ListaLlantas( JTable tabla){
        DefaultTableModel model;
        String[] titulo = {"ID"," DESCRIPCION DE LA LLANTA","RADIO DE LA LLANTA"};
        model = new DefaultTableModel(null, titulo);
        List<Llantas> datos =  ljc.findLlantasEntities();
        String[]  datosLlantas = new String[3];
        for (Llantas tbLlantas : datos) {
            datosLlantas[0]= String.valueOf(tbLlantas.getWheel());
            datosLlantas[1]= tbLlantas.getNombrellanta();
            datosLlantas[2]= String.valueOf(tbLlantas.getRadiollanta());
            model.addRow(datosLlantas);
        }
        tabla.setModel(model);       
    }
    
}
