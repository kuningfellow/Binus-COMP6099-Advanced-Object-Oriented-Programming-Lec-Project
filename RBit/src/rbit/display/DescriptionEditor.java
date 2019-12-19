package rbit.display;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class DescriptionEditor extends JPanel {
    Editor editor;
    JScrollPane scrollPane;
    JTextArea textArea;
    DescriptionEditor(Editor editor) {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(300, 80));
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTH;
        this.editor = editor;

        JPanel tmp = new JPanel();
        tmp.setLayout(new FlowLayout(FlowLayout.LEFT));
        tmp.setPreferredSize(new Dimension(300, 20));
        tmp.add(new JLabel("Description:"));
        add(tmp, c);


        JPanel ttmp = new JPanel();
        ttmp.setLayout(new BorderLayout());
        textArea = new JTextArea();
        scrollPane = new JScrollPane(textArea);
        ttmp.add(scrollPane);
        ttmp.setPreferredSize(new Dimension(300, 50));

        c.gridy = 1;
        c.weighty = 1;
        add(ttmp, c);
    }
}