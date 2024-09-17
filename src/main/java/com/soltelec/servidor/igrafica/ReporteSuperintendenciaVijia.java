/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.igrafica;

import com.soltelec.servidor.dao.CertificadosJpaController;
import com.soltelec.servidor.dao.HojaPruebasJpaController;
import com.soltelec.servidor.model.Certificados;
import com.soltelec.servidor.model.HojaPruebas;
import com.soltelec.servidor.model.Medidas;
import com.soltelec.servidor.model.Pruebas;
import com.soltelec.servidor.model.Reinspeccion;
import com.soltelec.servidor.utils.GenericExportExcel;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 * todos los clientes de los cda's deben poder generar este reporte para ser
 * enviado por medio del software "vigia" a la superintendencia.
 *
 * @requerimiento SART-2 Creación de reporte de inspecciones
 * @author Luis Berna
 * @fecha 01/02/2016
 */
public class ReporteSuperintendenciaVijia extends javax.swing.JInternalFrame {
//public class ReporteSuperintendenciaVijia extends javax.swing.JFrame {

    private DefaultTableModel modeloInfoVehiculo;
    private DefaultTableModel modeloGases;
    private DefaultTableModel modeloGasesOtto;
    private DefaultTableModel modeloTaximetro;
    private DefaultTableModel modeloDesviacion;
    private DefaultTableModel modeloRuido;
    private DefaultTableModel modeloLuces;
    private DefaultTableModel modeloFrenos;
    private DefaultTableModel modeloSuspension;

    private static final Logger LOG = Logger.getLogger(ReporteSuperintendenciaVijia.class.getName());
    private SimpleDateFormat sdf;

    public ReporteSuperintendenciaVijia() {
        super("Reporte superintendencia (Vijia)",
                true, //resizable
                true, //closable
                false, //maximizable
                true);
        initModels();
        initComponents();

    }
//    public ReporteSuperintendenciaVijia() {
//        initModels();
//        initComponents();
//    }

