package com.revature.feed.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.revature.feed.config.JwtUtility;
import com.revature.feed.models.Post;
import com.revature.feed.models.Response;
import com.revature.feed.services.PostService;
import com.revature.feed.services.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RestController("postController")
@RequestMapping(value= "post")
public class PostController {

    private PostService postService;

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private RabbitService rabbitService;

    @Autowired
    public PostController(PostService postService, JwtUtility jwtUtility, RabbitService rabbitService){
        this.postService = postService;
        this.jwtUtility = jwtUtility;
        this.rabbitService = rabbitService;
    }
    /**
     * @param post
     *         -Takes the post object passed from Front-end to add post to the database.
     * @param headers
     *         -Contains the token to validate user.
     *
     * This is to add a post to the database.
     *
     * @return null if user token is invalid.
     * @return true if the post was added to the database.
     * @return false if there was an issue with adding the post.
     */
    @PostMapping
    public Response createPost(@RequestBody Post post, @RequestHeader Map<String, String> headers){
        DecodedJWT decoded = jwtUtility.verify(headers.get("authorization"));
        if(decoded == null){
            return new Response(false, "Invalid token", null);
        }
        Response response;
        Post tempPost = this.postService.createPost(post);
        if(tempPost != null){
            response = new Response(true, "Post has been created", post);

            /**
             * @param post
             * Sends message to user service for notification feature. This notifies the user of a post was made in relations to a user.
             * */
            rabbitService.postNotification(post);

        }else{
            response = new Response(false, "Post was not created", null);
        }
        return response;
    }
    /**
     * @param pageNumber
     *         -This is page number to call 20 posts at a time to be returned to Front-end.
     * @param headers
     *         -Contains the token to validate user.
     *
     * Returns the post of the "Favorites" the user is following
     *
     * @return null if user token is invalid.
     * @return true and returns the posts of the "Favorites".
     * @return false and returns null if no posts are returned.
     */
    @GetMapping("fave/{pageNumber}")
    public Response getPostFromFave(@PathVariable Integer pageNumber, @RequestHeader Map<String, String> headers){
        DecodedJWT decoded = jwtUtility.verify(headers.get("authorization"));
        if(decoded == null){
            return new Response(false, "Invalid token", null);
        }
        Response response;
        /**
         * @param userId
         * Sends a message to User-Service to get the list of userIds' for "Favorite" list so we can return the posts for those users.
         * */
        List<Integer> fave = rabbitService.requestListOfFollowers(decoded.getClaims().get("userId").asInt());
        fave.add(decoded.getClaims().get("userId").asInt());
        List<Post> favePost = this.postService.selectPostForFav(pageNumber, fave);

        if(favePost != null){
            response = new Response(true,"Favorite list", favePost);
        }else{
            response = new Response(false,"You have reached the end", null);
        }
        return response;
    }
    /**
     * @param postId
     *         -PostId of the post user wants to view.
     * @param headers
     *         -Contains the token to validate user.
     *
     * Returns the single post the user requests.
     *
     * @return null if user token is invalid.
     * @return true and returns the single post requested.
     * @return false and returns null if no post are returned.
     */
    //Read a post
    @GetMapping("{postId}")
    public Response lookForAPost(@PathVariable Integer postId, @RequestHeader Map<String, String> headers){
        //Verify the JWT
        DecodedJWT decoded = jwtUtility.verify(headers.get("authorization"));
        if(decoded == null){
            return new Response(false, "Invalid token", null);
        }

        Response response;
        Post post = this.postService.getPostById(postId);
        if(post != null){
            response = new Response(true, "Here is the post", post);
        }else{
            response = new Response(false, "Post was not found",null);
        }
        return response;
    }
    /**
     * @param postId
     *         -This is to get the comments on a post. Or return comments to a comment made on a post.
     * @param headers
     *         -Contains the token to validate user.
     *
     * Returns the comments on a post or the comments on a comment.
     *
     * @return null if user token is invalid.
     * @return true and returns the comments on a post.
     * @return false and returns null if no comments are returned.
     */
    @GetMapping("comment/{postId}")
    public Response getComment(@PathVariable Integer postId, @RequestHeader Map<String, String> headers){
        //Verify the JWT
        DecodedJWT decoded = jwtUtility.verify(headers.get("authorization"));
        if(decoded == null){
            return new Response(false, "Invalid token", null);
        }
        Response response;
        List<Post> comment = this.postService.getAllParentId(postId);
        if(comment.size() <= 0){
            response = new Response(false, "There are no comments on this post",null);
        }else{
            response = new Response(true, "Here are the comments", comment);
        }
        return response;
    }

    /**
     * @param userId
     *         -userId to look for post.
     * @param pageNumber
     *         -This is the page being requested.
     * @param headers
     *         -Contains the token to validate user.
     *
     * Returns the posts of a user 20 at a time.
     *
     * @return null if user token is invalid.
     * @return true and returns 20 post of that user.
     * @return false and returns null if no posts are returned or they have reached the end of the user's posts.
     */
    @GetMapping("userId/{userId}/{pageNumber}")
    public Response lookForPostByUser(@PathVariable Integer userId, @PathVariable Integer pageNumber, @RequestHeader Map<String, String> headers){
        DecodedJWT decoded = jwtUtility.verify(headers.get("authorization"));
        if(decoded == null){
            return new Response(false, "Invalid token", null);
        }
        Response response;
        List<Post> post = this.postService.getPostByUserId(userId,pageNumber);
        if (post == null || post.size() <= 0) {
            response = new Response(false, "Post was not found",null);
        } else {
            response = new Response(true, "Here is the post", post);
        }
        return response;
    }
    /**
     * @param post
     *         -For if/when the user wants to update a post they have made.
     * @param headers
     *         -Contains the token to validate user.
     *
     * Allows the user to update a post/comment they have already made.
     *
     * @return null if user token is invalid.
     * @return true and returns the updated post/comment.
     * @return false and returns null if no post/comment are updated.
     */
    @PutMapping
    public Response updatePost(@RequestBody Post post, @RequestHeader Map<String, String> headers){
        DecodedJWT decoded = jwtUtility.verify(headers.get("authorization"));
        if(decoded == null){
            return new Response(false, "Invalid token", null);
        }
        Response response;
        Post updatePost = this.postService.updatePost(post);
        if(updatePost != post){
            response = new Response(false,"Post has not been updated", post);
        }else{
            response = new Response(true,"Post has been updated",post);
        }
        return response;
    }
    /**
     * @param postId
     *         -For if/when the user wants to delete a post they have made.
     * @param headers
     *         -Contains the token to validate user.
     *
     * Allows the user to delete a post/comment they have already made.
     *
     * @return null if user token is invalid.
     * @return true and returns the deleted post/comment.
     * @return false and returns null if no post/comment are deleted.
     */
    @DeleteMapping("{postId}")
    public Response deletePost(@PathVariable Integer postId, @RequestHeader Map<String, String> headers){
        DecodedJWT decoded = jwtUtility.verify(headers.get("authorization"));
        if(decoded == null){
            return new Response(false, "Invalid token", null);
        }

        Response response;
        Post deletePost = this.postService.deletePost(postId);
        if(deletePost != null){
            response = new Response(true,"Post was deleted", deletePost);
        }else{
            response = new Response(false,"There was an error deleting this post", null);
        }
        return response;
    }
}
