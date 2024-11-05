/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.igrafica;

import com.soltelec.servidor.conexion.PersistenceController;
import com.soltelec.servidor.consultas.Reportes;
import com.soltelec.servidor.dao.CalibracionDosPuntosJpaController;
import com.soltelec.servidor.dao.CdaJpaController;
import com.soltelec.servidor.dao.CertificadosJpaController;
import com.soltelec.servidor.dao.HojaPruebasJpaController;
import com.soltelec.servidor.dao.PruebasJpaController;
import com.soltelec.servidor.dao.SoftwareJpaController;
import com.soltelec.servidor.dtos.DatosCda;
import com.soltelec.servidor.dtos.reportes_ntcs.DatosGeneralesNtc;
import com.soltelec.servidor.dtos.reportes_ntcs.EquiposSoftDieselNtc;
import com.soltelec.servidor.dtos.reportes_ntcs.EquiposSoftOttoNtc;
import com.soltelec.servidor.dtos.reportes_ntcs.PropietariosNtc;
import com.soltelec.servidor.dtos.reportes_ntcs.ReporteNtc;
import com.soltelec.servidor.dtos.reportes_ntcs.ResultadosDieselNtc;
import com.soltelec.servidor.dtos.reportes_ntcs.ResultadosOttoNtc;
import com.soltelec.servidor.dtos.reportes_ntcs.VehiculoNtc;
import com.soltelec.servidor.model.CalibracionDosPuntos;
import com.soltelec.servidor.model.Calibraciones;
import com.soltelec.servidor.model.Cda;
import com.soltelec.servidor.model.Certificados;
import com.soltelec.servidor.model.Defxprueba;
import com.soltelec.servidor.model.DefxpruebaPK;
import com.soltelec.servidor.model.HojaPruebas;
import com.soltelec.servidor.model.Medidas;
import com.soltelec.servidor.model.Propietarios;
import com.soltelec.servidor.model.Pruebas;
import com.soltelec.servidor.model.Reinspeccion;
import com.soltelec.servidor.model.Software;
import com.soltelec.servidor.model.Vehiculos;
import com.soltelec.servidor.utils.GenericExportExcel;
import java.text.DateFormat;
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
import com.soltelec.servidor.model.Equipos;
import java.sql.Timestamp;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * todos los clientes de los cda's deben poder generar este reporte para ser
 * enviado por medio del software "vigia" a la superintendencia.
 *
 * @requerimiento SART-2 Creación de reporte de inspecciones
 * @author Gerencia Desarollo Tecnologica
 * @fecha 01/03/2018
 */
public class ReporteMedellin4231 extends javax.swing.JInternalFrame {
//public class ReporteSuperintendenciaVijia extends javax.swing.JFrame {

    private DefaultTableModel modeloCDA;
    private DefaultTableModel modeloInfoVehiculo;
    private DefaultTableModel modeloMedidas;
    private DefaultTableModel modeloDatosPropietarioVehiculo;
    private DefaultTableModel modeloDatosEquipoYSoftware;
    private DefaultTableModel modeloDatosGeneralesInspeccion;
    private DefaultTableModel modeloResolucionInspeccionRealizada;
    private Object[] cal2 = new Object[60];

    private static final Logger LOG = Logger.getLogger(ReporteCAR.class.getName());
    private SimpleDateFormat formatoFecha;
    private DateFormat formatoFechas;
    private SimpleDateFormat sdfH;

    public ReporteMedellin4231() {
        super("Reporte Medellin (4231)",
                true, //resizable
                true, //closable
                false, //maximizable
                true);
        initModels();
        initComponents();

    }

