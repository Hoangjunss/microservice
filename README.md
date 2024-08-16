# Portfolio
    This project was developed by a team of three members with the aim of creating an environment where users can interact with each other about their profiles, find suitable jobs, and build engaging personal portfolios.

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [TechnologiesUsed](#technologiesused)
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [Contact](#contact)

## Introduction
- Overview: A comprehensive portfolio management system designed for users, HRs, managers, and admins. The platform allows users to create profiles, manage projects, and apply for jobs. HRs and managers can oversee job applications, manage job postings, and control company details. Admins have the highest level of control, managing the overall system.
- Purpose: The purpose of this project is to develop and refine skills in building scalable, high-performance web applications using Angular for the frontend and a microservices architecture for the backend. This project also aims to provide hands-on experience with modern software development practices, including RESTful API design, authentication, and authorization with Spring Security and JWT, and efficient data management with both SQL (MySQL) and NoSQL (MongoDB) databases. Additionally, it enhances teamwork and collaboration skills in a real-world development environment.
- Audience: This portfolio is targeted at recruiters, users seeking to create and showcase their professional profiles, and administrators responsible for managing the platform and overseeing company interactions within the microservices ecosystem.

## Features
1. User Profile Management:
- Users can create and customize their profiles, including adding personal details, skills, and work experience.
- Ability to create and manage projects, with options to link these projects to the user's profile.
- View company information and explore available jobs.
- Apply for jobs directly through the platform.
2. Job Management:
- HR managers can create, edit, and manage job postings specific to their companies.
- Jobs are linked to specific companies managed by the HR, providing a streamlined job application process for users.
3. Application Review:
- HR managers can review jobs submitted by users.
- Option to approve or reject user apply jobs based on profile suitability.
- Notifications are sent to users regarding the status of their job applications.
4. Company Management:
Managers have the ability to manage company details, including editing company information.
Managers can oversee job postings and coordinate with HR managers to ensure job openings are properly listed.
5. Admin Controls:
- Admins have the highest level of control, managing both companies and their associated managers.
6. Authentication and Security:
- Secure user authentication using JWT tokens, ensuring safe and reliable access to the platform.
- Role-based access control to ensure users, HR managers, managers, and admins only access authorized sections.
7. Microservices Architecture:
- The project is built using a microservices architecture, allowing for scalable and modular development.
- Services communicate via REST APIs, with load balancing and service discovery handled by Eureka Server.
- API Gateway for centralized request handling and security enforcement.
8. Technology Stack:
- Frontend: Angular framework for a responsive and dynamic user interface.
- Backend: Spring Boot, with integrated features such as Spring Security, JWT for authentication, API Gateway for request management, OpenFeign for service communication, and Eureka Server for service discovery.
- Database: MySQL for structured, relational data and MongoDB for flexible, NoSQL data management.
- Service Communication: OpenFeign is utilized for seamless inter-service communication within the microservices architecture. API Gateway and Eureka Server are employed for centralized routing and service discovery, ensuring efficient and secure operations across services.

# Project Installation Guide

## Overview

This guide will walk you through the process of setting up the project, including installing necessary tools, setting up databases, and running the services.

## Prerequisites

Before you start, make sure you have the following installed:

1. **Node.js and Angular CLI**
   - Download and install Node.js from [Node.js official site](https://nodejs.org/).
   - Install Angular CLI globally:
     ```bash
     npm install -g @angular/cli
     ```

2. **Java and Maven**
   - Install Java Development Kit (JDK) version 21 or higher from [Oracle](https://www.oracle.com/java/technologies/downloads/#java21).
   - Install Maven from [Maven official site](https://maven.apache.org/install.html).

3. **MySQL or XAMPP**
   - Download and install MySQL or XAMPP from [XAMPP official site](https://www.apachefriends.org/index.html).
   - Set up MySQL and create the following databases:
     - `portfolio`
     - `project1`
     - `notification1`

4. **MongoDB**
   - Download and install MongoDB from [MongoDB official site](https://www.mongodb.com/try/download/community).
   - Start the MongoDB server.

## Step 1: Clone the Repository

Clone the project repository to your local machine:
```bash
git clone https://github.com/Hoangjunss/microservice.git
cd microservice
```

## Step 2: Setting Up Angular (Client)
```bash
    cd microservice/client
    npm install
```
- Start the Angular development server:
```bash 
    ng serve
```
The Angular app should now be running on http://localhost:4200/.

## Step 3: Set Up MySQL Databases
1. For MySQL
    Log in to MySQL: ```bash mysql -u root -p ```
    Create the databases:
        CREATE DATABASE portfolio;
        CREATE DATABASE project1;
        CREATE DATABASE notification1;
2. For XAMPP
    - Start XAMPP Control Panel.
    - Start Apache and MySQL services.
    - Open phpMyAdmin in your browser (http://localhost/phpmyadmin).
    - Create the portfolio, project1, and notification1 databases.
## Step 4: Set Up MongoDB
    Start your MongoDB server (usually on localhost:27017 by default). If MongoDB is installed via a package manager, you may start it using: mongod

## Step 5: Running the Microservices
1. Discovery Server (Eureka Server)
    - Run Discovery Server
    microservice\discovery-server\src\main\java\com\example\discovery_server>Discovery.java
2. API Gateway
    - Run API Gateway
    microservice\api-gateway\src\main\java\com\baconbao\api_gateway>ApiGatewayApplication.java
3. Running the Individual Services
For each service (e.g., user-service, manager-service, profile-service, project-service, etc,..):
## Conclusion
Once all services are up and running, the application should be fully operational. You can access the Angular frontend at http://localhost:4200/ and interact with the microservices through the API Gateway.

## Usage

### Frontend (Angular)

1. **Running the Angular Application**
   - Ensure you have started the backend services (Discovery Server, API Gateway, and other microservices) as described in the [Installation](#installation) section.
   - Navigate to the Angular project directory:
     ```bash
     cd microservice/client
     ```
   - Start the Angular development server:
     ```bash
     ng serve
     ```
   - Open your browser and go to [http://localhost:4200/](http://localhost:4200/) to access the frontend application.

2. **Features**
   - **User Role**: Users can create profiles, add projects, and describe their skills. They can view company information, apply for jobs, and manage their profiles.
   - **HR Role**: HR users can review applications, approve or reject candidates, and create job postings for specific companies.
   - **Manager Role**: Managers can create and manage job postings for their company, update company details, and oversee HR activities.
   - **Admin Role**: Admins manage overall system configuration and monitor all activities within the application.

### Backend (Microservices)

1. **Interacting with the Services**
   - The API Gateway handles routing requests to the appropriate microservice. You can interact with the services via RESTful endpoints exposed by the API Gateway.
   - For detailed API endpoints and usage, refer to the individual microservices' documentation.

2. **Service Endpoints**
   - **Discovery Server**: Manages service registration and discovery. Accessible at [http://localhost:8761/](http://localhost:8761/).
   - **API Gateway**: Routes requests to the correct microservices. Accessible at [http://localhost:8080/](http://localhost:8080/).
   - **Microservices**: Each microservice exposes its own set of API endpoints. You can interact with these through the API Gateway.

3. **Testing**
   - To test the application, you can use tools like Postman to send requests to the API Gateway and verify responses from different services.


4. **Configuration**
   - Configuration files are located in the `src/main/resources` directory of each microservice. Update these files to change service settings, such as database connections and service-specific configurations.

## Contributing

We welcome contributions to this project! If you would like to contribute, please follow these guidelines:

1. **Fork the Repository**
   - Fork the repository on GitHub to create your own copy of the project.

2. **Create a Branch**
   - Create a new branch for your feature or bug fix:
     ```bash
     git checkout -b feature/your-feature
     ```

3. **Make Changes**
   - Make your changes to the codebase. Be sure to include tests for new features or fixes.

4. **Commit Your Changes**
   - Commit your changes with a descriptive message:
     ```bash
     git commit -m "Add feature X or fix issue Y"
     ```

5. **Push Your Changes**
   - Push your changes to your forked repository:
     ```bash
     git push origin feature/your-feature
     ```

6. **Submit a Pull Request**
   - Open a pull request on the original repository. Describe the changes you have made and why they should be merged.

7. **Code Review**
   - Your pull request will be reviewed by the project maintainers. Address any feedback or requested changes.

8. **Merge**
   - Once approved, your changes will be merged into the main branch of the project.

### Issues

- **Reporting Issues**: If you encounter any issues with the project, please open an issue on GitHub. Provide detailed information about the problem and steps to reproduce it.
- **Feature Requests**: If you have ideas for new features, feel free to submit a feature request.

Thank you for contributing to our project!

## Contact

If you have any questions, feedback, or inquiries regarding the project, please reach out to the respective project maintainers based on their area of responsibility:

### Project Maintainers

- **Lai Tran Trung Kien** (Frontend Lead)
  - Role: Responsible for Angular frontend development and UI/UX design.
  - Email: [Kien06112004@gmail.com](Kien06112004@gmail.com)

- **Vu Hoang Chung**
  - Role: Responsible for backend development including Spring Security, JWT, OpenFeign, email integration, Cloudinary configuration and handling service.
  - Email: [Vuhoangchung12122004@gmail.com](Vuhoangchung12122004@gmail.com)

- **Nguyen Quoc Khanh** (Testing and Database Management Lead)
  - Role: Responsible for testing, designing database schemas, and handling service queries for the database.
  - Email: [kn26066@gmail.com](kb26066@gmail.com)

### Team Communication

- **Discord**: Connect with us on Discord for community support. [Join here](https://discord.gg/MC7cMwVe)


## Conclusion

Thank you for exploring our project. We hope this documentation has provided you with a clear understanding of our application and its components. Our team has put significant effort into creating a robust and scalable system using Angular for the frontend and a microservices architecture for the backend, supported by MySQL and MongoDB databases.

If you have any questions, suggestions, or contributions, please don't hesitate to reach out to us through the contact details provided. Your feedback is invaluable in helping us improve and enhance the project.

We appreciate your interest and support. Happy exploring!

Best regards,  
The [BaConBao] Team