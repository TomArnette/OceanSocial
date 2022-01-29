package com.revature.feed.services;

import com.revature.feed.models.Like;
import com.revature.feed.models.Post;
import com.revature.feed.repository.LikeDao;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest//annotation
@ExtendWith(MockitoExtension.class)
class LikeServiceTest {

    LikeService likeService;
    //PostService postService;

    @Mock
    LikeDao likeDao;
    //  PostDao postDao;

    @BeforeEach
    void setUp(){
        this.likeService = new LikeService(likeDao);
        // this.postService = new PostService(postDao);
    }
    @Ignore
    @Test
    void createLike() {
        //  Date todaysDate = new Date();
        Post post =new Post(1,null,"xdikjjhhhjhg","Hello there",null,"lolhgghjjiuytg",1);
        Like tempLike = new Like(1,1,post);

        Mockito.when(likeDao.save(tempLike)).thenReturn(tempLike);

        //this.postService.createPost(post);
        Like actualResult = this.likeService.createLike(tempLike);

        assertEquals(tempLike,actualResult);
    }
    @Ignore
    @Test
    void createLikeReturnNull(){
        Post post =new Post(1,null,"xdikjjhhhjhg","Hello there",null,"lolhgghjjiuytg",1);
        Like tempLike = new Like(1,1,post);
        Mockito.when(likeDao.save(tempLike)).thenReturn(null);
        Like actualResult = this.likeService.createLike(tempLike);
        assertNotEquals(tempLike,actualResult);
    }

    @Test
    void getLikeByPostId() {
        Post post =new Post(1,null,"xd","Hello there",null,"lol",1);
        List<Like> tempLike = new ArrayList<>();

        tempLike.add(new Like(1,1,post));

        Mockito.when(likeDao.getLikesByPostId(post.getPostId())).thenReturn(tempLike);
        List<Like> actualResult = this.likeService.getLikeByPostId(post.getPostId());
        assertEquals(tempLike,actualResult);
    }

    @Test
    void getLikeByPostIdZero(){

        Post post =new Post(1,null,"xd","Hello there",null,"lol",1);

        List<Like> expectedResult = new ArrayList<>();

        // expectedResult.add(new Like(1,1,post));

        Mockito.when(likeDao.getLikesByPostId(post.getPostId())).thenReturn(expectedResult);

        List<Like> actualResult = this.likeService.getLikeByPostId(post.getPostId());

        assertEquals(expectedResult,actualResult);



    }
    @Test
    void deleteLike() {
        Integer likeId=1;

        boolean expectedResult=true;

        Mockito.when(likeDao.existsById(likeId)).thenReturn(expectedResult);

        boolean actualResult=this.likeService.deleteLike(likeId);

        assertEquals(expectedResult,actualResult);
        Mockito.verify(likeDao, Mockito.times(1)).deleteById(likeId);

    }

    @Test
    void deleteLikeNotFound(){
        Integer likeId=1;

        boolean expectedResult=false;

        Mockito.when(likeDao.existsById(likeId)).thenReturn(false);

        boolean actualResult = this.likeService.deleteLike(likeId);

        assertEquals(expectedResult,actualResult);
        Mockito.verify(likeDao, Mockito.times(0)).deleteById(likeId);
    }
}