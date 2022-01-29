package com.revature.feed.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.revature.feed.config.JwtUtility;
import com.revature.feed.models.Post;
import com.revature.feed.models.Response;
import com.revature.feed.services.PostService;
import com.revature.feed.services.RabbitService;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PostControllerTest {

    PostController postController;

    @Mock
    PostService postService;

    @Mock
    JwtUtility jwtUtility;

    @Mock
    DecodedJWT decodedJWT;

    @Mock
    RabbitService rabbitService;

    @BeforeEach
    void setUp() {
        this.postController = new PostController(postService, jwtUtility, rabbitService);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createPostTokenVerifiedPostSuccess() {
        //successfully create a post with a valid token

        //ASSIGN
        Post post = new Post();
        post.setPostId(1);
        post.setPostText("This is a post!");
        post.setUserId(1);

        Response expectedResult = new Response(true, "Post has been created", post);

        Map<String, String> headers = new HashMap<>();
        headers.put("authorization", "token string goes here"); //jwt is being mocked so the value can really be anything

        Mockito.when(jwtUtility.verify(headers.get("authorization"))).thenReturn(decodedJWT);
        Mockito.when(postService.createPost(post)).thenReturn(post);
        Mockito.when(rabbitService.postNotification(post)).thenReturn("Ignoring...");

        //ACT
        Response actualResult = this.postController.createPost(post, headers);

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void createPostTokenNotValid() {
        //attempt to create a post with an invalid token

        //ASSIGN
        Post post = new Post();
        post.setPostId(1);
        post.setPostText("This is a post!");
        post.setUserId(1);

        Response expectedResult = new Response(false, "Invalid token", null);

        Map<String, String> headers = new HashMap<>();
        headers.put("authorization", "token string goes here"); //jwt is being mocked so the value can really be anything

        Mockito.when(this.jwtUtility.verify(headers.get("authorization"))).thenReturn(null);

        //ACT
        Response actualResult = this.postController.createPost(post, headers);

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void createPostUnsuccessful(){
        //unsuccessfully create a post with a valid token

        //ASSIGN
        Post post = new Post();
        post.setPostId(1);
        post.setPostText("This is a post!");
        post.setUserId(1);

        Response expectedResult = new Response(false, "Post was not created", null);

        Map<String, String> headers = new HashMap<>();
        headers.put("authorization", "token string goes here"); //jwt is being mocked so the value can really be anything

        Mockito.when(jwtUtility.verify(headers.get("authorization"))).thenReturn(decodedJWT);
        Mockito.when(postService.createPost(post)).thenReturn(null);

        //ACT
        Response actualResult = this.postController.createPost(post,headers);

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void lookForAPostSuccessful() {
        //successfully get a post given a post ID (token valid)

        //ASSIGN
        Post post = new Post();
        post.setPostId(1);
        post.setPostText("This is a post!");
        post.setUserId(1);

        Response expectedResult = new Response(true, "Here is the post", post);

        Map<String, String> headers = new HashMap<>();
        headers.put("authorization", "token string goes here"); //jwt is being mocked so the value can really be anything

        Mockito.when(jwtUtility.verify(headers.get("authorization"))).thenReturn(decodedJWT);
        Mockito.when(postService.getPostById(1)).thenReturn(post);

        //ACT
        Response actualResult = this.postController.lookForAPost(1, headers);

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void lookForAPostUnsuccessful() {
        //unsuccessfully get a post given a post ID (token valid)

        //ASSIGN
        Post post = new Post();
        post.setPostId(1);
        post.setPostText("This is a post!");
        post.setUserId(1);

        Response expectedResult = new Response(false, "Post was not found",null);

        Map<String, String> headers = new HashMap<>();
        headers.put("authorization", "token string goes here"); //jwt is being mocked so the value can really be anything

        Mockito.when(jwtUtility.verify(headers.get("authorization"))).thenReturn(decodedJWT);
        Mockito.when(postService.getPostById(2)).thenReturn(null);

        //ACT
        Response actualResult = this.postController.lookForAPost(2, headers);

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }

/*    @Test
    void lookForPostByUserSuccessful() {
        //successfully get posts given a user ID

        //ASSIGN
        Post post1 = new Post();
        post1.setPostId(1);
        post1.setPostText("This is a post!");
        post1.setUserId(1);
        Post post2 = new Post();
        post2.setPostId(2);
        post2.setPostText("This is a post!");
        post2.setUserId(1);
        List<Post> posts = new ArrayList<>();
        posts.add(post1);
        posts.add(post2);

        Response expectedResult = new Response(true, "Here is the post", posts);

        Map<String, String> headers = new HashMap<>();
        headers.put("authorization", "token string goes here"); //jwt is being mocked so the value can really be anything

        Mockito.when(jwtUtility.verify(headers.get("authorization"))).thenReturn(decodedJWT);
        Mockito.when(postService.getPostByUserId(1)).thenReturn(posts);

        //ACT
        Response actualResult = this.postController.lookForPostByUser(1, headers);

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }*/

/*    @Test
    void lookForPostByUserUnsuccessful() {
        //unsuccessfully get posts given a user ID

        //ASSIGN
        Post post1 = new Post();
        post1.setPostId(1);
        post1.setPostText("This is a post!");
        post1.setUserId(1);
        Post post2 = new Post();
        post2.setPostId(2);
        post2.setPostText("This is a post!");
        post2.setUserId(1);
        List<Post> posts = new ArrayList<>();
        posts.add(post1);
        posts.add(post2);

        Response expectedResult = new Response(false, "Post was not found",null);

        Map<String, String> headers = new HashMap<>();
        headers.put("authorization", "token string goes here"); //jwt is being mocked so the value can really be anything

        Mockito.when(jwtUtility.verify(headers.get("authorization"))).thenReturn(decodedJWT);
        Mockito.when(postService.getPostByUserId(2)).thenReturn(new ArrayList<>());

        //ACT
        Response actualResult = this.postController.lookForPostByUser(2, headers);

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }*/

    @Test
    void updatePostSuccessfully() {
        //successfully update a post given a post

        //ASSIGN
        Post updatedPost = new Post();
        updatedPost.setPostId(1);
        updatedPost.setPostText("Updated!");
        updatedPost.setUserId(1);


        Response expectedResult = new Response(true,"Post has been updated",updatedPost);

        Map<String, String> headers = new HashMap<>();
        headers.put("authorization", "token string goes here"); //jwt is being mocked so the value can really be anything

        Mockito.when(jwtUtility.verify(headers.get("authorization"))).thenReturn(decodedJWT);
        Mockito.when(postService.updatePost(updatedPost)).thenReturn(updatedPost);

        //ACT
        Response actualResult = this.postController.updatePost(updatedPost, headers);

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void updatePostUnsuccessfully() {
        //unsuccessfully update a post given a post

        //ASSIGN
        Post updatedPost = new Post();
        updatedPost.setPostId(1);
        updatedPost.setPostText("Updated!");
        updatedPost.setUserId(1);


        Response expectedResult = new Response(false,"Post has not been updated", updatedPost);

        Map<String, String> headers = new HashMap<>();
        headers.put("authorization", "token string goes here"); //jwt is being mocked so the value can really be anything

        Mockito.when(jwtUtility.verify(headers.get("authorization"))).thenReturn(decodedJWT);
        Mockito.when(postService.updatePost(updatedPost)).thenReturn(null);

        //ACT
        Response actualResult = this.postController.updatePost(updatedPost, headers);

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void deletePostSuccessfully() {
        //successfully delete a post given a post

        //ASSIGN
        Post deletePost = new Post();
        deletePost.setPostId(1);
        deletePost.setPostText("Delete me! I dare you!");
        deletePost.setUserId(1);


        Response expectedResult = new Response(true,"Post was deleted", deletePost);

        Map<String, String> headers = new HashMap<>();
        headers.put("authorization", "token string goes here"); //jwt is being mocked so the value can really be anything

        Mockito.when(jwtUtility.verify(headers.get("authorization"))).thenReturn(decodedJWT);
        Mockito.when(postService.deletePost(1)).thenReturn(deletePost);

        //ACT
        Response actualResult = this.postController.deletePost(deletePost.getPostId(), headers);

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void deletePostUnsuccessfully() {
        //unsuccessfully delete a post given a post

        //ASSIGN
        Post deletePost = new Post();
        deletePost.setPostId(1);
        deletePost.setPostText("Delete me! I dare you!");
        deletePost.setUserId(1);


        Response expectedResult = new Response(false,"There was an error deleting this post", null);

        Map<String, String> headers = new HashMap<>();
        headers.put("authorization", "token string goes here"); //jwt is being mocked so the value can really be anything

        Mockito.when(jwtUtility.verify(headers.get("authorization"))).thenReturn(decodedJWT);
        Mockito.when(postService.deletePost(1)).thenReturn(null);

        //ACT
        Response actualResult = this.postController.deletePost(deletePost.getPostId(), headers);

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }
    @Ignore
    @Test
    void getPostFromFaveSuccessfully() {
        //successfully get favorite posts given a "page number"

        //ASSIGN
        List<Integer> fave = new ArrayList<>();
        fave.add(1);
        fave.add(2);
        fave.add(3);

        Post post1 = new Post();
        post1.setPostId(1);
        post1.setPostText("This is a post!");
        post1.setUserId(1);
        Post post2 = new Post();
        post2.setPostId(2);
        post2.setPostText("This is a post!");
        post2.setUserId(1);
        Post post3 = new Post();
        post3.setPostId(3);
        post3.setPostText("This is a post!");
        post3.setUserId(1);

        List<Post> favePost = new ArrayList<>();
        favePost.add(post1);
        favePost.add(post2);
        favePost.add(post3);

        Map<String, String> headers = new HashMap<>();
        headers.put("authorization", "token string goes here"); //jwt is being mocked so the value can really be anything

        Response expectedResult = new Response(true,"Favorite list", favePost);

        Mockito.when(jwtUtility.verify(headers.get("authorization"))).thenReturn(decodedJWT);
        Mockito.when(decodedJWT.getClaims().get("userId").asInt()).thenReturn(2);
        Mockito.when(rabbitService.requestListOfFollowers(2)).thenReturn(fave);
        Mockito.when(postService.selectPostForFav(1, fave)).thenReturn(favePost);

        //ACT
        Response actualResult = this.postController.getPostFromFave(1, headers);

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }
    @Ignore
    @Test
    void getPostFromFaveUnsuccessfully() {
        //unsuccessfully get favorite posts given a "page number"

        //ASSIGN
        List<Integer> fave = new ArrayList<>();
        fave.add(1);
        fave.add(2);
        fave.add(3);

        /*Post post1 = new Post();
        post1.setPostId(1);
        post1.setPostText("This is a post!");
        post1.setUserId(1);
        Post post2 = new Post();
        post2.setPostId(2);
        post2.setPostText("This is a post!");
        post2.setUserId(1);
        Post post3 = new Post();
        post3.setPostId(3);
        post3.setPostText("This is a post!");
        post3.setUserId(1);
        List<Post> favePost = new ArrayList<>();
        favePost.add(post1);
        favePost.add(post2);
        favePost.add(post3);*/

        Map<String, String> headers = new HashMap<>();
        headers.put("authorization", "token string goes here"); //jwt is being mocked so the value can really be anything

        Response expectedResult = new Response(false,"You have reached the end", null);

        Mockito.when(jwtUtility.verify(headers.get("authorization"))).thenReturn(decodedJWT);
        //Mockito.when(decodedJWT.getClaims().get("userId").asInt()).thenReturn(2);
        Mockito.when(rabbitService.requestListOfFollowers(2)).thenReturn(fave);
        Mockito.when(postService.selectPostForFav(2, fave)).thenReturn(null);

        //ACT
        Response actualResult = this.postController.getPostFromFave(2, headers);

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getCommentReturnSuccessfully() {
        //get comments given a post ID
        //it will always return a successful response

        //ASSIGN
        Post comment1 = new Post();
        comment1.setPostId(2);
        comment1.setUserId(2);
        comment1.setPostText("This is a comment!");
        comment1.setPostParentId(1);
        Post comment2 = new Post();
        comment2.setPostId(3);
        comment2.setUserId(2);
        comment2.setPostText("This is a comment!");
        comment2.setPostParentId(1);
        Post comment3 = new Post();
        comment3.setPostId(4);
        comment3.setUserId(2);
        comment3.setPostText("This is a comment!");
        comment3.setPostParentId(1);

        List<Post> comment = new ArrayList<>();
        comment.add(comment1);
        comment.add(comment2);
        comment.add(comment3);

        Map<String, String> headers = new HashMap<>();
        headers.put("authorization", "token string goes here"); //jwt is being mocked so the value can really be anything

        Response expectedResult = new Response(true, "Here are the comments", comment);

        Mockito.when(jwtUtility.verify(headers.get("authorization"))).thenReturn(decodedJWT);
        Mockito.when(postService.getAllParentId(1)).thenReturn(comment);

        //ACT
        Response actualResult = this.postController.getComment(1, headers);

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }
    @Test
    void getCommentReturnUnsuccessfully() {
        //get comments given a post ID
        //it will always return a successful response

        //ASSIGN
        Post comment1 = new Post();
        comment1.setPostId(2);
        comment1.setUserId(2);
        comment1.setPostText("This is a comment!");
        comment1.setPostParentId(1);
        Post comment2 = new Post();
        comment2.setPostId(3);
        comment2.setUserId(2);
        comment2.setPostText("This is a comment!");
        comment2.setPostParentId(1);
        Post comment3 = new Post();
        comment3.setPostId(4);
        comment3.setUserId(2);
        comment3.setPostText("This is a comment!");
        comment3.setPostParentId(1);

        List<Post> comment = new ArrayList<>();
        comment.add(comment1);
        comment.add(comment2);
        comment.add(comment3);

        Map<String, String> headers = new HashMap<>();
        headers.put("authorization", "token string goes here"); //jwt is being mocked so the value can really be anything

        Response expectedResult = new Response(true, "Here are the comments", comment);

        Mockito.when(jwtUtility.verify(headers.get("authorization"))).thenReturn(decodedJWT);
        Mockito.when(postService.getAllParentId(1)).thenReturn(comment);

        //ACT
        Response actualResult = this.postController.getComment(1, headers);

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }
}

