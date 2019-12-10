package rbit;

import javax.swing.JFrame;

import rbit.machine.Track;
import rbit.machine.Machine;

class Test {
    Test() {
        JFrame frame = new JFrame();
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

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
        machine.addTrack("rbit/samples/snares/snare1.wav", 2);
        machine.tracks.get(2).togglePattern(1, 0);
        machine.tracks.get(2).togglePattern(3, 0);

        // machine.tracks.get(0).togglePattern(0, 0);
        // machine.tracks.get(0).togglePattern(4, 0);
        // machine.tracks.get(0).togglePattern(5, 2);
        // machine.tracks.get(0).togglePattern(5, 3);
        // machine.tracks.get(0).togglePattern(7, 0);
        // machine.tracks.get(0).togglePattern(7, 2);
        // machine.addTrack("rbit/samples/snares/snare1.wav", 1);
        // machine.tracks.get(1).togglePattern(2, 0);
        // machine.tracks.get(1).togglePattern(6, 0);
        // machine.addTrack("rbit/samples/cymbals/closedhihats/closedhihat2.wav", 2);
        // machine.tracks.get(2).togglePattern(0, 0);
        // machine.tracks.get(2).togglePattern(0, 2);
        // machine.tracks.get(2).togglePattern(0, 3);
        // machine.tracks.get(2).togglePattern(1, 0);
        // machine.tracks.get(2).togglePattern(1, 2);
        // machine.tracks.get(2).togglePattern(3, 0);
        // machine.tracks.get(2).togglePattern(3, 2);
        // machine.tracks.get(2).togglePattern(3, 3);
        // machine.tracks.get(2).togglePattern(0+4, 0);
        // machine.tracks.get(2).togglePattern(0+4, 2);
        // machine.tracks.get(2).togglePattern(0+4, 3);
        // machine.tracks.get(2).togglePattern(1+4, 0);
        // machine.tracks.get(2).togglePattern(1+4, 2);
        // machine.tracks.get(2).togglePattern(2+4, 0);
        // machine.tracks.get(2).togglePattern(3+4, 0);
        // machine.tracks.get(2).togglePattern(3+4, 1);
        // machine.tracks.get(2).togglePattern(3+4, 2);
        // machine.tracks.get(2).togglePattern(3+4, 3);
        machine.play();
        // try {
        //     Thread.sleep(4000);
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
        // machine.setSubTempo(3);
        // System.out.println("Changed subTempo, Pattern still correct, yay");
    }
    public static void main(String[] args) {
        new Test();
    }
}