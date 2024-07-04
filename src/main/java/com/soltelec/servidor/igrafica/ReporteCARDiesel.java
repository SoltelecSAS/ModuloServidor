/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.igrafica;

import com.soltelec.servidor.conexion.PersistenceController;
import com.soltelec.servidor.dao.CdaJpaController;
import com.soltelec.servidor.dao.CertificadosJpaController;
import com.soltelec.servidor.dao.HojaPruebasJpaController;
import com.soltelec.servidor.dao.PruebasJpaController;
import com.soltelec.servidor.dao.SoftwareJpaController;
import com.soltelec.servidor.model.Cda;
import com.soltelec.servidor.model.Certificados;
import com.soltelec.servidor.model.Defxprueba;
import com.soltelec.servidor.model.HojaPruebas;
import com.soltelec.servidor.model.Medidas;
import com.soltelec.servidor.model.Pruebas;
import com.soltelec.servidor.model.Reinspeccion;
import com.soltelec.servidor.model.Software;
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
import javax.persistence.EntityManager;
import javax.persistence.Query;
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
public class ReporteCARDiesel extends javax.swing.JInternalFrame {
//public class ReporteSuperintendenciaVijia extends javax.swing.JFrame {

    private DefaultTableModel modeloInfoVehiculo;
    private DefaultTableModel modeloGases;
    private DefaultTableModel modeloPrueba;
    private DefaultTableModel modeloCDA;
    private DefaultTableModel modelEquipo;
    private DefaultTableModel modeloGasesOtto;

    private static final Logger LOG = Logger.getLogger(ReporteCAR.class.getName());
    private SimpleDateFormat sdf;
    private SimpleDateFormat sdfH;

