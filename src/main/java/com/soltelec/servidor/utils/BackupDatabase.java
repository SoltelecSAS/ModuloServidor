
package com.soltelec.servidor.utils;

import com.soltelec.servidor.conexion.Conexion;
import com.soltelec.servidor.consultas.DatabaseBackup;
import com.soltelec.servidor.dtos.superuser.SuperUser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

import javax.swing.*;
import java.awt.*;

public class BackupDatabase {

    static String dbName = "";
    static String dbUser = "";
    static String dbPass = "";
    static String folderPath = "";
    static String sourceFile = "";
    private static final Logger LOG = Logger.getLogger(BackupDatabase.class.getName());

    public static void main(String args[]) throws InterruptedException {
        //Restoredbfromsql("bd_saldana.sql");
        //Backupdbtosql();

    }

    public static void Backupdbtosql(JLabel loading) throws InterruptedException {

        // Crear los campos de entrada con valores predeterminados
        JTextField usuarioField = new JTextField(Conexion.getUsuario());
        JPasswordField contrasenaField = new JPasswordField(Conexion.getContrasena());
        JTextField puertoField = new JTextField(Conexion.getPuerto());
        JTextField baseDatosField = new JTextField(Conexion.getBaseDatos());
        JTextField ipField = new JTextField(Conexion.getIpServidor());


        int dbCredentials = panelCredenciales(usuarioField, contrasenaField, puertoField, baseDatosField, ipField);

        if (dbCredentials != JOptionPane.OK_OPTION) {
            CMensajes.mensajeCorrecto("Operacion cancelada");
            throw new RuntimeException("Operacion cancelada");
        }

        // Crear un diálogo de progreso indeterminado
        JOptionPane pane = new JOptionPane("Creando...", JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
        JDialog dialog = pane.createDialog("Mensaje");


        // Declara una variable atómica para verificar si el hilo ya se ha ejecutado
        AtomicBoolean backupExecuted = new AtomicBoolean(false);

        // Ejecutar el respaldo de la base de datos en un hilo separado
        Thread backupThread = new Thread(() -> {
            // Verifica si el hilo aún no se ha ejecutado
            if (!backupExecuted.getAndSet(true)) {
                // Ejecuta la creación del respaldo en el hilo actual
                int result = DatabaseBackup.createBackups(usuarioField.getText(), new String(contrasenaField.getPassword()), puertoField.getText(), baseDatosField.getText(), ipField.getText());

                // Cierra el diálogo de progreso en el hilo actual
                dialog.dispose();

                // Ejecuta el código relacionado con Swing en el EDT
                SwingUtilities.invokeLater(() -> {
                    // Muestra un cuadro de diálogo después de que el respaldo haya terminado
                    if (result == 0) {
                        loading.setVisible(false);
                        JOptionPane.showMessageDialog(null, "Terminado.\nPuede observar el backup entre la lista de opciones de restauración", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(null, "Este computador debe tener instalado el mysql server y tener configurada la variable de entorno (C:\\Program Files\\MySQL\\MySQL Server x.x\\bin) para poder realizar esta accion. Contacte con soporte", "Mensaje", JOptionPane.ERROR_MESSAGE);
                    }
                });
            }
        });
        backupThread.start();


        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        pane.add(progressBar);
        dialog.pack();
        dialog.setVisible(true);
    }

    public static void Restoredbfromsql(String fileName, JLabel loading) throws InterruptedException {
        // Crear un diálogo de progreso indeterminado
        String[] credentials = pedirCredenciales(loading);

        String puerto = credentials[2];
        String dbName = credentials[3];
        String ubicacion = "C:\\\\BACKUPS\\\\" + fileName;

        String batchContent = String.format("mysql --defaults-extra-file=mysql.cnf --host=%s --port=%s %s < \"%s\"",
                    "localhost", puerto, dbName, ubicacion);

        JOptionPane pane = new JOptionPane("Importando... (esto puede tardar de 10 a 40 min)\nRecuerde que aunque cierre este programa la importacion seguira corriendo en segundo plano\n\nComando mysql ejecutado para la importacion:\n"+batchContent, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
        JDialog dialog = pane.createDialog("Mensaje");

        // Ejecutar el respaldo de la base de datos en un hilo separado
        Thread backupThread = new Thread(() -> {

            
            // Realizar la importación del respaldo de la base de datos
            int code = 1;
            try {
                code = DatabaseBackup.importBackup(fileName, credentials);
            } catch (IOException e) {
                e.printStackTrace();
            }

            int result = code;

            // Verificar el resultado de la importación del respaldo
            
            // Ejecutar el resto del código relacionado con Swing en el EDT
            SwingUtilities.invokeLater(() -> {
                // Cuando el respaldo termine, cerrar el diálogo de progreso
                dialog.dispose();
                loading.setVisible(false);
                // Muestra un cuadro de diálogo después de que el respaldo haya terminado
                if (result == 0) {
                    JOptionPane.showMessageDialog(null, "Importacion terminada con exito.\nCuando ingrese a la base de datos, \nencontrara la base de datos de "+ credentials[3]+" con los datos del backup", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                }else if(result == 1){
                    JOptionPane.showMessageDialog(null, "Importacion cancelada. \nCerraremos el programa por seguridad.\ncodigo = "+result, "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                }else{
                    JOptionPane.showMessageDialog(null, "codigo = "+result+"\nEste computador debe tener instalado el mysql server y tener configurada la variable de entorno (C:\\Program Files\\MySQL\\MySQL Server x.x\\bin) para poder realizar esta accion. Contacte con soporte", "Mensaje", JOptionPane.ERROR_MESSAGE);
                }
                
            });
            
        });

        // Iniciar el hilo
        backupThread.start();

        

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        pane.add(progressBar);
        dialog.pack();

        // Obtener el tamaño de la pantalla
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Calcular el centro horizontal y ajustar la posición vertical (Y)
        int centerX = (screenSize.width - dialog.getWidth()) / 2;
        int yPosition = 100; // Ajusta esta coordenada según sea necesario

        // Ajustar la posición del diálogo
        dialog.setLocation(centerX, yPosition);

        dialog.setVisible(true);
    }

    public static String[] pedirCredenciales(JLabel loading) {
        // Crear los campos de entrada con valores predeterminados
        JTextField usuarioField = new JTextField(Conexion.getUsuario());
        JPasswordField contrasenaField = new JPasswordField(Conexion.getContrasena());
        JTextField puertoField = new JTextField(Conexion.getPuerto());
        JTextField baseDatosField = new JTextField("db_cda");
        JTextField ipField = new JTextField("127.0.0.1");

        // Crea el panel de las credenciales
        int dbCredentials = panelCredenciales(usuarioField, contrasenaField, puertoField, baseDatosField, ipField);
    
        //Pregunta si accedio con las credeciales digitadas
        if (dbCredentials == JOptionPane.OK_OPTION) {
            //Si las credenciales digitadas acceden a otro IP(Que puede ser el del servidor o un equipo desconocido)
            //Entonces deben ingresarse unas credenciales adicionales que pertenecen al personal de soltelec
            if (exiteRiesgoConLaImportacion(baseDatosField.getText(), ipField.getText())) {

                //Aqui es donde pregunta las credenciales del personal de soltelec
                boolean tienePermisos = credencialesPersonalSoltelec(ipField.getText());

                //Si las credenciales del personal de soltelec son incorrectas no le dejara realizar la importacion
                if(!tienePermisos){
                    loading.setVisible(false);
                    CMensajes.mensajeError("El credenciales del personal de soltelec incorrectos");
                    throw new RuntimeException("El credenciales del personal de soltelec incorrectos");
                } 
            }

            // Retornar los valores de las credenciales de la base de datos en un arreglo
            return new String[] {
                usuarioField.getText(),
                new String(contrasenaField.getPassword()),
                puertoField.getText(),
                baseDatosField.getText(),
                ipField.getText()
            };
        } 
        //En este caso el usuario le dio cancelar a la importacion
        else {
            loading.setVisible(false);
            // Lanzar excepción si el usuario cancela
            CMensajes.mensajeCorrecto("Operacion cancelada");
            throw new RuntimeException("El usuario cancelo la operacion");
        }
    }

    private static boolean exiteRiesgoConLaImportacion(String nombreDb, String ipDb){
        //Si el nombre de la base de datos es db_cda y no se importa al 127.0.0.1,
        //existen riesgos en la importacion ya que esto puede involucrar a la base de datos del servidor
        return nombreDb.equalsIgnoreCase("db_cda") && 
        !ipDb.equalsIgnoreCase("127.0.0.1");
    }

    private static boolean credencialesPersonalSoltelec(String ipDb){
        boolean confirmacion = CMensajes.mensajePregunta(
            "Está a punto de importar el backup a una dirección IP distinta de 127.0.0.1 (localhost), con el nombre de 'db_cda'.\n"+
            "Esto puede ser riesgoso por las siguientes razones:\n" +
            "- Si la dirección IP corresponde a un servidor, podría sobreescribir la información existente.\n" +
            "- La integración de los datos podría no ser segura, comprometiendo la protección de los datos del sistema del cda.\n\n" +
            "¿Desea continuar, asumiendo los riesgos mencionados?");

        if (confirmacion) {

            JTextField usernameField = new JTextField(15);
            JPasswordField passwordField = new JPasswordField(15);

            JPanel panel2 = new JPanel(new GridLayout(2, 2));
            panel2.add(new JLabel("Usuario:"));
            panel2.add(usernameField);
            panel2.add(new JLabel("Contraseña:"));
            panel2.add(passwordField);

            int personalSoltelecCredentials = JOptionPane.showConfirmDialog(null, panel2, "Credenciales Personal Soltelec", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            String username = "";
            String password = "";

            if (personalSoltelecCredentials == JOptionPane.OK_OPTION) {
                username = usernameField.getText();
                password = new String(passwordField.getPassword());

                SuperUser superUser = new SuperUser();
                superUser.setNickName(username);
                superUser.setPassword(password);
                superUser.setLog("importación y sobreescritura directa del backup a la base de datos con la ip de: "+ipDb);
                superUser.setNitCda(Conexion.getNitCda());

                String response = Conexion.sendPost("http://api.soltelec.com:8087/api/public/superUser", superUser);

                System.out.println("Response: "+response);

                return Boolean.valueOf(response);
            }
        }

        return false;
    }

    public static int panelCredenciales(JTextField usuarioField, JPasswordField contrasenaField, JTextField puertoField, JTextField baseDatosField, JTextField ipField){
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Usuario:"));
        panel.add(usuarioField);
        panel.add(new JLabel("Contraseña:"));
        panel.add(contrasenaField);
        panel.add(new JLabel("Puerto:"));
        panel.add(puertoField);
        panel.add(new JLabel("Nombre DB:"));
        panel.add(baseDatosField);
        panel.add(new JLabel("ip:"));
        panel.add(ipField);
        
    
        // Mostrar el cuadro de diálogo
        int dbCredentials = JOptionPane.showConfirmDialog(
            null,
            panel,
            "Ingrese las credenciales de la base de datos",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        ); 

        return dbCredentials;
    }
    

    public static void Restoredbfromsqllll(String fileName) {
        try {
            String[] userAndPassword = getUserAndPassword();

            String dbUser = null; // Usuario de la base de datos
            String dbPass = null; // Contraseña del usuario

            if (userAndPassword != null) {
                dbUser = userAndPassword[0];
                dbPass = userAndPassword[1];
            }else{

            }

            // Datos de conexión
            String dbName = Conexion.getBaseDatos(); // Nombre de la base de datos
            String dbHost = Conexion.getIpServidor(); // Dirección IP del servidor (localhost o IP en red local)
            String dbPort = Conexion.getPuerto(); // Puerto de MySQL
            
            String destinationFile = "";

            // Cargar propiedades desde un archivo de configuración
            Properties properties = new Properties();
            try {
                properties.load(CargarArchivos.cargarArchivo("conexion.properties"));

                // Ruta donde se almacenará el archivo de respaldo
                destinationFile = properties.getProperty("directoriobackup") + fileName;
                JOptionPane.showMessageDialog(null, "Destino: " + destinationFile);
            } catch (IOException ex) {
                LOG.severe("Error al cargar el archivo de propiedades: " + ex.getMessage());
                return;
            }

            // Crear el comando para realizar el respaldo
            String executeCmd = String.format(
                "mysqldump -h %s -P %s -u %s -p%s %s > \"%s\"",
                dbHost, dbPort, dbUser, dbPass, dbName, destinationFile
            );

            // Imprimir el comando para verificar
            //System.out.println("Se ejecuta en cmd: " + executeCmd);

            // Ejecutar el proceso
            Process runtimeProcess = Runtime.getRuntime().exec(new String[]{"bash", "-c", executeCmd});
            int processComplete = runtimeProcess.waitFor();

            // Verificar si el proceso fue exitoso
            if (processComplete == 0) {
                JOptionPane.showMessageDialog(null, "Restauracion de la base de datos exitoso");
            } else {
                JOptionPane.showMessageDialog(null, "Falló el proceso de respaldo de la base de datos");
            }
        } catch (IOException | InterruptedException ex) {
            LOG.severe("Error durante el respaldo: " + ex.getMessage());
        }
    }

    public static String[] getUserAndPassword() {
        // Crear el panel
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        
        // Etiquetas y campos de entrada
        JLabel userLabel = new JLabel("Usuario:");
        JTextField userField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Contraseña:");
        JPasswordField passwordField = new JPasswordField(20);

        // Añadir los componentes al panel
        panel.add(userLabel);
        panel.add(userField);
        panel.add(passwordLabel);
        panel.add(passwordField);

        // Mostrar el cuadro de diálogo
        int option = JOptionPane.showConfirmDialog(
            null,
            panel,
            "Credenciales de la Base de Datos",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        // Verificar si el usuario presionó OK
        if (option == JOptionPane.OK_OPTION) {
            // Retornar usuario y contraseña como un array
            return new String[]{userField.getText(), new String(passwordField.getPassword())};
        } else {
            // Retornar null si se cancela
            return null;
        }
    }

    /* public static void Restoredbfromsql(String s) {
        try {
            String dbName = "";
            String port = "";
            String dbUser = "";
            String dbPass = "";
            String sourceFile = "";
    
            Properties properties = new Properties();
            try {
                // Cargar las propiedades desde el archivo "conexion.properties"
                properties.load(CargarArchivos.cargarArchivo("conexion.properties"));
                dbName = Conexion.getBaseDatos(); // Obtener nombre de la base de datos
                port = Conexion.getPuerto(); // Obtener el puerto de la base de datos
                dbUser = Conexion.getUsuario(); // Obtener el usuario
                dbPass = Conexion.getContrasena(); // Obtener la contraseña
                
                // Ruta del archivo de respaldo
                sourceFile = properties.getProperty("directoriobackup") + s;
                JOptionPane.showMessageDialog(null, "source " + sourceFile);
            } catch (IOException ex) {
                LOG.severe(ex.getMessage());
            }
    
            // Crear el comando para ejecutar en el sistema, sin necesidad de especificar la ruta completa de mysql
            String executeCmd = "mysql -u " + dbUser + " -p" + dbPass + " " + dbName + " < \"" + sourceFile + "\"";
            
            // Imprimir el comando para verificar la ejecución
            System.out.println("Se ejecuta en cmd: " + executeCmd);
            
            // Ejecutar el proceso
            Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();
    
            // Verificar si el proceso fue exitoso
            if (processComplete == 0) {
                JOptionPane.showMessageDialog(null, "Restauración de la base de datos exitosa");
            } else {
                JOptionPane.showMessageDialog(null, "Falló el proceso de restauración de la base de datos");
            }
        } catch (IOException | InterruptedException | HeadlessException ex) {
            LOG.severe(ex.getMessage());
        }
    } */

    
    /* public static void Restoredbfromsql(String parametrosr) {
        // Crear un diálogo de progreso indeterminado
        JOptionPane pane = new JOptionPane("Restaurando base de datos...", JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
        JDialog dialog = pane.createDialog("Mensaje");

        // Crear el hilo para realizar la restauración de la base de datos
        Thread restoreThread = new Thread(() -> {
            String dbName = "";
            String dbUser = "";
            String dbPass = "";
            String sourceFile = "";

            try {
                Properties properties = new Properties();
                properties.load(CargarArchivos.cargarArchivo("conexion.properties"));
                dbName = Conexion.getBaseDatos();
                dbUser = Conexion.getUsuario();
                dbPass = Conexion.getContrasena();
                sourceFile = properties.getProperty("directoriobackup") + parametrosr;
                JOptionPane.showMessageDialog(null, "Source file: " + sourceFile);

                // Comando para iniciar mysql con credenciales
                String[] executeCmd = {"mysql", "-u", dbUser, "-p" + dbPass, dbName};

                ProcessBuilder processBuilder = new ProcessBuilder(executeCmd);
                processBuilder.redirectErrorStream(true);
                Process process = processBuilder.start();

                // Enviar el archivo SQL como entrada al proceso
                try (OutputStream outputStream = process.getOutputStream();
                    FileInputStream fis = new FileInputStream(sourceFile);
                    BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()))) {

                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = fis.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    outputStream.flush();

                    // Leer salida del proceso
                    String line;
                    System.out.println("Salida del proceso:");
                    while ((line = stdInput.readLine()) != null) {
                        System.out.println(line);
                    }
                }

                int processComplete = process.waitFor();

                // Verificar el resultado de la restauración y actualizar la UI en el EDT
                SwingUtilities.invokeLater(() -> {
                    dialog.dispose();
                    if (processComplete == 0) {
                        JOptionPane.showMessageDialog(null, "Restauración de la base de datos exitosa", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Falló el proceso de restauración de la base de datos", "Mensaje", JOptionPane.ERROR_MESSAGE);
                    }
                });
            } catch (IOException | InterruptedException ex) {
                LOG.severe(ex.getMessage());
                SwingUtilities.invokeLater(() -> {
                    dialog.dispose();
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
                });
            }
        });

        // Iniciar el hilo de restauración
        restoreThread.start();

        // Mostrar la barra de progreso indeterminada
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        pane.add(progressBar);
        dialog.pack();
        dialog.setVisible(true);
    } */

}