package ru.inbox.foreman.converter;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.inbox.foreman.converter.UI.MainUI;

import javax.swing.*;

@SpringBootApplication
public class ConverterApplication {



    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainUI::new);
    }

}
