/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.igrafica;

import com.soltelec.servidor.consultas.Reportes;
import com.soltelec.servidor.dtos.reporte_corantioquia_cornare.CorantioquiaCornare;
import com.soltelec.servidor.dtos.reporte_corantioquia_cornare.DatosPruebaCorantioquia;
import com.soltelec.servidor.dtos.reporte_corantioquia_cornare.DatosVehiculoCorantioquia;
import com.soltelec.servidor.dtos.reporte_corantioquia_cornare.EquipoAnalizadorCorantioquia;
import com.soltelec.servidor.dtos.reporte_corantioquia_cornare.ResultadoPruebaCorantioquia;
import com.soltelec.servidor.utils.GenericExportExcel;
import com.soltelec.servidor.utils.JTitlePanel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class ReporteCorAntioquia extends javax.swing.JInternalFrame {

    JFileChooser fc;
    JPanel componentPanel;
    JTitlePanel componentTitlePanel;
    private DefaultTableModel modeloReporte;
    private JTable ReporteTabla;

    private DefaultTableModel datosVehiculos;
    private DefaultTableModel datosPrueba;
    private DefaultTableModel resultadosPrueba;
    private DefaultTableModel datosEquipoAnalizadorVariable;

    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker2;
    private javax.swing.JTabbedPane tabla4;
    private javax.swing.JTable tblDatosVehiculos;
    private javax.swing.JTable tblDatosPruebas;
    private javax.swing.JTable tblDatosResultadosP;
    private javax.swing.JTable tblDatosEquipo;

    /**
     * Creates new form ReportGeneral
     */
    public ReporteCorAntioquia() {
        super("Reporte CorAntioquia",
                true, //resizable
                true, //closable
                false, //maximizable
                true);
        iniciarModelo();
        initComponents();
    }

    private void iniciarModelo() {
        this.ReporteTabla = new JTable();
        this.modeloReporte = new DefaultTableModel();
//      ******Datos del Vehiculo****** 
        datosVehiculos = new DefaultTableModel();
        datosVehiculos.addColumn("Numero de Certificado");
        datosVehiculos.addColumn("Marca");
        datosVehiculos.addColumn("Año Modelo");
        datosVehiculos.addColumn("Placa");
        datosVehiculos.addColumn("Cilindraje (cm3)");
        datosVehiculos.addColumn("Tipo de Motor");
        datosVehiculos.addColumn("Diseño");

//      ******Datos de la Prueba******
        datosPrueba = new DefaultTableModel();
        datosPrueba.addColumn("fecha de Realizacion de Prueba");
        datosPrueba.addColumn("Inspector");
        datosPrueba.addColumn("Temperatura Ambiente");
        datosPrueba.addColumn("Humedad Relativa");
        datosPrueba.addColumn("Ciudad");
        datosPrueba.addColumn("Direccion");

//         ******Resultados de la Prueba******
        resultadosPrueba = new DefaultTableModel();
        resultadosPrueba.addColumn("Temperatura de Motor");
        resultadosPrueba.addColumn("RPM Ralenti");
        resultadosPrueba.addColumn("HC Ralenti (ppm)");
        resultadosPrueba.addColumn("CO Ralenti (%)");
        resultadosPrueba.addColumn("CO2 Ralenti (%)");
        resultadosPrueba.addColumn("02 Ralenti (%)");
        resultadosPrueba.addColumn("Presencia Dilucion");
        resultadosPrueba.addColumn("Concepto Final");

        datosEquipoAnalizadorVariable = new DefaultTableModel(); //749332077
        datosEquipoAnalizadorVariable.addColumn("PEF");
        datosEquipoAnalizadorVariable.addColumn("Marca-Analizador");
        datosEquipoAnalizadorVariable.addColumn("Serie-Analizador");
        datosEquipoAnalizadorVariable.addColumn("Soft. Aplicacion");
        datosEquipoAnalizadorVariable.addColumn("Soft. version");

        datosEquipoAnalizadorVariable.addColumn("Span HC Baja (ppm)");
        datosEquipoAnalizadorVariable.addColumn("Span CO Baja %");
        datosEquipoAnalizadorVariable.addColumn("Span CO2 Baja %");

        datosEquipoAnalizadorVariable.addColumn("Valor Leido HC Baja (ppm)");
        datosEquipoAnalizadorVariable.addColumn("Valor Leido CO Baja %");
        datosEquipoAnalizadorVariable.addColumn("Valor Leido C02 Baja %");

        datosEquipoAnalizadorVariable.addColumn("Span HC Alta (ppm)");
        datosEquipoAnalizadorVariable.addColumn("Span CO Alta %");
        datosEquipoAnalizadorVariable.addColumn("Span CO2 Alta %");

        datosEquipoAnalizadorVariable.addColumn("Valor Leido HC Alta (ppm)");
        datosEquipoAnalizadorVariable.addColumn("Valor Leido CO Alta %");
        datosEquipoAnalizadorVariable.addColumn("Valor Leido CO2 Alta %");

        datosEquipoAnalizadorVariable.addColumn("Fecha de Verificacion");
        datosEquipoAnalizadorVariable.addColumn("Resultado de la Verificacion");

//----------   modelo que se imprime
        modeloReporte.addColumn("Numero de Certificado");
        modeloReporte.addColumn("Marca");
        modeloReporte.addColumn("Año Modelo");
        modeloReporte.addColumn("Placa");
        modeloReporte.addColumn("Cilindraje (cm3)");
        modeloReporte.addColumn("Tipo de Motor");
        modeloReporte.addColumn("Diseño");
        modeloReporte.addColumn("fecha de Realizacion de Prueba");
        modeloReporte.addColumn("Inspector");
        modeloReporte.addColumn("Temperatura Ambiente");
        modeloReporte.addColumn("Humedad Relativa");
        modeloReporte.addColumn("Ciudad");
        modeloReporte.addColumn("Direccion");
        modeloReporte.addColumn("Temperatura de Motor");
        modeloReporte.addColumn("RPM Ralenti");
        modeloReporte.addColumn("HC Ralenti (ppm)");
        modeloReporte.addColumn("CO Ralenti (%)");
        modeloReporte.addColumn("CO2 Ralenti (%)");
        modeloReporte.addColumn("02 Ralenti (%)");
        modeloReporte.addColumn("Presencia Dilucion");
        modeloReporte.addColumn("Concepto Final");
        modeloReporte.addColumn("PEF");
        modeloReporte.addColumn("Marca-Analizador");
        modeloReporte.addColumn("Serie-Analizador");
        modeloReporte.addColumn("Soft. Aplicacion");
        modeloReporte.addColumn("Soft. version");
        modeloReporte.addColumn("Span HC Baja (ppm)");
        modeloReporte.addColumn("Span CO Baja %");
        modeloReporte.addColumn("Span CO2 Baja %");
        modeloReporte.addColumn("Valor Leido HC Baja (ppm)");
        modeloReporte.addColumn("Valor Leido CO Baja %");
        modeloReporte.addColumn("Valor Leido C02 Baja %");
        modeloReporte.addColumn("Span HC Alta (ppm)");
        modeloReporte.addColumn("Span CO Alta %");
        modeloReporte.addColumn("Span CO2 Alta %");
        modeloReporte.addColumn("Valor Leido HC Alta (ppm)");
        modeloReporte.addColumn("Valor Leido CO Alta %");
        modeloReporte.addColumn("Valor Leido CO2 Alta %");
        modeloReporte.addColumn("Fecha de Verificacion");
        modeloReporte.addColumn("Resultado de la Verificacion");

//---------- 


    }

    private void fillData(Date fInicial, Date fFinal) {

        CorantioquiaCornare todosLosDatos = Reportes.getCorantioquiaOrCornare(fInicial, fFinal);

        List<DatosVehiculoCorantioquia> datosVehiculosDTO = todosLosDatos.getDatosVehiculo();
        List<DatosPruebaCorantioquia> datosPruebasDTO = todosLosDatos.getDatosPrueba();
        List<ResultadoPruebaCorantioquia> datosResultadoPruebasDTO = todosLosDatos.getDatosResultadoPruebas();
        List<EquipoAnalizadorCorantioquia> datosEquipoAnalizadorDTO = todosLosDatos.getDatosEquipoAnalizadors();

        datosVehiculosDTO.stream().forEach(datosVehiculo -> {
            Object[] fila = {
                datosVehiculo.getNumeroCertificado(),
                datosVehiculo.getMarca(),
                datosVehiculo.getAnoModelo(),
                datosVehiculo.getPlaca(),
                datosVehiculo.getCilindrajeCm3(),
                datosVehiculo.getTipoMotor(),
                datosVehiculo.getDiseño()
            };
            datosVehiculos.addRow(fila);
        });

        datosPruebasDTO.stream().forEach(datoPrueba ->{
            Object [] fila ={
                datoPrueba.getFechaPrueba(),
                datoPrueba.getNombreUsuario(),
                datoPrueba.getTemperaturaAmbiente(),
                datoPrueba.getHumedadRelativa(),
                datoPrueba.getCiudad(),
                datoPrueba.getDireccion()
            };
            datosPrueba.addRow(fila);
        });

        datosResultadoPruebasDTO.stream().forEach(resultado ->{
            Object [] fila ={
                resultado.getTemperaturaMotor(),
                resultado.getRpmRalenti(),
                resultado.getHcRalenti(),
                resultado.getCoRalenti(),
                resultado.getCo2Ralenti(),
                resultado.getO2Ralenti(),
                resultado.getPresenciaDilucion(),
                resultado.getConceptoFinal()
            };
            resultadosPrueba.addRow(fila);
        });

        datosEquipoAnalizadorDTO.stream().forEach(equipo ->{
            Object [] fila ={
                equipo.getPef(),
                equipo.getMarca(),
                equipo.getSerie(),
                equipo.getSoftAplicacion(),
                equipo.getSoftVersion(),
                equipo.getSpanHcBaja(),
                equipo.getSpanCoBaja(),
                equipo.getSpanCo2Baja(),
                equipo.getValorLeidoHcBaja(),
                equipo.getValorLeidoCoBaja(),
                equipo.getValorLeidoCo2Baja(),
                equipo.getSpanHcAlta(),
                equipo.getSpanCoAlta(),
                equipo.getSpanCo2Alta(),
                equipo.getValorLeidoHcAlta(),
                equipo.getValorLeidoCoAlta(),
                equipo.getValorLeidoCo2Alta(),
                equipo.getFechaVerificacion(),
                equipo.getResultadoVerificacion()
            };
            datosEquipoAnalizadorVariable.addRow(fila);
            System.out.println(equipo);
        });

        tblDatosVehiculos.setModel(datosVehiculos);
        tblDatosPruebas.setModel(datosPrueba);
        tblDatosResultadosP.setModel(resultadosPrueba);
        tblDatosEquipo.setModel(datosEquipoAnalizadorVariable);
        ReporteTabla.setModel(modeloReporte);
        tblDatosVehiculos.setEnabled(false);
        tblDatosPruebas.setEnabled(false);
        tblDatosResultadosP.setEnabled(false);
        tblDatosEquipo.setEnabled(false);
        ReporteTabla.setEnabled(false);
    }

    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();
        jXDatePicker1.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
        jLabel2 = new javax.swing.JLabel();
        jXDatePicker2 = new org.jdesktop.swingx.JXDatePicker();
        jXDatePicker2.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        tabla4 = new javax.swing.JTabbedPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblDatosVehiculos = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDatosPruebas = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDatosResultadosP = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblDatosEquipo = new javax.swing.JTable();

        setClosable(true);
        setPreferredSize(new java.awt.Dimension(816, 662));

        jLabel1.setText("Fecha Inicio:");

        jLabel2.setText("Fecha Fin:");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/soltelec/servidor/images/green_flag.png"))); // NOI18N
        jButton1.setText("Generar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/soltelec/servidor/images/save.png"))); // NOI18N
        jButton2.setText("Exportar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        tabla4.setName("Reporte CorAntioquia"); // NOI18N

        tblDatosVehiculos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblDatosVehiculos.setName("Datos Del Vehiculo"); // NOI18N
        jScrollPane3.setViewportView(tblDatosVehiculos);

        tabla4.addTab("Datos del Vehiculo", jScrollPane3);

        tblDatosPruebas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblDatosPruebas.setName("Datos de la Prueba"); // NOI18N
        jScrollPane1.setViewportView(tblDatosPruebas);

        tabla4.addTab("Datos de la Prueba", jScrollPane1);

        tblDatosResultadosP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblDatosResultadosP.setName("Resultados de la Prueba"); // NOI18N
        jScrollPane2.setViewportView(tblDatosResultadosP);

        tabla4.addTab("Resultados de la Prueba", jScrollPane2);

        tblDatosEquipo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblDatosEquipo.setName("Datos del Equipo Analizador"); // NOI18N
        jScrollPane4.setViewportView(tblDatosEquipo);

        tabla4.addTab("Datos del Equipo Analizador", jScrollPane4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jXDatePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap(223, Short.MAX_VALUE))
            .addComponent(tabla4, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jXDatePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tabla4, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (jXDatePicker1.getDate() == null || jXDatePicker2.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Seleccione Fechas");
            return;
        }
        
        // Es necesario porque lo toma desde las 00:00 en lugar de las 23:59 de ese dia
        Date fechaFinal = ajustarFechaFinal(jXDatePicker2.getDate()); 

        fillData(jXDatePicker1.getDate(), fechaFinal);

    }

    private Date ajustarFechaFinal(Date finalDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(finalDate);

        /* Añade un dia ya que lo selecciona desde las 00:00 en lugar de las 23:59,
        que es lo mismo que las 00:00 del siguiente dia si lo pensamos*/
        calendar.add(Calendar.DAY_OF_MONTH, 1); 

        return calendar.getTime();
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
		List<JTable> tables = new ArrayList<>();
        ReporteTabla.setName("Reporte CorAntioquia");
        tables.add(tblDatosVehiculos);
        tables.add(tblDatosPruebas);
        tables.add(tblDatosResultadosP);
        tables.add(tblDatosEquipo);

        GenericExportExcel excel = new GenericExportExcel();
        excel.exportExcel(tables);
    }

}
