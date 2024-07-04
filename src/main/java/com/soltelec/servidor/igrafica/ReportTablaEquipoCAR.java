/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.igrafica;

import com.soltelec.servidor.conexion.PersistenceController;
import com.soltelec.servidor.dao.CdaJpaController;
import com.soltelec.servidor.dao.PruebasJpaController;
import com.soltelec.servidor.dao.SoftwareJpaController;
import com.soltelec.servidor.model.CalibracionDosPuntos;
import com.soltelec.servidor.model.Cda;
import com.soltelec.servidor.model.Defxprueba;
import com.soltelec.servidor.model.Diseno;
import com.soltelec.servidor.model.HojaPruebas;
import com.soltelec.servidor.model.Medidas;
import com.soltelec.servidor.model.Propietarios;
import com.soltelec.servidor.model.Pruebas;
import com.soltelec.servidor.model.Software;
import com.soltelec.servidor.model.Vehiculos;
import com.soltelec.servidor.model.Equipos;
import com.soltelec.servidor.utils.CargarCalibracionReporte;
import com.soltelec.servidor.utils.GenericExportExcel;
import com.soltelec.servidor.utils.JTitlePanel;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Administrador
 */
public class ReportTablaEquipoCAR extends javax.swing.JInternalFrame {

    JFileChooser fc;
    private Object calendarPanel;
    JPanel componentPanel;
    JTitlePanel componentTitlePanel;
    private DefaultTableModel modelEquipo;
    private Cda results;
    private SimpleDateFormat sdf;
    private static final Logger LOG = Logger.getLogger(ReportTablaEquipoCAR.class.getName());

    /**
     * Creates new form ReportMotos
     */
    public ReportTablaEquipoCAR() {
        super("Reporte Equipos CAR ",
                true, //resizable
                true, //closable
                false, //maximizable
                true);
        generarModelo();
        initComponents();
    }

    private void generarModelo() {
        sdf = new java.text.SimpleDateFormat("yyyy/MM/dd");
//      ******MODELO EQUIPO******          
        modelEquipo = new DefaultTableModel();
        modelEquipo.addColumn("Vr PEF");// 1
        modelEquipo.addColumn("Número de serie del banco");// 2
        modelEquipo.addColumn("No. serie  analizador");// 3
        modelEquipo.addColumn("Marca analizador");// 4
        modelEquipo.addColumn("Vr bajo Span Bajo HC  ");// 5
        modelEquipo.addColumn("Resultado de Vr Span Bajo HC ");// 6
        modelEquipo.addColumn("Vr bajo Span Bajo CO  ");// 7
        modelEquipo.addColumn("Resultado de Vr Span Bajo CO ");// 8
        modelEquipo.addColumn("Vr bajo Span Bajo CO2  ");// 9
        modelEquipo.addColumn("Resultado de Vr Span Bajo CO2 ");// 10

        modelEquipo.addColumn("Vr bajo Span Alto HC  ");// 11
        modelEquipo.addColumn("Resultado de Vr Span Alto HC ");// 12
        modelEquipo.addColumn("Vr bajo Span Alto CO ");// 13
        modelEquipo.addColumn("Resultado de Vr Span Alto CO ");// 14
        modelEquipo.addColumn("Vr bajo Span Alto CO2 ");// 15
        modelEquipo.addColumn("Resultado de Vr Span Alto CO2 "); // 16
        modelEquipo.addColumn("Fecha  y hora ultima verificación y ajuste");// 17
        modelEquipo.addColumn("Nombre Proveedor");// 18
        modelEquipo.addColumn("Nombre del Programa");// 19
        modelEquipo.addColumn("Version Programa");// 20
    }

