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
import com.soltelec.servidor.model.CalibracionDosPuntos;
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
public class ReporteCAR extends javax.swing.JInternalFrame {
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
    private static CalibracionDosPuntos ctxC2p;
    private static com.soltelec.servidor.model.Equipos ctxEq;
    private static Date ctxFecha;

    public ReporteCAR() {
        super("Reporte  (C.A.R. )",
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
        modelEquipo.addColumn("Vr PEF");// 1
        modelEquipo.addColumn("Número de serie del banco");// 2
        modelEquipo.addColumn("No. serie  analizador");// 3
        modelEquipo.addColumn("Marca analizador");// 4
        modelEquipo.addColumn("Vr bajo Span Bajo HC  ");// 5
        modelEquipo.addColumn("Resultado de Vr Span Bajo HC ");// 6
        modelEquipo.addColumn("Vr bajo Span Bajo CO  ");// 7
        modelEquipo.addColumn("Resultado de Vr Span Bajo CO ");// 8
        modelEquipo.addColumn("Vr bajo Span Bajo CO2  ");// 9
        modelEquipo.addColumn("Resultado de Vr Span Bajo CO2 ");// 10

        modelEquipo.addColumn("Vr bajo Span Alto HC  ");// 11
        modelEquipo.addColumn("Resultado de Vr Span Alto HC ");// 12
        modelEquipo.addColumn("Vr bajo Span Alto CO ");// 13
        modelEquipo.addColumn("Resultado de Vr Span Alto CO ");// 14
        modelEquipo.addColumn("Vr bajo Span Alto CO2 ");// 15
        modelEquipo.addColumn("Resultado de Vr Span Alto CO2 "); // 16
        modelEquipo.addColumn("Fecha  y hora ultima verificación y ajuste");// 17
        modelEquipo.addColumn("Nombre Proveedor");// 18
        modelEquipo.addColumn("Nombre del Programa");// 19
        modelEquipo.addColumn("Version Programa");// 20

        modeloPrueba = new DefaultTableModel();
        modeloPrueba.addColumn("No de Consecutivo Prueba");//1
        modeloPrueba.addColumn("Fecha y Hora inicio de la Prueba");//2
        modeloPrueba.addColumn("Fecha y Hora final de la Prueba");//3
        modeloPrueba.addColumn("Fecha y Hora  Aborto de la Prueba");//4
        modeloPrueba.addColumn("Operador Realiza Prueba");//5

        modeloPrueba.addColumn("Metodo de Medicion de Temperatura Motor");//6
        modeloPrueba.addColumn("Temperatura Ambiente");//7
        modeloPrueba.addColumn("Humedad Relativa");//8
        modeloPrueba.addColumn("Causal Aborto Analisis");//9

        modeloInfoVehiculo = new DefaultTableModel();
        modeloInfoVehiculo.addColumn("Nombre o Razon Social Propietario");//1
        modeloInfoVehiculo.addColumn("Tipo Documento");//2
        modeloInfoVehiculo.addColumn("No. Documento");//3
        modeloInfoVehiculo.addColumn("Direccion");//4
        modeloInfoVehiculo.addColumn("Telefono 1");//5
        modeloInfoVehiculo.addColumn("Telefono 2 (Celular)");//6
        modeloInfoVehiculo.addColumn("Ciudad");//7

        modeloInfoVehiculo.addColumn("Marca");//8
        modeloInfoVehiculo.addColumn("Linea");//9
        modeloInfoVehiculo.addColumn("Placa");//10
        modeloInfoVehiculo.addColumn("Cilindraje");//11
        modeloInfoVehiculo.addColumn("Modelo");//11
        modeloInfoVehiculo.addColumn("Clase");//8
        modeloInfoVehiculo.addColumn("Servicio");//7 
        modeloInfoVehiculo.addColumn("Combustible");//14
        modeloInfoVehiculo.addColumn("Nro. Motor");//17
        modeloInfoVehiculo.addColumn("Nro. vim Serie");//18
        modeloInfoVehiculo.addColumn("Nro. Licencia");//19
        modeloInfoVehiculo.addColumn("Kilometraje");//20      

        //Set de columnas de pruebas de luces
        //Set de columnas de pruebas de frenos
        //Set de columnas de pruebas de gases otto
        modeloGasesOtto = new DefaultTableModel();

        modeloGasesOtto.addColumn("fugas en el tubo Escape");//0
        modeloGasesOtto.addColumn("fugas Silenciador");//1
        modeloGasesOtto.addColumn("Accesorio o deformaciones en el tubo escape que no permitan la instalacion sistema muestreo");//11 84001

        modeloGasesOtto.addColumn("Presencia tapa Combustible o fugas en el mismo");//13
        modeloGasesOtto.addColumn("Presencia Tapa Aceite");//13
        modeloGasesOtto.addColumn("Ausencia o mal estado filtro de aire");//15
        modeloGasesOtto.addColumn("Salidas Adicionales Diseño");//16

        modeloGasesOtto.addColumn("PCV (Sistema recirculacion de gases del Carter)");//17
        modeloGasesOtto.addColumn("Presencia Humo Negro,Azul");//18

        modeloGasesOtto.addColumn("Revoluciones inestables o fuera de Rango");//19
        modeloGasesOtto.addColumn("Fallas sistema de refrigeracion");//20

        modeloGasesOtto.addColumn("Temperatura de Motor");//21
        modeloGasesOtto.addColumn("RPM Ralentí");//22
        modeloGasesOtto.addColumn("HC Ralentí");//22
        modeloGasesOtto.addColumn("CO Ralentí");//22
        modeloGasesOtto.addColumn("CO2 Ralentí");//23
        modeloGasesOtto.addColumn("O2 Ralentí");//24

        modeloGasesOtto.addColumn("RPM Crucero");//26
        modeloGasesOtto.addColumn("HC Crucero");//26
        modeloGasesOtto.addColumn("CO Crucero");//26
        modeloGasesOtto.addColumn("CO2 Crucero");//27
        modeloGasesOtto.addColumn("O2 Crucero");//28

        modeloGasesOtto.addColumn("Presencia de Dilucion");//28
        modeloGasesOtto.addColumn("Incumplimiento de niveles de emision");//28
        modeloGasesOtto.addColumn("Causa de Rechazo");//28
        modeloGasesOtto.addColumn("Concepto Tecnico de Vehiculo");//28

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
        int work = 0;
        int lng = lstPruebas.size();
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
            if (pruebas.getFinalizada().equalsIgnoreCase("Y")) {
                work = work + 1;
                System.out.println("proc..!  " + work + " de " + lng);
                if (pruebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype() == 4 || pruebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype() == 1 || pruebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype() == 10) {
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
            String eve = "pelos";
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
		tblCalibracion= new javax.swing.JTable();

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
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String[] args) {
        ReporteCAR rsv = new ReporteCAR();
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
        tables.add(tblCda);
        tables.add(tblCalibracion);
        tables.add(tblPruebas);
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
    // End of variables declaration//GEN-END:variables

    private void cargarInformacionvehiculo(Pruebas pruebas, String strIntento) {
        int deci = 1;

        Object[] objInfoVehiculo = new Object[19];

        objInfoVehiculo[0] = pruebas.getHojaPruebas().getVehiculos().getPropietarios().getNombres();
        objInfoVehiculo[1] = pruebas.getHojaPruebas().getVehiculos().getPropietarios().getTipoIdentificacion().name();
        objInfoVehiculo[2] = pruebas.getHojaPruebas().getVehiculos().getPropietarios().getCarowner();

        objInfoVehiculo[3] = pruebas.getHojaPruebas().getVehiculos().getPropietarios().getDireccion();
        objInfoVehiculo[4] = pruebas.getHojaPruebas().getVehiculos().getPropietarios().getNumerotelefono();
        objInfoVehiculo[5] = pruebas.getHojaPruebas().getVehiculos().getPropietarios().getCelular();
        objInfoVehiculo[6] = pruebas.getHojaPruebas().getVehiculos().getPropietarios().getCiudadades().getNombreciudad();

        objInfoVehiculo[7] = pruebas.getHojaPruebas().getVehiculos().getMarcas().getCarmark();
        System.out.println("id Prueba " + pruebas.getIdPruebas());
        objInfoVehiculo[8] = pruebas.getHojaPruebas().getVehiculos().getLineasVehiculos().getCarline();
        objInfoVehiculo[9] = pruebas.getHojaPruebas().getVehiculos().getCarplate();
        objInfoVehiculo[10] = pruebas.getHojaPruebas().getVehiculos().getCilindraje();
        objInfoVehiculo[11] = pruebas.getHojaPruebas().getVehiculos().getModelo();
        objInfoVehiculo[12] = pruebas.getHojaPruebas().getVehiculos().getClasesVehiculo().getClass1();
        objInfoVehiculo[13] = pruebas.getHojaPruebas().getVehiculos().getServicios().getService();
        objInfoVehiculo[14] = pruebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype();
        objInfoVehiculo[15] = pruebas.getHojaPruebas().getVehiculos().getNumeromotor();
        objInfoVehiculo[16] = pruebas.getHojaPruebas().getVehiculos().getVin();
        objInfoVehiculo[17] = pruebas.getHojaPruebas().getVehiculos().getNumerolicencia();
        objInfoVehiculo[18] = pruebas.getHojaPruebas().getVehiculos().getKilometraje();
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
            Query q = em.createNativeQuery("SELECT MAX(c.CALIBRATION),c.id_equipo,c.CURDATE FROM calibracion_dos_puntos pc inner join calibraciones c on pc.CALIBRATION = c.CALIBRATION WHERE c.id_tipo_calibracion = 2 AND  Date(c.CURDATE) between ? and ? order by c.CURDATE ");
            q.setParameter(1, new SimpleDateFormat("yyyy-MM-dd").format(c1.getTime()));
            q.setParameter(2, new SimpleDateFormat("yyyy-MM-dd").format(c1.getTime()));
            List<Object[]> LstCal = q.getResultList();
            if (LstCal.size() > 0) {
                Object[] filaEquipo = new Object[20]; // Hay tres columnas en la tabla
                for (Object[] result : LstCal) {
                    if (result[1] == null) {
                        if (ctxEq != null && ctxC2p != null) {
                            filaEquipo[0] = ctxEq.getPef();             //PEF
                            filaEquipo[1] = ctxEq.getSerial();                 //Serie Banco
                            filaEquipo[2] = ctxEq.getReolucionserial();                    //Serie Analizador
                            filaEquipo[3] = ctxEq.getMarca();        //Marca Analizador

                            filaEquipo[4] = df.format(ctxC2p.getBmHc());                   //Valor gas referencia bajo  HC verificaciòn y ajuste 
                            filaEquipo[5] = df.format(ctxC2p.getBancoBmHc());  //Valor gas referencia bajo HC verificaciòn y ajuste

                            filaEquipo[6] = df.format(ctxC2p.getBmCo());                 //Valor gas referencia bajo CO verificaciòn y ajuste
                            filaEquipo[7] = df.format(ctxC2p.getBancoBmCo());                   //Valor gas referencia BAJO CO verificaciòn y ajuste

                            filaEquipo[8] = df.format(ctxC2p.getBmCo2());                   //Valor gas referencia  BAJO CO2 verificaciòn y ajuste
                            filaEquipo[9] = df.format(ctxC2p.getBancoBmCo2());                 //Valor gas referencia  BAJO CO2 verificaciòn y ajuste

                            filaEquipo[10] = df.format(ctxC2p.getAltaHc());                   //Valor gas referencia bajo  HC verificaciòn y ajuste 
                            filaEquipo[11] = df.format(ctxC2p.getBancoAltaHc());                   //Valor gas referencia bajo CO verificaciòn y ajuste

                            filaEquipo[12] = df.format(ctxC2p.getAltaCo());                //Valor gas referencia bajo CO2 verificaciòn y ajuste
                            filaEquipo[13] = df.format(ctxC2p.getBancoAltaCo());                   //Valor gas referencia alto HC verificaciòn y ajuste

                            filaEquipo[14] = df.format(ctxC2p.getAltaCo2());                   //Valor gas referencia  alto CO verificaciòn y ajuste
                            filaEquipo[15] = df.format(ctxC2p.getBancoAltaCo2());                 //Valor gas referencia  alto CO2 verificaciòn y ajuste

                            filaEquipo[16] = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(ctxFecha);    //Fecha de Version Software
                            filaEquipo[17] = "SOLTELEC SAS";                        //Nombre del PROVEEDOR
                            filaEquipo[18] = sw.getNombre();                            //Nombre del software de aplicación
                            filaEquipo[19] = sw.getVersion();                      //Version software de aplicación
                            modelEquipo.addRow(filaEquipo);
                            break;
                        }
                        break;
                    }
                    com.soltelec.servidor.model.Equipos eq = em.find(com.soltelec.servidor.model.Equipos.class, result[1]);
                    com.soltelec.servidor.model.CalibracionDosPuntos c2p = em.find(com.soltelec.servidor.model.CalibracionDosPuntos.class, result[0]);
                    ctxC2p = c2p;
                    ctxEq = eq;
                    ctxFecha = (Date) result[2];
                    filaEquipo[0] = eq.getPef();             //PEF
                    filaEquipo[1] = eq.getSerial();                 //Serie Banco
                    filaEquipo[2] = eq.getReolucionserial();                    //Serie Analizador
                    filaEquipo[3] = eq.getMarca();                 //Marca Analizador

                    filaEquipo[4] = df.format(c2p.getBmHc());                   //Valor gas referencia bajo  HC verificaciòn y ajuste 
                    filaEquipo[5] = df.format(c2p.getBancoBmHc());  //Valor gas referencia bajo HC verificaciòn y ajuste

                    filaEquipo[6] = df.format(c2p.getBmCo());                 //Valor gas referencia bajo CO verificaciòn y ajuste
                    filaEquipo[7] = df.format(c2p.getBancoBmCo());                   //Valor gas referencia BAJO CO verificaciòn y ajuste

                    filaEquipo[8] = df.format(c2p.getBmCo2());                   //Valor gas referencia  BAJO CO2 verificaciòn y ajuste
                    filaEquipo[9] = df.format(c2p.getBancoBmCo2());                 //Valor gas referencia  BAJO CO2 verificaciòn y ajuste

                    filaEquipo[10] = df.format(c2p.getAltaHc());                   //Valor gas referencia bajo  HC verificaciòn y ajuste 
                    filaEquipo[11] = df.format(c2p.getBancoAltaHc());                   //Valor gas referencia bajo CO verificaciòn y ajuste

                    filaEquipo[12] = df.format(c2p.getAltaCo());                //Valor gas referencia bajo CO2 verificaciòn y ajuste
                    filaEquipo[13] = df.format(c2p.getBancoAltaCo());                   //Valor gas referencia alto HC verificaciòn y ajuste

                    filaEquipo[14] = df.format(c2p.getAltaCo2());                   //Valor gas referencia  alto CO verificaciòn y ajuste
                    filaEquipo[15] = df.format(c2p.getBancoAltaCo2());                 //Valor gas referencia  alto CO2 verificaciòn y ajuste

                    filaEquipo[16] = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(result[2]);    //Fecha de Version Software
                    filaEquipo[17] = "SOLTELEC SAS";                        //Nombre del PROVEEDOR
                    filaEquipo[18] = sw.getNombre();                            //Nombre del software de aplicación
                    filaEquipo[19] = sw.getVersion();                      //Version software de aplicación
                    modelEquipo.addRow(filaEquipo);

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
        //Float valTemp=0.0F;
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
                    objPrueba[6] = medidas.getValormedida();
                    break;
                case 8032: //                        
                    objPrueba[7] = medidas.getValormedida();
                    break;
            }
        }
        String forMedTemp = "No Definido";
        if (pruebas.getHojaPruebas().getVehiculos().getTipoVehiculo().getCartype() == 4) {
            forMedTemp = "N/A";
        } else {
            if (valTemp > 40 && valTemp < 59) {
                forMedTemp = "2";
            }
            if (valTemp > 60) {
                forMedTemp = "1";
            }
            if (valTemp == 0) {
                forMedTemp = "3";
            }
        }
        objPrueba[8] = "0";
        objPrueba[5] = forMedTemp;
        if (modeloPrueba.getRowCount() > 14) {
            int e = 1;
        }
        modeloPrueba.addRow(objPrueba);
    }

    /**
     * Carga todas las medidas de la prueba de luces
     */
    private void cargarInformacionPruebaGasesOtto(Pruebas pruebas, int nroIntento) {
        Object[] dataResultGases = new Object[29];
        boolean tieneRegistro = false;
        Float valTemp = 0.0F;
        if (pruebas.getTipoPrueba().getTesttype() == 8) {

            dataResultGases[0] = "NO";
            dataResultGases[1] = "NO";
            dataResultGases[2] = "NO";
            dataResultGases[3] = "NO";
            dataResultGases[4] = "NO";
            dataResultGases[5] = "NO";
            dataResultGases[6] = "NO";
            dataResultGases[7] = "NO";
            dataResultGases[8] = "NO";
            dataResultGases[9] = "NO";
            dataResultGases[10] = "NO";
            dataResultGases[22] = "NO";
            dataResultGases[23] = "NO";

            boolean condicionAnormal = false;
            if (pruebas.getComentarioAborto() != null) {
                if (pruebas.getComentarioAborto().equalsIgnoreCase("Condiciones Anormales")) {
                    if (pruebas.getObservaciones() != null) {
                        String[] arrValor = pruebas.getObservaciones().split(".-");
                        if (arrValor.length > 1) {
                            String[] arrCondiciones = arrValor[1].split(";");
                            if (arrCondiciones.length > 0) {
                                for (int i = 0; i < arrCondiciones.length; i++) {
                                    if (arrCondiciones[i].startsWith("Existencia de fugas en el tubo")) {
                                        dataResultGases[0] = "SI";
                                        dataResultGases[24] = "1";
                                        condicionAnormal = true;
                                    }
                                    if (arrCondiciones[i].startsWith("Existencia de fugas en el tubo") || arrCondiciones[i].startsWith(" Existencia de fugas en el tubo")) {
                                        dataResultGases[1] = "SI";
                                        dataResultGases[24] = "2";
                                        condicionAnormal = true;
                                    }
                                    if (arrCondiciones[i].startsWith("Instalación de accesorios o deformaciones") || arrCondiciones[i].startsWith(" Instalación de accesorios o deformaciones")) {
                                        dataResultGases[2] = "SI";
                                        dataResultGases[24] = "10";
                                        condicionAnormal = true;
                                    }
                                    if (arrCondiciones[i].startsWith("Ausencia de tapas o tapones de combustible") || arrCondiciones[i].startsWith(" Ausencia de tapas o tapones de combustible")) {
                                        dataResultGases[3] = "SI";
                                        dataResultGases[24] = "4";
                                        condicionAnormal = true;
                                    }
                                    if (arrCondiciones[i].startsWith("Ausencia de tapones de aceite") || arrCondiciones[i].startsWith(" Ausencia de tapones de aceite")) {
                                        dataResultGases[4] = "SI";
                                        dataResultGases[24] = "3";
                                        condicionAnormal = true;
                                    }
                                    if (arrCondiciones[i].startsWith("Sistema de admisión de aire en mal estado") || arrCondiciones[i].startsWith(" Sistema de admisión de aire en mal estado")) {
                                        dataResultGases[5] = "SI";
                                        dataResultGases[24] = "6";
                                        condicionAnormal = true;
                                    }

                                    if (arrCondiciones[i].startsWith("Salidas adicionales en el sistema de escape") || arrCondiciones[i].startsWith(" Salidas adicionales en el sistema de escape")) {
                                        dataResultGases[6] = "SI";
                                        dataResultGases[24] = "5";
                                        condicionAnormal = true;
                                    }
                                    if (arrCondiciones[i].startsWith("Desconexión de sistemas de recirculaci") || arrCondiciones[i].startsWith(" Desconexión de sistemas de recirculaci")) {
                                        dataResultGases[7] = "SI";
                                        dataResultGases[24] = "9";
                                        condicionAnormal = true;
                                    }
                                    if (arrCondiciones[i].startsWith("Incorrecta operación del sistema de refrigeraci") || arrCondiciones[i].startsWith(" Incorrecta operación del sistema de refrigeraci")) {
                                        dataResultGases[10] = "SI";
                                        dataResultGases[24] = "11";
                                        condicionAnormal = true;
                                    }

                                }
                            } else {
                                for (Defxprueba defxprueba : pruebas.getDefectos()) {
                                    switch (defxprueba.getDefxpruebaPK().getIdDefecto()) {
                                        case 84002:                             //Existencia de fugas en el tubo, uniones del múltiple y silenciador del sistema de escape del vehiculo
                                            dataResultGases[0] = "SI";
                                            dataResultGases[24] = "1";
                                            condicionAnormal = true;
                                            break;
                                        case 84035:                             //Existencia de fugas en el tubo, uniones del múltiple y silenciador del sistema de escape del vehiculo
                                            dataResultGases[1] = "SI";
                                            dataResultGases[24] = "2";
                                            condicionAnormal = true;
                                            break;
                                        case 84003:                            //Salidas adicionales en el sistema de escape diferentes a las de diseño original del vehículo
                                            dataResultGases[6] = "SI";
                                            dataResultGases[24] = "5";
                                            condicionAnormal = true;
                                            break;
                                        case 84038:                 //Salidas adicionales en el sistema de escape diferentes a las de diseño original del vehículo
                                            dataResultGases[6] = "SI";
                                            dataResultGases[24] = "5";
                                            condicionAnormal = true;
                                            break;
                                        case 84000:                            //AUSENCIAS TAPONES COMBUSTIBLES
                                            dataResultGases[3] = "SI";
                                            condicionAnormal = true;
                                            dataResultGases[24] = "4";
                                            break;
                                        case 84001:                            //Ausencia de tapones de aceite o fugas en el mismo
                                            dataResultGases[4] = "SI";
                                            dataResultGases[24] = "3";
                                            condicionAnormal = true;
                                            break;
                                        case 84037:                            //Ausencia de tapones de aceite o fugas en el mismo
                                            dataResultGases[4] = "SI";
                                            dataResultGases[24] = "4";
                                            condicionAnormal = true;
                                            break;
                                        case 84009:                            //Sistema de admision de aire en mal estado (filtro roto, o deformado) o ausencia del filtro de aire
                                            dataResultGases[5] = "SI";
                                            dataResultGases[24] = "6";
                                            condicionAnormal = true;
                                            break;
                                        case 84010:                            //Desconexion de sistemas de recirculacion de gases provenientes del Carter del motor.(Por ejemplo valvula de ventilacion positiva del Carter)
                                            dataResultGases[7] = "SI";
                                            dataResultGases[24] = "9";
                                            condicionAnormal = true;
                                            break;
                                        case 84011:                            //Instalacion de accesorios o deformaciones en el tubo de escape que no permitan la introduccion de la sonda.
                                            dataResultGases[2] = "SI";
                                            condicionAnormal = true;
                                            dataResultGases[24] = "10";
                                            break;
                                        case 84012:                            //Incorrecta operacion del sistema de refrigeracion, cuya verificacion se hara por medio de inspeccion.
                                            dataResultGases[10] = "SI";
                                            dataResultGases[24] = "11";
                                            condicionAnormal = true;
                                            break;
                                    }

                                }
                            }
                        }
                    }//si hay coment condiciones Anormales
                }//si exitenCondiciones
            }//si diferenteNull
            String ruido = obtenerRuidoScape(pruebas.getHojaPruebas());
            if (ruido != null) {
                String[] tr = ruido.split(";");
                dataResultGases[26] = "Artisan";
                dataResultGases[27] = tr[1];
                dataResultGases[28] = tr[0];
            }
            if (pruebas.getComentarioAborto() != null) {
                if (pruebas.getComentarioAborto().equalsIgnoreCase("REVOLUCIONES FUERA RANGO")) {
                    dataResultGases[9] = "SI";
                    dataResultGases[24] = "8";
                    dataResultGases[25] = "2";
                    condicionAnormal = true;
                }

                if (pruebas.getComentarioAborto().startsWith("Presencia")) {
                    dataResultGases[8] = "SI";
                    dataResultGases[24] = "7";
                    dataResultGases[25] = "2";
                    condicionAnormal = true;
                }
                if (pruebas.getComentarioAborto().equalsIgnoreCase("DILUCION DE MUESTRA")) {
                    dataResultGases[22] = "SI";
                    dataResultGases[24] = "12";
                }
            }

            if (pruebas.getFinalizada().equalsIgnoreCase("A")) {
                dataResultGases[25] = "3";
            }

            Float hcRalenti = 0.0F;
            Float hcCrucero = 0.0F;
            Float coRalenti = 0.0F;
            Float coCrucero = 0.0F;
            List<Defxprueba> lstDefxprueba = pruebas.getDefectos();
            for (Medidas medidas : pruebas.getMedidasList()) {
                switch (medidas.getTiposMedida().getMeasuretype()) {
                    case 8006: //Temperatura en ralenti (TEMPR)
                        dataResultGases[11] = medidas.getValormedida();
                        valTemp = medidas.getValormedida();
                        break;
                    case 8022: //Temperatura en ralenti (TEMPR) FOR MOTO 2T
                        dataResultGases[11] = medidas.getValormedida();
                        break;
                    case 8012: //Temperatura en ralenti (TEMPR) FOR MOTO 4T
                        dataResultGases[11] = medidas.getValormedida();
                        valTemp = medidas.getValormedida();
                        break;
                    case 8011: //Revoluciones por minuto en crucero (RPMC)
                        dataResultGases[17] = medidas.getValormedida();
                        break;
                    case 8028: //Revoluciones por minuto en ralenty  (RPMC) PARA MOTO 
                        dataResultGases[21] = medidas.getValormedida();
                        break;
                    case 8005: //Revoluciones por minuto en ralenty (RPMR)
                        dataResultGases[12] = medidas.getValormedida();
                        break;

                    case 8002: //Monoxido de carbono en ralenty (COR)
                        dataResultGases[14] = medidas.getValormedida();
                        coRalenti = medidas.getValormedida();
                        break;
                    case 8003: //Dioxido de carbono en ralenty (CO2R)
                        dataResultGases[15] = medidas.getValormedida();
                        break;
                    case 8004: //Oxigeno en ralenty (O2R)
                        dataResultGases[16] = medidas.getValormedida();
                        break;
                    case 8021: //Oxigeno en ralenty (O2R) PARA MOTO 2T
                        dataResultGases[16] = medidas.getValormedida();
                        break;
                    case 8001: //HidroCarburos en ralenty (HCR)
                        dataResultGases[13] = medidas.getValormedida();
                        hcRalenti = medidas.getValormedida();
                        break;
                    case 8018: //HidroCarburos en ralenty (HCR) Para Motos 2t
                        dataResultGases[13] = medidas.getValormedida();
                        hcCrucero = medidas.getValormedida();
                        break;
                    case 8008: //Monoxido de carbono en crucero (COC)
                        dataResultGases[19] = medidas.getValormedida();
                        coCrucero = medidas.getValormedida();
                        break;
                    case 8020: //Monoxido de carbono en crucero (COC) PARA MOTOS 2T
                        dataResultGases[19] = medidas.getValormedida();
                        coRalenti = medidas.getValormedida();
                        break;
                    case 8009: //Dioxido de carbono en crucero (CO2C)
                        dataResultGases[20] = medidas.getValormedida();
                        break;
                    case 8019: //Dioxido de carbono en crucero (CO2C) para Motos 2t
                        dataResultGases[20] = medidas.getValormedida();
                        break;
                    case 8010: //Oxigeno en crucero (O2C)
                        dataResultGases[21] = medidas.getValormedida();
                        break;
                    case 8007: //HidroCarburos en crucero (HCC)
                        dataResultGases[18] = medidas.getValormedida();
                        hcCrucero = medidas.getValormedida();
                        break;

                }
                if (pruebas.getAprobada().equalsIgnoreCase("Y")) {
                    dataResultGases[23] = "NO";
                    dataResultGases[24] = "0";
                    dataResultGases[25] = "1";
                } else {
                    dataResultGases[25] = "2";
                    if (condicionAnormal == false) {
                        if ((pruebas.getHojaPruebas().vehiculos.getModelo()) <= 1970) {
                            dataResultGases[24] = "0";

                            if (coRalenti <= 5) {
                                dataResultGases[24] = "13";
                            }
                            if (coCrucero <= 5) {
                                dataResultGases[24] = "21";
                            }
                            if (hcRalenti <= 800) {
                                dataResultGases[24] = "14";
                            }
                            if (hcCrucero <= 800) {
                                dataResultGases[24] = "22";
                            }
                        } else if (pruebas.getHojaPruebas().vehiculos.getModelo() > 1970 && pruebas.getHojaPruebas().vehiculos.getModelo() <= 1984) {

                            if (coRalenti <= 4) {
                                dataResultGases[24] = "15";
                            }
                            if (coCrucero <= 4) {
                                dataResultGases[24] = "23";
                            }
                            if (hcRalenti <= 650) {
                                dataResultGases[24] = "16";
                            }
                            if (hcCrucero <= 650) {
                                dataResultGases[24] = "24";
                            }

                        } else if (pruebas.getHojaPruebas().vehiculos.getModelo() > 1984 && pruebas.getHojaPruebas().vehiculos.getModelo() <= 1997) {

                            if (coRalenti <= 3) {
                                dataResultGases[24] = "17";
                            }
                            if (coCrucero <= 3) {
                                dataResultGases[24] = "25";
                            }
                            if (hcRalenti <= 400) {
                                dataResultGases[24] = "18";
                            }
                            if (hcCrucero <= 400) {
                                dataResultGases[24] = "26";
                            }
                        } else if (pruebas.getHojaPruebas().vehiculos.getModelo() > 1997) {

                            if (coRalenti <= 1) {
                                dataResultGases[24] = "19";
                            }
                            if (coCrucero <= 1) {
                                dataResultGases[24] = "27";
                            }
                            if (hcRalenti <= 200) {
                                dataResultGases[24] = "18";
                            }
                            if (hcCrucero <= 200) {
                                dataResultGases[24] = "28";
                            }
                        }
                    }
                    dataResultGases[23] = "SI";

                }

            }
        }
        modeloGasesOtto.addRow(dataResultGases);
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
