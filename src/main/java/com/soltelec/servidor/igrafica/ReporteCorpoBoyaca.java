/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.igrafica;

import com.soltelec.servidor.consultas.Reportes;
import com.soltelec.servidor.dtos.DatosCda;
import com.soltelec.servidor.dtos.reporte_corpoboyaca.Corpoboyaca;
import com.soltelec.servidor.dtos.reporte_corpoboyaca.EquipoAnalizadorBoyaca;
import com.soltelec.servidor.dtos.reporte_corpoboyaca.PropietarioBoyaca;
import com.soltelec.servidor.dtos.reporte_corpoboyaca.PruebasBoyaca;
import com.soltelec.servidor.dtos.reporte_corpoboyaca.ResultadoDieselBoyaca;
import com.soltelec.servidor.dtos.reporte_corpoboyaca.ResultadoOttoBoyaca;
import com.soltelec.servidor.dtos.reporte_corpoboyaca.VehiculoBoyaca;
import com.soltelec.servidor.utils.GenericExportExcel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;
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
public class ReporteCorpoBoyaca extends javax.swing.JInternalFrame {

    private DefaultTableModel modeloCDA;
    private DefaultTableModel pruebas;
    private DefaultTableModel datosEquipo;
    private DefaultTableModel propietario;
    private DefaultTableModel datosDelVehiculo;
    private DefaultTableModel resultadoDeLaPrueba;

