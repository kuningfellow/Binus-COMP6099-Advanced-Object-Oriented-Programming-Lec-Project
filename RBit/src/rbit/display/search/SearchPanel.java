package rbit.display.search;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import rbit.database.SearchEngine;
import rbit.display.Screen;

public class SearchPanel extends JPanel {
    Screen screen;
    SearchEngine searchEngine;
    SearchQuery searchQuery;
    Vector<SearchEntry> searchEntries;
    JPanel searchResults;
    public SearchPanel(Screen screen) {
        this.screen = screen;
        searchEngine = new SearchEngine();
        searchQuery = new SearchQuery(this, searchEngine);
        searchEntries = new Vector<>();

        setPreferredSize(new Dimension(250, 500));
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTH;
        searchResults = new JPanel();
        searchResults.setLayout(new GridBagLayout());
        add(searchQuery, c);
        c.insets = new Insets(10, 0, 0, 0);
        c.weighty = 1;
        c.gridy = 1;
        JScrollPane scrollPane = new JScrollPane(searchResults);
        scrollPane.setPreferredSize(new Dimension(250, 400));
        add(scrollPane, c);
    }
    void refresh() {
        clear();
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 0, 5, 0);
        c.anchor = GridBagConstraints.NORTH;
        for (int i = 0; i < searchEngine.results.size(); i++) {
            c.gridy = i+1;
            SearchEntry tmp = new SearchEntry(this, searchEngine.results.get(i));
            searchEntries.add(tmp);
            if (i == searchEngine.results.size() - 1) c.weighty = 1;
            searchResults.add(tmp, c);
        }
        this.revalidate();
    }
    void clear() {
        for (int i = 0; i < searchEntries.size(); i++) {
            searchResults.remove(searchEntries.get(i));
        }
        searchEntries.clear();
        this.revalidate();
    }
}