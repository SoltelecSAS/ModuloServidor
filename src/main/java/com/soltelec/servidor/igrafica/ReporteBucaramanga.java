/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.igrafica;

import com.soltelec.servidor.consultas.Reportes;
import com.soltelec.servidor.dtos.DatosCda;
import com.soltelec.servidor.dtos.reportes_ntcs.DatosGeneralesNtc;
import com.soltelec.servidor.dtos.reportes_ntcs.EquiposSoftOttoNtc;
import com.soltelec.servidor.dtos.reportes_ntcs.PropietariosNtc;
import com.soltelec.servidor.dtos.reportes_ntcs.ReporteNtc;
import com.soltelec.servidor.dtos.reportes_ntcs.ResultadosDieselNtc;
import com.soltelec.servidor.dtos.reportes_ntcs.ResultadosOttoNtc;
import com.soltelec.servidor.dtos.reportes_ntcs.VehiculoNtc;
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
 * todos los clientes de los cda's deben poder generar este reporte para ser
 * enviado por medio del software "vigia" a la superintendencia.
 *
 * @requerimiento SART-2 Creación de reporte de inspecciones
 * @author Gerencia Desarollo TecNOlogica
 * @fecha 01/03/2018
 */
public class ReporteBucaramanga extends javax.swing.JInternalFrame {
//public class ReporteSuperintendenciaVijia extends javax.swing.JFrame {

    private DefaultTableModel modeloCDA;
    private DefaultTableModel modeloInfoVehiculo;
    private DefaultTableModel modeloPropietario;
    private DefaultTableModel modeloEquipoMedicion;
    private DefaultTableModel modeloDatosPrueba;
    private DefaultTableModel modeloResultadoPrueba;
    private DefaultTableModel modeloResultadoDiesel;

    public ReporteBucaramanga() {
        super("Reporte Bucaramanga",
                true, //resizable
                true, //closable
                false, //maximizable
                true);
        initModels();
        initComponents();

    }

