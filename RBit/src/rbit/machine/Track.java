package rbit.machine;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

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
        sample[0] = sample[1] = null;
        setInstrument(part.getInstrument());
    }

    public void setInstrument(String instrument) {
        synchronized (machine) {
            if (!instrument.equals("")) {
                close();
                try {
                    sample[0] = AudioSystem.getClip();
                    sample[0].open(AudioSystem.getAudioInputStream(new File(instrument)));
                    sample[1] = AudioSystem.getClip();
                    sample[1].open(AudioSystem.getAudioInputStream(new File(instrument)));
                    part.setInstrument(instrument);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public String getInstrument() {
        return part.getInstrument();
    }
    public int getLength() {
        return machine.getLength();
    }
    public int getMaxSubTempo() {
        return machine.getMaxSubTempo();
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

    public double getVolume() {
        return part.getVolume();
    }
    public float setVolume(float volume) {
        part.setVolume(volume);
        FloatControl gainControl;
        if (sample[0] != null) {
            gainControl = (FloatControl) sample[0].getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume);
        }
        if (sample[1] != null) {
            gainControl = (FloatControl) sample[1].getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume);
        }
        return volume;
    }

    void play(int beat, int subBeat) {
        if (sample[0] != null && sample[1] != null && part.shouldPlay(beat, subBeat)) {
            sample[bit].stop();
            sample[bit].setFramePosition(0);
            bit ^= 1;           // Reverse Bit
            sample[bit].start();
        }
    }
    void close() {
        if (sample[0] != null) {
            sample[0].stop();
            sample[0].close();
        }
        if (sample[1] != null) {
            sample[1].stop();
            sample[1].close();
        }
        sample[0] = sample[1] = null;
    }
}