    /**
     * Metodo para llenar los datos en las tablas
     */
    private void fillData(Date fInicial, Date fFinal) {
        generarModelo();
        //LLENAR EQUIPO
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(Locale.ENGLISH);
        df.applyPattern("#0.00");//averiguar sobre DecimalFormat
        Object[] filaprueba = new Object[8];
        // Se crea un array que será una de las filas de la tabla.
        Calendar c1 = Calendar.getInstance();
        c1.setTime(fInicial);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(fFinal);
        SoftwareJpaController swJpa = new SoftwareJpaController();
        Software sw = swJpa.findSoftware(1);
        EntityManager em = PersistenceController.getEntityManager();
        try {
            Query q = em.createNativeQuery("SELECT c.CALIBRATION,c.id_equipo,MAX(c.CURDATE),pc.banco_bm_hc,pc.banco_bm_co,pc.banco_bm_co2,pc.banco_alta_hc,pc.banco_alta_co,pc.banco_alta_co2,pc.bm_co,pc.bm_co2,pc.bm_hc,pc.alta_co,pc.alta_co2,pc.alta_hc FROM calibracion_dos_puntos pc inner join calibraciones c on pc.CALIBRATION = c.CALIBRATION WHERE c.id_tipo_calibracion = 2 AND  Date(c.CURDATE) between ? and ? group by Date(c.CURDATE),c.id_equipo order by c.CURDATE ");
            q.setParameter(1, new SimpleDateFormat("yyyy-MM-dd").format(c1.getTime()));
            q.setParameter(2, new SimpleDateFormat("yyyy-MM-dd").format(c2.getTime()));
            List<Object[]> LstCal = q.getResultList();
            Object[] filaEquipo = new Object[20]; // Hay tres columnas en la tabla
            for (Object[] result : LstCal) {
                Equipos eq = em.find(Equipos.class, result[1]);
                filaEquipo[0] = eq.getPef();             //PEF
                filaEquipo[1] = eq.getSerial();                 //Serie Banco
                filaEquipo[2] = eq.getReolucionserial();                    //Serie Analizador
                filaEquipo[3] = eq.getMarca();                   //Marca Analizador

                filaEquipo[4] = df.format(result[11]);                   //Valor gas referencia bajo  HC verificaciòn y ajuste 
                filaEquipo[5] = df.format(result[4]);  //Valor gas referencia bajo CO verificaciòn y ajuste

                filaEquipo[6] = df.format(result[9]);                 //Valor gas referencia bajo CO2 verificaciòn y ajuste
                filaEquipo[7] = df.format(result[5]);                   //Valor gas referencia alto HC verificaciòn y ajuste

                filaEquipo[8] = df.format(result[10]);                   //Valor gas referencia  alto CO verificaciòn y ajuste
                filaEquipo[9] = df.format(result[6]);                 //Valor gas referencia  alto CO2 verificaciòn y ajuste

                filaEquipo[10] = df.format(result[14]);                   //Valor gas referencia bajo  HC verificaciòn y ajuste 
                filaEquipo[11] = df.format(result[7]);                   //Valor gas referencia bajo CO verificaciòn y ajuste

                filaEquipo[12] = df.format(result[12]);                //Valor gas referencia bajo CO2 verificaciòn y ajuste
                filaEquipo[13] = df.format(result[8]);                   //Valor gas referencia alto HC verificaciòn y ajuste

                filaEquipo[14] = df.format(result[13]);                   //Valor gas referencia  alto CO verificaciòn y ajuste
                filaEquipo[15] = df.format(result[9]);                 //Valor gas referencia  alto CO2 verificaciòn y ajuste

                filaEquipo[16] = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(result[2]);    //Fecha de Version Software
                filaEquipo[17] = "SOLTELEC SAS";                        //Nombre del PROVEEDOR
                filaEquipo[18] = sw.getNombre();                            //Nombre del software de aplicación
                filaEquipo[19] = sw.getVersion();                      //Version software de aplicación
                modelEquipo.addRow(filaEquipo);
            }
        } catch (Exception e) {
            LOG.severe("" + e.getMessage());
        } finally {
        }
        //Se actualiza el modelo de cada tabla para que se refresque la informacion
        tblEquipo.setModel(modelEquipo);
        tblEquipo.setEnabled(false);
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
        progreso = new javax.swing.JProgressBar();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEquipo = new javax.swing.JTable();

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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Datos Equipo", jPanel2);

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

        jTabbedPane1.getAccessibleContext().setAccessibleName("Datos CD");
        jTabbedPane1.getAccessibleContext().setAccessibleDescription("");

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
        tables.add(tblEquipo);
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
            java.util.logging.Logger.getLogger(ReportTablaEquipoCAR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReportTablaEquipoCAR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReportTablaEquipoCAR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReportTablaEquipoCAR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ReportTablaEquipoCAR().setVisible(true);
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker2;
    private javax.swing.JProgressBar progreso;
    private javax.swing.JTable tblEquipo;
    // End of variables declaration//GEN-END:variables
}