    private void initModels() {
        /*
        *
        *Informacion de CDA Reporte Medellin
        *
         */
        modeloCDA = new DefaultTableModel();
        modeloCDA.addColumn("CM");
        modeloCDA.addColumn("TIP_IDE_CDA");
        modeloCDA.addColumn("NUM_IDE_CDA");
        modeloCDA.addColumn("NOM_RS");
        modeloCDA.addColumn("NOM_CDA");
        modeloCDA.addColumn("CLASE_CDA");
        modeloCDA.addColumn("DIR_CDA");
        modeloCDA.addColumn("CORREO_E");
        modeloCDA.addColumn("TEL1_CDA");//9
        modeloCDA.addColumn("TEL2_CDA");//10
        modeloCDA.addColumn("MUN_CDA");//12
        modeloCDA.addColumn("RES_EC");//13
        modeloCDA.addColumn("F_RES_EC");//14
        modeloCDA.addColumn("TOTAL_DIS");//14

        modeloDatosPropietarioVehiculo = new DefaultTableModel();
        modeloDatosPropietarioVehiculo.addColumn("TIP_IDE_PROP");
        modeloDatosPropietarioVehiculo.addColumn("NUM_ID_PROP");
        modeloDatosPropietarioVehiculo.addColumn("NOM_PROP");
        modeloDatosPropietarioVehiculo.addColumn("DIR_PROP");
        modeloDatosPropietarioVehiculo.addColumn("TEL1_PROP");
        modeloDatosPropietarioVehiculo.addColumn("TEL2_PROP");
        modeloDatosPropietarioVehiculo.addColumn("MUN_PROP");
        modeloDatosPropietarioVehiculo.addColumn("CORR_E_PROP");


        /*
        *
        *Informacion Vehidculo
        *
         */
        modeloInfoVehiculo = new DefaultTableModel();
        modeloInfoVehiculo.addColumn("PLACA");//si
        modeloInfoVehiculo.addColumn("MODELO");
        modeloInfoVehiculo.addColumn("NUM_MOTOR");//si
        modeloInfoVehiculo.addColumn("VIN");//si
        modeloInfoVehiculo.addColumn("MOD_MOTOR");//si
        modeloInfoVehiculo.addColumn("DIA_ESCAPE");//si
        modeloInfoVehiculo.addColumn("CILINDRAJE");//si
        modeloInfoVehiculo.addColumn("LIC_TRANS");//si
        modeloInfoVehiculo.addColumn("KM");//si
        modeloInfoVehiculo.addColumn("MARCA");//si
        modeloInfoVehiculo.addColumn("LINEA");//si
        modeloInfoVehiculo.addColumn("CLASE");//si
        modeloInfoVehiculo.addColumn("SERVICIO");//si
        modeloInfoVehiculo.addColumn("TIP_COMB");
        modeloInfoVehiculo.addColumn("TIP_MOTOR");
        modeloInfoVehiculo.addColumn("RPM_FAB");//si
        modeloInfoVehiculo.addColumn("RAL_MIN_FAB");
        modeloInfoVehiculo.addColumn("RAL_MAX_FAB");
        modeloInfoVehiculo.addColumn("GOB_MIN_FAB");
        modeloInfoVehiculo.addColumn("GOB_MAX_FAB");
        //modeloInfoVehiculo.addColumn("DIS_MOTOR");

//         
/////////Equipos y software
        modeloDatosEquipoYSoftware = new DefaultTableModel();
        modeloDatosEquipoYSoftware.addColumn("MARCA_AG");//0
        modeloDatosEquipoYSoftware.addColumn("MOD_AG");//1
        modeloDatosEquipoYSoftware.addColumn("SERIAL_AG");//2
        modeloDatosEquipoYSoftware.addColumn("MARCA_BG");//3
        modeloDatosEquipoYSoftware.addColumn("MOD_BG");//4
        modeloDatosEquipoYSoftware.addColumn("SERIAL_BG");//5
        modeloDatosEquipoYSoftware.addColumn("LTOE");//6
        modeloDatosEquipoYSoftware.addColumn("SERIAL_E");//7
        modeloDatosEquipoYSoftware.addColumn("MARCA_RPM");//8
        modeloDatosEquipoYSoftware.addColumn("SERIAL_RPM");//9
        modeloDatosEquipoYSoftware.addColumn("MARCA_TEMP_M");//10
        modeloDatosEquipoYSoftware.addColumn("SERIAL_TEMP_M");//11
        modeloDatosEquipoYSoftware.addColumn("MARCA_TEMP_A");//12
        modeloDatosEquipoYSoftware.addColumn("SERIAL_TEMP_A");//13
        modeloDatosEquipoYSoftware.addColumn("MARCA_HUM_R");//14
        modeloDatosEquipoYSoftware.addColumn("SERIAL_HUM_R");//15
        modeloDatosEquipoYSoftware.addColumn("NOM_SOFT");//16
        modeloDatosEquipoYSoftware.addColumn("VER_SOFT");//17
        modeloDatosEquipoYSoftware.addColumn("DES_SOFT");//18
        modeloDatosEquipoYSoftware.addColumn("TIP_IDE_LIN");//19
        modeloDatosEquipoYSoftware.addColumn("NUM_IDE_LIN");//20
        modeloDatosEquipoYSoftware.addColumn("NOM_LIN");//21
        modeloDatosEquipoYSoftware.addColumn("FECHA_LIN");//22
        modeloDatosEquipoYSoftware.addColumn("P_ALTO_LAB");//23
        modeloDatosEquipoYSoftware.addColumn("P_ALTO_SERIAL");//24
        modeloDatosEquipoYSoftware.addColumn("P_ALTO_CER");//25
        modeloDatosEquipoYSoftware.addColumn("V_FDN_ALTO");//26
        modeloDatosEquipoYSoftware.addColumn("P_BAJO_LAB");//27
        modeloDatosEquipoYSoftware.addColumn("P_BAJO_SERIAL");//28
        modeloDatosEquipoYSoftware.addColumn("P_BAJO_CER");//29
        modeloDatosEquipoYSoftware.addColumn("V_FDN_BAJO");//30
        modeloDatosEquipoYSoftware.addColumn("R_FDN_CERO");//31
        modeloDatosEquipoYSoftware.addColumn("R_FDN_BAJO");//32
        modeloDatosEquipoYSoftware.addColumn("R_FDN_ALTO");//33
        modeloDatosEquipoYSoftware.addColumn("R_FDN_CIEN");//34
        modeloDatosEquipoYSoftware.addColumn("R_LIN");//35

        //Tabla datos generales de inspeccion
        modeloDatosGeneralesInspeccion = new DefaultTableModel();
        modeloDatosGeneralesInspeccion.addColumn("TIP_IDE_DT");
        modeloDatosGeneralesInspeccion.addColumn("NUM_IDE_DT");
        modeloDatosGeneralesInspeccion.addColumn("NOM_DT");
        modeloDatosGeneralesInspeccion.addColumn("TIP_IDE_IT");
        modeloDatosGeneralesInspeccion.addColumn("NUM_IDE_IT");
        modeloDatosGeneralesInspeccion.addColumn("NOM_IT");
        modeloDatosGeneralesInspeccion.addColumn("NUM_FUR");
        modeloDatosGeneralesInspeccion.addColumn("FECHA_FUR");
        modeloDatosGeneralesInspeccion.addColumn("CONS_RUNT");
        modeloDatosGeneralesInspeccion.addColumn("FUR_ASOC");
        modeloDatosGeneralesInspeccion.addColumn("CERT_RTMYG");
        modeloDatosGeneralesInspeccion.addColumn("F_INI_INSP");
        modeloDatosGeneralesInspeccion.addColumn("F_FIN_INSP");
        modeloDatosGeneralesInspeccion.addColumn("F_ABORTO");
        modeloDatosGeneralesInspeccion.addColumn("C_ABORTO");
        modeloDatosGeneralesInspeccion.addColumn("T_AMB");
        modeloDatosGeneralesInspeccion.addColumn("H_REL");

        //
        modeloResolucionInspeccionRealizada = new DefaultTableModel();//0
        modeloResolucionInspeccionRealizada.addColumn("T_INICIAL_MOTOR");//1
        modeloResolucionInspeccionRealizada.addColumn("T_FINAL_MOTOR");//2
        modeloResolucionInspeccionRealizada.addColumn("RPM_RAL");//3
        modeloResolucionInspeccionRealizada.addColumn("RPM_GOB");//4
        modeloResolucionInspeccionRealizada.addColumn("RPM_FUERA");//5
        modeloResolucionInspeccionRealizada.addColumn("FUGA_TUBO");//6
        modeloResolucionInspeccionRealizada.addColumn("SALIDAS_AD");//7
        modeloResolucionInspeccionRealizada.addColumn("FUGA_ACEITE");//8
        modeloResolucionInspeccionRealizada.addColumn("FUGA_COMB");//9
        modeloResolucionInspeccionRealizada.addColumn("ADMISION_NC");//10
        modeloResolucionInspeccionRealizada.addColumn("ACC_DISP");//11
        modeloResolucionInspeccionRealizada.addColumn("ACC_TUBO");//12
        modeloResolucionInspeccionRealizada.addColumn("REFRIG_NC");//13
        modeloResolucionInspeccionRealizada.addColumn("DIF_TEMP_10");//14
        modeloResolucionInspeccionRealizada.addColumn("GOB_NC");//15
        modeloResolucionInspeccionRealizada.addColumn("FUN_MOTOR");//16
        modeloResolucionInspeccionRealizada.addColumn("ACC_SUBITA");//17
        modeloResolucionInspeccionRealizada.addColumn("FALLA_SUBITA");//18
        modeloResolucionInspeccionRealizada.addColumn("DIF_ARITM");//19
        modeloResolucionInspeccionRealizada.addColumn("NC_EMISIONES");//20
        modeloResolucionInspeccionRealizada.addColumn("RPM_RAL_PRE");//21
        modeloResolucionInspeccionRealizada.addColumn("RPM_GOB_PRE");//22
        modeloResolucionInspeccionRealizada.addColumn("R_OP_PRE");//23
        modeloResolucionInspeccionRealizada.addColumn("R_DEN_PRE");//24
        modeloResolucionInspeccionRealizada.addColumn("RPM_RAL_C1");//25
        modeloResolucionInspeccionRealizada.addColumn("RPM_GOB_C1");//26
        modeloResolucionInspeccionRealizada.addColumn("R_OP_C1");//27
        modeloResolucionInspeccionRealizada.addColumn("R_DEN_C1");//28
        modeloResolucionInspeccionRealizada.addColumn("RPM_RAL_C2");//29
        modeloResolucionInspeccionRealizada.addColumn("RPM_GOB_C2");//30
        modeloResolucionInspeccionRealizada.addColumn("R_OP_C2");//31
        modeloResolucionInspeccionRealizada.addColumn("R_DEN_C2");//32
        modeloResolucionInspeccionRealizada.addColumn("RPM_RAL_C3");//33
        modeloResolucionInspeccionRealizada.addColumn("RPM_GOB_C3");//34
        modeloResolucionInspeccionRealizada.addColumn("R_OP_C3");//35
        modeloResolucionInspeccionRealizada.addColumn("R_DEN_C3");//36
        modeloResolucionInspeccionRealizada.addColumn("R_FINAL_OP");//37
        modeloResolucionInspeccionRealizada.addColumn("R_FINAL_DEN");//38
        modeloResolucionInspeccionRealizada.addColumn("RES_FINAL");//39

    }

