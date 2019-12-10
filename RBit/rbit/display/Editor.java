package rbit.display;

import java.awt.Insets;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JPanel;

import java.util.Vector;

import rbit.machine.Track;
import rbit.machine.Machine;

class Editor extends JPanel {
    Vector<TrackPanel> trackPanels;
    Editor(Machine machine) {
        trackPanels = new Vector<>();
        // setPreferredSize(new Dimension(800, 500));
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        // c.insets = new Insets(5, 0, 0, 0);
        c.gridx = 3;
        c.gridy = 0;
        // setSize(800, 500);
        for (int i = 0; i < machine.tracks.size(); i++) {
            c.gridy = i;
            add(new TrackPanel(this, machine.tracks.get(i)), c);
        }
        // for (int i = 0; i < trackPanels.size(); i++) {
        //     c.gridy = i;
        //     add(trackPanels.get(i), c);
        // }
    }
}