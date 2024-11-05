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
import com.soltelec.servidor.dtos.reporte_ntc5365.DatosInspeccionNtc5365;
import com.soltelec.servidor.dtos.reporte_ntc5365.Ntc5365;
import com.soltelec.servidor.dtos.reporte_ntc5365.PropietarioNtC5365;
import com.soltelec.servidor.dtos.reporte_ntc5365.ResultadosInspeccionNtc5365;
import com.soltelec.servidor.dtos.reporte_ntc5365.SoftwareEquipoNtc5365;
import com.soltelec.servidor.dtos.reporte_ntc5365.VehiculoNtc5365;
import com.soltelec.servidor.dtos.reportes_ntcs.DatosGeneralesNtc;
import com.soltelec.servidor.dtos.reportes_ntcs.EquiposSoftOttoNtc;
import com.soltelec.servidor.dtos.reportes_ntcs.PropietariosNtc;
import com.soltelec.servidor.dtos.reportes_ntcs.ReporteNtc;
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
 * @author Gerencia Desarollo TecNOlogica
 * @fecha 01/03/2018
 */
public class ReporteMedellin extends javax.swing.JInternalFrame {
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

    public ReporteMedellin() {
        super("Reporte Medellin (4983)",
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
        modeloCDA.addColumn("TOTAL_EQ4983");//14

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
        modeloInfoVehiculo.addColumn("PLACA");//0
        modeloInfoVehiculo.addColumn("MODELO");//1
        modeloInfoVehiculo.addColumn("NUM_MOTOR");//2
        modeloInfoVehiculo.addColumn("VIN");//3
        modeloInfoVehiculo.addColumn("CILINDRAJE");//4
        modeloInfoVehiculo.addColumn("LIC_TRANS");//5
        modeloInfoVehiculo.addColumn("KM");//6
        modeloInfoVehiculo.addColumn("GNV_CONV");//7
        modeloInfoVehiculo.addColumn("GNV_CONV_V");//8
        modeloInfoVehiculo.addColumn("MARCA");//9
        modeloInfoVehiculo.addColumn("LINEA");//10
        modeloInfoVehiculo.addColumn("CLASE");//11
        modeloInfoVehiculo.addColumn("SERVICIO");//12
        modeloInfoVehiculo.addColumn("TIP_COMB");//13
        modeloInfoVehiculo.addColumn("TIP_MOTOR");//14
        modeloInfoVehiculo.addColumn("RPM_FAB");//15
        modeloInfoVehiculo.addColumn("RAL_MIN_FAB");//16
        modeloInfoVehiculo.addColumn("RAL_MAX_FAB");//17
        //modeloInfoVehiculo.addColumn("DIS_MOTOR");//18
        modeloInfoVehiculo.addColumn("NUM_ESCAPE");//18
//         

/////////Equipos y software
        modeloDatosEquipoYSoftware = new DefaultTableModel();
        modeloDatosEquipoYSoftware.addColumn("MARCA_AG");//0
        modeloDatosEquipoYSoftware.addColumn("MOD_AG");//1
        modeloDatosEquipoYSoftware.addColumn("SERIAL_AG");//2
        modeloDatosEquipoYSoftware.addColumn("MARCA_BG");//3
        modeloDatosEquipoYSoftware.addColumn("MOD_BG");//4
        modeloDatosEquipoYSoftware.addColumn("SERIAL_BG");//5
        modeloDatosEquipoYSoftware.addColumn("PEF");//6
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
        modeloDatosEquipoYSoftware.addColumn("TIP_IDE_VGP");//19
        modeloDatosEquipoYSoftware.addColumn("NUM_IDE_VGP");//20
        modeloDatosEquipoYSoftware.addColumn("NOM_VGP");//21
        modeloDatosEquipoYSoftware.addColumn("F_FUGAS");//22
        modeloDatosEquipoYSoftware.addColumn("F_VGP");//23
        modeloDatosEquipoYSoftware.addColumn("P_ALTA_LAB");//24
        modeloDatosEquipoYSoftware.addColumn("P_ALTA_CIL");//25
        modeloDatosEquipoYSoftware.addColumn("P_ALTA_CER");//26
        modeloDatosEquipoYSoftware.addColumn("P_BAJA_LAB");//27
        modeloDatosEquipoYSoftware.addColumn("P_BAJA_CIL");//28
        modeloDatosEquipoYSoftware.addColumn("P_BAJA_CER");//29
        modeloDatosEquipoYSoftware.addColumn("P_HC_ALTO_P");//30
        modeloDatosEquipoYSoftware.addColumn("P_HC_ALTO_H");//31
        modeloDatosEquipoYSoftware.addColumn("P_HC_BAJO_P");//32
        modeloDatosEquipoYSoftware.addColumn("P_HC_BAJO_H");//33
        modeloDatosEquipoYSoftware.addColumn("P_CO_ALTO");//34
        modeloDatosEquipoYSoftware.addColumn("P_CO_BAJO");//35
        modeloDatosEquipoYSoftware.addColumn("P_CO2_ALTO");//36
        modeloDatosEquipoYSoftware.addColumn("P_CO2_BAJO");//37
        modeloDatosEquipoYSoftware.addColumn("P_O2_ALTO");//38
        modeloDatosEquipoYSoftware.addColumn("P_O2_BAJO");//39
        modeloDatosEquipoYSoftware.addColumn("R_HC_ALTO_P");//40
        modeloDatosEquipoYSoftware.addColumn("R_HC_ALTO_H");//41
        modeloDatosEquipoYSoftware.addColumn("R_HC_BAJO_P");//42
        modeloDatosEquipoYSoftware.addColumn("R_HC_BAJO_H");//43
        modeloDatosEquipoYSoftware.addColumn("R_CO_ALTO");//44
        modeloDatosEquipoYSoftware.addColumn("R_CO_BAJO");//45
        modeloDatosEquipoYSoftware.addColumn("R_CO2_ALTO");//46
        modeloDatosEquipoYSoftware.addColumn("R_CO2_BAJO");//47
        modeloDatosEquipoYSoftware.addColumn("R_O2_ALTO");//48
        modeloDatosEquipoYSoftware.addColumn("R_O2_BAJO");//49
        modeloDatosEquipoYSoftware.addColumn("C_VGP");//50
        modeloDatosEquipoYSoftware.addColumn("R_VGP");//51

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
        modeloDatosGeneralesInspeccion.addColumn("CATALIZADOR");
        modeloDatosGeneralesInspeccion.addColumn("LUGAR_TEMP");
        modeloDatosGeneralesInspeccion.addColumn("T_AMB");
        modeloDatosGeneralesInspeccion.addColumn("H_REL");

        //
        modeloResolucionInspeccionRealizada = new DefaultTableModel();
        modeloResolucionInspeccionRealizada.addColumn("T_MOTOR");//0
        modeloResolucionInspeccionRealizada.addColumn("RPM_RAL");//1
        modeloResolucionInspeccionRealizada.addColumn("RPM_CRU");//2
        modeloResolucionInspeccionRealizada.addColumn("HUMO");//3
        modeloResolucionInspeccionRealizada.addColumn("DILUCION");//4
        modeloResolucionInspeccionRealizada.addColumn("RPM_FUERA");//5
        modeloResolucionInspeccionRealizada.addColumn("FUGA_TUBO");//6
        modeloResolucionInspeccionRealizada.addColumn("SALIDAS_AD");//7
        modeloResolucionInspeccionRealizada.addColumn("FUGA_ACEITE");//8
        modeloResolucionInspeccionRealizada.addColumn("FUGA_COMB");//9
        modeloResolucionInspeccionRealizada.addColumn("ADMISION_NC");//10
        modeloResolucionInspeccionRealizada.addColumn("RECIRCULACION");//11
        modeloResolucionInspeccionRealizada.addColumn("ACC_TUBO");//12
        modeloResolucionInspeccionRealizada.addColumn("REFRIG_NC");//13
        modeloResolucionInspeccionRealizada.addColumn("R_HC_RAL");//14
        modeloResolucionInspeccionRealizada.addColumn("R_HC_CRU");//15
        modeloResolucionInspeccionRealizada.addColumn("R_CO_RAL");//16
        modeloResolucionInspeccionRealizada.addColumn("R_CO_CRU");//17
        modeloResolucionInspeccionRealizada.addColumn("R_CO2_RAL");//18
        modeloResolucionInspeccionRealizada.addColumn("R_CO2_CRU");//19
        modeloResolucionInspeccionRealizada.addColumn("R_O2_RAL");//20
        modeloResolucionInspeccionRealizada.addColumn("R_O2_CRU");//21
        //modeloResolucionInspeccionRealizada.addColumn("R_GAS_SCOR");//0
        modeloResolucionInspeccionRealizada.addColumn("NC_EMISIONES");//22
        modeloResolucionInspeccionRealizada.addColumn("RES_FINAL");//23

        /*
        *
        *Informacion Medidas
        *
         */
//        modeloMedidas = new DefaultTableModel();
//        //Ralenty 
//        modeloMedidas.addColumn("Rev Emisiones CO Ral"); //0
//        modeloMedidas.addColumn("Rev Emisiones CO2 Ral");//1
//        modeloMedidas.addColumn("Rev Emisiones HC Ral");//2
//        modeloMedidas.addColumn("Rev Emisiones O2 Ral");//3
//        modeloMedidas.addColumn("Rev Emisiones NOx Ral");//4
//        modeloMedidas.addColumn("Rev Temperatura Ral");//5
//        modeloMedidas.addColumn("Rev Revoluciones Ral");//6
//
//        //Cruceros        
//        modeloMedidas.addColumn("Rev Emisiones CO Cru ");//7
//        modeloMedidas.addColumn("Rev Emisiones CO2 Cru");//8
//        modeloMedidas.addColumn("Rev Emisiones HC Cru");//9
//        modeloMedidas.addColumn("Rev Emisiones O2 Cru");//10
//        modeloMedidas.addColumn("Rev Emisiones NOx");//11
//        modeloMedidas.addColumn("Rev Temperatura Cru");//12
//        modeloMedidas.addColumn("Rev Revoluciones Cru");//13
//
//        //Humedad Relativa y Temperatura
//        modeloMedidas.addColumn("Temperatura Ambiente");//14
//        modeloMedidas.addColumn("Humedad Relativa");//15
//
//        //Opacidad , Ruido y Causa 
//        modeloMedidas.addColumn("Opacidad");//16
//        modeloMedidas.addColumn("Ruido");//17
//        modeloMedidas.addColumn("Causa");
    }

    private void fillData(Date fechaInicial, Date fechaFinal){

        ReporteNtc todosLosDatos = Reportes.getNtc(fechaInicial, fechaFinal,0);

        List<DatosCda> datosCda = todosLosDatos.getCda();

        List<PropietariosNtc> datosPropietarios = todosLosDatos.getPropietarios();
        List<VehiculoNtc> datosVehiculo = todosLosDatos.getVehiculos(); 
        List<EquiposSoftOttoNtc> datosSoftwareEquipo = todosLosDatos.getEquiposOtto(); 
        List<DatosGeneralesNtc> datosInspeccion = todosLosDatos.getGeneralesPrueba(); 
        List<ResultadosOttoNtc> datosResulInspeccion  = todosLosDatos.getResultadosOtto(); 

        datosCda.stream().forEach(datoCda -> {
            Object[] fila = {
                datoCda.getCm(),
                "N",
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
                datoCda.getNTotalOtto()
            };
            modeloCDA.addRow(fila);
        });

        datosPropietarios.stream().forEach(datoPropietarios -> {
            Object[] fila = {
                datoPropietarios.getTipIdeProp().equals("CC") ? "C" : "N",
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
                datoVehiculo.getCilindraje(),
                datoVehiculo.getLicTrans(),
                datoVehiculo.getKm().equals("0") ? "NO FUNCIONAL" : datoVehiculo.getKm(),
                datoVehiculo.getGnvConv().contains("N") ? "NO" : "SI",
                datoVehiculo.getGnvConvV(),
                datoVehiculo.getMarca(),
                datoVehiculo.getLinea(),
                datoVehiculo.getClase(),
                datoVehiculo.getServicio(),
                datoVehiculo.getTipComb(),
                datoVehiculo.getTipMotor(),
                datoVehiculo.getRpmFab(),
                datoVehiculo.getRalMinFab(),
                datoVehiculo.getRalMaxFab(),
                datoVehiculo.getNumEscape()
            };
            modeloInfoVehiculo.addRow(fila);
        });

        datosSoftwareEquipo.stream().forEach(softEquipo -> {
            Object[] fila = {
                softEquipo.getMarcaAg(),
                softEquipo.getModAg(),
                softEquipo.getSerialAg(),
                softEquipo.getMarcaBg(),
                softEquipo.getModBg(),
                softEquipo.getSerialBg(),
                softEquipo.getPef(),
                softEquipo.getSerialE(),
                softEquipo.getMarcaRpm(),
                softEquipo.getSerialRpm(),
                softEquipo.getMarcaTempM(),
                softEquipo.getSerialTempM(),
                softEquipo.getMarcaTempA(),
                softEquipo.getSerialTempA(),
                softEquipo.getMarcaHumR(),
                softEquipo.getSerialHumR(),
                softEquipo.getNumSoft(),
                softEquipo.getVerSoft(),
                softEquipo.getDesSoft(),
                softEquipo.getTipIdeVgp().equals("CC") ? "C" : softEquipo.getTipIdeVgp(),
                softEquipo.getNumIdeVgp(),
                softEquipo.getNomVgp(),
                softEquipo.getFFugas(),
                softEquipo.getFVgp(),
                softEquipo.getPAltaLab(),
                softEquipo.getPAltaCil(),
                softEquipo.getPAltaCer(),
                softEquipo.getPBajaLab(),
                softEquipo.getPBajaCil(),
                softEquipo.getPBajaCer(),
                softEquipo.getPHcAltoP(),
                softEquipo.getPHcAltoH(),
                softEquipo.getPHcBajoP(),
                softEquipo.getPHcBajoH(),
                softEquipo.getPCoAlto(),
                softEquipo.getPCoBajo(),
                softEquipo.getPCo2Alto(),
                softEquipo.getPCo2Bajo(),
                softEquipo.getPO2Alto(),
                softEquipo.getPO2Bajo(),
                softEquipo.getRHcAltoP(),
                softEquipo.getRHcAltoH(),
                softEquipo.getRHcBajoP(),
                softEquipo.getRHcBajoH(),
                softEquipo.getRCoAlto(),
                softEquipo.getRCoBajo(),
                softEquipo.getRCo2Alto(),
                softEquipo.getRCo2Bajo(),
                softEquipo.getRO2Alto(),
                softEquipo.getRO2Bajo(),
                softEquipo.getCVgp(),
                softEquipo.getRVgp()
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
                datoInspeccion.getCatalizardor(),
                datoInspeccion.getLugarTemp(),
                datoInspeccion.getTAmb(),
                datoInspeccion.getHRel()
            };
            modeloDatosGeneralesInspeccion.addRow(fila);
        });

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
                resultado.getAdmisionNc(),
                resultado.getRecirculacion(),
                resultado.getAccTubo(),
                resultado.getRefrigNc(),
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

    private void fillDataaa(Date fechaInicial, Date fechaFinal){

        Ntc5365 todosLosDatos = Reportes.getNtc5365(fechaInicial, fechaFinal);

        List<DatosCda> datosCda = todosLosDatos.getDatosCda();
        List<PropietarioNtC5365> datosPropietarios = todosLosDatos.getDatosPropietario();
        List<VehiculoNtc5365> datosVehiculo = todosLosDatos.getDatosVehiculo();
        List<SoftwareEquipoNtc5365> datosSoftwareEquipo = todosLosDatos.getDatosSoftware();
        List<DatosInspeccionNtc5365> datosInspeccion = todosLosDatos.getDatosInspeccion();
        List<ResultadosInspeccionNtc5365> datosResulInspeccion = todosLosDatos.getDatosRInspeccion();

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
                datoCda.getNTotal4t(),
                datoCda.getNTotal2t()
            };
            modeloCDA.addRow(fila);
        });

