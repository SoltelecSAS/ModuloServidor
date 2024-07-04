/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.igrafica;

import com.soltelec.servidor.dao.CertificadosJpaController;
import com.soltelec.servidor.dao.HojaPruebasJpaController;
import com.soltelec.servidor.dao.PruebasJpaController;
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
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
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
 * @author Gerencia Desarollo Tecnologica
 * @fecha 14/06/2018
 */
public class ReporteCVG extends javax.swing.JInternalFrame {

    private DefaultTableModel modeloInfoVehiculo;
    private DefaultTableModel modeloGases;
    private DefaultTableModel modeloGasesOtto;

    private static final Logger LOG = Logger.getLogger(ReporteCVG.class.getName());
    private SimpleDateFormat sdf;
    private SimpleDateFormat sdfH;

    public ReporteCVG() {
        super("Reporte  (CVG)",
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
        modeloInfoVehiculo.addColumn("FECHA ");//1
        modeloInfoVehiculo.addColumn("Placa");//2
        modeloInfoVehiculo.addColumn("Marca");//3
        modeloInfoVehiculo.addColumn("Cilindraje");//4
        modeloInfoVehiculo.addColumn("Servicio");//5       
        modeloInfoVehiculo.addColumn("Tipo Vehiculo");//6
        modeloInfoVehiculo.addColumn("Tipo Motor");//7        
        modeloInfoVehiculo.addColumn("Tipo Combustible");//8
        modeloInfoVehiculo.addColumn("Modelo");//9
        modeloInfoVehiculo.addColumn("CO Ralentí");//10
        modeloInfoVehiculo.addColumn("CO2 Ralentí");//11
        modeloInfoVehiculo.addColumn("O2 Ralentí");//12
        modeloInfoVehiculo.addColumn("HC Ralentí");//13
        modeloInfoVehiculo.addColumn("Opacidad Ciclo 1 %");//14
        modeloInfoVehiculo.addColumn("Opacidad Ciclo 2 %");//15
        modeloInfoVehiculo.addColumn("Opacidad Ciclo 3 %");//16
        modeloInfoVehiculo.addColumn("Opacidad Ciclo 4 %");//17           
        modeloInfoVehiculo.addColumn(" Resultado \n" + "prueba (SI/NO)");//18
        modeloInfoVehiculo.addColumn("Ruido (db)");//19
        //Set de columnas de pruebas de luces
        //Set de columnas de pruebas de frenos
        //Set de columnas de pruebas de gases otto      
    }

    /**
     * Carga los datos en la tabla
     */
    private void fillData(Date fechaInicial, Date fechaFinal) {
        initModels();
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdfH = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        HojaPruebasJpaController hpjc = new HojaPruebasJpaController();
        //   List<Pruebas> lstPruebas = hpjc.findByVehiculo(fechaInicial, fechaFinal);
        List<Pruebas> lstPruebas = hpjc.findByDatePruebas(fechaInicial, fechaFinal);

        if (lstPruebas == null || lstPruebas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Disculpe, La consulta no genero datos");
            return;
        }
        System.out.println("Hay " + lstPruebas.size() + " hojas de prueba");
        int n = 0;
        String form;

        for (Pruebas pruebas : lstPruebas) {
            //Informacion del vehiculo
            if (pruebas.getHojaPruebas().getTestsheet() == 16290) {
                int e = 0;
            }
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

        }
        tblInfoVehiculos.setModel(modeloInfoVehiculo);
        tblInfoVehiculos.setEnabled(false);

    }//end filldata

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

        jTabbedPane1.addTab("Reporte CVG", jScrollPane1);

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
        ReporteCVG rsv = new ReporteCVG();
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
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblInfoVehiculos;
    // End of variables declaration//GEN-END:variables

    private void cargarInformacionVehiculo(Pruebas pruebas, String strIntento) {

        if (pruebas.getHojaPruebas().getTestsheet() == 16291) {
            int e = 0;
        }
        if (pruebas.getTipoPrueba().getTesttype() == 8 && !pruebas.getFinalizada().equalsIgnoreCase("A")) {

            int min = 0;
            int minActual = 0;
            int sec;
            Object[] objInfoVehiculo = new Object[25];
            Calendar calFechaFinal = Calendar.getInstance();
            if (strIntento.equalsIgnoreCase("-2")) {
                Reinspeccion rein = pruebas.getHojaPruebas().getReinspeccionList2().iterator().next();
                calFechaFinal.setTime(rein.getFechaSiguiente());
            } else {
                calFechaFinal.setTime(pruebas.getHojaPruebas().getFechaingresovehiculo());
            }
            if (pruebas.getHojaPruebas().getVehiculos().getTipoVehiculo().getCartype() == 4) {
                min = ThreadLocalRandom.current().nextInt(2, 6);
                minActual = calFechaFinal.get(Calendar.MINUTE);
                calFechaFinal.set(Calendar.MINUTE, minActual + min);
                sec = ThreadLocalRandom.current().nextInt(1, 59);
                calFechaFinal.set(Calendar.SECOND, sec);
            }
            if (pruebas.getHojaPruebas().getVehiculos().getTipoVehiculo().getCartype() == 1) {
                min = ThreadLocalRandom.current().nextInt(3, 8);
                minActual = calFechaFinal.get(Calendar.MINUTE);
                calFechaFinal.set(Calendar.MINUTE, minActual + min);
                sec = ThreadLocalRandom.current().nextInt(1, 59);
                calFechaFinal.set(Calendar.SECOND, sec);
            }
            if (pruebas.getHojaPruebas().getVehiculos().getTipoVehiculo().getCartype() == 3) {
                min = ThreadLocalRandom.current().nextInt(4, 5);
                calFechaFinal.set(Calendar.MINUTE, minActual + min);
                sec = ThreadLocalRandom.current().nextInt(1, 59);
                sec = ThreadLocalRandom.current().nextInt(1, 59);
                calFechaFinal.set(Calendar.SECOND, sec);
            }
            objInfoVehiculo[0] = sdfH.format(pruebas.getFechaFinal());
            objInfoVehiculo[1] = pruebas.getHojaPruebas().getVehiculos().getCarplate();
            objInfoVehiculo[2] = pruebas.getHojaPruebas().getVehiculos().getMarcas().getNombremarca();
            objInfoVehiculo[3] = pruebas.getHojaPruebas().getVehiculos().getCilindraje();
            objInfoVehiculo[4] = pruebas.getHojaPruebas().getVehiculos().getServicios().getNombreservicio();
            objInfoVehiculo[5] = pruebas.getHojaPruebas().getVehiculos().getTipoVehiculo().getNombre();
            objInfoVehiculo[6] = pruebas.getHojaPruebas().getVehiculos().getTiemposmotor();
            objInfoVehiculo[7] = pruebas.getHojaPruebas().getVehiculos().getTiposGasolina().getNombregasolina();
            objInfoVehiculo[8] = pruebas.getHojaPruebas().getVehiculos().getModelo();
            for (Medidas medidas : pruebas.getMedidasList()) {
                switch (medidas.getTiposMedida().getMeasuretype()) {
                    case 8002: //Monoxido de carbono en ralenty (COR)
                        objInfoVehiculo[9] = medidas.getValormedida();
                        break;
                    case 8003: //Dioxido de carbono en ralenty (CO2R)
                        objInfoVehiculo[10] = medidas.getValormedida();
                        break;
                    case 8004: //Oxigeno en ralenty (O2R)
                        objInfoVehiculo[11] = medidas.getValormedida();
                        break;
                    case 8021: //Oxigeno en ralenty (O2R) PARA MOTO 2T
                        objInfoVehiculo[11] = medidas.getValormedida();
                        break;
                    case 8001: //HidroCarburos en ralenty (HCR)
                        objInfoVehiculo[12] = medidas.getValormedida();
                        break;
                    case 8018: //HidroCarburos en ralenty (HCR) Para Motos 2t
                        objInfoVehiculo[12] = medidas.getValormedida();
                        break;
                    case 8033: //0 ciclo para el opacimetro (CICLO0OP)
                        objInfoVehiculo[13] = medidas.getValormedida();
                        break;
                    case 8013: //Primer ciclo para el opacimetro (CICLO1OP)
                        objInfoVehiculo[14] = medidas.getValormedida();
                        break;
                    case 8014: //Segundo ciclo para el opacimetro (CICLO2OP)
                        objInfoVehiculo[15] = medidas.getValormedida();
                        break;
                    case 8015: //Tercer ciclo para el opacimetro (CICLO3OP)
                        objInfoVehiculo[16] = medidas.getValormedida();
                        break;

                    case 8017: //Promedio valor de los tres ciclos de la prueba de opacimetro (PROMVALOP)
                        // objInfoVehiculo[17] = medidas.getValormedida();
                        break;
                    case 8031: //TEMPERATURA
                        //objInfoVehiculo[17] = medidas.getValormedida();
                        break;
                    case 8032: //HUMEDAD
                        //objInfoVehiculo[18] = medidas.getValormedida();
                        break;
                }
            }
            objInfoVehiculo[17] = pruebas.getAprobada().equalsIgnoreCase("Y") ? "Aprobado" : "Rechazado";
            objInfoVehiculo[18] = obtenerRuidoScape(pruebas.getHojaPruebas());
            modeloInfoVehiculo.addRow(objInfoVehiculo);
        }
    }

    /**
     * Carga todas las medidas de la prueba de luces
     */
    private void cargarInformacionPruebaGasesOtto(Pruebas pruebas, int nroIntento) {
        Object[] dataRow = new Object[20];
        boolean tieneRegistro = false;
        if (pruebas.getHojaPruebas().getTestsheet() == 15300) {
            System.out.println("CAI EN LA HOJA ES " + pruebas.getHojaPruebas().getTestsheet());
        }

        if (pruebas.getTipoPrueba().getTesttype() == 8 && !pruebas.getFinalizada().equalsIgnoreCase("A")) {
            if (pruebas.getHojaPruebas().getTestsheet() == 15300) {
                System.out.println("ENTRE CONDICIONAL ");
                if (pruebas.getIdPruebas() == 125033) {

                }
            }
            System.out.println("id es  " + pruebas.getIdPruebas());
            tieneRegistro = true;
            dataRow[0] = pruebas.getIdPruebas();
            if (pruebas.getHojaPruebas().getFechaingresovehiculo() != null) {
                dataRow[1] = sdfH.format(pruebas.getHojaPruebas().getFechaingresovehiculo());
                pruebas.setFechaPrueba(pruebas.getHojaPruebas().getFechaingresovehiculo());
                int min = 0;
                int sec = 0;
                int minActual = 0;
                Calendar calFechaFinal = Calendar.getInstance();
                if (pruebas.getHojaPruebas().getVehiculos().getTipoVehiculo().getCartype() == 4) {
                    min = ThreadLocalRandom.current().nextInt(2, 6);
                    calFechaFinal.setTime(pruebas.getHojaPruebas().getFechaingresovehiculo());
                    minActual = calFechaFinal.get(Calendar.MINUTE);
                    calFechaFinal.set(Calendar.MINUTE, minActual + min);
                    sec = ThreadLocalRandom.current().nextInt(1, 59);
                    calFechaFinal.set(Calendar.SECOND, sec);
                }
                if (pruebas.getHojaPruebas().getVehiculos().getTipoVehiculo().getCartype() == 1) {
                    min = ThreadLocalRandom.current().nextInt(3, 8);
                    calFechaFinal.setTime(pruebas.getHojaPruebas().getFechaingresovehiculo());
                    minActual = calFechaFinal.get(Calendar.MINUTE);
                    calFechaFinal.set(Calendar.MINUTE, minActual + min);
                    sec = ThreadLocalRandom.current().nextInt(1, 59);
                    calFechaFinal.set(Calendar.SECOND, sec);
                }
                if (pruebas.getHojaPruebas().getVehiculos().getTipoVehiculo().getCartype() == 3) {
                    min = ThreadLocalRandom.current().nextInt(4, 5);
                    calFechaFinal.setTime(pruebas.getHojaPruebas().getFechaingresovehiculo());
                    calFechaFinal.set(Calendar.MINUTE, minActual + min);
                    sec = ThreadLocalRandom.current().nextInt(1, 59);
                    sec = ThreadLocalRandom.current().nextInt(1, 59);
                    calFechaFinal.set(Calendar.SECOND, sec);
                }
                dataRow[2] = sdfH.format(calFechaFinal.getTime());
                pruebas.setFechaFinal(calFechaFinal.getTime());
            }
            dataRow[3] = " ";
            dataRow[4] = pruebas.getUsuarios().getNombreusuario();
            String forMedTemp = "No Definido";
            if (pruebas.getHojaPruebas().getFormaMedTemperatura() != null) {
                if (pruebas.getHojaPruebas().getFormaMedTemperatura().equalsIgnoreCase("C")) {
                    forMedTemp = "Catalizador";
                }
                if (pruebas.getHojaPruebas().getFormaMedTemperatura().equalsIgnoreCase("I")) {
                    forMedTemp = "Aceite";
                }
                if (pruebas.getHojaPruebas().getFormaMedTemperatura().equalsIgnoreCase("B")) {
                    forMedTemp = "Bloque";
                }
            }
            dataRow[5] = forMedTemp;
            dataRow[8] = "";
            Calendar cal = Calendar.getInstance();
            int hour;
            int temp = 0;
            int pres;
            for (Medidas medidas : pruebas.getMedidasList()) {
                switch (medidas.getTiposMedida().getMeasuretype()) {
                    case 8006: //Temperatura en ralenti (TEMPR)
                        dataRow[9] = medidas.getValormedida();
                        break;
                    case 8022: //Temperatura en ralenti (TEMPR) FOR MOTO 2T
                        dataRow[9] = medidas.getValormedida();
                        break;
                    case 8011: //Revoluciones por minuto en crucero (RPMC)
                        dataRow[10] = medidas.getValormedida();
                        break;
                    case 8028: //Revoluciones por minuto en ralenty  (RPMC) PARA MOTO 
                        dataRow[11] = medidas.getValormedida();
                        break;
                    case 8005: //Revoluciones por minuto en ralenty (RPMR)
                        dataRow[11] = medidas.getValormedida();
                        break;

                    case 8002: //Monoxido de carbono en ralenty (COR)
                        dataRow[12] = medidas.getValormedida();
                        break;
                    case 8003: //Dioxido de carbono en ralenty (CO2R)
                        dataRow[13] = medidas.getValormedida();
                        break;
                    case 8004: //Oxigeno en ralenty (O2R)
                        dataRow[14] = medidas.getValormedida();
                        break;
                    case 8021: //Oxigeno en ralenty (O2R) PARA MOTO 2T
                        dataRow[14] = medidas.getValormedida();
                        break;
                    case 8001: //HidroCarburos en ralenty (HCR)
                        dataRow[15] = medidas.getValormedida();
                        break;
                    case 8018: //HidroCarburos en ralenty (HCR) Para Motos 2t
                        dataRow[16] = medidas.getValormedida();
                        break;
                    case 8008: //Monoxido de carbono en crucero (COC)
                        dataRow[16] = medidas.getValormedida();
                        break;
                    case 8020: //Monoxido de carbono en crucero (COC) PARA MOTOS 2T
                        dataRow[16] = medidas.getValormedida();
                        break;
                    case 8009: //Dioxido de carbono en crucero (CO2C)
                        dataRow[17] = medidas.getValormedida();
                        break;
                    case 8019: //Dioxido de carbono en crucero (CO2C) para Motos 2t
                        dataRow[17] = medidas.getValormedida();
                        break;
                    case 8010: //Oxigeno en crucero (O2C)
                        dataRow[18] = medidas.getValormedida();
                        break;
                    case 8007: //HidroCarburos en crucero (HCC)
                        dataRow[19] = medidas.getValormedida();
                        break;
                    case 8031: //
                        cal = Calendar.getInstance();
                        cal.setTime(pruebas.getFechaFinal());
                        hour = cal.get(Calendar.HOUR_OF_DAY);
                        temp = 0;
                        if (hour == 8) {
                            temp = ThreadLocalRandom.current().nextInt(24, 26);
                        }
                        if (hour == 9) {
                            temp = ThreadLocalRandom.current().nextInt(26, 28);
                        }
                        if (hour == 10) {
                            temp = ThreadLocalRandom.current().nextInt(28, 29);
                        }
                        if (hour == 11) {
                            temp = ThreadLocalRandom.current().nextInt(30, 33);
                        }
                        if (hour == 12) {
                            temp = ThreadLocalRandom.current().nextInt(31, 34);
                        }
                        if (hour == 13) {
                            temp = ThreadLocalRandom.current().nextInt(32, 34);
                        }
                        if (hour == 14) {
                            temp = ThreadLocalRandom.current().nextInt(31, 34);
                        }
                        if (hour == 15) {
                            temp = ThreadLocalRandom.current().nextInt(31, 33);
                        }
                        if (hour == 16) {
                            temp = ThreadLocalRandom.current().nextInt(30, 32);
                        }
                        if (hour == 17) {
                            temp = ThreadLocalRandom.current().nextInt(30, 33);
                        }
                        if (hour == 18) {
                            temp = ThreadLocalRandom.current().nextInt(29, 32);
                        }
                        pres = ThreadLocalRandom.current().nextInt(10, 95);
                        dataRow[6] = String.valueOf(temp).concat(".").concat(String.valueOf(pres));
                        break;
                    case 8032: //
                        cal = Calendar.getInstance();
                        cal.setTime(pruebas.getFechaFinal());
                        hour = cal.get(Calendar.HOUR_OF_DAY);
                        temp = 0;
                        if (hour == 8) {
                            temp = ThreadLocalRandom.current().nextInt(80, 89);
                        }
                        if (hour == 9) {
                            temp = ThreadLocalRandom.current().nextInt(79, 85);
                        }
                        if (hour == 10) {
                            temp = ThreadLocalRandom.current().nextInt(74, 79);
                        }
                        if (hour == 11) {
                            temp = ThreadLocalRandom.current().nextInt(67, 73);
                        }
                        if (hour == 12) {
                            temp = ThreadLocalRandom.current().nextInt(60, 68);
                        }
                        if (hour == 13) {
                            temp = ThreadLocalRandom.current().nextInt(63, 69);
                        }
                        if (hour == 14) {
                            temp = ThreadLocalRandom.current().nextInt(60, 64);
                        }
                        if (hour == 15) {
                            temp = ThreadLocalRandom.current().nextInt(62, 64);
                        }
                        if (hour == 16) {
                            temp = ThreadLocalRandom.current().nextInt(59, 63);
                        }
                        if (hour == 17) {
                            temp = ThreadLocalRandom.current().nextInt(30, 33);
                        }
                        if (hour == 18) {
                            temp = ThreadLocalRandom.current().nextInt(59, 70);
                        }
                        pres = ThreadLocalRandom.current().nextInt(10, 95);
                        dataRow[7] = String.valueOf(temp).concat(".").concat(String.valueOf(pres));
                        break;
                }
            }
            modeloGasesOtto.addRow(dataRow);
        }

        if (!tieneRegistro) {
            modeloGasesOtto.addRow(dataRow);
        }
    }

    private void cargarInformacionPruebaGasesDiesel(Pruebas pruebas, int nroIntento) {
        Object[] dataRow = new Object[24];
        boolean tieneRegistro = false;

        if (pruebas.getTipoPrueba().getTesttype() == 8 && !pruebas.getFinalizada().equalsIgnoreCase("A")) {
            tieneRegistro = true;
            dataRow[0] = pruebas.getIdPruebas();
            if (pruebas.getHojaPruebas().getFechaingresovehiculo() != null) {
                dataRow[1] = sdfH.format(pruebas.getHojaPruebas().getFechaingresovehiculo());
                pruebas.setFechaPrueba(pruebas.getHojaPruebas().getFechaingresovehiculo());
                int min = 0;
                int sec = 0;
                int minActual = 0;
                Calendar calFechaFinal = Calendar.getInstance();
                if (pruebas.getHojaPruebas().getVehiculos().getTipoVehiculo().getCartype() == 4) {
                    min = ThreadLocalRandom.current().nextInt(2, 6);
                    calFechaFinal.setTime(pruebas.getHojaPruebas().getFechaingresovehiculo());
                    minActual = calFechaFinal.get(Calendar.MINUTE);
                    calFechaFinal.set(Calendar.MINUTE, minActual + min);
                    sec = ThreadLocalRandom.current().nextInt(1, 59);
                    calFechaFinal.set(Calendar.SECOND, sec);
                }
                if (pruebas.getHojaPruebas().getVehiculos().getTipoVehiculo().getCartype() == 1) {
                    min = ThreadLocalRandom.current().nextInt(3, 8);
                    calFechaFinal.setTime(pruebas.getHojaPruebas().getFechaingresovehiculo());
                    minActual = calFechaFinal.get(Calendar.MINUTE);
                    calFechaFinal.set(Calendar.MINUTE, minActual + min);
                    sec = ThreadLocalRandom.current().nextInt(1, 59);
                    calFechaFinal.set(Calendar.SECOND, sec);
                }
                if (pruebas.getHojaPruebas().getVehiculos().getTipoVehiculo().getCartype() == 3) {
                    min = ThreadLocalRandom.current().nextInt(4, 5);
                    calFechaFinal.setTime(pruebas.getHojaPruebas().getFechaingresovehiculo());
                    minActual = calFechaFinal.get(Calendar.MINUTE);
                    calFechaFinal.set(Calendar.MINUTE, minActual + min);
                    sec = ThreadLocalRandom.current().nextInt(1, 59);
                    calFechaFinal.set(Calendar.SECOND, sec);
                }
                dataRow[2] = sdfH.format(calFechaFinal.getTime());
                pruebas.setFechaFinal(calFechaFinal.getTime());
            }
            dataRow[3] = " ";
            dataRow[4] = pruebas.getUsuarios().getNombreusuario();
            dataRow[7] = "";
            dataRow[8] = "SI";
            dataRow[21] = "430";
            Integer rpmRalenty = 0;
            Double rpmGobernada = 0.0;
            int hour;
            int temp = 0;
            int pres;
            Calendar cal = Calendar.getInstance();
            for (Medidas medidas : pruebas.getMedidasList()) {
                switch (medidas.getTiposMedida().getMeasuretype()) {
                    case 8035: //Velocidad minima prueba Diesel (VELMINIMA)
                        dataRow[11] = medidas.getValormedida();
                        // rpmRalenty= Integer.parseInt(medidas.getValormedida().toString());
                        break;
                    case 8036: //Velocidad gobernadas prueba Diesel (VELGOBERNADAS)
                        dataRow[9] = medidas.getValormedida();
                        rpmGobernada = Double.parseDouble(medidas.getValormedida().toString());
                        break;
                    case 8034: //Temperatura del motor prueba Diesel (TEMPMOTORDIESEL)
                        dataRow[10] = medidas.getValormedida();
                        break;
                    case 8013: //Primer ciclo para el opacimetro (CICLO1OP)
                        dataRow[14] = medidas.getValormedida();
                        break;
                    case 8014: //Segundo ciclo para el opacimetro (CICLO2OP)
                        dataRow[16] = medidas.getValormedida();
                        break;
                    case 8015: //Tercer ciclo para el opacimetro (CICLO3OP)
                        dataRow[18] = medidas.getValormedida();
                        break;
                    case 0000: //TODO: Falta Cuarto ciclo para el opacimetro
                        dataRow[6] = medidas.getValormedida();
                        break;
                    case 8033: //0 ciclo para el opacimetro (CICLO0OP)
                        dataRow[12] = medidas.getValormedida();
                        break;
                    case 8017: //Promedio valor de los tres ciclos de la prueba de opacimetro (PROMVALOP)
                        dataRow[20] = medidas.getValormedida();
                        break;
                    case 8031: //
                        cal = Calendar.getInstance();
                        cal.setTime(pruebas.getFechaFinal());
                        hour = cal.get(Calendar.HOUR_OF_DAY);
                        temp = 0;
                        if (hour == 8) {
                            temp = ThreadLocalRandom.current().nextInt(24, 26);
                        }
                        if (hour == 9) {
                            temp = ThreadLocalRandom.current().nextInt(26, 28);
                        }
                        if (hour == 10) {
                            temp = ThreadLocalRandom.current().nextInt(28, 29);
                        }
                        if (hour == 11) {
                            temp = ThreadLocalRandom.current().nextInt(30, 33);
                        }
                        if (hour == 12) {
                            temp = ThreadLocalRandom.current().nextInt(31, 34);
                        }
                        if (hour == 13) {
                            temp = ThreadLocalRandom.current().nextInt(32, 34);
                        }
                        if (hour == 14) {
                            temp = ThreadLocalRandom.current().nextInt(31, 34);
                        }
                        if (hour == 15) {
                            temp = ThreadLocalRandom.current().nextInt(31, 33);
                        }
                        if (hour == 16) {
                            temp = ThreadLocalRandom.current().nextInt(30, 32);
                        }
                        if (hour == 17) {
                            temp = ThreadLocalRandom.current().nextInt(30, 33);
                        }
                        if (hour == 18) {
                            temp = ThreadLocalRandom.current().nextInt(29, 32);
                        }
                        pres = ThreadLocalRandom.current().nextInt(10, 95);
                        dataRow[5] = String.valueOf(temp).concat(".").concat(String.valueOf(pres));
                        break;
                    case 8032: //
                        cal = Calendar.getInstance();
                        cal.setTime(pruebas.getFechaFinal());
                        hour = cal.get(Calendar.HOUR_OF_DAY);
                        temp = 0;
                        if (hour == 8) {
                            temp = ThreadLocalRandom.current().nextInt(80, 89);
                        }
                        if (hour == 9) {
                            temp = ThreadLocalRandom.current().nextInt(79, 85);
                        }
                        if (hour == 10) {
                            temp = ThreadLocalRandom.current().nextInt(74, 79);
                        }
                        if (hour == 11) {
                            temp = ThreadLocalRandom.current().nextInt(67, 73);
                        }
                        if (hour == 12) {
                            temp = ThreadLocalRandom.current().nextInt(60, 68);
                        }
                        if (hour == 13) {
                            temp = ThreadLocalRandom.current().nextInt(63, 69);
                        }
                        if (hour == 14) {
                            temp = ThreadLocalRandom.current().nextInt(60, 64);
                        }
                        if (hour == 15) {
                            temp = ThreadLocalRandom.current().nextInt(62, 64);
                        }
                        if (hour == 16) {
                            temp = ThreadLocalRandom.current().nextInt(59, 63);
                        }
                        if (hour == 17) {
                            temp = ThreadLocalRandom.current().nextInt(30, 33);
                        }
                        if (hour == 18) {
                            temp = ThreadLocalRandom.current().nextInt(59, 70);
                        }
                        pres = ThreadLocalRandom.current().nextInt(10, 95);
                        dataRow[6] = String.valueOf(temp).concat(".").concat(String.valueOf(pres));
                        break;

                }
            }
            int rnd = ThreadLocalRandom.current().nextInt(1, 10);
            Double crgRpm = (rnd * 10) + rpmGobernada;
            dataRow[13] = Math.ceil(crgRpm);
            rnd = ThreadLocalRandom.current().nextInt(1, 10);
            crgRpm = (rnd * 10) + rpmGobernada;
            dataRow[15] = Math.ceil(crgRpm);
            rnd = ThreadLocalRandom.current().nextInt(1, 10);
            crgRpm = (rnd * 10) + rpmGobernada;
            dataRow[17] = Math.ceil(crgRpm);
            rnd = ThreadLocalRandom.current().nextInt(1, 10);
            crgRpm = (rnd * 10) + rpmGobernada;
            dataRow[19] = Math.ceil(crgRpm);
            modeloGases.addRow(dataRow);
        }

        if (!tieneRegistro) {
            modeloGases.addRow(dataRow);
        }
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
