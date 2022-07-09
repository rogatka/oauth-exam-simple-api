## Exam API
### Summary
Simple Java application to manage students, tutors, subjects, exam results.\
Application uses the following stack: Java 11, Maven, Spring Boot, PostgreSQL, Liquibase, OAuth2.0, Mapstruct.\

### How to run
Before running the application locally:
- Generate the OAuth2 client ID and client secret (https://developers.google.com/adwords/api/docs/guides/authentication)
and put them into application-local.yml or set the ${OAUTH_GOOGLE_CLIENT_ID} and ${OAUTH_GOOGLE_CLIENT_SECRET} environment
variables
- Run the docker-compose.yml to initialize PostgreSQL database