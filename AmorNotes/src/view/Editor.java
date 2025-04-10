package view;

import model.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.undo.*; // Import for undo/redo functionality

public class Editor extends JPanel {
    private JLabel titleLabel;
    private JTextArea contentArea;
    private Card currentCard;
    private JToolBar toolBar;
    private UndoManager undoManager;

    public Editor() {
        setLayout(new BorderLayout());
        setBackground(new Color(255, 255, 255));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        titleLabel = new JLabel("Title");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        toolBar = new JToolBar();
        addFormattingButtons();
        add(toolBar, BorderLayout.SOUTH);

        contentArea = new JTextArea("Notes...");
        contentArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setMargin(new Insets(10, 10, 10, 10)); // Add padding
        add(new JScrollPane(contentArea), BorderLayout.CENTER);

        undoManager = new UndoManager();
        contentArea.getDocument().addUndoableEditListener(e -> undoManager.addEdit(e.getEdit()));
    }

    private void addFormattingButtons() {
        JButton boldButton = new JButton("B");
        boldButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boldButton.addActionListener(e -> toggleBold());
        toolBar.add(boldButton);

        JButton italicButton = new JButton("I");
        italicButton.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        italicButton.addActionListener(e -> toggleItalic());
        toolBar.add(italicButton);

        JButton underlineButton = new JButton("U");
        underlineButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        underlineButton.addActionListener(e -> toggleUnderline());
        toolBar.add(underlineButton);

        JButton alignLeftButton = new JButton("L");
        alignLeftButton.addActionListener(e -> alignText(SwingConstants.LEFT));
        toolBar.add(alignLeftButton);

        JButton alignCenterButton = new JButton("C");
        alignCenterButton.addActionListener(e -> alignText(SwingConstants.CENTER));
        toolBar.add(alignCenterButton);

        JButton alignRightButton = new JButton("R");
        alignRightButton.addActionListener(e -> alignText(SwingConstants.RIGHT));
        toolBar.add(alignRightButton);

        JButton colorButton = new JButton("Color");
        colorButton.addActionListener(e -> changeTextColor());
        toolBar.add(colorButton);

        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(e -> undoAction());
        toolBar.add(undoButton);

        JButton redoButton = new JButton("Redo");
        redoButton.addActionListener(e -> redoAction());
        toolBar.add(redoButton);

        JComboBox<String> fontSizeBox = new JComboBox<>(new String[]{"12", "14", "16", "18", "20", "24", "28", "32"});
        fontSizeBox.addActionListener(e -> changeFontSize((String) fontSizeBox.getSelectedItem()));
        toolBar.add(fontSizeBox);

        JComboBox<String> fontFamilyBox = new JComboBox<>(GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getAvailableFontFamilyNames());
        fontFamilyBox.addActionListener(e -> changeFontFamily((String) fontFamilyBox.getSelectedItem()));
        toolBar.add(fontFamilyBox);
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

    public void loadCard(Card card) {
        this.currentCard = card;
        titleLabel.setText(card.getTitle());
        contentArea.setText(card.getContent());
    }

    public void saveCard() {
        if (currentCard != null) {
            currentCard.setContent(contentArea.getText());
        }
    }
}
