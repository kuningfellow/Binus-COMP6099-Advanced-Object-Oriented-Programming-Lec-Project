package rbit.display;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import rbit.util.DataBase;
import rbit.util.DataPath;

public class Screen extends JFrame {
    Screen ini;
    SearchPanel searchPanel;
    Session session = null;
    GridBagConstraints c;
    public Screen() {
        DataBase.init();
        // DataBase.rebuild();
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

        JMenuItem arrangementNew = new JMenuItem("New");
        arrangementNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newSession();
            }
        });

        JMenuItem arrangementOpen = new JMenuItem("Open");
        arrangementOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser("src/rbit/presets/");
                int retVal = fileChooser.showOpenDialog(ini);
                if (retVal == JFileChooser.APPROVE_OPTION) {
                    openSession(DataPath.getPath(fileChooser.getSelectedFile()));
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
                    session.saveAs();
                }
            }
        });

        menuBar.add(arrangementMenu);
        arrangementMenu.add(arrangementNew);
        arrangementMenu.add(arrangementOpen);
        arrangementMenu.add(arrangementSave);
        arrangementMenu.add(arrangementSaveAs);
        setJMenuBar(menuBar);

        this.searchPanel = new SearchPanel();
        add(searchPanel, c);
        c.gridx = 1;
        setVisible(true);
        newSession();
    }
    void openSession(String path) {
        if (session != null) {
            remove(session);
            session.close();
        }
        session = new Session(this, path);
        add(session, c);
        setVisible(true);
    }
    void newSession() {
        if (session != null) {
            remove(session);
            session.close();
        }
        session = new Session(this);
        add(session, c);
        setVisible(true);
    }
}