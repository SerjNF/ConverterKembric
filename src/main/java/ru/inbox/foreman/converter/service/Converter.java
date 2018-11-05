package ru.inbox.foreman.converter.service;


import javax.swing.table.DefaultTableModel;


public class Converter {

    public Object[][] convert(DefaultTableModel tableModelOpenFile, int[] selectRows) {
        int rowsCount = tableModelOpenFile.getRowCount();
        Object[][] result = new Object[rowsCount][1];

        //      List rowsList = Arrays.asList(selectRows);

        for (int i = 0; i < rowsCount; ++i) {
            if (selectRows.length != 0) {
                for (int selectRow : selectRows) {
                    if (selectRow == i) {
                        result[i][0] = splitAndReverse((String) tableModelOpenFile.getValueAt(i, 0));
                        break;
                    } else {
                        result[i][0] = tableModelOpenFile.getValueAt(i, 0);
                    }
                }
            } else {
                result[i][0] = tableModelOpenFile.getValueAt(i, 0);
            }
        }
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
