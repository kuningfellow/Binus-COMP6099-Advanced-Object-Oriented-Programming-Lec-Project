package rbit.database;

class SearchResult {
    String path;
    String title, description, tags;
    SearchResult(String path, String title, String description, String tags) {
        this.path = path;
        this.title = title;
        this.description = description;
        this.tags = tags;
    }
}