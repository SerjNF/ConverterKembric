package ru.inbox.foreman.converter.service;


import javax.swing.table.DefaultTableModel;


public class Converter {

    private String[] regexElement = {"Пробел ( )", "Слэш прямой (/)", "Двоеточие ( : )", "Подчеркивание (_)"};
    private String[] regexString = {"[ ]+", "[/]+", "[:]+", "[_]+"};
    private String[] regexForResult = {" ", " / ", " : ", "_"};

    public Object[][] convert(DefaultTableModel tableModelOpenFile, int[] selectRows, int regexNumber) {

        int offSetRows = 2;
        int rowsCount = tableModelOpenFile.getRowCount();
        Object[][] result = new Object[rowsCount + offSetRows][2];

        for (int i = 0; i < rowsCount; ++i) {
            result[i][0] = String.valueOf(i);
            if (selectRows.length != 0) {
                for (int selectRow : selectRows) {
                    if (selectRow == i) {
                        result[i][1] = splitAndReverse((String) tableModelOpenFile.getValueAt(i, 1), regexNumber);
                        break;
                    } else {
                        result[i][1] = tableModelOpenFile.getValueAt(i, 1);
                    }
                }
            } else {
                result[i][1] = tableModelOpenFile.getValueAt(i, 1);
            }
        }
        result[rowsCount][1] = "by SergeyNF";
        result[rowsCount + 1][1] = "foreman@inbox.ru";
        return result;
    }

    private Object splitAndReverse(String valueAt, int regexNumber) {

        String[] splitString = valueAt.split(regexString[regexNumber]);
        String[] splitS = valueAt.split("[\\ ]+ ");

        if (splitString.length != 3) {
            return "Не формат " + valueAt;
        }
        return splitString[2].trim() + regexForResult[regexNumber] + splitString[1].trim() + regexForResult[regexNumber] + splitString[0].trim();
    }

    public String[] getRegexElement() {
        return regexElement;
    }
}
