package com.revature.ocean.controllers;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.revature.ocean.models.Response;
import com.revature.ocean.models.User;
import com.revature.ocean.services.EmailService;
import com.revature.ocean.services.UserService;
import com.revature.ocean.utility.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Path;
import java.util.List;
import java.util.Set;
import java.util.Map;

/**
 * UserController controls the API endpoints associated with User methods.
 */
@RestController("userController")
public class UserController {

    private UserService userService;
    private EmailService emailService;
    private JwtUtility jwtUtility;

    @Autowired
    public UserController(UserService userService, EmailService emailService, JwtUtility jwtUtility) {
        this.userService = userService;
        this.emailService = emailService;
        this.jwtUtility = jwtUtility;
    }

    //SET FOR REMOVAL
    //Creates a Session for the user logged in
    @GetMapping("check-session")
    public Response checkSession(HttpSession session) {
        Response response;
        User user = (User) session.getAttribute("loggedInUser");
        if (user != null) {
            user.setPassword(null);
            response = new Response(true, "Session found.", user);
        } else {
            response = new Response(false, "No session found.", null);
        }
        return response;
    }

    //Checks to see if user is in database other wise it'll reject their log in
    @PostMapping("login")
    public Response login(@RequestBody User user) {
        Response response;

        User tempUser = this.userService.login(user);
        if (tempUser != null) {
            //session.setAttribute("loggedInUser", tempUser);
            response = new Response(true, jwtUtility.genToken(tempUser.getUserId()),tempUser);
        } else {
            response = new Response(false, "Invalid username or password. (Remember, these are case sensitive!)", null);
        }
        return response;
    }

    //SET FOR REMOVAL
    //Logs the user out and ends the session
    @GetMapping("logout")
    public Response logout(HttpSession session) {
        session.setAttribute("loggedInUser", null);
        return new Response(true, "You have logged out and session terminated", null);
    }

    //Creates a new user
    @PostMapping("user")
    public Response createUser(@RequestBody User user) {
        Response response;
        User tempUser = this.userService.createUser(user);
        if (tempUser != null) {
            this.emailService.welcomeEmail(tempUser.getEmail(), tempUser.getFirstName());
            user.setPassword(null);
            response = new Response(true, jwtUtility.genToken(tempUser.getUserId()), tempUser);
        } else {
            response = new Response(false, "This User already exists.", null);
        }
        return response;
    }

    //Get all users
    @GetMapping("user")
    public Response getAllUsers() {
        Response response;
        List<User> allUsers = this.userService.getAllUsers();
        if (allUsers != null) {
            response = new Response(true, "Users obtained.", allUsers);
        } else {
            response = new Response(false, "Failed to find users.", null);
        }
        return response;
    }

    //Checks username in database and will send them email with password
    @GetMapping("forgot/{username}")
    public Response forGotInfo(@PathVariable String username) {
        Response response;
        User tempUser = this.userService.forGotInfo(username);
        if (tempUser != null) {
            String pass = this.emailService.sendNewPassword(tempUser.getEmail(), tempUser.getFirstName());
            tempUser.setPassword(pass);
            this.userService.updateUser(tempUser);
            response = new Response(true, "An email has been sent to this account holder.", tempUser.getEmail());
        } else {
            response = new Response(false, "There is no user by the username:" + username, null);
        }
        return response;
    }

    //Will update the profile of this user
    @PutMapping("updateUser")
    public Response updateUser(@RequestBody User user, @RequestHeader Map<String, String> headers) {
        Response response;

        DecodedJWT decoded = jwtUtility.verify(headers.get("authorization"));
        if(decoded == null) {
            return new Response(false, "Invalid Token (1), try again.", null);
        }
        else{
            if(decoded.getClaims().get("userId").asInt() == user.getUserId()) {
                if (user.getPassword() != null && user.getPassword().length() >= 8)
                    user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
                User updateUser = this.userService.updateUser(user);

                user.setPassword(null);
                response = new Response(true, "Token found. Profile has been updated.", updateUser);
            }
            else{
                return new Response(false, "Invalid Token (2), try again.", null);
            }
        }

        return response;
    }

    //Will get user with matching Id
    @GetMapping("user/{id}")
    public Response getUserById(@PathVariable Integer id) {
        Response response;
        User user = (User) this.userService.getUserById(id);
        if (user != null) {
            user.setPassword(null);
            response = new Response(true, "User obtained.", user);
        } else {
            response = new Response(false, "User was not found.", null);
        }
        return response;
    }

    @GetMapping("bookmark/{userId}/{pageNumber}")
    public Response getBookmarks(@PathVariable Integer userId, @PathVariable Integer pageNumber,@RequestHeader Map<String, String> headers) {
        Response response;
        DecodedJWT decoded = jwtUtility.verify(headers.get("authorization"));
        if(decoded == null) {
            return new Response(false, "Invalid Token, try again.", null);
        }
        else {
            if(decoded.getClaims().get("userId").asInt() == userId) {
                User user = userService.getUserById(userId);
                if (user != null) {
                    List<Integer> bookmarks = this.userService.getBookmarks(user.getUserId(), pageNumber);

                    if (bookmarks != null) {
                        response = new Response(true, "Bookmarks obtained.", bookmarks);
                    } else {
                        response = new Response(false, "Bookmarks not found.", null);
                    }
                    return response;
                } else {
                    response = new Response(false, "User not found", null);
                    return response;
                }
            }
            else{
                return new Response(false, "Invalid Token, try again.", null);
            }
        }
    }