    private void fillData(Date fechaInicial, Date fechaFinal){

        ReporteNtc todosLosDatos = Reportes.getNtc(fechaInicial, fechaFinal,2);

        List<DatosCda> datosCda = todosLosDatos.getCda();

        List<PropietariosNtc> datosPropietarios = todosLosDatos.getPropietarios();
        List<VehiculoNtc> datosVehiculo = todosLosDatos.getVehiculos(); 
        List<EquiposSoftDieselNtc> datosSoftwareEquipo = todosLosDatos.getEquiposDiesel(); 
        List<DatosGeneralesNtc> datosInspeccion = todosLosDatos.getGeneralesPrueba(); 
        List<ResultadosDieselNtc> datosResulInspeccion  = todosLosDatos.getResultadosDiesel(); 

        datosCda.stream().forEach(datoCda -> {
            Object[] fila = {
                datoCda.getCm(),
                datoCda.getTipoDocumento(),
                datoCda.getNIT(),
                datoCda.getNombreCda(),
                datoCda.getNombreCda(),
                datoCda.getClaseCda(),
                datoCda.getDireccion(),
                datoCda.getCorreoElectronico(),
                datoCda.getTelefono(),
                "",
                datoCda.getCiudad(),
                datoCda.getNoResolucionAuthAmbiental(),
                datoCda.getFechaResolucionAuthAmbiental(),
                datoCda.getNTotalOpacimetros()
            };
            modeloCDA.addRow(fila);
        });

        datosPropietarios.stream().forEach(datoPropietarios -> {
            Object[] fila = {
                datoPropietarios.getTipIdeProp(),
                datoPropietarios.getNumIdeProp(),
                datoPropietarios.getNomProp(),
                datoPropietarios.getDirProp(),
                datoPropietarios.getTel1Prop(),
                datoPropietarios.getTel2Prop(),
                datoPropietarios.getMunProp(),
                datoPropietarios.getCorrEProp()
            };
            modeloDatosPropietarioVehiculo.addRow(fila);
        });

        datosVehiculo.stream().forEach(datoVehiculo -> {
            Object[] fila = {
                datoVehiculo.getPlaca(),
                datoVehiculo.getModelo(),
                datoVehiculo.getNumMotor(),
                datoVehiculo.getVin(),
                datoVehiculo.getModMotor(),
                datoVehiculo.getDiaEscape(),
                datoVehiculo.getCilindraje(),
                datoVehiculo.getLicTrans(),
                datoVehiculo.getKm(),
                datoVehiculo.getMarca(),
                datoVehiculo.getLinea(),
                datoVehiculo.getClase(),
                datoVehiculo.getServicio(),
                datoVehiculo.getTipComb(),
                datoVehiculo.getTipMotor(),
                datoVehiculo.getRpmFab(),
                datoVehiculo.getRalMinFab(),
                datoVehiculo.getRalMaxFab(),
                datoVehiculo.getGobMinFab(),
                datoVehiculo.getGobMaxFab()
            };
            modeloInfoVehiculo.addRow(fila);
        });

        datosSoftwareEquipo.stream().forEach(softEquipo -> {
            Object[] fila = {
                softEquipo.getMarcaAg(),
                softEquipo.getModAg(),
                softEquipo.getSerialAg(),
                "",
                "",
                "",
                softEquipo.getLtoe(),
                softEquipo.getSerialE(),
                softEquipo.getMarcaRpm(),
                softEquipo.getSerialRpm(),
                softEquipo.getMarcaTempM(),
                softEquipo.getSerialTempM(),
                softEquipo.getMarcaTempA(),
                softEquipo.getSerialTempA(),
                softEquipo.getMarcaHumR(),
                softEquipo.getSerialHumR(),
                softEquipo.getNomSoft(),
                softEquipo.getVerSoft(),
                softEquipo.getDesSoft(),
                softEquipo.getTipIdeLin(),
                softEquipo.getNumIdeLin(),
                softEquipo.getNomLin(),
                softEquipo.getFechaLin(),
                softEquipo.getPAltoLab(),
                softEquipo.getPAltoSerial(),
                softEquipo.getPAltoCer(),
                softEquipo.getVFdnAlto(),
                softEquipo.getPBajoLab(),
                softEquipo.getPBajoSerial(),
                softEquipo.getPBajoCer(),
                softEquipo.getVFdnBajo(),
                softEquipo.getRFdnCero(),
                softEquipo.getRFdnBajo(),
                softEquipo.getRFdnAlto(),
                softEquipo.getRFndCien(),
                softEquipo.getRLin()
            };
            modeloDatosEquipoYSoftware.addRow(fila);
        });

        datosInspeccion.stream().forEach(datoInspeccion -> {
            Object[] fila = {
                datoInspeccion.getTipIdeDt(),
                datoInspeccion.getNumIdeDt(),
                datoInspeccion.getNomDt(),
                datoInspeccion.getTipIdeIt(),
                datoInspeccion.getNumIdeIt(),
                datoInspeccion.getNomIt(),
                datoInspeccion.getNumFur(),
                datoInspeccion.getFechaFur(),
                datoInspeccion.getConsRunt(),
                datoInspeccion.getFurAsoc(),
                datoInspeccion.getCertRtmyg(),
                datoInspeccion.getFIniInsp(),
                datoInspeccion.getFFinInsp(),
                datoInspeccion.getFAborto(),
                datoInspeccion.getCAborto(),
                datoInspeccion.getTAmb(),
                datoInspeccion.getHRel()
            };
            modeloDatosGeneralesInspeccion.addRow(fila);
        });

        datosResulInspeccion.stream().forEach(resultado -> {
            Object[] fila = {
                resultado.getTInicialMotor(),
                resultado.getTFinalMotor(),
                resultado.getRpmRal(),
                resultado.getRpmGob(),
                resultado.getRpmFuera(),
                resultado.getFugaTubo(),
                resultado.getSalidasAd(),
                resultado.getFugaAceite(),
                resultado.getFugaComb(),
                resultado.getAdmicionNc(),
                resultado.getAcDisp(),
                resultado.getAccTubo(),
                resultado.getRefrigNc(),
                resultado.getDifTemp10(),
                resultado.getGobNc(),
                resultado.getFunMotor(),
                resultado.getAccSubita(),
                resultado.getFallaSubita(),
                resultado.getDifAritm(),
                resultado.getNcEmisiones(),
                resultado.getRpmRalPre(),
                resultado.getRpmGobPre(),
                resultado.getROpPre(),
                resultado.getRDenPre(),
                resultado.getRpmRalC1(),
                resultado.getRpmGobC1(),
                resultado.getROpC1(),
                resultado.getRDenC1(),
                resultado.getRpmRalC2(),
                resultado.getRpmGobC2(),
                resultado.getROpC2(),
                resultado.getRDenC2(),
                resultado.getRpmRalC3(),
                resultado.getRpmGobC3(),
                resultado.getROpC3(),
                resultado.getRDenC3(),
                resultado.getRFinalOp(),
                resultado.getRFinalDen(),
                resultado.getResFinal()
            };
            modeloResolucionInspeccionRealizada.addRow(fila);
        });
        

        this.tblCda.setModel(modeloCDA);
        this.tblInfoVehiculos.setModel(modeloInfoVehiculo);
        // this.tblMedidas.setModel(modeloMedidas);
        this.tblPropietario.setModel(modeloDatosPropietarioVehiculo);
        this.tblEquiposYSoftware.setModel(modeloDatosEquipoYSoftware);
        this.tblDatosGeneralesInspeccion.setModel(modeloDatosGeneralesInspeccion);
        this.tblResolucionInspeccionRealizada.setModel(modeloResolucionInspeccionRealizada);

        tblCda.setEnabled(false);
        tblInfoVehiculos.setEnabled(false);
        tblMedidas.setEnabled(false);
        tblPropietario.setEnabled(false);
        tblEquiposYSoftware.setEnabled(false);
        tblDatosGeneralesInspeccion.setEnabled(false);
        tblResolucionInspeccionRealizada.setEnabled(false);
    }

