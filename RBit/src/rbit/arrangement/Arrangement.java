package rbit.arrangement;

import java.util.Vector;

public class Arrangement {
    public final int maxSubTempo = 5;
    int beatCount;      // how many beats there are in the arrangement
    int tempo;          // in beats per minute
    int subTempo;       // 2^subTempo beats in a beat
    Vector<Part> parts;

    public Arrangement() {
        tempo = 128;
        beatCount = 4;
        subTempo = 2;
        parts = new Vector<>();
    }

    public int getTempo() {
        return tempo;
    }
    public void setTempo(int tempo) {
        this.tempo = tempo;
    }
    public int getSubTempo() {
        return subTempo;
    }
    public void setSubTempo(int subTempo) {
        this.subTempo = subTempo;
    }
    public int getLength() {
        return beatCount;
    }
    public void setLength(int l) {
        beatCount = l;
        for (Part part : parts) {
            part.setLength(l);
        }
    }

    public void removePart(int k) {
        parts.remove(k);
    }
    public Part addPart(String instrument, int k) {
        Part add = new Part(this, instrument, beatCount);
        parts.insertElementAt(add, k);
        return add;
    }


    public void print() {
        for (int i = 0; i < parts.size(); i++) {
            System.out.println(">> " + parts.get(i).instrument + " " + parts.get(i).pattern.size());
            for (int j = 0; j < parts.get(i).pattern.size(); j++) {
                System.out.print(parts.get(i).pattern.get(j) + ", ");
            }
            System.out.println("");
        }
    }
}