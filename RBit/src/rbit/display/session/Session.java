package rbit.display.session;

import java.util.Arrays;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;

import rbit.machine.Machine;
import rbit.database.DataBase;
import rbit.util.DataPath;
import rbit.display.Screen;

public class Session extends JPanel {
    /**
     * Single session
     */
    String path;
    TitleEditor titleEditor;
    DescriptionEditor descriptionEditor;
    TagEditor tagEditor;
    Screen screen;
    Editor editor;
    JButton addTrack, play, stop;
    JSpinner tempo, subTempo, length;
    boolean edited;
    public Session(Screen screen, String path) {
        this.screen = screen;
        this.edited = false;
        this.path = path;
        this.editor = new Editor(this, new Machine(DataBase.getArrangement(path)));
        init();
    }
    public Session(Screen screen) {
        this.screen = screen;
        this.edited = false;
        this.path = "";
        this.editor = new Editor(this, new Machine());
        init();
    }

    public boolean isModified() {
        return edited;
    }
    public boolean save() {
        if (!path.equals("")) {
            DataBase.saveFile(path, editor.getArrangement());
            DataBase.update(path, editor.getArrangement());
            edited = false;
            return true;
        } else {
            return !saveAs().equals("");
        }
    }
    public String saveAs() {
        JFileChooser fileChooser = new JFileChooser("src/rbit/presets/");
        int retVal = fileChooser.showSaveDialog(screen);
        if (retVal == JFileChooser.APPROVE_OPTION) {
            path = DataPath.getPath(fileChooser.getSelectedFile());
            DataBase.saveFile(path, editor.getArrangement());
            DataBase.insert(path, editor.getArrangement());
            edited = false;
            return path;
        }
        return "";
    }

    public void close() {
        editor.stop();
    }
    
    void init() {
        setLayout(new GridBagLayout());

        // titleEditor
        this.titleEditor = new TitleEditor(editor);

        // descriptionEditor
        this.descriptionEditor = new DescriptionEditor(editor);

        // tagEditor
        this.tagEditor = new TagEditor(editor);

        // addTrack button
        this.addTrack = new JButton("Add Track");
        this.addTrack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                edited = true;
                editor.addTrack("");
            }
        });

        // play button
        this.play = new JButton("Play");
        this.play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editor.play(editor.beatGuide.startBeat, editor.beatGuide.startSubBeat);
            }
        });

        // stop button
        this.stop = new JButton("Stop");
        this.stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editor.stop();
            }
        });

        // tempo spinner
        this.tempo = new JSpinner(new SpinnerNumberModel(128, 10, 300, 1));
        this.tempo.setValue(editor.getTempo());
        this.tempo.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                edited = true;
                editor.setTempo((Integer) tempo.getValue());
            }
        });

        // subtempo spinner
        this.subTempo = new JSpinner(new SpinnerListModel(Arrays.asList(new String[]{"1/1", "1/2", "1/4", "1/8", "1/16", "1/32"})));
        if (editor.getSubTempo() == 0) {
            this.subTempo.setValue("1/1");
        } else if (editor.getSubTempo() == 1) {
            this.subTempo.setValue("1/2");
        } else if (editor.getSubTempo() == 2) {
            this.subTempo.setValue("1/4");
        } else if (editor.getSubTempo() == 3) {
            this.subTempo.setValue("1/8");
        } else if (editor.getSubTempo() == 4) {
            this.subTempo.setValue("1/16");
        } else if (editor.getSubTempo() == 5) {
            this.subTempo.setValue("1/32");
        }
        this.subTempo.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                edited = true;
                if (subTempo.getValue().equals("1/1")) {
                    editor.setSubTempo(0);
                } else if (subTempo.getValue().equals("1/2")) {
                    editor.setSubTempo(1);
                } else if (subTempo.getValue().equals("1/4")) {
                    editor.setSubTempo(2);
                } else if (subTempo.getValue().equals("1/8")) {
                    editor.setSubTempo(3);
                } else if (subTempo.getValue().equals("1/16")) {
                    editor.setSubTempo(4);
                } else if (subTempo.getValue().equals("1/32")) {
                    editor.setSubTempo(5);
                }
            }
        });

        // length spinner
        this.length = new JSpinner(new SpinnerNumberModel(editor.getLength(), 0, Integer.MAX_VALUE, 1));
        this.length.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                edited = true;
                editor.setLength((Integer) length.getValue());
            }
        });

        // Laying out the layout
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = c.gridy = 0;

        // metadata section
        JPanel upper = new JPanel();
        upper.setLayout(new GridBagLayout());
        upper.setPreferredSize(new Dimension(800, 110));
        add(upper);
        
        JPanel upperUpper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        // upperUpper.setLayout(new GridBagLayout());
        upperUpper.setPreferredSize(new Dimension(800, 30));
        JPanel upperLower = new JPanel();
        upperLower.setLayout(new GridBagLayout());
        upperLower.setPreferredSize(new Dimension(800, 80));

        upper.add(upperUpper, c);
        c.gridy = 1;
        upper.add(upperLower, c);

        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0;
        upperUpper.add(new JLabel("Title: "), c);
        c.gridx = 1;
        upperUpper.add(this.titleEditor, c);
        
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        upperLower.add(this.descriptionEditor, c);
        c.gridx = 1;
        c.weightx = 0;
        upperLower.add(this.tagEditor, c);

        // editor section
        c.weightx = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 2;
        add(this.editor, c);

        // lower section
        JPanel lower = new JPanel();
        lower.setPreferredSize(new Dimension(800, 50));
        lower.setLayout(new GridBagLayout());
        JPanel lowerLeft = new JPanel();
        lowerLeft.setLayout(new GridLayout(2, 1));
        lowerLeft.add(this.addTrack);
        lowerLeft.add(this.length);
        c.anchor = GridBagConstraints.WEST;
        c.weightx = 0.5;
        lower.add(lowerLeft, c);

        JPanel lowerRight = new JPanel();
        lowerRight.setLayout(new GridLayout(2, 2));
        lowerRight.add(this.tempo);
        lowerRight.add(this.subTempo);
        lowerRight.add(this.play);
        lowerRight.add(this.stop);
        c.gridx = 1;
        c.anchor = GridBagConstraints.EAST;
        lower.add(lowerRight, c);

        c.gridx = 0;
        c.gridy = 3;
        c.anchor = GridBagConstraints.CENTER;
        add(lower, c);
    }
}