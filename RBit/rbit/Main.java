package rbit;

import rbit.display.Screen;
import rbit.machine.Machine;

class Main {
    Main() {
        Machine machine = new Machine();
        machine.setTempo(128);
        machine.setSubTempo(2);
        // machine.setLength(8);
        machine.setLength(4);
        machine.addTrack("rbit/samples/kicks/kick1.wav", 0);
        machine.tracks.get(0).togglePattern(0, 0);
        machine.tracks.get(0).togglePattern(1, 0);
        machine.tracks.get(0).togglePattern(2, 0);
        machine.tracks.get(0).togglePattern(3, 0);
        machine.addTrack("rbit/samples/cymbals/closedhihats/closedhihat2.wav", 1);
        machine.tracks.get(1).togglePattern(0, 2);
        machine.tracks.get(1).togglePattern(1, 2);
        machine.tracks.get(1).togglePattern(2, 2);
        machine.tracks.get(1).togglePattern(3, 2);
        machine.tracks.get(1).togglePattern(3, 3);
        machine.addTrack("rbit/samples/snares/snare2.wav", 2);
        machine.tracks.get(2).togglePattern(1, 0);
        machine.tracks.get(2).togglePattern(3, 0);
        new Screen(machine);
    }
    public static void main(String[] args) {
        new Main();
    }
}