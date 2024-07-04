/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.igrafica;

import com.soltelec.servidor.dao.CalibracionDosPuntosJpaController;
import com.soltelec.servidor.dao.CalibracionesJpaController;
import com.soltelec.servidor.dao.CdaJpaController;
import com.soltelec.servidor.dao.CertificadosJpaController;
import com.soltelec.servidor.dao.EquiposJpaController;
import com.soltelec.servidor.dao.HojaPruebasJpaController;
import com.soltelec.servidor.dao.MedidasJpaController;
import com.soltelec.servidor.dao.PruebasJpaController;
import com.soltelec.servidor.dao.SoftwareJpaController;
import com.soltelec.servidor.dao.VehiculosJpaController;
import com.soltelec.servidor.model.CalibracionDosPuntos;
import com.soltelec.servidor.model.Calibraciones;
import com.soltelec.servidor.model.Cda;
import com.soltelec.servidor.model.Certificados;
import com.soltelec.servidor.model.Equipos;
import com.soltelec.servidor.model.HojaPruebas;
import com.soltelec.servidor.model.Medidas;
import com.soltelec.servidor.model.Pruebas;
import com.soltelec.servidor.model.Software;
import com.soltelec.servidor.model.Vehiculos;
import com.soltelec.servidor.utils.GenericExportExcel;
import com.soltelec.servidor.utils.JTitlePanel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SingleSelectionModel;
import javax.swing.table.DefaultTableModel;
import sun.awt.Win32ColorModel24;

/*import javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS;
import javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER;
import javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS;
import javax.swing.JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED;
import javax.swing.JScrollPane.VERTICAL_SCROLLBAR_NEVER;*/
/**
 *
 * @author User
 */
public class ReporteDivecol extends javax.swing.JInternalFrame {

    JFileChooser fc;
    JPanel componentPanel;
    JTitlePanel componentTitlePanel;
    private DefaultTableModel modeloGasolina;
    private DefaultTableModel modeloRuido;
    private DefaultTableModel modelo;
    private Object nivel_ruido = "----";
    private Object veh[] = {"----", "", ""};

    private DefaultTableModel datosVehiculos;
    private DefaultTableModel datosPrueba;
    private DefaultTableModel modeloPruDiesel;
    private DefaultTableModel resultadosPrueba;
    private DefaultTableModel datosEquipoAnalizador;
    private DefaultTableModel modeloPruOTTO;

    private HashMap<Integer, Object> prueba = new HashMap<>();
    private HashMap<Integer, Object> hojaPrueba = new HashMap<>();
    private Set<Integer> hoja_Pruebas = new HashSet<>();

    private Object[] fila_1 = new Object[6];
    private Object[] fila_2 = new Object[46];
    private Object[] fila_3 = new Object[8];
    private final Object[] fila_4 = new Object[19];

    // private float pef;
    private String software;
    private String version;
    private List<Software> softList;

    /**
     * Creates new form ReportGeneral
     */
    public ReporteDivecol() {
        super("Reporte Divecol",
                true, //resizable
                true, //closable
                false, //maximizable
                true);
        iniciarModelo();
        initComponents();
    }

