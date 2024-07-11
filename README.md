# Planner 📅

RestFull monolith API using Spring Boot. This API handles the logic of a web and mobile based application called `Planner`.
The Planner application is used to create travel plans. It stores trips information such as important links, activities to be realized and participants.

This project was built during the Rocketseat NLW Journey, a 3-day event to learn a new stack.

### Status 🏗️

- Base version **finished**!
  - Under improvement 🚀

### Tech Stack

- Java 17
- Maven
- Spring Boot
- Lombok
- Fly Way
- Postgres SQL
- Hibernate JPA
- Spring Boot DevTools
- Docker

### How to run

Install the git CLI into your machine and clone this repository by using HTTPS or SSH:

```bash
# HTTPS
git clone https://github.com/vickttor/nlw_journey_planner_java.git

# SSH
git clone git@github.com:vickttor/nlw_journey_planner_java.git 
```

Install the **[Docker](https://www.docker.com/products/docker-desktop/)** and run the following command:

```bash
docker compose up -d
```

This command will execute the steps of the `compose.yaml` file execute both API and PostgreSQL database. To stop the execution, run:

```bash
docker compose down
```

### Improvements

- [X] Add validation to datetime properties 
- [X] Extract the core logic of the Trips controller and add into a Service Class.
- [X] Implement Exception handlers do avoid the return of `500` server errors
- [X] Implement Postgres as the main database by using docker and docker compose
- [X] Create a `compose.yaml` file to execute the API and create the database with only one command.
  ```bash
  docker compose up -d
  ```
- [ ] implement sending emails to trip participants


### Author

<img  style="border-radius: 50%;"  src="https://avatars.githubusercontent.com/u/70340221?v=4"  width="100px;"  alt="Victor"/>

<sub><b>Victor H. Silva</b></sub>🚀

---