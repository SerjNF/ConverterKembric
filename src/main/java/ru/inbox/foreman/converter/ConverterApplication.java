package ru.inbox.foreman.converter;




import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.inbox.foreman.converter.UI.UI;

import javax.swing.*;
import java.awt.*;


@SpringBootApplication
public class ConverterApplication {



    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        UI uiConverter = new UI();
        frame.getContentPane().add(uiConverter);
        frame.setSize(600, 300);
        frame.setMinimumSize(new Dimension(600, 300));

    }

}
