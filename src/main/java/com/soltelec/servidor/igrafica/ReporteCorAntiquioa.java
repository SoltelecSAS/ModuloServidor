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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class ReporteCorAntiquioa extends javax.swing.JInternalFrame {

    private DefaultTableModel modeloInfoVehiculo;    
    private DefaultTableModel modeloGasesOtto;
    
    private SimpleDateFormat sdfH;

    public ReporteCorAntiquioa() {
        super("Reporte  (CorAntioquia)",
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
        modeloInfoVehiculo.addColumn("NIT");//0
        modeloInfoVehiculo.addColumn("CLASE CDA");//1        
        modeloInfoVehiculo.addColumn("No. Certificado");//3
        modeloInfoVehiculo.addColumn("No. Consecutivo RUNT");//4
        modeloInfoVehiculo.addColumn("Marca");//9
        modeloInfoVehiculo.addColumn("Linea");//10
        modeloInfoVehiculo.addColumn("Modelo");//11
        modeloInfoVehiculo.addColumn("Placa");//6
        modeloInfoVehiculo.addColumn("Cilindraje");//16
        modeloInfoVehiculo.addColumn("Clase");//8
        modeloInfoVehiculo.addColumn("Servicio");//16
        modeloGasesOtto = new DefaultTableModel();
        modeloGasesOtto.addColumn("Gases Temp. Motor");//12
        modeloGasesOtto.addColumn("Gases Ralenty RPM");//12        
        modeloGasesOtto.addColumn("Gases Ralenty HC");//12
        modeloGasesOtto.addColumn("Gases Ralenty CO");//12
        modeloGasesOtto.addColumn("Gases Ralenty CO2");//12
        modeloGasesOtto.addColumn("Gases Ralenty O2");//12        
        
        modeloGasesOtto.addColumn("SONIDO RUIDO MOTOR");//13
        modeloGasesOtto.addColumn("Resultado Pruebas Gases");//13
        modeloGasesOtto.addColumn("Fecha Prueba");//12
        
         

        //Set de columnas de pruebas de luces
        //Set de columnas de pruebas de frenos
        //Set de columnas de pruebas de gases otto
      
        
        
        
       
       
    }

    /**
     * Carga los datos en la tabla
     */
    private void fillData(Date fechaInicial, Date fechaFinal) {
        initModels();
        sdfH = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        HojaPruebasJpaController hpjc = new HojaPruebasJpaController();
        List<Pruebas> lstPruebas = hpjc.findByDatePruebas(fechaInicial, fechaFinal);
        if (lstPruebas == null || lstPruebas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La consulta no genero datos");
            return;
        }

        System.out.println("Hay " + lstPruebas.size() + " hojas de prueba");
        int n = 0;
        String form;      


        

        for (Pruebas pruebas : lstPruebas) {
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
            cargarInformacionVehiculo(pruebas, form);
            if (pruebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype() == 4 || pruebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype() == 1 || pruebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype() == 10) {
                cargarInformacionPruebaGasesOtto(pruebas, n);                
            }
            //Informacion prueba Gases otto              
            
        }
        tblInfoVehiculos.setModel(modeloInfoVehiculo);
        tblPruebaGasesOtto.setModel(modeloGasesOtto);        
    }

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
        jScrollPane7 = new javax.swing.JScrollPane();
        tblPruebaGasesOtto = new javax.swing.JTable();

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

        tblPruebaGasesOtto.setModel(modeloGasesOtto);
        tblPruebaGasesOtto.setName("Prueba de desviación"); // NOI18N
        jScrollPane7.setViewportView(tblPruebaGasesOtto);

        jTabbedPane1.addTab("Prueba de gases vehiculos ciclo otto", jScrollPane7);

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
        ReporteCorAntiquioa rsv = new ReporteCorAntiquioa();
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
        tables.add(tblPruebaGasesOtto);
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
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblInfoVehiculos;
    private javax.swing.JTable tblPruebaGasesOtto;
    // End of variables declaration//GEN-END:variables

    private void cargarInformacionVehiculo(Pruebas pruebas, String strIntento) {
        CertificadosJpaController cjc = new CertificadosJpaController();
        Certificados certificado = cjc.findCertificadoHojaPrueba(pruebas.getHojaPruebas().getTestsheet());
    
        Object[] objInfoVehiculo = new Object[11];
        //objInfoVehiculo[2] = String.valueOf(pruebas.getHojaPruebas().getConHojaPrueba()).concat(strIntento);
        /*if (strIntento.equalsIgnoreCase("-2")) {
            Reinspeccion rein = pruebas.getHojaPruebas().getReinspeccionList2().iterator().next();
            objInfoVehiculo[1] = sdf.format(rein.getFechaSiguiente());
        } else {
            objInfoVehiculo[1] = sdf.format(pruebas.getHojaPruebas().getFechaingresovehiculo());
        }
        if (pruebas.getHojaPruebas().getReinspeccionList2().size() > 0 && strIntento.equalsIgnoreCase("-1")) {
            objInfoVehiculo[2] = "Rechazado";
            deci =0;
            
        } else {
            objInfoVehiculo[2] = "Y".equals(pruebas.getHojaPruebas().getAprobado()) ? "Aprobado" : "Rechazado";
        }
        if ( pruebas.getAprobada().equalsIgnoreCase("Y")) {
            objInfoVehiculo[3] = certificado.getConsecutive();
        } else {
            objInfoVehiculo[3] = "";
        }*/
        
        objInfoVehiculo[2] = certificado.getConsecutive();
        objInfoVehiculo[3] = certificado.getConsecutivoRunt();
        objInfoVehiculo[4] = pruebas.getHojaPruebas().getVehiculos().getMarcas().getNombremarca(); 
        objInfoVehiculo[5] = pruebas.getHojaPruebas().getVehiculos().getLineasVehiculos().getCrlname();
        objInfoVehiculo[6] = pruebas.getHojaPruebas().getVehiculos().getModelo();
        objInfoVehiculo[7] =  pruebas.getHojaPruebas().getVehiculos().getCarplate();
        objInfoVehiculo[8] = pruebas.getHojaPruebas().getVehiculos().getCilindraje();
        objInfoVehiculo[9] = pruebas.getHojaPruebas().getVehiculos().getClasesVehiculo().getNombreclase();
        objInfoVehiculo[10] = pruebas.getHojaPruebas().getVehiculos().getServicios().getNombreservicio();  
       
        modeloInfoVehiculo.addRow(objInfoVehiculo);
    }

    /**
     * Carga todas las medidas de la prueba de luces
     */
    private void cargarInformacionPruebaGasesOtto(Pruebas pruebas, int nroIntento) {
         sdfH = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Object[] dataRow = new Object[9];
        boolean tieneRegistro = false; 
        if (pruebas.getTipoPrueba().getTesttype() == 8 && !pruebas.getFinalizada().equalsIgnoreCase("A")) {
            if (pruebas.getHojaPruebas().getTestsheet() == 15300) {
                System.out.println("ENTRE CONDICIONAL ");
                if (pruebas.getIdPruebas() == 125033) {

                }
            }
            System.out.println("id es  " + pruebas.getIdPruebas());
            tieneRegistro = true;
            //dataRow[0] = pruebas.getIdPruebas();
          
            for (Medidas medidas : pruebas.getMedidasList()) {
                switch (medidas.getTiposMedida().getMeasuretype()) {
                    case 8006: //Temperatura en ralenti (TEMPR)
                        dataRow[0] = medidas.getValormedida();                        
                        break;
                    case 8022: //Temperatura en ralenti (TEMPR) FOR MOTO 2T
                        dataRow[0] = medidas.getValormedida();
                        break;
                    case 8012: //Temperatura en ralenti (TEMPR) FOR MOTO 4T
                        dataRow[0] = medidas.getValormedida();
                         //valTemp= medidas.getValormedida();
                        break;
                    case 8011: //Revoluciones por minuto en crucero (RPMC)
                        dataRow[1] = medidas.getValormedida();
                        break;
                    case 8028: //Revoluciones por minuto en ralenty  (RPMC) PARA MOTO 
                        dataRow[1] = medidas.getValormedida();
                        break;
                    case 8005: //Revoluciones por minuto en ralenty (RPMR)
                        dataRow[1] = medidas.getValormedida();
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
                        dataRow[2] = medidas.getValormedida();
                        break;
                    case 8018: //HidroCarburos en ralenty (HCR) Para Motos 2t
                        dataRow[2] = medidas.getValormedida();
                        break;
                    case 8008: //Monoxido de carbono en crucero (COC)
                        dataRow[3] = medidas.getValormedida();
                        break;
                    case 8020: //Monoxido de carbono en crucero (COC) PARA MOTOS 2T
                        
                        break;
                    case 8009: //Dioxido de carbono en crucero (CO2C)
                        
                        break;
                    case 8019: //Dioxido de carbono en crucero (CO2C) para Motos 2t
                        
                        break;
                    case 8010: //Oxigeno en crucero (O2C)
                        
                        break;
                    case 8007: //HidroCarburos en crucero (HCC)
                        
                        break;
                    case 8031: //                       condiciones ambientales
                        
                        break;
                    case 8032: //                        
                        
                        break;
                }
            }
           dataRow[6] = obtenerRuidoScape(pruebas.getHojaPruebas());
           String resultadoPrueba;
          if (pruebas.getAprobada().equalsIgnoreCase("Y")) {
                resultadoPrueba = "APROBADA"; 
          }else{
              resultadoPrueba = "RECHAZADA";               
          }            
          dataRow[7] = resultadoPrueba;
           dataRow[8] =sdfH.format(pruebas.getFechaPrueba());          
           modeloGasesOtto.addRow(dataRow);
        }

        if (!tieneRegistro) {
            modeloGasesOtto.addRow(dataRow);
        }
    }



    private String obtenerRuidoScape(HojaPruebas hojaPruebas) {
        double promedioRuidoMotor = 0;
        String ruidoMotor = null;
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(Locale.ENGLISH);
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
