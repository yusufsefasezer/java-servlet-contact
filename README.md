# Java Servlet Contact

A simple contact list application developed with Jakarta EE Servlet.

This project developed with Jakarta EE Servlet, Maven, Repository(MySQL-SQLite-Object).

The "Java Servlet Contact" project utilizes Jakarta EE Servlet, Maven and the Repository pattern (supporting MySQL, SQLite, and Object repositories). The project is structured to manage contacts, leveraging the power of Servlet technology.

## Technologies Used:

- **Jakarta EE Servlet:** The project employs Jakarta EE Servlet to handle HTTP requests and responses, facilitating the development of robust web applications.
- **Maven:** Maven is utilized for project management and dependency resolution, ensuring a streamlined and organized development process.
- **Repository Pattern:** The application implements the Repository pattern, supporting various repositories such as MySQL, SQLite, and Object repositories for efficient data management.

## Project Initialization:

At the project's inception, a ServletContextListener is employed. This listener dynamically adds values as context parameters, subsequently creating repositories based on these parameters. The repositories are then added as ServletContext Attributes, enabling seamless integration and accessibility throughout the application.

## Controller:

The primary controller in this project is implemented using Servlet technology. The Servlet serves as the central point for handling requests, interacting with the repositories, and orchestrating the business logic associated with managing contacts.

## Servlet Features:

- **Version 6.0:** The project is developed using Servlet version 6.0, incorporating the latest features and enhancements.
- **Annotations:** Modern Servlet annotations such as @WebServlet and @WebListener are extensively used. These annotations simplify the configuration and deployment of Servlets and listeners.

This "Java Servlet Contact" project provides a solid foundation for developing a contact management system. The combination of Jakarta EE Servlet, Maven, and the Repository pattern ensures a scalable and maintainable solution for handling and organizing contact information.

## [Download](https://github.com/yusufsefasezer/java-servlet-contact/archive/master.zip)

## How to run

### Maven

Maven must be installed to compile this application.

You can use the following commands to generate a WAR file.

```
mvn package
```

Onc compiled, You can install the WAR in your favorite Servlet Container.

## Docker

**Docker must be installed.**

Build the Docker image with the tag "java-servlet-contact"

```
docker build -t java-servlet-contact .
```

```
docker run -p 80:8080 java-servlet-contact
```

You can access the application using `localhost:80` in your web browser.

# License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details

Created by [Yusuf Sezer](https://www.yusufsezer.com)