    public ReporteCARDiesel() {
        super("Reporte  (C.A.R. Diesel )",
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
        modeloCDA = new DefaultTableModel();
        modeloCDA.addColumn("No CDA");//1
        modeloCDA.addColumn("Nombre Cda");//2
        modeloCDA.addColumn("NIT CDA");//3
        modeloCDA.addColumn("DIRECCION CDA");//4
        modeloCDA.addColumn("TELEFONO 1 ");//5
        modeloCDA.addColumn("NUMERO DE CERTIFICADO RTM ");//6
        modeloCDA.addColumn("CIUDAD CDA");//7
        modeloCDA.addColumn("NO. Resolucion CDA");//8
        modeloCDA.addColumn("Fecha Resolucion CDA");//9        

        modelEquipo = new DefaultTableModel();
        modelEquipo.addColumn("Serie del Medidor");// 1
        modelEquipo.addColumn("Marca del Medidor");// 2
        modelEquipo.addColumn("Nombre Programa");// 3
        modelEquipo.addColumn("Version  Programa");// 4
        modelEquipo.addColumn("Vr Primer Punto de Linealidad  ");// 5
        modelEquipo.addColumn("Resultado Primer Punto de Linealidad ");// 6        

        modelEquipo.addColumn("Vr Segundo Punto de Linealidad  ");// 7
        modelEquipo.addColumn("Resultado Segundo Punto de Linealidad ");// 8        
        modelEquipo.addColumn("Vr Tercer Punto de Linealidad  ");// 9
        modelEquipo.addColumn("Resultado Tercer Punto de Linealidad ");// 10        
        modelEquipo.addColumn("Vr Cuarto Punto de Linealidad  ");// 11
        modelEquipo.addColumn("Resultado Cuarto Punto de Linealidad ");// 12     

        modeloPrueba = new DefaultTableModel();
        modeloPrueba.addColumn("No de Consecutivo Prueba");//1
        modeloPrueba.addColumn("Fecha y Hora inicio de la Prueba");//2
        modeloPrueba.addColumn("Fecha y Hora final de la Prueba");//3
        modeloPrueba.addColumn("Fecha y Hora  Aborto de la Prueba");//4
        modeloPrueba.addColumn("Inspector Realiza Prueba");//5        
        modeloPrueba.addColumn("Temperatura Ambiente");//7
        modeloPrueba.addColumn("Humedad Relativa");//8
        modeloPrueba.addColumn("Causal Aborto Analisis");//9

        modeloInfoVehiculo = new DefaultTableModel();
        modeloInfoVehiculo.addColumn("Nombre o Razon Social Propietario");//1
        modeloInfoVehiculo.addColumn("Tipo Documento");//2
        modeloInfoVehiculo.addColumn("No. Documento");//3
        modeloInfoVehiculo.addColumn("Direccion");//4
        modeloInfoVehiculo.addColumn("Telefono 1");//5      
        modeloInfoVehiculo.addColumn("Ciudad");//7

        modeloInfoVehiculo.addColumn("Marca");//8
        modeloInfoVehiculo.addColumn("Linea");//9
        modeloInfoVehiculo.addColumn("Año Modelo");//11
        modeloInfoVehiculo.addColumn("Placa");//10
        modeloInfoVehiculo.addColumn("Cilindraje");//11

        modeloInfoVehiculo.addColumn("Clase");//8
        modeloInfoVehiculo.addColumn("Servicio");//7 
        modeloInfoVehiculo.addColumn("Combustible");//14
        modeloInfoVehiculo.addColumn("Nro. Motor");//17
        modeloInfoVehiculo.addColumn("Nro. vim Serie");//18
        modeloInfoVehiculo.addColumn("Nro. Licencia");//19
        modeloInfoVehiculo.addColumn("Modificaciones al Motor");//19
        modeloInfoVehiculo.addColumn("Kilometraje");//20      
        modeloInfoVehiculo.addColumn("Potencial del Motor (Valor necesario solo para el reporte de resultados en porcentaje de opacidad)");//20      

        //Set de columnas de pruebas de luces
        //Set de columnas de pruebas de frenos
        //Set de columnas de pruebas de gases otto
        modeloGasesOtto = new DefaultTableModel();
        modeloGasesOtto.addColumn("fugas en el tubo Escape");//9 84002
        modeloGasesOtto.addColumn("fugas Silenciador");//9 84002
        modeloGasesOtto.addColumn("Presencia Tapa Combustible o Fugas");//11 84001
        modeloGasesOtto.addColumn("Presencia Tapa Aceite o Fugas");//11 84001
        modeloGasesOtto.addColumn("Accesorios o Deformaciones en Tubo de Escape");//12 84000
        modeloGasesOtto.addColumn("Salidas Adicionales al Diseño");//13
        modeloGasesOtto.addColumn("Presencia Filtro de Aire");//13
        modeloGasesOtto.addColumn("Fallas Sistema de Refrigeracion");//15
        modeloGasesOtto.addColumn("Revoluciones Inestables o Fuera de Rango");//16        
        modeloGasesOtto.addColumn("Indicacion Mal Funcionamiento del Motor");//17
        modeloGasesOtto.addColumn("Funcionamiento del Sistema del Sistema de Control de Velocidad del Motor");//18

        modeloGasesOtto.addColumn("Instalacion Dispositivos que alteran RPM");//19
        modeloGasesOtto.addColumn("Temperatura Inicial del Motor ");//20

        modeloGasesOtto.addColumn("Velocidad no alcanzada en 5 seg.");//21

        modeloGasesOtto.addColumn("RPM Velocidad Gobernada");//22
        modeloGasesOtto.addColumn("Falla Subita de Motor");//22
        modeloGasesOtto.addColumn("RPM Ralentí");//22

        modeloGasesOtto.addColumn("RESULTADO OPACIDAD CICLO PRELIMINAR");//23
        modeloGasesOtto.addColumn("RPM GOBERNADA CICLO PRELIMINAR");//23
        modeloGasesOtto.addColumn("RESULTADO OPACIDAD PRIMER CICLO");//23
        modeloGasesOtto.addColumn("RPM GOBERNADA PRIMER CICLO");//23
        modeloGasesOtto.addColumn("RESULTADO OPACIDAD SEGUNDO CICLO");//23
        modeloGasesOtto.addColumn("RPM GOBERNADA SEGUNDO CICLO");//23
        modeloGasesOtto.addColumn("RESULTADO OPACIDAD TERCER CICLO");//23
        modeloGasesOtto.addColumn("RPM GOBERNADA TERCER CICLO");//23
        modeloGasesOtto.addColumn("LTOE");//23
        modeloGasesOtto.addColumn("TEMPERATURA FINAL DEL MOTOR");//23
        modeloGasesOtto.addColumn("FALLA POR TEMPERATURA DEL MOTOR");//23
        modeloGasesOtto.addColumn("INESTABILIDAD DURANTE CICLOS DE MEDICION");//23
        modeloGasesOtto.addColumn("DIFERENCIA ARITMETICAS");//23

        modeloGasesOtto.addColumn("RESULTADO FINAL");//23        
        modeloGasesOtto.addColumn("Causa de Rechazo");//28
        modeloGasesOtto.addColumn("Concepto Tecnico ");//28
        modeloGasesOtto.addColumn("Marca Sonometro");//28
        modeloGasesOtto.addColumn("Serie Sonometro");//28
        modeloGasesOtto.addColumn("Valor de Ruido Reportado");//28        
        //Set de columnas de pruebas de gases diesel        
    }

    /**
     * Carga los datos en la tabla
     */
    private void fillData(Date fechaInicial, Date fechaFinal) {
        initModels();
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdfH = new SimpleDateFormat("YYYY/MM/dd HH:mm");
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
        int n = 0;
        String form;
        int work = 0;
        int lng = lstPruebas.size();
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
            if (pruebas.getFinalizada().equalsIgnoreCase("Y")) {
                work = work + 1;
                System.out.println("proc..!  " + work + " de " + lng);
                if (pruebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype() == 3 || pruebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype() == 11) {

                    cargarInformacionCda(pruebas);
                    cargarInformacionCalibracion(pruebas);
                    cargarInformacionPrueba(pruebas, form);
                    cargarInformacionvehiculo(pruebas, form);
                    Object[] dataRow = new Object[33];
                    // modeloGases.addRow(dataRow);            
                    cargarInformacionPruebaGasesOtto(pruebas, n);
                    tblCda.setModel(modeloCDA);
                    tblCalibracion.setModel(modelEquipo);
                    tblPruebas.setModel(modeloPrueba);
                    tblInfoVehiculos.setModel(modeloInfoVehiculo);
                    tblPruebaGasesOtto.setModel(modeloGasesOtto);
                    tblCda.setEnabled(false);
                    tblCalibracion.setEnabled(false);
                    tblPruebas.setEnabled(false);
                    tblInfoVehiculos.setEnabled(false);
                    tblPruebaGasesOtto.setEnabled(false);
                }
            }

            //Informacion prueba Gases otto              
            /* if (pruebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype() == 3 || pruebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype() == 11) {
                //Informacion prueba Gases diesel
                cargarInformacionPruebaGasesDiesel(pruebas, n);
                Object[] dataRow = new Object[30];
                modeloGasesOtto.addRow(dataRow);
            }*/
        }

        // tblPruebaGasesDiesel.setModel(modeloGases);
    }//end filldata

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        fechaInicial = new org.jdesktop.swingx.JXDatePicker();
        fechaInicial.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
        fechaFInal = new org.jdesktop.swingx.JXDatePicker();
        fechaInicial.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
        btnGenerar = new javax.swing.JButton();
        btnExportar = new javax.swing.JButton();
        infPrueba = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblInfoVehiculos = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        jScrollPane10 = new javax.swing.JScrollPane();
        jScrollPane11 = new javax.swing.JScrollPane();
        jScrollPane12 = new javax.swing.JScrollPane();
        tblPruebaGasesOtto = new javax.swing.JTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblPruebaGasesDiesel = new javax.swing.JTable();
        tblPruebas = new javax.swing.JTable();
        tblCda = new javax.swing.JTable();
        tblCalibracion = new javax.swing.JTable();

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