    private void initModels() {
        modeloInfoVehiculo = new DefaultTableModel();

        modeloInfoVehiculo.addColumn("NÚMERO FORMATO");//1
        modeloInfoVehiculo.addColumn("FECHA DE PRUEBA");//2
        modeloInfoVehiculo.addColumn("RESULTADO DE TODA LA PRUEBA (APROBADO/RECHAZADO)");//3
        modeloInfoVehiculo.addColumn("Número de Control");//4
        modeloInfoVehiculo.addColumn("No Consecutivo RUNT");//5
        modeloInfoVehiculo.addColumn("Placa");//6
        modeloInfoVehiculo.addColumn("Servicio");//7
        modeloInfoVehiculo.addColumn("Clase");//8
        modeloInfoVehiculo.addColumn("Marca");//9
        modeloInfoVehiculo.addColumn("Linea");//10
        modeloInfoVehiculo.addColumn("Modelo");//11
        modeloInfoVehiculo.addColumn("Fecha Matricula");//12
        modeloInfoVehiculo.addColumn("Combustible");//13
        modeloInfoVehiculo.addColumn("Tipo Motor");//14
        modeloInfoVehiculo.addColumn("Ruido Escape");//15

        //Set de columnas de pruebas de luces
        modeloLuces = new DefaultTableModel();

        modeloLuces.addColumn("Intensidad Baja Derecha");//0
        modeloLuces.addColumn("Intencidad Baja Izquierda");//1
        modeloLuces.addColumn("Inclinación Baja Derecha");//2
        modeloLuces.addColumn("Inclinacion Baja Izquierda");//3
        modeloLuces.addColumn("Intensidad Total");//4

        //Set de columnas de pruebas de luces
        modeloSuspension = new DefaultTableModel();

        modeloSuspension.addColumn("Delantera Derecha");//0
        modeloSuspension.addColumn("Delantera Izquierda");//1
        modeloSuspension.addColumn("Trasera Derecha");//2
        modeloSuspension.addColumn("Trasera Izquierda");//3

        //Set de columnas de pruebas de frenos
        modeloFrenos = new DefaultTableModel();

        modeloFrenos.addColumn("Eficacia Total");//0
        modeloFrenos.addColumn("Eficacia Auxiliar");//1
        modeloFrenos.addColumn("Fuerza Eje 1 Derecho");//2
        modeloFrenos.addColumn("Fuerza eje 2 Derecho");//3
        modeloFrenos.addColumn("Fuerza Eje 3 Derecho");//4
        modeloFrenos.addColumn("Fuerza Eje 4 Derecho");//5
        modeloFrenos.addColumn("Fuerza Eje 5 Derecho");//6
        modeloFrenos.addColumn("Fuerza Eje 1 Izquierdo");//7
        modeloFrenos.addColumn("Fuerza Eje 2 Izquierdo");//8
        modeloFrenos.addColumn("Fuerza Eje 3 Izquierdo");//9
        modeloFrenos.addColumn("Fuerza Eje 4 Izquierdo");//10
        modeloFrenos.addColumn("Fuerza Eje 5 Izquierdo");//11
        modeloFrenos.addColumn("Peso Eje 1 Derecho");//12
        modeloFrenos.addColumn("Peso eje 2 Derecho");//13
        modeloFrenos.addColumn("Peso Eje 3 Derecho");//14
        modeloFrenos.addColumn("Peso Eje 4 Derecho");//15
        modeloFrenos.addColumn("Peso Eje 5 Derecho");//16
        modeloFrenos.addColumn("Peso Eje 1 Izquierdo");//17
        modeloFrenos.addColumn("Peso Eje 2 Izquierdo");//18
        modeloFrenos.addColumn("Peso Eje 3 Izquierdo");//19
        modeloFrenos.addColumn("Peso Eje 4 Izquierdo");//20
        modeloFrenos.addColumn("Peso Eje 5 Izquierdo");//21
        modeloFrenos.addColumn("Desequilibrio eje 1 ");//22
        modeloFrenos.addColumn("Desequilibrio eje 2");//23
        modeloFrenos.addColumn("Desequilibrio eje 3 ");//24
        modeloFrenos.addColumn("Desequilibrio eje 4 ");//25
        modeloFrenos.addColumn("Desequilibrio eje 5 ");//26

        //Set de columnas de pruebas de frenos
        modeloDesviacion = new DefaultTableModel();

        modeloDesviacion.addColumn("Desviación eje 1");//0
        modeloDesviacion.addColumn("Desviación eje 2");//1
        modeloDesviacion.addColumn("Desviación eje 3");//2
        modeloDesviacion.addColumn("Desviación eje 4");//3
        modeloDesviacion.addColumn("Desviación eje 5");//4

        //Set de columnas de pruebas de taximetro
        modeloTaximetro = new DefaultTableModel();

        modeloTaximetro.addColumn("Referencia Comercial de la Llanta");//0
        modeloTaximetro.addColumn("Error en Distancia");//1
        modeloTaximetro.addColumn("Error en Tiempo");//2

        //Set de columnas de pruebas de gases otto
        modeloGasesOtto = new DefaultTableModel();

        modeloGasesOtto.addColumn("Temperatura");//0
        modeloGasesOtto.addColumn("Rpm Crucero");//1
        modeloGasesOtto.addColumn("Rpm Ralentí");//2
        modeloGasesOtto.addColumn("CO Ralentí");//3
        modeloGasesOtto.addColumn("CO2 Ralentí");//4
        modeloGasesOtto.addColumn("O2 Ralentí");//5
        modeloGasesOtto.addColumn("HC Ralentí");//6
        modeloGasesOtto.addColumn("CO Crucero");//7
        modeloGasesOtto.addColumn("CO2 Crucero");//8
        modeloGasesOtto.addColumn("O2 Crucero");//9
        modeloGasesOtto.addColumn("HC Crucero");//10

        //Set de columnas de pruebas de gases diesel
        modeloGases = new DefaultTableModel();

        modeloGases.addColumn("Rpm Ralentí Diesel");//0
        modeloGases.addColumn("Rpm Gobernada");//1
        modeloGases.addColumn("Temperatura (Diesel)");//2
        modeloGases.addColumn("Opacidad Ciclo 1");//3
        modeloGases.addColumn("Opacidad Ciclo 2");//4
        modeloGases.addColumn("Opacidad Ciclo 3");//5
        modeloGases.addColumn("Opacidad Ciclo 4");//6
        modeloGases.addColumn("Resultado");//7
    }

