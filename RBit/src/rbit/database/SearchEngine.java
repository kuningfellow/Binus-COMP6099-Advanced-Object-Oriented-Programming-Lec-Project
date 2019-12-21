package rbit.database;

import java.util.Vector;

public class SearchEngine {
    Vector<SearchResult> results;
    public SearchEngine() {
    }
    void search(String query) {
        Vector<String> tags = new Vector<>();
        String tag = "";
        for (int i = 0; i < query.length(); i++) {
            if (query.charAt(i) == '\n' || query.charAt(i) == '\r' || query.charAt(i) == ' ' || query.charAt(i) == '\t') {
                if (!tag.equals("")) {
                    tags.add(tag);
                }
                tag = "";
            } else {
                tag += query.charAt(i);
            }
        }
        if (!tag.equals("")) {
            tags.add(tag);
        }
        results = DataBase.search(tags);
    }
}