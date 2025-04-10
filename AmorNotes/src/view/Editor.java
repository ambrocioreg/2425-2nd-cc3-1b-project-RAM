package view;

import model.*;
import viewmodel.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.undo.*; // Import for undo/redo functionality
import java.io.*; // Import for file handling
import javax.swing.text.*; // Import for rich text features

public class Editor extends JPanel {
    private JLabel titleLabel; // Ensure this is declared
    private JTextPane contentArea;
    private Card currentCard;
    private UndoManager undoManager;
    private JPopupMenu contextMenu;
    
    public Editor(SettingsViewModel settingsViewModel) {
        setLayout(new BorderLayout());
        setBackground(new Color(255, 255, 255));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        titleLabel = new JLabel("Default Title"); // Initialize titleLabel with a default value
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(Color.BLACK);
        // Add titleLabel to the appropriate panel or layout

        contentArea = new JTextPane();
        contentArea.setFont(new Font("Segoe UI", Font.PLAIN, settingsViewModel.getDefaultFontSize())); // Use default font size
        contentArea.setMargin(new Insets(30, 30, 30, 30)); // Increased padding (top, left, bottom, right)
        contentArea.setBackground(Color.WHITE); // White background
        contentArea.setForeground(Color.BLACK); // Black text
        contentArea.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200))); // Medium Gray Border
        add(new JScrollPane(contentArea), BorderLayout.CENTER);

        undoManager = new UndoManager();
        contentArea.getDocument().addUndoableEditListener(e -> undoManager.addEdit(e.getEdit()));

        createContextMenu();
        contentArea.setComponentPopupMenu(contextMenu);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Color gradientStart = new Color(240, 240, 240); // Light Gray
        Color gradientEnd = new Color(220, 220, 220); // Slightly Darker Gray
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Enable anti-aliasing
        g2d.setPaint(new GradientPaint(0, 0, gradientStart, 0, getHeight(), gradientEnd));
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    private void toggleBold() {
        StyledDocument doc = contentArea.getStyledDocument();
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setBold(attrs, !StyleConstants.isBold(contentArea.getCharacterAttributes()));
        doc.setCharacterAttributes(contentArea.getSelectionStart(), contentArea.getSelectionEnd() - contentArea.getSelectionStart(), attrs, false);
    }

    private void toggleItalic() {
        StyledDocument doc = contentArea.getStyledDocument();
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setItalic(attrs, !StyleConstants.isItalic(contentArea.getCharacterAttributes()));
        doc.setCharacterAttributes(contentArea.getSelectionStart(), contentArea.getSelectionEnd() - contentArea.getSelectionStart(), attrs, false);
    }

    private void toggleUnderline() {
        StyledDocument doc = contentArea.getStyledDocument();
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setUnderline(attrs, !StyleConstants.isUnderline(contentArea.getCharacterAttributes()));
        doc.setCharacterAttributes(contentArea.getSelectionStart(), contentArea.getSelectionEnd() - contentArea.getSelectionStart(), attrs, false);
    }

    private void alignText(int alignment) {
        StyledDocument doc = contentArea.getStyledDocument();
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setAlignment(attrs, alignment);
        doc.setParagraphAttributes(contentArea.getSelectionStart(), contentArea.getSelectionEnd() - contentArea.getSelectionStart(), attrs, true);
    }

    private void changeTextColor() {
        JColorChooser colorChooser = new JColorChooser(contentArea.getForeground());
        for (Component comp : colorChooser.getComponents()) {
            comp.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Increase font size
        }
        JDialog dialog = JColorChooser.createDialog(this, "Choose Text Color", true, colorChooser, e -> {
            Color selectedColor = colorChooser.getColor();
            if (selectedColor != null) {
                contentArea.setForeground(selectedColor);
            }
        }, null);
        dialog.setSize(600, 400); // Increase dialog size
        dialog.setVisible(true);
    }

    private void undoAction() {
        if (undoManager.canUndo()) {
            undoManager.undo();
        }
    }

    private void redoAction() {
        if (undoManager.canRedo()) {
            undoManager.redo();
        }
    }

    private void changeFontSize(String size) {
        int fontSize = Integer.parseInt(size);
        StyledDocument doc = contentArea.getStyledDocument();
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setFontSize(attrs, fontSize);
        doc.setCharacterAttributes(contentArea.getSelectionStart(), contentArea.getSelectionEnd() - contentArea.getSelectionStart(), attrs, false);
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                contentArea.read(reader, null);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error opening file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveToFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text Files (*.txt)", "txt"));
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.getName().endsWith(".txt")) {
                file = new File(file.getAbsolutePath() + ".txt");
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(contentArea.getText());
                JOptionPane.showMessageDialog(this, "File saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void createContextMenu() {
        contextMenu = new JPopupMenu();

        JMenuItem boldItem = new JMenuItem("Bold");
        boldItem.addActionListener(e -> toggleBold());
        contextMenu.add(boldItem);

        JMenuItem italicItem = new JMenuItem("Italic");
        italicItem.addActionListener(e -> toggleItalic());
        contextMenu.add(italicItem);

        JMenuItem underlineItem = new JMenuItem("Underline");
        underlineItem.addActionListener(e -> toggleUnderline());
        contextMenu.add(underlineItem);

        contextMenu.addSeparator();

        JMenuItem alignLeftItem = new JMenuItem("Align Left");
        alignLeftItem.addActionListener(e -> alignText(SwingConstants.CENTER));
        contextMenu.add(alignLeftItem);

        JMenuItem alignCenterItem = new JMenuItem("Align Center");
        alignCenterItem.addActionListener(e -> alignText(SwingConstants.RIGHT));
        contextMenu.add(alignCenterItem);

        JMenuItem alignRightItem = new JMenuItem("Align Right");
        alignRightItem.addActionListener(e -> alignText(SwingConstants.LEFT));
        contextMenu.add(alignRightItem);

        contextMenu.addSeparator();

        JMenuItem colorItem = new JMenuItem("Change Text Color");
        colorItem.addActionListener(e -> changeTextColor());
        contextMenu.add(colorItem);

        contextMenu.addSeparator();

        JMenuItem undoItem = new JMenuItem("Undo");
        undoItem.addActionListener(e -> undoAction());
        contextMenu.add(undoItem);

        JMenuItem redoItem = new JMenuItem("Redo");
        redoItem.addActionListener(e -> redoAction());
        contextMenu.add(redoItem);

        contextMenu.addSeparator();

        JMenuItem openItem = new JMenuItem("Open File");
        openItem.addActionListener(e -> openFile());
        contextMenu.add(openItem);

        JMenuItem saveItem = new JMenuItem("Save File");
        saveItem.addActionListener(e -> saveToFile());
        contextMenu.add(saveItem);

        contextMenu.addSeparator();

        JMenu fontSizeMenu = new JMenu("Font Size");
        String[] fontSizes = {"8", "10", "12", "14", "16", "18", "20", "24", "28", "32", "36", "48", "64"};
        for (String size : fontSizes) {
            JMenuItem sizeItem = new JMenuItem(size);
            sizeItem.addActionListener(e -> changeFontSize(size));
            fontSizeMenu.add(sizeItem);
        }
        contextMenu.add(fontSizeMenu);

        JMenuItem fontChangerItem = new JMenuItem("Font Changer");
        fontChangerItem.addActionListener(e -> openFontChangerDialog());
        contextMenu.add(fontChangerItem);

        contextMenu.addSeparator();
    }

    private void openFontChangerDialog() {
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        JPanel panel = new JPanel(new BorderLayout());
        JLabel previewLabel = new JLabel("Font Preview");
        previewLabel.setFont(contentArea.getFont());
        previewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(previewLabel, BorderLayout.CENTER);

        JComboBox<String> fontComboBox = new JComboBox<>(fonts);
        fontComboBox.setSelectedItem(contentArea.getFont().getFamily()); // Default to current font
        fontComboBox.addActionListener(e -> {
            String selectedFont = (String) fontComboBox.getSelectedItem();
            if (selectedFont != null) {
                previewLabel.setFont(new Font(selectedFont, Font.PLAIN, 16));
            }
        });
        panel.add(fontComboBox, BorderLayout.NORTH);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Font Changer",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String selectedFont = (String) fontComboBox.getSelectedItem();
            if (selectedFont != null) {
                StyledDocument doc = contentArea.getStyledDocument();
                SimpleAttributeSet attrs = new SimpleAttributeSet();
                StyleConstants.setFontFamily(attrs, selectedFont);
                doc.setCharacterAttributes(contentArea.getSelectionStart(), contentArea.getSelectionEnd() - contentArea.getSelectionStart(), attrs, false);
            }
        }
    }

    public void loadCard(Card card) {
        this.currentCard = card;
        if (titleLabel != null) {
            titleLabel.setText(card.getTitle()); // Safely update the title
        } else {
            System.err.println("Error: titleLabel is not initialized.");
        }
        contentArea.setText(card.getContent());
    }

    public void saveCard() {
        if (currentCard != null) {
            currentCard.setContent(contentArea.getText());
        }
    }
}