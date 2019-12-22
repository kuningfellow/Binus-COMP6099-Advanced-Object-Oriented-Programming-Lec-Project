package rbit.display.session;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.util.Vector;
import java.awt.GridBagConstraints;

import javax.swing.JPanel;
import javax.swing.JLabel;

class BeatGuide extends JPanel {
    Editor editor;
    int startBeat, startSubBeat;
    Vector<Vector<BeatGuideCell>> beatGuideCells;
    BeatGuide(Editor editor) {
        this.editor = editor;
        this.beatGuideCells = new Vector<>();
        startBeat = startSubBeat = 0;
        setLayout(new GridBagLayout());
        rebuild();
    }
    void rebuild() {
        startBeat = Math.max(Math.min(startBeat, editor.getLength() - 1), 0);
        startSubBeat = Math.max(Math.min(startSubBeat, (1 << editor.getSubTempo()) - 1), 0);
        removeAll();
        beatGuideCells.clear();
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;
        c.gridx = c.gridy = 0;
        for (int i = 0; i < editor.getLength(); i++) {
            beatGuideCells.add(new Vector<>());
            for (int j = 0; j < (1 << editor.getSubTempo()); j++) {
                if (i == editor.getLength() - 1 && j == (1 << editor.getSubTempo()) - 1) {
                    c.weightx = 1;
                }
                JPanel tmp = new JPanel();
                tmp.setLayout(new FlowLayout(FlowLayout.LEFT));
                JLabel beat = new JLabel();
                tmp.add(beat);
                if (j == 0) {
                    beat.setText((i+1) + "");
                }
                if (i % 2 == 0) {
                    tmp.setBackground(new Color(165, 165, 165)); 
                } else {
                    tmp.setBackground(new Color(179, 179, 179)); 
                }
                tmp.setPreferredSize(new Dimension(256 / (1 << editor.getSubTempo()), 20));
                BeatGuideCell beatGuideCell = new BeatGuideCell(this, i, j);
                beatGuideCells.get(i).add(beatGuideCell);
                c.gridy = 0;
                add(tmp, c);
                c.gridy = 1;
                add(beatGuideCell, c);
                c.gridx++;
            }
        }
        mark();
    }
    void mark() {
        if (startBeat < beatGuideCells.size()) {
            if (startSubBeat < beatGuideCells.get(startBeat).size()) {
                beatGuideCells.get(startBeat).get(startSubBeat).mark();
            }
        }
    }
    void unmark() {
        if (startBeat < beatGuideCells.size()) {
            if (startSubBeat < beatGuideCells.get(startBeat).size()) {
                beatGuideCells.get(startBeat).get(startSubBeat).unmark();
            }
        }
    }
}