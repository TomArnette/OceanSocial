# Ocean Social Media App
Revature social network application to connect and interact with friends

### Members:
* Trevor Drury
* Angel Walker
* Bhavani Yelagala
* Cameron Trumbo
* Gabriel Gil
* Garrett St. Amand
* Jack Gildea


### Resource Links:
* http://3.139.90.29:8080/

#### Repositories
 * Front End - https://github.com/Ocean-Social-Media-App/Frontend.git
 * User Microservice - https://github.com/Ocean-Social-Media-App/UserMicroService.git
 * Feed Microservice - https://github.com/Ocean-Social-Media-App/Feed-Service.git
 * Eureka - https://github.com/Ocean-Social-Media-App/Eureka.git
 * Gateway - https://github.com/Ocean-Social-Media-App/gateway.git
 * JWT - https://github.com/Ocean-Social-Media-App/JWT.git


## Project Parameters

### Users Can :

* Register
  * Receive welcome email.
* Login/Logout.
* Reset their password.
  * Receive email with new password
* Modify their information.
* Upload a profile picture (using AWS: S3).
* Search other people.
* Create a post.
  *  Image(s) can be added to these posts (using AWS: S3).
  *  Youtube links can be added to these posts
* View his own profile (including posts)
* View others’ profile (including posts)
* See their main feed.
  *  Posts of everyone should show here.
  *  Pagination should be implemented (only fetching 20 posts at a
     time).
* Like/unlike someone’s post.
* Bookmark/unbookmark favorite posts
* Follow other peoples accounts
* Reveive notifications when they are followed by another user (using RabbitMQ)


### Roles:

* Trevor Drury - Scrum Master


### Programs/Libraries used:

**BackEnd :**

1) Spring MVC
2) Hibernate
3) Java
4) Postgres
5) AWS EC2
6) AWS S3
7) Junit/Mockito/H2
8) Log4J
9) Javadoc
10) Microservice

**FrontEnd :**

1) Angular
2) TypeScript
3) JavaScript
4) HTML
5) CSS
6) Jasmine

**Deployment :**

1) AWS EC2
2) Docker
3) Jenkins


## Backend Requirements

**Tests :**

* Junit
* Mockito
* H2 (creates and drops after test)

**Hibernate :**

* Models
* PostgreSQL


**Register, Models and database**

* Sessions 
* Register- email password
* Forgot password will email them their temp password
* Modify info

- Dummy email
- Google interface for handling email
- AWS S3 upload a picture- handle saving URL to database



### Backend endpoints

|         Action          |           Endpoint                  |
| :---------------------: | :---------------------------:       |
|        **Login**        |    `POST /api/user/login`           |
|       **Logout**        |    POST /api/user/loogout`          |
|       **Session**       | `GET /api/user/check-session`       |
|       **Sign Up**       |     `POST /api/user/user`           |
|       **Forgot**        |`POST /api/user/forgot/{username}`   |
|   **Update Profile**    |  `PUT /api/user/updateUser`         |
|    **Get Following**    |   `GET /api/follow/{userId}`        |
|    **Follow Someone**   |   `POST /api/follow/{userId}`       |
|   **Unfollow Someone**  | `DELETE /api/follow/{userId}`       |
|     **Create Post**     |    `POST /api/feed/post`            |
|     **Delete Post**     |`DELETE /api/feed/post/{postId}`     |
|**Get Post by Favorites**|`GET /api/feed/post/comment/{postId}`|
|   **Posts By PostId**   | `GET /api/feed/post/{postId}`       |
|   **Posts By UserId**   |`GET /api/feed/post/userId/{userId}` |
|     **Like a Post**     |     `POST /api/feed/like`           |
| **Get Likes by PostId** | `GET /api/feed/like/{postId}`       |
|     **Delete Like**     |`DELETE /api/feed/like/{likeId}`     |


### Models 

**User Model**

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


**Post Model**

    Integer postId: serial
    Integer postParentId
    String postPicUrl
    String postText not null
    Date postTime
    String postYouUrl
    Integer userId not null

**Response Model**

   Boolean success
   String message
   Object data
   
**User Response Model**

    Integer id
    String name
    String profileImage
    Long lastNotification
    
 **Notification Model**

    Integer id: serial
    String type
    Long timestamp
    User user (@ManyToOne(cascade = CascadeType.ALL))
    Response response (@Transient)
    UserResponse userResponse (@Transient)

  	
**Like Composite Model**
    
    Integer likeId
    Integer userId not null
    Post post not null

**RabbitMessage Model**

    Integer userIdFrom
    Integer postId
    Integer userIdTo

		

## Frontend Requirements

### Angular (component = comp)

**Views to Display : **

- **Login**
  - Nav Bar comp
  - Login Form comp
  - Forgot Password comp
  - Sign up - directs them to CreateProfile page

- **Profile/Create Profile/update**
  - Nav Bar comp
  - Update Info	
  - Add picture
- **Feed**
  - Nav Bar comp
  - Filter by person
  - (Reuse Create Post Comp to display post?)
  - main Feed (Shows friends post)
    - Only show 20 post and load more as needed
- **Create Post**
  - Add image
  - Add youtube url
  - Like post
  - Comment
    - users can reply to comments

- **Follow User/ Following Information**
  - Follow button on explore page and user page
  - Can view users following info on profile page

- **Bookmarks Page**
  - Users can bookmark posts
  - Users can view their bookmarked posts on Bookmarks page

- **Notifications**
  - Users can view their notification
  - These are loaded any time the toggle buttons are hit


