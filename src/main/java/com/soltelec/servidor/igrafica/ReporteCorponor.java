/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.igrafica;

import com.soltelec.servidor.consultas.Reportes;

import static com.soltelec.servidor.conexion.PersistenceController.getEntityManager;
import com.soltelec.servidor.dao.CdaJpaController;
import com.soltelec.servidor.dao.CertificadosJpaController;
import com.soltelec.servidor.dao.HojaPruebasJpaController;
import com.soltelec.servidor.dtos.reporte_corponor.Corponor;
import com.soltelec.servidor.model.Cda;
import com.soltelec.servidor.model.Certificados;
import com.soltelec.servidor.model.Medidas;
import com.soltelec.servidor.model.Pruebas;
import com.soltelec.servidor.model.Reinspeccion;
import com.soltelec.servidor.utils.GenericExportExcel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
public class ReporteCorponor extends javax.swing.JInternalFrame {

    private DefaultTableModel modeloPrueba;
    private DefaultTableModel modeloInfoVehiculo;
    private DefaultTableModel resultadoPrueba;
    private DefaultTableModel defectosYruido;
    private Object[] cal2 = new Object[60];

    private DateFormat formatoFechas;

    public ReporteCorponor() {
        super("Reporte Corponor",
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
        *Informacion de CDA Reporte Corponor
        *
         */
        modeloPrueba = new DefaultTableModel();
        modeloPrueba.addColumn("Fecha Inicio de Prueba");
        modeloPrueba.addColumn("Fecha fin de Prueba");
        modeloPrueba.addColumn("Municipio de Inspeccion");
        modeloPrueba.addColumn("Lugar de prueba");
        modeloPrueba.addColumn("Numero de Inspeccion/FUR");
        modeloPrueba.addColumn("Numero de Certificado Emitido");
        modeloPrueba.addColumn("Serial Equipo Utilizado");
        modeloPrueba.addColumn("PEF");
        modeloPrueba.addColumn("Marca Software Operacion");//9
        modeloPrueba.addColumn("Version Software Operacion");//10
        modeloPrueba.addColumn("ID Inspector");//12

//        modeloDatosPropietarioVehiculo = new DefaultTableModel();
//        modeloDatosPropietarioVehiculo.addColumn("TIP_IDE_PROP");
//        modeloDatosPropietarioVehiculo.addColumn("NUM_PROP");
//        modeloDatosPropietarioVehiculo.addColumn("NOM_PROP");
//        modeloDatosPropietarioVehiculo.addColumn("DIR_PROP");
//        modeloDatosPropietarioVehiculo.addColumn("TEL1_PROP");
//        modeloDatosPropietarioVehiculo.addColumn("TEL2_PROP");
//        modeloDatosPropietarioVehiculo.addColumn("MUN_PROP");
//        modeloDatosPropietarioVehiculo.addColumn("CORR_E_PROP");
        /*
        *
        *Informacion Vehidculo
        *
         */
        modeloInfoVehiculo = new DefaultTableModel();
        modeloInfoVehiculo.addColumn("Placa");//si
        modeloInfoVehiculo.addColumn("Marca");
        modeloInfoVehiculo.addColumn("Modelo");//si
        modeloInfoVehiculo.addColumn("Cilindraje");//si
        modeloInfoVehiculo.addColumn("Linea");//si
        modeloInfoVehiculo.addColumn("Clase");//si
        modeloInfoVehiculo.addColumn("Servicio");//si
        modeloInfoVehiculo.addColumn("Combustible");//si
        modeloInfoVehiculo.addColumn("Tipo de Motor");
        modeloInfoVehiculo.addColumn("Numero de tubos de escape");//si
        modeloInfoVehiculo.addColumn("Diseño");//si
//         

/////////Equipos y software
        resultadoPrueba = new DefaultTableModel();
        resultadoPrueba.addColumn("Temperatura Ambiente");//0
        resultadoPrueba.addColumn("Humedad Relativa");//1
        resultadoPrueba.addColumn("LTOE Estandar");//2
        resultadoPrueba.addColumn("Metodo de Medicion de Temperatura");//3
        resultadoPrueba.addColumn("Temperatura Motor");//4
        resultadoPrueba.addColumn("Temperatura Final");//5
        resultadoPrueba.addColumn("RPM Ralenti");//6
        resultadoPrueba.addColumn("RPM Crucero o Gobernada");//7
        resultadoPrueba.addColumn("HC Ralenti (ppm)");//8
        resultadoPrueba.addColumn("HC Crucero (ppm)");//9
        resultadoPrueba.addColumn("CO Ralenti (m-1)");//10
        resultadoPrueba.addColumn("CO Crucero (m-1)");//11
        resultadoPrueba.addColumn("CO2 Ralenti (m-1)");//12
        resultadoPrueba.addColumn("CO2 Crucero (m-1)");//13
        resultadoPrueba.addColumn("O2 Ralenti (m-1)");//14
        resultadoPrueba.addColumn("O2 Crucero (m-1)");//15
        resultadoPrueba.addColumn("Ciclo Preliminar (m-1)");//16
        resultadoPrueba.addColumn("RPM Gobernada Ciclo Preliminar");//17
        resultadoPrueba.addColumn("RPM Ralenti Ciclo Preliminar");//18
        resultadoPrueba.addColumn("Ciclo 1 (m-1)");//19
        resultadoPrueba.addColumn("RPM Gobernada Ciclo 1");//20
        resultadoPrueba.addColumn("RPM Ralenti Ciclo 1");//21
        resultadoPrueba.addColumn("Ciclo 2 (m-1)");//22
        resultadoPrueba.addColumn("RPM Gobernada Ciclo 2");//23
        resultadoPrueba.addColumn("RPM Ralenti Ciclo 2");//24
        resultadoPrueba.addColumn("Ciclo 3 (m-1)");//25
        resultadoPrueba.addColumn("RPM Gobernada Ciclo 3");//26
        resultadoPrueba.addColumn("RPM Ralenti Ciclo 3");//27
        resultadoPrueba.addColumn("Promedio Final (m-1)");//28
        resultadoPrueba.addColumn("Concepto final");//29

        //Tabla datos generales de inspeccion
//        modeloDatosGeneralesInspeccion = new DefaultTableModel();
//        modeloDatosGeneralesInspeccion.addColumn("TIP_IDE_DT");
//        modeloDatosGeneralesInspeccion.addColumn("NUM_IDE_DT");
//        modeloDatosGeneralesInspeccion.addColumn("NOM_DT");
//        modeloDatosGeneralesInspeccion.addColumn("TIP_IDE_IT");
//        modeloDatosGeneralesInspeccion.addColumn("NUM_IDE_IT");
//        modeloDatosGeneralesInspeccion.addColumn("NOM_IT");
//        modeloDatosGeneralesInspeccion.addColumn("NUM_FUR");
//        modeloDatosGeneralesInspeccion.addColumn("FECHA_FUR");
//        modeloDatosGeneralesInspeccion.addColumn("CONS_RUNT");
//        modeloDatosGeneralesInspeccion.addColumn("FUR_ASOC");
//        modeloDatosGeneralesInspeccion.addColumn("CERT_RTMYG");
//        modeloDatosGeneralesInspeccion.addColumn("F_INI_INSP");
//        modeloDatosGeneralesInspeccion.addColumn("F_FIN_INSP");
//        modeloDatosGeneralesInspeccion.addColumn("F_ABORTO");
//        modeloDatosGeneralesInspeccion.addColumn("C_ABORTO");
//        modeloDatosGeneralesInspeccion.addColumn("CATALIZADOR");
//        modeloDatosGeneralesInspeccion.addColumn("LUGAR_TEMP");
//        modeloDatosGeneralesInspeccion.addColumn("T_AMB");
//        modeloDatosGeneralesInspeccion.addColumn("H_REL");
        //
        defectosYruido = new DefaultTableModel();
        defectosYruido.addColumn("Presencia de Humo");//0
        defectosYruido.addColumn("Dilucion en la Mezcla");//1
        defectosYruido.addColumn("Nivel Emisiones");//2
        defectosYruido.addColumn("RPM Fuera de Rango");//3
        defectosYruido.addColumn("Fugas tubo");//4
        defectosYruido.addColumn("Salidas Adicionales");//5
        defectosYruido.addColumn("Ausencia Tapones Aceite o fuga");//6
        defectosYruido.addColumn("Ausencia Tapones Combustible o fugas");//7
        defectosYruido.addColumn("Admision Mal estado - Filtro aire");//8
        defectosYruido.addColumn("Desconexion Recirculacion");//9
        defectosYruido.addColumn("Accesorios tubo");//10
        defectosYruido.addColumn("Operacion Incorrecta Refrigeracion");//11
        defectosYruido.addColumn("Emisiones");//12
        defectosYruido.addColumn("Incorrecta Operacion Gobernador");//13
        defectosYruido.addColumn("Falla Subita");//14
        defectosYruido.addColumn("Ejecucion Incorrecta");//15
        defectosYruido.addColumn("Diferencia Aritmetica");//16
        defectosYruido.addColumn("Diferencia de Temperatura");//17
        defectosYruido.addColumn("Instalacion accesorios tubo");//18
        defectosYruido.addColumn("Ausencia o incorrecta Inst. Filtro Aire");//19
        defectosYruido.addColumn("Activacion Dispositivos");//20
        defectosYruido.addColumn("Falla del equipo de Medicion");//21
        defectosYruido.addColumn("Falla subita fluido electrico");//22
        defectosYruido.addColumn("Bloqueo forzado del equipo de Medicion");//23
        defectosYruido.addColumn("Ejecucion incorrecta prueba");//24
        defectosYruido.addColumn("Falla de desviacion cero");//25
        defectosYruido.addColumn("Resolucion aplicada");//26
        defectosYruido.addColumn("Ruidos");//27

    }

    private void fillData(Date fechaInicial, Date fechaFinal) {
        List<Corponor> todosLosDatos = Reportes.getCorpocor(fechaInicial, fechaFinal);

        todosLosDatos.stream().forEach(dato -> {
            Object[] datosPrueba = {
                dato.getFechaInicio(),
                dato.getFechaFin(),
                dato.getMunicipio(),
                dato.getLugar(),
                dato.getNFur(),
                dato.getNCertificado(),
                dato.getSerialAnalizador(),
                dato.getPef(),
                dato.getMarcaSoftware(),
                dato.getVersionSoftware(),
                dato.getIdInspector()
            };
            modeloPrueba.addRow(datosPrueba);

            Object[] datosVehiculo = {
                dato.getPlaca(),
                dato.getMarcaCarro(),
                dato.getModeloCarro(),
                dato.getCilindraje(),
                dato.getLinea(),
                dato.getClase(),
                dato.getServicio(),
                dato.getTCombustible(),
                dato.getTMotor(),
                dato.getNExostos(),
                dato.getDesign()
            };
            modeloInfoVehiculo.addRow(datosVehiculo);

            Object[] resultados = {
                dato.getTempAmbiente(),//0
                dato.getHumedadRelativa(),//1
                dato.getLtoe(),//2
                dato.getMetodoMedicionTemp(),//3
                dato.getTempMotor(),//4
                dato.getTempFinal(),//5
                dato.getRpmRalenti(),//6
                dato.getRpmCruceroOGobernada(),//7
                dato.getHcRalenti(),//8
                dato.getHcCrucero(),//9
                dato.getCoRalenti(),//10
                dato.getCoCrucero(),//11
                dato.getCo2Ralenti(),//12
                dato.getCo2Crucero(),//13
                dato.getO2Ralenti(),//14
                dato.getO2Crucero(),//15
                dato.getCicloPreliminar(),//16
                dato.getRpmGobernadaCicloPreliminar(),//17
                dato.getRpmRalentiCicloPreliminar(),//18
                dato.getOpacidadC1(),//19
                dato.getGobernadaC1(),//20
                dato.getRalentiC1(),//21
                dato.getOpacidadC2(),//22
                dato.getGobernadaC2(),//23
                dato.getRalentiC2(),//24
                dato.getOpacidadC3(),//25
                dato.getGobernadaC3(),//26
                dato.getRalentiC3(),//27
                dato.getPromedioFinal(),//28
                dato.getConceptoFinal()//29
            };
            resultadoPrueba.addRow(resultados);

            /* defectosYruido = new DefaultTableModel();
            defectosYruido.addColumn("Presencia de Humo");//0
            defectosYruido.addColumn("Dilucion en la Mezcla");//1
            defectosYruido.addColumn("Nivel Emisiones");//2
            defectosYruido.addColumn("RPM Fuera de Rango");//3
            defectosYruido.addColumn("Fugas tubo");//4
            defectosYruido.addColumn("Salidas Adicionales");//5
            defectosYruido.addColumn("Ausencia Tapones Aceite o fuga");//6
            defectosYruido.addColumn("Ausencia Tapones Combustible o fugas");//7
            defectosYruido.addColumn("Admision Mal estado - Filtro aire");//8
            defectosYruido.addColumn("Desconexion Recirculacion");//9
            defectosYruido.addColumn("Accesorios tubo");//10
            defectosYruido.addColumn("Operacion Incorrecta Refrigeracion");//11
            defectosYruido.addColumn("Emisiones");//12
            defectosYruido.addColumn("Incorrecta Operacion Gobernador");//13
            defectosYruido.addColumn("Falla Subita");//14
            defectosYruido.addColumn("Ejecucion Incorrecta");//15
            defectosYruido.addColumn("Diferencia Aritmetica");//16
            defectosYruido.addColumn("Diferencia de Temperatura");//17
            defectosYruido.addColumn("Instalacion accesorios tubo");//18
            defectosYruido.addColumn("Ausencia o incorrecta Inst. Filtro Aire");//19
            defectosYruido.addColumn("Activacion Dispositivos");//20
            defectosYruido.addColumn("Falla del equipo de Medicion");//21
            defectosYruido.addColumn("Falla subita fluido electrico");//22
            defectosYruido.addColumn("Bloqueo forzado del equipo de Medicion");//23
            defectosYruido.addColumn("Ejecucion incorrecta prueba");//24
            defectosYruido.addColumn("Falla de desviacion cero");//25
            defectosYruido.addColumn("Resolucion aplicada");//26
            defectosYruido.addColumn("Ruidos");//27 */

            Object[] datosDefectosYRuido = {
                dato.getPresenciaHumo(),//0
                dato.getDilucionMezcla(),//1
                dato.getNivelEmisiones(),//2
                dato.getRpmFueraRango(),//3
                dato.getFugasTubo(),//4
                dato.getSalidasAdicionales(),//5
                dato.getTapaAceite(),//6
                dato.getTapaCombustible(),//7
                dato.getFiltroAire(),//8
                "NO",
                dato.getAccesorios(),//9
                dato.getIncorrectaRefrigeracion(),//10
                dato.getEmisiones(),//11
                dato.getIncorrectaOperacionGobernador(),//12
                "NO",
                "NO",
                dato.getDiferenciaAritmetica(),//13
                dato.getDiferenciaTemperatura(),//14
                dato.getInstalacionTubo(),//15
                dato.getFiltroAire2(),//16
                dato.getDispositivos(),//17
                dato.getFallaEquipoMedicion(),//18
                dato.getFallaFluidoElectrico(),//19
                dato.getBloqueoForzadoEquipoMedicion(),//20
                dato.getEjecucionIncorrectaPrueba(),//21
                dato.getFallaDesviacionCero(),//22
                dato.getResolucionAplicada(),//23
                dato.getRuidos()//24
            };
            defectosYruido.addRow(datosDefectosYRuido);


            this.tblPruebas.setModel(modeloPrueba);
            this.tblInfoVehiculos.setModel(modeloInfoVehiculo);
            this.tblResultadoPruebas.setModel(resultadoPrueba);
            this.tblDefectosYRuido.setModel(defectosYruido);

            tblPruebas.setEnabled(false);
            tblInfoVehiculos.setEnabled(false);
            tblMedidas.setEnabled(false);
            tblPropietario.setEnabled(false);
            tblResultadoPruebas.setEnabled(false);
            tblDatosGeneralesInspeccion.setEnabled(false);
            tblDefectosYRuido.setEnabled(false);

        });
    }

    private void fillDataa(Date fechaInicial, Date fechaFinal) {
        initModels();
        DateFormat formatoFechas = new SimpleDateFormat("yyyy-MM-dd");
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
            if (pruebasebas.getFinalizada().equalsIgnoreCase("Y")) {
                work = work + 1;
                System.out.println("proc..!  " + work + " de " + lng);
                if (work > lng) {
                    JOptionPane.showMessageDialog(null, "proc..!  " + work + " de " + lng);
                }

                cargarInformacionCda(pruebasebas);
                cargarEquipos(pruebasebas);
                cargarInformacionvehiculo(pruebasebas, form);
                cargarResultadosInspeccion(pruebasebas,fechaInicial, fechaFinal);

                this.tblPruebas.setModel(modeloPrueba);
                this.tblInfoVehiculos.setModel(modeloInfoVehiculo);
                this.tblResultadoPruebas.setModel(resultadoPrueba);
                this.tblDefectosYRuido.setModel(defectosYruido);

                tblPruebas.setEnabled(false);
                tblInfoVehiculos.setEnabled(false);
                tblMedidas.setEnabled(false);
                tblPropietario.setEnabled(false);
                tblResultadoPruebas.setEnabled(false);
                tblDatosGeneralesInspeccion.setEnabled(false);
                tblDefectosYRuido.setEnabled(false);

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
        tblPruebas = new javax.swing.JTable();
        tblPropietario = new javax.swing.JTable();
        tblResultadoPruebas = new javax.swing.JTable();
        tblDatosGeneralesInspeccion = new javax.swing.JTable();
        tblDefectosYRuido = new javax.swing.JTable();

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
        tblPruebas.setModel(modeloPrueba);
        tblPruebas.setName("Información de las pruebas."); // NOI18N
        jScrollPane11.setViewportView(tblPruebas);
        infPrueba.addTab("Inf. pruebas", jScrollPane11);

//        tblPropietario.setModel(modeloDatosPropietarioVehiculo);
//        tblPropietario.setName("Dtos del Propietario."); // NOI18N
//        jScrollPane12.setViewportView(tblPropietario);
//        infPrueba.addTab("Inf. Prop", jScrollPane12);
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
        tblResultadoPruebas.setModel(resultadoPrueba);
        tblResultadoPruebas.setName("Información de resultado pruebas"); // NOI18N		
        jScrollPane13.setViewportView(tblResultadoPruebas);
        infPrueba.addTab("Inf. resultado pruebas", jScrollPane13);

        //Tabla datos generales inspeccion
//
        //Tabla 
        tblDefectosYRuido.setModel(defectosYruido);
        tblDefectosYRuido.setName("Datos defectos y ruido"); // NOI18N		
        jScrollPane15.setViewportView(tblDefectosYRuido);
        infPrueba.addTab("Inf. defectos y ruido", jScrollPane15);

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
        ReporteCorponor rsv = new ReporteCorponor();
        rsv.setVisible(true);
    }

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

    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {

        List<JTable> tables = new ArrayList<>();
        tables.add(tblPruebas);
        tables.add(tblPropietario);
        tables.add(tblInfoVehiculos);
        tables.add(tblMedidas);
        tables.add(tblResultadoPruebas);
        tables.add(tblDatosGeneralesInspeccion);
        tables.add(tblDefectosYRuido);
        GenericExportExcel excel = new GenericExportExcel();
        excel.exportSameSheet(tables);
    }

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
    private javax.swing.JTable tblPruebas;
    private javax.swing.JTable tblPropietario;
    private javax.swing.JTable tblMedidas;
    private javax.swing.JTable tblInfoVehiculos;
    private javax.swing.JTable tblResultadoPruebas;
    private javax.swing.JTable tblDatosGeneralesInspeccion;
    private javax.swing.JTable tblDefectosYRuido;

    private void cargarInformacionCda(Pruebas pruebasebas) {
        formatoFechas = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        CdaJpaController cda = new CdaJpaController();
        Cda mcda = cda.findCda(1);
        Object[] objCda = new Object[17];

        objCda[0] = formatoFechas.format(pruebasebas.getFechaPrueba());
        objCda[1] = formatoFechas.format(pruebasebas.getFechaFinal());
        objCda[2] = mcda.getCiudad();
        objCda[3] = mcda.getCiudad();
        objCda[4] = pruebasebas.getHojaPruebas().getConHojaPrueba();
        CertificadosJpaController cert = new CertificadosJpaController();
        Certificados certificado = cert.findCertificadoHojaPrueba(pruebasebas.getHojaPruebas().getTestsheet());
        objCda[5] = certificado.getConsecutive();
        objCda[6] = pruebasebas.getSerialEquipo();
        String PEF = Pruebas.generarConsulta(pruebasebas.getSerialEquipo());
        objCda[8] = PEF;
        objCda[9] = "SART";
        objCda[10] = "1.7.3";
        objCda[11] = pruebasebas.getHojaPruebas().getUsuario_resp().getCedula();
        modeloPrueba.addRow(objCda);
    }

    private void cargarInformacionvehiculo(Pruebas pruebasebas, String strIntento) {

        Object[] objInfoVehiculo = new Object[25];
        objInfoVehiculo[0] = pruebasebas.getHojaPruebas().getVehiculos().getCarplate();
        objInfoVehiculo[1] = pruebasebas.getHojaPruebas().getVehiculos().getMarcas().getNombremarca();
        objInfoVehiculo[2] = pruebasebas.getHojaPruebas().getVehiculos().getModelo();
        objInfoVehiculo[3] = pruebasebas.getHojaPruebas().getVehiculos().getCilindraje();
        objInfoVehiculo[4] = pruebasebas.getHojaPruebas().getVehiculos().getLineasVehiculos().getCrlname();
        objInfoVehiculo[5] = pruebasebas.getHojaPruebas().getVehiculos().getClasesVehiculo().getNombreclase();
        objInfoVehiculo[6] = pruebasebas.getHojaPruebas().getVehiculos().getServicios().getNombreservicio();
        objInfoVehiculo[7] = pruebasebas.getHojaPruebas().getVehiculos().getTiposGasolina().getNombregasolina();
        objInfoVehiculo[8] = pruebasebas.getHojaPruebas().getVehiculos().getTiemposmotor();
        objInfoVehiculo[9] = pruebasebas.getHojaPruebas().getVehiculos().getNumeroexostos();
        objInfoVehiculo[10] = pruebasebas.getHojaPruebas().getVehiculos().getDiseno();

        modeloInfoVehiculo.addRow(objInfoVehiculo);
    }
    Object[] objProp = new Object[55];

    private void cargarResultadosInspeccion(Pruebas pruebasebas,Date fechaInicial,Date fechaFinal) {
        if (pruebasebas.getObservaciones() != null) {

            if (pruebasebas.getObservaciones().contains("Presencia Humo") || pruebasebas.getObservaciones().contains("PRESENCIA HUMO")) {
                objProp[0] = "SI";
            } else {
                objProp[0] = "NO";
            }
            if (pruebasebas.getObservaciones().contains("DILUCION")) {
                objProp[1] = "SI";
            } else {
                objProp[1] = "NO";
            }
            if (pruebasebas.getObservaciones().contains("REVOLUCIONES") || pruebasebas.getObservaciones().contains("Revoluciones")) {
                objProp[3] = "SI";
            } else {
                objProp[3] = "NO";
            }
            if (pruebasebas.getObservaciones().contains("FUGAS") || pruebasebas.getObservaciones().contains("TUBO")) {
                objProp[4] = "SI";
            } else {
                objProp[4] = "NO";
            }
            if (pruebasebas.getObservaciones().contains("EMICIONES") || pruebasebas.getObservaciones().contains("Emiciones")) {
                objProp[12] = "SI";
            } else {
                objProp[12] = "NO";
            }
            if (pruebasebas.getObservaciones().contains("OPERACION") || pruebasebas.getObservaciones().contains("Operacion") || pruebasebas.getObservaciones().contains("INCORRECTA")) {
                objProp[13] = "SI";
            } else {
                objProp[13] = "NO";
            }
            if (pruebasebas.getObservaciones().contains("Falla subita")) {
                objProp[14] = "SI";
            } else {
                objProp[14] = "NO";
            }
            if (pruebasebas.getObservaciones().contains("Ejecucion incorrecta")) {
                objProp[15] = "SI";
            } else {
                objProp[15] = "NO";
            }
            if (pruebasebas.getObservaciones().contains("Fallas del equipo")) {
                objProp[21] = "SI";
            } else {
                objProp[21] = "NO";
            }
            if (pruebasebas.getObservaciones().contains("de fluido electrico")) {
                objProp[22] = "SI";
            } else {
                objProp[22] = "NO";
            }
            if (pruebasebas.getObservaciones().contains("Bloqueo forzado del equipo")) {
                objProp[23] = "SI";
            } else {
                objProp[23] = "NO";
            }
            if (pruebasebas.getObservaciones().contains("Ejecucion incorrecta de la prueba")) {
                objProp[24] = "SI";
            } else {
                objProp[24] = "NO";
            }
            if (pruebasebas.getObservaciones().contains("Desviacion") || pruebasebas.getObservaciones().contains("DESVIACION")) {
                objProp[25] = "SI";
            } else {
                objProp[25] = "NO";
            }
        } else {
            objProp[0] = "NO";
            objProp[1] = "NO";
            objProp[3] = "NO";
            objProp[4] = "NO";
            objProp[12] = "NO";
            objProp[13] = "NO";
            objProp[14] = "NO";
            objProp[15] = "NO";
            objProp[21] = "NO";
            objProp[22] = "NO";
            objProp[23] = "NO";
            objProp[24] = "NO";
            objProp[25] = "NO";
        }
        objProp[4] = "NO";
        objProp[5] = "NO";
        objProp[6] = "NO";
        objProp[7] = "NO";
        objProp[8] = "NO";
        objProp[9] = "NO";
        objProp[10] = "NO";
        objProp[11] = "NO";
        objProp[14] = "NO";
        objProp[16] = "NO";
        objProp[17] = "NO";
        objProp[18] = "NO";
        objProp[19] = "NO";
        objProp[20] = "NO";
        objProp[25] = "NO";
        if (pruebasebas.getComentarioAborto() != null && !pruebasebas.getComentarioAborto().isEmpty()) {
            //Concatenar detalles son las causas de rechazo
            if (pruebasebas.getComentarioAborto().equalsIgnoreCase("Condiciones Anormales")) {
                // si hay condiciones anormales y las observaciones no estan nullos
                if (pruebasebas.getObservaciones() != null) {
                    //si hay comentario aborto y observaciones concatenar un .-
                    String[] arrValor = pruebasebas.getObservaciones().split(".-");

                    if (arrValor.length > 1) {
                        String[] arrCondiciones = arrValor[1].split(";");
                        if (arrCondiciones.length > 0) {

                            for (int i = 0; i < arrCondiciones.length; i++) {
                                if (arrCondiciones[i].contains("Existencia de fugas en el tubo")) {
                                    objProp[4] = "SI";
                                }

                                // Salidas adicionales en el sistema de escape
                                if (arrCondiciones[i].contains("Salidas adicionales en el sistema de escape")
                                        || arrCondiciones[i].contains(" Salidas adicionales en el sistema de escape")) {
                                    objProp[5] = "SI";
                                }

                                if (arrCondiciones[i].contains("Ausencia de tapones de aceite")) {
                                    objProp[6] = "SI";
                                }
                                if (arrCondiciones[i].contains("Ausencia de tapones de combustible")) {
                                    objProp[7] = "SI";
                                }
                                if (arrCondiciones[i].contains("Sistema de admisi")) {
                                    objProp[8] = "SI";
                                }
                                if (arrCondiciones[i].contains("Desconexión de sistemas de recirculación")) {
                                    objProp[9] = "SI";
                                }
                                if (arrCondiciones[i].contains("Instalación de accesorios o deformaciones en el tubo")) {
                                    objProp[10] = "SI";
                                    objProp[18] = "SI";
                                }
                                if (arrCondiciones[i].contains("Incorrecta operación del sistema")) {
                                    objProp[11] = "SI";
                                }
                                if (arrCondiciones[i].contains("Falla subita")) {
                                    objProp[14] = "SI";
                                }
                                if (arrCondiciones[i].contains("La diferencia aritmética")) {
                                    objProp[16] = "SI";
                                }
                                if (arrCondiciones[i].contains("diferencia")) {
                                    objProp[17] = "SI";
                                }
                                if (arrCondiciones[i].contains("Ausencia o incorrecta instalación del filtro")) {
                                    objProp[19] = "SI";
                                }
                                if (arrCondiciones[i].contains("Activación de dispositivos")) {
                                    objProp[20] = "SI";
                                }
                                if (arrCondiciones[i].contains("DESVIACION") || arrCondiciones[i].contains("Desviacion")) {
                                    objProp[25] = "SI";
                                }

                            }

                        }
                    }
                }
            }
        }

        objProp[26] = "0762";
        objProp[2] = "35";

        List<Pruebas> pruebasList = findByDatePruebassiete(fechaInicial, fechaFinal);

        for (Pruebas pruebasebas2 : pruebasList) {
            if (pruebasebas2.getTipoPrueba().getTesttype() == 7) {
                double mayorValor = Double.NEGATIVE_INFINITY;
                for (Medidas medidas : pruebasebas2.getMedidasList()) {
                    double valorMedida = medidas.getValormedida();
                    if (valorMedida > mayorValor) {
                        mayorValor = valorMedida;
                        System.out.println("valor------" + mayorValor);
                    }
                }
                System.out.println("Resultado------" + mayorValor);
                objProp[27] = mayorValor;
            }
        }

        defectosYruido.addRow(objProp);
        objProp[27] = "";
    }

    private void cargarEquipos(Pruebas pruebasebas) {

        System.out.println("placa" + pruebasebas.getHojaPruebas().getVehiculos().getCarplate());
        for (int i = 0; i < cal2.length; i++) {
            cal2[i] = "";
        }
        for (Medidas medidas : pruebasebas.getMedidasList()) {
            if (medidas.getTiposMedida().getMeasuretype() == 8031) {
                cal2[0] = medidas.getValormedida();
            } else if (medidas.getTiposMedida().getMeasuretype() == 8032) {
                cal2[1] = medidas.getValormedida();
            } else if (medidas.getTiposMedida().getMeasuretype() == 8006) {
                cal2[4] = medidas.getValormedida();
            } else if (medidas.getTiposMedida().getMeasuretype() == 8034 || medidas.getTiposMedida().getMeasuretype() == 8037) {
                cal2[5] = medidas.getValormedida();
            } else if (medidas.getTiposMedida().getMeasuretype() == 8005) {
                cal2[6] = medidas.getValormedida();
            } else if (medidas.getTiposMedida().getMeasuretype() == 8011) {
                cal2[7] = medidas.getValormedida();
            } else if (medidas.getTiposMedida().getMeasuretype() == 8001) {
                cal2[8] = medidas.getValormedida();
            } else if (medidas.getTiposMedida().getMeasuretype() == 8007) {
                cal2[9] = medidas.getValormedida();
            } else if (medidas.getTiposMedida().getMeasuretype() == 8002) {
                cal2[10] = medidas.getValormedida();
            } else if (medidas.getTiposMedida().getMeasuretype() == 8008) {
                cal2[11] = medidas.getValormedida();
            } else if (medidas.getTiposMedida().getMeasuretype() == 8003) {
                cal2[12] = medidas.getValormedida();
            } else if (medidas.getTiposMedida().getMeasuretype() == 8009) {
                cal2[13] = medidas.getValormedida();
            } else if (medidas.getTiposMedida().getMeasuretype() == 8004) {
                cal2[14] = medidas.getValormedida();
            } else if (medidas.getTiposMedida().getMeasuretype() == 8010) {
                cal2[15] = medidas.getValormedida();
            } else if (medidas.getTiposMedida().getMeasuretype() == 8033) {
                cal2[16] = medidas.getValormedida();
                cal2[29] = medidas.getValormedida();
            } else if (medidas.getTiposMedida().getMeasuretype() == 8036) {
                cal2[17] = medidas.getValormedida();
            } else if (medidas.getTiposMedida().getMeasuretype() == 8035) {
                cal2[18] = medidas.getValormedida();
                cal2[21] = medidas.getValormedida();
                cal2[24] = medidas.getValormedida();
                cal2[27] = medidas.getValormedida();
            } else if (medidas.getTiposMedida().getMeasuretype() == 8013) {
                cal2[19] = medidas.getValormedida();
                cal2[30] = medidas.getValormedida();
            } else if (medidas.getTiposMedida().getMeasuretype() == 8039) {
                cal2[20] = medidas.getValormedida();
            } else if (medidas.getTiposMedida().getMeasuretype() == 8014) {
                cal2[22] = medidas.getValormedida();
                cal2[31] = medidas.getValormedida();
            } else if (medidas.getTiposMedida().getMeasuretype() == 8038) {
                cal2[23] = medidas.getValormedida();
            } else if (medidas.getTiposMedida().getMeasuretype() == 8015) {
                cal2[25] = medidas.getValormedida();
                cal2[32] = medidas.getValormedida();
            } else if (medidas.getTiposMedida().getMeasuretype() == 8041) {
                cal2[26] = medidas.getValormedida();
            } else if (medidas.getTiposMedida().getMeasuretype() == 8017) {
                cal2[28] = medidas.getValormedida();
            }
        }

        // }
        cal2[2] = pruebasebas.getHojaPruebas().getVehiculos().getDiametro();
        cal2[3] = pruebasebas.getHojaPruebas().getFormaMedTemperatura();
        if (pruebasebas.getAprobada().equals("Y")) {
            cal2[33] = "APROBADO";
        } else {
            cal2[33] = "REPROBADO";
        }
        resultadoPrueba.addRow(cal2);


        /* resultadoPrueba = new DefaultTableModel();
        resultadoPrueba.addColumn("Temperatura Ambiente");//0
        resultadoPrueba.addColumn("Humedad Relativa");//1
        resultadoPrueba.addColumn("LTOE Estandar");//2
        resultadoPrueba.addColumn("Metodo de Medicion de Temperatura");//3
        resultadoPrueba.addColumn("Temperatura Motor");//4
        resultadoPrueba.addColumn("Temperatura Final");//5
        resultadoPrueba.addColumn("RPM Ralenti");//6
        resultadoPrueba.addColumn("RPM Crucero o Gobernada");//7
        resultadoPrueba.addColumn("HC Ralenti (ppm)");//8
        resultadoPrueba.addColumn("HC Crucero (ppm)");//9
        resultadoPrueba.addColumn("CO Ralenti (%)");//10
        resultadoPrueba.addColumn("CO Crucero (%)");//11
        resultadoPrueba.addColumn("CO2 Ralenti (%)");//12
        resultadoPrueba.addColumn("O2 Ralenti (%)");//13
        resultadoPrueba.addColumn("O2 Crucero (%)");//14
        resultadoPrueba.addColumn("Ciclo Preliminar (%)");//15
        resultadoPrueba.addColumn("RPM Gobernada Ciclo Preliminar");//16
        resultadoPrueba.addColumn("RPM Ralenti Ciclo Preliminar");//17
        resultadoPrueba.addColumn("Ciclo 1 (%)");//18
        resultadoPrueba.addColumn("RPM Gobernada Ciclo 1");//19
        resultadoPrueba.addColumn("RPM Ralenti Ciclo 1");//20
        resultadoPrueba.addColumn("Ciclo 2 (%)");//21
        resultadoPrueba.addColumn("RPM Gobernada Ciclo 2");//22
        resultadoPrueba.addColumn("RPM Ralenti Ciclo 2");//23
        resultadoPrueba.addColumn("Ciclo 3 (%)");//24
        resultadoPrueba.addColumn("RPM Gobernada Ciclo 3");//25
        resultadoPrueba.addColumn("RPM Ralenti Ciclo 3");//26
        resultadoPrueba.addColumn("Promedio Final (%)");//27
        resultadoPrueba.addColumn("Ciclo preliminar (m-1)");//28
        resultadoPrueba.addColumn("Ciclo 1 (m-1)");//29
        resultadoPrueba.addColumn("Ciclo 2 (m-1)");//30
        resultadoPrueba.addColumn("Ciclo 3 (m-1)");//31
        resultadoPrueba.addColumn("Promedio Final (m-1)");//32
        resultadoPrueba.addColumn("Concepto final");//33 */
    }
        public List<Pruebas> findByDatePruebassiete(Date fechaInicial, Date fechaFinal) {
        EntityManager em = getEntityManager();        
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaFinal);
        cal.set(Calendar.HOUR, 23);
        cal.set(Calendar.MINUTE, 59);
        Query q = em.createQuery("SELECT p FROM Pruebas  p join fetch p.hojaPruebas   hp WHERE p.hojaPruebas.preventiva ='N' AND p.tipoPrueba.testtype=7 AND p.fechaFinal BETWEEN :fec1 and :fec2 ORDER BY p.idPruebas", Pruebas.class);// 
        q.setParameter("fec1", fechaInicial);
        q.setParameter("fec2", cal.getTime());
        return q.getResultList();
    }
}
