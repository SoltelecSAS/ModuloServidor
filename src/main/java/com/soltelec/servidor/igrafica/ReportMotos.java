/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.igrafica;

import com.soltelec.servidor.dao.CdaJpaController;
import com.soltelec.servidor.dao.PruebasJpaController;
import com.soltelec.servidor.model.Cda;
import com.soltelec.servidor.model.Defxprueba;
import com.soltelec.servidor.model.Diseno;
import com.soltelec.servidor.model.HojaPruebas;
import com.soltelec.servidor.model.Medidas;
import com.soltelec.servidor.model.Propietarios;
import com.soltelec.servidor.model.Pruebas;
import com.soltelec.servidor.model.Vehiculos;
import com.soltelec.servidor.utils.CargarCalibracionReporte;
import com.soltelec.servidor.utils.GenericExportExcel;
import com.soltelec.servidor.utils.JTitlePanel;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Administrador
 */
public class ReportMotos extends javax.swing.JInternalFrame {

    JFileChooser fc;
    private Object calendarPanel;
    JPanel componentPanel;
    JTitlePanel componentTitlePanel;
    private DefaultTableModel modelCDA;
    private DefaultTableModel modelEquipo;
    private DefaultTableModel modelPrueba;
    private DefaultTableModel modelPropietario;
    private DefaultTableModel modelVehiculo;
    private DefaultTableModel modelResultados;
    private Cda results;
    private SimpleDateFormat sdf;
    private static final Logger LOG = Logger.getLogger(ReportTabla6Equipos.class.getName());

    /**
     * Creates new form ReportMotos
     */
    public ReportMotos() {
        super("Reporte Motos",
                true, //resizable
                true, //closable
                false, //maximizable
                true);
        generarModelo();
        initComponents();
    }

