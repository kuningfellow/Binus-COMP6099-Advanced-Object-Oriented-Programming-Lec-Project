package rbit.display;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JPanel;
import javax.swing.JLabel;

class BeatGuide extends JPanel {
    Editor editor;
    BeatGuide(Editor editor) {
        this.editor = editor;
        setLayout(new GridBagLayout());
        rebuild();
    }
    void rebuild() {
        removeAll();
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = c.gridy = 0;
        for (int i = 0; i < editor.machine.getLength(); i++) {
            for (int j = 0; j < (1 << editor.machine.getSubTempo()); j++) {
                JLabel beat = new JLabel();
                if (j == 0) {
                    beat.setText((i+1) + "");
                }
                beat.setPreferredSize(new Dimension(256 / (1 << editor.machine.getSubTempo()), 20));
                JLabel subBeat = new JLabel((j+1) + "/" + (1 << editor.machine.getSubTempo()));
                subBeat.setPreferredSize(new Dimension(256 / (1 << editor.machine.getSubTempo()), 20));
                c.gridy = 0;
                add(beat, c);
                c.gridy = 1;
                add(subBeat, c);
                c.gridx++;
            }
        }
    }
}