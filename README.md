# Streamer Event Viewer (SEV)

This application is a Spring Boot application. It also depends on other frameworks including:
<ul><li>Spring Security and Spring OAuth client framework to integrate with Twitch as its Identity Provider. 
Users can login to this application using their twitch credential. 
<li>Spring JPA to store data in Postgres DB.
<li>Liquibase for database schema management. 
<li>Spring Cloud OpenFeign framework for interactions with Twitch APIs. 
<li>Thymeleaf and Spring Web for UI.
</ul>

The source code is a fork from the Heroku's [Java Getting Started sample app](https://github.com/heroku/java-getting-started.git).

[![Deploy to Heroku](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy)

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

## Documentation

For more information about using Java on Heroku, see these Dev Center articles:

- [Java on Heroku](https://devcenter.heroku.com/categories/java)
