/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.dao;

import com.soltelec.servidor.conexion.Conexion;
import com.soltelec.servidor.utils.CMensajes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author SOLTELEC
 */
public class Conexion2 {

    public static String password = "passfabian";
    public static String usuario = "fabian";

    public static void cargarConexion() {

   

    }//end of method cargarUrl

    public static String getMysqlBinPath(String db) {


        Connection con;
        Statement st = null;
        ResultSet res;
        cargarConexion();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            CMensajes.mostrarExcepcion(e);
        }
        try {
           // con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db, usuario, password);

            // con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db, usuario, password);

            con = (Connection) DriverManager.getConnection("jdbc:mysql://" + Conexion.getIpServidor() + ":" + Conexion.getPuerto() + "/" + Conexion.getBaseDatos(), Conexion.getUsuario(), Conexion.getContrasena());

            st = (Statement) con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

        } catch (Exception e) {

            System.out.print("I am here yaaar");
            CMensajes.mostrarExcepcion(e);
        }



        String a = "";


        try {
            res = st.executeQuery("select @@basedir");
            while (res.next()) {
                a = res.getString(1);
            }
        } catch (Exception e) {
            CMensajes.mostrarExcepcion(e);
        }
        a = a + "bin\\";
        System.err.println("Mysql path is :" + a);
        return a;

    }
    //  public Connection getConexion
}
