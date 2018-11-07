package ru.inbox.foreman.converter.controller;


import ru.inbox.foreman.converter.service.Converter;
import ru.inbox.foreman.converter.service.ReadFile;
import ru.inbox.foreman.converter.service.SaveFile;

import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.IOException;

public class ControllerImpl implements Controller {
    private ReadFile readFile = new ReadFile();
    private Converter converter = new Converter();
    private SaveFile saveFile = new SaveFile();

    public ControllerImpl() {
    }

    public Object[][] readFile(File selectedFile) throws IOException {
        String pathFile = selectedFile.getPath();
        return readFile.readFile(pathFile);
    }


    @Override
    public Object[][] convertFile(DefaultTableModel tableModel, int[] selectRows, int regexNumber) {
        return converter.convert(tableModel, selectRows, regexNumber);
    }

    @Override
    public void saveFile(File saveFile, DefaultTableModel model) throws IOException {
        this.saveFile.saveFile(saveFile, model);
    }

    @Override
    public String[] getRegexElement() {
        return converter.getRegexElement();
    }
}
