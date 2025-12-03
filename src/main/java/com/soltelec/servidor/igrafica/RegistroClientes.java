
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.  
 */
package com.soltelec.servidor.igrafica;

import com.soltelec.servidor.consultas.Reportes;
import com.soltelec.servidor.dtos.clientes.Clientes;
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

public class RegistroClientes extends javax.swing.JInternalFrame {

    JFileChooser fc;
    JPanel componentPanel;
    JTitlePanel componentTitlePanel;
    private DefaultTableModel datosClientes;

    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker2;
    private javax.swing.JTable tblDatos;

    public RegistroClientes() {
        super("Reporte de clientes",
                true, //resizable
                true, //closable
                false, //maximizable
                true);
        iniciarModelo();
        initComponents();
    }

    private void iniciarModelo() {

        datosClientes = new DefaultTableModel();
        datosClientes.addColumn("Placa");//0
        datosClientes.addColumn("Fecha ingreso");//1
        datosClientes.addColumn("Nombre propietario");//2
        datosClientes.addColumn("Apellido propietario");//3
        datosClientes.addColumn("Tipo documento");//4
        datosClientes.addColumn("Numero documento");//5
        datosClientes.addColumn("Telefono 1");//6
        datosClientes.addColumn("Telefono 2");//7
        datosClientes.addColumn("Direccion");//8
        datosClientes.addColumn("Ciudad");//9
        datosClientes.addColumn("Email");//10
        datosClientes.addColumn("Marca");//11
        datosClientes.addColumn("Linea"); //12
        datosClientes.addColumn("Modelo"); //13
        datosClientes.addColumn("Tipo Vehiculo"); //14
        datosClientes.addColumn("Clase Vehiculo"); //15
        datosClientes.addColumn("Tipo Combustible"); //16
        datosClientes.addColumn("Tipo Servicio"); //17
        datosClientes.addColumn("Numero Chasis"); //18
        datosClientes.addColumn("Numero Certificado"); //19
        datosClientes.addColumn("Director tecnico"); //20
        datosClientes.addColumn("Recepcionista"); //21
        datosClientes.addColumn("Fecha Soat"); //22
        datosClientes.addColumn("Preventiva"); //23

//---------- 
    }

    private void fillData(Date fInicial, Date fFinal) {
        List<Clientes> listaDatos = Reportes.getClientes(fInicial, fFinal);

        listaDatos.stream().forEach(datos->{
            Object [] fila ={
                datos.getPlaca(), //0
                datos.getUltimaFechaRevision(), //1
                datos.getNombre(), //2
                datos.getApellido(), //3
                datos.getTipoDocumento(), //4
                datos.getNumeroDocumento(), //5
                datos.getTelefono1(), //6
                datos.getTelefono2(), //7
                datos.getDireccion(), //8
                datos.getCiudad(), //9
                datos.getEmail(), //10
                datos.getNombreMarca(), //11
                datos.getNombreLinea(), //12
                datos.getModelo(), //13
                datos.getTipoVehiculo(), //14
                datos.getClaseVehiculo(), //15
                datos.getTipoCombustible(), //16
                datos.getTipoServicio(), //17
                datos.getNumeroChasis(), //18
                datos.getNumeroCertificado(), //19
                datos.getDirectorTecnico(), //20
                datos.getRecepcionista(), //21
                datos.getFechaSoat(), //22
                datos.getEsPreventiva() //23
            };
            datosClientes.addRow(fila);
        });

        tblDatos.setModel(datosClientes);
        tblDatos.setEnabled(false);
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblDatos = new javax.swing.JTable();

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

        tblDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));

        tblDatos.setName("Registro clientes"); // NOI18N
        jScrollPane3.setViewportView(tblDatos);

        jTabbedPane1.addTab("Registro clientes", jScrollPane3);

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
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
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
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE))
        );

        pack();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
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
        tables.add(tblDatos);

        GenericExportExcel excel = new GenericExportExcel();
        excel.exportExcel(tables);

    }

    
}
