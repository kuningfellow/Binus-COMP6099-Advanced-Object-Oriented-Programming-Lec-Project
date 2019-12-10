package rbit.display;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import javax.swing.JPanel;

import java.util.Vector;

import rbit.machine.Track;

class TrackPattern extends JPanel {
    Track track;
    Vector<Vector<PatternCell> > pattern;
    TrackPattern(Track track) {
        this.track = track;
        pattern = new Vector<>();
        refresh();
    }

    // removes all and adds all PatternCell
    void refresh() {
        while (pattern.size() > 0) {
            Vector<PatternCell> beat = pattern.get(pattern.size() - 1);
            while (beat.size() > 0) {
                remove(beat.get(beat.size() - 1));      // remove JPanel from this.JPanel
                beat.remove(beat.size() - 1);           // remove JPanel from pattern
            }
            pattern.remove(pattern.size() - 1);
        }
        for (int i = 0; i < track.getLength(); i++) {
            Vector<PatternCell> tmpV = new Vector<>();
            for (int j = 0; j < (1 << track.getSubTempo()); j++) {
                System.out.println("add " + i + "." + j);
                PatternCell tmp = new PatternCell(track, i, j);
                add(tmp);
                tmpV.add(tmp);
            }
            System.out.println("");
            pattern.add(tmpV);
        }
    }
}