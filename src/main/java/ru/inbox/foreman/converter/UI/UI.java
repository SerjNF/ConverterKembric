package ru.inbox.foreman.converter.UI;

import ru.inbox.foreman.converter.controller.Controller;
import ru.inbox.foreman.converter.controller.ControllerImpl;

import ru.inbox.foreman.converter.service.MyCellRenderer;
import ru.inbox.foreman.converter.service.MyEditor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class UI extends JPanel {
    private JTable openDataTable;
    private Controller controller;
    private DefaultTableModel tableModelOpenData;
    private DefaultTableModel tableModelConvertedData;
    private Object[] columnsHeader = new String[]{"№", "Наименование"};
    private FileNameExtensionFilter filter;


    public UI() {
        controller = new ControllerImpl();
        filter = new FileNameExtensionFilter("Excel", "xlsx");
        createUI();
    }

    private void createUI() {
        tableModelOpenData = new DefaultTableModel();
        tableModelOpenData.setColumnIdentifiers(columnsHeader);
        tableModelConvertedData = new DefaultTableModel();
        tableModelConvertedData.setColumnIdentifiers(columnsHeader);
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
            tableModelConvertedData.setRowCount(0);
            Object[][] convertedList = controller.convertFile(tableModelOpenData, openDataTable.getSelectedRows(), regexBox.getSelectedIndex());
            addRowsOnTable(tableModelConvertedData, convertedList);
        });

        JPanel buttonAndBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonAndBoxPanel.add(openButton);
        buttonAndBoxPanel.add(convert);
        buttonAndBoxPanel.add(regexBox);

        JPanel southPanel = new JPanel(new BorderLayout());
        JButton anSelectAll = new JButton("Сбросить выделение");
        anSelectAll.addActionListener(e -> openDataTable.getSelectionModel().clearSelection());
        JButton selectAll = new JButton("Выделить все");
        selectAll.addActionListener(e -> openDataTable.selectAll());
        JPanel selectPanel = new JPanel(new GridLayout(1, 2));
        selectPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        selectPanel.add(selectAll);
        selectPanel.add(anSelectAll);
        southPanel.add(choiceFileLabel, BorderLayout.SOUTH);
        southPanel.add(selectPanel, BorderLayout.NORTH);

        openDataTable = new JTable(tableModelOpenData);
        openDataTable.getColumnModel().getColumn(0).setMaxWidth(40);
        openDataTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        openDataTable.setDefaultRenderer(Object.class, new MyCellRenderer());
        openDataTable.setDefaultEditor(Object.class, new MyEditor());

        openDataTable.setRowHeight(30);
        openDataTable.setFont(new Font("Arial Narrow", Font.BOLD, 20));
        openDataTable.getTableHeader().setFont(new Font("Arial Narrow", Font.BOLD, 20));

        JPanel readPanel = new JPanel();
        readPanel.setLayout(new BorderLayout());
        readPanel.add(buttonAndBoxPanel, BorderLayout.NORTH);
        readPanel.add(new JScrollPane(openDataTable));
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

    private JButton createOpenButton(JLabel labelOpenFilePath) {
        JButton openButton = new JButton("Открыть файл");
        openButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Выбор файла");
            fileChooser.setFileFilter(filter);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = fileChooser.showOpenDialog(UI.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File openFile = fileChooser.getSelectedFile();
                labelOpenFilePath.setText(openFile.toString());
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
                    addRowsOnTable(tableModelOpenData, readList);
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
        JLabel labelSavePath = new JLabel("Файл не сохранен");
        JButton saveButton = createSaveButton(labelSavePath);
        JButton repeatButton = new JButton("Повторить");
        repeatButton.addActionListener(e -> replace());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(saveButton);
        buttonPanel.add(repeatButton);

        JTable convertedDataTable = new JTable(tableModelConvertedData);
        convertedDataTable.getTableHeader().setFont(new Font("Arial Narrow", Font.BOLD, 20));
        convertedDataTable.setRowHeight(30);
        convertedDataTable.setFont(new Font("Arial Narrow", Font.BOLD, 20));
        convertedDataTable.getColumnModel().getColumn(0).setMaxWidth(40);
        convertedDataTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        convertedDataTable.setDefaultRenderer(Object.class, new MyCellRenderer());
        convertedDataTable.setDefaultEditor(Object.class, new MyEditor());

        JPanel panelConvertedDataTable = new JPanel();
        panelConvertedDataTable.setLayout(new BorderLayout());
        panelConvertedDataTable.add(buttonPanel, BorderLayout.NORTH);
        panelConvertedDataTable.add(new JScrollPane(convertedDataTable));
        panelConvertedDataTable.add(labelSavePath, BorderLayout.SOUTH);
        panelConvertedDataTable.setBorder(new EmptyBorder(10, 10, 10, 10));
        return panelConvertedDataTable;
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
                    controller.saveFile(saveFile, tableModelConvertedData);
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
        for (int i = 0; i < tableModelOpenData.getRowCount(); i++) {
            tableModelOpenData.setValueAt(tableModelConvertedData.getValueAt(i, 1), i, 1);
        }
        tableModelConvertedData.setRowCount(0);
    }
}
