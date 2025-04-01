import java.util.ArrayList;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Card {
    String id;
    String Content;
    String date;
    ArrayList<String> tags;
    ArrayList<String> category;
    ArrayList<String> attachments;

    // Constructor
    public Card() {
        this.id = UUID.randomUUID().toString();
        this.Content = "";
        this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.tags = new ArrayList<>();
        this.category = new ArrayList<>();
        this.attachments = new ArrayList<>();
    }

    // Add a tag if it doesn't already exist
    void addTag(String tag) {
        if (!tags.contains(tag)) {
            tags.add(tag);
            updateDate();
        }
    }

    // Remove a tag
    void removeTag(String tag) {
        tags.remove(tag);
        updateDate();
    }

    // Add an attachment
    void addAttachment(String filePath) {
        attachments.add(filePath);
        updateDate();
    }

    // Remove an attachment
    void removeAttachment(String filePath) {
        attachments.remove(filePath);
        updateDate();
    }

    // Update card content
    void updateContent(String newContent) {
        this.Content = newContent;
        updateDate();
    }

    // Add to category
    void addToCategory(String categoryName) {
        if (!category.contains(categoryName)) {
            category.add(categoryName);
            updateDate();
        }
    }

    // Remove from category
    void removeFromCategory(String categoryName) {
        category.remove(categoryName);
        updateDate();
    }

    // Update modification date
    private void updateDate() {
        this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    // Getters
    public String getId() { return id; }
    public String getContent() { return Content; }
    public String getDate() { return date; }
    public ArrayList<String> getTags() { return new ArrayList<>(tags); }
    public ArrayList<String> getCategory() { return new ArrayList<>(category); }
    public ArrayList<String> getAttachments() { return new ArrayList<>(attachments); }
}