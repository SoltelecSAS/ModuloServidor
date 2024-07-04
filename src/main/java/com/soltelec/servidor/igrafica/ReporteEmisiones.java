package com.soltelec.servidor.igrafica;

import com.soltelec.servidor.dao.CalibracionDosPuntosJpaController;
import com.soltelec.servidor.dao.CalibracionesJpaController;
import com.soltelec.servidor.dao.CdaJpaController;
import com.soltelec.servidor.dao.CertificadosJpaController;
import com.soltelec.servidor.dao.CiudadesJpaController;
import com.soltelec.servidor.dao.EquiposJpaController;
import com.soltelec.servidor.dao.HojaPruebasJpaController;
import com.soltelec.servidor.dao.PruebasJpaController;
import com.soltelec.servidor.dao.SoftwareJpaController;
import com.soltelec.servidor.model.CalibracionDosPuntos;
import com.soltelec.servidor.model.Calibraciones;
import com.soltelec.servidor.model.Certificados;
import com.soltelec.servidor.model.HojaPruebas;
import com.soltelec.servidor.model.Medidas;
import com.soltelec.servidor.model.Pruebas;
import com.soltelec.servidor.model.Software;
import com.soltelec.servidor.model.Vehiculos;
import com.soltelec.servidor.utils.GenericExportExcel;
import com.soltelec.servidor.model.Cda;
import com.soltelec.servidor.model.Ciudades;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import javax.swing.JProgressBar;
import javax.swing.Timer;

public class ReporteEmisiones extends javax.swing.JInternalFrame {

    private static final Logger LOG = Logger.getLogger(ReporteSuperintendenciaVijia.class.getName());
    private SimpleDateFormat sdf;
    private Date fechaInicio, fechaFin;
    private Cda cda;

    private javax.swing.JTable tablaMotos;
    private javax.swing.JTable tablaCicloOtto;
    private javax.swing.JTable tablaCicloDiesel;

    private DefaultTableModel modeloMotosDatoVehiculo;
    private DefaultTableModel modeloMotosDatoPrueba;
    private DefaultTableModel modeloMotosResultadoPrueba;
    private DefaultTableModel modeloMotosDatoEquipo;

    private DefaultTableModel modeloOttoDatoVehiculo;
    private DefaultTableModel modeloOttoDatoPrueba;
    private DefaultTableModel modeloOttoResultadoPrueba;
    private DefaultTableModel modeloOttoDatoEquipo;

    private DefaultTableModel modeloDieselDatoVehiculo;
    private DefaultTableModel modeloDieselDatoPrueba;
    private DefaultTableModel modeloDieselResultadoPrueba;
    private DefaultTableModel modeloDieselDatoEquipo;

    private DefaultTableModel modeloMotos;
    private DefaultTableModel modeloCicloOtto;
    private DefaultTableModel modeloCicloDiesel;

    private int numero = 0;

    public ReporteEmisiones() {
        super("Reporte Cornare",
                true, //resizable
                true, //closable
                false, //maximizable
                true);
        iniciarModeloMotos();
        iniciarModeloOtto();
        iniciarModeloDiesel();

        iniciarModeloExcel();
        initComponents();
        initComponents2();
    }

    public void iniciarModeloMotos() {
        ////////MOTOS (DATOS DEL VEHICULO)
        this.modeloMotosDatoVehiculo = new DefaultTableModel();

        this.modeloMotosDatoVehiculo.addColumn("Numero Certificado");
        this.modeloMotosDatoVehiculo.addColumn("Marca");
        this.modeloMotosDatoVehiculo.addColumn("Año Modelo");
        this.modeloMotosDatoVehiculo.addColumn("Placa");
        this.modeloMotosDatoVehiculo.addColumn("Cilindraje en CM3");
        this.modeloMotosDatoVehiculo.addColumn("Tipo Motor");
        this.modeloMotosDatoVehiculo.addColumn("Diseño");

        ////////MOTOS (DATOS DE LA PRUEBA)
        this.modeloMotosDatoPrueba = new DefaultTableModel();

        this.modeloMotosDatoPrueba.addColumn("Realizacion Prueba");
        this.modeloMotosDatoPrueba.addColumn("Inspector");
        this.modeloMotosDatoPrueba.addColumn("Temperatura °C");
        this.modeloMotosDatoPrueba.addColumn("Humedad");
        this.modeloMotosDatoPrueba.addColumn("Ciudad");
        this.modeloMotosDatoPrueba.addColumn("Direccion");

        ////////MOTOS (RESULTADOS DE LA PRUEBA)
        this.modeloMotosResultadoPrueba = new DefaultTableModel();

        this.modeloMotosResultadoPrueba.addColumn("Temperatura Motor °C");
        this.modeloMotosResultadoPrueba.addColumn("RPM Ralenti");
        this.modeloMotosResultadoPrueba.addColumn("HC Ralenti ppm");
        this.modeloMotosResultadoPrueba.addColumn("CO Ralenti %");
        this.modeloMotosResultadoPrueba.addColumn("CO2 Ralenti %");
        this.modeloMotosResultadoPrueba.addColumn("O2 Ralenti %");
        this.modeloMotosResultadoPrueba.addColumn("Presencia Dilucion");
        this.modeloMotosResultadoPrueba.addColumn("Concepto Final");

        ////////MOTOS (DATOS DEL EQUIPO)
        this.modeloMotosDatoEquipo = new DefaultTableModel();

        this.modeloMotosDatoEquipo.addColumn("Valor PEF");
        this.modeloMotosDatoEquipo.addColumn("Marca Analizador");
        this.modeloMotosDatoEquipo.addColumn("No. Serie Analizador");
        this.modeloMotosDatoEquipo.addColumn("Nombre Software");
        this.modeloMotosDatoEquipo.addColumn("Version Software");
        this.modeloMotosDatoEquipo.addColumn("SPAN HC baja PPM");
        this.modeloMotosDatoEquipo.addColumn("SPAN CO Baja %");
        this.modeloMotosDatoEquipo.addColumn("SPAN CO2 Baja %");
        this.modeloMotosDatoEquipo.addColumn("Valor Leido HC Baja PPM");
        this.modeloMotosDatoEquipo.addColumn("Valor Leido CO Baja %");
        this.modeloMotosDatoEquipo.addColumn("Valor Leido CO2 Baja %");
        this.modeloMotosDatoEquipo.addColumn("SPAN HC Alta PPM ");
        this.modeloMotosDatoEquipo.addColumn("SPAN CO Alta %");
        this.modeloMotosDatoEquipo.addColumn("SPAN CO2 Alta %");
        this.modeloMotosDatoEquipo.addColumn("Valor Leido HC Alta PPM");
        this.modeloMotosDatoEquipo.addColumn("Valor Leido CO Alta %");
        this.modeloMotosDatoEquipo.addColumn("Valor Leido CO2 Alta %");
        this.modeloMotosDatoEquipo.addColumn("Fecha Verificacion");
        this.modeloMotosDatoEquipo.addColumn("Resultado Verificacion");
    }

    public void iniciarModeloOtto() {

        ////////OTTO (DATOS DEL VEHICULO)
        this.modeloOttoDatoVehiculo = new DefaultTableModel();

        this.modeloOttoDatoVehiculo.addColumn("Numero Certificado");
        this.modeloOttoDatoVehiculo.addColumn("Marca");
        this.modeloOttoDatoVehiculo.addColumn("Año Modelo");
        this.modeloOttoDatoVehiculo.addColumn("Placa");
        this.modeloOttoDatoVehiculo.addColumn("Cilindraje en CM3");
        this.modeloOttoDatoVehiculo.addColumn("Clase");
        this.modeloOttoDatoVehiculo.addColumn("Servicio");
        this.modeloOttoDatoVehiculo.addColumn("Combustible");

        ////////OTTO (DATOS DE LA PRUEBA)
        this.modeloOttoDatoPrueba = new DefaultTableModel();

        this.modeloOttoDatoPrueba.addColumn("Realizacion Prueba");
        this.modeloOttoDatoPrueba.addColumn("Inspector");
        this.modeloOttoDatoPrueba.addColumn("Temperatura °C");
        this.modeloOttoDatoPrueba.addColumn("Humedad Relativa %");
        this.modeloOttoDatoPrueba.addColumn("Ciudad");
        this.modeloOttoDatoPrueba.addColumn("Direccion");

        ////////OTTO (RESULTADOS DE LA PRUEBA)
        this.modeloOttoResultadoPrueba = new DefaultTableModel();

        this.modeloOttoResultadoPrueba.addColumn("Temperatura Motor °C");
        this.modeloOttoResultadoPrueba.addColumn("RPM Ralenti");
        this.modeloOttoResultadoPrueba.addColumn("HC Ralenti ppm");
        this.modeloOttoResultadoPrueba.addColumn("CO Ralenti %");
        this.modeloOttoResultadoPrueba.addColumn("CO2 Ralenti %");
        this.modeloOttoResultadoPrueba.addColumn("O2 Ralenti %");
        this.modeloOttoResultadoPrueba.addColumn("RPM Crucero");
        this.modeloOttoResultadoPrueba.addColumn("HC Crucero PPM");
        this.modeloOttoResultadoPrueba.addColumn("CO Crucero %");
        this.modeloOttoResultadoPrueba.addColumn("CO2 Crucero %");
        this.modeloOttoResultadoPrueba.addColumn("O2 Crucero %");
        this.modeloOttoResultadoPrueba.addColumn("Presencia Dilucion");
        this.modeloOttoResultadoPrueba.addColumn("Concepto Final");

        ////////OTTO (DATOS DEL EQUIPO)
        this.modeloOttoDatoEquipo = new DefaultTableModel();

        this.modeloOttoDatoEquipo.addColumn("Valor PEF");
        this.modeloOttoDatoEquipo.addColumn("Marca Analizador");
        this.modeloOttoDatoEquipo.addColumn("No. Serie Analizador");
        this.modeloOttoDatoEquipo.addColumn("Nombre Software");
        this.modeloOttoDatoEquipo.addColumn("Version Software");
        this.modeloOttoDatoEquipo.addColumn("SPAN HC baja PPM");
        this.modeloOttoDatoEquipo.addColumn("SPAN CO Baja %");
        this.modeloOttoDatoEquipo.addColumn("SPAN CO2 Baja %");
        this.modeloOttoDatoEquipo.addColumn("Valor Leido HC Baja PPM");
        this.modeloOttoDatoEquipo.addColumn("Valor Leido CO Baja %");
        this.modeloOttoDatoEquipo.addColumn("Valor Leido CO2 Baja %");
        this.modeloOttoDatoEquipo.addColumn("SPAN HC Alta PPM ");
        this.modeloOttoDatoEquipo.addColumn("SPAN CO Alta %");
        this.modeloOttoDatoEquipo.addColumn("SPAN CO2 Alta %");
        this.modeloOttoDatoEquipo.addColumn("Valor Leido HC Alta PPM");
        this.modeloOttoDatoEquipo.addColumn("Valor Leido CO Alta %");
        this.modeloOttoDatoEquipo.addColumn("Valor Leido CO2 Alta %");
        this.modeloOttoDatoEquipo.addColumn("Fecha Verificacion");
        this.modeloOttoDatoEquipo.addColumn("Resultado Verificacion");
    }

