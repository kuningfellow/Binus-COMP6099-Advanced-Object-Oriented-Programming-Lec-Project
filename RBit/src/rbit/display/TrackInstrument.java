package rbit.display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import rbit.machine.Track;
import rbit.util.DataPath;

class TrackInstrument extends JPanel {
    /**
     * Tambah tombol remove, edit, volume
     */
    final int width = 180;
    TrackPanel trackPanel;
    Track track;
    Color color = new Color(78, 121, 158);
    JSlider volume;
    JLabel volumeIndicator;
    JLabel instrument;
    JButton removeTrack;
    TrackInstrument(TrackPanel trackPanel, Track track) {
        this.trackPanel = trackPanel;
        this.track = track;

        setPreferredSize(new Dimension(width, trackPanel.editor.trackHeight));
        setLayout(new GridBagLayout());
        
        // volume slider
        this.volume = new JSlider(JSlider.HORIZONTAL, 3, 125, 100);
        this.volume.setBackground(color);
        this.volume.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                volumeIndicator.setText(String.format("%.2f", track.setVolume(50f * (float) Math.log10((float)volume.getValue()/100))) + " dB");
            }
        });

        // volume indicator
        this.volumeIndicator = new JLabel(String.format("%.2f", track.getVolume()) + " dB");
        this.volumeIndicator.setPreferredSize(new Dimension(70, 20));
        
        // instrument name and change button
        this.instrument = new JLabel(parseInstrument(track.getInstrument()));
        this.instrument.setPreferredSize(new Dimension(width - 20, 20));
        this.instrument.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e) {
                changeInstrument();
            }
        });

        // remove track button
        this.removeTrack = new JButton("X");
        this.removeTrack.setPreferredSize(new Dimension(20, 20));
        this.removeTrack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                trackPanel.removeTrack();
            }
        });
        
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = c.gridy = 0;
        
        // create upper part
        JPanel upper = new JPanel();
        upper.setLayout(new GridBagLayout());
        upper.setPreferredSize(new Dimension(width, 20));
        upper.setBackground(color);
        add(upper, c);
        
        // create lower part
        JPanel lower = new JPanel();
        lower.setLayout(new GridBagLayout());
        lower.setPreferredSize(new Dimension(width, 20));
        lower.setBackground(color);
        c.gridy = 1; add(lower, c);
        
        c.gridy = 0;
        c.gridx = 0; upper.add(instrument, c);
        c.gridx = 1; upper.add(removeTrack, c);

        // wrap volume slider in a JPanel (otherwise it won't size properly)
        JPanel tmp = new JPanel();
        tmp.setLayout(new BorderLayout());
        tmp.setPreferredSize(new Dimension(110, 20));
        c.gridx = 0;
        tmp.add(volume);

        // add volume (its wrapper) to lower part
        lower.add(tmp, c);

        // add volume indicator to lower part
        c.gridx = 1;
        lower.add(volumeIndicator, c);

        setBackground(color);
    }

    String parseInstrument(String instrument) {
        if (instrument.equals("")) {
            return "N/A";
        }
        for (int i = instrument.length()-1; i >= 0; i--) {
            if (instrument.charAt(i) == '/' || instrument.charAt(i) == '\\') {
                return instrument.substring(i+1, instrument.length());
            }
        }
        return instrument;
    }
    void changeInstrument() {
        JFileChooser fileChooser;
        if (track.getInstrument().equals("")) {
            fileChooser = new JFileChooser("src/rbit/samples/");
        } else {
            fileChooser = new JFileChooser(track.getInstrument());
        }
        int retVal = fileChooser.showOpenDialog(this.trackPanel.editor.session.screen);
        if (retVal == JFileChooser.APPROVE_OPTION) {
            track.setInstrument(DataPath.getPath(fileChooser.getSelectedFile()));
            this.instrument.setText(parseInstrument(track.getInstrument()));
        }
    }
}