package rbit.display;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.util.Vector;

import rbit.machine.Machine;

public class Screen extends JFrame {
    public Screen(Machine machine) {
        setTitle("RBit");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        Editor editor = new Editor(this, machine);
        add(editor);
        machine.play();
        setVisible(true);
    }
}