# Take Home Challenge: Movie Info API

## Overview
This exercise is meant to showcase your technical implementation & design abilities to adapt an existing system to new requirements.
It is expected that you will fork this repository in GitHub and share the link with us when complete.

For the purposes of the exercise, pretend you're on a team working on a new website that customers can use to look up information about movies.
Other team members are handling the front end, but you're working on the back-end API.

As such, this repo contains a basic Spring Boot application that serves a REST API, backed by an in-memory H2 database.
It should fire up and run without errors.  This should run on any Java 8 or higher JVM.

`./gradlew bootRun` will fire up the app, but feel free to use any IDE or other tooling of your choice

Below are the tickets for a pair of bugs and a feature request.  Implement at least one of these, and open a PR with the changes.

## Tickets

---------
BUG-1701
---------

Report from a customer: 
"None of Mark Hamill's movies after 1980 show up when I request his Resume."

QA confirmed by checking `GET /crew/person/1`


---------
BUG-42
---------

Help!  Our Movie App is super popular, it's been running great, but after about an hour under load it starts giving slow responses and then stops serving altogether.

The database load is nill

A restart seems to fix it for the next hour.  Right now we have a cron job restarting it every half hour.

We'd love to improve the uptime, what's cuasing this?


---------
FEATURE-1138
---------

Product would like an endpoint that shows all the other people a given person has worked with over their career.


### Implementation

The endpoint is `/crew/collaborations/:id` where `:id` is the unique identifier for a Person. Here are a couple of sample runs:

```
potlia@agni:/tmp $ curl -s localhost:8080/crew/collaborations/1 | jq
{
  "person": {
    "id": 1,
    "name": "Mark Hamill"
  },
  "collaborators": [
    {
      "id": 3,
      "name": "Harrison Ford"
    },
    {
      "id": 4,
      "name": "Carrie Fisher"
    }
  ]
}
potlia@agni:/tmp $ curl -s localhost:8081/crew/collaborations/5 | jq
{
  "person": {
    "id": 5,
    "name": "John Belushi"
  },
  "collaborators": [
    {
      "id": 4,
      "name": "Carrie Fisher"
    }
  ]
}
potlia@agni:/tmp $
```

### Unit Test

The pertinent unit test is `findCollaboratorsForPersonId5`. Here is sample run:

```
potlia@agni:/tmp/candidate-technical-assesment-movie-app  (feature
/feature-1138)$ ./gradlew  test --tests net.gaggle.challenge.ChallengeApplicationTests.findCollaboratorsForPersonId5
Starting a Gradle Daemon, 1 busy and 1 stopped Daemons could not be reused, use --status for details

> Task :test
2022-08-18 12:01:52.523  INFO 10522 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown initiated...
2022-08-18 12:01:52.524  INFO 10522 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown completed.

BUILD SUCCESSFUL in 8s
5 actionable tasks: 3 executed, 2 up-to-date
potlia@agni:/tmp/candidate-technical-assesment-movie-app  (feature/feature-1138)$
```


