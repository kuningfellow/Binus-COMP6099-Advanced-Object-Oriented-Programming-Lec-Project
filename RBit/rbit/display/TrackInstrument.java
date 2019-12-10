package rbit.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JLabel;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import rbit.machine.Track;

class TrackInstrument extends JPanel {
    /**
     * Tambah tombol remove, edit, volume
     */
    TrackPanel trackPanel;
    JSlider volume;
    JLabel volumeIndicator;
    Track track;
    TrackInstrument(TrackPanel trackPanel, Track track) {
        this.trackPanel = trackPanel;
        this.track = track;
        setPreferredSize(new Dimension(200, 50));
        setSize(100, 50);
        this.volume = new JSlider(JSlider.HORIZONTAL, 3, 125, 100);
        this.volume.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                volumeIndicator.setText(String.format("%.2f", track.setVolume(50f * (float) Math.log10((float)volume.getValue()/100))) + " dB");
            }
        });
        this.volumeIndicator = new JLabel(String.format("%.2f", track.getVolume()) + " dB");
        add(volumeIndicator);
        add(volume);
        setBackground(Color.RED);

        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e) {
                trackPanel.removeTrack();
            }
        });
    }
}