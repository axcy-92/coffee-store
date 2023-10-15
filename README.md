# Coffee Store App

The Coffee Store App is a backend API that allows you to manage orders, drinks, and toppings for a coffee store.
This is a part of BESTSELLER Technical Assignment.

## Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [API Documentation](#api-documentation)
- [Dockerization](#dockerization)

## Features

- Manage drinks, toppings, and orders
- Calculate discounts based on cart contents
- User authentication and authorization
- API endpoints for CRUD operations

## Technologies

- Java
- Spring Boot
- H2 Database
- Hibernate
- Spring Security
- Docker
- OpenAPI (Swagger)
- JWT (JSON Web Tokens)

## Getting Started

### Prerequisites

Before you begin, ensure you have met the following requirements:

- Java 17
- Maven
- Docker (optional)

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/coffee-store.git
   ```
2. Change into the project directory:
    ```bash
    cd coffee-store
    ```
3. Build the project:
    ```bash
   mvn clean install
   ```
4. Run the application:
    ```bash
    java -jar target/coffee-store-0.0.1-SNAPSHOT.jar
    ```
    The application should now be running at http://localhost:8080/coffee-store.

## Configuration
You can configure the application by modifying the `application.yaml` file. 
Update the database settings, logging levels, and more as needed.

## Usage
Once the application is up and running, you can interact with it using the provided API endpoints.

You can interact with API by using [Swagger](http://localhost:8080/coffee-store/swagger-ui/index.html)
or [Postman Collection](Bestseller%20Coffee%20Store.postman_collection.json).

You can explore H2 Database at http://localhost:8080/coffee-store/h2-ui.
Use this for login to H2 Console:
- Driver Class: `org.h2.Driver`
- JDBC URL: `jdbc:h2:file:./database/coffee-store`
- User Name: `admin`
- Password: `bestseller`

## API Documentation
The API is documented using Swagger.
You can access the API documentation by visiting http://localhost:8080/coffee-store/swagger-ui/index.html after starting the application.

## Dockerization
You can dockerize the application for easier deployment. To build the Docker image, use the following command:
```bash
docker build -t coffee-store:latest .
```

To run the Docker container, use:
```bash
docker run --name coffee-store-app -p 8080:8080 coffee-store:latest
```