    private void iniciarModelo() {
        modeloRuido = new DefaultTableModel();
        modeloPruDiesel = new DefaultTableModel();
        modeloPruOTTO = new DefaultTableModel();

        modeloGasolina = new DefaultTableModel();
        modelo = new DefaultTableModel();

//      DATOS PARA LAS PRUEBAS DE RUIDO
        modeloRuido.addColumn("Nro prueba");
        modeloRuido.addColumn("Placa Vehiculo");
        modeloRuido.addColumn("Hora y fecha prueba");
        modeloRuido.addColumn("Motivo aborto");
        modeloRuido.addColumn("Abortada");
        modeloRuido.addColumn("Presión sonora exosto");

//      PRUEBAS DIESEL
        modeloPruOTTO.addColumn("PLACA");
        modeloPruOTTO.addColumn("LINEA");
        modeloPruOTTO.addColumn("MARCA");
        modeloPruOTTO.addColumn("TIPO DE VEHICULO");
        modeloPruOTTO.addColumn("MODELO VEHICULO");
        modeloPruOTTO.addColumn("CILINDRAJE");
        modeloPruOTTO.addColumn("CLASE VEHICULO");
        modeloPruOTTO.addColumn("TIPO SERVICIO VEHICULO");
        modeloPruOTTO.addColumn("NUMERO MOTOR VEHICULO");
        modeloPruOTTO.addColumn("VIN");
        modeloPruOTTO.addColumn("NUMERO LICENCIA");
        modeloPruOTTO.addColumn("KILOMETRAJE VEHICULO");
        modeloPruOTTO.addColumn("TIEMPOS DEL MOTOR");
        modeloPruOTTO.addColumn("TIPO COMBUSTIBLE VEHICULO ");
        modeloPruOTTO.addColumn("FECHA INGRESO VEHICULO");
        modeloPruOTTO.addColumn("NOMBRE PROPIETARIO");
        modeloPruOTTO.addColumn("APELLIDOS PROPIETARIO");
        modeloPruOTTO.addColumn("TIPO DOCUMENTO PROPIETARIO");
        modeloPruOTTO.addColumn("DOCUMENTO PROPIETARIO");
        modeloPruOTTO.addColumn("NUMERO CELULAR PROPIETARIO");
        modeloPruOTTO.addColumn("NUMERO TELEFONO PROPIETARIO");
        modeloPruOTTO.addColumn("DIRECCION PROPIETARIO");
        modeloPruOTTO.addColumn("CIUDAD PRINCIPAL PROPIETARIO");
        modeloPruOTTO.addColumn("CIUDAD PROPIETARIO");
        modeloPruOTTO.addColumn("FECHA PRUEBA");
        modeloPruOTTO.addColumn("Temperatura del motor");
        modeloPruOTTO.addColumn("Temp del motor 2 tiempos");
        modeloPruOTTO.addColumn("rpm en ralenti");
        modeloPruOTTO.addColumn("rpm en ralenti 2 tiempos");
        modeloPruOTTO.addColumn("HC ralenti");
        modeloPruOTTO.addColumn("HC ralenti 2 tiempos");
        modeloPruOTTO.addColumn("CO ralenti");
        modeloPruOTTO.addColumn("CO ralenti 2 tiempos");
        modeloPruOTTO.addColumn("CO2 Ralenti");
        modeloPruOTTO.addColumn("CO2 Ralenti 2 tiempos");
        modeloPruOTTO.addColumn("O2 Ralenti");
        modeloPruOTTO.addColumn("O2 Ralenti 2 tiempos");
        modeloPruOTTO.addColumn("RPM CRUCERO");
        modeloPruOTTO.addColumn("HC crucero");
        modeloPruOTTO.addColumn("CO crucero");
        modeloPruOTTO.addColumn("CO2 crucero");
        modeloPruOTTO.addColumn("O2 crucero");
        modeloPruOTTO.addColumn("Condiciones Anormales");
        modeloPruOTTO.addColumn("REINSPECCION");
        modeloPruOTTO.addColumn("RESULTADO PRUEBA");

//---------- 
    }