    @PostMapping("bookmark/{userId}")
    public Response setBookmark(@PathVariable Integer userId, @RequestBody Integer postId, @RequestHeader Map<String, String> headers) {
        Response response;
        DecodedJWT decoded = jwtUtility.verify(headers.get("authorization"));

        if(decoded == null) {
            return new Response(false, "Invalid Token, try again.", null);
        }
        else {
            if(decoded.getClaims().get("userId").asInt() == userId) {
                User user = userService.getUserById(userId);
                if (user != null) {
                    List<Integer> bookmarks = this.userService.setBookmark(user.getUserId(), postId);

                    if (bookmarks != null) {
                        response = new Response(true, "Bookmark set.", bookmarks);
                    } else {
                        response = new Response(false, "Bookmark not set.", null);
                    }
                    return response;
                } else {
                    response = new Response(false, "User not found", null);
                    return response;
                }
            }
            else{
                return new Response(false, "Invalid Token, try again.", null);
            }
        }
    }

    @DeleteMapping("bookmark/{userId}/{postId}")
    public Response removeBookmark(@PathVariable Integer userId, @PathVariable Integer postId, @RequestHeader Map<String, String> headers){
        Response response;
        DecodedJWT decoded = jwtUtility.verify(headers.get("authorization"));

        if(decoded == null) {
            return new Response(false, "Invalid Token, try again.", null);
        }
        else {
            if(decoded.getClaims().get("userId").asInt() == userId) {
                User user = userService.getUserById(userId);
                if (user != null) {
                    List<Integer> bookmarks = this.userService.removeBookmark(user.getUserId(), postId);

                    if (bookmarks != null) {
                        response = new Response(true, "Bookmark removed.", bookmarks);
                    } else {
                        response = new Response(false, "Bookmark not found.", null);
                    }
                    return response;
                } else {
                    response = new Response(false, "User not found", null);
                    return response;
                }
            }
            else{
                return new Response(false, "Invalid Token, try again.", null);
            }
        }
    }

    @PostMapping("follow/{userId}")
    public Response follow(@PathVariable Integer userId, @RequestBody Integer followUserId, @RequestHeader Map<String, String> headers) {
        Response response;
        DecodedJWT decoded = jwtUtility.verify(headers.get("authorization"));

        if(decoded == null) {
            return new Response(false, "Invalid Token, try again.", null);
        }
        else {
            if(decoded.getClaims().get("userId").asInt() == userId) {
                User followUser = userService.getUserById(followUserId);

                int id = followUserId;

                if (followUser == null) {
                    response = new Response(false, "User not found", null);
                    return response;
                } else if (followUser.getUserId() == userId) {
                    response = new Response(false, "You cannot follow youself", null);
                    return response;
                } else {
                    Set<Integer> followers = this.userService.follow(userId, id);
                    if (followers != null) {
                        response = new Response(true, "follow success.", followers);
                    } else {
                        response = new Response(false, "cannot follow.", null);
                    }
                    return response;
                }
            }
            else {
                return new Response(false, "Invalid Token, try again.", null);
            }
        }
    }

    @DeleteMapping("follow/{userId}")
    public Response unfollow(@PathVariable Integer userId, @RequestBody Integer followUserId, @RequestHeader Map<String, String> headers) {
        Response response;
        DecodedJWT decoded = jwtUtility.verify(headers.get("authorization"));

        if(decoded == null) {
            return new Response(false, "Invalid Token, try again.", null);
        }
        else {
            if(decoded.getClaims().get("userId").asInt() == userId) {
                User followUser = userService.getUserById(followUserId);

                int id = followUserId;

                Set<Integer> followings = this.userService.getFollowing(userId);
                boolean isFollowing = followings.contains(followUserId);
                System.out.println("Is following: " + isFollowing);

                if (followUser == null) {
                    response = new Response(false, "User not found", null);
                    return response;
                } else if (followUser.getUserId() == userId) {
                    response = new Response(false, "You cannot unfollow youself", null);
                    return response;
                } else if (!isFollowing) {
                    response = new Response(false, "you are not following this user.", null);
                    return response;
                } else {
                    Set<Integer> followers = this.userService.unfollow(userId, id);
                    if (followers != null) {
                        response = new Response(true, "unfollow success.", followers);
                    } else {
                        response = new Response(false, "you are not following this user.", null);
                    }
                    return response;
                }
            }
            else{
                return new Response(false, "Invalid Token, try again.", null);
            }
        }
    }

    // will review for further usage
    @GetMapping("follower")
    public Response getfollowers(HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("loggedInUser");
        Response response;
        if (user != null) {
            Set<Integer> followers = this.userService.getFollowers(user.getUserId());
            if (followers != null) {
                response = new Response(true, "Followers obtained.", followers);
            } else {
                response = new Response(false, "Followers not found.", null);

            }
            return response;
        }else{
            response = new Response(false, "User not found", null);
            return response;
        }

    }

    @GetMapping("follow/{userId}")
    public Response getfollowing(@PathVariable Integer userId, @RequestHeader Map<String, String> headers) {
        Response response;
        DecodedJWT decoded = jwtUtility.verify(headers.get("authorization"));

        if(decoded == null) {
            return new Response(false, "Invalid Token, try again.", null);
        }
        else {
            if (decoded.getClaims().get("userId").asInt() == userId) {
                User user = userService.getUserById(userId);
                if (user != null) {
                    Set<Integer> followers = this.userService.getFollowing(userId);
                    if (followers != null) {
                        response = new Response(true, "Following obtained.", followers);
                    } else {
                        response = new Response(false, "Following not found.", null);

                    }
                    return response;
                } else {
                    response = new Response(false, "User not found", null);
                    return response;
                }
            }
            else{
                return new Response(false, "Invalid Token, try again.", null);
            }
        }
    }
}



