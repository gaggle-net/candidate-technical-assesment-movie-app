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
FEATURE-1138
---------

Product would like an endpoint that shows all the other people a given person has worked with over their career.




