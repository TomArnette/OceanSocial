# Prerequisite
* Java 8+
* Docker
* Maven

# Dockerize
Please run the following command in the command line, in order to dockerize the application:
```
mvn -DskipTests clean package
docker build -t [IMAGE_NAME]:[TAG_NAME] .
```
