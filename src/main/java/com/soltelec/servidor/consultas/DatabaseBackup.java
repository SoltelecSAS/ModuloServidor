package com.soltelec.servidor.consultas;

import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.soltelec.servidor.exceptions.BackupException;
import com.soltelec.servidor.utils.CMensajes;

public class DatabaseBackup {

    private DatabaseBackup() {
        throw new IllegalStateException("Utility class");
    }

    private static final Logger logger = LoggerFactory.getLogger(DatabaseBackup.class);

    /* private static String url = Conexion.getUrl();
    private static String dbUser = Conexion.getUsuario();
    private static String dbName = Conexion.getBaseDatos();
    private static String dbHost = Conexion.getIpServidor();
    private static String dbPort = Conexion.getPuerto(); */

    /* public static Integer createBackup() {
        // Crear los campos de entrada con valores predeterminados
        JTextField usuarioField = new JTextField(Conexion.getUsuario());
        JPasswordField contrasenaField = new JPasswordField(Conexion.getContrasena());
        JTextField puertoField = new JTextField(Conexion.getPuerto());
        JTextField baseDatosField = new JTextField(Conexion.getBaseDatos());
        JTextField ipField = new JTextField(Conexion.getIpServidor());


        int dbCredentials = panelCredenciales(usuarioField, contrasenaField, puertoField, baseDatosField, ipField);

        if (dbCredentials == JOptionPane.OK_OPTION) {
            return createBackups(usuarioField.getText(), new String(contrasenaField.getPassword()), puertoField.getText(), baseDatosField.getText(), ipField.getText());
        }else{
            CMensajes.mensajeCorrecto("Operacion cancelada");
            throw new RuntimeException("Operacion cancelada");
        }
    } */


