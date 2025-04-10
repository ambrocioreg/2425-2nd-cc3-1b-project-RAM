import view.*;
import viewmodel.*;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends JFrame {
    private JButton toggleSidebarButton, saveButton; // Added saveButton
    private Sidebar sidebar;
    private SearchBar searchBar;
    private Editor editor;
    private SettingsViewModel settingsViewModel;
    private List<Card> cards; // List to store all notes

    public Main() {
        setTitle("Amor Notes");
        setSize(960, 540);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Center the window on the screen
        setLocationRelativeTo(null);

        setExtendedState(JFrame.MAXIMIZED_BOTH); // Start maximized

        settingsViewModel = new SettingsViewModel(); // Initialize SettingsViewModel

        // Initialize the list of cards
        cards = new ArrayList<>();

        // Editor
        editor = new Editor(settingsViewModel); // Pass SettingsViewModel to Editor
        add(editor, BorderLayout.CENTER); // Add Editor to the center

        // Sidebar
        sidebar = new Sidebar(this, editor); // Pass the Editor instance to Sidebar
        add(sidebar.getPanel(), BorderLayout.WEST);

        // Control Panel (top bar)
        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        controlPanel.setBackground(new Color(240, 240, 240)); // Light Gray

        // Left section (sidebar toggle and "Notes" label)
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        leftPanel.setBackground(new Color(240, 240, 240)); // Light Gray

        toggleSidebarButton = new JButton("☰");
        toggleSidebarButton.setFont(new Font("Arial Unicode MS", Font.PLAIN, 16));
        toggleSidebarButton.setMargin(new Insets(2, 8, 2, 8));
        toggleSidebarButton.addActionListener(e -> sidebar.toggleSidebar());
        leftPanel.add(toggleSidebarButton);

        JLabel notesLabel = new JLabel("AmorNotes");
        notesLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        leftPanel.add(notesLabel);

        controlPanel.add(leftPanel, BorderLayout.WEST);

        // Right section (search bar and save button)
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightPanel.setBackground(new Color(240, 240, 240)); // Light Gray

        // Search bar
        searchBar = new SearchBar(this);
        searchBar.getTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rightPanel.add(searchBar.getTextField());

        // Save button
        saveButton = new JButton("Save");
        saveButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        saveButton.setMargin(new Insets(2, 8, 2, 8));
        saveButton.addActionListener(e -> saveNotes()); // Trigger saveNotes() on click
        rightPanel.add(saveButton);

        controlPanel.add(rightPanel, BorderLayout.EAST);

        add(controlPanel, BorderLayout.NORTH);

        // Load saved notes
        loadNotes();

        // Add a window listener to save notes on close
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                saveNotes();
            }
        });
    }

    private void saveNotes() {
        // Save the current card being edited
        editor.saveCard();
    
        try (FileWriter writer = new FileWriter("notes.json")) {
            for (Card card : cards) {
                writer.write(card.getTitle() + "\n");
                writer.write(card.getContent() + "\n");
                writer.write("---\n"); // Separator between notes
            }
            System.out.println("Notes saved successfully.");
        } catch (IOException ex) {
            System.err.println("Error saving notes: " + ex.getMessage());
        }
    }

    private void loadNotes() {
        File file = new File("notes.json");
        if (!file.exists()) return;
    
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            String title = null;
            StringBuilder content = new StringBuilder();
    
            while ((line = reader.readLine()) != null) {
                if (line.equals("---")) {
                    if (title != null) {
                        Card card = new Card(title);
                        card.setContent(content.toString());
                        cards.add(card);
                        sidebar.addCard(card); // Add the card to the sidebar
                    }
                    title = null;
                    content = new StringBuilder();
                } else if (title == null) {
                    title = line;
                } else {
                    content.append(line).append("\n");
                }
            }
    
            System.out.println("Notes loaded successfully.");
        } catch (IOException ex) {
            System.err.println("Error loading notes: " + ex.getMessage());
        }
    }

    public Editor getEditor() {
        return editor; // Provide access to the Editor instance
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main app = new Main();
            app.setVisible(true);
        });
    }
}