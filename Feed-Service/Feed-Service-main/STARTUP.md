# Prerequisite
* Java 8+
* Docker
* Maven

# Env Variable
* EMAIL_OCEANUN=[GMAIL-USERNAME]
* EMAIL_PASS=[GMAIL-PASSWORD]
* POSTGRES_PASSWORD=[DATABASE-PASSWORD]
* POSTGRES_URI=[DATABASE-URI]
* POSTGRES_USERNAME=[DATABASE-USERNAME]
* TEAMWATER_ACCESSKEY=[AWS-S3-ACCESS-KEY]
* TEAMWATER_SECRETKEY=[AWS-S3-SECRET-KEY]

# Dockerize
Please run the following command in the command line, in order to dockerize the application:
```
cd feed
mvn -DskipTests clean package
docker build -t [IMAGE_NAME]:[TAG_NAME] .
```
