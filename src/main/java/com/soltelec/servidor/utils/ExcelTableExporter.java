package com.soltelec.servidor.utils;

import com.soltelec.servidor.dao.CdaJpaController;
import com.soltelec.servidor.model.Cda;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.*;
import jxl.write.*;
import jxl.*;

public class ExcelTableExporter {

    private final File file;
    private JTable[] table = new JTable[4];
    private final String nombreTab;
    private final Date[] fecha1;
    private static final Logger LOG = Logger.getLogger(CargarCalibracionReporte.class.getName());

    public ExcelTableExporter(Date[] date, JTable[] table, File file, String nombreTab) {
        fecha1 = date;

        this.file = file;
        this.table = table;
        this.nombreTab = nombreTab;
    }

    public boolean export() {

        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(file));

            WritableWorkbook w = Workbook.createWorkbook(out);

            WritableSheet s = w.createSheet(nombreTab, 0);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy  hh:mm:ss");
            /*INSTACIAMOS UN OBJETO DE TIPO DATE A NULL*/
            java.util.Date date = new java.util.Date();

            String fecha = sdf.format(date);
            fecha = "Consulta Calibraciones y Pruebas " + fecha;

            SimpleDateFormat f1 = new SimpleDateFormat("dd/MM/yyyy");
            String finicial = f1.format(fecha1[0]);
            String ffinal = f1.format(fecha1[1]);

            CdaJpaController uno = new CdaJpaController();
            //  List<Vehiculos> uno1 = uno.findVehiculos_porCedPropietario("111", "carplate", ">");
            List<Cda> uno1 = uno.findCdaEntities();

            Cda p = new Cda();
            p = uno1.get(0);

            int mfilas = table[0].getRowCount();
            int mcolumnas = table[0].getColumnCount();
            mfilas += 5;

            int x = 0;

            s.addCell(new Label(0, 0, fecha));
            s.addCell(new Label(0, 1, "Fecha Inicial: " + finicial + " Fecha Final: " + ffinal));

            s.addCell(new Label(0, 2, "Id CDA: " + p.getIdCda()));
            s.addCell(new Label(1, 2, "Nit: " + p.getNit()));
            s.addCell(new Label(2, 2, "Nombre Cda: " + p.getNombre()));
            s.addCell(new Label(3, 2, "Direccion: " + p.getDireccion()));
            s.addCell(new Label(4, 2, "Telefono: " + p.getTelefono()));
            s.addCell(new Label(5, 2, "Resolucion: " + p.getResolucion()));

            s.addCell(new Label(0, 4, "Serial"));
            s.addCell(new Label(1, 4, "PEF"));
            s.addCell(new Label(2, 4, "Fecha"));
            s.addCell(new Label(3, 4, "HC Bajo"));
            s.addCell(new Label(4, 4, "CO Bajo"));
            s.addCell(new Label(5, 4, "CO2 Bajo"));
            s.addCell(new Label(6, 4, "HC Medio"));
            s.addCell(new Label(7, 4, "CO Medio"));
            s.addCell(new Label(8, 4, "CO2 Medio"));

            for (int i = 0; i < table[0].getColumnCount(); i++) {
                for (int j = 5; j < mfilas; j++) {
                    {
                        System.out.println("Filas i " + i + " Columnas j " + j);

                        x = j - 5;
                        Object objeto = table[0].getValueAt(x, i);

                        s.addCell(new Label(i, j, String.valueOf(objeto)));
                    }
                }
            }

            mfilas += 2;

            s.addCell(new Label(0, mfilas, "Nro Prueba"));
            s.addCell(new Label(1, mfilas, "Hora y Fecha Prueb"));
            s.addCell(new Label(2, mfilas, "Motivo aborto"));
            s.addCell(new Label(3, mfilas, "Hora y Fecha Aborto"));

            for (int j = 0; j < table[1].getRowCount(); j++) {
                ++mfilas;
                for (int i = 0; i < table[1].getColumnCount(); i++) {
                    System.out.println("Filas i " + i + " Columnas j " + j);

                    Object objeto = table[1].getValueAt(j, i);

                    s.addCell(new Label(i, mfilas, String.valueOf(objeto)));
                }
            }

            mfilas += 2;

            for (int j = 0; j < table[2].getColumnCount(); j++) {
                s.addCell(new Label(j, mfilas, table[2].getColumnName(j)));
            }

            for (int j = 0; j < table[2].getRowCount(); j++) {
                ++mfilas;
                for (int i = 0; i < table[2].getColumnCount(); i++) {
                    System.out.println("Filas i " + i + " Columnas j " + j);

                    Object objeto = table[2].getValueAt(j, i);

                    s.addCell(new Label(i, mfilas, String.valueOf(objeto)));
                }
            }

            mfilas += 2;

            s.addCell(new Label(0, mfilas, "Nro Prueba"));
            s.addCell(new Label(1, mfilas, "Hora y Fecha Prueba"));
            s.addCell(new Label(2, mfilas, "Motivo aborto"));
            s.addCell(new Label(3, mfilas, "Hora y Fecha Aborto"));
            s.addCell(new Label(4, mfilas, "PSonora Exosto"));

            for (int j = 0; j < table[3].getRowCount(); j++) {
                ++mfilas;
                for (int i = 0; i < table[3].getColumnCount(); i++) {
                    System.out.println("Filas i " + i + " Columnas j " + j);

                    Object objeto = table[3].getValueAt(j, i);

                    s.addCell(new Label(i, mfilas, String.valueOf(objeto)));
                }
            }

            w.write();
            w.close();
            out.close();

            return true;

        } catch (IOException | WriteException ex) {
            LOG.severe(ex.getMessage());
        }
        return false;
    }
}
