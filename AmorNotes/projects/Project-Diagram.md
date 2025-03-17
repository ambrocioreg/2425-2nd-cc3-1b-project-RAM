`` mermaid
---
title: Note Taking App
--

classDiagram
    class MainUi{
        + title : String
        - ArrayList<String> : addButton
        - boolean : searchButton
        - boolean : toggleGrid
        # ArrayList<Card> : cards
        + void addCard(card: Card)
        + void editCard(id: String)
        + void readCard(id: String)
        + void deleteCard(id: String)
        + void selectCard(id: String)
        + void archiveCard(id: String)
        + void toggleSidebar()
        + void toggleSearchbar()
        + void syncToCloud()
    }

    class Sidebar{
        - boolean : optionButton
        - boolean : userButton
        + toggleSettings()
        + toggleUserProfile()
    }

    class CloudStorage{
        - String : storageProvider
        - boolean : isConnected
        + void connect(provider : String)
        + void uploadData(data: String)
        + void downloadData()
        + void syncWithLocal(cards : ArrayList<Card>)
    }

    class Card {
        # String : id
        # String : content
        # String : date
        - ArrayList<String> : tags
        - ArrayList<String> : category
        - ArrayList<String> : attachments
        + void addTag()
        + void addAttachment()
        + void updateContent()
    }
    class Searchbar {
        - String : searchEntry
        - boolean : isFilterEnabled
        + void toggleSort()
        + void applyFilter(String)
    }
    class Editor {
        # boolean : isEditorOpen
        + void editCard(card Card)
        + void deleteCard(card Card)
    }

    class Settings {
        - boolean : toggleUIColor
        - boolean : sync
        + void enableDarkMode()
        + void enableLightMode()
        + void toggleAutoSync()
    }

    MainUi <|-- Sidebar
    MainUi <|-- CloudStorage
    MainUi <|-- Card
    MainUi <|-- Editor
    MainUi <|-- Settings
    MainUi <|-- Searchbar


```