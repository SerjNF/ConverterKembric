package ru.inbox.foreman.converter.controller;


import ru.inbox.foreman.converter.service.Converter;
import ru.inbox.foreman.converter.service.ReadFile;

import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.IOException;

public class ControllerImpl implements Controller {
    private ReadFile readFile = new ReadFile();
    private Converter converter = new Converter();

    public ControllerImpl() {
    }

    public Object[][] readFile(File selectedFile) throws IOException {
        String pathFile = selectedFile.getPath();
            return readFile.readFile(pathFile);
    }


    @Override
    public Object[][] convertFile(DefaultTableModel tableModel, int[] selectRows) {
        return converter.convert(tableModel, selectRows);
    }

    @Override
    public void setSaveFile(File saveFile) {

    }
}
