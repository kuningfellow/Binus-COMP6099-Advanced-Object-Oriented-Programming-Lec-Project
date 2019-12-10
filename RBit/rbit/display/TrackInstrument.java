package rbit.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import javax.swing.JPanel;

import rbit.machine.Track;

class TrackInstrument extends JPanel {
    /**
     * Tambah tombol remove, edit, volume
     */
    TrackPanel trackPanel;
    Track track;
    TrackInstrument(TrackPanel trackPanel, Track track) {
        this.trackPanel = trackPanel;
        this.track = track;
        setPreferredSize(new Dimension(100, 50));
        setBackground(Color.RED);

        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e) {
                trackPanel.removeTrack();
            }
        });
    }
}