package com.revature.ocean.services;

import com.revature.ocean.models.Notification;
import com.revature.ocean.models.Response;
import com.revature.ocean.models.User;
import com.revature.ocean.models.UserResponse;
import com.revature.ocean.repository.NotificationDao;
import com.revature.ocean.repository.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {
    NotificationService notificationService;

    @Mock
    NotificationDao notificationDao;

    @Mock
    UserDao userDao;

    @BeforeEach
    void setUp() {
        this.notificationService = new NotificationService(notificationDao, userDao);
    }

    @Test
    void createNotification() {
        //Assign
        User user = new User("user1", "password", "test1@test.com", "User1", "Test1", new Date() ,"About Me");
        User followed = new User("user2", "password", "test2@test.com", "User2", "Test2", new Date() ,"About Me");
        user.setUserId(1);
        followed.setUserId(2);
        user.setLastNotification(Long.parseLong("1633099934481"));
        followed.setLastNotification(Long.parseLong("1633099934481"));

        Set<Integer> user_following = new HashSet<>();
        user_following.add(followed.getUserId());
        user.setUser_following(user_following);

        Set<Integer> user_followers = new HashSet<>();
        user.setFollowers(user_followers);

        Set<Integer> follow_following = new HashSet<>();
        follow_following.add(user.getUserId());
        followed.setFollowers(follow_following);
        followed.setUser_following(user_followers);

        System.out.println("USER: " + user);
        System.out.println();
        System.out.println("FOLLOWED: " + followed);
        System.out.println();

        Response response = new Response(true,"notification created",null);
        UserResponse userResponse = new UserResponse(1,"User1 Test1",null,Long.parseLong("1633099934481"));
        Notification expectedResult = new Notification(1,"follow",Long.parseLong("1633099934481"),followed,user,1,response,userResponse);

        user.setUserId(1);
        followed.setUserId(2);

        System.out.println(expectedResult);

        Mockito.when(notificationDao.save(expectedResult)).thenReturn(expectedResult);

        //Act
        Notification actualResult = this.notificationService.createNotification(expectedResult);
        System.out.println(actualResult);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void format() {
    }

    @Test
    void getTop10NotificationByUserID() {
        //Assign
        User user = new User("user1", "password", "test1@test.com", "User1", "Test1", new Date() ,"About Me");
        User follow = new User("user2", "password", "test2@test.com", "User2", "Test2", new Date() ,"About Me");
        user.setUserId(1);
        follow.setUserId(2);

        List<Notification> notificationList = new ArrayList<>();
        notificationList.add(new Notification(1,"follow",Long.parseLong("1633099934481"),follow,user,1,null,null));
        Integer expectedResult = notificationList.size();
        System.out.println(expectedResult);

        //Mock
        Mockito.when(userDao.findById(user.getUserId())).thenReturn(Optional.of(user));
        Mockito.when(notificationDao.findTop10ByUserBelongToOrderByTimestampDesc(user)).thenReturn(notificationList);
        Mockito.when(userDao.save(user)).thenReturn(user);

        //Act
        Integer actualResult = this.notificationService.getTop10NotificationByUserID(user.getUserId()).size();
        System.out.println(actualResult);

        //Asset
        assertEquals(expectedResult,actualResult);

        //Verify
        Mockito.verify(userDao, Mockito.times(1) ).findById(user.getUserId());
        Mockito.verify(notificationDao, Mockito.times(1)).findTop10ByUserBelongToOrderByTimestampDesc(user);
        Mockito.verify(userDao, Mockito.times(1)).save(user);
    }

    @Test
    void getNotificationByUserID() {
    }
}