        datosPropietarios.stream().forEach(datoPropietarios -> {
            Object[] fila = {
                datoPropietarios.getTipoDocumento(),
                datoPropietarios.getNumeroIdentificacion(),
                datoPropietarios.getNombrePropietario(),
                datoPropietarios.getDireccionPropietario(),
                datoPropietarios.getTelefono1Propietario(),
                datoPropietarios.getTelefono2Propietario(),
                datoPropietarios.getMunicipioPropietario(),
                datoPropietarios.getEmailPropietario()
            };
            modeloDatosPropietarioVehiculo.addRow(fila);
        });

        datosVehiculo.stream().forEach(datoVehiculo -> {
            Object[] fila = {
                datoVehiculo.getPlaca(),
                datoVehiculo.getModelo(),
                datoVehiculo.getNumMotor(),
                datoVehiculo.getVin(),
                datoVehiculo.getCilindraje(),
                datoVehiculo.getLicencia(),
                datoVehiculo.getKilometraje(),
                datoVehiculo.getConversionGnw(),
                datoVehiculo.getFechaVencimientoGnv(),
                datoVehiculo.getMarca(),
                datoVehiculo.getLinea(),
                datoVehiculo.getClase(),
                datoVehiculo.getServicio(),
                datoVehiculo.getTCombustible(),
                datoVehiculo.getTMotor(),
                datoVehiculo.getRpmConocidas(),
                datoVehiculo.getRpmMinimasRalenti(),
                datoVehiculo.getRpmMaximasRalenti(),
                datoVehiculo.getDisenoMotor(),
                datoVehiculo.getNTubosEscapes()
            };
            modeloInfoVehiculo.addRow(fila);
        });

