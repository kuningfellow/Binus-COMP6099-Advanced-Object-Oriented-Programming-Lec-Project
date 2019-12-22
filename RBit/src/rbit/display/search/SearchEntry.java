package rbit.display.search;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import javax.swing.JLabel;
import javax.swing.JPanel;

import rbit.database.SearchResult;

class SearchEntry extends JPanel {
    SearchPanel searchPanel;
    SearchResult searchResult;
    SearchEntry(SearchPanel searchPanel, SearchResult searchResult) {
        this.searchPanel = searchPanel;
        this.searchResult = searchResult;
        JLabel title = new JLabel(searchResult.getTitle());
        JLabel description = new JLabel(searchResult.getDescription());

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        setPreferredSize(new Dimension(240, 50));
        c.anchor = GridBagConstraints.NORTHWEST;
        c.weightx = 1;
        add(title, c);
        c.gridy = 1;
        c.weighty = 1;
        add(description, c);
        setBackground(new Color(86, 134, 140));
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (searchPanel.screen.promptIfUnsaved()) {
                    searchPanel.screen.openSession(searchResult.getPath());
                    searchPanel.screen.requestFocusInWindow();
                }
            }
        });
    }
}