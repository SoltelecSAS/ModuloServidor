package com.soltelec.servidor.igrafica;

import com.soltelec.servidor.model.Usuarios;
import com.soltelec.servidor.conexion.PersistenceController;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.skin.DustSkin;

/**
 * @author
 */
public class Login extends javax.swing.JFrame {

    private String pass, nombreUsuario;
    private List results;
    private static final Logger LOG = Logger.getLogger(FrmSimulacion.class.getName());

    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
        setLocationRelativeTo(null);//permite que la ventana aparezca en la mitad de la pantalla
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        txtContrasena = new javax.swing.JPasswordField();
        btnIngresar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        btnSalir = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Serif", 1, 19)); // NOI18N
        jLabel1.setText("ACCESO DE USUARIO PARA EL MÓDULO SERVIDOR DEL SOFTWARE");

        jLabel2.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        jLabel2.setText("USUARIO:");

        jLabel3.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        jLabel3.setText("CONTRASEÑA:");

        txtUsuario.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N

        txtContrasena.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        txtContrasena.setForeground(new java.awt.Color(255, 102, 0));

        btnIngresar.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        btnIngresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/soltelec/servidor/images/accept.png"))); // NOI18N
        btnIngresar.setText("INGRESAR");
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarActionPerformed(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(0, 102, 255));

        jSeparator2.setForeground(new java.awt.Color(0, 102, 255));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/soltelec/servidor/images/solt.png"))); // NOI18N

        btnSalir.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/soltelec/servidor/images/shut_down.png"))); // NOI18N
        btnSalir.setText("SALIR");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        jSeparator3.setForeground(new java.awt.Color(0, 102, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtContrasena)
                            .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(156, 156, 156))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jSeparator3)
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)))
                .addGap(19, 19, 19))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(191, 191, 191))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(216, 216, 216)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(199, 199, 199))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnIngresar, btnSalir});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(11, 11, 11)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(11, 11, 11)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnIngresar)
                    .addComponent(btnSalir))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarActionPerformed

        //Captura de lo digitado en el campo de texto de la contrasena        
        char p[] = txtContrasena.getPassword();
        pass = new String(p);
        nombreUsuario = txtUsuario.getText();
        //String contrasena = txtContrasena.getText();
        char[] contrasenaChars = txtContrasena.getPassword();
        String contrasena = new String(contrasenaChars);
        System.out.println(contrasena);
            

        if (nombreUsuario.equals("") || contrasena.equals("")) {
            JOptionPane.showMessageDialog(null, "Por favor llenar el campo Usuario y Contrasena.", "Informacion", JOptionPane.INFORMATION_MESSAGE);

        } else {
//            EntityManager em = PersistenceController.getEntityManager();
            EntityManager em = PersistenceController.getEntityManager();
            em.getTransaction().begin();

            //Try catch para capturar nombre de usuario.
            try {
                Query queryNombre = em.createQuery("SELECT u FROM Usuarios u WHERE u.nickusuario = :nickusuario");
                queryNombre.setParameter("nickusuario", nombreUsuario);
                results = queryNombre.getResultList();
                Usuarios c1 = (Usuarios) results.get(0);

                String admin = c1.getEsAdministrador();

                if (!c1.getContrasenia().equals(pass)) {
                    JOptionPane.showMessageDialog(null, "La contrasena no es valida para el usuario.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
                    txtContrasena.setText("");
                    txtContrasena.requestFocus();
                } else if (!c1.getEsAdministrador().equals("Y") && !c1.getEsAdministrador().equals("A")) {
                    JOptionPane.showMessageDialog(null, "El usuario no es Administrador.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
                    txtUsuario.setText("");
                    txtContrasena.setText("");
                } else {
                    Principal.em=em;
                        Principal dlg = new Principal(admin.equals("Y"));
                    dlg.setVisible(true);
                    this.setVisible(false);
                    txtContrasena.setText("");
                }

            } catch (IndexOutOfBoundsException ex) {
                LOG.severe(ex.getMessage());
                JOptionPane.showMessageDialog(null, "Ingrese un NOMBRE de usuario valido", "ERROR", JOptionPane.ERROR_MESSAGE);
                txtUsuario.setText("");
                txtUsuario.requestFocus();
            }
        }
    }//GEN-LAST:event_btnIngresarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceDustCoffeeLookAndFeel");
                    SubstanceLookAndFeel.setSkin(new DustSkin());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                    LOG.severe(e.getMessage());
                }
                Login login = new Login();
                login.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIngresar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JPasswordField txtContrasena;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
