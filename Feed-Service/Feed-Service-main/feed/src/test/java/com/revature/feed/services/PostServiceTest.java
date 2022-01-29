package com.revature.feed.services;

import com.revature.feed.models.Post;
import com.revature.feed.repository.PostDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    PostService postService;

    @Mock
    PostDao postDao;

    @BeforeEach
    void setUp()  {

        this.postService = new PostService(postDao);
    }



    @Test
    void getPostByIdReturnNull() {
        //assign
        Integer id = 1;
        Post expectedResult = null;
        Mockito.when(postDao.findById(id)).thenReturn(Optional.empty());
        //act
        Post actualResult = this.postService.getPostById(id);

        //assert
        assertEquals(expectedResult, actualResult);

    }

    @Test
    void getPostByIdReturnNotNull() {
        //assign
        Integer id = 1;
        Post expectedResult = new Post();
        Mockito.when(postDao.findById(id)).thenReturn(Optional.of(expectedResult));
        //act
        Post actualResult = this.postService.getPostById(id);

        //assert
        assertEquals(expectedResult, actualResult);

    }


    @Test
    void createPostWhenPostIdAlreadyExists() {
        //assign
       /* Post expectedResult = null;
        Integer id = 1;
        Post post = new Post(1, "postPic","postText",null,"postUrl",1);
        Mockito.when(postDao.findById(id)).thenReturn(Optional.of(post));
        //act
        Post actualResult = this.postService.createPost(post);
        //assert
        assertEquals(expectedResult, actualResult);
        Mockito.verify(postDao,Mockito.times(0)).save(post);*/
    }


    @Test
    void createUserWhenPostIdDoesntExists(){
        //assign
        Integer id = 1;
        Post expectedResult = new Post(5,null, "postPic","postText",null,"postUrl",1);
        // Mockito.when(postDao.findById(id)).thenReturn(Optional.empty());
        Mockito.when(postDao.save(expectedResult)).thenReturn(expectedResult);


        //act
        Post actualResult = this.postService.createPost(expectedResult);

        //assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void updatePostReturnNull() {
        Post post = new Post(2, 1, "URL", "Text", null, "URL", 1);
        Integer postId = 1;
        Mockito.when(this.postService.getPostById(postId)).thenReturn(null);
        Post expectedResult = null;
        Post actualResult = postService.updatePost(post);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void updatePost() {

        Integer id = 5;
        Post expectedResult = new Post(5,null, "postPic","postText",null,"postUrl",5);
        Mockito.when(postDao.findById(id)).thenReturn(Optional.of(expectedResult));

        //act
        Post actualResult = this.postService.updatePost(expectedResult);

        //assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void deletePost() {

        Integer id = 5;
        Post expectedResult = new Post(5,null, "postPic","postText",null,"postUrl",5);
        Mockito.when(postDao.findById(id)).thenReturn(Optional.of(expectedResult));

        //act
        Post actualResult = this.postService.deletePost(5);

        //assert
        assertEquals(expectedResult, actualResult);

    }
}