    private void generarModelo() {
        CdaJpaController cdaJpa = new CdaJpaController();
        modelCDA = new DefaultTableModel();
        results = cdaJpa.findCda(1);
        sdf = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        modelCDA = new DefaultTableModel();
        modelCDA.addColumn("No. CDA");
        modelCDA.addColumn("Nombre CDA");
        modelCDA.addColumn("NIT CDA");
        modelCDA.addColumn("Dirección CDA");
        modelCDA.addColumn("Telefono 1  CDA");
        modelCDA.addColumn("Telefono 2 (celular)");
        modelCDA.addColumn("Ciudad CDA");
        modelCDA.addColumn("No Resolución CDA");
        modelCDA.addColumn("Fecha Resolución CDA");

        //jTable2.setModel(modelCDA);
        //jScrollPane2.setViewportView(jTable2);
        //progreso.setValue(1);
//      ******MODELO EQUIPO******          
        modelEquipo = new DefaultTableModel();

        modelEquipo.addColumn("Vr PEF");
        modelEquipo.addColumn("Número de serie del banco");
        modelEquipo.addColumn("No. serie  analizador");
        modelEquipo.addColumn("Marca analizador");
        modelEquipo.addColumn("Valor gas referencia bajo  HC verificaciòn y ajuste ");
        modelEquipo.addColumn("Valor leido bajo  HC verificaciòn y ajuste ");
        modelEquipo.addColumn("Valor gas referencia bajo CO verificaciòn y ajuste");
        modelEquipo.addColumn("Valor leido bajo CO verificaciòn y ajuste");
        modelEquipo.addColumn("Valor gas referencia bajo CO2 verificaciòn y ajuste");
        modelEquipo.addColumn("Valor leido bajo CO2 verificaciòn y ajuste");
        modelEquipo.addColumn("Valor gas referencia alto HC verificaciòn y ajuste");
        modelEquipo.addColumn("Valor leido alto HC verificaciòn y ajuste");
        modelEquipo.addColumn("Valor gas referencia  alto CO verificaciòn y ajuste");
        modelEquipo.addColumn("Valor leido  alto CO verificaciòn y ajuste");
        modelEquipo.addColumn("Valor gas referencia  alto CO2 verificaciòn y ajuste");
        modelEquipo.addColumn("Valor leido alto CO2 verificaciòn y ajuste");
        modelEquipo.addColumn("Fecha  y hora ultima verificación y ajuste");
        modelEquipo.addColumn("Nombre del software de aplicación");
        modelEquipo.addColumn("Version software de aplicación");

//        ******MODELO PRUEBAS******   
        modelPrueba = new DefaultTableModel();
        modelPrueba.addColumn("No de consecutivo prueba");
        modelPrueba.addColumn("Fecha y hora inicio de la prueba");
        modelPrueba.addColumn("Fecha y hora final de la prueba");
        modelPrueba.addColumn("Fecha y hora aborto de la prueba");
        modelPrueba.addColumn("Inspector que realiza la prueba");
        modelPrueba.addColumn("Temperatura ambiente");
        modelPrueba.addColumn("Humedad Relativa");
        modelPrueba.addColumn("Causal de aborto de la prueba");

        // *****MODELO PROPIETARIO*****
        modelPropietario = new DefaultTableModel();
        modelPropietario.addColumn("Nombre o razón social propietario");
        modelPropietario.addColumn("Tipo documento");
        modelPropietario.addColumn("No. Documento de identificación");
        modelPropietario.addColumn("Direccion");
        modelPropietario.addColumn("Telefono");
        modelPropietario.addColumn("Celular");
        modelPropietario.addColumn("Ciudad");

        //***** MODELO VEHICULO *****
        modelVehiculo = new DefaultTableModel();
        modelVehiculo.addColumn("Marca");
        modelVehiculo.addColumn("Tipo de motor");
        modelVehiculo.addColumn("Línea");
        modelVehiculo.addColumn("Diseño");
        modelVehiculo.addColumn("Año modelo");
        modelVehiculo.addColumn("Placa");
        modelVehiculo.addColumn("Cilindraje");
        modelVehiculo.addColumn("Clase");
        modelVehiculo.addColumn("Servicio");
        modelVehiculo.addColumn("Combustible");
        modelVehiculo.addColumn("No Motor");
        modelVehiculo.addColumn("No VIN / Serie");
        modelVehiculo.addColumn("No licencia transito");
        modelVehiculo.addColumn("Kilometraje");

        //*****MODELO RESULTADOS*****
        modelResultados = new DefaultTableModel();
        modelResultados.addColumn("Fugas tubo escape");
        modelResultados.addColumn("Fugas silenciador");
        modelResultados.addColumn("Accesorios o deformaciones en el tubo de escape que no permitan la instalación sistema de muestreo");
        modelResultados.addColumn("Tapa combustible o fugas");
        modelResultados.addColumn("Tapa aceite o fugas");
        modelResultados.addColumn("Sistema de adimisión de aire");
        modelResultados.addColumn("Salidas adicionales diseño");
        modelResultados.addColumn("PCV (Sistema recirculaciòn de gases del carter)");
        modelResultados.addColumn("Presencia humo negro, azul (solo motos 4T)");
        modelResultados.addColumn("Revoluciones fuera de rango");
        modelResultados.addColumn("Falla sistema de refrigeración");
        modelResultados.addColumn("Temperatura de motor");
        modelResultados.addColumn("RPM ralenti");
        modelResultados.addColumn("HC ralenti");
        modelResultados.addColumn("CO ralenti");
        modelResultados.addColumn("CO2 ralenti");
        modelResultados.addColumn("O2 ralenti");
        modelResultados.addColumn("Incumplimiento de niveles de emisión");
        modelResultados.addColumn("Concepto final del vehículo");
    }

