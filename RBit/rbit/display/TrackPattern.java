package rbit.display;

import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JPanel;

import java.util.Vector;

import rbit.machine.Track;

// Contains pattern of a track
class TrackPattern extends JPanel {
    Track track;
    Vector<Vector<PatternCell> > pattern;
    GridBagConstraints c = new GridBagConstraints();
    TrackPattern(Track track) {
        this.track = track;
        pattern = new Vector<>();
        setLayout(new GridBagLayout());
        c.insets = new Insets(0, 5, 0, 0);
        c.gridy = 0;
        c.gridx = 0;
        build();
    }
    // removes all and adds all PatternCell
    void build() {
        while (pattern.size() > 0) {
            Vector<PatternCell> beat = pattern.get(pattern.size() - 1);
            while (beat.size() > 0) {
                remove(beat.get(beat.size() - 1));      // remove JPanel from this.JPanel
                beat.remove(beat.size() - 1);           // remove JPanel from pattern
            }
            pattern.remove(pattern.size() - 1);
        }
        c.gridx = 0;
        for (int i = 0; i < track.getLength(); i++) {
            Vector<PatternCell> tmpV = new Vector<>();
            for (int j = 0; j < (1 << track.getSubTempo()); j++) {
                PatternCell tmp = new PatternCell(track, i, j);
                c.gridx++;
                add(tmp, c);
                tmpV.add(tmp);
            }
            pattern.add(tmpV);
        }
    }
}