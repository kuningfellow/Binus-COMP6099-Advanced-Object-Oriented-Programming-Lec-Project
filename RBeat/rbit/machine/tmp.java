package rbit.machine;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.util.Vector;

import rbit.arrangement.Part;

class Tracke {
    Machine machine;
    Part part;
    // Problematic
        // Clip sample;
    // Alternative
    Vector<Vector<Clip> > clips;
    Tracke(Machine machine, Part part) {
        this.machine = machine;
        this.part = part;
        // Problematic
            // try {
            //     sample = AudioSystem.getClip();
            //     sample.open(AudioSystem.getAudioInputStream(new File(part.getInstrument())));
            // } catch (Exception e) {
            //     e.printStackTrace();
            // }
        // Alternative
        this.clips = new Vector<>();
        refresh();
    }
    Clip getSample() {
        try {
            Clip ret;
            ret = AudioSystem.getClip();
            ret.open(AudioSystem.getAudioInputStream(new File(part.getInstrument())));
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    boolean popBeat() {
        if (clips.size() > 0) {
            refresh2(clips.get(clips.size()-1), 0);
            clips.remove(clips.size()-1);
            return true;
        }
        return false;
    }
    void addBeat() {
        Vector<Clip> tmp = new Vector<>();
        int subTempo = machine.getSubTempo();
        for (int i = 0; i < subTempo; i++) {
            tmp.add(getSample());
        }
        clips.add(tmp);
    }
    void refresh() {
        int length = machine.getLength();
        int subTempo = machine.getSubTempo();
        while (clips.size() > length) {
            popBeat();
        }
        while (clips.size() < length) {
            addBeat();
        }
        for (Vector<Clip> beat : clips) {
            refresh2(beat, subTempo);
        }
    }
    void refresh2(Vector<Clip> beat, int subTempo) {
        while (beat.size() < subTempo) {
            beat.add(getSample());
        }
        while (beat.size() > subTempo) {
            beat.get(beat.size()-1).close();
            beat.remove(beat.size()-1);
        }
    }
    void play(int beat, int subBeat) {
        if (part.shouldPlay(beat, subBeat)) {
            // Problematic
                // sample.stop();
                // sample.setFramePosition(0);
                // sample.start();
            // Alternative
            clips.get(beat).get(subBeat).stop();
            clips.get(beat).get(subBeat).setFramePosition(0);
            clips.get(beat).get(subBeat).start();
        }
    }
    void close() {
        // Problematic
            // sample.close();
        // Alternative
        while (popBeat());
    }
}