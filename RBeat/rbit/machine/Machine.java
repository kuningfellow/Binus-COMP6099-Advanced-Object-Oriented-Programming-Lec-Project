package rbit.machine;

import java.util.Vector;

import rbit.arrangement.Arrangement;

public class Machine {
    Arrangement arrangement;
    Vector<Track> tracks;
    Player player;

    public Machine() {
        arrangement = new Arrangement();
        tracks = new Vector<>();
        player = null;
    }

    public int getTempo() {
        return arrangement.getTempo();
    }
    public void setTempo(int tempo) {
        arrangement.setTempo(tempo);
    }
    public int getSubTempo() {
        return arrangement.getSubTempo();
    }
    public void setSubTempo(int subTempo) {
        arrangement.setSubTempo(subTempo);
        for (Track track : tracks) {
            track.refresh();
        }
    }
    public int getLength() {
        return arrangement.getLength();
    }
    public void setLength(int l) {
        arrangement.setLength(l);
        for (Track track : tracks) {
            track.refresh();
        }
    }
    public void togglePart(int part, int beat, int subBeat) {
        arrangement.togglePart(part, beat, subBeat);
    }

    public void removeTrack(int k) {
        tracks.get(k).close();       // to prevent memory leak
        tracks.remove(k);
        arrangement.removePart(k);
    }
    public void addTrack(String instrument, int k) {
        k = Math.max(0, Math.min(tracks.size(), k));
        tracks.insertElementAt(new Track(this, arrangement.addPart(instrument, k)), k);
    }

    void trigger(int beat, int subBeat) {
        for (int i = 0; i < tracks.size(); i++) {
            tracks.get(i).play(beat, subBeat);
        }
    }

    public void play() {
        if (player == null) {
            player = new Player(this);
        }
    }
    public void stop() {
        if (player != null) {
            player.kill();
            player = null;
        }
    }
}