    public static Integer createBackups(String usuario, String contrasena, String puerto, String baseDatos, String ip) {

        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();

        //System.out.println( db);
        
        // Definir el formato deseado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        
        // Formatear la fecha en el formato deseado
        String fechaActualStr = fechaActual.format(formatter);
    
        // Obtener la ruta del directorio actual
        String currentDirectory = System.getProperty("user.dir");


        //String currentDirectory = System.getProperty("user.dir");
    
        // Nombre del archivo de backup
        String backupFileName = "backup.bat";
    
        // Ruta completa del archivo de backup
        String backupPath = currentDirectory + File.separator + backupFileName;
    
        // Crear el contenido del archivo de lote con las opciones necesarias
        String batchContent = String.format(
            "mysqldump --defaults-extra-file=mysql.cnf --hex-blob --host=%s --port=%s %s > C:\\\\BACKUPS\\\\%s.sql",
            ip, puerto, baseDatos, fechaActualStr);
    
        BufferedWriter writer = null;
        String mysqlCnfPath = null;
        try {
            // Crear el archivo de opciones de configuración
            String mysqlCnfContent = String.format("[client]%nuser=%s%npassword=%s%n", usuario, "'" + contrasena + "'");
            mysqlCnfPath = currentDirectory + File.separator + "mysql.cnf";
            writer = new BufferedWriter(new FileWriter(mysqlCnfPath));
            System.out.println("mysqlcontent: "+mysqlCnfContent);
            writer.write(mysqlCnfContent);
            writer.close();
    
            // Crear el archivo de lote
            writer = new BufferedWriter(new FileWriter(backupPath));
            writer.write(batchContent);
            writer.close();
    
            // Ejecutar el archivo de lote sin abrir una nueva ventana de cmd
            Process process = Runtime.getRuntime().exec("cmd /c " + backupPath);
    
            // Crear hilos para leer el flujo de salida y error del proceso
            new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println("OUTPUT mysqlDump: " + line); // Muestra la salida del proceso
                    }
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }).start();
    
            new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.err.println("ERROR mysqlDump: " + line); // Muestra los errores del proceso
                        if (!line.contains("Warning")) {
                            CMensajes.mensajeError("ERROR mysqlDump: " + line);
                        }
                        
                    }
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }).start();
    
            // Esperar a que el proceso termine
            int exitCode = process.waitFor();
    
            // Verificar el resultado
            if (exitCode == 0) {
                logger.info("Backup creado exitosamente");
            } else {
                logger.info("Error al crear el backup. Código de salida: {}", exitCode);
            }
    
            // Eliminar el archivo mysql.cnf después de que se haya completado el backup
            File mysqlCnfFile = new File(mysqlCnfPath);
    
            if (mysqlCnfFile.exists()) {
                @SuppressWarnings("java:S4042")
                boolean deleted = mysqlCnfFile.delete();
                if (deleted) {
                    logger.info("Archivo mysql.cnf eliminado exitosamente");
                } else {
                    logger.info("No se pudo eliminar el archivo mysql.cnf");
                }
            }
    
            return exitCode;
    
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            // Asegurarse de cerrar el writer y eliminar el archivo mysql.cnf si ocurre un error
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (mysqlCnfPath != null) {
                File mysqlCnfFile = new File(mysqlCnfPath);
                if (mysqlCnfFile.exists()) {
                    mysqlCnfFile.delete();
                }
            }
        }
    
        return null;
    }

    public static int importBackup(String backupFilePath, String dbName, String usuario, String dbPassword, String puerto, String ip) {
        Integer exitCode = null;


        try {
            // Obtener la ruta del directorio actual
            String currentDirectory = System.getProperty("user.dir");
    
            // Ruta completa del archivo de backup
            String backupPath = "import.bat";
    
            // Crear el contenido del archivo de lote
            String batchContent = String.format("mysql --defaults-extra-file=mysql.cnf --host=%s --port=%s %s  < \"%s\"",
                    ip, puerto, dbName, backupFilePath);
    
            // Crear el archivo de opciones de configuración
            String mysqlCnfContent = String.format("[client]%nuser=%s%npassword=\"%s\"%n", usuario, dbPassword);
            String mysqlCnfPath = currentDirectory + File.separator + "mysql.cnf";
            BufferedWriter writer = new BufferedWriter(new FileWriter(mysqlCnfPath));
            writer.write(mysqlCnfContent);
            writer.close();
    
            // Crear el archivo de lote
            writer = new BufferedWriter(new FileWriter(backupPath));
            writer.write(batchContent);
            writer.close();
    
            // Ejecutar el archivo de lote sin abrir una nueva ventana de cmd
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", backupPath);
            processBuilder.redirectErrorStream(true); // Combinar stdout y stderr
            Process process = processBuilder.start();
    
            // Capturar la salida del proceso
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();
    
            System.out.println("Recordar que 127.0.0.1 y localhost son exactamente lo mismo");
            while ((line = reader.readLine()) != null) {
                
                System.out.println(line); // Mostrar cada línea en la consola
                output.append(line).append(System.lineSeparator());
            }
    
            // Esperar a que el proceso termine
            exitCode = process.waitFor();
    
            // Verificar el resultado
            if (exitCode == 0) {
                logger.info("Importado exitosamente");
            } else {
                logger.info("Error al importar el backup. Error: {}", line);
                JOptionPane.showMessageDialog(null, "Importacion cancelada. \nOcurrio un error durante la importacion.\nerror:\n "+line, "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            }
    
            // Eliminar el archivo mysql.cnf después de que se haya completado el backup
            File mysqlCnfFile = new File(mysqlCnfPath);
            if (mysqlCnfFile.exists()) {
                boolean deleted = mysqlCnfFile.delete();
                if (deleted) {
                    logger.info("Archivo mysql.cnf eliminado exitosamente");
                } else {
                    logger.info("No se pudo eliminar el archivo mysql.cnf");
                }
            }

    
            return exitCode;
    
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    
        throw new BackupException("No se pudo hacer un backup");
    }

    public static void eliminarNoAutoCreateUser(String ubicacion) {
        File archivo = new File(ubicacion);
    
        if (!archivo.exists()) {
            System.err.println("El archivo no existe: " + ubicacion);
            return;
        }
    
        File archivoTemporal = new File(archivo.getParent(), "temp_backup.sql");
    
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo));
             BufferedWriter writer = new BufferedWriter(new FileWriter(archivoTemporal))) {
    
            String linea;
    
            while ((linea = reader.readLine()) != null) {
                // Paso 1: Eliminar ",NO_AUTO_CREATE_USER"
                String lineaModificada = linea.replaceAll(",NO_AUTO_CREATE_USER", "");

                // Paso 2: Eliminar "NO_AUTO_CREATE_USER" restante
                lineaModificada = lineaModificada.replaceAll("NO_AUTO_CREATE_USER,", "");
    
                // Paso 3: Eliminar "NO_AUTO_CREATE_USER" restante (sin coma)
                lineaModificada = lineaModificada.replaceAll("NO_AUTO_CREATE_USER", "");
    
                writer.write(lineaModificada);
                writer.newLine();
            }
    
            System.out.println("El archivo ha sido procesado correctamente.");
        } catch (IOException e) {
            System.err.println("Ocurrió un error al procesar el archivo: " + e.getMessage());
            return;
        }
    
        // Reemplazar el archivo original con el archivo temporal
        if (archivo.delete() && archivoTemporal.renameTo(archivo)) {
            System.out.println("El archivo original se ha reemplazado correctamente.");
        } else {
            System.err.println("No se pudo reemplazar el archivo original.");
        }
    }

    public static Integer importBackup(String nameBackup, String[] credentials) throws IOException {
        String ubicacion = "C:\\\\BACKUPS\\\\" + nameBackup;
    
        // Si la ubicación es nula o vacía, significa que el usuario canceló la selección.
        if (ubicacion == null || ubicacion.isEmpty()) {
            return 1;  // Retornar 1 si se cancela.
        }
    
        String usuario = credentials[0];
        String password = credentials[1];
        String puerto = credentials[2];
        String dbName = credentials[3];
        String dbIp = credentials[4];
    
        String url = "jdbc:mysql://"+dbIp+":" + puerto + "/?zeroDateTimeBehavior=convertToNull";
    
        String consulta = "SHOW databases";
        boolean isDbExist = false;
    
        try (Connection conexion = DriverManager.getConnection(url, usuario, password);
             PreparedStatement consultaDagma = conexion.prepareStatement(consulta);
             Statement stmt = conexion.createStatement()) {
    
            // Configurar el sql_mode compatible antes de proceder
            /* String ajustarSqlMode = "SET GLOBAL sql_mode = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION';";
            System.out.println("variable global 'NO_AUTO_CREATE_USER' seteada");
            stmt.executeUpdate(ajustarSqlMode); */

            // Ajustar el archivo SQL para eliminar o modificar sql_mode
            eliminarNoAutoCreateUser(ubicacion);
    
            // Revisar si la base de datos existe
            String database = null;
            String useDatabase = null;
    
            try (ResultSet rc = consultaDagma.executeQuery()) {
                while (rc.next()) {
                    database = rc.getString("Database").toLowerCase();
                    logger.info(database);
                    if (database.equals(dbName)) {
                        isDbExist = true;
                        break;
                    }
                }
            }
    
            if (!isDbExist) {
                String createDatabase = "CREATE DATABASE " + dbName;
                database = dbName;
                stmt.executeUpdate(createDatabase);
            }
    
            useDatabase = "USE " + database;
            stmt.executeUpdate(useDatabase);
    
            return importBackup(ubicacion, database, usuario, password, puerto, dbIp);
    
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al importar a la base de datos local.\nSi el mensaje dice 'Access denied' le recomendamos que revise la contraseña y/o usuario de la base de datos local,\nde lo contrario contactese con soporte soltelec. \nERROR: \n" + e.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        throw new BackupException("No se pudo importar");
    }

    private static void ajustarArchivoSql(String archivoSql) throws IOException {
        File archivoOriginal = new File(archivoSql);
        File archivoTemporal = new File(archivoSql + ".tmp");
    
        // Leer línea por línea y escribir solo las necesarias en el archivo temporal
        try (BufferedReader reader = new BufferedReader(new FileReader(archivoOriginal));
             BufferedWriter writer = new BufferedWriter(new FileWriter(archivoTemporal))) {
    
            String linea;
            while ((linea = reader.readLine()) != null) {
                // Ignorar líneas que contengan "SET sql_mode"
                if (linea.contains("SET sql_mode")) {
                    logger.info("Eliminando línea: " + linea);
                    continue;
                }
                writer.write(linea);
                writer.newLine();
            }
        }
    
        // Reemplazar el archivo original con el archivo temporal
        if (!archivoOriginal.delete()) {
            throw new IOException("No se pudo eliminar el archivo original: " + archivoOriginal.getAbsolutePath());
        }
    
        if (!archivoTemporal.renameTo(archivoOriginal)) {
            throw new IOException("No se pudo renombrar el archivo temporal a: " + archivoOriginal.getAbsolutePath());
        }
    }
    

    private static String selectPathBackup() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona un archivo");
        
        // Muestra el diálogo para seleccionar un archivo
        int seleccion = fileChooser.showOpenDialog(null);
        
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            return file.getAbsolutePath();
        } else {
            // Si el usuario cancela la selección, retorna null
            return null;
        }
    }
}