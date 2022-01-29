# User Microservice

## To Do
* Session Management (using JWT)
* Notifications (using RabbitMQ)

# Ocean Social Media App
Revature social network application to connect and interact with friends

## Members:
* David Burton
* Kevin Dian
* Milo Cao
* Roel Crodua
* Tommy Arnette

## Resource Links:

    (deployed link to website)

## Repositories
   * Front End - https://github.com/Ocean-Social-Media-App/Frontend.git
   * User Microservice - https://github.com/Ocean-Social-Media-App/UserMicroService.git
   * Feed Microservice - https://github.com/Ocean-Social-Media-App/Feed-Service.git
   * Eureka - https://github.com/Ocean-Social-Media-App/Eureka.git
   * Gateway - https://github.com/Ocean-Social-Media-App/gateway.git
   * JWT - https://github.com/Ocean-Social-Media-App/JWT.git


## Project Parameters
### Users Can:
* Register
    * Receive welcome email
* Login/Logout.
* Reset their password.
    * Receive email with new password
* Modify their profile information
* Upload a profile picture (using AWS: S3)
* Search for other ppeople
* Create a post
    * Image(s) can be added to these posts (using AWS: S3)
* Comment on posts
    * Reply to comments
* View their own profile (including posts)
* View others’ profile (including their posts)
* See their main feed
    * Posts by people the user follows appear here
    * Pagination should be implemented (only fetching 20 posts at a time).
* Like/unlike someone’s post.
* Bookmark favorite posts
* Follow other people's accounts
* Receive notifications when they are followed by another user (using RabbitMQ)

## Roles:
Fill In Member Roles

## Programs/Libraries used:

### BackEnd:
* Spring Boot
* Hibernate
* Java
* Postgres
* AWS EC2
* AWS S3
* Junit/Mockito
* Log4J
* Javadocs
* Microservice Architecture

### FrontEnd:
* Angular
* TypeScript
* JavaScript
* Jasmine

### Deployment:
* AWS EC2
* Docker
* Jenkins

## Backend Requirements
Tests:
* JUnit
* Mockito
* Test Driven Development

### Backend endpoints (Non-finishedl list)

|   Action        |             Endpoint                |
|   ------        |             --------                |
|  Login 	      |   `POST /api/user/login`            |
|  Logout 	      |   `GET /api//user/logout`           |
|  Session        |   `GET /api/user/check-session`     |
|  Register       |   `POST /api/user/user`             |
|  Forgot 	      |   `GET /api/user/forgot/{username}` |
|  Update Profile |   `PUT /api/user/updateUser`        |
|  Get Following |    `GET /api/follow/{userId}`        |
|  Follow Someone |   `POST /api/follow/{userId}`        |
|  Unfollow Someone | `DELETE /api/follow/{userId}`        |

## Models (Non-finished list)
### User Model
```
    Integer Id: serial
    String firstname not null
    String lastname not null
    String username Unique not null
    String password not null
    String email Unique not null
    String aboutMe char(250)
    Date bday int
    String proPicUrl
    Long lastNotification
    Set<Integer> bookmarks
    Set<Integer> user_following
    Set<Integer> followers
```
### Response Model
```
   Boolean success
   String message
   Object data
```
### User Response Model
```
    Integer id
    String name
    String profileImage
    Long lastNotification
```
### Notification Model
```
    Integer id: serial
    String type
    Long timestamp
    User user (@ManyToOne(cascade = CascadeType.ALL))
    Response response (@Transient)
    UserResponse userResponse (@Transient)
```
