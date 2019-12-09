package rbit.machine;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.util.Vector;

import rbit.arrangement.Part;

class Track {
    Machine machine;
    Part part;
    Clip sample;
    Track(Machine machine, Part part) {
        this.machine = machine;
        this.part = part;
        try {
            sample = AudioSystem.getClip();
            sample.open(AudioSystem.getAudioInputStream(new File(part.getInstrument())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void refresh() {
        return;
    }
    void play(int beat, int subBeat) {
        if (part.shouldPlay(beat, subBeat)) {
            sample.stop();
            sample.setFramePosition(0);
            sample.start();
        }
    }
    void close() {
        sample.close();
    }
}