package com.revature.ocean.services;

import com.revature.ocean.models.Notification;
import com.revature.ocean.models.User;
import com.revature.ocean.models.Response;
import com.revature.ocean.models.UserResponse;
import com.revature.ocean.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * The UserService handles the business logic for the various actions a User will take when using the
 * Ocean social network app.
 */
@Service("userService")
public class UserService {
    private UserDao userDao;

    private NotificationService notificationService;

    @Autowired
    public UserService(UserDao userDao, NotificationService notificationService) {
        this.userDao = userDao;
        this.notificationService = notificationService;
    }

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    protected static UserResponse format(User user) {
        return new UserResponse(user.getUserId(), user.getUsername(), user.getProPicUrl(), user.getLastNotification());
    }

    /**
     * Method to log the user into the website
     *
     * @param user  the user object that is signing into the website
     * @return      returns the user object
     */
    public User login(User user){
        User tempUser = checkForUser(user.getUsername());
        //checks if the search returns a user object
        if(tempUser != null){
            //Checks to make sure their passwords match
            boolean isPasswordMatch = passwordEncoder.matches(user.getPassword(), tempUser.getPassword());
            if(isPasswordMatch){
                return tempUser;
            }
        }
        return null;
    }

    /**
     * Checks whether a User is persisted in the database
     * @param username  the username being checked against data in the database
     * @return          returns the found User object
     */
    public User checkForUser(String username){ return this.userDao.findUserByUsername(username); }

    /**
     * Used to register/create a new User object. Performed when a user registers with the website.
     *
     * @param user  user object that is being passed to the method
     * @return      returns the User object
     */
    public User createUser(User user) {
        //check to see if user is already in database
        User tempUser = checkForUser(user.getUsername());
        //If username is not found in database it will create the user
        if (tempUser == null) {
            String bcPass = user.getPassword();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encode = passwordEncoder.encode(bcPass);
            user.setPassword(encode);
            return this.userDao.save(user);
        }
        //if tempUser returns something from database it'll return null as username is in use
        return null;
    }

    /**
     * Method invoked when user clicks "Forgot Password?" and enters their username.
     * The method checks that the user exists in the database. Used to create and send a new password
     * to the user.
     *
     * @param username  username for the user who forgot their info
     * @return          returns the User object
     */
    public User forGotInfo(String username) {
        //check to see if user is already in database
        return checkForUser(username);
    }

    /**
     * Used to update information associated with a registered user.
     * Individual pieces of information may be updated or all information may be updated at once.
     *
     * @param user  the user whose information will be changed
     * @return      returns the User object with updated information
     */
    public User updateUser(User user) {
        //Gets the user from Database
        User dataBaseUser =this.userDao.findById(user.getUserId()).orElse(null);
        //Checks to see if a result was returned
        if(dataBaseUser != null){
            //To make sure the ID & Password doesn't get changed by anyone
            dataBaseUser.setFirstName(user.getFirstName());
            dataBaseUser.setLastName(user.getLastName());
            dataBaseUser.setEmail(user.getEmail());
            dataBaseUser.setBday(user.getBday());
            dataBaseUser.setAboutMe(user.getAboutMe());
            if (user.getProPicUrl() != null) dataBaseUser.setProPicUrl(user.getProPicUrl());
            if (user.getPassword() != null) dataBaseUser.setPassword(user.getPassword());

            this.userDao.save(dataBaseUser);
            //Returns the updated user
            return dataBaseUser;
        }
        return null;
    }

    /**
     * Retrieves a user that matches the userId passed to the method.
     *
     * @param userId    userId of the user to be retrieved
     * @return          returns the User object matching the userId
     */
    public User getUserById(Integer userId) {
        return this.userDao.findById(userId).orElse(null);
    }

    /**
     * Retrieves a list of all Users registered with the appl
     *
     * @return  returns the list of all Users
     */
    public List<User> getAllUsers() {
        List<User> users = this.userDao.findAll();
        for(User a : users){
            a.setPassword(null);
        }
        return users;
    }

