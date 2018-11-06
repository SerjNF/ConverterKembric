package ru.inbox.foreman.converter.service;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.table.DefaultTableModel;
import java.io.*;

public class SaveFile {

    public void saveFile(File file, DefaultTableModel model) throws IOException {
        String filePath = file.getPath() + ".xlsx";

        XSSFWorkbook book = new XSSFWorkbook();
        XSSFSheet sheet = book.createSheet("Converted");
        writeDataToSheet(sheet, model);
        sheet.autoSizeColumn(0);

        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(filePath));
        book.write(bufferedOutputStream);
    }

    private void writeDataToSheet(XSSFSheet sheet, DefaultTableModel model) {
        for (int i = 0; i < model.getRowCount(); ++i) {
            XSSFRow row = sheet.createRow(i);
            XSSFCell cell = row.createCell(0);
            cell.setCellValue(String.valueOf(model.getValueAt(i, 1)));
        }
    }
}
