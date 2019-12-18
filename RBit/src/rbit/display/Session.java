package rbit.display;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;

import rbit.machine.Machine;

public class Session extends JPanel {
    Screen screen;
    Editor editor;
    JButton addTrack, play, stop;
    JSpinner tempo, subTempo, length;
    public Session(Screen screen, Machine machine) {
        this.screen = screen;
        this.editor = new Editor(this, machine);
        init();
    }

    void init() {
        setLayout(new GridBagLayout());

        // addTrack button
        this.addTrack = new JButton("Add Track");
        this.addTrack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editor.addTrack("");
            }
        });

        // play button
        this.play = new JButton("Play");
        this.play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editor.play();
            }
        });

        // stop button
        this.stop = new JButton("Stop");
        stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editor.stop();
            }
        });

        // tempo spinner
        this.tempo = new JSpinner(new SpinnerNumberModel(128, 10, 300, 1));
        this.tempo.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                editor.setTempo((Integer) tempo.getValue());
            }
        });
        this.tempo.setValue(editor.getTempo());

        // subtempo spinner
        this.subTempo = new JSpinner(new SpinnerListModel(Arrays.asList(new String[]{"1/1", "1/2", "1/4", "1/8", "1/16", "1/32"})));
        this.subTempo.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
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

        // length spinner
        this.length = new JSpinner(new SpinnerNumberModel(editor.getLength(), 0, Integer.MAX_VALUE, 1));
        this.length.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                editor.setLength((Integer) length.getValue());
            }
        });

        // Laying out the layout
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = c.gridy = 0;
        add(this.editor, c);

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
        c.gridy = 1;
        c.anchor = GridBagConstraints.CENTER;
        add(lower, c);
    }
}