    public ReporteCorpoBoyaca() {
        super("Reporte CorpoBoyaca",
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
        modeloCDA.addColumn("TOTAL_M4T5365");//14
        modeloCDA.addColumn("TOTAL_M2T5365");//14

        datosEquipo = new DefaultTableModel();
        datosEquipo.addColumn("TIP_IDE_PROP");
        datosEquipo.addColumn("NUM_ID_PROP");
        datosEquipo.addColumn("NOM_PROP");
        datosEquipo.addColumn("DIR_PROP");
        datosEquipo.addColumn("TEL1_PROP");
        datosEquipo.addColumn("TEL2_PROP");
        datosEquipo.addColumn("MUN_PROP");
        datosEquipo.addColumn("CORR_E_PROP");


        /*
        *
        *Informacion Vehidculo
        *
         */
        pruebas = new DefaultTableModel();
        pruebas.addColumn("PLACA");//si
        pruebas.addColumn("MODELO");
        pruebas.addColumn("NUM_MOTOR");//si
        pruebas.addColumn("VIN");//si
        pruebas.addColumn("CILINDRAJE");//si
        pruebas.addColumn("LIC_TRANS");//si
        pruebas.addColumn("KM");//si
        pruebas.addColumn("GNV_CONV");//si
        pruebas.addColumn("GNV_CONV_V");
        pruebas.addColumn("MARCA");//si
        pruebas.addColumn("LINEA");//si
        pruebas.addColumn("CLASE");//si
        pruebas.addColumn("SERVICIO");//si
        pruebas.addColumn("TIP_COMB");
        pruebas.addColumn("TIP_MOTOR");
        pruebas.addColumn("RPM_FAB");//si
        pruebas.addColumn("RAL_MIN_FAB");
        pruebas.addColumn("RAL_MAX_FAB");
        pruebas.addColumn("DIS_MOTOR");
        pruebas.addColumn("NUM_ESCAPE");
//         

/////////Equipos y software
        propietario = new DefaultTableModel();
        propietario.addColumn("MARCA_AG");//0
        propietario.addColumn("MOD_AG");//1
        propietario.addColumn("SERIAL_AG");//2
        propietario.addColumn("MARCA_BG");//3
        propietario.addColumn("MOD_BG");//4
        propietario.addColumn("SERIAL_BG");//5
        propietario.addColumn("PEF");//6
        propietario.addColumn("SERIAL_E");//7
        propietario.addColumn("MARCA_RPM");//8
        propietario.addColumn("SERIAL_RPM");//9
        propietario.addColumn("MARCA_TEMP_M");//10
        propietario.addColumn("SERIAL_TEMP_M");//11
        propietario.addColumn("MARCA_TEMP_A");//12
        propietario.addColumn("SERIAL_TEMP_A");//13
        propietario.addColumn("MARCA_HUM_R");//14
        propietario.addColumn("SERIAL_HUM_R");//15
        propietario.addColumn("NOM_SOFT");//16
        propietario.addColumn("VER_SOFT");//17
        propietario.addColumn("DES_SOFT");//18
        propietario.addColumn("TIP_IDE_VGP");//19
        propietario.addColumn("NUM_IDE_VGP");//20
        propietario.addColumn("NOM_VGP");//21
        propietario.addColumn("F_FUGAS");//22
        propietario.addColumn("F_VGP");//23
        propietario.addColumn("P_ALTA_LAB");//24
        propietario.addColumn("P_ALTA_CIL");//25
        propietario.addColumn("P_ALTA_CER");//26
        propietario.addColumn("P_BAJA_LAB");//27
        propietario.addColumn("P_BAJA_CIL");//28
        propietario.addColumn("P_BAJA_CER");//29
        propietario.addColumn("P_HC_ALTO_P");//30
        propietario.addColumn("P_HC_ALTO_H");//31
        propietario.addColumn("P_HC_BAJO_P");//32
        propietario.addColumn("P_HC_BAJO_H");//33
        propietario.addColumn("P_CO_ALTO");//34
        propietario.addColumn("P_CO_BAJO");//35
        propietario.addColumn("P_CO2_ALTO");//36
        propietario.addColumn("P_CO2_BAJO");//37
        propietario.addColumn("P_O2_ALTO");//38
        propietario.addColumn("P_O2_BAJO");//39
        propietario.addColumn("R_HC_ALTO_P");//40
        propietario.addColumn("R_HC_ALTO_H");//41
        propietario.addColumn("R_HC_BAJO_P");//42
        propietario.addColumn("R_HC_BAJO_H");//43
        propietario.addColumn("R_CO_ALTO");//44
        propietario.addColumn("R_CO_BAJO");//45
        propietario.addColumn("R_CO2_ALTO");//46
        propietario.addColumn("R_CO2_BAJO");//47
        propietario.addColumn("R_O2_ALTO");//48
        propietario.addColumn("R_O2_BAJO");//49
        propietario.addColumn("C_VGP");//50
        propietario.addColumn("R_VGP");//51

        //Tabla datos generales de inspeccion
        datosDelVehiculo = new DefaultTableModel();
        datosDelVehiculo.addColumn("TIP_IDE_DT");
        datosDelVehiculo.addColumn("NUM_IDE_DT");
        datosDelVehiculo.addColumn("NOM_DT");
        datosDelVehiculo.addColumn("TIP_IDE_IT");
        datosDelVehiculo.addColumn("NUM_IDE_IT");
        datosDelVehiculo.addColumn("NOM_IT");
        datosDelVehiculo.addColumn("NUM_FUR");
        datosDelVehiculo.addColumn("FECHA_FUR");
        datosDelVehiculo.addColumn("CONS_RUNT");
        datosDelVehiculo.addColumn("FUR_ASOC");
        datosDelVehiculo.addColumn("CERT_RTMYG");
        datosDelVehiculo.addColumn("F_INI_INSP");
        datosDelVehiculo.addColumn("F_FIN_INSP");
        datosDelVehiculo.addColumn("F_ABORTO");
        datosDelVehiculo.addColumn("C_ABORTO");
        datosDelVehiculo.addColumn("CATALIZADOR");
        datosDelVehiculo.addColumn("T_AMB");
        datosDelVehiculo.addColumn("H_REL");

        //
        resultadoDeLaPrueba = new DefaultTableModel();
        resultadoDeLaPrueba.addColumn("T_MOTOR");
        resultadoDeLaPrueba.addColumn("RPM_RAL");
        resultadoDeLaPrueba.addColumn("HUMO");
        resultadoDeLaPrueba.addColumn("CORR_O2");
        resultadoDeLaPrueba.addColumn("RPM_FUERA");
        resultadoDeLaPrueba.addColumn("FUGA_TUBO");
        resultadoDeLaPrueba.addColumn("SALIDAS_AD");
        resultadoDeLaPrueba.addColumn("FUGA_ACEITE");
        resultadoDeLaPrueba.addColumn("FUGA_COMB");
        resultadoDeLaPrueba.addColumn("R_HC_RAL");
        resultadoDeLaPrueba.addColumn("R_CO_RAL");
        resultadoDeLaPrueba.addColumn("R_CO2_RAL");
        resultadoDeLaPrueba.addColumn("R_O2_RAL");
        resultadoDeLaPrueba.addColumn("R_GAS_SCOR");
        resultadoDeLaPrueba.addColumn("NC_EMISIONES");
        resultadoDeLaPrueba.addColumn("RES_FINAL");

        /*
        *
        *Informacion Medidas
        *
         */
    }

    private void fillData(Date fechaInicial, Date fechaFinal){

        if(option == -1){
            JOptionPane.showMessageDialog(null, "Seleccione un tipo", "Error", JOptionPane.ERROR_MESSAGE);
            throw new IllegalStateException("Bad option");
        } 

        Corpoboyaca todosLosDatos = Reportes.getCorpoboyaca(fechaInicial, fechaFinal, option);

        List<DatosCda> datosCda = todosLosDatos.getCda();
        List<EquipoAnalizadorBoyaca> analizador = todosLosDatos.getAnalizador();
        List<PruebasBoyaca> datosPrueba = todosLosDatos.getDatosPrueba();
        List<PropietarioBoyaca> datosDePropietarios = todosLosDatos.getPropietarioBoyacas();
        List<VehiculoBoyaca> datosVehiculo = todosLosDatos.getVehiculoBoyaca();
        List<ResultadoOttoBoyaca> datosOtto = todosLosDatos.getResultadoOtto();
        List<ResultadoDieselBoyaca> datosDiesel = todosLosDatos.getResultadoDiesel();

        modeloCDA = new DefaultTableModel();
        modeloCDA.addColumn("No. CDA");
        modeloCDA.addColumn("Nombre CDA");
        modeloCDA.addColumn("NIT CDA");
        modeloCDA.addColumn("Direccion CDA");
        modeloCDA.addColumn("Telefono 1");
        modeloCDA.addColumn("Telefono 2");
        modeloCDA.addColumn("ciudad CDA");
        modeloCDA.addColumn("No. resolucion CDA");
        modeloCDA.addColumn("fecha resolucion CDA");//9

        datosCda.stream().forEach(datoCda -> {
            Object[] fila = {
                datoCda.getCm(),
                datoCda.getNombreCda(),
                datoCda.getNIT(),
                datoCda.getDireccion(),
                datoCda.getTelefono(),
                "",
                datoCda.getCiudad(),
                datoCda.getNoResolucionAuthAmbiental(),
                datoCda.getFechaResolucionAuthAmbiental(),
            };
            modeloCDA.addRow(fila);
        });

        datosEquipo = new DefaultTableModel();
        if(option < 2){
            datosEquipo.addColumn("Vr pef");
            datosEquipo.addColumn("No. serie banco");
            datosEquipo.addColumn("No. serie analizador");
            datosEquipo.addColumn("marca analizador");
            datosEquipo.addColumn("HC bajo");
            datosEquipo.addColumn("CO bajo");
            datosEquipo.addColumn("CO2 bajo");
            datosEquipo.addColumn("HC alto");
            datosEquipo.addColumn("CO alto");
            datosEquipo.addColumn("CO2 alto");
            datosEquipo.addColumn("fecha y hora verificacion");
            datosEquipo.addColumn("Nombre del software");
            datosEquipo.addColumn("version del software");

            analizador.stream().forEach(datosAnalizador -> {
                Object[] fila = {
                    datosAnalizador.getVrPef(),
                    datosAnalizador.getNoSerieBanco(),
                    datosAnalizador.getNoSerieAnalizador(),
                    datosAnalizador.getMarcaAnalizador(),
                    datosAnalizador.getHcBaja(),
                    datosAnalizador.getCoBaja(),
                    datosAnalizador.getCo2Baja(),
                    datosAnalizador.getHcAlta(),
                    datosAnalizador.getCoAlta(),
                    datosAnalizador.getCo2Alta(),
                    datosAnalizador.getFechaVerificacion(),
                    datosAnalizador.getSoftware(),
                    datosAnalizador.getVSoftware()
                };
                datosEquipo.addRow(fila);
            });
        }else{
            datosEquipo.addColumn("Serie opacimetro");
            datosEquipo.addColumn("Marca opacimetro");
            datosEquipo.addColumn("Nombre del software");
            datosEquipo.addColumn("version del software");

            analizador.stream().forEach(datosAnalizador -> {
                Object[] fila = {
                    datosAnalizador.getNoSerieAnalizador(),
                    datosAnalizador.getMarcaAnalizador(),
                    datosAnalizador.getSoftware(),
                    datosAnalizador.getVSoftware()
                };
                datosEquipo.addRow(fila);
            });
        }

        pruebas = new DefaultTableModel();
        pruebas.addColumn("No consecutivo prueba");//si
        pruebas.addColumn("Fecha y hora inicio");
        pruebas.addColumn("Fecha y hora fin");//si
        pruebas.addColumn("fecha y hora aborto");//si
        pruebas.addColumn("Inspector que realiza la prueba");//si
        pruebas.addColumn("Temperatura ambiente");//si
        pruebas.addColumn("Humedad relativa");//si
        pruebas.addColumn("Causal aborto");//si

        datosPrueba.stream().forEach(datoPrueba -> {
            Object[] fila = {
                datoPrueba.getNoPrueba(),
                datoPrueba.getFechaInicio(),
                datoPrueba.getFechaFin(),
                datoPrueba.getFechaAborto(),
                datoPrueba.getInspector(),
                datoPrueba.getTemperaturaAmbiente(),
                datoPrueba.getHumedadRelativa(),
                datoPrueba.getCausaAborto()
            };
            pruebas.addRow(fila);
        });

        propietario = new DefaultTableModel();
        propietario.addColumn("Nombre completo");//0
        propietario.addColumn("Tipo de documento");//1
        propietario.addColumn("No. documento");//2
        propietario.addColumn("Direccion");//3
        propietario.addColumn("Telefono 1");//4
        propietario.addColumn("Telefono 2");//4
        propietario.addColumn("Ciudad");//5
        
        datosDePropietarios.stream().forEach(owner -> {
            Object[] fila = {
                owner.getNombre(),
                owner.getTipoDocumento(),
                owner.getNoDocumento(),
                owner.getDireccion(),
                owner.getTelefono1(),
                owner.getTelefono2(),
                owner.getCiudad(),
            };
            propietario.addRow(fila);
        });

        datosDelVehiculo = new DefaultTableModel();
        if(option != 0){
            datosDelVehiculo.addColumn("Marca");
            datosDelVehiculo.addColumn("Linea");
            datosDelVehiculo.addColumn("Modelo");
            datosDelVehiculo.addColumn("Placa");
            datosDelVehiculo.addColumn("Cilindraje");
            datosDelVehiculo.addColumn("Clase");
            datosDelVehiculo.addColumn("Servicio");
            datosDelVehiculo.addColumn("Combustible");
            datosDelVehiculo.addColumn("No Motor");
            datosDelVehiculo.addColumn("Vin");
            datosDelVehiculo.addColumn("No licencia");
            datosDelVehiculo.addColumn("Kilometraje");

            datosVehiculo.stream().forEach(datoVehiculo -> {
                Object[] fila = {
                    datoVehiculo.getMarca(),
                    datoVehiculo.getLinea(),
                    datoVehiculo.getModelo(),
                    datoVehiculo.getPlaca(),
                    datoVehiculo.getCilindraje(),
                    datoVehiculo.getClase(),
                    datoVehiculo.getServicio(),
                    datoVehiculo.getCombustible(),
                    datoVehiculo.getNoMotor(),
                    datoVehiculo.getVin(),
                    datoVehiculo.getNoLicencia(),
                    datoVehiculo.getKm()
                };
                datosDelVehiculo.addRow(fila);
            });
        }else{
            datosDelVehiculo.addColumn("Marca");
            datosDelVehiculo.addColumn("TipoMotor");
            datosDelVehiculo.addColumn("Linea");
            datosDelVehiculo.addColumn("Diseño");
            datosDelVehiculo.addColumn("Modelo");
            datosDelVehiculo.addColumn("Placa");
            datosDelVehiculo.addColumn("Cilindraje");
            datosDelVehiculo.addColumn("Clase");
            datosDelVehiculo.addColumn("Servicio");
            datosDelVehiculo.addColumn("Combustible");
            datosDelVehiculo.addColumn("No Motor");
            datosDelVehiculo.addColumn("Vin");
            datosDelVehiculo.addColumn("No licencia");
            datosDelVehiculo.addColumn("Kilometraje");

            datosVehiculo.stream().forEach(datoVehiculo -> {
                Object[] fila = {
                    datoVehiculo.getMarca(),
                    datoVehiculo.getTipoMotor(),
                    datoVehiculo.getLinea(),
                    datoVehiculo.getDesing(),
                    datoVehiculo.getModelo(),
                    datoVehiculo.getPlaca(),
                    datoVehiculo.getCilindraje(),
                    datoVehiculo.getClase(),
                    datoVehiculo.getServicio(),
                    datoVehiculo.getCombustible(),
                    datoVehiculo.getNoMotor(),
                    datoVehiculo.getVin(),
                    datoVehiculo.getNoLicencia(),
                    datoVehiculo.getKm()
                };
                datosDelVehiculo.addRow(fila);
            });
        }


        resultadoDeLaPrueba = new DefaultTableModel();
        if (option != 2) {
            if(option == 1){
                resultadoDeLaPrueba.addColumn("fugas tubo escape");//0
                resultadoDeLaPrueba.addColumn("fugas silenciador");//1
                resultadoDeLaPrueba.addColumn("accesorios o deformaciones tubo de escape");//2
                resultadoDeLaPrueba.addColumn("tapa de combustible o fugas");//3
                resultadoDeLaPrueba.addColumn("tapa aceite o fugas");//4
                resultadoDeLaPrueba.addColumn("sistema adquisicion aire");//5
                resultadoDeLaPrueba.addColumn("salidas adicionales diseño");//6
                resultadoDeLaPrueba.addColumn("PVC");//7
                resultadoDeLaPrueba.addColumn("Presencia humo negro, azul");//8
                resultadoDeLaPrueba.addColumn("Revoluciones fuera rango");//9
                resultadoDeLaPrueba.addColumn("falla sistema refrigeracion");//10
                resultadoDeLaPrueba.addColumn("temperatura motor");//11
                resultadoDeLaPrueba.addColumn("rpm ralenti");//12
                resultadoDeLaPrueba.addColumn("hc ralenti");//13
                resultadoDeLaPrueba.addColumn("co ralenti");//14
                resultadoDeLaPrueba.addColumn("co2 ralenti");//15
                resultadoDeLaPrueba.addColumn("O2 ralenti");//16
                resultadoDeLaPrueba.addColumn("rpm crucero");//17
                resultadoDeLaPrueba.addColumn("hc crucero");//18
                resultadoDeLaPrueba.addColumn("co crucero");//19
                resultadoDeLaPrueba.addColumn("co2 crucero");//20
                resultadoDeLaPrueba.addColumn("O2 crucero");//21
                resultadoDeLaPrueba.addColumn("Incumplimiento de niveles");//22
                resultadoDeLaPrueba.addColumn("Concepto final");//23
                resultadoDeLaPrueba.addColumn("Ruido");//23
            }

            if(option == 1){
                datosOtto.stream().forEach(resultado -> {
                    Object[] fila = {
                        resultado.getFugaExosto(),//0
                        resultado.getFugaSilenciador(),//1
                        resultado.getAccesoriosTubo(),//2
                        resultado.getTapaCombustible(),//3
                        resultado.getTapaAceite(),//4
                        resultado.getSistemaAdqusicionAire(),//5
                        resultado.getSalidasAdicionales(),//6
                        resultado.getPcv(),//7
                        resultado.getPresenciaHumo(),//8
                        resultado.getRpmFueraRango(),//9
                        resultado.getFallaRefrigeracion(),//10
                        resultado.getTempMotor(),//11
                        resultado.getRpmRalenti(),//12
                        resultado.getHcRalenti(),//13
                        resultado.getCoRalenti(),//14
                        resultado.getCo2Ralenti(),//15
                        resultado.getO2Ralenti(),//16
                        resultado.getRpmCrucero(),//17
                        resultado.getHcCrucero(),//18
                        resultado.getCoCrucero(),//19
                        resultado.getCo2Crucero(),//20
                        resultado.getO2Crucero(),//21
                        resultado.getIncumplimientoEmision(),//22
                        resultado.getConceptoFinal(),//18
                        resultado.getRuido()
                    };
                    resultadoDeLaPrueba.addRow(fila);
                });
            }else{


                resultadoDeLaPrueba.addColumn("fugas tubo escape");//0
                resultadoDeLaPrueba.addColumn("fugas silenciador");//1
                resultadoDeLaPrueba.addColumn("accesorios o deformaciones tubo de escape");//2
                resultadoDeLaPrueba.addColumn("tapa de combustible o fugas");//3
                resultadoDeLaPrueba.addColumn("tapa aceite o fugas");//4
                resultadoDeLaPrueba.addColumn("sistema adquisicion aire");//5
                resultadoDeLaPrueba.addColumn("salidas adicionales diseño");//6
                resultadoDeLaPrueba.addColumn("PVC");//7
                resultadoDeLaPrueba.addColumn("Presencia humo negro, azul");//8
                resultadoDeLaPrueba.addColumn("Revoluciones fuera rango");//9
                resultadoDeLaPrueba.addColumn("falla sistema refrigeracion");//10
                resultadoDeLaPrueba.addColumn("temperatura motor");//11
                resultadoDeLaPrueba.addColumn("rpm ralenti");//12
                resultadoDeLaPrueba.addColumn("hc ralenti");//13
                resultadoDeLaPrueba.addColumn("co ralenti");//14
                resultadoDeLaPrueba.addColumn("co2 ralenti");//15
                resultadoDeLaPrueba.addColumn("O2 ralenti");//16
                resultadoDeLaPrueba.addColumn("Incumplimiento de niveles");//17
                resultadoDeLaPrueba.addColumn("Concepto final");//18
                resultadoDeLaPrueba.addColumn("Ruido");//18

                datosOtto.stream().forEach(resultado -> {
                    Object[] fila = {
                        resultado.getFugaExosto(),//0
                        resultado.getFugaSilenciador(),//1
                        resultado.getAccesoriosTubo(),//2
                        resultado.getTapaCombustible(),//3
                        resultado.getTapaAceite(),//4
                        resultado.getSistemaAdqusicionAire(),//5
                        resultado.getSalidasAdicionales(),//6
                        resultado.getPcv(),//7
                        resultado.getPresenciaHumo(),//8
                        resultado.getRpmFueraRango(),//9
                        resultado.getFallaRefrigeracion(),//10
                        resultado.getTempMotor(),//11
                        resultado.getRpmRalenti(),//12
                        resultado.getHcRalenti(),//13
                        resultado.getCoRalenti(),//14
                        resultado.getCo2Ralenti(),//15
                        resultado.getO2Ralenti(),//16
                        resultado.getIncumplimientoEmision(),//17
                        resultado.getConceptoFinal(),//18
                        resultado.getRuido()
                    };
                    resultadoDeLaPrueba.addRow(fila);
                });
            }
        }else{

            resultadoDeLaPrueba.addColumn("fugas tubo escape");//0
            resultadoDeLaPrueba.addColumn("fugas silenciador");//1
            resultadoDeLaPrueba.addColumn("tapa de combustible o fugas");//2
            resultadoDeLaPrueba.addColumn("tapa aceite o fugas");//3
            resultadoDeLaPrueba.addColumn("Accesorios o deformaciones tubo de escape");//4
            resultadoDeLaPrueba.addColumn("salidas adicionales diseño");//5
            resultadoDeLaPrueba.addColumn("Filtro aire");//6
            resultadoDeLaPrueba.addColumn("Sistema refrigeracion");//7
            resultadoDeLaPrueba.addColumn("Revoluciones inestables o fuera rango");//8
            resultadoDeLaPrueba.addColumn("Mal funcionamiento del motor");//9
            resultadoDeLaPrueba.addColumn("Funcionamiento del sistema de control de velocidad del motor");//10
            resultadoDeLaPrueba.addColumn("Dispositivos que alteran las rpm");//11
            resultadoDeLaPrueba.addColumn("temp inicial motor");//12
            resultadoDeLaPrueba.addColumn("velocidad no alcanzada en 5 segundos");//13
            resultadoDeLaPrueba.addColumn("Falla subita Motor");//14
            resultadoDeLaPrueba.addColumn("rpm velocidad gobernada mediana");//15
            resultadoDeLaPrueba.addColumn("rpm ralenti");//16
            resultadoDeLaPrueba.addColumn("resultado densidad ciclo preliminar");//17
            resultadoDeLaPrueba.addColumn("rpm gobernada ciclo preliminar");//18
            resultadoDeLaPrueba.addColumn("resultado dencidad ciclo 1");//19
            resultadoDeLaPrueba.addColumn("rpm gobernada ciclo 1");//20
            resultadoDeLaPrueba.addColumn("resultado dencidad ciclo 2");//21
            resultadoDeLaPrueba.addColumn("rpm gobernada ciclo 2");//22
            resultadoDeLaPrueba.addColumn("resultado dencidad ciclo 3");//23
            resultadoDeLaPrueba.addColumn("rpm gobernada ciclo 3");//24
            resultadoDeLaPrueba.addColumn("LTOE");//25
            resultadoDeLaPrueba.addColumn("Temp final del motor");//26
            resultadoDeLaPrueba.addColumn("falla por temp motor");//27
            resultadoDeLaPrueba.addColumn("inestabilidad durante ciclos de medicion");//28
            resultadoDeLaPrueba.addColumn("Diferencias aritmeticas");//29
            resultadoDeLaPrueba.addColumn("Resultado final promedio");//30
            resultadoDeLaPrueba.addColumn("Incumplimiento niveles");//31
            resultadoDeLaPrueba.addColumn("Resultado prueba");//32
            resultadoDeLaPrueba.addColumn("Ruido");//33

            datosDiesel.stream().forEach(resultado -> {
                Object[] fila = {
                    resultado.getFugaTubo(),//0
                    resultado.getFugaSilenciador(),//1
                    resultado.getTapaCombustible(),//2
                    resultado.getTapaAceite(),//3
                    resultado.getAccesorios(),//4
                    resultado.getSalidasAdicionales(),//5
                    resultado.getFiltroAire(),//6
                    resultado.getRefrigeracion(),//7
                    resultado.getRpmFueraRango(),//8
                    resultado.getMalFuncionamiento(),//9
                    resultado.getControlVelocidadMotor(),//10
                    resultado.getDispositivosRpm(),//11
                    resultado.getTempMotor(),//12
                    resultado.getVelocidad5s(),//13
                    resultado.getFallaMotor(),//14
                    resultado.getRpmGovernadaMediana(),//15
                    resultado.getRpmRalenti(),//16
                    resultado.getDensidadPreliminar(),//17
                    resultado.getRpmGobernadaPreliminar(),//18
                    resultado.getDencidadC1(),//19
                    resultado.getGobernadaC1(),//20
                    resultado.getDencidadC2(),//21
                    resultado.getGobernadaC2(),//22
                    resultado.getDencidadC3(),//23
                    resultado.getGobernadaC3(),//24
                    resultado.getLtoe(),//25
                    resultado.getTempFinalMotor(),//26
                    resultado.getFallaMotor(),//27
                    resultado.getInstabilidadCiclos(),//28
                    resultado.getDiferenciaAritmetica(),//29
                    resultado.getPromedio(),//30
                    resultado.getIncumplimientoNiveles(),//31
                    resultado.getResultadoPrueba(),//32
                    resultado.getRuido()//33
                };
                resultadoDeLaPrueba.addRow(fila);
            });
        }
        

        this.tblCda.setModel(modeloCDA);
        this.tblPruebas.setModel(pruebas);
        // this.tblMedidas.setModel(modeloMedidas);
        this.tblEquipoAnlizador.setModel(datosEquipo);
        this.tblDatosPropietario.setModel(propietario);
        this.tblDatosVehiculo.setModel(datosDelVehiculo);
        this.tblResultadoPrueba.setModel(resultadoDeLaPrueba);

        tblCda.setEnabled(false);
        tblPruebas.setEnabled(false);
        tblMedidas.setEnabled(false);
        tblEquipoAnlizador.setEnabled(false);
        tblDatosPropietario.setEnabled(false);
        tblDatosVehiculo.setEnabled(false);
        tblResultadoPrueba.setEnabled(false);
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
        ottoMotos = new javax.swing.JRadioButton("OTTO motos");
        otto = new javax.swing.JRadioButton("OTTO");
        diesel = new javax.swing.JRadioButton("Diesel");
        infPrueba = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPruebas = new javax.swing.JTable();
        jScrollPane10 = new javax.swing.JScrollPane();
        jScrollPane11 = new javax.swing.JScrollPane();
        jScrollPane12 = new javax.swing.JScrollPane();
        jScrollPane13 = new javax.swing.JScrollPane();
        jScrollPane14 = new javax.swing.JScrollPane();
        jScrollPane15 = new javax.swing.JScrollPane();
        tblMedidas = new javax.swing.JTable();
        tblCda = new javax.swing.JTable();
        tblEquipoAnlizador = new javax.swing.JTable();
        tblDatosPropietario = new javax.swing.JTable();
        tblDatosVehiculo = new javax.swing.JTable();
        tblResultadoPrueba = new javax.swing.JTable();

        ButtonGroup group = new ButtonGroup();
        group.add(ottoMotos);
        group.add(otto);
        group.add(diesel);

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

        ottoMotos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ottoMotosActionPerformed(evt);
            }
        });

        otto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ottoActionPerformed(evt);
            }
        });

        diesel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dieselActionPerformed(evt);
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

        tblEquipoAnlizador.setModel(datosEquipo);
        tblEquipoAnlizador.setName("Equipo Analizador."); // NOI18N
        jScrollPane12.setViewportView(tblEquipoAnlizador);
        infPrueba.addTab("Inf. Equipo Analizador", jScrollPane12);

        //Tabla de Modelo d einformacion Vehiculo		
        tblPruebas.setModel(pruebas);
        tblPruebas.setName("Datos Prueba"); // NOI18N
        jScrollPane1.setViewportView(tblPruebas);
        infPrueba.addTab("Inf. Datos Prueba", jScrollPane1);

        //Tabla d eModelo Medidas
