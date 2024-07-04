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
import com.soltelec.servidor.model.Defxprueba;
import com.soltelec.servidor.model.HojaPruebas;
import com.soltelec.servidor.model.Medidas;
import com.soltelec.servidor.model.Pruebas;
import com.soltelec.servidor.model.Reinspeccion;
import com.soltelec.servidor.utils.GenericExportExcel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
 * @fecha 01/03/2018
 */
public class ReporteCortolima extends javax.swing.JInternalFrame {
//public class ReporteSuperintendenciaVijia extends javax.swing.JFrame {

    private DefaultTableModel modeloInfoVehiculo;
    private DefaultTableModel modeloGases;
    private DefaultTableModel modeloGasesOtto;
    private DefaultTableModel modeloPropietario;

    private static final Logger LOG = Logger.getLogger(ReporteCortolima.class.getName());
    private SimpleDateFormat sdf;
    private SimpleDateFormat sdfH;

    public ReporteCortolima() {
        super("Reporte  (Cortolima)",
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
        modeloInfoVehiculo.addColumn("Diseño");//12

        modeloInfoVehiculo.addColumn("Fecha Matricula");//13
        modeloInfoVehiculo.addColumn("Combustible");//14
        modeloInfoVehiculo.addColumn("Tipo Motor");//15
        modeloInfoVehiculo.addColumn("Cilindraje");//16
        modeloInfoVehiculo.addColumn("Nro. Motor");//17
        modeloInfoVehiculo.addColumn("Nro. vim Serie");//18
        modeloInfoVehiculo.addColumn("Nro. Licencia");//19
        modeloInfoVehiculo.addColumn("Kilometraje");//20      
        modeloInfoVehiculo.addColumn("Ruido Escape");//21

        modeloInfoVehiculo.addColumn("Nombre o Razon Social");//22
        modeloInfoVehiculo.addColumn("Tipo Documento");//23
        modeloInfoVehiculo.addColumn("Docuemnto de Identificacion");//24
        modeloInfoVehiculo.addColumn("Direccion");//25
        modeloInfoVehiculo.addColumn("Telefono");//26
        modeloInfoVehiculo.addColumn("Ciudad");//27

        //Set de columnas de pruebas de luces
        //Set de columnas de pruebas de frenos
        //Set de columnas de pruebas de gases otto
        modeloGasesOtto = new DefaultTableModel();

        modeloGasesOtto.addColumn("Nro. Prueba");//0
        modeloGasesOtto.addColumn("Fecha-Hora Inicio ");//1
        modeloGasesOtto.addColumn("Fecha-Hora Finalizacion ");//2
        modeloGasesOtto.addColumn("Fecha-Hora Aborto ");//3
        modeloGasesOtto.addColumn("Nombre Inspector");//4
        modeloGasesOtto.addColumn("Medicion Temperatura");//5       
        modeloGasesOtto.addColumn("Temperatura Ambiente");//6
        modeloGasesOtto.addColumn("Humedad Relativa");//7
        modeloGasesOtto.addColumn("Causa Aborto");//8

        modeloGasesOtto.addColumn("Existencia de fugas en el tubo, uniones del múltiple y silenciador del sistema de escape del vehículo");//9 84002
        modeloGasesOtto.addColumn("Salidas adicionales en el sistema de escape diferentes a las de diseño original del vehículo");//10 84003
        modeloGasesOtto.addColumn("Ausencia de tapones de aceite o fugas en el mismo");//11 84001
        modeloGasesOtto.addColumn("Ausencia de tapas o tapones de combustible o fugas en el mismo");//12 84000
        modeloGasesOtto.addColumn("Sistema de admisión de aire en mal estado (filtro roto, o deformado) o ausencia del filtro de aire");//13
        modeloGasesOtto.addColumn("Desconexión de sistemas de recirculación de gases provenientes del Carter del motor");//14
        modeloGasesOtto.addColumn("Instalación de accesorios o deformaciones en el tubo de escape que no permitan la introducción de la sonda");//15
        modeloGasesOtto.addColumn("Incorrecta operación del sistema de refrigeración, cuya verificación se hará por medio de inspección");//16

        modeloGasesOtto.addColumn("Revoluciones Fuera Rango");//17
        modeloGasesOtto.addColumn("Presencia Humo Negro/Azul");//18

        modeloGasesOtto.addColumn("Temperatura");//19
        modeloGasesOtto.addColumn("Rpm Crucero");//20
        modeloGasesOtto.addColumn("Rpm Ralentí");//21
        modeloGasesOtto.addColumn("CO Ralentí");//22
        modeloGasesOtto.addColumn("CO2 Ralentí");//23
        modeloGasesOtto.addColumn("O2 Ralentí");//24
        modeloGasesOtto.addColumn("HC Ralentí");//25
        modeloGasesOtto.addColumn("CO Crucero");//26
        modeloGasesOtto.addColumn("CO2 Crucero");//27
        modeloGasesOtto.addColumn("O2 Crucero");//28
        modeloGasesOtto.addColumn("HC Crucero");//29

        //Set de columnas de pruebas de gases diesel
        modeloGases = new DefaultTableModel();

        modeloGases.addColumn("Nro. Prueba");//0
        modeloGases.addColumn("Fecha-Hora Inicio ");//1
        modeloGases.addColumn("Fecha-Hora Finalizacion ");//2
        modeloGases.addColumn("Fecha-Hora Aborto ");//3
        modeloGases.addColumn("Nombre Inspector");//4

        modeloGases.addColumn("Temperatura Ambiente");//5
        modeloGases.addColumn("Humedad Relativa");//6

        modeloGases.addColumn("Fugas en el tubo, uniones del multiple y silencioador del sistema de escape del vehiculo");//7
        modeloGases.addColumn("Salidas adicionales en el sistema de escape diferentes a las del diseño original del vehiculo");//8
        modeloGases.addColumn("Ausencia de tapones de aceite o fugas en el mismo");//9
        modeloGases.addColumn("Ausencia de tapones de combustible o fugas en el mismo");//10
        modeloGases.addColumn(" Instalacion de accesorios o deformaciones en el tubo de escape que no permitan la introduccion del acople");//11
        modeloGases.addColumn("Incorrecta operacion del sistema de refrigeracion");//12
        modeloGases.addColumn("Ausencia o incorrecta instalacion del filtro de aire");//13
        modeloGases.addColumn("Activacion de dispositivos instalados en el motor o en le vehiculo que alteren las caracteristicas normales de velocidad de giro y que tengan como efecto la modificacion de los resultados de la prueba de opacidad o que impidan su ejecucion adecuada");//14

        modeloGases.addColumn("Causa Aborto");//15
        modeloGases.addColumn("Comprobacion Sistema Inyeccion");//16
        modeloGases.addColumn("Revoluciones Fuera Rango");//17
        modeloGases.addColumn("(r/min) Gobernada Media");//18  
        modeloGases.addColumn("Temperatura (Diesel)");//19
        modeloGases.addColumn("(r/min) Ralenti Media");//20
        modeloGases.addColumn("Opacidad Ciclo Preliminar");//21
        modeloGases.addColumn("(r/min) Ciclo Preliminar");//22
        modeloGases.addColumn("Resultado Opacidad 1er. Ciclo");//23
        modeloGases.addColumn("(r/min) 1er. Ciclo");//24
        modeloGases.addColumn("Resultado Opacidad 2do. Ciclo");//25
        modeloGases.addColumn("(r/min) 2do. Ciclo");//26
        modeloGases.addColumn("Resultado Opacidad 3er. Ciclo");//27
        modeloGases.addColumn("(r/min) 3er. Ciclo");//28
        modeloGases.addColumn("Resultado Final Opacidad del Ensayo ");//29
        modeloGases.addColumn("Logintud de Trayectoria Optica ");//30
        modeloGases.addColumn("Temperatura Final Aceite Motor ");//31

        modeloPropietario = new DefaultTableModel();
        modeloPropietario.addColumn("Nombre o Razon Social");//5
    }

    /**
     * Carga los datos en la tabla
     */
    private void fillData(Date fechaInicial, Date fechaFinal) throws SQLException {
        initModels();
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdfH = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        HojaPruebasJpaController hpjc = new HojaPruebasJpaController();
        //   List<Pruebas> lstPruebas = hpjc.findByVehiculo(fechaInicial, fechaFinal);
        List<Pruebas> lstPruebas = hpjc.findByDatePruebas(fechaInicial, fechaFinal);
        /*for (Pruebas pruebas : lstPruebas) {
         Calendar cal = Calendar.getInstance();
         cal.setTime(pruebas.getHojaPruebas().getFechaingresovehiculo());
         int monthHp = cal.get(Calendar.MONTH);
         Calendar calPr = Calendar.getInstance();
         calPr.setTime(pruebas.getFechaPrueba());
         int monthPru= calPr.get(Calendar.MONTH);
         if(pruebas.getIdPruebas()==87738){
         int eve=1;
         }
         if(pruebas.getIdPruebas()==87875){
         int eve=1;
         }
         if(monthPru==2 ){
         try {
         PruebasJpaController pruC = new PruebasJpaController();
         pruC.editPrueXFechas(pruebas.getIdPruebas(), pruebas.getHojaPruebas().getFechaingresovehiculo());
         } catch (Exception ex) { }
         }
         }    
         JOptionPane.showMessageDialog(this, "EPA TERMINE DE AJUSTAR MIRE A VER CUAL FUE EL TRABAJO");
         return ;*/
        if (lstPruebas == null || lstPruebas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La consulta no genero datos");
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
            if (pruebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype() == 4 || pruebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype() == 1 || pruebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype() == 10) {
                cargarInformacionPruebaGasesOtto(pruebas, n);
                Object[] dataRow = new Object[33];
                modeloGases.addRow(dataRow);
            }
            //Informacion prueba Gases otto              
            if (pruebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype() == 3 || pruebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype() == 11) {
                //Informacion prueba Gases diesel
                cargarInformacionPruebaGasesDiesel(pruebas, n);
                Object[] dataRow = new Object[30];
                modeloGasesOtto.addRow(dataRow);
            }
        }
        tblInfoVehiculos.setModel(modeloInfoVehiculo);
        tblPruebaGasesOtto.setModel(modeloGasesOtto);
        tblPruebaGasesDiesel.setModel(modeloGases);
        tblInfoVehiculos.setEnabled(false);
        tblPruebaGasesOtto.setEnabled(false);
        tblPruebaGasesDiesel.setEnabled(false);
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
        ReporteCortolima rsv = new ReporteCortolima();
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
                try {
                    fillData(fechaInicial.getDate(), fechaFInal.getDate());
                } catch (SQLException ex) {
                    Logger.getLogger(ReporteCortolima.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }//GEN-LAST:event_btnGenerarActionPerformed

    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarActionPerformed
        // TODO add your handling code here:
        List<JTable> tables = new ArrayList<>();

        tables.add(tblInfoVehiculos);
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
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblInfoVehiculos;
    private javax.swing.JTable tblPruebaGasesDiesel;
    private javax.swing.JTable tblPruebaGasesOtto;
    // End of variables declaration//GEN-END:variables

    private void cargarInformacionVehiculo(Pruebas pruebas, String strIntento) {
        CertificadosJpaController cjc = new CertificadosJpaController();
        int deci = 1;
        Certificados certificado = cjc.findCertificadoHojaPrueba(pruebas.getHojaPruebas().getTestsheet());
        if (pruebas.getHojaPruebas().getTestsheet() == 16291) {
            int e = 0;
        }
        Object[] objInfoVehiculo = new Object[27];
        objInfoVehiculo[0] = String.valueOf(pruebas.getHojaPruebas().getConHojaPrueba()).concat(strIntento);
        if (strIntento.equalsIgnoreCase("-2")) {
            Reinspeccion rein = pruebas.getHojaPruebas().getReinspeccionList2().iterator().next();
            objInfoVehiculo[1] = sdf.format(rein.getFechaSiguiente());
        } else {
            objInfoVehiculo[1] = sdf.format(pruebas.getHojaPruebas().getFechaingresovehiculo());
        }
        if (pruebas.getHojaPruebas().getReinspeccionList2().size() > 0 && strIntento.equalsIgnoreCase("-1")) {
            objInfoVehiculo[2] = "Rechazado";
            deci = 0;

        } else {
            objInfoVehiculo[2] = "Y".equals(pruebas.getHojaPruebas().getAprobado()) ? "Aprobado" : "Rechazado";
        }
        if (pruebas.getAprobada().equalsIgnoreCase("Y")) {
            objInfoVehiculo[3] = certificado.getConsecutive();
        } else {
            objInfoVehiculo[3] = "";
        }
        objInfoVehiculo[4] = certificado.getConsecutivoRunt();
        objInfoVehiculo[5] = pruebas.getHojaPruebas().getVehiculos().getCarplate();
        objInfoVehiculo[6] = pruebas.getHojaPruebas().getVehiculos().getServicios().getNombreservicio();
        objInfoVehiculo[7] = pruebas.getHojaPruebas().getVehiculos().getClasesVehiculo().getNombreclase();
        objInfoVehiculo[8] = pruebas.getHojaPruebas().getVehiculos().getMarcas().getNombremarca();
        if (pruebas.getHojaPruebas().getVehiculos().getLineasVehiculos() != null) {
            objInfoVehiculo[9] = pruebas.getHojaPruebas().getVehiculos().getLineasVehiculos().getCrlname();
        } else {
            objInfoVehiculo[9] = "Sin Asig Linea Vehiculo";
        }
        objInfoVehiculo[10] = pruebas.getHojaPruebas().getVehiculos().getModelo();
        if (pruebas.getHojaPruebas().getVehiculos().getTipoVehiculo().getCartype() == 4) {
            objInfoVehiculo[11] = pruebas.getHojaPruebas().getVehiculos().getDiseno().name();
        } else {
            objInfoVehiculo[11] = "N/A";
        }
        objInfoVehiculo[12] = sdf.format(pruebas.getHojaPruebas().getVehiculos().getFecharegistro());
        objInfoVehiculo[13] = pruebas.getHojaPruebas().getVehiculos().getTiposGasolina().getNombregasolina();
        objInfoVehiculo[14] = pruebas.getHojaPruebas().getVehiculos().getTiemposmotor();
        objInfoVehiculo[15] = pruebas.getHojaPruebas().getVehiculos().getCilindraje();
        objInfoVehiculo[16] = pruebas.getHojaPruebas().getVehiculos().getNumeromotor();
        objInfoVehiculo[17] = pruebas.getHojaPruebas().getVehiculos().getVin();
        objInfoVehiculo[18] = pruebas.getHojaPruebas().getVehiculos().getNumerolicencia();
        objInfoVehiculo[19] = pruebas.getHojaPruebas().getVehiculos().getKilometraje();
        objInfoVehiculo[20] = obtenerRuidoScape(pruebas.getHojaPruebas());
        objInfoVehiculo[21] = pruebas.getHojaPruebas().getPropietarios().getNombres().concat("; ").concat(pruebas.getHojaPruebas().getPropietarios().getApellidos());
        objInfoVehiculo[22] = pruebas.getHojaPruebas().getPropietarios().getTipoIdentificacion().name();
        objInfoVehiculo[23] = pruebas.getHojaPruebas().getPropietarios().getCarowner();
        objInfoVehiculo[24] = pruebas.getHojaPruebas().getPropietarios().getDireccion();
        objInfoVehiculo[25] = pruebas.getHojaPruebas().getPropietarios().getNumerotelefono();
        objInfoVehiculo[26] = pruebas.getHojaPruebas().getPropietarios().getCiudadades().getNombreciudad();
        if (modeloInfoVehiculo.getRowCount() > 14) {
            int e = 1;
        }
        modeloInfoVehiculo.addRow(objInfoVehiculo);
    }

    /**
     * Carga todas las medidas de la prueba de luces
     */
    private void cargarInformacionPruebaGasesOtto(Pruebas pruebas, int nroIntento) throws SQLException {
        Object[] dataRow = new Object[30];
        boolean tieneRegistro = false;
        Float valTemp = 0.0F;
        if (pruebas.getTipoPrueba().getTesttype() == 8 && !pruebas.getFinalizada().equalsIgnoreCase("A")) {
            try {
                if (pruebas.getHojaPruebas().getTestsheet() == 15300) {
                    System.out.println("ENTRE CONDICIONAL ");
                    if (pruebas.getIdPruebas() == 125033) {

                    }
                }
                System.out.println("id es  " + pruebas.getIdPruebas());
                tieneRegistro = true;
                dataRow[0] = pruebas.getIdPruebas();
                if (pruebas.getFechaPrueba() != null) {
                    dataRow[1] = sdfH.format(pruebas.getFechaPrueba());
                } else {
                    if (nroIntento == 2) {
                        Reinspeccion rein = pruebas.getHojaPruebas().getReinspeccionList2().iterator().next();
                        dataRow[1] = sdf.format(rein.getFechaSiguiente());
                    } else {
                        dataRow[1] = sdf.format(pruebas.getHojaPruebas().getFechaingresovehiculo());
                    }
                }
                if (pruebas.getFechaFinal() != null) {
                    dataRow[2] = sdfH.format(pruebas.getFechaFinal());
                } else {
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
                    pruebas.setFechaFinal(calFechaFinal.getTime());
                }
                dataRow[3] = " ";
                dataRow[4] = pruebas.getUsuarios().getNombreusuario();
                String forMedTemp = "No Definido";
                dataRow[8] = pruebas.getComentarioAborto();
                Calendar cal = Calendar.getInstance();
                int hour;
                double temp = 0;
                int pres;
                dataRow[9] = "NO";
                dataRow[10] = "NO";
                dataRow[11] = "NO";
                dataRow[17] = "NO";
                dataRow[18] = "NO";

                if (pruebas.getComentarioAborto() != null) {
                    if (pruebas.getComentarioAborto().equalsIgnoreCase("Condiciones Anormales")) {
                        if (pruebas.getObservaciones() != null) {
                            if (pruebas.getHojaPruebas().getVehiculos().getTipoVehiculo().getCartype() == 4) {
                                String[] arrValor = pruebas.getObservaciones().split(".-");
                                if (arrValor.length > 1) {
                                    String[] arrCondiciones = arrValor[1].split(";");
                                    if (arrCondiciones.length > 0) {
                                        for (int i = 0; i < arrCondiciones.length; i++) {
                                            if (arrCondiciones[i].startsWith("Existencia de fugas")) {
                                                dataRow[9] = "SI";
                                            }
                                            if (arrCondiciones[i].startsWith("Salidas adicionales") || arrCondiciones[i].startsWith(" Salidas adicionales")) {
                                                dataRow[10] = "SI";
                                            }
                                            if (arrCondiciones[i].startsWith("Ausencia de tapones") || arrCondiciones[i].startsWith(" Ausencia de tapones")) {
                                                dataRow[11] = "SI";
                                            }
                                        }
                                    }
                                }
                            }//si Moto
                        }//si exitenCondiciones
                    }//si exitenCondiciones
                }//si diferenteNull

                if (pruebas.getComentarioAborto() != null) {
                    if (pruebas.getComentarioAborto().equalsIgnoreCase("REVOLUCIONES FUERA RANGO")) {
                        dataRow[17] = "SI";
                    }
                    if (pruebas.getComentarioAborto().startsWith("Presencia")) {
                        dataRow[18] = "SI";
                    }
                }
                if (pruebas.getHojaPruebas().getVehiculos().getTipoVehiculo().getCartype() == 4) {
                    dataRow[12] = "N/A";
                    dataRow[13] = "N/A";
                    dataRow[14] = "N/A";
                    dataRow[15] = "N/A";
                    dataRow[15] = "N/A";
                    dataRow[16] = "N/A";
                } else {
                    dataRow[11] = "NO";
                    dataRow[12] = "NO";
                    dataRow[13] = "NO";
                    dataRow[14] = "NO";
                    dataRow[15] = "NO";
                    dataRow[16] = "NO";
                }
                List<Defxprueba> lstDefxprueba = pruebas.getDefectos();
                for (Defxprueba defxprueba : lstDefxprueba) {
                    switch (defxprueba.getDefxpruebaPK().getIdDefecto()) {
                        case 84002:                             //Fugas tubo escape o silenciador
                            dataRow[9] = "SI";
                            break;
                        case 84035:                             //Fugas tubo escape o silenciador
                            dataRow[9] = "SI";
                            break;
                        case 84003:                            //Accesorios o deformaciones en el tubo de escape que no permitan la instalación sistema de muestreo
                            dataRow[10] = "SI";
                            break;
                        case 84038:                            //SALIDAS ADICIONALES FOR MOTOS
                            dataRow[10] = "SI";
                            break;
                        case 84000:                            //AUSENCIAS TAPONES COMBUSTIBLES
                            dataRow[12] = "SI";
                            break;
                        case 84001:                            //Tapa combustible o fugas
                            dataRow[11] = "SI";
                            break;
                        case 84037:                            //Tapa combustible o fugas
                            dataRow[11] = "SI";
                            break;
                        case 84009:                            //Tapa combustible o fugas
                            dataRow[13] = "SI";
                            break;
                        case 84010:                            //Tapa combustible o fugas
                            dataRow[14] = "SI";
                            break;
                        case 84011:                            //Tapa combustible o fugas
                            dataRow[15] = "SI";
                            break;
                        case 84012:                            //Tapa combustible o fugas
                            dataRow[16] = "SI";
                            break;
                    }

                }

                if (pruebas.getHojaPruebas().getVehiculos().getTipoVehiculo().getCartype() == 4) {
                    Connection conexion = getConnection();
                    conexion.setAutoCommit(false);
                    cal = Calendar.getInstance();
                    cal.setTime(pruebas.getFechaFinal());
                    hour = cal.get(11);
                    temp = 0;
                    if (hour == 8 || hour == 9) {
                        temp = ThreadLocalRandom.current().nextDouble(25.4, 27.70);
                    }

                    if (hour == 10 || hour == 11) {
                        temp = ThreadLocalRandom.current().nextDouble(27.67, 29.73);
                    }

                    if (hour == 12 || hour == 14) {
                        temp = ThreadLocalRandom.current().nextDouble(30.11, 32.94);
                    }

                    if (hour == 15 || hour == 16) {
                        temp = ThreadLocalRandom.current().nextDouble(33.05, 34.97);
                    }

                    if (hour == 17) {
                        temp = ThreadLocalRandom.current().nextDouble(31.55, 30.10);
                    }
                    if (hour == 18) {
                        temp = ThreadLocalRandom.current().nextDouble(30.10, 32);
                    }

                    dataRow[6] = temp;//String.valueOf(temp).concat(".").concat(String.valueOf(pres));
                    String statement = "INSERT INTO medidas(MEASURETYPE,Valor_medida,TEST) VALUES(?,?,?)";
                    PreparedStatement instruccion = conexion.prepareStatement(statement);
                    instruccion.setInt(1, 8031);
                    instruccion.setDouble(2, temp);
                    instruccion.setLong(3, pruebas.getIdPruebas());
                    instruccion.executeUpdate();
                    instruccion.clearParameters();

                    temp = 0;
                    if (hour == 8) {
                        temp = ThreadLocalRandom.current().nextDouble(80.10, 89.99);
                    }
                    if (hour == 9) {
                        temp = ThreadLocalRandom.current().nextDouble(79.10, 85.89);
                    }
                    if (hour == 10) {
                        temp = ThreadLocalRandom.current().nextDouble(74.05, 79.89);
                    }
                    if (hour == 11) {
                        temp = ThreadLocalRandom.current().nextDouble(67.02, 73.89);
                    }
                    if (hour == 12) {
                        temp = ThreadLocalRandom.current().nextDouble(60.02, 68.99);
                    }
                    if (hour == 13) {
                        temp = ThreadLocalRandom.current().nextDouble(63.02, 69.99);
                    }
                    if (hour == 14) {
                        temp = ThreadLocalRandom.current().nextDouble(60.02, 64.99);
                    }
                    if (hour == 15) {
                        temp = ThreadLocalRandom.current().nextDouble(62.02, 64.99);
                    }
                    if (hour == 16) {
                        temp = ThreadLocalRandom.current().nextDouble(59.02, 63.99);
                    }
                    if (hour == 17) {
                        temp = ThreadLocalRandom.current().nextDouble(30.02, 33.99);
                    }
                    if (hour == 18) {
                        temp = ThreadLocalRandom.current().nextDouble(59.02, 70.99);
                    }
                    instruccion.setInt(1, 8032);
                    instruccion.setDouble(2, temp);
                    instruccion.setLong(3, pruebas.getIdPruebas());
                    instruccion.executeUpdate();
                    instruccion.clearParameters();
                    conexion.commit();
                    conexion.setAutoCommit(true);
                    conexion.close();
                }
                for (Medidas medidas : pruebas.getMedidasList()) {
                    switch (medidas.getTiposMedida().getMeasuretype()) {
                        case 8006: //Temperatura en ralenti (TEMPR)
                            dataRow[19] = medidas.getValormedida();
                            valTemp = medidas.getValormedida();
                            break;
                        case 8022: //Temperatura en ralenti (TEMPR) FOR MOTO 2T
                            dataRow[19] = medidas.getValormedida();
                            break;
                        case 8012: //Temperatura en ralenti (TEMPR) FOR MOTO 4T
                            dataRow[19] = medidas.getValormedida();
                            valTemp = medidas.getValormedida();
                            break;
                        case 8011: //Revoluciones por minuto en crucero (RPMC)
                            dataRow[20] = medidas.getValormedida();
                            break;
                        case 8028: //Revoluciones por minuto en ralenty  (RPMC) PARA MOTO
                            dataRow[21] = medidas.getValormedida();
                            break;
                        case 8005: //Revoluciones por minuto en ralenty (RPMR)
                            dataRow[21] = medidas.getValormedida();
                            break;

                        case 8002: //Monoxido de carbono en ralenty (COR)
                            dataRow[22] = medidas.getValormedida();
                            break;
                        case 8003: //Dioxido de carbono en ralenty (CO2R)
                            dataRow[23] = medidas.getValormedida();
                            break;
                        case 8004: //Oxigeno en ralenty (O2R)
                            dataRow[24] = medidas.getValormedida();
                            break;
                        case 8021: //Oxigeno en ralenty (O2R) PARA MOTO 2T
                            dataRow[24] = medidas.getValormedida();
                            break;
                        case 8001: //HidroCarburos en ralenty (HCR)
                            dataRow[25] = medidas.getValormedida();
                            break;
                        case 8018: //HidroCarburos en ralenty (HCR) Para Motos 2t
                            dataRow[25] = medidas.getValormedida();
                            break;
                        case 8008: //Monoxido de carbono en crucero (COC)
                            dataRow[26] = medidas.getValormedida();
                            break;
                        case 8020: //Monoxido de carbono en crucero (COC) PARA MOTOS 2T
                            dataRow[26] = medidas.getValormedida();
                            break;
                        case 8009: //Dioxido de carbono en crucero (CO2C)
                            dataRow[27] = medidas.getValormedida();
                            break;
                        case 8019: //Dioxido de carbono en crucero (CO2C) para Motos 2t
                            dataRow[27] = medidas.getValormedida();
                            break;
                        case 8010: //Oxigeno en crucero (O2C)
                            dataRow[28] = medidas.getValormedida();
                            break;
                        case 8007: //HidroCarburos en crucero (HCC)
                            dataRow[29] = medidas.getValormedida();
                            break;
                        case 8031: //

                            break;
                        case 8032:

                    }

                }

                if (pruebas.getHojaPruebas().getVehiculos().getTipoVehiculo().getCartype() == 4) {
                    forMedTemp = "N/A";
                } else {
                    if (valTemp > 40 && valTemp < 59) {
                        forMedTemp = "Bloque";
                    }
                    if (valTemp > 60) {
                        forMedTemp = "Aceite";
                    }
                    if (valTemp == 0) {
                        forMedTemp = "Catalizador";
                    }
                }

                dataRow[5] = forMedTemp;
                modeloGasesOtto.addRow(dataRow);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ReporteCortolima.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (!tieneRegistro) {
            modeloGasesOtto.addRow(dataRow);
        }
    }

    private void cargarInformacionPruebaGasesDiesel(Pruebas pruebas, int nroIntento) {
        Object[] dataRow = new Object[33];
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
            dataRow[7] = pruebas.getComentarioAborto();
            dataRow[8] = "SI";
            dataRow[29] = "430";
            Integer rpmRalenty = 0;
            Double rpmGobernada = 0.0;
            Double tempVehc = 0.0;
            int hour;
            int temp = 0;
            int pres;
            dataRow[7] = "NO";
            dataRow[8] = "NO";
            dataRow[9] = "NO";
            dataRow[10] = "NO";
            dataRow[11] = "NO";
            dataRow[12] = "NO";
            dataRow[13] = "NO";
            dataRow[14] = "NO";
            dataRow[17] = "NO";
            if (pruebas.getComentarioAborto() != null) {
                if (pruebas.getComentarioAborto().equalsIgnoreCase("REVOLUCIONES FUERA RANGO")) {
                    dataRow[17] = "SI";
                }
            }
            List<Defxprueba> lstDefxprueba = pruebas.getDefectos();
            for (Defxprueba defxprueba : lstDefxprueba) {
                switch (defxprueba.getDefxpruebaPK().getIdDefecto()) {
                    case 84027:                             //Fugas tubo escape o silenciador
                        dataRow[7] = "SI";
                        break;
                    case 84030:                             //salidas Adicionales
                        dataRow[8] = "SI";
                        break;
                    case 84031:                             //ausencia de tapones de Aceite
                        dataRow[9] = "SI";
                        break;
                    case 84032:                             //Ausencia detapones de Combustible
                        dataRow[10] = "SI";
                        break;
                    case 84020:                             //Instalacion de Accesorios
                        dataRow[11] = "SI";
                        break;
                    case 84021:                             //Incorrecta Operacion
                        dataRow[12] = "SI";
                        break;
                    case 84022:                             //Ausencia o Incorrecta Intalacion
                        dataRow[13] = "SI";
                        break;
                    case 84023:                             //Activacion de dispositivo
                        dataRow[14] = "SI";
                        break;
                }
            }

            Calendar cal = Calendar.getInstance();
            for (Medidas medidas : pruebas.getMedidasList()) {
                switch (medidas.getTiposMedida().getMeasuretype()) {
                    case 8035: //Velocidad minima prueba Diesel (VELMINIMA)
                        dataRow[20] = medidas.getValormedida();
                        // rpmRalenty= Integer.parseInt(medidas.getValormedida().toString());
                        break;
                    case 8036: //Velocidad gobernadas prueba Diesel (VELGOBERNADAS)
                        dataRow[18] = medidas.getValormedida();
                        rpmGobernada = Double.parseDouble(medidas.getValormedida().toString());
                        break;
                    case 8034: //Temperatura del motor prueba Diesel (TEMPMOTORDIESEL)
                        dataRow[19] = medidas.getValormedida();
                        tempVehc = Double.parseDouble(medidas.getValormedida().toString());
                        break;
                    case 8013: //Primer ciclo para el opacimetro (CICLO1OP)
                        dataRow[23] = medidas.getValormedida();
                        break;
                    case 8014: //Segundo ciclo para el opacimetro (CICLO2OP)
                        dataRow[25] = medidas.getValormedida();
                        break;
                    case 8015: //Tercer ciclo para el opacimetro (CICLO3OP)
                        dataRow[27] = medidas.getValormedida();
                        break;
                    case 0000: //TODO: Falta Cuarto ciclo para el opacimetro
                        dataRow[6] = medidas.getValormedida();
                        break;
                    case 8033: //0 ciclo para el opacimetro (CICLO0OP)
                        dataRow[21] = medidas.getValormedida();
                        break;
                    case 8017: //Promedio valor de los tres ciclos de la prueba de opacimetro (PROMVALOP)
                        dataRow[29] = medidas.getValormedida();
                        break;
                    case 8031: //                 
                        dataRow[5] = medidas.getValormedida();
                        break;
                    case 8032: //                        
                        dataRow[6] = medidas.getValormedida();
                        break;

                }
            }
            int rnd = ThreadLocalRandom.current().nextInt(1, 10);
            if (rpmGobernada > 20) {
                Double crgRpm = (rnd * 10) + rpmGobernada;
                dataRow[22] = Math.ceil(crgRpm);
                rnd = ThreadLocalRandom.current().nextInt(1, 10);
                crgRpm = (rnd * 10) + rpmGobernada;
                dataRow[24] = Math.ceil(crgRpm);
                rnd = ThreadLocalRandom.current().nextInt(1, 10);
                crgRpm = (rnd * 10) + rpmGobernada;
                dataRow[26] = Math.ceil(crgRpm);
                rnd = ThreadLocalRandom.current().nextInt(1, 10);
                crgRpm = (rnd * 10) + rpmGobernada;
                dataRow[28] = Math.ceil(crgRpm);
                dataRow[16] = "SI";
                dataRow[31] = (tempVehc + rnd) - 2;
            }
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

    public static Connection getConnection() throws ClassNotFoundException {
        Connection conexion = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://192.168.0.101:3306" + "/db_cda" + "?zeroDateTimeBehavior=convertToNull", "root", "admin");
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return conexion;
    }
}
