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
    private DefaultTableModel modeloDatosGeneralesInspeccion;
    private DefaultTableModel modeloResultadoPrueba;

    public ReporteBucaramanga() {
        super("Reporte Medellin (4983)",
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
        modeloDatosPrueba.addColumn("Fecha de aborto");//2
        modeloDatosPrueba.addColumn("Nombre inspector");//3
        modeloDatosPrueba.addColumn("Tipo documento inspector");//4
        modeloDatosPrueba.addColumn("No. documento inspector");//5
        modeloDatosPrueba.addColumn("Lugar de medicion");//6
        modeloDatosPrueba.addColumn("Temperatura ambiente");//7
        modeloDatosPrueba.addColumn("Humedad relativa");//8
        modeloDatosPrueba.addColumn("Causal Aborto");//9


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
        modeloResultadoPrueba.addColumn("NC_EMISIONES");//22
        modeloResultadoPrueba.addColumn("RES_FINAL");//23

    }

    private void fillData(Date fechaInicial, Date fechaFinal){

        ReporteNtc todosLosDatos = Reportes.getNtc(fechaInicial, fechaFinal,3);

        List<DatosCda> datosCda = todosLosDatos.getCda();

        List<PropietariosNtc> datosPropietarios = todosLosDatos.getPropietarios();
        List<VehiculoNtc> datosVehiculo = todosLosDatos.getVehiculos(); 
        List<EquiposSoftOttoNtc> datosSoftwareEquipo = todosLosDatos.getEquiposOtto(); 
        List<DatosGeneralesNtc> datosInspeccion = todosLosDatos.getGeneralesPrueba(); 
        List<ResultadosOttoNtc> datosResulInspeccion  = todosLosDatos.getResultadosOtto(); 

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
                datoCda.getFechaResolucionAuthAmbiental()

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
                datoVehiculo.getDisMotor(),
                datoVehiculo.getTipComb(),
                datoVehiculo.getTipMotor()
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
                datoInspeccion.getFAborto(),
                datoInspeccion.getNomIt(),
                datoInspeccion.getTipIdeIt(),
                datoInspeccion.getNumIdeIt(),
                datoInspeccion.getLugarTemp(),
                datoInspeccion.getTAmb(),
                datoInspeccion.getHRel(),
                datoInspeccion.getCAborto()
            };
            modeloDatosPrueba.addRow(fila);
        });

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
        modeloResultadoPrueba.addColumn("NC_EMISIONES");//22
        modeloResultadoPrueba.addColumn("RES_FINAL");//23

        datosResulInspeccion.stream().forEach(resultado -> {
            Object[] fila = {
                resultado.getTMotor(),
                resultado.getRpmRal(),
                resultado.getRpmCru(),
                resultado.getHumo(),
                resultado.getDilucion(),
                resultado.getRpmFuera(),
                resultado.getFugaTubo(),
                resultado.getSalidasAd(),
                resultado.getFugaAceite(),
                resultado.getFugaComb(),
                resultado.getAccTubo(),
                resultado.getRHcRal(),
                resultado.getRHcCru(),
                resultado.getRCoRal(),
                resultado.getRCoCru(),
                resultado.getRCo2Ral(),
                resultado.getRCo2Cru(),
                resultado.getRO2Ral(),
                resultado.getRO2Cru(),
                resultado.getNcEmisiones(),
                resultado.getResFinal()
            };
            modeloResultadoPrueba.addRow(fila);
        });
        

        this.tblCda.setModel(modeloCDA);
        this.tblEquipoMedicion.setModel(modeloEquipoMedicion);
        this.tblInfoVehiculos.setModel(modeloInfoVehiculo);
        // this.tblMedidas.setModel(modeloMedidas);
        
        this.tblModeloPropietario.setModel(modeloPropietario);
        this.tblDatosPrueba.setModel(modeloDatosPrueba);
        this.tblResultadosPrueba.setModel(modeloResultadoPrueba);

        tblCda.setEnabled(false);
        tblInfoVehiculos.setEnabled(false);
        tblMedidas.setEnabled(false);
        tblEquipoMedicion.setEnabled(false);
        tblModeloPropietario.setEnabled(false);
        tblDatosPrueba.setEnabled(false);
        tblResultadosPrueba.setEnabled(false);
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
        tblMedidas = new javax.swing.JTable();
        tblCda = new javax.swing.JTable();
        tblEquipoMedicion = new javax.swing.JTable();
        tblModeloPropietario = new javax.swing.JTable();
        tblDatosPrueba = new javax.swing.JTable();
        tblResultadosPrueba = new javax.swing.JTable();

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
        tblEquipoMedicion.setName("Dtos del Propietario."); // NOI18N
        jScrollPane12.setViewportView(tblEquipoMedicion);
        infPrueba.addTab("Inf. Prop", jScrollPane12);

        //Tabla de Modelo d einformacion Vehiculo		
        tblInfoVehiculos.setModel(modeloInfoVehiculo);
        tblInfoVehiculos.setName("Información de Vehiculo"); // NOI18N
        jScrollPane1.setViewportView(tblInfoVehiculos);
        infPrueba.addTab("Inf. Vehiculo", jScrollPane1);

        //Tabla d eModelo Medidas
//        tblMedidas.setModel(modeloMedidas);
//        tblMedidas.setName("Información del Medidas"); // NOI18N		
//        jScrollPane10.setViewportView(tblMedidas);
//        infPrueba.addTab("Inf. Medidas", jScrollPane10);
        //Tabla de Equipos
        tblModeloPropietario.setModel(modeloPropietario);
        tblModeloPropietario.setName("Información de Equipos y Software"); // NOI18N		
        jScrollPane13.setViewportView(tblModeloPropietario);
        infPrueba.addTab("Inf. Equipos/Software", jScrollPane13);

        //Tabla datos generales inspeccion
        tblDatosPrueba.setModel(modeloDatosPrueba);
        tblDatosPrueba.setName("Datos Generales Inspeccion"); // NOI18N		
        jScrollPane14.setViewportView(tblDatosPrueba);
        infPrueba.addTab("Inf. Datos generales inspeccion", jScrollPane14);

        //Tabla 
        tblResultadosPrueba.setModel(modeloResultadoPrueba);
        tblResultadosPrueba.setName("Datos resolucion realizada"); // NOI18N		
        jScrollPane15.setViewportView(tblResultadosPrueba);
        infPrueba.addTab("Inf. Datos inspeccion realizada", jScrollPane15);

        /*
        tblCalibracion.setModel(modelEquipo);
	tblCalibracion.setName("Información de Calibracion"); // NOI18N		
	jScrollPane12.setViewportView(tblCalibracion);
	infPrueba.addTab("Inf. Calibracion", jScrollPane12);

         */
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
    private javax.swing.JTable tblCda;
    private javax.swing.JTable tblEquipoMedicion;
    private javax.swing.JTable tblMedidas;
    private javax.swing.JTable tblInfoVehiculos;
    private javax.swing.JTable tblModeloPropietario;
    private javax.swing.JTable tblDatosPrueba;
    private javax.swing.JTable tblResultadosPrueba;



}
