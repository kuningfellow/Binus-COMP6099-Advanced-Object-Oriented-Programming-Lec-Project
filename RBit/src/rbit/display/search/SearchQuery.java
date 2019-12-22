package rbit.display.search;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import rbit.database.SearchEngine;

class SearchQuery extends JPanel {
    SearchEngine searchEngine;
    SearchPanel searchPanel;
    JButton searchButton;
    JTextField searchField;
    SearchQuery(SearchPanel searchPanel, SearchEngine searchEngine) {
        this.searchPanel = searchPanel;
        this.searchEngine = searchEngine;

        this.searchButton = new JButton("Search");
        this.searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchEngine.search(searchField.getText());
                searchPanel.refresh();
            }
        });
        this.searchButton.setPreferredSize(new Dimension(90, 25));

        this.searchField = new JTextField();
        JPanel tmp = new JPanel(new BorderLayout());
        tmp.add(searchField);
        tmp.setPreferredSize(new Dimension(160, 25));


        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        add(tmp, c);
        c.gridx = 1;
        add(searchButton, c);
    }
}