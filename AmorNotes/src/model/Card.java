package model;

// CardModel.java
public class Card {
    private String title;
    private String content;

    public Card(String title) {
        this.title = title;
        this.content = ""; // Default empty content
    }

    public boolean containsText(String query) {
        return title.toLowerCase().contains(query.toLowerCase()) ||
               content.toLowerCase().contains(query.toLowerCase());
    }

    @Override
    public String toString() {
        return title;
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
