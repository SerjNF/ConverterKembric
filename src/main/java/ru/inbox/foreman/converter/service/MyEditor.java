package ru.inbox.foreman.converter.service;

import ru.inbox.foreman.converter.model.Cell;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.*;
import java.util.EventObject;

public class MyEditor extends AbstractCellEditor implements TableCellEditor {

    private JTextField editor;

    public MyEditor() {
        editor = new JTextField();
    }

    // Метод получения компонента для прорисовки
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        // Изменение значения
        Cell cellData = (Cell) value;
        editor.setText(cellData.getValue());
        return editor;
    }

    // Функция текущего значения в редакторе
    @Override
    public Object getCellEditorValue() {
        return new Cell(editor.getText());
    }

    public boolean isCellEditable(EventObject e) {
        if (super.isCellEditable(e)) {
            if (e instanceof MouseEvent) {
                MouseEvent me = (MouseEvent) e;
                return me.getClickCount() >= 2;
            }
        }
        return false;
    }
}

