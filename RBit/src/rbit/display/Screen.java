package rbit.display;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

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
        requestFocusInWindow();
        newSession();
    }

    void openSession(String path) {
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
    boolean promptIfUnsaved() {
        if (session == null) return true;
        if (session.isModified == true) {
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