package com.soltelec.servidor.consultas;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JFileChooser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.soltelec.servidor.conexion.Conexion;
import com.soltelec.servidor.exceptions.BackupException;

public class DatabaseBackup {

    private DatabaseBackup() {
        throw new IllegalStateException("Utility class");
    }

    private static final Logger logger = LoggerFactory.getLogger(DatabaseBackup.class);

    private static String url = Conexion.getUrl();
    private static String password = Conexion.getContrasena();
    private static String dbUser = Conexion.getUsuario();
    private static String dbName = Conexion.getBaseDatos();
    private static String dbHost = Conexion.getIpServidor();
    private static String dbPort = Conexion.getPuerto();

    public static Integer createBackup() {

        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();
        
        // Definir el formato deseado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        
        // Formatear la fecha en el formato deseado
        String fechaActualStr = fechaActual.format(formatter);

        // Obtener la ruta del directorio actual
        String currentDirectory = System.getProperty("user.dir");

        // Nombre del archivo de backup
        String backupFileName = "backup.bat";

        // Ruta completa del archivo de backup
        String backupPath = currentDirectory + File.separator + backupFileName;

        // Crear el contenido del archivo de lote
        String batchContent = String.format("mysqldump --defaults-extra-file=mysql.cnf --host=%s --port=%s %s > "+fechaActualStr+".sql",
                dbHost, dbPort, dbName);

        try {
            // Crear el archivo de opciones de configuración
            String mysqlCnfContent = String.format("[client]%nuser=%s%npassword=%s%n", dbUser, "'"+Conexion.getContrasena()+"'");
            String mysqlCnfPath = currentDirectory + File.separator + "mysql.cnf";
            BufferedWriter writer = new BufferedWriter(new FileWriter(mysqlCnfPath));
            writer.write(mysqlCnfContent);
            writer.close();

            // Crear el archivo de lote
            writer = new BufferedWriter(new FileWriter(backupPath));
            writer.write(batchContent);
            writer.close();

            // Ejecutar el archivo de lote sin abrir una nueva ventana de cmd
            Process process = Runtime.getRuntime().exec("cmd /c " + backupPath);

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
        }

        return null;
    }

    public static int importBackup(String backupFilePath, String dbName) {
        Integer exitCode = null;
        try {
            // Obtener la ruta del directorio actual
            String currentDirectory = System.getProperty("user.dir");

            // Ruta completa del archivo de backup
            String backupPath = "import.bat";

            // Crear el contenido del archivo de lote
            String batchContent = String.format("mysql --defaults-extra-file=mysql.cnf --host=%s --port=%s %s < "+ backupFilePath,
                    dbHost, dbPort, dbName);

            // Crear el archivo de opciones de configuración
            String mysqlCnfContent = String.format("[client]%nuser=%s%npassword=%s%n", dbUser, password);
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
            Process process = processBuilder.start();

            // Esperar a que el proceso termine
            exitCode = process.waitFor();

            // Verificar el resultado
            if (exitCode == 0) {
                logger.info("Importado exitosamente");
            } else {
                logger.info("Error al importar el backup. Código de salida: {}", exitCode);
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
            
        }
        
        throw new BackupException("No se pudo hacer un backup");
    }

    public static Integer importBackup(String dbName) {
        String ubicacion = selectPathBackup();

        String consulta = "SHOW databases";
        boolean isDbExist = false;

        try (Connection conexion = DriverManager.getConnection(url, dbUser, password);
             PreparedStatement consultaDagma = conexion.prepareStatement(consulta);
             Statement stmt = conexion.createStatement()) {

            //database name
            String database = null;

            //choice database
            String useDatabase = null;

            //rc representa el resultado de la consulta
            try (ResultSet rc = consultaDagma.executeQuery()) {
                while (rc.next()) {

                    database = rc.getString("Database").toLowerCase();
                    logger.info(database);
                    if(database.equals(dbName.toLowerCase())){
                        isDbExist = true;
                        break;
                    }
                }
            }

            if (!isDbExist) {
                String createDatabase = "CREATE DATABASE "+ dbName.toLowerCase();
                database = dbName.toLowerCase();
                stmt.executeUpdate(createDatabase);
            }

            useDatabase = "USE "+database;
            stmt.executeUpdate(useDatabase);

            return importBackup(ubicacion, database);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new BackupException("No se pudo importar");
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