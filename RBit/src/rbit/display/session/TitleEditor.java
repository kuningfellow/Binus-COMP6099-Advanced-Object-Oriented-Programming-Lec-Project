package rbit.display.session;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

class TitleEditor extends JPanel {
    Editor editor;
    JScrollPane scrollPane;
    JTextField textField;
    TitleEditor(Editor editor) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300, 20));
        this.editor = editor;
        textField = new JTextField();
        textField.setText(editor.getArrangement().title);
        textField.getDocument().addDocumentListener(new DocumentListener(){
            
            @Override
            public void removeUpdate(DocumentEvent arg0) {
                editor.session.edited = true;
                editor.getArrangement().title = textField.getText();
            }
            
            @Override
            public void insertUpdate(DocumentEvent arg0) {
                editor.session.edited = true;
                editor.getArrangement().title = textField.getText();
            }
            
            @Override
            public void changedUpdate(DocumentEvent arg0) {
            }
        });
        add(textField);
    }
}