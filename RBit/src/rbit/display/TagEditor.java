package rbit.display;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class TagEditor extends JPanel {
    Editor editor;
    JScrollPane scrollPane;
    JTextArea textArea;
    TagEditor(Editor editor) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 60));
        this.editor = editor;
        textArea = new JTextArea();
        scrollPane = new JScrollPane(textArea);
        add(scrollPane);
    }
}