        datosSoftwareEquipo.stream().forEach(softEquipo -> {
            Object[] fila = {
                softEquipo.getMarcaAnalizador(),
                softEquipo.getModeloAnalizador(),
                softEquipo.getSerialAnalizador(),
                softEquipo.getMarcaBanco(),
                softEquipo.getModeloBanco(),
                softEquipo.getSerialBanco(),
                softEquipo.getPefBanco(),
                softEquipo.getSerialElectronico(),
                softEquipo.getMarcaCaptadorRpm(),
                softEquipo.getSerialCaptadorRpm(),
                softEquipo.getMarcaSensorTempMotor(),
                softEquipo.getSerialSensorTempMotor(),
                softEquipo.getMarcaSensorTempAmbiente(),
                softEquipo.getSerialSensorTempAmbiente(),
                softEquipo.getMarcaSensorhumedad(),
                softEquipo.getSerialSensorHumedad(),
                softEquipo.getNombreSoftware(),
                softEquipo.getVersionSoftware(),
                softEquipo.getDesarrolladorSoftware(),
                softEquipo.getTipoIdentificacionInspector(),
                softEquipo.getNumeroIdentificacionInspector(),
                softEquipo.getNombreInspector(),
                softEquipo.getFechaFugas(),
                softEquipo.getFechaGasPatron(),
                softEquipo.getLaboratioAlta(),
                softEquipo.getCilindroAlta(),
                softEquipo.getCertificadorAlta(),
                softEquipo.getLaboratorioBaja(),
                softEquipo.getCilindroBaja(),
                softEquipo.getCertificadoBaja(),
                softEquipo.getValorHcAltaP(),
                softEquipo.getValorHcAltaH(),
                softEquipo.getValorHcBajaP(),
                softEquipo.getValorHcBajaH(),
                softEquipo.getValorCoAlto(),
                softEquipo.getValorCoBajo(),
                softEquipo.getValorCo2Alto(),
                softEquipo.getValorCo2Bajo(),
                softEquipo.getValorO2Alto(),
                softEquipo.getValorO2Bajo(),
                softEquipo.getResultadoHcAltaP(),
                softEquipo.getResultadoHcAltaH(),
                softEquipo.getResultadoHcBajaP(),
                softEquipo.getResultadoHcBajaH(),
                softEquipo.getResultadoCoAlto(),
                softEquipo.getResultadoCoBajo(),
                softEquipo.getResultadoCo2Alto(),
                softEquipo.getResultadoCo2Bajo(),
                softEquipo.getResultadoO2Alto(),
                softEquipo.getResultadoO2Bajo(),
                softEquipo.getCriterioGasPatron(),
                softEquipo.getResultadoGasPatron()
            };
            modeloDatosEquipoYSoftware.addRow(fila);
        });

