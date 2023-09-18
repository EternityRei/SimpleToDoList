# Todo List Application

This is a simple Todo List application built using Spring Boot, Hibernate, and PostgreSQL. It allows users to manage their todo items by creating, reading, updating, and deleting tasks. Each task has a title and a description.

## Features

- Create new todo items.
- Retrieve a list of all todo items.
- Retrieve a specific todo item by ID.
- Update existing todo items.
- Delete todo items.

## Prerequisites

Before running the application, make sure you have the following prerequisites installed on your system:

- Java 8 or higher
- Apache Maven
- PostgreSQL database server

## Setup

1. Clone the repository: 
    `git clone https://github.com/your-username/todo-list-app.git`

2. Navigate to the project directory: `cd todo-list-app`

3. Update the database configuration in `src/main/resources/application.properties` with your PostgreSQL database settings:

   - `spring.datasource.url=jdbc:postgresql://localhost:5432/todo_db`
   - `spring.datasource.username=your_username`
   - `spring.datasource.password=your_password`

4. Build the project: `mvn clean package`

## Running the Application

1. Start the PostgreSQL database server.

2. Run the application using Maven: `mvn spring-boot:run`

   The application should now be accessible at `http://localhost:8080`.

## API Endpoints

- GET `/api/todos`: Get a list of all todo items.
- GET `/api/todos/{id}`: Get a specific todo item by ID.
- POST `/api/todos`: Create a new todo item.
- PUT `/api/todos/{id}`: Update an existing todo item by ID.
- DELETE `/api/todos/{id}`: Delete a todo item by ID.

## Usage

You can use a tool like Postman to interact with the API endpoints and manage your todo items.

###  Feedback

- Was it easy to complete the task using AI? 

That was much more easier to compelete the task using AI, because it can generate for you some drafts, templates you can use and do some routine work instead of you.

- How long did task take you to complete?

Almost 4 hours

- Was the code ready to run after generation? What did you have to change to make it usable?

To tell the truth, sometimes generated code was not perfect (some bad practices were proposed) and caused some problems with running. Actually, the code usually needed a structure changing, but sometimes I had to used my acknowledge to correct ChatGPT's mistakes.

- Which challenges did you face during completion of the task?

The main challenges were connected with analyzing Chat's answers and trying to find a better solution to complete the task

-  Which specific prompts you learned as a good practice to complete the task?

1. Framework configuration
2. Unit testing
