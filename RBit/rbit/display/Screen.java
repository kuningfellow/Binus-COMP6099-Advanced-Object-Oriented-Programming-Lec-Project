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
        setSize(1500, 800);
        Editor editor = new Editor(this, machine);
        add(editor);
        setVisible(true);
        machine.play();
        // try {
        //     Thread.sleep(3000);
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
        // System.out.println("stop");
        // machine.tracks.get(0).close();
        // editor.setLength(8);
        // System.out.println("ok?");
    }
}