        datosInspeccion.stream().forEach(datoInspeccion -> {
            Object[] fila = {
                datoInspeccion.getTipoDocumentoDirector(),
                datoInspeccion.getNumDocumentoDirector(),
                datoInspeccion.getNombreDirector(),
                datoInspeccion.getTipoDocumentoInspector(),
                datoInspeccion.getNumDocumentoInspector(),
                datoInspeccion.getNombreInspector(),
                datoInspeccion.getNumeroFur(),
                datoInspeccion.getFechaFur(),
                datoInspeccion.getConsecutivoFur(),
                datoInspeccion.getFurAsociado(),
                datoInspeccion.getCertificadoEmitido(),
                datoInspeccion.getFechaInicio(),
                datoInspeccion.getFechaFin(),
                datoInspeccion.getFechaAborto(),
                datoInspeccion.getCausalAborto(),
                datoInspeccion.getCatalizador(),
                datoInspeccion.getTempAmbiente(),
                datoInspeccion.getHumedadRelativa()
            };
            modeloDatosGeneralesInspeccion.addRow(fila);
        });

        datosResulInspeccion.stream().forEach(resultado -> {
            Object[] fila = {
                resultado.getTempMotor(),
                resultado.getRpmRalenti(),
                resultado.getPresenciaHumo(),
                resultado.getCorreccionOxigeno(),
                resultado.getRpmFuera(),
                resultado.getFugaTubo(),
                resultado.getSalidasAdicionales(),
                resultado.getFugaAceite(),
                resultado.getFugaCombustible(),
                resultado.getRHcRalenti(),
                resultado.getRCoRalenti(),
                resultado.getRCo2Ralenti(),
                resultado.getRO2Ralenti(),
                resultado.getNcEmisiones(),
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
        *Mensaje Dialog que NO se encontro Datos en la Base de Datos
         */
        if (lstPruebas == null || lstPruebas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La consulta NO genero datos");
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
                if (pruebasebas.getHojaPruebas().getVehiculos().getTipoVehiculo().getCartype() != 4 && pruebasebas.getHojaPruebas().getVehiculos().getTipoVehiculo().getCartype() != 5 && pruebasebas.getHojaPruebas().getVehiculos().getTipoVehiculo().getCartype() != 104) {
                    if (pruebasebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype() == 4 || pruebasebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype() == 1) {

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
        *   Crear los NOmbres de la tablas y agregarlos al menu de Reportes
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
        ReporteMedellin rsv = new ReporteMedellin();
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
        objCda[13] = mcda.getTotal_Eq_Otto();

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
            objProp[5] = "NO Posee";
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
        objInfoVehiculo[1] = pruebasebas.getHojaPruebas().getVehiculos().getModelo();//el formato numero se da en el excel, aca no es posible
        objInfoVehiculo[2] = pruebasebas.getHojaPruebas().getVehiculos().getNumeromotor();
        objInfoVehiculo[3] = pruebasebas.getHojaPruebas().getVehiculos().getVin();
        objInfoVehiculo[4] = pruebasebas.getHojaPruebas().getVehiculos().getCilindraje();
        objInfoVehiculo[5] = pruebasebas.getHojaPruebas().getVehiculos().getNumerolicencia();
        if (pruebasebas.getHojaPruebas().getVehiculos().getKilometraje().equals(0)) {
            objInfoVehiculo[6] = "NO FUNCIONAL";
        } else {
            objInfoVehiculo[6] = pruebasebas.getHojaPruebas().getVehiculos().getKilometraje();
        }

        if (pruebasebas.getHojaPruebas().getVehiculos().getConversion_GNV().equals("N")) {
            objInfoVehiculo[7] = "NO";
            objInfoVehiculo[8] = "";
        } else if (pruebasebas.getHojaPruebas().getVehiculos().getConversion_GNV().equals("Y")) {
            objInfoVehiculo[7] = "SI";
            objInfoVehiculo[8] = pruebasebas.getHojaPruebas().getFecha_venc_gnv();
        } else if (pruebasebas.getHojaPruebas().getVehiculos().getConversion_GNV().equals("NA")) {
            objInfoVehiculo[7] = "N/A";
            objInfoVehiculo[8] = "";
        }

        objInfoVehiculo[9] = pruebasebas.getHojaPruebas().getVehiculos().getMarcas().getNombremarca();
        objInfoVehiculo[10] = pruebasebas.getHojaPruebas().getVehiculos().getLineasVehiculos().getCrlname();
        objInfoVehiculo[11] = pruebasebas.getHojaPruebas().getVehiculos().getClasesVehiculo().getNombreclase();
        System.out.println(pruebasebas.getHojaPruebas().getVehiculos().getClasesVehiculo().getNombreclase());
        objInfoVehiculo[12] = pruebasebas.getHojaPruebas().getVehiculos().getServicios().getNombreservicio();
        objInfoVehiculo[13] = pruebasebas.getHojaPruebas().getVehiculos().getTiposGasolina().getNombregasolina();
        objInfoVehiculo[14] = "OTTO 4T";
        if (pruebasebas.getHojaPruebas().getVehiculos().getLineasVehiculos().getRpm_ini() != null && pruebasebas.getHojaPruebas().getVehiculos().getLineasVehiculos().getRpm_fin() != null && pruebasebas.getHojaPruebas().getVehiculos().getLineasVehiculos().getRpm_ini() != "0" && pruebasebas.getHojaPruebas().getVehiculos().getLineasVehiculos().getRpm_fin() != "0") {
            objInfoVehiculo[15] = "SI";
            objInfoVehiculo[16] = pruebasebas.getHojaPruebas().getVehiculos().getLineasVehiculos().getRpm_ini();
            objInfoVehiculo[17] = pruebasebas.getHojaPruebas().getVehiculos().getLineasVehiculos().getRpm_fin();
        } else {
            objInfoVehiculo[15] = "NO";
            objInfoVehiculo[16] = "";
            objInfoVehiculo[17] = "";
        }

        // objInfoVehiculo[18] = pruebasebas.getHojaPruebas().getVehiculos().getDiseNO();
        objInfoVehiculo[18] = pruebasebas.getHojaPruebas().getVehiculos().getNumeroexostos();
//        if (pruebasebas.getHojaPruebas().getVehiculos().getLineasVehiculos().getCrlname() == null) {
//            objInfoVehiculo[6] = "NO posee";
//        } else {
//            objInfoVehiculo[6] = pruebasebas.getHojaPruebas().getVehiculos().getLineasVehiculos().getCrlname();
//        }
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
                        dataRowMedellin[0] = medidas.getValormedida();      //MoNOxido de CarboNO Ralenty (COR)
                        break;
                    case 8020:
                        dataRowMedellin[0] = medidas.getValormedida();      //MoNOxido de CarboNO 2T Ralenty (COR2T)
                        break;
                    case 8003:
                        dataRowMedellin[1] = medidas.getValormedida();      //Dioxido de CarboNO Ralenty (CO2R)
                        break;
                    case 8019:
                        dataRowMedellin[1] = medidas.getValormedida();      //Dioxido de CarboNO 2 Tiempos Ralenty (CO2R2T
                        break;
                    case 8001:
                        dataRowMedellin[2] = medidas.getValormedida();      //HidroCarburos Ralenty (HC)
                        break;
                    case 8018:
                        dataRowMedellin[2] = medidas.getValormedida();      // HidroCarburos vehiculo 2T Ralenty (HC)
                        break;
                    case 8004:
                        dataRowMedellin[3] = medidas.getValormedida();      //OxigeNO Ralenty (O2)
                        break;
                    case 8021:
                        dataRowMedellin[3] = medidas.getValormedida();      // OxigeNO Ralenty 2 Tiempos (O2R2T)
                        break;
                    case 8006:
                        dataRowMedellin[5] = medidas.getValormedida();      ///Temperatura Ralenty 
                        break;
                    case 8022:
                        dataRowMedellin[5] = medidas.getValormedida();      // Temperatura Ralenty 2 Tiempos 
                        break;
                    case 8005:

                        dataRowMedellin[6] = medidas.getValormedida();   //Revoluciones por minuto Ralenty 
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
                        dataRowMedellin[7] = medidas.getValormedida();      //MoNOxido de CarboNO Crucero (COC)
                        break;
                    case 8024:
                        dataRowMedellin[7] = medidas.getValormedida();      //MoNOxido de CarboNO Crucero (COC2T)
                        break;
                    case 8009:
                        dataRowMedellin[8] = medidas.getValormedida();      //Dioxido de CarboNO Crucero (CO2C)
                        break;
                    case 8025:
                        dataRowMedellin[8] = medidas.getValormedida();      //Dioxido de CarboNO Crucero (COC2T)
                        break;
                    case 8007:
                        dataRowMedellin[9] = medidas.getValormedida();      //HidroCarburos Crucero (HCC)
                        break;
                    case 8023:
                        dataRowMedellin[9] = medidas.getValormedida();      //Hidrocarburos Crucero 2 Tiempos (COC2T)
                        break;
                    case 8010:
                        dataRowMedellin[10] = medidas.getValormedida();     //OxigeNO Crucero (O2C)
                        break;
                    case 8026:
                        dataRowMedellin[10] = medidas.getValormedida();      //OxigeNO Crucero 2 Tiempos (OC2T)
                        break;
                    case 8012:
                        dataRowMedellin[12] = medidas.getValormedida();       //Temperatura Crucero
                        break;
                    case 8027:
                        dataRowMedellin[12] = medidas.getValormedida();      // Temperatura Crucero 2 Tiempos 
                        break;
                    case 8011:
                        dataRowMedellin[13] = medidas.getValormedida();
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
            *Enla Columan de Causas condiciones ANOrmales
            *
             */
            if (pruebasebas.getTipoPrueba().getTesttype() == 8) {
                //si los campos NO estan nullos en comentarios de aborte ejecutar
                if (pruebasebas.getComentarioAborto() != null) {
                    //Cuando es igual condiciones aNOrmales  y si existe condicionales
                    if (pruebasebas.getComentarioAborto().equalsIgnoreCase("Condiciones ANOrmales")) {
                        // si NO esta nullo la condicion aNOrmal ejecuta un detallle
                        if (pruebasebas.getObservaciones() != null) {
                            dataRowMedellin[18] = pruebasebas.getComentarioAborto().concat(" detalle: ").concat(pruebasebas.getObservaciones());
                            // si NO entonces dice N/A
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
        if (prueba.getAbortada().equals("Y")) {
            objProp[13] = prueba.getFechaFinal();
        } else {
            objProp[13] = "";
        }
        if (prueba.getComentarioAborto() != null) {
            if (prueba.getComentarioAborto().contains("equipo de medicion")) {
                objProp[14] = "1";
            } else if (prueba.getComentarioAborto().contains("fluido electrico")) {
                objProp[14] = "2";
            } else if (prueba.getComentarioAborto().contains("bloqueo")) {
                objProp[14] = "3";
            } else if (prueba.getComentarioAborto().contains("Ejecucion Incorrecta")) {
                objProp[14] = "4";
            } else {
                objProp[14] = "0";
            }
        } else {
            objProp[14] = "0";
        }

        if (prueba.getHojaPruebas().getVehiculos().getCatalizador().equals("N")) {
            objProp[15] = "NO";
        } else if (prueba.getHojaPruebas().getVehiculos().getCatalizador().equals("Y")) {
            objProp[15] = "SI";
        } else {
            objProp[15] = "NO APLICA";
        }

        if (prueba.getHojaPruebas().getFormaMedTemperatura().equals("B")) {
            objProp[16] = "Bloque";
        } else if (prueba.getHojaPruebas().getFormaMedTemperatura().equals("I")) {
            objProp[16] = "Aceite";
        } else {
            objProp[16] = "Metodo de Tiempo";
            objProp[15] = "SI";
        }

        for (Medidas medidas : prueba.getMedidasList()) {
            if (medidas.getTiposMedida().getMeasuretype() == 8031) {
                objProp[17] = medidas.getValormedida();
            } else if (medidas.getTiposMedida().getMeasuretype() == 8032) {
                objProp[18] = medidas.getValormedida();
            }
        }
        modeloDatosGeneralesInspeccion.addRow(objProp);

    }

    private void cargarResultadosInspeccion(Pruebas pruebasebas) {

        Object[] objProp = new Object[55];
        String datos = "";
        Equipos eq = new Equipos();

        for (Medidas medidas : pruebasebas.getMedidasList()) {
            if (medidas.getTiposMedida().getMeasuretype() == 8006) {
                if (!pruebasebas.getHojaPruebas().getVehiculos().getTiposGasolina().equals(3) && pruebasebas.getHojaPruebas().getFormaMedTemperatura().equals("C")) {
                    objProp[0] = "";
                } else {
                    objProp[0] = medidas.getValormedida();//temperatura motor
                }
            } else if (medidas.getTiposMedida().getMeasuretype() == 8005 || medidas.getTiposMedida().getMeasuretype() == 8028) {
                float v = medidas.getValormedida();
                String v2 = String.format("%.0f", v);
                objProp[1] = v2;//temperatura ralenti
            } else if (medidas.getTiposMedida().getMeasuretype() == 8011) {
                float v = medidas.getValormedida();
                String v2 = String.format("%.0f", v);
                objProp[2] = v2;//crucero
            } else if (pruebasebas.getHojaPruebas().getVehiculos().getTiemposmotor().equals(4)) {
                if (medidas.getTiposMedida().getMeasuretype() == 8001) {
                    objProp[14] = medidas.getValormedida();
                    datos += medidas.getValormedida() + "HC ";
                }
                if (medidas.getTiposMedida().getMeasuretype() == 8002) {
                    objProp[16] = medidas.getValormedida();
                    datos += medidas.getValormedida() + "CO ";
                }
                if (medidas.getTiposMedida().getMeasuretype() == 8003) {
                    objProp[18] = medidas.getValormedida();
                    datos += medidas.getValormedida() + "CO2 ";
                }
                if (medidas.getTiposMedida().getMeasuretype() == 8004) {
                    objProp[20] = medidas.getValormedida();
                    datos += medidas.getValormedida() + "O2 ";
                }
            } else {
                if (medidas.getTiposMedida().getMeasuretype() == 8018) {
                    objProp[15] = medidas.getValormedida();
                    datos += medidas.getValormedida() + "HC,";
                }
                if (medidas.getTiposMedida().getMeasuretype() == 8020) {
                    objProp[16] = medidas.getValormedida();
                    datos += medidas.getValormedida() + "CO,";
                }
                if (medidas.getTiposMedida().getMeasuretype() == 8019) {
                    objProp[18] = medidas.getValormedida();
                    datos += medidas.getValormedida() + "CO2,";
                }
                if (medidas.getTiposMedida().getMeasuretype() == 8021) {
                    objProp[20] = medidas.getValormedida();
                    datos += medidas.getValormedida() + "O2,";
                }
            }
            if (medidas.getTiposMedida().getMeasuretype() == 8007) {
                objProp[15] = medidas.getValormedida();
            }
            if (medidas.getTiposMedida().getMeasuretype() == 8008) {
                objProp[17] = medidas.getValormedida();
            }
            if (medidas.getTiposMedida().getMeasuretype() == 8009) {
                objProp[19] = medidas.getValormedida();
            }
            if (medidas.getTiposMedida().getMeasuretype() == 8010) {
                objProp[21] = medidas.getValormedida();
            }
            String v1 = "si";
            String v2 = "si";
            String v3 = "si";
            String v4 = "si";
            if (medidas.getTiposMedida().getMeasuretype() == 8003 && medidas.getValormedida() > 7) {
                v1 = "NO";
            }
            if (medidas.getTiposMedida().getMeasuretype() == 8009 && medidas.getValormedida() > 7) {
                v2 = "NO";
            }
            if (medidas.getTiposMedida().getMeasuretype() == 8004 && medidas.getValormedida() < 5) {
                v1 = "NO";
            }
            if (medidas.getTiposMedida().getMeasuretype() == 8010 && medidas.getValormedida() < 5) {
                v2 = "NO";
            }
            if (v1 == "NO" || v2 == "NO") {
                objProp[4] = "SI";
            } else {
                objProp[4] = "NO";
            }

        }

        objProp[3] = objProp[6] = objProp[5] = objProp[7] = objProp[8] = objProp[9] = objProp[10] = objProp[11] = objProp[12] = objProp[13] = objProp[22] = "NO";
        for (Defxprueba def : pruebasebas.getDefectos()) {
            if (def.getDefectos().getCardefault() == 84004) {
                objProp[3] = "Si";
            } else {
                objProp[3] = "NO";
            }
            if (def.getDefectos().getCardefault() == 84002) {
                objProp[6] = "Si";
            } else {
                objProp[6] = "NO";
            }
            if (pruebasebas.getHojaPruebas().getVehiculos().getCar() == 1) {
                if (def.getDefectos().getCardefault() == 84019) {
                    objProp[5] = "Si";
                } else {
                    objProp[5] = "NO";
                }
            } else {
                objProp[5] = "NO";
            }
            if (pruebasebas.getHojaPruebas().getVehiculos().getCar() == 4) {
                if (def.getDefectos().getCardefault() == 84040) {
                    objProp[5] = "Si";
                } else {
                    objProp[5] = "NO";
                }
            } else {
                objProp[5] = "NO";
            }
            if (def.getDefectos().getCardefault() == 84003) {
                objProp[7] = "Si";
            } else {
                objProp[7] = "NO";
            }
            if (pruebasebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype() == 1 && pruebasebas.getHojaPruebas().getVehiculos().getCar() == 1) {//ojo pensar si eso necesita ver si hay carros liviaNOs a diesel
                if (def.getDefectos().getCardefault() == 84001) {
                    objProp[8] = "Si";
                } else {
                    objProp[8] = "NO";
                }
            } else {
                objProp[8] = "NO";
            }
            if (pruebasebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype() == 3) {
                if (def.getDefectos().getCardefault() == 84031) {
                    objProp[9] = "Si";
                } else {
                    objProp[9] = "NO";
                }
            } else {
                objProp[9] = "NO";
            }
            if (pruebasebas.getHojaPruebas().getVehiculos().getCar() == 4) {
                if (def.getDefectos().getCardefault() == 84037) {
                    objProp[9] = "Si";
                } else {
                    objProp[9] = "NO";
                }
            } else {
                objProp[9] = "NO";
            }
            if (def.getDefectos().getCardefault() == 84000) {
                objProp[9] = "Si";
            } else {
                objProp[9] = "NO";
            }
            if (def.getDefectos().getCardefault() == 84009) {
                objProp[10] = "Si";
            } else {
                objProp[10] = "NO";
            }
            if (def.getDefectos().getCardefault() == 84010) {
                objProp[11] = "Si";
            } else {
                objProp[11] = "NO";
            }
            if (def.getDefectos().getCardefault() == 84011) {
                objProp[12] = "Si";
            } else {
                objProp[12] = "NO";
            }
            if (pruebasebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype() == 1) {
                if (def.getDefectos().getCardefault() == 84012) {
                    objProp[13] = "Si";
                } else {
                    objProp[13] = "NO";
                }
            } else {
                objProp[13] = "NO";
            }
            if (pruebasebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype() == 3) {
                if (def.getDefectos().getCardefault() == 84021) {
                    objProp[13] = "Si";
                } else {
                    objProp[13] = "NO";
                }
            } else {
                objProp[13] = "NO";
            }
//            if (def.getDefectos().getCardefault() == 15057 || def.getDefectos().getCardefault() == 80000) {
//                objProp[23] = "Si";
//            } else {
//                objProp[23] = "NO";
//            }

        }
        String humo = eq.findObs(pruebasebas.getIdPruebas(), "humo");

        if (humo.equals("SI")) {
            objProp[3] = "SI";
        } else {
            objProp[3] = "NO";
        }
        String revoluciones = eq.findObs(pruebasebas.getIdPruebas(), "Revoluciones");

        if (revoluciones.equals("SI")) {
            objProp[5] = "SI";
        } else {
            objProp[5] = "NO";
        }
        String fugas = eq.findObs(pruebasebas.getIdPruebas(), "fugas");

        if (fugas.equals("SI")) {
            objProp[6] = "SI";
        } else {
            objProp[6] = "NO";
        }
        String fugasTubo = eq.findObs(pruebasebas.getIdPruebas(), "fugas en el tubo");

        if (fugasTubo.equals("SI")) {
            objProp[6] = "SI";
        } else {
            objProp[6] = "NO";
        }
        String Accesorios = eq.findObs(pruebasebas.getIdPruebas(), "Instalación de accesorios o deformaciones");

        if (Accesorios.equals("SI")) {
            objProp[12] = "SI";
        } else {
            objProp[12] = "NO";
        }
        String tapones = eq.findObs(pruebasebas.getIdPruebas(), "Ausencia de tapas o tapones de combustible");

        if (tapones.equals("SI")) {
            objProp[9] = "SI";
        } else {
            objProp[9] = "NO";
        }
        String aceite = eq.findObs(pruebasebas.getIdPruebas(), "Ausencia de tapones de aceite");

        if (aceite.equals("SI")) {
            objProp[8] = "SI";
        } else {
            objProp[8] = "NO";
        }
        String salidas = eq.findObs(pruebasebas.getIdPruebas(), "Salidas adicionales en el sistema de escape");

        if (salidas.equals("SI")) {
            objProp[7] = "SI";
        } else {
            objProp[7] = "NO";
        }
        String sistema = eq.findObs(pruebasebas.getIdPruebas(), "Sistema de admisión de aire en mal estado");

        if (sistema.equals("SI")) {
            objProp[10] = "SI";
        } else {
            objProp[10] = "NO";
        }
        String desconexion = eq.findObs(pruebasebas.getIdPruebas(), "Desconexión de sistemas de recirculación");

        if (desconexion.equals("SI")) {
            objProp[11] = "SI";
        } else {
            objProp[11] = "NO";
        }
        String refrigeracion = eq.findObs(pruebasebas.getIdPruebas(), "Incorrecta operación del sistema de refrigeración");

        if (refrigeracion.equals("SI")) {
            objProp[13] = "SI";
        } else {
            objProp[13] = "NO";
        }
       
        if (pruebasebas.getAprobada().equals("Y")) {
            objProp[22] = "NO";
            objProp[23] = "APROBADO";
        } else if (pruebasebas.getAprobada().equals("N")) {
            objProp[23] = "RECHAZADO";
            objProp[22] = "SI";
            System.out.println("Prueba" + pruebasebas.getComentarioAborto());
            if (pruebasebas != null) {
                String comentarioAborto = pruebasebas.getComentarioAborto();

                if (comentarioAborto != null && !comentarioAborto.isEmpty()) {
                    //objProp[22] = comentarioAborto.equals("Condiciones Anormales") ? "SI" : "NO";
                    objProp[22] = "NO";
                } else {
                    objProp[22] = "SI";
                }
            } else {
                objProp[22] = "SI";
            }
        } else {
            objProp[23] = "ABORTADO";
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

        try {
            if (pruebasebas.getTipoPrueba().getTesttype() == 8) {
                CdaJpaController cda = new CdaJpaController();
                Software sw = new Software();
                Object[] info = cda.findmedellin();
                Cda mcda = cda.findCda(1);
                Object[] calibracionesEquipos = eq.findDosPuntos(pruebasebas.getFechaPrueba(), pruebasebas.getIdPruebas());
                String ser2 = pruebasebas.getSerialEquipo();
                List<String> serial = eq.listaSerial(ser2);
                System.out.println("serial" + serial);
                List<String> serialRPM = eq.listaRPM(serial.get(1));
                //String marca0 = eq.findmarca(serial.get(0));
                String marca1 = eq.findmarca(serial.get(1));
                String marca2 = eq.findmarca(serial.get(2));
                cal2[0] = calibracionesEquipos[23];
                cal2[1] = "AGPSP";
                if (serial.get(0) != null) {
                    cal2[2] = serial.get(0);
                } else {
                    cal2[2] = "NO Posee";
                }
                cal2[3] = "SENSORS";
                cal2[4] = "AMBII";
                if (serial.get(0) != null) {
                    cal2[5] = serial.get(0);
                } else {
                    cal2[5] = "NO Posee";
                }
                //cal2[6] = calibracionesEquipos[25];
                if (pruebasebas.getHojaPruebas().getVehiculos().getTiposGasolina().equals(3)) {
                    cal2[6] = "";
                } else {
                    cal2[6] = "0." + calibracionesEquipos[25];
                }
                cal2[7] = calibracionesEquipos[26];
                cal2[8] = marca1;
                if (serialRPM.get(0) != null) {
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
                    cal2[9] = "NO Posee";
                }
                if (pruebasebas.getHojaPruebas().getVehiculos().getCatalizador().equals("No") || pruebasebas.getHojaPruebas().getVehiculos().getCatalizador().equals("NO") || pruebasebas.getHojaPruebas().getVehiculos().getCatalizador().equals("N")) {
                    cal2[10] = marca1;
                } else {
                    cal2[10] = "";
                }

                if (serial.get(2) != null) {
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
                    cal2[11] = "NO Posee";
                }
                cal2[12] = marca2;
                if (serial.get(2) != null) {
                    cal2[13] = serial.get(2);
                } else {
                    cal2[13] = "NO Posee";
                }
                cal2[14] = marca2;
                if (serial.get(2) != null) {
                    cal2[15] = serial.get(2);
                } else {
                    cal2[15] = "NO Posee";
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

                String idEquipoCalibracion = eq.findIdEquipo(pruebasebas.getSerialEquipo());
                System.out.println("idEquipoCalibracion" + idEquipoCalibracion);

                Timestamp fechaFugas = eq.findFugas(pruebasebas.getFechaPrueba(), idEquipoCalibracion);
                System.out.println("fechaFugas" + sdfH.format(fechaFugas));

                cal2[22] = sdfH.format(fechaFugas);
                cal2[23] = sdfH.format(calibracionesEquipos[1]);
                cal2[24] = calibracionesEquipos[56];
                cal2[25] = calibracionesEquipos[57];
                cal2[26] = calibracionesEquipos[58];
                cal2[27] = calibracionesEquipos[59];
                cal2[28] = calibracionesEquipos[60];
                cal2[29] = calibracionesEquipos[61];
                System.out.println("dato" + calibracionesEquipos[25]);
                Double dato = Double.parseDouble(calibracionesEquipos[25].toString());
                Double HCalta = (Double) calibracionesEquipos[47];
                Double pef = dato / 1000;
                cal2[30] = HCalta / pef;
                cal2[31] = calibracionesEquipos[47];
                Double Hcbaja = ((Double) calibracionesEquipos[44]);
                cal2[32] = Hcbaja / pef;
                cal2[33] = calibracionesEquipos[44];
                DecimalFormat df = new DecimalFormat("#.###");
                cal2[34] = df.format(calibracionesEquipos[45]);
                cal2[35] = df.format(calibracionesEquipos[42]);
                cal2[36] = df.format(calibracionesEquipos[46]);
                cal2[37] = df.format(calibracionesEquipos[43]);
                cal2[38] = 0;
                cal2[39] = 0;
                Double rhcalta = ((Double) calibracionesEquipos[55]);
                cal2[40] = df.format(rhcalta / pef);
                cal2[41] = df.format(calibracionesEquipos[55]);
                Double rhcbaja = ((Double) calibracionesEquipos[51]);
                cal2[42] = df.format(rhcbaja / pef);
                cal2[43] = df.format(calibracionesEquipos[51]);
                cal2[44] = df.format(calibracionesEquipos[52]);
                cal2[45] = df.format(calibracionesEquipos[48]);
                cal2[46] = df.format(calibracionesEquipos[53]);
                cal2[47] = df.format(calibracionesEquipos[50]);
                cal2[48] = df.format(calibracionesEquipos[54]);
                cal2[49] = df.format(calibracionesEquipos[49]);
                cal2[50] = "";
                cal2[51] = "APROBADA";

                //}
                //}
            }

            /* modeloDatosEquipoYSoftware = new DefaultTableModel();
            modeloDatosEquipoYSoftware.addColumn("MARCA_AG");//0
            modeloDatosEquipoYSoftware.addColumn("MOD_AG");//1
            modeloDatosEquipoYSoftware.addColumn("SERIAL_AG");//2
            modeloDatosEquipoYSoftware.addColumn("MARCA_BG");//3
            modeloDatosEquipoYSoftware.addColumn("MOD_BG");//4
            modeloDatosEquipoYSoftware.addColumn("SERIAL_BG");//5
            modeloDatosEquipoYSoftware.addColumn("PEF");//6
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
            modeloDatosEquipoYSoftware.addColumn("TIP_IDE_VGP");//19
            modeloDatosEquipoYSoftware.addColumn("NUM_IDE_VGP");//20
            modeloDatosEquipoYSoftware.addColumn("NOM_VGP");//21
            modeloDatosEquipoYSoftware.addColumn("F_FUGAS");//22
            modeloDatosEquipoYSoftware.addColumn("F_VGP");//23
            modeloDatosEquipoYSoftware.addColumn("P_ALTA_LAB");//24
            modeloDatosEquipoYSoftware.addColumn("P_ALTA_CIL");//25
            modeloDatosEquipoYSoftware.addColumn("P_ALTA_CER");//26
            modeloDatosEquipoYSoftware.addColumn("P_BAJA_LAB");//27
            modeloDatosEquipoYSoftware.addColumn("P_BAJA_CIL");//28
            modeloDatosEquipoYSoftware.addColumn("P_BAJA_CER");//29
            modeloDatosEquipoYSoftware.addColumn("P_HC_ALTO_P");//30
            modeloDatosEquipoYSoftware.addColumn("P_HC_ALTO_H");//31
            modeloDatosEquipoYSoftware.addColumn("P_HC_BAJO_P");//32
            modeloDatosEquipoYSoftware.addColumn("P_HC_BAJO_H");//33
            modeloDatosEquipoYSoftware.addColumn("P_CO_ALTO");//34
            modeloDatosEquipoYSoftware.addColumn("P_CO_BAJO");//35
            modeloDatosEquipoYSoftware.addColumn("P_CO2_ALTO");//36
            modeloDatosEquipoYSoftware.addColumn("P_CO2_BAJO");//37
            modeloDatosEquipoYSoftware.addColumn("P_O2_ALTO");//38
            modeloDatosEquipoYSoftware.addColumn("P_O2_BAJO");//39
            modeloDatosEquipoYSoftware.addColumn("R_HC_ALTO_P");//40
            modeloDatosEquipoYSoftware.addColumn("R_HC_ALTO_H");//41
            modeloDatosEquipoYSoftware.addColumn("R_HC_BAJO_P");//42
            modeloDatosEquipoYSoftware.addColumn("R_HC_BAJO_H");//43
            modeloDatosEquipoYSoftware.addColumn("R_CO_ALTO");//44
            modeloDatosEquipoYSoftware.addColumn("R_CO_BAJO");//45
            modeloDatosEquipoYSoftware.addColumn("R_CO2_ALTO");//46
            modeloDatosEquipoYSoftware.addColumn("R_CO2_BAJO");//47
            modeloDatosEquipoYSoftware.addColumn("R_O2_ALTO");//48
            modeloDatosEquipoYSoftware.addColumn("R_O2_BAJO");//49
            modeloDatosEquipoYSoftware.addColumn("C_VGP");//50
            modeloDatosEquipoYSoftware.addColumn("R_VGP");//51 */
        } catch (Exception e) {
            e.printStackTrace();
            cal2 = new Object[52];
        }

        modeloDatosEquipoYSoftware.addRow(cal2);

    }


}
