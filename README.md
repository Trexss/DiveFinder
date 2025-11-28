# DiveFinder

DiveFinder is a web application that allows users to discover, share, and explore dive sites. Users can browse dive locations on an interactive map, view detailed information about each site, and share their experiences through comments.

## Features

- **Interactive Map**: Browse dive sites on an interactive map powered by Leaflet and OpenStreetMap
- **Dive Site Management**: Create, view, and delete dive sites with detailed descriptions and GPS coordinates
- **Comments System**: Add comments to dive sites to share experiences and tips
- **User Authentication**: Register and login functionality with session management
- **Admin Approval**: Dive sites require approval before being publicly visible

## Technology Stack

- **Backend**: Java 17 with Spring Boot 3.5.7
- **Database**: MariaDB
- **Frontend**: Thymeleaf templates with HTML/CSS
- **Map Integration**: Leaflet.js with OpenStreetMap
- **Build Tool**: Gradle
- **Validation**: Spring Boot Starter Validation

## Prerequisites

- Java 17 or higher
- MariaDB
- Gradle (or use the included Gradle wrapper)

## Getting Started

### Database Setup

1. Install and start MariaDB
2. Create a database named `dive_finder`:
   ```sql
   CREATE DATABASE dive_finder;
   ```
3. Update the database credentials in `src/main/resources/application.properties` if needed:
   ```properties
   database.url=jdbc:mariadb://localhost:3306/dive_finder
   database.username=root
   database.password=root
   ```

### Running the Application

Using Gradle wrapper:

```bash
./gradlew bootRun
```

Or on Windows:

```bash
gradlew.bat bootRun
```

The application will start at `http://localhost:8080`

### Building the Application

```bash
./gradlew build
```

### Running Tests

```bash
./gradlew test
```

## API Endpoints

### Dive Sites

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/sites` | Get all approved dive sites |
| GET | `/api/v1/sites/{id}` | Get a specific dive site by ID |
| POST | `/api/v1/sites` | Create a new dive site |
| DELETE | `/api/v1/sites/{id}` | Delete a dive site |
| GET | `/api/v1/sites/{id}/comments` | Get comments for a dive site |
| POST | `/api/v1/sites/{id}/comments` | Add a comment to a dive site |

### Users

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/users/{id}` | Get user by ID |
| POST | `/api/v1/users` | Create a new user |

## Web Pages

| Route | Description |
|-------|-------------|
| `/` | Home page with interactive map |
| `/auth/login` | User login page |
| `/auth/register` | User registration page |
| `/auth/logout` | Logout user |
| `/sites/{id}` | View dive site details |
| `/sites/new` | Create new dive site |

## Project Structure

```
src/
├── main/
│   ├── java/com/
│   │   ├── DiveFinderApplication.java      # Main application entry point
│   │   └── divefinder/
│   │       ├── config/                      # Configuration classes
│   │       ├── controllers/
│   │       │   ├── mvc/                     # Web controllers (Thymeleaf)
│   │       │   └── rest/                    # REST API controllers
│   │       ├── exceptions/                  # Custom exceptions
│   │       ├── helpers/                     # DTO mappers and utilities
│   │       ├── models/                      # Entity and DTO classes
│   │       ├── repositories/                # Data access layer
│   │       └── services/                    # Business logic layer
│   └── resources/
│       ├── application.properties           # Application configuration
│       ├── messages.properties              # Internationalization messages
│       ├── static/css/                      # Stylesheets
│       └── templates/                       # Thymeleaf HTML templates
└── test/                                    # Test classes
```

## License

This project is open source and available for personal and educational use.
