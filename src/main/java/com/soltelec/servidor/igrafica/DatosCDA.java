/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * FrIntPru.java
 *
 * Created on 30/06/2011, 09:34:07 AM
 */
package com.soltelec.servidor.igrafica;

/**
 *
 * @author GerenciaDesarrollo
 */
import com.soltelec.servidor.dao.CdaJpaController;
import com.soltelec.servidor.dao.CiudadesJpaController;
import com.soltelec.servidor.model.Cda;
import com.soltelec.servidor.model.Ciudades;
import com.soltelec.servidor.model.Usuarios;
import com.soltelec.servidor.utils.CMensajes;
import com.soltelec.servidor.utils.CValidaciones;
import com.soltelec.servidor.utils.Validaciones;
import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JTextField;

/* Used by InternalFrameDemo.java. */
public class DatosCDA extends javax.swing.JInternalFrame {

    private JButton jButton1;
    private JTextField jTextField1;
    private String direccionIP;
    private String usuario;
    private String password;

    public DatosCDA() {
        super("CDA",
                true, //resizable
                true, //closable
                false, //maximizable
                true);

        //Set the window's location.
//        cargarConexion();
        initComponents();
        dimension();
        llenarCampos();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlContenedor = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        pnlDatos = new javax.swing.JPanel();
        btnCancelar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        txtResolucion = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        txtNit = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtConformidad = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtCiudad = new javax.swing.JTextField();
        cmbResponsable = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        txtCelular = new javax.swing.JTextField();
        dteFResolucion = new org.jdesktop.swingx.JXDatePicker();
        jLabel11 = new javax.swing.JLabel();
        cmbCiudades = new javax.swing.JComboBox();

        setClosable(true);

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/soltelec/servidor/images/solt.png"))); // NOI18N

        pnlDatos.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Datos del CDA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(0, 51, 204))); // NOI18N

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/soltelec/servidor/images/block.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/soltelec/servidor/images/save.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        txtResolucion.setName("Resolucion"); // NOI18N
        txtResolucion.setPreferredSize(new java.awt.Dimension(163, 20));

        jLabel5.setText("Resolucion");

        jLabel3.setText("Direccion");

        txtDireccion.setName("Direccion"); // NOI18N
        txtDireccion.setPreferredSize(new java.awt.Dimension(163, 20));

        txtNombre.setName("Nombre"); // NOI18N
        txtNombre.setPreferredSize(new java.awt.Dimension(163, 20));

        jLabel1.setText("Nombre");

        jLabel2.setText("Nit");

        jLabel4.setText("Telefono");

        jLabel6.setText("Responsable");

        txtTelefono.setName("Telefono"); // NOI18N
        txtTelefono.setPreferredSize(new java.awt.Dimension(163, 20));
        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyTyped(evt);
            }
        });

        txtNit.setName("Nit"); // NOI18N
        txtNit.setPreferredSize(new java.awt.Dimension(163, 20));
        txtNit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNitKeyTyped(evt);
            }
        });

        jLabel7.setText("Conformidad");

        txtConformidad.setName("Conformidad"); // NOI18N
        txtConformidad.setPreferredSize(new java.awt.Dimension(163, 20));

        jLabel8.setText("Ciudad");

        txtCiudad.setName("Ciudad"); // NOI18N
        txtCiudad.setPreferredSize(new java.awt.Dimension(163, 20));
        txtCiudad.setRequestFocusEnabled(false);

        cmbResponsable.setPreferredSize(new java.awt.Dimension(163, 20));

        jLabel10.setText("Celular");

        txtCelular.setName("Telefono"); // NOI18N
        txtCelular.setPreferredSize(new java.awt.Dimension(163, 20));
        txtCelular.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelularKeyTyped(evt);
            }
        });

        dteFResolucion.setPreferredSize(new java.awt.Dimension(163, 20));

        jLabel11.setText("Fecha Resolucion");

        cmbCiudades.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbCiudadesItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout pnlDatosLayout = new javax.swing.GroupLayout(pnlDatos);
        pnlDatos.setLayout(pnlDatosLayout);
        pnlDatosLayout.setHorizontalGroup(
            pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosLayout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlDatosLayout.createSequentialGroup()
                        .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtResolucion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlDatosLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(txtDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlDatosLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(txtConformidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlDatosLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(txtCelular, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDatosLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtNit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDatosLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txtTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDatosLayout.createSequentialGroup()
                        .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dteFResolucion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbResponsable, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDatosLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmbCiudades, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCiudad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(43, Short.MAX_VALUE))
            .addGroup(pnlDatosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlDatosLayout.setVerticalGroup(
            pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDatosLayout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtNit, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(cmbResponsable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16)
                .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtResolucion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dteFResolucion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8))
                    .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtConformidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)))
                .addGap(18, 18, 18)
                .addComponent(cmbCiudades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnGuardar))
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlContenedorLayout = new javax.swing.GroupLayout(pnlContenedor);
        pnlContenedor.setLayout(pnlContenedorLayout);
        pnlContenedorLayout.setHorizontalGroup(
            pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContenedorLayout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel9)
                    .addComponent(pnlDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlContenedorLayout.setVerticalGroup(
            pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContenedorLayout.createSequentialGroup()
                .addComponent(jLabel9)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(pnlDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(67, Short.MAX_VALUE)
                .addComponent(pnlContenedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(67, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlContenedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dimension() {
        Dimension dim = super.getToolkit().getScreenSize();
        this.setSize(dim);
    }

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

        if (!CValidaciones.validarCampos(pnlDatos)) {
            return;
        }

        CdaJpaController cda = new CdaJpaController();
        Cda mcda = cda.findCda(1);
        if (mcda == null) {
            mcda = new Cda(1);
        }

        mcda.setNombre(txtNombre.getText());
        mcda.setNit(txtNit.getText());
        mcda.setDireccion(txtDireccion.getText());
        mcda.setTelefono(txtTelefono.getText());
        mcda.setCelular(txtCelular.getText());
        mcda.setResolucion(txtResolucion.getText());
        mcda.setFechaResolucion(dteFResolucion.getDate());
        mcda.setCertificadoconformidad(txtConformidad.getText());

        CiudadesJpaController cjc = new CiudadesJpaController();
       
        mcda.setCiudad(txtCiudad.getText());

        mcda.setIdUsuarioResponsable(((Usuarios) cmbResponsable.getSelectedItem()).getGeuser());
        mcda.setNomRespCertificados(((Usuarios) cmbResponsable.getSelectedItem()).getNombreusuario());

        try {
            cda.edit(mcda);
            CMensajes.mensajeCorrecto("Se ha REGISTRADO los Datos Exitosamente..!");
            this.dispose();
        } catch (Exception ex) {
            CMensajes.mensajeError("Disculpe, no he podido  lograr guardar los datos");
            CMensajes.mostrarExcepcion(ex);
        }

}//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.setVisible(false);
}//GEN-LAST:event_btnCancelarActionPerformed

    private void txtNitKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNitKeyTyped
        Validaciones.soloNumeros(evt);
    }//GEN-LAST:event_txtNitKeyTyped

    private void txtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyTyped
        Validaciones.soloNumeros(evt);
    }//GEN-LAST:event_txtTelefonoKeyTyped

    private void txtCelularKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelularKeyTyped
        Validaciones.soloNumeros(evt);
    }//GEN-LAST:event_txtCelularKeyTyped

    private void cmbCiudadesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbCiudadesItemStateChanged
        //txtCiudad.set = cmbCiudades.getSelectedItem().toString();
        //String seleccionado = (String) cmbCiudades.getSelectedItem();
        //txtCiudad.setText(seleccionado);        
    }//GEN-LAST:event_cmbCiudadesItemStateChanged

    private void llenarCampos() {
        CdaJpaController cda = new CdaJpaController();
        Cda mcda = cda.findCda(1);
        for (Usuarios u : cda.listaUsuariosAdministradores()) {
            cmbResponsable.addItem(u);
        }

        CiudadesJpaController cjc = new CiudadesJpaController();
        for (Ciudades c : cjc.listaCiudadesObj()) {
            //cmbCiudades.addItem(c.getCiudadprincipal() + " - " + c.getNombreciudad());
            cmbCiudades.addItem(c);            
        }

        if (mcda != null) {
            txtNombre.setText(mcda.getNombre());
            txtNit.setText(mcda.getNit());
            txtDireccion.setText(mcda.getDireccion());
            txtTelefono.setText(mcda.getTelefono());
            txtCelular.setText("" + mcda.getCelular());
            txtResolucion.setText(mcda.getResolucion());
            dteFResolucion.setDate(mcda.getFechaResolucion());
            txtConformidad.setText(mcda.getCertificadoconformidad());
            txtCiudad.setText("" + mcda.getCiudad());
            if (mcda.getIdUsuarioResponsable() != null) {
                cmbResponsable.setSelectedItem(cda.findUsuarioResponsable(mcda.getIdUsuarioResponsable()));
            } else {

            }
        } else {

        }
    }

    private void cargarConexion() {
        Properties props = new Properties();
        //try retrieve data from file
        try {
            props.load(new FileInputStream("./conexion.properties"));
            direccionIP = props.getProperty("urljdbc");
            usuario = props.getProperty("usuario");
            password = props.getProperty("password");
        } //catch exception in case properties file does not exist
        catch (IOException e) {
            e.printStackTrace();
        }
    }//end of method cargarUrl
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox cmbCiudades;
    private javax.swing.JComboBox cmbResponsable;
    private org.jdesktop.swingx.JXDatePicker dteFResolucion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel pnlContenedor;
    private javax.swing.JPanel pnlDatos;
    private javax.swing.JTextField txtCelular;
    private javax.swing.JTextField txtCiudad;
    private javax.swing.JTextField txtConformidad;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtNit;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtResolucion;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
