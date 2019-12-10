package rbit.display;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import rbit.machine.Track;

class TrackInstrument extends JPanel {
    TrackPanel trackPanel;
    Track track;
    TrackInstrument(TrackPanel trackPanel, Track track) {
        this.trackPanel = trackPanel;
        this.track = track;
        setPreferredSize(new Dimension(100, 100));
        setBackground(Color.RED);
    }
}