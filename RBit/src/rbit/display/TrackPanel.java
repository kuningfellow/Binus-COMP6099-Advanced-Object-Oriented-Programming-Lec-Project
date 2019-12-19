package rbit.display;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JPanel;

import rbit.machine.Track;

// Contains instrument and its controls along with the pattern
class TrackPanel extends JPanel {
    Editor editor;
    Track track;
    TrackInstrument instrument;
    TrackPattern pattern;
    TrackPanel(Editor editor, Track track) {
        this.editor = editor;
        this.track = track;
        instrument = new TrackInstrument(this, track);
        pattern = new TrackPattern(editor, track);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = c.gridy = 0;
        add(instrument, c);     // add the instrument panel
        c.gridx = 1;
        add(pattern, c);        // add the pattern panel
    }
    void rebuild() {
        pattern.build();
    }
    void removeTrack() {
        editor.removeTrack(this);
    }
}