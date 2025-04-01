import java.awt.Color;
import java.time.LocalDateTime;

public class CardModel {
    private String title;
    private String content;
    private Color color;
    private LocalDateTime createdAt;
    private LocalDateTime lastModified;
    private boolean pinned;
    private String[] tags;

    public CardModel(String title) {
        this(title, "", new Color(225, 205, 180)); // Default light brown color
    }

    public CardModel(String title, String content, Color color) {
        this.title = title;
        this.content = content;
        this.color = color;
        this.createdAt = LocalDateTime.now();
        this.lastModified = LocalDateTime.now();
        this.pinned = false;
        this.tags = new String[0];
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { 
        this.title = title; 
        updateLastModified();
    }

    public String getContent() { return content; }
    public void setContent(String content) { 
        this.content = content; 
        updateLastModified();
    }

    public Color getColor() { return color; }
    public void setColor(Color color) { 
        this.color = color; 
        updateLastModified();
    }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getLastModified() { return lastModified; }

    public boolean isPinned() { return pinned; }
    public void setPinned(boolean pinned) { 
        this.pinned = pinned; 
        updateLastModified();
    }

    public String[] getTags() { return tags; }
    public void setTags(String[] tags) { 
        this.tags = tags; 
        updateLastModified();
    }

    private void updateLastModified() {
        this.lastModified = LocalDateTime.now();
    }

    // Additional functionality
    public void addTag(String tag) {
        String[] newTags = new String[tags.length + 1];
        System.arraycopy(tags, 0, newTags, 0, tags.length);
        newTags[tags.length] = tag;
        tags = newTags;
        updateLastModified();
    }

    public boolean containsText(String searchText) {
        String searchLower = searchText.toLowerCase();
        return title.toLowerCase().contains(searchLower) || 
               content.toLowerCase().contains(searchLower) ||
               tagsContainText(searchLower);
    }

    private boolean tagsContainText(String searchLower) {
        for (String tag : tags) {
            if (tag.toLowerCase().contains(searchLower)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return title + (pinned ? " 📌" : "");
    }
}