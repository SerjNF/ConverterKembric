package ru.inbox.foreman.converter.UI;


import ru.inbox.foreman.converter.controller.Controller;
import ru.inbox.foreman.converter.controller.ControllerImpl;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class UI extends JPanel {
    private JTable openFileTable;
    private Controller controller;
    private DefaultTableModel tableModelOpenFile;
    private DefaultTableModel tableModelSaveFile;
    private Object[] columnsHeader = new String[]{"№", "Наименование"};
    private FileNameExtensionFilter filter;


    public UI() {
        controller = new ControllerImpl();
        filter = new FileNameExtensionFilter("Excel", "xlsx");
        createUI();
    }

    private void createUI() {
        tableModelOpenFile = new DefaultTableModel();
        tableModelOpenFile.setColumnIdentifiers(columnsHeader);
        tableModelSaveFile = new DefaultTableModel();
        tableModelSaveFile.setColumnIdentifiers(columnsHeader);
        this.setLayout(new GridLayout(1, 2));
        this.add(createReadPanel());
        this.add(createSavePanel());
    }

    private JPanel createReadPanel() {
        JLabel choiceFileLabel = new JLabel("Файд не выбран");
        JButton openButton = createOpenButton(choiceFileLabel);
        JComboBox<String> regexBox = createRegexComboBox();
        JButton convert = new JButton("Конвертировать");
        convert.addActionListener(e -> {
            tableModelSaveFile.setRowCount(0);
            Object[][] convertedList = controller.convertFile(tableModelOpenFile, openFileTable.getSelectedRows(), regexBox.getSelectedIndex());
            addRowsOnTable(tableModelSaveFile, convertedList);
        });



        JPanel buttonAndBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonAndBoxPanel.add(openButton);
        buttonAndBoxPanel.add(convert);
        buttonAndBoxPanel.add(regexBox);

        JPanel southPanel = new JPanel(new BorderLayout());
        JButton anSelectAll = new JButton("Сбросить выделение");
        anSelectAll.addActionListener(e -> openFileTable.getSelectionModel().clearSelection());
        JButton selectAll = new JButton("Выделить все");
        selectAll.addActionListener(e -> openFileTable.selectAll());
        JPanel selectPanel = new JPanel(new GridLayout(1, 2));
        selectPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        selectPanel.add(selectAll);
        selectPanel.add(anSelectAll);
        southPanel.add(choiceFileLabel, BorderLayout.SOUTH);
        southPanel.add(selectPanel, BorderLayout.NORTH);
        openFileTable = new JTable(tableModelOpenFile);
        openFileTable.getColumnModel().getColumn(0).setMaxWidth(40);
        openFileTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        openFileTable.setRowHeight(30);
        openFileTable.setFont(new Font("Arial Narrow", Font.BOLD, 20));
        openFileTable.getTableHeader().setFont(new Font("Arial Narrow", Font.BOLD, 20));

        JPanel readPanel = new JPanel();
        readPanel.setLayout(new BorderLayout());
        readPanel.add(buttonAndBoxPanel, BorderLayout.NORTH);
        readPanel.add(new JScrollPane(openFileTable));
        readPanel.add(southPanel, BorderLayout.SOUTH);
        readPanel.setBorder(new EmptyBorder(10, 5, 10, 10));
        return readPanel;
    }

    private JComboBox<String> createRegexComboBox() {
        JComboBox<String> comboBox = new JComboBox<>();
        String[] boxElement = controller.getRegexElement();
        for (String aBoxElement : boxElement) {
            comboBox.addItem(aBoxElement);
        }
        return comboBox;
    }

    private JButton createOpenButton(JLabel choiceFileLabel) {
        JButton openButton = new JButton("Открыть файл");
        openButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Выбор файла");
            fileChooser.setFileFilter(filter);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = fileChooser.showOpenDialog(UI.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File openFile = fileChooser.getSelectedFile();
                choiceFileLabel.setText(openFile.toString());
                Object[][] readList = new Object[0][];
                try {
                    readList = controller.readFile(openFile);
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(UI.this,
                            new String[]{"Файл не найден, либо заблокирован",
                            },
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace();
                }
                if (readList != null) {
                    addRowsOnTable(tableModelOpenFile, readList);
                }
            }
        });
        return openButton;
    }

    private void addRowsOnTable(DefaultTableModel tableModel, Object[] readList) {
        tableModel.setRowCount(0);
        for (Object input : readList) {
            tableModel.addRow((Object[]) input);
        }
    }

    private JPanel createSavePanel() {
        JLabel choiceSaveLabel = new JLabel("Файл не сохранен");
        JButton saveButton = createSaveButton(choiceSaveLabel);
        JButton repeat = new JButton("Повторить");
        repeat.addActionListener(e -> replace());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(saveButton);
        buttonPanel.add(repeat);

        JTable saveFileTable = new JTable(tableModelSaveFile);
        saveFileTable.getTableHeader().setFont(new Font("Arial Narrow", Font.BOLD, 20));
        saveFileTable.setRowHeight(30);
        saveFileTable.setFont(new Font("Arial Narrow", Font.BOLD, 20));
        saveFileTable.getColumnModel().getColumn(0).setMaxWidth(40);
        saveFileTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        JPanel savePanel = new JPanel();
        savePanel.setLayout(new BorderLayout());
        savePanel.add(buttonPanel, BorderLayout.NORTH);
        savePanel.add(new JScrollPane(saveFileTable));
        savePanel.add(choiceSaveLabel, BorderLayout.SOUTH);
        savePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        return savePanel;
    }

    private JButton createSaveButton(JLabel choiceSaveLabel) {
        JButton saveButton = new JButton("Сохранить файл");
        saveButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(filter);
            fileChooser.setDialogTitle("Сохранить файл");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = fileChooser.showSaveDialog(UI.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File saveFile = fileChooser.getSelectedFile();
                choiceSaveLabel.setText("Сохранено в: " + saveFile.toString());
                try {
                    controller.saveFile(saveFile, tableModelSaveFile);
                    JOptionPane.showMessageDialog(UI.this, "Файл сохранен");
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(UI.this, "Не удалось сохранить файл");
                    e1.printStackTrace();
                }

            }
        });
        return saveButton;
    }

    private void replace() {
        for (int i = 0; i < tableModelOpenFile.getRowCount(); i++) {
            tableModelOpenFile.setValueAt(tableModelSaveFile.getValueAt(i, 1), i, 1);
        }
        tableModelSaveFile.setRowCount(0);
    }
}
