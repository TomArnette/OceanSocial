# Feed Microservice

# Ocean Social Media App
Revature social network application to connect and interact with friends

## Members:
* Andrew Patrick
* Ezequiel Flores
* Joan Gorsky
* Shane Danner
* Thanh Nguyen

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

|   Action                |             Endpoint                   |
|   ------                |             --------                   |
| Create Post             |  `POST /api/feed/post`                 |
| Get Post By Id          |  `GET /api/feed/post/{postId}`         |
| Get Post By User        |  `GET /api/feed/post/userId/{userId}`  |
| Update Post             |  `Put /api/feed/post`                  |
| Delete Post             |  `DELETE /api/feed/post/{postId}`      |
| Get Post by Favorites   |  `Get /api//feed/post/fave`            |
| Get Comments of Post    |  `Get /api//feed/post/comment/{postid}`|
| Like a Post             |  `POST /api/feed/like`                 |
| Get Likes by PostId     |  `GET /api/feed/like/{postId}`         |
| Delete Like             |  `DELETE /api/feed/like/{likeId}`      |

## Models
### Post Model
```
    Integer postId: serial
    Integer postParentId
    String postPicUrl
    String postText not null
    Date postTime
    String postYouUrl
    Integer userId not null
```
### Response Model
```
   Boolean success
   String message
   Object data
```
### Like Model
```
    Integer likeId
    Integer userId not null
    Post post not null
```
### RabbitMessage Model
```
    Integer userIdFrom
    Integer postId
    Integer userIdTo
```
