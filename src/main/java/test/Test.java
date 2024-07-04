/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.soltelec.servidor.conexion.PersistenceController;
import com.soltelec.servidor.dao.CalibracionDosPuntosJpaController;
import com.soltelec.servidor.dao.CalibracionesJpaController;
import com.soltelec.servidor.igrafica.ReporteCornare;
import com.soltelec.servidor.model.CalibracionDosPuntos;
import com.soltelec.servidor.model.Calibraciones;
import com.soltelec.servidor.model.Equipos;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author user
 */
public class Test {

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha1 = sdf.parse("2022-12-05");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha2 = sdf.parse("2022-12-10");
        System.out.println("fecha 1: " + fecha1);
        System.out.println("fecha 2:" + fecha2);
        List<Date> fechas = Test.llamarFechaCalibraciones(fecha1, fecha2);
        List<Date> auxfechas = new ArrayList();
        for (int i = 0; i < fechas.size() - 1; i++) {
            auxfechas.add(fechas.get(i));
            Date d = Test.dateMas1(fechas.get(i));
            auxfechas.add(d);
            System.out.println("fechas cal:" + fechas.get(i));
        }
        for (Date date : auxfechas) {
            System.out.println("fechas:" + date);
        }
    }

    public static Date dateMas1(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // Configuramos la fecha que se recibe
        calendar.add(Calendar.HOUR, 24);
        return calendar.getTime();
    }

    private static List<Date> llamarFechaCalibraciones(Date fecha1, Date fecha2) throws NullPointerException {
        CalibracionesJpaController calJpa = new CalibracionesJpaController();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("yyyy/MM/dd hh:mm");
        List<Date> fechas = new ArrayList();
        try {
            List listaCalibraciones = calJpa.findCalibracionesByFecha(fecha1, fecha2);
            for (Calibraciones cal : (List<Calibraciones>) listaCalibraciones) {
                fechas.add(cal.getFecha());
                System.out.println("------------------ sdasffasdfsafd   " + cal.getFecha());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return fechas;
    }
    
}
