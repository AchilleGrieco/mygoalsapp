# MyGoals App - Goal Management API

A robust REST API backend for a productivity application that enables users to create, manage, and track their goals using customizable templates.

## Features

- User authentication and authorization using JWT tokens
- Goal template creation and management
- RESTful API endpoints
- Secure user data handling
- PostgreSQL database integration

## Technology Stack

### Backend
- Framework: Spring Boot
- Security: Spring Security with JWT
- Database: PostgreSQL
- ORM: Spring Data JPA
- Database Access: JDBC
- Build Tool: Maven


### Frontend
- Framework: Flutter
- Repository: [MyGoals App Frontend](https://github.com/AchilleGrieco/mygoalsapp-front-end/tree/main)

## Project Status


## Current State

- Backend: Complete with full functionality
  - User authentication system
  - Goal management features
  - API endpoints
  - Security implementation
- Frontend: Complete (Available in separate repository)
  - Flutter-based UI
  - Full feature implementation
  - Connected with backend API

## Setup and Installation

### Prerequisites
- Java 11 or higher
- Maven
- PostgreSQL
- IDE (recommended: IntelliJ IDEA)


### Environment Variables
The following environment variables need to be configured:
- Database connection details
- JWT secret key
- Other application-specific configurations


### Database Setup
1. Install PostgreSQL
2. Create a new database
3. Configure the database connection in application properties

### Running the Application
1. Clone the repository
2. Configure environment variables
3. Run mvn clean install
4. Start the application using mvn spring:run

## Learning Outcomes

- Implemented JWT authentication system
- Enhanced understanding of Spring Security
- Gained experience in API design and implementation
- Improved Flutter development skills (frontend)

## Contributing

Feel free to fork the project and submit pull requests for any improvements.

## Contact

For any questions or suggestions, please open an issue in the repository.