    private void initModels() {

        modeloCDA = new DefaultTableModel();
        modeloCDA.addColumn("Número Establecimiento");
        modeloCDA.addColumn("Nombre Establecimiento");
        modeloCDA.addColumn("NIT");
        modeloCDA.addColumn("Dirección");
        modeloCDA.addColumn("Telefono 1");
        modeloCDA.addColumn("Telefono 2");
        modeloCDA.addColumn("Ciudad");
        modeloCDA.addColumn("Número Resolución");
        modeloCDA.addColumn("Fecha Resolución");//9
        modeloCDA.addColumn("Clase cda");//9
        modeloCDA.addColumn("Norma aplicada");//9

        modeloEquipoMedicion = new DefaultTableModel();
        modeloEquipoMedicion.addColumn("Factor Equivalencia PEF");
        modeloEquipoMedicion.addColumn("# serie Banco");
        modeloEquipoMedicion.addColumn("# Serie Equipo Gases");
        modeloEquipoMedicion.addColumn("Marca Equipo");
        modeloEquipoMedicion.addColumn("Referencia Bajo y ajuste HC");
        modeloEquipoMedicion.addColumn("Referencia Bajo y ajuste CO");
        modeloEquipoMedicion.addColumn("Referencia Bajo y ajuste CO2");
        modeloEquipoMedicion.addColumn("Referencia alto y ajuste HC");
        modeloEquipoMedicion.addColumn("Referencia alto y ajuste CO");
        modeloEquipoMedicion.addColumn("Referencia alto y ajuste CO2");
        modeloEquipoMedicion.addColumn("Fecha y hora ultima verificación fugas");
        modeloEquipoMedicion.addColumn("Fecha y hora ultima verificación dos puntos");
        modeloEquipoMedicion.addColumn("Nombre Sw aplicación");
        modeloEquipoMedicion.addColumn("Versión del SW Aplicacion");

        modeloInfoVehiculo = new DefaultTableModel();
        modeloInfoVehiculo.addColumn("Placa");//0
        modeloInfoVehiculo.addColumn("Marca");//9
        modeloInfoVehiculo.addColumn("Modelo");//1
        modeloInfoVehiculo.addColumn("Linea");//10
        modeloInfoVehiculo.addColumn("Clase");//6
        modeloInfoVehiculo.addColumn("Servicio");//6
        modeloInfoVehiculo.addColumn("No. Motor");//2
        modeloInfoVehiculo.addColumn("VIN");//3
        modeloInfoVehiculo.addColumn("No. licencia");//6
        modeloInfoVehiculo.addColumn("Cilindraje");//4
        modeloInfoVehiculo.addColumn("Kilometraje");//6
        modeloInfoVehiculo.addColumn("Diseño");//6
        modeloInfoVehiculo.addColumn("Tipo de combustible");//6
        modeloInfoVehiculo.addColumn("Tipo de motor");//6
        

        modeloPropietario = new DefaultTableModel();
        modeloPropietario.addColumn("Nombre propietario");//4
        modeloPropietario.addColumn("Tipo documento propietario");//6
        modeloPropietario.addColumn("No. documento propietario");//6
        modeloPropietario.addColumn("Celular propietario");//4
        modeloPropietario.addColumn("Telefono propietario");//6

        modeloDatosPrueba = new DefaultTableModel();
        modeloDatosPrueba.addColumn("Fecha inicial");//0
        modeloDatosPrueba.addColumn("Fecha final");//1
        modeloDatosPrueba.addColumn("Nombre inspector");//3
        modeloDatosPrueba.addColumn("Tipo documento inspector");//4
        modeloDatosPrueba.addColumn("No. documento inspector");//5
        modeloDatosPrueba.addColumn("Lugar de medicion");//6
        modeloDatosPrueba.addColumn("Temperatura ambiente");//7
        modeloDatosPrueba.addColumn("Humedad relativa");//


        modeloResultadoPrueba = new DefaultTableModel();
        modeloResultadoPrueba.addColumn("Temperatura motor");//0--
        modeloResultadoPrueba.addColumn("RPM_RAL");//1
        modeloResultadoPrueba.addColumn("RPM_CRU");//2
        modeloResultadoPrueba.addColumn("Presencia humo");//3 ---
        modeloResultadoPrueba.addColumn("Dilucion");//4
        modeloResultadoPrueba.addColumn("Rpm fuera rango");//5 ---
        modeloResultadoPrueba.addColumn("Fugas tubo escape");//6 ----
        modeloResultadoPrueba.addColumn("Salidas adicionales");//7 ---
        modeloResultadoPrueba.addColumn("Sin tapa o fuga aceite");//8 --
        modeloResultadoPrueba.addColumn("Sin tapa o fuga combustible");//9 ---
        modeloResultadoPrueba.addColumn("Accesorios tubos");//12
        modeloResultadoPrueba.addColumn("R_HC_RAL");//14
        modeloResultadoPrueba.addColumn("R_HC_CRU");//15
        modeloResultadoPrueba.addColumn("R_CO_RAL");//16
        modeloResultadoPrueba.addColumn("R_CO_CRU");//17
        modeloResultadoPrueba.addColumn("R_CO2_RAL");//18
        modeloResultadoPrueba.addColumn("R_CO2_CRU");//19
        modeloResultadoPrueba.addColumn("R_O2_RAL");//20
        modeloResultadoPrueba.addColumn("R_O2_CRU");//21
        modeloResultadoPrueba.addColumn("NC_EMISIONES_OTTO");//22
        modeloResultadoPrueba.addColumn("RES_FINAL_OTTO");//23

        modeloResultadoDiesel = new DefaultTableModel();
        modeloResultadoDiesel.addColumn("Ciclo preliminar %");//0--
        modeloResultadoDiesel.addColumn("Rpm Gobernada Preliminar");//1
        modeloResultadoDiesel.addColumn("Rpm Ralenti preliminar");//2
        modeloResultadoDiesel.addColumn("Ciclo 1 %");//3 ---
        modeloResultadoDiesel.addColumn("Rpm gobernada ciclo 1");//4
        modeloResultadoDiesel.addColumn("Rpm Ralenti ciclo 1");//5 ---
        modeloResultadoDiesel.addColumn("Ciclo 2 %");//6 ----
        modeloResultadoDiesel.addColumn("RPM gobernada ciclo 2");//7 ---
        modeloResultadoDiesel.addColumn("RPM Ralenti ciclo 2");//8 --
        modeloResultadoDiesel.addColumn("Ciclo 3 %");//9 ---
        modeloResultadoDiesel.addColumn("RPM Gobernada ciclo 3");//12
        modeloResultadoDiesel.addColumn("Rpm ralenti ciclo 3");//14
        modeloResultadoDiesel.addColumn("Resultado final %");//15
        modeloResultadoDiesel.addColumn("ciclo preliminar m-1");//16
        modeloResultadoDiesel.addColumn("ciclo 1 m-1");//17
        modeloResultadoDiesel.addColumn("ciclo 2 m-1");//18
        modeloResultadoDiesel.addColumn("ciclo 3 m-1");//19
        modeloResultadoDiesel.addColumn("Resultado final m-1");//20
        modeloResultadoDiesel.addColumn("RES_FINAL_DIESEL");//23

    }