//        tblMedidas.setModel(modeloMedidas);
//        tblMedidas.setName("Información del Medidas"); // NOI18N		
//        jScrollPane10.setViewportView(tblMedidas);
//        infPrueba.addTab("Inf. Medidas", jScrollPane10);
        //Tabla de Equipos
        tblDatosPropietario.setModel(propietario);
        tblDatosPropietario.setName("Datos propietario"); // NOI18N		
        jScrollPane13.setViewportView(tblDatosPropietario);
        infPrueba.addTab("Inf. Datos propietario", jScrollPane13);

        //Tabla datos generales inspeccion
        tblDatosVehiculo.setModel(datosDelVehiculo);
        tblDatosVehiculo.setName("Datos vehiculo"); // NOI18N		
        jScrollPane14.setViewportView(tblDatosVehiculo);
        infPrueba.addTab("Inf. Datos vehiculo", jScrollPane14);

        //Tabla 
        tblResultadoPrueba.setModel(resultadoDeLaPrueba);
        tblResultadoPrueba.setName("Datos resultado prueba"); // NOI18N		
        jScrollPane15.setViewportView(tblResultadoPrueba);
        infPrueba.addTab("Inf. Datos resultado prueba", jScrollPane15);

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
                            .addGap(18, 18, 18)
                            .addComponent(ottoMotos)
                            .addComponent(otto)
                            .addComponent(diesel)
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
                                        .addComponent(btnExportar)
                                        .addComponent(ottoMotos)
                                        .addComponent(otto)
                                        .addComponent(diesel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(infPrueba, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                                .addGap(282, 282, 282))
        );

        infPrueba.getAccessibleContext().setAccessibleName("Prueba de luces");
        infPrueba.getAccessibleContext().setAccessibleDescription("");

        setBounds(0, 0, 816, 639);
    }// </editor-fold>                        

    public static void main(String[] args) {
        ReporteCorpoBoyaca rsv = new ReporteCorpoBoyaca();
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
            }
        });
    }

    private void ottoMotosActionPerformed(java.awt.event.ActionEvent evt) {
        option = 0;
        System.out.println(option);
    }

    private void ottoActionPerformed(java.awt.event.ActionEvent evt) {
        option = 1;
        System.out.println(option);
    }

    private void dieselActionPerformed(java.awt.event.ActionEvent evt) {
        option = 2;
        System.out.println(option);
    }

    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {

        List<JTable> tables = new ArrayList<>();
        tables.add(tblCda);
        tables.add(tblEquipoAnlizador);
        tables.add(tblPruebas);
        tables.add(tblMedidas);
        tables.add(tblDatosPropietario);
        tables.add(tblDatosVehiculo);
        tables.add(tblResultadoPrueba);
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

    private javax.swing.JRadioButton ottoMotos;
    private javax.swing.JRadioButton otto;
    private javax.swing.JRadioButton diesel;

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
    private javax.swing.JTable tblEquipoAnlizador;
    private javax.swing.JTable tblMedidas;
    private javax.swing.JTable tblPruebas;
    private javax.swing.JTable tblDatosPropietario;
    private javax.swing.JTable tblDatosVehiculo;
    private javax.swing.JTable tblResultadoPrueba;

    private int option = -1;

}
