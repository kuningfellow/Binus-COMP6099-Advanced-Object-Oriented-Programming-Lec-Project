package rbit.database;

public class SearchResult {
    String path;
    String title, description, tags;
    SearchResult(String path, String title, String description, String tags) {
        this.path = path;
        this.title = title;
        this.description = description;
        this.tags = tags;
    }
    public String getPath() {
        return path;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getTags() {
        return tags;
    }
}