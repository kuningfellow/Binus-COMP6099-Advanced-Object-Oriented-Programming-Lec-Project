package rbit.arrangement;

import java.util.Vector;

public class Part {
    String instrument;
    double volume;
    Vector<Integer> pattern;        // vector of beats. subdivisions are stored as bitmasks
    Part(String instrument, int beatCount) {
        this.instrument = instrument;
        this.volume = 0;
        this.pattern = new Vector<>();
        setLength(beatCount);
    }

    void setLength(int l) {
        while (pattern.size() > l) {
            pattern.remove(pattern.size()-1);
        }
        while (pattern.size() < l) {
            pattern.add(0);
        }
    }
    
    public String getInstrument() {
        return instrument;
    }
    public boolean shouldPlay(int beat, int subBeat) {
        return (pattern.get(beat) & (1 << subBeat)) > 0;
    }
    public void togglePattern(int beat, int subBeat) {
        int mask = pattern.get(beat);
        mask ^= (1 << subBeat);
        pattern.set(beat, mask);        // bit(beat) mask wkwkwkwk
    }
}