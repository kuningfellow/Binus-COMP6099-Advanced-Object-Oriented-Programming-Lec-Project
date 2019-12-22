package rbit.display.session;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

class TagEditor extends JPanel {
    Editor editor;
    JScrollPane scrollPane;
    JTextArea textArea;
    TagEditor(Editor editor) {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(300, 80));
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTH;
        this.editor = editor;

        JPanel tmp = new JPanel();
        tmp.setLayout(new FlowLayout(FlowLayout.LEFT));
        tmp.setPreferredSize(new Dimension(300, 20));
        tmp.add(new JLabel("Tags:"));
        add(tmp, c);

        JPanel ttmp = new JPanel();
        ttmp.setLayout(new BorderLayout());
        textArea = new JTextArea();
        textArea.setText(editor.getArrangement().tags);
        textArea.getDocument().addDocumentListener(new DocumentListener(){

            @Override
            public void removeUpdate(DocumentEvent arg0) {
                editor.session.edited = true;
                editor.getArrangement().tags = textArea.getText();
            }

            @Override
            public void insertUpdate(DocumentEvent arg0) {
                editor.session.edited = true;
                editor.getArrangement().tags = textArea.getText();
            }

            @Override
            public void changedUpdate(DocumentEvent arg0) {
            }
        });
        scrollPane = new JScrollPane(textArea);
        ttmp.add(scrollPane);
        ttmp.setPreferredSize(new Dimension(300, 50));

        c.gridy = 1;
        c.weighty = 1;
        add(ttmp, c);
    }
}