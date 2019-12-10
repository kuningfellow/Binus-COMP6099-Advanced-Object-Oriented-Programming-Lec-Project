package rbit.display;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JPanel;

import java.util.Vector;

import rbit.machine.Track;
import rbit.machine.Machine;

class Editor extends JPanel {
    Vector<TrackPanel> tracks;
    TrackPanel tmp2;
    Editor(Machine machine) {
        tracks = new Vector<>();
        // setSize(800, 500);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 3;
        c.gridy = 0;
        TrackPanel tmp = new TrackPanel(1, machine.tracks.get(0));
        tracks.add(tmp);
        add(tmp, c);
        c.gridy = 1;
        tmp2 = new TrackPanel(2, machine.tracks.get(1));
        tracks.add(tmp2);
        add(tmp2, c);
        c.gridy = 2;
        TrackPanel tmp3 = new TrackPanel(3, machine.tracks.get(2));
        tracks.add(tmp3);
        add(tmp3, c);
    }
    void remove() {
        remove(tmp2);
    }
}