/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.igrafica;

import com.soltelec.servidor.dao.PruebasJpaController;
import com.soltelec.servidor.model.HojaPruebas;
import com.soltelec.servidor.model.Medidas;
import com.soltelec.servidor.model.Pruebas;
import com.soltelec.servidor.model.Vehiculos;
import com.soltelec.servidor.utils.GenericExportExcel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Dany
 */
public class ReporteSuper extends javax.swing.JInternalFrame {

    private DefaultTableModel modeloLuces;
    private DefaultTableModel modeloFrenos;
    private DefaultTableModel modeloSuspension;

    public ReporteSuper() {
        super("Pruebas RT",
                true, //resizable
                true, //closable
                false, //maximizable
                true);
        initModels();
        initComponents();

    }

    private void initModels() {
        // ***** MODELO PARA LAS PRUEBA LUCES *****
        modeloLuces = new DefaultTableModel();
        modeloLuces.addColumn("Id");
        modeloLuces.addColumn("Fecha Prueba");
        modeloLuces.addColumn("Int.BajaD");
        modeloLuces.addColumn("Inc.BajaD");
        modeloLuces.addColumn("Int.BajaIz");
        modeloLuces.addColumn("Inc.BajaIz");
        modeloLuces.addColumn("Suma Luces");
        modeloLuces.addColumn("Aprobada");
        modeloLuces.addColumn("Placa");
        modeloLuces.addColumn("Tipo Vehiculo");
        // ***** MODELO PARA LAS PRUEBA FRENOS *****   
        modeloFrenos = new DefaultTableModel();
        modeloFrenos.addColumn("Id");
        modeloFrenos.addColumn("Fecha Prueba");
        modeloFrenos.addColumn("Ej1 FuerzaD");
        modeloFrenos.addColumn("Ej1 PesoD");
        modeloFrenos.addColumn("Desequilibrio");
        modeloFrenos.addColumn("Ej2 FuerzaD");
        modeloFrenos.addColumn("Ej2 PesoD");
        modeloFrenos.addColumn("Desequilibrio");
        modeloFrenos.addColumn("Eficacia Total");
        modeloFrenos.addColumn("Eficacia Auxiliar");
        modeloFrenos.addColumn("Aprobada");
        modeloFrenos.addColumn("Placa");
        modeloFrenos.addColumn("Tipo Vehiculo");
        // ***** MODELO PARA LAS PRUEBA SUSPENSION *****   
        modeloSuspension = new DefaultTableModel();
        modeloSuspension.addColumn("Id");
        modeloSuspension.addColumn("Fecha Prueba");
        modeloSuspension.addColumn("Eje1 Der");
        modeloSuspension.addColumn("Eje1 Izq");
        modeloSuspension.addColumn("Eje2 Der");
        modeloSuspension.addColumn("Eje2 Izq");
        modeloSuspension.addColumn("Aprobada");
        modeloSuspension.addColumn("Placa");
//      // ***** MODELO PARA LAS PRUEBA SONORA *****
//        modeloRuido = new DefaultTableModel();
//        modeloRuido.addColumn("Nro Prueba");
//        modeloLuces.addColumn("Fecha Prueba");
//        modeloRuido.addColumn("Hora y Fecha Prueba");
//        modeloRuido.addColumn("Motivo aborto");
//        modeloRuido.addColumn("Hora y Fecha Aborto");
//        modeloRuido.addColumn("PSonora Exosto");
//        modeloRuido.addColumn("Aprobada");

    }

