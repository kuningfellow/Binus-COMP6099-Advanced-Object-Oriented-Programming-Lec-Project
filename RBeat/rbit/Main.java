package rbit;

import javax.swing.JFrame;

import rbit.machine.Machine;

class Main {
    Main() {
        JFrame frame = new JFrame();
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Machine machine = new Machine();
        machine.setTempo(140);
        machine.setSubTempo(4);
        machine.setLength(8);
        machine.addTrack("rbit/samples/kicks/kick1.wav", 0);
        machine.togglePart(0, 0, 0);
        machine.togglePart(0, 4, 0);
        machine.togglePart(0, 5, 2);
        machine.togglePart(0, 5, 3);
        machine.togglePart(0, 7, 0);
        machine.togglePart(0, 7, 2);
        machine.addTrack("rbit/samples/snares/snare1.wav", 1);
        machine.togglePart(1, 2, 0);
        machine.togglePart(1, 6, 0);
        machine.addTrack("rbit/samples/cymbals/closedhihats/closedhihat2.wav", 2);
        machine.togglePart(2, 0, 0);
        machine.togglePart(2, 0, 2);
        machine.togglePart(2, 0, 3);
        machine.togglePart(2, 1, 0);
        machine.togglePart(2, 1, 2);
        machine.togglePart(2, 3, 0);
        machine.togglePart(2, 3, 2);
        machine.togglePart(2, 3, 3);
        machine.togglePart(2, 0+4, 0);
        machine.togglePart(2, 0+4, 2);
        machine.togglePart(2, 0+4, 3);
        machine.togglePart(2, 1+4, 0);
        machine.togglePart(2, 1+4, 2);
        machine.togglePart(2, 2+4, 0);
        machine.togglePart(2, 3+4, 0);
        machine.togglePart(2, 3+4, 1);
        machine.togglePart(2, 3+4, 2);
        machine.togglePart(2, 3+4, 3);
        machine.play();
    }
    public static void main(String[] args) {
        new Main();
    }
}