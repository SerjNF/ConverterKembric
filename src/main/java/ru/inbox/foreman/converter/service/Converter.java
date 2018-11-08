package ru.inbox.foreman.converter.service;


import ru.inbox.foreman.converter.model.Cell;

import javax.swing.table.DefaultTableModel;


public class Converter {

    private String[] regexElement = {"Пробел ( )", "Слэш прямой (/)", "Двоеточие ( : )", "Подчеркивание (_)"};
    private String[] regexString = {"[ ]+", "[/]+", "[:]+", "[_]+"};
    private String[] regexForResult = {" ", " / ", " : ", "_"};

    public Object[][] convert(DefaultTableModel tableModelOpenFile, int[] selectRows, int regexNumber) {

        int offSetRows = 2;
        int rowsCount = tableModelOpenFile.getRowCount();
        Object[][] result = new Object[rowsCount + offSetRows][2];
        //TODO упростить
        for (int i = 0; i < rowsCount; ++i) {
            result[i][0] = new Cell(String.valueOf(i));
            if (selectRows.length != 0) {
                for (int selectRow : selectRows) {
                    if (selectRow == i) {
                        result[i][1] = splitAndReverse((Cell) tableModelOpenFile.getValueAt(i, 1), regexNumber);
                        break;
                    } else {
                        Cell cell = (Cell) tableModelOpenFile.getValueAt(i, 1);
                        result[i][1] = new Cell(cell.getValue(), false);
                    }
                }
            } else {
                Cell cell = (Cell) tableModelOpenFile.getValueAt(i, 1);
                result[i][1] = new Cell(cell.getValue(), false);
            }
        }
        result[rowsCount][1] = new Cell("by SergeyNF", false);
        result[rowsCount + 1][1] = new Cell("foreman@inbox.ru", false);
        return result;
    }

    private Cell splitAndReverse(Cell valueAt, int regexNumber) {
        String[] splitString = valueAt.getValue().split(regexString[regexNumber]);
        if (splitString.length != 3) {
            return new Cell(valueAt.getValue(), true);
        }
        String stringBuilders = splitString[2].trim() +
                regexForResult[regexNumber] +
                splitString[1].trim() +
                regexForResult[regexNumber] +
                splitString[0].trim();
        return new Cell(stringBuilders, false);
    }

    public String[] getRegexElement() {
        return regexElement;
    }
}
