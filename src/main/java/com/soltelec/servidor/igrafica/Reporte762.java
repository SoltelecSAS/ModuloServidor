/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.igrafica;

import com.soltelec.servidor.conexion.PersistenceController;
import com.soltelec.servidor.dao.CalibracionesJpaController;
import com.soltelec.servidor.dao.CdaJpaController;
import com.soltelec.servidor.dao.CertificadosJpaController;
import com.soltelec.servidor.dao.EquiposJpaController;
import com.soltelec.servidor.dao.HojaPruebasJpaController;
import com.soltelec.servidor.dao.PruebasJpaController;
import com.soltelec.servidor.dao.SoftwareJpaController;
import com.soltelec.servidor.dao.UsuariosJpaController;
import com.soltelec.servidor.dao.VehiculosJpaController;
import com.soltelec.servidor.model.CalibracionDosPuntos;
import com.soltelec.servidor.model.Calibraciones;
import com.soltelec.servidor.model.Cda;
import com.soltelec.servidor.model.Certificados;
import com.soltelec.servidor.model.Defxprueba;
import com.soltelec.servidor.model.HojaPruebas;
import com.soltelec.servidor.model.Medidas;
import com.soltelec.servidor.model.Pruebas;
import com.soltelec.servidor.model.Reinspeccion;
import com.soltelec.servidor.model.Software;
import com.soltelec.servidor.model.Usuarios;
import com.soltelec.servidor.model.Vehiculos;
import com.soltelec.servidor.utils.FormulaOpacidad;
import com.soltelec.servidor.utils.GenericExportExcel;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.JXDatePicker;

/**
 *
 * @author user
 */
public class Reporte762 extends javax.swing.JInternalFrame {

    private DefaultTableModel modeloCDA;
    private DefaultTableModel modeloInfoVehiculo;
    private DefaultTableModel modeloMedidas;
    private DefaultTableModel modeloPruebasAbortadas;
    private DefaultTableModel modeloDatosInicioPrueba;
    private DefaultTableModel modeloDatosEquipoYSoftware;
    private DefaultTableModel modeloDatosGeneralesInspeccion;
    private DefaultTableModel modeloCondicionesRechazo;

    private static final Logger LOG = Logger.getLogger(ReporteCAR.class.getName());
    private SimpleDateFormat formatoFecha;
    private DateFormat formatoFechas;
    private SimpleDateFormat sdfH;

