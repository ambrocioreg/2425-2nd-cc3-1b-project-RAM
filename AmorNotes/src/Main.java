import model.*;
import view.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class Main extends JFrame {
    private JButton toggleSidebarButton, addNoteButton;
    private JPanel cardPanel;
    private Sidebar sidebar;
    private SearchBar searchBar;
    private ArrayList<Card> cards;
    private final Color LIGHT_BROWN = new Color(225, 205, 180);

    public Main() {
        setTitle("Amor Notes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Sidebar
        sidebar = new Sidebar(this);
        add(sidebar.getPanel(), BorderLayout.WEST);

        // Control Panel (top bar)
        JPanel controlPanel = new JPanel(new GridBagLayout());
        controlPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 0, 5);

        // Sidebar toggle (=)
        toggleSidebarButton = new JButton("=");
        toggleSidebarButton.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        toggleSidebarButton.setMargin(new Insets(2, 8, 2, 8));
        toggleSidebarButton.addActionListener(e -> sidebar.toggleSidebar());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        controlPanel.add(toggleSidebarButton, gbc);

        // Search bar
        searchBar = new SearchBar(this);
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        controlPanel.add(searchBar.getTextField(), gbc);

        // Add Note button (+)
        addNoteButton = new JButton("+");
        addNoteButton.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        addNoteButton.setMargin(new Insets(2, 8, 2, 8));
        addNoteButton.addActionListener(e -> addCard(new Card("Note " + (cards.size() + 1))));
        gbc.gridx = 2;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        controlPanel.add(addNoteButton, gbc);

        add(controlPanel, BorderLayout.NORTH);

        // Card Panel (3x3 grid)
        cardPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        cardPanel.setBackground(new Color(240, 235, 230));
        add(cardPanel, BorderLayout.CENTER);

        cards = new ArrayList<>();
    }

    private void addCard(Card card) {
        if (cards.size() >= 9) {
            JOptionPane.showMessageDialog(this, "Maximum 9 notes allowed.");
            return;
        }

        cards.add(card);

        // Replace JButton with your custom CardView
        CardView view = new CardView(card);
        cardPanel.add(view);

        // Repaint and revalidate to refresh layout
        cardPanel.revalidate();
        cardPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main app = new Main();
            app.setVisible(true);
        });
    }

    public JPanel getCardPanel() {
        return cardPanel;
    }
}