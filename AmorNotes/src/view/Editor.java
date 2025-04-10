package view;

import model.*;
import viewmodel.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.undo.*; // Import for undo/redo functionality
import java.io.*; // Import for file handling

public class Editor extends JPanel {
    private JLabel titleLabel; // Ensure this is declared
    private JTextArea contentArea;
    private Card currentCard;
    private JToolBar toolBar;
    private UndoManager undoManager;
    
    public Editor(SettingsViewModel settingsViewModel) {
        setLayout(new BorderLayout());
        setBackground(new Color(255, 255, 255));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        titleLabel = new JLabel("Default Title"); // Initialize titleLabel with a default value
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(Color.BLACK);
        // Add titleLabel to the appropriate panel or layout

        toolBar = new JToolBar();
        toolBar.setFloatable(false); // Disable floating
        toolBar.setBackground(new Color(240, 240, 245)); // Soft Gray-Blue
        toolBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Add padding
        addFormattingButtons();
        add(toolBar, BorderLayout.SOUTH);

        contentArea = new JTextArea("Notes...");
        contentArea.setFont(new Font("Segoe UI", Font.PLAIN, settingsViewModel.getDefaultFontSize())); // Use default font size
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setMargin(new Insets(30, 30, 30, 30)); // Increased padding (top, left, bottom, right)
        contentArea.setBackground(Color.WHITE); // White background
        contentArea.setForeground(Color.BLACK); // Black text
        contentArea.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200))); // Medium Gray Border
        add(new JScrollPane(contentArea), BorderLayout.CENTER);

        undoManager = new UndoManager();
        contentArea.getDocument().addUndoableEditListener(e -> undoManager.addEdit(e.getEdit()));
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

    private void addFormattingButtons() {
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5)); // Set layout with margins and spacing

        JButton boldButton = createStyledButton("B");
        boldButton.addActionListener(e -> toggleBold());
        toolBar.add(boldButton);

        JButton italicButton = createStyledButton("I");
        italicButton.addActionListener(e -> toggleItalic());
        toolBar.add(italicButton);

        JButton underlineButton = createStyledButton("U");
        underlineButton.addActionListener(e -> toggleUnderline());
        toolBar.add(underlineButton);

        toolBar.addSeparator(new Dimension(10, 0)); // Add spacing

        JButton alignLeftButton = createStyledButton("L");
        alignLeftButton.addActionListener(e -> alignText(SwingConstants.LEFT));
        toolBar.add(alignLeftButton);

        JButton alignCenterButton = createStyledButton("C");
        alignCenterButton.addActionListener(e -> alignText(SwingConstants.CENTER));
        toolBar.add(alignCenterButton);

        JButton alignRightButton = createStyledButton("R");
        alignRightButton.addActionListener(e -> alignText(SwingConstants.RIGHT));
        toolBar.add(alignRightButton);

        toolBar.addSeparator(new Dimension(10, 0)); // Add spacing

        JButton colorButton = createStyledButton("Color");
        colorButton.addActionListener(e -> changeTextColor());
        toolBar.add(colorButton);

        toolBar.addSeparator(new Dimension(10, 0)); // Add spacing

        JButton undoButton = createStyledButton("Undo");
        undoButton.addActionListener(e -> undoAction());
        toolBar.add(undoButton);

        JButton redoButton = createStyledButton("Redo");
        redoButton.addActionListener(e -> redoAction());
        toolBar.add(redoButton);

        toolBar.addSeparator(new Dimension(10, 0)); // Add spacing

        JComboBox<String> fontSizeBox = new JComboBox<>(new String[]{"12", "14", "16", "18", "20", "24", "28", "32"});
        fontSizeBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        fontSizeBox.addActionListener(e -> changeFontSize((String) fontSizeBox.getSelectedItem()));
        toolBar.add(fontSizeBox);

        JComboBox<String> fontFamilyBox = new JComboBox<>(GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getAvailableFontFamilyNames());
        fontFamilyBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        fontFamilyBox.addActionListener(e -> changeFontFamily((String) fontFamilyBox.getSelectedItem()));
        toolBar.add(fontFamilyBox);

        toolBar.addSeparator(new Dimension(10, 0)); // Add spacing

        JButton openButton = createStyledButton("Open");
        openButton.addActionListener(e -> openFile());
        toolBar.add(openButton);

        JButton saveButton = createStyledButton("Save");
        saveButton.addActionListener(e -> saveToFile());
        toolBar.add(saveButton);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        button.setBackground(new Color(240, 240, 240)); // Light gray background
        button.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200))); // Border
        button.setPreferredSize(new Dimension(50, 30)); // Set button size
        return button;
    }

    private void toggleBold() {
        Font currentFont = contentArea.getFont();
        contentArea.setFont(currentFont.deriveFont(currentFont.getStyle() ^ Font.BOLD));
    }

    private void toggleItalic() {
        Font currentFont = contentArea.getFont();
        contentArea.setFont(currentFont.deriveFont(currentFont.getStyle() ^ Font.ITALIC));
    }

    private void toggleUnderline() {
        // Underline is not directly supported by JTextArea, so this is a placeholder.
        JOptionPane.showMessageDialog(this, "Underline is not supported in plain text.");
    }

    private void alignText(int alignment) {
        // Placeholder: JTextArea does not support alignment directly.
        JOptionPane.showMessageDialog(this, "Text alignment is not supported in plain text.");
    }

    private void changeTextColor() {
        Color selectedColor = JColorChooser.showDialog(this, "Choose Text Color", contentArea.getForeground());
        if (selectedColor != null) {
            contentArea.setForeground(selectedColor);
        }
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
        Font currentFont = contentArea.getFont();
        contentArea.setFont(new Font(currentFont.getFontName(), currentFont.getStyle(), fontSize));
    }

    private void changeFontFamily(String fontFamily) {
        Font currentFont = contentArea.getFont();
        contentArea.setFont(new Font(fontFamily, currentFont.getStyle(), currentFont.getSize()));
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
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                contentArea.write(writer);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
