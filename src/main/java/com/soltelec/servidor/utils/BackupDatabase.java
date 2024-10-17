
package com.soltelec.servidor.utils;

import com.soltelec.servidor.conexion.Conexion;
import com.soltelec.servidor.igrafica.FrmBackup;
import com.sun.org.apache.xml.internal.serializer.ToSAXHandler;
import groovy.transform.ToString;
import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static jdk.nashorn.internal.objects.NativeRegExp.source;

public class BackupDatabase {

    static String dbName = "";
    static String dbUser = "";
    static String dbPass = "";
    static String folderPath = "";
    static String sourceFile = "";
    private static final Logger LOG = Logger.getLogger(BackupDatabase.class.getName());
    private static String versionmysql;
    private static String ubackup;
    private static String port;
    private static String dirBin;

    public static void main(String args[]) throws InterruptedException {
        //Restoredbfromsql("bd_saldana.sql");
        //Backupdbtosql();

    }

    public static void Backupdbtosql(JLabel loading) throws InterruptedException {
        loading.setVisible(true);
        try {
                
            Properties properties = new Properties();
            try {
                properties.load(CargarArchivos.cargarArchivo("conexion.properties"));
              
                 
                dbName = Conexion.getBaseDatos();
                port =  Conexion.getPuerto();
                dbUser = Conexion.getUsuario();
                //Este usuario es usado para la version de mysql57 ya que no tiene password
                ubackup = Conexion.getUsuario();
                dbPass = Conexion.getContrasena();
                folderPath = properties.getProperty("directoriobackup");
                versionmysql = properties.getProperty("versionmysql");
                dirBin = properties.getProperty("directorioMysql");
            } catch (IOException ex) {
                LOG.severe(ex.getMessage());
            } 
            String nombreArchivo = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            File f1 = new File(folderPath);
            if (!f1.exists()) {
                f1.mkdir();
            }
            String savePath = folderPath + nombreArchivo + ".sql";
            String executeCmd;
            System.out.println("savepath:" +savePath);
           System.out.println("en dir bin es: "+ dirBin); 
            if (versionmysql.equals("mysql57")) {
                System.out.println("entre en la version 5.7");
                executeCmd = "C:\\Program Files\\MySQL\\MySQL Server 5.7\\bin\\mysqldump --opt -u"+dbUser+ " -p"+ dbPass + " -B "+ dbName + " -r " + savePath;
               // System.out.println("lo que se ejecuta en el cmd: "+ executeCmd);
                System.out.println("Finalice GBackup de la Base de Datos");
            } else {
                
                System.out.println("entre en la version 5.6 en el" + dirBin + "Gmysqldump -u " + dbUser + " -p*****" + " --databases " + dbName + " > " + savePath);
               // executeCmd = "C:\\Program Files\\MySQL\\MySQL Server 5.6\\bin\\mysqldump --opt -u"+dbUser+ " -p"+ dbPass + " -B "+ dbName + " -r " + savePath;
               executeCmd = "C:\\Program Files\\MySQL\\MySQL Server 5.6\\bin\\mysqldump  -u "+dbUser+ " -p "+ dbPass + " --routines "+ dbName + " > " + savePath;
               
               // System.out.println("lo que se ejecuta en el cmd: "+ executeCmd);
               // System.out.println("Finalice GBackup de la Base de Datos");
             }                       
            
            System.out.println("lo que se ejecuta en el cmd "+ executeCmd);
            Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            
            int processComplete = runtimeProcess.waitFor();
            InputStream inputstream = runtimeProcess.getErrorStream();
            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
            System.out.println(bufferedreader.readLine());
            do{
                  
                if (processComplete == 0) {                                     
                    JOptionPane.showMessageDialog(null, "BackUp finalizado correctamente");
                } else {
                   JOptionPane.showMessageDialog(null, "Fallo el proceso, Procces completado 0");
                   break;
                }
            }while(processComplete!=0);
            loading.setVisible(false);
             
            
        } catch (IOException ex) {
            LOG.severe(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Fallo el proceso Ioexception " + ex.getMessage());
        }
    }

    public static void Restoredbfromsql(String s) {
        try {
            String dbName="";

            Properties properties = new Properties();
            try {
                properties.load(CargarArchivos.cargarArchivo("conexion.properties"));
                dbName = Conexion.getBaseDatos();
                port = Conexion.getPuerto();
                dbUser = Conexion.getUsuario();
                dbPass = Conexion.getContrasena();
                 
                sourceFile = (properties.getProperty("directoriobackup")) + s;
                 JOptionPane.showMessageDialog(null, "source "+sourceFile);
                dirBin = properties.getProperty("directorioMysql");
            } catch (IOException ex) {
                LOG.severe(ex.getMessage());
            }
            //creardb();
          //  String executeCmd = "" + dirBin + "mysql -u " + dbUser + " -p" + dbPass +" -h"+Conexion.getIpServidor() +" "+ dbName + " -e source  " + sourceFile;               
           
            String executeCmd =dirBin + "mysql  -u" + dbUser+ "-p" + dbPass + " "+dbName+" < "+ sourceFile;
            //se ejecuta en cmd: C:\Program Files\MySQL\MySQL Server 5.6\bin\mysql -P 3306db_cda-uroot-p50lt3l3c545-e C:\BACKUPS\botanico.sql  sourceFile;
           // String executeCmd ="mysql -u " + dbUser+ dbName + "-u" + dbUser, "-p" + dbPass, "-e "+ source " + sourceFile;
            System.out.println("se ejecuta en cmd: "+executeCmd);
           
            Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();
            if (processComplete == 0) {
                JOptionPane.showMessageDialog(null, "Reestauracion de la base de datos exitosa");
            } else {
                JOptionPane.showMessageDialog(null, "Fallo el proceso de creacion de BackUp");
            }
        } catch (IOException | InterruptedException | HeadlessException ex) {
            LOG.severe(ex.getMessage());
        }

    }

    private static void creardb() {
        try {
            Properties properties = new Properties();
            properties.load(CargarArchivos.cargarArchivo("conexion.properties"));
            dirBin = properties.getProperty("directorioMysql");
            String[] executeCmd2 = new String[]{"" + dirBin + "gmysql", "-P " + port, "-u" + dbUser, "-p" + dbPass, "-e" + " DROP DATABASE  " + dbName + " "};
            Process runtimeProcess2 = Runtime.getRuntime().exec(executeCmd2);
            int processComplete = runtimeProcess2.waitFor();
             if (processComplete == 0) {
                JOptionPane.showMessageDialog(null, "SE HIZO UN DROP A LA BD");
            } else {
                JOptionPane.showMessageDialog(null, "Fallo el proceso");
            }
            executeCmd2 = new String[]{"" + dirBin + "gmysql", "-P " + port, "-u" + dbUser, "-p" + dbPass, "-e" + " CREATE DATABASE " + dbName + " "};
            runtimeProcess2 = Runtime.getRuntime().exec(executeCmd2);
            processComplete = runtimeProcess2.waitFor();
              if (processComplete == 0) {
                JOptionPane.showMessageDialog(null, "LOGRE CREAR LA BD, y EMPIEZO A IMPLEMETAR EL RESTORED BD ");
            } else {
                JOptionPane.showMessageDialog(null, "Fallo el proceso");
            }
        } catch (IOException | InterruptedException e) {
            LOG.severe(e.getMessage());
        }
    }

}

//package com.soltelec.servidor.utils;
//
//import java.awt.HeadlessException;
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Properties;
//import java.util.logging.Logger;
//import javax.swing.JOptionPane;
//
//public class BackupDatabase {
//
//    static String dbName = "";
//    static String dbUser = "";
//    static String dbPass = "";
//    static String folderPath = "";
//    static String sourceFile = "";
//    private static final Logger LOG = Logger.getLogger(BackupDatabase.class.getName());
//    private static String versionmysql;
//    private static String ubackup;
//    private static String port;
//    private static String urljdbc;
//    private static String defaultFile;
//
//    public static void main(String args[]) {
//        crearArchivoCnf();
//        Restoredbfromsql("defectos.sql");
//    }
//
//    public static void Backupdbtosql() {
//        try {
//
//            Properties properties = new Properties();
//            try {
//                properties.load(CargarArchivos.cargarArchivo("conexion.properties"));
//                dbName = properties.getProperty("namebd");
//                port = properties.getProperty("port");
//                dbUser = properties.getProperty("usuario");
//                urljdbc = properties.getProperty("urljdbc");
//                //Este usuario es usado para la version de mysql57 ya que no tiene password
//                ubackup = properties.getProperty("usuariobackup");
//                dbPass = properties.getProperty("password");
//                folderPath = properties.getProperty("directoriobackup");
//                versionmysql = properties.getProperty("versionmysql");
//                defaultFile = System.getProperty("user.dir").concat("\\conf.cnf");
//            } catch (IOException ex) {
//                LOG.severe(ex.getMessage());
//            }
//
//            crearArchivoCnf();
//            String nombreArchivo = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
//            File f1 = new File(folderPath);
//            if (!f1.exists()) {
//                f1.mkdir();
//            }
//            String savePath = folderPath + nombreArchivo + ".sql";
//            String executeCmd;
//            executeCmd = String.format("mysql "
//                    + "--defaults-file=%s "
//                    + "--protocol=tcp "
//                    + "--host=%s "
//                    + "--user=%s "
//                    + "--port=%s  "
//                    + "--default-character-set=utf8 "
//                    + "--comments < %s", defaultFile, urljdbc, ubackup, dbUser, port, savePath);
////                executeCmd = "mysqldump -u" + ubackup + dbName + " -r " + savePath;
//            Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
//            int processComplete = runtimeProcess.waitFor();
//            InputStream inputstream = runtimeProcess.getErrorStream();
//            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
//            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
//            System.out.println(bufferedreader.readLine());
//            if (processComplete == 0) {
//                JOptionPane.showMessageDialog(null, "Se completo el proceso");
//            } else {
//                JOptionPane.showMessageDialog(null, "Fallo el proceso");
//            }
//        } catch (IOException | InterruptedException ex) {
//            LOG.severe(ex.getMessage());
//            JOptionPane.showMessageDialog(null, "Fallo el proceso " + ex.getMessage());
//        }
//    }
//
//    public static void Restoredbfromsql(String s) {
//        try {
//            Properties properties = new Properties();
//            try {
//                properties.load(CargarArchivos.cargarArchivo("conexion.properties"));
//                dbName = properties.getProperty("namebd");
//                port = properties.getProperty("port");
//                dbUser = properties.getProperty("usuario");
//                urljdbc = properties.getProperty("urljdbc");
//                //Este usuario es usado para la version de mysql57 ya que no tiene password
//                ubackup = properties.getProperty("usuariobackup");
//                dbPass = properties.getProperty("password");
//                folderPath = properties.getProperty("directoriobackup");
//                versionmysql = properties.getProperty("versionmysql");
//                defaultFile = System.getProperty("user.dir").concat("\\conf.cnf");
//                sourceFile = (properties.getProperty("directoriobackup")) + s;
//            } catch (IOException ex) {
//                LOG.severe(ex.getMessage());
//            }
//            String executeCmd = String.format("cmd /c start /wait mysql "
//                    + "--defaults-file=%s "
//                    + "--protocol=tcp "
//                    + "--host=%s "
//                    + "--user=%s "
//                    + "--port=%s  "
//                    + "--default-character-set=utf8 "
//                    + "--comments < %s", defaultFile, urljdbc, dbUser, port, sourceFile);
//            Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
//            int processComplete = runtimeProcess.waitFor();
//            if (processComplete == 0) {
//                JOptionPane.showMessageDialog(null, "Se completo el proceso");
//            } else {
//                JOptionPane.showMessageDialog(null, "Fallo el proceso");
//            }
//        } catch (IOException | InterruptedException | HeadlessException ex) {
//            LOG.severe(ex.getMessage());
//        }
//
//    }
//
//    private static void creardb() {
//        try {
//            String[] executeCmd2 = new String[]{"mysql", "-P " + port, "-u" + dbUser, "-p" + dbPass, "-e" + " CREATE DATABASE " + dbName + " "};
//            Process runtimeProcess2 = Runtime.getRuntime().exec(executeCmd2);
//            int processComplete = runtimeProcess2.waitFor();
//            System.out.println(processComplete);
//        } catch (IOException | InterruptedException e) {
//            LOG.severe(e.getMessage());
//        }
//    }
//
//    private static void crearArchivoCnf() {
//        try {
//            String data = "[client]\n"
//                    + "password=\"admin\"";
//            File file = new File("conf.cnf");
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//            //true = append file
//            FileWriter fileWritter = new FileWriter(file.getName(), true);
//            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
//            bufferWritter.write(data);
//            bufferWritter.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//}
