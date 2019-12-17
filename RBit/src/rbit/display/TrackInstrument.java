package rbit.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;

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
        setPreferredSize(new Dimension(100, 50));
        this.trackPanel = trackPanel;
        this.track = track;
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = c.gridy = 0;
        // setSize(100, 50);
        this.volume = new JSlider(JSlider.HORIZONTAL, 3, 125, 100);
        this.volume.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                volumeIndicator.setText(String.format("%.2f", track.setVolume(50f * (float) Math.log10((float)volume.getValue()/100))) + " dB");
            }
        });
        this.volumeIndicator = new JLabel(String.format("%.2f", track.getVolume()) + " dB");
        JPanel tmp = new JPanel();
        tmp.setLayout(new BorderLayout());
        tmp.setPreferredSize(new Dimension(100, 20));
        tmp.add(volume);
        add(tmp, c);
        c.gridy = 1;
        add(volumeIndicator, c);
        setBackground(Color.RED);

        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e) {
                // track.close();
                // track.setInstrument("rbit/samples/toms/Tom Hi 606.wav");
                // trackPanel.removeTrack();
                trackPanel.editor.setSubTempo(trackPanel.editor.trackPanels.indexOf(trackPanel) + 1);
                // trackPanel.editor.addTrack("none", 1);
            }
        });
    }
}