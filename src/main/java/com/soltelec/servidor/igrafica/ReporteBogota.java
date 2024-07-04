package com.soltelec.servidor.igrafica;

/*
*
* Importaciones de librerias y codigo del modulo de Servidor
 */
import com.soltelec.servidor.conexion.PersistenceController;
import static com.soltelec.servidor.conexion.PersistenceController.getEntityManager;
import com.soltelec.servidor.dao.CdaJpaController;
import com.soltelec.servidor.dao.CertificadosJpaController;
import com.soltelec.servidor.dao.EquiposJpaController;
import com.soltelec.servidor.dao.HojaPruebasJpaController;
import com.soltelec.servidor.dao.PruebasJpaController;
import com.soltelec.servidor.dao.SoftwareJpaController;
import com.soltelec.servidor.model.CalibracionDosPuntos;
import com.soltelec.servidor.model.Cda;
import com.soltelec.servidor.model.Certificados;
import com.soltelec.servidor.model.Defxprueba;
import com.soltelec.servidor.model.Diseno;
import com.soltelec.servidor.model.HojaPruebas;
import com.soltelec.servidor.model.Medidas;
import com.soltelec.servidor.model.Pruebas;
import com.soltelec.servidor.model.Reinspeccion;
import com.soltelec.servidor.model.Software;
import com.soltelec.servidor.utils.GenericExportExcel;
import java.text.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import org.codehaus.groovy.tools.shell.ParseCode;

/**
 * @requerimiento Creacion de Reporte Ecce
 * @author Gerencia Desarollo Tecnologica
 * @author Diego Garzon
 * @fecha 17/10/2019
 */
public class ReporteBogota extends javax.swing.JInternalFrame {

    /*
    *
    *   Variables de las tablas 
    *
     */
    private DefaultTableModel modeloCDA;
    private DefaultTableModel modeloEquipos;
    private DefaultTableModel modeloDatosPrueba;
    private DefaultTableModel modeloPropietario;
    private DefaultTableModel modeloVehiculo;
    private DefaultTableModel modeloRelPruebas;
    //Variables del formato de fechas
    private DateFormat formatoFechas, formatoFechaExt;
    private static CalibracionDosPuntos CalDosPuntos;
    private static com.soltelec.servidor.model.Equipos CalEquipos;
    private static Date ctxFecha;

    /*
    * 
    * Variables para la GUI Swing interfaz grafica
    *
     */
    private javax.swing.JTable tblCda;
    private javax.swing.JTable tblCalibracion;
    private javax.swing.JTable tblPruebas;
    private javax.swing.JTable tblPropietarios;
    private javax.swing.JTable tblVehiculos;
    private javax.swing.JTable tblResultadoPrueba;
    // Variables de los botones    
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btnGenerar;
    //Variables de ingresar los datos    
    private org.jdesktop.swingx.JXDatePicker fechaFInal;
    private org.jdesktop.swingx.JXDatePicker fechaInicial;
    //Variable de GUI de la tabla    
    private javax.swing.JTabbedPane infPrueba;
    //Variablede los label de las fechas    
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    //Variables de los ScrollPanell    
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private static final Logger LOG = Logger.getLogger(ReporteCAR.class.getName());

    /*
    *
    *   Constructor Reporte Bogota
    *
     */
    public ReporteBogota() {
        super("Reporte Bogotá D.C",
                true, //resizable
                true, //closable
                false, //maximizable
                true);
        initModels();
        initComponents();
    }

