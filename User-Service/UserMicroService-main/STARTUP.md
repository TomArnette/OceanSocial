# Prerequisite
* Java 8+
* Docker
* Maven

# Env Variable
* DBPASSWORD=[DATABASE-PASSWORD]
* TEAMWATER_ACCESSKEY=[AWS-S3-ACCESS-KEY]
* TEAMWATER_SECRETKEY=[AWS-S3-SECRET-KEY]
* OCEAN_PASSWORD=[EMAIL-PASSWORD]

# Dockerize
Please run the following command in the command line, in order to dockerize the application:
```
mvn -DskipTests clean package
docker build -t [IMAGE_NAME]:[TAG_NAME] .
```