    public Reporte762() {
        super("Reporte 762",
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
        modeloCDA.addColumn("Nombre Del Encargado");
        modeloCDA.addColumn("Tipo de Documento");
        modeloCDA.addColumn("Numero de Documento");
        modeloCDA.addColumn("Nombre o razón social");
        modeloCDA.addColumn("Tipo de documento(C.C.; C.E., NIT");
        modeloCDA.addColumn("Numero de identidficacion");
        modeloCDA.addColumn("Persona de contacto");
        modeloCDA.addColumn("Correo electronico");
        modeloCDA.addColumn("Telefonos de contacto");
        modeloCDA.addColumn("Ciudad/Departamento");
        modeloCDA.addColumn("Resolucion");
        modeloCDA.addColumn("Numero de expediente CDA");
        modeloCDA.addColumn("Fecha Resolucion Ambiental");//9
        modeloCDA.addColumn("Clase de CDA");//10
        modeloCDA.addColumn("Numero Expediente de la Autoridad Ambiental");//11
        modeloCDA.addColumn("Numero opacimetros");//12
        modeloCDA.addColumn("Numero analizadores(motores de encendidio por chispa)");//12
        modeloCDA.addColumn("Numero analizadores motos 4T");//12
        modeloCDA.addColumn("Numero equipos 2T");//12

        modeloDatosInicioPrueba = new DefaultTableModel();
        modeloDatosInicioPrueba.addColumn("Fecha - Hora Inicio prueba");
        modeloDatosInicioPrueba.addColumn("Fecha - Hora Terminacion");
        modeloDatosInicioPrueba.addColumn("Municipio de Inspeccion");
        modeloDatosInicioPrueba.addColumn("Lugar de la prueba");
        modeloDatosInicioPrueba.addColumn("Numero de Inspeccion");
        modeloDatosInicioPrueba.addColumn("Numero de Certificado Emitido");
        modeloDatosInicioPrueba.addColumn("Serial equipo");
        modeloDatosInicioPrueba.addColumn("Pef");
        modeloDatosInicioPrueba.addColumn("Marca Software");
        modeloDatosInicioPrueba.addColumn("Version Software");
        modeloDatosInicioPrueba.addColumn("ID inspector");


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
        modeloInfoVehiculo.addColumn("Tipo Motor");
        modeloInfoVehiculo.addColumn("N. Tubos de escape");//si
        modeloInfoVehiculo.addColumn("Diseño");
//         

/////////Equipos y software
        modeloDatosEquipoYSoftware = new DefaultTableModel();
        modeloDatosEquipoYSoftware.addColumn("MARCA_AG");
        modeloDatosEquipoYSoftware.addColumn("MOD_AG");
        modeloDatosEquipoYSoftware.addColumn("SERIAL_AG");
        modeloDatosEquipoYSoftware.addColumn("MARCA_BG");
        modeloDatosEquipoYSoftware.addColumn("MOD_BG");
        modeloDatosEquipoYSoftware.addColumn("SERIAL_BG");
        modeloDatosEquipoYSoftware.addColumn("PEF");
        modeloDatosEquipoYSoftware.addColumn("SERIAL_E");
        modeloDatosEquipoYSoftware.addColumn("MARCA_RPM");
        modeloDatosEquipoYSoftware.addColumn("SERIAL_RPM");
        modeloDatosEquipoYSoftware.addColumn("MARCA_TEMP_M");
        modeloDatosEquipoYSoftware.addColumn("SERIAL_TEMP_M");
        modeloDatosEquipoYSoftware.addColumn("MARCA_TEMP_A");
        modeloDatosEquipoYSoftware.addColumn("SERIAL_TEMP_A");
        modeloDatosEquipoYSoftware.addColumn("MARCA_HUM_R");
        modeloDatosEquipoYSoftware.addColumn("SERIAL_HUM_R");
        modeloDatosEquipoYSoftware.addColumn("NOM_SOFT");
        modeloDatosEquipoYSoftware.addColumn("VER_SOFT");
        modeloDatosEquipoYSoftware.addColumn("DES_SOFT");
        modeloDatosEquipoYSoftware.addColumn("TIP_IDE_VGP");
        modeloDatosEquipoYSoftware.addColumn("NUM_IDE_VGP");
        modeloDatosEquipoYSoftware.addColumn("NOM_VGP");
        modeloDatosEquipoYSoftware.addColumn("F_FUGAS");
        modeloDatosEquipoYSoftware.addColumn("F_VGP");
        modeloDatosEquipoYSoftware.addColumn("P_ALTA_LAB");
        modeloDatosEquipoYSoftware.addColumn("P_ALTA_CIL");
        modeloDatosEquipoYSoftware.addColumn("P_ALTA_CER");
        modeloDatosEquipoYSoftware.addColumn("P_BAJA_LAB");
        modeloDatosEquipoYSoftware.addColumn("P_BAJA_CIL");
        modeloDatosEquipoYSoftware.addColumn("P_BAJA_CER");
        modeloDatosEquipoYSoftware.addColumn("P_HC_ALTO_P");
        modeloDatosEquipoYSoftware.addColumn("P_HC_ALTO_H");
        modeloDatosEquipoYSoftware.addColumn("P_HC_BAJO_P");
        modeloDatosEquipoYSoftware.addColumn("P_HC_BAJO_H");
        modeloDatosEquipoYSoftware.addColumn("P_CO_ALTO");
        modeloDatosEquipoYSoftware.addColumn("P_CO_BAJO");
        modeloDatosEquipoYSoftware.addColumn("P_CO2_ALTO");
        modeloDatosEquipoYSoftware.addColumn("P_CO2_BAJO");
        modeloDatosEquipoYSoftware.addColumn("P_O2_ALTO");
        modeloDatosEquipoYSoftware.addColumn("P_O2_BAJO");
        modeloDatosEquipoYSoftware.addColumn("P_O2_BAJO");
        modeloDatosEquipoYSoftware.addColumn("R_HC_ALTO_H");
        modeloDatosEquipoYSoftware.addColumn("R_HC_BAJO_P");
        modeloDatosEquipoYSoftware.addColumn("R_HC_BAJO_H");
        modeloDatosEquipoYSoftware.addColumn("R_CO_ALTO");
        modeloDatosEquipoYSoftware.addColumn("R_CO_BAJO");
        modeloDatosEquipoYSoftware.addColumn("R_CO2_ALTO");
        modeloDatosEquipoYSoftware.addColumn("R_CO2_BAJO");
        modeloDatosEquipoYSoftware.addColumn("R_O2_ALTO");
        modeloDatosEquipoYSoftware.addColumn("R_O2_BAJO");
        modeloDatosEquipoYSoftware.addColumn("C_VGP");
        modeloDatosEquipoYSoftware.addColumn("R_VGP");

        //Tabla datos generales de inspeccion
        modeloDatosGeneralesInspeccion = new DefaultTableModel();
        modeloDatosGeneralesInspeccion.addColumn("Temperatura Ambiente(°C)");
        modeloDatosGeneralesInspeccion.addColumn("HUmedad Relativa(%)");
        modeloDatosGeneralesInspeccion.addColumn("LTOE Estandar(mm)");
        modeloDatosGeneralesInspeccion.addColumn("Metodo de medicion");
        modeloDatosGeneralesInspeccion.addColumn("Temperatura Motor(Temperatura inicial Diésel)(°C)");
        modeloDatosGeneralesInspeccion.addColumn("Temperatura Final (Diesel T<50)");
        modeloDatosGeneralesInspeccion.addColumn("RPM Ralentí");
        modeloDatosGeneralesInspeccion.addColumn("Rpm Crucero o Gobernada");
        modeloDatosGeneralesInspeccion.addColumn("HC en Ralentí");
        modeloDatosGeneralesInspeccion.addColumn("HC en crucero(si aplica)");
        modeloDatosGeneralesInspeccion.addColumn("CO en Ralentí");
        modeloDatosGeneralesInspeccion.addColumn("CO en Crucero(si aplica)");
        modeloDatosGeneralesInspeccion.addColumn("CO2 en Ralentí");
        modeloDatosGeneralesInspeccion.addColumn("CO2 en crucero (si aplica)");
        modeloDatosGeneralesInspeccion.addColumn("O2 en Ralentí");
        modeloDatosGeneralesInspeccion.addColumn("O2 en crucero (si aplica)");
        modeloDatosGeneralesInspeccion.addColumn("Ciclo preliminar (%)");
        modeloDatosGeneralesInspeccion.addColumn("RPM Gobernada en Ciclo Preliminar");
        modeloDatosGeneralesInspeccion.addColumn("RPM en Ralentí en Ciclo preliminar");
        modeloDatosGeneralesInspeccion.addColumn("Ciclo 1(%)");
        modeloDatosGeneralesInspeccion.addColumn("RPM Gobernada en Ciclo 1");
        modeloDatosGeneralesInspeccion.addColumn("RPM en Ralentí en Ciclo 1");
        modeloDatosGeneralesInspeccion.addColumn("Ciclo 2(%)");
        modeloDatosGeneralesInspeccion.addColumn("RPM Gobernada en Ciclo 2");
        modeloDatosGeneralesInspeccion.addColumn("RPM en Ralentí en Ciclo 2");
        modeloDatosGeneralesInspeccion.addColumn("Ciclo 3(%)");
        modeloDatosGeneralesInspeccion.addColumn("RPM Gobernada en Ciclo 3");
        modeloDatosGeneralesInspeccion.addColumn("RPM en Ralentí en Ciclo 3");
        modeloDatosGeneralesInspeccion.addColumn("Promedio final");
        modeloDatosGeneralesInspeccion.addColumn("Ciclo preliminar(m-1)");
        modeloDatosGeneralesInspeccion.addColumn("Ciclo 1(m-1)");
        modeloDatosGeneralesInspeccion.addColumn("Ciclo 2(m-1)");
        modeloDatosGeneralesInspeccion.addColumn("Ciclo 3(m-1)");
        modeloDatosGeneralesInspeccion.addColumn("Concepto final(%)");
        modeloDatosGeneralesInspeccion.addColumn("Condiciones de Aborto de la Prueba");
        modeloDatosGeneralesInspeccion.addColumn("Norma de Emisión Aplicada");
        //
        modeloCondicionesRechazo = new DefaultTableModel();
        modeloCondicionesRechazo.addColumn("Presencia de Humo(Negro/Azul)");
        modeloCondicionesRechazo.addColumn("Dilución en la Mezcla(SI/NO)");
        modeloCondicionesRechazo.addColumn("Nivel Emisiones(norma aplicable)");
        modeloCondicionesRechazo.addColumn("RPM fuera de Rango");
        modeloCondicionesRechazo.addColumn("Fugas tubo(SI/NO)");
        modeloCondicionesRechazo.addColumn("Salidas Adicionales(SI/NO)");
        modeloCondicionesRechazo.addColumn("Ausencia Tapones Aceite o fuga(SI/NO)");
        modeloCondicionesRechazo.addColumn("Ausencia Tapones Combustible o fuga(SI/NO)");
        modeloCondicionesRechazo.addColumn("Admisión Mal estado - Filtro aire(si/no)");
        modeloCondicionesRechazo.addColumn("Desconexión Recirculación (SI/NO)");
        modeloCondicionesRechazo.addColumn("Accesorios tubo(SI/NO)");
        modeloCondicionesRechazo.addColumn("Operación Incorrecta Refrigeración");
        modeloCondicionesRechazo.addColumn("Emisiones");
        modeloCondicionesRechazo.addColumn("Incorrecta Operacion Gobernador");
        modeloCondicionesRechazo.addColumn("Falla Súbita");
        modeloCondicionesRechazo.addColumn("Ejecución Incorrecta");
        modeloCondicionesRechazo.addColumn("Diferencia Aritmetica");
        modeloCondicionesRechazo.addColumn("Diferencia de Temperatura");
        modeloCondicionesRechazo.addColumn("Instalación accesorios tubo (SI/NO)");
        modeloCondicionesRechazo.addColumn("Operación Incorrecta Refrigeración(SI/NO)");
        modeloCondicionesRechazo.addColumn("Ausencia o Incorrecta Inst. Filtro Aire(SI/NO)");
        modeloCondicionesRechazo.addColumn("Activación Dispositivo(SI/NO)");
        modeloCondicionesRechazo.addColumn("RPM fuera de Rango");
        modeloCondicionesRechazo.addColumn("Presencia Humo Negro / Azul");
        modeloCondicionesRechazo.addColumn("Fugas tubo(SI/NO)");
        modeloCondicionesRechazo.addColumn("Ausencia Tapones Aceite o fuga(SI/NO)");
        modeloCondicionesRechazo.addColumn("Nro Prueba");
        modeloCondicionesRechazo.addColumn("Placa");
        modeloCondicionesRechazo.addColumn("Hora y Fecha Prueba");
        modeloCondicionesRechazo.addColumn("Motivo aborto");
        modeloCondicionesRechazo.addColumn("Hora y Fecha Aborto");

        /*
        *
        *Informacion Medidas
        *
         */
        modeloMedidas = new DefaultTableModel();
        modeloMedidas.addColumn("Rev Emisiones CO Ral"); //0
        modeloMedidas.addColumn("Rev Emisiones CO2 Ral");//1
        modeloMedidas.addColumn("Rev Emisiones HC Ral");//2
        modeloMedidas.addColumn("Rev Emisiones O2 Ral");//3
        modeloMedidas.addColumn("Rev Emisiones NOx Ral");//4
        modeloMedidas.addColumn("Rev Temperatura Ral");//5
        modeloMedidas.addColumn("Rev Revoluciones Ral");//6

        //Cruceros        
        modeloMedidas.addColumn("Rev Emisiones CO Cru ");//7
        modeloMedidas.addColumn("Rev Emisiones CO2 Cru");//8
        modeloMedidas.addColumn("Rev Emisiones HC Cru");//9
        modeloMedidas.addColumn("Rev Emisiones O2 Cru");//10
        modeloMedidas.addColumn("Rev Emisiones NOx");//11
        modeloMedidas.addColumn("Rev Temperatura Cru");//12
        modeloMedidas.addColumn("Rev Revoluciones Cru");//13

        //Humedad Relativa y Temperatura
        modeloMedidas.addColumn("Temperatura Ambiente");//14
        modeloMedidas.addColumn("Humedad Relativa");//15

        //Opacidad , Ruido y Causa 
        modeloMedidas.addColumn("Opacidad");//16
        modeloMedidas.addColumn("Ruido");//17
        modeloMedidas.addColumn("Causa");

        modeloPruebasAbortadas = new DefaultTableModel();
        modeloPruebasAbortadas.addColumn("Nro Prueba");
        modeloPruebasAbortadas.addColumn("Placa");
        modeloPruebasAbortadas.addColumn("Hora y Fecha Prueba");
        modeloPruebasAbortadas.addColumn("Motivo aborto");
        modeloPruebasAbortadas.addColumn("Hora y Fecha Aborto");

    }

    /**
     * Carga los datos en la tabla
     */
    private void fillData(Date fechaInicial, Date fechaFinal) throws SQLException {
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
        cargarInformacionCda();//Datos generales de la inspeccion
        for (Pruebas pruebasebas : lstPruebas) {
            //Informacion del vehiculo
            if (pruebasebas.getHojaPruebas().getTestsheet() == 16290) {
                int e = 0;
            }
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
            if (pruebasebas.getFinalizada().equalsIgnoreCase("Y")
                    && pruebasebas.getHojaPruebas().getPreventiva().equalsIgnoreCase("N")
                    && pruebasebas.getTipoPrueba().getTesttype() == 8) {
                work = work + 1;
                System.out.println("proc..!  " + work + " de " + lng);
                if (pruebasebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype() == 4
                        || pruebasebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype() == 1
                        || pruebasebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype() == 10
                        || pruebasebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype() == 3) {

                    /*
                    *
                    *
                    * LLamar todos los methodos  de cargar informacion de las tablas
                    *
                     */
                    //cargarInformacionCda();//Datos generales de la inspeccion
                    cargarDatosInicioPrueba(pruebasebas);//Datos de inicio de la prueba
                    cargarInformacionvehiculo(pruebasebas, form);//Datos del vehiculo inspeccionado
                    cargarDatosGeneralesInspeccion(pruebasebas);//Datos generales de la inspeccion
                    cargarCondicionesRechazo(pruebasebas);
                    cargarCondicionesAborto(pruebasebas);
                    //Object[] dataRowMedellin = new Object[37]; 
                    // modeloGases.addRow(dataRow);            

                    //cargarInformacionMedidas(pruebasebas, n);
                    this.tblCda.setModel(modeloCDA);
                    this.tblInfoVehiculos.setModel(modeloInfoVehiculo);
                    this.tblMedidas.setModel(modeloMedidas);
                    this.tblDatosInicioPrueba.setModel(modeloDatosInicioPrueba);
                    this.tblDatosGeneralesInspeccion.setModel(modeloDatosGeneralesInspeccion);
                    this.tblCondicionesRechazo.setModel(modeloCondicionesRechazo);
                    this.tblAborto.setModel(modeloPruebasAbortadas);

                    tblCda.setEnabled(false);
                    tblInfoVehiculos.setEnabled(false);
                    tblMedidas.setEnabled(false);
                    tblDatosInicioPrueba.setEnabled(false);
                    tblEquiposYSoftware.setEnabled(false);
                    tblDatosGeneralesInspeccion.setEnabled(false);
                    tblCondicionesRechazo.setEnabled(false);
                    tblAborto.setEnabled(false);

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
        jScrollPane16 = new javax.swing.JScrollPane();
        jScrollPane17 = new javax.swing.JScrollPane();
        tblMedidas = new javax.swing.JTable();
        tblCda = new javax.swing.JTable();
        tblDatosInicioPrueba = new javax.swing.JTable();
        tblEquiposYSoftware = new javax.swing.JTable();
        tblDatosGeneralesInspeccion = new javax.swing.JTable();
        tblCondicionesRechazo = new javax.swing.JTable();
        tblAborto = new javax.swing.JTable();

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
        infPrueba.addTab("Inf. CDA", jScrollPane11);

        tblDatosInicioPrueba.setModel(modeloDatosInicioPrueba);
        tblDatosInicioPrueba.setName("Dtos del Propietario."); // NOI18N
        jScrollPane12.setViewportView(tblDatosInicioPrueba);
        infPrueba.addTab("Datos Inicio Prueba", jScrollPane12);

        //Tabla de Modelo d einformacion Vehiculo		
        tblInfoVehiculos.setModel(modeloInfoVehiculo);
        tblInfoVehiculos.setName("Información de Vehiculo"); // NOI18N
        jScrollPane1.setViewportView(tblInfoVehiculos);
        infPrueba.addTab("Inf. Vehiculo", jScrollPane1);

        //Tabla datos generales inspeccion
        tblDatosGeneralesInspeccion.setModel(modeloDatosGeneralesInspeccion);
        tblDatosGeneralesInspeccion.setName("Datos Generales Inspeccion"); // NOI18N		
        jScrollPane14.setViewportView(tblDatosGeneralesInspeccion);
        infPrueba.addTab("Inf. Datos generales inspeccion", jScrollPane14);

        //Tabla 
        tblCondicionesRechazo.setModel(modeloCondicionesRechazo);
        tblCondicionesRechazo.setName("Datos resolucion realizada"); // NOI18N		
        jScrollPane15.setViewportView(tblCondicionesRechazo);
        infPrueba.addTab("Condiciones de Rechazo", jScrollPane15);

        tblAborto.setModel(modeloPruebasAbortadas);
        tblAborto.setName("Condiciones de Aborto");
        jScrollPane17.setViewportView(tblAborto);
        infPrueba.addTab("Condiciones de Aborto", jScrollPane17);

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
        Reporte762 rsv = new Reporte762();
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
                try {
                    fillData(fechaInicial.getDate(), fechaFInal.getDate());
                } catch (SQLException ex) {
                    Logger.getLogger(Reporte762.class.getName()).log(Level.SEVERE, null, ex);
                }
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
        tables.add(tblInfoVehiculos);
        //tables.add(tblMedidas);
        tables.add(tblDatosInicioPrueba);
        //tables.add(tblEquiposYSoftware);
        tables.add(tblDatosGeneralesInspeccion);
        tables.add(tblCondicionesRechazo);
        tables.add(tblAborto);
        // tables.add(tblCondicionesAborto);
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
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JTable tblCda;
    private javax.swing.JTable tblAborto;
    private javax.swing.JTable tblDatosInicioPrueba;
    private javax.swing.JTable tblMedidas;
    private javax.swing.JTable tblInfoVehiculos;
    private javax.swing.JTable tblEquiposYSoftware;
    private javax.swing.JTable tblDatosGeneralesInspeccion;
    private javax.swing.JTable tblCondicionesRechazo;
    private javax.swing.JTable tblCondicionesAborto;

    /*
     *
     *
     *
     * Cargar Informacion de CDA
     *
     *
     */
    private void cargarInformacionCda() {
        CdaJpaController cda = new CdaJpaController();
        Cda mcda = cda.findCda(1);
        Object[] objCda = new Object[19];
        Object[] info = cda.findNombre();
        SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        objCda[0] = info[2];
        objCda[1] = "C.C";
        objCda[2] = info[3];
        objCda[3] = info[12];
        objCda[4] = "NIT";
        objCda[5] = info[13];
        objCda[6] = info[2];
        objCda[7] = info[38];
        objCda[8] = info[15];
        objCda[9] = info[22];
        objCda[10] = info[17];
        objCda[11] = "Por favor poner el expediente de la autoridad ambiental";
        objCda[12] = fecha.format(info[18]);
        objCda[13] = info[37];
        objCda[14] = "Por favor poner el número de expediente de la autoridad ambiental";

        EquiposJpaController equiposJpaController = new EquiposJpaController();
        int cantidadOpacimetros = equiposJpaController.buscarCantidadOpacimetros();
        objCda[15] = cantidadOpacimetros;
        objCda[16] = "Porfavor Poner el Numero Analizadores chispa moto";
        int cantidadAnalizadores4T = equiposJpaController.buscarCantidadAnalizadores4T();
        objCda[17] = cantidadAnalizadores4T;
        int cantidadAnalizadores2T = equiposJpaController.buscarCantidaAnalizadores2T();
        objCda[18] = cantidadAnalizadores2T;

        modeloCDA.addRow(objCda);
    }

    private void cargarDatosInicioPrueba(Pruebas pruebas) {
        formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        sdfH = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Object[] objProp = new Object[20];
        CdaJpaController cda = new CdaJpaController();
        Certificados cer = new Certificados();
        Cda mcda = cda.findCda(1);

        objProp[0] = sdfH.format(pruebas.getFechaPrueba());
        objProp[1] = sdfH.format(pruebas.getFechaFinal());
        objProp[2] = pruebas.getHojaPruebas().getUbicacionMunicipio();
        objProp[3] = mcda.getDireccion();
        objProp[4] = pruebas.getHojaPruebas().getConHojaPrueba();
        CertificadosJpaController cert = new CertificadosJpaController();
        Certificados certificado = cert.findCertificadoHojaPrueba(pruebas.getHojaPruebas().getTestsheet());
        objProp[5] = certificado.getConsecutive();
        objProp[6] = pruebas.getSerialEquipo();
        EquiposJpaController equiposJpaController = new EquiposJpaController();
        com.soltelec.servidor.model.Equipos equipos = equiposJpaController.buscarEquipoBySerialRes(pruebas.getSerialEquipo());
            objProp[7] = "0." + equipos.getPef();
        SoftwareJpaController softwareJpaController = new SoftwareJpaController();
        Software software = softwareJpaController.findSoftware(1);
        objProp[8] = software.getNombre();
        objProp[9] = software.getVersion();
        objProp[10] = pruebas.getUsuarios().getCedula();

        modeloDatosInicioPrueba.addRow(objProp);
    }

    /*
        *
        *
        * Metodo de cargar informacion Vehiculo
        *
        *
     */
    private void cargarInformacionvehiculo(Pruebas pruebasebas, String strIntento) {
        int deci = 1;

        Object[] objInfoVehiculo = new Object[12];
        //Codigo de pais de acuerdo a la codificacionque se anexa
        objInfoVehiculo[0] = pruebasebas.getHojaPruebas().getVehiculos().getCarplate();
        // Codigo para identificar si el vehiculo o motocilceta RTM hay tratamiento
        objInfoVehiculo[1] = pruebasebas.getHojaPruebas().getVehiculos().getMarcas().getNombremarca();
        // Numero de placa del vehiculo
        objInfoVehiculo[2] = pruebasebas.getHojaPruebas().getVehiculos().getModelo();
        //Codigo de la clase del vehiculo
        objInfoVehiculo[3] = pruebasebas.getHojaPruebas().getVehiculos().getCilindraje();
        //tipo de Servicio del vehiculo RTM
        if (pruebasebas.getHojaPruebas().getVehiculos().getLineasVehiculos().getCrlname() == null) {
            objInfoVehiculo[4] = "No posee";
        } else {
            objInfoVehiculo[4] = pruebasebas.getHojaPruebas().getVehiculos().getLineasVehiculos().getCrlname();
        }
        //Marca del Vehiculo 
        //Linea del vehiculo al que practicaron RTM
        //Modelo del Vehiculo
        objInfoVehiculo[5] = pruebasebas.getHojaPruebas().getVehiculos().getClasesVehiculo().getNombreclase();
        objInfoVehiculo[6] = pruebasebas.getHojaPruebas().getVehiculos().getServicios().getNombreservicio();
        objInfoVehiculo[7] = pruebasebas.getHojaPruebas().getVehiculos().getTiposGasolina().getNombregasolina();
        // Tipo de Combustible del vehiculo RTM
        objInfoVehiculo[8] = pruebasebas.getHojaPruebas().getVehiculos().getTiemposmotor();

        // Numero del Vin
        objInfoVehiculo[9] = pruebasebas.getHojaPruebas().getVehiculos().getNumeroexostos();
        // Cilindraje dle Vehiculo
        objInfoVehiculo[10] = pruebasebas.getHojaPruebas().getVehiculos().getDiseno().name();

        modeloInfoVehiculo.addRow(objInfoVehiculo);
    }

    //se quito el dia 6 de 12 del 2022 resolucion 762
    private void cargarInformacionEquipos(Pruebas pruebasebas) {
        formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        Object[] objProp = new Object[55];
        if (pruebasebas.getTipoPrueba().getTesttype() == 8) {
            EntityManager em = PersistenceController.getEntityManager();
            Query query = em.createNamedQuery("Equipos.findBySerialResolucion", com.soltelec.servidor.model.Equipos.class);
            query.setParameter("serialresolucion", pruebasebas.getSerialEquipo());
            List<com.soltelec.servidor.model.Equipos> eq = (List<com.soltelec.servidor.model.Equipos>) query.getResultList();
            for (com.soltelec.servidor.model.Equipos equipos : eq) {
                //query = em.createNamedQuery("Calibraciones.findAllCalibracionesBySerialId", Calibraciones.class);
                TypedQuery<Calibraciones> q = em.createQuery("SELECT c FROM Calibraciones c WHERE c.equipo.idEquipo = :equipo and c.tipoCalibracion.idTipoCalibracion = 2 ", Calibraciones.class);
                q.setParameter("equipo", equipos.getIdEquipo());
                List<Calibraciones> list = (List<Calibraciones>) q.getResultList();
                System.out.println("--" + list.size());
                for (Calibraciones c : list) {
                    if (c.getCalibration() != null) {
                        query = em.createNamedQuery("CalibracionDosPuntos.findByCalibration", CalibracionDosPuntos.class);
                        query.setParameter("calibration", c.getCalibration());

                        try {
                            CalibracionDosPuntos cp = (CalibracionDosPuntos) query.getSingleResult();
                            if (cp != null) {
                                objProp[0] = equipos.getMarca();
                                objProp[1] = equipos.getTipo();
                                objProp[2] = equipos.getNumAnalizador();
                                objProp[3] = equipos.getMarca();
                                objProp[4] = equipos.getTipo();
                                objProp[6] = equipos.getSerial();
                                objProp[7] = equipos.getPef();
                                objProp[8] = equipos.getSerial2();//Serial electronico serial2 o serial bench
                                objProp[9] = equipos.getSerial();
                                objProp[10] = equipos.getMarca();
                                objProp[11] = equipos.getSerial();
                                objProp[12] = equipos.getMarca();
                                objProp[13] = equipos.getSerial();
                                objProp[14] = equipos.getMarca();
                                objProp[15] = equipos.getSerial();
                                objProp[16] = equipos.getMarca();
                                objProp[17] = equipos.getSerial();
                                objProp[18] = "SART";
                                objProp[19] = "1.7.3";
                                objProp[20] = "Soltelec S.A.S";
                                objProp[21] = "C.C";
                                objProp[22] = pruebasebas.getUsuarios().getCedula();
                                objProp[23] = pruebasebas.getUsuarios().getNombreusuario();
                                objProp[24] = pruebasebas.getHojaPruebas().getFechaingresovehiculo();
                                objProp[25] = pruebasebas.getHojaPruebas().getFechaingresovehiculo();
                                objProp[26] = "Laboratorio gases";
                                objProp[27] = cp.getAltaCo();
                                objProp[28] = "Certificado de gas de alta";
                                objProp[29] = "Laboratorio gases";
                                objProp[30] = cp.getBmCo();
                                objProp[31] = "Certificado de gas de alta";
                                objProp[32] = "Laboratorio gases";
                                objProp[33] = cp.getAltaHc();
                                objProp[34] = cp.getBancoAltaHc();
                                objProp[35] = cp.getBmHc();
                                objProp[36] = cp.getBancoBmHc();
                                objProp[37] = cp.getAltaCo();
                                objProp[38] = cp.getBmCo();
                                objProp[39] = cp.getAltaCo2();
                                objProp[40] = cp.getBmCo2();
                                objProp[41] = cp.getBancoAltaO2();
                                objProp[42] = cp.getBancoBmO2();
                                objProp[43] = cp.getAltaHc();
                                objProp[44] = cp.getBancoAltaHc();
                                objProp[45] = cp.getBmHc();
                                objProp[46] = cp.getBancoBmHc();
                                objProp[47] = cp.getAltaCo();
                                objProp[48] = cp.getBmCo();
                                objProp[49] = cp.getAltaCo2();
                                objProp[50] = cp.getBmCo2();
                                objProp[51] = cp.getBancoAltaO2();
                                objProp[52] = cp.getBancoBmO2();
                                for (Defxprueba def : pruebasebas.getDefectos()) {
                                    if (def.getDefectos().getCardefault() == 80000) {
                                        objProp[53] = "Si";
                                    } else {
                                        objProp[53] = "No";
                                    }
                                }
                                objProp[54] = pruebasebas.getAbortada();
                                modeloDatosEquipoYSoftware.addRow(objProp);

                            }

                        } catch (NoResultException e) {
                            e.printStackTrace();

                            continue;
                        }

                    }
                }
            }
        }

    }

    private void cargarDatosGeneralesInspeccion(Pruebas pruebasebas) {
        Object[] objProp = new Object[37];
        int idTipoGasolina = pruebasebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype();
        if (pruebasebas.getAbortada().equalsIgnoreCase("N")) {
            VehiculosJpaController vehiculosJpaController = new VehiculosJpaController();
            Vehiculos vehiculos = vehiculosJpaController.findVehiculos(pruebasebas.getHojaPruebas().getVehiculos().getCar());
            if (vehiculos.getTiposGasolina().equals("3")) {
                objProp[2] = vehiculos.getDiametro();
            }
            objProp[2] = "";
            objProp[3] = pruebasebas.getHojaPruebas().getFormaMedTemperatura();

            for (Medidas medidas : pruebasebas.getMedidasList()) {
                switch (medidas.getTiposMedida().getMeasuretype()) {
                    //----------------------------------------------------------
                    //---------------VEHICULOS CICLO OTTO 4T -------------------
                    //----------------------------------------------------------

                    case 8001://medida de HC Ralenti Cuatro Tiempos
                        // df.applyPattern("#0.0");
                        String HCRalenti = String.valueOf(medidas.getValormedida());
                        objProp[8] = HCRalenti;
                        break;

                    case 8002://medida de CO Ralenti Cuatro tiempos
                        //df.applyPattern("#0.0");
                        // String CORalenti = df.format(m.getValor());// se comentaria debidso a que si se deja funcionando redondea los varoles de gases en ralenti
                        String CORalenti = String.valueOf(medidas.getValormedida());
                        objProp[10] = CORalenti;
                        break;
                    case 8003:
                        // df.applyPattern("#0.0");

                        if (idTipoGasolina == 1) {
                            String CO2Ralenti = String.valueOf(medidas.getValormedida());
                            objProp[12] = CO2Ralenti;
                        } else {
                            String CO2Ralenti = String.valueOf(medidas.getValormedida());
                            objProp[12] = CO2Ralenti;
                        }
                        break;
                    case 8004://medida de O2 Ralenti Cuatro Tiempos
                        //df.applyPattern("#0.0");
                        if (idTipoGasolina == 1) {
                            String O2Ralenti = String.valueOf(medidas.getValormedida());
                            objProp[14] = O2Ralenti;
                        } else {
                            String O2Ralenti = String.valueOf(medidas.getValormedida());
                            objProp[14] = O2Ralenti;
                        }
                        break;

                    case 8005: //rpm en ralenti
                        //df.applyPattern("#0.0");
                        String RevGasoRal = String.valueOf(medidas.getValormedida());
                        objProp[6] = RevGasoRal;
                        break; //Temperatura de Aceite 

                    case 8007://medida de HC Crucero Cuatro tiempos
                        // df.applyPattern("#0.0");
                        String HCCrucero = String.valueOf(medidas.getValormedida());
                        objProp[9] = HCCrucero;
                        break;

                    case 8008: // medida de CO Crucero Cuatro tiempos
                        //df.applyPattern("#0.0");
                        String COCrucero = String.valueOf(medidas.getValormedida());
                        objProp[11] = COCrucero;
                        break;

                    case 8009://medida de CO2  Crucero cuatro tiempos
                        //df.applyPattern("#0.0");
                        if (idTipoGasolina == 1) {
                            String CO2Crucero = String.valueOf(medidas.getValormedida());
                            objProp[13] = CO2Crucero;
                        } else {
                        }
                        break;

                    case 8010://medida de O2 Crucero cuatro tiempos                               
                        //df.applyPattern("#0.0");
                        if (idTipoGasolina == 1) {
                            String O2Crucero = String.valueOf(medidas.getValormedida());
                            objProp[15] = O2Crucero;
                        } else {
                            String O2Crucero = String.valueOf(medidas.getValormedida());
                            objProp[15] = O2Crucero;
                        }
                        break;

                    case 8011://Revoluciones en crucero
                        String RevGasoCruc = String.valueOf(medidas.getValormedida());
                        objProp[7] = RevGasoCruc;
                        break;

                    //----------------------------------------------------------
                    //---------------VEHICULOS CICLO OTTO 2T -------------------
                    //----------------------------------------------------------
                    case 8019:// medida de CO Ralenti dos tiempos
                        // df.applyPattern("#0.0");
                        if (idTipoGasolina == 1) {
                            String CO2Ralenti = "" + medidas.getValormedida();
                            objProp[12] = CO2Ralenti;
                        } else {
                            String CO2Ralenti = "" + medidas.getValormedida();
                            objProp[12] = CO2Ralenti;
                        }
                        break;
                    case 8020://medida de CO2 Ralenti dos tiempos
                        //df.applyPattern("#0.0");
                        String CORalenti2T = String.valueOf(medidas.getValormedida());
                        objProp[10] = CORalenti2T;
                        break;

                    case 8028://medida de RPM Ralenti dos tiempos
                        //df.applyPattern("#0.0");
                        String RevGasoRal2T = String.valueOf(medidas.getValormedida());
                        objProp[6] = RevGasoRal2T;
                        break;

                    case 8021://medida de O2 Ralenti dos tiempos
                        //df.applyPattern("#0.0");
                        if (idTipoGasolina == 1) {
                            String O2Ralenti2T = "" + medidas.getValormedida();
                            objProp[14] = O2Ralenti2T;
                        } else {
                            String O2Ralenti2T = "" + medidas.getValormedida();
                            objProp[14] = O2Ralenti2T;
                        }
                        break;

                    case 8018://medida de HC Ralenti Dos tiempos
                        //df.applyPattern("#0.0");
                        String HCRalenti2T = String.valueOf(medidas.getValormedida());
                        objProp[8] = HCRalenti2T;
                        break;

                    //----------------------------------------------------------
                    //---------------VEHICULOS CICLO OTTO(CATALIZADOR) ---------
                    //----------------------------------------------------------                         
                    case 8006://Revoluciones
//                        char temp = FormaMedTemperatura;
//                        //JFM------------------------------------------------------------------------------------------------
//                        if (temp == 'C') {//SI ES DIESEL Y NO ES MOTO MUESTRA VALOR 0 
//                            String TempGasoRal = String.valueOf(m.getValor());
//                            //TIPO DE GASOLINA 3 DIESEL--SI ES DIESEL MJESTRA VALOR 0 EN EL 
//
//                            if (idTipoGasolina == 3 && tipoVehiculo != 4) {
//
//                            } else if (tipoVehiculo == 4 && disenoVehiculo.equalsIgnoreCase("Convencional")) {
//
//                            } else // SI ES DIFERENTE DE DIESEL Y TIENE CATALIZADOR MUESTRA EN BLANCO EN EL FUR
//                            {
//                            }
                        //parametros.put("Catalizador", "SI");
                        //JFM------------------------------------------------------------------------------------------------    
//                        } else {
//                            if (temp == 'B' || temp == 'A') {
//                                String TempGasoRal = String.valueOf(m.getValor());
//                            } else {
//                                String TempGasoRal = String.valueOf(m.getValor());
//
//                                if (disenoVehiculo.equalsIgnoreCase("Scooter")) {//MUESTRA LA TEMPERATURA DE LA SCOTTER EN ""
//                                    if (tipoVehiculo == 4) {
//                                    } else {
//                                    }
//                                } else {
//                                }
//
//                                //parametros.put("Catalizador", "NA");
//                            }
//                        }
                        break;

                    //----------------------------------------------------------
                    //---------------VEHICULOS CICLO OTTO(TEMPERATURA) ---------
                    //----------------------------------------------------------     
                    case 8022://medida de O2 Ralenti dos tiempos
                        String TempGasoRal = medidas.getValormedida().equals("0") ? "" : String.valueOf(medidas.getValormedida());
//                    if (disenoVehiculo.equalsIgnoreCase("scooter")) {
//                    } else {
//                    }
                        break;
                    //----------------------------------------------------------
                    //---------VEHICULOS CICLO OTTO(TEMPERATURA AMBIENTE) ------
                    //----------------------------------------------------------      
                    case 8031:
                        if (idTipoGasolina != 3) {
                            String TempAmbGas = String.valueOf(medidas.getValormedida());
                            objProp[0] = TempAmbGas;
                        } else {
                            String TempAmbDis = String.valueOf(medidas.getValormedida());
                            objProp[0] = TempAmbDis;
                        }
                        /* 
                        if(tipoVehiculo == 5 )
                        {
                             String HRGas = df.format(m.getValor());
                            parametros.put("HRGas", ajustarValorMedida(HRGas.trim()));
                            
                        }*/
                        break;

                    //----------------------------------------------------------
                    //---------VEHICULOS CICLO OTTO(HUMEDAD RELATIVA) ----------
                    //---------------------------------------------------------- 
                    case 8032:
                        if (idTipoGasolina != 3) {
                            String HRGas = String.valueOf(medidas.getValormedida());
                            objProp[1] = HRGas;
                        } else {
                            String HRDis = String.valueOf(medidas.getValormedida());
                            objProp[1] = HRDis;
                        }

                        /* if(tipoVehiculo == 5 )
                        {
                             String HRGas = df.format(m.getValor());
                            parametros.put("HRGas", ajustarValorMedida(HRGas.trim()));
                            
                        }*/
                        break;

                    //----------------------------------------------------------
                    //--VEHICULOS CICLO DIESEL (MEDIDAS DE OPACIMETRO) ---------
                    //----------------------------------------------------------    
                    case 8033://OPACIDAD CICLO0P - PREELIMINAR
                        String OpCiclo1 = String.valueOf(medidas.getValormedida());
                        objProp[16] = OpCiclo1;
                        System.out.println();
                        Double K = FormulaOpacidad.opacidad(Double.parseDouble(OpCiclo1));
                        System.out.println("---- oacidad: " + OpCiclo1 + " K:" + K);
                        objProp[29] = K;
                        break;

                    case 8013://OPACIDAD CICLO1   
                        String OpCiclo2 = String.valueOf(medidas.getValormedida());
                        objProp[19] = OpCiclo2;
                        Double K1 = FormulaOpacidad.opacidad(Double.parseDouble(OpCiclo2));
                        objProp[30] = K1;
                        break;

                    case 8014://OPACIDAD CICLO2
                        String OpCiclo3 = String.valueOf(medidas.getValormedida());
                        objProp[22] = OpCiclo3;
                        Double K2 = FormulaOpacidad.opacidad(Double.parseDouble(OpCiclo3));
                        objProp[31] = K2;
                        break;

                    case 8015://OPACIDAD CICLO3 
                        String OpCiclo4 = String.valueOf(medidas.getValormedida());
                        objProp[25] = OpCiclo4;
                        Double K3 = FormulaOpacidad.opacidad(Double.parseDouble(OpCiclo4));
                        objProp[32] = K3;
                        break;

                    //----------------------------------------------------------
                    //--VEHICULOS CICLO DIESEL (MEDIDAS DE GOBERNADA) ----------
                    //----------------------------------------------------------      
                    case 8038: //GOBERNADA CICLO0 - PRELIMINAR
                        String GobCic1 = String.valueOf(medidas.getValormedida());
                        objProp[17] = GobCic1;
                        break;

                    case 8039://GOBERNADA CICLO1
                        String GobCic2 = String.valueOf(medidas.getValormedida());
                        objProp[20] = GobCic2;
                        break;

                    case 8040: //GOBERNADA CICLO2
                        String GobCic3 = String.valueOf(medidas.getValormedida());
                        objProp[23] = GobCic3;
                        break;

                    case 8041: //GOBERNADA CICLO3
                        String GobCic4 = String.valueOf(medidas.getValormedida());
                        objProp[26] = GobCic4;
                        break;

                    //----------------------------------------------------------
                    //------VEHICULOS CICLO DIESEL (RESULTADO) -----------------
                    //----------------------------------------------------------   
                    case 8017:
                        String ResultadoOp = String.valueOf(medidas.getValormedida());
                        objProp[28] = ResultadoOp;
                        Double K4 = FormulaOpacidad.opacidad(Double.parseDouble(ResultadoOp));
                        objProp[33] = K4;
                        break;
                    //----------------------------------------------------------
                    //------VEHICULOS CICLO DIESEL (RPM RALENTI) ---------------
                    //----------------------------------------------------------      
                    case 8035: //RPM RALENTI
                        // parametros.put("RevDiesel", Validaciones.redondear(Double.parseDouble(df.format(m.getValor()))) + m.getCondicion());
                        float Valor = medidas.getValormedida();//procedimiento para quitarle los decimales a la medida si es mayor a 999                         
                        String auxiliar;
                        if (Valor >= 100f && Valor < 1000f) {
                            auxiliar = String.valueOf(Valor);
                            auxiliar = auxiliar.substring(0, 3);
                        } else if (Valor >= 1000f) {
                            auxiliar = String.valueOf(Valor);
                            auxiliar = auxiliar.substring(0, 4);
                        } else {
                            auxiliar = medidas.getValormedida().toString();
                            System.out.println("valor de auxiliar : " + auxiliar);
                        }
                        objProp[18] = Valor;
                        objProp[21] = Valor;
                        objProp[24] = Valor;
                        objProp[27] = Valor;
                        //trunca u 
                        break;

                    //----------------------------------------------------------
                    //------VEHICULOS CICLO DIESEL (TERPERATURA INICIAL) -------
                    //----------------------------------------------------------       
                    case 8034: //Temperatura Inicial Diesel
                        String RevDiesel = (String.valueOf(medidas.getValormedida()).equals("0")) ? "" : String.valueOf(medidas.getValormedida());
                        objProp[4] = RevDiesel;
                        break;
                    //----------------------------------------------------------
                    //------VEHICULOS CICLO DIESEL (TERPERATURA FINAL) -------
                    //----------------------------------------------------------     
                    case 8037:
                        String TempFinD = (String.valueOf(medidas.getValormedida()).equals("0")) ? "" : String.valueOf(medidas.getValormedida());
                        objProp[5] = TempFinD;//validar que sea menor a 50
                        break;

                    case 8012://Revoluciones
//                    String valor = String.valueOf(m.getValor());
//                    char tempC = FormaMedTemperatura;
//                    if (tempC == 'C') {
//                    } else {
//                    }
                        break;

                    default://Error con el codigo de la medida pra gases
                        break;
                }//end of switch

            }
        }

        if (pruebasebas.getComentarioAborto() == null) {
            objProp[34] = "";
        }
        objProp[34] = pruebasebas.getComentarioAborto();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
            Date fechaInicioResolucion;
            fechaInicioResolucion = sdf.parse("2022-08-19");
            if (pruebasebas.getFechaPrueba().after(fechaInicioResolucion)) {
                objProp[35] = "Resolución Número 0762";
            } else {
                objProp[35] = "Resolución Número 970";
            }
        } catch (ParseException ex) {
            Logger.getLogger(ReporteCVC.class.getName()).log(Level.SEVERE, null, ex);
        }
        modeloDatosGeneralesInspeccion.addRow(objProp);

    }

    private void cargarCondicionesRechazo(Pruebas pruebas) {
        //implementar
        int tipoPrueba = pruebas.getTipoPrueba().getTesttype();
        Object[] objProp = new Object[36];
        if (tipoPrueba == 8) {
            System.out.println(" ------ Comentario de aborto:" + pruebas.getComentarioAborto());
            if (pruebas.getComentarioAborto() != null) {
                //presencia humo negro

                if (pruebas.getComentarioAborto().contains("Presencia")) {
                    objProp[0] = "Presencia de Humo Negro o Azul";
                } else {
                    objProp[0] = "No";
                }
                //Si cel comentario aborto contiene datos y hay revoluciones fuera de Rango 
                if (pruebas.getComentarioAborto().equalsIgnoreCase("REVOLUCIONES FUERA RANGO")) {
                    objProp[3] = "RPM fuera de rango";
                } else {
                    objProp[3] = "No";
                }
                //Si en el comentario Aborto es igual a condiciones Anormales
                if (pruebas.getComentarioAborto().equalsIgnoreCase("Condiciones Anormales")) {
                    // si hay condiciones anormales y las observaciones no estan nullos 
                    if (pruebas.getObservaciones() != null) {
                        //Concatenar detalles son las causas de rechazo
                        String aux = pruebas.getComentarioAborto().concat(" detalle: ").concat(pruebas.getObservaciones());
                        System.out.println("aux: " + aux);
                        //si hay comentario aborto y observaciones concatenar un .-
                        String[] arrValor = pruebas.getObservaciones().split(".-");
                        if (arrValor.length > 1) {
                            String[] arrCondiciones = arrValor[1].split(";");
                            if (arrCondiciones.length > 0) {
                                for (int i = 0; i < arrCondiciones.length; i++) {
                                    //Fugas de Tubo de Escape
                                    if (arrCondiciones[i].contains("Instalación de accesorios o deformaciones") || arrCondiciones[i].contains(" Instalación de accesorios o deformaciones")) {

                                    }
                                    if (arrCondiciones[i].contains("DILUCION DE MUESTRA") || arrCondiciones[i].contains(" DILUCION DE MUESTRA")) {
                                        objProp[1] = "SI";
                                    }
                                    if (arrCondiciones[i].contains("Existencia de fugas en el tubo")) {
                                        objProp[4] = "SI";
                                    }
                                    if (arrCondiciones[i].contains("Existencia de fugas en el tubo") || arrCondiciones[i].contains(" Existencia de fugas en el tubo")) {
                                        objProp[4] = "SI";
                                    }
                                    if (arrCondiciones[i].contains("Salidas adicionales en el sistema de escape") || arrCondiciones[i].contains(" Salidas adicionales en el sistema de escape")) {
                                        objProp[5] = "Si";
                                    }
                                    if (arrCondiciones[i].contains("Ausencia de tapones de aceite") || arrCondiciones[i].contains(" Ausencia de tapones de aceite")) {
                                        objProp[6] = "SI";
                                    }
                                    if (arrCondiciones[i].contains("Ausencia de tapas o tapones de combustible") || arrCondiciones[i].contains(" Ausencia de tapas o tapones de combustible")) {
                                        objProp[7] = "Si";
                                    }
                                    if (arrCondiciones[i].contains("Sistema de admisión de aire en mal estado (filtro roto, o deformado) o ausencia del filtro de aire") || arrCondiciones[i].contains(" Sistema de admisión de aire en mal estado (filtro roto, o deformado) o ausencia del filtro de aire")) {
                                        objProp[8] = "si";
                                    }
                                    if (arrCondiciones[i].contains("Desconexión de sistemas de recirculación de gases provenientes del Carter del motor.(Por ejemplo válvula de ventilación positiva del Carter)") || arrCondiciones[i].contains(" Desconexión de sistemas de recirculación de gases provenientes del Carter del motor.(Por ejemplo válvula de ventilación positiva del Carter)")) {
                                        objProp[9] = "SI";
                                    }
                                    if (arrCondiciones[i].contains("Instalación de accesorios o deformaciones") || arrCondiciones[i].contains(" Instalación de accesorios o deformaciones")) {
                                        objProp[10] = "SI";
                                    }
                                    if (arrCondiciones[i].contains("Incorrecta operación del sistema de refrigeración") || arrCondiciones[i].contains(" Incorrecta operación del sistema de refrigeración")) {
                                        objProp[11] = "SI";
                                    }
                                    if (arrCondiciones[i].contains("Emisiones")) {//modificar
                                        objProp[12] = "SI";
                                    }
                                    if (arrCondiciones[i].contains("Gobernador")) {//modificar
                                        objProp[13] = "SI";
                                    }
                                    if (arrCondiciones[i].contains("Falla súbita del motor y/o sus accesorios") || arrCondiciones[i].contains(" Falla súbita del motor y/o sus accesorios")) {
                                        objProp[14] = "SI";
                                    }
                                    if (arrCondiciones[i].contains("La diferencia aritmética entre el valor mayor y menor de opacidad de las tres aceleraciones.") || arrCondiciones[i].contains(" La diferencia aritmética entre el valor mayor y menor de opacidad de las tres aceleraciones.")) {
                                        objProp[15] = "SI";
                                    }
                                    if (arrCondiciones[i].contains("Diferencia de temperatura")) {
                                        objProp[16] = "SI";
                                    }
                                    if (arrCondiciones[i].contains("Instalación de accesorios o deformaciones") || arrCondiciones[i].contains(" Instalación de accesorios o deformaciones")) {
                                        objProp[17] = "SI";
                                    }
                                    if (arrCondiciones[i].contains("Incorrecta operación del sistema de refrigeración") || arrCondiciones[i].contains(" Incorrecta operación del sistema de refrigeración")) {
                                        objProp[18] = "SI";
                                    }
                                    if (arrCondiciones[i].contains("Sistema de admisión de aire en mal estado (filtro roto, o deformado) o ausencia del filtro de aire") || arrCondiciones[i].contains(" Sistema de admisión de aire en mal estado (filtro roto, o deformado) o ausencia del filtro de aire")) {
                                        objProp[19] = "si";
                                    }
                                    if (arrCondiciones[i].contains("Activación de dispositivos") || arrCondiciones[i].contains(" Activación de dispositivos")) {
                                        objProp[20] = "SI";
                                    }
                                }
                            }

                        }
                    } else {
                        objProp[15] = pruebas.getComentarioAborto();
                    }//si hay coment condiciones Anormales
                } else {
                    objProp[0] = objProp[1] = objProp[2] = objProp[3] = objProp[4] = objProp[5] = objProp[6]
                            = objProp[7] = objProp[8] = objProp[9] = objProp[10] = objProp[11] = objProp[12] = objProp[13]
                            = objProp[14] = objProp[15] = objProp[16] = objProp[17] = objProp[18] = objProp[19]
                            = objProp[20] = objProp[21] = objProp[22] = objProp[23] = "No";
                }//si exitenCondiciones
            } else {
                objProp[0] = objProp[1] = objProp[2] = objProp[3] = objProp[4] = objProp[5] = objProp[6]
                        = objProp[7] = objProp[8] = objProp[9] = objProp[10] = objProp[11] = objProp[12] = objProp[13]
                        = objProp[14] = objProp[15] = objProp[16] = objProp[17] = objProp[18] = objProp[19]
                        = objProp[20] = objProp[21] = objProp[22] = objProp[23] = "No";
            }

        }
        modeloCondicionesRechazo.addRow(objProp);
    }

    private void cargarCondicionesAborto(Pruebas pruebas) throws SQLException {
        Object[] datos = new Object[5];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        datos[0] = pruebas.getIdPruebas();
        datos[1] = pruebas.getHojaPruebas().getVehiculos().getCarplate();
        datos[2] = sdf.format(pruebas.getFechaPrueba());
        datos[3] = pruebas.getComentarioAborto();
        datos[4] = sdf.format(pruebas.getFechaFinal());

        modeloPruebasAbortadas.addRow(datos);
    }


    /*
    *
    *
    *   Methodo Strng ObtenerRuidoScape para carga todas las medidas de la pruebaseba de luces
    *
    *
     */
}
