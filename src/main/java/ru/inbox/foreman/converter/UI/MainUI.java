package ru.inbox.foreman.converter.UI;

import javax.swing.*;
import java.awt.*;

public class MainUI extends JFrame {

    public MainUI() {
        super("Конвертер кабельных бирок");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        UI uiConverter = new UI();
        getContentPane().add(uiConverter);

        setMinimumSize(new Dimension(900, 300));
    }
}
