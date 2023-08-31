
CARD RESTFUL WEB SERVICE PROJECT:

Welcome! This project is designed to manage users and their card items. 
The application is built using Java, Spring Boot, and uses MYSQL database for local testing and development.

Prerequisites:
Before running the project, make sure you have the following installed on your machine:

Java 8 or later
Maven

Build and Run Instructions:

1.Clone the repository to your local machine
2.Build the project using Maven
3.Run the application

The application will start with preloaded data of users with their email and password 
configured inside the commandLineRunner interface, in the CardsApplicationProjectApplication class
you should see log messages indicating that the application is up and running.

Database Configuration:

The application uses Mysql for local testing and development. 
The database schema is automatically created based on the entity classes in the project.

Use the following to log in:

#JDBC URL TO CONNECT TO THE DATABASE AND THE USERNAME AND PASSWORD TO CONNECT TO IT
spring.datasource.url=jdbc:mysql://localhost:3306/cards_application
spring.datasource.username=root
spring.datasource.password=(leave empty)

API Documentation:

The project uses OpenApiDocumentation and you can use the swagger ui to test out the api on http://localhost:8080/swagger-ui/index.html




