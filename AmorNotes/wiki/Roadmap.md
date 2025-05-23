``` mermaid
---
title: Roadmap
---
flowchart
    %% Documentation
    A[Project Setup] --> C[Design Core Application]
    C --> C1[UML Diagram]

    %% UI Development
    C1 --> M[Create Main.java]
    M --> D[Implement UI Components]
    D --> E1[Implement Sidebar]
    D --> E2[Implement Searchbar]
    D --> E3[Implement Editor]
    D --> E4[Implement Card]

    %% Frontend Development
    E1 --> F[Implement Settings]
    E2 --> F
    E3 --> F
    E4 --> F
    M --> F

    %% Backend Development
    F --> G[Integrate Cloud Storage]

    %% Integration and Testing
    G --> H[Testing and Debugging]
    H --> I[Final Integration and Refinement]
    I --> J[Deployment]

    %% Grouping
    subgraph Documentation [Documentation]
        C
        C1
    end

    subgraph UI [UI Components]
        M
        E1
        E2
        E3
        E4
    end

    subgraph Frontend [Frontend Development]
        D
        F
    end

    subgraph Backend [Backend Development]
        G
    end
```
