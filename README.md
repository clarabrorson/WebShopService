# WebShopService

## Description

This project works as a backend server for a web-shop. It's a Spring Boot application that uses Spring Security to authenticate users and OAuth2 to authorize them.
The application is connected to a MySQL database and uses Spring Data JPA to access the data.

The application will automatically create an admin account if it does not already exist. The username is *admin* and the password is *password*.

**JavaDoc** can be found here: *WebShopServie/index.HTML*

## Functionality

**All users can:**
- Browse through articles.

**Logged-in users can:** 
- Browse through articles, add them to their shopping cart and place orders.

**Admins users can:** 
- Do everything a regular user can do.
- Create, read, update and delete articles.
- See all current shopping carts.
- See all historic shopping carts.
- See all users.

## Installation

Before you can start this project, you must follow these steps:

- Download and install IntelliJ IDEA or your preferred IDE.
- Download and install MySQL and MySQL Workbench.
- Clone this repository to your local machine.

## Usage

### 1. Set up the MySQL database:
- Open MySQL Workbench.
- Connect to your MySQL server.
- Create a new schema for the application. You can do this by clicking on the "Create a new schema in the connected server" button, entering a name for the schema, and clicking "Apply".
- Ensure that the *schema name*, *username*, and *password* in the application.properties file match your MySQL setup.

### 2. Start the WebShopService application:
- Open your IDE and run the WebShopServiceApplication class to start the application.

### 3. Start the client application:
- Open your IDE and run the Main class to start the application.
- Follow the instructions in the console to use the web-shop.


## Dependencies:

- Spring Web
- Lombok
- Spring Data JPA
- MySQL Driver
- Spring Security
- OAuth2 Client
- Spring Boot DevTools


## Credits

This project is a collaboration between:
- **[Clara Brorson](https://github.com/clarabrorson)**
- **[Jafar Hussein](https://github.com/Jafar-Hussein)**
- **[Fredrik Rinstad](https://github.com/fringston)**


## License

**[MIT License](https://choosealicense.com/licenses/mit/)**

---