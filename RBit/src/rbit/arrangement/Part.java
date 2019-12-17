package rbit.arrangement;

import java.util.Vector;

public class Part {
    Arrangement arrangement;
    String instrument;
    double volume;
    Vector<Integer> pattern;        // vector of beats. subdivisions are stored as bitmasks
    Part(Arrangement arrangement, String instrument, int beatCount) {
        this.arrangement = arrangement;
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
    
    int getBit(int subBeat) {
        return 1 << (subBeat * ( 1 << (arrangement.maxSubTempo-arrangement.subTempo) ) );
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }
    public double getVolume() {
        return this.volume;
    }
    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }
    public String getInstrument() {
        return instrument;
    }
    public boolean shouldPlay(int beat, int subBeat) {
        return (pattern.get(beat) & getBit(subBeat)) > 0;
    }
    public boolean togglePattern(int beat, int subBeat) {
        int mask = pattern.get(beat);
        mask ^= getBit(subBeat);
        pattern.set(beat, mask);        // bit(beat) mask wkwkwkwk
        return shouldPlay(beat, subBeat);
    }
}