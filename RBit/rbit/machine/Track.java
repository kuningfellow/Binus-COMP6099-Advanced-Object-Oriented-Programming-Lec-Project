package rbit.machine;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import rbit.arrangement.Part;

public class Track {
    Machine machine;
    Part part;
    Clip[] sample = new Clip[2];        // Alternating sample to prevent audio dropouts. Quite a neat trick
    int bit;
    Track(Machine machine, Part part) {
        this.machine = machine;
        this.part = part;
        this.bit = 0;
        try {
            sample[0] = AudioSystem.getClip();
            sample[0].open(AudioSystem.getAudioInputStream(new File(part.getInstrument())));
            sample[1] = AudioSystem.getClip();
            sample[1].open(AudioSystem.getAudioInputStream(new File(part.getInstrument())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getLength() {
        return machine.getLength();
    }
    public int getSubTempo() {
        return machine.getSubTempo();
    }
    public boolean togglePattern(int beat, int subBeat) {
        return part.togglePattern(beat, subBeat);
    }
    public boolean shouldPlay(int beat, int subBeat) {
        return part.shouldPlay(beat, subBeat);
    }

    void play(int beat, int subBeat) {
        if (part.shouldPlay(beat, subBeat)) {
            sample[bit].stop();
            sample[bit].setFramePosition(0);
            bit ^= 1;           // Reverse Bit
            sample[bit].start();
        }
    }
    void close() {
        sample[0].close();
        sample[1].close();
    }
}