    /*
    *
    * Metodo initModels() es donde esta todas las tablas y columnas del CDA 
    *
     */
    private void initModels() {
        /*
        *
        *Columnas de la tabla CDA Reporte ECCE
        *
         */
        modeloCDA = new DefaultTableModel();
        modeloCDA.addColumn("Nº CDA");
        modeloCDA.addColumn("Razon Social");
        modeloCDA.addColumn("Nombre CDA");
        modeloCDA.addColumn("Nit CDA");
        modeloCDA.addColumn("Persona de Contacto");
        modeloCDA.addColumn("Dirrecion CDA");
        modeloCDA.addColumn("Correo Electronico");
        modeloCDA.addColumn("Telefono  CDA");
        modeloCDA.addColumn("Ciudad CDA");
        modeloCDA.addColumn("No. Resolucion de Certificacion Autoridad Ambiental");
        modeloCDA.addColumn("Fecha Resolucion");
        modeloCDA.addColumn("Clase del CDA");
        modeloCDA.addColumn("No. Expediente de la Autoridad Ambiental");
        modeloCDA.addColumn("Numero Certificado RTM");
        modeloCDA.addColumn("Total equipos Analizadores Motos");

        /*
        *
        *Columnas de la tabla Datos de Equipo
        *
         */
        modeloEquipos = new DefaultTableModel();
        modeloEquipos.addColumn("Valor Pef");
        modeloEquipos.addColumn("Nº Serie del Banco");
        modeloEquipos.addColumn("Nº Serie Analizador");
        modeloEquipos.addColumn("Marca Analizador");
        modeloEquipos.addColumn("Vr Span Bajo HC");
        modeloEquipos.addColumn("Resultado de Vr Span Bajo HC");
        modeloEquipos.addColumn("Vr Span Bajo CO");
        modeloEquipos.addColumn("Resultado de Vr Span Bajo CO");
        modeloEquipos.addColumn("Vr Span Bajo CO2");
        modeloEquipos.addColumn("Resultado de Vr Span Bajo CO2");
        modeloEquipos.addColumn("Vr Span Alto HC");
        modeloEquipos.addColumn("Resultado de Vr Span Alto HC");
        modeloEquipos.addColumn("Vr Span Alto CO");
        modeloEquipos.addColumn("Resultado de Vr Span Alto CO");
        modeloEquipos.addColumn("Vr Span Alto CO2");
        modeloEquipos.addColumn("Resultado de Vr Span Alto CO2");
        modeloEquipos.addColumn("Fecha y Hora ultima Verificacion");
        modeloEquipos.addColumn("Nombre Programa ");
        modeloEquipos.addColumn("Marca del Software");
        modeloEquipos.addColumn("Version Programa");
        /*
         *
         *  Columnas de Datos de la Prueba
         *
         */
        modeloDatosPrueba = new DefaultTableModel();
        modeloDatosPrueba.addColumn("Nº Consecutiva Prueba");
        modeloDatosPrueba.addColumn("Fecha y Hora Inicio Prueba");
        modeloDatosPrueba.addColumn("Fecha y Hora Final Prueba");
        modeloDatosPrueba.addColumn("Operador Realiza Prueba");

        /*
        *
        * Columnas de la Tabla del Propietario del Vehiculo
        *
         */
        modeloPropietario = new DefaultTableModel();
        modeloPropietario.addColumn("Nombre Completo del Propietario");
        modeloPropietario.addColumn("Tipo de Documento");
        modeloPropietario.addColumn("Nº Documento");
        modeloPropietario.addColumn("Direccion");
        modeloPropietario.addColumn("Telefono Fijo");
        modeloPropietario.addColumn("Telefono Celular");
        modeloPropietario.addColumn("Ciudad");
        /*
        *
        * Columnas de Las Tablas del Vehiculo
        *
         */
        modeloVehiculo = new DefaultTableModel();
        modeloVehiculo.addColumn("Placa");
        modeloVehiculo.addColumn("Marca");
        modeloVehiculo.addColumn("Año modelo");
        modeloVehiculo.addColumn("Cilindraje");
        modeloVehiculo.addColumn("Línea");
        modeloVehiculo.addColumn("Diseño");
        modeloVehiculo.addColumn("Clase");
        modeloVehiculo.addColumn("Servicio");
        modeloVehiculo.addColumn("Combustible");
        modeloVehiculo.addColumn("Tipo de Motor");
        modeloVehiculo.addColumn("Número de tubos de escape");
        modeloVehiculo.addColumn("No motor");
        modeloVehiculo.addColumn("No vin / serie");
        modeloVehiculo.addColumn("No licencia transito");
        modeloVehiculo.addColumn("kilometraje (km)");
        /*
            *
            *
            * Columnas  de la tabla de Resultados Prueba 
            *
         */
        modeloRelPruebas = new DefaultTableModel();
        modeloRelPruebas.addColumn("Temperatura ambiente (°C)");
        modeloRelPruebas.addColumn("Humedad Relativa (%)");
        modeloRelPruebas.addColumn("Temperatura de motor (°C)");
        modeloRelPruebas.addColumn("RPM Ralentí");
        modeloRelPruebas.addColumn("HC (ppm) Ralentí");
        modeloRelPruebas.addColumn("CO (%) Ralentí");
        modeloRelPruebas.addColumn("CO2(%) Ralentí");
        modeloRelPruebas.addColumn("O2 (%) Ralentí");
        modeloRelPruebas.addColumn("Nivel Emisiones (norma aplicable)");
        modeloRelPruebas.addColumn("Fugas en el tubo de escape ");
        modeloRelPruebas.addColumn("Fugas en el silenciador ");
        modeloRelPruebas.addColumn("Ausencia tapa combustible o fugas ");
        modeloRelPruebas.addColumn("Ausencia tapa aceite o de fugas de aceite ");
        modeloRelPruebas.addColumn("Accesorios o deformaciones en el tubo de escape que no permitan la instalación sistema de muestreo");
        modeloRelPruebas.addColumn("Salidas adicionales al diseño");
        modeloRelPruebas.addColumn("Revoluciones fuera de rango ");
        modeloRelPruebas.addColumn("Presencia humo negro, azul");
        modeloRelPruebas.addColumn("Fecha y hora aborto de prueba");
        modeloRelPruebas.addColumn("Falla del equipo de medición ");
        modeloRelPruebas.addColumn("Falla súbita fluido eléctrico ");
        modeloRelPruebas.addColumn("Bloqueo forzado del equipo de medición ");
        modeloRelPruebas.addColumn("Ejecución incorrecta prueba ");
        modeloRelPruebas.addColumn("Causas de rechazo");
        modeloRelPruebas.addColumn("Concepto Tecnico");
        modeloRelPruebas.addColumn("Resolución de límites máximos permisibles");
    }

