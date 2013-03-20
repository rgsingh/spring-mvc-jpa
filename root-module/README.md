spring-mvc-jpa
==============

Simple Spring MVC example with JPA (Adapted from Spring Data book by Petri Kainulainen).
simple-app can be split out into separate Maven modules so that separate
layers can be build independently (e.g. simple-config, simple-controller, 
simple-service might be 3 separate modules with common-utils being a fourth) or built within a single module (recommended for non-shared modules). Shared modules across multiple web apps would be good candidates to be separated out into independent modules.

Structure:

root-module (kicks off all other modules as defined below)
    ---common-utils (might be a shared utilities module used by all apps)
    |
    ---simple-app (the main app with Spring MVC, Spring Data, JPA, etc)
    |
    ---simple-app-web (packages all dependent modules in this case only one: simple-app)
    |
    ---<another-arbitrary-app> (Another application with similar structure to simple-app)
    |
    ---<another-arbitrary-app-web> (The accompanying web portion of another-arbitrary-app)

To build a project including a war:
cd root-module 
mvn clean install

To run the web app for simple-app:
cd simple-app-web (must be within a *-web folder to run within jetty)
mvn clean jetty:run -Djetty.port=<port number>