    public void iniciarModeloDiesel() {

        ////////DIESEL (DATOS DEL VEHICULO)
        this.modeloDieselDatoVehiculo = new DefaultTableModel();

        this.modeloDieselDatoVehiculo.addColumn("Numero Certificado");
        this.modeloDieselDatoVehiculo.addColumn("Marca");
        this.modeloDieselDatoVehiculo.addColumn("Año Modelo");
        this.modeloDieselDatoVehiculo.addColumn("Placa");
        this.modeloDieselDatoVehiculo.addColumn("Cilindraje en CM3");
        this.modeloDieselDatoVehiculo.addColumn("Clase");
        this.modeloDieselDatoVehiculo.addColumn("Servicio");
        this.modeloDieselDatoVehiculo.addColumn("Modificaciones Motor");

        ////////DIESEL (DATOS DE LA PRUEBA)
        this.modeloDieselDatoPrueba = new DefaultTableModel();

        this.modeloDieselDatoPrueba.addColumn("Realizacion Prueba");
        this.modeloDieselDatoPrueba.addColumn("Inspector");
        this.modeloDieselDatoPrueba.addColumn("Temperatura °C");
        this.modeloDieselDatoPrueba.addColumn("Humedad");
        this.modeloDieselDatoPrueba.addColumn("Ciudad");
        this.modeloDieselDatoPrueba.addColumn("Direccion");

        ////////DIESEL (RESULTADOS DE LA PRUEBA)
        this.modeloDieselResultadoPrueba = new DefaultTableModel();

        this.modeloDieselResultadoPrueba.addColumn("RPM Gobernada Medida");
        this.modeloDieselResultadoPrueba.addColumn("Temperatura Inicial Motor °C");
        this.modeloDieselResultadoPrueba.addColumn("RPM Ralenti");
        this.modeloDieselResultadoPrueba.addColumn("Resultado Inicial Opacidad CP %");
        this.modeloDieselResultadoPrueba.addColumn("RPM Gobernada CP ");
        this.modeloDieselResultadoPrueba.addColumn("Resultado Opacidad 1C %");
        this.modeloDieselResultadoPrueba.addColumn("RPM 1C Gobernada");
        this.modeloDieselResultadoPrueba.addColumn("Resultado Opacidad 2C %");
        this.modeloDieselResultadoPrueba.addColumn("RPM 2C Gobernada");
        this.modeloDieselResultadoPrueba.addColumn("Resultado Opacidad 3C %");
        this.modeloDieselResultadoPrueba.addColumn("RPM 3C Gobernada");
        this.modeloDieselResultadoPrueba.addColumn("Diferencias Aritmeticas %");
        this.modeloDieselResultadoPrueba.addColumn("Resultado Opacidad Final");
        this.modeloDieselResultadoPrueba.addColumn("Temperatura Final Motor °C");
        this.modeloDieselResultadoPrueba.addColumn("Diametro Tubo Escape mm");
        this.modeloDieselResultadoPrueba.addColumn("Concepto Final");

        ////////DIESEL (DATOS DEL EQUIPO)
        this.modeloDieselDatoEquipo = new DefaultTableModel();

        this.modeloDieselDatoEquipo.addColumn("LTOE M");
        this.modeloDieselDatoEquipo.addColumn("Marca Analizador");
        this.modeloDieselDatoEquipo.addColumn("No. Serie Analizador");
        this.modeloDieselDatoEquipo.addColumn("Nombre Software");
        this.modeloDieselDatoEquipo.addColumn("Version Software");
        this.modeloDieselDatoEquipo.addColumn("Valor Referencia Filtro 1 %");
        this.modeloDieselDatoEquipo.addColumn("Valor Leido Filtro 1 %");
        this.modeloDieselDatoEquipo.addColumn("Valor Referencia Filtro 2 %");
        this.modeloDieselDatoEquipo.addColumn("Valor Leido Filtro 2 %");
        this.modeloDieselDatoEquipo.addColumn("Resultado Verificacion");
        this.modeloDieselDatoEquipo.addColumn("Fecha Verificacion");
        this.modeloDieselDatoEquipo.addColumn("Lente1");

    }