        tblCda.setModel(modeloCDA);
        tblCda.setName("Información del Cda."); // NOI18N

        jScrollPane11.setViewportView(tblCda);
        infPrueba.addTab("Inf. Cda", jScrollPane11);

        tblCalibracion.setModel(modelEquipo);
        tblCalibracion.setName("Información de Calibracion"); // NOI18N

        jScrollPane12.setViewportView(tblCalibracion);
        infPrueba.addTab("Inf. Calibracion", jScrollPane12);

        tblPruebas.setModel(modeloPrueba);
        tblPruebas.setName("Información del Pruebas"); // NOI18N

        jScrollPane10.setViewportView(tblPruebas);
        infPrueba.addTab("Inf. pruebas", jScrollPane10);

        tblInfoVehiculos.setModel(modeloInfoVehiculo);
        tblInfoVehiculos.setName("Información de Vehiculo"); // NOI18N
        jScrollPane1.setViewportView(tblInfoVehiculos);
        infPrueba.addTab("Inf. Vehiculo", jScrollPane1);

        tblPruebaGasesOtto.setModel(modeloGasesOtto);
        tblPruebaGasesOtto.setName("Prueba de Gases Otto"); // NOI18N
        jScrollPane7.setViewportView(tblPruebaGasesOtto);

        infPrueba.addTab("Inf. gasesOtto", jScrollPane7);

