/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.igrafica;

import com.soltelec.servidor.consultas.Reportes;
import com.soltelec.servidor.dtos.DatosCda;
import com.soltelec.servidor.dtos.reporte_corpocaldas.AnalizadorCorpocaldas;
import com.soltelec.servidor.dtos.reporte_corpocaldas.CausasAbortoCorpocaldas;
import com.soltelec.servidor.dtos.reporte_corpocaldas.CausasRechazoCorpocaldas;
import com.soltelec.servidor.dtos.reporte_corpocaldas.Corpocaldas;
import com.soltelec.servidor.dtos.reporte_corpocaldas.DatosPruebaCorpocaldas;
import com.soltelec.servidor.dtos.reporte_corpocaldas.DatosVehiculoCorpocaldas;
import com.soltelec.servidor.dtos.reporte_corpocaldas.EncendidoChispaCorpocaldas;
import com.soltelec.servidor.dtos.reporte_corpocaldas.EncendidoCompresionCorpocaldas;
import com.soltelec.servidor.dtos.reporte_corpocaldas.InicioPruebaCorpocaldas;
import com.soltelec.servidor.dtos.reporte_corpocaldas.RuidoCorpocaldas;
import com.soltelec.servidor.utils.GenericExportExcel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class ReporteCorpocaldas extends javax.swing.JInternalFrame {

    private DefaultTableModel modeloCDA;
    private DefaultTableModel modeloInfoVehiculo;
    private DefaultTableModel modeloInicioPrueba;
    private DefaultTableModel datosPrueba;
    private DefaultTableModel ruido;
    private DefaultTableModel equipoAnalizador;
    private DefaultTableModel resultadosEncendidoPorChispa;
    private DefaultTableModel resultadosPruebaEncendidoPorCompresion;
    private DefaultTableModel causasRechazo;
    private DefaultTableModel causasAborto;

    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btnGenerar;
    private org.jdesktop.swingx.JXDatePicker fechaFInal;
    private org.jdesktop.swingx.JXDatePicker fechaInicial;
    private javax.swing.JTabbedPane infPrueba;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JTable tblCda;
    private javax.swing.JTable tblPruebas;
    private javax.swing.JTable tblResultadosPruebaEncendidoPorCompresion;
    private javax.swing.JTable tblMedidas;
    private javax.swing.JTable tblInfoVehiculos;
    private javax.swing.JTable tblEquipoAnalizador;
    private javax.swing.JTable tblDatosPrueba;
    private javax.swing.JTable tblResultadosEncendidoPorChispa;
    private javax.swing.JTable tblCausasRechazo;
    private javax.swing.JTable tblCausasAborto;
    private javax.swing.JTable tblRuido;

    public ReporteCorpocaldas() {
        super("Reporte Corpocaldas",
                true, //resizable
                true, //closable
                false, //maximizable
                true);
        initModels();
        initComponents();

    }

    private void initModels() {
        modeloCDA = new DefaultTableModel();
        modeloCDA.addColumn("Nombre CDA");//0
        modeloCDA.addColumn("Tipo documento");//1
        modeloCDA.addColumn("NIT");//2
        modeloCDA.addColumn("persona contacto");//3
        modeloCDA.addColumn("Email");//4
        modeloCDA.addColumn("Telefono");//5
        modeloCDA.addColumn("Departamento");//6
        modeloCDA.addColumn("Municipio");//7
        modeloCDA.addColumn("no_reso_ambiental");//8
        modeloCDA.addColumn("F_no_reso_ambiental");//9
        modeloCDA.addColumn("Clase CDA");//10
        modeloCDA.addColumn("No. Expediente");//11
        modeloCDA.addColumn("No. total e_opacimetro");//12
        modeloCDA.addColumn("OTTO");//13
        modeloCDA.addColumn("TOTAL_4t");//14
        modeloCDA.addColumn("TOTAL_2t");//15
        
        modeloInicioPrueba = new DefaultTableModel();
        modeloInicioPrueba.addColumn("Fecha y hora inicio prueba");
        modeloInicioPrueba.addColumn("Fecha y hora finalizacion");
        modeloInicioPrueba.addColumn("Municipio de inspeccion");
        modeloInicioPrueba.addColumn("Direccion CDA");
        modeloInicioPrueba.addColumn("# inspeccion (FUR)");
        modeloInicioPrueba.addColumn("# certificado emitido");
        modeloInicioPrueba.addColumn("# serie equipo(gases-opacidad)");
        modeloInicioPrueba.addColumn("Marca medidor");
        modeloInicioPrueba.addColumn("Nombre proveedor software");
        modeloInicioPrueba.addColumn("Nombre programa");
        modeloInicioPrueba.addColumn("Version programa");
        modeloInicioPrueba.addColumn("ID Inspector");

        equipoAnalizador = new DefaultTableModel();//0
        equipoAnalizador.addColumn("Vr PEF");//0
        equipoAnalizador.addColumn("# Serie banco");//1
        equipoAnalizador.addColumn("Marca analizador");//2
        equipoAnalizador.addColumn("Vr Span Bajo HC");//3
        equipoAnalizador.addColumn("Resul span bajo HC");//4
        equipoAnalizador.addColumn("Vr Span Bajo CO");//5
        equipoAnalizador.addColumn("Resul span bajo CO");//6
        equipoAnalizador.addColumn("Vr Span Bajo CO2");//7
        equipoAnalizador.addColumn("Resul span bajo CO2");//8
        equipoAnalizador.addColumn("Vr Span Alto HC");//9
        equipoAnalizador.addColumn("Resul Vr Span Alto HC");//10
        equipoAnalizador.addColumn("Vr Span Alto CO");//11
        equipoAnalizador.addColumn("Resul Vr Span Alto CO");//12
        equipoAnalizador.addColumn("Vr Span Alto CO2");//13
        equipoAnalizador.addColumn("Resul Vr Span Alto CO2");//14
        equipoAnalizador.addColumn("Fecha y hora ultima verificacion");//15

        ruido = new DefaultTableModel();
        ruido.addColumn("Ruido");

        modeloInfoVehiculo = new DefaultTableModel();
        modeloInfoVehiculo.addColumn("PLACA");
        modeloInfoVehiculo.addColumn("MARCA");
        modeloInfoVehiculo.addColumn("MODELO");
        modeloInfoVehiculo.addColumn("CILINDRAJE");
        modeloInfoVehiculo.addColumn("KM");
        modeloInfoVehiculo.addColumn("LINEA");
        modeloInfoVehiculo.addColumn("CLASE");
        modeloInfoVehiculo.addColumn("SERVICIO");
        modeloInfoVehiculo.addColumn("TIP_COMBus");
        modeloInfoVehiculo.addColumn("TIP_MOTOR");
        modeloInfoVehiculo.addColumn("NUM_ESCAPE");
        modeloInfoVehiculo.addColumn("DIS_MOTOR");

        datosPrueba = new DefaultTableModel();
        datosPrueba.addColumn("Temperatura Ambiente");
        datosPrueba.addColumn("Humedad relativa");
        datosPrueba.addColumn("LTOE (opacidad)");
        datosPrueba.addColumn("LTOE (densidad de humo)");
        datosPrueba.addColumn("Temperatura de motor inicial");
        datosPrueba.addColumn("Temperatura de motor final");

        resultadosEncendidoPorChispa = new DefaultTableModel();
        resultadosEncendidoPorChispa.addColumn("RPM Ralentí");
        resultadosEncendidoPorChispa.addColumn("HC Ralentí");
        resultadosEncendidoPorChispa.addColumn("CO Ralentí");
        resultadosEncendidoPorChispa.addColumn("CO2 Ralentí");
        resultadosEncendidoPorChispa.addColumn("O2 Ralentí");
        resultadosEncendidoPorChispa.addColumn("RPM Crucero");
        resultadosEncendidoPorChispa.addColumn("HC Crucero");
        resultadosEncendidoPorChispa.addColumn("CO Crucero");
        resultadosEncendidoPorChispa.addColumn("CO2 Crucero");
        resultadosEncendidoPorChispa.addColumn("O2 Crucero");

        resultadosPruebaEncendidoPorCompresion = new DefaultTableModel();//0
        resultadosPruebaEncendidoPorCompresion.addColumn("RPM Ralentí");//1
        resultadosPruebaEncendidoPorCompresion.addColumn("RPM GOBERNADA CICLO PRELIMINAR");//2
        resultadosPruebaEncendidoPorCompresion.addColumn("Resultado Ciclo Preliminar");//3
        resultadosPruebaEncendidoPorCompresion.addColumn("RPM Ralentí CICLO 1");//4
        resultadosPruebaEncendidoPorCompresion.addColumn("RPM GOBERNADA CICLO 1");//5
        resultadosPruebaEncendidoPorCompresion.addColumn("Resultado Ciclo 1");//6
        resultadosPruebaEncendidoPorCompresion.addColumn("RPM Ralentí CICLO 2");//7
        resultadosPruebaEncendidoPorCompresion.addColumn("RPM GOBERNADA CICLO 2");//8
        resultadosPruebaEncendidoPorCompresion.addColumn("Resultado Ciclo 2");//9
        resultadosPruebaEncendidoPorCompresion.addColumn("RPM Ralentí CICLO 3");//10
        resultadosPruebaEncendidoPorCompresion.addColumn("RPM GOBERNADA CICLO 3");//11
        resultadosPruebaEncendidoPorCompresion.addColumn("Resultado Ciclo 3");//12
        resultadosPruebaEncendidoPorCompresion.addColumn("PROMEDIO FINAL (%)");//13
        resultadosPruebaEncendidoPorCompresion.addColumn("CICLO PRELIMINAR (m-1)");//14
        resultadosPruebaEncendidoPorCompresion.addColumn("CICLO 1 (m-1)");//15
        resultadosPruebaEncendidoPorCompresion.addColumn("CICLO 2 (m-1)");//16
        resultadosPruebaEncendidoPorCompresion.addColumn("CICLO 3 (m-1)");//17
        resultadosPruebaEncendidoPorCompresion.addColumn("PROMEDIO FINAL (m-1)");//18
        resultadosPruebaEncendidoPorCompresion.addColumn("CONCEPTO FINAL (APROBADO / NO APROBADO)");//19

        causasRechazo = new DefaultTableModel();
        causasRechazo.addColumn("Presencia humo negro, azul");//0
        causasRechazo.addColumn("Presencia de dilucion");//1
        causasRechazo.addColumn("nivel de emision aplicable");//2
        causasRechazo.addColumn("Rpm fuera de rango");//3
        causasRechazo.addColumn("Fugas tubo escape");//4
        causasRechazo.addColumn("Salidas adicionales diseño");//5
        causasRechazo.addColumn("ausencia tapa aceite");//6
        causasRechazo.addColumn("ausencia tapa combustible");//7
        causasRechazo.addColumn("ausencia o mal estado filtro de aire");//8
        causasRechazo.addColumn("desconexion recirculacion");//9
        causasRechazo.addColumn("accesorios o deformacion en el t_escape");//10
        causasRechazo.addColumn("operacion incorrecta refrigeracion");//11
        causasRechazo.addColumn("emisiones");//12
        causasRechazo.addColumn("Incorrecta operacion gobernador");//13
        causasRechazo.addColumn("falla subita");//14
        causasRechazo.addColumn("ejecucion incorrecta");//15
        causasRechazo.addColumn("diferencia aritmetica");//16
        causasRechazo.addColumn("diferencia de temperatura");//17
        causasRechazo.addColumn("activación dispositivos");//18

        causasAborto = new DefaultTableModel();
        causasAborto.addColumn("Fecha aborto");
        causasAborto.addColumn("Falla equipo medicion");
        causasAborto.addColumn("falla subita fluido electrico");
        causasAborto.addColumn("bloqueo forzado equipo medicion");
        causasAborto.addColumn("Ejecucion incorrecta");
        causasAborto.addColumn("Falla desviacion cero");
        causasAborto.addColumn("Normal aplicada");

        ruido = new DefaultTableModel();
        ruido.addColumn("Ruido");
    }

    private void fillData(Date fechaInicial, Date fechaFinal){

        Corpocaldas todosLosDatos = Reportes.getCorpocaldas(fechaInicial, fechaFinal);

        List<DatosCda> datosCda = todosLosDatos.getDatosCda();
        List<InicioPruebaCorpocaldas> listaDatosInicioPruebas = todosLosDatos.getDatosInicioPrueba();
        List<AnalizadorCorpocaldas> listaDatosAnalizador = todosLosDatos.getDatosAnalizador();
        List<DatosVehiculoCorpocaldas> listaDatosVehiculos = todosLosDatos.getDatosVehiculo();
        List<DatosPruebaCorpocaldas> listaDatosPruebas = todosLosDatos.getDatosPrueba();
        List<EncendidoChispaCorpocaldas> listaDatosChispa = todosLosDatos.getDatosEncendidoChispa();
        List<EncendidoCompresionCorpocaldas> listaDatosCompresion = todosLosDatos.getDatosEncendidoCompresion();
        List<CausasRechazoCorpocaldas> listaCausasRechazo = todosLosDatos.getDatosCausaRechazo();
        List<CausasAbortoCorpocaldas> listaCausasAborto = todosLosDatos.getDatosCausaAborto();
        List<RuidoCorpocaldas> listaRuido = todosLosDatos.getDatosRuido();

        datosCda.stream().forEach(datoCda -> {
            Object[] fila = {
                datoCda.getNombreCda(),
                datoCda.getTipoDocumento(),
                datoCda.getNIT(),
                datoCda.getPersonaContacto(),
                datoCda.getCorreoElectronico(),
                datoCda.getTelefono(),
                datoCda.getDepartamento(),
                datoCda.getCiudad(),
                datoCda.getNoResolucionAuthAmbiental(),
                datoCda.getFechaResolucionAuthAmbiental(),
                datoCda.getClaseCda(),
                datoCda.getNoExpedienteAuthAmbiental(),
                datoCda.getNTotalOpacimetros(),
                datoCda.getNTotalOtto(),
                datoCda.getNTotal4t(),
                datoCda.getNTotal2t()
            };
            modeloCDA.addRow(fila);
        });

        listaDatosInicioPruebas.stream().forEach(datoInicio -> {
            Object[] fila = {
                datoInicio.getFechaInicio(),
                datoInicio.getFechaFin(),
                datoInicio.getMunicipio(),
                datoInicio.getDireccion(),
                datoInicio.getFur(),
                datoInicio.getNCertificado(),
                datoInicio.getNSerieEquipo(),
                datoInicio.getMarcaMedidor(),
                datoInicio.getNombreProveedor(),
                datoInicio.getNombrePrograma(),
                datoInicio.getVersionPrograma(),
                datoInicio.getIdInspector()
            };
            modeloInicioPrueba.addRow(fila);
        });

        listaDatosAnalizador.stream().forEach(analizador -> {
            Object[] fila = {
                analizador.getVrPef(),
                analizador.getNSerieBanco(),
                analizador.getMarcaAnalizador(),
                analizador.getVrSpanBajoHc(),
                analizador.getRVrSpanBajoHc(),
                analizador.getVrSpanBajoCo(),
                analizador.getRVrSpanBajoCo(),
                analizador.getVrSpanBajoCo2(),
                analizador.getRVrSpanBajoCo2(),
                analizador.getVrSpanAltoHc(),
                analizador.getRVrSpanAltoHc(),
                analizador.getVrSpanAltoCo(),
                analizador.getRVrSpanAltoCo(),
                analizador.getVrSpanAltoCo2(),
                analizador.getRVrSpanAltoCo2(),
                analizador.getFechaVerificacion()
            };
            equipoAnalizador.addRow(fila);
        });

        listaDatosVehiculos.stream().forEach(datoVehiculo -> {
            Object[] fila = {
                datoVehiculo.getPlaca(),
                datoVehiculo.getMarca(),
                datoVehiculo.getModelo(),
                datoVehiculo.getCilindraje(),
                datoVehiculo.getKilometraje(),
                datoVehiculo.getLinea(),
                datoVehiculo.getClase(),
                datoVehiculo.getServicio(),
                datoVehiculo.getCombustible(),
                datoVehiculo.getTipoMotor(),
                datoVehiculo.getNTubosEscape(),
                datoVehiculo.getDiseño()
            };
            modeloInfoVehiculo.addRow(fila);
        });

        listaDatosPruebas.stream().forEach(datoPrueba -> {
            Object[] fila = {
                datoPrueba.getTempAmbiente(),
                datoPrueba.getHumedadRelativa(),
                datoPrueba.getLtoeOpacidad(),
                datoPrueba.getLtoeDensidadHumo(),
                datoPrueba.getTempInicialMotor(),
                datoPrueba.getTempFinalMotor()
            };
            datosPrueba.addRow(fila);
        });

        listaDatosChispa.stream().forEach(encendidoChispa -> {
            Object[] fila = {
                encendidoChispa.getRpmRalenti(),
                encendidoChispa.getHcRalenti(),
                encendidoChispa.getCoRalenti(),
                encendidoChispa.getCo2Ralenti(),
                encendidoChispa.getO2Ralenti(),
                encendidoChispa.getRpmCrucero(),
                encendidoChispa.getHcCrucero(),
                encendidoChispa.getCoCrucero(),
                encendidoChispa.getCo2Crucero(),
                encendidoChispa.getO2Crucero()
            };
            resultadosEncendidoPorChispa.addRow(fila);
        });

        listaDatosCompresion.stream().forEach(compresion -> {
            Object[] fila = {
                compresion.getRpmRalenti(),
                compresion.getRpmGobernadaCicloPreliminar(),
                compresion.getResultadoCicloPreliminar(),
                compresion.getRpmRalentoCiclo1(),
                compresion.getRpmGobernadaCiclo1(),
                compresion.getResultadoCiclo1(),

                compresion.getRpmRalentiCiclo2(),
                compresion.getRpmGobernadaCiclo2(),
                compresion.getResultadoCiclo2(),

                compresion.getRpmRalentiCiclo3(),
                compresion.getRpmGobernadaCiclo3(),
                compresion.getResultadoCiclo3(),

                compresion.getPromedioFinal(),
                compresion.getCicloPreliminarM_1(),
                compresion.getCiclo1M_1(),
                compresion.getCiclo2M_1(),
                compresion.getCiclo3M_1(),
                compresion.getPromedioFinalM_1(),
                compresion.getConceptoFinal()
            };
            resultadosPruebaEncendidoPorCompresion.addRow(fila);
        });

        listaCausasRechazo.stream().forEach(rechazo -> {
            Object[] fila = {
                rechazo.getPresenciaHumo(),
                rechazo.getPresenciaDilucion(),
                rechazo.getNivelEmisionAplicable(),
                rechazo.getRpmFueraRango(),
                rechazo.getFugasTuboEscape(),
                rechazo.getSalidasAdicionales(),
                rechazo.getAusenciaTapaAceite(),
                rechazo.getAusenciaTapaCombustible(),
                rechazo.getAusenciaMalEstadoFiltroAire(),
                rechazo.getDesconexionRecirculacion(),
                rechazo.getAccesoriosDeformacionesTuboEscape(),
                rechazo.getOperacionIncorrectaRefrigeracion(),
                rechazo.getEmisiones(),
                rechazo.getIncorrectaOperacionGobernador(),
                rechazo.getFallaSubita(),
                rechazo.getEjecucionIncorrecta(),
                rechazo.getDiferenciaAritmetica(),
                rechazo.getDiferenciaTemp(),
                rechazo.getActivacionDispositivos()
            };
            causasRechazo.addRow(fila);
        });

        listaCausasAborto.stream().forEach(causaAborto -> {
            Object[] fila = {
                causaAborto.getFechaAborto(),
                causaAborto.getFallaEquipoMedicion(),
                causaAborto.getFallaSubitaFluido(),
                causaAborto.getBloqueoForzadoEquipo(),
                causaAborto.getEjecucionIncorrecta(),
                causaAborto.getFallaDesviacion(),
                causaAborto.getNormaApllicada()
            };
            causasAborto.addRow(fila);
        });

        listaRuido.stream().forEach(valorRuido -> {
            Object[] fila = {
                valorRuido.getRuido()
            };
            ruido.addRow(fila);
        });
        

        tblCda.setModel(modeloCDA);
        tblPruebas.setModel(modeloInicioPrueba);
        tblInfoVehiculos.setModel(modeloInfoVehiculo);
        tblResultadosPruebaEncendidoPorCompresion.setModel(resultadosPruebaEncendidoPorCompresion);
        tblEquipoAnalizador.setModel(equipoAnalizador);
        tblDatosPrueba.setModel(datosPrueba);
        tblResultadosEncendidoPorChispa.setModel(resultadosEncendidoPorChispa);
        tblCausasRechazo.setModel(causasRechazo);
        tblCausasAborto.setModel(causasAborto);
        tblRuido.setModel(ruido);

        tblCda.setEnabled(false);
        tblPruebas.setEnabled(false);
        tblInfoVehiculos.setEnabled(false);
        tblMedidas.setEnabled(false);
        tblResultadosPruebaEncendidoPorCompresion.setEnabled(false);
        tblEquipoAnalizador.setEnabled(false);
        tblDatosPrueba.setEnabled(false);
        tblResultadosEncendidoPorChispa.setEnabled(false);
        tblCausasRechazo.setEnabled(false);
        tblCausasAborto.setEnabled(false);
        tblRuido.setEnabled(false);
    }

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
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane11 = new javax.swing.JScrollPane();
        jScrollPane12 = new javax.swing.JScrollPane();
        jScrollPane13 = new javax.swing.JScrollPane();
        jScrollPane14 = new javax.swing.JScrollPane();
        jScrollPane15 = new javax.swing.JScrollPane();
        jScrollPane16 = new javax.swing.JScrollPane();
        jScrollPane17 = new javax.swing.JScrollPane();
        tblMedidas = new javax.swing.JTable();
        tblCda = new javax.swing.JTable();
        tblPruebas = new javax.swing.JTable();
        tblResultadosPruebaEncendidoPorCompresion = new javax.swing.JTable();
        tblEquipoAnalizador = new javax.swing.JTable();
        tblDatosPrueba = new javax.swing.JTable();
        tblResultadosEncendidoPorChispa = new javax.swing.JTable();
        tblCausasRechazo = new javax.swing.JTable();
        tblCausasAborto = new javax.swing.JTable();
        tblRuido = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(1024, 768));

        jLabel1.setText("Fecha Inicio:");
        jLabel2.setText("Fecha Fin:");
        btnGenerar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/soltelec/servidor/images/search.png"))); 
        btnGenerar.setText("Generar");
        btnGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarActionPerformed(evt);
            }
        });

        btnExportar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/soltelec/servidor/images/save.png"))); 
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
        tblCda.setName("Información del Cda."); 
        jScrollPane11.setViewportView(tblCda);
        infPrueba.addTab("CDA", jScrollPane11);

        tblPruebas.setModel(modeloInicioPrueba);
        tblPruebas.setName("Información de las pruebas"); 
        jScrollPane12.setViewportView(tblPruebas);
        infPrueba.addTab("Pruebas", jScrollPane12);

        tblEquipoAnalizador.setModel(equipoAnalizador);
        tblEquipoAnalizador.setName("PEF equipo utilizado"); 		
        jScrollPane1.setViewportView(tblEquipoAnalizador);
        infPrueba.addTab("PEF equipo utilizado", jScrollPane1);

        tblInfoVehiculos.setModel(modeloInfoVehiculo);
        tblInfoVehiculos.setName("Información de Vehiculo"); 
        jScrollPane13.setViewportView(tblInfoVehiculos);
        infPrueba.addTab("Inf. Vehiculo", jScrollPane13);
        
        tblDatosPrueba.setModel(datosPrueba);
        tblDatosPrueba.setName("Datos prueba"); 		
        jScrollPane14.setViewportView(tblDatosPrueba);
        infPrueba.addTab("Inf. datos de la prueba", jScrollPane14);

        tblResultadosEncendidoPorChispa.setModel(resultadosEncendidoPorChispa);
        tblResultadosEncendidoPorChispa.setName("Resultados encendido por chispa"); 		
        jScrollPane15.setViewportView(tblResultadosEncendidoPorChispa);
        infPrueba.addTab("Inf. Encendido por chispa", jScrollPane15);

        tblResultadosPruebaEncendidoPorCompresion.setModel(resultadosPruebaEncendidoPorCompresion);
        tblResultadosPruebaEncendidoPorCompresion.setName("Resultados encendido por compresion"); 		
        jScrollPane10.setViewportView(tblResultadosPruebaEncendidoPorCompresion);
        infPrueba.addTab("Inf. Encendido por compresion", jScrollPane10);

        tblCausasRechazo.setModel(causasRechazo);
        tblCausasRechazo.setName("Resultados causas rechazo"); 		
        jScrollPane2.setViewportView(tblCausasRechazo);
        infPrueba.addTab("Inf. causas rechazo", jScrollPane2);

        tblCausasAborto.setModel(causasAborto);
        tblCausasAborto.setName("Causas aborto"); 		
        jScrollPane16.setViewportView(tblCausasAborto);
        infPrueba.addTab("Inf. causas aborto", jScrollPane16);

        tblRuido.setModel(ruido);
        tblRuido.setName("Ruido"); 		
        jScrollPane17.setViewportView(tblRuido);
        infPrueba.addTab("Inf. Ruido", jScrollPane17);

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
    }                      

    public static void main(String[] args) {
        ReporteCorpocaldas rsv = new ReporteCorpocaldas();
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
        tables.add(tblCda);
        tables.add(tblPruebas);
        tables.add(tblEquipoAnalizador);
        tables.add(tblInfoVehiculos);
        tables.add(tblMedidas);
        tables.add(tblDatosPrueba);
        tables.add(tblResultadosEncendidoPorChispa);
        tables.add(tblResultadosPruebaEncendidoPorCompresion);
        tables.add(tblCausasRechazo);
        tables.add(tblCausasAborto);
        tables.add(tblRuido);
        GenericExportExcel excel = new GenericExportExcel();
        excel.exportSameSheet(tables);
    }

    

}
