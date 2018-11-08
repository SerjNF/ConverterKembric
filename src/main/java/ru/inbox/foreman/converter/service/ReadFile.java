package ru.inbox.foreman.converter.service;


import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.inbox.foreman.converter.model.Cell;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadFile {
    public Object[][] readFile(String filePath) throws IOException {
        List<String> readiedList = new ArrayList<>();
        XSSFWorkbook myExcelBook;
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(filePath));
        myExcelBook = new XSSFWorkbook(bufferedInputStream);
        XSSFSheet myExcelSheet = myExcelBook.getSheetAt(0);

        for (int i = 0; ; ++i) {
            XSSFRow row = myExcelSheet.getRow(i);
            if (row == null) {
                break;
            }
            XSSFCell cell = row.getCell(0);
            if (cell == null) {
                continue;
            }
            String stringCellValue = cell.getStringCellValue();
            readiedList.add(stringCellValue);
        }

        Object[][] resultReadList = new Cell[readiedList.size()][2];
        for (int i = 0; i < resultReadList.length; ++i) {
            Cell cell = new Cell(readiedList.get(i), false);
            resultReadList[i][1] = cell;
            resultReadList[i][0] = new Cell(String.valueOf(i));
        }
        return resultReadList;
    }
}
