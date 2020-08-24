# Streamer Event Viewer (SEV)

This application is a Spring Boot application. It also depends on other frameworks including:
- Spring Security and Spring OAuth client framework to integrate with Twitch as its Identity Provider. 
Users can login to this application using their twitch credential. 
- Spring JPA to store data in Postgres DB.
- Liquibase for database schema management. 
- Spring Cloud OpenFeign framework for interactions with Twitch APIs. 
- Thymeleaf and Spring Web for UI.

The source code is a fork from the Heroku's [Java Getting Started sample app](https://github.com/heroku/java-getting-started.git).

[![Deploy to Heroku](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy)

## Notes 

There are some features that still do work as expected:

- No landing page. The Home page is to show the video player and allow user to create a clip for 
his favorite streamers. User will be automatically redirected to Twitch's login page 
if he is not logged in.
- No cron job to update view counts because Twitch API to read clips now require a valid
credential. Currently, user can kick off the update job by clicking on `Update View Count`
on the navigation bar.
- Session's TTL is still set to the default value.

## Running Locally

Make sure you have Java and Maven installed.  Also, install the [Heroku CLI](https://cli.heroku.com/).

```sh
$ git clone https://github.com/boonma04/SEV.git
$ cd SEV
$ mvn install
$ heroku local:start
```

Your app should now be running on [localhost:5000](http://localhost:5000/).

If you're going to use a database, ensure you have a local `.env` file that reads something like this:

```
JDBC_DATABASE_URL=jdbc:postgresql://localhost:5432/java_database_name
```

## Deploying to Heroku

```sh
$ heroku create
$ git push heroku master
$ heroku open
```