    private void fillData(Date fechaInicial, Date fechaFinal) {
        iniciarModeloMotos();
        iniciarModeloOtto();
        iniciarModeloDiesel();

        sdf = new SimpleDateFormat("dd/MM/yyyy");
        HojaPruebasJpaController hpjc = new HojaPruebasJpaController();
        
        
        List<Pruebas> lstPruebas = hpjc.findByDatePruebas(fechaInicial, fechaFinal);
        System.out.println(" total take "+ lstPruebas.size());

        if (lstPruebas == null || lstPruebas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La consulta no genero datos");
            return;
        }

        System.out.println("Hay " + lstPruebas.size() + " hojas de prueba");

        CdaJpaController cdaJpa = new CdaJpaController();
        Cda cda = cdaJpa.findCda(1);

        SoftwareJpaController swJpa = new SoftwareJpaController();
        Software sw = swJpa.findSoftware(1);

        CiudadesJpaController CiuJpa = new CiudadesJpaController();
        //Ciudades ciu = CiuJpa.findCiudades(cda.getCiudad().getCity());
        int i = 0;
        for (Pruebas pruebas : lstPruebas) {
             i=i+1;
            CertificadosJpaController cjc = new CertificadosJpaController();
            Certificados ct = cjc.findCertificadoHojaPrueba(pruebas.getHojaPruebas().getTestsheet());
             System.out.println(" cargada ln of test "+i );
             
              //////////////////////////Datos equipo analizador/////////////////////
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String fec = sf.format(pruebas.getFechaPrueba());//PARAMETRO 1

                    String serEquipo = pruebas.getSerialEquipo();
                    EquiposJpaController eqJpa = new EquiposJpaController();
                     Integer idEquipo = eqJpa.buscarEquiposBySerialRes(serEquipo);//PARAMETRO 2
                    Integer tipoCal;
                    CalibracionesJpaController caJpa = new CalibracionesJpaController();
                     if (pruebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype() == 3 || pruebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype() == 11) {
                         tipoCal=1;
                     }else{
                          tipoCal=2;
                     }
                    List<Calibraciones> listCalib = caJpa.findCalibracionFechaEquipo(fec, idEquipo,tipoCal);

                    SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    Date fechaNue;
                    Date fechaAnt = new Date(80, 01, 01, 00, 00, 00);
                    Calibraciones c = null;

                    for (Calibraciones cal : listCalib) {
                        if (tipoCal == 2) {
                            if (cal.getTipoCalibracion().getIdTipoCalibracion() == 2) {
                                fechaNue = cal.getFecha();
                                if (fechaNue.after(fechaAnt)) {
                                    fechaAnt = fechaNue;
                                    c = cal;
                                    Integer iv = c.getCalibration();
                                } else {

                                }
                                break;
                            }
                        }else{
                            if (cal.getTipoCalibracion().getIdTipoCalibracion() == 1) {
                                fechaNue = cal.getFecha();
                                if (fechaNue.after(fechaAnt)) {
                                    fechaAnt = fechaNue;
                                    c = cal;
                                    Integer iv = c.getCalibration();
                                } else {

                                }
                                break;
                            }
                        }
                    }
//////////////////////////FinDatos equipo analizador/////////////////////
             
             
            if (pruebas.getHojaPruebas().getVehiculos().getTipoVehiculo().getCartype() == 4 && pruebas.getFinalizada().equalsIgnoreCase("Y")) {
                cargarMoto(pruebas, cda, sw, cda.getCiudad(), ct, c);
            }                 
            if (pruebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype() == 1 || pruebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype() == 10) {
                cargarOtto(pruebas, cda, sw, cda.getCiudad(), ct, c);
            }
            if (pruebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype() == 3 || pruebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype() == 11) {
                cargarDiesel(pruebas, cda, sw, cda.getCiudad(), ct, c);
            }

        }

        this.tblMotosDatoVehiculo.setModel(this.modeloMotosDatoVehiculo);
        this.tblMotosDatoPrueba.setModel(this.modeloMotosDatoPrueba);
        this.tblMotosResultadoPrueba.setModel(this.modeloMotosResultadoPrueba);
        this.tblMotosDatoEquipo.setModel(this.modeloMotosDatoEquipo);

        this.tblOttoDatoVehiculo.setModel(this.modeloOttoDatoVehiculo);
        this.tblOttoDatoPrueba.setModel(this.modeloOttoDatoPrueba);
        this.tblOttoResultadoPrueba.setModel(this.modeloOttoResultadoPrueba);
        this.tblOttoDatoEquipo.setModel(this.modeloOttoDatoEquipo);

        this.tblDieselDatoVehiculo.setModel(this.modeloDieselDatoVehiculo);
        this.tblDieselDatoPrueba.setModel(this.modeloDieselDatoPrueba);
        this.tblDieselResultadoPrueba.setModel(this.modeloDieselResultadoPrueba);
        this.tblDieselDatoEquipo.setModel(this.modeloDieselDatoEquipo);
    }

/////////////////////////////MOTOS//////////////////////////////////////////////////////////////
    private void cargarMoto(Pruebas pruebas, Cda cda, Software sw, String ciu, Certificados ct,Calibraciones c) {
        Object[] filaMotos = new Object[8];
        Object[] filaMotos2 = new Object[10];
        Object[] filaMotos3 = new Object[10];
        Object[] filaMotos4 = new Object[20];

        Vehiculos vehiculo = pruebas.getHojaPruebas().getVehiculos();
if (pruebas.getIdPruebas() == 242520) {          
    int eve=0;
}
        if (vehiculo.getTipoVehiculo().getCartype() == 4) {          
                if (pruebas.getTipoPrueba().getTesttype() == 8) {
                    ///////////////////////Datos del vehiculo/////////////////////                    
                    filaMotos[0] = ct.getConsecutive();
                    filaMotos[1] = vehiculo.getMarcas().getNombremarca();
                    filaMotos[2] = vehiculo.getModelo();
                    filaMotos[3] = vehiculo.getCarplate();
                    filaMotos[4] = vehiculo.getCilindraje();
                    filaMotos[5] = vehiculo.getTiemposmotor();
                    filaMotos[6] = vehiculo.getDiseno();
                    SimpleDateFormat sdm = new SimpleDateFormat("yyyy/MM/dd");
                    String fecha = sdm.format(pruebas.getFechaPrueba());

                    /////////////////////////Datos de la prueba/////////////////////
                    filaMotos2[0] = fecha;
                    filaMotos2[1] = pruebas.getUsuarios().getCedula();//Inspector que realiza la prueba

                    List<Medidas> medidas = pruebas.getMedidasList();
                    for (Medidas med : medidas) {
                        switch (med.getTiposMedida().getMeasuretype()) {
                            case 8031://Temperatura ambiente
                                filaMotos2[2] = med.getValormedida();
                                break;
                            case 8032://Humedad Relativa
                                filaMotos2[3] = med.getValormedida();
                                break;
                        }
                    }
                    filaMotos2[4] =  pruebas.getHojaPruebas().getUbicacionMunicipio();
                    filaMotos2[5] = cda.getDireccion();
                    
                    float o2Bd=0;
                    ///////////////////////////Resultados de la prueba/////////////////////
                    for (Medidas med2 : medidas) {
                        switch (med2.getTiposMedida().getMeasuretype()) {
                            case 8006:                                  //Temp. Motor
                                filaMotos3[0] = med2.getValormedida();
                                break;
                                
                            case 8022: //Temperatura en ralenti (TEMPR) FOR MOTO 2T
                                    filaMotos3[0] = med2.getValormedida();
                                
                            case 8005:                                  //RPM Ralenti                                
                                filaMotos3[1] = med2.getValormedida();
                                break;
                            case 8028:                                  //RPM Ralenti 2T
                                filaMotos3[1] = med2.getValormedida();
                                break;    
                              case 8018: //HidroCarburos en ralenty (HCR) Para Motos 2t
                                filaMotos3[2] = med2.getValormedida();
                                break;
                            case 8001:                                  //HC Ralenti ppm
                                filaMotos3[2] = med2.getValormedida();
                                break;
                                
                            case 8020: //Monoxido de carbono en crucero (COC) PARA MOTOS 2T
                                filaMotos3[3] = med2.getValormedida();
                                break;                               
                                
                            case 8002:                                  //CO ralenti
                                filaMotos3[3] = med2.getValormedida();
                                break;                                
                              case 8019: //Dioxido de carbono en crucero (CO2C) para Motos 2t
                                filaMotos3[4] = med2.getValormedida();
                                break;
                            case 8003:                                  //CO2 ralenti
                                filaMotos3[4] = med2.getValormedida();
                                break;
                            case 8021: //Oxigeno en ralenty (O2R) PARA MOTO 2T
                                o2Bd =med2.getValormedida();
                                filaMotos3[5] = med2.getValormedida();
                                break;
                            case 8004:                                  //O2 ralenti
                                filaMotos3[5] = med2.getValormedida();
                                o2Bd =med2.getValormedida();
                                break;
                        }//50lt3l3c545
                        float  oxigenoCorregir = 0;
                        if (vehiculo.getTiemposmotor() == 4) {
                            oxigenoCorregir = 6;
                        }
                        if (vehiculo.getTiemposmotor() == 2 && vehiculo.getModelo() >= 2010) {
                            oxigenoCorregir = 6;
                        }
                        if (vehiculo.getTiemposmotor() == 2 && vehiculo.getModelo() < 2010) {
                            oxigenoCorregir = 11;
                        }
                        
                        if (oxigenoCorregir <= o2Bd) {
                            filaMotos3[6] = "SI";//Presencia Dilucion
                        }else{
                            filaMotos3[6] = "NO";//Presencia Dilucion
                        }                        

                        if (pruebas.getAbortada().trim().equalsIgnoreCase("Y")) {
                            filaMotos3[7] = "Abortada";//Concepto Final
                        } else if (pruebas.getAprobada().trim().equalsIgnoreCase("N")) {
                            filaMotos3[7] = "Reprobada";//Concepto Final
                        } else if (pruebas.getAprobada().trim().equalsIgnoreCase("Y")) {
                            filaMotos3[7] = "Aprobada";//Concepto Final
                        }
                    }

                   
                    if (c != null) {
                        Integer idCalib = c.getCalibration();

                        CalibracionDosPuntosJpaController ca2Jpa = new CalibracionDosPuntosJpaController();
                        CalibracionDosPuntos ca2 = ca2Jpa.findCalibracionDosPuntos(idCalib);

                        if (ca2 != null) {
                            filaMotos4[0] = c.getEquipo().getIdEquipo() + " " + c.getTipoCalibracion().getIdTipoCalibracion() + " " + c.getCalibration();//ca2.getEquipo().getPef();//Valor PEF
                            filaMotos4[1] = ca2.getEquipo().getMarca();//Marca Analizador
                            filaMotos4[2] = ca2.getEquipo().getSerial();//Numero de serie analizador
                            filaMotos4[3] = sw.getNombre();//Nombre del software
                            filaMotos4[4] = sw.getVersion();//Version del software
                            filaMotos4[5] = ca2.getBmHc();//SPAN HC Baja ppm
                            filaMotos4[6] = ca2.getBmCo();//SPAN CO Baja %
                            filaMotos4[7] = ca2.getBmCo2();//SPAN CO2 Baja %
                            filaMotos4[8] = ca2.getBancoBmHc();//Valor leido HC Baja ppm
                            filaMotos4[9] = ca2.getBancoBmCo();//Valor leido CO Baja %
                            filaMotos4[10] = ca2.getBancoBmCo2();//Valor leido CO2 Baja %
                            filaMotos4[11] = ca2.getAltaHc();//SPAN HC Alta ppm
                            filaMotos4[12] = ca2.getAltaCo();//SPAN CO Alta %
                            filaMotos4[13] = ca2.getAltaCo2();//SPAN CO2 Alta %
                            filaMotos4[14] = ca2.getBancoAltaHc();//Valor leido HC Alta ppm
                            filaMotos4[15] = ca2.getBancoAltaCo();//Valor leido CO Alta %
                            filaMotos4[16] = ca2.getBancoAltaCo2();//Valor leido CO2 Alta %

                            String fecha2 = sdm.format(ca2.getFecha());

                            filaMotos4[17] = fecha2;//Fecha calibracion
                            filaMotos4[18] = "----";

                        } else {
                            filaMotos4[0] = "";//Valor PEF
                            filaMotos4[1] = "";//Marca Analizador
                            filaMotos4[2] = "";//Numero de serie analizador
                            filaMotos4[3] = "";//Nombre del software
                            filaMotos4[4] = "";//Version del software
                            filaMotos4[5] = "";//SPAN HC Baja ppm
                            filaMotos4[6] = "";//SPAN CO Baja %
                            filaMotos4[7] = "";//SPAN CO2 Baja %
                            filaMotos4[8] = "";//Valor leido HC Baja ppm
                            filaMotos4[9] = "";//Valor leido CO Baja %
                            filaMotos4[10] = "";//Valor leido CO2 Baja %
                            filaMotos4[11] = "";//SPAN HC Alta ppm
                            filaMotos4[12] = "";//SPAN CO Alta %
                            filaMotos4[13] = "";//SPAN CO2 Alta %
                            filaMotos4[14] = "";//Valor leido HC Alta ppm
                            filaMotos4[15] = "";//Valor leido CO Alta %
                            filaMotos4[16] = "";//Valor leido CO2 Alta %

                            //String fecha2 = sdm.format(calibracionDosPuntos.getFecha());
                            filaMotos4[17] = "";//Fecha calibracion
                            filaMotos4[18] = "";
                        }

                    } else {
                        filaMotos4[0] = "";//Valor PEF
                        filaMotos4[1] = "";//Marca Analizador
                        filaMotos4[2] = "";//Numero de serie analizador
                        filaMotos4[3] = "";//Nombre del software
                        filaMotos4[4] = "";//Version del software
                        filaMotos4[5] = "";//SPAN HC Baja ppm
                        filaMotos4[6] = "";//SPAN CO Baja %
                        filaMotos4[7] = "";//SPAN CO2 Baja %
                        filaMotos4[8] = "";//Valor leido HC Baja ppm
                        filaMotos4[9] = "";//Valor leido CO Baja %
                        filaMotos4[10] = "";//Valor leido CO2 Baja %
                        filaMotos4[11] = "";//SPAN HC Alta ppm
                        filaMotos4[12] = "";//SPAN CO Alta %
                        filaMotos4[13] = "";//SPAN CO2 Alta %
                        filaMotos4[14] = "";//Valor leido HC Alta ppm
                        filaMotos4[15] = "";//Valor leido CO Alta %
                        filaMotos4[16] = "";//Valor leido CO2 Alta %

                        //String fecha2 = sdm.format(calibracionDosPuntos.getFecha());
                        filaMotos4[17] = "";//Fecha calibracion
                        filaMotos4[18] = "";
                    }
                    this.modeloMotosDatoVehiculo.addRow(filaMotos);
                    this.modeloMotosDatoPrueba.addRow(filaMotos2);
                    this.modeloMotosResultadoPrueba.addRow(filaMotos3);
                    this.modeloMotosDatoEquipo.addRow(filaMotos4);
                }            
        }
    }

///////////////////////////OTTO///////////////////////////////////////////////////////////////////////////////
    public void cargarOtto(Pruebas pruebas, Cda cda, Software sw,  String ciu, Certificados ct,Calibraciones c) {
        Object[] filaOtto = new Object[10];
        Object[] filaOtto2 = new Object[10];
        Object[] filaOtto3 = new Object[15];
        Object[] filaOtto4 = new Object[20];

        Vehiculos vehiculo = pruebas.getHojaPruebas().getVehiculos();

        if (pruebas.getTipoPrueba().getTesttype() == 8) {
                filaOtto[0] = ct.getConsecutive();
                filaOtto[1] = vehiculo.getMarcas().getNombremarca();
                filaOtto[2] = vehiculo.getModelo();
                filaOtto[3] = vehiculo.getCarplate();
                filaOtto[4] = vehiculo.getCilindraje();
                filaOtto[5] = vehiculo.getClasesVehiculo().getNombreclase();
                filaOtto[6] = vehiculo.getServicios().getNombreservicio();
                filaOtto[7] = vehiculo.getTiposGasolina().getNombregasolina();

                SimpleDateFormat sdm = new SimpleDateFormat("yyyy/MM/dd");
                String fecha = sdm.format(pruebas.getFechaPrueba());

                filaOtto2[0] = fecha;
                filaOtto2[1] = pruebas.getUsuarios().getNombreusuario();

                List<Medidas> medidas = pruebas.getMedidasList();

                for (Medidas med : medidas) {

                    switch (med.getTiposMedida().getMeasuretype()) {
                        case 8031:                                      //Temperatura ambiente
                            filaOtto2[2] = med.getValormedida();
                            break;
                        case 8032:                                      //Humedad Relativa
                            filaOtto2[3] = med.getValormedida();
                            break;
                    }
                }

                filaOtto2[4] = ciu;
                filaOtto2[5] = cda.getDireccion();

                for (Medidas med : medidas) {
                    switch (med.getTiposMedida().getMeasuretype()) {
                        case 8027:                                  //Temp. Motor
                            filaOtto3[0] = med.getValormedida();
                            break;
                        case 8005:                                  //RPM Ralenti
                            filaOtto3[1] = med.getValormedida();
                            break;
                        case 8001:                                  //HC Ralenti PPM
                            filaOtto3[2] = med.getValormedida();
                            break;
                        case 8002:                                  //CO ralenti
                            filaOtto3[3] = med.getValormedida();
                            break;
                        case 8003:                                  //CO2 ralenti
                            filaOtto3[4] = med.getValormedida();
                            break;
                        case 8004:                                  //O2 ralenti
                            filaOtto3[5] = med.getValormedida();
                            break;
                        case 8011:                                  //RPM Crucero
                            filaOtto3[6] = med.getValormedida();
                            break;
                        case 8007:                                  //HC Crucero PPM
                            filaOtto3[7] = med.getValormedida();
                            break;
                        case 8008:                                  //CO Crucero %
                            filaOtto3[8] = med.getValormedida();
                            break;
                        case 8009:                                  //CO2 Crucero %
                            filaOtto3[9] = med.getValormedida();
                            break;
                        case 8010:                                  //O2 Crucero %
                            filaOtto3[10] = med.getValormedida();
                            break;
                    }
                }
                filaOtto3[11] = "----";//Presencia Dilucion

                if (pruebas.getAbortada().trim().equalsIgnoreCase("Y")) {
                    filaOtto3[12] = "3";//Concepto Final
                } else if (pruebas.getAprobada().trim().equalsIgnoreCase("N")) {
                    filaOtto3[12] = "2";//Concepto Final
                } else if (pruebas.getAprobada().trim().equalsIgnoreCase("Y")) {
                    filaOtto3[12] = "1";//Concepto Final
                }
               
                  
                    if (c != null) {
                        Integer idCalib = c.getCalibration();

                        CalibracionDosPuntosJpaController ca2Jpa = new CalibracionDosPuntosJpaController();
                        CalibracionDosPuntos ca2 = ca2Jpa.findCalibracionDosPuntos(idCalib);

                        if (ca2 != null) {
                            filaOtto4[0] = c.getEquipo().getIdEquipo() + " " + c.getTipoCalibracion().getIdTipoCalibracion() + " " + c.getCalibration();//ca2.getEquipo().getPef();//Valor PEF
                            filaOtto4[1] = ca2.getEquipo().getMarca();//Marca Analizador
                            filaOtto4[2] = ca2.getEquipo().getSerial();//Numero de serie analizador
                            filaOtto4[3] = sw.getNombre();//Nombre del software
                            filaOtto4[4] = sw.getVersion();//Version del software
                            filaOtto4[5] = ca2.getBmHc();//SPAN HC Baja ppm
                            filaOtto4[6] = ca2.getBmCo();//SPAN CO Baja %
                            filaOtto4[7] = ca2.getBmCo2();//SPAN CO2 Baja %
                            filaOtto4[8] = ca2.getBancoBmHc();//Valor leido HC Baja ppm
                            filaOtto4[9] = ca2.getBancoBmCo();//Valor leido CO Baja %
                            filaOtto4[10] = ca2.getBancoBmCo2();//Valor leido CO2 Baja %
                            filaOtto4[11] = ca2.getAltaHc();//SPAN HC Alta ppm
                            filaOtto4[12] = ca2.getAltaCo();//SPAN CO Alta %
                            filaOtto4[13] = ca2.getAltaCo2();//SPAN CO2 Alta %
                            filaOtto4[14] = ca2.getBancoAltaHc();//Valor leido HC Alta ppm
                            filaOtto4[15] = ca2.getBancoAltaCo();//Valor leido CO Alta %
                            filaOtto4[16] = ca2.getBancoAltaCo2();//Valor leido CO2 Alta %

                            String fecha2 = sdm.format(ca2.getFecha());

                            filaOtto4[17] = fecha2;//Fecha calibracion
                            filaOtto4[18] = "----";

                        } else {
                            filaOtto4[0] = "";//Valor PEF
                            filaOtto4[1] = "";//Marca Analizador
                            filaOtto4[2] = "";//Numero de serie analizador
                            filaOtto4[3] = "";//Nombre del software
                            filaOtto4[4] = "";//Version del software
                            filaOtto4[5] = "";//SPAN HC Baja ppm
                            filaOtto4[6] = "";//SPAN CO Baja %
                            filaOtto4[7] = "";//SPAN CO2 Baja %
                            filaOtto4[8] = "";//Valor leido HC Baja ppm
                            filaOtto4[9] = "";//Valor leido CO Baja %
                            filaOtto4[10] = "";//Valor leido CO2 Baja %
                            filaOtto4[11] = "";//SPAN HC Alta ppm
                            filaOtto4[12] = "";//SPAN CO Alta %
                            filaOtto4[13] = "";//SPAN CO2 Alta %
                            filaOtto4[14] = "";//Valor leido HC Alta ppm
                            filaOtto4[15] = "";//Valor leido CO Alta %
                            filaOtto4[16] = "";//Valor leido CO2 Alta %

                            //String fecha2 = sdm.format(calibracionDosPuntos.getFecha());
                            filaOtto4[17] = "";//Fecha calibracion
                            filaOtto4[18] = "";
                        }

                    } else {
                        filaOtto4[0] = "";//Valor PEF
                        filaOtto4[1] = "";//Marca Analizador
                        filaOtto4[2] = "";//Numero de serie analizador
                        filaOtto4[3] = "";//Nombre del software
                        filaOtto4[4] = "";//Version del software
                        filaOtto4[5] = "";//SPAN HC Baja ppm
                        filaOtto4[6] = "";//SPAN CO Baja %
                        filaOtto4[7] = "";//SPAN CO2 Baja %
                        filaOtto4[8] = "";//Valor leido HC Baja ppm
                        filaOtto4[9] = "";//Valor leido CO Baja %
                        filaOtto4[10] = "";//Valor leido CO2 Baja %
                        filaOtto4[11] = "";//SPAN HC Alta ppm
                        filaOtto4[12] = "";//SPAN CO Alta %
                        filaOtto4[13] = "";//SPAN CO2 Alta %
                        filaOtto4[14] = "";//Valor leido HC Alta ppm
                        filaOtto4[15] = "";//Valor leido CO Alta %
                        filaOtto4[16] = "";//Valor leido CO2 Alta %

                        //String fecha2 = sdm.format(calibracionDosPuntos.getFecha());
                        filaOtto4[17] = "";//Fecha calibracion
                        filaOtto4[18] = "";
                    }

                this.modeloOttoDatoVehiculo.addRow(filaOtto);
                this.modeloOttoDatoPrueba.addRow(filaOtto2);
                this.modeloOttoResultadoPrueba.addRow(filaOtto3);
                this.modeloOttoDatoEquipo.addRow(filaOtto4);

            }
        
    }

//////////////////////////DIESEL///////////////////////////////////////////////////////////////////////////
    public void cargarDiesel(Pruebas pruebas, Cda cda, Software sw, String ciu, Certificados ct,Calibraciones c) {
        Object[] filaDiesel = new Object[10];
        Object[] filaDiesel2 = new Object[10];
        Object[] filaDiesel3 = new Object[20];
        Object[] filaDiesel4 = new Object[20];
        Vehiculos vehiculo = pruebas.getHojaPruebas().getVehiculos();
        
                filaDiesel[0] = ct.getConsecutive();
                filaDiesel[1] = vehiculo.getMarcas().getNombremarca();
                filaDiesel[2] = vehiculo.getModelo();
                filaDiesel[3] = vehiculo.getCarplate();
                filaDiesel[4] = vehiculo.getCilindraje();
                filaDiesel[5] = vehiculo.getClasesVehiculo().getNombreclase();
                filaDiesel[6] = vehiculo.getServicios().getNombreservicio();
                filaDiesel[7] = vehiculo.getNumeromotor();

                SimpleDateFormat sdm = new SimpleDateFormat("yyyy/MM/dd");
                String fecha = sdm.format(pruebas.getFechaPrueba());

                filaDiesel2[0] = fecha;
                filaDiesel2[1] = pruebas.getUsuarios().getNombreusuario();

                List<Medidas> medidas = pruebas.getMedidasList();

                for (Medidas med : medidas) {

                    switch (med.getTiposMedida().getMeasuretype()) {
                        case 8031:                                      //Temperatura ambiente
                            filaDiesel2[2] = med.getValormedida();
                            break;
                        case 8032:                                      //Humedad Relativa
                            filaDiesel2[3] = med.getValormedida();
                            break;
                    }
                }
                filaDiesel2[4] = ciu;
                filaDiesel2[5] = cda.getDireccion();

                for (Medidas med : medidas) {
                    switch (med.getTiposMedida().getMeasuretype()) {
                        case 0000: //RPM Gobernada
                            filaDiesel3[0] = med.getValormedida();
                            break;
                        case 8034: //Temperatura del motor prueba Diesel (TEMPMOTORDIESEL)
                            filaDiesel3[1] = med.getValormedida();
                            break;
                        case 8005: //RPM Ralenti
                            filaDiesel3[2] = med.getValormedida();
                            break;
                        case 8033: //Ciclo preliminar para el opacimetro (CICLO1OP)
                            filaDiesel3[3] = med.getValormedida();
                            break;
                        case 0001: //RPM Gobernada en ciclo preliminar
                            filaDiesel3[4] = med.getValormedida();
                            break;
                        case 8013: //Primer ciclo para el opacimetro (CICLO1OP)
                            filaDiesel3[5] = med.getValormedida();
                            break;
                        case 0002: //RPM Gobernada en primer ciclo
                            filaDiesel3[6] = med.getValormedida();
                            break;
                        case 8014: //Segundo ciclo para el opacimetro (CICLO2OP)
                            filaDiesel3[7] = med.getValormedida();
                            break;
                        case 0003: //RPM Gobernada en segundo ciclo
                            filaDiesel3[8] = med.getValormedida();
                            break;
                        case 8015: //Tercer ciclo para el opacimetro (CICLO3OP)
                            filaDiesel3[9] = med.getValormedida();
                            break;
                        case 0004: //RPM Gobernada en tercer ciclo
                            filaDiesel3[10] = med.getValormedida();
                            break;
                        case 0005: //Diferencias aritmeticas
                            filaDiesel3[11] = med.getValormedida();
                            break;
                        case 8017: //Resultado opacidad final
                            filaDiesel3[12] = med.getValormedida();
                            break;
                        case 0006: //Temperatura final de motor
                            filaDiesel3[13] = med.getValormedida();
                            break;
                    }

                }
                filaDiesel3[14] = vehiculo.getDiametro();//Diametro del tubo de escape                            
                filaDiesel3[15] = "";
                                                     
                    Integer idCalib = c.getCalibration();
                        CalibracionesJpaController ca2Jpa = new CalibracionesJpaController();
                        Calibraciones ca2 = ca2Jpa.findCalibraciones(idCalib);

         if (ca2 != null) {
            filaDiesel4[0] = "360";
            filaDiesel4[1] = ca2.getEquipo().getMarca();//Marca Analizador
            filaDiesel4[2] = ca2.getEquipo().getSerial();//Numero de serie analizador
            filaDiesel4[3] = sw.getNombre();//Nombre del software
            filaDiesel4[4] = sw.getVersion();//Version del software
            filaDiesel4[5] = ca2.getValor1();//Valor referencia filtro 1
            filaDiesel4[6] = ca2.getValor4();//Valor leido filtro 1
            filaDiesel4[7] = ca2.getValor2();//Valor referencia filtro 2
            filaDiesel4[8] = ca2.getValor5();//Valor leido filtro 2
            String fecha2 = sdm.format(ca2.getFecha());
            filaDiesel4[10] = fecha2;//Fecha calibracion
            if (ca2.isAprobada()) {
                filaDiesel4[9] = "Aprobada";
            } else {
                filaDiesel4[9] = "Reprobada";
            }
        }
                this.modeloDieselDatoVehiculo.addRow(filaDiesel);
                this.modeloDieselDatoPrueba.addRow(filaDiesel2);
                this.modeloDieselResultadoPrueba.addRow(filaDiesel3);
                this.modeloDieselDatoEquipo.addRow(filaDiesel4);

            
        

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">  

    private void initComponents2() {
        tablaMotos = new javax.swing.JTable();
        tablaMotos.setModel(modeloMotos);
        tablaMotos.setName("Motos"); // NOI18N

        tablaCicloOtto = new javax.swing.JTable();
        tablaCicloOtto.setModel(modeloCicloOtto);
        tablaCicloOtto.setName("Otto"); // NOI18N

        tablaCicloDiesel = new javax.swing.JTable();
        tablaCicloDiesel.setModel(modeloCicloDiesel);
        tablaCicloDiesel.setName("Diesel"); // NOI18N

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtbPestanas = new javax.swing.JTabbedPane();
        jsPestanaMoto = new javax.swing.JScrollPane();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMotosDatoVehiculo = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblMotosDatoPrueba = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblMotosResultadoPrueba = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblMotosDatoEquipo = new javax.swing.JTable();
        jsPestanaOtto = new javax.swing.JScrollPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblOttoDatoVehiculo = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblOttoDatoPrueba = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblOttoResultadoPrueba = new javax.swing.JTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblOttoDatoEquipo = new javax.swing.JTable();
        jsPestanaDiesel = new javax.swing.JScrollPane();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblDieselDatoVehiculo = new javax.swing.JTable();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblDieselDatoPrueba = new javax.swing.JTable();
        jScrollPane11 = new javax.swing.JScrollPane();
        tblDieselResultadoPrueba = new javax.swing.JTable();
        jScrollPane12 = new javax.swing.JScrollPane();
        tblDieselDatoEquipo = new javax.swing.JTable();
        btnGenerar = new javax.swing.JButton();
        btnExportar = new javax.swing.JButton();
        progreso = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        fecha1 = new org.jdesktop.swingx.JXDatePicker();
        jLabel2 = new javax.swing.JLabel();
        fecha2 = new org.jdesktop.swingx.JXDatePicker();

        setPreferredSize(new java.awt.Dimension(846, 641));

        tblMotosDatoVehiculo.setModel(modeloMotosDatoVehiculo);
        tblMotosDatoVehiculo.setName("DVehiculoM"); // NOI18N
        jScrollPane1.setViewportView(tblMotosDatoVehiculo);

        jTabbedPane2.addTab("Datos del vehiculo", jScrollPane1);

        tblMotosDatoPrueba.setModel(modeloMotosDatoPrueba);
        jScrollPane2.setViewportView(tblMotosDatoPrueba);

        jTabbedPane2.addTab("Datos de la prueba", jScrollPane2);

        tblMotosResultadoPrueba.setModel(modeloMotosResultadoPrueba);
        jScrollPane3.setViewportView(tblMotosResultadoPrueba);

        jTabbedPane2.addTab("Resultados de la prueba", jScrollPane3);

        tblMotosDatoEquipo.setModel(modeloMotosDatoEquipo);
        jScrollPane7.setViewportView(tblMotosDatoEquipo);

        jTabbedPane2.addTab("Datos del equipo analizador", jScrollPane7);

        jsPestanaMoto.setViewportView(jTabbedPane2);

        jtbPestanas.addTab("Motos", jsPestanaMoto);

        tblOttoDatoVehiculo.setModel(modeloOttoDatoVehiculo);
        jScrollPane4.setViewportView(tblOttoDatoVehiculo);

        jTabbedPane1.addTab("Datos del Vehiculo", jScrollPane4);

        tblOttoDatoPrueba.setModel(modeloOttoDatoPrueba);
        jScrollPane5.setViewportView(tblOttoDatoPrueba);

        jTabbedPane1.addTab("Datos de la prueba", jScrollPane5);

        tblOttoResultadoPrueba.setModel(modeloOttoResultadoPrueba);
        jScrollPane6.setViewportView(tblOttoResultadoPrueba);

        jTabbedPane1.addTab("Resultado de la prueba", jScrollPane6);

        tblOttoDatoEquipo.setModel(modeloOttoDatoEquipo);
        jScrollPane8.setViewportView(tblOttoDatoEquipo);

        jTabbedPane1.addTab("Datos del equipo analizador", jScrollPane8);

        jsPestanaOtto.setViewportView(jTabbedPane1);

        jtbPestanas.addTab("Ciclo Otto", jsPestanaOtto);

        tblDieselDatoVehiculo.setModel(modeloDieselDatoVehiculo);
        jScrollPane9.setViewportView(tblDieselDatoVehiculo);

        jTabbedPane3.addTab("Datos del vehiculo", jScrollPane9);

        tblDieselDatoPrueba.setModel(modeloDieselDatoPrueba);
        jScrollPane10.setViewportView(tblDieselDatoPrueba);

        jTabbedPane3.addTab("Datos de la prueba", jScrollPane10);

        tblDieselResultadoPrueba.setModel(modeloDieselResultadoPrueba);
        jScrollPane11.setViewportView(tblDieselResultadoPrueba);

        jTabbedPane3.addTab("Resultados de la prueba", jScrollPane11);

        tblDieselDatoEquipo.setModel(modeloDieselDatoEquipo);
        jScrollPane12.setViewportView(tblDieselDatoEquipo);

        jTabbedPane3.addTab("Datos del equipo analizador", jScrollPane12);

        jsPestanaDiesel.setViewportView(jTabbedPane3);

        jtbPestanas.addTab("Ciclo Diesel", jsPestanaDiesel);

        btnGenerar.setText("Generar");
        btnGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarActionPerformed(evt);
            }
        });

        btnExportar.setText("Exportar");
        btnExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarActionPerformed(evt);
            }
        });

        jLabel1.setText("Fecha Inicio:");

        jLabel2.setText("Fecha Fin:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fecha1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fecha2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(btnGenerar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(btnExportar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 158, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(progreso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jtbPestanas)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnExportar)
                    .addComponent(btnGenerar)
                    .addComponent(jLabel1)
                    .addComponent(fecha1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(fecha2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progreso, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtbPestanas, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void btnGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarActionPerformed
        if (fecha1.getDate() == null || fecha2.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Seleccione Fechas");
            return;
        }
        SwingUtilities.invokeLater(
                new Runnable() {

                    @Override
                    public void run() {
                        fechaInicio = fecha1.getDate();
                        fechaFin = fecha2.getDate();
                        fillData(fecha1.getDate(), fecha2.getDate());
                        fillDataExcel(fecha1.getDate(), fecha2.getDate());
                    }
                });

    }//GEN-LAST:event_btnGenerarActionPerformed

    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarActionPerformed
        //fillDataExcel(fecha1.getDate(), fecha2.getDate());
        List<JTable> tables = new ArrayList<>();
        tables.add(tablaMotos);
        tables.add(tablaCicloOtto);
        tables.add(tablaCicloDiesel);
        GenericExportExcel excel = new GenericExportExcel();
        excel.exportExcel(tables);
    }//GEN-LAST:event_btnExportarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btnGenerar;
    private org.jdesktop.swingx.JXDatePicker fecha1;
    private org.jdesktop.swingx.JXDatePicker fecha2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JScrollPane jsPestanaDiesel;
    private javax.swing.JScrollPane jsPestanaMoto;
    private javax.swing.JScrollPane jsPestanaOtto;
    private javax.swing.JTabbedPane jtbPestanas;
    private javax.swing.JProgressBar progreso;
    private javax.swing.JTable tblDieselDatoEquipo;
    private javax.swing.JTable tblDieselDatoPrueba;
    private javax.swing.JTable tblDieselDatoVehiculo;
    private javax.swing.JTable tblDieselResultadoPrueba;
    private javax.swing.JTable tblMotosDatoEquipo;
    private javax.swing.JTable tblMotosDatoPrueba;
    private javax.swing.JTable tblMotosDatoVehiculo;
    private javax.swing.JTable tblMotosResultadoPrueba;
    private javax.swing.JTable tblOttoDatoEquipo;
    private javax.swing.JTable tblOttoDatoPrueba;
    private javax.swing.JTable tblOttoDatoVehiculo;
    private javax.swing.JTable tblOttoResultadoPrueba;
    // End of variables declaration//GEN-END:variables

    public void iniciarModeloExcel() {
        this.modeloMotos = new DefaultTableModel();

        this.modeloMotos.addColumn("Numero Certificado");
        this.modeloMotos.addColumn("Marca");
        this.modeloMotos.addColumn("Año Modelo");
        this.modeloMotos.addColumn("Placa");
        this.modeloMotos.addColumn("Cilindraje en CM3");
        this.modeloMotos.addColumn("Tipo Motor");
        this.modeloMotos.addColumn("Diseño");

        this.modeloMotos.addColumn("Realizacion Prueba");
        this.modeloMotos.addColumn("Inspector");
        this.modeloMotos.addColumn("Temperatura °C");
        this.modeloMotos.addColumn("Humedad");
        this.modeloMotos.addColumn("Ciudad");
        this.modeloMotos.addColumn("Direccion");

        this.modeloMotos.addColumn("Temperatura Motor °C");
        this.modeloMotos.addColumn("RPM Ralenti");
        this.modeloMotos.addColumn("HC Ralenti ppm");
        this.modeloMotos.addColumn("CO Ralenti %");
        this.modeloMotos.addColumn("CO2 Ralenti %");
        this.modeloMotos.addColumn("O2 Ralenti %");
        this.modeloMotos.addColumn("Presencia Dilucion");
        this.modeloMotos.addColumn("Concepto Final");

        this.modeloMotos.addColumn("Valor PEF");
        this.modeloMotos.addColumn("Marca Analizador");
        this.modeloMotos.addColumn("No. Serie Analizador");
        this.modeloMotos.addColumn("Nombre Software");
        this.modeloMotos.addColumn("Version Software");
        this.modeloMotos.addColumn("SPAN HC baja PPM");
        this.modeloMotos.addColumn("SPAN CO Baja %");
        this.modeloMotos.addColumn("SPAN CO2 Baja %");
        this.modeloMotos.addColumn("Valor Leido HC Baja PPM");
        this.modeloMotos.addColumn("Valor Leido CO Baja %");
        this.modeloMotos.addColumn("Valor Leido CO2 Baja %");
        this.modeloMotos.addColumn("SPAN HC Alta PPM ");
        this.modeloMotos.addColumn("SPAN CO Alta %");
        this.modeloMotos.addColumn("SPAN CO2 Alta %");
        this.modeloMotos.addColumn("Valor Leido HC Alta PPM");
        this.modeloMotos.addColumn("Valor Leido CO Alta %");
        this.modeloMotos.addColumn("Valor Leido CO2 Alta %");
        this.modeloMotos.addColumn("Fecha Verificacion");
        this.modeloMotos.addColumn("Resultado Verificacion");
        ///////////////////////////////////MODELO CICLO OTTO///////////////////////////////////////       
        this.modeloCicloOtto = new DefaultTableModel();

        this.modeloCicloOtto.addColumn("Numero Certificado");
        this.modeloCicloOtto.addColumn("Marca");
        this.modeloCicloOtto.addColumn("Año Modelo");
        this.modeloCicloOtto.addColumn("Placa");
        this.modeloCicloOtto.addColumn("Cilindraje en CM3");
        this.modeloCicloOtto.addColumn("Clase");
        this.modeloCicloOtto.addColumn("Servicio");
        this.modeloCicloOtto.addColumn("Combustible");

        this.modeloCicloOtto.addColumn("Realizacion Prueba");
        this.modeloCicloOtto.addColumn("Inspector");
        this.modeloCicloOtto.addColumn("Temperatura °C");
        this.modeloCicloOtto.addColumn("Humedad Relativa %");
        this.modeloCicloOtto.addColumn("Ciudad");
        this.modeloCicloOtto.addColumn("Direccion");

        this.modeloCicloOtto.addColumn("Temperatura Motor °C");
        this.modeloCicloOtto.addColumn("RPM Ralenti");
        this.modeloCicloOtto.addColumn("HC Ralenti ppm");
        this.modeloCicloOtto.addColumn("CO Ralenti %");
        this.modeloCicloOtto.addColumn("CO2 Ralenti %");
        this.modeloCicloOtto.addColumn("O2 Ralenti %");
        this.modeloCicloOtto.addColumn("RPM Crucero");
        this.modeloCicloOtto.addColumn("HC Crucero PPM");
        this.modeloCicloOtto.addColumn("CO Crucero %");
        this.modeloCicloOtto.addColumn("CO2 Crucero %");
        this.modeloCicloOtto.addColumn("O2 Crucero %");
        this.modeloCicloOtto.addColumn("Presencia Dilucion");
        this.modeloCicloOtto.addColumn("Concepto Final");

        this.modeloCicloOtto.addColumn("Valor PEF");
        this.modeloCicloOtto.addColumn("Marca Analizador");
        this.modeloCicloOtto.addColumn("No. Serie Analizador");
        this.modeloCicloOtto.addColumn("Nombre Software");
        this.modeloCicloOtto.addColumn("Version Software");
        this.modeloCicloOtto.addColumn("SPAN HC baja PPM");
        this.modeloCicloOtto.addColumn("SPAN CO Baja %");
        this.modeloCicloOtto.addColumn("SPAN CO2 Baja %");
        this.modeloCicloOtto.addColumn("Valor Leido HC Baja PPM");
        this.modeloCicloOtto.addColumn("Valor Leido CO Baja %");
        this.modeloCicloOtto.addColumn("Valor Leido CO2 Baja %");
        this.modeloCicloOtto.addColumn("SPAN HC Alta PPM ");
        this.modeloCicloOtto.addColumn("SPAN CO Alta %");
        this.modeloCicloOtto.addColumn("SPAN CO2 Alta %");
        this.modeloCicloOtto.addColumn("Valor Leido HC Alta PPM");
        this.modeloCicloOtto.addColumn("Valor Leido CO Alta %");
        this.modeloCicloOtto.addColumn("Valor Leido CO2 Alta %");
        this.modeloCicloOtto.addColumn("Fecha Verificacion");
        this.modeloCicloOtto.addColumn("Resultado Verificacion");
        ////////////////////////////MODELO CICLO DIESEL/////////////////////////////////////////
        this.modeloCicloDiesel = new DefaultTableModel();

        this.modeloCicloDiesel.addColumn("Numero Certificado");
        this.modeloCicloDiesel.addColumn("Marca");
        this.modeloCicloDiesel.addColumn("Año Modelo");
        this.modeloCicloDiesel.addColumn("Placa");
        this.modeloCicloDiesel.addColumn("Cilindraje en CM3");
        this.modeloCicloDiesel.addColumn("Clase");
        this.modeloCicloDiesel.addColumn("Servicio");
        this.modeloCicloDiesel.addColumn("Modificaciones Motor");

        this.modeloCicloDiesel.addColumn("Realizacion Prueba");
        this.modeloCicloDiesel.addColumn("Inspector");
        this.modeloCicloDiesel.addColumn("Temperatura °C");
        this.modeloCicloDiesel.addColumn("Humedad");
        this.modeloCicloDiesel.addColumn("Ciudad");
        this.modeloCicloDiesel.addColumn("Direccion");

        this.modeloCicloDiesel.addColumn("RPM Gobernada Medida");
        this.modeloCicloDiesel.addColumn("Temperatura Inicial Motor °C");
        this.modeloCicloDiesel.addColumn("RPM Ralenti");
        this.modeloCicloDiesel.addColumn("Resultado Inicial Opacidad CP %");
        this.modeloCicloDiesel.addColumn("RPM Gobernada CP ");
        this.modeloCicloDiesel.addColumn("Resultado Opacidad 1C %");
        this.modeloCicloDiesel.addColumn("RPM 1C Gobernada");
        this.modeloCicloDiesel.addColumn("Resultado Opacidad 2C %");
        this.modeloCicloDiesel.addColumn("RPM 2C Gobernada");
        this.modeloCicloDiesel.addColumn("Resultado Opacidad 3C %");
        this.modeloCicloDiesel.addColumn("RPM 3C Gobernada");
        this.modeloCicloDiesel.addColumn("Diferencias Aritmeticas %");
        this.modeloCicloDiesel.addColumn("Resultado Opacidad Final");
        this.modeloCicloDiesel.addColumn("Temperatura Final Motor °C");
        this.modeloCicloDiesel.addColumn("Diametro Tubo Escape mm");
        this.modeloCicloDiesel.addColumn("Concepto Final");

        this.modeloCicloDiesel.addColumn("LTOE M");
        this.modeloCicloDiesel.addColumn("Marca Analizador");
        this.modeloCicloDiesel.addColumn("No. Serie Analizador");
        this.modeloCicloDiesel.addColumn("Nombre Software");
        this.modeloCicloDiesel.addColumn("Version Software");
        this.modeloCicloDiesel.addColumn("Valor Referencia Filtro 1 %");
        this.modeloCicloDiesel.addColumn("Valor Leido Filtro 1 %");
        this.modeloCicloDiesel.addColumn("Valor Referencia Filtro 2 %");
        this.modeloCicloDiesel.addColumn("Valor Leido Filtro 2 %");
        this.modeloCicloDiesel.addColumn("Resultado Verificacion");
        this.modeloCicloDiesel.addColumn("Fecha Verificacion");
    }

    private void fillDataExcel(Date fechaInicial, Date fechaFinal) {
        iniciarModeloExcel();

        sdf = new SimpleDateFormat("dd/MM/yyyy");
        HojaPruebasJpaController hpjc = new HojaPruebasJpaController();
        List<HojaPruebas> hojasDePruebas = hpjc.findByDate(fechaInicial, fechaFinal);

        if (hojasDePruebas == null || hojasDePruebas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La consulta no genero datos");
            return;
        }

        SoftwareJpaController swJpa = new SoftwareJpaController();
        Software sw = swJpa.findSoftware(1);
        CdaJpaController cdaJpa = new CdaJpaController();
        Cda cda = cdaJpa.findCda(1);
        CiudadesJpaController CiuJpa = new CiudadesJpaController();

        List<CalibracionDosPuntos> calibracionesDosPuntos = new CalibracionDosPuntosJpaController().findCalibracionDosPuntosByFecha(this.fechaInicio, this.fechaFin);

        for (HojaPruebas hojaPruebas : hojasDePruebas) {

            CertificadosJpaController cjc = new CertificadosJpaController();
            Certificados ct = cjc.findCertificadoHojaPrueba(hojaPruebas.getTestsheet());

            cargarInformacionMotoExcel(hojaPruebas, cda, sw, cda.getCiudad(), calibracionesDosPuntos, ct);
            cargarInformacionOttoExcel(hojaPruebas, cda, sw, cda.getCiudad(), calibracionesDosPuntos, ct);
            cargarInformacionDieselExcel(hojaPruebas, cda, sw, cda.getCiudad(), calibracionesDosPuntos, ct);

        }

        this.tablaMotos.setModel(this.modeloMotos);
        this.tablaCicloOtto.setModel(this.modeloCicloOtto);
        this.tablaCicloDiesel.setModel(this.modeloCicloDiesel);

    }

    private void cargarInformacionMotoExcel(HojaPruebas hojaPruebas, Cda cda, Software sw, String ciu, List<CalibracionDosPuntos> calibracionesDosPuntos, Certificados ct) {
        Object[] filaMotos = new Object[50]; // Hay 11 columnas en la tabla
        Vehiculos vehiculo = hojaPruebas.getVehiculos();

        for (Pruebas pru : hojaPruebas.getPruebasCollection()) {
            if (vehiculo.getTipoVehiculo().getCartype() == 4 && pru.getTipoPrueba().getTesttype() == 8) {

                ///////////////////////Datos del vehiculo///////////////////// 
                if (vehiculo.getCarplate().equalsIgnoreCase("MCS323") || vehiculo.getCarplate().equalsIgnoreCase("PXE28B")) {
                    int e = 0;
                }
                filaMotos[0] = ct.getConsecutive();
                filaMotos[1] = vehiculo.getMarcas().getNombremarca();
                filaMotos[2] = vehiculo.getModelo();
                filaMotos[3] = vehiculo.getCarplate();
                filaMotos[4] = vehiculo.getCilindraje();
                filaMotos[5] = vehiculo.getTiemposmotor();
                filaMotos[6] = vehiculo.getDiseno();

                SimpleDateFormat sdm = new SimpleDateFormat("yyyy/MM/dd");
                String fecha = sdm.format(pru.getFechaPrueba());

                filaMotos[7] = fecha;//Fecha prueba
                filaMotos[8] = pru.getUsuarios().getNombreusuario();

                List<Medidas> medidas = pru.getMedidasList();

                for (Medidas med : medidas) {

                    switch (med.getTiposMedida().getMeasuretype()) {
                        case 8031:                                      //Temperatura ambiente
                            filaMotos[9] = med.getValormedida();
                            break;
                        case 8032:                                      //Humedad Relativa
                            filaMotos[10] = med.getValormedida();
                            break;
                    }
                }

                filaMotos[11] = hojaPruebas.getUbicacionMunicipio();
                filaMotos[12] = cda.getDireccion();
float o2Bd=0;
                for (Medidas med2 : medidas) {
                    switch (med2.getTiposMedida().getMeasuretype()) {
                        case 8006:                                  //Temp. Motor
                            filaMotos[13] = med2.getValormedida();
                            break;
                        case 8022:                                  //Temp. Motor
                            filaMotos[13] = med2.getValormedida();
                            break;
                        case 8027:                                  //Temp. Motor
                            filaMotos[13] = med2.getValormedida();
                            break;
                        case 8005:                                  //RPM Ralenti
                            filaMotos[14] = med2.getValormedida();
                            break;
                        case 8001:                                  //HC Ralenti ppm
                            filaMotos[15] = med2.getValormedida();
                            break;
                        case 8002:                                  //CO ralenti
                            filaMotos[16] = med2.getValormedida();
                            break;
                        case 8003:                                  //CO2 ralenti
                            filaMotos[17] = med2.getValormedida();
                            break;
                        case 8004:                                  //O2 ralenti
                            filaMotos[18] = med2.getValormedida();
                             o2Bd =med2.getValormedida();
                            break;
                        case 80000:                                  //Presencia Dilucion
                            filaMotos[19] = med2.getValormedida();
                            break;
                        case 80001:                                  //Concepto Final
                            filaMotos[20] = "";
                            break;
                            
                        case 8028:                                  //RPM Ralenti 2T
                                filaMotos[14] = med2.getValormedida();
                                break;    
                              case 8018: //HidroCarburos en ralenty (HCR) Para Motos 2t
                                filaMotos[15] = med2.getValormedida();
                                break;
                        case 8020: //Monoxido de carbono en crucero (COC) PARA MOTOS 2T
                               filaMotos[16] = med2.getValormedida();
                                break;                               
                        case 8019: //Dioxido de carbono en crucero (CO2C) para Motos 2t
                                filaMotos[17]= med2.getValormedida();
                                break;
                        case 8021: //Oxigeno en ralenty (O2R) PARA MOTO 2T
                               filaMotos[18] = med2.getValormedida();
                                o2Bd =med2.getValormedida();
                                break;                    
                    }
                }
                if (pru.getAprobada().equalsIgnoreCase("Y")) {
                    filaMotos[20] = "Aprobada";
                } else {
                    filaMotos[20] = "ReAprobada";
                }
                float oxigenoCorregir = 0;
                if (vehiculo.getTiemposmotor() == 4) {
                    oxigenoCorregir = 6;
                }
                if (vehiculo.getTiemposmotor() == 2 && vehiculo.getModelo() >= 2010) {
                    oxigenoCorregir = 6;
                }
                if (vehiculo.getTiemposmotor() == 2 && vehiculo.getModelo() < 2010) {
                    oxigenoCorregir = 11;
                }

                if (oxigenoCorregir <= o2Bd) {
                    filaMotos[19] = "SI";//Presencia Dilucion
                } else {
                    filaMotos[19] = "NO";//Presencia Dilucion
                }  
                //////////////////////////Datos equipo analizador/////////////////////
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String fec = sf.format(pru.getFechaPrueba());//PARAMETRO 1

                String serEquipo = pru.getSerialEquipo();
                EquiposJpaController eqJpa = new EquiposJpaController();
                Integer idEquipo = eqJpa.buscarEquiposBySerialRes(serEquipo);//PARAMETRO 2

                CalibracionesJpaController caJpa = new CalibracionesJpaController();
                List<Calibraciones> listCalib = caJpa.findCalibracionFechaEquipo(fec, idEquipo,2);

                SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                Date fechaNue;
                Date fechaAnt = new Date(80, 01, 01, 00, 00, 00);
                Calibraciones c = null;

                for (Calibraciones cal : listCalib) {
                    if (cal.getTipoCalibracion().getIdTipoCalibracion() == 2) {
                        fechaNue = cal.getFecha();

                        if (fechaNue.after(fechaAnt)) {
                            fechaAnt = fechaNue;
                            c = cal;
                            Integer i = c.getCalibration();
                        } else {

                        }
                    }
                }

                if (c != null) {
                    Integer idCalib = c.getCalibration();
                    CalibracionDosPuntosJpaController ca2Jpa = new CalibracionDosPuntosJpaController();
                    CalibracionDosPuntos ca2 = ca2Jpa.findCalibracionDosPuntos(idCalib);
                    if (pru.getAprobada().equalsIgnoreCase("Y")) {
                        filaMotos[20] = "Aprobada";
                    } else {
                        filaMotos[20] = "Reprobada";
                    }
                    if (ca2 != null) {
                        filaMotos[21] = ca2.getEquipo().getPef();//Valor PEF
                        filaMotos[22] = ca2.getEquipo().getMarca();//Marca Analizador
                        filaMotos[23] = ca2.getEquipo().getReolucionserial();//Numero de serie analizador
                        filaMotos[24] = sw.getNombre();//Nombre del software
                        filaMotos[25] = sw.getVersion();//Version del software
                        filaMotos[26] = ca2.getBmHc();//SPAN HC Baja ppm
                        filaMotos[27] = ca2.getBmCo();//SPAN CO Baja %
                        filaMotos[28] = ca2.getBmCo2();//SPAN CO2 Baja %
                        filaMotos[29] = ca2.getBancoBmHc();//Valor leido HC Baja ppm
                        filaMotos[30] = ca2.getBancoBmCo();//Valor leido CO Baja %
                        filaMotos[31] = ca2.getBancoBmCo2();//Valor leido CO2 Baja %
                        filaMotos[32] = ca2.getAltaHc();//SPAN HC Alta ppm
                        filaMotos[33] = ca2.getAltaCo();//SPAN CO Alta %
                        filaMotos[34] = ca2.getAltaCo2();//SPAN CO2 Alta %
                        filaMotos[35] = ca2.getBancoAltaHc();//Valor leido HC Alta ppm
                        filaMotos[36] = ca2.getBancoAltaCo();//Valor leido CO Alta %
                        filaMotos[37] = ca2.getBancoAltaCo2();//Valor leido CO2 Alta %

                        String fecha2 = sdm.format(ca2.getFecha());

                        filaMotos[38] = fecha2;//Fecha calibracion
                        if (ca2.isAprobada() == true) {
                            filaMotos[39] = "Aprobada";
                        } else {
                            filaMotos[39] = "Reprobada";
                        }

                    } else {
                        filaMotos[21] = "";//Valor PEF
                        filaMotos[22] = "";//Marca Analizador
                        filaMotos[23] = "";//Numero de serie analizador
                        filaMotos[24] = "";//Nombre del software
                        filaMotos[25] = "";//Version del software
                        filaMotos[26] = "";//SPAN HC Baja ppm
                        filaMotos[27] = "";//SPAN CO Baja %
                        filaMotos[28] = "";//SPAN CO2 Baja %
                        filaMotos[29] = "";//Valor leido HC Baja ppm
                        filaMotos[30] = "";//Valor leido CO Baja %
                        filaMotos[31] = "";//Valor leido CO2 Baja %
                        filaMotos[32] = "";//SPAN HC Alta ppm
                        filaMotos[33] = "";//SPAN CO Alta %
                        filaMotos[34] = "";//SPAN CO2 Alta %
                        filaMotos[35] = "";//Valor leido HC Alta ppm
                        filaMotos[36] = "";//Valor leido CO Alta %
                        filaMotos[37] = "";//Valor leido CO2 Alta %

                        //String fecha2 = sdm.format(calibracionDosPuntos.getFecha());
                        filaMotos[38] = "";//Fecha calibracion
                        filaMotos[39] = "";
                    }

                } else {
                    filaMotos[21] = "";//Valor PEF
                    filaMotos[22] = "";//Marca Analizador
                    filaMotos[23] = "";//Numero de serie analizador
                    filaMotos[24] = "";//Nombre del software
                    filaMotos[25] = "";//Version del software
                    filaMotos[26] = "";//SPAN HC Baja ppm
                    filaMotos[27] = "";//SPAN CO Baja %
                    filaMotos[28] = "";//SPAN CO2 Baja %
                    filaMotos[29] = "";//Valor leido HC Baja ppm
                    filaMotos[30] = "";//Valor leido CO Baja %
                    filaMotos[31] = "";//Valor leido CO2 Baja %
                    filaMotos[32] = "";//SPAN HC Alta ppm
                    filaMotos[33] = "";//SPAN CO Alta %
                    filaMotos[34] = "";//SPAN CO2 Alta %
                    filaMotos[35] = "";//Valor leido HC Alta ppm
                    filaMotos[36] = "";//Valor leido CO Alta %
                    filaMotos[37] = "";//Valor leido CO2 Alta %

                    //String fecha2 = sdm.format(calibracionDosPuntos.getFecha());
                    filaMotos[38] = "";//Fecha calibracion
                    filaMotos[39] = "";
                }
                this.modeloMotos.addRow(filaMotos);
            }
        }
    }

    private void cargarInformacionOttoExcel(HojaPruebas hojaPruebas, Cda cda, Software sw, String ciu, List<CalibracionDosPuntos> calibracionesDosPuntos, Certificados ct) {
        Object[] filaOtto = new Object[50];
        Vehiculos vehiculo = hojaPruebas.getVehiculos();

        for (Pruebas pruebas : hojaPruebas.getPruebasCollection()) {
            if ((vehiculo.getTipoVehiculo().getCartype() != 4)) {
                if (pruebas.getTipoPrueba().getTesttype() == 8 && pruebas.getHojaPruebas().getVehiculos().getTiposGasolina().getFueltype() != 3) {
                    filaOtto[0] = ct.getConsecutive();
                    filaOtto[1] = vehiculo.getMarcas().getNombremarca();
                    filaOtto[2] = vehiculo.getModelo();
                    filaOtto[3] = vehiculo.getCarplate();
                    filaOtto[4] = vehiculo.getCilindraje();
                    filaOtto[5] = vehiculo.getClasesVehiculo().getNombreclase();
                    filaOtto[6] = vehiculo.getServicios().getNombreservicio();
                    filaOtto[7] = vehiculo.getTiposGasolina().getNombregasolina();

                    SimpleDateFormat sdm = new SimpleDateFormat("yyyy/MM/dd");
                    String fecha = sdm.format(pruebas.getFechaPrueba());

                    filaOtto[8] = fecha;//Fecha prueba
                    filaOtto[9] = pruebas.getUsuarios().getNombreusuario();

                    List<Medidas> medidas = pruebas.getMedidasList();

                    for (Medidas med : medidas) {

                        switch (med.getTiposMedida().getMeasuretype()) {
                            case 8031:                                      //Temperatura ambiente
                                filaOtto[10] = med.getValormedida();
                                break;
                            case 8032:                                      //Humedad Relativa
                                filaOtto[11] = med.getValormedida();
                                break;
                        }
                    }

                    filaOtto[12] = ciu;
                    filaOtto[13] = cda.getDireccion();

                    for (Medidas med : medidas) {
                        switch (med.getTiposMedida().getMeasuretype()) {
                            case 8006:                                  //Temp. Motor
                                filaOtto[14] = med.getValormedida();
                                break;
                            case 8012:                                  //Temp. Motor
                                filaOtto[14] = med.getValormedida();
                                break;
                            case 8005:                                  //RPM Ralenti
                                filaOtto[15] = med.getValormedida();
                                break;
                            case 8001:                                  //HC Ralenti PPM
                                filaOtto[16] = med.getValormedida();
                                break;
                            case 8002:                                  //CO ralenti
                                filaOtto[17] = med.getValormedida();
                                break;
                            case 8003:                                  //CO2 ralenti
                                filaOtto[18] = med.getValormedida();
                                break;
                            case 8004:                                  //O2 ralenti
                                filaOtto[19] = med.getValormedida();
                                break;
                            case 8011:                                  //RPM Crucero
                                filaOtto[20] = med.getValormedida();
                                break;
                            case 8007:                                  //HC Crucero PPM
                                filaOtto[21] = med.getValormedida();
                                break;
                            case 8008:                                  //CO Crucero %
                                filaOtto[22] = med.getValormedida();
                                break;
                            case 8009:                                  //CO2 Crucero %
                                filaOtto[23] = med.getValormedida();
                                break;
                            case 8010:                                  //O2 Crucero %
                                filaOtto[24] = med.getValormedida();
                                break;
                            case 80000:                                  //Presencia Dilucion
                                filaOtto[25] = med.getValormedida();
                                break;
                            case 80001:                                  //Concepto Final
                                filaOtto[26] = "";
                                break;
                        }
                    }
                    if (pruebas.getAprobada().equalsIgnoreCase("Y")) {
                        filaOtto[26] = "Aprobada";
                    } else {
                        filaOtto[26] = "Reprobada";
                    }

                    for (CalibracionDosPuntos calibracionDosPuntos : calibracionesDosPuntos) {
                        filaOtto[27] = calibracionDosPuntos.getEquipo().getPef();//Valor PEF
                        filaOtto[28] = calibracionDosPuntos.getEquipo().getMarca();//Marca Analizador
                        filaOtto[29] = calibracionDosPuntos.getEquipo().getReolucionserial();//Numero de serie analizador
                        filaOtto[30] = sw.getNombre();//Nombre del software
                        filaOtto[31] = sw.getVersion();//Version del software
                        filaOtto[32] = calibracionDosPuntos.getBmHc();//SPAN HC Baja ppm
                        filaOtto[33] = calibracionDosPuntos.getBmCo();//SPAN CO Baja %
                        filaOtto[34] = calibracionDosPuntos.getBmCo2();//SPAN CO2 Baja %
                        filaOtto[35] = calibracionDosPuntos.getBancoBmHc();//Valor leido HC Baja ppm
                        filaOtto[36] = calibracionDosPuntos.getBancoBmCo();//Valor leido CO Baja %
                        filaOtto[37] = calibracionDosPuntos.getBancoBmCo2();//Valor leido CO2 Baja %
                        filaOtto[38] = calibracionDosPuntos.getAltaHc();//SPAN HC Alta ppm
                        filaOtto[39] = calibracionDosPuntos.getAltaCo();//SPAN CO Alta %
                        filaOtto[40] = calibracionDosPuntos.getAltaCo2();//SPAN CO2 Alta %
                        filaOtto[41] = calibracionDosPuntos.getBancoAltaHc();//Valor leido HC Alta ppm
                        filaOtto[42] = calibracionDosPuntos.getBancoAltaCo();//Valor leido CO Alta %
                        filaOtto[43] = calibracionDosPuntos.getBancoAltaCo2();//Valor leido CO2 Alta %

                        String fecha2 = sdm.format(calibracionDosPuntos.getFecha());
                        filaOtto[44] = fecha2;//Fecha verificacion
                        if (calibracionDosPuntos.isAprobada() == true) {
                            filaOtto[45] = "Aprobada";//Resultado Final
                        } else {
                            filaOtto[45] = "Reprobada";//Resultado Final
                        }
                    }
                    this.modeloCicloOtto.addRow(filaOtto);
                }
            }
        }
    }

    private void cargarInformacionDieselExcel(HojaPruebas hojaPruebas, Cda cda, Software sw, String ciu, List<CalibracionDosPuntos> calibracionesDosPuntos, Certificados ct) {
        Object[] filaDiesel = new Object[50];
        Vehiculos vehiculo = hojaPruebas.getVehiculos();

        for (Pruebas pruebas : hojaPruebas.getPruebasCollection()) {
            if (vehiculo.getTiposGasolina().getFueltype() == 3 && pruebas.getTipoPrueba().getTesttype() == 8) {
                filaDiesel[0] = ct.getConsecutive();
                filaDiesel[1] = vehiculo.getMarcas().getNombremarca();
                filaDiesel[2] = vehiculo.getModelo();
                filaDiesel[3] = vehiculo.getCarplate();
                filaDiesel[4] = vehiculo.getCilindraje();
                filaDiesel[5] = vehiculo.getClasesVehiculo().getNombreclase();
                filaDiesel[6] = vehiculo.getServicios().getNombreservicio();
                filaDiesel[7] = vehiculo.getNumeromotor();
                SimpleDateFormat sdm = new SimpleDateFormat("yyyy/MM/dd");
                String fecha = sdm.format(pruebas.getFechaPrueba());
                filaDiesel[8] = fecha;//Fecha prueba
                filaDiesel[9] = pruebas.getUsuarios().getNombreusuario();

                List<Medidas> medidas = pruebas.getMedidasList();

                for (Medidas med : medidas) {

                    switch (med.getTiposMedida().getMeasuretype()) {
                        case 8031:                                      //Temperatura ambiente
                            filaDiesel[10] = med.getValormedida();
                            break;
                        case 8032:                                      //Humedad Relativa
                            filaDiesel[11] = med.getValormedida();
                            break;
                    }
                }

                filaDiesel[12] = ciu;
                filaDiesel[13] = cda.getDireccion();
                for (Medidas med : medidas) {
                    switch (med.getTiposMedida().getMeasuretype()) {
                        case 0000: //RPM Gobernada
                            filaDiesel[14] = med.getValormedida();
                            break;
                        case 8034: //Temperatura del motor prueba Diesel (TEMPMOTORDIESEL)
                            filaDiesel[15] = med.getValormedida();
                            break;
                        case 8005: //RPM Ralenti
                            filaDiesel[16] = med.getValormedida();
                            break;
                        case 8033: //Ciclo preliminar para el opacimetro (CICLO1OP)
                            filaDiesel[17] = med.getValormedida();
                            break;
                        case 0001: //RPM Gobernada en ciclo preliminar
                            filaDiesel[18] = med.getValormedida();
                            break;
                        case 8013: //Primer ciclo para el opacimetro (CICLO1OP)
                            filaDiesel[19] = med.getValormedida();
                            break;
                        case 0002: //RPM Gobernada en primer ciclo
                            filaDiesel[20] = med.getValormedida();
                            break;
                        case 8014: //Segundo ciclo para el opacimetro (CICLO2OP)
                            filaDiesel[21] = med.getValormedida();
                            break;
                        case 0003: //RPM Gobernada en segundo ciclo
                            filaDiesel[22] = med.getValormedida();
                            break;
                        case 8015: //Tercer ciclo para el opacimetro (CICLO3OP)
                            filaDiesel[23] = med.getValormedida();
                            break;
                        case 0004: //RPM Gobernada en tercer ciclo
                            filaDiesel[24] = med.getValormedida();
                            break;
                        case 0005: //Diferencias aritmeticas
                            filaDiesel[25] = med.getValormedida();
                            break;
                        case 8017: //Resultado opacidad final
                            filaDiesel[26] = med.getValormedida();
                            break;
                        case 0006: //Temperatura final de motor
                            filaDiesel[27] = med.getValormedida();
                            break;
                    }
                }
                filaDiesel[28] = vehiculo.getDiametro();//Diametro del tubo de escape               
                if (pruebas.getAprobada().equalsIgnoreCase("Y")) {
                    filaDiesel[29] = "Aprobada";
                } else {
                    filaDiesel[29] = "Reprobada";
                }
                for (CalibracionDosPuntos calibracionDosPuntos : calibracionesDosPuntos) {
                    filaDiesel[30] = "";
                    filaDiesel[31] = calibracionDosPuntos.getEquipo().getMarca();//Marca Analizador
                    filaDiesel[32] = calibracionDosPuntos.getEquipo().getReolucionserial();//Numero de serie analizador
                    filaDiesel[33] = sw.getNombre();//Nombre del software
                    filaDiesel[34] = sw.getVersion();//Version software
                    filaDiesel[35] = "0";//Valor referencia filtro 1
                    filaDiesel[36] = "36";//Valor leido filtro 1
                    filaDiesel[37] = "76";//Valor referencia filtro 2
                    filaDiesel[38] = "100";//Valor leido filtro 2

                    String fecha2 = sdm.format(calibracionDosPuntos.getFecha());//Fecha verificacion                    
                    filaDiesel[39] = fecha2;
                    if (calibracionDosPuntos.isAprobada() == true) {
                        filaDiesel[40] = "Aprobada";
                    } else {
                        filaDiesel[40] = "Reprobada";
                    }

                }

                this.modeloCicloDiesel.addRow(filaDiesel);
            }
        }
    }

}
