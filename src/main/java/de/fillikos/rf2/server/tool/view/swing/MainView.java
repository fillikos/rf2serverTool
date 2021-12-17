package de.fillikos.rf2.server.tool.view.swing;

import de.fillikos.rf2.server.tool.model.RF2Server;
import de.fillikos.rf2.server.tool.view.swing.speicher.LocalSpeicher;
import de.fillikos.rf2.server.tool.view.swing.table.model.TableModelLiveView;
import de.fillikos.rf2.service.webui.httpss.Connection;
import de.fillikos.rf2.service.webui.httpss.model.SessionInfo;
import de.fillikos.rf2.service.webui.httpss.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainView {

    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyy HH:mm");
    private LocalSpeicher localSpeicher;
    private JFrame frame = new JFrame();
    private Container conMain;
    private boolean loadServer;
    private Connection con;
    private SessionInfo sessionInfo = new SessionInfo();
    private User[] standings = new User[0];
    private ArrayList<String[]> list = new ArrayList<String[]>();
    private JLabel txtServerName;
    private TableModelLiveView tabModelLiveView;

    public MainView() {
        frame.setTitle("rF2 Server Tool");

        localSpeicher = new LocalSpeicher();
        con = new Connection("http://" + localSpeicher.getAdresse(localSpeicher.getLocalTempSpeicher().getServerListe().get(1)) +
                ":", localSpeicher.getPort(localSpeicher.getLocalTempSpeicher().getServerListe().get(1)));
        setLayout();

        panNorth();
        panCenter();
        panSouth();
        panWest();

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    private void panWest() {
        JPanel panWest = new JPanel();
        panWest.setLayout(new BoxLayout(panWest, BoxLayout.PAGE_AXIS));

        txtServerName = new JLabel("Servername: " + sessionInfo.getServerName());

        panWest.add(txtServerName);

        conMain.add(panWest, BorderLayout.WEST);
    }

    private void panCenter() {
        JPanel panCenter = new JPanel();

        String[] columns = new String[]{"N1ame", "T1est", "D1atum"};
        String[] fa = new String[]{"N1ame", "Test", "Datum"};
        String[] da = new String[]{"N2ame", "Test", "Datum"};
        String[] aa = new String[]{"N3ame", "Test", "Datum"};


        list.add(fa);
        list.add(da);

        tabModelLiveView = new TableModelLiveView(columns);

        tabModelLiveView.addAll(list);

        JTable tabLiveView = new JTable(tabModelLiveView);
        JScrollPane scrPane = new JScrollPane(tabLiveView);
        panCenter.add(scrPane);
        conMain.add(panCenter, BorderLayout.CENTER);

//        JButton btnNew = new JButton("New");
//        btnNew.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                list.add(aa);
//                tabModelLiveView.removeAllElements();
//                tabModelLiveView.addAll(list);
//            }
//        });
//
//        conMain.add(btnNew,BorderLayout.WEST);
    }

    private void panNorth() {
        JPanel panNorth = new JPanel(new FlowLayout(FlowLayout.LEADING));

        JComboBox<String> boxServerAuswahl = new JComboBox<String>();
        for(RF2Server s: localSpeicher.getLocalTempSpeicher().getServerListe()) {
            boxServerAuswahl.addItem(s.getServerName());
        }
        boxServerAuswahl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(boxServerAuswahl.getSelectedItem());
                con = new Connection("http://" + localSpeicher.getAdresse(boxServerAuswahl.getSelectedItem()) + ":", localSpeicher.getPort(boxServerAuswahl.getSelectedItem()));
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
                addServerDialog();
                boxServerAuswahl.removeAllItems();
                for(RF2Server s: localSpeicher.getLocalTempSpeicher().getServerListe()) {
                    boxServerAuswahl.addItem(s.getServerName());
                }
                boxServerAuswahl.repaint();
            }
        });
        panNorth.add(btnAddServer);

        JButton btnLoadServer = new JButton("Load Server");
        btnLoadServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(loadServer) {
                    loadServer = false;
                    btnLoadServer.setText("Load Server");
                } else {
                    btnLoadServer.setText("Loading...");
                    loadServer = true;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            loadServerSessionInfo();
                        }
                    }
                    ).start();
                }
            }
        });
        panNorth.add(btnLoadServer);

        conMain.add(panNorth, BorderLayout.NORTH);

    }

    private void loadServerSessionInfo() {
        while(loadServer) {
            con.loadData();
            sessionInfo = con.getSessionInfo();
            standings = con.getStandings();
            txtServerName.setText("<html><body>" +
                    "Servername: " + sessionInfo.getServerName() + "<br>" +
                    "Session: " + sessionInfo.getSession() + "<br>" +
                    "" + sdf.format(System.currentTimeMillis()) + "<br>" +
                    "SessionZeit: " + rf2CETToTime(sessionInfo.getCurrentEventTime()) + "<br>" +
                    "</body></html>");
            list.clear();
            for(User u: standings) {
                list.add(new String[]{u.getDriverName(), u.getLapDistance(), u.getFullTeamName()});
            }
            tabModelLiveView.removeAllElements();
            tabModelLiveView.addAll(list);
            try {
                Thread.sleep(950);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String rf2CETToTime(String currentEventTime) {
        String time = "00:00";
        long cet = Long.parseLong(currentEventTime.substring(0, currentEventTime.indexOf(".")));
        time = String.format("%01d:%02d:%02d", (cet / 60 / 60), ((cet / 60) % 60), (cet % 60));
        return time;
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
        if(loadServer) {
            loadServer = false;
        }
        frame.dispose();
    }

    private void setLayout() {
        conMain = frame.getContentPane();
        frame.setSize(900,600);
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