    /**
     * Carga los datos en la tabla
     */
    private void fillData(Date fechaInicial, Date fechaFinal) {
        initModels();
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        HojaPruebasJpaController hpjc = new HojaPruebasJpaController();

        List<Pruebas> lstPruebas = hpjc.findByDatePruebasAll(fechaInicial, fechaFinal);
        if (lstPruebas == null || lstPruebas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La consulta no genero datos");
            return;
        }
        System.out.println("Hay " + lstPruebas.size() + " hojas de prueba");
        int cmbPrueba = 0;
        int n = 0;
        String form;
        Boolean wTg = false;
        Boolean wTf = false;
        Boolean wTl = false;
        Boolean wTd = false;
        Boolean wTs = false;
        Boolean wTt = false;
        Boolean wTo = false;
        int f = 0;
        for (Pruebas pruebas : lstPruebas) {
            if (pruebas.getHojaPruebas().getTestsheet() == 20385) {
                int eve = 0;
            }
            if (cmbPrueba != pruebas.getHojaPruebas().getTestsheet()) {
                f = f + 1;

                if (pruebas.getHojaPruebas().getReinspeccionList2().size() > 0) {
                    Reinspeccion r = pruebas.getHojaPruebas().getReinspeccionList2().iterator().next();
                    List<Pruebas> lstPrueba = r.getPruebaList();
                    boolean encontre = false;
                    for (Pruebas pru : lstPrueba) {
                        if (pru.getIdPruebas() == pruebas.getIdPruebas()) {
                            encontre = true;
                            break;
                        }
                    }
                    if (encontre == false) {
                        n = 1;
                        form = "-1";
                    } else {
                        n = 2;
                        form = "-2";
                    }
                } else {
                    n = 1;
                    form = "-1";
                }
                cargarInformacionVehiculo(pruebas.getHojaPruebas(), form);
                if (cmbPrueba != 0) {
                    if (wTg == false) {
                        Object[] dataRow = new Object[8];
                        modeloGases.addRow(dataRow);
                    }
                    if (wTo == false) {
                        Object[] dataRow = new Object[11];
                        modeloGasesOtto.addRow(dataRow);
                    }
                    if (wTf == false) {
                        Object[] dataRow = new Object[26];
                        modeloFrenos.addRow(dataRow);
                    }
                    if (wTl == false) {
                        Object[] dataRow = new Object[5];
                        modeloLuces.addRow(dataRow);
                    }
                    if (wTd == false) {
                        Object[] dataRow = new Object[4];
                        modeloDesviacion.addRow(dataRow);
                    }
                    if (wTs == false) {
                        Object[] dataRow = new Object[4];
                        modeloSuspension.addRow(dataRow);
                    }
                    if (wTt == false) {
                        Object[] dataRow = new Object[5];
                        modeloTaximetro.addRow(dataRow);
                    }
                }
                wTg = false;
                wTf = false;
                wTl = false;
                wTd = false;
                wTs = false;
                wTt = false;
                wTo = false;
                cmbPrueba = pruebas.getHojaPruebas().getTestsheet();
                System.out.println("=================++=+===================== ");
                System.out.println("inicio de hp con  " + pruebas.getHojaPruebas().getTestsheet());
                System.out.println("LNG LUCES  " + modeloLuces.getRowCount());
                System.out.println("LNG SUSPENSION  " + modeloSuspension.getRowCount());
                System.out.println("LNG FRENOS  " + modeloFrenos.getRowCount());
                System.out.println("LNG DESVIACION  " + modeloDesviacion.getRowCount());
                System.out.println("LNG TAXIMETRO  " + modeloTaximetro.getRowCount());
                System.out.println("LNG GASESOTTO  " + modeloGasesOtto.getRowCount());
                System.out.println("LNG DIESEL  " + modeloGases.getRowCount());
                System.out.println("********************************************* ");
                System.out.println(" ");
            }
            switch (pruebas.getTipoPrueba().getTesttype()) {
                case 2:
                    if (!pruebas.getFinalizada().equalsIgnoreCase("A")) {
                        cargarInformacionPruebaLuces(pruebas);
                        wTl = true;
                    }
                    break;
                case 4:
                    if (!pruebas.getFinalizada().equalsIgnoreCase("A")) {
                        cargarInformacionPruebaDesviacion(pruebas);
                        wTd = true;
                    }
                    break;
                case 5:
                    if (!pruebas.getFinalizada().equalsIgnoreCase("A")) {
                        cargarInformacionPruebaFreno(pruebas);
                        wTf = true;
                    }
                    break;
                case 6:
                    if (!pruebas.getFinalizada().equalsIgnoreCase("A")) {
                        cargarInformacionPruebaSuspension(pruebas);
                        wTs = true;
                    }
                    break;
                case 8:
                    if (!pruebas.getFinalizada().equalsIgnoreCase("A")) {
                        if (pruebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype() == 3 || pruebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype() == 11) {
                            cargarInformacionPruebaGasesDiesel(pruebas);
                            wTg = true;
                        } else {
                            cargarInformacionPruebaGasesOtto(pruebas);
                            wTo = true;
                        }
                    }
                    break;
                case 9:
                    if (!pruebas.getFinalizada().equalsIgnoreCase("A")) {
                        cargarInformacionPruebaTaximetro(pruebas);
                        wTt = true;
                    }
                    break;
            }
        }
        tblInfoVehiculos.setModel(modeloInfoVehiculo);
        tblInfoVehiculos.setEnabled(false);
        System.out.println("RESUMEN DE LNG TBL ");
        System.out.println("total hp " + n);
        System.out.println("LNG LUCES  " + modeloLuces.getRowCount());
        tblPruebaLuces.setModel(modeloLuces);
        tblPruebaLuces.setEnabled(false);
        tblPruebaSuspension.setModel(modeloSuspension);
        tblPruebaSuspension.setEnabled(false);
        System.out.println("LNG SUSPENSION  " + modeloSuspension.getRowCount());
        tblPruebaFreno.setModel(modeloFrenos);
        tblPruebaFreno.setEnabled(false);
        System.out.println("LNG FRENOS  " + modeloFrenos.getRowCount());
        tblPruebaDesviacion.setModel(modeloDesviacion);
        tblPruebaDesviacion.setEnabled(false);
        System.out.println("LNG DESVIACION  " + modeloDesviacion.getRowCount());
        tblPruebaTaximetro.setModel(modeloTaximetro);
        tblPruebaTaximetro.setEnabled(false);
        System.out.println("LNG TAXIMETRO  " + modeloTaximetro.getRowCount());
        tblPruebaGasesOtto.setModel(modeloGasesOtto);
        tblPruebaGasesOtto.setEnabled(false);
        System.out.println("LNG GASESOTTO  " + modeloGasesOtto.getRowCount());
        tblPruebaGasesDiesel.setModel(modeloGases);
        tblPruebaGasesDiesel.setEnabled(false);
        System.out.println("LNG DIESEL  " + modeloGases.getRowCount());
    } //end filldata

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        fechaInicial = new org.jdesktop.swingx.JXDatePicker();
        fechaInicial.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
        fechaFInal = new org.jdesktop.swingx.JXDatePicker();
        fechaInicial.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
        btnGenerar = new javax.swing.JButton();
        btnExportar = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblInfoVehiculos = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPruebaLuces = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblPruebaSuspension = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblPruebaFreno = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblPruebaDesviacion = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblPruebaTaximetro = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblPruebaGasesOtto = new javax.swing.JTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblPruebaGasesDiesel = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(1024, 768));

        jLabel1.setText("Fecha Inicio:");

        jLabel2.setText("Fecha Fin:");

        btnGenerar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/soltelec/servidor/images/search.png"))); // NOI18N
        btnGenerar.setText("Generar");
        btnGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarActionPerformed(evt);
            }
        });

        btnExportar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/soltelec/servidor/images/save.png"))); // NOI18N
        btnExportar.setText("Exportar");
        btnExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarActionPerformed(evt);
            }
        });

        tblInfoVehiculos.setModel(modeloInfoVehiculo);
        tblInfoVehiculos.setName("Información del vehiculo"); // NOI18N
        jScrollPane1.setViewportView(tblInfoVehiculos);

        jTabbedPane1.addTab("Información del vehiculo", jScrollPane1);

        tblPruebaLuces.setModel(modeloLuces);
        tblPruebaLuces.setName("Prueba de luces"); // NOI18N
        jScrollPane2.setViewportView(tblPruebaLuces);

        jTabbedPane1.addTab("Prueba de luces", jScrollPane2);

        tblPruebaSuspension.setModel(modeloSuspension);
        tblPruebaSuspension.setName("Prueba de suspensión"); // NOI18N
        jScrollPane3.setViewportView(tblPruebaSuspension);

        jTabbedPane1.addTab("Prueba de suspensión", jScrollPane3);

        tblPruebaFreno.setModel(modeloFrenos);
        tblPruebaFreno.setName("Prueba de frenos"); // NOI18N
        jScrollPane4.setViewportView(tblPruebaFreno);

        jTabbedPane1.addTab("Prueba de frenos", jScrollPane4);

        tblPruebaDesviacion.setModel(modeloDesviacion);
        tblPruebaDesviacion.setName("Prueba de desviación"); // NOI18N
        jScrollPane5.setViewportView(tblPruebaDesviacion);

        jTabbedPane1.addTab("Prueba Desviación", jScrollPane5);

        tblPruebaTaximetro.setModel(modeloTaximetro);
        tblPruebaTaximetro.setName("Prueba de desviación"); // NOI18N
        jScrollPane6.setViewportView(tblPruebaTaximetro);

        jTabbedPane1.addTab("Dispositivos de Cobro\t\t ", jScrollPane6);

        tblPruebaGasesOtto.setModel(modeloGasesOtto);
        tblPruebaGasesOtto.setName("Prueba de desviación"); // NOI18N
        jScrollPane7.setViewportView(tblPruebaGasesOtto);

        jTabbedPane1.addTab("Prueba de gases vehiculos ciclo otto", jScrollPane7);

        tblPruebaGasesDiesel.setModel(modeloGases);
        tblPruebaGasesDiesel.setName("Prueba de desviación"); // NOI18N
        jScrollPane8.setViewportView(tblPruebaGasesDiesel);

        jTabbedPane1.addTab("Prueba de gases vehiculos diesel", jScrollPane8);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(fechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(fechaFInal, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnGenerar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnExportar)
                .addContainerGap(224, Short.MAX_VALUE))
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(fechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fechaFInal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGenerar)
                    .addComponent(btnExportar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE))
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Prueba de luces");
        jTabbedPane1.getAccessibleContext().setAccessibleDescription("");

        setBounds(0, 0, 816, 639);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String[] args) {
        ReporteSuperintendenciaVijia rsv = new ReporteSuperintendenciaVijia();
        rsv.setVisible(true);

    }

    private void btnGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarActionPerformed
        // TODO add your handling code here:
        if (fechaInicial.getDate() == null || fechaFInal.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Seleccione Fechas");
            return;
        }
        SwingUtilities.invokeLater(
                new Runnable() {

            @Override
            public void run() {
                fillData(fechaInicial.getDate(), fechaFInal.getDate());
            }
        });
    }//GEN-LAST:event_btnGenerarActionPerformed

    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarActionPerformed
        // TODO add your handling code here:
        List<JTable> tables = new ArrayList<>();

        tables.add(tblInfoVehiculos);
        tables.add(tblPruebaLuces);
        tables.add(tblPruebaSuspension);
        tables.add(tblPruebaFreno);
        tables.add(tblPruebaDesviacion);
        tables.add(tblPruebaTaximetro);
        tables.add(tblPruebaGasesOtto);
        tables.add(tblPruebaGasesDiesel);

        GenericExportExcel excel = new GenericExportExcel();
        excel.exportSameSheet(tables);
    }//GEN-LAST:event_btnExportarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btnGenerar;
    private org.jdesktop.swingx.JXDatePicker fechaFInal;
    private org.jdesktop.swingx.JXDatePicker fechaInicial;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblInfoVehiculos;
    private javax.swing.JTable tblPruebaDesviacion;
    private javax.swing.JTable tblPruebaFreno;
    private javax.swing.JTable tblPruebaGasesDiesel;
    private javax.swing.JTable tblPruebaGasesOtto;
    private javax.swing.JTable tblPruebaLuces;
    private javax.swing.JTable tblPruebaSuspension;
    private javax.swing.JTable tblPruebaTaximetro;
    // End of variables declaration//GEN-END:variables

    private void cargarInformacionVehiculo(HojaPruebas hojaPruebas, String strIntento) {
        CertificadosJpaController cjc = new CertificadosJpaController();
        Certificados certificado = cjc.findCertificadoHojaPrueba(hojaPruebas.getTestsheet());
        if (hojaPruebas.getTestsheet() == 16291) {
            int e = 0;
        }
        if (hojaPruebas.getTestsheet() == 16255) {
            int e = 0;
        }
        Object[] objInfoVehiculo = new Object[15];
        objInfoVehiculo[0] = String.valueOf(hojaPruebas.getConHojaPrueba()).concat(strIntento);
        if (strIntento.equalsIgnoreCase("-2")) {
            Reinspeccion rein = hojaPruebas.getReinspeccionList2().iterator().next();
            objInfoVehiculo[1] = sdf.format(rein.getFechaSiguiente());
        } else {
            objInfoVehiculo[1] = sdf.format(hojaPruebas.getFechaingresovehiculo());
        }
        if (hojaPruebas.getReinspeccionList2().size() > 0 && strIntento.equalsIgnoreCase("-1")) {
            objInfoVehiculo[2] = "Rechazado";
        } else {
            objInfoVehiculo[2] = "Y".equals(hojaPruebas.getAprobado()) ? "Aprobado" : "Rechazado";
        }
        objInfoVehiculo[3] = certificado.getConsecutive();
        objInfoVehiculo[4] = certificado.getConsecutivoRunt();
        objInfoVehiculo[5] = hojaPruebas.getVehiculos().getCarplate();
        objInfoVehiculo[6] = hojaPruebas.getVehiculos().getServicios().getNombreservicio();
        objInfoVehiculo[7] = hojaPruebas.getVehiculos().getClasesVehiculo().getNombreclase();
        objInfoVehiculo[8] = hojaPruebas.getVehiculos().getMarcas().getNombremarca();
        objInfoVehiculo[9] = hojaPruebas.getVehiculos().getLineasVehiculos().getCrlname();
        objInfoVehiculo[10] = hojaPruebas.getVehiculos().getModelo();
        objInfoVehiculo[11] = sdf.format(hojaPruebas.getVehiculos().getFechaexpsoat());
        objInfoVehiculo[12] = hojaPruebas.getVehiculos().getTiposGasolina().getNombregasolina();
        objInfoVehiculo[13] = hojaPruebas.getVehiculos().getTiemposmotor();
        objInfoVehiculo[14] = obtenerRuidoScape(hojaPruebas);
        if (modeloInfoVehiculo.getRowCount() > 14) {
            int e = 1;
        }
        modeloInfoVehiculo.addRow(objInfoVehiculo);
    }

    /**
     * Carga todas las medidas de la prueba de luces
     */
    private void cargarInformacionPruebaLuces(Pruebas pruebas) {
        Object[] dataRow = new Object[5];
        for (Medidas medidas : pruebas.getMedidasList()) {
            switch (medidas.getTiposMedida().getMeasuretype()) {
                case 2006: //Intencidad de la luz baja derecha para vehiculo
                    dataRow[0] = medidas.getValormedida();
                    break;
                case 2009://Intencidad de las luces baja izquierda para vehiculo
                    dataRow[1] = medidas.getValormedida();
                    break;
                case 2003://Inclinacion de luz baja derecha para vehiculos
                    dataRow[2] = medidas.getValormedida();
                    break;
                case 2005://Inclinacion de luz baja izquierda para vehiculo
                    dataRow[3] = medidas.getValormedida();
                    break;
                case 2011://Suma de todas las intensidades de luz para vehiculos
                    dataRow[4] = medidas.getValormedida();
                    break;
            }
        }
        modeloLuces.addRow(dataRow);
    }

    private void cargarInformacionPruebaSuspension(Pruebas pruebas) {
        Object[] dataRow = new Object[4];
        for (Medidas medidas : pruebas.getMedidasList()) {
            switch (medidas.getTiposMedida().getMeasuretype()) {
                case 6016: //Suspension derecha por eje
                    dataRow[0] = medidas.getValormedida();
                    break;
                case 6020://Suspension izquierda por eje
                    dataRow[1] = medidas.getValormedida();
                    break;
                case 6017://Suspension derecha por eje
                    dataRow[2] = medidas.getValormedida();
                    break;
                case 6021://Suspension izquierda  por eje
                    dataRow[3] = medidas.getValormedida();
                    break;
            }
        }
        modeloSuspension.addRow(dataRow);
    }

    private void cargarInformacionPruebaFreno(Pruebas pruebas) {
        Object[] dataRow = new Object[26];
        for (Medidas medidas : pruebas.getMedidasList()) {
            switch (medidas.getTiposMedida().getMeasuretype()) {
                case 5024: //Eficacia de frenado
                    dataRow[0] = medidas.getValormedida();
                    break;
                case 5036://Eficacia de freno de mano (EFIFREMAN)
                    dataRow[1] = medidas.getValormedida();
                    break;
                case 5008://Fuerza de frenado maxima derecha por eje (FFMDEEJE1)
                    dataRow[2] = medidas.getValormedida();
                    break;
                case 5009://Fuerza de frenado maxima derecha por eje (FFMDEEJE2)
                    dataRow[3] = medidas.getValormedida();
                    break;
                case 5010://Fuerza de frenado maxima derecha por eje (FFMDEEJE3)
                    dataRow[4] = medidas.getValormedida();
                    break;
                case 5011://Fuerza de frenado maxima derecha por eje (FFMDEEJE4)
                    dataRow[5] = medidas.getValormedida();
                    break;
                case 0001://TODO: Falta fuerza de frenado eje5
                    dataRow[6] = medidas.getValormedida();
                    break;
                case 5012://Fuerza de frenado maxima izquierda por eje (FFMIZEJE1)
                    dataRow[7] = medidas.getValormedida();
                    break;
                case 5013://Fuerza de frenado maxima izquierda por eje (FFMIZEJE2)
                    dataRow[8] = medidas.getValormedida();
                    break;
                case 5014://Fuerza de frenado maxima izquierda por eje (FFMIZEJE3)
                    dataRow[9] = medidas.getValormedida();
                    break;
                case 5015://Fuerza de frenado maxima izquierda por eje (FFMIZEJE4)
                    dataRow[10] = medidas.getValormedida();
                    break;
                case 0002://TODO: Falta fuerza de frenado izquierda eje5
                    dataRow[11] = medidas.getValormedida();
                    break;
                case 5000://Peso derecho por eje (PESDEREJE1)
                    dataRow[12] = medidas.getValormedida();
                    break;
                case 5001://Peso derecho por eje (PESDEREJE2)
                    dataRow[13] = medidas.getValormedida();
                    break;
                case 5002://Peso derecho por eje (PESDEREJE3)
                    dataRow[14] = medidas.getValormedida();
                    break;
                case 5003://Peso derecho por eje (PESDEREJE4)
                    dataRow[15] = medidas.getValormedida();
                    break;
                case 0003://TODO: Falta Peso derecho por eje 5
                    dataRow[16] = medidas.getValormedida();
                    break;
                case 5004://Peso izquierdo por eje (PESIZQEJE1)
                    dataRow[17] = medidas.getValormedida();
                    break;
                case 5005://Peso izquierdo por eje (PESIZQEJE2)
                    dataRow[18] = medidas.getValormedida();
                    break;
                case 5006://Peso izquierdo por eje (PESIZQEJE3)
                    dataRow[19] = medidas.getValormedida();
                    break;
                case 5007://Peso izquierdo por eje (PESIZQEJE4)
                    dataRow[20] = medidas.getValormedida();
                    break;
                case 0004://TODO: Falta Peso izquierdo por eje 5
                    dataRow[21] = medidas.getValormedida();
                    break;
                case 5032://Desequilibrio por eje (DESEJE1)
                    dataRow[22] = medidas.getValormedida();
                    break;
                case 5033://Desequilibrio por eje (DESEJE2)
                    dataRow[23] = medidas.getValormedida();
                    break;
                case 5034://Desequilibrio por eje (DESEJE3)
                    dataRow[24] = medidas.getValormedida();
                    break;
                case 5035://Desequilibrio por eje (DESEJE4)
                    dataRow[25] = medidas.getValormedida();
                    break;
            }
        }
        modeloFrenos.addRow(dataRow);
    }

    private void cargarInformacionPruebaDesviacion(Pruebas pruebas) {
        Object[] dataRow = new Object[4];
        for (Medidas medidas : pruebas.getMedidasList()) {
            switch (medidas.getTiposMedida().getMeasuretype()) {
                case 4000: //Desviacion del eje numero 1 (DESVEJE1)
                    dataRow[0] = medidas.getValormedida();
                    break;
                case 4001: //Desviacion del eje numero 2 (DESVEJE2)
                    dataRow[1] = medidas.getValormedida();
                    break;
                case 4002: //Desviacion del eje numero 3 (DESVEJE3)
                    dataRow[2] = medidas.getValormedida();
                    break;
                case 4003: //Desviacion del eje numero 4 (DESVEJE4)
                    dataRow[3] = medidas.getValormedida();
                    break;
                case 0000: //TODO: Falta Desviacion del eje numero 5 
                    dataRow[4] = medidas.getValormedida();
                    break;
            }
        }
        modeloDesviacion.addRow(dataRow);
    }

    private void cargarInformacionPruebaTaximetro(Pruebas pruebas) {
        Object[] dataRow = new Object[3];
        dataRow[0] = pruebas.getHojaPruebas().getVehiculos().getLlantas().getNombrellanta();
        for (Medidas medidas : pruebas.getMedidasList()) {
            switch (medidas.getTiposMedida().getMeasuretype()) {
                case 9002: //Error de taximetro en distancia (ERRORXDIST)
                    dataRow[1] = medidas.getValormedida();
                    break;
                case 9003: //Error de taximetro en tirmpo (ERRORXTIEM)
                    dataRow[2] = medidas.getValormedida();
                    break;
            }
        }
        modeloTaximetro.addRow(dataRow);
    }

    private void cargarInformacionPruebaGasesOtto(Pruebas pruebas) {
        Object[] dataRow = new Object[11];
        System.out.println("el Id prueba caido es " + pruebas.getIdPruebas());
        for (Medidas medidas : pruebas.getMedidasList()) {
            switch (medidas.getTiposMedida().getMeasuretype()) {
                case 8006: //Temperatura en ralenti (TEMPR)
                    dataRow[0] = medidas.getValormedida();
                    break;
                case 8022: //Temperatura en ralenti (TEMPR) FOR MOTO 2T
                    dataRow[0] = medidas.getValormedida();
                    break;
                case 8011: //Revoluciones por minuto en crucero (RPMC)
                    dataRow[1] = medidas.getValormedida();
                    break;
                case 8028: //Revoluciones por minuto en ralenty  (RPMC) PARA MOTO 
                    dataRow[2] = medidas.getValormedida();
                    break;
                case 8005: //Revoluciones por minuto en ralenty (RPMR)
                    dataRow[2] = medidas.getValormedida();
                    break;
                case 8002: //Monoxido de carbono en ralenty (COR)
                    dataRow[3] = medidas.getValormedida();
                    break;
                case 8003: //Dioxido de carbono en ralenty (CO2R)
                    dataRow[4] = medidas.getValormedida();
                    break;
                case 8004: //Oxigeno en ralenty (O2R)
                    dataRow[5] = medidas.getValormedida();
                    break;
                case 8021: //Oxigeno en ralenty (O2R) PARA MOTO 2T
                    dataRow[5] = medidas.getValormedida();
                    break;
                case 8001: //HidroCarburos en ralenty (HCR)
                    dataRow[6] = medidas.getValormedida();
                    break;
                case 8018: //HidroCarburos en ralenty (HCR) Para Motos 2t
                    dataRow[6] = medidas.getValormedida();
                    break;
                case 8008: //Monoxido de carbono en crucero (COC)
                    dataRow[7] = medidas.getValormedida();
                    break;
                case 8020: //Monoxido de carbono en crucero (COC) PARA MOTOS 2T
                    dataRow[3] = medidas.getValormedida();
                    break;
                case 8009: //Dioxido de carbono en crucero (CO2C)
                    dataRow[8] = medidas.getValormedida();
                    break;
                case 8019: //Dioxido de carbono en crucero (CO2C) para Motos 2t
                    dataRow[4] = medidas.getValormedida();
                    break;
                case 8010: //Oxigeno en crucero (O2C)
                    dataRow[9] = medidas.getValormedida();
                    break;
                case 8007: //HidroCarburos en crucero (HCC)
                    dataRow[10] = medidas.getValormedida();
                    break;
            }
        }
        modeloGasesOtto.addRow(dataRow);
    }

    private void cargarInformacionPruebaGasesDiesel(Pruebas pruebas) {
        Object[] dataRow = new Object[8];
        for (Medidas medidas : pruebas.getMedidasList()) {
            switch (medidas.getTiposMedida().getMeasuretype()) {
                case 8035: //Velocidad minima prueba Diesel (VELMINIMA)
                    dataRow[0] = medidas.getValormedida();
                    break;
                case 8036: //Velocidad gobernadas prueba Diesel (VELGOBERNADAS)
                    dataRow[1] = medidas.getValormedida();
                    break;
                case 8034: //Temperatura del motor prueba Diesel (TEMPMOTORDIESEL)
                    dataRow[2] = medidas.getValormedida();
                    break;
                case 8013: //Primer ciclo para el opacimetro (CICLO1OP)
                    dataRow[3] = medidas.getValormedida();
                    break;
                case 8014: //Segundo ciclo para el opacimetro (CICLO2OP)
                    dataRow[4] = medidas.getValormedida();
                    break;
                case 8015: //Tercer ciclo para el opacimetro (CICLO3OP)
                    dataRow[5] = medidas.getValormedida();
                    break;
                case 0000: //TODO: Falta Cuarto ciclo para el opacimetro
                    dataRow[6] = medidas.getValormedida();
                    break;
                case 8017: //Promedio valor de los tres ciclos de la prueba de opacimetro (PROMVALOP)
                    dataRow[7] = medidas.getValormedida();
                    break;
            }
        }
        modeloGases.addRow(dataRow);
    }

    private String obtenerRuidoScape(HojaPruebas hojaPruebas) {
        double promedioRuidoMotor = 0;
        String ruidoMotor = null;
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(Locale.ENGLISH);
        int tipoMedida;
        for (Pruebas p : hojaPruebas.getPruebasCollection()) {
            if (p.getTipoPrueba().getTesttype() == 7) {
                for (Medidas m : p.getMedidasList()) {
                    if (m.getTiposMedida().getMeasuretype() == 7005) {
                        promedioRuidoMotor = m.getValormedida();
                        df.applyPattern("#.0#");
                        df.setMaximumFractionDigits(1);
                        ruidoMotor = df.format(promedioRuidoMotor);
                        break;
                    }
                }
            }
        }
        return ruidoMotor;
    }
}
