package de.fillikos.rf2.server.tool.view.swing;

import javax.swing.*;
import java.awt.*;

public class MainView {

    private JFrame frame = new JFrame();
    private Container conMain;

    public MainView() {
        frame.setTitle("rF2 Server Tool");

        setLayout();

        layNorth();
        laySouth();

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    private void laySouth() {
        JPanel panSouth = new JPanel();

        conMain.add(panSouth, BorderLayout.SOUTH);
    }

    private void layNorth() {
        JPanel panNorth = new JPanel();

        conMain.add(panNorth, BorderLayout.NORTH);
    }

    private void setLayout() {
        conMain = frame.getContentPane();
        frame.setSize(300,200);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation( (int) (screenSize.getWidth() / 2) - frame.getWidth(),
                (int) screenSize.getHeight() / 2 - frame.getHeight());
    }
}
