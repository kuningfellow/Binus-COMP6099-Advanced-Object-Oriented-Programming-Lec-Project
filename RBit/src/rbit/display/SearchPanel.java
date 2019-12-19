package rbit.display;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTextArea;

class SearchPanel extends JPanel {
    SearchPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(250, 500));
        add(new JTextArea());
    }
}