package ru.inbox.foreman.converter.service;

import ru.inbox.foreman.converter.model.Cell;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class MyCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Cell cell = (Cell) (value);
        if(cell == null){
            return null;
        }
        Object val = cell.getValue();
        // Надпись от базового класса
        JLabel label = (JLabel) super.getTableCellRendererComponent(table,
                val, isSelected, hasFocus, row, column);

        label.setOpaque(true);
        if (cell.isError()) {
            label.setBackground(Color.red);
        } else {
            label.setBackground(Color.WHITE);
        }
        if (isSelected) {
            label.setBackground(Color.BLUE);
        }
        // Выравнивание
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }
}
