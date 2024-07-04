package com.soltelec.servidor.utils;

import com.soltelec.servidor.model.Software;
import com.soltelec.servidor.conexion.PersistenceController;
import com.soltelec.servidor.dao.SoftwareJpaController;
import com.soltelec.servidor.model.CalibracionDosPuntos;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.table.DefaultTableModel;

/**
 * Clase encargada de cargar las calibraciones que pertenecen a una prueba.
 *
 * @author Luis Berna
 * @fecha 20/07/2015
 */
public class CargarCalibracionReporte {

    private static final Logger LOG = Logger.getLogger(CargarCalibracionReporte.class.getName());

    public static Object[] setDate(Date time) {
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        SoftwareJpaController swJpa = new SoftwareJpaController();
        Software sw = swJpa.findSoftware(1);
        EntityManager em = PersistenceController.getEntityManager();

        try {
            while (true) {
                System.out.println("aqui me quede.....");
                Query q = em.createNativeQuery("SELECT * FROM calibracion_dos_puntos pc inner join calibraciones c on pc.CALIBRATION = c.CALIBRATION WHERE c.id_tipo_calibracion = 2 AND DATE(c.CURDATE) = ? order by c.CALIBRATION desc", CalibracionDosPuntos.class);
                q.setParameter(1, new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()));

                if (q.getResultList() == null || q.getResultList().isEmpty()) {
                    c.add(Calendar.DATE, -1);
                    continue;
                }
                System.out.println("llegue aqui.....");
                CalibracionDosPuntos e = (CalibracionDosPuntos) q.getResultList().get(0);

//            for (CalibracionDosPuntos e : cal) {
                Object[] filaEquipo = new Object[25]; // Hay tres columnas en la tabla
                filaEquipo[0] = e.getEquipo().getPef();                //PEF
                filaEquipo[1] = e.getEquipo().getSerial2();                //Serie Banco
                filaEquipo[2] = e.getEquipo().getSerial2();                   //Serie Analizador
                filaEquipo[3] = e.getEquipo().getMarca();                  //Marca Analizador
                filaEquipo[4] = e.getBmHc();                  //Valor gas referencia bajo  HC verificaciòn y ajuste 
                filaEquipo[5] = e.getBancoBmHc();                  //Valor gas referencia bajo  HC verificaciòn y ajuste 
                filaEquipo[6] = e.getBmCo();                  //Valor gas referencia bajo CO verificaciòn y ajuste
                filaEquipo[7] = e.getBancoBmCo();                  //Valor gas referencia bajo CO verificaciòn y ajuste
                filaEquipo[8] = e.getBmCo2();                  //Valor gas referencia bajo CO2 verificaciòn y ajuste
                filaEquipo[9] = e.getBancoBmCo2();                  //Valor gas referencia bajo CO2 verificaciòn y ajuste
                filaEquipo[10] = e.getAltaHc();                  //Valor gas referencia alto HC verificaciòn y ajuste
                filaEquipo[11] = e.getBancoAltaHc();                  //Valor gas referencia alto HC verificaciòn y ajuste
                filaEquipo[12] = e.getAltaCo();                  //Valor gas referencia  alto CO verificaciòn y ajuste
                filaEquipo[13] = e.getBancoAltaCo();                  //Valor gas referencia  alto CO verificaciòn y ajuste
                filaEquipo[14] = e.getAltaCo2();               //Valor gas referencia  alto CO2 verificaciòn y ajuste
                filaEquipo[15] = e.getBancoAltaCo2();               //Valor gas referencia  alto CO2 verificaciòn y ajuste
                filaEquipo[16] = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(e.getFecha());    //Fecha de Version Software
                filaEquipo[17] = sw.getNombre();                            //Nombre del software de aplicación
                filaEquipo[18] = sw.getVersion();                        //Version software de aplicación
                System.out.println("aqui estoy ahora");
                return filaEquipo;
            }
//            }
        } catch (Exception e) {
            LOG.severe("No se encontro calibraciones de dos puntos");
            System.out.println("excepcion");
            return null;
        } finally {
         
        }
    }    

    public static void main(String[] args) {
        try {
            new CargarCalibracionReporte().setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2016-02-03"));
        } catch (ParseException ex) {
            Logger.getLogger(CargarCalibracionReporte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
