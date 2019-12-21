package rbit.display;

import java.awt.Insets;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.util.Vector;

import rbit.arrangement.Arrangement;
import rbit.machine.Machine;

class Editor extends JPanel {
    final int trackHeight = 50;
    Session session;
    Machine machine;
    Vector<TrackPanel> trackPanels;
    JPanel instruments;
    JPanel patterns;
    JScrollPane scrollPane;
    BeatGuide beatGuide;
    Editor(Session session, Machine machine) {
        this.session = session;
        this.machine = machine;


        // initializes tracks
        trackPanels = new Vector<>();
        for (int i = 0; i < machine.tracks.size(); i++) {
            trackPanels.add(new TrackPanel(this, machine.tracks.get(i)));
        }

        // initializes panels
        beatGuide = new BeatGuide(this);
        instruments = new JPanel();
        instruments.setLayout(new GridBagLayout());
        patterns = new JPanel();
        patterns.setLayout(new GridBagLayout());
        scrollPane = new JScrollPane(patterns);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        scrollPane.setRowHeaderView(instruments);
        scrollPane.setColumnHeaderView(beatGuide);
        scrollPane.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                session.screen.requestFocusInWindow();
            }
        });

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        add(scrollPane, c);

        fillEditorPane();
    }

    Arrangement getArrangement() {
        return machine.getArrangement();
    }
    void play(int beat, int subBeat) {
        machine.play(beat, subBeat);
    }
    void stop() {
        machine.stop();
    }
    void removeTrack(TrackPanel trackPanel) {
        machine.removeTrack(trackPanels.indexOf(trackPanel));   // buang dari machine
        trackPanels.remove(trackPanel);                         // buang dari trackPanels
        fillEditorPane();
        revalidate();
        repaint();
    }
    void addTrack(String instrument) {
        addTrack(instrument, machine.tracks.size());
    }
    void addTrack(String instrument, int k) {
        trackPanels.insertElementAt(new TrackPanel(this, machine.addTrack(instrument, k)), k);
        fillEditorPane();
        revalidate();
        repaint();
    }
    void setLength(int length) {
        machine.setLength(length);
        for (TrackPanel track : trackPanels) {
            track.rebuild();
        }
        beatGuide.rebuild();
        revalidate();
        repaint();
    }
    int getLength() {
        return machine.getLength();
    }
    void setTempo(int tempo) {
        machine.setTempo(tempo);
    }
    int getTempo() {
        return machine.getTempo();
    }
    void setSubTempo(int subTempo) {
        machine.setSubTempo(subTempo);
        for (TrackPanel track : trackPanels) {
            track.rebuild();
        }
        beatGuide.rebuild();
        revalidate();
        repaint();
    }
    int getSubTempo() {
        return machine.getSubTempo();
    }
    
    // fills editor scrollable pane with tracks
    void fillEditorPane() {
        GridBagConstraints c = new GridBagConstraints();
        instruments.removeAll();
        patterns.removeAll();
        c.anchor = GridBagConstraints.NORTHWEST;
        c.gridx = 0;
        c.gridy = 0;
        for (int i = 0; i < trackPanels.size(); i++) {
            c.gridy = i;
            if (i == trackPanels.size() - 1) {
                c.weighty = 1;
                c.weightx = 1;
            }
            instruments.add(trackPanels.get(i).instrument, c);
            patterns.add(trackPanels.get(i).pattern, c);
        }
    }
}