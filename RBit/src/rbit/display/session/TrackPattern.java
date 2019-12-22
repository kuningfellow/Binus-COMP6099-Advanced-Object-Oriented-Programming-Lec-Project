package rbit.display.session;

import java.awt.Insets;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JPanel;

import java.util.Vector;

import rbit.machine.Track;

// Contains pattern of a track
class TrackPattern extends JPanel {
    TrackPanel trackPanel;
    Track track;
    Vector<Vector<PatternCell> > pattern;
    TrackPattern(TrackPanel trackPanel, Track track) {
        this.trackPanel = trackPanel;
        this.track = track;
        pattern = new Vector<>();
        setLayout(new GridBagLayout());
        build();
    }
    // removes all and adds all PatternCell
    void build() {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 0, 0, 32 / (1 << track.getSubTempo()));
        c.gridy = 0;
        c.gridx = 0;
        while (pattern.size() > 0) {
            Vector<PatternCell> beat = pattern.get(pattern.size() - 1);
            while (beat.size() > 0) {
                remove(beat.get(beat.size() - 1));      // remove PatternCell from this.JPanel
                beat.remove(beat.size() - 1);           // remove PatternCell from pattern
            }
            pattern.remove(pattern.size() - 1);
        }
        c.gridx = 0;
        for (int i = 0; i < track.getLength(); i++) {
            Vector<PatternCell> tmpV = new Vector<>();
            for (int j = 0; j < (1 << track.getSubTempo()); j++) {
                PatternCell tmp = new PatternCell(this, track, i, j);
                c.gridx++;
                add(tmp, c);
                tmpV.add(tmp);
            }
            pattern.add(tmpV);
        }
    }
}