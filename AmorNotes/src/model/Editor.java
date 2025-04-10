package model;

// Editor.java
import javax.swing.*;

public class Editor {
    public void editCard(Card card) {
        // Implement card editing functionality
        String newTitle = JOptionPane.showInputDialog("Edit note title:", card.getTitle());
        if (newTitle != null && !newTitle.trim().isEmpty()) {
            card.setTitle(newTitle);
        }
    }

    public void deleteCard(Card card) {
        // Implement card deletion confirmation
        int option = JOptionPane.showConfirmDialog(null, 
            "Delete this note?", "Confirm Deletion", 
            JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            // Card will be removed from the list by the caller
        }
    }
}