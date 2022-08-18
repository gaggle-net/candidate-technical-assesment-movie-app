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

### Resolution
The movie id was read off as an `int` whereas the schema (and the data not returned by the REST endpoint) had values bigger than max int 2147483647. The fix is to read off the movie id as `long`.

### Unit Test
The pertinent unit test name is `findAPersonByID_Bug1701` (passes as below):

```
potlia@agni:/tmp/candidate-technical-assesment-movie-app  (bugfix/bug-1701)$ ./gradlew  test --tests net.gaggle.challenge.ChallengeApplicationTests.findAPersonByID_Bug1701

> Task :test
2022-08-18 12:09:17.629  INFO 11270 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown initiated...
2022-08-18 12:09:17.631  INFO 11270 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown completed.

BUILD SUCCESSFUL in 5s
5 actionable tasks: 4 executed, 1 up-to-date
potlia@agni:/tmp/candidate-technical-assesment-movie-app  (bugfix/bug-1701)$
```

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




