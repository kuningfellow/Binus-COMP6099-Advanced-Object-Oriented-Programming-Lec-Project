package rbit.display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import javax.swing.JLabel;
import javax.swing.JPanel;

class BeatGuideCell extends JPanel {
    BeatGuide beatGuide;
    int beat, subBeat;
    BeatGuideCell(BeatGuide beatGuide, int beat, int subBeat) {
        setPreferredSize(new Dimension(256 / (1 << beatGuide.editor.getSubTempo()), 20));
        setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel tmp = new JLabel((subBeat+1) + "/" + (1 << beatGuide.editor.getSubTempo()));
        add(tmp);
        this.beatGuide = beatGuide;
        this.beat = beat;
        this.subBeat = subBeat;
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                beatGuide.unmark();
                beatGuide.startBeat = beat;
                beatGuide.startSubBeat = subBeat;
                beatGuide.mark();
            }
        });
        unmark();
    }
    void mark() {
        setBackground(new Color(255, 144, 203));
    }
    void unmark() {
        setBackground(new Color(142, 118, 179));
    }
}