    /*
    *
    * Metodo fillData() los parametros del CDA ingresa las Fechas
    * con eso genera un reporte en java7 Swing
    *
     */
    private void fillData(Date fechaInicial, Date fechaFinal) {
        //Agregamos las tablas 
        initModels();
        //Formatos de las fechas
        formatoFechas = new SimpleDateFormat("yyyy-MM-dd");
        formatoFechaExt = new SimpleDateFormat("YYYY/MM/dd HH:mm");
        //Recojemos los datos de las hoja de pruebas
        HojaPruebasJpaController hpjc = new HojaPruebasJpaController();
        List<Pruebas> lstPruebas = hpjc.findByDatePruebas(fechaInicial, fechaFinal);
        //Tamaño de datos 
        int lng = lstPruebas.size();
        System.out.println("Total datos recogidos: " + lng);
        //Mensaje Dialog que no se encontro Datos en la Base de Datos
        if (lstPruebas == null || lstPruebas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La consulta no genero datos");
            return;
        }

        int n = 0;
        String form;
        int work = 0;
        this.tblCda.setModel(modeloCDA);
        this.tblCalibracion.setModel(modeloEquipos);
        this.tblPruebas.setModel(modeloDatosPrueba);
        this.tblPropietarios.setModel(modeloPropietario);
        this.tblVehiculos.setModel(modeloVehiculo);
        this.tblResultadoPrueba.setModel(modeloRelPruebas);
        tblCda.setEnabled(false);
        tblCalibracion.setEnabled(false);
        tblPruebas.setEnabled(false);
        tblPropietarios.setEnabled(false);
        tblVehiculos.setEnabled(false);
        tblResultadoPrueba.setEnabled(false);
        for (Pruebas pruebas : lstPruebas) {
            if (pruebas.getTipoPrueba().getTesttype() == 8) {// solo evalua las pruebas de gases que son las que necesitamos 

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
                    System.out.println("id pruebas" + pruebas.getIdPruebas());
                    System.out.println("placa" + pruebas.getHojaPruebas().getVehiculos().getCarplate());
                    System.out.println("pru: !  " + work + " number of test : " + pruebas.getIdPruebas() + " carplate: " + pruebas.getHojaPruebas().getVehiculos().getCarplate());
                    if (pruebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype() == 4 || pruebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype() == 1 || pruebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype() == 10) {
                        /*
                    *
                    *llamamos los metodos de cada tabla del cda vehiculo etc
                    *
                         */
                        cargarDatosCDA(pruebas);
                        cargarDatosEquipos(pruebas, fechaInicial, fechaFinal);
                        cargarDatosPruebas(pruebas, form);
                        cargarDatosPropietario(pruebas, form);
                        cargarDatosVehiculo(pruebas, form);
                        cargarResultadosPrueba(pruebas);

                        /*
                    *
                    * Cargamos todas las tablas a la interfaz
                    *
                         */
                    } else {
                        //Cuando no es 4 y el 8
                        //JOptionPane.showMessageDialog(this, "esto es Diesel");
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
        jScrollPane7 = new javax.swing.JScrollPane();
        jScrollPane8 = new javax.swing.JScrollPane();
        jScrollPane10 = new javax.swing.JScrollPane();
        jScrollPane11 = new javax.swing.JScrollPane();
        jScrollPane12 = new javax.swing.JScrollPane();
        // GUI las tablas
        tblCda = new javax.swing.JTable();
        tblCalibracion = new javax.swing.JTable();
        tblPruebas = new javax.swing.JTable();
        tblPropietarios = new javax.swing.JTable();
        tblVehiculos = new javax.swing.JTable();
        tblResultadoPrueba = new javax.swing.JTable();

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
        tblCda.setName("Información del Cda.");
        jScrollPane11.setViewportView(tblCda);
        infPrueba.addTab("Inf. Cda", jScrollPane11);

        //Tabla del modeloCalibracion
        tblCalibracion.setModel(modeloEquipos);
        tblCalibracion.setName("Información de Calibracion");
        jScrollPane12.setViewportView(tblCalibracion);
        infPrueba.addTab("Inf. Calibracion", jScrollPane12);

        //Tabla del modeloPruebas
        tblPruebas.setModel(modeloDatosPrueba);
        tblPruebas.setName("Info. Pruebas");
        jScrollPane7.setViewportView(tblPruebas);
        infPrueba.addTab("Inf. Pruebas", jScrollPane7);

        //Tabla de modeloPropietarios
        tblPropietarios.setModel(modeloPropietario);
        tblPropietarios.setName("Informacion Propietario");
        jScrollPane8.setViewportView(tblPropietarios);
        infPrueba.addTab("Inf. Propietarios", jScrollPane8);

        //Tabla de Modelo de informacion Vehiculo		
        tblVehiculos.setModel(modeloVehiculo);
        tblVehiculos.setName("Información de Vehiculo");
        jScrollPane1.setViewportView(tblVehiculos);
        infPrueba.addTab("Inf. Vehiculo", jScrollPane1);

        //Tabla deModelo Medidas
        tblResultadoPrueba.setModel(modeloRelPruebas);
        tblResultadoPrueba.setName("Resultados Pruebas ");
        jScrollPane10.setViewportView(tblResultadoPrueba);
        infPrueba.addTab("Resultados Pruebas", jScrollPane10);

        /*
*
*   Agregar los componetes de  nombres fechas y btn de generar al layout sea visible    
 *  
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
        ReporteBogota rsv = new ReporteBogota();
        rsv.setVisible(true);
    }

    /*
    *
    *
    * Evento de Boton de Generar ingresando la fecha de inicio y fin
    *
     */
    private void btnGenerarActionPerformed(java.awt.event.ActionEvent evt) {
        //Si no ingreso alguna de las dos fechas mostrar mensaje
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
    }//Fin del Boton Generar               

    /*
    *
    *
    * Evento de Boton de Exportar la informacion a Excel 
    *
     */
    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {
        //ArrayLsit es una matriz de todas las columnas que va a recoger
        List<JTable> tables = new ArrayList<>();
        //Agregar a excel todas las tablas 
        tables.add(tblCda);
        tables.add(tblCalibracion);
        tables.add(tblPruebas);
        tables.add(tblPropietarios);
        tables.add(tblVehiculos);
        tables.add(tblResultadoPrueba);
        GenericExportExcel excel = new GenericExportExcel();
        excel.exportSameSheet(tables);
    }//Fin de btn Exportar en Excel

    private void cargarDatosCDA(Pruebas pruebas) {
        formatoFechas = new SimpleDateFormat("yyyy-MM-dd");
        CdaJpaController cda = new CdaJpaController();
        Cda mcda = cda.findCda(1);
        Object[] columnCDA = new Object[20];
        columnCDA[0] = mcda.getCodigo();
        columnCDA[1] = mcda.getNombre();
        columnCDA[2] = mcda.getNombre();
        columnCDA[3] = mcda.getNit();
        EquiposJpaController eq = new EquiposJpaController();
        String ususario = eq.buscarUsuario(mcda.getIdUsuarioResponsable());
        System.out.println("usuario: " + ususario);
        columnCDA[4] = ususario;
        columnCDA[5] = mcda.getDireccion();
        columnCDA[6] = mcda.getCorreo();
        columnCDA[7] = mcda.getTelefono();
        columnCDA[8] = mcda.getCiudad();
        columnCDA[9] = mcda.getResolucion();
        if (mcda.getFechaResolucion() != null) {
            Calendar fechaResolucion = Calendar.getInstance();
            DateFormat formatoFech = new SimpleDateFormat("yyyy-MM-dd");
            fechaResolucion.setTime(mcda.getFechaResolucion());
            columnCDA[10] = formatoFech.format(mcda.getFechaResolucion());
        } else {
            columnCDA[10] = "N/A";
        }
        columnCDA[11] = mcda.getClase();
        columnCDA[12] = mcda.getNro_Expediente_Autoridad_Ambiental();
        if (pruebas.getFinalizada().equalsIgnoreCase("Y") && pruebas.getAprobada().equalsIgnoreCase("Y")) {
            CertificadosJpaController cert = new CertificadosJpaController();
            Certificados certificado = cert.findCertificadoHojaPrueba(pruebas.getHojaPruebas().getTestsheet());
            columnCDA[13] = certificado.getConsecutive();
        } else {
            columnCDA[13] = "0";
        }
        int total_eq = mcda.getTotal_Eq_2T() + mcda.getTotal_Eq_4T();
        columnCDA[14] = total_eq;
        modeloCDA.addRow(columnCDA);
    }

    private void cargarDatosEquipos(Pruebas pruebas, Date fechaInicial, Date fechaFinal) {
        SoftwareJpaController swJpa = new SoftwareJpaController();
        Software sw = swJpa.findSoftware(1);
        EntityManager em = getEntityManager();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            String idEquipo = pruebas.generarIdEquipo(pruebas.getSerialEquipo());
            System.out.println("Id Equipo: " + idEquipo);

            Query q = em.createNativeQuery("SELECT \n"
                    + "  eq.pef,\n"
                    + "  eq.serial,\n"
                    + "  eq.serialresolucion,\n"
                    + "  eq.marca,\n"
                    + "  cl.bm_hc,\n"
                    + "  cl.banco_bm_hc,\n"
                    + "  cl.bm_co,\n"
                    + "  cl.banco_bm_co,\n"
                    + "  cl.bm_co2,\n"
                    + "  cl.banco_bm_co2,\n"
                    + "  cl.alta_hc,\n"
                    + "  cl.banco_alta_hc,\n"
                    + "  cl.alta_co,\n"
                    + "  cl.banco_alta_co,\n"
                    + "  cl.alta_co2,\n"
                    + "  cl.banco_alta_co2,\n"
                    + "  cal.CURDATE\n"
                    + "FROM \n"
                    + "  calibracion_dos_puntos cl \n"
                    + "  INNER JOIN calibraciones cal ON cl.CALIBRATION = cal.CALIBRATION\n"
                    + "  INNER JOIN equipos eq ON eq.id_equipo = cal.id_equipo\n"
                    + "WHERE \n"
                    + "  eq.id_equipo_calibracion = ? \n"
                    + "  AND cal.CURDATE > ?  AND cal.aprobada = 1\n"
                    + "ORDER BY cal.CURDATE ASC LIMIT 1;");

            q.setParameter(1, idEquipo);
            q.setParameter(2, pruebas.getFechaPrueba(), TemporalType.DATE);

            List<Object[]> results = q.getResultList();
            Object[] columnCalibracion = new Object[20];
            for (Object[] result : results) {
                columnCalibracion[0] = result[0];   // eq.pef
                columnCalibracion[1] = result[1];   // eq.serial
                columnCalibracion[2] = result[2];   // eq.serialresolucion
                columnCalibracion[3] = result[3];   // eq.marca
                columnCalibracion[4] = result[4];   // cl.bm_hc
                columnCalibracion[5] = result[5];   // cl.banco_bm_hc
                columnCalibracion[6] = result[6];   // cl.bm_co
                columnCalibracion[7] = result[7];   // cl.banco_bm_co
                columnCalibracion[8] = result[8];   // cl.bm_co2
                columnCalibracion[9] = result[9];   // cl.banco_bm_co2
                columnCalibracion[10] = result[10]; // cl.alta_hc
                columnCalibracion[11] = result[11]; // cl.banco_alta_hc
                columnCalibracion[12] = result[12]; // cl.alta_co
                columnCalibracion[13] = result[13]; // cl.banco_alta_co
                columnCalibracion[14] = result[14]; // cl.alta_co2
                columnCalibracion[15] = result[15]; // cl.banco_alta_co2
                columnCalibracion[16] = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(result[16]); // cal.CURDATE
                columnCalibracion[17] = "Soltelec S.A.S";
                columnCalibracion[18] = sw.getNombre();
                columnCalibracion[19] = sw.getVersion();
            }

            modeloEquipos.addRow(columnCalibracion);
            columnCalibracion = null;

        } catch (Exception e) {
            LOG.severe("" + e.getMessage());
        }

    }//Cierre de Cargar Datos del Equipo

    private void cargarDatosPruebas(Pruebas pruebas, String strIntent) {
        //Dar Formato a las Fechas
        formatoFechaExt = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
        //Condicional que es solo para gases  
        if (pruebas.getTipoPrueba().getTesttype() == 8 && !pruebas.getFinalizada().equalsIgnoreCase("A")) {
            //Cuantas columnas tiene la tabla de Pruebas 
            Object[] ColumnaPruebas = new Object[8];
            //Identificacion consecutiva Prueba
            ColumnaPruebas[0] = pruebas.getIdPruebas();
            //objPrueba[0] = String.valueOf(pruebas.getIdPruebas());
            //Fecha y hora Inicio Prueba
            ColumnaPruebas[1] = formatoFechaExt.format(pruebas.getFechaPrueba());
            //Fecha y hora Final Prueba 
            ColumnaPruebas[2] = formatoFechaExt.format(pruebas.getFechaFinal());
            //Fecha y hora Aborto 
            //cedula operario
            ColumnaPruebas[3] = pruebas.getUsuarios().getCedula();
            //Las medidas la temperatura etc 

//            for (Medidas medidas : pruebas.getMedidasList()) {
//                switch (medidas.getTiposMedida().getMeasuretype()) {
//                    case 8031: // Temperatura  Ambiente                      
//                        ColumnaPruebas[4] = medidas.getValormedida();
//                        break;
//                    case 8032://Humedad Ambiental  
//                        ColumnaPruebas[5] = medidas.getValormedida();
//                        break;
//                }
//            }
//            if (pruebas.getAbortada().equalsIgnoreCase("Y")) {//Si es abortada 3
//                ColumnaPruebas[7] = "1";
//
//            }
//            //Causa del Aborto
//            ColumnaPruebas[7] = pruebas.getIdTipoAborto();
//            //Ingresar Datos a las columnas
            modeloDatosPrueba.addRow(ColumnaPruebas);
        }

    }

    private void cargarDatosPropietario(Pruebas pruebas, String strIntent) {
        Object[] columnDatosPropietario = new Object[8];
        //Nombre o Razon Social Propietario
        columnDatosPropietario[0] = pruebas.getHojaPruebas().getPropietarios().getNombres() + " " + pruebas.getHojaPruebas().getPropietarios().getApellidos();
        // Tipo de Documento
        columnDatosPropietario[1] = pruebas.getHojaPruebas().getPropietarios().getTipoIdentificacion();
        //Nº Documento
        columnDatosPropietario[2] = pruebas.getHojaPruebas().getPropietarios().getCarowner();
        //Dirrecion del Propietario
        columnDatosPropietario[3] = pruebas.getHojaPruebas().getPropietarios().getDireccion();
        //Telefono fijo del propietario
        columnDatosPropietario[4] = pruebas.getHojaPruebas().getPropietarios().getNumerotelefono();
        //Telefono Celular del porpietario
        columnDatosPropietario[5] = pruebas.getHojaPruebas().getPropietarios().getCelular();
        //Ciudad del Propietario
        columnDatosPropietario[6] = pruebas.getHojaPruebas().getPropietarios().getCiudadades().getCodigo();

        modeloPropietario.addRow(columnDatosPropietario);
    }

    private void cargarDatosVehiculo(Pruebas pruebas, String strIntento) {

        Object[] columnaVehiculo = new Object[16];
        columnaVehiculo[0] = pruebas.getHojaPruebas().getVehiculos().getCarplate();
        columnaVehiculo[1] = pruebas.getHojaPruebas().getVehiculos().getMarcas().getCarmark();
        columnaVehiculo[2] = pruebas.getHojaPruebas().getVehiculos().getModelo();
        columnaVehiculo[3] = pruebas.getHojaPruebas().getVehiculos().getCilindraje();
        columnaVehiculo[4] = pruebas.getHojaPruebas().getVehiculos().getLineasVehiculos().getCarline();
        if (pruebas.getHojaPruebas().getVehiculos().getDiseno() == Diseno.Convencional) {
            columnaVehiculo[5] = "1";
        } else {
            columnaVehiculo[5] = "2";
        }
        columnaVehiculo[6] = pruebas.getHojaPruebas().getVehiculos().getClasesVehiculo().getClass1();
        columnaVehiculo[7] = pruebas.getHojaPruebas().getVehiculos().getServicios().getService();
        columnaVehiculo[8] = pruebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype();
        columnaVehiculo[9] = pruebas.getHojaPruebas().getVehiculos().getTiemposmotor() + "T";
        columnaVehiculo[10] = pruebas.getHojaPruebas().getVehiculos().getNumeroexostos();
        columnaVehiculo[11] = pruebas.getHojaPruebas().getVehiculos().getNumeromotor();
        columnaVehiculo[12] = pruebas.getHojaPruebas().getVehiculos().getVin();
        columnaVehiculo[13] = pruebas.getHojaPruebas().getVehiculos().getNumerolicencia();
        columnaVehiculo[14] = pruebas.getHojaPruebas().getVehiculos().getKilometraje();
        modeloVehiculo.addRow(columnaVehiculo);
    }

    private void cargarResultadosPrueba(Pruebas pruebas) {
        formatoFechaExt = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
        Object[] columnResultPruebas = new Object[50];
        String codigosDefectos = "";
        //Si el carro es 8 que es Gases
        //if (!pruebas.getAbortada().equalsIgnoreCase("A")) {


            for (Medidas medidas : pruebas.getMedidasList()) {

                switch (medidas.getTiposMedida().getMeasuretype()) {
                    case 8031: // Temperatura  Ambiente                      
                        columnResultPruebas[0] = medidas.getValormedida();
                        break;
                    case 8032://Humedad Ambiental  
                        columnResultPruebas[1] = medidas.getValormedida();
                        break;
                    case 8006:
                        columnResultPruebas[2] = medidas.getValormedida();      //Temperatura Ralenty
                        break;
                    case 8020:
                        columnResultPruebas[5] = medidas.getValormedida();      //Monoxido de Carbono 2T Ralenty (COR2T)
                        break;
                    case 8003:
                        columnResultPruebas[6] = medidas.getValormedida();      //Dioxido de Carbono Ralenty (CO2R)
                        break;
                    case 8019:
                        columnResultPruebas[6] = medidas.getValormedida();      //Dioxido de Carbono 2 Tiempos Ralenty (CO2R2T
                        break;
                    case 8001:
                        columnResultPruebas[4] = medidas.getValormedida();      //HidroCarburos Ralenty (HC)
                        break;
                    case 8018:
                        columnResultPruebas[4] = medidas.getValormedida();      // HidroCarburos vehiculo 2T Ralenty (HC)
                        break;
                    case 8004:
                        columnResultPruebas[7] = medidas.getValormedida();      //Oxigeno Ralenty (O2)
                        break;
                    case 8021:
                        columnResultPruebas[7] = medidas.getValormedida();      // Oxigeno Ralenty 2 Tiempos (O2R2T)
                        break;
                    case 8002:
                        columnResultPruebas[5] = medidas.getValormedida();      ///  Monoxido de Carbono Ralenty (COR)
                        break;
                    case 8022:
                        columnResultPruebas[2] = medidas.getValormedida();      // Temperatura Ralenty 2 Tiempos 
                        break;
                    case 8005:
                        columnResultPruebas[3] = medidas.getValormedida();      //Revoluciones por minuto Ralenty 
                        break;
                    case 8028:
                        columnResultPruebas[3] = medidas.getValormedida();      //Revoluciones por minuto 2 Tiempos Ralenty 
                        break;

                }

            }

            //Si el comentario aborto no esta null ejecutar esta condicional   
            if (pruebas.getComentarioAborto() != null && !pruebas.getComentarioAborto().isEmpty()) {
                //presencia humo negro
                System.out.println("------------------Comentario---------------------- " + pruebas.getComentarioAborto());
                if (pruebas.getComentarioAborto().contains("Presencia Humo") || pruebas.getComentarioAborto().contains("PRESENCIA DE HUMO")) {
                    columnResultPruebas[16] = "Si";
                    codigosDefectos += "7, ";
                }
                if (pruebas.getComentarioAborto().contains("equipo de medicion")) {
                    columnResultPruebas[18] = "Si";
                }
                if (pruebas.getComentarioAborto().contains("fluido electrico") || pruebas.getComentarioAborto().contains("falla subita")) {
                    columnResultPruebas[19] = "Si";
                }
                if (pruebas.getComentarioAborto().contains("Bloqueo forzado") || pruebas.getComentarioAborto().contains("bloqueo forzado")) {
                    columnResultPruebas[20] = "Si";
                }
                if (pruebas.getComentarioAborto().contains("Ejecucion incorrecta") || pruebas.getComentarioAborto().contains("ejecicion incorrecta")) {
                    columnResultPruebas[21] = "Si";
                }
                if (pruebas.getHojaPruebas().getVehiculos().getCarplate().equals("YVW68D")) {
                    System.out.println("esta es la placa;");
                }
                //Si cel comentario aborto contiene datos y hay revoluciones fuera de Rango 
                if (pruebas.getComentarioAborto().equalsIgnoreCase("REVOLUCIONES FUERA RANGO") || pruebas.getComentarioAborto().equalsIgnoreCase("Revoluciones")) {
                    columnResultPruebas[15] = "Si";
                    codigosDefectos += "8, ";
                }
                if (columnResultPruebas[16] == null) {
                    columnResultPruebas[16] = "NO";

                }
                if (columnResultPruebas[15] == null) {
                    columnResultPruebas[15] = "NO";

                }
                if (columnResultPruebas[18] == null) {
                    columnResultPruebas[18] = "NO";

                }
                if (columnResultPruebas[19] == null) {
                    columnResultPruebas[19] = "NO";

                }
                if (columnResultPruebas[20] == null) {
                    columnResultPruebas[20] = "NO";

                }
                if (columnResultPruebas[21] == null) {
                    columnResultPruebas[21] = "NO";

                }
                //Si en el comentario Aborto es igual a condiciones Anormales
                
                    // si hay condiciones anormales y las observaciones no estan nullos
                    if (pruebas.getObservaciones() != null) {
                        //Concatenar detalles son las causas de rechazo

                        //si hay comentario aborto y observaciones concatenar un .-
                        String[] arrValor = pruebas.getObservaciones().split(".-");

                        if (arrValor.length > 1) {
                            String[] arrCondiciones = arrValor[1].split(";");
                            if (arrCondiciones.length > 0) {

                                for (int i = 0; i < arrCondiciones.length; i++) {
                                    if (arrCondiciones[i].contains("Existencia de fugas en el tubo")) {
                                        columnResultPruebas[9] = "SI";
                                        codigosDefectos += "1, ";
                                    }

                                    if (arrCondiciones[i].contains("equipo de medicion")) {
                                        columnResultPruebas[18] = "SI";
                                    }
                                    if (arrCondiciones[i].contains("fluido electrico") || arrCondiciones[i].contains("falla subita")) {
                                        columnResultPruebas[19] = "SI";
                                    }
                                    if (arrCondiciones[i].contains("Bloqueo forzado") || arrCondiciones[i].contains("bloqueo forzado")) {
                                        columnResultPruebas[20] = "SI";
                                    }
                                    if (arrCondiciones[i].contains("Ejecucion incorrecta") || arrCondiciones[i].contains("ejecucion incorrecta")) {
                                        columnResultPruebas[21] = "SI";
                                    }

                                    // Fugas Silenciador
                                    if (arrCondiciones[i].contains("Existencia de fugas en el tubo, uniones del múltiple y silenciador")
                                            || arrCondiciones[i].contains(" Existencia de fugas en el tubo, uniones del múltiple y silenciador")) {
                                        columnResultPruebas[10] = "SI";
                                        codigosDefectos += "2, ";
                                    }
                                    if (arrCondiciones[i].contains("Revoluciones fuera de rango")
                                            || arrCondiciones[i].contains("REVOLUCIONES FUERA RANGO")) {
                                        columnResultPruebas[15] = "Si";
                                        codigosDefectos += "8, ";
                                    }

                                    // Accesorios o Deformaciones
                                    if (arrCondiciones[i].contains("Instalación de accesorios o deformaciones")
                                            || arrCondiciones[i].contains(" Instalación de accesorios o deformaciones")) {
                                        columnResultPruebas[13] = "SI";
                                        codigosDefectos += "3, ";
                                    }

                                    // Ausencia de tapas o tapones de combustible
                                    if (arrCondiciones[i].contains("Ausencia de tapas o tapones de combustible")
                                            || arrCondiciones[i].contains(" Ausencia de tapas o tapones de combustible")) {
                                        columnResultPruebas[11] = "SI";
                                        codigosDefectos += "4, ";
                                    }

                                    // Ausencia de tapones de aceite
                                    if (arrCondiciones[i].contains("Ausencia de tapones de aceite")
                                            || arrCondiciones[i].contains(" Ausencia de tapones de aceite")) {
                                        columnResultPruebas[12] = "SI";
                                        codigosDefectos += "3, ";
                                    }

                                    // Salidas adicionales en el sistema de escape
                                    if (arrCondiciones[i].contains("Salidas adicionales en el sistema de escape")
                                            || arrCondiciones[i].contains(" Salidas adicionales en el sistema de escape")) {
                                        columnResultPruebas[14] = "SI";
                                        codigosDefectos += "5, ";
                                    }

                                }
                                if (columnResultPruebas[9] == null) {
                                    columnResultPruebas[9] = "NO";
                                }
                                if (columnResultPruebas[10] == null) {
                                    columnResultPruebas[10] = "NO";
                                }
                                if (columnResultPruebas[11] == null) {
                                    columnResultPruebas[11] = "NO";
                                }
                                if (columnResultPruebas[12] == null) {
                                    columnResultPruebas[12] = "NO";
                                }
                                if (columnResultPruebas[13] == null) {
                                    columnResultPruebas[13] = "NO";
                                }
                                if (columnResultPruebas[14] == null) {
                                    columnResultPruebas[14] = "NO";
                                }
                                if (columnResultPruebas[18] == null) {
                                    columnResultPruebas[18] = "NO";
                                }
                                if (columnResultPruebas[19] == null) {
                                    columnResultPruebas[19] = "NO";
                                }
                                if (columnResultPruebas[20] == null) {
                                    columnResultPruebas[20] = "NO";
                                }
                                if (columnResultPruebas[21] == null) {
                                    columnResultPruebas[21] = "NO";
                                }

                            }
                        }

                    } else {
                        columnResultPruebas[9] = "NO";
                        columnResultPruebas[10] = "NO";
                        columnResultPruebas[11] = "NO";
                        columnResultPruebas[12] = "NO";
                        columnResultPruebas[13] = "NO";
                        columnResultPruebas[14] = "NO";
                        columnResultPruebas[18] = "NO";
                        columnResultPruebas[19] = "NO";
                        columnResultPruebas[20] = "NO";
                        columnResultPruebas[21] = "NO";
                    }//si hay coment condiciones Anormales
                } else {

                }//si exitenCondiciones
            
            if (pruebas.getAprobada().equalsIgnoreCase("Y")) {//Si las pruebas  es aprobada
                columnResultPruebas[23] = "1";
            } else {//Si no es aprobada 2
                columnResultPruebas[23] = "2";
            }
            if (pruebas.getAbortada().equalsIgnoreCase("A")) {//Si es abortada 3
                columnResultPruebas[23] = "3";
                columnResultPruebas[17] = formatoFechaExt.format(pruebas.getFechaPrueba());

            }
            // Incumplimiento de niveles
            //if (pruebas.getAprobada().equalsIgnoreCase("N") && pruebas.getFinalizada().equalsIgnoreCase("N")) {
            if (columnResultPruebas[23].equals("2")) {
                columnResultPruebas[8] = "SI";
            } else {
                columnResultPruebas[8] = "NO";
            }
            if (columnResultPruebas[8] == "SI") {
                if (pruebas != null && pruebas.getHojaPruebas() != null && pruebas.getHojaPruebas().getVehiculos() != null && columnResultPruebas[4] != null && columnResultPruebas[5] != null) {
                    if (pruebas.getHojaPruebas().getVehiculos().getTiemposmotor() == 4) {
                        if (Float.parseFloat(columnResultPruebas[4].toString()) >= 1600) {
                            codigosDefectos += "18, ";
                        }
                        if (Float.parseFloat(columnResultPruebas[5].toString()) >= 3.6) {
                            codigosDefectos += "17, ";
                        }
                    } else {
                        if (pruebas.getHojaPruebas().getVehiculos().getModelo() <= 2009) {
                            if (Float.parseFloat(columnResultPruebas[4].toString()) >= 10000) {
                                codigosDefectos += "13, ";
                            }
                            if (Float.parseFloat(columnResultPruebas[5].toString()) >= 4.5) {
                                codigosDefectos += "14, ";
                            }
                        } else {
                            if (Float.parseFloat(columnResultPruebas[4].toString()) >= 2000) {
                                codigosDefectos += "15, ";
                            }
                            if (Float.parseFloat(columnResultPruebas[5].toString()) >= 4.5) {
                                codigosDefectos += "16, ";
                            }
                        }
                    }
                } else {
                    // Manejar el caso en el que los objetos sean nulos
                    // Puedes agregar código aquí para manejar la situación de objetos nulos
                }
                columnResultPruebas[22] = codigosDefectos.trim();

            }
            columnResultPruebas[24] = "Resolucion 0762 de 2022";

            modeloRelPruebas.addRow(columnResultPruebas);
//            if (!tieneRegistro) {
//                modeloRelPruebas.addRow(columnResultPruebas);
//            }

            if (pruebas.getAbortada().equalsIgnoreCase("A")) {
                columnResultPruebas[17] = formatoFechaExt.format(pruebas.getFechaPrueba());
            }
        //}

    }//Metodo 
}
