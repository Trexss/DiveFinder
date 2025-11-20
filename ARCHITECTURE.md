# DiveFinder Front-End Architecture

## Architecture Overview

```
┌─────────────────────────────────────────────────────────────┐
│                         Browser                              │
│  ┌────────────────────────────────────────────────────┐    │
│  │           index.html (Thymeleaf)                   │    │
│  │  ┌──────────────────────────────────────────┐     │    │
│  │  │         Header (Gradient Blue)           │     │    │
│  │  └──────────────────────────────────────────┘     │    │
│  │  ┌──────────────────────────────────────────┐     │    │
│  │  │    Interactive Map (Leaflet.js)          │     │    │
│  │  │    - Markers for each dive site          │     │    │
│  │  │    - Click to show popup                 │     │    │
│  │  │    - View Details button                 │     │    │
│  │  └──────────────────────────────────────────┘     │    │
│  │                                                     │    │
│  │  ┌──────────────────────────────────────────┐     │    │
│  │  │   Modal (Hidden by default)              │     │    │
│  │  │   - Site Name                            │     │    │
│  │  │   - Description                          │     │    │
│  │  │   - Coordinates                          │     │    │
│  │  └──────────────────────────────────────────┘     │    │
│  └────────────────────────────────────────────────────┘    │
│           │                               ▲                 │
│           │ map.js                        │                 │
│           ▼                               │                 │
│  ┌────────────────────────────────────────────────────┐    │
│  │         JavaScript Layer                           │    │
│  │  - Initialize map                                  │    │
│  │  - Fetch dive sites from API                       │    │
│  │  - Display markers                                 │    │
│  │  - Handle click events                             │    │
│  │  - Show/hide modal                                 │    │
│  └────────────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────────┘
                              │
                              │ HTTP GET /api/v1/sites
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    Spring Boot Application                   │
│  ┌────────────────────────────────────────────────────┐    │
│  │         HomeController (MVC)                       │    │
│  │         GET / -> index.html                        │    │
│  └────────────────────────────────────────────────────┘    │
│                                                              │
│  ┌────────────────────────────────────────────────────┐    │
│  │      DiveSiteRestController (REST API)             │    │
│  │      GET /api/v1/sites                             │    │
│  │         │                                           │    │
│  │         ▼                                           │    │
│  │      DiveSiteService                               │    │
│  │         │                                           │    │
│  │         ▼                                           │    │
│  │      DiveSiteRepository                            │    │
│  └────────────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    MariaDB Database                          │
│                    dive_finder.dive_sites                    │
└─────────────────────────────────────────────────────────────┘
```

## Request Flow

### 1. Initial Page Load
```
User -> Browser
  └─> GET http://localhost:8080/
        └─> HomeController.home()
              └─> Returns "index" (Thymeleaf template)
                    └─> Renders index.html with static resources
                          └─> Loads map.js and style.css
```

### 2. Dive Sites Loading
```
Browser (map.js)
  └─> GET http://localhost:8080/api/v1/sites
        └─> DiveSiteRestController.getApprovedSites()
              └─> DiveSiteService.getAllApprovedSites()
                    └─> DiveSiteRepository.findAll()
                          └─> Query: SELECT * FROM dive_sites WHERE isApproved = true
                                └─> Returns List<DiveSite>
                                      └─> Mapped to List<DiveSiteDto>
                                            └─> JSON response to browser
                                                  └─> map.js creates markers
```

### 3. User Interaction
```
User clicks marker
  └─> Leaflet shows popup
        └─> User clicks "View Details"
              └─> map.js.showSiteDetails()
                    └─> Displays modal with full site information
                          └─> User can close with ESC or X button
```

## Components

### Frontend Components

| Component | Technology | Purpose |
|-----------|-----------|---------|
| index.html | Thymeleaf | Main page template |
| style.css | CSS3 | Styling and responsive design |
| map.js | JavaScript | Map logic and API interaction |
| Leaflet.js | External CDN | Map rendering library |
| OpenStreetMap | Tile Server | Map tiles provider |

### Backend Components

| Component | Type | Purpose |
|-----------|------|---------|
| HomeController | @Controller | Serves Thymeleaf template |
| DiveSiteRestController | @RestController | REST API endpoints |
| DiveSiteService | @Service | Business logic |
| DiveSiteRepository | @Repository | Database access |

## Data Flow

```
DiveSite Entity (Database)
  ├─> id: int
  ├─> siteName: String
  ├─> description: String (LOB)
  ├─> latitude: double
  ├─> longitude: double
  └─> isApproved: boolean

DiveSiteDto (API Response)
  ├─> siteName: String
  ├─> description: String
  ├─> latitude: double
  ├─> longitude: double
  └─> approved: boolean

JavaScript Object (Client)
  ├─> siteName: String
  ├─> description: String
  ├─> latitude: double
  └─> longitude: double
```

## Security Measures

1. **XSS Prevention**: All user input is escaped using `escapeHtml()` function
2. **HTTPS Ready**: Can be configured for secure connections
3. **Input Validation**: Backend validation using Jakarta Validation
4. **No SQL Injection**: Using JPA/Hibernate with parameterized queries
5. **CORS**: Can be configured for cross-origin requests if needed

## Responsive Design

The application uses CSS media queries and flexible layouts to ensure proper display on:
- Desktop browsers (1920x1080 and above)
- Tablets (768px - 1024px)
- Mobile devices (320px - 767px)

## Browser Support

- Chrome 90+
- Firefox 88+
- Safari 14+
- Edge 90+

## Performance Considerations

1. **CDN Usage**: Leaflet.js loaded from unpkg.com CDN
2. **Lazy Loading**: Map tiles loaded on-demand
3. **Minimal Dependencies**: No heavy frontend frameworks
4. **Efficient Rendering**: Only approved sites are fetched and displayed
5. **Single API Call**: All dive sites fetched once on page load
