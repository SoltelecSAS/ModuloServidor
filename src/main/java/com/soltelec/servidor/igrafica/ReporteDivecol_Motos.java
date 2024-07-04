/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.igrafica;

import static com.soltelec.servidor.conexion.PersistenceController.getEntityManager;
import com.soltelec.servidor.dao.CalibracionDosPuntosJpaController;
import com.soltelec.servidor.dao.CdaJpaController;
import com.soltelec.servidor.dao.CertificadosJpaController;
import com.soltelec.servidor.dao.EquiposJpaController;
import com.soltelec.servidor.dao.HojaPruebasJpaController;
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
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import com.soltelec.servidor.model.Equipos;
/**
 * todos los clientes de los cda's deben poder generar este reporte para ser
 * enviado por medio del software "vigia" a la superintendencia.
 *
 * @requerimiento SART-2 Creación de reporte de inspecciones
 * @author Gerencia Desarollo Tecnologica
 * @fecha 01/03/2018
 */
public class ReporteDivecol_Motos extends javax.swing.JInternalFrame {
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

    public ReporteDivecol_Motos() {
        super("Reporte Divecol(Motos)",
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
        modeloCDA = new DefaultTableModel();
        modeloCDA.addColumn("Fecha Inicio de Prueba");
        modeloCDA.addColumn("Fecha Fin de Prueba");
        modeloCDA.addColumn("Municipio de Inspeccion");
        modeloCDA.addColumn("Lugar de prueba");
        modeloCDA.addColumn("Numero de Inspeccion (FUR)");
        modeloCDA.addColumn("Numero de Certificado Emitido");
        modeloCDA.addColumn("Nombre Completo Inspector que realiza la Prueba");
        modeloCDA.addColumn("Temperatura ambiente °C");
        modeloCDA.addColumn("Método medición T°");
        modeloCDA.addColumn("Humedad Relativa %");
        modeloCDA.addColumn("Causal del aborto");
        modeloCDA.addColumn("Fecha Aborto");

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

        modeloDatosEquipoYSoftware = new DefaultTableModel();
        modeloDatosEquipoYSoftware.addColumn("No. serie  analizador");
        modeloDatosEquipoYSoftware.addColumn("Marca del analizador");
        modeloDatosEquipoYSoftware.addColumn("Valor PEF");
        modeloDatosEquipoYSoftware.addColumn("Nombre del software de aplicación");
        modeloDatosEquipoYSoftware.addColumn("Versión software de aplicación");
        modeloDatosEquipoYSoftware.addColumn("SPAN HC Baja ppm");
        modeloDatosEquipoYSoftware.addColumn("SPAN CO Baja %");
        modeloDatosEquipoYSoftware.addColumn("SPAN CO2 Baja %");
        modeloDatosEquipoYSoftware.addColumn("Valor leído HC Baja ppm");
        modeloDatosEquipoYSoftware.addColumn("Valor leído  CO Baja %");
        modeloDatosEquipoYSoftware.addColumn("Valor leído  CO2 Baja %");
        modeloDatosEquipoYSoftware.addColumn("SPAN HC Alta ppm");
        modeloDatosEquipoYSoftware.addColumn("SPAN CO Alta %");
        modeloDatosEquipoYSoftware.addColumn("SPAN CO2 Alta %");
        modeloDatosEquipoYSoftware.addColumn("Valor leído HC Alta ppm");
        modeloDatosEquipoYSoftware.addColumn("Valor leído CO Alta %");
        modeloDatosEquipoYSoftware.addColumn("Valor leído CO2 Alta %");
        modeloDatosEquipoYSoftware.addColumn("Fecha de Verificación");
        modeloDatosEquipoYSoftware.addColumn("Resultado de Verificación");

        modeloResolucionInspeccionRealizada = new DefaultTableModel();
        modeloResolucionInspeccionRealizada.addColumn("Temperatura Motor °C");
        modeloResolucionInspeccionRealizada.addColumn("RPM Ralentí");
        modeloResolucionInspeccionRealizada.addColumn("HC Ralentí ppm");
        modeloResolucionInspeccionRealizada.addColumn("CO Ralentí %");
        modeloResolucionInspeccionRealizada.addColumn("CO2 Ralentí %");
        modeloResolucionInspeccionRealizada.addColumn("O2 Ralentí %");
        modeloResolucionInspeccionRealizada.addColumn("Presencia Dilución");
        modeloResolucionInspeccionRealizada.addColumn("Concepto Final");
        modeloResolucionInspeccionRealizada.addColumn("Fugas tubo escape");
        modeloResolucionInspeccionRealizada.addColumn("Fugas silenciador");
        modeloResolucionInspeccionRealizada.addColumn("Salidas adicionales");
        modeloResolucionInspeccionRealizada.addColumn("Ausencia o mal estado tapón combustible");
        modeloResolucionInspeccionRealizada.addColumn("Ausencia o mal estado tapón aceite");
        modeloResolucionInspeccionRealizada.addColumn("Humo negro o azul");
        modeloResolucionInspeccionRealizada.addColumn("RPM fuera de rango");

    }

    private void fillData(Date fechaInicial, Date fechaFinal) {
        initModels();
        DateFormat formatoFechas = new SimpleDateFormat("yyyy-MM-dd");
        formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        sdfH = new SimpleDateFormat("YYYY/MM/dd HH:mm");
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
            if (pruebasebas.getFinalizada().equalsIgnoreCase("Y") && pruebasebas.getHojaPruebas().getVehiculos().getTipoVehiculo().getCartype().equals(4)) {
                work = work + 1;
                System.out.println("proc..!  " + work + " de " + lng);
                if (work > lng) {
                    JOptionPane.showMessageDialog(null, "proc..!  " + work + " de " + lng);
                }

                cargarInformacionCda(pruebasebas);
                cargarEquipos(pruebasebas);
                cargarInformacionvehiculo(pruebasebas, form);
                cargarResultadosInspeccion(pruebasebas, fechaInicial, fechaFinal);

                this.tblCda.setModel(modeloCDA);
                this.tblInfoVehiculos.setModel(modeloInfoVehiculo);
                //this.tblPropietario.setModel(modeloDatosPropietarioVehiculo);
                this.tblEquiposYSoftware.setModel(modeloDatosEquipoYSoftware);
                //this.tblDatosGeneralesInspeccion.setModel(modeloDatosGeneralesInspeccion);u
                this.tblResolucionInspeccionRealizada.setModel(modeloResolucionInspeccionRealizada);

                tblCda.setEnabled(false);
                tblInfoVehiculos.setEnabled(false);
                tblMedidas.setEnabled(false);
                tblPropietario.setEnabled(false);
                tblEquiposYSoftware.setEnabled(false);
                tblDatosGeneralesInspeccion.setEnabled(false);
                tblResolucionInspeccionRealizada.setEnabled(false);

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
        tblEquiposYSoftware.setModel(modeloDatosEquipoYSoftware);
        tblEquiposYSoftware.setName("Información de Equipo Analizador"); // NOI18N		
        jScrollPane13.setViewportView(tblEquiposYSoftware);
        infPrueba.addTab("Datos Equipo Analizador", jScrollPane13);

        //Tabla datos generales inspeccion
//
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
        ReporteDivecol_Motos rsv = new ReporteDivecol_Motos();
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
        tables.add(tblPropietario);
        tables.add(tblInfoVehiculos);
        tables.add(tblMedidas);
        tables.add(tblEquiposYSoftware);
        tables.add(tblDatosGeneralesInspeccion);
        tables.add(tblResolucionInspeccionRealizada);
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
    private javax.swing.JTable tblCda;
    private javax.swing.JTable tblPropietario;
    private javax.swing.JTable tblMedidas;
    private javax.swing.JTable tblInfoVehiculos;
    private javax.swing.JTable tblEquiposYSoftware;
    private javax.swing.JTable tblDatosGeneralesInspeccion;
    private javax.swing.JTable tblResolucionInspeccionRealizada;

    private void cargarInformacionCda(Pruebas pruebasebas) {
        formatoFechas = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        CdaJpaController cda = new CdaJpaController();
        Cda mcda = cda.findCda(1);
        Object[] objCda = new Object[17];

        objCda[0] = formatoFechas.format(pruebasebas.getFechaFinal());
        objCda[1] = formatoFechas.format(pruebasebas.getFechaPrueba());
        objCda[2] = mcda.getCiudad();
        objCda[3] = mcda.getCiudad();
        objCda[4] = pruebasebas.getHojaPruebas().getConHojaPrueba();
        CertificadosJpaController cert = new CertificadosJpaController();
        Certificados certificado = cert.findCertificadoHojaPrueba(pruebasebas.getHojaPruebas().getTestsheet());
        objCda[5] = certificado.getConsecutive();
        objCda[6] = pruebasebas.getUsuarios().getNombreusuario();
        objCda[8] = "Tapa Embrague";
        for (Medidas medidas : pruebasebas.getMedidasList()) {
            if (medidas.getTiposMedida().getMeasuretype() == 8031) {
                objCda[7] = medidas.getValormedida();
            } else if (medidas.getTiposMedida().getMeasuretype() == 8032) {
                objCda[9] = medidas.getValormedida();
            }
        }
        if (pruebasebas.getAbortada().equals("A") || pruebasebas.getAbortada().equals("Y")) {
            objCda[10] = "SI";
            objCda[11] = formatoFechas.format(pruebasebas.getFechaFinal());
        } else {
            objCda[10] = "NO";
            objCda[11] = "";
        }
        modeloCDA.addRow(objCda);
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
        objInfoVehiculo[8] = pruebasebas.getHojaPruebas().getVehiculos().getTiemposmotor() + "T";
        objInfoVehiculo[9] = pruebasebas.getHojaPruebas().getVehiculos().getNumeroexostos();
        objInfoVehiculo[10] = pruebasebas.getHojaPruebas().getVehiculos().getDiseno();

        modeloInfoVehiculo.addRow(objInfoVehiculo);
    }

    private void cargarResultadosInspeccion(Pruebas pruebasebas, Date fechaInicial, Date fechaFinal) {


        /* modeloResolucionInspeccionRealizada.addColumn("Temperatura Motor °C");//0
        modeloResolucionInspeccionRealizada.addColumn("RPM Ralentí");//1
        modeloResolucionInspeccionRealizada.addColumn("HC Ralentí ppm");//2
        modeloResolucionInspeccionRealizada.addColumn("CO Ralentí %");//3
        modeloResolucionInspeccionRealizada.addColumn("CO2 Ralentí %");//4
        modeloResolucionInspeccionRealizada.addColumn("O2 Ralentí %");//5
        modeloResolucionInspeccionRealizada.addColumn("Presencia Dilución");//6
        modeloResolucionInspeccionRealizada.addColumn("Concepto Final");//7
        modeloResolucionInspeccionRealizada.addColumn("Fugas tubo escape");//8
        modeloResolucionInspeccionRealizada.addColumn("Fugas silenciador");//9
        modeloResolucionInspeccionRealizada.addColumn("Salidas adicionales");//10
        modeloResolucionInspeccionRealizada.addColumn("Ausencia o mal estado tapón combustible");//11
        modeloResolucionInspeccionRealizada.addColumn("Ausencia o mal estado tapón aceite");//12
        modeloResolucionInspeccionRealizada.addColumn("Humo negro o azul");//13
        modeloResolucionInspeccionRealizada.addColumn("RPM fuera de rango");//14 */

        Object[] objProp = new Object[55];
        for (Medidas medidas : pruebasebas.getMedidasList()) {
            if (null != medidas.getTiposMedida().getMeasuretype()) {
                switch (medidas.getTiposMedida().getMeasuretype()) {
                    case 8006:
                        objProp[0] = medidas.getValormedida();
                        break;
                    case 8022:
                        objProp[0] = medidas.getValormedida();
                        break;
                    case 8005:
                        objProp[1] = medidas.getValormedida();
                        break;
                    case 8028:
                        objProp[1] = medidas.getValormedida();
                        break;
                    case 8001:
                        objProp[2] = medidas.getValormedida();
                        break;
                    case 8018:
                        objProp[2] = medidas.getValormedida();
                        break;
                    case 8002:
                        objProp[3] = medidas.getValormedida();
                        break;
                    case 8020:
                        objProp[3] = medidas.getValormedida();
                        break;
                    case 8003:
                        objProp[4] = medidas.getValormedida();
                        break;
                    case 8019:
                        objProp[4] = medidas.getValormedida();
                        break;
                    case 8004:
                        objProp[5] = medidas.getValormedida();
                        if (medidas.getValormedida() > 6) {
                            objProp[6] = "SI";
                        } else {
                            objProp[6] = "NO";
                        }
                        break;
                    case 8021:
                        objProp[5] = medidas.getValormedida();
                        if (pruebasebas.getHojaPruebas().getVehiculos().getModelo() <= 2010) {
                            if (medidas.getValormedida() > 11) {
                                objProp[6] = "SI";
                            } else {
                                objProp[6] = "NO";
                            }
                        } else {
                            if (medidas.getValormedida() > 6) {
                                objProp[6] = "SI";
                            } else {
                                objProp[6] = "NO";
                            }
                        }

                        break;
                    default:
                        break;
                }
            }
        }

        if (pruebasebas.getHojaPruebas().getAprobado().equals("Y")) {
            objProp[7] = "APROBADA";
        } else {
            objProp[7] = "REPOROBRADA";
        }

        if (pruebasebas.getObservaciones() != null) {
//            if (pruebasebas.getObservaciones().contains("DILUCION")) {
//                objProp[6] = "SI";
//            } else {
//                objProp[6] = "NO";
//            }
            if (pruebasebas.getObservaciones().contains("FUGAS") || pruebasebas.getObservaciones().contains("TUBO")) {
                objProp[8] = "SI";
            } else {
                objProp[8] = "NO";
            }
            if (pruebasebas.getObservaciones().contains("silenciador") || pruebasebas.getObservaciones().contains("SILENCIADOR")) {
                objProp[9] = "SI";
            } else {
                objProp[9] = "NO";
            }

            if (pruebasebas.getObservaciones().contains("Presencia Humo") || pruebasebas.getObservaciones().contains("PRESENCIA HUMO")) {
                objProp[13] = "SI";
            } else {
                objProp[13] = "NO";
            }

            if (pruebasebas.getObservaciones().contains("REVOLUCIONES") || pruebasebas.getObservaciones().contains("Revoluciones")) {
                objProp[14] = "SI";
            } else {
                objProp[14] = "NO";
            }

        } else {
            //objProp[6] = "NO";
            objProp[8] = "NO";
            objProp[9] = "NO";
            objProp[13] = "NO";
            objProp[14] = "NO";

        }

        objProp[10] = "NO";
        objProp[11] = "NO";
        objProp[12] = "NO";
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
                                if (arrCondiciones[i].contains("Salidas adicionales en el sistema de escape")
                                        || arrCondiciones[i].contains(" Salidas adicionales en el sistema de escape")) {
                                    objProp[10] = "SI";
                                }
                                if (arrCondiciones[i].contains("Ausencia de tapones de combustible")) {
                                    objProp[11] = "SI";
                                }
                                if (arrCondiciones[i].contains("Ausencia de tapones de aceite")) {
                                    objProp[12] = "SI";
                                }

                            }

                        }
                    }
                }
            }
        }

        modeloResolucionInspeccionRealizada.addRow(objProp);
    }

    private void cargarEquipos(Pruebas pruebasebas) {
        Object[] objProp = new Object[55];
        formatoFechas = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        System.out.println("placa" + pruebasebas.getHojaPruebas().getVehiculos().getCarplate());
        CalibracionDosPuntosJpaController cal2PuntosJpa = new CalibracionDosPuntosJpaController();
        List<String> fragmentos = separarCadena(pruebasebas.getSerialEquipo());
        EquiposJpaController equiposJpaController = new EquiposJpaController();
        Equipos eq = new Equipos();
        try {
            Object[] cal = eq.findDosPuntos(pruebasebas.getFechaPrueba(), pruebasebas.getIdPruebas());

            int Idequipos = equiposJpaController.buscarEquiposBySerialRes(pruebasebas.getSerialEquipo());
            Equipos equipos = equiposJpaController.findEquipos(Idequipos);
            int PEF = equipos.getPef();
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            float pefConversion = (float) PEF / 1000f;
            System.out.println("-------" + PEF);
            String serialResolucion = pruebasebas.getSerialEquipo();
            Integer idEquipo = equiposJpaController.buscarEquiposBySerialRes(serialResolucion);
            Equipos equipo = equiposJpaController.findEquipos(idEquipo);

            objProp[0] = cal[21];
            objProp[1] = cal[17];
            objProp[2] = cal[19];
            objProp[3] = "SART";
            objProp[4] = "1.7.3";
            objProp[5] = cal[38];
            objProp[6] = cal[36];
            objProp[7] = cal[37];
            objProp[8] = cal[45];
            objProp[9] = cal[42];
            objProp[10] = cal[44];
            objProp[11] = cal[41];
            objProp[12] = cal[39];
            objProp[13] = cal[40];
            objProp[14] = cal[49];
            objProp[15] = cal[46];
            objProp[16] = cal[47];
            objProp[17] = formatoFechas.format(cal[1]);
            if (cal[3].equals(1)) {
                objProp[18] = "APROBADA";
            } else {
                objProp[18] = "REPROBADA";
            }

        } catch (Exception e) {
            cal2 = new Object[60];
            System.out.println("-----------" + e);
            e.printStackTrace();
        }
        modeloDatosEquipoYSoftware.addRow(objProp);
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

    public static List<String> separarCadena(String cadena) {
        String[] fragmentosArray = cadena.split(";");
        List<String> fragmentosList = new ArrayList<>();
        
        for (String fragmento : fragmentosArray) {
            fragmentosList.add(fragmento);
        }
        
        return fragmentosList;
    }
}
