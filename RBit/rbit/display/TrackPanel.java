package rbit.display;

import java.awt.Insets;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JPanel;

import java.util.Vector;

import rbit.machine.Track;

class TrackPanel extends JPanel {
    Track track;
    JPanel instrument;
    // Vector<Vector<JPanel>> pattern;
    TrackPattern pattern;
    int k;
    TrackPanel(int k, Track track) {
        this.track = track;
        this.k = k;
        instrument = new JPanel();
        // instrument.setPreferredSize(new Dimension(100, 50));
        instrument.setBackground(Color.PINK);
        pattern = new TrackPattern(track);
        // pattern = new Vector<>();
        // pattern.setPreferredSize(new Dimension(200, 50));
        // pattern.setBackground(Color.BLUE);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 0, 0, 0);
        c.gridx = 0;
        c.gridy = 0;
        add(instrument, c);
        c.gridx = 1;
        add(pattern, c);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                System.out.println(k + " clicked " + e.getX() + " " + e.getY());
            }
        });
    }
}