    private void fillDataa(Date fechaInicial, Date fechaFinal) {
        initModels();
        DateFormat formatoFechas = new SimpleDateFormat("yyyy-MM-dd");
        formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        sdfH = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
        HojaPruebasJpaController hpjc = new HojaPruebasJpaController();
        List<Pruebas> lstPruebas = hpjc.findByDatePruebas(fechaInicial, fechaFinal);
        //Tamaño de datos 
        int lng = lstPruebas.size();
        System.out.println("Total datos recogidos: " + lng);

        /*
        *Mensaje Dialog que no se encontro Datos en la Base de Datos
         */
        if (lstPruebas == null || lstPruebas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La consulta no genero datos");
            return;
        }

        int n = 0;
        String form;
        int work = 0;
        for (Pruebas pruebasebas : lstPruebas) {

            if (pruebasebas.getHojaPruebas().getReinspeccionList2().size() > 0) {
                Reinspeccion r = pruebasebas.getHojaPruebas().getReinspeccionList2().iterator().next();
                List<Pruebas> lstPrueba = r.getPruebaList();
                boolean encontre = false;
                for (Pruebas pruebas : lstPrueba) {
                    if (pruebas.getIdPruebas() == pruebasebas.getIdPruebas()) {
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
            if (pruebasebas.getFinalizada().equalsIgnoreCase("Y") && pruebasebas.getHojaPruebas().getPreventiva().equals("N")) {
                work = work + 1;
                System.out.println("proc..!  " + work + " de " + lng);
                if (work > lng) {
                    JOptionPane.showMessageDialog(null, "proc..!  " + work + " de " + lng);
                }
                if (pruebasebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype() == 3) {

                    cargarInformacionCda(pruebasebas);
                    cargarEquipos(pruebasebas);
                    cargarInformacionPropietarios(pruebasebas);
                    cargarInformacionvehiculo(pruebasebas, form);
                    cargarDatosGeneralesInspeccion(pruebasebas);
                    cargarResultadosInspeccion(pruebasebas);

                    cargarInformacionMedidas(pruebasebas, n);
                    this.tblCda.setModel(modeloCDA);
                    this.tblInfoVehiculos.setModel(modeloInfoVehiculo);
                    // this.tblMedidas.setModel(modeloMedidas);
                    this.tblPropietario.setModel(modeloDatosPropietarioVehiculo);
                    this.tblEquiposYSoftware.setModel(modeloDatosEquipoYSoftware);
                    this.tblDatosGeneralesInspeccion.setModel(modeloDatosGeneralesInspeccion);
                    this.tblResolucionInspeccionRealizada.setModel(modeloResolucionInspeccionRealizada);

                    tblCda.setEnabled(false);
                    tblInfoVehiculos.setEnabled(false);
                    tblMedidas.setEnabled(false);
                    tblPropietario.setEnabled(false);
                    tblEquiposYSoftware.setEnabled(false);
                    tblDatosGeneralesInspeccion.setEnabled(false);
                    tblResolucionInspeccionRealizada.setEnabled(false);

                } else {
                    // JOptionPane.showMessageDialog(this, "esto es Diesel");
                }

            }
        }

    }//end filldata

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
        jScrollPane10 = new javax.swing.JScrollPane();
        jScrollPane11 = new javax.swing.JScrollPane();
        jScrollPane12 = new javax.swing.JScrollPane();
        jScrollPane13 = new javax.swing.JScrollPane();
        jScrollPane14 = new javax.swing.JScrollPane();
        jScrollPane15 = new javax.swing.JScrollPane();
        tblMedidas = new javax.swing.JTable();
        tblCda = new javax.swing.JTable();
        tblPropietario = new javax.swing.JTable();
        tblEquiposYSoftware = new javax.swing.JTable();
        tblDatosGeneralesInspeccion = new javax.swing.JTable();
        tblResolucionInspeccionRealizada = new javax.swing.JTable();

        /*
        *
        * Llamar los objetos de swing estan cerrados para otras tablas
        *
         */
        //jScrollPane7 = new javax.swing.JScrollPane();
        //jScrollPane12 = new javax.swing.JScrollPane();
        //tblPruebaGasesOtto = new javax.swing.JTable();
        //jScrollPane8 = new javax.swing.JScrollPane();
        //tblPruebaGasesDiesel = new javax.swing.JTable();        
        //tblCalibracion= new javax.swing.JTable();
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
        *   Crear los nombres de la tablas y agregarlos al menu de Reportes
        *
         */
        //Tabla de Modelo CDA	
        tblCda.setModel(modeloCDA);
        tblCda.setName("Información del Cda."); // NOI18N
        jScrollPane11.setViewportView(tblCda);
        infPrueba.addTab("Inf. Cda", jScrollPane11);

        tblPropietario.setModel(modeloDatosPropietarioVehiculo);
        tblPropietario.setName("Dtos del Propietario."); // NOI18N
        jScrollPane12.setViewportView(tblPropietario);
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
        tblEquiposYSoftware.setModel(modeloDatosEquipoYSoftware);
        tblEquiposYSoftware.setName("Información de Equipos y Software"); // NOI18N		
        jScrollPane13.setViewportView(tblEquiposYSoftware);
        infPrueba.addTab("Inf. Equipos/Software", jScrollPane13);

        //Tabla datos generales inspeccion
        tblDatosGeneralesInspeccion.setModel(modeloDatosGeneralesInspeccion);
        tblDatosGeneralesInspeccion.setName("Datos Generales Inspeccion"); // NOI18N		
        jScrollPane14.setViewportView(tblDatosGeneralesInspeccion);
        infPrueba.addTab("Inf. Datos generales inspeccion", jScrollPane14);

        //Tabla 
        tblResolucionInspeccionRealizada.setModel(modeloResolucionInspeccionRealizada);
        tblResolucionInspeccionRealizada.setName("Datos resolucion realizada"); // NOI18N		
        jScrollPane15.setViewportView(tblResolucionInspeccionRealizada);
        infPrueba.addTab("Inf. Datos inspeccion realizada", jScrollPane15);

        /*
        tblCalibracion.setModel(modelEquipo);
	tblCalibracion.setName("Información de Calibracion"); // NOI18N		
	jScrollPane12.setViewportView(tblCalibracion);
	infPrueba.addTab("Inf. Calibracion", jScrollPane12);

         */
 /*
*
*   Agregar los componetes de  nombres fechas y btn de generar al layout sea visible    
    
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
        ReporteMedellin4231 rsv = new ReporteMedellin4231();
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
        tables.add(tblPropietario);
        tables.add(tblInfoVehiculos);
        tables.add(tblMedidas);
        tables.add(tblEquiposYSoftware);
        tables.add(tblDatosGeneralesInspeccion);
        tables.add(tblResolucionInspeccionRealizada);
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
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JTable tblCda;
    private javax.swing.JTable tblPropietario;
    private javax.swing.JTable tblMedidas;
    private javax.swing.JTable tblInfoVehiculos;
    private javax.swing.JTable tblEquiposYSoftware;
    private javax.swing.JTable tblDatosGeneralesInspeccion;
    private javax.swing.JTable tblResolucionInspeccionRealizada;

    private void cargarInformacionCda(Pruebas pruebasebas) {
        formatoFecha = new SimpleDateFormat("dd/MM/YYYY hh:mm:ss");
        CdaJpaController cda = new CdaJpaController();
        Cda mcda = cda.findCda(1);
        Object[] objCda = new Object[17];

        objCda[0] = mcda.getCM();

        if (mcda.getNit() != null) {
            objCda[1] = "N";
        }

        objCda[2] = mcda.getNit();
        objCda[3] = mcda.getRazon_Social();
        objCda[4] = mcda.getNombre();
        objCda[5] = mcda.getClase();
        objCda[6] = mcda.getDireccion();
        objCda[7] = mcda.getCorreo();
        objCda[8] = mcda.getTelefono();
        objCda[9] = mcda.getCelular();
        objCda[10] = mcda.getCiudad();
        objCda[11] = mcda.getResolucion_Ambiental();
        formatoFechas = new SimpleDateFormat("dd/MM/YYYY hh:mm:ss");
        objCda[12] = mcda.getFecha_Resolucion_Ambiental();
        objCda[13] = mcda.getTotal_Eq_Diesel();
        modeloCDA.addRow(objCda);
    }

    private void cargarInformacionPropietarios(Pruebas pruebas) {
        formatoFecha = new SimpleDateFormat("dd/MM/YYYY hh:mm:ss");
        Object[] objProp = new Object[9];

        if (pruebas.getHojaPruebas().getPropietarios().getTipoIdentificacion().toString().equals("CC")) {
            objProp[0] = "C";
        } else if (pruebas.getHojaPruebas().getPropietarios().getTipoIdentificacion().toString().equals("CE")) {
            objProp[0] = "E";
        } else if (pruebas.getHojaPruebas().getPropietarios().getTipoIdentificacion().toString().equals("NIT")) {
            objProp[0] = "N";
        } else if (pruebas.getHojaPruebas().getPropietarios().getTipoIdentificacion().toString().equals("T")) {
            objProp[0] = "T";
        } else if (pruebas.getHojaPruebas().getPropietarios().getTipoIdentificacion().toString().equals("U")) {
            objProp[0] = "U";
        }
        objProp[1] = pruebas.getHojaPruebas().getPropietarios().getCarowner();
        objProp[2] = pruebas.getHojaPruebas().getPropietarios().getNombres() + " " + pruebas.getHojaPruebas().getPropietarios().getApellidos();
        objProp[3] = pruebas.getHojaPruebas().getPropietarios().getDireccion();
        objProp[4] = pruebas.getHojaPruebas().getPropietarios().getNumerotelefono();
        if (pruebas.getHojaPruebas().getPropietarios().getCelular() == null) {
            objProp[5] = "No Posee";
        } else {
            objProp[5] = pruebas.getHojaPruebas().getPropietarios().getNumerotelefono();
        }
        objProp[6] = pruebas.getHojaPruebas().getPropietarios().getCiudadades();
        objProp[7] = pruebas.getHojaPruebas().getPropietarios().getEmail();

        modeloDatosPropietarioVehiculo.addRow(objProp);
    }

    private void cargarInformacionvehiculo(Pruebas pruebasebas, String strIntento) {

        Object[] objInfoVehiculo = new Object[25];
        objInfoVehiculo[0] = pruebasebas.getHojaPruebas().getVehiculos().getCarplate();
        objInfoVehiculo[1] = pruebasebas.getHojaPruebas().getVehiculos().getModelo();
        objInfoVehiculo[2] = pruebasebas.getHojaPruebas().getVehiculos().getNumeromotor();
        objInfoVehiculo[3] = pruebasebas.getHojaPruebas().getVehiculos().getVin();
        objInfoVehiculo[4] = "NO";
        objInfoVehiculo[5] = pruebasebas.getHojaPruebas().getVehiculos().getDiametro();
        objInfoVehiculo[6] = pruebasebas.getHojaPruebas().getVehiculos().getCilindraje();
        objInfoVehiculo[7] = pruebasebas.getHojaPruebas().getVehiculos().getNumerolicencia();
        if (pruebasebas.getHojaPruebas().getVehiculos().getKilometraje().equals(0)) {
            objInfoVehiculo[8] = "NO FUNCIONAL";
        } else {
            objInfoVehiculo[8] = pruebasebas.getHojaPruebas().getVehiculos().getKilometraje();
        }

        objInfoVehiculo[9] = pruebasebas.getHojaPruebas().getVehiculos().getMarcas().getNombremarca();
        objInfoVehiculo[10] = pruebasebas.getHojaPruebas().getVehiculos().getLineasVehiculos().getCrlname();
        objInfoVehiculo[11] = pruebasebas.getHojaPruebas().getVehiculos().getClasesVehiculo().getNombreclase();
        System.out.println(pruebasebas.getHojaPruebas().getVehiculos().getClasesVehiculo().getNombreclase());
        objInfoVehiculo[12] = pruebasebas.getHojaPruebas().getVehiculos().getServicios().getNombreservicio();
        objInfoVehiculo[13] = pruebasebas.getHojaPruebas().getVehiculos().getTiposGasolina().getNombregasolina();
        objInfoVehiculo[14] = "DIESEL";
        if (pruebasebas.getHojaPruebas().getVehiculos().getLineasVehiculos().getRpm_ini() != null && pruebasebas.getHojaPruebas().getVehiculos().getLineasVehiculos().getRpm_fin() != null && pruebasebas.getHojaPruebas().getVehiculos().getLineasVehiculos().getRpm_ini() != "0" && pruebasebas.getHojaPruebas().getVehiculos().getLineasVehiculos().getRpm_fin() != "0") {
            objInfoVehiculo[15] = "SI";
            objInfoVehiculo[16] = pruebasebas.getHojaPruebas().getVehiculos().getLineasVehiculos().getRpm_ini();
            objInfoVehiculo[17] = pruebasebas.getHojaPruebas().getVehiculos().getLineasVehiculos().getRpm_fin();
            objInfoVehiculo[18] = pruebasebas.getHojaPruebas().getVehiculos().getLineasVehiculos().getRpm_ini();
            objInfoVehiculo[19] = pruebasebas.getHojaPruebas().getVehiculos().getLineasVehiculos().getRpm_fin();
        } else {
            objInfoVehiculo[15] = "NO";
            objInfoVehiculo[16] = "";
            objInfoVehiculo[17] = "";
            objInfoVehiculo[18] = "";
            objInfoVehiculo[19] = "";
        }

        modeloInfoVehiculo.addRow(objInfoVehiculo);
    }

    private void cargarInformacionMedidas(Pruebas pruebasebas, int nroIntento) {
        sdfH = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
        Object[] dataRowMedellin = new Object[19];
        boolean tieneRegistro = false;
        if (pruebasebas.getTipoPrueba().getTesttype() == 8 && !pruebasebas.getFinalizada().equalsIgnoreCase("A")) {
            if (pruebasebas.getHojaPruebas().getTestsheet() == 15300) {
                System.out.println("ENTRE CONDICIONAL ");
                if (pruebasebas.getIdPruebas() == 125033) {

                }
            }
            System.out.println("id es  " + pruebasebas.getIdPruebas());
            System.out.println("fecha  " + sdfH.format(pruebasebas.getFechaPrueba()));
            tieneRegistro = true;

            for (Medidas medidas : pruebasebas.getMedidasList()) {

                switch (medidas.getTiposMedida().getMeasuretype()) {
                    /*
                    *
                    *   Ralenty
                    *
                     */

                    case 8002:
                        dataRowMedellin[0] = medidas.getValormedida();      //Monoxido de Carbono Ralenty (COR)
                        break;
                    case 8020:
                        dataRowMedellin[0] = medidas.getValormedida();      //Monoxido de Carbono 2T Ralenty (COR2T)
                        break;
                    case 8003:
                        dataRowMedellin[1] = medidas.getValormedida();      //Dioxido de Carbono Ralenty (CO2R)
                        break;
                    case 8019:
                        dataRowMedellin[1] = medidas.getValormedida();      //Dioxido de Carbono 2 Tiempos Ralenty (CO2R2T
                        break;
                    case 8001:
                        dataRowMedellin[2] = medidas.getValormedida();      //HidroCarburos Ralenty (HC)
                        break;
                    case 8018:
                        dataRowMedellin[2] = medidas.getValormedida();      // HidroCarburos vehiculo 2T Ralenty (HC)
                        break;
                    case 8004:
                        dataRowMedellin[3] = medidas.getValormedida();      //Oxigeno Ralenty (O2)
                        break;
                    case 8021:
                        dataRowMedellin[3] = medidas.getValormedida();      // Oxigeno Ralenty 2 Tiempos (O2R2T)
                        break;
                    case 8006:
                        dataRowMedellin[5] = medidas.getValormedida();      ///Temperatura Ralenty 
                        break;
                    case 8022:
                        dataRowMedellin[5] = medidas.getValormedida();      // Temperatura Ralenty 2 Tiempos 
                        break;
                    case 8005:
                        dataRowMedellin[6] = medidas.getValormedida();      //Revoluciones por minuto Ralenty 
                        break;
                    case 8028:
                        dataRowMedellin[6] = medidas.getValormedida();      //Revoluciones por minuto 2 Tiempos Ralenty 
                        break;
                    /*
                    *
                    *  Crucero
                    *   
                     */
                    case 8008:
                        dataRowMedellin[7] = medidas.getValormedida();      //Monoxido de Carbono Crucero (COC)
                        break;
                    case 8024:
                        dataRowMedellin[7] = medidas.getValormedida();      //Monoxido de Carbono Crucero (COC2T)
                        break;
                    case 8009:
                        dataRowMedellin[8] = medidas.getValormedida();      //Dioxido de Carbono Crucero (CO2C)
                        break;
                    case 8025:
                        dataRowMedellin[8] = medidas.getValormedida();      //Dioxido de Carbono Crucero (COC2T)
                        break;
                    case 8007:
                        dataRowMedellin[9] = medidas.getValormedida();      //HidroCarburos Crucero (HCC)
                        break;
                    case 8023:
                        dataRowMedellin[9] = medidas.getValormedida();      //Hidrocarburos Crucero 2 Tiempos (COC2T)
                        break;
                    case 8010:
                        dataRowMedellin[10] = medidas.getValormedida();     //Oxigeno Crucero (O2C)
                        break;
                    case 8026:
                        dataRowMedellin[10] = medidas.getValormedida();      //Oxigeno Crucero 2 Tiempos (OC2T)
                        break;
                    case 8012:
                        dataRowMedellin[12] = medidas.getValormedida();       //Temperatura Crucero
                        break;
                    case 8027:
                        dataRowMedellin[12] = medidas.getValormedida();      // Temperatura Crucero 2 Tiempos 
                        break;
                    case 8011:
                        dataRowMedellin[13] = medidas.getValormedida();      //Revoluciones por minutoCrucer0 
                        break;
                    case 8029:
                        dataRowMedellin[13] = medidas.getValormedida();      //Revoluciones por minuto 2 Tiempos Crucer0 
                        break;
                    /*
                        *
                        * Opacidad, Ruido Causa
                        *
                     */

                    case 8031:// Temperatura  Ambiente                      
                        dataRowMedellin[14] = medidas.getValormedida();
                        break;
                    case 8032://Humedad Ambiental  
                        dataRowMedellin[15] = medidas.getValormedida();
                        break;
                    case 8030://Opacidad
                        dataRowMedellin[16] = medidas.getValormedida();
                        break;
                    default:

                        dataRowMedellin[4] = "N/A";      // Odixo Nitronego ralenty(NOx)
                        dataRowMedellin[7] = "N/A";
                        dataRowMedellin[8] = "N/A";
                        dataRowMedellin[9] = "N/A";
                        dataRowMedellin[10] = "N/A";
                        dataRowMedellin[11] = "N/A";
                        dataRowMedellin[12] = "N/A";
                        dataRowMedellin[13] = "N/A";
                        dataRowMedellin[14] = "N/A";
                        dataRowMedellin[15] = "N/A";
                        dataRowMedellin[16] = "N/A";

                        break;
                }
            }

            /*
            *
            * Obtener Resultado de Ruido
            *
             */
            dataRowMedellin[17] = obtenerRuidoScape(pruebasebas.getHojaPruebas());
            /*
            *
            *Enla Columan de Causas condiciones Anormales
            *
             */
            if (pruebasebas.getTipoPrueba().getTesttype() == 8) {
                //Si los campos no estan nullos en comentarios de aborte ejecutar
                if (pruebasebas.getComentarioAborto() != null) {
                    //Cuando es igual condiciones anormales  y si existe condicionales
                    if (pruebasebas.getComentarioAborto().equalsIgnoreCase("Condiciones Anormales")) {
                        // si no esta nullo la condicion anormal ejecuta un detallle
                        if (pruebasebas.getObservaciones() != null) {
                            dataRowMedellin[18] = pruebasebas.getComentarioAborto().concat(" detalle: ").concat(pruebasebas.getObservaciones());
                            // si no entonces dice N/A
                        } else {
                            dataRowMedellin[18] = pruebasebas.getComentarioAborto();
                        }
                    }//si exitenCondiciones
                } else {//si diferenteNull 
                    //dataRowMedellin[16] = "N/A";  //Esto si es nulo dice N/A
                }
            }// Cierre si es de Gases
            //modeloMedidas.addRow(dataRowMedellin);
            if (!tieneRegistro) {
                // modeloMedidas.addRow(dataRowMedellin);
            }
        }
    }// Cierre de Metodo de Cargar Informacion Medidas

    private void cargarDatosGeneralesInspeccion(Pruebas prueba) {
        CertificadosJpaController cert = new CertificadosJpaController();
        Certificados certificado = cert.findCertificadoHojaPrueba(prueba.getHojaPruebas().getTestsheet());

        Object[] objProp = new Object[55];
        objProp[0] = "C";
        objProp[1] = prueba.getHojaPruebas().getUsuario_resp().getCedula();
        objProp[2] = prueba.getHojaPruebas().getUsuario_resp().getNombreusuario();
        objProp[3] = "C";
        objProp[4] = prueba.getUsuarios().getCedula();
        objProp[5] = prueba.getUsuarios().getNombreusuario();
        objProp[6] = prueba.getHojaPruebas().getConHojaPrueba();
        objProp[7] = sdfH.format(prueba.getHojaPruebas().getFechaingresovehiculo());
        if (prueba.getAprobada().equals("N") || prueba.getHojaPruebas().getAprobado().equals("N")) {
            objProp[8] = "R" + prueba.getHojaPruebas().getConsecutivoRunt();
        } else {
            objProp[8] = "A" + prueba.getHojaPruebas().getConsecutivoRunt();
        }
        objProp[9] = prueba.getHojaPruebas().getConHojaPrueba();

        if (prueba.getAprobada().equals("N") || prueba.getHojaPruebas().getAprobado().equals("N")) {
            objProp[10] = "";
        } else {
            objProp[10] = certificado.getConsecutive();
        }
        objProp[11] = "---";
        objProp[12] = "---";
        if (prueba.getTipoPrueba().getTesttype() == 8) {
            objProp[11] = sdfH.format(prueba.getFechaPrueba());
            objProp[12] = sdfH.format(prueba.getFechaFinal());
        }

        objProp[13] = prueba.getFechaAborto();
        objProp[14] = prueba.getComentarioAborto();

        for (Medidas medidas : prueba.getMedidasList()) {
            if (medidas.getTiposMedida().getMeasuretype() == 8031) {
                objProp[15] = medidas.getValormedida();
            } else if (medidas.getTiposMedida().getMeasuretype() == 8032) {
                objProp[16] = medidas.getValormedida();
            }
        }
        modeloDatosGeneralesInspeccion.addRow(objProp);

    }

    private void cargarResultadosInspeccion(Pruebas pruebasebas) {
        Equipos eq = new Equipos();
        Object[] objProp = new Object[55];
        String datos = "";

        /* modeloResolucionInspeccionRealizada = new DefaultTableModel();//0
        modeloResolucionInspeccionRealizada.addColumn("T_INICIAL_MOTOR");//1
        modeloResolucionInspeccionRealizada.addColumn("T_FINAL_MOTOR");//2
        modeloResolucionInspeccionRealizada.addColumn("RPM_RAL");//3
        modeloResolucionInspeccionRealizada.addColumn("RPM_GOB");//4
        modeloResolucionInspeccionRealizada.addColumn("RPM_FUERA");//5
        modeloResolucionInspeccionRealizada.addColumn("FUGA_TUBO");//6
        modeloResolucionInspeccionRealizada.addColumn("SALIDAS_AD");//7
        modeloResolucionInspeccionRealizada.addColumn("FUGA_ACEITE");//8
        modeloResolucionInspeccionRealizada.addColumn("FUGA_COMB");//9
        modeloResolucionInspeccionRealizada.addColumn("ADMISION_NC");//10
        modeloResolucionInspeccionRealizada.addColumn("ACC_DISP");//11
        modeloResolucionInspeccionRealizada.addColumn("ACC_TUBO");//12
        modeloResolucionInspeccionRealizada.addColumn("REFRIG_NC");//13
        modeloResolucionInspeccionRealizada.addColumn("DIF_TEMP_10");//14
        modeloResolucionInspeccionRealizada.addColumn("GOB_NC");//15
        modeloResolucionInspeccionRealizada.addColumn("FUN_MOTOR");//16
        modeloResolucionInspeccionRealizada.addColumn("ACC_SUBITA");//17
        modeloResolucionInspeccionRealizada.addColumn("FALLA_SUBITA");//18
        modeloResolucionInspeccionRealizada.addColumn("DIF_ARITM");//19
        modeloResolucionInspeccionRealizada.addColumn("NC_EMISIONES");//20
        modeloResolucionInspeccionRealizada.addColumn("RPM_RAL_PRE");//21
        modeloResolucionInspeccionRealizada.addColumn("RPM_GOB_PRE");//22
        modeloResolucionInspeccionRealizada.addColumn("R_OP_PRE");//23
        modeloResolucionInspeccionRealizada.addColumn("R_DEN_PRE");//24
        modeloResolucionInspeccionRealizada.addColumn("RPM_RAL_C1");//25
        modeloResolucionInspeccionRealizada.addColumn("RPM_GOB_C1");//26
        modeloResolucionInspeccionRealizada.addColumn("R_OP_C1");//27
        modeloResolucionInspeccionRealizada.addColumn("R_DEN_C1");//28
        modeloResolucionInspeccionRealizada.addColumn("RPM_RAL_C2");//29
        modeloResolucionInspeccionRealizada.addColumn("RPM_GOB_C2");//30
        modeloResolucionInspeccionRealizada.addColumn("R_OP_C2");//31
        modeloResolucionInspeccionRealizada.addColumn("R_DEN_C2");//32
        modeloResolucionInspeccionRealizada.addColumn("RPM_RAL_C3");//33
        modeloResolucionInspeccionRealizada.addColumn("RPM_GOB_C3");//34
        modeloResolucionInspeccionRealizada.addColumn("R_OP_C3");//35
        modeloResolucionInspeccionRealizada.addColumn("R_DEN_C3");//36
        modeloResolucionInspeccionRealizada.addColumn("R_FINAL_OP");//37
        modeloResolucionInspeccionRealizada.addColumn("R_FINAL_DEN");//38
        modeloResolucionInspeccionRealizada.addColumn("RES_FINAL");//39 */

        for (Medidas medidas : pruebasebas.getMedidasList()) {

            if (medidas.getTiposMedida().getMeasuretype() == 8034) {
                objProp[0] = medidas.getValormedida();//TEMP INCIAL
            }
            if (medidas.getTiposMedida().getMeasuretype() == 8037) {
                objProp[1] = medidas.getValormedida();//TEMP FINAL
            }
            if (medidas.getTiposMedida().getMeasuretype() == 8035) {
                //objProp[2] = medidas.getValormedida();//RPM RAL
                float v = medidas.getValormedida();
                String v2 = String.format("%.0f", v);
                objProp[2] = v2;
            }
            if (medidas.getTiposMedida().getMeasuretype() == 8036) {
                //objProp[3] = medidas.getValormedida();//RPM GOB
                float v = medidas.getValormedida();
                String v2 = String.format("%.0f", v);
                objProp[3] = v2;
            }

            if (medidas.getTiposMedida().getMeasuretype() == 8035) {
                //objProp[20] = medidas.getValormedida();//RPM RAL PR
                float v = medidas.getValormedida();
                String v2 = String.format("%.0f", v);
                objProp[20] = v2;
            }
            if (medidas.getTiposMedida().getMeasuretype() == 8038) {
                //objProp[21] = medidas.getValormedida();//RPM GOB PR
                float v = medidas.getValormedida();
                String v2 = String.format("%.0f", v);
                objProp[21] = v2;
            }
            if (medidas.getTiposMedida().getMeasuretype() == 8033) {
                //objProp[22] = medidas.getValormedida();//R OP PRE
                double v = calcularN(medidas.getValormedida());
                String v2 = String.format("%.2f", v);
                v2 = v2.replace(",", ".");
                objProp[22] = AproximarMedidas(Double.parseDouble(v2));
            }
            if (medidas.getTiposMedida().getMeasuretype() == 8033) {
                //objProp[23] = calcularOpacidad(medidas.getValormedida());//R DEN PRE
                float v = medidas.getValormedida();
                String v2 = String.format("%.2f", v);
                v2 = v2.replace(",", ".");
                objProp[23] = AproximarMedidas(Double.parseDouble(v2));
            }
            if (medidas.getTiposMedida().getMeasuretype() == 8035) {
                //objProp[24] = medidas.getValormedida();//RPM CAL C1
                float v = medidas.getValormedida();
                String v2 = String.format("%.0f", v);
                objProp[24] = v2;
            }
            if (medidas.getTiposMedida().getMeasuretype() == 8039) {
                //objProp[25] = medidas.getValormedida();//RPM GOB C1
                float v = medidas.getValormedida();
                String v2 = String.format("%.0f", v);
                objProp[25] = v2;
            }
            if (medidas.getTiposMedida().getMeasuretype() == 8013) {
                //objProp[26] = medidas.getValormedida();//R OP C1
                double v = calcularN(medidas.getValormedida());
                String v2 = String.format("%.2f", v);
                v2 = v2.replace(",", ".");
                objProp[26] = AproximarMedidas(Double.parseDouble(v2));
            }
            if (medidas.getTiposMedida().getMeasuretype() == 8013) {
                //objProp[27] = calcularOpacidad(medidas.getValormedida());//R DEN C1
                float v = medidas.getValormedida();
                String v2 = String.format("%.2f", v);
                v2 = v2.replace(",", ".");
                objProp[27] = AproximarMedidas(Double.parseDouble(v2));
            }
            if (medidas.getTiposMedida().getMeasuretype() == 8035) {
                //objProp[28] = medidas.getValormedida();//RPM RAL C2
                float v = medidas.getValormedida();
                String v2 = String.format("%.0f", v);
                objProp[28] = v2;
            }
            if (medidas.getTiposMedida().getMeasuretype() == 8040) {
                //objProp[29] = medidas.getValormedida();//RPM GOB C2
                float v = medidas.getValormedida();
                String v2 = String.format("%.0f", v);
                objProp[29] = v2;
            }
            if (medidas.getTiposMedida().getMeasuretype() == 8014) {
                // objProp[30] = medidas.getValormedida();//R OP C2
                double v = calcularN(medidas.getValormedida());
                String v2 = String.format("%.2f", v);
                v2 = v2.replace(",", ".");
                objProp[30] = AproximarMedidas(Double.parseDouble(v2));
            }
            if (medidas.getTiposMedida().getMeasuretype() == 8014) {
                //objProp[31] = calcularOpacidad(medidas.getValormedida());//R DEN C2
                float v = medidas.getValormedida();
                String v2 = String.format("%.2f", v);
                v2 = v2.replace(",", ".");
                objProp[31] = AproximarMedidas(Double.parseDouble(v2));
            }
            if (medidas.getTiposMedida().getMeasuretype() == 8035) {
                //objProp[32] = medidas.getValormedida();//RPM RAL C2
                float v = medidas.getValormedida();
                String v2 = String.format("%.0f", v);
                objProp[32] = v2;
            } else if (medidas.getTiposMedida().getMeasuretype() == 8041) {
                //objProp[33] = medidas.getValormedida();//RPM GOB C3
                float v = medidas.getValormedida();
                String v2 = String.format("%.0f", v);
                objProp[33] = v2;
            }
            if (medidas.getTiposMedida().getMeasuretype() == 8015) {
                //objProp[34] = medidas.getValormedida();//R OP C3
                double v = calcularN(medidas.getValormedida());
                String v2 = String.format("%.2f", v);
                v2 = v2.replace(",", ".");
                objProp[34] = AproximarMedidas(Double.parseDouble(v2));
            }
            if (medidas.getTiposMedida().getMeasuretype() == 8015) {
                //objProp[35] = calcularOpacidad(medidas.getValormedida());//R DEN C3
                float v = medidas.getValormedida();
                String v2 = String.format("%.2f", v);
                v2 = v2.replace(",", ".");
                objProp[35] = AproximarMedidas(Double.parseDouble(v2));
            }
            if (medidas.getTiposMedida().getMeasuretype() == 8017) {
                //objProp[36] = medidas.getValormedida();//R FINAL OP
                double v = calcularN(medidas.getValormedida());
                String v2 = String.format("%.2f", v);
                v2 = v2.replace(",", ".");
                objProp[36] = AproximarMedidas(Double.parseDouble(v2));
            }
            if (medidas.getTiposMedida().getMeasuretype() == 8017) {
                //objProp[37] = calcularOpacidad(medidas.getValormedida());//R FINAL D
                float v = medidas.getValormedida();
                String v2 = String.format("%.2f", v);
                v2 = v2.replace(",", ".");
                objProp[37] = AproximarMedidas(Double.parseDouble(v2));
            }
            System.out.println("");
            objProp[4] = objProp[5] = objProp[6] = objProp[7] = objProp[8] = objProp[9] = objProp[10] = objProp[11] = objProp[12] = objProp[14] = objProp[15] = objProp[16] = objProp[17] = objProp[18] = objProp[19] = "NO";

            String revoluciones = eq.findObs(pruebasebas.getIdPruebas(), "Revoluciones");

            if (revoluciones.equals("SI")) {
                objProp[4] = "SI";
            } else {
                objProp[4] = "NO";
            }

            String fugasTubo = eq.findObs(pruebasebas.getIdPruebas(), "fugas en el tubo");

            if (fugasTubo.equals("SI")) {
                objProp[5] = "SI";
            } else {
                objProp[5] = "NO";
            }

            String salidas = eq.findObs(pruebasebas.getIdPruebas(), "Salidas adicionales en el sistema de escape");

            if (salidas.equals("SI")) {
                objProp[6] = "SI";
            } else {
                objProp[6] = "NO";
            }
            String aceite = eq.findObs(pruebasebas.getIdPruebas(), "Ausencia de tapones de aceite");

            if (aceite.equals("SI")) {
                objProp[7] = "SI";
            } else {
                objProp[7] = "NO";
            }

            String tapones = eq.findObs(pruebasebas.getIdPruebas(), "Ausencia de tapas o tapones de combustible");

            if (tapones.equals("SI")) {
                objProp[8] = "SI";
            } else {
                objProp[8] = "NO";
            }
            String sistema = eq.findObs(pruebasebas.getIdPruebas(), "Sistema de admisión de aire en mal estado");

            if (sistema.equals("SI")) {
                objProp[9] = "SI";
            } else {
                objProp[9] = "NO";
            }

            String Accesorios = eq.findObs(pruebasebas.getIdPruebas(), "Instalación de accesorios o deformaciones");

            if (Accesorios.equals("SI")) {
                objProp[11] = "SI";
            } else {
                objProp[11] = "NO";
            }

            String refrigeracion = eq.findObs(pruebasebas.getIdPruebas(), "Incorrecta operación del sistema de refrigeración");

            if (refrigeracion.equals("SI")) {
                objProp[12] = "SI";
            } else {
                objProp[12] = "NO";
            }
            String gobernador = eq.findObs(pruebasebas.getIdPruebas(), "Gobernador no limita las Revoluciones");

            if (gobernador.equals("SI")) {
                objProp[14] = "SI";
            } else {
                objProp[14] = "NO";
            }
            String motor = eq.findObs(pruebasebas.getIdPruebas(), "Indicación de mal funcionamiento del motor");

            if (motor.equals("SI")) {
                objProp[15] = "SI";
            } else {
                objProp[15] = "NO";
            }

            String subita = eq.findObs(pruebasebas.getIdPruebas(), "Falla súbita");

            if (subita.equals("SI")) {
                objProp[16] = "SI";
                objProp[17] = "SI";
            } else {
                objProp[16] = "NO";
                objProp[17] = "NO";
            }
            String aritmetica = eq.findObs(pruebasebas.getIdPruebas(), "La diferencia aritmética");

            if (aritmetica.equals("SI")) {
                objProp[18] = "SI";
            } else {
                objProp[18] = "NO";
            }
            objProp[13] = "NO";

            if (pruebasebas.getAprobada().equals("Y")) {
                objProp[38] = "APROBADO";
            } else if (pruebasebas.getAprobada().equals("N")) {
                objProp[38] = "RECHAZADO";
                objProp[19] = "SI";
                System.out.println("Prueba" + pruebasebas.getComentarioAborto());
                if (pruebasebas != null) {
                    String comentarioAborto = pruebasebas.getComentarioAborto();

                    if (comentarioAborto != null && !comentarioAborto.isEmpty()) {
                        //objProp[22] = comentarioAborto.equals("Condiciones Anormales") ? "SI" : "NO";
                        objProp[19] = "NO";
                    } else {
                        objProp[19] = "SI";
                    }
                } else {
                    objProp[19] = "SI";
                }
            } else {
                objProp[38] = "ABORTADO";
            }

        }
        modeloResolucionInspeccionRealizada.addRow(objProp);
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
                        if (p.getSerialEquipo() != null) {
                            ruidoMotor = ruidoMotor.toString();
                            break;
                        } else {
                            ruidoMotor = ruidoMotor;
                        }
                    }
                }
            }
        }
        return ruidoMotor;

    }

    private void cargarEquipos(Pruebas pruebasebas) {
        Equipos eq = new Equipos();

        /* modeloDatosEquipoYSoftware = new DefaultTableModel();
        modeloDatosEquipoYSoftware.addColumn("MARCA_AG");//0
        modeloDatosEquipoYSoftware.addColumn("MOD_AG");//1
        modeloDatosEquipoYSoftware.addColumn("SERIAL_AG");//2
        modeloDatosEquipoYSoftware.addColumn("MARCA_BG");//3
        modeloDatosEquipoYSoftware.addColumn("MOD_BG");//4
        modeloDatosEquipoYSoftware.addColumn("SERIAL_BG");//5
        modeloDatosEquipoYSoftware.addColumn("LTOE");//6
        modeloDatosEquipoYSoftware.addColumn("SERIAL_E");//7
        modeloDatosEquipoYSoftware.addColumn("MARCA_RPM");//8
        modeloDatosEquipoYSoftware.addColumn("SERIAL_RPM");//9
        modeloDatosEquipoYSoftware.addColumn("MARCA_TEMP_M");//10
        modeloDatosEquipoYSoftware.addColumn("SERIAL_TEMP_M");//11
        modeloDatosEquipoYSoftware.addColumn("MARCA_TEMP_A");//12
        modeloDatosEquipoYSoftware.addColumn("SERIAL_TEMP_A");//13
        modeloDatosEquipoYSoftware.addColumn("MARCA_HUM_R");//14
        modeloDatosEquipoYSoftware.addColumn("SERIAL_HUM_R");//15
        modeloDatosEquipoYSoftware.addColumn("NOM_SOFT");//16
        modeloDatosEquipoYSoftware.addColumn("VER_SOFT");//17
        modeloDatosEquipoYSoftware.addColumn("DES_SOFT");//18
        modeloDatosEquipoYSoftware.addColumn("TIP_IDE_LIN");//19
        modeloDatosEquipoYSoftware.addColumn("NUM_IDE_LIN");//20
        modeloDatosEquipoYSoftware.addColumn("NOM_LIN");//21
        modeloDatosEquipoYSoftware.addColumn("FECHA_LIN");//22
        modeloDatosEquipoYSoftware.addColumn("P_ALTO_LAB");//23
        modeloDatosEquipoYSoftware.addColumn("P_ALTO_SERIAL");//24
        modeloDatosEquipoYSoftware.addColumn("P_ALTO_CER");//25
        modeloDatosEquipoYSoftware.addColumn("V_FDN_ALTO");//26
        modeloDatosEquipoYSoftware.addColumn("P_BAJO_LAB");//27
        modeloDatosEquipoYSoftware.addColumn("P_BAJO_SERIAL");//28
        modeloDatosEquipoYSoftware.addColumn("P_BAJO_CER");//29
        modeloDatosEquipoYSoftware.addColumn("V_FDN_BAJO");//30
        modeloDatosEquipoYSoftware.addColumn("R_FDN_CERO");//31
        modeloDatosEquipoYSoftware.addColumn("R_FDN_BAJO");//32
        modeloDatosEquipoYSoftware.addColumn("R_FDN_ALTO");//33
        modeloDatosEquipoYSoftware.addColumn("R_FDN_CIEN");//34
        modeloDatosEquipoYSoftware.addColumn("R_LIN");//35 */

        try {
            if (pruebasebas.getTipoPrueba().getTesttype() == 8) {
                CdaJpaController cda = new CdaJpaController();
                Software sw = new Software();
                Object[] info = cda.findmedellin();
                Cda mcda = cda.findCda(1);
                Object[] calibracionesEquipos = eq.findLienalidad(pruebasebas.getFechaPrueba(), pruebasebas.getIdPruebas());
                String ser2 = (String) calibracionesEquipos[27];
                List<String> serial = eq.listaSerial(ser2);
                List<String> serialRPM = eq.listaRPM(serial.get(1));
                String marca1 = eq.findmarca(serial.get(1));//rpm
                String marca2 = eq.findmarca(serial.get(2));//termohigrometro
                cal2[0] = calibracionesEquipos[23];
                cal2[1] = "LCS2400";
                if (serial.get(0) != null) {
                    cal2[2] = serial.get(0);
                } else {
                    cal2[2] = "No Posee";
                }
                cal2[3] = "SENSORS";
                cal2[4] = "";
                cal2[5] = "";
                cal2[6] = calibracionesEquipos[25];
                cal2[7] = calibracionesEquipos[26];
                cal2[8] = marca1;
                if (serial.get(1) != null) {
                    //cal2[9] = serialRPM.get(0);
                    try {
                        if (serialRPM.get(2) != null) {
                            cal2[9] = serialRPM.get(0) + "/" + serialRPM.get(2);
                        } else {
                            cal2[9] = serial.get(1);
                        }
                    } catch (Exception e) {
                        cal2[9] = serial.get(1);
                    }
                } else {
                    cal2[9] = "No Posee";
                }
                cal2[10] = marca1;
                if (serial.get(2) != null) {
                    //cal2[11] = serial.get(2);
                    try {
                        if (serialRPM.get(2) != null) {
                            cal2[11] = serialRPM.get(0) + "/" + serialRPM.get(1);
                        } else {
                            cal2[11] = serialRPM.get(1);
                        }
                    } catch (Exception e) {
                        cal2[11] = "";
                    }
                } else {
                    cal2[11] = "No Posee";
                }
                cal2[12] = marca2;
                if (serial.get(2) != null) {
                    cal2[13] = serial.get(2);
                } else {
                    cal2[13] = "No Posee";
                }
                cal2[14] = marca2;
                if (serial.get(2) != null) {
                    cal2[15] = serial.get(2);
                } else {
                    cal2[15] = "No Posee";
                }
                cal2[16] = "SART";
                cal2[17] = "1.7.3";
                cal2[18] = "Soltelec S.A.S";
                cal2[19] = "C";
                int idinspector = (Integer) calibracionesEquipos[2];
                List<Object[]> inspectorResults = eq.findInspector(idinspector);
                Object dato1 = null;
                Object dato2 = null;
                Object dato3 = null;
                Object dato4 = null;
                Object dato5 = null;
                Object dato6 = null;
                Object dato7 = null;
                Object dato8 = null;
                Object dato9 = null;
                for (Object[] resultado : inspectorResults) {
                    dato1 = resultado[0];
                    System.out.println("dato1" + dato1);
                    dato2 = resultado[1];
                    System.out.println("dato2" + dato2);
                    dato3 = resultado[2];
                    System.out.println("dato3" + dato3);
                    dato4 = resultado[3];
                    System.out.println("dato4" + dato4);
                    dato5 = resultado[4];
                    System.out.println("dato5" + dato5);
                    dato6 = resultado[5];
                    System.out.println("dato6" + dato6);
                    dato7 = resultado[6];
                    System.out.println("dato7" + dato7);
                    dato8 = resultado[7];
                    System.out.println("dato8" + dato8);
                    dato9 = resultado[8];
                    System.out.println("dato9" + dato9);
                }
                cal2[20] = dato4;
                cal2[21] = dato3;
                sdfH = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
                Timestamp FechaLinealidad = eq.findLin(pruebasebas.getFechaPrueba());
                cal2[22] = FechaLinealidad;
                cal2[23] = calibracionesEquipos[14];
                cal2[24] = calibracionesEquipos[15];
                cal2[25] = calibracionesEquipos[16];
                Object[] val = eq.findVal(pruebasebas.getFechaPrueba());
                cal2[26] = val[1];
                cal2[27] = calibracionesEquipos[17];
                cal2[28] = calibracionesEquipos[18];
                cal2[29] = calibracionesEquipos[19];
                cal2[30] = val[0];

                cal2[31] = val[2];
                cal2[32] = val[3];
                cal2[33] = val[4];
                cal2[34] = val[5];
                cal2[35] = val[6];

                //}
                //}
            }
        } catch (Exception e) {
            e.printStackTrace();
            cal2 = new Object[52];
        }

        modeloDatosEquipoYSoftware.addRow(cal2);

    }

    public static ArrayList<Double> calcularOpacidad(Float opacidades) throws ArithmeticException {
        ArrayList<Double> resultados = new ArrayList<>();

        double a = Math.log(1 - (opacidades / 100));
        double K = a / 0.430;
        double resultado = (-1 * K);
        resultados.add(resultado);

        return resultados;
    }

    public static double calcularN(double k) {
        return 100 * (1 - Math.exp(-k * 0.430));
    }
    
       public static double AproximarMedidas(Double Medida) { //Metodo para realizar la aproximacion de las medidas 
        Integer Bandera = 0;
        Double Bandera2;
        System.out.println("entro a aproximar medida :" + Medida);
        if (Medida > 99) {
            Bandera = Medida.intValue();
            Bandera2 = Medida - Bandera;
            Medida = (Bandera2 >= 0.5) ? Medida + (1 - Bandera2) : Medida- Bandera2;
            // return Medida;

        } else if (Medida > 10 && Medida < 100) {
            System.out.println("+++++++++++++++++++++++++++ medida: " + Medida + " +++++++++++++++++++++++++");
            Medida = Medida * 10;
            Bandera = Medida.intValue();
            Bandera2 = Medida - Bandera;
            Medida = (Bandera2 >= 0.5) ? Medida + (1 - Bandera2) : Medida- Bandera2;
            Medida = Medida / 10;
            System.out.println("+++++++++++++++++++++++++++ medida: " + Medida + " +++++++++++++++++++++++++");
            // return Medida;
        } else if (Medida < 10) {
            Medida = Medida * 100;
            Bandera = Medida.intValue();
            Bandera2 = Medida - Bandera;
            Medida = (Bandera2 >= 0.5) ? Medida + (1 - Bandera2) : Medida- Bandera2;
            Medida = Medida / 100;
            //return Medida;
        }
        System.out.println("despues de aproximar: " + Medida);
        return Medida;
    }

}