    private void fillData(Date fechaInicial, Date fechaFinal){

        ReporteNtc todosLosDatos = Reportes.getNtc(fechaInicial, fechaFinal,3);

        List<DatosCda> datosCda = todosLosDatos.getCda();

        List<PropietariosNtc> datosPropietarios = todosLosDatos.getPropietarios();
        List<VehiculoNtc> datosVehiculo = todosLosDatos.getVehiculos(); 
        List<EquiposSoftOttoNtc> datosSoftwareEquipo = todosLosDatos.getEquiposOtto(); 
        List<DatosGeneralesNtc> datosInspeccion = todosLosDatos.getGeneralesPrueba(); 
        List<ResultadosOttoNtc> datosResulInspeccion  = todosLosDatos.getResultadosOtto(); 
        List<ResultadosDieselNtc> datosResutDiesel = todosLosDatos.getResultadosDiesel();

        datosCda.stream().forEach(datoCda -> {
            Object[] fila = {

                datoCda.getCm(),
                datoCda.getNombreCda(),
                datoCda.getNIT(),
                datoCda.getDireccion(),
                datoCda.getTelefono(),
                datoCda.getCelular(),
                datoCda.getCiudad(),
                datoCda.getNoResolucionAuthAmbiental(),
                datoCda.getFechaResolucionAuthAmbiental(),
                datoCda.getClaseCda(),
                datoCda.getNormaAplicada()
            };
            modeloCDA.addRow(fila);
        });

        datosSoftwareEquipo.stream().forEach(datoSoftwareEquipo -> {
            Object[] fila = {
                datoSoftwareEquipo.getPef(),
                datoSoftwareEquipo.getSerialBg(),
                datoSoftwareEquipo.getSerialAg(),
                datoSoftwareEquipo.getMarcaAg(),
                datoSoftwareEquipo.getPHcBajoH(),
                datoSoftwareEquipo.getPCoBajo(),
                datoSoftwareEquipo.getPCo2Bajo(),
                datoSoftwareEquipo.getPHcAltoP(),
                datoSoftwareEquipo.getPCoAlto(),
                datoSoftwareEquipo.getPCo2Alto(),
                datoSoftwareEquipo.getFFugas(),
                datoSoftwareEquipo.getFVgp(),
                "Soltelec",
                "1.7.3"
            };
            modeloEquipoMedicion.addRow(fila);
        });

        

        datosVehiculo.stream().forEach(datoVehiculo -> {
            Object[] fila = {
                datoVehiculo.getPlaca(),
                datoVehiculo.getMarca(),
                datoVehiculo.getModelo(),
                datoVehiculo.getLinea(),
                datoVehiculo.getClase(),
                datoVehiculo.getServicio(),
                datoVehiculo.getNumMotor(),
                datoVehiculo.getVin(),
                datoVehiculo.getLicTrans(),
                datoVehiculo.getCilindraje(),
                datoVehiculo.getKm(),
                !datoVehiculo.getTipComb().equals("DIESEL") ?datoVehiculo.getDiseno() : "None",
                datoVehiculo.getTipComb(),
                !datoVehiculo.getTipComb().equals("DIESEL") ? (datoVehiculo.getDiseno().equals("None") ? "Otto" : datoVehiculo.getTipMotor()) : ""
            };
            modeloInfoVehiculo.addRow(fila);
        });

        datosPropietarios.stream().forEach(datoPropietarios ->{
            Object[] fila = {
                datoPropietarios.getNomProp(),
                datoPropietarios.getTipIdeProp(),
                datoPropietarios.getNumIdeProp(),
                datoPropietarios.getTel2Prop(),
                datoPropietarios.getTel1Prop()
            };
            modeloPropietario.addRow(fila);
        });

        datosInspeccion.stream().forEach(datoInspeccion -> {
            Object[] fila = {
                datoInspeccion.getFIniInsp(),
                datoInspeccion.getFFinInsp(),
                datoInspeccion.getNomIt(),
                datoInspeccion.getTipIdeIt(),
                datoInspeccion.getNumIdeIt(),
                datoInspeccion.getLugarTemp(),
                datoInspeccion.getTAmb().replace(",0", ""),
                datoInspeccion.getHRel().replace(",0", "")
            };
            modeloDatosPrueba.addRow(fila);
        });

        datosResulInspeccion.stream().forEach(resultado -> {
            Object[] fila = {
                resultado.getTMotor(),
                resultado.getRpmRal(),
                resultado.getRpmCru(),
                resultado.getRpmRal() == null ? "": resultado.getHumo(),
                resultado.getRpmRal() == null ? "": resultado.getDilucion(),
                resultado.getRpmRal() == null ? "": resultado.getRpmFuera(),
                resultado.getRpmRal() == null ? "": resultado.getFugaTubo(),
                resultado.getRpmRal() == null ? "": resultado.getSalidasAd(),
                resultado.getRpmRal() == null ? "": resultado.getFugaAceite(),
                resultado.getRpmRal() == null ? "": resultado.getFugaComb(),
                resultado.getRpmRal() == null ? "": resultado.getAccTubo(),
                resultado.getRHcRal().replace(",0", ""),
                resultado.getRHcCru().replace(",0", ""),
                resultado.getRCoRal().replace(",0", ""),
                resultado.getRCoCru().replace(",0", ""),
                resultado.getRCo2Ral().replace(",0", ""),
                resultado.getRCo2Cru().replace(",0", ""),
                resultado.getRO2Ral().replace(",0", ""),
                resultado.getRO2Cru().replace(",0", ""),
                resultado.getRpmRal() == null ? "": resultado.getNcEmisiones(),
                resultado.getRpmRal() == null ? "": resultado.getResFinal()
            };
            modeloResultadoPrueba.addRow(fila);
        });

        datosResutDiesel.stream().forEach(resultado -> {
            Object[] fila = {
                resultado.getROpPre().equals("0.00") ? "" : resultado.getROpPre().replace(",0", ""),
                resultado.getRpmGobPre(),
                resultado.getRpmRalPre(),
                resultado.getROpC1().equals("0.00") ? "" : resultado.getROpC1().replace(",0", ""),
                resultado.getRpmGobC1(),
                resultado.getRpmRalC1(),
                resultado.getROpC2().equals("0.00") ? "" : resultado.getROpC2().replace(",0", ""),
                resultado.getRpmGobC2(),
                resultado.getRpmRalC2(),
                resultado.getROpC3().equals("0.00") ? "" : resultado.getROpC3().replace(",0", ""),
                resultado.getRpmGobC3(),
                resultado.getRpmRalC3(),
                resultado.getRFinalOp().equals("0.00") ? "" : resultado.getRFinalOp().replace(",0", ""),
                resultado.getRDenPre(),
                resultado.getRDenC1(),
                resultado.getRDenC2(),
                resultado.getRDenC3(),
                resultado.getRFinalDen(),
                resultado.getRFinalOp().equals("0.00") ? "" : resultado.getResFinal()
            };
            modeloResultadoDiesel.addRow(fila);
        });
        

        this.tblCda.setModel(modeloCDA);
        this.tblEquipoMedicion.setModel(modeloEquipoMedicion);
        this.tblInfoVehiculos.setModel(modeloInfoVehiculo);
        // this.tblMedidas.setModel(modeloMedidas);
        
        this.tblModeloPropietario.setModel(modeloPropietario);
        this.tblDatosPrueba.setModel(modeloDatosPrueba);
        this.tblResultadosPrueba.setModel(modeloResultadoPrueba);
        this.tblResultadosDiesel.setModel(modeloResultadoDiesel);

        tblCda.setEnabled(false);
        tblInfoVehiculos.setEnabled(false);
        tblMedidas.setEnabled(false);
        tblEquipoMedicion.setEnabled(false);
        tblModeloPropietario.setEnabled(false);
        tblDatosPrueba.setEnabled(false);
        tblResultadosPrueba.setEnabled(false);
        tblResultadosDiesel.setEnabled(false);
    }

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
        jScrollPane11 = new javax.swing.JScrollPane();
        jScrollPane12 = new javax.swing.JScrollPane();
        jScrollPane13 = new javax.swing.JScrollPane();
        jScrollPane14 = new javax.swing.JScrollPane();
        jScrollPane15 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblMedidas = new javax.swing.JTable();
        tblCda = new javax.swing.JTable();
        tblEquipoMedicion = new javax.swing.JTable();
        tblModeloPropietario = new javax.swing.JTable();
        tblDatosPrueba = new javax.swing.JTable();
        tblResultadosPrueba = new javax.swing.JTable();
        tblResultadosDiesel = new javax.swing.JTable();

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

