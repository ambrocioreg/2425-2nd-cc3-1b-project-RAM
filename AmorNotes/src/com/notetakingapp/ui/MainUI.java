import java.util.ArrayList;

public class MainUI {
    public String title;
    public ArrayList<String> addButton;
    public boolean searchButton;
    public boolean toggleGrid;
    public ArrayList<Card> cards;
    private Sidebar sidebar;
    private CloudStorage cloudStorage;
    private SearchBar searchBar;
    private Settings settings;

    public MainUI() {
        this.sidebar = new Sidebar();
        this.cloudStorage = new CloudStorage();
        this.searchBar = new SearchBar();
        this.settings = new Settings();
    }

    public void addCard(Card card) {}
    public void editCard(String id) {}
    public void readCard(String id) {}
    public void deleteCard(String id) {}
    public void selectCard(String id) {}
    public void archiveCard(String id) {}
    public void toggleSidebar() {}
    public void toggleSearchBar() {}
    public void syncToCloud() {}

    
    public static void main(String[] args) {
        MainUI app = new MainUI();
        System.out.println("Amor Notes!");
    }
}
