package rbit.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import javax.swing.JPanel;

import rbit.machine.Track;

// Single graphical track beat
class PatternCell extends JPanel {
    TrackPattern trackPattern;
    Track track;
    int beat, subBeat;
    PatternCell(TrackPattern trackPattern, Track track, int beat, int subBeat) {
        setPreferredSize(new Dimension((256 - 32) / (1 << track.getSubTempo()), trackPattern.editor.trackHeight));
        this.trackPattern = trackPattern;
        this.track = track;
        this.beat = beat;
        this.subBeat = subBeat;
        if (track.shouldPlay(beat, subBeat)) {
            setBackground(Color.GREEN);
        } else {
            setBackground(Color.PINK);
        }
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                trackPattern.editor.session.screen.requestFocusInWindow();
                trackPattern.editor.session.isModified = true;
                if (track.togglePattern(beat, subBeat)) {
                    setBackground(Color.GREEN);
                } else {
                    setBackground(Color.PINK);
                }
            }
        });
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (track.shouldPlay(beat, subBeat)) {
            setBackground(Color.GREEN);
        } else {
            setBackground(Color.PINK);
        }
    }
    void refresh() {
        repaint();
    }
}