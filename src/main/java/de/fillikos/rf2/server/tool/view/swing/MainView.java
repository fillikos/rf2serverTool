package de.fillikos.rf2.server.tool.view.swing;

import de.fillikos.rf2.server.tool.model.RF2Server;
import de.fillikos.rf2.server.tool.view.swing.speicher.LocalSpeicher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView {

    private LocalSpeicher localSpeicher;
    private JFrame frame = new JFrame();
    private Container conMain;

    public MainView() {
        frame.setTitle("rF2 Server Tool");

        localSpeicher = new LocalSpeicher();
        setLayout();

        panNorth();
        panCenter();
        panSouth();

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    private void panCenter() {

    }

    private void panNorth() {
        JPanel panNorth = new JPanel(new FlowLayout(FlowLayout.LEADING));

        JComboBox boxServerAuswahl = new JComboBox();
        for(RF2Server s: localSpeicher.getLocalTempSpeicher().getServerListe()) {
            boxServerAuswahl.addItem(s.getServerName());
        }
        boxServerAuswahl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(boxServerAuswahl.getSelectedItem());
                for(RF2Server r: localSpeicher.getLocalTempSpeicher().getServerListe()) {
                    if(r.getServerName().equals(boxServerAuswahl.getSelectedItem())) {
                        r.addCounter();
                    }
                }
            }
        });
        panNorth.add(boxServerAuswahl);

        JButton btnAddServer = new JButton("Add Server");
        btnAddServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO neues Fenster aufrufen, neuen Server eingeben und im tempSpeicher speichern und frame neu laden
                addServerDialog();
                boxServerAuswahl.removeAllItems();
                for(RF2Server s: localSpeicher.getLocalTempSpeicher().getServerListe()) {
                    boxServerAuswahl.addItem(s.getServerName());
                }
                boxServerAuswahl.repaint();
            }
        });

        panNorth.add(btnAddServer);
        conMain.add(panNorth, BorderLayout.NORTH);
    }


    private void panSouth() {
        JPanel panSouth = new JPanel(new FlowLayout(FlowLayout.LEADING));
        panSouth.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(e -> close());
        panSouth.add(btnClose);

        conMain.add(panSouth, BorderLayout.SOUTH);
    }

    private void close() {
        localSpeicher.saveLocalSpeicher();
        frame.dispose();
    }

    private void setLayout() {
        conMain = frame.getContentPane();
        frame.setSize(300,200);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation( ((int) screenSize.getWidth() - frame.getWidth()) / 2,
                ((int) screenSize.getHeight() - frame.getHeight()) / 2);
    }

    private void addServerDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Add Server");
        dialog.setSize(200,200);
        dialog.setLocation(frame.getLocation());
        dialog.setModal(true);

        JPanel panMain = new JPanel();
        panMain.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        panMain.add(new JLabel("Servername:"), c);
        JTextField txtServerName = new JTextField("Servername");
        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        panMain.add(txtServerName, c);

        c.gridx = 0;
        c.gridy = 1;
        panMain.add(new JLabel("IP:"), c);
        JTextField txtServerIP = new JTextField("IP");
        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        panMain.add(txtServerIP, c);

        c.gridx = 0;
        c.gridy = 2;
        JTextField txtServerPort = new JTextField("Port");
        panMain.add(new JLabel("Port:"), c);
        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        panMain.add(txtServerPort, c);

        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(e -> localSpeicher.getLocalTempSpeicher().addNewServer(txtServerName.getText(), txtServerIP.getText(), txtServerPort.getText()));
        JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(e -> {
            txtServerIP.setText("");
            txtServerName.setText("");
            txtServerPort.setText("");
        });

        c.gridx = 0;
        c.gridy = 3;
        panMain.add(btnClear, c);
        c.gridx = 1;
        panMain.add(btnSave, c);

        dialog.add(panMain);

        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }
}
