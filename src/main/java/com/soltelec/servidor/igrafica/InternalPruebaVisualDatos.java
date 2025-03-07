/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.igrafica;

import com.soltelec.servidor.conexion.Conexion;
import com.soltelec.servidor.dao.PruebasJpaController;
import com.soltelec.servidor.model.DefectoSensorial;
import com.soltelec.servidor.utils.GenerarReporte;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Dany
 */
public class InternalPruebaVisualDatos extends javax.swing.JInternalFrame {

    private DefaultTableModel modeloInfoDefectos;
    private static final Logger LOG = Logger.getLogger(InternalPruebaVisualDatos.class.getName());
    private final Integer idTipoVehiculo;
    private final Integer idPruebaVisual;
    private final String usuarioPrueba;
    private final InternalPruebaVisual internalPruebaVisual;
    static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(InternalPruebaVisualDatos.class);
    private String placa;
    Connection cn = null;

    /**
     * Creates new form InternalPruebaVisual
     *
     * @param idTipoVehiculo
     * @param idPruebaVisual
     * @param placa
     * @param usuarioPrueba
     * @param internalPruebaVisual
     */
    public InternalPruebaVisualDatos(Integer idTipoVehiculo, Integer idPruebaVisual, String placa, String usuarioPrueba, InternalPruebaVisual internalPruebaVisual) {
        super("Reporte inspeccion sensorial",
                true, //resizable
                true, //closable
                false, //maximizable
                true);
        this.idTipoVehiculo = idTipoVehiculo;

        this.idPruebaVisual = idPruebaVisual;
        this.usuarioPrueba = usuarioPrueba;
        this.internalPruebaVisual = internalPruebaVisual;
        initModel();
        initComponents();
        this.placa = placa;
        lblPlaca.setText(placa);
        tblDefectos.setEnabled(false);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblDefectos = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnVolver = new javax.swing.JButton();
        lblPlaca = new javax.swing.JLabel();
        btnExportar = new javax.swing.JButton();

        tblDefectos.setModel(modeloInfoDefectos);
        jScrollPane1.setViewportView(tblDefectos);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Lista de Inspección sensorial:");

        btnVolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/soltelec/servidor/images/back.png"))); // NOI18N
        btnVolver.setText("Volver");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        lblPlaca.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        btnExportar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/soltelec/servidor/images/save.png"))); // NOI18N
        btnExportar.setText("Exportar");
        btnExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblPlaca)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnExportar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVolver))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVolver)
                    .addComponent(lblPlaca)
                    .addComponent(jLabel1)
                    .addComponent(btnExportar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 973, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        InternalPruebaVisual pruebas = new InternalPruebaVisual();
        pruebas.setVisible(true);
        try {
            if (!Principal.desktop.isAncestorOf(internalPruebaVisual)) {
                Principal.desktop.add(internalPruebaVisual);
            }
            internalPruebaVisual.toFront();
            internalPruebaVisual.setMaximum(true);
            internalPruebaVisual.setVisible(true);

            this.dispose();
        } catch (PropertyVetoException ex) {
            LOG.severe(ex.getMessage());
        }
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarActionPerformed
        Map parametros = new HashMap();
        parametros.put("placaVehiculo", placa);
        parametros.put("idPrueba", idPruebaVisual);
        parametros.put("idTipoVehiculo", idTipoVehiculo);
        parametros.put("idTipoPrueba", 1);
        parametros.put("userReport", usuarioPrueba);
        parametros.put("celular", 123456789);
        parametros.put("nombreCda", "Centro de Diagnostico Automotriz ECCE");
        GenerarReporte.generarReportePdf("inspeccionSensorial.jasper", parametros);
    }//GEN-LAST:event_btnExportarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblPlaca;
    private javax.swing.JTable tblDefectos;
    // End of variables declaration//GEN-END:variables

    private void initModel() {
        modeloInfoDefectos = new DefaultTableModel();
        modeloInfoDefectos.addColumn("CÓDIGO");//0
        modeloInfoDefectos.addColumn("DEFECTO");//1
        modeloInfoDefectos.addColumn("CONCEPTO");//2
        tblDefectos = new JTable();
        PruebasJpaController pjc = new PruebasJpaController();
        List<DefectoSensorial> defectos = pjc.findReporteDefectosSensorial(idTipoVehiculo, idPruebaVisual);
        cargarInformacionDefectos(defectos);
    }

    /**
     *
     *
     * @param defectoSensorial
     */
    private void cargarInformacionDefectos(List<DefectoSensorial> defectoSensorial) {
        try {
            Object[] objInfoDefectos = new Object[3];
            cn = llamarConexion();
            for (DefectoSensorial sensorial : defectoSensorial) {
                objInfoDefectos[0] = sensorial.getCodigo();
                objInfoDefectos[1] = sensorial.getDefecto();
                objInfoDefectos[2] = cargarCocepto(sensorial.getCodigo(), idPruebaVisual);
                modeloInfoDefectos.addRow(objInfoDefectos);
            }
            tblDefectos.setModel(modeloInfoDefectos);
            tblDefectos.setEnabled(false);
            cn.close();
        } catch (Exception e) {
            log.error("Error en el metodo : validacionDefectosregistradoDB()" + e.getMessage() + e.getLocalizedMessage());
        }

    }

    /**
     *
     * @param codigo
     * @param idPrueba
     * @return
     */
    private String cargarCocepto(String codigo, int idPrueba) {
        if (validacionDefectosregistradoDB(codigo, idPrueba, "defxprueba")) {
            return "SI";
        } else if (validacionDefectosregistradoDB(codigo, idPrueba, "defxpruebana")) {
            return "NA";
        } else {
            return "NO";
        }
    }

    /**
     *
     * @param codigoDefecto
     * @param idPrueba
     * @param tabla
     * @return
     */
    private boolean validacionDefectosregistradoDB(String codigoDefecto, int idPrueba, String tabla) {
        String select = "SELECT * FROM " + tabla + " dp WHERE dp.id_defecto=? AND dp.id_prueba=?";
        try {
            PreparedStatement instruccion1 = (PreparedStatement) cn.prepareStatement(select);
            instruccion1.setString(1, codigoDefecto);
            instruccion1.setInt(2, idPrueba);
            ResultSet r = instruccion1.executeQuery();
            if (r.next()) {
                return true;
            }
            instruccion1.clearParameters();
        } catch (SQLException ex) {
            log.error("Error en el metodo : validacionDefectosregistradoDB()" + ex.getMessage());
        }
        return false;
    }

    public Connection llamarConexion() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection cn = (Connection) DriverManager.getConnection("jdbc:mysql://" + Conexion.getIpServidor() + ":" + Conexion.getPuerto() + "/" + Conexion.getBaseDatos(), Conexion.getUsuario(), Conexion.getContrasena());
//            Connection cn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/db_cda", "root", "50lt3l3c545");
            return cn;
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("Error en el metodo llamarConexion() : PruebaInspecionSencorial: " + ex.getMessage() + ex.toString() + ex.getLocalizedMessage());
            System.err.println("Error " + ex.getCause());
            JOptionPane.showMessageDialog(null, "Error al conectar con la db");
        }
        return null;
    }

}