    /**
     * Returns a set of the Bookmarks associated with a specific userId.
     *
     * @param userId    the userId of the user whose bookmarks are being retrieved
     * @return          returns a set of bookmarks
     */
    public List<Integer> getBookmarks(Integer userId, Integer pageNumber){
        User user = this.userDao.findById(userId).orElse(null);
        List<Integer> bookmarks = user.getBookmarks();
        if(bookmarks == null){
            return new ArrayList<>();
        }

        int start = (pageNumber-1)*20;
        int end = (pageNumber*20)-1;

        List<Integer> pages = new ArrayList<>();

        for(int i = start; i<=end && i<bookmarks.size(); i++){
            pages.add(bookmarks.get(i));
        }


        return pages;
    }

    /**
     * Adds a new Bookmark to a User object.
     *
     * @param userId    the userId of the user adding a new bookmark
     * @param postId    the postId of the post that the user is bookmarking
     * @return          returns the updated set of bookmarks
     */
    public List<Integer> setBookmark(Integer userId, Integer postId){
        User user = this.userDao.findById(userId).orElse(null);
        List<Integer> bookmarks = user.getBookmarks();
        if(bookmarks.contains(postId)){
            return null;
        }
        bookmarks.add(postId);
        user.setBookmarks(bookmarks);
        this.userDao.save(user);
        return bookmarks;
    }

    /**
     * Deletes a bookmark from the User object.
     *
     * @param userId    userId of the user who is removing the bookmark
     * @param postId    postId that will have its bookmark removed
     * @return          returns the updated set of bookmarks
     */
    public List<Integer> removeBookmark(Integer userId, Integer postId){
        User user = this.userDao.findById(userId).orElse(null);
        List<Integer> bookmarks = user.getBookmarks();
        bookmarks.remove(postId);
        user.setBookmarks(bookmarks);
        this.userDao.save(user);
        return bookmarks;
    }

    /**
     * Used to allow one User object to follow another User object.
     * This method will update the User's set of userId's displaying the userId's that the user follows
     * and will update the set of userId's displaying the userId's of the newly followed User object
     * showing which userId's are following them.
     *
     * @param userId        userId of the user following another user
     * @param followingID   userId of the user being followed
     * @return              returns a set of integers representing the userId's for followed & following users
     */
    public Set<Integer> follow(Integer userId, Integer followingID){
        User user = this.userDao.findById(userId).orElse(null);
        Set<Integer> following = user.getUser_following();
        following.add(followingID);

        User follow = this.userDao.findById(followingID).orElse(null);
        Set<Integer> followers = follow.getFollowers();
        followers.add(userId);
        user.setUser_following(following);
        this.userDao.save(user);

        if(userId != followingID){
            Notification notification = new Notification();
            notification.setUserFrom(user);
            notification.setUserBelongTo(follow);
            notification.setType("follow");
            notification.setTimestamp(System.currentTimeMillis());

            this.notificationService.createNotification(notification);
        }
        return following;
    }

    /**
     * Unfollows (removes a follow) for a specific userId
     *
     * @param userId        userId that is unfollowing another user
     * @param followingID   the userId of the user that has been unfollowed
     * @return              returns the updated set of userId's representing followed & following users
     */
    public Set<Integer> unfollow(Integer userId, Integer followingID){
        User user = this.userDao.findById(userId).orElse(null);
        Set<Integer> following = user.getUser_following();
        following.remove(followingID);

        User follow = this.userDao.findById(followingID).orElse(null);
        Set<Integer> followers = follow.getFollowers();
        followers.remove(userId);
        user.setUser_following(following);
        this.userDao.save(user);

        Notification notification = new Notification();
        notification.setUserFrom(user);
        notification.setUserBelongTo(follow);
        notification.setType("unfollow");
        notification.setTimestamp(System.currentTimeMillis());

        this.notificationService.createNotification(notification);

        return following;
    }

    /**
     * Retrieves the set of userId's of the user's that this user object follows
     *
     * @param userId    userId of the user whose followed users are being retrieved
     * @return          returns the set of userId's the user object follows
     */
    public Set<Integer> getFollowing(Integer userId){
        User user = this.userDao.findById(userId).orElse(null);
        if(user == null)
            return new HashSet<>();
        return user.getUser_following();
    }

    /**
     * Retrieves the set of userId's that are following the user object
     *
     * @param userId    userId of the user whose follower's userId's are being retrieved
     * @return          returns a set of userId's that are following the user
     */
    public Set<Integer> getFollowers(Integer userId){
        User user = this.userDao.findById(userId).orElse(null);
        return user.getFollowers();
    }
}