    private void fillData(Date fechaInicial, Date fechaFinal) {
        initModels();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        progreso.setValue(1);

        //****LLENAR DATOS****
        PruebasJpaController pruebasJpa = new PruebasJpaController();
        List<Pruebas> pruebas = pruebasJpa.findPruebasByFechaPruebas(fechaInicial, fechaFinal);
        //List<HojaPruebas> hpbyfecha = pruebasJpa.findHP_porFecha(fInicial, fFinal);

        Object[] filaLuces = new Object[10]; // Hay 10 columnas en la tabla
        Object[] filaFrenos = new Object[13]; // Hay 12 columnas en la tabla
        Object[] filaSuspension = new Object[8]; // Hay 11 columnas en la tabla

        for (Pruebas p : pruebas) {
            HojaPruebas hojaPruebas = p.getHojaPruebas();
            Vehiculos vehiculo = hojaPruebas.getVehiculos();
            List<Medidas> medidas = p.getMedidasList();
            //Si la prueba es de Gases o Ruido
            if (p.getTipoPrueba().getTesttype() == 2) {
                // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
                //filaLuces[0] = vehiculo.getMarcas().getNombremarca();            //Marca del vehiculo
                filaLuces[0] = p.getIdPruebas();                                 //Id
                filaLuces[1] = sdf.format(p.getFechaPrueba());                   //Fecha Prueba                            
                filaLuces[2] = "----";                                           //Int.BDr
                filaLuces[3] = "----";                                              //Inc.BDr
                filaLuces[4] = "----";                                              //Int.BIz 
                filaLuces[5] = "----";                                              //Inc.BIz
                filaLuces[6] = "----";                                              //Suma Luces
                filaLuces[7] = "----";                                              //Aprobada
                filaLuces[8] = vehiculo.getCarplate();                              //Placa
                filaLuces[9] = vehiculo.getClasesVehiculo().getNombreclase();       //Tipo de Vehiculo

                for (Medidas m : p.getMedidasList()) {
                    switch (m.getTiposMedida().getMeasuretype()) {
                        case 2006://intensidad baja derecha vh
                            filaLuces[2] = m.getValormedida();
                            break;
                        case 2003://inclinacion baja derecha vh
                            filaLuces[3] = m.getValormedida();
                            break;
                        case 2009://intensidad baja izq vh
                            filaLuces[4] = m.getValormedida();
                            break;
                        case 2005://inclinacion baja izq vh
                            filaLuces[5] = m.getValormedida();
                            break;
                        case 2011://Suma Luces vh
                            filaLuces[6] = m.getValormedida();
                            break;
                        case 2014://intensidad baja derecha moto
                            filaLuces[2] = m.getValormedida();
                            break;
                        case 2013://inclinacion baja derecha moto
                            filaLuces[3] = m.getValormedida();
                            break;
                        case 2015://intensidad baja izq moto
                            filaLuces[4] = m.getValormedida();
                            break;
                        case 2002://inclinacion baja izq moto
                            filaLuces[5] = m.getValormedida();
                            break;
                        default:
                            break;
                    }//end of siwtch tipo medida
                }//end of for medidas
                if (p.getAprobada().trim().equalsIgnoreCase("Y")) {
                    filaLuces[7] = "Aprobada";
                } else if (p.getAprobada().trim().equalsIgnoreCase("N")) {
                    filaLuces[7] = "Reprobada";
                }
                modeloLuces.addRow(filaLuces);
            }//end prueba luces

            //Prueba Frenos
            if (p.getTipoPrueba().getTesttype() == 5) {
                // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
                //filaLuces[0] = vehiculo.getMarcas().getNombremarca();            //Marca del vehiculo
                filaFrenos[0] = p.getIdPruebas();                                   //Id
                filaFrenos[1] = sdf.format(p.getFechaPrueba());                     //Fecha Prueba                            
                filaFrenos[2] = "----";                                             //Ej1 FuerzaD
                filaFrenos[3] = "----";                                             //Ej1 PesoD
                filaFrenos[4] = "----";                                             //Desequilibrio
                filaFrenos[5] = "----";                                             //Ej2 FuerzaD
                filaFrenos[6] = "----";                                             //Ej2 PesoD
                filaFrenos[7] = "----";                                             //Desequilibrio
                filaFrenos[8] = "----";                                         //Eficacia Total
                filaFrenos[9] = "----";                                         //Eficacia Auxiliar    
                filaFrenos[10] = "----";                                        //Aprobada
                filaFrenos[11] = vehiculo.getCarplate();                        //Placa
                filaFrenos[12] = vehiculo.getTipoVehiculo().getNombre();        //Tipo Vehiculo

                for (Medidas m : p.getMedidasList()) {
                    switch (m.getTiposMedida().getMeasuretype()) {
                        case 5008://Ej1 fuerza D
                            filaFrenos[2] = m.getValormedida();
                            break;
                        case 5000://Ej1 Peso D
                            filaFrenos[3] = m.getValormedida();
                            break;
                        case 5032://desequilibrio 1
                            filaFrenos[4] = m.getValormedida();
                            break;
                        case 5009://Ej2 fuerza D
                            filaFrenos[5] = m.getValormedida();
                            break;
                        case 5001://Ej2 Peso D
                            filaFrenos[6] = m.getValormedida();
                            break;
                        case 5033://desequilibrio 2
                            filaFrenos[7] = m.getValormedida();
                            break;
                        case 5024://Eficacia Total
                            filaFrenos[8] = m.getValormedida();
                            break;
                        case 5036://Eficacia Auxiliar
                            filaFrenos[9] = m.getValormedida();
                            break;
                        default:
                            break;
                    }//end of siwtch tipo medida
                }//end of for medidas
                if (p.getAprobada().trim().equalsIgnoreCase("Y")) {
                    filaFrenos[10] = "Aprobada";
                } else if (p.getAprobada().trim().equalsIgnoreCase("N")) {
                    filaFrenos[10] = "Reprobada";
                }
                modeloFrenos.addRow(filaFrenos);
            }//end prueba frenos

            //Prueba Suspension
            if (p.getTipoPrueba().getTesttype() == 6) {
                // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
                //filaLuces[0] = vehiculo.getMarcas().getNombremarca();            //Marca del vehiculo
                filaSuspension[0] = p.getIdPruebas();                                   //Id
                filaSuspension[1] = sdf.format(p.getFechaPrueba());                     //Fecha Prueba                            
                filaSuspension[2] = "----";                                             //Ej1 Peso D
                filaSuspension[3] = "----";                                             //Ej1 Peso Izq
                filaSuspension[4] = "----";                                             //Ej2 Peso D
                filaSuspension[5] = "----";                                             //Ej2 Peso Izq
                filaSuspension[6] = "----";                                             //Aprobada
                filaSuspension[7] = vehiculo.getCarplate();                             //Placa

                for (Medidas m : p.getMedidasList()) {
                    switch (m.getTiposMedida().getMeasuretype()) {
                        case 6000://Ej1 Peso D
                            filaSuspension[2] = m.getValormedida();
                            break;
                        case 6004://Ej1 Peso Izq
                            filaSuspension[3] = m.getValormedida();
                            break;
                        case 6001://Eje2 Peso D
                            filaSuspension[4] = m.getValormedida();
                            break;
                        case 6005://Ej2 Peso Izq
                            filaSuspension[5] = m.getValormedida();
                            break;
                        default:
                            break;
                    }//end of siwtch tipo medida
                }//end of for medidas
                if (p.getAprobada().trim().equalsIgnoreCase("Y")) {
                    filaSuspension[6] = "Aprobada";
                } else if (p.getAprobada().trim().equalsIgnoreCase("N")) {
                    filaSuspension[6] = "Reprobada";
                }
                modeloSuspension.addRow(filaSuspension);
            }//end prueba suspension
            tblLuces.setModel(modeloLuces);
            tblFrenos.setModel(modeloFrenos);
            tblSuspension.setModel(modeloSuspension);
            tblLuces.setEnabled(false);
            tblFrenos.setEnabled(false);
            tblSuspension.setEnabled(false);
        }
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
        dteInicio = new org.jdesktop.swingx.JXDatePicker();
        dteInicio.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
        dteFin = new org.jdesktop.swingx.JXDatePicker();
        dteInicio.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
        btnGenerar = new javax.swing.JButton();
        btnExportar = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLuces = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblFrenos = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSuspension = new javax.swing.JTable();
        progreso = new javax.swing.JProgressBar();

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

        tblLuces.setModel(modeloLuces);
        tblLuces.setName("Luces"); // NOI18N
        jScrollPane1.setViewportView(tblLuces);

        jTabbedPane1.addTab("Luces", jScrollPane1);

        tblFrenos.setModel(modeloFrenos);
        tblFrenos.setName("Frenos"); // NOI18N
        jScrollPane2.setViewportView(tblFrenos);

        jTabbedPane1.addTab("Frenos", jScrollPane2);

        tblSuspension.setModel(modeloSuspension);
        tblSuspension.setName("Suspension"); // NOI18N
        jScrollPane3.setViewportView(tblSuspension);

        jTabbedPane1.addTab("Suspension", jScrollPane3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dteInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dteFin, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnGenerar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnExportar)
                .addContainerGap(224, Short.MAX_VALUE))
            .addComponent(jTabbedPane1)
            .addComponent(progreso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(dteInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dteFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGenerar)
                    .addComponent(btnExportar))
                .addGap(4, 4, 4)
                .addComponent(progreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE))
        );

        setBounds(0, 0, 816, 639);
    }// </editor-fold>//GEN-END:initComponents

    private void btnGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarActionPerformed
        // TODO add your handling code here:
        if (dteInicio.getDate() == null || dteFin.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Seleccione Fechas");
            return;
        }
        //progreso.setValue(1);
        SwingUtilities.invokeLater(
                new Runnable() {

            @Override
            public void run() {
                fillData(dteInicio.getDate(), dteFin.getDate());
            }
        });
    }//GEN-LAST:event_btnGenerarActionPerformed

    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarActionPerformed
        // TODO add your handling code here:
        List<JTable> tables = new ArrayList<>();
        tables.add(tblFrenos);
        tables.add(tblLuces);
        tables.add(tblSuspension);

        GenericExportExcel excel = new GenericExportExcel();
        excel.exportExcel(tables);
    }//GEN-LAST:event_btnExportarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btnGenerar;
    private org.jdesktop.swingx.JXDatePicker dteFin;
    private org.jdesktop.swingx.JXDatePicker dteInicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JProgressBar progreso;
    private javax.swing.JTable tblFrenos;
    private javax.swing.JTable tblLuces;
    private javax.swing.JTable tblSuspension;
    // End of variables declaration//GEN-END:variables
}
