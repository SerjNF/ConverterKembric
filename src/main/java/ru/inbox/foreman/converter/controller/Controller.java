package ru.inbox.foreman.converter.controller;


import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.IOException;

public interface Controller {
    Object[][] readFile(File selectedFile) throws IOException;

//    Object[] convertFile(DefaultTableModel file);

    Object[][] convertFile(DefaultTableModel tableModel, int[] selectRows);

    void setSaveFile(File savaFile);
}
