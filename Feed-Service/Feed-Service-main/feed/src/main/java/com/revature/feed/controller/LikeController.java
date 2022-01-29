package com.revature.feed.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.revature.feed.config.JwtUtility;
import com.revature.feed.models.Like;
import com.revature.feed.models.Response;
import com.revature.feed.services.LikeService;
import com.revature.feed.services.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
/**
 * @author Andrew Patrick
 * @author Ezequiel Flores
 * @author Joan Gorsky
 * @author Shane Danner
 * @author Thanh Nguyen
 */
@RestController("likeController")
@RequestMapping(value= "like")
public class LikeController {

    private LikeService likeService;

    //@Autowired
    private RabbitService rabbitService;

    //@Autowired
    private JwtUtility jwtUtility;

    @Autowired
    public LikeController(LikeService likeService, RabbitService rabbitService, JwtUtility jwtUtility){
        this.likeService = likeService;
        this.rabbitService = rabbitService;
        this.jwtUtility = jwtUtility;

    }

    /**
     * @param like
     *         -Takes the like object passed from Front-end to add to database.
     * @param headers
     *         -Contains the token to validate user.
     * @return null if user token is invalid.
     * @return true if the like was added to the database.
     * @return false if the like was not added to the database.
     */
    @PostMapping
    public Response createLike(@RequestBody Like like, @RequestHeader Map<String, String> headers){
        DecodedJWT decoded = jwtUtility.verify(headers.get("authorization"));
        if(decoded == null){
            return new Response(false, "Invalid token", null);
        }

        Response response;
        Like tempLike = this.likeService.createLike(like);
        if(tempLike != null){
            System.out.println(tempLike);
            response = new Response(true, "Like has been added to post", tempLike);

            /**
            * @param tempLike
            *Sends message to user service for notification feature. This notifies the user of a like made to their post.
            * */
            rabbitService.likeNotification(tempLike);
        }else{
            response = new Response(false, "Your like was not created", null);
        }
        return response;
    }
    /**
     * @param postId
     *         -Takes the postId passed from Front-end to locate all "Likes" made to that post.
     * @param headers
     *         -Contains the token to validate user.
     * @return null if user token is invalid.
     * @return true - with a list of all the "Likes" made to that post.
     * @return false - if there are no "Likes" made to that post.
     */
    @GetMapping("{postId}")
    public Response getLikeByPostId(@PathVariable Integer postId, @RequestHeader Map<String, String> headers){
        DecodedJWT decoded = jwtUtility.verify(headers.get("authorization"));
        if(decoded == null){
            return new Response(false, "Invalid token", null);
        }
            Response response;
        List<Like> like = this.likeService.getLikeByPostId(postId);
            if(like != null){
            response = new Response(true, "Here is the likes of this post", like);
        }else{
            response = new Response(false, "Post was not found",null);
        }
            return response;
    }
    /**
     * @param postId
     *         -Takes the postId passed from Front-end to check against the database.
     * @param headers
     *         -Contains the token to validate user.
     *
     * This is used to check if user has "liked" a post or not.
     *
     * @return null if user token is invalid.
     * @return true if they have already "liked" a post.
     * @return false if they have not "liked" a post.
     */
    @GetMapping("{postId}/{userId}")
    public Response getLikeByPostIdAndUserID(@PathVariable Integer postId, @PathVariable Integer userId,
                                             @RequestHeader Map<String, String> headers){
        DecodedJWT decoded = jwtUtility.verify(headers.get("authorization"));
        if(decoded == null){
            return new Response(false, "Invalid token", null);
        }
        Response response;
        boolean theyLikedIt = false;
       List<Like> like = this.likeService.getLikeByPostId(postId);
       Integer likeId = 0;
       for(Like a : like){
           if(a.getUserId().equals(userId)){
               ////////////////////will need to replace line above after testing///////////if(a.getUserId().equals(decoded.getClaims().get("userId").asInt())){
               theyLikedIt = true;
               likeId = a.getLikeId();
           }
       }
       if(!theyLikedIt){
           response = new Response(false, "They have not liked this post yet", false);
       }else{
           response = new Response(true, "They have already liked this post", likeId);
       }
       return response;
    }
    /**
     * @param likeId
     *         -Takes the likeId passed from Front-end to remove like from database.
     * @param headers
     *         -Contains the token to validate user.
     *
     * This is remove a like from the database.
     *
     * @return null if user token is invalid.
     * @return true if the like was removed.
     * @return false if there was an issue with removing the like.
     */
    @DeleteMapping("{likeId}")
    public Response deleteLike(@PathVariable Integer likeId, @RequestHeader Map<String, String> headers){
        DecodedJWT decoded = jwtUtility.verify(headers.get("authorization"));
        if(decoded == null){
            return new Response(false, "Invalid token", null);
        }
        Response response;
        Boolean deleteLike = this.likeService.deleteLike(likeId);
        if(deleteLike){
            response = new Response(true,"Your Like was removed", likeId);
        }else{
            response = new Response(false,"There was an error removing this like", likeId);
        }
        return response;
    }

}
