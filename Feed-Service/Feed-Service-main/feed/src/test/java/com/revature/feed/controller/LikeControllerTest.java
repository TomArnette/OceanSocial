package com.revature.feed.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.revature.feed.config.JwtUtility;
import com.revature.feed.models.Like;
import com.revature.feed.models.Post;
import com.revature.feed.models.Response;
import com.revature.feed.services.LikeService;
import com.revature.feed.services.RabbitService;
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
class LikeControllerTest {

    LikeController likeController;

    @Mock
    LikeService likeService;

    @Mock
    JwtUtility jwtUtility;

    @Mock
    DecodedJWT decodedJWT;

    @Mock
    RabbitService rabbitService;

    @BeforeEach
    void setUp() {
        this.likeController = new LikeController(likeService, rabbitService, jwtUtility);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createLikeSuccess() {
        //successfully like a post

        //ASSIGN
        Post post = new Post();
        post.setPostId(1);
        post.setPostText("Like if you see this");
        post.setUserId(1);

        Like like = new Like();
        like.setLikeId(1);
        like.setUserId(2);
        like.setPost(post);

        Map<String, String> headers = new HashMap<>();
        headers.put("authorization", "token string goes here"); //jwt is being mocked so the value can really be anything

        Mockito.when(jwtUtility.verify(headers.get("authorization"))).thenReturn(decodedJWT);
        Mockito.when(likeService.createLike(like)).thenReturn(like);
        Mockito.when(rabbitService.likeNotification(like)).thenReturn("Ignoring...");

        Response expectedResult = new Response(true, "Like has been added to post", like);

        //ACT
        Response actualResult = this.likeController.createLike(like, headers);

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void createLikeUnsuccessful(){
        //unsuccessfully like a post

        //ASSIGN
        Post post = new Post();
        post.setPostId(1);
        post.setPostText("Like if you see this");
        post.setUserId(1);

        Like like = new Like();
        like.setLikeId(1);
        like.setUserId(2);
        like.setPost(post);

        Map<String, String> headers = new HashMap<>();
        headers.put("authorization", "token string goes here"); //jwt is being mocked so the value can really be anything

        Mockito.when(jwtUtility.verify(headers.get("authorization"))).thenReturn(decodedJWT);
        Mockito.when(likeService.createLike(like)).thenReturn(null);

        Response expectedResult = new Response(false, "Your like was not created", null);

        //ACT
        Response actualResult = this.likeController.createLike(like, headers);

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getLikeByPostIdSuccess() {
        //successfully get a like given a post ID

        //ASSIGN
        Post post = new Post();
        post.setPostId(1);
        post.setPostText("Like if you see this");
        post.setUserId(1);

        Like like = new Like();
        like.setLikeId(1);
        like.setUserId(2);
        like.setPost(post);

        List<Like> likes = new ArrayList<>();
        likes.add(like);

        Map<String, String> headers = new HashMap<>();
        headers.put("authorization", "token string goes here"); //jwt is being mocked so the value can really be anything

        Mockito.when(jwtUtility.verify(headers.get("authorization"))).thenReturn(decodedJWT);
        Mockito.when(likeService.getLikeByPostId(1)).thenReturn(likes);

        Response expectedResult = new Response(true, "Here is the likes of this post", likes);

        //ACT
        Response actualResult = this.likeController.getLikeByPostId(1, headers);

        //ASSERT
        assertEquals(expectedResult, actualResult);

    }

    @Test
    void getLikeByPostIdUnsuccessful() {
        //successfully get a like given a post ID

        //ASSIGN
        Integer postId = 2;

        Map<String, String> headers = new HashMap<>();
        headers.put("authorization", "token string goes here"); //jwt is being mocked so the value can really be anything

        Mockito.when(jwtUtility.verify(headers.get("authorization"))).thenReturn(decodedJWT);
        Mockito.when(likeService.getLikeByPostId(postId)).thenReturn(null);

        Response expectedResult = new Response(false, "Post was not found",null);

        //ACT
        Response actualResult = this.likeController.getLikeByPostId(postId, headers);

        //ASSERT
        assertEquals(expectedResult, actualResult);

    }

    @Test
    void getLikeByPostIdAndUserIDSuccessfully() {
        //determine if a user already liked a post
        //testing if a userId already liked a post

        //ASSIGN
        Post post = new Post();
        post.setPostId(1);
        post.setPostText("Like if you see this");
        post.setUserId(1);

        Like like = new Like();
        like.setLikeId(1);
        like.setUserId(2);
        like.setPost(post);

        List<Like> likes = new ArrayList<>();
        likes.add(like);

        Map<String, String> headers = new HashMap<>();
        headers.put("authorization", "token string goes here"); //jwt is being mocked so the value can really be anything

        Mockito.when(jwtUtility.verify(headers.get("authorization"))).thenReturn(decodedJWT);
        Mockito.when(likeService.getLikeByPostId(like.getPost().getPostId())).thenReturn(likes);

        Response expectedResult = new Response(true, "They have already liked this post", like.getLikeId());

        //ACT
        Response actualResult = this.likeController.getLikeByPostIdAndUserID(like.getPost().getPostId(), like.getUserId(), headers);

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getLikeByPostIdAndUserIdUnsuccessful() {
        //determine if a user already liked a post
        //testing if a userId has not yet liked a post

        //ASSIGN
        Post post = new Post();
        post.setPostId(1);
        post.setPostText("Like if you see this");
        post.setUserId(1);

        Like like = new Like();
        like.setLikeId(1);
        like.setUserId(2);
        like.setPost(post);

        List<Like> likes = new ArrayList<>();
        likes.add(like);

        Integer userIdToTest = 3;

        Map<String, String> headers = new HashMap<>();
        headers.put("authorization", "token string goes here"); //jwt is being mocked so the value can really be anything

        Mockito.when(jwtUtility.verify(headers.get("authorization"))).thenReturn(decodedJWT);
        Mockito.when(likeService.getLikeByPostId(like.getPost().getPostId())).thenReturn(likes);

        Response expectedResult = new Response(false, "They have not liked this post yet", false);

        //ACT
        Response actualResult = this.likeController.getLikeByPostIdAndUserID(like.getPost().getPostId(), userIdToTest, headers);

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void deleteLikeSuccessful() {
        //delete a like given the id of the like

        //ASSIGN
        Post post = new Post();
        post.setPostId(1);
        post.setPostText("Like if you see this");
        post.setUserId(1);

        Like like = new Like();
        like.setLikeId(1);
        like.setUserId(2);
        like.setPost(post);

        Map<String, String> headers = new HashMap<>();
        headers.put("authorization", "token string goes here"); //jwt is being mocked so the value can really be anything

        Mockito.when(jwtUtility.verify(headers.get("authorization"))).thenReturn(decodedJWT);
        Mockito.when(likeService.deleteLike(like.getLikeId())).thenReturn(true);

        Response expectedResult = new Response(true,"Your Like was removed", like.getLikeId());

        //ACT
        Response actualResult = this.likeController.deleteLike(like.getLikeId(), headers);

        //ASSERT
        assertEquals(expectedResult, actualResult);

    }

    @Test
    void deleteLikeUnsuccessful() {
        //unsuccessfully delete a like given a like ID

        //ASSIGN
        Integer likeIdToDelete = 2;

        Map<String, String> headers = new HashMap<>();
        headers.put("authorization", "token string goes here"); //jwt is being mocked so the value can really be anything

        Mockito.when(jwtUtility.verify(headers.get("authorization"))).thenReturn(decodedJWT);
        Mockito.when(likeService.deleteLike(likeIdToDelete)).thenReturn(false);

        Response expectedResult = new Response(false,"There was an error removing this like", likeIdToDelete);

        //ACT
        Response actualResult = this.likeController.deleteLike(likeIdToDelete, headers);

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }
}