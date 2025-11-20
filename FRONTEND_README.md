# DiveFinder Front-End Implementation

This document describes the Thymeleaf-based front-end implementation for the DiveFinder application.

## Overview

A proof-of-concept front-end has been created using Thymeleaf and Spring MVC. The implementation features an interactive map that displays dive sites as markers, with detailed information available on click.

## Features

- **Interactive Map**: Built with Leaflet.js and OpenStreetMap
- **Dive Site Markers**: Each approved dive site appears as a clickable marker on the map
- **Site Details**: Click on any marker to view detailed information about the dive site
- **Responsive Design**: Clean, modern UI that works on different screen sizes
- **Real-time Data**: Fetches dive sites from the REST API at `/api/v1/sites`

## Implementation Details

### Components Created

1. **HomeController** (`src/main/java/com/divefinder/controllers/mvc/HomeController.java`)
   - Spring MVC controller that serves the main page at `/`

2. **index.html** (`src/main/resources/templates/index.html`)
   - Thymeleaf template with Leaflet.js map integration
   - Responsive layout with header and map container

3. **style.css** (`src/main/resources/static/css/style.css`)
   - Modern, clean styling for the application
   - Responsive design with gradient header
   - Modal dialog for site details

4. **map.js** (`src/main/resources/static/js/map.js`)
   - JavaScript for map initialization
   - Fetches dive sites from REST API
   - Displays markers and handles click events
   - Shows detailed information in a modal

### How It Works

1. When the page loads, the JavaScript initializes a Leaflet map
2. It fetches approved dive sites from `/api/v1/sites` REST endpoint
3. Each dive site is displayed as a marker on the map
4. Clicking a marker shows a popup with basic information
5. Clicking "View Details" opens a modal with full site information
6. The modal displays site name, description, and coordinates

## Testing the Application

### Prerequisites

- Java 17
- MariaDB database running on `localhost:3306`
- Database named `dive_finder` with appropriate schema
- Database populated with dive site data (with `isApproved = true`)

### Running the Application

1. Start your MariaDB database server

2. Build the application:
   ```bash
   ./gradlew build -x test
   ```

3. Run the application:
   ```bash
   ./gradlew bootRun
   ```

4. Open your browser and navigate to:
   ```
   http://localhost:8080/
   ```

### Expected Behavior

- You should see a blue header with "🤿 DiveFinder" title
- Below it, an interactive map showing your dive sites as markers
- Click any marker to see a popup with site information
- Click "View Details" to see the full description in a modal
- Press ESC or click the × button to close the modal

## API Endpoint Used

The front-end consumes the following REST API endpoint:

- **GET** `/api/v1/sites` - Returns list of all approved dive sites

Response format:
```json
[
  {
    "siteName": "Blue Hole",
    "description": "Amazing dive site...",
    "latitude": 28.5926,
    "longitude": 34.5229,
    "approved": true
  }
]
```

## Technology Stack

- **Spring Boot 3.5.7**: Backend framework
- **Thymeleaf**: Server-side templating engine
- **Leaflet.js 1.9.4**: Interactive map library
- **OpenStreetMap**: Free map tiles
- **Vanilla JavaScript**: No additional frameworks required
- **CSS3**: Modern styling with gradients and transitions

## Browser Compatibility

The front-end works on all modern browsers:
- Chrome/Edge (latest)
- Firefox (latest)
- Safari (latest)

## Future Enhancements

Potential improvements for future iterations:
- Add search/filter functionality
- Implement user authentication
- Add ability to create new dive sites from the UI
- Show dive site comments in the details modal
- Add clustering for many markers
- Implement favorites/bookmarks
- Add dive site photos/images

## Security Considerations

- All user input is escaped to prevent XSS attacks
- The application uses the existing REST API security measures
- No sensitive data is exposed in the front-end code

## Files Modified/Created

```
src/
├── main/
│   ├── java/com/divefinder/controllers/mvc/
│   │   └── HomeController.java          (new)
│   └── resources/
│       ├── templates/
│       │   └── index.html               (new)
│       └── static/
│           ├── css/
│           │   └── style.css            (new)
│           └── js/
│               └── map.js               (new)
```

## Screenshot

![DiveFinder Header](https://github.com/user-attachments/assets/abdcfdc2-7c1a-483f-9f0b-aac16102c059)

The header shows the clean, professional design with a gradient blue background and the DiveFinder branding.
