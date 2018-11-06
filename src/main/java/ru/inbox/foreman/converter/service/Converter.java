package ru.inbox.foreman.converter.service;


import javax.swing.table.DefaultTableModel;


public class Converter {

    public Object[][] convert(DefaultTableModel tableModelOpenFile, int[] selectRows) {
        int offSetRows = 2;
        int rowsCount = tableModelOpenFile.getRowCount();
        Object[][] result = new Object[rowsCount + offSetRows][2];

        for (int i = 0; i < rowsCount; ++i) {
            result[i][0] = String.valueOf(i);
            if (selectRows.length != 0) {
                for (int selectRow : selectRows) {
                    if (selectRow == i) {
                        result[i][1] = splitAndReverse((String) tableModelOpenFile.getValueAt(i, 1));
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

    private Object splitAndReverse(String valueAt) {
        String[] splitString = valueAt.split(" ");
        if (splitString.length != 3) {
            return "Не формат " + valueAt;
        }
        return splitString[2] + " " + splitString[1] + " " + splitString[0];
    }
}
