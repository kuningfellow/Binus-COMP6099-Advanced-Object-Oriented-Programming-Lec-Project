package rbit.display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import javax.swing.JPanel;

import rbit.machine.Track;

class PatternCell extends JPanel {
    Track track;
    int beat, subBeat;
    PatternCell(Track track, int beat, int subBeat) {
        setPreferredSize(new Dimension(50, 50));
        this.track = track;
        this.beat = beat;
        this.subBeat = subBeat;
        if (track.shouldPlay(beat, subBeat)) {
            setBackground(Color.GREEN);
        } else {
            setBackground(Color.PINK);
        }
        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e) {
                if (track.togglePattern(beat, subBeat)) {
                    setBackground(Color.GREEN);
                } else {
                    setBackground(Color.PINK);
                }
            }
        });
    }
}