package rbit.display;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.Paths;
import java.awt.event.MouseAdapter;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import rbit.database.DataBase;
import rbit.database.SearchEngine;
import rbit.util.DataPath;
import rbit.display.session.Session;
import rbit.display.search.SearchPanel;

public class Screen extends JFrame {
    Screen ini;
    SearchPanel searchPanel;
    Session session = null;
    GridBagConstraints c;
    public Screen() {
        DataBase.init();
        new SearchEngine();
        setTitle("RBit");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        ini = this;
        setSize(1200, 700);
        setLocationRelativeTo(null);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                requestFocusInWindow();
            }
        });

        JMenuBar menuBar = new JMenuBar();

        JMenu arrangementMenu = new JMenu("Arrangement");
        JMenu databaseMenu = new JMenu("Database");

        JMenuItem arrangementNew = new JMenuItem("New");
        arrangementNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (promptIfUnsaved()) {
                    newSession();
                    requestFocusInWindow();
                }
            }
        });

        JMenuItem arrangementOpen = new JMenuItem("Open");
        arrangementOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (promptIfUnsaved()) {
                    JFileChooser fileChooser = new JFileChooser("src/rbit/presets/");
                    int retVal = fileChooser.showOpenDialog(ini);
                    if (retVal == JFileChooser.APPROVE_OPTION) {
                        openSession(DataPath.getPath(fileChooser.getSelectedFile()));
                    }
                    requestFocusInWindow();
                }
            }
        });

        JMenuItem arrangementSave = new JMenuItem("Save");
        arrangementSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (session != null) {
                    session.save();
                }
            }
        });

        JMenuItem arrangementSaveAs = new JMenuItem("Save As");
        arrangementSaveAs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (session != null) {
                    String path = session.saveAs();
                    if (!path.equals("")) {
                        openSession(path);
                    }
                }
            }
        });

        JMenuItem databaseRescan = new JMenuItem("Rescan");
        databaseRescan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // rebuild and rescan database
                DataBase.rebuild();
                String dir = Paths.get(new File("").getAbsolutePath()).resolve("src/rbit/presets").toString();
                for (String pathname : new File(dir).list()) {
                    String path = "src/rbit/presets/" + pathname;
                    DataBase.insert(path, DataBase.getArrangement(path));
                }
            }
        });

        menuBar.add(arrangementMenu);
        menuBar.add(databaseMenu);
        arrangementMenu.add(arrangementNew);
        arrangementMenu.add(arrangementOpen);
        arrangementMenu.add(arrangementSave);
        arrangementMenu.add(arrangementSaveAs);
        databaseMenu.add(databaseRescan);
        setJMenuBar(menuBar);

        this.searchPanel = new SearchPanel(this);
        c.insets = new Insets(0, 10, 0, 10);
        add(searchPanel, c);
        c.gridx = 1;
        setVisible(true);
        requestFocusInWindow();
        newSession();
    }
    public void openSession(String path) {
        removeSession();
        session = new Session(this, path);
        add(session, c);
        setVisible(true);
    }
    void newSession() {
        removeSession();
        session = new Session(this);
        add(session, c);
        setVisible(true);
    }
    void removeSession() {
        if (session != null) {
            session.close();
            remove(session);
        }
    }
    public boolean promptIfUnsaved() {
        if (session == null) return true;
        if (session.isModified() == true) {
            Object[] options = {"Yes", "No", "Cancel"};
            int response = JOptionPane.showOptionDialog(this,
                "Do you want to save changes you made to this arrangement",
                "Warning: Arrangement not saved",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
            if (response == JOptionPane.YES_OPTION) {
                return session.save();
            } else if (response == JOptionPane.NO_OPTION) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
}