    /**
     * Metodo para llenar los datos en las tablas
     */
    private void fillData(Date fInicial, Date fFinal) {
        generarModelo();

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

        PruebasJpaController pruebasJpa = new PruebasJpaController();
        List<Pruebas> pruebas = pruebasJpa.findPruebasReporte(fInicial, fFinal);

        Object[] filaprueba = new Object[8];
        Object[] filaowner = new Object[7];
        Object[] filavehiculo = new Object[14];
        Object[] filadefectos = new Object[19];
        System.out.println("Cantidad de pruebas: " + pruebas.size());

        // Se crea un array que será una de las filas de la tabla.
        for (Pruebas pruebas1 : pruebas) {
            Object[] fila = new Object[9];

            // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.        
            fila[0] = results.getCodigo();
            fila[1] = results.getNombre();
            fila[2] = results.getNit();
            fila[3] = results.getDireccion();
            fila[4] = results.getTelefono();
            fila[5] = results.getCelular();
            fila[6] = results.getCiudad();
            fila[7] = results.getResolucion();
            String fres = sdf.format(results.getFechaResolucion());
            fila[8] = fres;

            //Se añade al modelo la fila completa.
            modelCDA.addRow(fila);

//            pruebas1.getFechaprueba()
        }

        for (Pruebas p : pruebas) {
            HojaPruebas hojaPruebas = p.getHojaPruebas();
            Propietarios propietario = new Propietarios();
            modelEquipo.addRow(CargarCalibracionReporte.setDate(p.getFechaPrueba()));
            try {
                propietario = hojaPruebas.getPropietarios();
            } catch (Exception e) {
                System.out.println(e);
                continue;
            }

            Vehiculos vehiculo = hojaPruebas.getVehiculos();
            List<Medidas> medidas = p.getMedidasList();
            List<Defxprueba> defectos = p.getDefectos();

            //Pruebas
            if (p.getTipoPrueba().getTesttype() == 8) {
                filaprueba[0] = p.getIdPruebas();               //Numero de Prueba
                filaprueba[1] = sdf.format(p.getFechaPrueba());
                if (p.getFechaFinal() == null) {
                    long ONE_MINUTE_IN_MILLIS = 60000;//millisecs
                    long t = p.getFechaPrueba().getTime();
                    Date afterAddingTenMins = new Date(t + (4 * ONE_MINUTE_IN_MILLIS));
                    filaprueba[2] = sdf.format(afterAddingTenMins);
                } else {
                    filaprueba[2] = sdf.format(p.getFechaFinal());
                }
                filaprueba[3] = p.getFechaAborto(); //Fecha de aborto de la prueba
                filaprueba[4] = p.getUsuarios().getCedula();    // Inspector que realiza la prueba
                // Humedad Relativa
                if (p.getIdTipoAborto() == null || p.getIdTipoAborto().getCodigo() == -1) {
                    filaprueba[7] = "";
                } else {
                    filaprueba[7] = p.getIdTipoAborto().getCodigo();        // Causal de aborto de la prueba  
                }

            }//end for prueba

            for (Medidas med : medidas) {

                switch (med.getTiposMedida().getMeasuretype()) {
                    case 8031:
                        filaprueba[5] = med.getValormedida();
//                        }
                        break;
                    case 8032:
                        filaprueba[6] = med.getValormedida();
                        break;
                    case 8006:                             //Temperatura de motor
                        filadefectos[11] = med.getValormedida();
                        break;
                    case 8022:                             //Temperatura de motor
                        filadefectos[11] = med.getValormedida();
                        break;
                    case 8005:                            //RPM ralenti
                        filadefectos[12] = med.getValormedida();
                        break;
                    case 8028:                            //RPM ralenti
                        filadefectos[12] = med.getValormedida();
                        break;
                    case 8001:                            //HC ralenti
                        filadefectos[13] = med.getValormedida();
                        break;
                    case 8018:                            //HC ralenti
                        filadefectos[13] = med.getValormedida();
                        break;
                    case 8002:                            //CO ralenti
                        filadefectos[14] = med.getValormedida();
                        break;
                    case 8020:                            //CO ralenti
                        filadefectos[14] = med.getValormedida();
                        break;
                    case 8003:                            //CO2 ralenti
                        filadefectos[15] = med.getValormedida();
                        break;
                    case 8019:                            //CO2 ralenti
                        filadefectos[15] = med.getValormedida();
                        break;
                    case 8004:                            //O2 ralenti
                        filadefectos[16] = med.getValormedida();
                        break;
                    case 8021:                            //O2 ralenti
                        filadefectos[16] = med.getValormedida();
                        break;
                }//end switch de los medidas
            }//end medidas

//                  ******LLENAR PROPIETARIO******  
            filaowner[0] = propietario.getNombres() + " " + propietario.getApellidos();//Nombres del propietario
            filaowner[1] = propietario.getTipoIdentificacion();                        //Tipo Identificacion
            filaowner[2] = propietario.getCarowner();                                 //Numero de identificacion
            filaowner[3] = propietario.getDireccion();                                //Direccion
            filaowner[4] = propietario.getNumerotelefono();                           //Telefono
            filaowner[5] = propietario.getCelular();                                  //Celular
            filaowner[6] = propietario.getCiudadades().getCodigo();            //Ciudad

//                  ******LLENAR VEHICULO******            
            filavehiculo[0] = vehiculo.getMarcas().getCarmark();                     //Marca del vehiculo
            filavehiculo[1] = vehiculo.getTiemposmotor() + "T";
            System.out.println("id Veh " + vehiculo.getCarplate());
//Tipo Motor
            filavehiculo[2] = vehiculo.getLineasVehiculos().getCrlcod();                //Linea
            Diseno d = vehiculo.getDiseno();
            if (vehiculo.getDiseno() == Diseno.Convencional) {
                filavehiculo[3] = "1";
            } else {
                filavehiculo[3] = "2";
            }                                  //Diseño
            filavehiculo[4] = vehiculo.getModelo();                                      //Modelo
            filavehiculo[5] = vehiculo.getCarplate();                                    //Placa
            filavehiculo[6] = vehiculo.getCilindraje();                                 //Cilindraje
            filavehiculo[7] = vehiculo.getClasesVehiculo().getClass1();                  //Clase
            filavehiculo[8] = vehiculo.getServicios().getService();                      //Servicio
            filavehiculo[9] = vehiculo.getTiposGasolina().getFueltype();                 //Tipo Combustible
            filavehiculo[10] = vehiculo.getNumeromotor();                                //Numero del motor
            filavehiculo[11] = vehiculo.getVin();                                        //Numero de VIN o chasis
            filavehiculo[12] = vehiculo.getNumerolicencia();                             //numero de licencia
            filavehiculo[13] = vehiculo.getKilometraje();                                //Kilometraje

//                  ******LLENAR DEFECTOS******
            filadefectos[0] = "NO";//Se llenaran datos en base al switch posterior
            filadefectos[1] = "NO";
            filadefectos[2] = "NO";
            filadefectos[3] = "NO";
            filadefectos[4] = "NO";
            filadefectos[5] = "NO";
            filadefectos[6] = "NO";
            filadefectos[7] = "NO";
            filadefectos[8] = "NO";
            filadefectos[9] = "NO";
            filadefectos[10] = "NO";
            filadefectos[17] = "NO";

            for (Defxprueba defxprueba : defectos) {
                switch (defxprueba.getDefxpruebaPK().getIdDefecto()) {
                    case 84002:                             //Fugas tubo escape o silenciador OBS
                        filadefectos[0] = "SI";
                        filadefectos[1] = "SI";
                        break;
                    case 84011:                            //Accesorios o deformaciones en el tubo de escape que no permitan la instalación sistema de muestreo DEF
                        filadefectos[2] = "SI";
                        break;
                    case 84000:                            //Tapa combustible o fugas OBS DEF
                        filadefectos[3] = "SI";
                        break;
                    case 84001:                            //Tapa aceite o fugas OBS DEF
                        filadefectos[4] = "SI";
                        break;
                    case 84009:                            //Sistema de adimisión de aire
                        filadefectos[5] = "SI";
                        break;
                    case 80002:
                        filadefectos[6] = "SI";            //Salidas adicionales al diseño
                        break;
                    case 84010:                            //SPCV (Sistema recirculaciòn de gases del carter)
                        filadefectos[7] = "SI";
                        break;
                    case 84004:                            //Presencia humo negro, azul (solo motos 4T)
                        filadefectos[8] = "SI";
                        break;
                    case 84007:                            //Revoluciones fuera de rango
                        filadefectos[9] = "SI";
                        break;
                    case 84003:                            //Falla sistema de refrigeración
                        filadefectos[10] = "SI";
                        break;
                    case 84018:                            //Incumplimiento de niveles de emisión
                        filadefectos[17] = "SI";
                        break;
                }//end switch      
            } //end defectos
            if (p.getObservaciones() == null || p.getObservaciones() == "") {

            } else {
                if (p.getObservaciones().contains("Fugas") || p.getObservaciones().contains("FUGAS")) {
                    filadefectos[0] = "SI";
                    filadefectos[1] = "SI";
                } else {
                    filadefectos[0] = "NO";
                    filadefectos[1] = "NO";
                }
                if (p.getObservaciones().contains("Accesorios") || p.getObservaciones().contains("ACCESORIOS")) {
                    filadefectos[2] = "SI";
                } else {
                    filadefectos[2] = "NO";
                }
                if (p.getObservaciones().contains("tapa llenado conbustible") || p.getObservaciones().contains("TAPA LLENADO COMBUSTIBLE")) {
                    filadefectos[3] = "SI";
                } else {
                    filadefectos[3] = "NO";
                }
                if (p.getObservaciones().contains("tapones") || p.getObservaciones().contains("Tapones") || p.getObservaciones().contains("TAPONES")) {
                    filadefectos[4] = "SI";
                } else {
                    filadefectos[4] = "NO";
                }
                if (p.getObservaciones().contains("Salidas") || p.getObservaciones().contains("SALIDAS")) {
                    filadefectos[6] = "SI";
                } else {
                    filadefectos[6] = "NO";
                }
                if (p.getObservaciones().contains("Presencia Humo") || p.getObservaciones().contains("PRESENCIA HUMO")|| p.getObservaciones().contains("Presencia de")|| p.getObservaciones().contains("presencia de")) {
                    filadefectos[8] = "SI";
                } else {
                    filadefectos[8] = "NO";
                }
                if (p.getObservaciones().contains("Revoluciones") || p.getObservaciones().contains("REVOCLUCIONES")|| p.getObservaciones().contains("revoluciones")) {
                    filadefectos[9] = "SI";
                } else {
                    filadefectos[9] = "NO";
                }
            }

            if (p.getAbortada().trim().equalsIgnoreCase("Y")) {
                filadefectos[18] = "3";
            } else if (p.getAprobada().trim().equalsIgnoreCase("N")) {
                filadefectos[18] = "2";
            } else if (p.getAprobada().trim().equalsIgnoreCase("Y")) {
                filadefectos[18] = "1";
            }

            //Se llenan los modelos dentro del for para que agregue cada row generado
            modelPropietario.addRow(filaowner);
            modelPrueba.addRow(filaprueba);
            modelVehiculo.addRow(filavehiculo);
            modelResultados.addRow(filadefectos);
        }

        //Se actualiza el modelo de cada tabla para que se refresque la informacion
        tblEquipo.setModel(modelEquipo);
        tblPropietarios.setModel(modelPropietario);
        tblPrueba.setModel(modelPrueba);
        tblVehiculo.setModel(modelVehiculo);
        tblResultados.setModel(modelResultados);
        tblCDA.setModel(modelCDA);

        tblCDA.setEnabled(false);
        tblEquipo.setEnabled(false);
        tblPropietarios.setEnabled(false);
        tblPrueba.setEnabled(false);
        tblVehiculo.setEnabled(false);
        tblResultados.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     *
     * @param fecha
     * @return
     */
    public Date sumarRestarHorasFecha(Date fecha) {

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(fecha); // Configuramos la fecha que se recibe

        calendar.add(Calendar.MINUTE, 3);  // numero de horas a añadir, o restar en caso de horas<0

        Date date = calendar.getTime();

        return calendar.getTime(); // Devuelve el objeto Date con las nuevas horas añadidas

    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();
        jXDatePicker1.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jXDatePicker2 = new org.jdesktop.swingx.JXDatePicker();
        jXDatePicker2.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCDA = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEquipo = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblPrueba = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblPropietarios = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblVehiculo = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblResultados = new javax.swing.JTable();
        progreso = new javax.swing.JProgressBar();

        setClosable(true);

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

        tblCDA.setModel(modelCDA);
        tblCDA.setName("Datos CDA"); // NOI18N
        jScrollPane2.setViewportView(tblCDA);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 795, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Datos CDA", jPanel1);

        tblEquipo.setModel(modelEquipo);
        tblEquipo.setName("Datos Equipo"); // NOI18N
        jScrollPane1.setViewportView(tblEquipo);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 795, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Datos Equipo", jPanel2);

        tblPrueba.setModel(modelPrueba);
        tblPrueba.setName("Datos Prueba"); // NOI18N
        jScrollPane3.setViewportView(tblPrueba);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 795, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Datos Prueba", jPanel3);

        tblPropietarios.setModel(modelPropietario);
        tblPropietarios.setName("Datos Propietarios"); // NOI18N
        jScrollPane4.setViewportView(tblPropietarios);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 795, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Datos Propietarios", jPanel4);

        tblVehiculo.setModel(modelVehiculo);
        tblVehiculo.setName("Datos Vehiculo"); // NOI18N
        jScrollPane5.setViewportView(tblVehiculo);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 795, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Datos Vehiculo", jPanel5);

        tblResultados.setModel(modelResultados);
        tblResultados.setName("Datos Resultados"); // NOI18N
        jScrollPane6.setViewportView(tblResultados);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 795, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Datos Resultados", jPanel6);

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jTabbedPane1)
            .addComponent(progreso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jXDatePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(4, 4, 4)
                .addComponent(progreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (jXDatePicker1.getDate() == null || jXDatePicker2.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Seleccione Fechas");
            return;
        }

        fillData(jXDatePicker1.getDate(), jXDatePicker2.getDate());

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        List<JTable> tables = new ArrayList<>();
        tables.add(tblCDA);
        tables.add(tblEquipo);
        tables.add(tblPrueba);
        tables.add(tblPropietarios);
        tables.add(tblVehiculo);
        tables.add(tblResultados);

        GenericExportExcel excel = new GenericExportExcel();
        excel.exportSameSheet(tables);
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ReportTabla6Equipos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReportTabla6Equipos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReportTabla6Equipos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReportTabla6Equipos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ReportTabla6Equipos().setVisible(true);
            }
        });
    }

    private static String randomInRange(double min, double max) {
        Random random = new Random();
        double range = max - min;
        double scaled = random.nextDouble() * range;
        double shifted = scaled + min;
        return new DecimalFormat("#.#").format(shifted); // == (rand.nextDouble() * (max-min)) + min;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker2;
    private javax.swing.JProgressBar progreso;
    private javax.swing.JTable tblCDA;
    private javax.swing.JTable tblEquipo;
    private javax.swing.JTable tblPropietarios;
    private javax.swing.JTable tblPrueba;
    private javax.swing.JTable tblResultados;
    private javax.swing.JTable tblVehiculo;
    // End of variables declaration//GEN-END:variables
}
