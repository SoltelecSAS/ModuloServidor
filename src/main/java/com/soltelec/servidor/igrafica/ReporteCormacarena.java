
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.igrafica;

import com.soltelec.servidor.consultas.Reportes;
import com.soltelec.servidor.dao.PruebasJpaController;
import com.soltelec.servidor.dtos.reporte_cormacarena.CormacarenaCarros;
import com.soltelec.servidor.dtos.reporte_cormacarena.CormacarenaMotos;
import com.soltelec.servidor.dtos.reporte_dagma.Dagma;
import com.soltelec.servidor.model.HojaPruebas;
import com.soltelec.servidor.model.Medidas;
import com.soltelec.servidor.model.Pruebas;
import com.soltelec.servidor.model.Vehiculos;
import com.soltelec.servidor.utils.GenericExportExcel;
import com.soltelec.servidor.utils.JTitlePanel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class ReporteCormacarena extends javax.swing.JInternalFrame {

    JFileChooser fc;
    JPanel componentPanel;
    JTitlePanel componentTitlePanel;
    private DefaultTableModel modeloGasolina;
    private DefaultTableModel modeloDiesel;
    private DefaultTableModel modeloMotos;
    private Object nivel_ruido = "----";
    private Object veh[] = {"----", "", ""};

    /**
     * Creates new form ReportGeneral
     */
    public ReporteCormacarena() {
        super("Reporte Gases y Motos",
                true, //resizable
                true, //closable
                false, //maximizable
                true);
        iniciarModelo();
        initComponents();
    }

    private void iniciarModelo() {

        modeloGasolina = new DefaultTableModel();

//      ******MODELO GASOLINA****** 
        modeloGasolina.addColumn("Marca");
        modeloGasolina.addColumn("Año Modelo");
        modeloGasolina.addColumn("Cilindraje");
        modeloGasolina.addColumn("Clase");
        modeloGasolina.addColumn("Servicio");
        modeloGasolina.addColumn("HC Ralenti");
        modeloGasolina.addColumn("CO Ralenti");
        modeloGasolina.addColumn("CO2 Ralenti");
        modeloGasolina.addColumn("O2 Ralenti");
        modeloGasolina.addColumn("RPM Crucero");
        modeloGasolina.addColumn("HC Crucero");
        modeloGasolina.addColumn("CO Crucero");
        modeloGasolina.addColumn("CO2 Crucero");
        modeloGasolina.addColumn("O2 Crucero");
        modeloGasolina.addColumn("Resultado de la Prueba");
        modeloGasolina.addColumn("Nivel de Emision de Ruido");
        modeloGasolina.addColumn("Fecha Prueba");
        modeloGasolina.addColumn("Placa");

//     ******MODELO DIESEL******          
        modeloDiesel = new DefaultTableModel();
        modeloDiesel.addColumn("Marca");
        modeloDiesel.addColumn("Año Modelo");
        modeloDiesel.addColumn("Cilindraje");
        modeloDiesel.addColumn("Clase");
        modeloDiesel.addColumn("Servicio");
        modeloDiesel.addColumn("Primer Ciclo");
        modeloDiesel.addColumn("Segundo Ciclo");
        modeloDiesel.addColumn("Tercer Ciclo");
        modeloDiesel.addColumn("Resultado Final");
        modeloDiesel.addColumn("Resultado de la prueba");
        modeloDiesel.addColumn("Nivel de Emision de Ruido");
        modeloDiesel.addColumn("Fecha Prueba");
        modeloDiesel.addColumn("Placa");
//    ******MODELO MOTOS******   

        modeloMotos = new DefaultTableModel();
        modeloMotos.addColumn("Marca");
        modeloMotos.addColumn("Año Modelo");
        modeloMotos.addColumn("Tiempos");
        modeloMotos.addColumn("CO Ralenti");
        modeloMotos.addColumn("CO2 Ralenti");
        modeloMotos.addColumn("O2 Ralenti");
        modeloMotos.addColumn("HC Ralenti");
        modeloMotos.addColumn("Temperatura Ambiente");
        modeloMotos.addColumn("Humedad Relativa Ambiente");
        modeloMotos.addColumn("Resultado");
        modeloMotos.addColumn("Nivel de emision de ruido");
        modeloMotos.addColumn("Fecha Prueba");
        modeloMotos.addColumn("Placa");

    }

    private void fillData(Date fInicial, Date fFinal) {
        List<CormacarenaMotos> listaDatos = Reportes.getCormacarenaMotos(fInicial, fFinal);
        //List<CormacarenaCarros> lista

        listaDatos.stream().forEach(datos->{
            Object [] fila ={
                datos.getMarca(),
                datos.getModelo(),
                datos.getTiempos(),
                datos.getCoRalenti(),
                datos.getCo2Ralenti(),
                datos.getO2Ralenti(),
                datos.getHcRalenti(),
                datos.getTempAmbiente(),
                datos.getHumedadRelativa(),
                datos.getResultado(),
                datos.getNivelDeEmisionRuido(),
                datos.getFechaPrueba(),
                datos.getPlaca()
            };
            modeloMotos.addRow(fila);
        });

        tblMotos.setModel(modeloMotos);
        tblMotos.setEnabled(false);
    }

    private void fillDataa(Date fInicial, Date fFinal) {
        iniciarModelo();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

        //--
        System.out.println("---- En fillData ---");
        //--

        //****LLENAR DATOS****
        PruebasJpaController pruebasJpa = new PruebasJpaController();
        List<Pruebas> pruebas = pruebasJpa.findPruebasByFechaGDM(fInicial, fFinal);
        //List<HojaPruebas> hpbyfecha = pruebasJpa.findHP_porFecha(fInicial, fFinal);

        Object[] filaGasolina = new Object[18]; // Hay 16 columnas en la tabla
        Object[] filaDiesel = new Object[13]; // Hay 11 columnas en la tabla
        Object[] filaMotos = new Object[13]; // Hay 11 columnas en la tabla

        for (Pruebas p : pruebas) {
            HojaPruebas hojaPruebas = p.getHojaPruebas();
            Vehiculos vehiculo = hojaPruebas.getVehiculos();
            List<Medidas> medidas = p.getMedidasList();
            //Si la prueba es de Gases o Ruido
            if ((vehiculo.getTiposGasolina().getFueltype() == 1) && (vehiculo.getTipoVehiculo().getCartype() != 4)) {//fueltype = gasolina y cartype diferente de moto
                // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
                filaGasolina[0] = vehiculo.getMarcas().getNombremarca();            //Marca del vehiculo
                filaGasolina[1] = vehiculo.getModelo();                             //Modelo
                filaGasolina[2] = vehiculo.getCilindraje();                        //Cilindraje
                filaGasolina[3] = vehiculo.getClasesVehiculo().getNombreclase();    //Clase
                filaGasolina[4] = vehiculo.getServicios().getNombreservicio();      //Servicio
                filaGasolina[5] = "----";                                           // HC Ralenti
                filaGasolina[6] = "----";                                           // CO Ralenti
                filaGasolina[7] = "----";                                           // CO2 Ralenti
                filaGasolina[8] = "----";                                           // O2 Ralenti
                filaGasolina[9] = "----";                                           // RPM CRUCERO
                filaGasolina[10] = "----";                                          // HC CRUCERO
                filaGasolina[11] = "----";                                          //CO CRUCERO
                filaGasolina[12] = "----";                                          //CO2 CRUCERO
                filaGasolina[13] = "....";                                          //O2 CRUCERO
                filaGasolina[14] = "----";                                          //Resultado de la prueba
                filaGasolina[15] = "----";                                          //Nivel de Emision de Ruido
                filaGasolina[16] = sdf.format(p.getFechaPrueba());                  //Fecha de la prueba
                filaGasolina[17] = vehiculo.getCarplate();                          //Placa

                if (p.getMedidasList() != null) {
                    List<Medidas> listaMed = p.getMedidasList();
                    for (Medidas m : listaMed) {
                        if (m.getTiposMedida().getMeasuretype() == 7003) {
                            filaGasolina[15] = m.getValormedida();
                        }
                    }
                }

                for (Medidas med : medidas) {
                    int measureType = med.getTiposMedida().getMeasuretype();
                    switch (measureType) {

                        case 8001:                                  //HC ralenti
                            filaGasolina[5] = med.getValormedida();
                            break;
                        case 8002:                                  //CO ralenti
                            filaGasolina[6] = med.getValormedida();
                            break;
                        case 8003:                                  //CO2 ralenti
                            filaGasolina[7] = med.getValormedida();
                            break;
                        case 8004:                                  //O2 ralenti
                            filaGasolina[8] = med.getValormedida();
                            break;
                        case 8011:                                  //RPM Crucero
                            filaGasolina[9] = med.getValormedida();
                            break;
                        case 8007:                                  //HC Crucero
                            filaGasolina[10] = med.getValormedida();
                            break;
                        case 8008:                                  //CO Crucero
                            filaGasolina[11] = med.getValormedida();
                            break;
                        case 8009:                                  //CO2 Crucero
                            filaGasolina[12] = med.getValormedida();
                            break;
                        case 8010:                                  //O2 Crucero
                            filaGasolina[13] = med.getValormedida();
                            break;
//                      case 7003:                                  //O2 Crucero
//                        filaGasolina[15] = med.getValormedida();
//                        break;
                    }//end switch de los medidas
                }//end medidas   
                if (p.getAbortada().trim().equalsIgnoreCase("Y")) {
                    filaGasolina[14] = "Prueba Abortada";
                } else if (p.getAprobada().trim().equalsIgnoreCase("N")) {
                    filaGasolina[14] = "Reprobada";
                } else if (p.getAprobada().trim().equalsIgnoreCase("Y")) {
                    filaGasolina[14] = "Aprobada";
                }

                //------------------------------------------------------------------------------
                //Rutina para mantener el nivel de ruido de una prueba a la siguiente siguiente manteniendo la fecha y la placa del vehiculo.
                if (!filaGasolina[15].equals("----")) {
                    veh[0] = filaGasolina[15]; //ruido
                    veh[1] = ((String) filaGasolina[16]).substring(0, 10); // YYYY/mm/dd
                    veh[2] = filaGasolina[17];  //carplate
                } else if (veh[2].equals(filaGasolina[17]) && (veh[1].equals(((String) filaGasolina[16]).substring(0, 10)))) {
                    filaGasolina[15] = veh[0];
                    veh[0] = "----";
                    veh[0] = "";
                    veh[2] = "";
                    modeloGasolina.addRow(filaGasolina);
                }
//System.out.println(filaGasolina[2]+" : "+filaGasolina[17]+" :  ººº "+filaGasolina[5]+" - "+filaGasolina[6]+" - "+filaGasolina[7]+" - "+filaGasolina[8]+" - "+filaGasolina[9]+" - "+filaGasolina[10]+" - "+filaGasolina[11]+" - "+filaGasolina[12]+" - "+filaGasolina[13]+" -[ "+filaGasolina[15]+ "/"+nivel_ruido+" - "+filaGasolina[16]);             
//------------------------------------------------------------------------------              
            }//end if Gasolina

            //Si es de tipo Diesel
            if (vehiculo.getTiposGasolina().getFueltype() == 3) {// Diesel

                // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
                filaDiesel[0] = vehiculo.getMarcas().getNombremarca();            //Marca del vehiculo
                filaDiesel[1] = vehiculo.getModelo();                             //Modelo
                filaDiesel[2] = vehiculo.getCilindraje();                        //Cilindraje
                filaDiesel[3] = vehiculo.getClasesVehiculo().getNombreclase();    //Clase
                filaDiesel[4] = vehiculo.getServicios().getNombreservicio();      //Servicio
                filaDiesel[5] = "----";                                           // Primer Ciclo
                filaDiesel[6] = "----";                                           // Segundo Ciclo
                filaDiesel[7] = "----";                                           // Tercer ciclo
                filaDiesel[8] = "----";                                           //Resultado Final
                filaDiesel[9] = "----";                                           //Resultado de la prueba
                filaDiesel[10] = "----";                                          //Nivel de Emision de Ruido
                filaDiesel[11] = sdf.format(p.getFechaPrueba());                  //Fecha de la prueba
                filaDiesel[12] = vehiculo.getCarplate();                          //Placa

                for (Medidas med : medidas) {

                    switch (med.getTiposMedida().getMeasuretype()) {
                        case 8013:                                  //1er ciclo
                            filaDiesel[5] = med.getValormedida();
                            break;
                        case 8014:                                  //2do ciclo
                            filaDiesel[6] = med.getValormedida();
                            break;
                        case 8015:                                 //3er ciclo
                            filaDiesel[7] = med.getValormedida();
                            break;
                        case 8017:                                 //Resultado Total
                            filaDiesel[8] = med.getValormedida();
                            break;
                        case 7003:                                 //Resultado Total
                            filaDiesel[10] = med.getValormedida();
                            break;

                    }//end switch de los medidas
                }//end medidas

                if (p.getAbortada().trim().equalsIgnoreCase("Y")) {
                    filaDiesel[9] = "Prueba Abortada";
                } else if (p.getAprobada().trim().equalsIgnoreCase("N")) {
                    filaDiesel[9] = "Reprobada";
                } else if (p.getAprobada().trim().equalsIgnoreCase("Y")) {
                    filaDiesel[9] = "Aprobada";
                }
//------------------------------------------------------------------------------
                //Rutina para mantener el nivel de ruido de una prueba a la siguiente siguiente manteniendo la fecha y la placa del vehiculo.
                if (!filaDiesel[10].equals("----")) {
                    veh[0] = filaDiesel[10]; //ruido
                    veh[1] = ((String) filaDiesel[11]).substring(0, 10); // YYYY/mm/dd
                    veh[2] = filaDiesel[12];  //carplate
                } else if (veh[2].equals(filaDiesel[12]) && (veh[1].equals(((String) filaDiesel[11]).substring(0, 10)))) {
                    filaDiesel[10] = veh[0];
                    veh[0] = "----";
                    veh[0] = "";
                    veh[2] = "";
                    modeloDiesel.addRow(filaDiesel);
                }
//------------------------------------------------------------------------------

            }//end if Diesel

            if (vehiculo.getTipoVehiculo().getCartype() == 4) {//----------------------------------------------------------------------------
                // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
                filaMotos[0] = vehiculo.getMarcas().getNombremarca();            //Marca del vehiculo
                filaMotos[1] = vehiculo.getModelo();                             //Modelo
                filaMotos[2] = vehiculo.getTiemposmotor() + "T";                 //Tiempos
                filaMotos[3] = "----";                                           //CO Ralenti
                filaMotos[4] = "----";                                           //CO2 Ralenti
                filaMotos[5] = "----";                                           //O2 Ralenti
                filaMotos[6] = "----";                                           //HC Ralenti
                filaMotos[7] = "----";                                           //Temperatura Ambiente
                filaMotos[8] = "----";                                           //Humedad Relativa Ambiente
                filaMotos[9] = "----";                                           //Resultado de la prueba
                filaMotos[10] = "----";                                          //Nivel de Emision de Ruido
                filaMotos[11] = sdf.format(p.getFechaPrueba());                  //Fecha de la prueba
                filaMotos[12] = vehiculo.getCarplate();                          //Placa

//------------------------------------------------------------------------------               
                if (((String) filaMotos[2]).equalsIgnoreCase("4T")) {
                    //----
                    for (Medidas med : medidas) {
                        //System.out.println("--"+med.getTiposMedida().getMeasuretype());
                        switch (med.getTiposMedida().getMeasuretype()) {
                            case 8001:                                  //HC ralenti 8018
                                filaMotos[6] = med.getValormedida();
                                break;
                            case 8002:                                  //CO ralenti 8020 
                                filaMotos[3] = med.getValormedida();
                                break;
                            case 8003:                                  //CO2 ralenti 8019
                                filaMotos[4] = med.getValormedida();
                                break;
                            case 8004:                                  //O2 ralenti 8021
                                filaMotos[5] = med.getValormedida();
                                break;
                            case 8031:                                  //Temperatura ambiente
                                filaMotos[7] = med.getValormedida();
                                break;
                            case 8032:                                      //Humedad Relativa
                                filaMotos[8] = med.getValormedida();
                                break;
                            case 7003:                                      //Humedad Relativa  -----/Presion sonora de tubo de escape (Decibeles)
                                filaMotos[10] = med.getValormedida();

                                break;
                        }//end switch de los medidas           
                        //System.out.println(filaMotos[2]+" : "+filaMotos[12]+" : "+ med.getTiposMedida().getMeasuretype() +" ººº "+filaMotos[3]+"-"+filaMotos[4]+"-"+filaMotos[5]+"-"+filaMotos[6]+"-"+filaMotos[7]+"-"+filaMotos[8]+"-"+filaMotos[9]+"-"+filaMotos[10]); 
                    }//end medidas
                    //----  
                    //System.out.println(filaMotos[2]+" : "+filaMotos[12]+" :  ººº "+filaMotos[3]+" - "+filaMotos[4]+" - "+filaMotos[5]+" - "+filaMotos[6]+" - "+filaMotos[7]+" - "+filaMotos[8]+" - "+filaMotos[9]+" -[ "+filaMotos[10]+ "/"+nivel_ruido+" - "+filaMotos[11]); 
                } else;
//------------------------------------------------------------------------------
                if (((String) filaMotos[2]).equalsIgnoreCase("2T")) {
                    //----
                    for (Medidas med : medidas) {
                        //System.out.println("--"+med.getTiposMedida().getMeasuretype());
                        switch (med.getTiposMedida().getMeasuretype()) {
                            case 8018:                                  //HC ralenti 8018
                                filaMotos[6] = med.getValormedida();
                                break;
                            case 8020:                                  //CO ralenti 8020 
                                filaMotos[3] = med.getValormedida();
                                break;
                            case 8019:                                  //CO2 ralenti 8019
                                filaMotos[4] = med.getValormedida();
                                break;
                            case 8021:                                  //O2 ralenti 8021
                                filaMotos[5] = med.getValormedida();
                                break;
                            case 8031:                                  //Temperatura ambiente
                                filaMotos[7] = med.getValormedida();
                                break;
                            case 8032:                                      //Humedad Relativa
                                filaMotos[8] = med.getValormedida();
                                break;
                            case 7003:                                      //Humedad Relativa  -----/Presion sonora de tubo de escape (Decibeles)
                                filaMotos[10] = med.getValormedida();
                                break;
                        }//end switch de los medidas           
                        //System.out.println(filaMotos[2]+" : "+filaMotos[12]+" : "+ med.getTiposMedida().getMeasuretype() +" ººº "+filaMotos[3]+"-"+filaMotos[4]+"-"+filaMotos[5]+"-"+filaMotos[6]+"-"+filaMotos[7]+"-"+filaMotos[8]+"-"+filaMotos[9]+"-"+filaMotos[10]); 
                    }//end medidas
                    //----  
                    //System.out.println(filaMotos[2]+" : "+filaMotos[12]+" :  ººº "+filaMotos[3]+" - "+filaMotos[4]+" - "+filaMotos[5]+" - "+filaMotos[6]+" - "+filaMotos[7]+" - "+filaMotos[8]+" - "+filaMotos[9]+" -[ "+filaMotos[10]+ "/"+nivel_ruido+" - "+filaMotos[11]); 
                } else;
//------------------------------------------------------------------------------

                if (p.getAbortada().trim().equalsIgnoreCase("Y")) {
                    filaMotos[9] = "Prueba Abortada";
                } else if (p.getAprobada().trim().equalsIgnoreCase("N")) {
                    filaMotos[9] = "Reprobada";
                } else if (p.getAprobada().trim().equalsIgnoreCase("Y")) {
                    filaMotos[9] = "Aprobada";
                } else;

                //Rutina para mantener el nivel de ruido de una prueba a la siguiente siguiente manteniendo la fecha y la placa del vehiculo.
                if (!filaMotos[10].equals("----")) {
                    veh[0] = filaMotos[10]; //ruido
                    veh[1] = ((String) filaMotos[11]).substring(0, 10); // YYYY/mm/dd
                    veh[2] = filaMotos[12];  //carplate
                } else if (veh[2].equals(filaMotos[12]) && (veh[1].equals(((String) filaMotos[11]).substring(0, 10)))) {
                    filaMotos[10] = veh[0];
                    veh[0] = "----";
                    veh[0] = "";
                    veh[2] = "";
                    modeloMotos.addRow(filaMotos);
                }
                //System.out.println(filaMotos[2]+" : "+filaMotos[12]+" :  ººº "+filaMotos[3]+" - "+filaMotos[4]+" - "+filaMotos[5]+" - "+filaMotos[6]+" - "+filaMotos[7]+" - "+filaMotos[8]+" - "+filaMotos[9]+" -[ "+filaMotos[10]+ "/"+nivel_ruido); 
//------------------------------------------------------------------------------            
            }//end if Motos

            //Se llenan los modelos dentro del for para que agregue cada row generado            
        }
        //Se actualiza el modelo de cada tabla para que se refresque la informacion
        tblMotos.setModel(modeloMotos);
        tblGasolina.setModel(modeloGasolina);
        tblDiesel.setModel(modeloDiesel);
        tblMotos.setEnabled(false);
        tblGasolina.setEnabled(false);
        tblDiesel.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();
        jXDatePicker1.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
        jLabel2 = new javax.swing.JLabel();
        jXDatePicker2 = new org.jdesktop.swingx.JXDatePicker();
        jXDatePicker2.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblGasolina = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDiesel = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblMotos = new javax.swing.JTable();

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

        jScrollPane1.setName(""); // NOI18N

        tblGasolina.setModel(modeloGasolina);
        tblGasolina.setName("Gasolina"); // NOI18N
        jScrollPane1.setViewportView(tblGasolina);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 795, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Gasolina", jPanel1);

        tblDiesel.setModel(modeloDiesel);
        tblDiesel.setName("Diesel"); // NOI18N
        jScrollPane2.setViewportView(tblDiesel);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 795, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        jTabbedPane1.addTab("Diesel", jPanel2);

        tblMotos.setModel(modeloMotos);
        tblMotos.setName("Motos"); // NOI18N
        jScrollPane3.setViewportView(tblMotos);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 795, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 795, Short.MAX_VALUE)
                    .addGap(0, 0, 0)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 596, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE)
                    .addGap(0, 0, 0)))
        );

        jTabbedPane1.addTab("Motos", jPanel3);

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        tables.add(tblGasolina);
        tables.add(tblDiesel);
        tables.add(tblMotos);

        GenericExportExcel excel = new GenericExportExcel();
        excel.exportExcel(tables);

    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker2;
    private javax.swing.JTable tblDiesel;
    private javax.swing.JTable tblGasolina;
    private javax.swing.JTable tblMotos;
    // End of variables declaration//GEN-END:variables
}
