/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.utils;


import java.awt.HeadlessException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author SOLTELEC
 */
public class ExportExcel {
  private static final Logger LOG = Logger.getLogger(GenericExportExcel.class.getName());

  private FileOutputStream generateDir()
  {
    JFileChooser jfChoose = new JFileChooser();

    if (jfChoose.showSaveDialog(null) != 0) {
      return null;
    }

    String directorio = jfChoose.getSelectedFile().getPath();

    String tmp = directorio.substring(directorio.length() - 3);

    if (!tmp.equals("xls")) {
      directorio = directorio + ".xls";
    }
    FileOutputStream out;
    try
    {
      out = new FileOutputStream(directorio);
    } catch (FileNotFoundException ex) {
      LOG.severe(ex.getMessage());
      JOptionPane.showMessageDialog(null, "Error de Lectura: " + ex.getMessage(), "Error", 0);
      return null;
    }

    return out;
  }

  public void exportExcel(JTable table) {
    FileOutputStream out = generateDir();

    if (out == null) {
      return;
    }

    HSSFWorkbook wb = new HSSFWorkbook();

    addSheet(wb, table);
    try {
      wb.write(out);
      out.close();
      JOptionPane.showMessageDialog(null, "Se Guardo Correctamente");
    } catch (IOException ex) {
      LOG.severe(ex.getMessage());
      CMensajes.mostrarExcepcion(ex);
    }
  }

  public void exportExcel(List<JTable> tables) {
    FileOutputStream out = generateDir();

    if (out == null) {
      return;
    }

    HSSFWorkbook wb = new HSSFWorkbook();

    for (JTable table : tables) {
      addSheet(wb, table);
    }
    try
    {
      wb.write(out);
      out.close();
      JOptionPane.showMessageDialog(null, "Se Guardo Correctamente");
    } catch (IOException ex) {
      LOG.severe(ex.getMessage());
      CMensajes.mostrarExcepcion(ex);
      JOptionPane.showMessageDialog(null, "Error al guardar");
    }
  }

  private void addSheet(HSSFWorkbook wb, JTable table)
  {
    try
    {
      TableModel model = table.getModel();

      if (model.getRowCount() < 1) {
        return;
      }

      HSSFSheet sheet = wb.createSheet(table.getName());

      HSSFCellStyle headerCellStyle = wb.createCellStyle();
      HSSFFont headerFont = wb.createFont();
      headerFont.setBoldweight((short)700);
      headerCellStyle.setFont(headerFont);
      headerCellStyle.setAlignment((short)2);
      headerCellStyle.setBorderBottom((short)1);
      headerCellStyle.setBorderTop((short)1);
      headerCellStyle.setBorderLeft((short)1);
      headerCellStyle.setBorderRight((short)1);
      headerCellStyle.setWrapText(true);

      HSSFCellStyle stringCellStyle = wb.createCellStyle();
      stringCellStyle.setWrapText(true);
      stringCellStyle.setBorderBottom((short)1);
      stringCellStyle.setBorderTop((short)1);
      stringCellStyle.setBorderLeft((short)1);
      stringCellStyle.setBorderRight((short)1);

      HSSFRow row = sheet.createRow(0);
      for (int colNumber = 0; colNumber < model.getColumnCount(); colNumber++)
      {
        HSSFCell column = row.createCell(colNumber);
        column.setCellStyle(headerCellStyle);
        column.setCellValue(model.getColumnName(colNumber));
      }

      for (int rowNumber = 0; rowNumber < model.getRowCount(); rowNumber++) {
        row = sheet.createRow(rowNumber + 1);

        for (int colNumber = 0; colNumber < model.getColumnCount(); colNumber++) {
          HSSFCell column = row.createCell(colNumber);
          column.setCellStyle(stringCellStyle);
          column.setCellValue(model.getValueAt(rowNumber, colNumber) == null ? "" : model.getValueAt(rowNumber, colNumber).toString());
        }

      }

      for (int colNumber = 0; colNumber < model.getColumnCount(); colNumber++)
        sheet.autoSizeColumn(colNumber);
    }
    catch (HeadlessException e) {
      LOG.severe(e.getMessage());
      CMensajes.mostrarExcepcion(e);
    }
  }

  public void exportSameSheet(List<JTable> tables) {
    FileOutputStream out = generateDir();
    int consecutivo = 0;
    int rowTemp = 1;

    if (out == null) {
      return;
    }

    HSSFWorkbook wb = new HSSFWorkbook();
    HSSFSheet sheet = wb.createSheet("Reporte");

    HSSFCellStyle headerCellStyle = wb.createCellStyle();
    HSSFFont headerFont = wb.createFont();
    headerFont.setBoldweight((short)700);
    headerCellStyle.setFont(headerFont);
    headerCellStyle.setAlignment((short)2);
    headerCellStyle.setBorderBottom((short)1);
    headerCellStyle.setBorderTop((short)1);
    headerCellStyle.setBorderLeft((short)1);
    headerCellStyle.setBorderRight((short)1);
    headerCellStyle.setWrapText(true);

    HSSFCellStyle stringCellStyle = wb.createCellStyle();
    stringCellStyle.setWrapText(true);
    stringCellStyle.setBorderBottom((short)1);
    stringCellStyle.setBorderTop((short)1);
    stringCellStyle.setBorderLeft((short)1);
    stringCellStyle.setBorderRight((short)1);

    for (JTable table : tables) {
      try
      {
        TableModel model = table.getModel();
        int temp = consecutivo;

        HSSFRow row = sheet.getRow(0);
        if (row == null) {
          row = sheet.createRow(0);
        }
        for (int colNumber = 0; colNumber < model.getColumnCount(); colNumber++)
        {
          HSSFCell column = row.createCell(temp);
          column.setCellStyle(headerCellStyle);
          column.setCellValue(model.getColumnName(colNumber));
          temp++;
        }

        for (int rowNumber = 0; rowNumber < model.getRowCount(); rowNumber++)
        {
          if ((row = sheet.getRow(rowNumber + 1)) == null) {
            row = sheet.createRow(rowNumber + 1);
          }

          temp = consecutivo;

          for (int colNumber = 0; colNumber < model.getColumnCount(); colNumber++) {
            HSSFCell column = row.createCell(temp);
            column.setCellStyle(stringCellStyle);
            column.setCellValue(model.getValueAt(rowNumber, colNumber) == null ? "" : model.getValueAt(rowNumber, colNumber).toString());
            temp++;
          }
        }

        temp = consecutivo;

        for (int colNumber = 0; colNumber < model.getColumnCount(); colNumber++) {
          sheet.autoSizeColumn(temp);
          temp++;
        }

        consecutivo += model.getColumnCount();
      } catch (HeadlessException e) {
        LOG.severe(e.getMessage());
        CMensajes.mostrarExcepcion(e);
      }
    }

    try
    {
      wb.write(out);
      out.close();
      JOptionPane.showMessageDialog(null, "Se Guardo Correctamente");
    } catch (IOException ex) {
      LOG.severe(ex.getMessage());
      CMensajes.mostrarExcepcion(ex);
    }
  }

    
}
