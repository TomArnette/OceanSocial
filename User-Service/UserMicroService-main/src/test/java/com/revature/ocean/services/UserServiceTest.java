package com.revature.ocean.services;

import com.revature.ocean.models.Notification;
import com.revature.ocean.models.User;
import com.revature.ocean.repository.NotificationDao;
import com.revature.ocean.repository.UserDao;
import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    UserService userService;

    @Mock
    UserDao userDao;

    @Mock
    NotificationService notificationService;

    @BeforeEach
    void setUp() {
        this.userService = new UserService(userDao,notificationService);

    }


    @Test
    void login() {
        //Mock method being called in the same class UserService
        UserService userServiceMethod = Mockito.spy(this.userService);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        //Assign
        // Mock DB entry
        User expectedResult = new User("user", "password", "test@test.com", "User", "Test", new Date(), "About Me");
        User inputUser = expectedResult;
        inputUser.setUsername("user");
        inputUser.setPassword("password");

        Mockito.when(userServiceMethod.checkForUser(inputUser.getUsername())).thenReturn(expectedResult);

        //Act
        this.userService.login(inputUser);

        //Assert
        assertTrue(bCryptPasswordEncoder.matches(inputUser.getPassword(), bCryptPasswordEncoder.encode(expectedResult.getPassword())));

        //Verify
        Mockito.verify(userServiceMethod, Mockito.times(1)).checkForUser(expectedResult.getUsername());
    }

    @Test
    void checkForUser() {
        //Assign
        User expectedResult = new User("user", "password", "test@test.com", "User", "Test", new Date(06-06-2001), "About Me");
        Mockito.when(userDao.findUserByUsername(expectedResult.getUsername())).thenReturn(expectedResult);

        //Act
        User actualResult = this.userService.checkForUser(expectedResult.getUsername());

        //Assert
        assertEquals(expectedResult, actualResult);

    }

    @Test
    void createUser() {
        //Mock method being called in the same class UserService
        UserService userServiceMethod = Mockito.spy(this.userService);

        //Assign
        User expectedResult = new User("user", "password", "test@test.com", "User", "Test", new Date(), "About Me");

        Mockito.when(userServiceMethod.checkForUser(expectedResult.getUsername())).thenReturn(null);
        Mockito.when(userDao.save(expectedResult)).thenReturn(expectedResult);

        //Act
        User actualResult = this.userService.createUser(expectedResult);

        //Assert
        assertEquals(expectedResult, actualResult);

        //Verify
        Mockito.verify(userDao, Mockito.times(1)).save(expectedResult);
        Mockito.verify(userServiceMethod, Mockito.times(1)).checkForUser(expectedResult.getUsername());
    }

    @Test
    void forGotInfo() {
        //Mock method being called in the same class UserService
        UserService userServiceMethod = Mockito.spy(this.userService);

        //Assign
        User expectedResult = new User("user", "password", "test@test.com", "User", "Test", new Date() ,"About Me");

        //Act
        Mockito.when(userServiceMethod.checkForUser(expectedResult.getUsername())).thenReturn(expectedResult);
        User actualResult = userServiceMethod.forGotInfo(expectedResult.getUsername());

        //Assert
        assertEquals(expectedResult, actualResult);

        //Verify
        //The forGotInfo() was invoked 2x as shown above in the //Act.
        Mockito.verify(userServiceMethod, Mockito.times(2)).checkForUser(expectedResult.getUsername());
    }

    @Test
    void updateUser() {
        //Assign
        User beforeUpdate = new User("user", "old", "test@test.com", "User", "Test", new Date() ,"About Me");
        User expectedResult = new User("user", "old", "test@test.com", "User1", "Test1", new Date() ,"About Me");

        Mockito.when(userDao.findById(beforeUpdate.getUserId())).thenReturn(Optional.of(beforeUpdate));
        Mockito.when(userDao.save(expectedResult)).thenReturn(expectedResult);

        //Act
        User actualResult = this.userService.updateUser(expectedResult);

        //Assert
        assertEquals(expectedResult, actualResult);

        //Verify
        Mockito.verify(userDao, Mockito.times(1)).findById(beforeUpdate.getUserId());
        Mockito.verify(userDao, Mockito.times(1)).save(expectedResult);

    }

    @Test
    void getUserById() {
        User expectedResult = new User("user", "new", "test@test.com", "User1", "Test1", new Date() ,"About Me");
        expectedResult.setUserId(1);

        Mockito.when(userDao.findById(expectedResult.getUserId())).thenReturn(Optional.of(expectedResult));

        //Act
        User actualResult = this.userService.getUserById(expectedResult.getUserId());

        //Assert
        assertEquals(expectedResult, actualResult);

        //Verify
        Mockito.verify(userDao, Mockito.times(1)).findById(expectedResult.getUserId());
    }

    @Test
    void getAllUsers() {
        //Assign
        List<User> expectedResult = new ArrayList<>();
        expectedResult.add(new User("user", "password", "test@test.com", "User", "Test", new Date() ,"About Me"));

        Mockito.when(userDao.findAll()).thenReturn(expectedResult);

        //Act
        List<User> actualResult = this.userService.getAllUsers();

        //Assert
        assertEquals(expectedResult, actualResult);

        //Verify
        Mockito.verify(userDao, Mockito.times(1)).findAll();
    }

    @Test
    void getBookmarks() {
        //Assign
        List<Integer> expectedResult = new ArrayList<>();
        expectedResult.add(2);
        User user = new User("user", "password", "test@test.com", "User", "Test", new Date() ,"About Me");
        user.setBookmarks(expectedResult);
        user.setUserId(1);

        Mockito.when(userDao.findById(user.getUserId())).thenReturn(Optional.of(user));

        //Act
        List<Integer>  actualResult = this.userService.getBookmarks(user.getUserId(),1);

        //Assert
        assertEquals(expectedResult, actualResult);

        //Verify
        Mockito.verify(userDao, Mockito.times(1)).findById(user.getUserId());
    }

    @Test
    void setBookmark() {
        //Assign
        List<Integer> expectedResult = new ArrayList<>();
        expectedResult.add(2);
        expectedResult.add(3);

        User user = new User();
        user.setBookmarks(expectedResult);
        user.setUserId(1);

        Mockito.when(userDao.findById(user.getUserId())).thenReturn(Optional.of(user));
        Mockito.when(userDao.save(user)).thenReturn(user);

        //Act
        List<Integer>  actualResult = this.userService.setBookmark(user.getUserId(), 4);

        //Assert
        assertEquals(expectedResult, actualResult);

        //Verify
        Mockito.verify(userDao, Mockito.times(1)).findById(user.getUserId());
        Mockito.verify(userDao, Mockito.times(1)).save(user);
    }

    @Test
    void removeBookmark() {
        //Assign
        List<Integer> expectedResult = new ArrayList<>();
        expectedResult.add(2);
        expectedResult.add(3);

        User user = new User();
        user.setBookmarks(expectedResult);
        user.setUserId(1);

        Mockito.when(userDao.findById(user.getUserId())).thenReturn(Optional.of(user));
        Mockito.when(userDao.save(user)).thenReturn(user);

        //Act
        List<Integer>  actualResult = this.userService.removeBookmark(user.getUserId(), 3);

        //Assert
        assertEquals(expectedResult, actualResult);

        //Verify
        Mockito.verify(userDao, Mockito.times(1)).findById(user.getUserId());
        Mockito.verify(userDao, Mockito.times(1)).save(user);
    }

    @Test
    void follow() {
        //Assign
        // Setting followers to user 1
        Set<Integer> following = new HashSet<>();
        following.add(2);
        User user = new User();
        user.setUserId(1);
        user.setUser_following(following);

        Set<Integer> expectedResult = user.getUser_following();

        // Setting following back to user2 as user1 follows him.
        Set<Integer> following2 = new HashSet<>();
        User user2 = new User();
        user2.setUserId(2);
        user2.setFollowers(following2);

        Set<Integer> followers = user2.getFollowers();
        followers.add(user.getUserId());


        Mockito.when(userDao.findById(user.getUserId())).thenReturn(Optional.of(user));
        Mockito.when(userDao.findById(user2.getUserId())).thenReturn(Optional.of(user2));
        Mockito.when(userDao.save(user)).thenReturn(user);

        //Act
        Set<Integer>  actualResult = this.userService.follow(user.getUserId(), 2);

        //Assert
        assertEquals(expectedResult, actualResult);

        //Verify
        Mockito.verify(userDao, Mockito.times(1)).findById(user.getUserId());
        Mockito.verify(userDao, Mockito.times(1)).save(user);
    }

    @Test
    void unfollow() {
        //Assign
        // Setting followers to user 1
        Set<Integer> following = new HashSet<>();
        following.add(2);
        User user = new User();
        user.setUserId(1);
        user.setUser_following(following);

        Set<Integer> expectedResult = user.getUser_following();

        // Setting following back to user2 as user1 follows him.
        Set<Integer> following2 = new HashSet<>();
        User user2 = new User();
        user2.setUserId(2);
        user2.setFollowers(following2);

        Set<Integer> followers = user2.getFollowers();
        followers.add(user.getUserId());

        Mockito.when(userDao.findById(user.getUserId())).thenReturn(Optional.of(user));
        Mockito.when(userDao.findById(user2.getUserId())).thenReturn(Optional.of(user2));
        Mockito.when(userDao.save(user)).thenReturn(user);

        //Act
        Set<Integer>  actualResult = this.userService.unfollow(user.getUserId(), 2);

        //Assert
        assertEquals(expectedResult, actualResult);

        //Verify
        Mockito.verify(userDao, Mockito.times(1)).findById(user.getUserId());
        Mockito.verify(userDao, Mockito.times(1)).save(user);
    }

    @Test
    void getFollowing() {
        //Assign
        Set<Integer> expectedResult = new HashSet<>();
        expectedResult.add(2);

        User user = new User();
        user.setUser_following(expectedResult);
        user.setUserId(1);

        Mockito.when(userDao.findById(user.getUserId())).thenReturn(Optional.of(user));

        //Act
        Set<Integer>  actualResult = this.userService.getFollowing(user.getUserId());

        //Assert
        assertEquals(expectedResult, actualResult);

        //Verify
        Mockito.verify(userDao, Mockito.times(1)).findById(user.getUserId());
    }

    @Test
    void getFollowers() {
        //Assign
        Set<Integer> expectedResult = new HashSet<>();
        expectedResult.add(2);

        User user = new User();
        user.setFollowers(expectedResult);
        user.setUserId(1);

        Mockito.when(userDao.findById(user.getUserId())).thenReturn(Optional.of(user));

        //Act
        Set<Integer>  actualResult = this.userService.getFollowers(user.getUserId());

        //Assert
        assertEquals(expectedResult, actualResult);

        //Verify
        Mockito.verify(userDao, Mockito.times(1)).findById(user.getUserId());
    }
}