        /*
        *
        *
        *   Crear los NOmbres de la tablas y agregarlos al menu de Reportes
        *
         */
        //Tabla de Modelo CDA	
        tblCda.setModel(modeloCDA);
        tblCda.setName("Información del Cda."); // NOI18N
        jScrollPane11.setViewportView(tblCda);
        infPrueba.addTab("Inf. Cda", jScrollPane11);

        tblEquipoMedicion.setModel(modeloEquipoMedicion);
        tblEquipoMedicion.setName("Dtos Equipo medicion"); // NOI18N
        jScrollPane12.setViewportView(tblEquipoMedicion);
        infPrueba.addTab("Inf. medicion", jScrollPane12);

        //Tabla de Modelo d einformacion Vehiculo		
        tblInfoVehiculos.setModel(modeloInfoVehiculo);
        tblInfoVehiculos.setName("Información de Vehiculo"); // NOI18N
        jScrollPane1.setViewportView(tblInfoVehiculos);
        infPrueba.addTab("Inf. Vehiculo", jScrollPane1);

        tblModeloPropietario.setModel(modeloPropietario);
        tblModeloPropietario.setName("Dtos. propietario"); // NOI18N		
        jScrollPane13.setViewportView(tblModeloPropietario);
        infPrueba.addTab("Inf. Propietario", jScrollPane13);