    private void imprimir(int hp, Pruebas p, Vehiculos vehiculo, HojaPruebasJpaController hojaJpa, CdaJpaController cdaJpa, EquiposJpaController equipoJpa, CalibracionesJpaController calJpa, HojaPruebas hPruebas) throws NullPointerException {

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        int id = equipoJpa.buscarEquiposBySerialRes(p.getSerialEquipo());
        Equipos equipo = equipoJpa.findEquipos(id);
        ///---           
        if (p.getTipoPrueba().getTesttype() == 7) {
            fila_1[0] = p.getIdPruebas();
            fila_1[1] = vehiculo.getCarplate();
            fila_1[2] = sdf.format(p.getFechaPrueba());
            fila_1[3] = p.getComentarioAborto();
            fila_1[4] = p.getAbortada();

            double max = 0;
            try {
                for (Medidas med : (List<Medidas>) prueba.get(7)) {
                    //System.out.println("valor de las medidas: "+ med.getValormedida());
                    if (med.getValormedida() > max) {
                        max = med.getValormedida();
                    }
                }
            } catch (NullPointerException ex) {
                System.out.println("No se encuentra las medidas para la prueba de ruido: " + ex);
            }
            fila_1[5] = max;
        }
        ///----Pruebas gasolina
        // if(vehiculo.getTiposGasolina().getFueltype()==1) si es solo gasolina descomentariar
        //{
        fila_2[0] = vehiculo.getCarplate();                          //PlACA
        fila_2[1] = vehiculo.getLineasVehiculos().getCrlname();      //LINEA DEL VEHICULO
        fila_2[2] = vehiculo.getMarcas().getNombremarca();           //NOMBRE DE LA MARCA
        fila_2[3] = vehiculo.getTipoVehiculo().getNombre();         //TIPO DE VEHICULO
        fila_2[4] = vehiculo.getModelo();                            //MODELO
        fila_2[5] = vehiculo.getCilindraje();                        //CILINDRAJE
        fila_2[6] = vehiculo.getClasesVehiculo().getNombreclase();   //CLASE DE VEHICULO
        fila_2[7] = vehiculo.getServicios().getNombreservicio();     //TIPO SERVICIO DE VEHICULO
        fila_2[8] = vehiculo.getNumeromotor();                       //NUMERO DEL MOTOR
        fila_2[9] = vehiculo.getVin();                               //VIN
        fila_2[10] = vehiculo.getPropietarios().getNumerolicencia(); //NUMERO DE LA LICENCIA DEL PROPIERTARIO
        String kilometraje = null;
        try {
            for (Medidas med : (List<Medidas>) prueba.get(1)) {
                if (med.getTiposMedida().getMeasuretype() == 1006) {
                    kilometraje = String.valueOf(med.getValormedida());
                    //System.out.println("medida: "+ med.getTiposMedida().getMeasuretype());   
                }
            }

            fila_2[11] = kilometraje;                                     //DIRECCION
            fila_2[12] = vehiculo.getTiemposmotor();                     //TIEMPOS DEL MOTOR
            fila_2[13] = vehiculo.getTiposGasolina().getNombregasolina();//TIPO DE GASOLINA
            fila_2[14] = sdf.format(hPruebas.getFechaingresovehiculo()); //FECHA DE INGRESO DEL VEHICULO
            fila_2[15] = vehiculo.getPropietarios().getNombres();        //NOMBRE PROPIETARIO
            fila_2[16] = vehiculo.getPropietarios().getApellidos();                        //Direccion                
            fila_2[17] = vehiculo.getPropietarios().getTipoIdentificacion().name();               //Direccion
            fila_2[18] = vehiculo.getPropietarios().getNumerolicencia();                       //Direccion
            fila_2[19] = vehiculo.getPropietarios().getCelular();                        //Direccion
            fila_2[20] = vehiculo.getPropietarios().getNumerotelefono();                        //Direccion
            fila_2[21] = vehiculo.getPropietarios().getDireccion();                        //Direccion
            fila_2[22] = vehiculo.getPropietarios().getCiudadades().getCiudadprincipal();                        //Direccion
            fila_2[23] = vehiculo.getPropietarios().getCiudadades().getNombreciudad();                        //Direccion
            fila_2[24] = sdf.format(p.getFechaPrueba());                        //Direccion

            for (Medidas med : (List<Medidas>) prueba.get(8)) {
                if (vehiculo.getTiemposmotor() != 2)//SI EL VEHICULO ES 4 TIEMPOS
                {
                    if (med.getTiposMedida().getMeasuretype() == 9111) {
                        fila_2[25] = String.valueOf(med.getValormedida());
                    }
                    fila_2[26] = "";                        //  TEMP DEL MOTOR 2T
                    if (med.getTiposMedida().getMeasuretype() == 8005) {
                        fila_2[27] = String.valueOf(med.getValormedida());//  RPM RALENTI                              
                    }
                    fila_2[28] = "";                        //  RPM RALENTI 2T                           
                    if (med.getTiposMedida().getMeasuretype() == 8001) {
                        fila_2[29] = String.valueOf(med.getValormedida());    //  HC RALENTI                         
                    }
                    fila_2[30] = "";                        //  HC RALENTI 2T
                    if (med.getTiposMedida().getMeasuretype() == 8002) {
                        fila_2[31] = String.valueOf(med.getValormedida());   //  CO RALENTI                            
                    }
                    fila_2[32] = "";                        //  CO RALENTI 2T
                    if (med.getTiposMedida().getMeasuretype() == 8003) {
                        fila_2[33] = String.valueOf(med.getValormedida()); //  CO2 RALENTI                             
                    }
                    fila_2[34] = "";                        //  CO2 RALENTI 2T
                    if (med.getTiposMedida().getMeasuretype() == 8004) {
                        fila_2[35] = String.valueOf(med.getValormedida()); //  O2 RALENTI                             
                    }
                    fila_2[36] = "";                        //  O2 RALENTI 2T
                    if (med.getTiposMedida().getMeasuretype() == 8011) {
                        fila_2[37] = String.valueOf(med.getValormedida()); //  RPM CRUCERO                           
                    }
                    if (med.getTiposMedida().getMeasuretype() == 8007) {
                        fila_2[38] = String.valueOf(med.getValormedida()); //  HC CRUCERO                           
                    }
                    if (med.getTiposMedida().getMeasuretype() == 8008) {
                        fila_2[39] = String.valueOf(med.getValormedida()); //  CO CRUCERO                           
                    }
                    if (med.getTiposMedida().getMeasuretype() == 8009) {
                        fila_2[40] = String.valueOf(med.getValormedida()); //  CO2 CRUCERO                          
                    }
                    if (med.getTiposMedida().getMeasuretype() == 8010) {
                        fila_2[41] = String.valueOf(med.getValormedida()); //  O2 CRUCERO  
                    } else if (vehiculo.getTiemposmotor() == 2) {

                        for (Medidas med2 : (List<Medidas>) prueba.get(8)) {
                            /*if(med.getTiposMedida().getMeasuretype() == 8018)
                                        {
                                          fila_2[30] = String.valueOf(med.getValormedida());  //  HC RALENTI 2T                            
                                        } */
                            fila_2[25] = "";

                            if (med2.getTiposMedida().getMeasuretype() == 9111) {
                                fila_2[26] = String.valueOf(med2.getValormedida()); //  TEMP DEL MOTOR 2T                               
                            }
                            fila_2[27] = "";//  RPM RALENTI 
                            if (med2.getTiposMedida().getMeasuretype() == 8028) {
                                fila_2[28] = String.valueOf(med2.getValormedida());  //  RPM RALENTI 2T                             
                            }
                            fila_2[29] = "";    //  HC RALENTI                         

                            if (med2.getTiposMedida().getMeasuretype() == 8018) {
                                fila_2[30] = String.valueOf(med2.getValormedida());  //  HC RALENTI 2T                            
                            }
                            fila_2[31] = "";   //  CO RALENTI                            

                            if (med2.getTiposMedida().getMeasuretype() == 8020) {
                                fila_2[32] = String.valueOf(med2.getValormedida());  //  CO RALENTI 2T                            
                            }

                            fila_2[33] = ""; //  CO2 RALENTI                             

                            if (med2.getTiposMedida().getMeasuretype() == 8018) {
                                fila_2[34] = String.valueOf(med2.getValormedida());  //  CO2 RALENTI 2T                           
                            }
                            fila_2[35] = "";
                            if (med2.getTiposMedida().getMeasuretype() == 8021 || med2.getTiposMedida().getMeasuretype() == 8019) {
                                fila_2[36] = String.valueOf(med2.getValormedida()); //  O2 RALENTI 2T                             
                            }
                            if (med2.getTiposMedida().getMeasuretype() == 8029) {
                                fila_2[37] = String.valueOf(med2.getValormedida()); //  RPM CRUCERO                           
                            }
                            if (med2.getTiposMedida().getMeasuretype() == 8023) {
                                fila_2[38] = String.valueOf(med2.getValormedida()); //  HC CRUCERO                          
                            }
                            if (med2.getTiposMedida().getMeasuretype() == 8024) {
                                fila_2[39] = String.valueOf(med2.getValormedida()); //  CO CRUCERO                           
                            }
                            if (med2.getTiposMedida().getMeasuretype() == 8025) {
                                fila_2[40] = String.valueOf(med2.getValormedida()); //  CO2 CRUCERO                          
                            }
                            if (med2.getTiposMedida().getMeasuretype() == 8026) {
                                fila_2[41] = String.valueOf(med2.getValormedida()); //  O2 CRUCERO                           
                            }
                        }
                    }
                }
            }
            fila_2[42] = cdaJpa.findCda(1).getDireccion();                        //  CONDICIONES ANORMALES
            fila_2[43] = "";                       //  REINSPECCION
            fila_2[44] = (hPruebas.getAprobado().equalsIgnoreCase("Y") ? "Aprobada" : "Reprobada");
        } catch (NullPointerException ex) {
            System.out.println("No se encuentra las medidas " + ex);
        }                      //  RESULTADO PRUEBA

        fila_3[0] = "";
        fila_3[1] = "";
        fila_3[2] = "";
        fila_3[3] = "";
        fila_3[4] = "";
        fila_3[5] = "";
        fila_3[6] = "";
        fila_3[7] = "";
        ///----
        fila_4[0] = "";
        fila_4[1] = "";
        fila_4[2] = "";
        fila_4[3] = "";
        fila_4[4] = "";
        fila_4[5] = "";
        fila_4[6] = "";
        fila_4[7] = "";
        fila_4[8] = "";
        fila_4[9] = "";
        fila_4[10] = "";
        fila_4[11] = "";
        fila_4[12] = "";
        fila_4[13] = "";
        fila_4[14] = "";
        fila_4[15] = "";
        fila_4[16] = "";
        fila_4[17] = "";
        fila_4[18] = "";

        if (id != 0) {

            fila_4[0] = equipo.getPef();
            fila_4[1] = equipo.getMarca();
            fila_4[2] = p.getSerialEquipo().split(";")[0];
            fila_4[3] = software;
            fila_4[4] = version;

            //Datos de calibracion
            //---
            int idCal = equipo.getIdEquipo();
            List listaCalibraciones = calJpa.findCalibracionesByFechaBefore(idCal, p.getFechaPrueba());
            Calibraciones calibracionFinal = null;
            ///---
            //for(Calibraciones cal : (List<Calibraciones>) listaCalibraciones){
            //        calibracionFinal = cal;
            //}                  
            ///---
            try {

                calibracionFinal = (Calibraciones) listaCalibraciones.get(listaCalibraciones.size() - 1);
                CalibracionDosPuntosJpaController cal2PuntosJpa = new CalibracionDosPuntosJpaController();
                CalibracionDosPuntos cal2 = cal2PuntosJpa.findCalibracionDosPuntos(calibracionFinal.getCalibration());

                fila_4[5] = cal2.getBmHc();//pefString.valueOf(; //Span hc  baja//
                fila_4[6] = cal2.getBmCo(); //Span co  baja
                fila_4[7] = cal2.getBmCo2(); //Span co2 baja 
                fila_4[8] = cal2.getBancoBmHc(); //Valor leido hc baja
                fila_4[9] = cal2.getBancoBmCo();  //Valor leido co baja
                fila_4[10] = cal2.getBancoBmCo2(); //Valor leido co2 baja
                fila_4[11] = cal2.getAltaHc(); //Span hc  alta//
                fila_4[12] = cal2.getAltaCo(); //Span co  alta
                fila_4[13] = cal2.getAltaCo2(); //Span co2 alta
                fila_4[14] = cal2.getBancoAltaHc(); //Valor leido hc alta
                fila_4[15] = cal2.getBancoAltaCo(); //Valor leido co alta
                fila_4[16] = cal2.getBancoAltaCo2(); //Valor leido co2 alta
                fila_4[17] = sdf.format(calibracionFinal.getFecha());
                fila_4[18] = (calibracionFinal.isAprobada()) ? "Aprobada" : "Rechazada";

            } catch (Exception ex) {

            }
        } else;

        //---     
        ///---
        if (p.getAbortada().trim().equalsIgnoreCase("Y")) {
            fila_3[7] = "Abortada";
        } else if (p.getAprobada().trim().equalsIgnoreCase("N")) {
            fila_3[7] = "Reprobada";
        } else if (p.getAprobada().trim().equalsIgnoreCase("Y")) {
            fila_3[7] = "Aprobada";
        }

        System.out.println("testsheet: " + hp);

        try {
            for (Medidas med : (List<Medidas>) prueba.get(1)) {
                System.out.println("medida: " + med.getTiposMedida().getMeasuretype());
            }
        } catch (NullPointerException ex) {
            System.out.println("No se encuentra las medidas para: " + 1 + " " + ex);
        }

        try {
            for (Medidas med : (List<Medidas>) prueba.get(7)) {
                System.out.println("medida: " + med.getTiposMedida().getMeasuretype());
            }
        } catch (NullPointerException ex) {
            System.out.println("No se encuentra las medidas para: " + 7 + " " + ex);
        }     //

        try {
            for (Medidas med : (List<Medidas>) prueba.get(8)) {

                System.out.println("medida: " + med.getTiposMedida().getMeasuretype());

                ///-----
                ///-----
            }
        } catch (NullPointerException ex) {
            System.out.println("No se encuentra las medidas para: " + 8 + " " + ex);
        }

        System.out.println("");

        modeloRuido.addRow(fila_1);
        //datosVehiculos.addRow(fila_1); 
        modeloPruOTTO.addRow(fila_2);
        //resultadosPrueba.addRow(fila_3);
        // datosEquipoAnalizador.addRow(fila_4);

        hojaPrueba.clear();
        prueba.clear();
        p = null;

    }

