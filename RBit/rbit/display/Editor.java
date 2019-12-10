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
    Screen screen;
    Machine machine;
    Vector<TrackPanel> trackPanels;
    Editor(Screen screen, Machine machine) {
        this.screen = screen;
        this.machine = machine;
        trackPanels = new Vector<>();
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        for (int i = 0; i < machine.tracks.size(); i++) {
            trackPanels.add(new TrackPanel(this, machine.tracks.get(i)));
        }
        for (int i = 0; i < trackPanels.size(); i++) {
            c.gridy = i;
            add(trackPanels.get(i), c);
        }
    }
    void removeTrack(TrackPanel trackPanel) {
        machine.removeTrack(trackPanels.indexOf(trackPanel));   // buang dari machine
        remove(trackPanel);                                     // buang dari JPanel
        trackPanels.remove(trackPanel);                         // buang dari trackPanels
        revalidate();
        repaint();
    }
}