        //Tabla datos generales inspeccion
        tblDatosPrueba.setModel(modeloDatosPrueba);
        tblDatosPrueba.setName("Datos Prueba"); // NOI18N		
        jScrollPane14.setViewportView(tblDatosPrueba);
        infPrueba.addTab("Inf. Datos prueba", jScrollPane14);

        //Tabla 
        tblResultadosPrueba.setModel(modeloResultadoPrueba);
        tblResultadosPrueba.setName("Datos resultados otto"); // NOI18N		
        jScrollPane15.setViewportView(tblResultadosPrueba);
        infPrueba.addTab("Inf. Datos resultados otto", jScrollPane15);

        
        tblResultadosDiesel.setModel(
            modeloResultadoDiesel);
        tblResultadosDiesel.setName("Datos diesel"); // NOI18N		
        jScrollPane2.setViewportView(tblResultadosDiesel);
        infPrueba.addTab("Inf. datos resultados diesel", jScrollPane2);

        
 /*
*
*   Agregar los componetes de  NOmbres fechas y btn de generar al layout sea visible    
    
*
         */
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
        ReporteBucaramanga rsv = new ReporteBucaramanga();
        rsv.setVisible(true);
    }

    /*
    *
    *
    * Evento de Boton de Generar ingresando la fecha de inicio y fin
    *
     */
    private void btnGenerarActionPerformed(java.awt.event.ActionEvent evt) {
        if (fechaInicial.getDate() == null || fechaFInal.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Seleccione Fechas");
            return;
        }
        SwingUtilities.invokeLater(
                new Runnable() {
            @Override
            public void run() {
                fillData(fechaInicial.getDate(), fechaFInal.getDate());
                btnGenerar.setEnabled(false);
            }
        });
    }

    /*
    *
    *
    * Evento de Boton de Exportar la informacion a Excel 
    *
     */
    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {

        List<JTable> tables = new ArrayList<>();
        tables.add(tblCda);
        tables.add(tblEquipoMedicion);
        tables.add(tblInfoVehiculos);
        tables.add(tblMedidas);
        tables.add(tblModeloPropietario);
        tables.add(tblDatosPrueba);
        tables.add(tblResultadosPrueba);
        tables.add(tblResultadosDiesel);
        GenericExportExcel excel = new GenericExportExcel();
        excel.exportSameSheet(tables);
    }

    /*
    *
    * Declaracion de Variables  para la interfaz Grafica
    *
     */
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btnGenerar;
    private org.jdesktop.swingx.JXDatePicker fechaFInal;
    private org.jdesktop.swingx.JXDatePicker fechaInicial;
    private javax.swing.JTabbedPane infPrueba;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblCda;
    private javax.swing.JTable tblEquipoMedicion;
    private javax.swing.JTable tblMedidas;
    private javax.swing.JTable tblInfoVehiculos;
    private javax.swing.JTable tblModeloPropietario;
    private javax.swing.JTable tblResultadosPrueba;
    private javax.swing.JTable tblResultadosDiesel;
    private javax.swing.JTable tblDatosPrueba;



}
