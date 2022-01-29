# Prerequisite
* Angular 2+
* Docker

# Build the Project
```
cd p2-frontend
npm install
ng build
```

# Dockerize
* Please make sure that the project is correctly built
* Please run the following command in the command line, in order to dockerize the application:
```
cd p2-frontend
docker build -t [IMAGE_NAME]:[TAG_NAME] .
```
