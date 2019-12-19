package rbit.machine;

import java.util.Vector;

import rbit.arrangement.Arrangement;
import rbit.arrangement.Part;

public class Machine {
    Arrangement arrangement;
    public Vector<Track> tracks;
    Player player;

    public Machine() {
        arrangement = new Arrangement();
        tracks = new Vector<>();
        player = null;
    }
    public Machine(Arrangement arrangement) {
        this.arrangement = arrangement;
        tracks = new Vector<>();
        for (int i = 0; i < arrangement.parts.size(); i++) {
            addTrack(arrangement.parts.get(i));
        }
        player = null;
    }

    public Arrangement getArrangement() {
        return arrangement;
    }
    public int getTempo() {
        return arrangement.getTempo();
    }
    public void setTempo(int tempo) {
        arrangement.setTempo(tempo);
        if (player != null) {
            player.calculateWait();
        }
    }
    public int getMaxSubTempo() {
        return arrangement.maxSubTempo;
    }
    public int getSubTempo() {
        return arrangement.getSubTempo();
    }
    public void setSubTempo(int subTempo) {
        synchronized (this) {
            subTempo = Math.max(0, Math.min(arrangement.maxSubTempo, subTempo));
            arrangement.setSubTempo(subTempo);
            if (player != null) {
                player.calculateWait();
            }
        }
    }
    public int getLength() {
        return arrangement.getLength();
    }
    public void setLength(int l) {
        synchronized (this) {
            arrangement.setLength(l);
        }
    }

    public void removeTrack(int k) {
        synchronized (this) {
            tracks.get(k).close();       // to prevent memory leak
            tracks.remove(k);
            arrangement.removePart(k);
        }
    }

    // adds new track to the kth index
    public Track addTrack(String instrument, int k) {
        synchronized (this) {
            k = Math.max(0, Math.min(tracks.size(), k));
            Track ret = new Track(this, arrangement.addPart(instrument, k));
            tracks.insertElementAt(ret, k);
            return ret;
        }
    }
    public Track addTrack(Part part) {
        synchronized (this) {
            Track ret = new Track(this, part);
            tracks.add(ret);
            return ret;
        }
    }

    void trigger(int beat, int subBeat) {
        synchronized (this) {
            for (int i = 0; i < tracks.size(); i++) {
                tracks.get(i).play(beat, subBeat);
            }
        }
    }

    public void play(int beat, int subBeat) {
        if (player == null) {
            player = new Player(this, beat, subBeat);
        }
    }
    public void stop() {
        if (player != null) {
            player.kill();
            player = null;
        }
    }
}