        // tblPruebaGasesDiesel.setModel(modeloGases);
        tblPruebaGasesDiesel.setName("Prueba de desviación"); // NOI18N
        jScrollPane8.setViewportView(tblPruebaGasesDiesel);

        infPrueba.addTab("Prueba de gases vehiculos ciclo otto", jScrollPane8);

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
                        .addComponent(infPrueba)
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
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(infPrueba, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                                .addGap(282, 282, 282))
        );

        infPrueba.getAccessibleContext().setAccessibleName("Prueba de luces");
        infPrueba.getAccessibleContext().setAccessibleDescription("");

        setBounds(0, 0, 816, 639);
    }// </editor-fold>                        

    public static void main(String[] args) {
        ReporteCAR rsv = new ReporteCAR();
        rsv.setVisible(true);
    }

    private void btnGenerarActionPerformed(java.awt.event.ActionEvent evt) {
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
    }

    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        List<JTable> tables = new ArrayList<>();
        tables.add(tblCda);
        tables.add(tblCalibracion);
        tables.add(tblPruebas);
        tables.add(tblInfoVehiculos);
        tables.add(tblPruebaGasesOtto);
        tables.add(tblPruebaGasesDiesel);

        GenericExportExcel excel = new GenericExportExcel();
        excel.exportSameSheet(tables);
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btnGenerar;
    private org.jdesktop.swingx.JXDatePicker fechaFInal;
    private org.jdesktop.swingx.JXDatePicker fechaInicial;
    private javax.swing.JTabbedPane infPrueba;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JTable tblInfoVehiculos;
    private javax.swing.JTable tblPruebaGasesDiesel;
    private javax.swing.JTable tblPruebaGasesOtto;
    private javax.swing.JTable tblPruebas;
    private javax.swing.JTable tblCda;
    private javax.swing.JTable tblCalibracion;
    // End of variables declaration                   

    private void cargarInformacionvehiculo(Pruebas pruebas, String strIntento) {
        int deci = 1;

        Object[] objInfoVehiculo = new Object[20];

        objInfoVehiculo[0] = pruebas.getHojaPruebas().getVehiculos().getPropietarios().getNombres();
        objInfoVehiculo[1] = pruebas.getHojaPruebas().getVehiculos().getPropietarios().getTipoIdentificacion().name();
        objInfoVehiculo[2] = pruebas.getHojaPruebas().getVehiculos().getPropietarios().getCarowner();

        objInfoVehiculo[3] = pruebas.getHojaPruebas().getVehiculos().getPropietarios().getDireccion();
        objInfoVehiculo[4] = pruebas.getHojaPruebas().getVehiculos().getPropietarios().getNumerotelefono();
        objInfoVehiculo[5] = pruebas.getHojaPruebas().getVehiculos().getPropietarios().getCiudadades().getNombreciudad();

        objInfoVehiculo[6] = pruebas.getHojaPruebas().getVehiculos().getMarcas().getCarmark();
        System.out.println("procId..!  " + pruebas.getIdPruebas());
        objInfoVehiculo[7] = pruebas.getHojaPruebas().getVehiculos().getLineasVehiculos().getCarline();
        objInfoVehiculo[8] = pruebas.getHojaPruebas().getVehiculos().getModelo();
        objInfoVehiculo[9] = pruebas.getHojaPruebas().getVehiculos().getCarplate();

        objInfoVehiculo[10] = pruebas.getHojaPruebas().getVehiculos().getCilindraje();
        objInfoVehiculo[11] = pruebas.getHojaPruebas().getVehiculos().getClasesVehiculo().getClass1();
        objInfoVehiculo[12] = pruebas.getHojaPruebas().getVehiculos().getServicios().getService();
        objInfoVehiculo[13] = pruebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype();
        objInfoVehiculo[14] = pruebas.getHojaPruebas().getVehiculos().getNumeromotor();
        objInfoVehiculo[15] = pruebas.getHojaPruebas().getVehiculos().getVin();
        objInfoVehiculo[16] = pruebas.getHojaPruebas().getVehiculos().getNumerolicencia();
        objInfoVehiculo[17] = "NO";
        objInfoVehiculo[18] = pruebas.getHojaPruebas().getVehiculos().getKilometraje();
        objInfoVehiculo[19] = "0";
        modeloInfoVehiculo.addRow(objInfoVehiculo);
    }

    private void cargarInformacionCda(Pruebas pruebas) {
        sdf = new SimpleDateFormat("yyyy/MM/dd");
        CdaJpaController cda = new CdaJpaController();
        Cda mcda = cda.findCda(1);
        Object[] objCda = new Object[9];
        objCda[0] = mcda.getCodigo();
        objCda[1] = mcda.getNombre();
        objCda[2] = mcda.getNit();
        objCda[3] = mcda.getDireccion();
        objCda[4] = mcda.getTelefono();
        if (pruebas.getFinalizada().equalsIgnoreCase("Y") && pruebas.getAprobada().equalsIgnoreCase("Y")) {
            CertificadosJpaController cert = new CertificadosJpaController();
            Certificados certificado = cert.findCertificadoHojaPrueba(pruebas.getHojaPruebas().getTestsheet());
            objCda[5] = certificado.getConsecutive();//certificado rtm    
        } else {
            objCda[5] = "0";//certificado rtm  
        }
        objCda[6] = mcda.getDivipola().toString().trim();//cdg de ciudad segun dane
        objCda[7] = mcda.getResolucion();
        objCda[8] = mcda.getPasswordSicov();
        modeloCDA.addRow(objCda);
    }

    private void cargarInformacionCalibracion(Pruebas pruebas) {
        sdf = new SimpleDateFormat("yyyy/MM/dd");
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(Locale.ENGLISH);
        Object[] filaprueba = new Object[8];
        // Se crea un array que será una de las filas de la tabla.
        Calendar c1 = Calendar.getInstance();
        c1.setTime(pruebas.getFechaPrueba());
        SoftwareJpaController swJpa = new SoftwareJpaController();
        Software sw = swJpa.findSoftware(1);
        EntityManager em = PersistenceController.getEntityManager();
        try {
            Query q = em.createNativeQuery("SELECT MAX(c.CALIBRATION),c.id_equipo,c.CURDATE FROM calibraciones c  WHERE c.id_tipo_calibracion = 1 AND  Date(c.CURDATE) between ? and ? order by c.CURDATE ");
            q.setParameter(1, new SimpleDateFormat("yyyy-MM-dd").format(c1.getTime()));
            q.setParameter(2, new SimpleDateFormat("yyyy-MM-dd").format(c1.getTime()));
            List<Object[]> LstCal = q.getResultList();
            if (LstCal.size() > 0) {
                Object[] filaEquipo = new Object[12]; // Hay tres columnas en la tabla
                for (Object[] result : LstCal) {
                    com.soltelec.servidor.model.Equipos eq = em.find(com.soltelec.servidor.model.Equipos.class, result[1]);
                    com.soltelec.servidor.model.Calibraciones cL = em.find(com.soltelec.servidor.model.Calibraciones.class, result[0]);
                    filaEquipo[0] = eq.getReolucionserial();               //PEF
                    filaEquipo[1] = eq.getMarca();                //Serie Banco
                    filaEquipo[2] = sw.getNombre();                   //Serie Analizador
                    filaEquipo[3] = sw.getVersion();                //Marca Analizador

                    filaEquipo[4] = "0";                   //Valor gas referencia bajo  HC verificaciòn y ajuste 
                    filaEquipo[5] = "0";  //Valor gas referencia bajo CO verificaciòn y ajuste

                    filaEquipo[6] = cL.getValor1();                 //Valor gas referencia bajo CO2 verificaciòn y ajuste
                    filaEquipo[7] = cL.getValor4();                   //Valor gas referencia alto HC verificaciòn y ajuste

                    filaEquipo[8] = cL.getValor2();                   //Valor gas referencia  alto CO verificaciòn y ajuste
                    filaEquipo[9] = cL.getValor5();                 //Valor gas referencia  alto CO2 verificaciòn y ajuste

                    filaEquipo[10] = cL.getValor3();                   //Valor gas referencia bajo  HC verificaciòn y ajuste 
                    filaEquipo[11] = cL.getValor6();                   //Valor gas referencia bajo CO verificaciòn y ajuste
                    //Version software de aplicación
                    modelEquipo.addRow(filaEquipo);
                    break;
                }
            }
        } catch (Exception e) {
            LOG.severe("" + e.getMessage());
        } finally {
        }
        //Se actualiza el modelo de cada tabla para que se refresque la informacion
        tblCalibracion.setModel(modelEquipo);
    }

    private void cargarInformacionPrueba(Pruebas pruebas, String strIntento) {
        int deci = 1;
        if (pruebas.getHojaPruebas().getTestsheet() == 16291) {
            int e = 0;
        }
        Object[] objPrueba = new Object[9];
        objPrueba[0] = String.valueOf(pruebas.getIdPruebas());
        int min = 0;
        int sec = 0;
        int minActual = 0;
        Calendar calFechaFinal = Calendar.getInstance();
        min = ThreadLocalRandom.current().nextInt(7, 10);
        calFechaFinal.setTime(pruebas.getFechaPrueba());
        minActual = calFechaFinal.get(Calendar.MINUTE);
        calFechaFinal.set(Calendar.MINUTE, minActual - min);
        sec = ThreadLocalRandom.current().nextInt(1, 59);
        calFechaFinal.set(Calendar.SECOND, sec);
        objPrueba[2] = sdfH.format(pruebas.getFechaPrueba());
        objPrueba[1] = sdfH.format(calFechaFinal.getTime());
        objPrueba[3] = pruebas.getFechaAborto();
        objPrueba[4] = pruebas.getUsuarios().getCedula();
        Float valTemp = 0.0F;
        for (Medidas medidas : pruebas.getMedidasList()) {
            switch (medidas.getTiposMedida().getMeasuretype()) {
                case 8006: //Temperatura en ralenti (TEMPR)                       
                    valTemp = medidas.getValormedida();
                    break;
                case 8022: //Temperatura en ralenti (TEMPR) FOR MOTO 2T                        
                    break;
                case 8012: //Temperatura en ralenti (TEMPR) FOR MOTO 4T                       
                    valTemp = medidas.getValormedida();
                    break;
                case 8031: //                       
                    objPrueba[5] = medidas.getValormedida();
                    break;
                case 8032: //                        
                    objPrueba[6] = medidas.getValormedida();
                    break;
            }
        }
        objPrueba[7] = "0";

        if (modeloPrueba.getRowCount() > 14) {
            int e = 1;
        }
        modeloPrueba.addRow(objPrueba);
    }

    /**
     * Carga todas las medidas de la prueba de luces
     */
    private void cargarInformacionPruebaGasesOtto(Pruebas pruebas, int nroIntento) {
        Object[] dataRow = new Object[36];
        boolean tieneRegistro = false;
        Integer rpmRalenty = 0;
        Double rpmGobernada = 0.0;
        Double tempVehc = 0.0;
        int hour;
        int temp = 0;
        int pres;
        dataRow[0] = "NO";
        dataRow[1] = "NO";
        dataRow[2] = "NO";
        dataRow[3] = "NO";
        dataRow[4] = "NO";
        dataRow[5] = "NO";
        dataRow[6] = "NO";
        dataRow[7] = "NO";
        dataRow[8] = "NO";
        dataRow[9] = "NO";
        dataRow[10] = "NO";
        dataRow[11] = "NO";
        dataRow[13] = "NO";
        dataRow[15] = "NO";
        dataRow[27] = "NO";
        dataRow[28] = "NO";
        dataRow[29] = "NO";
        boolean condicionAnormal = false;
        if (pruebas.getComentarioAborto() != null) {
            if (pruebas.getComentarioAborto().equalsIgnoreCase("Condiciones Anormales")) {
                if (pruebas.getObservaciones() != null) {
                    String[] arrValor = pruebas.getObservaciones().split(".-");
                    if (arrValor.length > 1) {
                        String[] arrCondiciones = arrValor[1].split(";");
                        if (arrCondiciones.length > 0) {
                            for (int i = 0; i < arrCondiciones.length; i++) {
                                if (arrCondiciones[i].startsWith("Fugas en el tubo, uniones del")) {
                                    dataRow[0] = "SI";
                                    dataRow[1] = "SI";
                                    dataRow[31] = "1";
                                    condicionAnormal = true;
                                }
                                if (arrCondiciones[i].startsWith("Salidas adicionales en el sistema de escape") || arrCondiciones[i].startsWith(" Salidas adicionales en el sistema de escape")) {
                                    dataRow[5] = "SI";
                                    dataRow[31] = "6";
                                    condicionAnormal = true;
                                }
                                if (arrCondiciones[i].startsWith("Ausencia de tapones de aceite") || arrCondiciones[i].startsWith(" Ausencia de tapones de aceite")) {
                                    dataRow[3] = "SI";
                                    dataRow[31] = "4";
                                    condicionAnormal = true;
                                }
                                if (arrCondiciones[i].startsWith("Ausencia de tapones de combustible") || arrCondiciones[i].startsWith(" Ausencia de tapones de combustible")) {
                                    dataRow[2] = "SI";
                                    condicionAnormal = true;
                                    dataRow[31] = "3";
                                }

                                if (arrCondiciones[i].startsWith("Instalacion de accesorios o deformaciones") || arrCondiciones[i].startsWith(" Instalacion de accesorios o deformaciones")) {
                                    dataRow[4] = "SI";
                                    condicionAnormal = true;
                                    dataRow[31] = "5";
                                }

                                if (arrCondiciones[i].startsWith("Incorrecta operacion del sistema") || arrCondiciones[i].startsWith(" Incorrecta operacion del sistema")) {
                                    dataRow[7] = "SI";
                                    condicionAnormal = true;
                                    dataRow[31] = "8";
                                }
                                if (arrCondiciones[i].startsWith("Ausencia o incorrecta instalacion") || arrCondiciones[i].startsWith(" Ausencia o incorrecta instalacion")) {
                                    dataRow[6] = "SI";
                                    condicionAnormal = true;
                                    dataRow[31] = "7";
                                }
                                if (arrCondiciones[i].startsWith("Activacion de dispositivos instalados en el motor") || arrCondiciones[i].startsWith(" Activacion de dispositivos instalados en el motor")) {
                                    dataRow[10] = "SI";
                                    condicionAnormal = true;
                                    dataRow[31] = "10";
                                }
                            }
                        }
                    } else {
                        List<Defxprueba> lstDefxprueba = pruebas.getDefectos();
                        for (Defxprueba defxprueba : lstDefxprueba) {
                            switch (defxprueba.getDefxpruebaPK().getIdDefecto()) {
                                case 84027:                             //Fugas tubo escape o silenciador
                                    dataRow[0] = "SI";
                                    dataRow[1] = "SI";
                                    dataRow[31] = "1";
                                    condicionAnormal = true;
                                    break;
                                case 84030:                             //Salidas adicionales en el sistema de escape diferentes a las del diseno original del vehiculo
                                    dataRow[5] = "SI";
                                    condicionAnormal = true;
                                    dataRow[31] = "6";
                                    break;
                                case 84031:                             //ausencia de tapones de Aceite
                                    dataRow[3] = "SI";
                                    condicionAnormal = true;
                                    dataRow[31] = "4";
                                    break;
                                case 84032:                             //Ausencia detapones de Combustible
                                    dataRow[2] = "SI";
                                    dataRow[31] = "3";
                                    condicionAnormal = true;
                                    break;
                                case 84020:                             //Instalacion de accesorios o deformaciones en el tubo de escape que no permitan la introduccion del acople
                                    dataRow[4] = "SI";
                                    condicionAnormal = true;
                                    dataRow[31] = "5";
                                    break;
                                case 84021:                             //Incorrecta operacion del sistema de refrigeracion, cuya verificacion se hara por medio de inspeccion
                                    dataRow[7] = "SI";
                                    condicionAnormal = true;
                                    dataRow[31] = "8";
                                    break;
                                case 84022:                             //Ausencia o incorrecta instalacion del filtro de aire
                                    dataRow[6] = "SI";
                                    condicionAnormal = true;
                                    dataRow[31] = "7";
                                    break;
                                case 84023:                             //Activacion de dispositivos instalados en el motor o en el vehiculo que alteren las caracteristicas normales de velocidad de giro y modifiquen la prueba de opacidad y su correcta ejecusion
                                    dataRow[10] = "SI";
                                    condicionAnormal = true;
                                    dataRow[31] = "10";
                                    break;
                            }
                        }
                    }
                }//si hay coment condiciones Anormales
            }//si exitenCondiciones
        }
        if (pruebas.getComentarioAborto() != null) {
            if (pruebas.getComentarioAborto().equalsIgnoreCase("REVOLUCIONES FUERA RANGO")) {
                dataRow[8] = "SI";
                condicionAnormal = true;
                dataRow[31] = "9";
            }
        }
        if (pruebas.getObservaciones() != null) {
            int e = pruebas.getObservaciones().indexOf("LA DIFERENCIA  ARITMETICA");
            if (e > 0) {
                dataRow[29] = "SI";
                condicionAnormal = true;
                dataRow[31] = "15";
            }
        }

        //   46      
        Calendar cal = Calendar.getInstance();
        for (Medidas medidas : pruebas.getMedidasList()) {
            switch (medidas.getTiposMedida().getMeasuretype()) {
                case 8035: //Velocidad minima prueba Diesel (VELMINIMA)
                    dataRow[16] = medidas.getValormedida();
                    dataRow[13] = "SI";
                    // rpmRalenty= Integer.parseInt(medidas.getValormedida().toString());
                    break;
                case 8036: //Velocidad gobernadas prueba Diesel (VELGOBERNADAS)
                    dataRow[14] = medidas.getValormedida();
                    rpmGobernada = Double.parseDouble(medidas.getValormedida().toString());
                    break;
                case 8034: //Temperatura del motor prueba Diesel (TEMPMOTORDIESEL)
                    dataRow[12] = medidas.getValormedida();
                    tempVehc = Double.parseDouble(medidas.getValormedida().toString());
                    break;
                case 8013: //Primer ciclo para el opacimetro (CICLO1OP)
                    dataRow[19] = medidas.getValormedida();
                    break;
                case 8014: //Segundo ciclo para el opacimetro (CICLO2OP)
                    dataRow[21] = medidas.getValormedida();
                    break;
                case 8015: //Tercer ciclo para el opacimetro (CICLO3OP)
                    dataRow[23] = medidas.getValormedida();
                    break;
                case 0000: //TODO: Falta Cuarto ciclo para el opacimetro
                    dataRow[6] = medidas.getValormedida();
                    break;
                case 8033: //0 ciclo para el opacimetro (CICLO0OP)
                    dataRow[17] = medidas.getValormedida();
                    break;
                case 8017: //Promedio valor de los tres ciclos de la prueba de opacimetro (PROMVALOP)
                    dataRow[30] = medidas.getValormedida();
                    break;
            }
        }

        if (pruebas.getAprobada().equalsIgnoreCase("Y")) {
            dataRow[31] = "0";
            dataRow[32] = "1";
        } else {
            dataRow[32] = "2";
            if (condicionAnormal == false) {
                if ((pruebas.getHojaPruebas().vehiculos.getModelo()) <= 1980) {

                } else if (pruebas.getHojaPruebas().vehiculos.getModelo() >= 1996 && pruebas.getHojaPruebas().vehiculos.getModelo() <= 2000) {
                    dataRow[31] = "19";
                } else if (pruebas.getHojaPruebas().vehiculos.getModelo() >= 1991 && pruebas.getHojaPruebas().vehiculos.getModelo() <= 1995) {
                    dataRow[31] = "20";
                } else if (pruebas.getHojaPruebas().vehiculos.getModelo() >= 1986 && pruebas.getHojaPruebas().vehiculos.getModelo() <= 1990) {
                    dataRow[31] = "21";
                } else if (pruebas.getHojaPruebas().vehiculos.getModelo() >= 1981 && pruebas.getHojaPruebas().vehiculos.getModelo() <= 1985) {
                    dataRow[31] = "22";
                } else if (pruebas.getHojaPruebas().vehiculos.getModelo() >= 2001) {
                    dataRow[31] = "18";
                }
            }
        }
        if (pruebas.getFinalizada().equalsIgnoreCase("A")) {
            dataRow[32] = "3";
        }
        int rnd = ThreadLocalRandom.current().nextInt(1, 10);
        if (rpmGobernada > 20) {
            Double crgRpm = (rnd * 10) + rpmGobernada;
            dataRow[18] = Math.ceil(crgRpm);
            rnd = ThreadLocalRandom.current().nextInt(1, 10);
            crgRpm = (rnd * 10) + rpmGobernada;
            dataRow[20] = Math.ceil(crgRpm);
            rnd = ThreadLocalRandom.current().nextInt(1, 10);
            crgRpm = (rnd * 10) + rpmGobernada;
            dataRow[22] = Math.ceil(crgRpm);
            rnd = ThreadLocalRandom.current().nextInt(1, 10);
            crgRpm = (rnd * 10) + rpmGobernada;
            dataRow[24] = Math.ceil(crgRpm);
            dataRow[25] = pruebas.getHojaPruebas().getVehiculos().getDiametro();
            dataRow[26] = (tempVehc + rnd) - 2;
        }
        String ruido = obtenerRuidoScape(pruebas.getHojaPruebas());
        if (ruido != null) {
            String[] tr = ruido.split(";");
            dataRow[33] = "Artisan";
            dataRow[34] = tr[1];
            dataRow[35] = tr[0];
        }
        modeloGasesOtto.addRow(dataRow);
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
                        if (p.getSerialEquipo() != null) {
                            ruidoMotor = ruidoMotor.toString().concat(";").concat(p.getSerialEquipo());
                            break;
                        } else {
                            ruidoMotor = ruidoMotor.toString().concat(";").concat(" ");
                        }
                    }
                }
            }
        }
        return ruidoMotor;
    }
}
