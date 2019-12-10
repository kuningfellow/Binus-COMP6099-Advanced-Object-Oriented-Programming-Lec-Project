package rbit.display;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;

import rbit.machine.Machine;

public class Screen extends JFrame {
    public Screen(Machine machine) {
        setTitle("RBit");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 500);
        Editor editor = new Editor(machine);
        add(editor);
        setVisible(true);
        machine.play();
        // try {
        //     Thread.sleep(3000);
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
        // editor.remove();
        // setVisible(true);
    }
}