    private void fillData(Date fInicial, Date fFinal) {
        iniciarModelo();
        System.out.println("Ingreso a fillData");
        //****LLENAR DATOS****
        PruebasJpaController pruebasJpa = new PruebasJpaController();//Controlador para las pruebas
        VehiculosJpaController Vehiculos = new VehiculosJpaController();//Controlador para los vehiculos
        HojaPruebasJpaController hojaJpa = new HojaPruebasJpaController();//Controlador para las hojas de prueba
        MedidasJpaController Medidas = new MedidasJpaController();//Controlador para las medidas
        List<Pruebas> pruebas = pruebasJpa.findPruebasByFechaGDM(fInicial, fFinal);//Array para guardar las medidas
        CdaJpaController cdaJpa = new CdaJpaController();
        String nombreCda = cdaJpa.findCda(1).getNombre();;
        CalibracionesJpaController calJpa = new CalibracionesJpaController();
        EquiposJpaController equipoJpa = new EquiposJpaController();
        //0095802772912769
        List<HojaPruebas> hpbyfecha = pruebasJpa.findHP_porFecha(fInicial, fFinal);
        SoftwareJpaController softJpa = new SoftwareJpaController();
        softList = softJpa.findSoftwareEntities();
        for (Software s : softList) {
            software = s.getNombre();
            version = s.getVersion();
        }

        for (Pruebas p : pruebas) {
            hoja_Pruebas.add(p.getHojaPruebas().getTestsheet());//hoja pruebas es un array list para guardar las hojas de pruebas 
        }

        Pruebas pru = null;
        Vehiculos vehiculo = null;
        for (int hp : hoja_Pruebas) {

            for (Pruebas pr : pruebas) {
                if (hp == pr.getHojaPruebas().getTestsheet()) {
                    ///---

                    HojaPruebas hojaPruebas = pr.getHojaPruebas();
                    vehiculo = hojaPruebas.getVehiculos();
                    System.out.println("placa de vehiculo que envio: " + vehiculo.getCarplate());
                    ///---le envio-tipo de prueba------------------medidas
                    prueba.put(pr.getTipoPrueba().getTesttype(), pr.getMedidasList());
                    hojaPrueba.put(hp, prueba);

                    String Seleccion = (String) CMBusqueda.getSelectedItem();
                    switch (Seleccion) {
                        case "Todos": {
                            if (hojaPruebas.getFinalizada().equalsIgnoreCase("Y") && hojaPruebas.getPreventiva().equalsIgnoreCase("N")) {
                                pru = pr;
                                imprimir(hp, pru, vehiculo, hojaJpa, cdaJpa, equipoJpa, calJpa, hojaPruebas);
                            } else {
                            }
                            break;
                        }
                        case "Diesel": {
                            if (hojaPruebas.getFinalizada().equalsIgnoreCase("Y") && hojaPruebas.getPreventiva().equalsIgnoreCase("N") && vehiculo.getTiposGasolina().getFueltype() == 3) {
                                pru = pr;
                                imprimir(hp, pru, vehiculo, hojaJpa, cdaJpa, equipoJpa, calJpa, hojaPruebas);
                            } else {
                            }
                            break;
                        }
                        case "Gasolina": {
                            if (hojaPruebas.getFinalizada().equalsIgnoreCase("Y") && hojaPruebas.getPreventiva().equalsIgnoreCase("N") && vehiculo.getTiposGasolina().getFueltype() != 3) {
                                pru = pr;
                                imprimir(hp, pru, vehiculo, hojaJpa, cdaJpa, equipoJpa, calJpa, hojaPruebas);
                            } else {
                            }
                            break;
                        }
                    }

                }
                //imprimir(hp, pru, vehiculo, hojaJpa, cdaJpa, equipoJpa, calJpa);                     
            }
        }

        //------------------
        tbl1.setModel(modeloRuido);
        //tbl2.setModel(modeloPruOTTO);
        for (int i = 0; i < tbl3.getColumnCount(); i++) {
            tbl3.getColumnModel().getColumn(i).setPreferredWidth(800);
        }
        tbl3.setModel(modeloPruOTTO);
        for (int i = 0; i < tbl3.getColumnCount(); i++) {
            tbl3.getColumnModel().getColumn(i).setPreferredWidth(150);
        }
        tbl1.setEnabled(false);
        tbl2.setEnabled(false);
        tbl3.setEnabled(false);
        tbl4.setEnabled(false);
        // tbl4.setModel(datosEquipoAnalizador);

//------------------------------------------------------------------------------                
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        Data1 = new org.jdesktop.swingx.JXDatePicker();
        Data1.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
        jLabel2 = new javax.swing.JLabel();
        Data2 = new org.jdesktop.swingx.JXDatePicker();
        Data2.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        tabla4 = new javax.swing.JTabbedPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl3 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl4 = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl2 = new javax.swing.JTable();
        CMBusqueda = new javax.swing.JComboBox<>();

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

        tbl1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbl1.setName("Datos Del Vehiculo"); // NOI18N
        jScrollPane3.setViewportView(tbl1);

        tabla4.addTab("Ruidos", jScrollPane3);

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        tbl3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbl3.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tbl3.setName("Resultados de la Prueba"); // NOI18N
        jScrollPane2.setViewportView(tbl3);

        tabla4.addTab("Pruebas OTTO", jScrollPane2);

        tbl4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbl4.setName("Datos del Equipo Analizador"); // NOI18N
        jScrollPane4.setViewportView(tbl4);

        tabla4.addTab("Datos del Equipo Analizador", jScrollPane4);

        tbl2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbl2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tbl2.setName("Datos de la Prueba"); // NOI18N
        jScrollPane1.setViewportView(tbl2);

        tabla4.addTab("Pruebas Diesel", jScrollPane1);

        CMBusqueda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Diesel", "Gasolina" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Data1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Data2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68)
                .addComponent(CMBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addContainerGap(54, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(tabla4)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Data1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(Data2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(CMBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tabla4, javax.swing.GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (Data1.getDate() == null || Data2.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Seleccione Fechas");
            return;
        }
        fillData(Data1.getDate(), Data2.getDate());
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        List<JTable> tables = new ArrayList<>();
        tables.add(tbl1);
        tables.add(tbl2);
        tables.add(tbl3);
        tables.add(tbl4);

        GenericExportExcel excel = new GenericExportExcel();
        excel.exportExcel(tables);

    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CMBusqueda;
    private org.jdesktop.swingx.JXDatePicker Data1;
    private org.jdesktop.swingx.JXDatePicker Data2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane tabla4;
    private javax.swing.JTable tbl1;
    private javax.swing.JTable tbl2;
    private javax.swing.JTable tbl3;
    private javax.swing.JTable tbl4;
    // End of variables declaration//GEN-END:variables
}
